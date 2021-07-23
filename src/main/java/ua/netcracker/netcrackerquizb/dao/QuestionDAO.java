package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {
    Question getQuestionById(BigInteger id, Collection<Answer> answers);

    void createQuestion(Question question, BigInteger id);

    void deleteQuestion(Question question, BigInteger id);

    Collection<Question> getAllQuestions(BigInteger id);

    void updateQuestion(Question question);

}
