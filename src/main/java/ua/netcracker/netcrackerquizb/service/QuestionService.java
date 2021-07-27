package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Question;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface QuestionService {

    Question createQuestion(Question question, BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException;

    void updateQuestion(Question updatedQuestion) throws DAOLogicException;

    void deleteQuestion(Question question) throws DAOLogicException, QuestionDoesNotExistException;

    Question getQuestionById(BigInteger questionId) throws DAOLogicException, QuestionDoesNotExistException;

    Collection<Question> getQuestionsByQuiz(BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException;

}
