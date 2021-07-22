package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDAOImplTest {

  @Autowired
  private UserDAOImpl userDAO;

  @Test
  public void getUserByIdTest() {
    System.out.println(userDAO.getUserById(BigInteger.ONE, Collections.emptySet(), Collections.emptySet()));
  }

  @Test
  public void getUserByEmailTest() {
    System.out.println(userDAO.getUserByEmail("abobuba@gmail.com", Collections.emptySet(), Collections.emptySet()));
  }



}
