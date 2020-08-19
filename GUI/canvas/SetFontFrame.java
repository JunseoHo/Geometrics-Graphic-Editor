package canvas;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import canvas.CanvasEnums.BuiltInFont;
import canvas.CanvasEnums.FontStyle;
import system.GUIText;
import util.GDialog;

@SuppressWarnings("serial")
public class SetFontFrame extends JFrame {
	// Associations
	private GCanvas canvas = null;

	// Components
	private JLabel lbl = null;
	private JComboBox<String> cbFontName = null;
	private JComboBox<String> cbFontStyle = null;
	private JTextField tfFontSize = null;
	private JButton btn = null;

	public SetFontFrame(GCanvas canvas) {
		// Set Associations
		this.canvas = canvas;

		// Set Atrributes
		setTitle(GUIText.get(87));
		setSize(300, 150);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(4, 2));

		// Add Components
		lbl = new JLabel(GUIText.get(85));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		add(lbl);

		cbFontName = new JComboBox<>(getFontNames());
		add(cbFontName);

		lbl = new JLabel(GUIText.get(86));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		add(lbl);

		cbFontStyle = new JComboBox<>(getFontStyle());
		add(cbFontStyle);

		lbl = new JLabel(GUIText.get(88));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		add(lbl);

		tfFontSize = new JTextField(20);
		add(tfFontSize);

		btn = new JButton(GUIText.get(87));
		btn.addActionListener(e -> setFont());
		add(btn);
	}

	private void setFont() {

		String text = tfFontSize.getText();

		try {
			int size = Integer.parseInt(text);

			Font font = new Font((String) cbFontName.getSelectedItem(),
					FontStyle.valueOf((String) cbFontStyle.getSelectedItem()).getStyle(), size);

			canvas.setFont(font);
			
			dispose();
		} catch (NumberFormatException e) {
			GDialog.showMessageDialog(canvas, GUIText.get(89));
			return;
		}

	}

	private String[] getFontNames() {

		int length = BuiltInFont.values().length;

		String[] fontNames = new String[length];

		for (int i = 0; i < length; i++) {
			fontNames[i] = BuiltInFont.values()[i].getFontName();
		}

		return fontNames;

	}

	private String[] getFontStyle() {

		int length = FontStyle.values().length;

		String[] styles = new String[length];

		for (int i = 0; i < length; i++) {
			styles[i] = FontStyle.values()[i].toString();
		}

		return styles;

	}

}
