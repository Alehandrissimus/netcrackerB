package ua.netcracker.netcrackerquizb.service.Impl;

import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.service.QuizService;

import java.util.Collection;

public class QuizServiceImpl implements QuizService {
    @Override
    public void buildNewQuiz(String name, String description, QuizType quizType, Collection<Question> questions, String owner) {

    }

    @Override
    public void validateQuiz(Quiz quiz) {

    }

    @Override
    public Quiz getQuiz() {
        return null;
    }
}
