package ch.heigvd.api.model.mail;

public class Person {
    private final String mailAddress;
    
    public String getMailAddress() {
        return mailAddress;
    }
    
    public Person(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
