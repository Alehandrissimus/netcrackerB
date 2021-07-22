package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

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
        assert (question != null);
        assert (question.getQuestion().equals("Ukraine location?"));
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
            bool = question.getQuestion().equals(questionText);
        }
        assert(bool);

        questionDAO.deleteQuestion(questionModel, quizId);
    }

    @Test
    void getQuestionsByQuizTest() {
        Collection<Question> questions = questionDAO.getAllQuestions(BigInteger.valueOf(1));
        for (Question question : questions) {
            assert (question.getQuestion() != null);
        }
    }

    @Test
    void updateQuestionTest() {

    }
}