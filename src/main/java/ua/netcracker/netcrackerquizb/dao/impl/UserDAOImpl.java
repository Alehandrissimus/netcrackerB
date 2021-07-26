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
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotConfirmedEmailException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.UserActive;
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

  @Autowired
  UserDAOImpl(
      @Value(URL_PROPERTY) String URL,
      @Value(USERNAME_PROPERTY) String USERNAME,
      @Value(PASSWORD_PROPERTY) String PASSWORD
  ) throws SQLException, ClassNotFoundException, IOException, DAOConfigException {
    this.URL = URL;
    this.USERNAME = USERNAME;
    this.PASSWORD = PASSWORD;

    getDataSource(URL, USERNAME, PASSWORD);
//    connection = DAOUtil.getDataSource(URL, USERNAME, PASSWORD, properties, false);
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
  public User getUserById(BigInteger id) throws UserDoesNotExistException, DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_ID))) {

      statement.setLong(1, id.longValue());

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        throw new UserDoesNotExistException();
      }

      return new UserImpl.UserBuilder()
          .setId(id)
          .setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)))
          .setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)))
          .setEmail(resultSet.getString(properties.getProperty(USER_EMAIL)))
//          .setPassword(resultSet.getString(properties.getProperty(USER_PASSWORD)))
          .setRole(
              UserRoles.convertFromIntToRole(resultSet.getInt(properties.getProperty(USER_ROLE))))
          .setActive(
              resultSet.getInt(properties.getProperty(USER_ACTIVE)) == UserActive.ACTIVE.ordinal())
//          .setEmailCode(resultSet.getString(properties.getProperty(USER_EMAIL_CODE)))
          .setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)))
          .build();

    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }
  }

  @Override
  public User getUserByEmail(String email) throws UserDoesNotExistException, DAOLogicException {

    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_EMAIL))) {

      statement.setString(1, email);

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        throw new UserDoesNotExistException();
      }

      return new UserImpl.UserBuilder()
          .setId(BigInteger.valueOf(resultSet.getLong(properties.getProperty(USER_ID))))
          .setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)))
          .setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)))
          .setEmail(email)
//          .setPassword(resultSet.getString(properties.getProperty(USER_PASSWORD)))
          .setRole(
              UserRoles.convertFromIntToRole(resultSet.getInt(properties.getProperty(USER_ROLE))))
          .setActive(
              resultSet.getInt(properties.getProperty(USER_ACTIVE)) == UserActive.ACTIVE.ordinal())
//          .setEmailCode(resultSet.getString(properties.getProperty(USER_EMAIL_CODE)))
          .setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)))
          .build();

    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }
  }

  @Override
  public void deleteUser(BigInteger id) throws DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(DELETE_USER_BY_ID))) {
      statement.setLong(1, id.longValue());
      statement.executeUpdate();
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }
  }

  @Override
  public BigInteger createUser(User user) throws DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(CREATE_USER))) {
      statement.setString(1, user.getFirstName());
      statement.setString(2, user.getLastName());
      statement.setString(3, user.getDescription());
      statement.setString(4, user.getEmail());
      statement.setString(5, user.getPassword());
      statement.setInt(6, UserRoles.UNVERIFIED.ordinal());
      statement.setInt(7, UserActive.NOT_ACTIVE.ordinal());
      statement.setString(8, user.getEmailCode());

      if (statement.executeUpdate() != 1) {
        throw new DAOLogicException();
      }

      return getUserByEmail(user.getEmail()).getId();

    } catch (SQLException | UserDoesNotExistException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }
  }

  @Override
  public void updateUsersFullName(BigInteger id, String newFirstName, String newLastName)
      throws DAOLogicException, UserDoesNotExistException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_NAME))) {
      statement.setString(1, newFirstName);
      statement.setString(2, newLastName);
      statement.setLong(3, id.longValue());

      if (statement.executeUpdate() != 1) {
        throw new UserDoesNotExistException();
      }
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }

  }

  @Override
  public void updateUsersPassword(BigInteger id, String newPassword)
      throws DAOLogicException, UserDoesNotExistException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_PASSWORD))) {
      statement.setString(1, newPassword);
      statement.setLong(2, id.longValue());

      if (statement.executeUpdate() != 1) {
        throw new UserDoesNotExistException();
      }
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }

  }

  @Override
  public User getAuthorizeUser(String email, String password)
      throws UserDoesNotExistException, UserDoesNotConfirmedEmailException, DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_AUTHORIZE))) {
      statement.setString(1, email);
      statement.setString(2, password);

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        throw new UserDoesNotExistException();
      }

      if (resultSet.getInt(properties.getProperty(USER_ACTIVE)) == UserActive.NOT_ACTIVE
          .ordinal()) {
        throw new UserDoesNotConfirmedEmailException();
      }

      return new UserImpl.UserBuilder()
          .setId(BigInteger.valueOf(resultSet.getLong(properties.getProperty(USER_ID))))
          .setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)))
          .setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)))
          .setEmail(email)
