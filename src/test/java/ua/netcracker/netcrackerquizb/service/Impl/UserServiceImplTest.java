package ua.netcracker.netcrackerquizb.service.Impl;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.service.QuizService;
import ua.netcracker.netcrackerquizb.service.UserService;

@SpringBootTest
class UserServiceImplTest {

  @Autowired
  private UserService userService;

  private static final Logger log = Logger.getLogger(UserServiceImplTest.class);

  @Test
  void buildNewInvalidEmailUserTest() {
    try {
      userService.buildNewUser("asd","qwertyui","asd","asd");
      fail();
    } catch (UserException | DAOLogicException e) {
      assertTrue(true);
    }
  }
}