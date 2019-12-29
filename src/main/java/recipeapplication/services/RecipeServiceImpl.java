package recipeapplication.services;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Bucket;

import recipeapplication.components.Recipe;
import recipeapplication.database.RecipeFirebaseCrud;

@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeFirebaseCrud recipes;
	private Bucket bucket;

	@Autowired
	public RecipeServiceImpl(RecipeFirebaseCrud recipes, Bucket bucket) {
		this.recipes = recipes;
		this.bucket = bucket;
	}

	@Override
	public void addRecipe(Recipe recipe) {
		this.recipes.create(recipe);
	}

	public void addRecipe(Recipe recipe, MultipartFile recipeImage) {
		try {
			if (recipeImage != null && !recipeImage.isEmpty()) {
				String id = recipe.getId() + "." + FilenameUtils.getExtension(recipeImage.getOriginalFilename());
				this.bucket.create(id, recipeImage.getBytes());
				recipe.setRecipeImageId(id);
			}
			this.addRecipe(recipe);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
