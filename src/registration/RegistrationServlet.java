package registration;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import password.GestionePassword;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nomeUtente = request.getParameter("username");
		String regex = "^[a-zA-Z0-9]+$";
		byte[] password = request.getParameter("password").getBytes();
		byte[] conferma_password = request.getParameter("conferma_password").getBytes();	
		
		if (nomeUtente.matches(regex) && nomeUtente.length() <= 45) {
			if (GestionePassword.isStrongPassword(password)) {
				if (Arrays.equals(password, conferma_password)) {

						byte[] sale = GestionePassword.generateRandomBytes(16);
						byte[] newPassword = GestionePassword.concatenateAndHash(password, sale);
						
						System.out.println("Password: " + password + " Sale:  " + sale +  " Password finale:  " + newPassword);

						try {
							if (RegistrationDao.registration(nomeUtente, newPassword, sale)) {

								nomeUtente = null;
								GestionePassword.clearBytes(newPassword);
								GestionePassword.clearBytes(password);
								GestionePassword.clearBytes(conferma_password);
								GestionePassword.clearBytes(sale);

								response.sendRedirect("login.jsp");
							} else {

								nomeUtente = null;
								GestionePassword.clearBytes(newPassword);
								GestionePassword.clearBytes(password);
								GestionePassword.clearBytes(conferma_password);
								GestionePassword.clearBytes(sale);
								System.out.println("Errore durante la registrazione!");
								request.getRequestDispatcher("registration.jsp").forward(request, response);
								
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Errore durante la registrazione!");
							request.getRequestDispatcher("registration.jsp").forward(request, response);
						}
				} else {
					System.out.println("Le password non corrispondono!");
					request.getRequestDispatcher("registration.jsp").forward(request, response);

				}
			} else {
				System.out.println("La password non rispetta i requisiti minimi di sicurezza!");
				request.getRequestDispatcher("registration.jsp").forward(request, response);

			}

		} else {

			System.out.println("Il nome contiene caratteri non validi!");
			request.getRequestDispatcher("registration.jsp").forward(request, response);

		}		
	}
}
