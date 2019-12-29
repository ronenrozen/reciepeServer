package recipeapplication.database;


import java.util.concurrent.ExecutionException;

public interface FirebaseCrud<T> {
	T create(T data) throws Exception;
	T read(String document) throws ExecutionException, InterruptedException, Exception;
	T update(T data) throws Exception;
	T delete(String document) throws ExecutionException, InterruptedException, Exception;
}
