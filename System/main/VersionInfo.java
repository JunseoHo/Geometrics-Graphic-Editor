package main;

import system.GUIText;

public final class VersionInfo {
	
	
	private static final String CURRENT_VERSION  = "1.0.0";
	
	
	private static final String PROJECT_NAME 	 = "Pattern-Oriented thinking and programming";
	
	
	private static final String RELEASED_DATE 	 = "2020-06-16";
	
	
	private static final String AUTHOR			 = "Junseo Ho";
	
	
	public static String getVersionInfo() {
		String info = "";
		
		info += GUIText.get(1) + " : " + CURRENT_VERSION + "\n";
		info += GUIText.get(2) + " : " + PROJECT_NAME 	 + "\n";
		info += GUIText.get(3) + " : " + RELEASED_DATE 	 + "\n";
		info += GUIText.get(4) + " : " + AUTHOR;
		
		return info;
	}
	
	

}
