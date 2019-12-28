package recipeapplication.database;

import org.springframework.stereotype.Component;

import recipeapplication.components.Recipe;

@Component
public class RecipeFirebaseCrud implements FirebaseCrud<Recipe> {

	@Override
	public void create(String collection, String document, Recipe data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read(String collection, String document) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String collection, String doucmnet, Recipe data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String collection, String document) {
		// TODO Auto-generated method stub
		
	}

}
