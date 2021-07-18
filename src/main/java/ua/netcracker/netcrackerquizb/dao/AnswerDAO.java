package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.impl.AnswerImpl;

import java.math.BigInteger;

public interface AnswerDAO {
    AnswerImpl getAnswerById(BigInteger id);

    void createAnswer();

    void deleteAnswer(AnswerImpl answer);

    AnswerImpl updateAnswer(AnswerImpl answer);
}
