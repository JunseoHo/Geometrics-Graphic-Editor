package util;

import java.awt.Cursor;

public enum GCursor {

	DEFAULT(Cursor.DEFAULT_CURSOR),

	CROSSHAIR(Cursor.CROSSHAIR_CURSOR),
	
	ROTATE(GCustomCursor.ROTATE),

	E_RESIZE(Cursor.E_RESIZE_CURSOR),

	W_RESIZE(Cursor.W_RESIZE_CURSOR),

	N_RESIZE(Cursor.N_RESIZE_CURSOR),

	S_RESIZE(Cursor.S_RESIZE_CURSOR),

	NE_RESIZE(Cursor.NE_RESIZE_CURSOR),

	SE_RESIZE(Cursor.SE_RESIZE_CURSOR),

	NW_RESIZE(Cursor.NW_RESIZE_CURSOR),

	SW_RESIZE(Cursor.SW_RESIZE_CURSOR);

	
	private Cursor cursor = null;

	private GCursor(int cursor) { this.cursor = new Cursor(cursor); }
	
	private GCursor(Cursor cursor) { this.cursor = cursor; }

	public Cursor getCursor() { return cursor; }

}
