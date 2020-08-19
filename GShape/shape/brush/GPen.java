package shape.brush;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;

import shape.GShape;
import shape.ShapeArea;
import shape.ShapeStyle;

@SuppressWarnings("serial")
public class GPen extends GShape {

	public GPen() { super(new GeneralPath(), ShapeStyle.BRUSH); }

	@Override
	public void setOrigin(Point p) {
		GeneralPath generalPath = (GeneralPath) shape;
		generalPath.moveTo(p.getX(), p.getY());
	}

	@Override
	public void setPoint(Point p) {
		/* DO NOTHING */
	}

	@Override
	public void addPoint(Point p) {
		GeneralPath generalPath = (GeneralPath) shape;
		generalPath.lineTo(p.getX(), p.getY());
	}

	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d = setGrapgics2D(g2d);

		// Paint Shape
		g2d.draw(shape);
	}

	@Override
	public ShapeArea contains(Point p) {
		return null;
	}

	@Override
	public void move(int dx, int dy) {
		/* DO NOTHING */
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void setFrame(Rectangle frame) {
		/* DO NOTHING */
	}

	@Override
	public void setLocation(Point p) {
		/* DO NOTHING */
	}

}
