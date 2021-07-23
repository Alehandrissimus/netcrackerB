package ua.netcracker.netcrackerquizb.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

public interface Quiz {

    BigInteger getId();

    void setId(BigInteger id);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    QuizType getQuizType();

    void setQuizType(QuizType quizType);

    Date getCreationDate();

    void setCreationDate(Date creationDate);

    BigInteger getCreatorId();

    void setCreatorId(BigInteger creatorId);

}


