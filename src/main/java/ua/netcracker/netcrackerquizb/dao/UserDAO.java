package ua.netcracker.netcrackerquizb.dao;

import java.math.BigInteger;
import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotConfirmedEmailException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.User;

public interface UserDAO {

  User getUserById(BigInteger id) throws UserDoesNotExistException, DaoLogicException;

  User getUserByEmail(String email) throws UserDoesNotExistException, DaoLogicException;

  void deleteUser(BigInteger id) throws DaoLogicException;

  void createUser(User user) throws DaoLogicException;

  void updateUsersFullName(BigInteger id, String newFirstName, String newLastName)
      throws DaoLogicException;

  void updateUsersPassword(BigInteger id, String newPassword) throws DaoLogicException;

  User getAuthorizeUser(String email, String password)
      throws UserDoesNotExistException, UserDoesNotConfirmedEmailException, DaoLogicException;

  void updateUsersDescription(BigInteger id, String newDescription) throws DaoLogicException;

  User getUserByEmailCode(String code) throws UserDoesNotExistException, DaoLogicException;

  void updateUsersEmailCode(BigInteger id, String newCode) throws DaoLogicException;

  void activateUser(BigInteger id) throws DaoLogicException;

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
  String SEARCH_USER_BY_EMAIL = "SEARCH_USER_BY_EMAIL";

  int TRUE_SQL = 1;

}
