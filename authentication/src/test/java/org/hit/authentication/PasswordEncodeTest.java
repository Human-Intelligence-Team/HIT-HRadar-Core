package org.hit.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncodeTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void password_encode_test() {
        String raw = "1234";
        String encoded = passwordEncoder.encode(raw);

        System.out.println(encoded);

        assertTrue(passwordEncoder.matches(raw, encoded));
    }
}

