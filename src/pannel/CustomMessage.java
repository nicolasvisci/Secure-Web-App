package pannel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;

public class CustomMessage extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel label;

	CustomMessage(String message) {
		label = new JLabel(message);
		add(label);
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension preferredSize = super.getPreferredSize();
		int width = (int) preferredSize.getWidth() + 20;
		int height = (int) preferredSize.getHeight() + 20;
		return new Dimension(width, height);
	}

	public static void showPanel(String message) {
		// Crea una finestra e aggiungi il pannello
		JFrame frame = new JFrame("MESSAGGIO DAL SITO WEB: ");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		CustomMessage panel = new CustomMessage(message);
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);

		frame.setVisible(true);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				frame.setVisible(false);
			}
		});
	}
}