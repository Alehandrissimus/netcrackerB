package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.AnswerDAO;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.dao.impl.QuestionDAOImpl;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.service.QuestionService;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService, MessagesForException {

    private static final Logger log = Logger.getLogger(QuestionDAOImpl.class);

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private AnswerDAO answerDAO;

    public void setTestConnection() throws DAOConfigException {
        questionDAO.setTestConnection();
        answerDAO.setTestConnection();
    }

    @Override
    public Question createQuestion(Question question, BigInteger quizId)
            throws DAOLogicException, QuestionDoesNotExistException, QuestionException, AnswerDoesNotExistException {
        if (StringUtils.isEmpty(question.getQuestion())) {
            log.error(QUESTION_EMPTY + " in QuestionService createQuestion question: " + question.toString());
            throw new QuestionException(QUESTION_EMPTY + "in createQuestion");
        }
        for (Answer answer : question.getAnswers()) {
            if (StringUtils.isEmpty(answer.getValue())) {
                log.error(QUESTION_EMPTY + " in QuestionService createQuestion question: " + question.toString());
                throw new QuestionException(ANSWER_EMPTY + "in createQuestion");
            }
        }
        Collection<Question> questions = questionDAO.getAllQuestions(quizId);
        for (Question questionElement : questions) {
            if (StringUtils.equals(question.getQuestion(), questionElement.getQuestion())) {
                log.error(QUESTION_EMPTY + " in QuestionService createQuestion question: " + question.toString());
                throw new QuestionException(QUESTION_DUPLICATE + "in createQuestion");
            }
        }

        Question createdQuestion = questionDAO.createQuestion(question, quizId);

        for (Answer answer : question.getAnswers()) {
            answer.setQuestionId(createdQuestion.getId());
            answerDAO.createAnswer(answer);
        }
        return createdQuestion;
    }

    @Override
    public void updateQuestion(Question updatedQuestion) throws DAOLogicException, QuestionException {
        if (StringUtils.isEmpty(updatedQuestion.getQuestion())) {
            log.error(QUESTION_EMPTY + " in QuestionService updateQuestion question: " + updatedQuestion.toString());
            throw new QuestionException(QUESTION_EMPTY + " in createQuestion");
        }
        questionDAO.updateQuestion(updatedQuestion);
    }

    @Override
    public void deleteQuestion(Question question) throws DAOLogicException, QuestionDoesNotExistException {
        questionDAO.deleteQuestion(question);
    }

    @Override
    public Question getQuestionById(BigInteger questionId)
            throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException {
        return questionDAO.getQuestionById(questionId, answerDAO.getAnswersByQuestionId(questionId));
    }

    @Override
    public Question getQuestionByData(String questionText, BigInteger quizId)
            throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException {
        Question question = questionDAO.getQuestionByData(questionText, quizId);
        question.setAnswers(answerDAO.getAnswersByQuestionId(question.getId()));
        return question;
    }

    @Override
    public List<Question> getQuestionsByQuiz(BigInteger quizId)
            throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException {
        List<Question> questions = questionDAO.getAllQuestions(quizId);
        for (Question question : questions) {
            Collection<Answer> answers = answerDAO.getAnswersByQuestionId(question.getId());
            question.setAnswers(answers);
        }
        return questions;
    }
}
