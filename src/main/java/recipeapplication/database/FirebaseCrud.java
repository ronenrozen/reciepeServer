package recipeapplication.database;


public interface FirebaseCrud<T> {
	void create(T data);
	T read(String document);
	T update(T data) throws Exception;
	T delete(String document);
}
