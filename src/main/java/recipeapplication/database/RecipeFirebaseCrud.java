package recipeapplication.database;

import Utils.FinalsStringsExceptions;
import Utils.FirebaseUtils;
import Utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import recipeapplication.components.Recipe;
import recipeapplication.exceptions.BadRequestException;

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

    private DocumentReference getDocRef(String id) {
        return this.firestore.collection(collection).document(id);
    }
}
