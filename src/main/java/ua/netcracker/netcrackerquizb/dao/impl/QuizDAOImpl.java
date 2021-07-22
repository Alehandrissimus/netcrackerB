package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.model.*;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuizDAOImpl implements QuizDAO {


    //done
    private final static String SQL_SELECT_QUIZ_BY_TYPE = "SELECT * FROM QUIZ JOIN QUIZ_TYPE" +
            " ON QUIZ.QUIZ_TYPE = QUIZ_TYPE.ID_QUIZ_TYPE AND QUIZ_TYPE.QUIZ_TYPE = ?";

    //done
    private final static String SQL_SELECT_ALL_QUIZZES = "SELECT * FROM QUIZ";


    private final static String SQL_SELECT_BY_ID = "SELECT * FROM QUIZ WHERE ID_QUIZ=?";

    //done
    private final static String SQL_SELECT_BY_TITLE = "SELECT * FROM QUIZ WHERE title=?";

    //done
    private final static String SQL_UPDATE_QUIZ = "UPDATE QUIZ SET TITLE=?,  " +
            "CREATION_DATE=?, QUIZ_TYPE=?, CREATOR=? WHERE ID_QUIZ=?";

    //done
    private final static String SQL_INSERT_INTO_QUIZ = "INSERT INTO QUIZ VALUES(s_quiz.NEXTVAL, ?, ?, ?, ?)";


    //FOREIGN KEY!
    private final static String SQL_DELETE_QUIZ = "DELETE FROM QUIZ WHERE ID_QUIZ=?";

    private static Connection connection;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

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


    //done
    @Override
    public void createQuiz(Quiz quiz) {
        try {

            preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_QUIZ);
            preparedStatement.setString(1, quiz.getTitle());
            //preparedStatement.setString(2, quiz.getDescription());
            preparedStatement.setDate(2, (Date) quiz.getCreationDate());
            preparedStatement.setInt(3, quiz.getQuizType().ordinal());
            preparedStatement.setInt(4, quiz.getCreatorId().intValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //done
    @Override
    public void updateQuiz(BigInteger id, Quiz updatedQuiz) {

        try {
            preparedStatement = connection.prepareStatement(SQL_UPDATE_QUIZ);

            preparedStatement.setString(1, updatedQuiz.getTitle());
            //preparedStatement.setString(2, updatedQuiz.getDescription());
            preparedStatement.setDate(2, (Date) updatedQuiz.getCreationDate());
            preparedStatement.setInt(3, updatedQuiz.getQuizType().ordinal());
            preparedStatement.setInt(4, updatedQuiz.getCreatorId().intValue());
            preparedStatement.setInt(5, id.intValue());


            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    //done?
    @Override
    public boolean deleteQuiz(Quiz quiz) {
        try {
            preparedStatement = connection.prepareStatement(SQL_DELETE_QUIZ);
            preparedStatement.setInt(1, quiz.getId().intValue());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    //done
    @Override
    public Quiz getQuizById(BigInteger id) {
        Quiz quiz = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id.intValue());
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            quiz = new Quiz();

            quiz.setId(BigInteger.valueOf(resultSet.getInt("ID_QUIZ")));
            quiz.setTitle(resultSet.getString("TITLE"));
            //quiz.setDescription(resultSet.getString("DESCRIPTION"));
            quiz.setQuizType(QuizType.values()[resultSet.getInt("QUIZ_TYPE")] );
            quiz.setCreationDate(resultSet.getDate("CREATION_DATE"));
            quiz.setCreatorId(BigInteger.valueOf(resultSet.getInt("CREATOR")));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return quiz;
    }

    //done
    @Override
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_QUIZZES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Quiz quiz = new Quiz();

                quiz.setId(BigInteger.valueOf(resultSet.getInt("ID_QUIZ")));
                quiz.setTitle(resultSet.getString("TITLE"));
                //quiz.setDescription(resultSet.getString("DESCRIPTION"));
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

    //done
    @Override
    public Quiz getQuizByTitle(String title) {
        Quiz quiz = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_TITLE);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            quiz = new Quiz();

            quiz.setId(BigInteger.valueOf(resultSet.getInt("ID_QUIZ")));
            quiz.setTitle(resultSet.getString("TITLE"));
            //quiz.setDescription(resultSet.getString("DESCRIPTION"));
            quiz.setQuizType(QuizType.values()[resultSet.getInt("QUIZ_TYPE")]);
            quiz.setCreationDate(resultSet.getDate("CREATION_DATE"));
            quiz.setCreatorId(BigInteger.valueOf(resultSet.getInt("CREATOR")));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return quiz;
    }

    //done
    @Override
    public List<Quiz> getQuizzesByType(QuizType quizType) {

        List<Quiz> quizzes = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_QUIZ_BY_TYPE);

            preparedStatement.setString(1, quizType.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                quizzes.add(new Quiz(
                        resultSet.getString("TITLE"),
                        //resultSet.getString("DESCRIPTION"),
                        QuizType.values()[resultSet.getInt("QUIZ_TYPE")],
                        resultSet.getDate("CREATION_DATE"),
                        BigInteger.valueOf(resultSet.getInt("CREATOR"))
                ));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return quizzes;
    }


}

