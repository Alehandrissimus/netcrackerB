package ua.netcracker.netcrackerquizb.service.Impl;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.DAO_LOGIC_EXCEPTION;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.EMAIL_ERROR;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.EMPTY_FIRST_NAME;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.EMPTY_LAST_NAME;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.EMPTY_PASSWORD;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.INVALID_USERS_EMAIL;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.INVALID_USERS_FIRST_NAME;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.INVALID_USERS_LAST_NAME;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.USERS_DOESNT_EXIT;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.USER_ALREADY_EXIST;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.USER_NOT_FOUND_EXCEPTION;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.UserDAO;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MailException;
import ua.netcracker.netcrackerquizb.exception.MessagesForException;
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
  @Autowired
  private MailSenderServiceImpl mailSenderService;


  @Override
  public BigInteger buildNewUser(String email, String password, String firstName, String lastName)
      throws UserException, DAOLogicException {
    try {
      validateNewUser(email, password, firstName, lastName);

      if (userDAO.getUserByEmail(email) != null) {
        throw new UserException(USER_ALREADY_EXIST);
      }

      User user = new UserImpl.UserBuilder()
          .setFirstName(firstName)
          .setLastName(lastName)
          .setEmail(email)
          .setPassword(password)
          .build();

      return userDAO.createUser(user);

    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.info(DAO_LOGIC_EXCEPTION);
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
    }

  }

  @Override
  public User authorize(User user) throws DAOLogicException, UserException {
    try {
      if (user != null) {
        return userDAO.getAuthorizeUser(user.getEmail(), user.getPassword());
      }
      throw new UserException(USERS_DOESNT_EXIT);
    } catch (DAOLogicException | UserDoesNotExistException | UserDoesNotConfirmedEmailException e) {
      log.info(DAO_LOGIC_EXCEPTION);
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
    }
  }

  @Override
  public void recoverPassword(User user) throws UserException, DAOLogicException, MailException {
    try {
      if (userDAO.getAuthorizeUser(user.getEmail(), user.getPassword()) != null) {
        mailSenderService.generateNewPassword(user.getEmail());
      }
    } catch (UserDoesNotExistException | UserDoesNotConfirmedEmailException e) {
      log.error(MessagesForException.USER_NOT_FOUND_EXCEPTION + e.getMessage());
      throw new UserException(USER_NOT_FOUND_EXCEPTION, e);
    } catch (DAOLogicException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
    } catch (MailException e) {
      log.error(MessagesForException.EMAIL_ERROR + e.getMessage());
      throw new MailException(EMAIL_ERROR, e);
    }
  }

  @Override
  public void validateNewUser(String email, String password, String firstName, String lastName)
      throws UserException {
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    if (!matcher.find()) {
      log.error(MessagesForException.INVALID_USERS_EMAIL);
      throw new UserException(INVALID_USERS_EMAIL);
    }
    if (password == null) {
      log.error(EMPTY_PASSWORD);
      throw new UserException(INVALID_USERS_EMAIL);
    }
    if (firstName == null) {
      log.error(EMPTY_FIRST_NAME);
      throw new UserException(INVALID_USERS_FIRST_NAME);
    }
    if (firstName.length() < 3) {
      log.error(EMPTY_FIRST_NAME);
      throw new UserException(INVALID_USERS_FIRST_NAME);
    }
    if (lastName == null) {
      log.error(EMPTY_LAST_NAME);
      throw new UserException(INVALID_USERS_LAST_NAME);
    }
    if (lastName.length() < 3) {
      log.error(EMPTY_LAST_NAME);
      throw new UserException(INVALID_USERS_LAST_NAME);
    }
  }
}

