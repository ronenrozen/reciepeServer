package recipeapplication.services;


import recipeapplication.components.User;

public interface UserService {
    User addUser(User user);

    User readUser(String id);

    User updateUser(User user);

    User deleteUser(String id);
}
