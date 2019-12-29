package recipeapplication;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;

@Configuration
public class FirebaseInitializer {
	@Bean
	public Firestore initialize(
			@Value("${recipeapp.firestore.service-account-file}") 
			String serviceAccountKey,
			
			@Value("${recipeapp.firestore.database-url}") 
			String databaseUrl,
			
			@Value("${recipeapp.firestore.storage-url}") 
			String storageUrl
			) {
		try {
			
			 FirebaseApp.initializeApp(
					new FirebaseOptions
					.Builder()
					.setCredentials(GoogleCredentials.fromStream(new FileInputStream(serviceAccountKey)))
					.setDatabaseUrl(databaseUrl)
					.setStorageBucket(storageUrl)
					.build());
			 System.out.println("initialized");
			return FirestoreClient.getFirestore();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Bean
	public Bucket getDefaultBucket() {
		return StorageClient.getInstance().bucket();
	}
}
