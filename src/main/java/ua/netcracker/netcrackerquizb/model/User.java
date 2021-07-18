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


}
