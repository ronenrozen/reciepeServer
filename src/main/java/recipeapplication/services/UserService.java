package recipeapplication.services;


import TransferObjects.UserEditTO;
import recipeapplication.components.User;

public interface UserService {
	User addUser(User user);
	User readUser(String id);
	User updateUser(User user) throws Exception;
	User deleteUser(String id);
}
