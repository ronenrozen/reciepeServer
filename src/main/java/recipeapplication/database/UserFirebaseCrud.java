package recipeapplication.database;

import TransferObjects.UserEditTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.*;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.firestore.Firestore;

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
    public void create(User user) {

        //Todo - more validations
        this.firestore.collection(collection).document(user.getId()).set(user);
    }

    @Override
    public User update(User user) throws Exception {
        User userFromDb = read(user.getId());
        if (userFromDb == null) {
            throw new Exception("User does not exist");
        } else {
            user.setRole(userFromDb.getRole());
            user.setName(userFromDb.getName());
            this.firestore.collection(collection).document(user.getId()).set(user, SetOptions.merge());
        }

        return user;
    }


    @Override
    public User delete(String document) {
        //Todo - more validations (empty id ..)
        /*Fb delete does not return an object, just details about the deletion time so we have to read the object in order to return it back*/
        User user = read(document);
        /*delete does not fail if a document doesn't exist*/
        this.firestore.collection(collection).document(document).delete();
        return user;
    }

    @Override
    public User read(String document) {
        //Todo - more validations  (empty id ..)
        DocumentReference userRef = this.firestore.collection(collection).document(document);
        User userPojo = null;
        try {
            DocumentSnapshot userSnapshot = userRef.get().get();
            Map<String, Object> userMap = userSnapshot.getData();
            if (userMap != null) {
                /*Use jackson in order to convert the map to an user instance*/
                userPojo = mapper.convertValue(userMap, User.class);
            }
        } catch (Exception e) {
            System.err.println("Could not delete the user");
        }
        return userPojo;
    }


}
