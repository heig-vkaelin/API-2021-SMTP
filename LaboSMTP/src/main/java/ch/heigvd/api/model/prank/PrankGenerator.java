package ch.heigvd.api.model.prank;

import ch.heigvd.api.config.ConfigurationManager;
import ch.heigvd.api.model.mail.Group;
import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;
import ch.heigvd.api.smtp.SmtpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PrankGenerator {
    private ConfigurationManager cm;
    static private final Random random = new Random();
    
    public PrankGenerator() {
        try {
            cm = new ConfigurationManager();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        createPranks();
    }
    
    private ArrayList<Group> createGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Person> remaningVictims = new ArrayList<>(cm.getVictims());
        
        for (int i = 0; i < cm.getNumberOfGroups(); i++) {
            groups.add(new Group());
        }
        
        int turn = 0;
        while (remaningVictims.size() > 0) {
            int lastIndex = remaningVictims.size() - 1;
            groups.get(turn).addMember(remaningVictims.get(lastIndex));
            remaningVictims.remove(lastIndex);
            turn = (turn + 1) % groups.size();
        }
        
        return groups;
    }
    
    public void createPranks() {
        ArrayList<Group> groups = createGroups();
        Collections.shuffle(groups);
        ArrayList<Message> messages = cm.getMessages();
        for (Group group : groups) {
            group.shuffleMembers();
            Prank prank = new Prank(
                    group.getMembers(),
                    messages.get(random.nextInt(messages.size())), cm.getWitnessesToCC());
            try {
                SmtpClient client = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort());
                client.sendMessage(prank.getMessage());
            } catch (IOException e) {
                System.out.println("Erreur lors de l'envoi d'un prank");
            }
        }
        
    }
}
