package toolpanel;

import shape.GShape;
import shape.brush.GPen;
import shape.doublepoints.GArrow;
import shape.doublepoints.GEllipse;
import shape.doublepoints.GLine;
import shape.doublepoints.GRectangle;
import shape.doublepoints.GRhombus;
import shape.image.GImage;
import shape.multipoints.GPolygon;
import shape.multipoints.GPolyline;
import shape.text.GText;

public enum GToolItem {

	RECT(7, new GRectangle()),

	ELLIPSE(8, new GEllipse()),

	LINE(9, new GLine()),
	
	POLYLINE(10, new GPolyline()),
	
	POLYGON(11, new GPolygon()),
	
	RHOMBUS(72, new GRhombus()),
	
	ARROW(73, new GArrow()),
	
	PEN(12, new GPen()),
	
	IMAGE(13, new GImage(null)),
	
	TEXT(84, new GText(null));
	
	
	// Attributes
	private int textCode = 0;
	private GShape tool = null;

	private GToolItem(int textCode, GShape tool) {
		this.textCode = textCode;
		this.tool = tool;
	}
	
	public int getTextCode() { return textCode; }
	
	public GShape getTool() { return tool; }

}
