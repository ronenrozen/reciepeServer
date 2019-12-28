package recipeapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import recipeapplication.components.User;
import recipeapplication.services.UserService;

@RestController
public class UserController {

	private UserService users;

	@Autowired
	public UserController(UserService users) {
		super();
		this.users = users;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() {
		User a = new User();
		a.setId("2");
		users.addUser(a);
	}
}
