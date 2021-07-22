package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.model.*;
import ua.netcracker.netcrackerquizb.model.enums.QuizType;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuizDAOImpl implements QuizDAO {

    private final static String SQL_SELECT_QUIZ_BY_TYPE = "SELECT * FROM QUIZ WHERE QUIZ_TYPE=? ORDER_BY(ID_QUIZ)";
    private final static String SQL_SELECT_ALL_QUIZZES = "SELECT * FROM QUIZ";
    private final static String SQL_SELECT_BY_ID = "SELECT * FROM QUIZ WHERE ID_QUIZ=?";
    private final static String SQL_SELECT_BY_TITLE = "SELECT * FROM QUIZ WHERE TITLE=?";


    private final static String SQL_UPDATE_QUIZ = "UPDATE QUIZ SET TITLE=?, DESCRIPTION=?, " +
            "CREATION_DATE=?, QUIZ_TYPE=?, CREATOR=? WHERE ID_QUIZ=?";

    private final static String SQL_INSERT_INTO_QUIZ = "INSERT INTO QUIZ VALUES(s_quiz.NEXTVAL, ?, ?, ?, ?, ?)";

    private final static String SQL_DELETE_QUIZ = "DELETE FROM QUIZ WHERE ID_QUIZ=?";

    private Connection connection;

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
    }


    @Override
    public void createQuiz(Quiz quiz) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_QUIZ)){

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

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_QUIZ)){

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_QUIZ)){
            preparedStatement.setInt(1, id.intValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public Quiz getQuizById(BigInteger id) {
        Quiz quiz = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)){
            preparedStatement.setInt(1, id.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();


            if(!resultSet.next()) return null;

            quiz = new Quiz();

            quiz.setId(BigInteger.valueOf(resultSet.getInt("ID_QUIZ")));
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
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_QUIZZES)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Quiz quiz = new Quiz();

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_TITLE)){

            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return null;

            quiz = new Quiz();

            quiz.setId(BigInteger.valueOf(resultSet.getInt("ID_QUIZ")));
            quiz.setTitle(resultSet.getString("TITLE"));
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
    public List<Quiz> getQuizzesByType(QuizType quizType) {

        List<Quiz> quizzes = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_QUIZ_BY_TYPE)){

//            int typeNumber;
//            switch (quizType) {
//                case HISTORIC: typeNumber = 0; break;
//                case SCIENCE: typeNumber = 1; break;
//                case GEOGRAPHICAL: typeNumber = 2; break;
//                case MATHEMATICS: typeNumber = 3; break;
//                default: typeNumber = 0;
//            }

            preparedStatement.setInt(1, quizType.ordinal());


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Quiz quiz = new Quiz();

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
