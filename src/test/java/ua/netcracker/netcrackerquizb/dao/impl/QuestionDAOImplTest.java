package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class QuestionDAOImplTest {

    private QuestionDAOImpl questionDAO;
    private static final Logger log = Logger.getLogger(QuestionDAOImplTest.class);

    @Autowired
    private void setDAO(QuestionDAOImpl questionDAO) {
        this.questionDAO = questionDAO;
        try {
            questionDAO.setTestConnection();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error("Error while setting test connection " + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void getQuestionByIdTest() {
        try {
            Question question = questionDAO.getQuestionById(BigInteger.ONE, new ArrayList<>());
            assertNotNull(question);
            assertEquals("Ukraine location?", question.getQuestion());
        } catch (DaoLogicException | QuestionDoesNotExistException e) {
            log.error("Error while testing getQuestionByIdTest " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void createQuestionTest() {
        try {
            BigInteger quizId = BigInteger.valueOf(1);
            String questionText = "" + new Random().nextInt(500000);
            Question questionModel = new QuestionImpl(
                    questionText,
                    QuestionType.TRUE_FALSE
            );

            log.info("questiontext = "+questionText);

            questionDAO.createQuestion(questionModel, quizId);

            boolean isFound = false;
            Collection<Question> questions = questionDAO.getAllQuestions(quizId);
            for (Question question : questions) {
                if (question.getQuestion().equals(""+questionText)) {
                    isFound = true;
                    log.info("found");
                }
            }
            assertTrue(isFound);

            questionDAO.deleteQuestion(questionModel, quizId);
        } catch (DaoLogicException | QuestionDoesNotExistException e) {
            log.error("Error while testing createQuestionTest " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void getQuestionsByQuizTest() {
        try {
            Collection<Question> questions = questionDAO.getAllQuestions(BigInteger.valueOf(1));
            for (Question question : questions) {
                assertNotNull(question.getQuestion());
            }
        } catch (DaoLogicException | QuestionDoesNotExistException e) {
            log.error("Error while testing getQuestionsByQuizTest " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void updateQuestionTest() {
        try {
            BigInteger questionId = BigInteger.valueOf(3);
            Question questionOld = questionDAO.getQuestionById(questionId, new ArrayList<>());
            Question questionNew = questionOld;
            questionNew.setQuestion("New que");
            questionNew.setQuestionType(QuestionType.FOUR_ANSWERS);

            questionDAO.updateQuestion(questionNew);

            Question questionGot = questionDAO.getQuestionById(questionId, new ArrayList<>());

            questionDAO.updateQuestion(questionOld);
            assertEquals(questionGot.getQuestion(), questionNew.getQuestion());
            assertEquals(questionGot.getQuestionType(), questionNew.getQuestionType());
        } catch (DaoLogicException | QuestionDoesNotExistException e) {
            log.error("Error while testing updateQuestionTest " + e.getMessage());
            fail();
        }
    }

    /*
    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void getQuestionReturningNull() {
        try {
            Question question = questionDAO.getQuestionById(BigInteger.valueOf(-1), new ArrayList<>());
            assertNull(question);
        } catch (DaoLogicException | QuestionNotFoundException e) {
            log.error("Error while testing getQuestionReturningNull " + e.getMessage());
            fail();
        }
    }
     */

}