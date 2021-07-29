package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuizDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.List;

public interface QuizDAO {

    String URL_PROPERTY = "${spring.datasource.url}";
    String USERNAME_PROPERTY = "${spring.datasource.username}";
    String PASSWORD_PROPERTY = "${spring.datasource.password}";

    String ID_QUIZ = "ID_QUIZ";
    String TITLE = "TITLE";
    String DESCRIPTION = "DESCRIPTION";
    String CREATION_DATE = "CREATION_DATE";
    String QUIZ_TYPE = "QUIZ_TYPE";
    String CREATOR = "CREATOR";

    String INSERT_INTO_QUIZ = "INSERT_INTO_QUIZ";
    String GET_QUIZ_ID_BY_DATA = "GET_QUIZ_ID_BY_DATA";
    String UPDATE_QUIZ = "UPDATE_QUIZ";
    String DELETE_QUIZ = "DELETE_QUIZ";
    String SELECT_QUIZ_BY_ID = "SELECT_QUIZ_BY_ID";
    String SELECT_ALL_QUIZZES = "SELECT_ALL_QUIZZES";
    String SELECT_QUIZ_BY_TITLE = "SELECT_QUIZ_BY_TITLE";
    String SELECT_QUIZZES_BY_TYPE = "SELECT_QUIZZES_BY_TYPE";
    String SELECT_LAST_THREE_CREATED_QUIZZES = "SELECT_LAST_THREE_CREATED_QUIZZES";

    Quiz createQuiz(Quiz quiz) throws DAOLogicException, UserDoesNotExistException;

    void updateQuiz(Quiz quiz) throws QuizDoesNotExistException, DAOLogicException;

    void deleteQuiz(Quiz quiz) throws QuizDoesNotExistException, DAOLogicException;

    Quiz getQuizById(BigInteger id) throws QuizDoesNotExistException, DAOLogicException;

    boolean existQuizByTitle(String title) throws DAOLogicException;

    List<Quiz> getQuizzesByType(QuizType quizType) throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getAllQuizzes() throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getLastThreeCreatedQuizzes() throws DAOLogicException, QuizDoesNotExistException;

    Quiz getQuizByTitle(String title) throws QuizDoesNotExistException, DAOLogicException;

}
