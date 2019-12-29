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
import recipeapplication.Exceptions.BadRequestException;
import recipeapplication.Exceptions.UserNotFoundException;
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
    public User create(User user) {
        if (!isAllUserDetailsValid(user)) {
            this.firestore.collection(collection).document(user.getId()).set(user);
        } else {
            throw new BadRequestException(FinalsStringsExceptions.CREATE_BAD_REQUEST);
        }
        return user;
    }

    @Override
    public User update(User user) {

        if (user.getFavoriteRecipes() == null) {
            throw new BadRequestException(FinalsStringsExceptions.UPDATE_BAD_REQUEST);
        }

        User userFromDb = read(user.getId());
        if (userFromDb == null) {
            throw new UserNotFoundException(FinalsStringsExceptions.NO_USER_FOUND);
        } else {
            //TODO - EDIT JUST the array properties and not replace the current array
            user.setRole(userFromDb.getRole());
            user.setName(userFromDb.getName());
            this.firestore.collection(collection).document(user.getId()).set(user, SetOptions.merge());
        }
        return user;
    }

    @Override
    public User delete(String document) {
        /*Fb delete does not return an object, just details about the deletion time so we have to read the object in order to return it back*/
        User user = read(document);
        if (user == null) {
            throw new UserNotFoundException(FinalsStringsExceptions.NO_USER_FOUND);
        }
        this.firestore.collection(collection).document(document).delete();
        return user;
    }

    @Override
    public User read(String document) {

        if (!StringUtils.isEmptyTrimmed(document)) {
            DocumentReference userRef = this.firestore.collection(collection).document(document);
            User userPojo = null;
            DocumentSnapshot userSnapshot;
            try {
                userSnapshot = userRef.get().get();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            Map<String, Object> userMap = userSnapshot.getData();
            if (userMap != null) {
                /*Use jackson in order to convert the map to an user instance*/
                userPojo = mapper.convertValue(userMap, User.class);
            } else {
                throw new UserNotFoundException(FinalsStringsExceptions.NO_USER_FOUND);
            }
            return userPojo;
        } else {
            throw new BadRequestException(FinalsStringsExceptions.READ_BAD_REQUEST);
        }
    }

    private boolean isAllUserDetailsValid(User user) {
        return StringUtils.isEmptyTrimmed(user.getId()) ||
                StringUtils.isEmptyTrimmed(user.getName().getFirst()) || StringUtils.isEmptyTrimmed(user.getName().getLast());
    }

}
