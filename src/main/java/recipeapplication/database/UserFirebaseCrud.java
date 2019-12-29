package recipeapplication.database;

import Utils.FinalsStringsExceptions;
import Utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.cloud.firestore.Firestore;

import org.springframework.web.server.ResponseStatusException;
import recipeapplication.components.User;

import java.util.Map;

@Component
public class UserFirebaseCrud implements FirebaseCrud<User> {

    private Firestore firestore;
    private String collection;
    private ObjectMapper mapper; // jackson's objectmapper

    @Autowired
    UserFirebaseCrud(Firestore firestore,
                     @Value("${recipeapp.firestore.collection.users:users}") String collection) {
        this.firestore = firestore;
        this.collection = collection;
        this.mapper = new ObjectMapper();
    }

    @Override
    public User create(User user) throws Exception {
        if (!isAllUserDetailsValid(user)) {
            this.firestore.collection(collection).document(user.getId()).set(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create the user, please verify that all the required parameters are filled correctly");
        }
        return user;
    }

    @Override
    public User update(User user) throws Exception {
        if (user.getFavoriteRecipes() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The edited user has not favoriteRecipes. Can't update his details ");
        }

        User userFromDb = read(user.getId());
        if (userFromDb == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, FinalsStringsExceptions.NO_USER_FOUND);
        } else {
            user.setRole(userFromDb.getRole());
            user.setName(userFromDb.getName());
            this.firestore.collection(collection).document(user.getId()).set(user, SetOptions.merge());
        }

        return user;
    }

    @Override
    public User delete(String document) throws Exception {
        /*Fb delete does not return an object, just details about the deletion time so we have to read the object in order to return it back*/
        User user = read(document);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, FinalsStringsExceptions.NO_USER_FOUND);
        }

        this.firestore.collection(collection).document(document).delete();
        return user;
    }

    @Override
    public User read(String document) throws Exception {

        if (!StringUtils.isNullOrEmptyTrimmed(document)) {
            DocumentReference userRef = this.firestore.collection(collection).document(document);
            User userPojo = null;
            try {
                DocumentSnapshot userSnapshot = userRef.get().get();
                Map<String, Object> userMap = userSnapshot.getData();
                if (userMap != null) {
                    /*Use jackson in order to convert the map to an user instance*/
                    userPojo = mapper.convertValue(userMap, User.class);
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, FinalsStringsExceptions.NO_USER_FOUND);
                }
            } catch (Exception e) {
                if (e instanceof ResponseStatusException) {
                    throw e;
                } else {
                    System.err.println("Could not read the user");
                }
            }
            return userPojo;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not fetch the user, please verify that all the required parameters are filled correctly");
        }
    }

    private boolean isAllUserDetailsValid(User user) {
        return StringUtils.isNullOrEmptyTrimmed(user.getId()) || user.getName() == null ||
                StringUtils.isNullOrEmptyTrimmed(user.getName().getFirst()) || StringUtils.isNullOrEmptyTrimmed(user.getName().getLast())
                || user.getRole() == null;
    }

}
