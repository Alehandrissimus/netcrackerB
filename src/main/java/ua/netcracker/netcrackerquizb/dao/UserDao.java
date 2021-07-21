package ua.netcracker.netcrackerquizb.dao;

import java.math.BigInteger;
import java.util.Set;
import ua.netcracker.netcrackerquizb.model.User;

public interface UserDao {

  User getUserById(BigInteger id);
  User getUserByEmail(String email);
  void deleteUser(BigInteger id);
  User createUser(String email, String lastName, String firstName, String password);
  User updateUsersName(User user, String newName);
  User updateUsersPassword(User user, String newPassword);
  User getAuthorizeUser(String email, String password);
  User updateUsersDescription(BigInteger id, String newDescription);
  Set<BigInteger> getAccomplishedQuizes(BigInteger id);
  Set<BigInteger> getFavoriteQuizes(BigInteger id);
  void addAccomplishedQuiz(BigInteger id);
  void addFavoriteQuiz(BigInteger id);
  void removeFavoriteQuiz(BigInteger id);
  String getUserByEmailCode(String code);
  void activateUser(BigInteger id);

}
