package ua.netcracker.netcrackerquizb.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ua.netcracker.netcrackerquizb.dao.UserAccomplishedQuizDAO;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;

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
  ) throws SQLException, ClassNotFoundException, IOException {
    this.URL = URL;
    this.USERNAME = USERNAME;
    this.PASSWORD = PASSWORD;

    getDataSource(URL, USERNAME, PASSWORD);
  }

  public void setTestConnection() throws SQLException, ClassNotFoundException, IOException {
    getDataSource(URL, USERNAME + "_TEST", PASSWORD);
  }

  private void getDataSource(String URL, String USERNAME, String PASSWORD)
      throws SQLException, ClassNotFoundException, IOException {

    try (FileInputStream fis = new FileInputStream(PATH_PROPERTY)) {
      Class.forName(DRIVER_PATH_PROPERTY);
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      properties.load(fis);
    } catch (IOException e) {
      log.error("Driver error " + e.getMessage());
      throw new IOException();
    } catch (ClassNotFoundException e) {
      log.error("Property file error " + e.getMessage());
      throw new ClassNotFoundException();
    } catch (SQLException e) {
      log.error("Database connection error " + e.getMessage());
      throw new SQLException();
    }
  }


  @Override
  public Set<QuizAccomplishedImpl> getAccomplishedQuizesByUser(BigInteger id) {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_ACCOMPLISHED_QUIZES_BY_USER_ID))) {

        statement.setLong(1, id.longValue());

      ResultSet resultSet = statement.executeQuery();

      if(!resultSet.isBeforeFirst()){
        return null;
      }

      Set<QuizAccomplishedImpl> quizes = new HashSet<>();

      while (resultSet.next()){

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public Set<QuizAccomplishedImpl> getFavoriteQuizesByUser(BigInteger id) {
    return null;
  }

  @Override
  public void addFavoriteQuiz(QuizAccomplishedImpl quiz) {

  }

  @Override
  public void addAccomplishedQuiz(QuizAccomplishedImpl quiz) {

  }

  @Override
  public void removeFavoriteQuiz(QuizAccomplishedImpl quiz) {

  }
}
