package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.model.User;

public interface UserService {
    void buildNewUser(String email, String password, String name, String surname);

    void authorize(User user);

    User recoverPassword(User user);

    void validateNewUser(User user);
}
