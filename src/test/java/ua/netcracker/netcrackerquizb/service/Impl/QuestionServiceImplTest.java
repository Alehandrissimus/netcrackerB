package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.AnswerDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.QuestionException;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;
import ua.netcracker.netcrackerquizb.service.QuestionService;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceImplTest {

    private static final Logger log = Logger.getLogger(QuestionServiceImplTest.class);

    @Autowired
    private QuestionService questionService;

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void createQuestion() throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException, QuestionException {
        BigInteger quizId = BigInteger.valueOf(2);
        String questionText = "" + new Random().nextInt(500000);
        Question questionModel = new QuestionImpl(
                questionText,
                QuestionType.TRUE_FALSE
        );

        questionService.createQuestion(questionModel, quizId);
        Question question = questionService.getQuestionByData(questionText, quizId);
        assertEquals(questionText, question.getQuestion());
        log.debug("QuestionServiceTest createQuestion id = " + question.getId());
        questionService.deleteQuestion(question);
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void createQuestionNull() throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException, QuestionException {
        BigInteger quizId = BigInteger.valueOf(2);
        String questionText = "";
        Question questionModel = new QuestionImpl(
                questionText,
                QuestionType.TRUE_FALSE
        );

        Question createdQuestion = questionService.createQuestion(questionModel, quizId);
        assertNull(createdQuestion);
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void updateQuestion() {
        BigInteger quizId = BigInteger.valueOf(2);
        String questionText = "" + new Random().nextInt(500000);
        Question questionModel = new QuestionImpl(
                questionText,
                QuestionType.TRUE_FALSE
        );
    }

}