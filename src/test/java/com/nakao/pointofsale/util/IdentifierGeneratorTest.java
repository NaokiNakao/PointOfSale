package com.nakao.pointofsale.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdentifierGeneratorTest {

    @Test
    public void testGenerateIdentifierWithNumbers() {
        String pattern = "##";
        String result = IdentifierGenerator.generateIdentifier(pattern);
        Assertions.assertTrue(result.matches("\\d{2}"), "Expected two digits in the generated identifier.");
    }

    @Test
    public void testGenerateIdentifierWithLetters() {
        String pattern = "**";
        String result = IdentifierGenerator.generateIdentifier(pattern);
        Assertions.assertTrue(result.matches("[A-Z]{2}"), "Expected two uppercase letters in the generated identifier.");
    }

    @Test
    public void testGenerateIdentifierWithMixedPattern() {
        String pattern = "#*-";
        String result = IdentifierGenerator.generateIdentifier(pattern);
        Assertions.assertTrue(result.matches("\\d[A-Z]-"), "Expected one digit, one uppercase letter, and a hyphen in the generated identifier.");
    }

    @Test
    public void testGenerateIdentifierWithNoSpecialCharacter() {
        String pattern = "ABC";
        String result = IdentifierGenerator.generateIdentifier(pattern);
        Assertions.assertEquals(pattern, result, "Expected the same pattern when no special character is present.");
    }
}
