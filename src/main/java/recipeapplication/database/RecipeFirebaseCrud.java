package recipeapplication.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.firestore.Firestore;
import recipeapplication.components.Recipe;

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
	public Recipe create(Recipe data) {

		this.firestore.collection(collection).document(data.getId()).set(data);
		return null;
	}

	@Override
	public Recipe read(String document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Recipe update(Recipe data) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public Recipe delete(String document) {
		// TODO Auto-generated method stub
		return null;
	}


}
