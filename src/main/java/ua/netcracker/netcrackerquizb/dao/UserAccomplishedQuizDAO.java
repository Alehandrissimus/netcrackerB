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

  void addFavoriteQuiz(BigInteger id, QuizAccomplishedImpl quiz);

  void addAccomplishedQuiz(BigInteger id, QuizAccomplishedImpl quiz) throws DAOLogicException;

  void removeFavoriteQuiz(BigInteger id, QuizAccomplishedImpl quiz);

  String SEARCH_ACCOMPLISHED_QUIZES_BY_USER_ID = "SEARCH_ACCOMPLISHED_QUIZES_BY_USER_ID";
  String ADD_ACCOMPLISHED_QUIZ = "ADD_ACCOMPLISHED_QUIZ";
  String UPDATE_ACCOMPLISHED_QUIZ = "UPDATE_ACCOMPLISHED_QUIZ";

  int TRUE_SQL = 1;
}
