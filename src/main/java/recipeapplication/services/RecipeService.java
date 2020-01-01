package recipeapplication.services;

import org.springframework.web.multipart.MultipartFile;

import recipeapplication.components.Recipe;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    Recipe addRecipe(Recipe recipe, MultipartFile recipeImage);

    Recipe readRecipe(String id);

    Recipe[] search(String category);

    Recipe[] search(String[] ingredients);

    Recipe updateRecipe(Recipe recipe, MultipartFile recipeImage);

    Recipe deleteRecipe(String id);
}
