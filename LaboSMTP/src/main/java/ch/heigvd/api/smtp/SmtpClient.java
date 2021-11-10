package ch.heigvd.api.smtp;

import ch.heigvd.api.model.mail.Message;

import java.io.IOException;

public class SmtpClient implements ISmtpClient {
    
    private String smtpServerAddress;
    private int smtpServerPort = 25;
    
    @Override
    public void sendMessage(Message message) throws IOException {
        // TODO
    }
}
