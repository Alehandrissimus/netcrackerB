package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface QuizDAO {

    void createQuiz(Quiz quiz);

    void updateQuiz(BigInteger id, Quiz updatedQuiz);

    void deleteQuiz(BigInteger id);

    Quiz getQuizById(BigInteger id);

    Collection<Quiz> getQuizzesByType(QuizType quizType);

    Collection<Quiz> getAllQuizzes();

    Quiz getQuizByTitle(String title);




}
