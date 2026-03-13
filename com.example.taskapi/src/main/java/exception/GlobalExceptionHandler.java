package exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import payload.ApiError;

@ControllerAdvice						//questa classe deve intercettare le eccezioni di tutti i controller 
public class GlobalExceptionHandler {
	
	@ExceptionHandler(TaskNotFoundException.class)
	//questo metodo viene chiamato quando viene lanciata TaskNotFoundException
	public ResponseEntity<ApiError> handleTaskNotFound(TaskNotFoundException ex, HttpServletRequest request) {	
		ApiError error= new ApiError( 
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage(), 
				request.getRequestURI(),null);
				
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
    	ApiError error= new ApiError(
    			LocalDateTime.now(),
    			HttpStatus.NOT_FOUND.value(),
    			ex.getMessage(),
    			request.getRequestURI(), null);
    	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex,HttpServletRequest request ) {
    	ApiError error= new ApiError(
    			LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                request.getRequestURI(), null);
                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String,String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        });

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                request.getRequestURI(),
                validationErrors
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /*
     * HttpServletRequest è un oggetto che rappresenta la richiesta HTTP che arriva dal server
     * Contiene tutte le informazioni della richiesta
     * --> header, body, path, parametri,metodo(GET, POST) 
     * 
     * GET /user/5
     * Authorization: Bearer token123
     * tutte queste informazioni sono in HttpServeltRequest
     */
}
