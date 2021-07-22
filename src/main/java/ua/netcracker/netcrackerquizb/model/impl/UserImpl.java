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
  private String description;
  private boolean active;
  private Set<BigInteger> favoriteQuizes;
  private Set<BigInteger> accomplishedQuizes;
  private String emailCode;

  public Set<BigInteger> getAllFavoriteQuizes() {
    return favoriteQuizes;
  }

  public Set<BigInteger> getAllAccomplishedQuizes() {
    return favoriteQuizes;
  }

  public void addFavoriteQuiz(BigInteger id) {
    favoriteQuizes.add(id);
  }

  public void addAccomplishedQuiz(BigInteger id) {
    accomplishedQuizes.add(id);
  }

  public void removeFavoriteQuiz(BigInteger id) {
    favoriteQuizes.remove(id);
  }

  @Override
  public void setId(BigInteger id) {
    this.id = id;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public void setRole(UserRoles role) {
    this.role = role;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String getDescription() {
    return description;
  }


  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public void setFavoriteQuizes(Set<BigInteger> favoriteQuizes) {
    this.favoriteQuizes = favoriteQuizes;
  }

  @Override
  public void setAccomplishedQuizes(Set<BigInteger> accomplishedQuizes) {
    this.accomplishedQuizes = accomplishedQuizes;
  }

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

  @Override
  public String toString() {
    return "UserImpl{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", role=" + role +
        ", description='" + description + '\'' +
        ", active=" + active +
        ", favoriteQuizes=" + favoriteQuizes +
        ", accomplishedQuizes=" + accomplishedQuizes +
        ", emailCode='" + emailCode + '\'' +
        '}';
  }
}
