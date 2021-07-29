package ua.netcracker.netcrackerquizb.service.Impl;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.DAO_LOGIC_EXCEPTION;

import java.math.BigInteger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.UserDAO;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotConfirmedEmailException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import ua.netcracker.netcrackerquizb.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger log = Logger.getLogger(UserServiceImpl.class);

  @Autowired
  private UserDAO userDAO;


  @Override
  public BigInteger buildNewUser(String email, String password, String firstName, String lastName)
      throws UserException, DAOLogicException {
    try {

      if (userDAO.getUserByEmail(email) != null) {
        throw new UserException("User already exist");
      }

      User user = new UserImpl.UserBuilder()
          .setFirstName(firstName)
          .setLastName(lastName)
          .setEmail(email)
          .setPassword(password)
          .build();

      return userDAO.createUser(user);


    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.info(DAO_LOGIC_EXCEPTION + " in buildNewUser()");
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
    }

  }

  @Override
  public User authorize(User user) throws DAOLogicException, UserException {

    try {
      if (user != null) {
        return userDAO.getAuthorizeUser(user.getEmail(), user.getPassword());
      }
      throw new UserException("Invalid user " + user);
    } catch (DAOLogicException | UserDoesNotExistException | UserDoesNotConfirmedEmailException e) {
      log.info(DAO_LOGIC_EXCEPTION + " in authorize()");
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
    }
  }

  @Override
  public User recoverPassword(User user) {
    return null;
  }

  @Override
  public void validateNewUser(User user) {

  }
}
