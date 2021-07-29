package ua.netcracker.netcrackerquizb.service.Impl;

import java.math.BigInteger;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.impl.UserDAOImpl;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.service.MailSenderService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderServiceImpl implements MailSenderService {

  @Autowired
  private UserDAOImpl userDAO;

  @Override
  public void sendEmail(User user) throws UserException {
    try {
      User userInDatabase = userDAO.getUserByEmail(user.getEmail());

      if (userInDatabase.isActive()) {
        throw new UserException("User already activated");
      }

      String code = generateCode(user.getId());

      userDAO.updateUsersEmailCode(user.getId(), code);

      String to = userInDatabase.getEmail();
      String from = "maksim.bataev.2016@gmail.com";
      String password = "";

      Properties properties = new Properties();

      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.port", "587");

      Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(from, password);
        }
      });

      Message message = prepareMessage(session, from, to);

      try {
        Transport.send(message);
      } catch (MessagingException e) {
        e.printStackTrace();
      }

    } catch (DAOLogicException | UserDoesNotExistException e) {
      e.printStackTrace();
    }
  }

  private Message prepareMessage(Session session, String from, String to) {
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.setRecipient(RecipientType.TO, new InternetAddress(to));

      message.setSubject("Email title");
      message.setText("Email tesxt");
      return message;
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String generateCode(BigInteger id) {
    return "GENERATED STRING";
  }

  @Override
  public void confirmEmail(String code) {

  }

  @Override
  public void generateNewPassword(String email) {

  }
}
