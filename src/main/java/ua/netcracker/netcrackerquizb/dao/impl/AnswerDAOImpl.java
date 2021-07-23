package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
//    private final static String GET_ANSWER_BY_ID = "SELECT * FROM answer WHERE id_answer = %d";
//    private final static String GET_ANSWER_BY_TITLE = "SELECT * FROM answer WHERE text = '%s'";
//    private final static String CREATE_ANSWER = "INSERT INTO answer VALUES(s_answer.NEXTVAL, '%s', %d, %d)";
//    private final static String DELETE_ANSWER = "DELETE answer WHERE id_answer = %d";
//    private final static String UPDATE_ANSWER =
//            "UPDATE answer SET text = '%s', is_true = %d, question = %d WHERE id_answer = %d";

    private Connection connection;
    private final Properties properties = new Properties();

    @Autowired
    AnswerDAOImpl(
            @Value("${spring.datasource.url}") String URL,
            @Value("${spring.datasource.username}") String USERNAME,
            @Value("${spring.datasource.password}") String PASSWORD
    ) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        try (FileInputStream fis = new FileInputStream("src/main/resources/sqlScripts.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Answer getAnswerById(BigInteger id) {
        Answer answer = new AnswerImpl();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(String.format(properties.getProperty("GET_ANSWER_BY_ID"), id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            answer.setValue(resultSet.getString("text"));
            answer.setAnswer(resultSet.getInt("is_true") == 1);
            answer.setQuestionId(BigInteger.valueOf(resultSet.getInt("question")));
            answer.setId(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return answer;
    }

    @Override
    public Answer getAnswerByTitle(String title) {
        Answer answer = new AnswerImpl();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement(String.format(properties.getProperty("GET_ANSWER_BY_TITLE"), title));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            answer.setId(BigInteger.valueOf(resultSet.getInt("id_answer")));
            answer.setValue(title);
            answer.setAnswer(resultSet.getInt("is_true") == 1);
            answer.setQuestionId(BigInteger.valueOf(resultSet.getInt("question")));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return answer;
    }


    @Override
    public void createAnswer(Answer answer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(
                    properties.getProperty("CREATE_ANSWER"), answer.getValue(), answer.getAnswer() ? 1 : 0, answer.getQuestionId()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void deleteAnswer(BigInteger id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(String.format(properties.getProperty("DELETE_ANSWER"), id));
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void updateAnswer(Answer answer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(
                    properties.getProperty("UPDATE_ANSWER"), answer.getValue(), answer.getAnswer() ? 1 : 0, answer.getQuestionId(), answer.getId()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
