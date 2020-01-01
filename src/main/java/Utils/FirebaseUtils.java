package Utils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import recipeapplication.exceptions.BadRequestException;
import recipeapplication.exceptions.RecipeAlreadyExistsException;
import recipeapplication.exceptions.RecipeNotFoundException;
import recipeapplication.exceptions.UserNotFoundException;

public class FirebaseUtils {
    public static void checkIfNotExists(DocumentReference docRef, String collection) {
        if (!getSnapshot(docRef).exists()) {
            if (collection.equals("recipes")) {
                throw new RecipeNotFoundException(FinalsStringsExceptions.RECIPE_NOT_FOUND);
            } else {
                throw new UserNotFoundException(FinalsStringsExceptions.NO_USER_FOUND);
            }
        }
    }

    public static void checkIfExists(DocumentReference docRef, String collection) {
        if (getSnapshot(docRef).exists()) {
            if (collection.equals("recipes")) {
                throw new RecipeAlreadyExistsException(FinalsStringsExceptions.RECIPE_EXISTS);
            } else {
                throw new BadRequestException(FinalsStringsExceptions.CREATE_BAD_REQUEST);
            }
        }
    }

    public static DocumentSnapshot getSnapshot(DocumentReference documentReference) {
        try {
            return documentReference.get().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static QuerySnapshot getQuerySnapshot(ApiFuture<QuerySnapshot> apiQuerySnapshot) {
        try {
            return apiQuerySnapshot.get();
        } catch (Exception e) {
            throw new RuntimeException("Error in getting QuerySnapshot");
        }

    }
}
