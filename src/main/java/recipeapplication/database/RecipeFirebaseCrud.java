package recipeapplication.database;

import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import recipeapplication.components.Recipe;

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

		try {
			if (this.firestore.collection(collection).document(data.getId()).get().get().exists() == false)  // Check if document already exists, to not allow overwriting it with create.
				this.firestore.collection(collection).document(data.getId()).set(data);
			else
				throw new Exception("Failed to create, Recipe with this ID already exists!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Recipe read(String document) {
		System.out.println("In recipe reading section");
		try {
			DocumentSnapshot docSnap = this.firestore.collection(collection).document(document).get().get();
			if (docSnap.exists() == false)
				throw new Exception("Failed to read, recipe doesn't exist!");
			else {
				Map<String, Object> data = this.firestore.collection(collection).document(document).get().get().getData();

				Recipe recipe = new Recipe();
				recipe.setId(data.get("id").toString());
				recipe.setCategory(data.get("category").toString());
				recipe.setIngridiant(((List<String>) data.get("ingridiant")));
				recipe.setPreperation(data.get("preperation").toString());
				if (data.get("recipeImageId") != null)
					recipe.setRecipeImageId(data.get("recipeImageId").toString());
				return recipe;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	@Override
	public void update(Recipe data) {
		System.out.println("In recipe updating section");

		try {
			DocumentSnapshot docSnap = this.firestore.collection(collection).document(data.getId()).get().get();
			if(docSnap.exists() && docSnap.get("id").toString().equalsIgnoreCase(data.getId())) {
				this.firestore.collection(collection).document(data.getId()).set(data);
			}
			else
				throw new Exception("Failed to update, recipe doesn't exist!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void delete(String document) {
		// TODO Auto-generated method stub
		
	}


}
