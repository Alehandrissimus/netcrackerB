package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.List;

public interface QuizDAO {

    void createQuiz(Quiz quiz);

    void updateQuiz(BigInteger id, Quiz updatedQuiz);

    boolean deleteQuiz(Quiz quiz);

    Quiz getQuizById(BigInteger id);

    List<Quiz> getQuizzesByType(QuizType quizType);

    List<Quiz> getAllQuizzes();

    Quiz getQuizByTitle(String title);




}
