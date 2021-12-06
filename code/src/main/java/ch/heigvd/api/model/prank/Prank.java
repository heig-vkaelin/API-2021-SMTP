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
    private final Message message;
    
    public Prank(Group group, Message message) {
        this.group = group;
        this.message = message;
    }
    
    public Mail generateMail(Person bcc) {
        List<String> to = group.getReceivers().stream()
                .map(Person::getMailAddress)
                .collect(Collectors.toList());
        
        return new Mail(
                message.getSubject(),
                message.getContent(),
                group.getSender().getMailAddress(),
                to,
                bcc.getMailAddress()
        );
    }
}
