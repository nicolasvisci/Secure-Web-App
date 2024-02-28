package password;

import java.security.SecureRandom;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManagement {

	public static void clearBytes(byte[] password) {
		if (password != null) {

			for (int i = 0; i < password.length; i++) {
				password[i] = 0;
			}
		}
	}

	public static byte[] generateRandomBytes(int saltLenghts) {

		int saltLength = 16;
		byte[] salt = new byte[saltLength];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);

		return salt;
	}

	public static byte[] concatenateAndHash(byte[] password, byte[] salt) {
		try {
			// Alloca un nuovo array di byte con dimensioni totali
			byte[] concatenatedData = new byte[password.length + salt.length];

			// Copia i dati dal primo array di byte al nuovo array
			System.arraycopy(password, 0, concatenatedData, 0, password.length);

			// Copia i dati dal secondo array di byte al nuovo array
			System.arraycopy(salt, 0, concatenatedData, password.length, salt.length);

			// Ottieni un'istanza di MessageDigest con l'algoritmo SHA-256
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			// Calcola l'hash dell'array di byte concatenato
			return digest.digest(concatenatedData);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isStrongPassword(byte[] password) {

		// Check the minimum length of the password
		if (password.length < 8) {
			return false;
		}

		// Check for lowercase, uppercase, numbers, and special characters
		boolean hasLowerCase = false;
		boolean hasUpperCase = false;
		boolean hasDigit = false;
		boolean hasSpecialChar = false;

		for (byte b : password) {
			char c = (char) b;
			if (Character.isLowerCase(c)) {
				hasLowerCase = true;
			} else if (Character.isUpperCase(c)) {
				hasUpperCase = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else if (!Character.isLetterOrDigit(c)) {
				hasSpecialChar = true;
			}
		}

		return hasLowerCase && hasUpperCase && hasDigit && hasSpecialChar;

	}
}
