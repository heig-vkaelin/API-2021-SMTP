package ch.heigvd.api.config;

import ch.heigvd.api.model.mail.Message;
import ch.heigvd.api.model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationManager implements IConfigurationManager {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    private String smtpServerAddress;
    private int smtpServerPort;
    private int numberOfGroups;
    private Person witnessesToCC;
    
    private final ArrayList<Person> victims;
    private final ArrayList<Message> messages;
    
    public ConfigurationManager() throws Exception {
        victims = loadVictims("./config/victims.utf8");
        messages = loadMessages("./config/messages.utf8");
        loadProperties("./config/config.properties");
        verifyConfig();
    }
    
    public ArrayList<Person> loadVictims(String filename) throws IOException {
        ArrayList<Person> result = new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(new Person(line));
            }
            
        }
        return result;
    }
    
    public ArrayList<Message> loadMessages(String filename) throws IOException {
        ArrayList<Message> result = new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            String subject = null;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Subject:")) {
                    subject = line.replaceFirst("Subject: ", "");
                } else if (line.equals("")) {
                    continue;
                } else if (line.equals("==")) {
                    result.add(new Message(subject, content.toString()));
                    content = new StringBuilder();
                } else { // Du contenu
                    content.append(line).append("\n");
                }
            }
            // Création du dernier message
            result.add(new Message(subject, content.toString()));
        }
        return result;
    }
    
    public void loadProperties(String filename) throws IOException {
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(filename), StandardCharsets.UTF_8))) {
            
            Properties prop = new Properties();
            prop.load(reader);
            
            smtpServerAddress = prop.getProperty("smtpServerAddress");
            smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
            numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
            witnessesToCC = new Person(prop.getProperty("witnessesToCC"));
        }
    }
    
    public static boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
    
    public void verifyConfig() {
        if (victims.size() < numberOfGroups * 3) {
            throw new RuntimeException("The number of groups is too big compared " +
                    "to the number of victims.");
        }
        
        if (messages.size() == 0) {
            throw new RuntimeException("You need to add messages to your config.");
        }
        
        if (!isEmailValid(witnessesToCC.getMailAdress())) {
            throw new RuntimeException("witnessesTo email <" +
                    witnessesToCC.getMailAdress() + "> is invalid.");
        }
        
        for (Person v : victims) {
            if (!isEmailValid(v.getMailAdress())) {
                throw new RuntimeException("Victim email <" +
                        v.getMailAdress() + "> is invalid.");
            }
        }
    }
    
    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }
    
    public int getSmtpServerPort() {
        return smtpServerPort;
    }
    
    public int getNumberOfGroups() {
        return numberOfGroups;
    }
    
    public Person getWitnessesToCC() {
        return witnessesToCC;
    }
    
    public ArrayList<Person> getVictims() {
        return victims;
    }
    
    public ArrayList<Message> getMessages() {
        return messages;
    }
}
