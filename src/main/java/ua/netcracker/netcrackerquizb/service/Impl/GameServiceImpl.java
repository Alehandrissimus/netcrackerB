package ua.netcracker.netcrackerquizb.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.AnswerDAO;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.dao.UserAccomplishedQuizDAO;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.*;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;
import ua.netcracker.netcrackerquizb.service.GameService;

import java.math.BigInteger;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private UserAccomplishedQuizDAO userAccomplishedQuizDAO;

    private final int NUMBER_OF_QUESTIONS = 10;

    @Autowired
    public GameServiceImpl(QuestionDAO questionDAO, AnswerDAO answerDAO, UserAccomplishedQuizDAO userAccomplishedQuizDAO) {
        this.questionDAO = questionDAO;
        this.answerDAO = answerDAO;
        this.userAccomplishedQuizDAO = userAccomplishedQuizDAO;
    }

    @Override
    public void setTestConnection() throws DAOConfigException {
        questionDAO.setTestConnection();
        answerDAO.setTestConnection();
        userAccomplishedQuizDAO.setTestConnection();
    }

    @Override
    public void sendGameQuiz(String title) {

    }

    @Override
    public void validateAnswers(Quiz quiz, User user, List<Answer> answers)
            throws QuestionDoesNotExistException, DAOLogicException, AnswerDoesNotExistException, QuizDoesNotExistException {
        BigInteger quizId = quiz.getId();
        BigInteger userId = user.getId();
        List<Question> questions = questionDAO.getAllQuestions(quizId);
        int counterOfCorrectAnswers = 0;
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            Question question = questions.get(i);
            Answer answer = answers.get(i);
            if (question.getQuestionType().equals(QuestionType.FOUR_ANSWERS)) {
                List<Answer> fourDefaultAnswers = answerDAO.getAnswersByQuestionId(question.getId());
                for (Answer defAnswer : fourDefaultAnswers) {
                    if(defAnswer.getValue().equals(answer.getValue()) && defAnswer.getAnswer()) {
                        counterOfCorrectAnswers++;
                        break;
                    }
                }
            } else {
                List<Answer> twoDefaultAnswers = answerDAO.getAnswersByQuestionId(question.getId());
                for (Answer defAnswer : twoDefaultAnswers) {
                    if(defAnswer.getValue().equals(answer.getValue()) && defAnswer.getAnswer()) {
                        counterOfCorrectAnswers++;
                        break;
                    }
                }
            }
        }

        boolean isAccomplishedQuiz = userAccomplishedQuizDAO.isAccomplishedQuiz(userId, quizId);
        if (isAccomplishedQuiz) {
            QuizAccomplishedImpl existedQuizAccomplished = userAccomplishedQuizDAO.getAccomplishedQuizById(userId, quizId);
            if (existedQuizAccomplished.getCorrectAnswers() < counterOfCorrectAnswers) {
                existedQuizAccomplished.setCorrectAnswers(counterOfCorrectAnswers);
                userAccomplishedQuizDAO.editAccomplishedQuiz(userId, existedQuizAccomplished);
            }
        } else {
            QuizAccomplishedImpl quizAccomplished = new QuizAccomplishedImpl(counterOfCorrectAnswers, quizId);
            userAccomplishedQuizDAO.addAccomplishedQuiz(userId, quizAccomplished);
        }
    }

    @Override
    public void setIsFavorite(User user, QuizAccomplishedImpl quizAccomplished) throws DAOLogicException {
        Boolean isFavorite = quizAccomplished.getFavourite();
        BigInteger userId = user.getId();
        quizAccomplished.setFavourite(!isFavorite);
        userAccomplishedQuizDAO.setIsFavoriteQuiz(userId, quizAccomplished);
    }
}
