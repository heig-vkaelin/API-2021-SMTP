package ch.heigvd.api.model.prank;

import ch.heigvd.api.model.mail.Group;

public class Prank {
    public Group victims;
    
    public Prank(Group victims) {
        this.victims = victims;
    }
}
