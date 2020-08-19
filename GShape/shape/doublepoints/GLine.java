package shape.doublepoints;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import shape.GShape;
import shape.ShapeArea;
import shape.ShapeStyle;
import util.GMath;
import util.ShapeUtil;

@SuppressWarnings("serial")
public class GLine extends GShape {

	public GLine() {
		super(new Line2D.Double(), ShapeStyle.DOUBLE_POINTS);
	}

	@Override
	public void setOrigin(Point p) {
		location = p;

		Line2D line = (Line2D) shape;
		line.setLine(location, location);
	}

	@Override
	public void setPoint(Point p) {
		Line2D line = (Line2D) shape;
		line.setLine(location, p);
	}

	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d = setGrapgics2D(g2d);
		
		// Transform Shape
		Shape transformed = transform(shape);
		
		// Paint Shape
		g2d.draw(transformed);

		// Paint Anchors
		paintAnchors(g2d);
	}

	@Override
	public boolean isEmpty() {
		Line2D line = (Line2D) shape;
		return line.getP1().distance(line.getP2()) == 0;
	}

	@Override
	public void move(int dx, int dy) {
		Line2D line = (Line2D) shape;
		
		line.setLine(line.getX1() + dx, line.getY1() + dy, line.getX2() + dx, line.getY2() + dy);

		setFrameOfAnchorsByShape();
	}

	@Override
	public ShapeArea contains(Point p) {
		// Initialize Shape Area
		ShapeArea area = null;

		// Check Anchor's Area
		area = containsForAnchors(p);

		if (area != null)
			return area;

		// Check Shape's Area
		Line2D line = (Line2D) shape;

		Point p1 = GMath.rotatePointClockwise(line.getP1(), getCenterPoint(), rotatedAngle);
		Point p2 = GMath.rotatePointClockwise(line.getP2(), getCenterPoint(), rotatedAngle);

		Line2D transformed = new Line2D.Double(p1, p2);

		if (transformed.ptSegDist(p) < 5) 
			area = ShapeArea.INNER_AREA;
		
		return area;
	}

	@Override
	public void addPoint(Point p) {
		/* DO NOTHING */
	}

	@Override
	public void setLocation(Point p) {
		Line2D line = (Line2D) shape;

		double dx = p.getX() - line.getX1();
		double dy = p.getY() - line.getY1();

		line.setLine(p.getX(), p.getY(), line.getX2() + dx, line.getY2() + dy);
	}

	@Override
	public void setFrame(Rectangle frame) {
		Line2D line = (Line2D) shape;

		Point p1 = null;
		Point p2 = null;

		if (line.getX1() > line.getX2()) {
			
			p1 = ShapeUtil.getNWPoint(frame);
			p2 = ShapeUtil.getSEPoint(frame);
		
		} else if (line.getX1() < line.getX2()) {
			
			p1 = ShapeUtil.getSEPoint(frame);
			p2 = ShapeUtil.getNWPoint(frame);
		
		} else { // x1 == x2
			p1 = ShapeUtil.getNPoint(frame);
			p2 = ShapeUtil.getSPoint(frame);
		}

		line.setLine(p1, p2);

	}

}
