package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

    private static final String getQuestionById = "SELECT * FROM question WHERE id_question = ?";
    private static final String getAnswersById = "SELECT * FROM answer WHERE question = ?";

    private static final String createQuestion = "INSERT INTO question VALUES(s_question.NEXTVAL, ?, ?, ?)";
    private static final String createAnswer = "INSERT INTO answer VALUES(s_answer.NEXTVAL, ?, ?, ?)";
    private static final String getCreatedId = "SELECT MAX(id_question) FROM (SELECT id_question FROM question WHERE question_name = ?";

    private static final String deleteQuestion = "DELETE FROM question WHERE id_question = ?";
    private static final String deleteAnswer = "DELETE FROM answer WHERE text = ? AND question = ?";
    private static final String getQuestionIdByData = "SELECT id_question FROM question WHERE question_name = ? AND quiz = ?";

    private static final String getAllQuestions = "SELECT * FROM question WHERE quiz = ?";

    private static final String updateQuestion = "UPDATE question SET question_name = ?, SET question_type = ? WHERE id_question = ?";

    private static Connection connection;

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
        }
    }

    // нужна коллекция answer
    @Override
    public Question getQuestionById(BigInteger id, Collection<Answer> answers) {
        Question question = new QuestionImpl();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getQuestionById);
            preparedStatement.setInt(1, id.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            question.setQuestion(resultSet.getString("question_name"));

            QuestionType[] questionTypes = QuestionType.values();
            int typeId = resultSet.getInt("question_type");
            for (QuestionType questionType : questionTypes) {
                if (questionType.ordinal() == typeId) {
                    question.setQuestionType(questionType);
                }
            }

            /*
            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(getAnswersById);
            preparedStatement.setInt(1, id.intValue());
            resultSet = preparedStatement.executeQuery();

            Collection<Answer> answers = new ArrayList<>();
            while (resultSet.next()) {
                AnswerImpl answer = new AnswerImpl();
                answer.setValue(resultSet.getString("text"));
                answer.setAnswer(resultSet.getInt("is_true") == 1);
                answers.add(answer);
            }

             */
            question.setAnswers(answers);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return question;
    }

    //need quiz id
    //после вызова createQuestion по идее в сервисе идет вызов createAnswer answerDAO
    //мб нужно вернуть id созданного question?
    @Override
    public void createQuestion(Question question, BigInteger quizId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(createQuestion);
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setInt(3, quizId.intValue());
            preparedStatement.executeUpdate();

            /*
            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(getCreatedId);
            preparedStatement.setString(1, question.getQuestion());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int questionId = resultSet.getInt("id_question");
             */

            /*
            Collection<Answer> answers = question.getAnswers();
            for(Answer answer : answers) {
                preparedStatement.clearParameters();
                preparedStatement = connection.prepareStatement(createAnswer);
                preparedStatement.setString(1, answer.getValue());
                preparedStatement.setInt(2, (answer.isTrue() ? 1 : 0));
                preparedStatement.setInt(3, questionId);
                preparedStatement.executeUpdate();
            }
             */
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //т.к. есть возможность удаления идентичного вопроса, нужен quizId
    @Override
    public void deleteQuestion(Question question, BigInteger quizId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getQuestionIdByData);
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, quizId.intValue());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int questionId = resultSet.getInt("id_question");

            /*
            Collection<Answer> answers = question.getAnswers();
            for(Answer answer: answers) {
                preparedStatement.clearParameters();
                preparedStatement = connection.prepareStatement(deleteAnswer);
                preparedStatement.setString(1, answer.getValue());
                preparedStatement.setInt(2, questionId);
                preparedStatement.executeUpdate();
            }
             */

            preparedStatement.clearParameters();
            preparedStatement = connection.prepareStatement(deleteQuestion);
            preparedStatement.setInt(1, questionId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //если будем разделять дао в сервисах, чтобы тут не был код, находяжийся в answerDAO,
    //нужно наверн вернуть в аргах коллекцию answers этого quiz
    @Override
    public Collection<Question> getAllQuestions(BigInteger quizId) {
        Collection<Question> questions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAllQuestions);
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

                /*
                preparedStatement.clearParameters();
                preparedStatement = connection.prepareStatement(getAnswersById);
                preparedStatement.setInt(1, resultSet.getInt("id_question"));
                ResultSet resultSetAnswer = preparedStatement.executeQuery();

                Collection<Answer> answers = new ArrayList<>();
                while (resultSetAnswer.next()) {
                    AnswerImpl answer = new AnswerImpl();
                    answer.setValue(resultSetAnswer.getString("text"));
                    answer.setAnswer(resultSetAnswer.getInt("is_true") == 1);
                    answers.add(answer);
                }
                 */

                questions.add(question);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return questions;
    }

    //зачем возвращать question?
    @Override
    public Question updateQuestion(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuestion);
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionType().ordinal());
            preparedStatement.setInt(3, question.getId().intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    //зачем возвращать collquestion?
    // и вообще, почему это относ. к question?
    @Override
    public Collection<Question> updateAllQuestionAnswers(Collection<Question> questions) {
        try {
            for (Question question : questions) {
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuestion);
                preparedStatement.setString(1, question.getQuestion());
                preparedStatement.setInt(2, question.getQuestionType().ordinal());
                preparedStatement.setInt(3, question.getId().intValue());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

}
