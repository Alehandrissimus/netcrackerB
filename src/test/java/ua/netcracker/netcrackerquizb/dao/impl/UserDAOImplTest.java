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
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByNullId() {
    assertNull(userDAO.getUserById(BigInteger.ZERO));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByAdminId() {
    assertNotNull(userDAO.getUserById(BigInteger.ONE));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByNullEmail() {
    assertNull(userDAO.getUserByEmail(""));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByAdminEmail() {
    assertNotNull(userDAO.getUserByEmail(
        userDAO.getUserById(BigInteger.ONE).getEmail()
    ));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void deleteUser() {
    assertNull(userDAO.getUserByEmail("test@gmail.co"));

    userDAO.createUser("test@gmail.co", "testLastName", "testFirstName", "testPassword",
        "testEmailCode");
    assertNotNull(userDAO.getUserByEmail("test@gmail.co"));
    userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.co").getId());
    assertNull(userDAO.getUserByEmail("test@gmail.co"));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void createUser() {
    assertNull(userDAO.getUserByEmail("test@gmail.co"));
    userDAO.createUser("test@gmail.co", "testLastName", "testFirstName", "testPassword",
        "testEmailCode");
    assertNotNull(userDAO.getUserByEmail("test@gmail.co"));
    userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.co").getId());
    assertNull(userDAO.getUserByEmail("test@gmail.co"));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersName() {
    String testFirstName = "testFirst";
    String testLastName = "testLast";

    String expected = testLastName + " " + testFirstName;
    String oldFirstName, oldLastName;
    oldLastName = userDAO.getUserById(BigInteger.ONE).getLastName().trim();
    oldFirstName = userDAO.getUserById(BigInteger.ONE).getFirstName().trim();

    userDAO.updateUsersName(BigInteger.ONE, testFirstName, testLastName);

    assertEquals(expected, userDAO.getUserById(BigInteger.ONE).getFullName().trim());

    userDAO.updateUsersName(BigInteger.ONE, oldFirstName, oldLastName);

    assertEquals(oldLastName + " " + oldFirstName,
        userDAO.getUserById(BigInteger.ONE).getFullName().trim());
  }


  @Test()
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateNotExistUsersName() {
    String testFirstName = "testFirst";
    String testLastName = "testLast";

    userDAO.updateUsersName(BigInteger.ZERO, testFirstName, testLastName);
    try {
      userDAO.getUserById(BigInteger.ZERO).getFullName();

      fail();
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }


  @Test
  @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
  void updateUsersPassword() {
    String testPassword = "testPassword";
    String oldPassword = userDAO.getUserById(BigInteger.ONE).getPassword().trim();

    userDAO.updateUsersPassword(BigInteger.ONE, testPassword);
    assertEquals(testPassword, userDAO.getUserById(BigInteger.ONE).getPassword().trim());
    userDAO.updateUsersPassword(BigInteger.ONE, oldPassword);
    assertEquals(oldPassword,
        userDAO.getUserById(BigInteger.ONE).getPassword().trim());
  }

  @Test()
  @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
  void updateNotExistUsersPassword() {
    String testPassword = "testPassword";

    userDAO.updateUsersPassword(BigInteger.ZERO, testPassword);
    try {
      userDAO.getUserById(BigInteger.ZERO).getPassword();

      fail();
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getAuthorizeActiveUser() {
    assertNotNull(userDAO.getAuthorizeUser(userDAO.getUserById(BigInteger.ONE).getEmail(),
        userDAO.getUserById(BigInteger.ONE).getPassword()));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getAuthorizeNotActiveUser() {
    userDAO.createUser("test@gmail.co", "testLastName", "testFirstName", "testPassword",
        "testEmailCode");

    assertNull(userDAO.getAuthorizeUser("test@gmail.co", "testPassword"));

    userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.co").getId());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersDescription() {
    String testDescription = "testDescription";
    String oldDescription = userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim();

    userDAO.updateUsersDescription(BigInteger.valueOf(2), testDescription);

    assertEquals(testDescription,
        userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim());

    userDAO.updateUsersDescription(BigInteger.valueOf(2), oldDescription);
    assertEquals(oldDescription,
        userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateNotExistUsersDescription() {
    String testDescription = "testDescription";

    userDAO.updateUsersDescription(BigInteger.ZERO, testDescription);
    try {
      userDAO.getUserById(BigInteger.ZERO).getDescription();

      fail();
    } catch (NullPointerException e) {
      assertTrue(true);
    }

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersEmailCode() {
    String testEmailCode = "testEmailCode";
    String oldEmailCode = userDAO.getUserById(BigInteger.ONE).getEmailCode();

    userDAO.updateUsersEmailCode(BigInteger.ONE, testEmailCode);

    assertEquals(testEmailCode, userDAO.getUserById(BigInteger.ONE).getEmailCode());

    userDAO.updateUsersEmailCode(BigInteger.ONE, oldEmailCode);

    assertEquals(oldEmailCode,
        userDAO.getUserById(BigInteger.ONE).getEmailCode());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByValidEmailCode() {
    String oldEmailCode = userDAO.getUserById(BigInteger.ONE).getEmailCode();
    String testEmailCode = "test";
    userDAO.updateUsersEmailCode(BigInteger.ONE, testEmailCode);
    assertNotNull(userDAO.getUserByEmailCode(userDAO.getUserById(BigInteger.ONE).getEmailCode()));

    userDAO.updateUsersEmailCode(BigInteger.ONE, oldEmailCode);
    assertEquals(oldEmailCode, userDAO.getUserById(BigInteger.ONE).getEmailCode());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByInvalidEmailCode() {
    assertNull(userDAO.getUserByEmailCode("notActiveCode"));
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void activateUnverifiedUser() {
    userDAO.createUser("test@gmail.co", "testLastName", "testFirstName", "testPassword",
        "testEmailCode");

    userDAO.activateUser(userDAO.getUserByEmail("test@gmail.co").getId());

    assertTrue(userDAO.getUserByEmail("test@gmail.co").isActive());

    userDAO.deleteUser(userDAO.getUserByEmail("test@gmail.co").getId());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void activateVerifiedUser() {
    userDAO.activateUser(BigInteger.ONE);

    assertTrue(userDAO.getUserById(BigInteger.ONE).isActive());
  }
}
