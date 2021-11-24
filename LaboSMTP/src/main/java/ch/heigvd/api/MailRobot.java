package ch.heigvd.api;

import ch.heigvd.api.model.prank.PrankGenerator;

public class MailRobot {
    public static void main(String[] args) {
        try {
            new PrankGenerator();
            System.out.println("Program ended.");
        } catch (Exception ignored) {
        }
    }
}
