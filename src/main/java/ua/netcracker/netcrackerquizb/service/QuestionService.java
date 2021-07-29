package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Question;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionService {

    void setTestConnection() throws DAOConfigException;

    Question createQuestion(Question question, BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException, QuestionException, AnswerDoesNotExistException;

    void updateQuestion(Question updatedQuestion) throws DAOLogicException, QuestionDoesNotExistException;

    void deleteQuestion(Question question) throws DAOLogicException, QuestionDoesNotExistException;

    Question getQuestionById(BigInteger questionId) throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException;

    Question getQuestionByData(String questionText, BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException;

    Collection<Question> getQuestionsByQuiz(BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException;

}
