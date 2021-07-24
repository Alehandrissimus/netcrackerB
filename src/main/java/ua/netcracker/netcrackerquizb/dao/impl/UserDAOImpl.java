package ua.netcracker.netcrackerquizb.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.UserDAO;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.UserRoles;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

@Repository
public class UserDAOImpl implements UserDAO {

  private Connection connection;
  private final Properties properties = new Properties();
  private static final Logger log = Logger.getLogger(UserDAOImpl.class);

  private final String URL;
  private final String USERNAME;
  private final String PASSWORD;

  private static final String URL_PROPERTY = "${spring.datasource.url}";
  private static final String USERNAME_PROPERTY = "${spring.datasource.username}";
  private static final String PASSWORD_PROPERTY = "${spring.datasource.password}";
  private static final String PATH_PROPERTY = "src/main/resources/sqlScripts.properties";
  private static final String DRIVER_PATH_PROPERTY = "oracle.jdbc.OracleDriver";
  private static final String SEARCH_USER_BY_ID = "SEARCH_USER_BY_ID";
  private static final String USER_FIRST_NAME = "USER_FIRST_NAME";
  private static final String USER_LAST_NAME = "USER_LAST_NAME";
  private static final String USER_EMAIL = "USER_EMAIL";
  private static final String USER_PASSWORD = "USER_PASSWORD";
  private static final String USER_ROLE = "USER_ROLE";
  private static final String USER_ACTIVE = "USER_ACTIVE";
  private static final String USER_EMAIL_CODE = "USER_EMAIL_CODE";

  private static final String USER_DESCRIPTION = "USER_DESCRIPTION";
  private static final String USER_ID = "USER_ID";
  private static final String DELETE_USER_BY_ID = "DELETE_USER_BY_ID";
  private static final String CREATE_USER = "CREATE_USER";
  private static final String UPDATE_USER_NAME = "UPDATE_USER_NAME";
  private static final String UPDATE_USER_PASSWORD = "UPDATE_USER_PASSWORD";
  private static final String SEARCH_USER_AUTHORIZE = "SEARCH_USER_AUTHORIZE";
  private static final String UPDATE_USER_DESCRIPTION = "UPDATE_USER_DESCRIPTION";
  private static final String UPDATE_USER_EMAIL_CODE = "UPDATE_USER_EMAIL_CODE";
  private static final String SEARCH_USER_BY_EMAIL_CODE = "SEARCH_USER_BY_EMAIL_CODE";
  private static final String UPDATE_USER_ACTIVE = "UPDATE_USER_ACTIVE";
  private static final String SEARCH_USER_BY_EMAIL = "SEARCH_USER_BY_EMAIL";


