package ch.heigvd.api.model.mail;

import java.util.ArrayList;
import java.util.Collections;

public class Group {
    private final ArrayList<Person> members;
    
    public Group() {
        this(new ArrayList<>());
    }
    
    public Group(ArrayList<Person> members) {
        this.members = members;
    }
    
    public ArrayList<Person> getMembers() {
        return members;
    }
    
    public void addMember(Person member) {
        members.add(member);
    }
    
    public void shuffleMembers() {
        Collections.shuffle(members);
    }
}
