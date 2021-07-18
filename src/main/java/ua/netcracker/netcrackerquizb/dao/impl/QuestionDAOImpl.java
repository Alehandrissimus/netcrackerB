package ua.netcracker.netcrackerquizb.dao.impl;

import ua.netcracker.netcrackerquizb.dao.QuestionDAO;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

import java.math.BigInteger;
import java.util.Collection;

public class QuestionDAOImpl implements QuestionDAO {

    @Override
    public QuestionImpl getQuestionById(BigInteger id) {
        return null;
    }

    @Override
    public void createQuestion(QuestionImpl question) {

    }

    @Override
    public void deleteQuestion(QuestionImpl question) {

    }

    @Override
    public Collection<QuestionImpl> getAllQuestions() {
        return null;
    }

    @Override
    public QuestionImpl updateQuestion(QuestionImpl question) {
        return null;
    }

    @Override
    public Collection<QuestionImpl> updateAllQuestionAnswers(Collection<QuestionImpl> questions) {
        return null;
    }
}
