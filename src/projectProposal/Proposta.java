package projectProposal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnessioniDatabase;
import query.DatabaseQuery;

public class Proposta {
	private String username;
	private String nomeFile;
	private String contenutoHtml;

	public Proposta(String username, String nomeFile, String contenutoHtml) {
		this.username = username;
		this.nomeFile = nomeFile;
		this.contenutoHtml = contenutoHtml;
	}

	public static List<Proposta> getProposte() {
		List<Proposta> proposte = new ArrayList<>();

		try (Connection connection = ConnessioniDatabase.getConnectionRead();
				PreparedStatement preparedStatement = connection
						.prepareStatement(DatabaseQuery.takeUsernameAndProposta());
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String nomeFile = resultSet.getString("nome_file");
				String contenutoHtml = resultSet.getString("file");

				Proposta proposta = new Proposta(username, nomeFile, contenutoHtml);
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
