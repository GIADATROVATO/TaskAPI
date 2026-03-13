package payload;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {
	private LocalDateTime timestamp;
	private int status; 
	private String message; 
	private String path; 
	private Map<String,String> errors;
		
	public ApiError(LocalDateTime timestamp,int status, String message,String path,Map<String,String> errors ) {
		this.message=message;
		this.status=status;
		this.timestamp=timestamp;
		this.path=path;
		this.errors=errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}
	
	
}	
