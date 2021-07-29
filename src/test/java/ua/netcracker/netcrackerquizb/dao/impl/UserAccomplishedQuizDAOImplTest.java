package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.dao.UserAccomplishedQuizDAO;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;
import ua.netcracker.netcrackerquizb.model.impl.QuizImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void addAccomplishedQuiz() {
      try {
        userAccomplishedQuizDAO.addAccomplishedQuiz(BigInteger.ONE, new QuizAccomplishedImpl(
                5, false, BigInteger.ONE));
      } catch (DAOLogicException e) {
        //log.error("Error while testing addAccomplishedQuiz " + e.getMessage());
        fail();
      }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void editAccomplishedQuiz() {
        try {
            userAccomplishedQuizDAO.editAccomplishedQuiz(BigInteger.ONE, new QuizAccomplishedImpl(
                    10, true, BigInteger.ONE));
        } catch (DAOLogicException e) {
          //log.error("Error while testing addAccomplishedQuiz " + e.getMessage());
          fail();
        }
    }

  @Test
  @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
  void getAccomplishedQuizesByUser() {
    try {
      Set<QuizAccomplishedImpl> accomplishedSet =  userAccomplishedQuizDAO.getAccomplishedQuizesByUser(BigInteger.ONE);
      assertNotNull(accomplishedSet);
      for(QuizAccomplishedImpl quizAccomplished: accomplishedSet)
        assertNotNull(quizAccomplished);
    } catch (DAOLogicException | QuizDoesNotExistException e) {
      //log.error("Error while testing addAccomplishedQuiz " + e.getMessage());
      fail();
    }
  }
}