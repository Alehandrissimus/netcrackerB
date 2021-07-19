package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.util.Collection;

public interface QuizService {
    void buildNewQuiz(String name, String description, QuizType quizType,
                      Collection<Question> questions, String owner);
    void validateQuiz(Quiz quiz);
    Quiz getQuiz();
}
