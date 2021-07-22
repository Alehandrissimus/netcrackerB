package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.UserDAO;
import ua.netcracker.netcrackerquizb.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

  //  добавить в параметры получения accomplished и favorite quizes
  public static final String SEARCH_USER_BY_ID_SQL = "SELECT * FROM usr WHERE id=(?)";
  public static final String SEARCH_USER_BY_EMAIL_SQL = "SELECT * FROM usr WHERE email=(?)";
  public static final String DELETE_USER_BY_ID = "DELETE FROM usr WHERE id=(?)";
  public static final String CREATE_USER = "INSERT INTRO usr (email, first_name, last_name, passwd) VALUES (?,?,?,?)";
//  public static final String GET_AUTHORIZE_USER = "SELECT "
//    в бд нет поля boolean active

  public static final String SEARCH_USER_BY_EMAIL_CODE = "SELECT * FROM usr WHERE email_code=(?)";

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
    return null;
  }

  @Override
  public User getUserByEmail(String email) {
    return null;
  }

  @Override
  public void deleteUser(BigInteger id) {

  }

  @Override
  public User createUser(String email, String lastName, String firstName, String password) {
    return null;
  }

  @Override
  public User updateUsersName(User user, String newName) {
    return null;
  }

  @Override
  public User updateUsersPassword(User user, String newPassword) {
    return null;
  }

  @Override
  public User getAuthorizeUser(String email, String password) {
    return null;
  }

  @Override
  public User updateUsersDescription(BigInteger id, String newDescription) {
    return null;
  }

  @Override
  public Set<BigInteger> getAccomplishedQuizes(BigInteger id) {
    return null;
  }

  @Override
  public Set<BigInteger> getFavoriteQuizes(BigInteger id) {
    return null;
  }

  @Override
  public void addAccomplishedQuiz(BigInteger id) {

  }

  @Override
  public void addFavoriteQuiz(BigInteger id) {

  }

  @Override
  public void removeFavoriteQuiz(BigInteger id) {

  }

  @Override
  public String getUserByEmailCode(String code) {
    return null;
  }

  @Override
  public void activateUser(BigInteger id) {

  }
}
