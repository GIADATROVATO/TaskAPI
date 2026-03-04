package exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice						//questa classe deve intercettare le eccezioni di tutti i controller 
public class GlobalExceptionHandler {
	@ExceptionHandler(TaskNotFoundException.class)
	//questo metodo viene chiamato quando viene lanciata TaskNotFoundException
	public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException ex) {	
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)	// 404
                .body(ex.getMessage());			// recupera il messaggio passato nel service
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong");
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
