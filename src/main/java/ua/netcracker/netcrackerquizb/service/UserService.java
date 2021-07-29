package ua.netcracker.netcrackerquizb.service;

import java.math.BigInteger;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.User;

public interface UserService {
    BigInteger buildNewUser(String email, String password, String name, String surname)
        throws UserException, DAOLogicException;

    User authorize(User user) throws DAOLogicException, UserException;

    User recoverPassword(User user);

    void validateNewUser(User user);
}
