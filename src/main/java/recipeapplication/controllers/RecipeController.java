package recipeapplication.controllers;

import Utils.FinalsStringsExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import recipeapplication.boundaries.RecipeTO;
import recipeapplication.components.Recipe;
import recipeapplication.exceptions.BadRequestException;
import recipeapplication.services.RecipeService;

import javax.validation.Valid;

@RestController
public class RecipeController {
    private RecipeService recipes;

    @Autowired
    public RecipeController(RecipeService recipes) {
        super();
        this.recipes = recipes;
    }

    @PostMapping(
            value = "/recipe",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe addRecipe(
            @RequestPart(value = "recipeImage", required = false) MultipartFile file,
            @ModelAttribute("recipe") @Valid RecipeTO recipe) {
        return this.recipes.addRecipe(toEntity(recipe), file);
    }

    @PutMapping(
            path = "/recipe/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe recipePut(
            @PathVariable("id") String id,
            @RequestPart(value = "recipeImage", required = false) MultipartFile file,
            @ModelAttribute("recipe") @Valid RecipeTO recipe) {
        if (!id.equalsIgnoreCase(recipe.getId()))
            throw new BadRequestException(FinalsStringsExceptions.ID_NOT_EQUAL);
        return this.recipes.updateRecipe(toEntity(recipe), file);
    }

    @GetMapping(
            path = "/recipe/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe recipeGet(@PathVariable("id") String id) {
        return this.recipes.readRecipe(id);
    }

    @DeleteMapping(
            value = "/recipe/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe recipeDelete(@PathVariable("id") String id) {
        return this.recipes.deleteRecipe(id);
    }

    @GetMapping(
            path = "/recipeSearch",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe[] recipeSearch(@RequestParam(name = "byCategory", required = false) String category, @RequestParam(name = "byIngredients", required = false) String [] ingredients) {
        return this.recipes.searchRecipe(category, ingredients);
    }



    private Recipe toEntity(RecipeTO recipe) {
        Recipe recipeEntity = new Recipe();
        recipeEntity.setCategory(recipe.getCategory());
        recipeEntity.setId(recipe.getId());
        recipeEntity.setIngredients(recipe.getIngredients());
        recipeEntity.setPreparation(recipe.getPreparation());

        return recipeEntity;
    }
}
