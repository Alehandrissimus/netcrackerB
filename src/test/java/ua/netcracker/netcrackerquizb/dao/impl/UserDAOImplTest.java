package ua.netcracker.netcrackerquizb.dao.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotConfirmedEmailException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDAOImplTest {

  private UserDAOImpl userDAO;
  private static final Logger log = Logger.getLogger(UserDAOImplTest.class);

  @Autowired
  private void setUserDAO(UserDAOImpl userDAO) {
    this.userDAO = userDAO;
    try {
      userDAO.setTestConnection();
    } catch (IOException | SQLException | ClassNotFoundException e) {
      log.error("Error while setting test connection " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByNullId() {
    try {
      userDAO.getUserById(BigInteger.ZERO);
      fail();
    } catch (UserDoesNotExistException | DaoLogicException e) {
      assertTrue(true);
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByAdminId() {
    try {
      assertNotNull(userDAO.getUserById(BigInteger.ONE));
    } catch (UserDoesNotExistException | DaoLogicException e) {
      log.error("Error while testing getUserByAdminId " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByNullEmail() {
    try {
      userDAO.getUserByEmail(null);
      fail();
    } catch (UserDoesNotExistException | DaoLogicException e) {
      assertTrue(true);
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByAdminEmail() {
    try {
      assertNotNull(userDAO.getUserByEmail(
          userDAO.getUserById(BigInteger.ONE).getEmail()
      ));
    } catch (UserDoesNotExistException | DaoLogicException e) {
      log.error("Error while testing getUserByAdminEmail " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void deleteUser() {
    try {
      try {
        userDAO.getUserByEmail("test@gmail.co");
      } catch (UserDoesNotExistException e) {
        assertTrue(true);
      }

      userDAO.createUser(
          new UserImpl.UserBuilder()
              .setFirstName("testFirstName")
              .setLastName("testLastName")
              .setEmail("test@gmail.co")
              .setPassword("testPassword")
              .setEmailCode("testEmailCode")
              .build()
      );

      assertNotNull(userDAO.getUserByEmail("test@gmail.co"));
      userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.co").getId());
      try {
        userDAO.getUserByEmail("test@gmail.co");
      } catch (UserDoesNotExistException e) {
        assertTrue(true);
      }

    } catch (UserDoesNotExistException | DaoLogicException e) {
      log.error("Error while testing deleteUser " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void createUser() {
    try {
      try {
        userDAO.getUserByEmail("test");
      } catch (UserDoesNotExistException e) {
        assertTrue(true);
      }
      userDAO.createUser(
          new UserImpl.UserBuilder()
              .setFirstName("testFirstName")
              .setLastName("testLastName")
              .setEmail("test@gmail.co")
              .setPassword("testPassword")
              .setEmailCode("testEmailCode")
              .build()
      );

      assertNotNull(userDAO.getUserByEmail("test@gmail.co"));

      userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.co").getId());
      try {
        userDAO.getUserByEmail("test@gmail.co");
      } catch (UserDoesNotExistException e) {
        assertTrue(true);
      }

    } catch (UserDoesNotExistException | DaoLogicException e) {
      log.error("Error while testing createUser " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersName() {
    try {
      String testFirstName = "testFirst";
      String testLastName = "testLast";

      String expected = testLastName + " " + testFirstName;
      String oldFirstName, oldLastName;
      oldLastName = userDAO.getUserById(BigInteger.ONE).getLastName();

      oldFirstName = userDAO.getUserById(BigInteger.ONE).getFirstName();

      userDAO.updateUsersFullName(BigInteger.ONE, testFirstName, testLastName);

      assertEquals(expected, userDAO.getUserById(BigInteger.ONE).getFullName());

      userDAO.updateUsersFullName(BigInteger.ONE, oldFirstName, oldLastName);

      assertEquals(oldLastName + " " + oldFirstName,
          userDAO.getUserById(BigInteger.ONE).getFullName().trim());

    } catch (UserDoesNotExistException | DaoLogicException e) {
      log.error("Error while testing updateExistUsersName " + e.getMessage());
      fail();
    }
  }


  @Test()
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateNotExistUsersName() {
    try {
      String testFirstName = "testFirst";
      String testLastName = "testLast";

      userDAO.updateUsersFullName(BigInteger.ZERO, testFirstName, testLastName);

      userDAO.getUserById(BigInteger.ZERO).getFullName();
    } catch (DaoLogicException e) {
      log.error("Error while testing updateNotExistUsersName " + e.getMessage());
      fail();
    } catch (UserDoesNotExistException e) {
      assertTrue(true);
    }

  }


  @Test
  @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
  void updateUsersPassword() {
    try {
      String testPassword = "testPassword";

      userDAO.updateUsersPassword(BigInteger.ONE, testPassword);
      assertTrue(userDAO.comparisonOfPasswords(BigInteger.ONE, testPassword));

    } catch (DaoLogicException e) {
      log.error("Error while testing updateUsersPassword " + e.getMessage());
      fail();
    }
  }

  @Test()
  @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
  void updateNotExistUsersPassword() {
    String testPassword = "testPassword";

    try {
      userDAO.updateUsersPassword(BigInteger.ZERO, testPassword);

      try {
        userDAO.getUserById(BigInteger.ZERO).getPassword();
        fail();
      } catch (UserDoesNotExistException e) {
        assertTrue(true);
      }
    } catch (DaoLogicException e) {
      log.error("Error while testing updateNotExistUsersPassword " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getAuthorizeActiveUser() {
//    try {
//      userDAO.activateUser(BigInteger.ONE);
//      assertNotNull(userDAO.getAuthorizeUser("abobuba@gmail.com", "passhash"));
//    } catch (DaoLogicException | UserDoesNotExistException | UserDoesNotConfirmedEmailException e) {
//      log.error("Error while testing getAuthorizeActiveUser " + e.getMessage());
//      fail();
//    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getAuthorizeNotActiveUser() {
//    try {
//      userDAO.createUser(
//          new UserImpl.UserBuilder().setFirstName("test").setLastName("test").setEmail("test")
//              .setPassword("test").build());
//      try {
//        assertNull(userDAO.getAuthorizeUser("test", "test"));
//      } catch (UserDoesNotConfirmedEmailException e) {
//        assertTrue(true);
//      }
//
//      userDAO.deleteUser(userDAO.getUserByEmail("test").getId());
//    } catch (DaoLogicException | UserDoesNotExistException e) {
//      log.error("Error while testing getAuthorizeNotActiveUser " + e.getMessage());
//      fail();
//    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void comparisonOfPasswordsTest() {
    try {
      userDAO.updateUsersPassword(BigInteger.ONE, "testPassword");
      assertTrue(userDAO.comparisonOfPasswords(BigInteger.ONE, "testPassword"));
    } catch (DaoLogicException e) {
      log.error("Error while testing comparisonOfPasswordsTest " + e.getMessage());
      fail();
    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersDescription() {
//    String testDescription = "testDescription";
//    String oldDescription = userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim();
//
//    userDAO.updateUsersDescription(BigInteger.valueOf(2), testDescription);
//
//    assertEquals(testDescription,
//        userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim());
//
//    userDAO.updateUsersDescription(BigInteger.valueOf(2), oldDescription);
//    assertEquals(oldDescription,
//        userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateNotExistUsersDescription() {
//    String testDescription = "testDescription";
//
//    userDAO.updateUsersDescription(BigInteger.ZERO, testDescription);
//    try {
//      userDAO.getUserById(BigInteger.ZERO).getDescription();
//
//      fail();
//    } catch (NullPointerException e) {
//      assertTrue(true);
//    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersEmailCode() {
//    String testEmailCode = "testEmailCode";
//    String oldEmailCode = userDAO.getUserById(BigInteger.ONE).getEmailCode();
//
//    userDAO.updateUsersEmailCode(BigInteger.ONE, testEmailCode);
//
//    assertEquals(testEmailCode, userDAO.getUserById(BigInteger.ONE).getEmailCode());
//
//    userDAO.updateUsersEmailCode(BigInteger.ONE, oldEmailCode);
//
//    assertEquals(oldEmailCode,
//        userDAO.getUserById(BigInteger.ONE).getEmailCode());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByValidEmailCode() {
//    String oldEmailCode = userDAO.getUserById(BigInteger.ONE).getEmailCode();
//    String testEmailCode = "test";
//    userDAO.updateUsersEmailCode(BigInteger.ONE, testEmailCode);
//    assertNotNull(userDAO.getUserByEmailCode(userDAO.getUserById(BigInteger.ONE).getEmailCode()));
//
//    userDAO.updateUsersEmailCode(BigInteger.ONE, oldEmailCode);
//    assertEquals(oldEmailCode, userDAO.getUserById(BigInteger.ONE).getEmailCode());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByInvalidEmailCode() {
//    assertNull(userDAO.getUserByEmailCode("notActiveCode"));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void activateUnverifiedUser() {
//    userDAO.createUser("test@gmail.co", "testLastName", "testFirstName", "testPassword",
//        "testEmailCode");
//
//    userDAO.activateUser(userDAO.getUserByEmail("test@gmail.co").getId());
//
//    assertTrue(userDAO.getUserByEmail("test@gmail.co").isActive());
//
//    userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.co").getId());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void activateVerifiedUser() {
//    userDAO.activateUser(BigInteger.ONE);
//
//    assertTrue(userDAO.getUserById(BigInteger.ONE).isActive());
  }
}
