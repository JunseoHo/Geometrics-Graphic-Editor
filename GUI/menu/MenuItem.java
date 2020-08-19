package menu;

public interface MenuItem {

	enum FileMenuItem implements MenuItem {

		NEW(14, "newFile"),

		SAVE(15, "save"),

		SAVEAS(16, "saveAs"),

		LOAD(17, "load"),

		PRINT(18, "print"),
		
		SCREENSHOT(79, "screenCapture"),

		VERSION(19, "getVersionInfo"),

		EXIT(20, "exit");

		private int 	 mainTextCode	 = 0;
		private int[] subTextCodes 	 = null;
		private String[] methodNames = null;

		private FileMenuItem(int mainTextCode, int[] subTextCodes, String[] methodNames) {
			this.mainTextCode	 = mainTextCode;
			this.subTextCodes 	 = subTextCodes;
			this.methodNames = methodNames;
		}

		private FileMenuItem(int mainTextCode, String methodName) {
			this.mainTextCode 	 = mainTextCode;
			this.methodNames = new String[] { methodName };
		}

		@Override public int getMainTextCode() { return mainTextCode; }
		@Override public MenuItem[] getValues() { return values(); }
		@Override public int[] getSubTextCodes() { return subTextCodes; }
		@Override public String[] getMethodNames() { return methodNames; }

	}

	enum EditMenuItem implements MenuItem {

		UNDO(62, "undo"),

		REDO(63, "redo"),

		INVERSE(21, "inverse"),

		GRAYSCALE(61, "setGrayScale"),

		CLEAR(22, "clear");

		private int 	 mainTextCode	 = 0;
		private int[] subTextCodes 	 = null;
		private String[] methodNames = null;

		private EditMenuItem(int mainTextCode, int[] subTextCodes, String[] methodNames) {
			this.mainTextCode	 = mainTextCode;
			this.subTextCodes 	 = subTextCodes;
			this.methodNames = methodNames;
		}

		private EditMenuItem(int mainTextCode, String methodName) {
			this.mainTextCode 	 = mainTextCode;
			this.methodNames = new String[] { methodName };
		}

		@Override public int getMainTextCode() { return mainTextCode; }
		@Override public MenuItem[] getValues() { return values(); }
		@Override public int[] getSubTextCodes() { return subTextCodes; }
		@Override public String[] getMethodNames() { return methodNames; }

	}

	enum PaintMenuItem implements MenuItem {

		SET_PAINTMODE(23, new int[] { 24, 25 },
				new String[] { "setDrawMode", "setFillMode" }),

		SET_PAINTCOLOR(26, "setPaintColor"),

		SET_WEIGHT(27, "setWeight"),

		SET_OPACITY(28, "setOpacity"),
		
		SET_STROKEMODE(69, new int[] {70,71}, new String[] {"setStraightMode", "setDottedMode"});

		
		
		private int 	 mainTextCode	 = 0;
		private int[] subTextCodes 	 = null;
		private String[] methodNames = null;

		private PaintMenuItem(int mainTextCode, int[] subTextCodes, String[] methodNames) {
			this.mainTextCode	 = mainTextCode;
			this.subTextCodes 	 = subTextCodes;
			this.methodNames = methodNames;
		}

		private PaintMenuItem(int mainTextCode, String methodName) {
			this.mainTextCode 	 = mainTextCode;
			this.methodNames = new String[] { methodName };
		}

		@Override public int getMainTextCode() { return mainTextCode; }
		@Override public MenuItem[] getValues() { return values(); }
		@Override public int[] getSubTextCodes() { return subTextCodes; }
		@Override public String[] getMethodNames() { return methodNames; }
	}

	enum SettingMenuItem implements MenuItem {

		SET_CORRECTION(90, new int[] { 30,31 },
				new String[] { "onCorrection", "offCorrection" }),
		
		SET_ANTIALIASING(29, new int[] { 30,31 },
				new String[] { "onAntiAliasing", "offAntiAliasing" }),
		
		SET_FONT(87, "setFont");
		
		private int 	 mainTextCode	 = 0;
		private int[] subTextCodes 	 = null;
		private String[] methodNames = null;

		private SettingMenuItem(int mainTextCode, int[] subTextCodes, String[] methodNames) {
			this.mainTextCode	 = mainTextCode;
			this.subTextCodes 	 = subTextCodes;
			this.methodNames = methodNames;
		}

		private SettingMenuItem(int mainTextCode, String methodName) {
			this.mainTextCode 	 = mainTextCode;
			this.methodNames = new String[] { methodName };
		}

		@Override public int getMainTextCode() { return mainTextCode; }
		@Override public MenuItem[] getValues() { return values(); }
		@Override public int[] getSubTextCodes() { return subTextCodes; }
		@Override public String[] getMethodNames() { return methodNames; }

	}

	enum SystemMenuItem implements MenuItem {

		SET_LOOKANDFEEL(32, new int[] { 33,34,35 },
				new String[] { "setNimbusLAF", "setMetalLAF", "setWindowsLAF" }),

		SET_LANGUAGE(36, new int[] { 37,38 },
				new String[] { "setEnglishLang", "setKoreanLang" });

		
		private int 	 mainTextCode	 = 0;
		private int[] subTextCodes 	 = null;
		private String[] methodNames = null;

		private SystemMenuItem(int mainTextCode, int[] subTextCodes, String[] methodNames) {
			this.mainTextCode	 = mainTextCode;
			this.subTextCodes 	 = subTextCodes;
			this.methodNames = methodNames;
		}

		private SystemMenuItem(int mainTextCode, String methodName) {
			this.mainTextCode 	 = mainTextCode;
			this.methodNames = new String[] { methodName };
		}

		@Override public int getMainTextCode() { return mainTextCode; }
		@Override public MenuItem[] getValues() { return values(); }
		@Override public int[] getSubTextCodes() { return subTextCodes; }
		@Override public String[] getMethodNames() { return methodNames; }

	}

	public int getMainTextCode();

	public MenuItem[] getValues();

	public int[] getSubTextCodes();

	public String[] getMethodNames();

}
