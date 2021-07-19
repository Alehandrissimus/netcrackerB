package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.model.User;

public interface MailSenderService {
    void sendEmail(String code);

    void generateCode();

    void sendEmail(User user);
}
