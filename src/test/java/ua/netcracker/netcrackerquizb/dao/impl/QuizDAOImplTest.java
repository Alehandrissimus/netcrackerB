package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.builders.QuizBuilder;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizDAOImplTest {

    @Autowired
    private QuizDAOImpl quizDAO;

    @Test
    void createQuizTest() {

        User user = new UserImpl();
        user.setId(BigInteger.valueOf(2));

        String title = "New super pooper quiz";
        String description = "Horror quiz";
        Quiz quiz = QuizBuilder.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();

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

//        Quiz quiz1 = quizDAO.getQuizById(BigInteger.valueOf(128));
//        quizDAO.deleteQuiz(quiz1.getId());


        User user = new UserImpl();
        user.setId(BigInteger.valueOf(2));

        String title = "newOlo";
        String description = "Horror quiz";
        Quiz quiz = QuizBuilder.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();


        quizDAO.createQuiz(quiz);

        System.out.println(quiz);

        Quiz thisQuiz = quizDAO.getQuizByTitle("newOlo");
        assertNotNull(thisQuiz);

        quizDAO.deleteQuiz(thisQuiz.getId());
        assertNull(quizDAO.getQuizByTitle("newOlo"));

    }

    @Test
    void updateQuizTest() {

        User user = new UserImpl();
        user.setId(BigInteger.valueOf(2));

        String title = "HeyHop";
        String description = "Funny quiz";
        Quiz quiz = QuizBuilder.newBuilder()
                .setId(BigInteger.valueOf(11))
                .setTitle(title)
                .setDescription(description)
                .setQuizType(QuizType.SCIENCE)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();

        quizDAO.createQuiz(quiz);

        Quiz updatedQuiz = quizDAO.getQuizByTitle(title);
        updatedQuiz.setTitle("newTitle");

        quizDAO.updateQuiz(quiz.getId(), updatedQuiz);

        System.out.println(quiz);
        System.out.println(updatedQuiz);

        assertNotEquals(quiz, updatedQuiz);

        quizDAO.deleteQuiz(quiz.getId());
        quizDAO.deleteQuiz(updatedQuiz.getId());

        System.out.println(quiz);
        System.out.println(updatedQuiz);

        assertNull(quizDAO.getQuizByTitle("HeyHop"));

    }

    @Test
    void getAllQuizzesTest() {
        List<Quiz> quizList = quizDAO.getAllQuizzes();

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
        List<Quiz> quizzes = quizDAO.getQuizzesByType(QuizType.MATHEMATICS);
        System.out.println(quizzes);
        assertNotNull(quizzes);
    }
}
