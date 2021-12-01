package ch.heigvd.api.model.prank;

import ch.heigvd.api.config.ConfigurationManager;
import ch.heigvd.api.model.mail.Group;
import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PrankGenerator {
    private final ConfigurationManager cm;
    private static final Random random = new Random();
    
    public PrankGenerator(ConfigurationManager cm) {
        this.cm = cm;
    }
    
    private List<Group> createGroups() {
        List<Group> groups = new ArrayList<>();
        List<Person> remainingVictims = new ArrayList<>(cm.getVictims());
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
    
    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();
        List<Group> groups = createGroups();
        List<Message> messages = cm.getMessages();
        
        for (Group group : groups) {
            group.shuffleMembers();
            Prank prank = new Prank(
                    group,
                    messages.get(random.nextInt(messages.size())),
                    cm.getWitnessesToCC());
            pranks.add(prank);
        }
        return pranks;
    }
}
