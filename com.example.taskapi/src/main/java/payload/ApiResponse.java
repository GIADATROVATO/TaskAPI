package payload;

public class ApiResponse<T> {
	private String message;
	private boolean reponse;
	private T data; 
	public ApiResponse( String message, boolean response, T data) {
		this.message=message;
		this.data=data;
		this.reponse=response;
	}
	public static <T> ApiResponse<T> success(T data, String message){
		return new ApiResponse<>(message, true, data);
	}
	public static <T>ApiResponse<T> error(String message){
		return new ApiResponse<>(message,false,null);
	}
	public String getMessage() { return message; }
	public boolean isReponse() { return reponse; }
	public T getData() { return data; }

}
