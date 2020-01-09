package recipeapplication.boundaries;

import java.util.List;

public class UserEditTO {

    List<String> favoriteRecipes;
    List<String> recipes;

    public UserEditTO() {

    }

    public UserEditTO(List<String> favoriteRecipes, List<String> recipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public List<String> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    public List<String> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<String> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }


}
