package Utils;

public interface FinalsStringsExceptions {
    String NO_USER_FOUND = "User not found";
    String CREATE_BAD_REQUEST = "Could not create the user, please verify that all the required parameters are filled correctly";
    String UPDATE_BAD_REQUEST = "The edited user has not favoriteRecipes. Can't update his details ";
    String READ_BAD_REQUEST = "Could not fetch the user, please verify that all the required parameters are filled correctly";
    String ID_NOT_EQUAL = "The id provided in the path variable is not equal to the recipe id";
    String RECIPE_NOT_FOUND = "recipe not found.";
    String RECIPE_EXISTS = "Failed to create, Recipe with this ID already exists!";
}
