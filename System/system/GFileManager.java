package system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import util.GDialog;

public final class GFileManager {

	private GFileManager() { /* DON'T LET ANYONE INSTANTIATE THIS CLASS */ }
	
	
	// File Directories
	public static final File DEFAULT_SAVE_DIRECTORY  = new File(GPath.DEFAULT_SAVE_PATH);
	public static final File DEFAULT_IMAGE_DIRECTORY = new File(GPath.SAMPLE_IMAGE_PATH);
	
	// File Working Variable
	private static 		File currentFile		     = null;

	
	public static Object readFile(File file) {
		
		Object object = null;
		
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			object = ois.readObject();
			
			ois.close();

		} catch (IOException | ClassNotFoundException e) { GDialog.showExceptionDialog(e); }
		
		return object;
	}
	
	

	public static void writeFile(Object object, File file) {
		try {

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(object);
			
			oos.close();

		} catch (IOException e) { GDialog.showExceptionDialog(e); }

	}
	
	
	public static File getCurrentFile() { 
		return currentFile; 
	}
	
	
	public static void setCurrentFile(File file) { 
		currentFile = file;
	}


	public static void deleteBackUpFile() {
		File file = new File(GPath.BACKUP_FILE_PATH);
		
		if(file.exists()) { file.delete(); }
		
	}

}
