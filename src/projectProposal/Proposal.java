package projectProposal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionsDatabase;
import query.DatabaseQuery;

public class Proposal {
	private String username;
	private String nomeFile;
	private String contenutoHtml;

	public Proposal(String username, String nomeFile, String contenutoHtml) {
		this.username = username;
		this.nomeFile = nomeFile;
		this.contenutoHtml = contenutoHtml;
	}

	public static List<Proposal> getProposte() {
		List<Proposal> proposte = new ArrayList<>();

		try (Connection connection = ConnectionsDatabase.getConnectionRead();
				PreparedStatement preparedStatement = connection
						.prepareStatement(DatabaseQuery.takeUsernameAndProposta());
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String nomeFile = resultSet.getString("nome_file");
				String contenutoHtml = resultSet.getString("file");

				Proposal proposta = new Proposal(username, nomeFile, contenutoHtml);
				proposte.add(proposta);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proposte;
	}

	public String getUsername() {
		return username;
	}

	public String getContenutoHtml() {
		return contenutoHtml;
	}

	public String getNomeFile() {
		return nomeFile;
	}
}
