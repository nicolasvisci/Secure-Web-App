package login;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import pannel.CustomMessage;
import password.GestionePassword;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	public LoginServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		        checkCookie(request, response);
		    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		HttpSession session = request.getSession();
	   		
		session = request.getSession();	
				
		String string_encryptedUsername = null;
		String string_encryptedPassword = null;

		byte[] byte_encryptedUsername = null;
		byte[] byte_encryptedPassword = null;

		byte[] pad_username = null;
		byte[] pad_password = null;
		
		byte[] byte_username = null;

		String nomeUtente = request.getParameter("username");
		byte[] password = request.getParameter("pass").getBytes();
		boolean ricordami = request.getParameter("ricordami") != null;

		String string_Password = byteArrayToString(password);

		if (ricordami) {
			try {

				try {
					// CONVERTO LA STRINGA IN BYTE
					byte_username = nomeUtente.getBytes(java.nio.charset.StandardCharsets.UTF_8); 

					// AGGIUNGO IL PADDING PER EVITARE PROBLEMI DI DECIFRATURA ED EFFETTUO LA CIFRATURA
					pad_password = pad(password);
					pad_username = pad(byte_username);

					byte_encryptedUsername = Aes.encrypt(pad_username);
					byte_encryptedPassword = Aes.encrypt(pad_password);

				} catch (IllegalArgumentException e) {
					System.out.println("ERRORE FUNZIONI getBytes/pad/Aes.Encrypt: " + e.getMessage());
					e.printStackTrace();
				}

				string_encryptedUsername = byteArrayToString(byte_encryptedUsername);
				string_encryptedPassword = byteArrayToString(byte_encryptedPassword);

				System.out.println("Encrypted username: " + string_encryptedUsername);
				System.out.println("Encrypted username: " + string_encryptedPassword);

			} catch (Exception e) {
				System.out.println("Errore durante l'encrypt di username e/o password");
				e.printStackTrace();
			}

			String base64EncodedUsername = Base64.getEncoder().encodeToString(byte_encryptedUsername);
			String base64EncodedPassword = Base64.getEncoder().encodeToString(byte_encryptedPassword);
						
			try {
			    if (LoginDao.isUserValid(nomeUtente, password)) {
			        //session = request.getSession();
			        //session.setAttribute("username", nomeUtente);
			        
			        Cookie usernameCookie = new Cookie("username", base64EncodedUsername);
					Cookie passwordCookie = new Cookie("password", base64EncodedPassword);

					usernameCookie.setPath("/");
					passwordCookie.setPath("/");

					usernameCookie.setMaxAge(10 * 60); // 10 minuti
					passwordCookie.setMaxAge(10 * 60); // 10 minuti

					response.addCookie(usernameCookie);
					response.addCookie(passwordCookie);
					
					request.setAttribute("nomeUtente", nomeUtente);
					request.setAttribute("login", true); //Se questa variabile non viene inizializzata su true, l'utente non riesce ad accedere a benvenuto.jsp

			        GestionePassword.clearBytes(password);
			        nomeUtente = null;
			        
			        request.getRequestDispatcher("welcome.jsp").forward(request, response);
			    } else {
			        System.out.println("ERRORE metodo isUserValid checked - username: " + nomeUtente);
			        System.out.println("ERRORE metodo isUserValid checked - password: " + string_Password);

			        nomeUtente = null;
			        GestionePassword.clearBytes(password);

			        CustomMessage.showPanel("Sembra che questo utente non esista! Controlla i dati inseriti.");
			        request.getRequestDispatcher("/login.jsp").forward(request, response);
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			}

		} else {

			try {
				if (LoginDao.isUserValid(nomeUtente, password)) {
					
					//session = request.getSession();
					//session.setAttribute("username", nomeUtente);

					request.setAttribute("login", true); //Se questa variabile non viene inizializzata su true, l'utente non riesce ad accedere a benvenuto.jsp
					request.setAttribute("nomeUtente", nomeUtente);

					GestionePassword.clearBytes(password);
					nomeUtente = null;

					request.getRequestDispatcher("welcome.jsp").forward(request, response);
				} else {
					System.out.println("ERRORE metodo isUserValid 2 - username: " + nomeUtente);
					System.out.println("ERRORE metodo isUserValid 2 - password: " + string_Password);

					nomeUtente = null;
					GestionePassword.clearBytes(password);

					CustomMessage.showPanel("Password errata! Riprova.");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//checkCookie(request, response);
	}

	private byte[] pad(byte[] bytes) {
		int paddingLength = 16 - bytes.length % 16;
		byte[] paddingBytes = new byte[paddingLength];
		Arrays.fill(paddingBytes, (byte) 0x00);

		return Arrays.copyOf(bytes, bytes.length + paddingLength);
	}

	private static String byteArrayToString(byte[] byteArray) {
		return new String(byteArray, StandardCharsets.UTF_8);
	}

	private static byte[] removePadding(byte[] bytes) {
		int paddingValue = bytes[bytes.length - 1]; // Ottieni l'ultimo byte, che rappresenta il valore di padding
		int unpaddedLength = bytes.length - paddingValue;

		// Verifica se i byte finali sono effettivamente byte di padding con valore diverso da zero
		for (int i = unpaddedLength; i < bytes.length; i++) {
			if (bytes[i] != 0x00) {
				// I byte finali non sono tutti byte di padding, restituisci l'array originale
				return bytes;
			}
		}

		// Ritorna il nuovo array senza i byte di padding
		return Arrays.copyOf(bytes, unpaddedLength);
	}

	private void checkCookie(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=UTF-8");

	    JsonObject cookieData = new JsonObject();

	    String string_encryptedUsernameCookie = null;
	    String string_encryptedPasswordCookie = null;

	    String usernameCookie = null;
	    String passwordCookie = null;

	    byte[] byte_encryptedPasswordCookie = null;
	    byte[] byte_encryptedUsernameCookie = null;

	    Cookie[] cookies = request.getCookies();

	    boolean foundUsernameCookie = false;
	    boolean foundPasswordCookie = false;

	    if (cookies != null && cookies.length > 0) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("username")) {
	                foundUsernameCookie = true;
	                string_encryptedUsernameCookie = cookie.getValue();
	                byte_encryptedUsernameCookie = Base64.getDecoder().decode(string_encryptedUsernameCookie);
	            } else if (cookie.getName().equals("password")) {
	                foundPasswordCookie = true;
	                string_encryptedPasswordCookie = cookie.getValue();
	                byte_encryptedPasswordCookie = Base64.getDecoder().decode(string_encryptedPasswordCookie);
	            } else if (!cookie.getName().equals("JSESSIONID")) {
	                // Se il cookie non si chiama "username" o "password" o "JSESSIONID", invalidalo
	                cookie.setValue("");  // Imposta il valore del cookie a una stringa vuota
	                cookie.setMaxAge(0);
	                response.addCookie(cookie);
	            }
	        }
	    }

	    if (!foundUsernameCookie || !foundPasswordCookie) {
	        System.out.println("Uno dei due cookie non e' stato trovato, interrompo");
	    } else {
	        try {
	            byte[] decryptedUsernameBytesCookie = Aes.decrypt(byte_encryptedUsernameCookie);
	            byte[] decryptedPasswordBytesCookie = Aes.decrypt(byte_encryptedPasswordCookie);

	            decryptedUsernameBytesCookie = removePadding(decryptedUsernameBytesCookie);
	            decryptedPasswordBytesCookie = removePadding(decryptedPasswordBytesCookie);

	            usernameCookie = byteArrayToString(decryptedUsernameBytesCookie);
	            passwordCookie = byteArrayToString(decryptedPasswordBytesCookie);

	            // RIEMPIRE IL FORM DEL login.jsp con questi dati se non sono nulli
	            request.setAttribute("decryptedUsername", usernameCookie);
	            request.setAttribute("decryptedPassword", passwordCookie);

	            cookieData.addProperty("cookiesPresent", true);
	            cookieData.addProperty("decryptedUsername", usernameCookie);
	            cookieData.addProperty("decryptedPassword", passwordCookie);
	        } catch (Exception e) {
	            System.out.println("ERRORE CON L'ACCESSO CON I COOKIE NEL DO POST");
	            e.printStackTrace();
	        }
	    }

	    response.getWriter().write(cookieData.toString());
	}
}