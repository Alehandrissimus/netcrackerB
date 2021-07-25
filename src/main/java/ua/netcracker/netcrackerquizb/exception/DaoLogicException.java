package ua.netcracker.netcrackerquizb.exception;

public class DaoLogicException extends Exception {

  @Override
  public String toString() {
    return "Dao logic exception!\n" + super.toString();
  }
}
