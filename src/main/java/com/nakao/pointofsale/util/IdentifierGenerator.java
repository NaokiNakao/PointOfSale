package com.nakao.pos.util;

import java.util.Random;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */
public class IdentifierGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int MAX_NUMBER = 9;

    public static String generateIdentifier(String pattern) {
        StringBuilder identifier = new StringBuilder();

        for (char c : pattern.toCharArray()) {
            if (c == '#') {
                identifier.append(generateRandomNumber());
            } else if (c == '*') {
                identifier.append(generateRandomLetter());
            } else {
                identifier.append(c);
            }
        }

        return identifier.toString();
    }

    private static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(MAX_NUMBER + 1);
    }

    private static char generateRandomLetter() {
        Random random = new Random();
        int index = random.nextInt(LETTERS.length());
        return LETTERS.charAt(index);
    }

}
