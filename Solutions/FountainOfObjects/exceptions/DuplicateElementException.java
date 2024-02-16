package exceptions;

public class DuplicateElementException extends RuntimeException {
  public DuplicateElementException() {
    super();
  }

  public DuplicateElementException(String message) {
    super(message);
  }

  public DuplicateElementException(Throwable cause) {
    super(cause);
  }

  public DuplicateElementException(String message, Throwable cause) {
    super(message, cause);
  }
}
