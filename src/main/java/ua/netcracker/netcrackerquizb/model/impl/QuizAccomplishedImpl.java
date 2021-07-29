package ua.netcracker.netcrackerquizb.model.impl;

import ua.netcracker.netcrackerquizb.model.Quiz;

import java.math.BigInteger;
import java.util.Objects;

public class QuizAccomplishedImpl {
    private BigInteger correctAnswers;
    private Boolean isFavourite;
    private Quiz quiz;

    public QuizAccomplishedImpl(BigInteger correctAnswers, Boolean isFavourite, Quiz quiz) {
        this.correctAnswers = correctAnswers;
        this.isFavourite = isFavourite;
        this.quiz = quiz;
    }

    public BigInteger getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(BigInteger correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public int getIntFavourite(){
        if(this.isFavourite)
            return 1;
        else return 0;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
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
