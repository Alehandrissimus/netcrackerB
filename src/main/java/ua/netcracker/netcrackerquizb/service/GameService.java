package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Answer;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.QuizAccomplishedImpl;

import java.util.List;

public interface GameService {
    void setTestConnection() throws DAOConfigException;
    void sendGameQuiz(String title);
    void validateAnswers(Quiz quiz, User user, List<Answer> answers) throws QuestionDoesNotExistException, DAOLogicException, AnswerDoesNotExistException, QuizDoesNotExistException;
    void setIsFavorite(User user, QuizAccomplishedImpl quizAccomplished) throws DAOLogicException;
}
