package ua.netcracker.netcrackerquizb.service.Impl;

import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.QuestionDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.service.QuestionService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDAO questionDAO;

    @Override
    public Question createQuestion(Question question, BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException {
        if(question.getQuestion().equals("")) {
            return null; // TODO some custom exc?
        }
        Collection<Question> questions = questionDAO.getAllQuestions(quizId);
        for (Question questionElement : questions) {
            if(question.getQuestion().equals(questionElement.getQuestion())) {
                throw new QuestionDoesNotExistException("");
            }
        }
        return questionDAO.createQuestion(question, quizId);
    }

    @Override
    public void updateQuestion(Question updatedQuestion) throws DAOLogicException, QuestionDoesNotExistException {
        if(questionDAO.getQuestionById(updatedQuestion.getId(), new ArrayList<>()) == null) {
            throw new QuestionDoesNotExistException("");
        }
        questionDAO.updateQuestion(updatedQuestion);
    }

    @Override
    public void deleteQuestion(Question question) throws DAOLogicException, QuestionDoesNotExistException {
        if(questionDAO.getQuestionById(question.getId(), new ArrayList<>()) == null) {
           throw new QuestionDoesNotExistException("");
        }
        questionDAO.deleteQuestion(question);
    }

    @Override
    public Question getQuestionById(BigInteger questionId) throws DAOLogicException, QuestionDoesNotExistException {
        return questionDAO.getQuestionById(questionId, new ArrayList<>());  // TODO get collection from AnswerService
    }

    @Override
    public Question getQuestionByData(String questionText, BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException {
        return questionDAO.getQuestionByData(questionText, quizId);
    }

    @Override
    public Collection<Question> getQuestionsByQuiz(BigInteger quizId) throws DAOLogicException, QuestionDoesNotExistException {
        return questionDAO.getAllQuestions(quizId); // TODO collections from AnswerService?
    }
}
