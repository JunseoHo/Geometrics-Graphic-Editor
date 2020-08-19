package anchor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.LinkedList;

import shape.ShapeArea;
import system.GSystem;
import util.GComposite;
import util.GMath;
import util.ShapeUtil;

@SuppressWarnings("serial")
public class GAnchors implements Serializable {
	// Attributes
	protected double 	  	rotatedAngle  		= 0; 	// Unit is radian
	protected Rectangle 	frame 				= null; // Frame of coordinate of anchors
	
	// Anchors Constants
	protected final int 	ANCHOR_WIDTH 		= 12;
	protected final int 	ANCHOR_HEIGHT 		= 12;
	protected final Color 	ANCHOR_FILL_COLOR	= Color.WHITE;
	protected final Color 	ANCHOR_DRAW_COLOR 	= Color.BLACK;
	protected final int 	ANCHOR_WEIGHT 		= 1;
	protected final float 	ANCHOR_OPACITY 		= 1.0f;

	// Components
	protected LinkedList<GAnchor> anchors = new LinkedList<>();


	public GAnchors() {
		// Initialize Components
		anchors.clear();
		
		for (int i = 0; i < ShapeArea.values().length - 1; i++) // Exclude INNER_AREA
			anchors.add(new GAnchor(ShapeArea.values()[i]));
	
	}
	
	
	public void setFrame(Rectangle frame) {
		// Initialize New Bounds
		this.frame = frame;
		
		// Set Coordinate Of Anchors By New Frame
		for (GAnchor anchor : anchors)
			setCoordinate(anchor, frame);

	}

	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d.setComposite(GComposite.getSourceOver(ANCHOR_OPACITY));
		g2d.setStroke(new BasicStroke(ANCHOR_WEIGHT));
		
		// Paint Anchors
		for (Rectangle2D anchor : anchors) {
			
			Shape shape = transform(anchor);
			
			g2d.setColor(ANCHOR_FILL_COLOR);
			g2d.fill(shape);
			
			g2d.setColor(ANCHOR_DRAW_COLOR);
			g2d.draw(shape);
			
		}
			
		
	}
	
	public ShapeArea contains(Point p) {
		
		for (GAnchor anchor : anchors) {
			if (transform(anchor).contains(p.getX(), p.getY())) 
				return anchor.getShapeArea();
		}
		
		return null;
	}

	public void setRotatedAngle(double angle) {
		this.rotatedAngle = angle;
	}

	public Rectangle getFrame() {
		return frame;
	}

	public Point getCenterPoint() {
		return ShapeUtil.getCenterPoint(frame);
	}
	
	public void resize(Point p, ShapeArea area) {
		// Get The Untransformed Point.
		p = GMath.rotatePointCounterClockwise(p, getCenterPoint(), rotatedAngle);

		// Resize Frame
		Rectangle resizedFrame = new Rectangle(frame);
		area.resizeBounds(p, resizedFrame);
		
		// Correct Location 
		if(GSystem.correction) {
			area.correctLocation(frame, resizedFrame, rotatedAngle);
		}
		

		// Set Bounds
		setFrame(resizedFrame);
	}
	
	protected void setCoordinate(GAnchor anchor, Rectangle bounds) {
		// Get The Certain Point Corresponding To The Anchor
		Point areaPoint = anchor.getAreaPoint(bounds);

		// Correct Location
		areaPoint.translate(-ANCHOR_WIDTH / 2, -ANCHOR_HEIGHT / 2);

		// Set Frame Of Anchor
		anchor.setFrame(areaPoint.getX(), areaPoint.getY(), ANCHOR_WIDTH, ANCHOR_HEIGHT);
	}
	
	protected Shape transform(Shape shape) {
		return ShapeUtil.getShapeRotatedClockWise(shape, getCenterPoint(), rotatedAngle);
	}

	
	// Inner Class
	protected class GAnchor extends Rectangle2D.Double {
		
		private ShapeArea area = null;

		public GAnchor(ShapeArea area) { this.area = area; }

		public ShapeArea getShapeArea() { return area; }
		
		public Point getAreaPoint (Rectangle bounds) { return area.getAreaPoint(bounds); }

	}

}
