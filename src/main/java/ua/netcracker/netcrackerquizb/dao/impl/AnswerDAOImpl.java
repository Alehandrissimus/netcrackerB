package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.AnswerDAO;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;

import java.math.BigInteger;
import java.sql.*;

@Repository
public class AnswerDAOImpl implements AnswerDAO {
    private final static String SQL_GET_ANSWER_BY_ID = "SELECT * FROM answer WHERE id_answer = %d";
    private final static String SQL_GET_ANSWER_BY_TITLE = "SELECT * FROM answer WHERE text = '%s'";
    private final static String SQL_CREATE_ANSWER = "INSERT INTO answer VALUES(s_answer.NEXTVAL, '%s', %d, %d)";
    private final static String SQL_DELETE_ANSWER = "DELETE answer WHERE id_answer = %d";
    private final static String SQL_UPDATE_ANSWER =
            "UPDATE answer SET text = '%s', is_true = %d, question = %d WHERE id_answer = %d";

    private static Connection connection;

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
    }

    @Override
    public Answer getAnswerById(BigInteger id) {
        Answer answer = new AnswerImpl();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(SQL_GET_ANSWER_BY_ID, id));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
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
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(SQL_GET_ANSWER_BY_TITLE, title));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
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
                    SQL_CREATE_ANSWER, answer.getValue(), answer.getAnswer() ? 1 : 0, answer.getQuestionId()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void deleteAnswer(BigInteger id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(SQL_DELETE_ANSWER, id));
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void updateAnswer(Answer answer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(
                    SQL_UPDATE_ANSWER, answer.getValue(), answer.getAnswer() ? 1 : 0, answer.getQuestionId(), answer.getId()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
