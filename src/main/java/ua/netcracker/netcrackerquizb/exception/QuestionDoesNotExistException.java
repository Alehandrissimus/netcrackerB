package ua.netcracker.netcrackerquizb.exception;

public class QuestionDoesNotExistException extends Exception {

    public QuestionDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }

    public QuestionDoesNotExistException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

    @Override
    public String toString() {
        return "Question not found";
    }
}
