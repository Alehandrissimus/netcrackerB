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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.UserAccomplishedQuizDAO;
import ua.netcracker.netcrackerquizb.dao.UserDAO;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import ua.netcracker.netcrackerquizb.service.MailSenderService;
import ua.netcracker.netcrackerquizb.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger log = Logger.getLogger(UserServiceImpl.class);

  private final UserDAO userDAO;
  private final UserAccomplishedQuizDAO userAccomplishedQuizDAO;
  private final MailSenderService mailSenderService;

  @Autowired
  public UserServiceImpl(UserDAO userDAO, UserAccomplishedQuizDAO userAccomplishedQuizDAO,
      MailSenderServiceImpl mailSenderService) {
    this.userDAO = userDAO;
    this.userAccomplishedQuizDAO = userAccomplishedQuizDAO;
    this.mailSenderService = mailSenderService;
  }

  @Override
  public void setTestConnection() throws DAOConfigException {
    userDAO.setTestConnection();
    userAccomplishedQuizDAO.setTestConnection();
    mailSenderService.setTestConnection();
  }

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
      if (userDAO.getUserByEmail(user.getEmail()).isActive()) {
        mailSenderService.generateNewPassword(user.getEmail());
      }
    } catch (UserDoesNotExistException e) {
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
    if (password.length() < 8) {
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


  @Override
  public User getUserById(BigInteger id) throws UserDoesNotExistException, DAOLogicException {
    if (id == null) {
      log.error(USER_NOT_FOUND_EXCEPTION);
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    if (id.longValue() < 1) {
      log.error(USER_NOT_FOUND_EXCEPTION);
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    return userDAO.getUserById(id);
  }

  @Override
  public User getUserByEmail(String email) throws UserDoesNotExistException, DAOLogicException {
    if (StringUtils.isEmpty(email)) {
      log.error(USER_NOT_FOUND_EXCEPTION);
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    return userDAO.getUserByEmail(email);
  }

  @Override
  public void deleteUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException {
    if (id == null) {
      log.error(USER_NOT_FOUND_EXCEPTION);
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    userDAO.deleteUser(id);
  }

  @Override
  public void updateUsersFullName(BigInteger id, String newFirstName, String newLastName)
      throws DAOLogicException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    userDAO.updateUsersFullName(id, newFirstName, newLastName);
  }

  @Override
  public void updateUsersPassword(BigInteger id, String newPassword)
      throws DAOLogicException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    userDAO.updateUsersPassword(id, newPassword);
  }

  @Override
  public void updateUsersDescription(BigInteger id, String newDescription)
      throws DAOLogicException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    userDAO.updateUsersDescription(id, newDescription);
  }

  @Override
  public User getUserByEmailCode(String code) throws UserDoesNotExistException, DAOLogicException {
    return userDAO.getUserByEmailCode(code);
  }

  @Override
  public void updateUsersEmailCode(BigInteger id, String newCode)
      throws DAOLogicException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    userDAO.updateUsersEmailCode(id, newCode);
  }

  @Override
  public boolean comparisonOfPasswords(BigInteger id, String checkPassword)
      throws DAOLogicException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    return userDAO.comparisonOfPasswords(id, checkPassword);
  }

  @Override
  public boolean activateUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    return userDAO.activateUser(id);
  }

  @Override
  public boolean disactivateUser(BigInteger id)
      throws DAOLogicException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    return userDAO.disactivateUser(id);
  }


  @Override
  public Set<QuizAccomplishedImpl> getAccomplishedQuizesByUser(BigInteger id)
      throws DAOLogicException, QuizDoesNotExistException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    return userAccomplishedQuizDAO.getAccomplishedQuizesByUser(id);
  }

  @Override
  public Set<QuizAccomplishedImpl> getFavoriteQuizesByUser(BigInteger id)
      throws DAOLogicException, UserDoesNotExistException {
    User userFromDAO = userDAO.getUserById(id);
    if (userFromDAO == null) {
      throw new UserDoesNotExistException(USER_NOT_FOUND_EXCEPTION);
    }
    return userAccomplishedQuizDAO.getFavoriteQuizesByUser(id);
  }

  @Override
  public void editAccomplishedQuiz(BigInteger idUser, QuizAccomplishedImpl newQuiz)
      throws DAOLogicException {
    userAccomplishedQuizDAO.editAccomplishedQuiz(idUser, newQuiz);
  }

  @Override
  public void setIsFavoriteQuiz(BigInteger idUser, QuizAccomplishedImpl quiz)
      throws DAOLogicException {
    userAccomplishedQuizDAO.setIsFavoriteQuiz(idUser, quiz);
  }

  @Override
  public void addAccomplishedQuiz(BigInteger id, QuizAccomplishedImpl quiz)
      throws DAOLogicException {
    userAccomplishedQuizDAO.addAccomplishedQuiz(id, quiz);
  }

  @Override
  public QuizAccomplishedImpl getAccomplishedQuizById(BigInteger idUser, BigInteger idQuiz)
      throws QuizDoesNotExistException, DAOLogicException {
    return userAccomplishedQuizDAO.getAccomplishedQuizById(idUser, idQuiz);
  }

  @Override
  public boolean isAccomplishedQuiz(BigInteger idUser, BigInteger idQuiz) throws DAOLogicException {
    return userAccomplishedQuizDAO.isAccomplishedQuiz(idUser, idQuiz);
  }
}

