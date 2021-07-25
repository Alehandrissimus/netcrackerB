package ua.netcracker.netcrackerquizb.exception;

public class AnswerDoesNotExistException extends Exception {
    @Override
    public String toString() {
        return "Answer does not exist!\n" + super.toString();
    }
}
