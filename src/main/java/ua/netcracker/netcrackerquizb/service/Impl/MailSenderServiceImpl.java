package ua.netcracker.netcrackerquizb.service.Impl;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import ua.netcracker.netcrackerquizb.dao.impl.UserDAOImpl;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.service.MailSenderService;

public class MailSenderServiceImpl implements MailSenderService {

  @Autowired
  private UserDAOImpl userDAO;

  @Override
  public void sendEmail(User user) {
    try {

      User userInDatabsse = userDAO.getUserById(user.getId());

      if (userInDatabsse.isActive()) {
//        throw new Exception();
      }

      String code = generateCode(user.getId());

      userDAO.updateUsersEmailCode(user.getId(), code);

      String to = userInDatabsse.getEmail();
      String from = "max.bataiev@gmail.com";


    } catch (DAOLogicException e) {
      e.printStackTrace();
    } catch (UserDoesNotExistException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String generateCode(BigInteger id) {
    return "GENERATED STRING";
  }

  @Override
  public void confirmEmail(User user) {

  }
}
