package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizServiceImplTest {

    @Autowired
    private QuizServiceImpl quizService;
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
            User user = new UserImpl.UserBuilder()
                    .setId(BigInteger.valueOf(1))
                    .build();
            Quiz quiz = quizService.buildNewQuiz(
                    "Math",
                    "Math quiz",
                    QuizType.MATHEMATICS,
                    user.getId());

            log.info("Quiz with id " + quiz.getId() + " was created");
            assertNotNull(quiz);

            log.info("Quiz with id " + quiz.getId() + " was deleted");
            quizService.deleteQuiz(quiz);

        } catch (QuizException | DAOLogicException | QuizDoesNotExistException e) {
            e.printStackTrace();
        }
    }

}