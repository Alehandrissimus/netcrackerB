package ua.netcracker.netcrackerquizb.exception;

public class QuizException extends Exception implements MessagesForException{
    public QuizException(String message) {
        super(message);
    }

    public QuizException(String message, Throwable cause) {
        super(message, cause);
    }
}
