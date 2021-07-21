package ua.netcracker.netcrackerquizb.model;

import java.math.BigInteger;
import java.util.Set;

public interface User {

  BigInteger getId();

  String getFullName();

  String getFirstName();

  String getLastName();

  String getEmail();

  String getPassword();

  boolean isActive();

  String getEmailCode();

  void setEmailCode(String email);

  Set<BigInteger> getAccomplishedQuizes();

  Set<BigInteger> getFavoriteQuizes();

  UserRoles getUserRole();

  public void setId(BigInteger id);

  public void setFirstName(String firstName);

  public void setLastName(String lastName);

  public void setEmail(String email);

  public void setPassword(String password);

  public void setRole(UserRoles role);

  public void setActive(boolean active);

  public void setFavoriteQuizes(Set<BigInteger> favoriteQuizes);

  public void setAccomplishedQuizes(Set<BigInteger> accomplishedQuizes);


}
