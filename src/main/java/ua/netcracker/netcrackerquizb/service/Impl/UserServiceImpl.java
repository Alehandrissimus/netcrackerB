package ua.netcracker.netcrackerquizb.service.Impl;

import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import ua.netcracker.netcrackerquizb.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void buildNewUser(String email, String password, String name, String surname) {
    }

    @Override
    public void authorize(User user) {

    }

    @Override
    public User recoverPassword(User user) {
        return null;
    }

    @Override
    public void validateNewUser(User user) {

    }
}
