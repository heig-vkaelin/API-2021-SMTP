package ch.heigvd.api.model.prank;

import ch.heigvd.api.model.mail.Group;
import ch.heigvd.api.model.mail.Message;

import java.util.Random;

public class PrankGenerator {
    public Message[] messages;
    public Group[] groups;
    
    public PrankGenerator(Message[] messages, Group[] groups) {
        this.messages = messages;
        this.groups = groups;
    }
    
    public void createPrank(Group[] groups){
        for (Group group : groups) {
            createPrank(group);
        }
    }
    
    public void createPrank(Group group){
    }
}
