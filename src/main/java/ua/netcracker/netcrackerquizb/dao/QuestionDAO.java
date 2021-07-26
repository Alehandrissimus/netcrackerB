package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {

    final String propertiesPath = "src/main/resources/sqlScripts.properties";
    final String questionIdColumn = "id_question";
    final String questionNameColumn = "question_name";
    final String questionTypeColumn = "question_type";
    final String questionQuizIdColumn = "quiz";

    Question getQuestionById(BigInteger id, Collection<Answer> answers) throws QuestionDoesNotExistException, DaoLogicException;

    Question createQuestion(Question question, BigInteger id) throws QuestionDoesNotExistException, DaoLogicException;

    void deleteQuestion(Question question, BigInteger id) throws QuestionDoesNotExistException, DaoLogicException;

    Collection<Question> getAllQuestions(BigInteger id) throws QuestionDoesNotExistException, DaoLogicException;

    void updateQuestion(Question question) throws DaoLogicException;

}
