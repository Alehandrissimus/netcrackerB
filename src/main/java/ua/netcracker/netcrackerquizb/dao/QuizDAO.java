package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.exception.QuizDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.List;

public interface QuizDAO {

    Quiz createQuiz(Quiz quiz) throws DaoLogicException, UserDoesNotExistException;

    void updateQuiz(Quiz quiz) throws QuizDoesNotExistException, DaoLogicException;

    void deleteQuiz(Quiz quiz) throws QuizDoesNotExistException, DaoLogicException;

    Quiz getQuizById(BigInteger id) throws QuizDoesNotExistException, DaoLogicException;

    List<Quiz> getQuizzesByType(QuizType quizType) throws QuizDoesNotExistException, DaoLogicException;

    List<Quiz> getAllQuizzes() throws QuizDoesNotExistException, DaoLogicException;

    List<Quiz> getQuizzesByTitle(String title) throws QuizDoesNotExistException, DaoLogicException;

}
