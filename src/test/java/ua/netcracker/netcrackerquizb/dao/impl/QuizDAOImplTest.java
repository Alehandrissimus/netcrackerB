package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.builders.QuizBuilder;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizDAOImplTest {

    private QuizDAOImpl quizDAO;
    private static final Logger log = Logger.getLogger(QuizDAOImplTest.class);

    @Autowired
    private void setQuizDAO(QuizDAOImpl quizDAO) {
        this.quizDAO = quizDAO;
        try {
            quizDAO.setTestConnection();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error("Error while setting test connection " + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void createQuizTest() {

        User user = new UserImpl();
        user.setId(BigInteger.valueOf(1));

        String title = "History Quiz";
        String description = "Historical quiz";
        Quiz quiz = QuizBuilder.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();

        quizDAO.createQuiz(quiz);

        log.info("Test Quiz was created");

        Quiz quizTest = quizDAO.getQuizById(quiz.getId());

        assertNotNull(quizTest);
        assertEquals(quiz.getTitle(), quizTest.getTitle());
        assertEquals(quiz.getDescription(), quizTest.getDescription());
        assertEquals(quiz.getQuizType(), quizTest.getQuizType());

        quizDAO.deleteQuiz(quizTest);
        log.info("Test Quiz with id was deleted");

        assertNull(quizDAO.getQuizByTitle(title));
    }

    @Test
    void getQuizByIdTest() {
        User user = new UserImpl();
        user.setId(BigInteger.valueOf(1));

        String title = "ZNO";
        String description = "Historical quiz";
        Quiz quiz = QuizBuilder.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();

        quizDAO.createQuiz(quiz);
        BigInteger id = BigInteger.valueOf(3);

        Quiz newQuiz = quizDAO.getQuizById(quiz.getId());
        Quiz expectedQuiz = quizDAO.getQuizByTitle(title);

        assertNotNull(quiz);
        assertEquals(expectedQuiz.getTitle(), newQuiz.getTitle());
        assertEquals(expectedQuiz.getId().intValue(), newQuiz.getId().intValue());

        log.info("Quiz was found by id: " + id);
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void deleteQuizTest() {

//        Quiz quiz1 = quizDAO.getQuizById(BigInteger.valueOf(128));
//        quizDAO.deleteQuiz(quiz1.getId());


        User user = new UserImpl();
        user.setId(BigInteger.valueOf(1));

        String title = "newOlo";
        String description = "Horror quiz";
        Quiz quiz = QuizBuilder.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();


        quizDAO.createQuiz(quiz);
        log.info("Quiz with id " + quiz.getId() + " was created");

        Quiz thisQuiz = quizDAO.getQuizByTitle(title);
        assertNotNull(thisQuiz);


        quizDAO.deleteQuiz(thisQuiz);
        log.info("Quiz with id " + thisQuiz.getId() + " was deleted");
        assertNull(quizDAO.getQuizByTitle("newOlo"));

    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void updateQuizTest() {

        User user = new UserImpl();
        user.setId(BigInteger.valueOf(1));

        String title = "Science quiz";
        String description = "Science quiz";
        Quiz quiz = QuizBuilder.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();

        quizDAO.createQuiz(quiz);

        Quiz updatedQuiz = quizDAO.getQuizByTitle(title);
        updatedQuiz.setTitle("newTitle");

        quizDAO.updateQuiz(updatedQuiz);

        log.info("Quiz with id " + quiz.getId() + " was updated");

        assertNotEquals(quiz, updatedQuiz);

        quizDAO.deleteQuiz(quiz);
        quizDAO.deleteQuiz(updatedQuiz);

        assertNull(quizDAO.getQuizByTitle(title));

    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getAllQuizzesTest() {
        User user = new UserImpl();
        user.setId(BigInteger.valueOf(1));

        String title = "Science quiz";
        String description = "Science quiz";
        Quiz quiz = QuizBuilder.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();

        quizDAO.createQuiz(quiz);

        List<Quiz> quizList = quizDAO.getAllQuizzes();

        log.info("Get all quizzes in test");
        assertNotNull(quizList);

        quizDAO.deleteQuiz(quiz);
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuizByTitleTest() {
        String expectedTitle = "Testing";
        Quiz quiz = quizDAO.getQuizByTitle("Testing");

        if (quiz.getId() != null) {
            assertEquals(expectedTitle, quiz.getTitle());
        }

    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuizzesByTypeTest() {
        List<Quiz> quizzes = quizDAO.getQuizzesByType(QuizType.MATHEMATICS);

        if(!quizzes.isEmpty()) {
            assertEquals(QuizType.MATHEMATICS, quizzes.get(0).getQuizType());
        }

    }
}
