package recipeapplication.boundries;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RecipeTO {

	@NotNull
	String id;

	@NotNull
	String category;

	@NotNull
	List<String> ingridiant;

	@NotNull
	String preperation;

	public RecipeTO() {
	}

	public RecipeTO(String id, String category, List<String> ingridiant, String preperation,
			MultipartFile recipeImage) {
		super();
		this.id = id;
		this.category = category;
		this.ingridiant = ingridiant;
		this.preperation = preperation;
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

	public List<String> getIngridiant() {
		return ingridiant;
	}

	public void setIngridiant(List<String> ingridiant) {
		this.ingridiant = ingridiant;
	}

	public String getPreperation() {
		return preperation;
	}

	public void setPreperation(String preperation) {
		this.preperation = preperation;
	}
}
