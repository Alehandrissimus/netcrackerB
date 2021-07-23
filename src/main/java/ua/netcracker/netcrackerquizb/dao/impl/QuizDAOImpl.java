package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.model.*;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.builders.QuizBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class QuizDAOImpl implements QuizDAO {

    private Connection connection;
    private final Properties properties = new Properties();

    private static final String ID_QUIZ = "ID_QUIZ";
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String CREATION_DATE = "CREATION_DATE";
    private static final String QUIZ_TYPE = "QUIZ_TYPE";
    private static final String CREATOR = "CREATOR";
    private static final String PATH = "src/main/resources/sqlScripts.properties";

    @Autowired
    QuizDAOImpl(
            @Value("${spring.datasource.url}") String URL,
            @Value("${spring.datasource.username}") String USERNAME,
            @Value("${spring.datasource.password}") String PASSWORD
    )
    {
        try (FileInputStream fis = new FileInputStream(PATH)) {
            properties.load(fis);

            Class.forName("oracle.jdbc.OracleDriver");

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BigInteger createQuiz(Quiz quiz) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("INSERT_INTO_QUIZ"))){

            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setDate(3, (Date) quiz.getCreationDate());
            preparedStatement.setInt(4, quiz.getQuizType().ordinal());
            preparedStatement.setInt(5, quiz.getCreatorId().intValue());

            preparedStatement.executeUpdate();

            return quiz.getId();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
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
            preparedStatement.setLong(6, id.longValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Override
    public void deleteQuiz(BigInteger id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("DELETE_QUIZ_BY_ID"))){
            preparedStatement.setLong(1, id.longValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public Quiz getQuizById(BigInteger id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("SELECT_QUIZ_BY_ID"))){
            preparedStatement.setLong(1, id.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return null;

            return QuizBuilder.newBuilder()
                    .setId(id)
                    .setTitle(resultSet.getString(TITLE))
                    .setDescription(resultSet.getString(DESCRIPTION))
                    .setQuizType(QuizType.values()[resultSet.getInt(QUIZ_TYPE)])
                    .setCreationDate(resultSet.getDate(CREATION_DATE))
                    .setCreatorId(BigInteger.valueOf(resultSet.getInt(CREATOR)))
                    .build();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }


    @Override
    public List<Quiz> getAllQuizzes() {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("SELECT_ALL_QUIZZES"))){

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Quiz> quizzes = new ArrayList<>();

            while (resultSet.next()) {

                Quiz quiz = QuizBuilder.newBuilder()
                        .setId(BigInteger.valueOf(resultSet.getLong(ID_QUIZ)))
                        .setTitle(resultSet.getString(TITLE))
                        .setDescription(resultSet.getString(DESCRIPTION))
                        .setQuizType(QuizType.values()[resultSet.getInt(QUIZ_TYPE)])
                        .setCreationDate(resultSet.getDate(CREATION_DATE))
                        .setCreatorId(BigInteger.valueOf(resultSet.getInt(CREATOR)))
                        .build();

                quizzes.add(quiz);
            }

            return quizzes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }


    @Override
    public Quiz getQuizByTitle(String title) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("SELECT_QUIZ_BY_TITLE"))){

            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return null;

            return QuizBuilder.newBuilder()
                    .setId(BigInteger.valueOf(resultSet.getLong(ID_QUIZ)))
                    .setTitle(title)
                    .setDescription(resultSet.getString(DESCRIPTION))
                    .setQuizType(QuizType.values()[resultSet.getInt(QUIZ_TYPE)])
                    .setCreationDate(resultSet.getDate(CREATION_DATE))
                    .setCreatorId(BigInteger.valueOf(resultSet.getInt(CREATOR)))
                    .build();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Quiz> getQuizzesByType(QuizType quizType) {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty("SELECT_QUIZZES_BY_TYPE"))){

            preparedStatement.setInt(1, quizType.ordinal());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Quiz> quizzes = new ArrayList<>();

            while (resultSet.next()) {
                Quiz quiz = QuizBuilder.newBuilder()
                        .setId(BigInteger.valueOf(resultSet.getLong(ID_QUIZ)))
                        .setTitle(resultSet.getString(TITLE))
                        .setDescription(resultSet.getString(DESCRIPTION))
                        .setQuizType(QuizType.values()[resultSet.getInt(QUIZ_TYPE)])
                        .setCreationDate(resultSet.getDate(CREATION_DATE))
                        .setCreatorId(BigInteger.valueOf(resultSet.getInt(CREATOR)))
                        .build();

                quizzes.add(quiz);
            }

            return quizzes;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
