package ua.netcracker.netcrackerquizb.dao;

import java.math.BigInteger;
import java.util.Set;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;

public interface UserDAO {

  User getUserById(BigInteger id, Set<Quiz> accomplishedQuizes, Set<Quiz> favoriteQuizes);

  User getUserByEmail(String email, Set<Quiz> accomplishedQuizes, Set<Quiz> favoriteQuizes);

  void deleteUser(BigInteger id);

  void createUser(String email, String lastName, String firstName, String password,
      String emailCode);

  void updateUsersName(BigInteger id, String newFirstName, String newLastName);

  void updateUsersPassword(BigInteger id, String newPassword);

  User getAuthorizeUser(String email, String password);

  void updateUsersDescription(BigInteger id, String newDescription);

  User getUserByEmailCode(String code);

  void activateUser(BigInteger id);

}
