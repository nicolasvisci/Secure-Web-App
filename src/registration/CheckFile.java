package registration;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.http.Part;

import org.apache.tika.Tika;

public class CheckFile {

	static boolean checkImageFile(Part filePart) throws IOException {
		
		if (filePart != null && filePart.getSize() > 0) {
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			
			if ("jpeg".equals(fileExtension) || "jpg".equals(fileExtension) || "png".equals(fileExtension)) {
				
				long maxSizeInBytes = 5 * 1024 * 1024; // 5 MB
				
				if (filePart.getSize() > maxSizeInBytes) {
					// Il file e' troppo grande
					System.out.println("L'immagine selezionata supera la dimensione massima consentita che e' di 5 MB");
					return false;

				}
				Tika tika = new Tika();
				String contentType = tika.detect(filePart.getInputStream());

				if (contentType != null && contentType.startsWith("image/")) {

				} else {

					System.out.println("Il file non e' un'immagine valida.");
					return false;
				}
			} else {

				System.out.println("Estensione del file non supportata.");
				return false;
			}
		} else {
			System.out.println("Nessun file caricato");
			return false;

		}
		return true;
	}

}
