package ua.netcracker.netcrackerquizb.service;

import java.math.BigInteger;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MailException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.User;

public interface MailSenderService {

  String SYMBOLS = "1234567890qwertyuiopasdfghjklzxcvbnm";
  String EMAIL_SUBJECT = "Email confirmation for quiz";
  String TEXT_HTML1 = "<p><a href=\"http://localhot:8080/";
  String TEXT_HTML2 = "\">Confirm email</a></p>";
  String TEXT_TYPE = "text/html";
  String PATH_PROPERTY = "src/main/resources/email.properties";
  String HOST_EMAIL_NAME = "HOST_EMAIL_NAME";
  String HOST_EMAIL_PASSWORD = "HOST_EMAIL_PASSWORD";

  void sendEmail(User user) throws UserException, MailException;

  String generateCode() throws DAOLogicException;

  User confirmEmail(String code) throws UserException, DAOLogicException;

  void generateNewPassword(String email) throws MailException;
}
