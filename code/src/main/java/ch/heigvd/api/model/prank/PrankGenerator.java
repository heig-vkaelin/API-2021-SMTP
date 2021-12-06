package ch.heigvd.api.model.prank;

import ch.heigvd.api.config.IConfigurationManager;
import ch.heigvd.api.model.mail.Group;
import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PrankGenerator {
    private final IConfigurationManager cm;
    private static final Random random = new Random();
    
    public PrankGenerator(IConfigurationManager cm) {
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
            groups.get(turn).addReceiver(remainingVictims.get(lastIndex));
            remainingVictims.remove(lastIndex);
            turn = (turn + 1) % groups.size();
        }
        
        // Sélection des expéditeurs aléatoirement
        for (Group g : groups)
            g.setRandomSender();
        
        return groups;
    }
    
    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();
        List<Group> groups = createGroups();
        List<Message> messages = cm.getMessages();
        
        for (Group group : groups) {
            Prank prank = new Prank(
                    group,
                    messages.get(random.nextInt(messages.size()))
            );
            pranks.add(prank);
        }
        return pranks;
    }
}
