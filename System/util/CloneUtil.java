package util;

import java.io.ByteArrayInputStream; 
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CloneUtil {
	
	public static Object deepClone(Object object) {
		
		try {
			// Write Object
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream 	  oos  = new ObjectOutputStream(baos);
			
			oos.writeObject(object);
			
			// Read Object
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream 	 ois  = new ObjectInputStream(bais);
			
			return ois.readObject();
			
		} catch (Exception e) { GDialog.showExceptionDialog(e); }
		
		return null;
		
	}
	
	
}
