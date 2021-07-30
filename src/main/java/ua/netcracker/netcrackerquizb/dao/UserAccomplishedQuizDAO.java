package ua.netcracker.netcrackerquizb.dao;

import java.math.BigInteger;
import java.util.Set;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuizDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;

public interface UserAccomplishedQuizDAO {

  String URL_PROPERTY = "${spring.datasource.url}";
  String USERNAME_PROPERTY = "${spring.datasource.username}";
  String PASSWORD_PROPERTY = "${spring.datasource.password}";
  String PATH_PROPERTY = "src/main/resources/sqlScripts.properties";
  String DRIVER_PATH_PROPERTY = "oracle.jdbc.OracleDriver";

  Set<QuizAccomplishedImpl> getAccomplishedQuizesByUser(BigInteger id)
      throws DAOLogicException, QuizDoesNotExistException;

  Set<QuizAccomplishedImpl> getFavoriteQuizesByUser(BigInteger id) throws DAOLogicException;

  void editAccomplishedQuiz(BigInteger idUser, QuizAccomplishedImpl newQuiz) throws DAOLogicException;

  void setIsFavoriteQuiz(BigInteger idUser, QuizAccomplishedImpl quiz) throws DAOLogicException;

  void addAccomplishedQuiz(BigInteger id, QuizAccomplishedImpl quiz) throws DAOLogicException;


  String SEARCH_ACCOMPLISHED_QUIZES_BY_USER_ID = "SEARCH_ACCOMPLISHED_QUIZES_BY_USER_ID";
  String ADD_ACCOMPLISHED_QUIZ = "ADD_ACCOMPLISHED_QUIZ";
  String UPDATE_ACCOMPLISHED_QUIZ = "UPDATE_ACCOMPLISHED_QUIZ";
  String SET_IS_FAVOURITE = "SET_IS_FAVOURITE";
  String GET_ACCOMPLISHED_QUIZ = "GET_ACCOMPLISHED_QUIZ";

  String CORRECT_ANSWERS = "correct_answers";
  String IS_FAVOURITE = "is_favourite";
  String QUIZ = "quiz";

  String MESSAGE_FOR_GET_ACCOMPLISHED_QUIZ_BY_ID = " in getAccomplishedQuizById";

  int TRUE_SQL = 1;
}
