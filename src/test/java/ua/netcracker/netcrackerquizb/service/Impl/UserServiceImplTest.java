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
      userDAO.updateUsersPassword(BigInteger.TWO, "testPassword");
      userDAO.activateUser(BigInteger.TWO);
      assertNotNull(userService.authorize(userDAO.getUserById(BigInteger.TWO)));
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




}