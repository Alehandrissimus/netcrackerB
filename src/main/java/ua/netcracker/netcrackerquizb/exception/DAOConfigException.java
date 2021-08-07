package ua.netcracker.netcrackerquizb.exception;

public class DAOConfigException  extends Exception implements MessagesForException {

  public DAOConfigException(String message) {
    super(message);
  }

  public DAOConfigException(String message, Throwable cause) {
    super(message, cause);
  }
}
