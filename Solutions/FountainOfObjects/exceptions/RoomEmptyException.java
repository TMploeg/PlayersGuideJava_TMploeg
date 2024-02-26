package exceptions;

public class RoomEmptyException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public RoomEmptyException() {
    super();
  }

  public RoomEmptyException(String message) {
    super(message);
  }

  public RoomEmptyException(Throwable cause) {
    super(cause);
  }

  public RoomEmptyException(String message, Throwable cause) {
    super(message, cause);
  }
}
