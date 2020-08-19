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
public class GRhombus extends GRectangle {

	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d = setGrapgics2D(g2d);

		// Transform Shape
		
		Shape transformed = transform(getRhombus());

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

			if (transform(getRhombus()).contains(p))
				area = ShapeArea.INNER_AREA;

		} else if (getPaintMode() == PaintMode.DRAW) {

			boolean bOuter = false;
			boolean bInner = false;

			bOuter = transform(getRhombus()).contains(p);

			Rectangle rectangle = (Rectangle) shape;

			int weight = getWeight();

			int x = (int) (rectangle.getX() + weight);
			int y = (int) (rectangle.getY() + weight);
			int w = (int) (rectangle.getWidth() - weight * 2);
			int h = (int) (rectangle.getHeight() - weight * 2);

			bInner = transform(getRhombus(new Rectangle(x, y, w, h))).contains(p);

			if (bOuter && !bInner)
				area = ShapeArea.INNER_AREA;

		}

		return area;
	}

	private Path2D getRhombus() {
		Rectangle rectangle = (Rectangle) shape;

		Path2D rhombus = new Path2D.Double();

		Point movePoint = ShapeUtil.getNPoint(rectangle);
		rhombus.moveTo(movePoint.getX(), movePoint.getY());

		movePoint = ShapeUtil.getEPoint(rectangle);
		rhombus.lineTo(movePoint.getX(), movePoint.getY());

		movePoint = ShapeUtil.getSPoint(rectangle);
		rhombus.lineTo(movePoint.getX(), movePoint.getY());

		movePoint = ShapeUtil.getWPoint(rectangle);
		rhombus.lineTo(movePoint.getX(), movePoint.getY());

		rhombus.closePath();
		
		return rhombus;
	}
	
	private Path2D getRhombus(Rectangle rectangle) {

		Path2D rhombus = new Path2D.Double();

		Point movePoint = ShapeUtil.getNPoint(rectangle);
		rhombus.moveTo(movePoint.getX(), movePoint.getY());

		movePoint = ShapeUtil.getEPoint(rectangle);
		rhombus.lineTo(movePoint.getX(), movePoint.getY());

		movePoint = ShapeUtil.getSPoint(rectangle);
		rhombus.lineTo(movePoint.getX(), movePoint.getY());

		movePoint = ShapeUtil.getWPoint(rectangle);
		rhombus.lineTo(movePoint.getX(), movePoint.getY());

		rhombus.closePath();
		
		return rhombus;
	}

}
