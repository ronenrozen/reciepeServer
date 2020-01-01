package recipeapplication.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class, RecipeNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFound(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "{\"error\" : \"" + ex.getMessage() + "\" }",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {BadRequestException.class, RecipeAlreadyExistsException.class})
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "{\"error\" : \"" + ex.getMessage() + "\" }",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
