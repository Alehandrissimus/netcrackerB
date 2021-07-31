package ua.netcracker.netcrackerquizb.service;

import java.math.BigInteger;
import java.util.Set;

import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;

public interface UserService {

  String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
          "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  BigInteger buildNewUser(String email, String password, String name, String surname)
      throws UserException, DAOLogicException;

  User authorize(User user) throws DAOLogicException, UserException;

  void recoverPassword(User user) throws UserException, DAOLogicException, MailException;

  void validateNewUser(String email, String password, String firstName, String lastName)
      throws UserException;



  User getUserById(BigInteger id) throws UserDoesNotExistException, DAOLogicException;

  User getUserByEmail(String email) throws UserDoesNotExistException, DAOLogicException;

  void deleteUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException;

  void updateUsersFullName(BigInteger id, String newFirstName, String newLastName)
          throws DAOLogicException, UserDoesNotExistException;

  void updateUsersPassword(BigInteger id, String newPassword)
          throws DAOLogicException, UserDoesNotExistException;

  void updateUsersDescription(BigInteger id, String newDescription)
          throws DAOLogicException, UserDoesNotExistException;

  User getUserByEmailCode(String code) throws UserDoesNotExistException, DAOLogicException;

  void updateUsersEmailCode(BigInteger id, String newCode) throws DAOLogicException, UserDoesNotExistException;

  boolean comparisonOfPasswords(BigInteger id, String checkPassword) throws DAOLogicException, UserDoesNotExistException;

  boolean activateUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException;

  boolean disactivateUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException;

  Set<QuizAccomplishedImpl> getAccomplishedQuizesByUser(BigInteger id) throws DAOLogicException, QuizDoesNotExistException, UserDoesNotExistException;

  Set<QuizAccomplishedImpl> getFavoriteQuizesByUser(BigInteger id) throws DAOLogicException, UserDoesNotExistException;

  void editAccomplishedQuiz(BigInteger idUser, QuizAccomplishedImpl newQuiz) throws DAOLogicException;

  void setIsFavoriteQuiz(BigInteger idUser, QuizAccomplishedImpl quiz) throws DAOLogicException;

  void addAccomplishedQuiz(BigInteger id, QuizAccomplishedImpl quiz) throws DAOLogicException;

  QuizAccomplishedImpl getAccomplishedQuizById(BigInteger idUser, BigInteger idQuiz) throws QuizDoesNotExistException, DAOLogicException;

  boolean isAccomplishedQuiz(BigInteger idUser, BigInteger idQuiz) throws DAOLogicException;
}
