package ch.heigvd.api.smtp;

import ch.heigvd.api.model.mail.Mail;

import java.io.IOException;

public interface ISmtpClient {
    void sendMail(Mail mail) throws IOException;
}
