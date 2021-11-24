package ch.heigvd.api.model.prank;

import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;
import ch.heigvd.api.smtp.SmtpClient;

import java.io.IOException;
import java.util.ArrayList;

public class Prank {
    private Person sender;
    private Person bcc;
    private ArrayList<Person> people;
    private Message message;
    
    public Prank(Person sender,ArrayList<Person> people, Message message, Person bcc){
        this.message = message;
        this.people = people;
        this.sender = sender;
        this.bcc = bcc;
        updateMessage();
    }
    
    private void updateMessage(){
        ArrayList<String> mails = new ArrayList<>();
        for (Person person : people){
            mails.add(person.getMailAdress());
        }
        message.setFrom(sender.getMailAdress());
        message.setTo(mails);
        message.setBcc(bcc.getMailAdress());
    }
    
    public void sendPrank() throws IOException {
        SmtpClient client = new SmtpClient();
        client.sendMessage(message);
    }
    
    
}
