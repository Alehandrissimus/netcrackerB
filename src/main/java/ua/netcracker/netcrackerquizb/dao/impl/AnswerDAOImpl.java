package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.AnswerDAO;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.Properties;

@Repository
public class AnswerDAOImpl implements AnswerDAO {

    private Connection connection;
    private final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(AnswerDAOImpl.class);
    private final int SQL_TRUE = 1;
    private final int SQL_FALSE = 0;
    private static final String PROPERTIESPATH = "src/main/resources/sqlScripts.properties";

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    @Autowired
    AnswerDAOImpl(
            @Value("${spring.datasource.url}") String URL,
            @Value("${spring.datasource.username}") String USERNAME,
            @Value("${spring.datasource.password}") String PASSWORD
    ) throws SQLException, ClassNotFoundException, IOException {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;

        getDataSource(URL, USERNAME, PASSWORD);
    }

    public void setTestConnection() throws SQLException, ClassNotFoundException, IOException {
        getDataSource(URL, USERNAME + "_TEST", PASSWORD);
    }

    private void getDataSource(String URL, String USERNAME, String PASSWORD)
            throws SQLException, ClassNotFoundException, IOException {
        try (FileInputStream fis = new FileInputStream(PROPERTIESPATH)) {
            Class.forName("oracle.jdbc.OracleDriver");
            properties.load(fis);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException throwable) {
            log.error("Drives was not found in AnswerDAOImpl ", throwable);
            throw new ClassNotFoundException();
        } catch (SQLException throwable) {
            log.error("Error while getting SQL Connection in AnswerDAOImpl ", throwable);
            throw new SQLException();
        } catch (IOException throwable) {
            log.error("Error while loading properties in AnswerDAOImpl ", throwable);
            throw new IOException();
        }
    }

    //ругается на возвращение null!!!
    @Override
    public Answer getAnswerById(BigInteger id) {
        try  {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(properties.getProperty("GET_ANSWER_BY_ID"));
            preparedStatement.setLong(1, id.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            return new AnswerImpl(
                    id,
                    resultSet.getString(SQL_ANSWER_TEXT),
                    resultSet.getInt(SQL_ANSWER_IS_TRUE) == SQL_TRUE,
                    BigInteger.valueOf(resultSet.getInt(SQL_ANSWER_QUESTION)));
        } catch (SQLException throwable) {
            log.error("SQL Exception while getAnswerById in AnswerDAOImpl ", throwable);
            return null;
        }

    }

    @Override
    public BigInteger getLastAnswerIdByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("GET_LAST_ANSWER_ID_BY_TITLE"));
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            return BigInteger.valueOf(resultSet.getInt(SQL_MAX_ID_ANSWER));
        } catch (SQLException throwable) {
            log.error("SQL Exception while getLastAnswerIdByTitle in AnswerDAOImpl ", throwable);
            return null;
        }

    }

    @Override
    public BigInteger createAnswer(Answer answer) {
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
            return null;
        }
    }

    @Override
    public void deleteAnswer(BigInteger id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("DELETE_ANSWER"));
            preparedStatement.setLong(1, id.longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            log.error("SQL Exception while deleteAnswer in AnswerDAOImpl ", throwable);
        }
    }

    @Override
    public BigInteger updateAnswer(Answer answer) {
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
            return null;
        }
    }
}
