package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {

    final String questionIdColumn = "id_question";
    final String questionNameColumn = "question_name";
    final String questionTypeColumn = "question_type";
    final String questionQuizIdColumn = "quiz";

    final String PROPERTY_GET_QUESTION_BY_ID = "GET_QUESTION_BY_ID";
    final String PROPERTY_GET_QUESTION_BY_DATA = "GET_QUESTION_BY_DATA";
    final String PROPERTY_CREATE_QUESTION = "CREATE_QUESTION";
    final String PROPERTY_GET_QUESTION_ID_BY_DATA = "GET_QUESTION_ID_BY_DATA";
    final String PROPERTY_DELETE_QUESTION = "DELETE_QUESTION";
    final String PROPERTY_GET_ALL_QUESTIONS = "GET_ALL_QUESTIONS";
    final String PROPERTY_UPDATE_QUESTION = "UPDATE_QUESTION";

    void setTestConnection() throws DAOConfigException;

    Question getQuestionById(BigInteger id, Collection<Answer> answers) throws QuestionDoesNotExistException, DAOLogicException;

    Question getQuestionByData(String questionText, BigInteger quizId) throws QuestionDoesNotExistException, DAOLogicException;

    Question createQuestion(Question question, BigInteger id) throws QuestionDoesNotExistException, DAOLogicException;

    void deleteQuestion(Question question) throws QuestionDoesNotExistException, DAOLogicException;

    Collection<Question> getAllQuestions(BigInteger id) throws QuestionDoesNotExistException, DAOLogicException;

    void updateQuestion(Question question) throws DAOLogicException;

}
