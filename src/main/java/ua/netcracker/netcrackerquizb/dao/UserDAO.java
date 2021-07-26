package ua.netcracker.netcrackerquizb.dao;

import java.math.BigInteger;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotConfirmedEmailException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.User;

public interface UserDAO {

  User getUserById(BigInteger id) throws UserDoesNotExistException, DAOLogicException;

  User getUserByEmail(String email) throws UserDoesNotExistException, DAOLogicException;

  void deleteUser(BigInteger id) throws DAOLogicException;

  BigInteger createUser(User user) throws DAOLogicException;

  void updateUsersFullName(BigInteger id, String newFirstName, String newLastName)
      throws DAOLogicException, UserDoesNotExistException;

  void updateUsersPassword(BigInteger id, String newPassword)
      throws DAOLogicException, UserDoesNotExistException;

  User getAuthorizeUser(String email, String password)
      throws UserDoesNotExistException, UserDoesNotConfirmedEmailException, DAOLogicException;

  void updateUsersDescription(BigInteger id, String newDescription)
      throws DAOLogicException, UserDoesNotExistException;

  User getUserByEmailCode(String code) throws UserDoesNotExistException, DAOLogicException;

  void updateUsersEmailCode(BigInteger id, String newCode)
      throws DAOLogicException, UserDoesNotExistException;

  boolean comparisonOfPasswords(BigInteger id, String checkPassword) throws DAOLogicException;

  boolean activateUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException;

  boolean disactivateUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException;

  String URL_PROPERTY = "${spring.datasource.url}";
  String USERNAME_PROPERTY = "${spring.datasource.username}";
  String PASSWORD_PROPERTY = "${spring.datasource.password}";
  String PATH_PROPERTY = "src/main/resources/sqlScripts.properties";
  String DRIVER_PATH_PROPERTY = "oracle.jdbc.OracleDriver";
  String SEARCH_USER_BY_ID = "SEARCH_USER_BY_ID";
  String USER_FIRST_NAME = "USER_FIRST_NAME";
  String USER_LAST_NAME = "USER_LAST_NAME";
  String USER_EMAIL = "USER_EMAIL";
  String USER_PASSWORD = "USER_PASSWORD";
  String USER_ROLE = "USER_ROLE";
  String USER_ACTIVE = "USER_ACTIVE";
  String USER_EMAIL_CODE = "USER_EMAIL_CODE";

  String USER_DESCRIPTION = "USER_DESCRIPTION";
  String USER_ID = "USER_ID";
  String DELETE_USER_BY_ID = "DELETE_USER_BY_ID";
  String CREATE_USER = "CREATE_USER";
  String UPDATE_USER_NAME = "UPDATE_USER_NAME";
  String UPDATE_USER_PASSWORD = "UPDATE_USER_PASSWORD";
  String SEARCH_USER_AUTHORIZE = "SEARCH_USER_AUTHORIZE";
  String UPDATE_USER_DESCRIPTION = "UPDATE_USER_DESCRIPTION";
  String UPDATE_USER_EMAIL_CODE = "UPDATE_USER_EMAIL_CODE";
  String SEARCH_USER_BY_EMAIL_CODE = "SEARCH_USER_BY_EMAIL_CODE";
  String UPDATE_USER_ACTIVE = "UPDATE_USER_ACTIVE";
  String UPDATE_USER_DISACTIVE = "UPDATE_USER_DISACTIVE";
  String SEARCH_USER_BY_EMAIL = "SEARCH_USER_BY_EMAIL";
  String CHECK_USER_PASSWORD = "CHECK_USER_PASSWORD";

  String DAO_LOGIC_EXCEPTION = "Dao logic exception ";

  int TRUE_SQL = 1;

}
