package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;

import java.math.BigInteger;

@SpringBootTest
public class AnswerDAOImplTest {

    @Autowired
    private AnswerDAOImpl answerDAO;

    @Test
    void getAnswerByIdTest() {
        Answer answer = answerDAO.getAnswerById(BigInteger.ONE);
        assert(answer != null);
        assert(answer.getValue().equals("America"));
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
        Answer newAnswer = answerDAO.getAnswerByTitle(value);

        assert(newAnswer.getValue().equals(answerImpl.getValue()));

        answerDAO.deleteAnswer(answerImpl.getId());
        Answer nullAnswer = answerDAO.getAnswerByTitle(value);

        assert(nullAnswer == null);

    }

    @Test
    void deleteAnswerTest() {

    }

    @Test
    void updateAnswerTest() {

    }
}
