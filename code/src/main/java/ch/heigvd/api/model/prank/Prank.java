package ch.heigvd.api.model.prank;

import ch.heigvd.api.model.mail.Mail;
import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;
import ch.heigvd.api.model.mail.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {
    private final Person sender;
    private final List<Person> people;
    private final Person copy;
    private final Message message;
    
    public Prank(Group people, Message message, Person bcc) {
        this.message = message;
        this.people = new ArrayList<>();
        // 1er personne de la liste people: le sender
        this.sender =  people.getMembers().get(0);

        for (int i = 1; i < people.getMembers().size(); i++) {
            this.people.add(people.getMembers().get(i));
        }
        this.copy = bcc;
    }
    
    public Mail generateMail() {
        Mail mail = new Mail(message.getSubject(), message.getContent());
        
        mail.setBcc(copy.getMailAddress());
        mail.setFrom(sender.getMailAddress());
        mail.setTo(people.stream().map(Person::getMailAddress).collect(Collectors.toList()));
        
        return mail;
    }
}
