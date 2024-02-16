package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import pannel.CustomMessage;

public class ConnessioniDatabase {
	private static String URL;
	private static String USERNAME_READ;
	private static String PASSWORD_READ;
	private static String USERNAME_WRITE;
	private static String PASSWORD_WRITE;

	static {
		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.ini")) {
			Properties prop = new Properties();
			prop.load(input);

			URL = prop.getProperty("db.url");

			USERNAME_READ = prop.getProperty("db.username_read");
			PASSWORD_READ = prop.getProperty("db.password_read");

			USERNAME_WRITE = prop.getProperty("db.username_write");
			PASSWORD_WRITE = prop.getProperty("db.password_write");

		} catch (IOException e) {
			e.printStackTrace();
			CustomMessage.showPanel("Sembra esserci un problema con qualche file di configurazione!");
			throw new RuntimeException("Errore nel caricamento del file di configurazione", e);
		}
	}

	public static Connection getConnectionRead() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME_READ, PASSWORD_READ);
	}

	public static Connection getConnectionWrite() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME_WRITE, PASSWORD_WRITE);
	}
}