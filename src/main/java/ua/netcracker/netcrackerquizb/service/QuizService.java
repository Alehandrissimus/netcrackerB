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

    void createQuiz(Quiz quiz);

    void updateQuiz(BigInteger id, Quiz updatedQuiz);

    void deleteQuiz(BigInteger id);

    Quiz getQuizById(BigInteger id);

    List<Quiz> getQuizzesByType(QuizType quizType);

    List<Quiz> getAllQuizzes();

    Quiz getQuizByTitle(String title);

    Quiz buildNewQuiz(String title, String description, QuizType quizType, User user);

    void validateQuiz(Quiz quiz);

}
