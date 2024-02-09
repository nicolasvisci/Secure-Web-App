package login;
import java.sql.*;

import password.GestionePassword;

public class LoginDao {

	public static boolean isUserValid(String name, byte[] password) {
		boolean status = false;
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps_pwd = null;
		
		try {
			// inizializza il driver per comunicare con il db
			Class.forName("com.mysql.cj.jdbc.Driver");
			// stringa di connessione: indirizzo - porta - nome db
			String url = "jdbc:mysql://localhost:3306/securewebapp";
			// oggetto connessione al db tramite inserimento di credenziali: stringa di
			// connessione - nome utente - password
			con = DriverManager.getConnection(url, "user_read", "YOURPASSWORD"); //Inserire la password di MySQL
			
			String takeusersale = "SELECT user_sale FROM sales WHERE user=?";
			
			ps_pwd = con.prepareStatement(takeusersale);
            ps_pwd.setString(1, name);

            rs = ps_pwd.executeQuery();
            
            if (rs.next()) {
            	
            	Blob userSalesBlob = rs.getBlob("user_sale");
            	
            	if (userSalesBlob != null) {
            		
            		byte[] sale = userSalesBlob.getBytes(1, (int) userSalesBlob.length());
                    byte[] newPassword = GestionePassword.concatenateAndHash(password, sale);
                    
                    String userlogin = "SELECT * FROM users WHERE username=? AND password=?";
                    
                    try (PreparedStatement ps = con.prepareStatement(userlogin)) {
            	
						// ... a partire dal nome e ...
						ps.setString(1, name);
						// ... password date in input dall'utente alla jsp, dalla jsp alla servlet e
						// dalla servlet al DAO
						ps.setBytes(2, newPassword);
						// svuoto la password
						//Arrays.fill(pass, (byte)0);
						// esegue effettivamente la query ed ottiene un oggetto ResultSet che contiene la risposta del db
					    rs = ps.executeQuery();
					    
					    boolean userFound = rs.next();
					    name = null;
                        GestionePassword.clearBytes(password);
                        GestionePassword.clearBytes(sale);
                        GestionePassword.clearBytes(newPassword);

                        if (userFound) {
                            System.out.println("Utente trovato");
                        } else {
                            System.out.println("Errore nell'inserimento dei dati dell'utente. Riprova");
                        }
						status = userFound;					
                    }					
            	}else {
                    name = null;
                    GestionePassword.clearBytes(password);
                    System.out.println("User_Sales e' nullo");
                }
            }else {
                name = null;
                GestionePassword.clearBytes(password);
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