package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.List;

public interface QuizService {

    int MIN_LENGTH_TITLE = 5;
    int MIN_LENGTH_DESCRIPTION = 5;

    Quiz buildNewQuiz(String title, String description, QuizType quizType, List<Question> questions, BigInteger userId) throws QuizException, DAOLogicException, UserException, QuestionException;

    void updateQuiz(Quiz updatedQuiz) throws QuizDoesNotExistException, DAOLogicException;

    void deleteQuiz(Quiz quiz) throws QuizDoesNotExistException, DAOLogicException, UserDoesNotExistException, UserException;

    Quiz getQuizById(BigInteger id) throws QuizDoesNotExistException, DAOLogicException, QuizException;

    List<Quiz> getQuizzesByType(QuizType quizType) throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getAllQuizzes() throws QuizDoesNotExistException, DAOLogicException;

    List<Quiz> getLastCreatedQuizzes(BigInteger count) throws QuizDoesNotExistException, DAOLogicException;

    Quiz getQuizByTitle(String title) throws QuizDoesNotExistException, DAOLogicException, QuizException;

    void validateNewQuiz(String title, String description, List<Question> questions, BigInteger creatorId) throws QuizException, UserException, QuestionException;

    void setTestConnection() throws DAOConfigException;
}
