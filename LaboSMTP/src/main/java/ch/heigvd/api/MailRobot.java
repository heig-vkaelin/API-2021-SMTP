package ch.heigvd.api;

import ch.heigvd.api.model.prank.PrankGenerator;

public class MailRobot {
    public static void main(String[] args) {
        try {
            PrankGenerator generator = new PrankGenerator();
            generator.generatePranks();
            System.out.println("Program ended.");
        } catch (Exception ignored) {
        }
    }
}
