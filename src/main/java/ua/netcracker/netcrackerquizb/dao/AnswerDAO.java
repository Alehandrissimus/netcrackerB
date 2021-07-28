package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.AnswerDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Answer;

import java.math.BigInteger;
import java.util.Collection;

public interface AnswerDAO {
    String SQL_ANSWER_ID = "id_answer";
    String SQL_MAX_ID_ANSWER = "MAX(ID_ANSWER)";
    String SQL_ANSWER_TEXT = "text";
    String SQL_ANSWER_IS_TRUE = "is_true";
    String SQL_ANSWER_QUESTION = "question";

    Answer getAnswerById(BigInteger id) throws DAOLogicException, AnswerDoesNotExistException;

    BigInteger getLastAnswerIdByTitle(String title) throws DAOLogicException, AnswerDoesNotExistException;

    BigInteger createAnswer(Answer answer) throws DAOLogicException, AnswerDoesNotExistException;

    void deleteAnswer(BigInteger id) throws DAOLogicException;

    BigInteger updateAnswer(Answer answer) throws DAOLogicException;

    Collection<Answer> getAnswersByQuestionId(BigInteger questionId) throws DAOLogicException, AnswerDoesNotExistException;

}
