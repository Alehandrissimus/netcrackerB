package ua.netcracker.netcrackerquizb.model.impl;

import java.math.BigInteger;
import java.util.Set;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.UserRoles;

public class UserImpl implements User {

  private UserImpl() {

  }

  private BigInteger id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private UserRoles role;
  private String description;
  private boolean active;
  private Set<Quiz> favoriteQuizes;
  private Set<Quiz> accomplishedQuizes;
  private String emailCode;

  public static class UserBuilder {

    private final UserImpl newUser;

    public UserBuilder() {
      newUser = new UserImpl();
    }

    public UserBuilder setId(BigInteger id) {
      newUser.id = id;
      return this;
    }

    public UserBuilder setFirstName(String firstName) {
      newUser.firstName = firstName;
      return this;
    }

    public UserBuilder setLastName(String lastName) {
      newUser.lastName = lastName;
      return this;
    }

    public UserBuilder setEmail(String email) {
      newUser.email = email;
      return this;
    }

    public UserBuilder setPassword(String password) {
      newUser.password = password;
      return this;
    }

    public UserBuilder setRole(UserRoles role) {
      newUser.role = role;
      return this;
    }

    public UserBuilder setDescription(String description) {
      newUser.description = description;
      return this;
    }

    public UserBuilder setActive(boolean active) {
      newUser.active = active;
      return this;
    }

    public UserBuilder setFavoriteQuizes(Set<Quiz> favoriteQuizes) {
      newUser.favoriteQuizes = favoriteQuizes;
      return this;
    }

    public UserBuilder setAccomplishedQuizes(Set<Quiz> accomplishedQuizes) {
      newUser.accomplishedQuizes = accomplishedQuizes;
      return this;
    }

    public UserBuilder setEmailCode(String emailCode) {
      newUser.emailCode = emailCode;
      return this;
    }

    public User build() {
      return newUser;
    }

  }

  @Override
  public Set<Quiz> getAllFavoriteQuizes() {
    return favoriteQuizes;
  }

  @Override
  public Set<Quiz> getAllAccomplishedQuizes() {
    return favoriteQuizes;
  }

  @Override
  public void addFavoriteQuiz(Quiz quiz) {
    favoriteQuizes.add(quiz);
  }

  @Override
  public void addAccomplishedQuiz(Quiz quiz) {
    accomplishedQuizes.add(quiz);
  }

  @Override
  public void removeFavoriteQuiz(Quiz quiz) {
    favoriteQuizes.remove(quiz);
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
  public void setFavoriteQuizes(Set<Quiz> favoriteQuizes) {
    this.favoriteQuizes = favoriteQuizes;
  }

  @Override
  public void setAccomplishedQuizes(Set<Quiz> accomplishedQuizes) {
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
  public Set<Quiz> getAccomplishedQuizes() {
    return accomplishedQuizes;
  }

  @Override
  public Set<Quiz> getFavoriteQuizes() {
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
