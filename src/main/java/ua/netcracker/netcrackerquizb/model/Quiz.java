package ua.netcracker.netcrackerquizb.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Quiz {

//    BigInteger getId();
//    String getTitle();
//    Date getCreationDate();
//    QuizType getType();
//    User getCreator();
//    List<Question> getQuestions();

    private BigInteger id;
    private String title;
    private String description;
    private QuizType quizType;
    private Date creationDate;
    private BigInteger creatorId;

    public Quiz() {

    }

    public Quiz(String title, QuizType quizType, Date creationDate, BigInteger creatorId) {
        this.title = title;
        this.quizType = quizType;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public BigInteger getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(BigInteger creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", quizType=" + quizType +
                ", creationDate=" + creationDate +
                ", creatorId=" + creatorId +
                '}';
    }
}


