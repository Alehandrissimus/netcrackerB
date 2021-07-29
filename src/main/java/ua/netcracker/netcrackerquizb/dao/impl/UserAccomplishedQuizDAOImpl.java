package ua.netcracker.netcrackerquizb.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.UserAccomplishedQuizDAO;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuizDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;
import ua.netcracker.netcrackerquizb.util.DAOUtil;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.DAO_LOGIC_EXCEPTION;

@Repository
public class UserAccomplishedQuizDAOImpl implements UserAccomplishedQuizDAO {

  private Connection connection;
  private final Properties properties = new Properties();
  private static final Logger log = Logger.getLogger(UserAccomplishedQuizDAOImpl.class);

  private final String URL;
  private final String USERNAME;
  private final String PASSWORD;

  @Autowired
  UserAccomplishedQuizDAOImpl(
      @Value(URL_PROPERTY) String URL,
      @Value(USERNAME_PROPERTY) String USERNAME,
      @Value(PASSWORD_PROPERTY) String PASSWORD
  ) throws DAOConfigException {
    this.URL = URL;
    this.USERNAME = USERNAME;
    this.PASSWORD = PASSWORD;

    connection = DAOUtil.getDataSource(URL, USERNAME, PASSWORD, properties);
  }

  public void setTestConnection()
      throws DAOConfigException {
    try {
      connection = DAOUtil.getDataSource(URL, USERNAME + "_TEST", PASSWORD, properties);
    } catch (DAOConfigException e) {
      log.error("Error while setting test connection " + e.getMessage());
      throw new DAOConfigException("Error while setting test connection ", e);
    }
  }

  String CORRECT_ANSWERS = "correct_answers";
  String IS_FAVOURITE = "is_favourite";
  String QUIZ = "quiz";
  @Override
  public Set<QuizAccomplishedImpl> getAccomplishedQuizesByUser(BigInteger idUser)
      throws DAOLogicException, QuizDoesNotExistException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_ACCOMPLISHED_QUIZES_BY_USER_ID))) {
      statement.setLong(1, idUser.longValue());
      ResultSet resultSet = statement.executeQuery();
      if (!resultSet.isBeforeFirst()) {
        throw new QuizDoesNotExistException("Error while getAccomplishedQuizesByUser with id=" + idUser);
      }
      Set<QuizAccomplishedImpl> quizes = new HashSet<>();
      while (resultSet.next()) {
        QuizAccomplishedImpl quiz = new QuizAccomplishedImpl();
        quiz.setCorrectAnswers(resultSet.getInt(CORRECT_ANSWERS));
        quiz.setBoolFavourite(resultSet.getInt(IS_FAVOURITE));
        quiz.setQuiz(BigInteger.valueOf(resultSet.getLong(QUIZ)));
        quizes.add(quiz);
      }
      return quizes;
    } catch (SQLException e) {
      throw new DAOLogicException("DaoLogic exception", e);
    }
  }

  @Override
  public Set<QuizAccomplishedImpl> getFavoriteQuizesByUser(BigInteger id) throws DAOLogicException {
    try {
      Set<QuizAccomplishedImpl> quizes = getAccomplishedQuizesByUser(id);
      Set<QuizAccomplishedImpl> result = Collections.emptySet();

      for (QuizAccomplishedImpl quiz :
          quizes) {
        if (quiz.getFavourite()) {
          result.add(quiz);
        }
      }
      return quizes;
    } catch (Exception e) {
      throw new DAOLogicException("Dao logic exception", e);
    }

  }

  @Override
  public void addAccomplishedQuiz(BigInteger idUser, QuizAccomplishedImpl quiz) throws DAOLogicException {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(
              properties.getProperty(ADD_ACCOMPLISHED_QUIZ));
      preparedStatement.setLong(1, idUser.longValue());
      preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
      preparedStatement.setLong(3, quiz.getQuiz().longValue());
      preparedStatement.setInt(4, quiz.getCorrectAnswers());
      preparedStatement.setInt(5, quiz.getIntFavourite());
      preparedStatement.executeUpdate();
    } catch (SQLException throwables) {
      log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION, throwables);
    }
  }

  @Override
  public void editAccomplishedQuiz(BigInteger idUser, QuizAccomplishedImpl newQuiz) throws DAOLogicException {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(UPDATE_ACCOMPLISHED_QUIZ));
      preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
      preparedStatement.setInt(2, newQuiz.getCorrectAnswers());
      preparedStatement.setInt(3, newQuiz.getIntFavourite());
      preparedStatement.setLong(4, idUser.longValue());
      preparedStatement.setLong(5, newQuiz.getQuiz().longValue());
      preparedStatement.executeUpdate();
    } catch (SQLException throwables) {
      log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION, throwables);
    }
  }

  @Override
  public void addFavoriteQuiz(BigInteger id, QuizAccomplishedImpl quiz) {

  }

  @Override
  public void removeFavoriteQuiz(BigInteger id, QuizAccomplishedImpl quiz) {

  }
}
