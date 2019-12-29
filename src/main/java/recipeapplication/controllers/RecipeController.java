package recipeapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import recipeapplication.boundries.RecipeTO;
import recipeapplication.components.Recipe;
import recipeapplication.services.RecipeService;

@RestController
public class RecipeController {
	private RecipeService recipes;

	@Autowired
	public RecipeController(RecipeService recipes) {
		super();
		this.recipes = recipes;
	}

	@RequestMapping(value = "/recipe", method = RequestMethod.POST)
	public void addRecipe(
			@RequestPart(value = "recipeImage", required = false) MultipartFile file,
			@ModelAttribute("recipe") RecipeTO recipe) {
		this.recipes.addRecipe(toEntity(recipe), file);
	}

	private Recipe toEntity(RecipeTO recipe) {
		Recipe recipeEntity = new Recipe();
		recipeEntity.setCategory(recipe.getCategory());
		recipeEntity.setId(recipe.getId());
		recipeEntity.setIngridiant(recipe.getIngridiant());
		recipeEntity.setPreperation(recipe.getPreperation());

		return recipeEntity;
	}
}
