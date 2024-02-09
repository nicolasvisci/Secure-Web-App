package password;

import java.security.SecureRandom;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GestionePassword {

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
		String passwordString = new String(password);

		return isLengthValid(passwordString) && containsUpperCase(passwordString) && containsDigit(passwordString)
				&& containsSpecialCharacter(passwordString);
	}

	private static boolean isLengthValid(String str) {

		return str.length() >= 8;
	}

	private static boolean containsUpperCase(String str) {

		return Pattern.compile("[A-Z]").matcher(str).find();
	}

	private static boolean containsDigit(String str) {

		return Pattern.compile("\\d").matcher(str).find();
	}

	private static boolean containsSpecialCharacter(String str) {
		String specialCharacters = "!@#$%^&*()-_+=<>?.";
		return Pattern.compile("[" + Pattern.quote(specialCharacters) + "]").matcher(str).find();
	}
}
