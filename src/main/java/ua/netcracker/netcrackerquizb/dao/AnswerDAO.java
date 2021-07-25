package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Answer;

import java.math.BigInteger;

public interface AnswerDAO {
    String SQL_ANSWER_ID = "id_answer";
    String SQL_MAX_ID_ANSWER = "MAX(ID_ANSWER)";
    String SQL_ANSWER_TEXT = "text";
    String SQL_ANSWER_IS_TRUE = "is_true";
    String SQL_ANSWER_QUESTION = "question";

    Answer getAnswerById(BigInteger id);

    BigInteger getLastAnswerIdByTitle(String title);

    BigInteger createAnswer(Answer answer);

    void deleteAnswer(BigInteger id);

    BigInteger updateAnswer(Answer answer);
}
