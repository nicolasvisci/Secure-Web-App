package login;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		session = request.getSession();
		
		String nomeUtente = request.getParameter("username");
		byte[] password = request.getParameter("pass").getBytes();
		boolean ricordami = request.getParameter("ricordami") != null;
		
		String string_Password = byteArrayToString(password);
		

		
		try {
			if (LoginDao.isUserValid(nomeUtente, password)) {
				
				request.setAttribute("login", true); //Se questa variabile non viene inizializzata su true, l'utente non riesce ad accedere a benvenuto.jsp
				request.setAttribute("nomeUtente", nomeUtente);
				
				nomeUtente = null;
				
				request.getRequestDispatcher("welcome.jsp").forward(request, response);
				
				
			} else {
				System.out.println("ERRORE metodo isUserValid 2 - username: " + nomeUtente);
				System.out.println("ERRORE metodo isUserValid 2 - password: " + string_Password);

				nomeUtente = null;

				System.out.println("Password errata! Riprova.");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String byteArrayToString(byte[] byteArray) {
		return new String(byteArray, StandardCharsets.UTF_8);
	}

}
