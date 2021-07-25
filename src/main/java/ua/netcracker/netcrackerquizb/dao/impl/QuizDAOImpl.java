package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
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
import java.util.List;
import java.util.Properties;

@Repository
public class QuizDAOImpl implements QuizDAO {

    private Connection connection;
    private final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(QuizDAOImpl.class);

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    private static final String URL_PROPERTY = "${spring.datasource.url}";
    private static final String USERNAME_PROPERTY = "${spring.datasource.username}";
    private static final String PASSWORD_PROPERTY = "${spring.datasource.password}";
    private static final String DRIVER_PATH = "oracle.jdbc.OracleDriver";
    private static final String PATH = "src/main/resources/sqlScripts.properties";

    private static final String ID_QUIZ = "ID_QUIZ";
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String CREATION_DATE = "CREATION_DATE";
    private static final String QUIZ_TYPE = "QUIZ_TYPE";
    private static final String CREATOR = "CREATOR";

    private static final String INSERT_INTO_QUIZ = "INSERT_INTO_QUIZ";
    private static final String GET_QUIZ_ID_BY_DATA = "GET_QUIZ_ID_BY_DATA";
    private static final String UPDATE_QUIZ = "UPDATE_QUIZ";
    private static final String DELETE_QUIZ = "DELETE_QUIZ";
    private static final String SELECT_QUIZ_BY_ID = "SELECT_QUIZ_BY_ID";
    private static final String SELECT_ALL_QUIZZES = "SELECT_ALL_QUIZZES";
    private static final String SELECT_QUIZZES_BY_TITLE = "SELECT_QUIZZES_BY_TITLE";
    private static final String SELECT_QUIZZES_BY_TYPE = "SELECT_QUIZZES_BY_TYPE";


    @Autowired
    QuizDAOImpl(
            @Value(URL_PROPERTY) String URL,
            @Value(USERNAME_PROPERTY) String USERNAME,
            @Value(PASSWORD_PROPERTY) String PASSWORD
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
        try (FileInputStream fis = new FileInputStream(PATH)) {
            Class.forName(DRIVER_PATH);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            properties.load(fis);
        } catch (IOException e) {
            log.error("Driver error " + e.getMessage());
            throw new IOException();
        } catch (ClassNotFoundException e) {
            log.error("Property file error " + e.getMessage());
            throw new ClassNotFoundException();
        } catch (SQLException e) {
            log.error("Database connection error " + e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public Quiz createQuiz(Quiz quiz) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(properties.getProperty(INSERT_INTO_QUIZ));

            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setDate(3, (Date) quiz.getCreationDate());
            preparedStatement.setLong(4, quiz.getQuizType().ordinal());
            preparedStatement.setLong(5, quiz.getCreatorId().longValue());
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(properties.getProperty(GET_QUIZ_ID_BY_DATA));
            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setLong(3, quiz.getQuizType().ordinal());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            long quizId = resultSet.getLong(ID_QUIZ);
            quiz.setId(BigInteger.valueOf(quizId));

            return quiz;

        } catch (SQLException throwables) {
            log.error("SQL Exception while createQuiz in QuizDAOImpl ", throwables);
            return null;
        }

    }


    @Override
    public void updateQuiz(Quiz quiz) {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(UPDATE_QUIZ))) {

            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setDate(3, (Date) quiz.getCreationDate());
            preparedStatement.setLong(4, quiz.getQuizType().ordinal());
            preparedStatement.setLong(5, quiz.getCreatorId().longValue());
            preparedStatement.setLong(6, quiz.getId().longValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            log.error("SQL Exception while updateQuiz in QuizDAOImpl ", throwables);
        }

    }


    @Override
    public void deleteQuiz(Quiz quiz) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(DELETE_QUIZ))) {
            preparedStatement.setLong(1, quiz.getId().longValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            log.error("SQL Exception while deleteQuiz in QuizDAOImpl ", throwables);
        }
    }


    @Override
    public Quiz getQuizById(BigInteger id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(SELECT_QUIZ_BY_ID))) {
            preparedStatement.setLong(1, id.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) return null;

            return QuizImpl.QuizBuilder()
                    .setId(id)
                    .setTitle(resultSet.getString(TITLE))
                    .setDescription(resultSet.getString(DESCRIPTION))
                    .setQuizType(QuizType.values()[resultSet.getInt(QUIZ_TYPE)])
                    .setCreationDate(resultSet.getDate(CREATION_DATE))
                    .setCreatorId(BigInteger.valueOf(resultSet.getInt(CREATOR)))
                    .build();

        } catch (SQLException throwables) {
            log.error("SQL Exception while getQuizById in QuizDAOImpl ", throwables);
            return null;
        }

    }


    @Override
    public List<Quiz> getAllQuizzes() {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(SELECT_ALL_QUIZZES))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Quiz> quizzes = new ArrayList<>();

            while (resultSet.next()) {

                Quiz quiz = QuizImpl.QuizBuilder()
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
            log.error("SQL Exception while getAllQuizzes in QuizDAOImpl ", throwables);
            return null;
        }

    }


    @Override
    public List<Quiz> getQuizzesByTitle(String title) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(SELECT_QUIZZES_BY_TITLE))) {

            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Quiz> quizzes = new ArrayList<>();

            while (resultSet.next()) {

                Quiz quiz = QuizImpl.QuizBuilder()
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
            log.error("SQL Exception while getQuizzesByTitle in QuizDAOImpl ", throwables);
            return null;
        }
    }

    @Override
    public List<Quiz> getQuizzesByType(QuizType quizType) {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(SELECT_QUIZZES_BY_TYPE))) {

            preparedStatement.setLong(1, quizType.ordinal());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Quiz> quizzes = new ArrayList<>();

            while (resultSet.next()) {
                Quiz quiz = QuizImpl.QuizBuilder()
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
            log.error("SQL Exception while getQuizzesByType in QuizDAOImpl ", throwables);

            return null;
        }
    }
}
