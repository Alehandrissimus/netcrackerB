package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.AnswerDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class AnswerDAOImplTest {

    private AnswerDAOImpl answerDAO;
    private static final Logger log = Logger.getLogger(AnswerDAOImplTest.class);

    @Autowired
    private void setDAO(AnswerDAOImpl answerDAO) {
        this.answerDAO = answerDAO;
        try {
            answerDAO.setTestConnection();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error("Error while setting test connection in AnswerDAOImplTest " + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getAnswerByIdTest() {
        try {
            Answer answer = answerDAO.getAnswerById(BigInteger.ONE);
            assertNotNull(answer);
            assertEquals("America", answer.getValue());
        } catch (DAOLogicException | AnswerDoesNotExistException e) {
            log.error("Error while testing getAnswerByIdTest " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getLastAnswerIdByTitleTest() {
        try {
            String aboba = "Aboba";
            Answer answerByTitle = new AnswerImpl(aboba, true, BigInteger.ONE);
            answerDAO.createAnswer(answerByTitle);

            BigInteger id = answerDAO.getLastAnswerIdByTitle(aboba);
            Answer answerByTitleTest = answerDAO.getAnswerById(id);
            assertNotNull(answerByTitleTest);

            assertEquals(answerByTitleTest.getAnswer(), answerByTitle.getAnswer());
            assertEquals(answerByTitleTest.getValue(), answerByTitle.getValue());
            assertEquals(answerByTitleTest.getQuestionId(), answerByTitle.getQuestionId());

            answerDAO.deleteAnswer(id);
        } catch (DAOLogicException | AnswerDoesNotExistException e) {
            log.error("Error while testing getLastAnswerIdByTitleTest " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void createAnswerTest() {
        try {
            String antarctica = "Antarctica";
            Answer answerImpl = new AnswerImpl(antarctica, false, BigInteger.ONE);

            answerDAO.createAnswer(answerImpl);
            BigInteger id = answerDAO.getLastAnswerIdByTitle(antarctica);
            Answer anAnswer = answerDAO.getAnswerById(id);
            assertNotNull(anAnswer);

            assertEquals(anAnswer.getValue(), answerImpl.getValue());
            assertEquals(anAnswer.getAnswer(), answerImpl.getAnswer());
            assertEquals(anAnswer.getQuestionId(), answerImpl.getQuestionId());

            answerDAO.deleteAnswer(id);
        } catch (DAOLogicException | AnswerDoesNotExistException e) {
            log.error("Error while testing createAnswerTest " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void deleteAnswerTest() {
        try {
            String mars = "Mars";
            Answer ans = new AnswerImpl(mars, false, BigInteger.ONE);
            answerDAO.createAnswer(ans);

            BigInteger id = answerDAO.getLastAnswerIdByTitle(mars);
            Answer nullAnswer = answerDAO.getAnswerById(id);
            assertNotNull(nullAnswer);

            answerDAO.deleteAnswer(id);
        } catch (DAOLogicException | AnswerDoesNotExistException e) {
            log.error("Error while testing deleteAnswerTest " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void updateAnswerTest() {
        try {
            String moon = "Moon";
            Answer newAnswer = new AnswerImpl(moon, false, BigInteger.TWO);
            answerDAO.createAnswer(newAnswer);

            BigInteger id = answerDAO.getLastAnswerIdByTitle(moon);
            Answer testNewAnswer = answerDAO.getAnswerById(id);
            assertNotNull(testNewAnswer);

            String sun = "Sun";
            testNewAnswer.setValue(sun);
            testNewAnswer.setAnswer(true);
            testNewAnswer.setQuestionId(BigInteger.valueOf(3));
            answerDAO.updateAnswer(testNewAnswer);

            Answer finalAnswer = answerDAO.getAnswerById(id);
            assertNotNull(finalAnswer);

            assertEquals(testNewAnswer.getAnswer(), finalAnswer.getAnswer());
            assertEquals(testNewAnswer.getQuestionId(), finalAnswer.getQuestionId());
            assertEquals(testNewAnswer.getValue(), finalAnswer.getValue());

            answerDAO.deleteAnswer(id);
        } catch (DAOLogicException | AnswerDoesNotExistException e) {
            log.error("Error while testing updateAnswerTest " + e.getMessage());
            fail();
        }
    }
}


