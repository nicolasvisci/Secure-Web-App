package login;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

class Aes {

    private static final String AES_KEY = aesKey();
    private static final String AES_IV = "1234567890123456"; // Cambia l'IV a tuo piacimento

    private static SecretKey secretKey;

    private static SecretKey getSecretKey() {
        if (secretKey == null) {
            secretKey = new SecretKeySpec(Base64.getDecoder().decode(AES_KEY), "AES");
        }
        return secretKey;
    }

    static byte[] encrypt(byte[] data) throws Exception {
        SecretKey key = getSecretKey();
        if (key != null) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(AES_IV.getBytes()));
            return cipher.doFinal(data);
        } else {
            System.out.println("Chiave non valida.");
            return null;
        }
    }

    static byte[] decrypt(byte[] encryptedBytes) throws Exception {
        SecretKey key = getSecretKey();
        if (key != null) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(AES_IV.getBytes()));
            return cipher.doFinal(encryptedBytes);
        } else {
            System.out.println("Chiave non valida.");
            return null;
        }
    }

    private static String aesKey() {
        Properties appProperties = new Properties();

        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
            if (input == null) {
                System.out.println("File di configurazione config.ini non trovato.");
                return null;
            }
            appProperties.load(input);
        } catch (IOException e) {
            System.out.println("Query AES - Aes.key");
            e.printStackTrace();
            return null;
        }

        return appProperties.getProperty("aes.key");
    }
}
