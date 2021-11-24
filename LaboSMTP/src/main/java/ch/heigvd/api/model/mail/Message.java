package ch.heigvd.api.model.mail;

import java.util.ArrayList;

public class Message {
    private String subject;
    private String content;
    private String from;
    private ArrayList<String> to;
    private String bcc;
    
    public Message(String subject, String content) {
        this.subject = subject;
        this.content = content;
        to = new ArrayList<>();
    }
    public String getSubject() {
        return subject;
    }
    
    public String getContent() {
        return content;
    }
    
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public ArrayList<String> getTo() {
        return to;
    }
    
    public void setTo(ArrayList<String> to) {
        this.to = to;
    }
    
    public String getBcc() {
        return bcc;
    }
    
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }
}
