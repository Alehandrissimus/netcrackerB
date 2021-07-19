package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;

import java.util.Collection;

public interface GameService {
    void sendGameQuiz(String title);
    void validateAnswers(Quiz quiz, User user, Collection<Answer> answers);
    void addToFavorite(User user, Quiz quiz);
}
