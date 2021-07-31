package ua.netcracker.netcrackerquizb.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import ua.netcracker.netcrackerquizb.service.MailSenderService;
import ua.netcracker.netcrackerquizb.service.UserService;
import ua.netcracker.netcrackerquizb.util.RegexPatterns;

import java.math.BigInteger;
import java.util.List;

@RestController
public class UserController implements RegexPatterns {

    private static final Logger log = Logger.getLogger(UserController.class);

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
        } catch (DAOLogicException | MailException | UserException e) {
            log.error("a");
        }
    }

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    //return user?
    @PostMapping("/login")
    public void tryToAuthorize(String email, String password) {
        log.info("email: " + email + ", password: " + password);
        try { 
            if(!email.matches(mailPattern)) {
                throw new UserException(MessagesForException.INVALID_EMAIL);
            }
            if(!password.matches(passPattern)) {
                throw new UserException(MessagesForException.INVALID_PASSWORD);
            }
            User user = new UserImpl.UserBuilder()
                    .setEmail(email)
                    .setPassword(password)
                    .build();
            User receivedUser = userService.authorize(user);
        } catch (DAOLogicException | UserException e) {
            log.error(e.getMessage());
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
        } catch (DAOLogicException | MailException | UserException e) {
            log.error("a");
        }
    }

    @PutMapping("/updatePassword")
    public void updatePassword(BigInteger id, String newPassword) {

        //free
        //userService.updateUsersPassword(id, newPassword);
    }

    public void registrationConfirm(User user) {
        //free
    }

    public void confirmEmail(String confirmationCode) {
        //free
    }

    public User getUser(BigInteger userId) {
        //User user = userService.getUserById(userId);
        return null;
    }

    public List<Quiz> getAccomplishedQuizes(BigInteger userId) {
        //free
        //return userService.getAccomplishedQuizById(userId);
        return null;
    }
}
