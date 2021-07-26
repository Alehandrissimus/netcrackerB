package ua.netcracker.netcrackerquizb.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.exception.QuizDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.QuizImpl;
import ua.netcracker.netcrackerquizb.service.QuizService;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDAO quizDAO;

    @Override
    public Quiz createQuiz(Quiz quiz) throws DaoLogicException, UserDoesNotExistException {
        return quizDAO.createQuiz(quiz);
    }

    @Override
    public void updateQuiz(Quiz updatedQuiz) throws QuizDoesNotExistException, DaoLogicException {
        quizDAO.updateQuiz(updatedQuiz);
    }

    @Override
    public void deleteQuiz(Quiz quiz) throws QuizDoesNotExistException, DaoLogicException {
        quizDAO.deleteQuiz(quiz);
    }

    @Override
    public Quiz getQuizById(BigInteger id) throws QuizDoesNotExistException, DaoLogicException {
        return quizDAO.getQuizById(id);
    }

    @Override
    public List<Quiz> getQuizzesByType(QuizType quizType) throws QuizDoesNotExistException, DaoLogicException {
        return quizDAO.getQuizzesByType(quizType);
    }

    @Override
    public List<Quiz> getAllQuizzes() throws QuizDoesNotExistException, DaoLogicException {
        return quizDAO.getAllQuizzes();
    }

    @Override
    public List<Quiz> getQuizzesByTitle(String title) throws QuizDoesNotExistException, DaoLogicException {
        return quizDAO.getQuizzesByTitle(title);
    }

    @Override
    public Quiz buildNewQuiz(String title, String description, QuizType quizType, User user) {
        return QuizImpl.QuizBuilder()
                .setTitle(title)
                .setDescription(description)
                .setQuizType(quizType)
                .setCreationDate(new Date(System.currentTimeMillis()))
                .setCreatorId(user.getId())
                .build();
    }
}
