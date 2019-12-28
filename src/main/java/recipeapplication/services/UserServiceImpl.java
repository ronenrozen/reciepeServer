package recipeapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recipeapplication.components.User;
import recipeapplication.database.UserFirebaseCrud;

@Service
public class UserServiceImpl implements UserService {

	private UserFirebaseCrud users;

	@Autowired
	public UserServiceImpl(UserFirebaseCrud users) {
		this.users = users;
	}

	@Override
	public void addUser(User user) {
		this.users.create(user);

	}

}
