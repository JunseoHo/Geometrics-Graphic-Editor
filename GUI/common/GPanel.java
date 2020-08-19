package common;

import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GPanel extends JPanel {

	public void addMouseAdapter(MouseAdapter a) {
		addMouseListener(a);
		addMouseMotionListener(a);
		addMouseWheelListener(a);
	}

	public void removeMouseAdapter(MouseAdapter a) {
		removeMouseListener(a);
		removeMouseMotionListener(a);
		removeMouseWheelListener(a);
	}

}
