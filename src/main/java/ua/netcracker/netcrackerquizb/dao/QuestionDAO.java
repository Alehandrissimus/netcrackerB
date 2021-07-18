package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.impl.QuestionImpl;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {
    Question getQuestionById(BigInteger id);

    void createQuestion(Question question);

    void deleteQuestion(Question question);

    Collection<Question> getAllQuestions();

    Question updateQuestion(Question question);

    Collection<Question> updateAllQuestionAnswers(Collection<Question> questions);
}
