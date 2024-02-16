package login;

import java.sql.*;

import database.ConnessioniDatabase;
import password.PasswordManagement;
import query.DatabaseQuery;
import pannel.CustomMessage;

public class LoginDao {
	static boolean isUserValid(String name, byte[] password) {
		boolean status = false;

		Connection con = null;
		PreparedStatement ps_pwd = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = ConnessioniDatabase.getConnectionRead();
			ps_pwd = con.prepareStatement(DatabaseQuery.takeUserSale());
			ps_pwd.setString(1, name);

			rs = ps_pwd.executeQuery();

			if (rs.next()) {
				Blob userSalesBlob = rs.getBlob("user_sale");

				if (userSalesBlob != null) {
					byte[] sale = userSalesBlob.getBytes(1, (int) userSalesBlob.length());
					byte[] newPassword = PasswordManagement.concatenateAndHash(password, sale);

					try (PreparedStatement ps = con.prepareStatement(DatabaseQuery.getSelectUserQuery())) {
						ps.setString(1, name);
						ps.setBytes(2, newPassword);

						rs = ps.executeQuery();
						boolean userFound = rs.next();

						name = null;
						PasswordManagement.clearBytes(password);
						PasswordManagement.clearBytes(sale);
						PasswordManagement.clearBytes(newPassword);

						if (userFound) {
							System.out.println("Utente trovato");
						} else {
							CustomMessage.showPanel("Errore nell'inserimento dei dati dell'utente. Riprova"); // VALUTARE
																												// DI
																												// ELIMINARE?
						}
						status = userFound;
					}
				} else {
					name = null;
					PasswordManagement.clearBytes(password);
					System.out.println("User_Sales e' nullo");
				}
			} else {
				name = null;
				PasswordManagement.clearBytes(password);
				System.out.println("Nessun risultato trovato per l'utente: " + name);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// Chiusura delle risorse
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps_pwd != null) {
					ps_pwd.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
}