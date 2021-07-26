package ua.netcracker.netcrackerquizb.exception;

public class UserDoesNotConfirmedEmailException extends Exception {

  public UserDoesNotConfirmedEmailException(String message) {
    super(message);
  }

  public UserDoesNotConfirmedEmailException(String message, Throwable cause) {
    super(message, cause);
  }
}
