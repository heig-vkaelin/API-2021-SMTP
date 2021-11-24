package ch.heigvd.api.model.prank;

import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;

import java.util.ArrayList;

public class Prank {
    private Person sender;
    private Person bcc;
    private ArrayList<Person> people;
    private Message message;
    
    // 1er personne de la liste people: le sender
    public Prank(ArrayList<Person> people, Message message, Person bcc) {
        this.message = message;
        this.people = new ArrayList<>();
        for (int i = 1; i < people.size(); i++) {
            this.people.add(people.get(i));
        }
        this.sender = people.get(0);
        this.bcc = bcc;
        updateMessage();
    }
    
    private void updateMessage() {
        ArrayList<String> mails = new ArrayList<>();
        for (Person person : people) {
            mails.add(person.getMailAdress());
        }
        message.setFrom(sender.getMailAdress());
        message.setTo(mails);
        message.setBcc(bcc.getMailAdress());
    }
    
    public Message getMessage() {
        return message;
    }
    
    
}
