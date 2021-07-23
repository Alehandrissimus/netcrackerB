package ua.netcracker.netcrackerquizb.model.impl;

import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.Date;

public class QuizImpl implements Quiz {

    private BigInteger id;
    private String title;
    private String description;
    private QuizType quizType;
    private Date creationDate;
    private BigInteger creatorId;


    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public QuizType getQuizType() {
        return quizType;
    }

    @Override
    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public BigInteger getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(BigInteger creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String toString() {
        return "QuizImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", quizType=" + quizType +
                ", creationDate=" + creationDate +
                ", creatorId=" + creatorId +
                '}';
    }
}
