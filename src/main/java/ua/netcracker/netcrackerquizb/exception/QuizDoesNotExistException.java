package ua.netcracker.netcrackerquizb.exception;

public class QuizDoesNotExistException extends Exception {
    @Override
    public String toString() {
        return "Quiz does not exist!\n" + super.toString();
    }
}
