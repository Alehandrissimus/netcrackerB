package ua.netcracker.netcrackerquizb.model.impl;

import ua.netcracker.netcrackerquizb.model.Quiz;

import java.math.BigInteger;
import java.util.Objects;

public class QuizAccomplishedImpl {
    private int correctAnswers;
    private Boolean isFavourite;
    private BigInteger quiz;

    public QuizAccomplishedImpl(int correctAnswers, Boolean isFavourite, BigInteger quiz) {
        this.correctAnswers = correctAnswers;
        this.isFavourite = isFavourite;
        this.quiz = quiz;
    }
    public QuizAccomplishedImpl(){
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

    public void setBoolFavourite(int isFavourite){
        if(isFavourite == 1)
            this.isFavourite = true;
        else if(isFavourite == 0)
            this.isFavourite = false;
    }

    public int getIntFavourite(){
        if(this.isFavourite)
            return 1;
        else return 0;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public BigInteger getQuiz() {
        return quiz;
    }

    public void setQuiz(BigInteger quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "QuizAccomplishedImpl{" +
                "correctAnswers=" + correctAnswers +
                ", isFavourite=" + isFavourite +
                ", quiz=" + quiz +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizAccomplishedImpl that = (QuizAccomplishedImpl) o;
        return Objects.equals(correctAnswers, that.correctAnswers) && Objects.equals(isFavourite, that.isFavourite) && Objects.equals(quiz, that.quiz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correctAnswers, isFavourite, quiz);
    }

}
