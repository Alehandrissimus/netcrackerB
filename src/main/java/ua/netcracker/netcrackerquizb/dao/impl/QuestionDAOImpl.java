package ua.netcracker.netcrackerquizb.dao.impl;

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
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

    private Connection connection;
    private final Properties properties = new Properties();

    @Autowired
    QuestionDAOImpl(
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } try (FileInputStream fis = new FileInputStream("src/main/resources/sqlScripts.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Question getQuestionById(BigInteger id, Collection<Answer> answers) {
        Question question = new QuestionImpl();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("GET_QUESTION_BY_ID"));
            preparedStatement.setInt(1, id.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;

            question.setQuestion(resultSet.getString("question_name"));

            QuestionType[] questionTypes = QuestionType.values();
            int typeId = resultSet.getInt("question_type");
            for (QuestionType questionType : questionTypes) {
                if (questionType.ordinal() == typeId) {
                    question.setQuestionType(questionType);
                }
            }
            question.setId(BigInteger.valueOf(resultSet.getInt("id_question")));
            question.setAnswers(answers);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return question;
    }

    @Override
    public void createQuestion(Question question, BigInteger quizId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("CREATE_QUESTION"));
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setInt(3, quizId.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteQuestion(Question question, BigInteger quizId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("GET_QUESTION_ID_BY_DATA"));
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, quizId.intValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return;

            int questionId = resultSet.getInt("id_question");

            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(properties.getProperty("DELETE_QUESTION"));
            preparedStatement.setInt(1, questionId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Collection<Question> getAllQuestions(BigInteger quizId) {
        Collection<Question> questions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("GET_ALL_QUESTIONS"));
            preparedStatement.setInt(1, quizId.intValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Question question = new QuestionImpl();
                question.setQuestion(resultSet.getString("question_name"));
                QuestionType[] questionTypes = QuestionType.values();
                int typeId = resultSet.getInt("question_type");
                for (QuestionType questionType : questionTypes) {
                    if (questionType.ordinal() == typeId) {
                        question.setQuestionType(questionType);
                    }
                }

                questions.add(question);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return questions;
    }

    @Override
    public void updateQuestion(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty("UPDATE_QUESTION"));
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setInt(3, question.getId().intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
