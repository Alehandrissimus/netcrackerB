package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {
    QuestionImpl getQuestionById(BigInteger id);

    void createQuestion(QuestionImpl question);

    void deleteQuestion(QuestionImpl question);

    Collection<QuestionImpl> getAllQuestions();

    QuestionImpl updateQuestion(QuestionImpl question);

    Collection<QuestionImpl> updateAllQuestionAnswers(Collection<QuestionImpl> questions);
}
