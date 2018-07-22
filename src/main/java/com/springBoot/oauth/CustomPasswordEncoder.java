package com.springBoot.oauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomPasswordEncoder implements PasswordEncoder{
    Logger logger = LogManager.getLogger(CustomPasswordEncoder.class);

    public CustomPasswordEncoder() {
    }

    @Override
    public String encode(CharSequence charSequence) {
        String rawPassword = charSequence.toString();
        StringBuffer hexString = new StringBuffer();
        byte[] defaultBytes = rawPassword.toString().getBytes();

        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
        } catch (NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(encode(charSequence));
    }

}