package recipeapplication.components;

import javax.validation.constraints.NotNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class User {

    @NotNull
    private String id;

    @NotNull
    private Role role;

    @NotNull
    private Name name;

    @NotNull
    private ArrayList<String>  favoriteRecipes;

    public User() {
    }

	public User(@NotNull ArrayList<String> favoriteRecipes, @NotNull String id) {
    	this.favoriteRecipes = favoriteRecipes;
    	this.id = id;
	}

    public User(@NotNull String id, @NotNull Role role, @NotNull Name name, @NotNull ArrayList<String>  favoriteRecipes) {
        super();
        this.id = id;
        this.role = role;
        this.name = name;
        this.favoriteRecipes = favoriteRecipes;
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


    public ArrayList<String>  getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(ArrayList<String>  favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

}
