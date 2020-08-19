package main;

import mainframe.GMainFrame;
import system.GSystem;

public class GMain {

	public static void main(String[] args) {
		// Load Main System
		GSystem.load();
	 
		// Show Frame
		new GMainFrame().setVisible(true);
		
	}	
	
}
