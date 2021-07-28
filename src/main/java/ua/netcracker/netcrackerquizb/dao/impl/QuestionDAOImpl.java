package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;
import ua.netcracker.netcrackerquizb.util.DAOUtil;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

@Repository
public class QuestionDAOImpl implements QuestionDAO, MessagesForException {

    private Connection connection;
    private final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(QuestionDAOImpl.class);

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    @Autowired
    QuestionDAOImpl(
            @Value("${spring.datasource.url}") String URL,
            @Value("${spring.datasource.username}") String USERNAME,
            @Value("${spring.datasource.password}") String PASSWORD
    ) throws DAOConfigException {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;

        connection = DAOUtil.getDataSource(URL, USERNAME, PASSWORD, properties);
    }

    public void setTestConnection() throws DAOConfigException {
        try {
            connection = DAOUtil.getDataSource(URL, USERNAME + "_TEST", PASSWORD, properties);
        } catch (DAOConfigException e) {
            log.error(String.format(testConnectionError, e.getMessage()));
            throw new DAOConfigException(testConnectionError, e);
        }
    }

    @Override
    public Question getQuestionById(BigInteger questionId, Collection<Answer> answers) throws QuestionDoesNotExistException, DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("GET_QUESTION_BY_ID"))) {
            preparedStatement.setLong(1, questionId.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                log.error(String.format(getQuestionByIdNotFoundErr, questionId));
                throw new QuestionDoesNotExistException(String.format(getQuestionByIdNotFoundExc, questionId));
            }

            return new QuestionImpl(
                    questionId,
                    resultSet.getString(questionNameColumn),
                    QuestionType.convertToRole(resultSet.getInt(questionTypeColumn)),
                    answers
            );
        } catch (SQLException throwable) {
            log.error(GetQuestionByIdLogicErr, throwable);
            throw new DAOLogicException(GetQuestionByIdLogicExc, throwable);
        }
    }

    @Override
    public Question getQuestionByData(String questionText, BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("GET_QUESTION_BY_DATA"))) {

            preparedStatement.setString(1, questionText);
            preparedStatement.setLong(2, quizId.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                log.error(String.format(getQuestionByDataNotFoundErr, questionText, quizId));
                throw new QuestionDoesNotExistException(String.format(getQuestionByDataNotFoundExc, questionText, quizId));
            }

            return new QuestionImpl(
                    BigInteger.valueOf(resultSet.getLong(questionIdColumn)),
                    resultSet.getString(questionNameColumn),
                    QuestionType.convertToRole(resultSet.getInt(questionTypeColumn))
            );
        } catch (SQLException throwable) {
            log.error(getQuestionByDataLogicErr, throwable);
            throw new DAOLogicException(getQuestionByDataLogicExc, throwable);
        }
    }

    @Override
    public Question createQuestion(Question question, BigInteger quizId) throws QuestionDoesNotExistException, DAOLogicException {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(properties.getProperty("CREATE_QUESTION"));
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setLong(3, quizId.longValue());
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(properties.getProperty("GET_QUESTION_ID_BY_DATA"));
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setLong(2, quizId.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                log.error(String.format(createQuestionNotFoundErr, question.getQuestion(), quizId));
                throw new QuestionDoesNotExistException(
                          String.format(createQuestionNotFoundExc, question.getQuestion(), quizId));
            }

            long questionId = resultSet.getLong(questionIdColumn);
            question.setId(BigInteger.valueOf(questionId));

            return question;
        } catch (SQLException throwable) {
            log.error(createQuestionLogicErr, throwable);
            throw new DAOLogicException(createQuestionLogicExc, throwable);
        }
    }

    @Override
    public void deleteQuestion(Question question) throws QuestionDoesNotExistException, DAOLogicException {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(properties.getProperty("GET_QUESTION_ID_BY_DATA"));
            /*
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setLong(2, quizId.longValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new QuestionDoesNotExistException("deleteQuestion have not found question by questionText = " +
                        question.getQuestion() + ", quizId = " + quizId);
            }
             */

            //long questionId = resultSet.getLong(questionIdColumn);

            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(properties.getProperty("DELETE_QUESTION"));
            preparedStatement.setLong(1, question.getId().longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            log.error(deleteQuestionLogicErr, throwable);
            throw new DAOLogicException(deleteQuestionLogicExc, throwable);
        }
    }

    @Override
    public Collection<Question> getAllQuestions(BigInteger quizId) throws QuestionDoesNotExistException, DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("GET_ALL_QUESTIONS"))) {
            preparedStatement.setLong(1, quizId.longValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                log.error(String.format(getAllQuestionsNotFoundErr, quizId));
                throw new QuestionDoesNotExistException(String.format(getAllQuestionsNotFoundExc, quizId));
            }

            Collection<Question> questions = new ArrayList<>();
            while (resultSet.next()) {
                questions.add(new QuestionImpl(
                        BigInteger.valueOf(resultSet.getLong(questionIdColumn)),
                        resultSet.getString(questionNameColumn),
                        QuestionType.convertToRole(resultSet.getInt(questionTypeColumn))
                ));
            }
            return questions;
        } catch (SQLException throwable) {
            log.error(getAllQuestionsLogicErr, throwable);
            throw new DAOLogicException(getAllQuestionsLogicExc, throwable);
        }
    }

    @Override
    public void updateQuestion(Question question) throws DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("UPDATE_QUESTION"))) {
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setLong(3, question.getId().longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            log.error(updateQuestionLogicErr, throwable);
            throw new DAOLogicException(updateQuestionLogicExc, throwable);
        }
    }
}
