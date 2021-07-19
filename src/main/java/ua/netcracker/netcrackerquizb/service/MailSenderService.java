package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.model.User;

public interface MailSenderService {
    void sendEmail(String code, User user);

    void generateCode();

    void confirmEmail(User user);
}
