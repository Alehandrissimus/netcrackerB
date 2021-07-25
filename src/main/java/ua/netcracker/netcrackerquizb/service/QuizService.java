package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

public interface QuizService {

    Quiz createQuiz(Quiz quiz);

    void updateQuiz(Quiz updatedQuiz);

    void deleteQuiz(Quiz quiz);

    Quiz getQuizById(BigInteger id);

    List<Quiz> getQuizzesByType(QuizType quizType);

    List<Quiz> getAllQuizzes();

    List<Quiz> getQuizzesByTitle(String title);

    Quiz buildNewQuiz(String title, String description, QuizType quizType, User user);

    void validateQuiz(Quiz quiz);

}
