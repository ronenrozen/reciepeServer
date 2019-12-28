package recipeapplication.database;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.firestore.Firestore;

import recipeapplication.components.User;

@Component
public class UserFirebaseCrud implements FirebaseCrud<User> {
	
	private Firestore firestore;
	private String collection;
	
	@Autowired
	UserFirebaseCrud(Firestore firestore, 
			@Value("${recipeapp.firestore.collection.users:users}") String collection) {
		this.firestore = firestore;
		this.collection = collection;
	}


	@Override
	public void create(User user) {
		this.firestore.collection(collection).document(user.getId()).set(user);
		
	}

	@Override
	public void read(String document) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String document) {
		// TODO Auto-generated method stub
		
	}

}
