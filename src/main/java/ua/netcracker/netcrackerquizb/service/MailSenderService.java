package ua.netcracker.netcrackerquizb.service;

import java.math.BigInteger;
import ua.netcracker.netcrackerquizb.model.User;

public interface MailSenderService {

  void sendEmail(User user);

  String generateCode(BigInteger id);

  void confirmEmail(User user);
}
