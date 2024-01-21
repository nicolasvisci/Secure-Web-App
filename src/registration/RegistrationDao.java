package registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class RegistrationDao {

	public static boolean registration(String name, byte[] pass) throws ClassNotFoundException {
		boolean status = false;
		try {
			System.out.println("Registrazione in corso...");
			// inizializza il driver per comunicare con il db
			Class.forName("com.mysql.cj.jdbc.Driver");
			// stringa di connessione: indirizzo - porta - nome db
			String url = "jdbc:mysql://localhost:3306/my_db1";
			// oggetto connessione al db tramite inserimento di credenziali: stringa di
			// connessione - nome utente - password
			Connection con = DriverManager.getConnection(url, "user_write", "YOURPASSWORD"); // <-- Inserire la password di MySQL
			// oggetto prepared statement che consente di eseguire una query al db...

			String sql = "INSERT INTO user (username, password) VALUES (?, ?)";

			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, name); // commentare il codice
			statement.setBytes(2, pass);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Inserimento riuscito!");
				//Arrays.fill(pass, (byte)0);
				status = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return status;
	}

}