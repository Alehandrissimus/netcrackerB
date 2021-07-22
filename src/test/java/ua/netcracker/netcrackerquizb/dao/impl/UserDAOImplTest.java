package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDAOImplTest {

  @Autowired
  private UserDAOImpl userDAO;
  
  @Test
  void getUserByNullId() {
    assertNull(userDAO.getUserById(BigInteger.ZERO));
  }

  @Test
  void getUserByAdminId() {
    assertNotNull(userDAO.getUserById(BigInteger.ONE));
  }

  @Test
  void getUserByNullEmail() {
    assertNull(userDAO.getUserByEmail(""));
  }

  @Test
  void deleteUser() {

  }

  @Test
  void createUser() {
  }

  @Test
  void updateUsersName() {
  }

  @Test
  void updateUsersPassword() {
  }

  @Test
  void getAuthorizeUser() {
  }

  @Test
  void updateUsersDescription() {
  }

  @Test
  void getUserByEmailCode() {
  }

  @Test
  void activateUser() {
  }
}
