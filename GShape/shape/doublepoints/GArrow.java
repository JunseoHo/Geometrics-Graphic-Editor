package shape.doublepoints;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Path2D;

import canvas.CanvasEnums.PaintMode;
import shape.ShapeArea;
import util.ShapeUtil;

@SuppressWarnings("serial")
public class GArrow extends GRectangle {
	// Attributes
	private double ratio = 0.3;

	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d = setGrapgics2D(g2d);

		// Transform Shape

		Shape transformed = transform(getArrow());

		// Paint Shape
		if (getPaintMode() == PaintMode.DRAW)
			g2d.draw(transformed);
		else if (getPaintMode() == PaintMode.FILL)
			g2d.fill(transformed);

		// Paint Anchors
		paintAnchors(g2d);

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

			if (transform(getArrow()).contains(p))
				area = ShapeArea.INNER_AREA;

		} else if (getPaintMode() == PaintMode.DRAW) {

			boolean bOuter = false;
			boolean bInner = false;

			bOuter = transform(getArrow()).contains(p);

			Rectangle rectangle = (Rectangle) shape;

			int weight = getWeight();

			int x = (int) (rectangle.getX() + weight);
			int y = (int) (rectangle.getY() + weight);
			int w = (int) (rectangle.getWidth() - weight * 2);
			int h = (int) (rectangle.getHeight() - weight * 2);

			bInner = transform(getArrow(new Rectangle(x, y, w, h))).contains(p);

			if (bOuter && !bInner)
				area = ShapeArea.INNER_AREA;

		}

		return area;
	}


	private Path2D getArrow(Rectangle rectangle) {

		Path2D arrow = new Path2D.Double();

		int x = (int) rectangle.getMinX();
		int y = (int) (rectangle.getMinY() + (rectangle.getHeight() / 3));

		arrow.moveTo(x, y);

		x += rectangle.getWidth() * (1 - ratio);
		arrow.lineTo(x, y);
		arrow.lineTo(x, rectangle.getMinY());

		Point p = ShapeUtil.getEPoint(rectangle);
		arrow.lineTo(p.x, p.y);

		arrow.lineTo(x, rectangle.getMaxY());

		y += rectangle.getHeight() / 3;
		arrow.lineTo(x, y);

		arrow.lineTo(rectangle.getMinX(), y);

		arrow.closePath();

		return arrow;
	}
	
	private Path2D getArrow() {
		Rectangle rectangle = (Rectangle) shape;
		
		Path2D arrow = new Path2D.Double();

		int x = (int) rectangle.getMinX();
		int y = (int) (rectangle.getMinY() + (rectangle.getHeight() / 3));

		arrow.moveTo(x, y);

		x += rectangle.getWidth() * (1 - ratio);
		arrow.lineTo(x, y);
		arrow.lineTo(x, rectangle.getMinY());

		Point p = ShapeUtil.getEPoint(rectangle);
		arrow.lineTo(p.x, p.y);

		arrow.lineTo(x, rectangle.getMaxY());

		y += rectangle.getHeight() / 3;
		arrow.lineTo(x, y);

		arrow.lineTo(rectangle.getMinX(), y);

		arrow.closePath();

		return arrow;
	}

}
