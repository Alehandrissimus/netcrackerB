package ua.netcracker.netcrackerquizb.exception;

public class UserDoesNotConfirmedEmailException extends Exception {

  @Override
  public String toString() {
    return "User does not confirmed email!\n" + super.toString();
  }
}
