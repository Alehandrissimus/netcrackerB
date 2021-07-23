package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class QuestionDAOImplTest {

    @Autowired
    private QuestionDAOImpl questionDAO;

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuestionByIdTest() {
        Question question = questionDAO.getQuestionById(BigInteger.ONE, new ArrayList<>());
        assertNotNull(question);
        assertEquals("Ukraine location?", question.getQuestion());
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void createQuestionTest() {
        BigInteger quizId = BigInteger.valueOf(1);
        String questionText = "Where is?";
        Question questionModel = new QuestionImpl();
        questionModel.setQuestion(questionText);
        questionModel.setQuestionType(QuestionType.TRUE_FALSE);

        questionDAO.createQuestion(questionModel, quizId);

        boolean isFound = false;
        Collection<Question> questions = questionDAO.getAllQuestions(quizId);
        for(Question question : questions) {
            if(question.getQuestion().equals(questionText)) {
                isFound = true;
            }
        }
        assertTrue(isFound);

        questionDAO.deleteQuestion(questionModel, quizId);
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuestionsByQuizTest() {
        Collection<Question> questions = questionDAO.getAllQuestions(BigInteger.valueOf(1));
        for (Question question : questions) {
            assertNotNull(question.getQuestion());
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void updateQuestionTest() {
        BigInteger questionId = BigInteger.valueOf(3);
        Question questionOld = questionDAO.getQuestionById(questionId, new ArrayList<>());
        Question questionNew = questionOld;
        questionNew.setQuestion("New que");
        questionNew.setQuestionType(QuestionType.FOUR_ANSWERS);

        questionDAO.updateQuestion(questionNew);

        Question questionGot  = questionDAO.getQuestionById(questionId, new ArrayList<>());

        questionDAO.updateQuestion(questionOld);
        assertEquals(questionGot.getQuestion(), questionNew.getQuestion());
        assertEquals(questionGot.getQuestionType(), questionNew.getQuestionType());
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuestionReturningNull() {
        Question question = questionDAO.getQuestionById(BigInteger.valueOf(-1), new ArrayList<>());
        assertNull(question);
    }

}