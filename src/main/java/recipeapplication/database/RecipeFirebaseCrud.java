package recipeapplication.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import recipeapplication.components.Recipe;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
public class RecipeFirebaseCrud implements FirebaseCrud<Recipe> {

	private Firestore firestore;
	private String collection;
	private ObjectMapper mapper; // jackson's object mapper
	
	@Autowired
	public RecipeFirebaseCrud(Firestore firestore,
			@Value("${recipeapp.firestore.collection.recipes:recipes}") String collection) {
		this.firestore = firestore;
		this.collection = collection;
		this.mapper = new ObjectMapper();
	}

	@Override
	public Recipe create(Recipe data) {
		System.out.println("In recipe creation section");
		DocumentReference docRef = this.firestore.collection(collection).document(data.getId());
		try {
			if (!docRef.get().get().exists())  // Check if document already exists, to not allow overwriting it with create.
				docRef.set(data);
			else
				throw new RuntimeException("Failed to create, Recipe with this ID already exists!");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Recipe read(String document) {
		System.out.println("In recipe reading section");
		try {
			DocumentSnapshot docSnap = this.firestore.collection(collection).document(document).get().get();
			if (!docSnap.exists())
				throw new RuntimeException("Failed to read, recipe doesn't exist!");
			else {
				Map<String, Object> data = docSnap.getData();
				return mapper.convertValue(data, Recipe.class);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public Recipe update(Recipe data) {
		System.out.println("In recipe updating section");
		DocumentReference docRef = this.firestore.collection(collection).document(data.getId());
		try {
			DocumentSnapshot docSnap = docRef.get().get();

			if(docSnap.exists() && Objects.requireNonNull(docSnap.get("id")).toString().equalsIgnoreCase(data.getId())) {
				docRef.set(data);
			}
			else
				throw new RuntimeException("Failed to update, invalid id / recipe not found.");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Recipe delete(String document) {
		System.out.println("In recipe deleting section");
		DocumentReference docRef = this.firestore.collection(collection).document(document);
		try {
			DocumentSnapshot docSnap = docRef.get().get();

			if(docSnap.exists() && Objects.requireNonNull(docSnap.get("id")).toString().equalsIgnoreCase(document)) {
				Recipe deletedRecipe = read(document);
				docRef.delete();
				return deletedRecipe;
			}

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed to delete, invalid id / recipe not found.");

		
	}


}
