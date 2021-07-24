package ua.netcracker.netcrackerquizb.dao;

import java.math.BigInteger;
import ua.netcracker.netcrackerquizb.model.User;

public interface UserDAO {

  User getUserById(BigInteger id);

  User getUserByEmail(String email);

  void deleteUser(BigInteger id);

  void createUser(String email, String lastName, String firstName, String password,
      String emailCode);

  void updateUsersName(BigInteger id, String newFirstName, String newLastName);

  void updateUsersPassword(BigInteger id, String newPassword);

  User getAuthorizeUser(String email, String password);

  void updateUsersDescription(BigInteger id, String newDescription);

  User getUserByEmailCode(String code);

  void updateUsersEmailCode(BigInteger id, String newCode);

  void activateUser(BigInteger id);

}
