package util;

import java.awt.Color;

public class GColorEditor {

	public static Color inverse(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}

	public static Color transparentize(Color c, int opacity) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity);
	}

	public static Color grayScale(Color c) {
		
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		
		int avg = (r + g + b) / 3;
		
		return new Color(avg, avg, avg);
	}

}
