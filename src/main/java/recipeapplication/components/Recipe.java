package recipeapplication.components;

import javax.validation.constraints.NotNull;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Recipe {

	@NotNull
	String id;

	@NotNull
	String category;

	@NotNull
	String[] ingridiant;

	@NotNull
	String preperation;

	String recipeImage; // TODO: figure out how to store images

	public Recipe() {
	}

	public Recipe(String id, String category, String[] ingridiant, String preperation, String recipeImage) {
		super();
		this.id = id;
		this.category = category;
		this.ingridiant = ingridiant;
		this.preperation = preperation;
		this.recipeImage = recipeImage;
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

	public String[] getIngridiant() {
		return ingridiant;
	}

	public void setIngridiant(String[] ingridiant) {
		this.ingridiant = ingridiant;
	}

	public String getPreperation() {
		return preperation;
	}

	public void setPreperation(String preperation) {
		this.preperation = preperation;
	}

	public String getRecipeImage() {
		return recipeImage;
	}

	public void setRecipeImage(String recipeImage) {
		this.recipeImage = recipeImage;
	}
}
