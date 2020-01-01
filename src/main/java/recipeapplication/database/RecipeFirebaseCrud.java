package recipeapplication.database;

import Utils.FinalsStringsExceptions;
import Utils.FirebaseUtils;
import Utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import recipeapplication.components.Recipe;
import recipeapplication.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class RecipeFirebaseCrud implements FirebaseCrud<Recipe> {

    private Firestore firestore;
    private String collection;
    private ObjectMapper mapper;

    @Autowired
    public RecipeFirebaseCrud(Firestore firestore, @Value("${recipeapp.firestore.collection.recipes:recipes}") String collection) {
        this.firestore = firestore;
        this.collection = collection;
        this.mapper = new ObjectMapper();
    }

    @Override
    public Recipe create(Recipe data) {
        DocumentReference docRef = this.getDocRef(data.getId());
        FirebaseUtils.checkIfExists(docRef, collection);
        docRef.set(data);
        return data;
    }

    @Override
    public Recipe read(String document) {
        if (StringUtils.isEmptyTrimmed(document)) {
            throw new BadRequestException(FinalsStringsExceptions.READ_BAD_REQUEST);
        }
        DocumentReference docRef = this.getDocRef(document);
        FirebaseUtils.checkIfNotExists(docRef, collection);
        return mapper.convertValue(FirebaseUtils.getSnapshot(docRef).getData(), Recipe.class);
    }

    @Override
    public Recipe update(Recipe data) {
        DocumentReference docRef = this.getDocRef(data.getId());
        FirebaseUtils.checkIfNotExists(docRef, collection);
        docRef.set(data);
        return data;
    }

    @Override
    public Recipe delete(String document) {
        DocumentReference docRef = this.getDocRef(document);
        FirebaseUtils.checkIfNotExists(docRef, collection);
        Recipe deletedRecipe = read(document);
        docRef.delete();
        return deletedRecipe;
    }

    public Recipe[] queryCategory(String category) {
        System.out.println("In queryCategory crud");
        CollectionReference recipes = firestore.collection(collection);
        Query query = recipes.whereEqualTo("category", category.toUpperCase());

        return getRecipeArrayFromQuery(query).toArray(new Recipe[0]);

    }

    public Recipe[] queryIngredients(String[] ingredients) {
        System.out.println("In queryIngredients crud");
        CollectionReference recipes = firestore.collection(collection);
        Query query = recipes.whereArrayContainsAny("ingredients", Arrays.asList(ingredients));

        ArrayList<Recipe> recipeArrayList = getRecipeArrayFromQuery(query);
        for (Recipe recipe : recipeArrayList) {
            if(!StringUtils.isFullyContainedInArray(recipe.getIngredients(), Arrays.asList(ingredients))) {
                recipeArrayList.remove(recipe);
            }
        }
        return recipeArrayList.toArray(new Recipe[0]);
    }

    private ArrayList<Recipe> getRecipeArrayFromQuery(Query query) {
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
        for (DocumentSnapshot document : FirebaseUtils.getQuerySnapshot(querySnapshot).getDocuments()) {
            recipeArrayList.add(mapper.convertValue(document.getData(), Recipe.class));
        }
        return recipeArrayList;
    }
    private DocumentReference getDocRef(String id) {
        return this.firestore.collection(collection).document(id);
    }
}
