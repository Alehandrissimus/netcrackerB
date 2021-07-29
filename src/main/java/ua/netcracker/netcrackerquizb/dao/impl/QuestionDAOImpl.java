package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MessagesForException;
import ua.netcracker.netcrackerquizb.exception.QuestionDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;
import ua.netcracker.netcrackerquizb.util.DAOUtil;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public void setTestConnection() throws DAOConfigException {
        try {
            connection = DAOUtil.getDataSource(URL, USERNAME + "_TEST", PASSWORD, properties);
        } catch (DAOConfigException e) {
            log.error(String.format(TEST_CONNECTION_ERR, e.getMessage()));
            throw new DAOConfigException(TEST_CONNECTION_EXC, e);
        }
    }

    @Override
    public Question getQuestionById(BigInteger questionId, Collection<Answer> answers)
            throws QuestionDoesNotExistException, DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(PROPERTY_GET_QUESTION_BY_ID))) {
            preparedStatement.setLong(1, questionId.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                log.error(QUESTION_NOT_FOUND + "in QuestionDAO getQuestionById, questionId = " + questionId);
                throw new QuestionDoesNotExistException(QUESTION_NOT_FOUND + "getQuestionById");
            }

            return new QuestionImpl(
                    questionId,
                    resultSet.getString(questionNameColumn),
                    QuestionType.convertToRole(resultSet.getInt(questionTypeColumn)),
                    answers
            );
        } catch (SQLException throwable) {
            log.error(DAO_LOGIC_EXCEPTION + "in QuestionDAO getQuestionById, questionId = " + questionId);
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION + "in getQuestionById", throwable);
        }
    }

    @Override
    public Question getQuestionByData(String questionText, BigInteger quizId)
            throws DAOLogicException, QuestionDoesNotExistException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(PROPERTY_GET_QUESTION_BY_DATA))) {

            preparedStatement.setString(1, questionText);
            preparedStatement.setLong(2, quizId.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                log.error(QUESTION_NOT_FOUND + "in QuestionDAO getQuestionByData, questionText = "
                        + questionText + ", quizId = " + quizId);
                throw new QuestionDoesNotExistException(QUESTION_NOT_FOUND + "in getQuestionByData");
            }

            return new QuestionImpl(
                    BigInteger.valueOf(resultSet.getLong(questionIdColumn)),
                    resultSet.getString(questionNameColumn),
                    QuestionType.convertToRole(resultSet.getInt(questionTypeColumn))
            );
        } catch (SQLException throwable) {
            log.error(DAO_LOGIC_EXCEPTION + "in QuestionDAO getQuestionByData, questionText = "
                    + questionText + ", quizId = " + quizId, throwable);
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION + "in getQuestionByData", throwable);
        }
    }

    @Override
    public Question createQuestion(Question question, BigInteger quizId)
            throws QuestionDoesNotExistException, DAOLogicException {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(properties.getProperty(PROPERTY_CREATE_QUESTION));
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setLong(3, quizId.longValue());
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(properties.getProperty(PROPERTY_GET_QUESTION_ID_BY_DATA));
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setLong(2, quizId.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                log.error(QUESTION_NOT_FOUND + "in QuestionDAO createQuestion, questionText = "
                        + question.getQuestion() + ", quizId = " + quizId);
                throw new QuestionDoesNotExistException(QUESTION_NOT_FOUND + "in createQuestion");
            }

            long questionId = resultSet.getLong(questionIdColumn);
            question.setId(BigInteger.valueOf(questionId));

            return question;
        } catch (SQLException throwable) {
            log.error(DAO_LOGIC_EXCEPTION + "in QuestionDAO createQuestion, questionText = "
                    + question.getQuestion() + ", quizId = " + quizId, throwable);
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION + "in createQuestion", throwable);
        }
    }

    @Override
    public void deleteQuestion(Question question) throws QuestionDoesNotExistException, DAOLogicException {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(properties.getProperty(PROPERTY_GET_QUESTION_BY_ID));
            preparedStatement.setLong(1, question.getId().longValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                log.error(QUESTION_NOT_FOUND + "in QuestionDAO deleteQuestion, questionId = " + question.getId());
                throw new QuestionDoesNotExistException(QUESTION_NOT_FOUND + "in deleteQuestion");
            }

            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(properties.getProperty(PROPERTY_DELETE_QUESTION));
            preparedStatement.setLong(1, question.getId().longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            log.error(DAO_LOGIC_EXCEPTION + "in QuestionDAO deleteQuestion, questionId = " + question.getId(), throwable);
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION + "in deleteQuestion", throwable);
        }
    }

    @Override
    public Collection<Question> getAllQuestions(BigInteger quizId)
            throws QuestionDoesNotExistException, DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(PROPERTY_GET_ALL_QUESTIONS))) {
            preparedStatement.setLong(1, quizId.longValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<Question> questions = new ArrayList<>();
            while (resultSet.next()) {
                questions.add(new QuestionImpl(
                        BigInteger.valueOf(resultSet.getLong(questionIdColumn)),
                        resultSet.getString(questionNameColumn),
                        QuestionType.convertToRole(resultSet.getInt(questionTypeColumn))
                ));
            }

            if(questions.isEmpty()) {
                log.error(QUESTION_NOT_FOUND + "in QuestionDAO getAllQuestions, quizId = " + quizId);
                throw new QuestionDoesNotExistException(QUESTION_NOT_FOUND + "in getAllQuestions");
            }

            return questions;
        } catch (SQLException throwable) {
            log.error(DAO_LOGIC_EXCEPTION + "in QuestionDAO getAllQuestions, quizId = " + quizId, throwable);
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION + "in getAllQuestions", throwable);
        }
    }

    @Override
    public void updateQuestion(Question question) throws DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(PROPERTY_UPDATE_QUESTION))) {
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setLong(3, question.getId().longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            log.error(DAO_LOGIC_EXCEPTION + "in QuestionDAO updateQuestion, questionId = " + question.getId(), throwable);
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION + "in updateQuestion", throwable);
        }
    }
}
