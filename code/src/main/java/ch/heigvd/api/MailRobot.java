package ch.heigvd.api;

import ch.heigvd.api.config.ConfigurationManager;
import ch.heigvd.api.config.IConfigurationManager;
import ch.heigvd.api.model.prank.Prank;
import ch.heigvd.api.model.prank.PrankGenerator;
import ch.heigvd.api.smtp.ISmtpClient;
import ch.heigvd.api.smtp.SmtpClient;

import java.util.List;
import java.util.logging.Logger;

public class MailRobot {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");
        // Parsing config
        IConfigurationManager cm;
        try {
            cm = new ConfigurationManager();
        } catch (Exception e) {
            LOG.severe("Error while parsing configuration:");
            LOG.severe(e.getMessage());
            return;
        }
        
        // Generate pranks
        ISmtpClient client = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort());
        PrankGenerator generator = new PrankGenerator(cm);
        List<Prank> pranks = generator.generatePranks();
        
        // Send pranks
        try {
            for (Prank p : pranks) {
                client.sendMail(p.generateMail());
            }
            LOG.info("Program finished.");
        } catch (Exception e) {
            LOG.severe("Error while sending pranks:");
            LOG.severe(e.getMessage());
        }
    }
}
