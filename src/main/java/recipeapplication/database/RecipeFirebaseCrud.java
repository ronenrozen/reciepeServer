package recipeapplication.database;

import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.multipart.MultipartFile;
import recipeapplication.components.Recipe;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class RecipeFirebaseCrud implements FirebaseCrud<Recipe> {

	private Firestore firestore;
	private String collection;
	
	@Autowired
	public RecipeFirebaseCrud(Firestore firestore,
			@Value("${recipeapp.firestore.collection.recipes:recipes}") String collection) {
		this.firestore = firestore;
		this.collection = collection;
	}

	@Override
	public void create(Recipe data) {
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

				return new Recipe(
						data.get("id").toString(),
						data.get("category").toString(),
						((List<String>) data.get("ingridiant")),
						data.get("preperation").toString(),
						data.get("recipeImageId") != null ? data.get("recipeImageId").toString() : null);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public void update(Recipe data) {
		System.out.println("In recipe updating section");
		DocumentReference docRef = this.firestore.collection(collection).document(data.getId());
		try {
			DocumentSnapshot docSnap = docRef.get().get();

			if(docSnap.exists() && docSnap.get("id").toString().equalsIgnoreCase(data.getId())) {
				docRef.set(data);
			}
			else
				throw new RuntimeException("Failed to update, invalid id / recipe not found.");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(String document) {
		// TODO Auto-generated method stub
		
	}


}
