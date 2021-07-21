package ua.netcracker.netcrackerquizb.model.impl;

import ua.netcracker.netcrackerquizb.model.Answer;

import java.math.BigInteger;

public class AnswerImpl implements Answer {

    private BigInteger id;
    private String value;
    private Boolean answer = false;
    private BigInteger questionId;

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public Boolean getAnswer() {
        return answer;
    }

    @Override
    public BigInteger getQuestionId() {
        return null;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @Override
    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
    }

}
