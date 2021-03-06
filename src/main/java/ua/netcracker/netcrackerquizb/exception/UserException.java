package ua.netcracker.netcrackerquizb.exception;

public class UserException extends Exception implements MessagesForException {

  public UserException(String errorMessage) {
    super(errorMessage);
  }

  public UserException(String errorMessage, Throwable error) {
    super(errorMessage, error);
  }
}
