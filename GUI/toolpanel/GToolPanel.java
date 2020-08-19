package toolpanel;

import java.awt.GridLayout;

import javax.swing.JButton;

import canvas.GCanvas;
import common.GPanel;
import system.GUIText;

@SuppressWarnings("serial")
public class GToolPanel extends GPanel {

	// Associations
	private GCanvas canvas = null;

	public GToolPanel() {
		// Set Attributes
		setLayout(new GridLayout(15, 1, 0, 3));

		// Add GUI Components
		for (GToolItem item : GToolItem.values()) {

			JButton button = new JButton(GUIText.get(item.getTextCode()));
			
			button.addActionListener(e -> canvas.setCurrentTool(item.getTool()));
			
			add(button);

		}

	}

	public void setAssociation(GCanvas canvas) { this.canvas = canvas; }

}
