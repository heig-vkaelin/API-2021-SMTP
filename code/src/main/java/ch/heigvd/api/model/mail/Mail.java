package ch.heigvd.api.model.mail;

import java.util.List;

public class Mail extends Message {
    private final String from;
    private final List<String> to;
    private final String bcc;
    
    public Mail(String subject, String content, String from, List<String> to,
                String bcc) {
        super(subject, content);
        
        this.from = from;
        this.to = to;
        this.bcc = bcc;
    }
    
    public String getFrom() {
        return from;
    }
    
    public List<String> getTo() {
        return to;
    }
    
    public String getBcc() {
        return bcc;
    }
}
