package com.scholastic.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AES {

    private static final Logger LOGGER = LoggerFactory.getLogger(AES.class);
    private static final int BUFFER_SIZE = 4;

    private AES() {
    }

    public static String encrypt(String data, String keyAES) {
        try {
            Key key = generateKey(keyAES);
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encVal);

        } catch (Exception e) {

            LOGGER.error("encrypting source", e);
        }
        return data;
    }

    public static String decrypt(String data, String keyAES) {
        try {
            Key key = generateKey(keyAES);
            byte[] encVal = Base64.getDecoder().decode(data);
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayInputStream inStream = new ByteArrayInputStream(encVal);
            CipherInputStream cipherInputStream = new CipherInputStream(inStream, c);
            byte[] buf = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            String decrypt = new String(outputStream.toByteArray());
            cipherInputStream.close();
            return decrypt;

        } catch (Exception e) {

            LOGGER.error("encrypting source", e);
        }
        return data;

    }

    private static Key generateKey(String key) {
        return new SecretKeySpec(key.getBytes(), "AES");
    }

}
