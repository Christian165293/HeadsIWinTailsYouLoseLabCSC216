package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FlipCoinTest {
    private static final String OUTPUT_FILE = "output.txt";
    private FlipCoin flipCoin;

    @BeforeEach
    public void setUp() {
        flipCoin = new FlipCoin();
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_FILE));
        } catch (IOException ignored) {
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_FILE));
        } catch (IOException ignored) {
        }
    }

    @Test
    public void testFlipCoin_SingleTrial() {
        int headsProbability = 50;
        int numOfFlips = 100;
        int numOfTrials = 1;

        flipCoin.flipCoin(headsProbability, numOfFlips, numOfTrials);

        assertTrue(Files.exists(Paths.get(OUTPUT_FILE)));

        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String header = reader.readLine();
            assertNotNull(header);
            assertTrue(header.contains("Number of Flips"));
            assertTrue(header.contains("Trial Number"));
            assertTrue(header.contains("Probability of Heads"));
            assertTrue(header.contains("Number of Heads"));

            String dataLine = reader.readLine();
            assertNotNull(dataLine);
            assertTrue(dataLine.contains(String.valueOf(numOfFlips)));
            assertTrue(dataLine.contains("1"));
            assertTrue(dataLine.contains(String.valueOf(headsProbability)));
        } catch (IOException e) {
            fail("Exception while reading output file: " + e.getMessage());
        }
    }

    @Test
    public void testFlipCoin_MultipleTrials() {
        int headsProbability = 30;
        int numOfFlips = 50;
        int numOfTrials = 5;

        flipCoin.flipCoin(headsProbability, numOfFlips, numOfTrials);

        assertTrue(Files.exists(Paths.get(OUTPUT_FILE)));

        try {
            long lineCount = Files.lines(Paths.get(OUTPUT_FILE)).count();
            assertEquals(numOfTrials + 1, lineCount);
        } catch (IOException e) {
            fail("Exception while counting lines in output file: " + e.getMessage());
        }
    }

    @Test
    public void testFlipCoin_AlwaysHeads() {
        int headsProbability = 100;
        int numOfFlips = 10;
        int numOfTrials = 1;

        flipCoin.flipCoin(headsProbability, numOfFlips, numOfTrials);

        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            reader.readLine();
            String dataLine = reader.readLine();
            assertNotNull(dataLine);
            assertTrue(dataLine.contains(String.valueOf(numOfFlips)));
        } catch (IOException e) {
            fail("Exception while reading output file: " + e.getMessage());
        }
    }

    @Test
    public void testFlipCoin_NeverHeads() {
        int headsProbability = 0;
        int numOfFlips = 10;
        int numOfTrials = 1;

        flipCoin.flipCoin(headsProbability, numOfFlips, numOfTrials);

        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            reader.readLine();
            String dataLine = reader.readLine();
            assertNotNull(dataLine);
            assertTrue(dataLine.contains("0"));
        } catch (IOException e) {
            fail("Exception while reading output file: " + e.getMessage());
        }
    }

    @Test
    public void testFlipCoin_AppendToFile() {
        flipCoin.flipCoin(50, 10, 1);

        flipCoin.flipCoin(60, 20, 1);

        try {
            long lineCount = Files.lines(Paths.get(OUTPUT_FILE)).count();
            assertEquals(3, lineCount); // Header + 2 trials
        } catch (IOException e) {
            fail("Exception while counting lines in output file: " + e.getMessage());
        }
    }

    @Test
    public void testFlipCoin_ZeroFlips() {
        int headsProbability = 50;
        int numOfFlips = 0;
        int numOfTrials = 1;

        flipCoin.flipCoin(headsProbability, numOfFlips, numOfTrials);

        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            reader.readLine();
            String dataLine = reader.readLine();
            assertNotNull(dataLine);
            assertTrue(dataLine.contains("0"));
        } catch (IOException e) {
            fail("Exception while reading output file: " + e.getMessage());
        }
    }
}