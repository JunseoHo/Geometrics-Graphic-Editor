package shape.multipoints;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import shape.GShape;
import shape.ShapeArea;
import shape.ShapeStyle;
import util.GMath;

@SuppressWarnings("serial")
public class GPolyline extends GShape {
	
	public GPolyline(Shape shape, ShapeStyle style) { super(shape, style); }

	public GPolyline() { super(new Polygon(), ShapeStyle.MULTI_POINTS); }

	
	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d = setGrapgics2D(g2d);

		// Paint Shape
		Polygon polygon = (Polygon) shape;
		Point[] points = rotatePoints(polygon.xpoints, polygon.ypoints);

		int[] xPoints = getXPoints(points);
		int[] yPoints = getYPoints(points);

		g2d.drawPolyline(xPoints, yPoints, polygon.npoints);
		
		// Paint Anchors
		paintAnchors(g2d);
	}

	@Override
	public void setOrigin(Point p) {
		Polygon polygon = (Polygon) shape;
		
		polygon.addPoint(p.x, p.y);
		polygon.addPoint(p.x, p.y);
	}

	@Override
	public void setPoint(Point p) {
		Polygon polygon = (Polygon) shape;
		
		polygon.xpoints[polygon.npoints - 1] = p.x;
		polygon.ypoints[polygon.npoints - 1] = p.y;
	}

	@Override
	public void addPoint(Point p) {
		Polygon polygon = (Polygon) shape;
		
		polygon.addPoint(p.x, p.y);
	}

	@Override
	public ShapeArea contains(Point p) {
		// Initialize Shape Area
		ShapeArea area = null;

		// Check Anchor's Area
		area = containsForAnchors(p);

		if (area != null) { return area; }

		// Check Shape's Area
		Polygon polygon = (Polygon) shape;

		for (int i = 0; i < polygon.npoints - 1; i++) {
			int x1 = polygon.xpoints[i];
			int y1 = polygon.ypoints[i];
			int x2 = polygon.xpoints[i + 1];
			int y2 = polygon.ypoints[i + 1];

			Point p1 = GMath.rotatePointClockwise(new Point(x1, y1), getCenterPoint(), rotatedAngle);
			Point p2 = GMath.rotatePointClockwise(new Point(x2, y2), getCenterPoint(), rotatedAngle);

			if (new Line2D.Double(p1, p2).ptSegDist(p) < 5) { area = ShapeArea.INNER_AREA; }
		}

		return area;

	}

	@Override
	public void move(int dx, int dy) {
		Polygon polygon = (Polygon) shape;
		
		polygon.translate(dx, dy);

		setFrameOfAnchorsByShape();
	}

	@Override
	public boolean isEmpty() {
		Polygon polygon = (Polygon) shape;
		
		return polygon.npoints < 2;
	}

	@Override
	public void setLocation(Point p) {
		Polygon polygon = (Polygon) shape;

		int dx = (int) (p.getX() - polygon.getBounds().getX());
		int dy = (int) (p.getY() - polygon.getBounds().getY());
		
		polygon.translate(dx, dy);

	}


	@Override
	public void setFrame(Rectangle frame) {
		// Type Cast Shape
		Polygon polygon = (Polygon) shape;

		// Calculate Framed X Coordinate Values
		int oldX = getBounds().x;
		int oldW = getBounds().width;

		int newX = frame.x;
		int newW = frame.width;

		int[] newXPoints = getFramedValues(polygon.xpoints, oldX, oldW, newX, newW);

		// Calculate Framed Y Coordinate Values
		int oldY = getBounds().y;
		int oldH = getBounds().height;

		int newY = frame.y;
		int newH = frame.height;

		int[] newYPoints = getFramedValues(polygon.ypoints,oldY,oldH, newY, newH);
		
		// Set Shape Framed By New Frame
		shape = new Polygon(newXPoints, newYPoints, polygon.npoints);

	}
	
	public int getNPoints() {
		Polygon polygon = (Polygon) shape;
		
		return polygon.npoints;
	}
	
	private Point[] rotatePoints(int[] xPoints, int[] yPoints) {

		Point[] rotatedPoints = new Point[xPoints.length];

		for (int i = 0; i < rotatedPoints.length; i++) 
			rotatedPoints[i] = GMath.rotatePointClockwise(new Point(xPoints[i], yPoints[i]), getCenterPoint(), rotatedAngle);
		

		return rotatedPoints;

	}

	private int[] getXPoints(Point[] points) {
		int[] xPoints = new int[points.length];

		for (int i = 0; i < xPoints.length; i++) { xPoints[i] = (int)points[i].getX(); }

		return xPoints;
	}

	private int[] getYPoints(Point[] points) {
		int[] yPoints = new int[points.length];

		for (int i = 0; i < yPoints.length; i++) { yPoints[i] = (int)points[i].getY(); }

		return yPoints;
	}
	
	private int[] getFramedValues(int[] values, int oldMin, int oldLength, int newMin, int newLength) {
		// Calculate Framed Value By Pixel Ratio
		for (int i = 0; i < getNPoints(); i++) {
			
			double pxPerUnit = (double) oldLength / 100d;
			double ratio 	 = (values[i] - oldMin) / pxPerUnit;

			pxPerUnit  = (double) newLength / 100d;
			
			values[i]  = (int) (newMin + (ratio * pxPerUnit));
		}
		
		// Return Calculated Values
		return values;
	}
	

}
