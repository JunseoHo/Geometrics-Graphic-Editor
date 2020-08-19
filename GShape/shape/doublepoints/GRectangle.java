package shape.doublepoints;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import canvas.CanvasEnums.PaintMode;
import shape.GShape;
import shape.ShapeArea;
import shape.ShapeStyle;

@SuppressWarnings("serial")
public class GRectangle extends GShape {

	public GRectangle() { super(new Rectangle(), ShapeStyle.DOUBLE_POINTS); }

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
	public void setOrigin(Point p) {
		location = p;

		Rectangle rectangle = (Rectangle) shape;
		rectangle.setFrame(location.getX(), location.getY(), 0, 0);
	}

	@Override
	public void setPoint(Point p) {
		Rectangle rectangle = (Rectangle) shape;

		int x = (int) Math.min(p.getX(),  location.getX());
		int y = (int) Math.min(p.getY(),  location.getY());
		int w = (int) Math.abs(p.getX() - location.getX());
		int h = (int) Math.abs(p.getY() - location.getY());

		rectangle.setFrame(x, y, w, h);
	}

	@Override
	public boolean isEmpty() {
		Rectangle rectangle = (Rectangle) shape;

		return rectangle.isEmpty();
	}

	@Override
	public void move(int dx, int dy) {
		Rectangle rectangle = (Rectangle) shape;
		
		rectangle.translate(dx, dy);

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
		if (getPaintMode() == PaintMode.FILL) {

			if (transform(shape).contains(p)) 
				area = ShapeArea.INNER_AREA;
			
		} else if (getPaintMode() == PaintMode.DRAW) {

			boolean bOuter = false;
			boolean bInner = false;

			bOuter = transform(shape).contains(p);

			Rectangle rectangle = (Rectangle) shape;
			
			int weight = getWeight();

			double x = rectangle.getX() 	 + weight;
			double y = rectangle.getY() 	 + weight;
			double w = rectangle.getWidth()  - weight * 2;
			double h = rectangle.getHeight() - weight * 2;

			bInner = transform(new Rectangle.Double(x, y, w, h)).contains(p);

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
		Rectangle rectangle = (Rectangle) shape;
		
		rectangle.setFrame(p.getX(), p.getY(), rectangle.getWidth(), rectangle.getHeight());
	}

	@Override
	public void setFrame(Rectangle frame) {
		Rectangle rectangle = (Rectangle) shape;
		rectangle.setFrame(frame);
	}
}
