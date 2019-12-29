package recipeapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

	@RequestMapping(value = "/postRecipe", method = RequestMethod.POST)
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

	@RequestMapping(value = "/putRecipeById", method = RequestMethod.PUT)
	public void recipePut(@RequestParam String id,
						   @RequestPart(value = "recipeImage", required = false) MultipartFile file,
						  @ModelAttribute("recipe") RecipeTO recipe) {
		if (id.equalsIgnoreCase(recipe.getId()) &&
				recipe.getCategory() != null &&
				recipe.getIngridiant() != null &&
				recipe.getPreperation() != null)
			this.recipes.updateRecipe(toEntity(recipe));
		}

	@RequestMapping(value = "/requestRecipeById", method = RequestMethod.GET)
	public Recipe recipeGet(@RequestParam String id)
	{
		return this.recipes.readRecipe(id);
	}
}
