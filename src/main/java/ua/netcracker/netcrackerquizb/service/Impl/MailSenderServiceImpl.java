package ua.netcracker.netcrackerquizb.service.Impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.UserDAO;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MailException;
import ua.netcracker.netcrackerquizb.exception.MessagesForException;
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

  private static final Logger log = Logger.getLogger(MailSenderServiceImpl.class);

  @Autowired
  private UserDAO userDAO;


  public void setTestConnection() throws DAOConfigException {
    userDAO.setTestConnection();
  }

  @Override
  public boolean sendEmail(User user) throws UserException, MailException {
    try {
      User userInDatabase = userDAO.getUserByEmail(user.getEmail());

      if (userInDatabase.isActive()) {
        log.debug(MessagesForException.ERROR_WHILE_ACTIVATING);
        throw new UserException(MessagesForException.ERROR_WHILE_ACTIVATING);
      }

      String code = generateCode();
      userDAO.updateUsersEmailCode(user.getId(), code);

      Properties properties = new Properties();
      setProperties(properties);

      String to = userInDatabase.getEmail();
      String from = properties.getProperty(HOST_EMAIL_NAME);
      String password = properties.getProperty(HOST_EMAIL_PASSWORD);

      properties = new Properties();

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

      Message message = prepareMessage(session, from, to, code);
      Transport.send(message);
      return true;
    } catch (DAOLogicException | UserDoesNotExistException | MessagingException e) {
      log.error(MessagesForException.EMAIL_ERROR + e.getMessage());
      throw new MailException(MessagesForException.EMAIL_ERROR);
    }
  }

  @Override
  public Message prepareMessage(Session session, String from, String to, String code)
      throws MailException {
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.setRecipient(RecipientType.TO, new InternetAddress(to));

      message.setSubject(EMAIL_SUBJECT);
//      message.setText("Email tesxt");
      message.setContent(TEXT_HTML1 + code + TEXT_HTML2, TEXT_TYPE);
      return message;
    } catch (MessagingException e) {
      log.error(MessagesForException.MESSAGE_ERROR + e.getMessage());
      throw new MailException(MessagesForException.MESSAGE_ERROR, e);
    }
  }

  @Override
  public void setProperties(Properties properties) throws MailException {
    try (FileInputStream fis = new FileInputStream(PATH_PROPERTY)) {
      properties.load(fis);
    } catch (IOException e) {
      log.error(MessagesForException.PROPERTY_ERROR + e.getMessage());
      throw new MailException(MessagesForException.PROPERTY_ERROR, e);
    }
  }

  @Override
  public String generateCode() throws DAOLogicException {
    StringBuilder randString = new StringBuilder();

    while (true) {
      for (int i = 0; i < 10; i++) {
        randString.append(SYMBOLS.charAt((int) (Math.random() * SYMBOLS.length())));
      }
      try {
        userDAO.getUserByEmailCode(String.valueOf(randString));

      } catch (UserDoesNotExistException e) {
        break;
      } catch (DAOLogicException e) {
        log.error(MessagesForException.DAO_LOGIC_EXCEPTION + e.getMessage());
        throw new DAOLogicException(MessagesForException.DAO_LOGIC_EXCEPTION, e);
      }
    }

    return String.valueOf(randString);
  }

  @Override
  public User confirmEmail(String code) throws UserException, DAOLogicException {
    try {
      User userInDatabase = userDAO.getUserByEmailCode(code);

      if (!userDAO.activateUser(userInDatabase.getId())) {
        throw new UserException(MessagesForException.ERROR_WHILE_ACTIVATING);
      }
      userDAO.updateUsersEmailCode(userInDatabase.getId(), null);

      return userDAO.getUserById(userInDatabase.getId());
    } catch (UserDoesNotExistException | UserException e) {
      log.error(MessagesForException.ERROR_WHILE_CONFIRMING_EMAIL + e.getMessage());
      throw new UserException(MessagesForException.ERROR_WHILE_CONFIRMING_EMAIL, e);
    } catch (DAOLogicException e) {
      log.error(MessagesForException.DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(MessagesForException.DAO_LOGIC_EXCEPTION, e);
    }
  }

  @Override
  public void generateNewPassword(String email) throws MailException {
    try {
      userDAO.updateUsersPassword(userDAO.getUserByEmail(email).getId(), generateCode());
    } catch (DAOLogicException | UserDoesNotExistException e) {
      log.error(MessagesForException.ERROR_WHILE_RECOVERING_PASSWORD + e.getMessage());
      throw new MailException(MessagesForException.ERROR_WHILE_RECOVERING_PASSWORD, e);
    }
  }
}
