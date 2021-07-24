package ua.netcracker.netcrackerquizb.exception;

public class UserDoesNotExistException extends Exception{

  @Override
  public String toString() {
    return "User does not exist";
  }
}
