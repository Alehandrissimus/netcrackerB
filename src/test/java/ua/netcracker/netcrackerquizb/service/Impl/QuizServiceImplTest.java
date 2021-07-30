package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.service.QuizService;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.ERROR_WHILE_SETTING_TEST_CONNECTION;

@SpringBootTest
class QuizServiceImplTest {

    private QuizService quizService;

    @Autowired
    private void setTestConnection(QuizService quizService) {
        this.quizService = quizService;
        try {
            quizService.setTestConnection();
        } catch (DAOConfigException e) {
            log.error(ERROR_WHILE_SETTING_TEST_CONNECTION + e.getMessage());
        }
    }

    private static final Logger log = Logger.getLogger(QuizServiceImplTest.class);

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void getQuizByIdTest() {
        try {
            Quiz quiz = quizService.getQuizById(BigInteger.valueOf(1));
            assertNotNull(quiz);
            assertEquals(1, quiz.getId().intValue());

            log.info("Quiz was found by id: " + quiz.getId());

        } catch (QuizDoesNotExistException | DAOLogicException e) {
            log.error("Error while testing getQuizById in QuizService", e);
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void buildNewQuiz() {
        try {
            Quiz quiz = quizService.buildNewQuiz(
                    "Math",
                    "Math quiz",
                    QuizType.MATHEMATICS,
                    BigInteger.valueOf(1));

            log.info("Quiz with id " + quiz.getId() + " was created");
            assertNotNull(quiz);

            log.info("Quiz with id " + quiz.getId() + " was deleted");
            quizService.deleteQuiz(quiz);

        } catch (QuizException | DAOLogicException | QuizDoesNotExistException | UserException | UserDoesNotExistException e) {
            log.error("Error while testing buildNewQuiz in QuizService", e);
            fail();
        }
    }


}
