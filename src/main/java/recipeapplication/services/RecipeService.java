package recipeapplication.services;

import org.springframework.web.multipart.MultipartFile;

import recipeapplication.components.Recipe;

public interface RecipeService {
	
	public void addRecipe(Recipe recipe);
	
	public void addRecipe(Recipe recipe, MultipartFile recipeImage);
	
	public Recipe readRecipe(String id);

	public Recipe updateRecipe(Recipe recipe);

	public Recipe updateRecipe(Recipe recipe, MultipartFile recipeImage);

	public Recipe deleteRecipe(String id);
}
