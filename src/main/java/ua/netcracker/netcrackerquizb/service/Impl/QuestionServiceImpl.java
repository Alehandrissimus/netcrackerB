package ua.netcracker.netcrackerquizb.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.AnswerDAO;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.service.QuestionService;

import java.math.BigInteger;
import java.util.Collection;

@Service
public class QuestionServiceImpl implements QuestionService, MessagesForException {

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
        if(question.getQuestion().equals("")) {
            throw new QuestionException(QUESTION_EMPTY);
        }
        for(Answer answer : question.getAnswers()) {
            if(answer.getValue().equals("")) {
                throw new QuestionException(ANSWER_EMPTY);
            }
        }
        Collection<Question> questions = questionDAO.getAllQuestions(quizId);
        for (Question questionElement : questions) {
            if(question.getQuestion().equals(questionElement.getQuestion())) {
                throw new QuestionException(QUESTION_DUPLICATE);
            }
        }

        Question createdQuestion = questionDAO.createQuestion(question, quizId);

        for(Answer answer : question.getAnswers()) {
            answer.setQuestionId(createdQuestion.getId());
            answerDAO.createAnswer(answer);
        }
        return createdQuestion;
    }

    @Override
    public void updateQuestion(Question updatedQuestion) throws DAOLogicException {
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
    public Collection<Question> getQuestionsByQuiz(BigInteger quizId)
            throws DAOLogicException, QuestionDoesNotExistException, AnswerDoesNotExistException {
        Collection<Question> questions = questionDAO.getAllQuestions(quizId);
        for(Question question: questions) {
            Collection<Answer> answers = answerDAO.getAnswersByQuestionId(question.getId());
            question.setAnswers(answers);
        }
        return questions;
    }
}
