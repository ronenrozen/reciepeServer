package recipeapplication.components;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Recipe {

	@NotNull
	String id;

	@NotNull
	String category;

	@NotNull
	List<String> ingredients;

	@NotNull
	String preparation;

	String recipeImageId;

	public Recipe() {
	}

	public Recipe(String id, String category, List<String> ingredients, String preparation, String recipeImageId) {
		super();
		this.id = id;
		this.category = category;
		this.ingredients = ingredients;
		this.preparation = preparation;
		this.recipeImageId = recipeImageId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

	public String getRecipeImageId() {
		return recipeImageId;
	}

	public void setRecipeImageId(String recipeImageId) {
		this.recipeImageId = recipeImageId;
	}
}
