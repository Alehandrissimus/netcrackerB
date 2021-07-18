package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Question;

import java.math.BigInteger;
import java.util.Collection;

public interface QuestionDAO {
    Question getQuestionById(BigInteger id);

    void createQuestion(Question question);

    void deleteQuestion(Question question);

    Collection<Question> getAllQuestions();

    void updateQuestion(Question question);

    void updateAllQuestionAnswers(Collection<Question> questions);
}
