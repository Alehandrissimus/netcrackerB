package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Answer;

import java.math.BigInteger;

public interface AnswerDAO {
    Answer getAnswerById(BigInteger id);

    Answer getAnswerByTitle(String title);

    void createAnswer(Answer answer);

    void deleteAnswer(BigInteger id);

    void updateAnswer(Answer answer);
}
