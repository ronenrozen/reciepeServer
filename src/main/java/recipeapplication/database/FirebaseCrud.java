package recipeapplication.database;


public interface FirebaseCrud<T> {
	
	void create(String collection, String document, T data);
	void read(String collection, String document);
	void update(String collection, String doucmnet, T data);
	void delete(String collection, String document);

}
