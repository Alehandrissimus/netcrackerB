package ua.netcracker.netcrackerquizb.exception;

public class AnswerDoesNotExistException  extends Exception implements MessagesForException {

    public AnswerDoesNotExistException (String errorMessage) {
        super(errorMessage);
    }
    public AnswerDoesNotExistException (String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

    @Override
    public String toString() {
        return "Answer does not exist!\n" + super.toString();
    }
}
