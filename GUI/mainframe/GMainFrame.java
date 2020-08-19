package mainframe;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.JFrame;

import canvas.GCanvas;
import canvas.GCanvasKeyDispatcher;
import menu.GMenuBar;
import system.GSystem;
import toolpanel.GToolPanel;

@SuppressWarnings("serial")
public class GMainFrame extends JFrame {

	// GUI Components
	private GMenuBar 	menuBar 	= null;
	private GToolPanel  toolPanel 	= null;
	private GCanvas 	canvas 		= null;
	

	{
		// Set Attributes
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		setFocusable(true);
		
		// Create & Register Components
		menuBar = new GMenuBar();
		setJMenuBar(menuBar);

		toolPanel = new GToolPanel();
		add(toolPanel, BorderLayout.WEST);
		
	
	}

	public GMainFrame(GCanvas canvas) {
		// Add Canvas
		this.canvas = canvas;
		add(canvas, BorderLayout.CENTER);

		// Set Associations
		menuBar.setAssociation(canvas);
		toolPanel.setAssociation(canvas);
		
		// Key Dispatch
		keyDispatch(new GCanvasKeyDispatcher(canvas));
	}

	public GMainFrame() {
		// Add Canvas
		canvas = new GCanvas();
		add(canvas, BorderLayout.CENTER);

		// Set Associations
		menuBar.setAssociation(canvas);
		toolPanel.setAssociation(canvas);
		
		// Key Dispatch
		keyDispatch(new GCanvasKeyDispatcher(canvas));
	}

	public void restart() {
		GSystem.load();
		
		new GMainFrame(canvas).setVisible(true);
		
		dispose();
	}
	
	private void keyDispatch(KeyEventDispatcher dispatcher) {
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(dispatcher);
	}

}
