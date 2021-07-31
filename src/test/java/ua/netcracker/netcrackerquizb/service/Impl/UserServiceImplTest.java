package ua.netcracker.netcrackerquizb.service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.DAO_LOGIC_EXCEPTION;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.ERROR_WHILE_SETTING_TEST_CONNECTION;

import java.math.BigInteger;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.dao.UserDAO;
import ua.netcracker.netcrackerquizb.dao.impl.UserDAOImpl;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MailException;
import ua.netcracker.netcrackerquizb.exception.MessagesForException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.service.QuestionService;
import ua.netcracker.netcrackerquizb.service.QuizService;
import ua.netcracker.netcrackerquizb.service.UserService;

@SpringBootTest
class UserServiceImplTest {

  private static final Logger log = Logger.getLogger(UserServiceImplTest.class);
  private UserService userService;
  private UserDAO userDAO;

  @Autowired
  private void setTestConnection(UserService userService, UserDAO userDAO) {
    this.userDAO = userDAO;
    this.userService = userService;
    try {
      userService.setTestConnection();
      userDAO.setTestConnection();
    } catch (DAOConfigException e) {
      log.error(ERROR_WHILE_SETTING_TEST_CONNECTION + e.getMessage());
    }
  }

  @Test
  void buildNewInvalidEmailUserTest() {
    try {
      userService.buildNewUser("asd", "qwertyui", "asd", "asd");
      log.error(MessagesForException.INVALID_EMAIL);
      fail();
    } catch (UserException | DAOLogicException e) {
      assertTrue(true);
    }
  }

  @Test
  void buildNewInvalidPasswordUserTest() {
    try {
      userService.buildNewUser("max.bataiev@gmail.com", "qwe", "asd", "asd");
      log.error(MessagesForException.INVALID_PASSWORD);
      fail();
    } catch (UserException | DAOLogicException e) {
      assertTrue(true);
    }
  }

  @Test
  void buildNewInvalidFirstNameUserTest() {
    try {
      userService.buildNewUser("max.bataiev@gmail.com", "qwertyui", "a", "asd");
      log.error(MessagesForException.INVALID_USERS_FIRST_NAME);
      fail();
    } catch (UserException | DAOLogicException e) {
      assertTrue(true);
    }
  }

  @Test
  void buildNewInvalidLastNameUserTest() {
    try {
      userService.buildNewUser("max.bataiev@gmail.com", "qwertyui", "asd", "a");
      log.error(MessagesForException.INVALID_USERS_LAST_NAME);
      fail();
    } catch (UserException | DAOLogicException e) {
      assertTrue(true);
    }
  }

  @Test
  void testAuthorizeNull() {
    try {
      userService.authorize(null);
    } catch (UserException e) {
      assertTrue(true);
    } catch (DAOLogicException e) {
      log.error(DAO_LOGIC_EXCEPTION);
      fail();
    }
  }

  @Test
  void testAuthorizeActiveValidUser() {
    try {
      String password = "testPassword";
      userDAO.activateUser(BigInteger.TWO);
      userDAO.updateUsersPassword(BigInteger.TWO, password);
      User user = userDAO.getUserById(BigInteger.TWO);
      user.setPassword(password);
      assertNotNull(userService.authorize(user));
    } catch (DAOLogicException | UserException | UserDoesNotExistException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      fail();
    }
  }

  @Test
  void testAuthorizeNotActiveValidUser() {
    try {
      userDAO.updateUsersPassword(BigInteger.TWO, "testPassword");
      userDAO.disactivateUser(BigInteger.TWO);
      userService.authorize(userDAO.getUserById(BigInteger.TWO));
      log.error(MessagesForException.USERS_DOESNT_EXIT);
      fail();
    } catch (DAOLogicException | UserException | UserDoesNotExistException e) {
      assertTrue(true);
    }
  }

  @Test
  void testRecoverPassword() {
    try {
      userDAO.updateUsersPassword(BigInteger.TWO, "testPassword");
      userDAO.activateUser(BigInteger.TWO);

      userService.recoverPassword(userDAO.getUserById(BigInteger.TWO));

      assertFalse(userDAO.comparisonOfPasswords(BigInteger.TWO, "testPassword"));
    } catch (DAOLogicException e) {
      e.printStackTrace();
    } catch (UserException e) {
      e.printStackTrace();
    } catch (MailException e) {
      e.printStackTrace();
    } catch (UserDoesNotExistException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testValidateNewUser() {
    try {
      userService
          .validateNewUser("max.bataiev@gmail.com", "qwertyui", "testFirstName", "testLastName");
      assertTrue(true);
    } catch (UserException e) {
      fail();
    }
  }

  @Test
  void testNotValidEmailValidateNewUser() {
    try {
      userService
          .validateNewUser("max.bataievgmail.com", "qwertyui", "testFirstName", "testLastName");
      fail();
    } catch (UserException e) {
      assertTrue(true);
    }
  }

  @Test
  void testNotValidPasswordValidateNewUser() {
    try {
      userService.validateNewUser("max.bataiev@gmail.com", "qwer", "testFirstName", "testLastName");
      fail();
    } catch (UserException e) {
      assertTrue(true);
    }
  }

  @Test
  void testNotValidFirstNameValidateNewUser() {
    try {
      userService.validateNewUser("max.bataiev@gmail.com", "qwertyui", "te", "testLastName");
      fail();
    } catch (UserException e) {
      assertTrue(true);
    }
  }

  @Test
  void testNotValidLastNameValidateNewUser() {
    try {
      userService.validateNewUser("max.bataiev@gmail.com", "qwertyui", "testFirstName", "te");
      fail();
    } catch (UserException e) {
      assertTrue(true);
    }
  }

  @Test
  void testUserGetById() {
    try {
      assertNotNull(userService.getUserById(BigInteger.ONE));
    } catch (DAOLogicException | UserDoesNotExistException e) {
      fail();
    }
  }

  @Test
  void testUserGetByInvalidId() {
    try {
      userService.getUserById(null);
      fail();
    } catch (DAOLogicException | UserDoesNotExistException e) {
      assertTrue(true);
    }
  }

  @Test
  void testComparisonOfPasswrods() {
    try {
      userDAO.updateUsersPassword(BigInteger.TWO, "testPassword");
      assertTrue(userService.comparisonOfPasswords(BigInteger.TWO, "testPassword"));
    } catch (DAOLogicException | UserDoesNotExistException e) {
      fail();
    }
  }

  @Test
  void testActiveUser() {
    try {
      userDAO.activateUser(BigInteger.TWO);
      assertTrue(userDAO.getUserById(BigInteger.TWO).isActive());
    } catch (DAOLogicException | UserDoesNotExistException e) {
      fail();
    }
  }

  @Test
  void testDisactiveUser() {
    try {
      userDAO.disactivateUser(BigInteger.TWO);
      assertFalse(userDAO.getUserById(BigInteger.TWO).isActive());
    } catch (DAOLogicException | UserDoesNotExistException e) {
      fail();
    }
  }



}