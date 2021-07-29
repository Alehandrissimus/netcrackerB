package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.dao.UserAccomplishedQuizDAO;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuizDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;

@SpringBootTest
class UserAccomplishedQuizDAOImplTest {

  private UserDAOImpl userDAO;
  private QuizDAOImpl quizDAO;
  private UserAccomplishedQuizDAOImpl userAccomplishedQuizDAO;


  @Autowired
  void setUserDAO(UserDAOImpl userDAO) {
    this.userDAO = userDAO;
    try {
      userDAO.setTestConnection();
    } catch (DAOConfigException e) {

    }
  }

  @Autowired
  void setQuizDAO(QuizDAOImpl quizDAO) {
    this.quizDAO = quizDAO;
    try {
      quizDAO.setTestConnection();
    } catch (DAOConfigException e) {

    }
  }

  @Autowired
  void setAccomplishedQuizDAO(UserAccomplishedQuizDAOImpl userAccomplishedQuizDAO) {
    this.userAccomplishedQuizDAO = userAccomplishedQuizDAO;
    try {
      userAccomplishedQuizDAO.setTestConnection();
    } catch (DAOConfigException e) {

    }
  }

  @Test
  void getAccomplishedQuizesByUserTest() {
//    try {
//      System.out.println( userAccomplishedQuizDAO.getAccomplishedQuizesByUser(BigInteger.ONE));
//
////      System.out.println(userDAO.getUserById(BigInteger.ONE));
//
//    } catch (DAOLogicException e) {
//      e.printStackTrace();
//    } catch (DAOConfigException e) {
//      e.printStackTrace();
//    } catch (QuizDoesNotExistException e) {
//      e.printStackTrace();
//    }

  }
}