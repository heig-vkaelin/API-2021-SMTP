package ch.heigvd.api;

import ch.heigvd.api.config.ConfigurationManager;
import ch.heigvd.api.model.prank.Prank;
import ch.heigvd.api.model.prank.PrankGenerator;
import ch.heigvd.api.smtp.SmtpClient;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MailRobot {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    
    public static void main(String[] args) {
        // Parsing config
        ConfigurationManager cm;
        try {
            cm = new ConfigurationManager();
        } catch (Exception e) {
            LOG.info("Error while parsing configuration:");
            LOG.info(e.getMessage());
            return;
        }
        
        // Generate pranks
        SmtpClient client = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort());
        PrankGenerator generator = new PrankGenerator(cm);
        ArrayList<Prank> pranks = generator.generatePranks();
        
        // Send pranks
        try {
            for (Prank p : pranks) {
                client.sendMessage(p.getMessage());
            }
            System.out.println("Program ended.");
        } catch (Exception e) {
            LOG.info("Error while sending pranks:");
            LOG.info(e.getMessage());
        }
    }
}
