package recipeapplication.database;


public interface FirebaseCrud<T> {
	
	void initCollection(String collection);
	void create(T data);
	void read(String document);
	void update(T data);
	void delete(String document);

}
