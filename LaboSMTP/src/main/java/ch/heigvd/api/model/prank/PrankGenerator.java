package ch.heigvd.api.model.prank;

import ch.heigvd.api.config.ConfigurationManager;
import ch.heigvd.api.model.mail.Group;
import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;

import java.io.IOException;
import java.util.ArrayList;

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
        for (Group group : groups){
            Prank prank = new Prank();
        }
    
    }
}
