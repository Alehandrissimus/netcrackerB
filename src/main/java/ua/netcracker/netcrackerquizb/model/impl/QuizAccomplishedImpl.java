package ua.netcracker.netcrackerquizb.model.impl;

import java.math.BigInteger;

public class QuizAccomplishedImpl {
    private int correctAnswers;
    private Boolean isFavourite;
    private BigInteger quizId;

    public QuizAccomplishedImpl(int correctAnswers, Boolean isFavourite, BigInteger quizId) {
        this.correctAnswers = correctAnswers;
        this.isFavourite = isFavourite;
        this.quizId = quizId;
    }

    public QuizAccomplishedImpl(int correctAnswers, BigInteger quizId) {
        this.correctAnswers = correctAnswers;
        this.quizId = quizId;
    }

    public QuizAccomplishedImpl() {
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setBoolFavourite(int isFavourite) {
        if(isFavourite == 1)
            this.isFavourite = true;
        else if(isFavourite == 0)
            this.isFavourite = false;
    }

    public int getIntFavourite() {
        if(this.isFavourite)
            return 1;
        else return 0;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public BigInteger getQuizId() {
        return quizId;
    }

    public void setQuizId(BigInteger quizId) {
        this.quizId = quizId;
    }

}
