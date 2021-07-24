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

        Quiz quizTest = quizDAO.getQuizByTitle(title);
        log.info("Test Quiz was created");

        assertNotNull(quizTest);
        assertEquals(quiz.getTitle(), quizTest.getTitle());
        assertEquals(quiz.getDescription(), quizTest.getDescription());
        assertEquals(quiz.getQuizType(), quizTest.getQuizType());

        quizDAO.deleteQuiz(quizTest.getId());
        log.info("Test Quiz with id was deleted");

        assertNull(quizDAO.getQuizByTitle(title));
    }

    @Test
    void getQuizByIdTest() {
        BigInteger id = BigInteger.valueOf(3);
        String title = "ZNO";

        Quiz quiz = quizDAO.getQuizById(id);
        Quiz expectedQuiz = quizDAO.getQuizByTitle(title);

        assertNotNull(quiz);
        assertEquals(quiz.getTitle(), expectedQuiz.getTitle());
        assertEquals(quiz.getId().intValue(), expectedQuiz.getId().intValue());

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


        Quiz thisQuiz = quizDAO.getQuizByTitle("newOlo");
        assertNotNull(thisQuiz);
        log.info("Quiz with id " + thisQuiz.getId() + " was created");

        quizDAO.deleteQuiz(thisQuiz.getId());
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
                .setId(BigInteger.valueOf(11))
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();

        quizDAO.createQuiz(quiz);

        Quiz updatedQuiz = quizDAO.getQuizByTitle(title);
        updatedQuiz.setTitle("newTitle");

        quizDAO.updateQuiz(quiz.getId(), updatedQuiz);

        log.info("Quiz with id " + quiz.getId() + " was updated");

        assertNotEquals(quiz, updatedQuiz);

        quizDAO.deleteQuiz(quiz.getId());
        quizDAO.deleteQuiz(updatedQuiz.getId());

        assertNull(quizDAO.getQuizByTitle(title));

    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getAllQuizzesTest() {
        List<Quiz> quizList = quizDAO.getAllQuizzes();

        assertNotNull(quizList);
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuizByTitleTest() {
        Quiz quiz = quizDAO.getQuizByTitle("Testing");

        assertEquals("Testing", quiz.getTitle());
        //assertEquals(2, quiz.getId().intValue());
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuizzesByTypeTest() {
        List<Quiz> quizzes = quizDAO.getQuizzesByType(QuizType.MATHEMATICS);
        System.out.println(quizzes);
        assertNotNull(quizzes);
    }
}
