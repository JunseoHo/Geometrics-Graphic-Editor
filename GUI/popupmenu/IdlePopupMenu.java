package popupmenu;

import java.awt.Point;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import canvas.GCanvas;
import shape.GShape;
import system.GUIText;
import util.GDialog;

@SuppressWarnings("serial")
public class IdlePopupMenu extends JPopupMenu {

	enum IdlePopupMenuItem {

		PASTE(GUIText.get(59), "paste");

		private String text 	  = null;
		private String methodName = null;

		private IdlePopupMenuItem(String text, String methodName) {
			this.text 		= text;
			this.methodName = methodName;
		}

		public String getText() { return text; }

		public String getMethodName() { return methodName; }

	}

	// Associations
	private GCanvas canvas = null;
	private Point 	point  = null;

	public IdlePopupMenu(GCanvas canvas, Point point) {
		// Set Associations
		this.canvas = canvas;
		this.point  = point;

		// Add Menu Items
		for (IdlePopupMenuItem item : IdlePopupMenuItem.values()) {

			JMenuItem menuItem = new JMenuItem(item.getText());
			menuItem.addActionListener(e -> invokeMethod(e.getActionCommand()));
			menuItem.setActionCommand(item.getMethodName());
			
			add(menuItem);

		}

	}

	public void invokeMethod(String methodName) {

		try {
			
			getClass().getMethod(methodName).invoke(this);
			
		} catch (Exception e) { GDialog.showExceptionDialog(e); }

	}

	public void paste() {
		canvas.backUp();
		
		
		if(canvas.getCopiedShape() == null) {
			GDialog.showMessageDialog(canvas, GUIText.get(67), GUIText.get(68), GDialog.WARNING_MESSAGE);
			return;
		}
		
		GShape copiedShape = canvas.getCopiedShape().clone();
		
		copiedShape.setLocation(point);
		
		canvas.addShape(copiedShape);
		
		canvas.repaint();
	}

}
