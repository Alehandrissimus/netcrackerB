package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.enums.QuizType;

import java.math.BigInteger;
import java.util.List;

public interface QuizDAO {

    void createQuiz(Quiz quiz);

    void updateQuiz(BigInteger id, Quiz updatedQuiz);

    void deleteQuiz(BigInteger id);

    Quiz getQuizById(BigInteger id);

    List<Quiz> getQuizzesByType(QuizType quizType);

    List<Quiz> getAllQuizzes();

    Quiz getQuizByTitle(String title);




}
