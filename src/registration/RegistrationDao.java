package registration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import javax.servlet.http.Part;
import database.ConnectionsDatabase;
import pannel.CustomMessage;
import query.DatabaseQuery;

public class RegistrationDao {

	public static boolean userRegistration(String username, byte[] password, byte[] salt, Part filePart)
			throws IOException {
		Connection con_write = null;
		Connection con_read = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con_write = ConnectionsDatabase.getConnectionWrite();
			con_read = ConnectionsDatabase.getConnectionRead();

			// Verifica se l'utente esiste gia'
			if (userAlreadyExists(username, con_read) > 0) {
				CustomMessage.showPanel("Utente gia' registrato!");
				return false;
			}

			try (PreparedStatement ps = con_write.prepareStatement(DatabaseQuery.registrationUserQuery())) {
				ps.setString(1, username);
				ps.setBytes(2, password);

				InputStream fileContent = filePart.getInputStream();
				ps.setBlob(3, fileContent);

				int rowsAffected = ps.executeUpdate();

				// Imposta lo stato a true se almeno una riga e' stata aggiornata
				boolean status = (rowsAffected > 0);
				CustomMessage.showPanel("Registrazione effettuata con successo!");

				if (status) {
					try (PreparedStatement psSale = con_write.prepareStatement(DatabaseQuery.userSaleQuery())) {
						psSale.setString(1, username);
						psSale.setBytes(2, salt);

						int rowsAffectedSale = psSale.executeUpdate();

						return (rowsAffectedSale > 0); // Restituisci true se anche la seconda query ha avuto successo
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// Chiusura delle connessioni
			if (con_write != null) {
				try {
					con_write.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con_read != null) {
				try {
					con_read.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		CustomMessage.showPanel("Non e' stato possibile terminare la registrazione!");
		return false;
	}

	private static int userAlreadyExists(String username, Connection connection) throws SQLException {
		String query = DatabaseQuery.userAlreadyExist();

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
