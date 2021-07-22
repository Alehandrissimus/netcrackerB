package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest
class QuestionDAOImplTest {

    @Autowired
    private QuestionDAOImpl questionDAO;

    @Test
    void getQuestionByIdTest() {
        Question question = questionDAO.getQuestionById(BigInteger.ONE, new ArrayList<>());
        assertNotNull(question);
        assertEquals(question.getQuestion(), "Ukraine location?");
    }

    @Test
    void createQuestionTest() {
        BigInteger quizId = BigInteger.valueOf(1);
        String questionText = "Where is?";
        Question questionModel = new QuestionImpl();
        questionModel.setQuestion(questionText);
        questionModel.setQuestionType(QuestionType.TRUE_FALSE);

        questionDAO.createQuestion(questionModel, quizId);

        boolean bool = false;
        Collection<Question> questions = questionDAO.getAllQuestions(quizId);
        for(Question question : questions) {
            if(question.getQuestion().equals(questionText)) {
                bool = true;
            }
        }
        assertTrue(bool);

        questionDAO.deleteQuestion(questionModel, quizId);
    }

    @Test
    void getQuestionsByQuizTest() {
        Collection<Question> questions = questionDAO.getAllQuestions(BigInteger.valueOf(1));
        for (Question question : questions) {
            assertNotNull(question.getQuestion());
        }
    }

    @Test
    void updateQuestionTest() {
        BigInteger quizId = BigInteger.valueOf(3);
        Question questionOld = questionDAO.getQuestionById(quizId, new ArrayList<>());
        Question questionNew = questionOld;
        questionNew.setQuestion("New que");
        questionNew.setQuestionType(QuestionType.FOUR_ANSWERS);

        questionDAO.updateQuestion(questionNew);

        Question questionGot  = questionDAO.getQuestionById(quizId, new ArrayList<>());

        questionDAO.updateQuestion(questionOld);
        assertEquals(questionGot.getQuestion(), questionNew.getQuestion());
        assertEquals(questionGot.getQuestionType(), questionNew.getQuestionType());
    }

    @Test
    void getQuestionReturningNull() {
        Question question = questionDAO.getQuestionById(BigInteger.valueOf(-1), new ArrayList<>());
        assertNull(question);
    }

}