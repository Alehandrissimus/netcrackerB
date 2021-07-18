package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.Collection;

public interface QuizDAO {

    void createQuiz(Quiz quiz);
    void deleteQuiz(Quiz quiz);
    Collection<Quiz> getQuizByType(QuizType quizType);
    Quiz updateQuiz(Quiz quiz);
    Quiz getLightQuizBean(Quiz quiz);

}
