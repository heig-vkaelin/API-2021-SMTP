package ch.heigvd.api.model.mail;

public class Person {
    private String mailAdress;
    
    public String getMailAdress() {
        return mailAdress;
    }
    public Person(String mailAdress) {
        this.mailAdress = mailAdress;
    }
}
