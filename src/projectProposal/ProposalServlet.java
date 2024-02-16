package projectProposal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import pannel.CustomMessage;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet("/PropostaServlet")
@MultipartConfig
public class ProposalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProposalServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Proposal> proposte = Proposal.getProposte();

		// Converti la lista in JSON
		String jsonProposte = new Gson().toJson(proposte);

		// Invia la risposta JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonProposte);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Part filePart = request.getPart("Proposta progettuale");
		String nomeUtente = request.getParameter("nomeUtente");

		if (CheckFile.checkPropostaFile(filePart, getServletContext())) {

			String cleanedHtml = CheckFile.processFile(filePart);

			String nomeFile = CheckFile.getFileName(filePart);
			// Aggiungi il contenuto filtrato all'oggetto request
			request.setAttribute("contenutoFiltrato", cleanedHtml);
			byte[] htmlBytes = cleanedHtml.getBytes(StandardCharsets.UTF_8);

			try {
				if (ProposalDao.uploadFile(nomeUtente, htmlBytes, nomeFile)) {
					CustomMessage.showPanel("La proposta e' stata correttamente caricata!");

					// Invia il contenuto filtrato come risposta AJAX
					response.setContentType("text/plain");
					response.getWriter().write(cleanedHtml);
				} else {
					CustomMessage.showPanel("Non e' stato possibile caricare il file della proposta!");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			CustomMessage.showPanel("File non valido!");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}