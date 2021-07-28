package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.AnswerDAO;
import ua.netcracker.netcrackerquizb.exception.AnswerDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MessagesForException;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;
import ua.netcracker.netcrackerquizb.util.DAOUtil;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

@Repository
public class AnswerDAOImpl implements AnswerDAO, MessagesForException {

    private Connection connection;
    private final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(AnswerDAOImpl.class);
    private final int SQL_TRUE = 1;
    private final int SQL_FALSE = 0;

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    @Autowired
    AnswerDAOImpl(
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
            throw new DAOConfigException(testConnectionErrorWithoutStringFormat, e);
        }
    }

    @Override
    public Answer getAnswerById(BigInteger answerId) throws DAOLogicException, AnswerDoesNotExistException {
        try  {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(properties.getProperty("GET_ANSWER_BY_ID"));
            preparedStatement.setLong(1, answerId.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                log.error(String.format(getAnswerByIdNotFoundErr, answerId));
                throw new AnswerDoesNotExistException(String.format(getAnswerByIdNotFoundExc, answerId));
            }
            return new AnswerImpl(
                    answerId,
                    resultSet.getString(SQL_ANSWER_TEXT),
                    resultSet.getInt(SQL_ANSWER_IS_TRUE) == SQL_TRUE,
                    BigInteger.valueOf(resultSet.getLong(SQL_ANSWER_QUESTION)));
        } catch (SQLException throwable) {
            log.error(getAnswerByIdLogicErr, throwable);
            throw new DAOLogicException(String.format(getAnswerByIdLogicExc ,answerId), throwable);
        }

    }

    @Override
    public BigInteger getLastAnswerIdByTitle(String title) throws DAOLogicException, AnswerDoesNotExistException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("GET_LAST_ANSWER_ID_BY_TITLE"));
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                log.error(String.format(getLastAnswerIdByTitleNotFoundErr, title));
                throw new AnswerDoesNotExistException(String.format(getLastAnswerIdByTitleNotFoundExc, title));
            }
            return BigInteger.valueOf(resultSet.getLong(SQL_MAX_ID_ANSWER));
        } catch (SQLException throwable) {
            log.error("SQL Exception while getLastAnswerIdByTitle in AnswerDAOImpl ", throwable);
            throw new DAOLogicException("SQL Exception while getLastAnswerIdByTitle with title = " + title, throwable);
        }
    }

    @Override
    public BigInteger createAnswer(Answer answer) throws DAOLogicException, AnswerDoesNotExistException {
        try {
            String title = answer.getValue();
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("CREATE_ANSWER"));
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, answer.getAnswer() ? SQL_TRUE : SQL_FALSE);
            preparedStatement.setLong(3, answer.getQuestionId().longValue());
            preparedStatement.executeUpdate();
            return getLastAnswerIdByTitle(title);
        } catch (SQLException throwable) {
            log.error("SQL Exception while createAnswer in AnswerDAOImpl ", throwable);
            throw new DAOLogicException("SQL Exception while createAnswer in AnswerDAOImpl", throwable);
        }
    }

    @Override
    public void deleteAnswer(BigInteger id) throws DAOLogicException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("DELETE_ANSWER"));
            preparedStatement.setLong(1, id.longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            log.error("SQL Exception while deleteAnswer in AnswerDAOImpl ", throwable);
            throw new DAOLogicException("SQL Exception while deleteAnswer in AnswerDAOImpl", throwable);
        }
    }

    @Override
    public BigInteger updateAnswer(Answer answer) throws DAOLogicException {
        try {
            BigInteger id = answer.getId();
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("UPDATE_ANSWER"));
            preparedStatement.setString(1, answer.getValue());
            preparedStatement.setInt(2, answer.getAnswer() ? SQL_TRUE : SQL_FALSE);
            preparedStatement.setLong(3, answer.getQuestionId().longValue());
            preparedStatement.setLong(4, id.longValue());
            preparedStatement.executeUpdate();
            return id;
        } catch (SQLException throwable) {
            log.error("SQL Exception while updateAnswer in AnswerDAOImpl ", throwable);
            throw new DAOLogicException("SQL Exception while updateAnswer in AnswerDAOImpl", throwable);
        }
    }

    @Override
    public Collection<Answer> getAnswersByQuestionId(BigInteger questionId) throws DAOLogicException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("GET_ANSWERS_BY_QUESTION_ID"));
            preparedStatement.setLong(1, questionId.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<Answer> answers = new ArrayList<>();

            while (resultSet.next()) {
                answers.add(new AnswerImpl(
                        BigInteger.valueOf(resultSet.getLong(SQL_ANSWER_ID)),
                        resultSet.getString(SQL_ANSWER_TEXT),
                        resultSet.getInt(SQL_ANSWER_IS_TRUE) == SQL_TRUE,
                        questionId));
            }
            return answers;
        } catch (SQLException throwable) {
            log.error("SQL Exception while getAnswersByQuestionId in AnswerDAOImpl ", throwable);
            throw new DAOLogicException("SQL Exception while getAnswersByQuestionId in AnswerDAOImpl", throwable);
        }
    }


}
