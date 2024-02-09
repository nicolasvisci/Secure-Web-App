package registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class RegistrationDao {

	public static boolean registration(String username, byte[] pass, byte[] sale) throws ClassNotFoundException {
		
		Connection con = null;
		boolean status = false;
		
		try {
			// inizializza il driver per comunicare con il db
			Class.forName("com.mysql.cj.jdbc.Driver");
			// stringa di connessione: indirizzo - porta - nome db
			String url = "jdbc:mysql://localhost:3306/securewebapp";
			// oggetto connessione al db tramite inserimento di credenziali: stringa di
			// connessione - nome utente - password
			con = DriverManager.getConnection(url, "user_write", "YOURPASSWORD"); // <-- Inserire la password di MySQL
			// oggetto prepared statement che consente di eseguire una query al db...

			// Verifica se l'utente esiste gia'
			if (userAlreadyExists(username, con) > 0) {
				System.out.println("Utente gia' registrato!");
				return false; 
			}
			
			String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
			String userSale= "INSERT INTO sales (user, user_sale) VALUES (?,?)";

			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, username); // commentare il codice
			statement.setBytes(2, pass);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Inserimento riuscito!");
			    status = true;
			}
			
			if(status) {
				try (PreparedStatement psSale = con.prepareStatement(userSale)) {
					psSale.setString(1, username);
					psSale.setBytes(2, sale);

					
					int rowsAffectedSale = psSale.executeUpdate();

					return (rowsAffectedSale > 0); // Restituisci true se anche la seconda query ha avuto successo
				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// Chiusura delle connessioni
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(); 
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Non e' stato possibile terminare la registrazione!");
		return false;
	}
	
	private static int userAlreadyExists(String username, Connection connection) throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM users WHERE username=?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("count"); 
				}
			}
		}

		return 0; 
	}

}