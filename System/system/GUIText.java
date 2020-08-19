package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import system.SystemValues.Value_Language;

public final class GUIText {
	// Attributes
	private final static String SEPARATOR = ":";
	
	// Components
	private static List<String> texts = new Vector<>();
	
	// Working Variables
	private static boolean loaded = false;
	
	
	private GUIText() {  /* DON'T LET ANYONE INSTANTIATE THIS CLASS */ };

	
	public static void load(Value_Language lang) throws FileNotFoundException {
		// Clear List
		texts.clear();
		
		// Load GUI Texts
		File 	langFile	= new File(GPath.LANGUAGE_PATH + lang.getFileName());
		Scanner scanner 	= new Scanner(langFile);

		while (scanner.hasNext())
			texts.add(scanner.nextLine().split(SEPARATOR)[1]);

		scanner.close();

		// Set Flag
		loaded = true;
	}

	public static String get(int i) {
		return texts.get(i);
	}

	public static boolean isLoaded() {
		return loaded;
	}

}
