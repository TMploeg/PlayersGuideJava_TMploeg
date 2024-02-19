package exceptions;

public class AlreadyOccupiedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
  public AlreadyOccupiedException() {
    super();
  }

  public AlreadyOccupiedException(String message) {
    super(message);
  }

  public AlreadyOccupiedException(Throwable cause) {
    super(cause);
  }

  public AlreadyOccupiedException(String message, Throwable cause) {
    super(message, cause);
  }
}