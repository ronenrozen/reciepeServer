package recipeapplication.services;

import com.google.cloud.storage.Bucket;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    public Recipe addRecipe(Recipe recipe) {
        return this.recipes.create(recipe);
    }

    public Recipe addRecipe(Recipe recipe, MultipartFile recipeImage) {
        if (recipeImage != null && !recipeImage.isEmpty()) {
            saveImageToStorage(recipe, recipeImage);
        }
        return this.addRecipe(recipe);
    }

    public Recipe readRecipe(String recipeId) {
        return this.recipes.read(recipeId);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe, MultipartFile recipeImage) {
        try {
            if (recipeImage != null && !recipeImage.isEmpty()) {
                saveImageToStorage(recipe, recipeImage);
                Recipe oldRecipe = this.readRecipe(recipe.getId());
                if (oldRecipe.getRecipeImageId() != null) {
                    this.bucket.get(oldRecipe.getRecipeImageId()).delete();
                }
            }
            return this.recipes.update(recipe);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveImageToStorage(Recipe recipe, MultipartFile recipeImage) {
        try {
            String id = this.getRecipeImageFileName(recipe, recipeImage);
            this.bucket.create(id, recipeImage.getBytes());
            recipe.setRecipeImageId(id);
        } catch (Exception e) {
            try {
              // Rollback, leave no trace
              this.bucket.get(id).delete();
              recipe.setRecipeImageId(null);
            } catch (Exception ex) {
              throw new RuntimeException("Exception in saveImageToStorage catch: " + ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recipe deleteRecipe(String id) {
        Recipe recipeForDeletion = this.recipes.delete(id);
        if (recipeForDeletion.getRecipeImageId() != null) {
            this.bucket.get(recipeForDeletion.getRecipeImageId()).delete();
        }
        return recipeForDeletion;
    }

    private String getRecipeImageFileName(Recipe recipe, MultipartFile recipeImage) {
        return recipe.getId() + "." + FilenameUtils.getExtension(recipeImage.getOriginalFilename());
    }
}
