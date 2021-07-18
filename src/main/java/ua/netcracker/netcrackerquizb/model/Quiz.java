package ua.netcracker.netcrackerquizb.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface Quiz {

    BigInteger getId();
    String getTitle();
    QuizType getType();
    Date getCreationDate();
    User getAuthor();
    Collection<Question> getQuestions();



}


