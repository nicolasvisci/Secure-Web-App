package registration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import javax.servlet.http.Part;

import pannel.CustomMessage;
import password.PasswordManagement;

@MultipartConfig
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

		String username = request.getParameter("username");
		String regex = "^[a-zA-Z0-9]+$";
		byte[] password = request.getParameter("password").getBytes();
		byte[] confirm_password = request.getParameter("conferma_password").getBytes();
		Part filePart = request.getPart("ImmagineProfilo");

		if (username.matches(regex) && username.length() <= 45) {
			if (PasswordManagement.isStrongPassword(password)) {
				if (Arrays.equals(password, confirm_password)) {
					if (CheckFile.checkImageFile(filePart)) {
						System.out.println("File valido");

						byte[] salt = PasswordManagement.generateRandomBytes(16);
						byte[] newPassword = PasswordManagement.concatenateAndHash(password, salt);

						try {
							if (RegistrationDao.userRegistration(username, newPassword, salt, filePart)) {

								username = null;
								PasswordManagement.clearBytes(newPassword);
								PasswordManagement.clearBytes(password);
								PasswordManagement.clearBytes(confirm_password);
								PasswordManagement.clearBytes(salt);

								response.sendRedirect("login.jsp");
							} else {

								username = null;
								PasswordManagement.clearBytes(newPassword);
								PasswordManagement.clearBytes(password);
								PasswordManagement.clearBytes(confirm_password);
								PasswordManagement.clearBytes(salt);
								CustomMessage.showPanel("Errore durante la registrazione!");
								request.getRequestDispatcher("registration.jsp").forward(request, response);

							}
						} catch (Exception e) {
							e.printStackTrace();
							CustomMessage.showPanel("Errore durante la registrazione!");
							request.getRequestDispatcher("registration.jsp").forward(request, response);
						}
					} else {
						CustomMessage.showPanel("Immagine non valida!");
						request.getRequestDispatcher("registration.jsp").forward(request, response);

					}
				} else {
					CustomMessage.showPanel("Le password non corrispondono!");
					request.getRequestDispatcher("registration.jsp").forward(request, response);

				}
			} else {
				CustomMessage.showPanel("La password non rispetta i requisiti minimi di sicurezza!");
				request.getRequestDispatcher("registration.jsp").forward(request, response);

			}

		} else {

			CustomMessage.showPanel("Il nome contiene caratteri non validi!");
			request.getRequestDispatcher("registration.jsp").forward(request, response);

		}

	}
}
