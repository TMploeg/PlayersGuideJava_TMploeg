package exceptions;

public class CollectionEmptyException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public CollectionEmptyException() {
    super();
  }

  public CollectionEmptyException(String message) {
    super(message);
  }

  public CollectionEmptyException(Throwable cause) {
    super(cause);
  }

  public CollectionEmptyException(String message, Throwable cause) {
    super(message, cause);
  }
}
