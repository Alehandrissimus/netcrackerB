package ua.netcracker.netcrackerquizb.model.impl;

import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Question;
import ua.netcracker.netcrackerquizb.model.QuestionType;

import java.math.BigInteger;
import java.util.Collection;

public class QuestionImpl implements Question {

    private BigInteger idQuestion;
    private String question;
    private QuestionType questionType;
    private Collection<Answer> answers;


    @Override
    public void setId(BigInteger id) {
        this.idQuestion = id;
    }

    @Override
    public BigInteger getId() {
        return idQuestion;
    }

    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public Collection<Answer> getAnswers() {
        return answers;
    }

    @Override
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    @Override
    public QuestionType getQuestionType() {
        return questionType;
    }
}
