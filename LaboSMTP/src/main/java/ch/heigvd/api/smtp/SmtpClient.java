package ch.heigvd.api.smtp;

import ch.heigvd.api.model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private static final String EOL = "\r\n";
    
    private final String smtpServerAddress;
    private final int smtpServerPort;
    
    private Socket socket;
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
    
    private void sendLine(String line) {
        writer.write(line + EOL);
        writer.flush();
    }
    
    private void sendLineVerified(String line) throws IOException {
        sendLine(line);
        checkSmtpServerResponse();
    }
    
    @Override
    public void sendMessage(Message message) throws IOException {
        LOG.info("Send message with SMTP protocol.");
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
        sendLineVerified("MAIL FROM:" + message.getFrom());
        
        // TO
        for (String to : message.getTo()) {
            sendLineVerified("RCPT TO:" + to);
        }
        
        // BCC
        for (String to : message.getTo()) {
            sendLineVerified("RCPT TO:" + to);
        }
        
        // CONTENT
        sendLine("DATA");
        
        line = reader.readLine();
        LOG.info(line);
        writer.write("Content-type: text/plain; charset=utf-8" + EOL);
        writer.write("From: " + message.getFrom() + EOL);
        
        writer.write("To: " + message.getTo().get(0));
        for (int i = 1; i < message.getTo().size(); ++i) {
            writer.write(", " + message.getTo().get(i));
        }
        writer.write(EOL);
        
        sendLine("Bcc: " + message.getBcc());
        
        writer.write("Subject: " + message.getSubject() + EOL);
        sendLine("");
        writer.write(message.getContent() + EOL);
        sendLine(".");
        
        line = reader.readLine();
        LOG.info(line);
        sendLine("QUIT");
        reader.close();
        writer.close();
        LOG.info("Mail delivered.");
    }
}
