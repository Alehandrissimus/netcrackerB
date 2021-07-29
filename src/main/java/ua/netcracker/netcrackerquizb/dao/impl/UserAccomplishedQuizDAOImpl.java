package ua.netcracker.netcrackerquizb.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import ua.netcracker.netcrackerquizb.model.impl.QuizImpl;
import ua.netcracker.netcrackerquizb.util.DAOUtil;

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

  @Override
  public Set<QuizAccomplishedImpl> getAccomplishedQuizesByUser(BigInteger id)
      throws DAOConfigException, DAOLogicException, QuizDoesNotExistException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_ACCOMPLISHED_QUIZES_BY_USER_ID))) {

      statement.setLong(1, id.longValue());

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.isBeforeFirst()) {
        throw new DAOLogicException("Error while getAccomplishedQuizesByUser with id=" + id);
      }

      Set<QuizAccomplishedImpl> quizes = new HashSet<>();

      while (resultSet.next()) {
        QuizAccomplishedImpl quiz = new QuizAccomplishedImpl(
            BigInteger.valueOf(resultSet.getLong("correct_answers")),
            resultSet.getInt("is_favorite") == TRUE_SQL,
            new QuizDAOImpl(URL, USERNAME, PASSWORD).getQuizById(
                BigInteger.valueOf(resultSet.getLong("quiz")))
        );
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
  public void addAccomplishedQuiz(BigInteger id, QuizAccomplishedImpl quiz, boolean favorite) {

  }

  @Override
  public void addFavoriteQuiz(BigInteger id, QuizAccomplishedImpl quiz) {

  }

  @Override
  public void removeFavoriteQuiz(BigInteger id, QuizAccomplishedImpl quiz) {

  }
}
