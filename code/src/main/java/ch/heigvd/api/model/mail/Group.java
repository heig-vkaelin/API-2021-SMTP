package ch.heigvd.api.model.mail;

import ch.heigvd.api.config.IConfigurationManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private Person sender;
    private final List<Person> receivers;
    
    public Group() {
        this(new ArrayList<>());
    }
    
    public Group(List<Person> receivers) {
        this.receivers = receivers;
    }
    
    public Person getSender() {
        return sender;
    }
    
    public List<Person> getReceivers() {
        return receivers;
    }
    
    public void addReceiver(Person receiver) {
        receivers.add(receiver);
    }
    
    public void setRandomSender() {
        if (receivers.size() < IConfigurationManager.MIN_SIZE_GROUP)
            throw new RuntimeException("The group hasn't enough members to choose " +
                    "a sender!");
        
        // Mélange afin d'avoir un expéditeur aléatoire
        Collections.shuffle(receivers);
        this.sender = receivers.get(0);
        this.receivers.remove(0);
    }
}
