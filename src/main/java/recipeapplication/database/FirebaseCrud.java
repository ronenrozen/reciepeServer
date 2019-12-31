package recipeapplication.database;

public interface FirebaseCrud<T> {
	T create(T data);
	T read(String document) throws Exception;
	T update(T data);
	T delete(String document);
}
