package ua.netcracker.netcrackerquizb.exception;

public class QuestionNotFoundException extends Exception {
    @Override
    public String toString() {
        return "Question not found";
    }
}
