package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
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
import ua.netcracker.netcrackerquizb.exception.MessagesForException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotConfirmedEmailException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.UserActive;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.UserRoles;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import ua.netcracker.netcrackerquizb.util.DAOUtil;

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
  ) throws DAOConfigException {
    this.URL = URL;
    this.USERNAME = USERNAME;
    this.PASSWORD = PASSWORD;

    connection = DAOUtil.getDataSource(URL, USERNAME, PASSWORD, properties);
  }

  public void setTestConnection() throws DAOConfigException {
    try {
      connection = DAOUtil.getDataSource(URL, USERNAME + "_TEST", PASSWORD, properties);
    } catch (DAOConfigException e) {
      log.error(MessagesForException.TEST_CONNECTION_ERR + e.getMessage());
      throw new DAOConfigException(MessagesForException.TEST_CONNECTION_ERR, e);
    }
  }


  @Override
  public User getUserById(BigInteger id) throws UserDoesNotExistException, DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_ID))) {

      statement.setLong(1, id.longValue());

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        throw new UserDoesNotExistException(MessagesForException.INVALID_USERS_ID + id);
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

    } catch (SQLException | UserException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(MessagesForException.DAO_LOGIC_EXCEPTION + id, e);
    }
  }

  @Override
  public User getUserByEmail(String email) throws UserDoesNotExistException, DAOLogicException {

    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_EMAIL))) {

      statement.setString(1, email);

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        throw new UserDoesNotExistException(MessagesForException.INVALID_USERS_EMAIL + email);
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

    } catch (SQLException | UserException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION + email, e);
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
      throw new DAOLogicException(DAO_LOGIC_EXCEPTION + id, e);
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
        throw new DAOLogicException(MessagesForException.ERROR_WHILE_CREATING_USER + user);
      }

      return getUserByEmail(user.getEmail()).getId();

    } catch (SQLException | UserDoesNotExistException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(MessagesForException.ERROR_WHILE_CREATING_USER + user, e);
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
        throw new UserDoesNotExistException(MessagesForException.USERS_DOESNT_EXIT + id);
      }
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(MessagesForException.DAO_LOGIC_EXCEPTION + id, e);
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
        throw new UserDoesNotExistException(MessagesForException.USERS_DOESNT_EXIT + id);
      }
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(MessagesForException.USERS_DOESNT_EXIT + id, e);
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
        throw new UserDoesNotExistException(
            MessagesForException.USERS_DOESNT_EXIT + email + password);
      }

      if (resultSet.getInt(properties.getProperty(USER_ACTIVE)) == UserActive.NOT_ACTIVE
          .ordinal()) {
        throw new UserDoesNotConfirmedEmailException(
            MessagesForException.USERS_DOESNT_EXIT + email + password);
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

    } catch (SQLException | UserException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(
          MessagesForException.DAO_LOGIC_EXCEPTION + email + password, e);
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
        throw new UserDoesNotExistException(MessagesForException.USERS_DOESNT_EXIT + id);
      }
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(MessagesForException.DAO_LOGIC_EXCEPTION + id + newDescription,
          e);
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
        throw new UserDoesNotExistException(MessagesForException.USERS_DOESNT_EXIT + id);
      }
    } catch (SQLException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(
          MessagesForException.DAO_LOGIC_EXCEPTION + id + newCode, e);
    }
  }

  @Override
  public User getUserByEmailCode(String code) throws UserDoesNotExistException, DAOLogicException {
    try (PreparedStatement statement = connection
        .prepareStatement(properties.getProperty(SEARCH_USER_BY_EMAIL_CODE))) {
      statement.setString(1, code);

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        throw new UserDoesNotExistException(MessagesForException.USERS_DOESNT_EXIT + code);
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

    } catch (SQLException | UserException e) {
      log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
      throw new DAOLogicException(MessagesForException.DAO_LOGIC_EXCEPTION + code, e);
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
      throw new DAOLogicException(
          MessagesForException.DAO_LOGIC_EXCEPTION + id + checkPassword, e);

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
      throw new DAOLogicException(MessagesForException.DAO_LOGIC_EXCEPTION + id, e);
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
      throw new DAOLogicException(MessagesForException.DAO_LOGIC_EXCEPTION + id, e);
    }

  }
}
