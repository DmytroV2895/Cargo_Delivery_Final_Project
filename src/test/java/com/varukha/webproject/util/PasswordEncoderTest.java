package com.varukha.webproject.util;

import org.junit.jupiter.api.Test;

import static com.varukha.webproject.util.Encoder.encodePassword;
import static org.junit.jupiter.api.Assertions.*;


class PasswordEncoderTest {
    private static final String password = "Qwer12345";

    @Test
    void testEncodePassword() {
        String encoded = encodePassword(password);
        assertNotEquals(password, encoded);
    }

}