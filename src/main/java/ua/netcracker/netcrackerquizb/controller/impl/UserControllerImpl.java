package ua.netcracker.netcrackerquizb.controller.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ua.netcracker.netcrackerquizb.controller.UserController;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MailException;
import ua.netcracker.netcrackerquizb.exception.MessagesForException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import ua.netcracker.netcrackerquizb.service.Impl.UserServiceImpl;
import ua.netcracker.netcrackerquizb.service.MailSenderService;
import ua.netcracker.netcrackerquizb.service.UserService;
import ua.netcracker.netcrackerquizb.util.RegexPatterns;

import java.math.BigInteger;
import java.util.List;

@Controller
public class UserControllerImpl implements UserController, RegexPatterns {

    private static final Logger log = Logger.getLogger(UserControllerImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public void createUser(String email, String password, String name, String surname) {
        try {
            userService.validateNewUser(email, password, name, surname);
            BigInteger userId = userService.buildNewUser(email, password, name, surname);

            User user = new UserImpl.UserBuilder()
                    .setId(userId)
                    .setEmail(email)
                    .build();

            mailSenderService.sendEmail(user);
        } catch (DAOLogicException e) {
            log.error("a");
        } catch (UserException e) {
            log.error("a");
        } catch (MailException e) {
            log.error("a");
        }
    }

    @Override
    public void tryToAuthorize(String email, String password) {
        try {
            if(!email.matches(mailPattern)) {
                throw new UserException(MessagesForException.INVALID_EMAIL);
            }
            if(StringUtils.isEmpty(password)) {
                throw new UserException(MessagesForException.INVALID_PASSWORD);
            }
            User user = new UserImpl.UserBuilder()
                    .setEmail(email)
                    .setPassword(password)
                    .build();
            userService.authorize(user);
        } catch (DAOLogicException e) {
            log.error("a");
        } catch (UserException e) {
            log.error("a");
        }
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void editUser(User user) {

    }

    @PostMapping("/recover")
    @Override
    public void recoverPassword(User user) {
        try {
            userService.recoverPassword(user);
        } catch (DAOLogicException e) {
            log.error("a");
        } catch (MailException e) {
            log.error("a");
        } catch (UserException e) {
            log.error("a");
        }
    }


    @Override
    public void updatePassword() {

    }

    @Override
    public void registrationConfirm(User user) {

    }

    @Override
    public void confirmEmail(String confirmationCode) {

    }

    @Override
    public User getUser(BigInteger userId) {
        return null;
    }

    @Override
    public List<Quiz> getAccomplishedQuizes(BigInteger userId) {
        return null;
    }
}
