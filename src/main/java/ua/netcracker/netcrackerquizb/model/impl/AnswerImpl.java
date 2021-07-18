package ua.netcracker.netcrackerquizb.model.impl;

import ua.netcracker.netcrackerquizb.model.Answer;

public class AnswerImpl implements Answer {

    private String value;
    private Boolean answer;

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Boolean isTrue() {
        return false;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

}
