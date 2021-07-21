package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.netcracker.netcrackerquizb.dao.AnswerDAO;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;

import java.math.BigInteger;
import java.sql.*;

public class AnswerDAOImpl implements AnswerDAO {
    private final static String SQL_GET_ANSWER_BY_ID = "SELECT * FROM answer WHERE id_answer = %d";
    private final static String SQL_CREATE_ANSWER = "INSERT INTO answer VALUES(s_answer.NEXTVAL, %s, %d, %d)";
    private final static String SQL_DELETE_ANSWER = "DELETE answer WHERE id_answer = %d";
    private final static String SQL_UPDATE_ANSWER =
            "UPDATE answer SET text = %s, is_true = %d, question = %d WHERE id_answer = %d";

    private final static String URL = "";
    private final static String USERNAME = "";
    private final static String PASSWORD = "";

    private static Connection connection;

    static {
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

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

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
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return answer;
        //return jdbcTemplate.queryForObject(String.format(SQL_GET_ANSWER_BY_ID, id), Answer.class);
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
        //jdbcTemplate.update(String.format(SQL_CREATE_ANSWER, answer.getValue(), answer.getAnswer(), questionId));
    }

    @Override
    public void deleteAnswer(Answer answer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(SQL_DELETE_ANSWER, answer.getId()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        //jdbcTemplate.update(String.format(SQL_DELETE_ANSWER, answer.getId()));
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
        //return jdbcTemplate.queryForObject(String.format(SQL_UPDATE_ANSWER, answer.getValue(), answer.getAnswer(), questionId, answer.getId()), Answer.class);
    }
}
