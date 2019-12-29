package recipeapplication.database;


import java.util.concurrent.ExecutionException;

public interface FirebaseCrud<T> {
	T create(T data);
	T read(String document) throws Exception;
	T update(T data);
	T delete(String document);
}
