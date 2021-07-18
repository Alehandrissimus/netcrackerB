package ua.netcracker.netcrackerquizb.dao.impl;

import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

import java.math.BigInteger;
import java.util.Collection;

public class QuestionDAOImpl implements QuestionDAO {

    @Override
    public Question getQuestionById(BigInteger id) {
        return null;
    }

    @Override
    public void createQuestion(Question question) {

    }

    @Override
    public void deleteQuestion(Question question) {

    }

    @Override
    public Collection<Question> getAllQuestions() {
        return null;
    }

    @Override
    public Question updateQuestion(Question question) {
        return null;
    }

    @Override
    public Collection<Question> updateAllQuestionAnswers(Collection<Question> questions) {
        return null;
    }
}
