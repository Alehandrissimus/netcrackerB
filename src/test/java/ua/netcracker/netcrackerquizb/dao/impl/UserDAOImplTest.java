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
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
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
    } catch (DAOConfigException e) {
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
    } catch (UserDoesNotExistException | DAOLogicException e) {
      assertTrue(true);
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByAdminId() {
    try {
      assertNotNull(userDAO.getUserById(BigInteger.ONE));
    } catch (UserDoesNotExistException | DAOLogicException e) {
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
    } catch (UserDoesNotExistException | DAOLogicException e) {
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
    } catch (UserDoesNotExistException | DAOLogicException e) {
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

    } catch (UserDoesNotExistException | DAOLogicException e) {
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

    } catch (UserDoesNotExistException | DAOLogicException e) {
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

    } catch (UserDoesNotExistException | DAOLogicException e) {
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
    } catch (DAOLogicException e) {
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

    } catch (DAOLogicException | UserDoesNotExistException e) {
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

    } catch (UserDoesNotExistException e) {
      assertTrue(true);
    } catch (DAOLogicException e) {
      log.error("Error while testing updateNotExistUsersPassword " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getAuthorizeActiveUser() {
    try {
      userDAO.activateUser(BigInteger.ONE);
      userDAO.updateUsersPassword(BigInteger.ONE, "testPassword");
      userDAO.getAuthorizeUser(userDAO.getUserById(BigInteger.ONE).getEmail(), "testPassword");
    } catch (DAOLogicException | UserDoesNotExistException | UserDoesNotConfirmedEmailException e) {
      log.error("Error while testing getAuthorizeActiveUser " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getAuthorizeNotActiveUser() {
    try {
      userDAO.disactivateUser(BigInteger.ONE);
      userDAO.updateUsersPassword(BigInteger.ONE, "testPassword");
      userDAO.getAuthorizeUser(userDAO.getUserById(BigInteger.ONE).getEmail(), "testPassword");
    } catch (UserDoesNotConfirmedEmailException e) {
      assertTrue(true);
    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing getAuthorizeNotActiveUser " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void comparisonOfPasswordsTest() {
    try {
      userDAO.updateUsersPassword(BigInteger.ONE, "testPassword");
      assertTrue(userDAO.comparisonOfPasswords(BigInteger.ONE, "testPassword"));
    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing comparisonOfPasswordsTest " + e.getMessage());
      fail();
    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersDescription() {
    try {
      String testDescription = "testDescription";
      userDAO.updateUsersDescription(BigInteger.ONE, testDescription);
      assertEquals(testDescription,
          userDAO.getUserById(BigInteger.ONE).getDescription());
    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing updateExistUsersDescription " + e.getMessage());
      fail();
    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateNotExistUsersDescription() {
    String testDescription = "testDescription";

    try {
      userDAO.updateUsersDescription(BigInteger.ZERO, testDescription);

    } catch (UserDoesNotExistException e) {
      assertTrue(true);
    } catch (DAOLogicException e) {
      log.error("Error while testing updateNotExistUsersDescription " + e.getMessage());
      fail();
    }


  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersEmailCode() {
    try {
      String testEmailCode = "test";
      userDAO.updateUsersEmailCode(BigInteger.ONE, testEmailCode);
      assertEquals(BigInteger.ONE, userDAO.getUserByEmailCode(testEmailCode).getId());

      String newEmailCode = "newTest";
      userDAO.updateUsersEmailCode(BigInteger.ONE, newEmailCode);
      assertEquals(BigInteger.ONE, userDAO.getUserByEmailCode(newEmailCode).getId());
    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing updateExistUsersEmailCode " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByValidEmailCode() {

    try {
      String testEmailCode = "test";
      userDAO.updateUsersEmailCode(BigInteger.ONE, testEmailCode);
      assertEquals(BigInteger.ONE, userDAO.getUserByEmailCode(testEmailCode).getId());

      String newEmailCode = "newTest";
      userDAO.updateUsersEmailCode(BigInteger.ONE, newEmailCode);
      assertEquals(BigInteger.ONE, userDAO.getUserByEmailCode(newEmailCode).getId());
    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing getUserByValidEmailCode " + e.getMessage());
      fail();
    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByInvalidEmailCode() {
    try {
      userDAO.getUserByEmailCode("notActiveCode");
      fail();
    } catch (UserDoesNotExistException e) {
      assertTrue(true);
    } catch (DAOLogicException e) {
      log.error("Error while testing getUserByInvalidEmailCode " + e.getMessage());
      fail();
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void activateUnverifiedUser() {
    try {
      userDAO.createUser(
          new UserImpl.UserBuilder()
              .setEmail("test@gmail.c")
              .setLastName("testLastName")
              .setFirstName("testFirstName")
              .setPassword("testPassword")
              .setEmailCode("testEmailCode")
              .build());

      userDAO.activateUser(userDAO.getUserByEmail("test@gmail.c").getId());

      assertTrue(userDAO.getUserByEmail("test@gmail.c").isActive());

      userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.c").getId());

    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing activateUnverifiedUser " + e.getMessage());
      fail();
    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void activateVerifiedUser() {
    try {
      userDAO.activateUser(BigInteger.ONE);
      assertTrue(userDAO.getUserById(BigInteger.ONE).isActive());
    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing activateVerifiedUser " + e.getMessage());
      fail();
    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void disactivateVerifiedUser() {
    try {
      userDAO.disactivateUser(BigInteger.ONE);
      assertFalse(userDAO.getUserById(BigInteger.ONE).isActive());
    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing activateVerifiedUser " + e.getMessage());
      fail();
    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void disactivateUnverifiedUser() {
    try {
      userDAO.createUser(
          new UserImpl.UserBuilder()
              .setEmail("test@gmail.c")
              .setLastName("testLastName")
              .setFirstName("testFirstName")
              .setPassword("testPassword")
              .setEmailCode("testEmailCode")
              .build());

      userDAO.disactivateUser(userDAO.getUserByEmail("test@gmail.c").getId());

      assertFalse(userDAO.getUserByEmail("test@gmail.c").isActive());

      userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.c").getId());

    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error("Error while testing activateUnverifiedUser " + e.getMessage());
      fail();
    }
  }

}
