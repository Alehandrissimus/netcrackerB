package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

@SpringBootTest
public class AnswerDAOImplTest {

    @Autowired
    private AnswerDAOImpl answerDAO;

    @Test
    void getAnswerByIdTest() {
        Answer answer = answerDAO.getAnswerById(BigInteger.ONE);
        assertNotNull(answer);
        assertEquals(answer.getValue(), "America");
    }

    @Test
    void createAnswerTest() {
        BigInteger questionId = BigInteger.valueOf(1);
        String value = "Antarctica";
        Answer answerImpl = new AnswerImpl();
        answerImpl.setValue(value);
        answerImpl.setAnswer(false);
        answerImpl.setQuestionId(questionId);

        answerDAO.createAnswer(answerImpl);
        Answer anAnswer = answerDAO.getAnswerByTitle(value);

        assertEquals(anAnswer.getValue(), answerImpl.getValue());
        assertEquals(anAnswer.getAnswer(), answerImpl.getAnswer());
        assertEquals(anAnswer.getQuestionId(), answerImpl.getQuestionId());
    }

    @Test
    void deleteAnswerTest() {
        Answer nullAnswer = answerDAO.getAnswerByTitle("Antarctica");
        assertNotNull(nullAnswer);

        answerDAO.deleteAnswer(nullAnswer.getId());
        assertNull(answerDAO.getAnswerByTitle("Antarctica"));
    }

    @Test
    void updateAnswerTest() {
        Answer newAnswer = new AnswerImpl();
        newAnswer.setValue("Moon");
        newAnswer.setAnswer(false);
        newAnswer.setQuestionId(BigInteger.valueOf(2));
        answerDAO.createAnswer(newAnswer);

        Answer testNewAnswer = answerDAO.getAnswerByTitle("Moon");
        newAnswer.setId(testNewAnswer.getId());
        assertEquals(testNewAnswer.getAnswer(), newAnswer.getAnswer());
        assertEquals(testNewAnswer.getQuestionId(), newAnswer.getQuestionId());
        assertEquals(testNewAnswer.getValue(), newAnswer.getValue());

        answerDAO.deleteAnswer(testNewAnswer.getId());
        answerDAO.deleteAnswer(newAnswer.getId());

        assertNull(answerDAO.getAnswerByTitle("Moon"));
    }
}


