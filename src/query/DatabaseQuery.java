package query;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseQuery {

	public static String getSelectUserQuery() {
		Properties appProperties = new Properties();

		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
			if (input == null) {
				System.out.println("File di configurazione config.ini non trovato.");
				return null;
			}
			appProperties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return appProperties.getProperty("db.query_userLogin");
	}

	public static String registrationUserQuery() {
		Properties appProperties = new Properties();

		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
			if (input == null) {
				System.out.println("File di configurazione config.ini non trovato.");
				return null;
			}
			appProperties.load(input);
		} catch (IOException e) {
			System.out.println("Query DatabaseQuery - Registration");
			e.printStackTrace();
			return null;
		}

		return appProperties.getProperty("db.query_userRegistration");
	}

	public static String userSaleQuery() {
		Properties appProperties = new Properties();

		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
			if (input == null) {
				System.out.println("File di configurazione config.ini non trovato.");
				return null;
			}
			appProperties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return appProperties.getProperty("db.query_userSale");

	}

	public static String userAlreadyExist() {

		Properties appProperties = new Properties();

		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
			if (input == null) {
				System.out.println("File di configurazione config.ini non trovato.");
				return null;
			}
			appProperties.load(input);
		} catch (IOException e) {
			System.out.println("Query DatabaseQuery - User already Exist");
			e.printStackTrace();
			return null;
		}

		return appProperties.getProperty("db.query_userAlreadyExist");
	}

	public static String takeUserSale() {

		Properties appProperties = new Properties();

		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
			if (input == null) {
				System.out.println("File di configurazione config.ini non trovato.");
				return null;
			}
			appProperties.load(input);
		} catch (IOException e) {
			System.out.println("Query DatabaseQuery - takeUserSale");
			e.printStackTrace();
			return null;
		}

		return appProperties.getProperty("db.query_takeUserSale");
	}

	public static String insertProposal() {

		Properties appProperties = new Properties();

		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
			if (input == null) {
				System.out.println("File di configurazione config.ini non trovato.");
				return null;
			}
			appProperties.load(input);
		} catch (IOException e) {
			System.out.println("Query DatabaseQuery - insertProposal");
			e.printStackTrace();
			return null;
		}

		return appProperties.getProperty("db.query_insertProposal");
	}

	public static String takeUsernameAndProposal() {

		Properties appProperties = new Properties();

		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
			if (input == null) {
				System.out.println("File di configurazione config.ini non trovato.");
				return null;
			}
			appProperties.load(input);
		} catch (IOException e) {
			System.out.println("Query DatabaseQuery - takeUsernameAndProposal");
			e.printStackTrace();
			return null;
		}

		return appProperties.getProperty("db.query_takeUsernameAndProposal");
	}

}
