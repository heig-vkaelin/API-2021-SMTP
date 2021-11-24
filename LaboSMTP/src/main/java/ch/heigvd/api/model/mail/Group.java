package ch.heigvd.api.model.mail;

import java.util.ArrayList;

public class Group {
    private ArrayList<Person> members;
    
    public Group(ArrayList<Person> members) {
        this.members = members;
    }
    
    public ArrayList<Person> getMembers() {
        return members;
    }
}
