package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Answer;

import java.math.BigInteger;

public interface AnswerDAO {
    Answer getAnswerById(BigInteger id);

    void createAnswer(Answer answer);

    void deleteAnswer(Answer answer);

    void updateAnswer(Answer answer);
}
