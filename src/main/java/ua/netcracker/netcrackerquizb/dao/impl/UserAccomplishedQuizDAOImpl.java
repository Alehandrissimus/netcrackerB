package ua.netcracker.netcrackerquizb.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ua.netcracker.netcrackerquizb.dao.UserAccomplishedQuizDAO;

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



}
