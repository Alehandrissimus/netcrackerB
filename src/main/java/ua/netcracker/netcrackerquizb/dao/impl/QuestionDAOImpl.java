package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

    private final String propertiesPath = "src/main/resources/sqlScripts.properties";
    private final String questionIdColumn = "id_question";
    private final String questionNameColumn = "question_name";
    private final String questionTypeColumn = "question_type";
    private final String questionQuizIdColumn = "quiz";

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
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            Class.forName("oracle.jdbc.OracleDriver");
            properties.load(fis);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException throwable) {
            log.error("Drives was not found in QuestionDAOImpl ", throwable);
            throw new ClassNotFoundException();
        } catch (SQLException throwable) {
            log.error("Error while getting SQL Connection in QuestionDAOImpl ", throwable);
            throw new SQLException();
        } catch (IOException throwable) {
            log.error("Error while loading properties in QuestionDAOImpl ", throwable);
            throw new IOException();
        }
    }


    @Override
    public Question getQuestionById(BigInteger questionId, Collection<Answer> answers) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("GET_QUESTION_BY_ID"))) {
            preparedStatement.setLong(1, questionId.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return new QuestionImpl(
                    questionId,
                    resultSet.getString(questionNameColumn),
                    QuestionType.convertToRole(resultSet.getInt(questionTypeColumn)),
                    answers
            );
        } catch (SQLException throwable) {
            log.error("SQL Exception while getQuestionById in QuestionDAOImpl ", throwable);
            return null;
        }
    }

    @Override
    public Question createQuestion(Question question, BigInteger quizId) {
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
                return null;
            }

            long questionId = resultSet.getLong(questionIdColumn);
            question.setId(BigInteger.valueOf(questionId));

            return question;
        } catch (SQLException throwable) {
            log.error("SQL Exception while createQuestion in QuestionDAOImpl ", throwable);
            return null;
        }
    }

    @Override
    public void deleteQuestion(Question question, BigInteger quizId) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(properties.getProperty("GET_QUESTION_ID_BY_DATA"));
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setLong(2, quizId.longValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return;
            }

            long questionId = resultSet.getLong(questionIdColumn);

            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(properties.getProperty("DELETE_QUESTION"));
            preparedStatement.setLong(1, questionId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            log.error("SQL Exception while deleteQuestion in QuestionDAOImpl ", throwable);
        }
    }

    @Override
    public Collection<Question> getAllQuestions(BigInteger quizId) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("GET_ALL_QUESTIONS"))) {
            preparedStatement.setLong(1, quizId.longValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
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
            log.error("SQL Exception while getAllQuestions in QuestionDAOImpl ", throwable);
            return null;
        }
    }

    @Override
    public void updateQuestion(Question question) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("UPDATE_QUESTION"))) {
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setLong(3, question.getId().longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            log.error("SQL Exception while updateQuestion in QuestionDAOImpl ", throwable);
        }
    }
}
