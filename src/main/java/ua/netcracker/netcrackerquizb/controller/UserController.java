package ua.netcracker.netcrackerquizb.controller;

import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface UserController {

    void createUser(String email, String password, String name, String surname);

    void tryToAuthorize(String email, String password);

    void deleteUser(User user);

    void editUser(User user);

    void recoverPassword(User user);

    void updatePassword();

    void registrationConfirm(User user);

    void confirmEmail(String confirmationCode);

    User getUser(BigInteger userId);

    List<Quiz> getAccomplishedQuizes(BigInteger userId);
}
