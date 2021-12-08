package ch.heigvd.api.smtp;

import ch.heigvd.api.model.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private static final String EOL = "\r\n";
    
    private final String smtpServerAddress;
    private final int smtpServerPort;
    
    private PrintWriter writer;
    private BufferedReader reader;
    
    public SmtpClient(String address, int port) {
        this.smtpServerAddress = address;
        this.smtpServerPort = port;
    }
    
    private void checkSmtpServerResponse() throws IOException {
        String line = reader.readLine();
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error: " + line);
        }
        LOG.info(line);
    }
    
    private void sendLineVerified(String line) throws IOException {
        writer.write(line + EOL);
        writer.flush();
        checkSmtpServerResponse();
    }
    
    @Override
    public void sendMail(Mail mail) throws IOException {
        LOG.info("Send mail with SMTP protocol.");
        Socket socket = new Socket(smtpServerAddress, smtpServerPort);
        writer = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        
        String line = reader.readLine();
        LOG.info(line);
        sendLineVerified("EHLO localhost");
        while (line.startsWith("250-")) {
            line = reader.readLine();
            LOG.info(line);
        }
        
        // FROM
        sendLineVerified("MAIL FROM:" + mail.getFrom());
        
        // TO
        for (String to : mail.getTo()) {
            sendLineVerified("RCPT TO:" + to);
        }
        
        // BCC
        for (String to : mail.getTo()) {
            sendLineVerified("RCPT TO:" + to);
        }
        
        // DATA
        sendLineVerified("DATA");
        writer.write("Content-Type: text/plain; charset=utf-8" + EOL);
        writer.write("From: " + mail.getFrom() + EOL);
        
        writer.write("To: " + mail.getTo().get(0));
        for (int i = 1; i < mail.getTo().size(); ++i) {
            writer.write(", " + mail.getTo().get(i));
        }
        writer.write(EOL);
        writer.write("Subject: =?utf-8?B?"
                + Base64.getEncoder().encodeToString(mail.getSubject().getBytes())
                + "?=" + EOL);
        writer.write("Subject: " + mail.getSubject() + EOL);
        
        // Content
        writer.write(EOL);
        writer.flush();
        writer.write(mail.getContent() + EOL);
        sendLineVerified(".");
        line = reader.readLine();
        LOG.info(line);
        
        // QUIT
        sendLineVerified("QUIT");
        reader.close();
        writer.close();
        socket.close();
        LOG.info("Mail delivered.\n");
    }
}
