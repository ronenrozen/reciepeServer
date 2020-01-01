package recipeapplication.exceptions;

public class RecipeAlreadyExistsException extends RuntimeException {
    public RecipeAlreadyExistsException() {
    }

    public RecipeAlreadyExistsException(String message) {
        super(message);
    }

    public RecipeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public RecipeAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
