package ua.netcracker.netcrackerquizb.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.AnswerDAO;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.dao.UserAccomplishedQuizDAO;
import ua.netcracker.netcrackerquizb.exception.AnswerDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.QuizDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.*;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;
import ua.netcracker.netcrackerquizb.service.GameService;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

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

        Set<QuizAccomplishedImpl> quizAccomplishedSet = userAccomplishedQuizDAO.getAccomplishedQuizesByUser(userId);
        for (QuizAccomplishedImpl quizAccomplishedFromSet: quizAccomplishedSet) {
            if(quizAccomplishedFromSet.getQuizId().equals(quizId)
                    && quizAccomplishedFromSet.getCorrectAnswers() < counterOfCorrectAnswers) {
                quizAccomplishedFromSet.setCorrectAnswers(counterOfCorrectAnswers);
            } else {
                QuizAccomplishedImpl quizAccomplished = new QuizAccomplishedImpl(counterOfCorrectAnswers, quizId);
                userAccomplishedQuizDAO.addAccomplishedQuiz(userId, quizAccomplished);
            }
            break;
        }
    }

    @Override
    public void addToFavorite(User user, QuizAccomplishedImpl quizAccomplished) throws DAOLogicException {
        Boolean isFavorite = quizAccomplished.getFavourite();
        BigInteger userId = user.getId();
        quizAccomplished.setFavourite(!isFavorite);
        userAccomplishedQuizDAO.setIsFavoriteQuiz(userId, quizAccomplished);
    }
}
