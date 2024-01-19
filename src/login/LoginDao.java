package login;
import java.sql.*;

public class LoginDao {

	public static boolean isUserValid(String name, byte[] pass) {
		boolean status = false;
		try {
			// inizializza il driver per comunicare con il db
			Class.forName("com.mysql.cj.jdbc.Driver");
			// stringa di connessione: indirizzo - porta - nome db
			String url = "jdbc:mysql://localhost:3306/my_db1";
			// oggetto connessione al db tramite inserimento di credenziali: stringa di
			// connessione - nome utente - password
			Connection con = DriverManager.getConnection(url, "user_read", "YOUR PASSWORD"); //Inserire la password di MySQL
			// oggetto prepared statement che consente di eseguire una query al db...
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
			// ... a partire dal nome e ...
			ps.setString(1, name);
			// ... password date in input dall'utente alla jsp, dalla jsp alla servlet e
			// dalla servlet al DAO
			ps.setBytes(2, pass);
			// svuoto la password
			//Arrays.fill(pass, (byte)0);
			// esegue effettivamente la query ed ottiene un oggetto ResultSet che contiene la risposta del db
			ResultSet rs = ps.executeQuery();
			// il next() prende la prima riga del risultato della query
			// restituisce true se c'è almeno una riga altrimenti false
			status = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}