//          .setPassword(password)
          .setRole(
              UserRoles.convertFromIntToRole(resultSet.getInt(properties.getProperty(USER_ROLE))))
          .setActive(
              resultSet.getInt(properties.getProperty(USER_ACTIVE)) == UserActive.ACTIVE.ordinal())
//          .setEmailCode(resultSet.getString(properties.getProperty(USER_EMAIL_CODE)))
          .setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)))
          .build();

    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }

  }

  @Override
  public void updateUsersDescription(BigInteger id, String newDescription)
      throws DAOLogicException, UserDoesNotExistException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_DESCRIPTION))) {
      statement.setString(1, newDescription);
      statement.setLong(2, id.longValue());

      if (statement.executeUpdate() != 1) {
        throw new UserDoesNotExistException();
      }
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }
  }

  @Override
  public void updateUsersEmailCode(BigInteger id, String newCode)
      throws DAOLogicException, UserDoesNotExistException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_EMAIL_CODE))) {
      statement.setString(1, newCode);
      statement.setLong(2, id.longValue());

      if (statement.executeUpdate() != 1) {
        throw new UserDoesNotExistException();
      }
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }
  }

  @Override
  public User getUserByEmailCode(String code) throws UserDoesNotExistException, DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_EMAIL_CODE))) {
      statement.setString(1, code);

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        throw new UserDoesNotExistException();
      }

      return new UserImpl.UserBuilder()
          .setId(BigInteger.valueOf(resultSet.getLong(properties.getProperty(USER_ID))))
          .setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)))
          .setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)))
          .setEmail(resultSet.getString(properties.getProperty(USER_EMAIL)))
//          .setPassword(resultSet.getString(properties.getProperty(USER_PASSWORD)))
          .setRole(
              UserRoles.convertFromIntToRole(resultSet.getInt(properties.getProperty(USER_ROLE))))
          .setActive(
              resultSet.getInt(properties.getProperty(USER_ACTIVE)) == UserActive.ACTIVE.ordinal())
//          .setEmailCode(code)
          .setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)))
          .build();

    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }

  }

  @Override
  public boolean comparisonOfPasswords(BigInteger id, String checkPassword)
      throws DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(CHECK_USER_PASSWORD))) {

      statement.setLong(1, id.longValue());
      statement.setString(2, checkPassword);

      ResultSet resultSet = statement.executeQuery();

      return resultSet.next();
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();

    }
  }


  @Override
  public boolean activateUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_ACTIVE))) {
      statement.setLong(1, id.longValue());

      return statement.executeUpdate() == 1;

    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }

  }

  @Override
  public boolean disactivateUser(BigInteger id) throws DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(UPDATE_USER_DISACTIVE))) {
      statement.setLong(1, id.longValue());

      return statement.executeUpdate() == 1;

    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException();
    }

  }
}
