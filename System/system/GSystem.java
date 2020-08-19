package system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;

import system.SystemValues.Value_LAF;
import system.SystemValues.Value_Language;
import util.GDialog;

public final class GSystem {

	private GSystem() { /* DON'T LET ANYONE INSTANTIATE THIS CLASS */ }

	// System Attribute Keys
	public static final String KEY_LANGUAGE 	= "Language";
	public static final String KEY_LOOKANDFEEL  = "LAF";

	// System Attributes Values
	private static Value_Language language 		= null;
	private static Value_LAF 	  lookandfeel 	= null;
	
	
	public static boolean		  correction	= true;
	
	
	public static void load() {
		setAttributes();
		applyToSystem();
		loadBackUp();
	}


	private static void setAttributes() {
		try {
			
			// Load System File
			Properties properties = new Properties();
			properties.load(new FileInputStream(GPath.INI_PATH));

			// Initialize Attribute Values
			language 	= Value_Language.valueOf(properties.getProperty(KEY_LANGUAGE));
			lookandfeel = Value_LAF.valueOf(properties.getProperty(KEY_LOOKANDFEEL));

		} catch (IOException e) { GDialog.showExceptionDialog(e); }
		
	}

	private static void applyToSystem() {
		try {
		
			// Apply Language
			GUIText.load(language);
			
			// Apply Look&Feel
			UIManager.setLookAndFeel(lookandfeel.getClassName());
			
		} catch (Exception e) { GDialog.showExceptionDialog(e); }
		
	}
	
	
	public static void setSystemValue(String key, SystemValues value) {
		try {
			
			Properties properties = new Properties();
			properties.load(new FileInputStream(GPath.INI_PATH));
			
			properties.setProperty(key, value.toString());
			
			properties.store(new FileOutputStream(GPath.INI_PATH), null);
			
		} catch (IOException e) { GDialog.showExceptionDialog(e); }
	}
	
	public static void reloadGUIText() {
		
		try {
			
			GUIText.load(language);
			
		} catch (FileNotFoundException e) { GDialog.showExceptionDialog(e); }
		
	}
	

	private static void loadBackUp() {
		File backupFile = new File(GPath.BACKUP_FILE_PATH);
		
		if(backupFile.exists()) { GFileManager.setCurrentFile(backupFile); }
		
	}

}
