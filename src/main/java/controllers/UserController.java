package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.firestore.Firestore;

import components.User;

@RestController
public class UserController {

	private Firestore firestore;

	@Autowired
	public UserController(Firestore firestore) {
		super();
		this.firestore = firestore;
	}

	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public void test() {
		User a = new User();
		a.setId("1");
		firestore.collection("users").document("1").set(a);
	}
}
