package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.List;

public interface QuizService {

    Quiz buildNewQuiz(String title, String description, QuizType quizType, BigInteger userId) throws QuizException, DAOLogicException, UserException;

    void updateQuiz(Quiz updatedQuiz) throws QuizDoesNotExistException, DAOLogicException;

    void deleteQuiz(Quiz quiz) throws QuizDoesNotExistException, DAOLogicException, UserDoesNotExistException, UserException;

    Quiz getQuizById(BigInteger id) throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getQuizzesByType(QuizType quizType) throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getAllQuizzes() throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getLastThreeCreatedQuizzes() throws QuizDoesNotExistException, DAOLogicException;

    Quiz getQuizByTitle(String title) throws QuizDoesNotExistException, DAOLogicException, QuizException;


}
