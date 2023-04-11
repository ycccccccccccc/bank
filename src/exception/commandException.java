package exception;

public class commandException extends Exception  {
	String message;
	
	public commandException(String message) {
		this.message = message;
	}

	public String getMessage(){
		return message;
	}


}
