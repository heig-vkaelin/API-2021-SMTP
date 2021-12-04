package ch.heigvd.api.model.prank;

import ch.heigvd.api.config.IConfigurationManager;
import ch.heigvd.api.model.mail.Mail;
import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;
import ch.heigvd.api.model.mail.Group;

import java.util.List;
import java.util.stream.Collectors;

public class Prank {
    private final Group group;
    private final Person copy;
    private final Message message;
    
    public Prank(Group group, Message message, Person bcc) {
        if (group.getMembers().size() < IConfigurationManager.MIN_SIZE_GROUP) {
            throw new RuntimeException("The group hasn't enough members to create " +
                    "a prank");
        }
        
        this.group = group;
        this.message = message;
        this.copy = bcc;
    }
    
    public Mail generateMail() {
        // 1ère personne de la liste: le sender
        String sender = group.getMembers().get(0).getMailAddress();
        List<String> to = group.getMembers().stream()
                .skip(1)
                .map(Person::getMailAddress)
                .collect(Collectors.toList());
        
        return new Mail(
                message.getSubject(),
                message.getContent(),
                sender,
                to,
                copy.getMailAddress()
        );
    }
}
