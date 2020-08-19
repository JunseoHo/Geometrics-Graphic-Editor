package util;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public final class ShapeUtil {

	public static void resizeByNWPoint(Point p, Rectangle rect) {
		int x = (int) p.getX();
		int y = (int) p.getY();
		int w = (int) (rect.getWidth() + (rect.getX() - p.getX()));
		int h = (int) (rect.getHeight() + (rect.getY() - p.getY()));
		rect.setFrame(x, y, w, h);
	}

	public static void resizeByNPoint(Point p, Rectangle rect) {
		int x = (int) rect.getX();
		int y = (int) p.getY();
		int w = (int) rect.getWidth();
		int h = (int) (rect.getHeight() + (rect.getY() - p.getY()));
		rect.setFrame(x, y, w, h);
	}

	public static void resizeByNEPoint(Point p, Rectangle rect) {
		int x = (int) rect.getX();
		int y = (int) p.getY();
		int w = (int) (rect.getWidth() - (rect.getMaxX() - p.getX()));
		int h = (int) (rect.getHeight() + (rect.getY() - p.getY()));
		rect.setFrame(x, y, w, h);
	}

	public static void resizeByEPoint(Point p, Rectangle rect) {
		int x = (int) rect.getX();
		int y = (int) rect.getY();
		int w = (int) (rect.getWidth() - (rect.getMaxX() - p.getX()));
		int h = (int) rect.getHeight();
		rect.setFrame(x, y, w, h);
	}

	public static void resizeBySEPoint(Point p, Rectangle rect) {
		int x = (int) rect.getX();
		int y = (int) rect.getY();
		int w = (int) (rect.getWidth() - (rect.getMaxX() - p.getX()));
		int h = (int) (rect.getHeight() - (rect.getMaxY() - p.getY()));
		rect.setFrame(x, y, w, h);
	}

	public static void resizeBySPoint(Point p, Rectangle rect) {
		int x = (int) rect.getX();
		int y = (int) rect.getY();
		int w = (int) rect.getWidth();
		int h = (int) (rect.getHeight() - (rect.getMaxY() - p.getY()));
		rect.setFrame(x, y, w, h);
	}

	public static void resizeBySWPoint(Point p, Rectangle rect) {
		int x = (int) p.getX();
		int y = (int) rect.getY();
		int w = (int) (rect.getWidth() + (rect.getX() - p.getX()));
		int h = (int) (rect.getHeight() - (rect.getMaxY() - p.getY()));
		rect.setFrame(x, y, w, h);
	}

	public static void resizeByWPoint(Point p, Rectangle rect) {
		int x = (int) p.getX();
		int y = (int) rect.getY();
		int w = (int) (rect.getWidth() + (rect.getX() - p.getX()));
		int h = (int) rect.getHeight();
		rect.setFrame(x, y, w, h);
	}

	public static Point getSPoint(Rectangle rect) {
		return new Point((int) rect.getCenterX(), (int) rect.getMaxY());
	}

	public static Point getNPoint(Rectangle rect) {
		return new Point((int) rect.getCenterX(), (int) rect.getMinY());
	}

	public static Point getWPoint(Rectangle rect) {
		return new Point((int) rect.getMinX(), (int) rect.getCenterY());
	}

	public static Point getEPoint(Rectangle rect) {
		return new Point((int) rect.getMaxX(), (int) rect.getCenterY());
	}

	public static Point getSEPoint(Rectangle rect) {
		return new Point((int) rect.getMaxX(), (int) rect.getMaxY());
	}

	public static Point getNEPoint(Rectangle rect) {
		return new Point((int) rect.getMaxX(), (int) rect.getMinY());
	}

	public static Point getSWPoint(Rectangle rect) {
		return new Point((int) rect.getMinX(), (int) rect.getMaxY());
	}

	public static Point getNWPoint(Rectangle rect) {
		return new Point((int) rect.getMinX(), (int) rect.getMinY());
	}

	public static Point getRotatePoint(Rectangle rect) {
		Point p = getNPoint(rect);
		p.translate(0, -50);
		return p;
	}

	public static Point getCenterPoint(Rectangle rect) {
		return new Point((int) rect.getCenterX(), (int) rect.getCenterY());
	}

	public static void translateRectangle(Rectangle rect, Point p) {
		rect.translate(p.x, p.y);
	}

	public static void alignSEPoint(Rectangle rect1, Rectangle rect2, double angle) {

		Point rect1SE = getSEPoint(rect1);
		Point rect1CenterPoint = getCenterPoint(rect1);

		Point rect2SE = getSEPoint(rect2);
		Point rect2CenterPoint = getCenterPoint(rect2);

		rect1SE = GMath.rotatePointClockwise(rect1SE, rect1CenterPoint, angle);
		rect2SE = GMath.rotatePointClockwise(rect2SE, rect2CenterPoint, angle);

		int dx = rect1SE.x - rect2SE.x;
		int dy = rect1SE.y - rect2SE.y;

		translateRectangle(rect2, new Point(dx, dy));
	}

	public static void alignNEPoint(Rectangle rect1, Rectangle rect2, double angle) {

		Point rect1NE = getNEPoint(rect1);
		Point rect1CenterPoint = getCenterPoint(rect1);

		Point rect2NE = getNEPoint(rect2);
		Point rect2CenterPoint = getCenterPoint(rect2);

		rect1NE = GMath.rotatePointClockwise(rect1NE, rect1CenterPoint, angle);
		rect2NE = GMath.rotatePointClockwise(rect2NE, rect2CenterPoint, angle);

		int dx = rect1NE.x - rect2NE.x;
		int dy = rect1NE.y - rect2NE.y;

		translateRectangle(rect2, new Point(dx, dy));
	}

	public static void alignSWPoint(Rectangle rect1, Rectangle rect2, double angle) {

		Point rect1SW = getSWPoint(rect1);
		Point rect1CenterPoint = getCenterPoint(rect1);

		Point rect2SW = getSWPoint(rect2);
		Point rect2CenterPoint = getCenterPoint(rect2);

		rect1SW = GMath.rotatePointClockwise(rect1SW, rect1CenterPoint, angle);
		rect2SW = GMath.rotatePointClockwise(rect2SW, rect2CenterPoint, angle);

		int dx = rect1SW.x - rect2SW.x;
		int dy = rect1SW.y - rect2SW.y;

		translateRectangle(rect2, new Point(dx, dy));
	}

	public static void alignNWPoint(Rectangle rect1, Rectangle rect2, double angle) {

		Point rect1NW = getNWPoint(rect1);
		Point rect1CenterPoint = getCenterPoint(rect1);

		Point rect2NW = getNWPoint(rect2);
		Point rect2CenterPoint = getCenterPoint(rect2);

		rect1NW = GMath.rotatePointClockwise(rect1NW, rect1CenterPoint, angle);
		rect2NW = GMath.rotatePointClockwise(rect2NW, rect2CenterPoint, angle);

		int dx = rect1NW.x - rect2NW.x;
		int dy = rect1NW.y - rect2NW.y;

		translateRectangle(rect2, new Point(dx, dy));
	}

	public static Shape getShapeRotatedClockWise(Shape shape, Point fixedPoint, double radian) {
		AffineTransform at = AffineTransform.getRotateInstance(radian, fixedPoint.getX(), fixedPoint.getY());
		shape = at.createTransformedShape(shape);
		return shape;
	}
	
}
