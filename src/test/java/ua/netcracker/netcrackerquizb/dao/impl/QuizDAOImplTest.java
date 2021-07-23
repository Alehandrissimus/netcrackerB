package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.QuizImpl;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizDAOImplTest {

    @Autowired
    private QuizDAOImpl quizDAO;

    @Test
    void createQuizTest() {
        Quiz quiz = new QuizImpl();

        User user = new UserImpl();
        user.setId(BigInteger.valueOf(2));
        String title = "New super pooper quiz";
        quiz.setTitle(title);
        quiz.setDescription("Horror quiz");
        quiz.setQuizType(QuizType.SCIENCE);
        quiz.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
        quiz.setCreatorId(user.getId());

        quizDAO.createQuiz(quiz);

        Quiz quizTest = quizDAO.getQuizByTitle(title);
        assertNotNull(quizTest);
        assertEquals(quiz.getTitle(), quizTest.getTitle());
        assertEquals(quiz.getDescription(), quizTest.getDescription());
        assertEquals(quiz.getQuizType(), quizTest.getQuizType());

        quizDAO.deleteQuiz(quizTest.getId());
        assertNull(quizDAO.getQuizByTitle(title));
    }

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
//        Quiz quiz = quizDAO.getQuizById(BigInteger.valueOf(32));
//        quizDAO.deleteQuiz(quiz.getId());
//
//        assertNull(quizDAO.getQuizById(BigInteger.valueOf(32)));

        Quiz quiz = new QuizImpl();
        User user = new UserImpl();
        user.setId(BigInteger.TWO);

        quiz.setTitle("newOlo");
        quiz.setDescription("Horror quiz");
        quiz.setQuizType(QuizType.SCIENCE);
        quiz.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
        quiz.setCreatorId(user.getId());

        quizDAO.createQuiz(quiz);

        Quiz thisQuiz = quizDAO.getQuizByTitle("newOlo");
        assertNotNull(thisQuiz);

        quizDAO.deleteQuiz(thisQuiz.getId());
        assertNull(quizDAO.getQuizByTitle("newOlo"));

    }

    @Test
    void updateQuizTest() {
        Quiz quiz = new QuizImpl();
        User user = new UserImpl();
        user.setId(BigInteger.TWO);

        quiz.setTitle("HeyHop");
        quiz.setDescription("Funny quiz");
        quiz.setQuizType(QuizType.SCIENCE);
        quiz.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
        quiz.setCreatorId(user.getId());
        quiz.setId(BigInteger.valueOf(7));

        quizDAO.createQuiz(quiz);

        Quiz updatedQuiz = quizDAO.getQuizByTitle("HeyHop");

        updatedQuiz.setTitle("Lalaley");
        updatedQuiz.setDescription("Funny quiz");
        updatedQuiz.setQuizType(QuizType.HISTORIC);

        quizDAO.updateQuiz(quiz.getId(), updatedQuiz);

        assertNotEquals(updatedQuiz.getTitle(), quiz.getTitle());
        assertEquals("Lalaley", updatedQuiz.getTitle());

        quizDAO.deleteQuiz(updatedQuiz.getId());

        assertNull(quizDAO.getQuizByTitle("Lalaley"));
    }

    @Test
    void getAllQuizzesTest() {
        Collection<Quiz> quizList = quizDAO.getAllQuizzes();

        assertNotNull(quizList);
    }

    @Test
    void getQuizByTitleTest() {
        Quiz quiz = quizDAO.getQuizByTitle("Testing");

        assertEquals("Testing", quiz.getTitle());
        //assertEquals(2, quiz.getId().intValue());
    }

    @Test
    void getQuizzesByTypeTest() {
        Collection<Quiz> quizzes = quizDAO.getQuizzesByType(QuizType.MATHEMATICS);

        assertNotNull(quizzes);
    }
}
