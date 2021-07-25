package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionNotFoundException;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {
    Question getQuestionById(BigInteger id, Collection<Answer> answers) throws QuestionNotFoundException, DaoLogicException;

    Question createQuestion(Question question, BigInteger id) throws QuestionNotFoundException, DaoLogicException;

    void deleteQuestion(Question question, BigInteger id) throws QuestionNotFoundException, DaoLogicException;

    Collection<Question> getAllQuestions(BigInteger id) throws QuestionNotFoundException, DaoLogicException;

    void updateQuestion(Question question) throws DaoLogicException;

}
