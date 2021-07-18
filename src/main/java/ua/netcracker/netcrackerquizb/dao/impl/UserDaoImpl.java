package ua.netcracker.netcrackerquizb.dao.impl;

import java.math.BigInteger;
import java.util.Set;
import ua.netcracker.netcrackerquizb.dao.UserDao;
import ua.netcracker.netcrackerquizb.model.User;

public class UserDaoImpl implements UserDao {

  @Override
  public User getUser(BigInteger id) {
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
