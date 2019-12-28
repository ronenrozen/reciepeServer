package database;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseInitializer {
	@Bean
	public FirebaseApp initialize() {
		try {
			System.out.println("initialized");
			return FirebaseApp.initializeApp(new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/key.json")))
					.setDatabaseUrl("https://reciappeagile.firebaseio.com").build());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
