package popupmenu;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import canvas.GCanvas;
import shape.GShape;
import system.GUIText;
import util.GDialog;

@SuppressWarnings("serial")
public class FocusPopupMenu extends JPopupMenu {

	enum FocusPopupMenuItem {

		FOREMOST(GUIText.get(44), "foremost"),

		BACKMOST(GUIText.get(45), "backmost"),

		SET_PAINTCOLOR(GUIText.get(66), "setPaintColor"),

		COPY(GUIText.get(58), "copy"),

		DELETE(GUIText.get(60), "delete"),
		
		ATTRIBUTES(GUIText.get(74), "getAttributes");

		private String text = null;
		private String methodName = null;

		private FocusPopupMenuItem(String text, String methodName) {
			this.text = text;
			this.methodName = methodName;
		}

		public String getText() {
			return text;
		}

		public String getMethodName() {
			return methodName;
		}

	}

	// Associations
	private GShape shape = null;
	private GCanvas canvas = null;

	public FocusPopupMenu(GShape shape, GCanvas canvas) {
		// Set Associations
		this.shape = shape;
		this.canvas = canvas;

		// Add Menu Items
		for (FocusPopupMenuItem item : FocusPopupMenuItem.values()) {

			JMenuItem menuItem = new JMenuItem(item.getText());
			menuItem.addActionListener(e -> invokeMethod(e.getActionCommand()));
			menuItem.setActionCommand(item.getMethodName());
			add(menuItem);

		}

	}

	public void invokeMethod(String methodName) {

		try {

			getClass().getMethod(methodName).invoke(this);

		} catch (Exception e) {
			GDialog.showExceptionDialog(e);
		}

	}

	public void foremost() {
		canvas.backUp();
		
		LinkedList<GShape> shapes = canvas.getShapes();

		shapes.remove(shape);
		shapes.addLast(shape);

		canvas.repaint();
	}

	public void backmost() {
		canvas.backUp();
		
		LinkedList<GShape> shapes = canvas.getShapes();

		shapes.remove(shape);
		shapes.addFirst(shape);

		canvas.repaint();
	}

	public void setPaintColor() {
		@SuppressWarnings("static-access")
		Color c = new JColorChooser().showDialog(canvas, GUIText.get(47), shape.getPaintColor());
		shape.setPaintColor(c);
		canvas.repaint();
	}

	public void copy() {
		canvas.setCopiedShape(shape);
	}

	public void delete() {
		canvas.backUp();
		canvas.removeShape(shape);
		canvas.repaint();
	}
	
	public void getAttributes() {
		GDialog.showAttributesDialog(shape);
	}

}
