package ua.netcracker.netcrackerquizb.service;

import java.math.BigInteger;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.User;

public interface MailSenderService {

  void sendEmail(User user) throws UserException;

  String generateCode(BigInteger id);

  void confirmEmail(String code);

  void generateNewPassword(String email);
}
