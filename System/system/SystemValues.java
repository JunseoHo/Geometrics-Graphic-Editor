package system;

public interface SystemValues {
	
	public enum Value_LAF implements SystemValues{ // Look And Feel

		NIMBUS("javax.swing.plaf.nimbus.NimbusLookAndFeel"),

		METAL("javax.swing.plaf.metal.MetalLookAndFeel"),

		WINDOW("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		
		

		private String className = null;
		
		private Value_LAF(String className) { this.className = className; }
		
		public String getClassName() { return className; }

	}
	
	public enum Value_Language implements SystemValues {
		
		ENGLISH("ENGLISH"),
		
		KOREAN("KOREAN");
		
		
		private String fileName = null;

		private Value_Language(String fileName) { this.fileName = fileName; }
		
		public String getFileName() { return fileName; }
		

	}
	
	
}