  @Autowired
  UserDAOImpl(
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
  public User getUserById(BigInteger id) {
    User user = null;
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_ID))) {
      statement.setInt(1, id.intValue());

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        user = new UserImpl();
        user.setId(id);
        user.setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)));
        user.setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)));
        user.setEmail(resultSet.getString(properties.getProperty(USER_EMAIL)));
        user.setPassword(resultSet.getString(properties.getProperty(USER_PASSWORD)));
        switch (resultSet.getInt(properties.getProperty(USER_ROLE))) {
          case 0:
            user.setRole(UserRoles.ADMIN);
            break;
          case 1:
            user.setRole(UserRoles.USER);
            break;
          default:
            user.setRole(UserRoles.UNVERIFIED);
        }
        user.setActive(resultSet.getInt(properties.getProperty(USER_ACTIVE)) == 1);
        user.setEmailCode(resultSet.getString(properties.getProperty(USER_EMAIL_CODE)));
        user.setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return user;
    }
    return user;
  }

  @Override
  public User getUserByEmail(String email) {
    User user = null;
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_EMAIL))) {
      statement.setString(1, email);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        user = new UserImpl();
        user.setId(BigInteger.valueOf(resultSet.getLong(properties.getProperty(USER_ID))));
        user.setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)));
        user.setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)));
        user.setEmail(email);
        user.setPassword(resultSet.getString(properties.getProperty(USER_PASSWORD)));
        switch (resultSet.getInt(properties.getProperty(USER_ROLE))) {
          case 0:
            user.setRole(UserRoles.ADMIN);
            break;
          case 1:
            user.setRole(UserRoles.USER);
            break;
          default:
            user.setRole(UserRoles.UNVERIFIED);
        }
        user.setActive(resultSet.getInt(properties.getProperty(USER_ACTIVE)) == 1);
        user.setEmailCode(resultSet.getString(properties.getProperty(USER_EMAIL_CODE)));
        user.setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return user;
    }
    return user;
  }

  @Override
  public void deleteUser(BigInteger id) {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(DELETE_USER_BY_ID))) {
      statement.setInt(1, id.intValue());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void createUser(String email, String lastName, String firstName, String password,
      String emailCode) {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(CREATE_USER))) {
      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setString(3, null);
      statement.setString(4, email);
      statement.setString(5, password);
      statement.setInt(6, 2);
      statement.setInt(7, 0);
      statement.setString(8, emailCode);

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void updateUsersName(BigInteger id, String newFirstName, String newLastName) {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_NAME))) {
      statement.setString(1, newFirstName);
      statement.setString(2, newLastName);
      statement.setInt(3, id.intValue());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void updateUsersPassword(BigInteger id, String newPassword) {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_PASSWORD))) {
      statement.setString(1, newPassword);
      statement.setInt(2, id.intValue());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public User getAuthorizeUser(String email, String password) {
    User user = null;
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_AUTHORIZE))) {
      statement.setString(1, email);
      statement.setString(2, password);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        user = new UserImpl();
        user.setId(BigInteger.valueOf(resultSet.getLong(properties.getProperty(USER_ID))));
        user.setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)));
        user.setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)));
        user.setEmail(email);
        user.setPassword(password);
        switch (resultSet.getInt(properties.getProperty(USER_ROLE))) {
          case 0:
            user.setRole(UserRoles.ADMIN);
            break;
          case 1:
            user.setRole(UserRoles.USER);
            break;
          default:
            user.setRole(UserRoles.UNVERIFIED);
        }
        user.setActive(resultSet.getInt(properties.getProperty(USER_ACTIVE)) == 1);
        user.setEmailCode(resultSet.getString(properties.getProperty(USER_EMAIL_CODE)));
        user.setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return user;
    }
    return user;
  }

  @Override
  public void updateUsersDescription(BigInteger id, String newDescription) {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_DESCRIPTION))) {
      statement.setString(1, newDescription);
      statement.setInt(2, id.intValue());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateUsersEmailCode(BigInteger id, String newCode) {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_EMAIL_CODE))) {
      statement.setString(1, newCode);
      statement.setInt(2, id.intValue());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public User getUserByEmailCode(String code) {
    User user = null;
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_EMAIL_CODE))) {
      statement.setString(1, code);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        user = new UserImpl();
        user.setId(BigInteger.valueOf(resultSet.getLong(properties.getProperty(USER_ID))));
        user.setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)));
        user.setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)));
        user.setEmail(resultSet.getString(properties.getProperty(USER_EMAIL)));
        user.setPassword(resultSet.getString(properties.getProperty(USER_PASSWORD)));
        switch (resultSet.getInt(properties.getProperty(USER_ROLE))) {
          case 0:
            user.setRole(UserRoles.ADMIN);
            break;
          case 1:
            user.setRole(UserRoles.USER);
            break;
          default:
            user.setRole(UserRoles.UNVERIFIED);
        }
        user.setActive(resultSet.getInt(properties.getProperty(USER_ACTIVE)) == 1);
        user.setEmailCode(code);
        user.setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return user;
    }
    return user;
  }

  @Override
  public void activateUser(BigInteger id) {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_ACTIVE))) {
      statement.setInt(1, id.intValue());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
