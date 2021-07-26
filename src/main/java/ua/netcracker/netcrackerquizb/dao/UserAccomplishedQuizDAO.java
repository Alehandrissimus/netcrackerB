package ua.netcracker.netcrackerquizb.dao;

import java.math.BigInteger;
import java.util.Set;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;

public interface UserAccomplishedQuizDAO {

  String URL_PROPERTY = "${spring.datasource.url}";
  String USERNAME_PROPERTY = "${spring.datasource.username}";
  String PASSWORD_PROPERTY = "${spring.datasource.password}";
  String PATH_PROPERTY = "src/main/resources/sqlScripts.properties";
  String DRIVER_PATH_PROPERTY = "oracle.jdbc.OracleDriver";

  Set<QuizAccomplishedImpl> getAccomplishedQuizesByUser(BigInteger id);
  Set<QuizAccomplishedImpl> getFavoriteQuizesByUser(BigInteger id);
  void addFavoriteQuiz(QuizAccomplishedImpl quiz);
  void addAccomplishedQuiz(QuizAccomplishedImpl quiz);
  void removeFavoriteQuiz(QuizAccomplishedImpl quiz);

}
