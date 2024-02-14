package logout;

import java.io.IOException;
import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pannel.CustomMessage;

@ThreadSafe
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
			System.out.println("Sessione invalidata");
		}

		// Elimina i cookie dopo aver invalidato la sessione
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				System.out.println("Nome del cookie: " + cookieName);
		        cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			System.out.println("Invalido cookie");
		}

		CustomMessage.showPanel("Log-Out effettuato correttamente!");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
}