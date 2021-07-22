package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDAOImplTest {

  @Autowired
  private UserDAOImpl userDAO;

  @Test
  public void getUserByIdTest() {
    System.out.println(userDAO.getUserById(BigInteger.ONE));
  }

  @Test
  public void getUserByEmailTest() {
    System.out.println(userDAO.getUserByEmail(""));
  }



}
