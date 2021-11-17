package ch.heigvd.api.model.mail;

public class Group {
    public Person sender;
    public Person[] receivers;
    
    public Group(Person sender, Person[] receivers) {
        this.sender = sender;
        this.receivers = receivers;
    }
}
