package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.enums.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.math.BigInteger;
import java.sql.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizDAOImplTest {

    @Autowired
    private QuizDAOImpl quizDAO;


    //done, add deleteQuiz()
    @Test
    void createQuizTest() {
//        Quiz quiz = new Quiz();
//
//        User user = new UserImpl();
//        user.setId(BigInteger.valueOf(2));
//
//        quiz.setTitle("New super pooper quiz");
//        quiz.setDescription("Horror quiz");
//        quiz.setQuizType(QuizType.SCIENCE);
//        quiz.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        quiz.setCreatorId(user.getId());
//
//        quizDAO.createQuiz(quiz);
//
//        assertEquals(quiz.getTitle(), "New super pooper quiz");
    }

    //done
    @Test
    void getQuizByIdTest() {
        Quiz quiz = quizDAO.getQuizById(BigInteger.valueOf(3));
        Quiz expectedQuiz = quizDAO.getQuizByTitle("ZNO");

        assertNotNull(quiz);
        assertEquals(quiz.getTitle(), expectedQuiz.getTitle());
        assertEquals(quiz.getId().intValue(), expectedQuiz.getId().intValue());
    }

    @Test
    void deleteQuizTest() {
//
//        Quiz quiz = quizDAO.getQuizById(BigInteger.valueOf(20));
//        quizDAO.deleteQuiz(quiz.getId());
//
//        assertNull(quizDAO.getQuizById(BigInteger.valueOf(20)).getId());

    }

    //done
    @Test
    void updateQuizTest() {
        Quiz quiz = quizDAO.getQuizById(BigInteger.valueOf(1));
        Quiz updatedQuiz = quizDAO.getQuizById(quiz.getId());
        updatedQuiz.setTitle("newTxt");
        quizDAO.updateQuiz(quiz.getId(), updatedQuiz);

        assertEquals("newTxt", updatedQuiz.getTitle());
    }

    //done
    @Test
    void getAllQuizzesTest() {
        List<Quiz> quizList = quizDAO.getAllQuizzes();

        assertEquals(quizList.get(1).getTitle(), "Testing");
        assert (!quizList.isEmpty());
    }

    //done
    @Test
    void getQuizByTitleTest() {
        Quiz quiz = quizDAO.getQuizByTitle("Testing") ;

        assertEquals("Testing", quiz.getTitle());
        assertEquals(2, quiz.getId().intValue());
    }

    @Test
    void getQuizzesByTypeTest() {
//        List<Quiz> quizzes = quizDAO.getQuizzesByType(QuizType.HISTORIC);
//
//        System.out.println(quizzes);
//        assertEquals(quizzes.get(0).getQuizType(), QuizType.HISTORIC);

    }

}
