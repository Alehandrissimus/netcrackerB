package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.*;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.impl.QuizImpl;
import ua.netcracker.netcrackerquizb.util.DAOUtil;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.*;

@Repository
public class QuizDAOImpl implements QuizDAO {

    private Connection connection;
    private final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(QuizDAOImpl.class);

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    @Autowired
    QuizDAOImpl(
            @Value(URL_PROPERTY) String URL,
            @Value(USERNAME_PROPERTY) String USERNAME,
            @Value(PASSWORD_PROPERTY) String PASSWORD
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
            log.error(ERROR_WHILE_SETTING_TEST_CONNECTION + e.getMessage());
            throw new DAOConfigException(ERROR_WHILE_SETTING_TEST_CONNECTION, e);
        }
    }


    @Override
    public Quiz createQuiz(Quiz quiz) throws DAOLogicException, UserDoesNotExistException {
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
                log.error(CREATE_QUIZ_EXCEPTION);
                throw new DAOLogicException(CREATE_QUIZ_EXCEPTION);
            }

            long quizId = resultSet.getLong(ID_QUIZ);
            quiz.setId(BigInteger.valueOf(quizId));

            return quiz;

        } catch (SQLException e) {
            log.error(CREATE_QUIZ_EXCEPTION + e.getMessage());
            throw new DAOLogicException(CREATE_QUIZ_EXCEPTION, e);
        }

    }


    @Override
    public void updateQuiz(Quiz quiz) throws QuizDoesNotExistException, DAOLogicException {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(UPDATE_QUIZ))) {

            preparedStatement.setString(1, quiz.getTitle());
            preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setDate(3, (Date) quiz.getCreationDate());
            preparedStatement.setLong(4, quiz.getQuizType().ordinal());
            preparedStatement.setLong(5, quiz.getCreatorId().longValue());
            preparedStatement.setLong(6, quiz.getId().longValue());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }

    }


    @Override
    public void deleteQuiz(Quiz quiz) throws QuizDoesNotExistException, DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(DELETE_QUIZ))) {
            preparedStatement.setLong(1, quiz.getId().longValue());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error(DELETE_QUIZ_EXCEPTION + e.getMessage());
            throw new DAOLogicException(DELETE_QUIZ_EXCEPTION, e);
        }
    }


    @Override
    public Quiz getQuizById(BigInteger id) throws QuizDoesNotExistException, DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(SELECT_QUIZ_BY_ID))) {
            preparedStatement.setLong(1, id.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                log.error(QUIZ_HAS_NOT_BEEN_RECEIVED + "in getQuizById()");
                throw new QuizDoesNotExistException(QUIZ_HAS_NOT_BEEN_RECEIVED);
            }

            return QuizImpl.QuizBuilder()
                    .setId(id)
                    .setTitle(resultSet.getString(TITLE))
                    .setDescription(resultSet.getString(DESCRIPTION))
                    .setQuizType(QuizType.values()[resultSet.getInt(QUIZ_TYPE)])
                    .setCreationDate(resultSet.getDate(CREATION_DATE))
                    .setCreatorId(BigInteger.valueOf(resultSet.getInt(CREATOR)))
                    .build();

        } catch (SQLException | QuizException e) {
            log.error(GET_QUIZ_BY_ID_EXCEPTION + e.getMessage());
            throw new DAOLogicException(GET_QUIZ_BY_ID_EXCEPTION, e);
        }

    }

    @Override
    public boolean existQuizByTitle(String title) throws DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(SELECT_QUIZ_BY_TITLE))) {

            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            log.error(DAO_LOGIC_EXCEPTION + e.getMessage());
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }
    }

    @Override
    public List<Quiz> getAllQuizzes() throws QuizDoesNotExistException, DAOLogicException {

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
        } catch (SQLException | QuizException e) {
            log.error(GET_ALL_QUIZZES_EXCEPTION + e.getMessage());
            throw new DAOLogicException(GET_ALL_QUIZZES_EXCEPTION, e);
        }

    }

    @Override
    public List<Quiz> getLastThreeCreatedQuizzes() throws DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(SELECT_LAST_THREE_CREATED_QUIZZES))) {

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
        } catch (SQLException | QuizException e) {
            log.error(GET_LAST_THREE_CREATED_QUIZZES_EXCEPTION + e.getMessage());
            throw new DAOLogicException(GET_LAST_THREE_CREATED_QUIZZES_EXCEPTION, e);
        }
    }

    @Override
    public Quiz getQuizByTitle(String title) throws QuizDoesNotExistException, DAOLogicException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(properties.getProperty(SELECT_QUIZ_BY_TITLE))) {

            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                log.error(QUIZ_HAS_NOT_BEEN_RECEIVED + "in getQuizByTitle()");
                throw new QuizDoesNotExistException(QUIZ_HAS_NOT_BEEN_RECEIVED);
            }

            return QuizImpl.QuizBuilder()
                    .setId(BigInteger.valueOf(resultSet.getLong(ID_QUIZ)))
                    .setTitle(resultSet.getString(TITLE))
                    .setDescription(resultSet.getString(DESCRIPTION))
                    .setQuizType(QuizType.values()[resultSet.getInt(QUIZ_TYPE)])
                    .setCreationDate(resultSet.getDate(CREATION_DATE))
                    .setCreatorId(BigInteger.valueOf(resultSet.getInt(CREATOR)))
                    .build();

        } catch (SQLException | QuizException e) {
            log.error(GET_QUIZ_BY_TITLE_EXCEPTION + e.getMessage());
            throw new DAOLogicException(GET_QUIZ_BY_TITLE_EXCEPTION, e);
        }
    }

    @Override
    public List<Quiz> getQuizzesByType(QuizType quizType) throws QuizDoesNotExistException, DAOLogicException {

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

        } catch (SQLException | QuizException e) {
            log.error(GET_QUIZZES_BY_TYPE_EXCEPTION + e.getMessage());
            throw new DAOLogicException(GET_QUIZZES_BY_TYPE_EXCEPTION, e);
        }
    }
}
