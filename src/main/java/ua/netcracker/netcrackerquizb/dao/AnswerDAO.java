package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;

import java.math.BigInteger;

public interface AnswerDAO {
    Answer getAnswerById(BigInteger id);

    void createAnswer();

    void deleteAnswer(Answer answer);

    Answer updateAnswer(Answer answer);
}