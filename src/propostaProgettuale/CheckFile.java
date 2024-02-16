package propostaProgettuale;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.apache.tika.Tika;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pannel.CustomMessage;

public class CheckFile {

	static boolean checkPropostaFile(Part filePart, ServletContext context) throws IOException {
		// Controlla se il file e' stato effettivamente caricato
		if (filePart != null && filePart.getSize() > 0) {
			// Ottieni il nome del file
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			System.out.println("fileName: " + fileName);
			// Controlla l'estensione del file
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if ("txt".equals(fileExtension)) {

				String realPath = context.getRealPath("/");
				Path filePath = Paths.get(realPath, fileName);
				System.out.println("filePath: " + filePath);

				return true;
			} else {
				// L'estensione del file non e' ".txt"
				CustomMessage.showPanel("Puoi caricare solo file di testo in formato txt!");
			}
		} else {
			// Nessun file caricato
			CustomMessage.showPanel("Devi caricare una proposta progettuale!");
		}
		// Se si arriva qui, qualcosa e' andato storto, restituisci false
		return false;
	}

	static String processFile(Part filePart) {

		try {
			long maxSizeInBytes = 20 * 1024 * 1024;
			if (filePart.getSize() > maxSizeInBytes) {
				CustomMessage.showPanel(
						"Il file supera la dimensione massima consentita. Il file puo' essere massimo di 20 MB");
				return null;
			}

			// Controlla content-type con Tika
			Tika tika = new Tika();
			String contentType = tika.detect(filePart.getInputStream());

			if ("text/plain".equals(contentType) || "text/html".equals(contentType)) {

				// Leggi il contenuto del filePart
				InputStream fileContent = filePart.getInputStream();
				byte[] contentBytes = new byte[fileContent.available()];
				fileContent.read(contentBytes);
				String content = new String(contentBytes, StandardCharsets.UTF_8);

				// Rimuovi gli script JavaScript, compreso il testo all'interno

				Document document = Jsoup.parse(content);
				document.select("script, [type=application/javascript], [type=text/javascript]").remove();
				document.select("[text]").unwrap(); // Rimuovi anche il testo all'interno dei tag script

				document.select("[onclick]").removeAttr("onclick");
				document.select("[onload]").removeAttr("onload");

				String cleanedHtml = document.toString();

				return cleanedHtml;
			} else {
				CustomMessage.showPanel("Il file contiene del testo non valido!");

				// Restituisci null quando il file non e' valido
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			CustomMessage.showPanel("C'e' stato un problema con il caricamento del file!");

			// Restituisci null in caso di eccezione
			return null;
		}
	}

	private static String getFileNameFromPath(String filePath) {
		Path path = Paths.get(filePath);
		return path.getFileName().toString();
	}

	static String getFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		String[] tokens = contentDisposition.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				// Ottieni solo il nome del file dal percorso completo
				String fileNameWithExtension = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
				return getFileNameFromPath(fileNameWithExtension);
			}
		}
		return "";
	}
}
