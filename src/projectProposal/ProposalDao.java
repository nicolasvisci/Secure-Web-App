package projectProposal;

import java.io.IOException;

import java.sql.*;

import database.ConnectionsDatabase;
import pannel.CustomMessage;
import query.DatabaseQuery;

public class ProposalDao {

	public static boolean uploadFile(String username, byte[] contenutoFile, String nomeFile)
			throws IOException, SQLException {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = ConnectionsDatabase.getConnectionWrite();

			try (PreparedStatement ps = con.prepareStatement(DatabaseQuery.insertProposal())) {
				ps.setString(1, username);
				ps.setBytes(2, contenutoFile);
				ps.setString(3, nomeFile);

				int rowsAffected = ps.executeUpdate();

				return rowsAffected > 0;

			} catch (SQLException e) {
				CustomMessage.showPanel("Si e' verificato un errore durante l'inserimento della proposta!");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			CustomMessage.showPanel("Si e' verificato un problema di connessione!");
			e.printStackTrace();
		} finally {
			// Chiusura della connessione nel blocco finally
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}