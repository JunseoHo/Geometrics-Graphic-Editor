package util;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

import shape.GShape;
import system.GUIText;

@SuppressWarnings("serial")
public class GDialog extends JOptionPane {

	public static void showExceptionDialog(Exception e) {

		String title   = "Exception";
		String content = "Exception Occurred!";

		if (GUIText.isLoaded()) {
			title   = GUIText.get(5);
			content = GUIText.get(6);
		}

		showMessageDialog(null, content, title, ERROR_MESSAGE);

	}

	public static void showAttributesDialog(GShape shape) {
		String content = "";

		Rectangle bounds = shape.getBounds();

		content += GUIText.get(75) + " : " + bounds.getWidth() + GUIText.get(82) + "\n";

		content += GUIText.get(76) + " : " + bounds.getHeight() + GUIText.get(82) + "\n";

		Color c = shape.getPaintColor();

		content += GUIText.get(77) + " : " + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "\n";

		content += GUIText.get(78) + " : " + shape.getOpacity() * 100 + "%" + "\n";

		showMessageDialog(null, content, GUIText.get(74), INFORMATION_MESSAGE);

	}

	public static void showBackUpDialog() {
		String title   = GUIText.get(81);
		String content = GUIText.get(80);

		showMessageDialog(null, content, title, INFORMATION_MESSAGE);
	}

}
