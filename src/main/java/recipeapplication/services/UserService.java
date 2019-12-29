package recipeapplication.services;


import TransferObjects.UserEditTO;
import recipeapplication.components.User;

public interface UserService {
	User addUser(User user) throws Exception;
	User readUser(String id) throws Exception;
	User updateUser(User user) throws Exception;
	User deleteUser(String id) throws Exception;
}
