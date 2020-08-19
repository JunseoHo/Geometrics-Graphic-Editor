package shape.doublepoints;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import canvas.CanvasEnums.PaintMode;
import shape.GShape;
import shape.ShapeArea;
import shape.ShapeStyle;

@SuppressWarnings("serial")
public class GEllipse extends GShape {

	public GEllipse() { super(new Ellipse2D.Double(), ShapeStyle.DOUBLE_POINTS); }

	@Override
	public void setOrigin(Point p) {
		// Set Location
		location = p;

		// Set Frame
		Ellipse2D ellipse = (Ellipse2D) shape;
		ellipse.setFrame(location.getX(), location.getY(), 0, 0);
	}

	@Override
	public void setPoint(Point p) {
		Ellipse2D ellipse = (Ellipse2D) shape;
		
		int x = (int) Math.min(p.getX(),  location.getX());
		int y = (int) Math.min(p.getY(),  location.getY());
		int w = (int) Math.abs(p.getX() - location.getX());
		int h = (int) Math.abs(p.getY() - location.getY());

		ellipse.setFrame(x, y, w, h);
	}

	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d = setGrapgics2D(g2d);
		
		// Transform Shape
		Shape transformed = transform(shape);
		
		// Paint Shape
		
		if (getPaintMode() == PaintMode.DRAW) 
			g2d.draw(transformed);
		
		else if (getPaintMode() == PaintMode.FILL) 
			g2d.fill(transformed);
		
		
		// Paint Anchors
		paintAnchors(g2d);
	}

	@Override
	public boolean isEmpty() {
		Ellipse2D ellipse = (Ellipse2D) shape;
		
		return ellipse.isEmpty();
	}

	@Override
	public void move(int dx, int dy) {
		Ellipse2D ellipse = (Ellipse2D) shape;
		
		ellipse.setFrame(ellipse.getX() + dx, ellipse.getY() + dy, ellipse.getWidth(), ellipse.getHeight());

		setFrameOfAnchorsByShape();
	}

	@Override
	public ShapeArea contains(Point p) {
		// Initialize Shape Area
		ShapeArea area = null;

		// Check Anchors's Area
		area = containsForAnchors(p);

		if (area != null)
			return area;

		// Check Shape's Area
		if (getPaintMode() == PaintMode.FILL) {

			if (transform(shape).contains(p)) 
				area = ShapeArea.INNER_AREA;

		} else if (getPaintMode() == PaintMode.DRAW) {
			 
			boolean bOuter = false;
			boolean bInner = false;

			bOuter = transform(shape).contains(p);

			Ellipse2D ellipse = (Ellipse2D) shape;
			
			int weight = getWeight();

			double x = ellipse.getX() 	   + weight;
			double y = ellipse.getY() 	   + weight;
			double w = ellipse.getWidth()  - weight * 2;
			double h = ellipse.getHeight() - weight * 2;

			bInner = transform(new Ellipse2D.Double(x, y, w, h)).contains(p);

			if (bOuter && !bInner) 
				area = ShapeArea.INNER_AREA;

		}

		return area;
	}

	@Override
	public void addPoint(Point p) {
		/* DO NOTHING */
	}

	@Override
	public void setLocation(Point p) {
		Ellipse2D ellipse = (Ellipse2D) shape;
		
		ellipse.setFrame(p.getX(), p.getY(), ellipse.getWidth(), ellipse.getHeight());
	}

	@Override
	public void setFrame(Rectangle frame) {
		Ellipse2D ellipse = (Ellipse2D) shape;
		
		ellipse.setFrame(frame);
	}

}
