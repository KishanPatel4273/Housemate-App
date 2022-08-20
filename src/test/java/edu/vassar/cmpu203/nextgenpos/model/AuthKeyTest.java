package edu.vassar.cmpu203.nextgenpos.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

class AuthKeyTest {

    @Test
    void validatePassword() {

        final int passLen = 32;
        for (int i=0; i < 25; i++){
            // generate random passwords
            String passRight = generateRandomPassword(passLen);
            String passWrong;
            do{
                passWrong = generateRandomPassword(passLen);
            }while(passWrong.equals(passRight));

            AuthKey authKey = new AuthKey(passRight);
            assertTrue(authKey.validatePassword(new String(passRight)),
                    "correct password considered invalid");

            assertFalse(authKey.validatePassword(passWrong),
                    "incorrect password considered valid");
        }
    }

    private static String generateRandomPassword(final int passLen) {
        final byte[] passArray = new byte[passLen];
        new Random().nextBytes(passArray);
        return new String(passArray, StandardCharsets.UTF_8);
    }
}