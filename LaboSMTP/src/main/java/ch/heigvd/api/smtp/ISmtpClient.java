package ch.heigvd.api.smtp;

import ch.heigvd.api.model.mail.Message;

import java.io.IOException;

public interface ISmtpClient {
    public void sendMessage(Message message) throws IOException;
}
