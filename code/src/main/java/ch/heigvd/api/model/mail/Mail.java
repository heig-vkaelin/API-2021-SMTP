package ch.heigvd.api.model.mail;

import java.util.ArrayList;
import java.util.List;

public class Mail extends Message {
    private String from;
    private List<String> to;
    private String bcc;
    
    public Mail(String subject, String content) {
        super(subject, content);
        to = new ArrayList<>();
    }
    
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public List<String> getTo() {
        return to;
    }
    
    public void setTo(List<String> to) {
        this.to = to;
    }
    
    public String getBcc() {
        return bcc;
    }
    
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }
}
