package ch.heigvd.api.config;

import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;

import java.util.List;

public interface IConfigurationManager {
    int MIN_SIZE_GROUP = 3;
    
    String getSmtpServerAddress();
    
    int getSmtpServerPort();
    
    int getNumberOfGroups();
    
    Person getWitnessesToCC();
    
    List<Person> getVictims();
    
    List<Message> getMessages();
}
