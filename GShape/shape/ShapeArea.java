package shape;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;

import util.GCursor;
import util.ShapeUtil;

public enum ShapeArea {

	// Enum Values
	E_RESIZE_AREA(GCursor.E_RESIZE, ShapeUtil::getEPoint, ShapeUtil::resizeByEPoint, ShapeUtil::alignSWPoint),

	W_RESIZE_AREA(GCursor.W_RESIZE, ShapeUtil::getWPoint, ShapeUtil::resizeByWPoint, ShapeUtil::alignSEPoint),

	N_RESIZE_AREA(GCursor.N_RESIZE, ShapeUtil::getNPoint, ShapeUtil::resizeByNPoint, ShapeUtil::alignSEPoint),

	S_RESIZE_AREA(GCursor.S_RESIZE, ShapeUtil::getSPoint, ShapeUtil::resizeBySPoint, ShapeUtil::alignNEPoint),

	NE_RESIZE_AREA(GCursor.NE_RESIZE, ShapeUtil::getNEPoint, ShapeUtil::resizeByNEPoint, ShapeUtil::alignSWPoint),

	SE_RESIZE_AREA(GCursor.SE_RESIZE, ShapeUtil::getSEPoint, ShapeUtil::resizeBySEPoint, ShapeUtil::alignNWPoint),

	NW_RESIZE_AREA(GCursor.NW_RESIZE, ShapeUtil::getNWPoint, ShapeUtil::resizeByNWPoint, ShapeUtil::alignSEPoint),

	SW_RESIZE_AREA(GCursor.SW_RESIZE, ShapeUtil::getSWPoint, ShapeUtil::resizeBySWPoint, ShapeUtil::alignNEPoint),

	ROTATE_AREA(GCursor.ROTATE, ShapeUtil::getRotatePoint, null, null),

	INNER_AREA(GCursor.CROSSHAIR, null, null, null);

	
	
	// Atrributes
	private GCursor 		  cursor 			= null;
	private AreaPointGetter   areaPointGetter   = null;
	private BoundsResizer 	  boundsResizer 	= null;
	private LocationCorrector locationCorrector = null;

	// Constructor
	private ShapeArea(GCursor 		  	cursor, 
					  AreaPointGetter 	areaPointGetter, 
					  BoundsResizer 	boundsResizer,
					  LocationCorrector locationCorrector) {

		this.cursor = cursor;
		this.areaPointGetter = areaPointGetter;
		this.boundsResizer = boundsResizer;
		this.locationCorrector = locationCorrector;
	}

	// Methods
	public Cursor getCursor() { return cursor.getCursor(); }

	public Point getAreaPoint(Rectangle bounds) { return areaPointGetter.getAreaPoint(bounds); }

	public void resizeBounds(Point deltaPoint, Rectangle bounds) { boundsResizer.resizeBounds(deltaPoint, bounds); }

	public void correctLocation(Rectangle rect1, Rectangle rect2, double angle) { locationCorrector.correctLocation(rect1, rect2, angle); }

	
	// Inner Interfaces
	@FunctionalInterface private interface AreaPointGetter { public Point getAreaPoint(Rectangle bounds); }

	@FunctionalInterface private interface BoundsResizer { public void resizeBounds(Point p, Rectangle bounds); }

	@FunctionalInterface private interface LocationCorrector { public void correctLocation(Rectangle rect1, Rectangle rect2, double angle); }

}
