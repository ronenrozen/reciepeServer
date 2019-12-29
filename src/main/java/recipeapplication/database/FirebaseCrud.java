package recipeapplication.database;


public interface FirebaseCrud<T> {
	void create(T data);
	T read(String document);
	void update(T data);
	void delete(String document);
}
