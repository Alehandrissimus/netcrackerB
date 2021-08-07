package ua.netcracker.netcrackerquizb.model;

import java.math.BigInteger;

public interface Answer {
    String getValue();

    BigInteger getId();

    Boolean getAnswer();

    BigInteger getQuestionId();

    void setId(BigInteger id);

    void setValue(String value);

    void setAnswer(Boolean answer);

    void setQuestionId(BigInteger questionId);
}
