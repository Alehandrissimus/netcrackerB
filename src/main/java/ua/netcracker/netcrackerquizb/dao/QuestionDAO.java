package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionNotFoundException;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {
    Question getQuestionById(BigInteger id, Collection<Answer> answers) throws QuestionNotFoundException, DAOLogicException;

    Question createQuestion(Question question, BigInteger id) throws QuestionNotFoundException, DAOLogicException;

    void deleteQuestion(Question question, BigInteger id) throws QuestionNotFoundException, DAOLogicException;

    Collection<Question> getAllQuestions(BigInteger id) throws QuestionNotFoundException, DAOLogicException;

    void updateQuestion(Question question) throws DAOLogicException;

}
