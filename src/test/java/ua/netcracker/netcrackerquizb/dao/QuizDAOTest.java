package ua.netcracker.netcrackerquizb.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ua.netcracker.netcrackerquizb.dao.impl.QuizDAOImpl;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuizDAOTest {

    @Autowired
    private QuizDAOImpl quizDAO;

    @Test
    void createQuizTest() {
//        Quiz quiz = new Quiz();
//        User user = new UserImpl();
//        user.setId(BigInteger.TWO);
//
//        quiz.setTitle("New super pooper quiz");
//        //quiz.setDescription("Horror quiz");
//        quiz.setQuizType(QuizType.MATHEMATICS);
//        quiz.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        quiz.setCreatorId(user.getId());
//
//        quizDAO.createQuiz(quiz);
//
//        assert (quiz.getTitle().equals("New super pooper quiz"));
    }

    @Test
    void getQuizByIdTest() {
        Quiz quiz = quizDAO.getQuizById(BigInteger.valueOf(29));
        System.out.println(quiz);
        assert (quiz != null);
        assert (quiz.getId().intValue() == 29);
        assert(quiz.getTitle().equals("TheBest"));
    }

    @Test
    void deleteQuizTest() {

//        Quiz quiz = new Quiz();
//        User user = new UserImpl();
//        user.setId(BigInteger.ONE);
//
//        quiz.setTitle("TheQuiz");
//        //quiz.setDescription("Horror quiz");
//        quiz.setQuizType(QuizType.SCIENCE);
//        quiz.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        quiz.setCreatorId(user.getId());
//
//        quizDAO.createQuiz(quiz);
////
//        boolean b = quizDAO.deleteQuiz(quizDAO.getQuizById(BigInteger.valueOf(34)));
//        assert (b);
    }

    @Test
    void updateQuizTest() {
        Quiz quiz = quizDAO.getQuizById(BigInteger.valueOf(1));
        Quiz updatedQuiz = quizDAO.getQuizById(quiz.getId());
        updatedQuiz.setTitle("Txt");
        quizDAO.updateQuiz(quiz.getId(), updatedQuiz);
        System.out.println(quiz);
        System.out.println(updatedQuiz);
        assert (quiz != updatedQuiz);
    }

    @Test
    void getAllQuizzesTest() {
        List<Quiz> quizList = quizDAO.getAllQuizzes();
        System.out.println(quizList);
        assert (quizList.get(1).getTitle().equals("Testing"));
        assert (!quizList.isEmpty());
    }

    @Test
    void getQuizByTitleTest() {
        Quiz quiz = quizDAO.getQuizByTitle("TheBest") ;
        System.out.println(quiz);
        assert (quiz.getTitle().equals("TheBest"));
        assert (quiz.getId().intValue() == 29);
    }

    @Test
    void getQuizzesByTypeTest() {

    }
}
