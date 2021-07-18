package ua.netcracker.netcrackerquizb.model.impl;

import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;
import ua.netcracker.netcrackerquizb.model.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public class QuizImpl implements Quiz {

    BigInteger id;
    String title;
    Collection<Question> questions;
    QuizType quizType;
    Date creationDate;
    User author;


    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public QuizType getType() {
        return quizType;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public Collection<Question> getQuestions() {
        return questions;
    }
}
