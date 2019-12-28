package recipeapplication.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.cloud.firestore.Firestore;

import recipeapplication.components.User;

@Component
public class UserFirebaseCrud implements FirebaseCrud<User> {
	
	private Firestore firestore;
	
	@Autowired
	UserFirebaseCrud(Firestore firestore) {
		this.firestore = firestore;
	}

	@Override
	public void create(String collection, String document, User data) {
		this.firestore.collection(collection).document(document).set(data);
		
	}

	@Override
	public void read(String collection, String document) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String collection, String doucmnet, User data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String collection, String document) {
		// TODO Auto-generated method stub
		
	}

}
