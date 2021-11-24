package ch.heigvd.api.model.prank;

import ch.heigvd.api.config.ConfigurationManager;
import ch.heigvd.api.model.mail.Group;
import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PrankGenerator {
    ConfigurationManager cm;
    
    public PrankGenerator() {
        try{
            cm = new ConfigurationManager();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        createPrank();
    }
    
    private ArrayList<Group> createGroups(){
        ArrayList<Group> groups =  new ArrayList<>();
        int groupSize = cm.getVictims().size() / cm.getNumberOfGroups();
        for (int i = 0; i < cm.getNumberOfGroups() ;++i){
            Group group;
            if(i != cm.getNumberOfGroups()-1){
                group = new Group((ArrayList<Person>) cm.getVictims().subList((i*groupSize),
                        ((i*groupSize)+groupSize)));
            } else{
                group = new Group((ArrayList<Person>) cm.getVictims().subList((i*groupSize)
                        , cm.getVictims().size()));
            }
            groups.add(group);
        }
        return groups;
    }
    
    public void createPrank(){
        ArrayList<Group> groups = createGroups();
        Collections.shuffle(groups);
        ArrayList<Message> messages = cm.getMessages();
        Random random = new Random();
        for (Group group : groups){
            group.shuffleMembers();
            Prank prank = new Prank(group.getMembers().get(0),
                    (ArrayList<Person>) group.getMembers().subList(1,group.getMembers().size()),
                    messages.get(random.nextInt(messages.size())),cm.getWitnessesToCC());
            try{
                prank.sendPrank();
            } catch (IOException e){
                System.out.println("Erreur lors de l'envoi d'un prank");
            }
        }
    
    }
}
