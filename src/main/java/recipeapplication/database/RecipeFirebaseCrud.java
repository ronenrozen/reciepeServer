package recipeapplication.database;

import javax.annotation.PostConstruct;

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
	public void create(Recipe data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read(String document) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Recipe data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String document) {
		// TODO Auto-generated method stub
		
	}


}
