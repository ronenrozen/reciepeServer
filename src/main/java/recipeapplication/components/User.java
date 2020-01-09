package recipeapplication.components;

import javax.validation.constraints.NotNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class User {

    @NotNull
    private String id;

    @NotNull
    private Role role;

    @NotNull
    private Name name;

    private List<String> recipes;

    private List<String> favoriteRecipes;

    public User() {
    }

    public User(@NotNull List<String> favoriteRecipes, @NotNull List<String> recipes, @NotNull String id) {
        this.favoriteRecipes = favoriteRecipes;
        this.recipes = recipes;
        this.id = id;
    }

    public User(@NotNull String id, @NotNull Role role, @NotNull Name name) {
        super();
        this.id = id;
        this.role = role;
        this.name = name;
    }

    public User(@NotNull String id, @NotNull Role role, @NotNull Name name, List<String> recipes, List<String> favoriteRecipes) {
        super();
        this.id = id;
        this.role = role;
        this.name = name;
        this.recipes = recipes == null ? new ArrayList<>() : recipes;
        this.favoriteRecipes = favoriteRecipes == null ? new ArrayList<>() : favoriteRecipes;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
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
