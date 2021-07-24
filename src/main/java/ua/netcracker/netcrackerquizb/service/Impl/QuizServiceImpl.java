package ua.netcracker.netcrackerquizb.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.builders.QuizBuilder;
import ua.netcracker.netcrackerquizb.service.QuizService;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDAO quizDAO;

    @Override
    public Quiz createQuiz(Quiz quiz) {
        return quizDAO.createQuiz(quiz);
    }

    @Override
    public void updateQuiz(Quiz updatedQuiz) {
        quizDAO.updateQuiz(updatedQuiz);
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        quizDAO.deleteQuiz(quiz);
    }

    @Override
    public Quiz getQuizById(BigInteger id) {
        return quizDAO.getQuizById(id);
    }

    @Override
    public List<Quiz> getQuizzesByType(QuizType quizType) {
        return quizDAO.getQuizzesByType(quizType);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDAO.getAllQuizzes();
    }

    @Override
    public List<Quiz> getQuizzesByTitle(String title) {
        return quizDAO.getQuizzesByTitle(title);
    }

    @Override
    public Quiz buildNewQuiz(String title, String description, QuizType quizType, User user) {
        return QuizBuilder.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(quizType)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();
    }

    @Override
    public void validateQuiz(Quiz quiz) {

    }
}
