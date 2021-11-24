package ch.heigvd.api.model.mail;

import java.util.ArrayList;
import java.util.Collections;

public class Group {
    private ArrayList<Person> members;
    
    public Group(ArrayList<Person> members) {
        this.members = members;
    }
    
    public ArrayList<Person> getMembers() {
        return members;
    }
    
    public void shuffleMembers(){
        Collections.shuffle(members);
    }
}
