package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuizDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;

import java.math.BigInteger;
import java.util.List;

public interface QuizService {

    Quiz createQuiz(Quiz quiz) throws DAOLogicException, UserDoesNotExistException;

    void updateQuiz(Quiz updatedQuiz) throws QuizDoesNotExistException, DAOLogicException;

    void deleteQuiz(Quiz quiz) throws QuizDoesNotExistException, DAOLogicException;

    Quiz getQuizById(BigInteger id) throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getQuizzesByType(QuizType quizType) throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getAllQuizzes() throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getQuizzesByTitle(String title) throws QuizDoesNotExistException, DAOLogicException;

    Quiz buildNewQuiz(String title, String description, QuizType quizType, User user);

}
