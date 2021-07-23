package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.model.*;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.impl.QuizImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Repository
public class QuizDAOImpl implements QuizDAO {

    private Connection connection;
    private final Properties properties = new Properties();

    @Autowired
    QuizDAOImpl(
            @Value("${spring.datasource.url}") String URL,
            @Value("${spring.datasource.username}") String USERNAME,
            @Value("${spring.datasource.password}") String PASSWORD
    )
    {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (FileInputStream fis = new FileInputStream("src/main/resources/sqlScripts.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void createQuiz(Quiz quiz) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("INSERT_INTO_QUIZ"))){

            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setDate(3, (Date) quiz.getCreationDate());
            preparedStatement.setInt(4, quiz.getQuizType().ordinal());
            preparedStatement.setInt(5, quiz.getCreatorId().intValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void updateQuiz(BigInteger id, Quiz updatedQuiz) {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("UPDATE_QUIZ"))){

            preparedStatement.setString(1, updatedQuiz.getTitle());
            preparedStatement.setString(2, updatedQuiz.getDescription());
            preparedStatement.setDate(3, (Date) updatedQuiz.getCreationDate());
            preparedStatement.setInt(4, updatedQuiz.getQuizType().ordinal());
            preparedStatement.setInt(5, updatedQuiz.getCreatorId().intValue());
            preparedStatement.setInt(6, id.intValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Override
    public void deleteQuiz(BigInteger id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("DELETE_QUIZ_BY_ID"))){
            preparedStatement.setInt(1, id.intValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public Quiz getQuizById(BigInteger id) {
        Quiz quiz = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("SELECT_QUIZ_BY_ID"))){
            preparedStatement.setInt(1, id.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return null;

            quiz = new QuizImpl();

            quiz.setId(id);
            quiz.setTitle(resultSet.getString("TITLE"));
            quiz.setDescription(resultSet.getString("DESCRIPTION"));
            quiz.setQuizType(QuizType.values()[resultSet.getInt("QUIZ_TYPE")] );
            quiz.setCreationDate(resultSet.getDate("CREATION_DATE"));
            quiz.setCreatorId(BigInteger.valueOf(resultSet.getInt("CREATOR")));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return quiz;
    }


    @Override
    public Collection<Quiz> getAllQuizzes() {
        Collection<Quiz> quizzes = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("SELECT_ALL_QUIZZES"))){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Quiz quiz = new QuizImpl();

                quiz.setId(BigInteger.valueOf(resultSet.getInt("ID_QUIZ")));
                quiz.setTitle(resultSet.getString("TITLE"));
                quiz.setDescription(resultSet.getString("DESCRIPTION"));
                quiz.setCreationDate(resultSet.getDate("CREATION_DATE"));
                quiz.setQuizType(QuizType.values()[resultSet.getInt("QUIZ_TYPE")] );
                quiz.setCreatorId(BigInteger.valueOf(resultSet.getInt("CREATOR")));

                quizzes.add(quiz);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return quizzes;
    }


    @Override
    public Quiz getQuizByTitle(String title) {
        Quiz quiz = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("SELECT_QUIZ_BY_TITLE"))){

            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return null;

            quiz = new QuizImpl();

            quiz.setId(BigInteger.valueOf(resultSet.getInt("ID_QUIZ")));
            quiz.setTitle(title);
            quiz.setDescription(resultSet.getString("DESCRIPTION"));
            quiz.setQuizType(QuizType.values()[resultSet.getInt("QUIZ_TYPE")]);
            quiz.setCreationDate(resultSet.getDate("CREATION_DATE"));
            quiz.setCreatorId(BigInteger.valueOf(resultSet.getInt("CREATOR")));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return quiz;
    }

    @Override
    public Collection<Quiz> getQuizzesByType(QuizType quizType) {

        Collection<Quiz> quizzes = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("SELECT_QUIZZES_BY_TYPE"))){

            preparedStatement.setInt(1, quizType.ordinal());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Quiz quiz = new QuizImpl();

                quiz.setId(BigInteger.valueOf(resultSet.getInt("ID_QUIZ")));
                quiz.setTitle(resultSet.getString("TITLE"));
                quiz.setDescription(resultSet.getString("DESCRIPTION"));
                quiz.setCreationDate(resultSet.getDate("CREATION_DATE"));
                quiz.setQuizType(QuizType.values()[resultSet.getInt("QUIZ_TYPE")]);
                quiz.setCreatorId(BigInteger.valueOf(resultSet.getInt("CREATOR")));

                quizzes.add(quiz);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return quizzes;
    }
}
