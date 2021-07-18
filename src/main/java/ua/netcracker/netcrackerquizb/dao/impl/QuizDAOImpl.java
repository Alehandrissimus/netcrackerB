package ua.netcracker.netcrackerquizb.dao.impl;

import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.util.Collection;

public class QuizDAOImpl implements QuizDAO {

    private Quiz quiz;


    @Override
    public void createQuiz(Quiz quiz) {

    }

    @Override
    public void deleteQuiz(Quiz quiz) {

    }

    @Override
    public Collection<Quiz> getQuizByType(QuizType quizType) {
        return null;
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quiz;
    }

    @Override
    public Quiz getLightQuizBean(Quiz quiz) {
        return quiz;
    }
}

