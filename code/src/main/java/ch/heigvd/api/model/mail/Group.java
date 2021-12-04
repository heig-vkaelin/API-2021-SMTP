package ch.heigvd.api.model.mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private final List<Person> members;
    
    public Group() {
        this(new ArrayList<>());
    }
    
    public Group(List<Person> members) {
        this.members = members;
    }
    
    public List<Person> getMembers() {
        return members;
    }
    
    public void addMember(Person member) {
        members.add(member);
    }
    
    public void shuffleMembers() {
        Collections.shuffle(members);
    }
}
