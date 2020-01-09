package recipeapplication.database;

import Utils.FinalsStringsExceptions;
import Utils.FirebaseUtils;
import Utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.firestore.Firestore;

import recipeapplication.exceptions.BadRequestException;
import recipeapplication.exceptions.UserNotFoundException;
import recipeapplication.components.User;


@Component
public class UserFirebaseCrud implements FirebaseCrud<User> {
    private Firestore firestore;
    private String collection;
    private ObjectMapper mapper;

    @Autowired
    UserFirebaseCrud(Firestore firestore, @Value("${recipeapp.firestore.collection.users:users}") String collection) {
        this.firestore = firestore;
        this.collection = collection;
        this.mapper = new ObjectMapper();
    }

    @Override
    public User create(User user) {
        if (AnyUserDetailsAreNotValid(user)) {
            throw new BadRequestException(FinalsStringsExceptions.CREATE_BAD_REQUEST);
        }
        DocumentReference docRef = this.getDocRef(user.getId());
        FirebaseUtils.checkIfExists(docRef, collection);
        docRef.set(user);
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getFavoriteRecipes() == null && user.getRecipes() == null) {
            throw new BadRequestException(FinalsStringsExceptions.UPDATE_BAD_REQUEST);
        }

        User userFromDb = read(user.getId());
        if (userFromDb == null) {
            throw new UserNotFoundException(FinalsStringsExceptions.NO_USER_FOUND);
        }

        user.setRole(userFromDb.getRole());
        user.setName(userFromDb.getName());

        if (user.getFavoriteRecipes() == null) {
            user.setFavoriteRecipes(userFromDb.getFavoriteRecipes());
        }

        if (user.getRecipes() == null) {
            user.setRecipes(userFromDb.getRecipes());
        }

        this.firestore.collection(collection).document(user.getId()).set(user, SetOptions.merge());

        return user;
    }

    @Override
    public User delete(String document) {
        DocumentReference docRef = this.getDocRef(document);
        FirebaseUtils.checkIfNotExists(docRef, collection);
        User user = read(document);
        docRef.delete();
        return user;
    }

    @Override
    public User read(String document) {
        if (StringUtils.isEmptyTrimmed(document)) {
            throw new BadRequestException(FinalsStringsExceptions.READ_BAD_REQUEST);
        }
        DocumentReference docRef = this.getDocRef(document);
        FirebaseUtils.checkIfNotExists(docRef, collection);
        return mapper.convertValue(FirebaseUtils.getSnapshot(docRef).getData(), User.class);
    }

    private boolean AnyUserDetailsAreNotValid(User user) {
        return StringUtils.isEmptyTrimmed(user.getId()) ||
                StringUtils.isEmptyTrimmed(user.getName().getFirst()) ||
                StringUtils.isEmptyTrimmed(user.getName().getLast());
    }

    private DocumentReference getDocRef(String id) {
        return this.firestore.collection(collection).document(id);
    }
}
