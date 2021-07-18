package ua.netcracker.netcrackerquizb.model;

import java.util.Collection;

public interface Question {

    void setQuestion(String question);

    String getQuestion();

    void setAnswers(Collection<Answer> answers);

    Collection<Answer> getAnswers();

    void setQuestionType(QuestionType questionType);

    QuestionType getQuestionType();
}
