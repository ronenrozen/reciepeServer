package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;

import components.User;

@RestController
public class UserController {

	private DatabaseReference firebase;

	@Autowired
	public UserController(FirebaseApp firebase) {
		super();
		this.firebase = FirebaseDatabase.getInstance(firebase).getReference();
	}

	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public void test() {
		User a = new User();
		a.setId("1");
		this.firebase.child("users").child("1").setValue(a, new CompletionListener() {
			
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (error != null) {
					System.err.println(error.getMessage());
				} else {
					System.out.println("success!");
				}
			}
		});
	}
}
