package ch.heigvd.api.model.prank;

import ch.heigvd.api.config.ConfigurationManager;
import ch.heigvd.api.model.mail.Group;
import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PrankGenerator {
    private final ConfigurationManager cm;
    private static final Random random = new Random();
    
    public PrankGenerator(ConfigurationManager cm) {
        this.cm = cm;
    }
    
    private ArrayList<Group> createGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Person> remainingVictims = new ArrayList<>(cm.getVictims());
        Collections.shuffle(remainingVictims);
        
        for (int i = 0; i < cm.getNumberOfGroups(); i++) {
            groups.add(new Group());
        }
        
        int turn = 0;
        while (remainingVictims.size() > 0) {
            int lastIndex = remainingVictims.size() - 1;
            groups.get(turn).addMember(remainingVictims.get(lastIndex));
            remainingVictims.remove(lastIndex);
            turn = (turn + 1) % groups.size();
        }
        return groups;
    }
    
    public ArrayList<Prank> generatePranks() {
        ArrayList<Prank> pranks = new ArrayList<>();
        ArrayList<Group> groups = createGroups();
        ArrayList<Message> messages = cm.getMessages();
        
        for (Group group : groups) {
            group.shuffleMembers();
            Prank prank = new Prank(
                    group.getMembers(),
                    messages.get(random.nextInt(messages.size())),
                    cm.getWitnessesToCC());
            pranks.add(prank);
        }
        return pranks;
    }
}
