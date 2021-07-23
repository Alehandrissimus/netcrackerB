package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.UserDAO;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.UserRoles;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

@Repository
public class UserDAOImpl implements UserDAO {

  public static final String SEARCH_USER_BY_ID = "SELECT * FROM usr WHERE id_usr=(?)";
  public static final String SEARCH_USER_BY_EMAIL = "SELECT * FROM usr WHERE email=(?)";
  public static final String SEARCH_USER_BY_EMAIL_CODE = "SELECT * FROM usr WHERE email_code=(?)";
  public static final String SEARCH_USER_AUTHORIZE = "SELECT * FROM usr WHERE email=(?) and passwd=(?) and isactive='1'";

  private static final String UPDATE_USER_NAME = "UPDATE usr SET first_name=(?), last_name=(?) WHERE id_usr=(?)";
  private static final String UPDATE_USER_PASSWORD = "UPDATE usr SET passwd=(?) WHERE id_usr=(?)";
  private static final String UPDATE_USER_DESCRIPTION = "UPDATE usr SET description=(?) WHERE id_usr=(?)";
  private static final String UPDATE_USER_ACTIVE = "UPDATE usr SET isactive='1' WHERE id_usr=(?)";

//  private static final JOIN

  public static final String DELETE_USER_BY_ID = "DELETE FROM usr WHERE id_usr=(?)";


  public static final String CREATE_USER = "INSERT INTO usr VALUES (s_usr.NEXTVAL, ?,?,?,?,?,?,?,?)";
  public static final String USER_TABLE = "usr";
  public static final String USER_ID = "id_usr";
  public static final String USER_FIRST_NAME = "first_name";
  public static final String USER_LAST_NAME = "last_name";
  public static final String USER_EMAIL = "email";
  public static final String USER_PASSWORD = "passwd";
  public static final String USER_ROLE = "usr_role";
  public static final String USER_ACTIVE = "isactive";
  public static final String USER_EMAIL_CODE = "email_code";
  public static final String USER_DESCRIPTION = "description";

  private Connection connection;

  @Autowired
  UserDAOImpl(
      @Value("${spring.datasource.url}") String URL,
      @Value("${spring.datasource.username}") String USERNAME,
      @Value("${spring.datasource.password}") String PASSWORD
  ) {
    try {
      Class.forName("oracle.jdbc.OracleDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Override
  public User getUserById(BigInteger id) {
    User user = null;
    try (PreparedStatement statement = connection.prepareStatement(SEARCH_USER_BY_ID)) {
      statement.setInt(1, id.intValue());

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        user = new UserImpl();
        user.setId(id);
        user.setFirstName(resultSet.getString(USER_FIRST_NAME));
        user.setLastName(resultSet.getString(USER_LAST_NAME));
        user.setEmail(resultSet.getString(USER_EMAIL));
        user.setPassword(resultSet.getString(USER_PASSWORD));
        switch (resultSet.getInt(USER_ROLE)) {
          case 1:
            user.setRole(UserRoles.ADMIN);
            break;
          case 2:
            user.setRole(UserRoles.USER);
            break;
          default:
            user.setRole(UserRoles.UNVERIFIED);
        }
        user.setActive(resultSet.getInt(USER_ACTIVE) == 1);
        user.setEmailCode(resultSet.getString(USER_EMAIL_CODE));
        user.setDescription(resultSet.getString(USER_DESCRIPTION));
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
    try (PreparedStatement statement = connection.prepareStatement(SEARCH_USER_BY_EMAIL)) {
      statement.setString(1, email);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        user = new UserImpl();
        user.setId(BigInteger.valueOf(resultSet.getLong(USER_ID)));
        user.setFirstName(resultSet.getString(USER_FIRST_NAME));
        user.setLastName(resultSet.getString(USER_LAST_NAME));
        user.setEmail(email);
        user.setPassword(resultSet.getString(USER_PASSWORD));
        switch (resultSet.getInt(USER_ROLE)) {
          case 1:
            user.setRole(UserRoles.ADMIN);
            break;
          case 2:
            user.setRole(UserRoles.USER);
            break;
          default:
            user.setRole(UserRoles.UNVERIFIED);
        }
        user.setActive(resultSet.getInt(USER_ACTIVE) == 1);
        user.setEmailCode(resultSet.getString(USER_EMAIL_CODE));
        user.setDescription(resultSet.getString(USER_DESCRIPTION));

      }
    } catch (SQLException e) {
      e.printStackTrace();
      return user;
    }
    return user;
  }

  @Override
  public void deleteUser(BigInteger id) {
    try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
      statement.setInt(1, id.intValue());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void createUser(String email, String lastName, String firstName, String password,
      String emailCode) {
    try (PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
      statement.setString(1, firstName);
      statement.setString(2, lastName);
      statement.setString(3, null);
      statement.setString(4, email);
      statement.setString(5, password);
      statement.setInt(6, 3);
      statement.setInt(7, 0);
      statement.setString(8, emailCode);

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void updateUsersName(BigInteger id, String newFirstName, String newLastName) {
    try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_NAME)) {
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
    try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
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
    try (PreparedStatement statement = connection.prepareStatement(SEARCH_USER_AUTHORIZE)) {
      statement.setString(1, email);
      statement.setString(2, password);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        user = new UserImpl();
        user.setId(BigInteger.valueOf(resultSet.getLong(USER_ID)));
        user.setFirstName(resultSet.getString(USER_FIRST_NAME));
        user.setLastName(resultSet.getString(USER_LAST_NAME));
        user.setEmail(email);
        user.setPassword(password);
        switch (resultSet.getInt(USER_ROLE)) {
          case 1:
            user.setRole(UserRoles.ADMIN);
            break;
          case 2:
            user.setRole(UserRoles.USER);
            break;
          default:
            user.setRole(UserRoles.UNVERIFIED);
        }
        user.setActive(resultSet.getInt(USER_ACTIVE) == 1);
        user.setEmailCode(resultSet.getString(USER_EMAIL_CODE));
        user.setDescription(resultSet.getString(USER_DESCRIPTION));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return user;
    }
    return user;
  }

  @Override
  public void updateUsersDescription(BigInteger id, String newDescription) {
    try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_DESCRIPTION)) {
      statement.setString(1, newDescription);
      statement.setInt(2, id.intValue());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public User getUserByEmailCode(String code) {
    User user = null;
    try (PreparedStatement statement = connection.prepareStatement(SEARCH_USER_BY_EMAIL_CODE)) {
      statement.setString(1, code);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        user = new UserImpl();
        user.setId((BigInteger) resultSet.getObject(USER_ID));
        user.setFirstName(resultSet.getString(USER_FIRST_NAME));
        user.setLastName(resultSet.getString(USER_LAST_NAME));
        user.setEmail(resultSet.getString(USER_EMAIL));
        user.setPassword(resultSet.getString(USER_PASSWORD));
        switch (resultSet.getInt(USER_ROLE)) {
          case 1:
            user.setRole(UserRoles.ADMIN);
            break;
          case 2:
            user.setRole(UserRoles.USER);
            break;
          default:
            user.setRole(UserRoles.UNVERIFIED);
        }
        user.setActive(resultSet.getInt(USER_ACTIVE) == 1);
        user.setEmailCode(code);
        user.setDescription(resultSet.getString(USER_DESCRIPTION));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return user;
    }
    return user;
  }

  @Override
  public void activateUser(BigInteger id) {
    try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_ACTIVE)) {
      statement.setInt(1, id.intValue());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
