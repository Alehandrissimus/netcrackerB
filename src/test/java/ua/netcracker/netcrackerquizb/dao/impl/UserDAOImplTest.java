package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDAOImplTest {

  @Autowired
  private UserDAOImpl userDAO;

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

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void createUser() {
//    userDAO.createUser("test@gmail.com", "testLastName", "testFirstName", "testPassword", "testEmailCode");

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
      userDAO.getUserById(BigInteger.ZERO).getFullName().trim();

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
      userDAO.getUserById(BigInteger.ZERO).getPassword().trim();

      fail();
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getAuthorizeUser() {
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateExistUsersDescription() {
    String testDescription = "testDescription";
    String oldDescriptino = userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim();

    userDAO.updateUsersDescription(BigInteger.valueOf(2), testDescription);

    assertEquals(testDescription, userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim());

    userDAO.updateUsersDescription(BigInteger.valueOf(2), oldDescriptino);

    assertEquals(oldDescriptino,
        userDAO.getUserById(BigInteger.valueOf(2)).getDescription().trim());
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void updateNotExistUsersDescription() {

  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void getUserByEmailCode() {
  }

  @Test
  @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
  void activateUser() {
  }
}
