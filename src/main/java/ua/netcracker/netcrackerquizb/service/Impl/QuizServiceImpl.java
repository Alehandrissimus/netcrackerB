package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.QuizDAO;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.impl.QuizImpl;
import ua.netcracker.netcrackerquizb.service.QuizService;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.*;


@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger log = Logger.getLogger(QuizServiceImpl.class);

    @Autowired
    private QuizDAO quizDAO;

    @Override
    public Quiz buildNewQuiz(String title, String description, QuizType quizType, BigInteger userId) throws QuizException, DAOLogicException {
        title = title.trim();
        description = description.trim();
        try {
            if (quizDAO.existQuizByDescription(description)) {
                log.info(QUIZ_ALREADY_EXISTS);
                throw new QuizException(QUIZ_ALREADY_EXISTS);
            }
            Quiz quiz = QuizImpl.QuizBuilder()
                    .setTitle(title)
                    .setDescription(description)
                    .setQuizType(quizType)
                    .setCreationDate(new Date(System.currentTimeMillis()))
                    .setCreatorId(userId)
                    .build();

            return quizDAO.createQuiz(quiz);

        } catch (DAOLogicException | UserDoesNotExistException e) {
            log.info(DAO_LOGIC_EXCEPTION + " in buildNewQuiz()");
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }

    }

    @Override
    public void updateQuiz(Quiz updatedQuiz) throws QuizDoesNotExistException, DAOLogicException {
        Quiz quizFromDAO = quizDAO.getQuizById(updatedQuiz.getId());
        if (quizFromDAO != null) {
            quizDAO.updateQuiz(updatedQuiz);
        } else {
            throw new QuizDoesNotExistException(QUIZ_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public void deleteQuiz(Quiz quiz) throws QuizDoesNotExistException, DAOLogicException {
        Quiz quizFromDAO = quizDAO.getQuizById(quiz.getId());
        if (quizFromDAO != null) {
            quizDAO.deleteQuiz(quiz);
        } else {
            throw new QuizDoesNotExistException(QUIZ_NOT_FOUND_EXCEPTION);
        }

    }

    @Override
    public Quiz getQuizById(BigInteger id) throws QuizDoesNotExistException, DAOLogicException {
        return quizDAO.getQuizById(id);
    }

    @Override
    public List<Quiz> getQuizzesByType(QuizType quizType) throws QuizDoesNotExistException, DAOLogicException {
        return quizDAO.getQuizzesByType(quizType);
    }

    @Override
    public List<Quiz> getAllQuizzes() throws QuizDoesNotExistException, DAOLogicException {
        return quizDAO.getAllQuizzes();
    }

    @Override
    public List<Quiz> getQuizzesByTitle(String title) throws QuizDoesNotExistException, DAOLogicException, QuizException {
        if (title.isBlank()) {
            throw new QuizException(EMPTY_TITLE);
        }
        return quizDAO.getQuizzesByTitle(title);
    }

}
