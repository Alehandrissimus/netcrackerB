package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.QuizImpl;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.math.BigInteger;
import java.sql.Date;
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
        } catch (DAOConfigException e) {
            log.error("Error while setting test connection " + e.getMessage());
            fail();
        }
    }


    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void createQuizTest() {
        try {
            User user = new UserImpl.UserBuilder()
                    .setId(BigInteger.valueOf(1))
                    .build();

            String title = "History Quiz";
            String description = "Historical quiz";
            QuizType quizType = QuizType.HISTORIC;
            Quiz quiz = QuizImpl.QuizBuilder()
                    .setTitle(title)
                    .setDescription(description)
                    .setQuizType(quizType)
                    .setCreationDate(new Date(System.currentTimeMillis()))
                    .setCreatorId(user.getId())
                    .build();

            Quiz newQuiz = quizDAO.createQuiz(quiz);
            log.info("Quiz with id " + newQuiz.getId() + " was created");

            assertNotNull(newQuiz);
            assertEquals(title, newQuiz.getTitle());

            quizDAO.deleteQuiz(newQuiz);
            log.info("Test Quiz with id: " + newQuiz.getId() + " was deleted");
        } catch (QuizDoesNotExistException | UserDoesNotExistException | DAOLogicException | QuizException e) {
            log.error("Error while testing createQuiz ", e);
            fail();
        }


    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuizByIdTest() {
        try {

            Quiz quiz = quizDAO.getQuizById(BigInteger.valueOf(1));

            assertNotNull(quiz);
            assertEquals(1, quiz.getId().intValue());

            log.info("Quiz was found by id: " + quiz.getId());

        } catch (QuizDoesNotExistException | DAOLogicException e) {
            log.error("Error while testing getQuizById ", e);
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void deleteQuizTest() {

        try {
            User user = new UserImpl.UserBuilder()
                    .setId(BigInteger.valueOf(1))
                    .build();

            String title = "newOlo";
            String description = "Horror quiz";
            QuizType quizType = QuizType.SCIENCE;
            Quiz quiz = QuizImpl.QuizBuilder()
                    .setTitle(title)
                    .setDescription(description)
                    .setQuizType(quizType)
                    .setCreationDate(new Date(System.currentTimeMillis()))
                    .setCreatorId(user.getId())
                    .build();

            Quiz newQuiz = quizDAO.createQuiz(quiz);
            log.info("Quiz with id " + newQuiz.getId() + " was created");

            assertEquals(title, newQuiz.getTitle());

            quizDAO.deleteQuiz(newQuiz);
            log.info("Quiz with id " + newQuiz.getId() + " was deleted");


        } catch (QuizDoesNotExistException | UserDoesNotExistException | DAOLogicException | QuizException e) {
            log.error("Error while testing deleteQuiz ", e);
            fail();
        }

    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void updateQuizTest() {
        try {
            Quiz quiz = quizDAO.getQuizById(BigInteger.valueOf(1));
            Quiz updatedQuiz = quizDAO.getQuizById(quiz.getId());

            updatedQuiz.setCreationDate(new Date(System.currentTimeMillis()));

            quizDAO.updateQuiz(updatedQuiz);
            log.info("Quiz with id " + updatedQuiz.getId() + " was updated");

            assertNotEquals(quiz.getCreationDate(), updatedQuiz.getCreationDate());

        } catch (QuizDoesNotExistException | DAOLogicException e) {
            log.error("Error while testing updateQuiz ", e);
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getAllQuizzesTest() {
        try {
            List<Quiz> quizList = quizDAO.getAllQuizzes();

            if (!quizList.isEmpty()) {
                assertNotNull(quizList);
            }

            log.info("Get all quizzes in test");
        } catch (QuizDoesNotExistException | DAOLogicException e) {
            log.error("Error while testing getAllQuizzes ", e);
            fail();
        }


    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuizzesByTitleTest() {
        try {
            String title = "ZNO";
            List<Quiz> quizzes = quizDAO.getQuizzesByTitle(title);
            log.info("Get quizzes by title in test");
            if (!quizzes.isEmpty()) {
                assertEquals(title, quizzes.get(0).getTitle());
            }
        } catch (QuizDoesNotExistException | DAOLogicException e) {
            log.error("Error while testing getQuizzesByTitle ", e);
            fail();
        }


    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuizzesByTypeTest() {
        try {
            QuizType quizType = QuizType.MATHEMATICS;
            List<Quiz> quizzes = quizDAO.getQuizzesByType(quizType);

            if (!quizzes.isEmpty()) {
                assertEquals(quizType, quizzes.get(0).getQuizType());
            }
        } catch (QuizDoesNotExistException | DAOLogicException e) {
            log.error("Error while testing getQuizzesByType ", e);
            fail();
        }
    }
}
