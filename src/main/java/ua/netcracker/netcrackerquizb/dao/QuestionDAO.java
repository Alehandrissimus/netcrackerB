package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {
    Question getQuestionById(BigInteger id, Collection<Answer> answers);

    void createQuestion(Question question, BigInteger id);

    void deleteQuestion(Question question, BigInteger id);

    Collection<Question> getAllQuestions(BigInteger id);

    Question updateQuestion(Question question);

    Collection<Question> updateAllQuestionAnswers(Collection<Question> questions);
}
