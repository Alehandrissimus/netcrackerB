package ua.netcracker.netcrackerquizb.service;

import java.math.BigInteger;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MailException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.User;

public interface UserService {

  String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
          "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  BigInteger buildNewUser(String email, String password, String name, String surname)
      throws UserException, DAOLogicException;

  User authorize(User user) throws DAOLogicException, UserException;

  void recoverPassword(User user) throws UserException, DAOLogicException, MailException;

  void validateNewUser(String email, String password, String firstName, String lastName)
      throws UserException;
}
