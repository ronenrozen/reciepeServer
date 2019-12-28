package TransferObjects;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class UserEditTO {

    @NotNull
    ArrayList<String> favoriteRecipes;

    public UserEditTO() {

    }

    public UserEditTO(@NotNull ArrayList<String> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public ArrayList<String> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(ArrayList<String> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }


}
