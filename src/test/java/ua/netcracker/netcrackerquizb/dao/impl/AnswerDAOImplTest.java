package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class AnswerDAOImplTest {

    @Autowired
    private AnswerDAOImpl answerDAO;

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getAnswerByIdTest() {
        Answer answer = answerDAO.getAnswerById(BigInteger.ONE);
        assertNotNull(answer);
        assertEquals("America", answer.getValue());
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getAnswerByTitleTest() {
        Answer answerByTitle = new AnswerImpl();
        answerByTitle.setValue("Aboba");
        answerByTitle.setAnswer(true);
        answerByTitle.setQuestionId(BigInteger.valueOf(1));
        answerDAO.createAnswer(answerByTitle);

        Answer answerByTitleTest = answerDAO.getAnswerByTitle("Aboba");
        assertNotNull(answerByTitleTest);

        assertEquals(answerByTitleTest.getAnswer(), answerByTitle.getAnswer());
        assertEquals(answerByTitleTest.getValue(), answerByTitle.getValue());
        assertEquals(answerByTitleTest.getQuestionId(), answerByTitle.getQuestionId());

        answerDAO.deleteAnswer(answerByTitleTest.getId());
        assertNull(answerDAO.getAnswerByTitle("Aboba"));

    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void createAnswerTest() {
        BigInteger questionId = BigInteger.valueOf(1);
        String value = "Antarctica";
        Answer answerImpl = new AnswerImpl();
        answerImpl.setValue(value);
        answerImpl.setAnswer(false);
        answerImpl.setQuestionId(questionId);

        answerDAO.createAnswer(answerImpl);
        Answer anAnswer = answerDAO.getAnswerByTitle(value);
        assertNotNull(anAnswer);

        assertEquals(anAnswer.getValue(), answerImpl.getValue());
        assertEquals(anAnswer.getAnswer(), answerImpl.getAnswer());
        assertEquals(anAnswer.getQuestionId(), answerImpl.getQuestionId());

        answerDAO.deleteAnswer(anAnswer.getId());
        assertNull(answerDAO.getAnswerByTitle("Antarctica"));
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void deleteAnswerTest() {
        Answer ans = new AnswerImpl();
        ans.setValue("Mars");
        ans.setQuestionId(BigInteger.valueOf(1));
        ans.setAnswer(false);
        answerDAO.createAnswer(ans);

        Answer nullAnswer = answerDAO.getAnswerByTitle("Mars");
        assertNotNull(nullAnswer);

        answerDAO.deleteAnswer(nullAnswer.getId());
        assertNull(answerDAO.getAnswerByTitle("Mars"));
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void updateAnswerTest() {
        Answer newAnswer = new AnswerImpl();
        newAnswer.setValue("Moon");
        newAnswer.setAnswer(false);
        newAnswer.setQuestionId(BigInteger.valueOf(2));
        answerDAO.createAnswer(newAnswer);

        Answer testNewAnswer = answerDAO.getAnswerByTitle("Moon");
        assertNotNull(testNewAnswer);

        newAnswer.setId(testNewAnswer.getId());

        testNewAnswer.setAnswer(true);
        testNewAnswer.setValue("Sun");
        testNewAnswer.setQuestionId(BigInteger.valueOf(3));
        answerDAO.updateAnswer(testNewAnswer);

        Answer finalAnswer = answerDAO.getAnswerByTitle("Sun");
        assertNotNull(finalAnswer);
        assertEquals(testNewAnswer.getAnswer(), finalAnswer.getAnswer());
        assertEquals(testNewAnswer.getQuestionId(), finalAnswer.getQuestionId());
        assertEquals(testNewAnswer.getValue(), finalAnswer.getValue());

        answerDAO.deleteAnswer(finalAnswer.getId());

        assertNull(answerDAO.getAnswerByTitle("Sun"));
    }
}


