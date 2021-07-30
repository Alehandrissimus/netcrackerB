package ua.netcracker.netcrackerquizb.rest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.MailException;
import ua.netcracker.netcrackerquizb.exception.MessagesForException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import ua.netcracker.netcrackerquizb.service.MailSenderService;
import ua.netcracker.netcrackerquizb.service.UserService;
import ua.netcracker.netcrackerquizb.util.RegexPatterns;

import java.math.BigInteger;
import java.util.List;

@Controller
public class UserControllerImpl implements RegexPatterns {

    private static final Logger log = Logger.getLogger(UserControllerImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailSenderService;

    @PostMapping("/register")
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

    @PostMapping("/login")
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
            User receivedUser = userService.authorize(user);
        } catch (DAOLogicException e) {
            log.error("a");
        } catch (UserException e) {
            log.error("a");
        }
    }

    public void deleteUser(User user) {

    }

    public void editUser(User user) {

    }

    @PostMapping("/recover")
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


    public void updatePassword() {

    }

    public void registrationConfirm(User user) {

    }

    public void confirmEmail(String confirmationCode) {

    }

    public User getUser(BigInteger userId) {
        return null;
    }

    public List<Quiz> getAccomplishedQuizes(BigInteger userId) {
        return null;
    }
}
