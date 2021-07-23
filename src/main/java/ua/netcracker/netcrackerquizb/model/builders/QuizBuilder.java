package ua.netcracker.netcrackerquizb.model.builders;

import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.QuizType;

import java.math.BigInteger;
import java.util.Date;

public class QuizBuilder implements Quiz{

    private BigInteger id;
    private String title;
    private String description;
    private QuizType quizType;
    private Date creationDate;
    private BigInteger creatorId;

    private QuizBuilder() {
    }

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

    public static Builder newBuilder() {
        return new QuizBuilder().new Builder();
    }

    @Override
    public String toString() {
        return "QuizBuilder{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", quizType=" + quizType +
                ", creationDate=" + creationDate +
                ", creatorId=" + creatorId +
                '}';
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(BigInteger id) {
            QuizBuilder.this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            QuizBuilder.this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            QuizBuilder.this.description = description;
            return this;
        }

        public Builder setQuizType(QuizType quizType) {
            QuizBuilder.this.quizType = quizType;
            return this;
        }

        public Builder setCreationDate(Date creationDate) {
            QuizBuilder.this.creationDate = creationDate;
            return this;
        }

        public Builder setCreatorId(BigInteger creatorId) {
            QuizBuilder.this.creatorId = creatorId;
            return this;
        }

        public QuizBuilder build() {
            return QuizBuilder.this;
        }
    }

}
