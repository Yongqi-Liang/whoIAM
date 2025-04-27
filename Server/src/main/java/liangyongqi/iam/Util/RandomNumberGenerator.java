package liangyongqi.iam.Util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Utility class for generating 15-digit random numbers.
 */
@Component
public class RandomNumberGenerator {

    private static final int RANDOM_NUMBER_LENGTH = 15;
    private static final Random RANDOM = new Random();

    /**
     * Generates a 15-digit random number.
     *
     * @return A string representing the 15-digit random number.
     */
    public static String generateRandomNumber() {
        LogTool.writelog("liangyongqi.iam.Util.RandomNumberGenerator", "generateRandomNumber", null);
        StringBuilder randomNumber = new StringBuilder(RANDOM_NUMBER_LENGTH);
        for (int i = 0; i < RANDOM_NUMBER_LENGTH; i++) {
            randomNumber.append(RANDOM.nextInt(10)); // Append a random digit (0-9)
        }
        return randomNumber.toString();
    }

    /**
     * Generates a new 15-digit random number based on an existing 15-digit number.
     *
     * @param baseNumber A string representing the base 15-digit number.
     * @return A string representing a new 15-digit random number.
     * @throws IllegalArgumentException if the input is not a valid 15-digit number.
     */
    public String generateFromBase(String baseNumber) {
        LogTool.writelog("liangyongqi.iam.Util.RandomNumberGenerator", "generateFromBase", null);
        if (baseNumber == null || baseNumber.length() != RANDOM_NUMBER_LENGTH || !baseNumber.matches("\\d{" + RANDOM_NUMBER_LENGTH + "}")) {
            throw new IllegalArgumentException("Input must be a 15-digit numeric string.");
        }

        StringBuilder newRandomNumber = new StringBuilder(RANDOM_NUMBER_LENGTH);
        for (int i = 0; i < RANDOM_NUMBER_LENGTH; i++) {
            int baseDigit = Character.getNumericValue(baseNumber.charAt(i));
            int newDigit = (baseDigit + RANDOM.nextInt(10)) % 10; // Ensure the digit stays within 0-9
            newRandomNumber.append(newDigit);
        }
        return newRandomNumber.toString();
    }
}
