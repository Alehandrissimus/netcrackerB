package ua.netcracker.netcrackerquizb.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface Quiz {

    void setId(BigInteger id);

    BigInteger getId();

    void setTitle(String title);

    String getTitle();

    void setDescription(String description);

    String getDescription();

    void setQuizType(QuizType quizType);

    QuizType getQuizType();

    void setCreationDate(Date creationDate);

    Date getCreationDate();

    void setCreatorId(BigInteger creatorId);

    BigInteger getCreatorId();

    List<Question> getQuestions();

    void setQuestions(List<Question> questions);

}


