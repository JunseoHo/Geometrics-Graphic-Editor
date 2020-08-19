package util;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import system.GPath;

public class GCustomCursor {
	// Cursors
	public final static Cursor ROTATE = getCustomCursor(GPath.ROTATE_CURSOR_IMAGE_PATH, 10);

	
	private static Cursor getCustomCursor(String imagePath, int range) {

		Toolkit toolKit = Toolkit.getDefaultToolkit();

		Point point = new Point(range, range);

		return toolKit.createCustomCursor(toolKit.getImage(imagePath), point, null);

	}

}
