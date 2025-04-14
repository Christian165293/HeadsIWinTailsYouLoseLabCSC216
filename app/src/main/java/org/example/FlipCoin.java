package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FlipCoin {
    private int headsCount;
    private boolean firstTimeCheck = false;

    public void flipCoin(int headsProbability, int numOfFlips, int numOfTrials) {
        for (int i = 1; i <= numOfTrials; i++) {
            headsCount = 0;
            for (int j = 1; j <= numOfFlips; j++) {
                int randomNum = (int) (Math.random() * 100) + 1;
                if (randomNum <= headsProbability) {
                    headsCount++;
                }
            }
            writeResults(headsProbability, numOfFlips, i);
            firstTimeCheck = true;
        }
    }

    private void writeResults(int headsProbability, int numOfFlips, int trialNum) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", firstTimeCheck))) {
            if (!firstTimeCheck) {
                writer.write(String.format("%-20s %-20s %-25s %-15s", "Number of Flips (n)", "Trial Number (m)", "Probability of Heads (p)", "Number of Heads"));
            }
            writer.newLine();
            writer.write(String.format("%-20d %-20d %-25d %-15d", numOfFlips, trialNum, headsProbability, headsCount));
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}
