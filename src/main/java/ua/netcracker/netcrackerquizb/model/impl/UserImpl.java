package ua.netcracker.netcrackerquizb.model.impl;

import java.math.BigInteger;
import java.util.Set;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.UserRoles;

public class UserImpl implements User {

  private BigInteger id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private UserRoles role;
  private boolean active;
  private Set<BigInteger> favoriteQuizes;
  private Set<BigInteger> accomplishedQuizes;
  private String emailCode;


  @Override
  public BigInteger getId() {
    return id;
  }

  @Override
  public String getFullName() {
    return lastName + " " + firstName;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public Set<BigInteger> getAccomplishedQuizes() {
    return accomplishedQuizes;
  }

  @Override
  public Set<BigInteger> getFavoriteQuizes() {
    return favoriteQuizes;
  }

  @Override
  public UserRoles getUserRole() {
    return role;
  }

  @Override
  public String getEmailCode() {
    return emailCode;
  }

  @Override
  public void setEmailCode(String emailCode) {
    this.emailCode = emailCode;
  }
}
