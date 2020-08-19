package canvas;

import java.awt.Font;

public interface CanvasEnums {

	public enum CanvasState {

		IDLE,

		PROCESSING

	}

	public enum PaintMode {

		DRAW,

		FILL

	}

	public enum BackUpState {

		UNDO,

		REDO

	}

	public enum StrokeMode {

		STRAIGHT,

		DOTTED

	}

	public enum BuiltInFont {

		MALGUN_GOTHIC("¸¼Àº °íµñ"),

		ARIAL("Arial"),

		CONSOLAS("Consolas");

		private String fontName = null;

		private BuiltInFont(String fontName) {
			this.fontName = fontName;
		}

		public String getFontName() {
			return fontName;
		}

	}

	public enum FontStyle {

		PLAIN(Font.PLAIN),

		BOLD(Font.BOLD),

		ITALIC(Font.ITALIC);

		private int style = 0;

		private FontStyle(int style) {
			this.style = style;
		}

		public int getStyle() {
			return style;
		}

	}

}
