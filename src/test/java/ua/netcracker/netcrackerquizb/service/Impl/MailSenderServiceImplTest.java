package ua.netcracker.netcrackerquizb.service.Impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

@SpringBootTest
class MailSenderServiceImplTest {

  @Autowired
  private MailSenderServiceImpl mailSenderService;

  @Test
  void testEmail() {
    try {
      mailSenderService.sendEmail(
          new UserImpl.UserBuilder()
              .setId(BigInteger.ONE)
              .setFirstName("test")
              .setLastName("test")
              .setPassword("test")
              .setEmail("max.bataiev@gmail.com")
              .build()
      );
    } catch (UserException e) {
      e.printStackTrace();
    }
  }


}