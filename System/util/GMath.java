package util;

import java.awt.Point;
import java.awt.geom.Point2D;

public final class GMath {

	public static double radianOfRotation(Point p1, Point p2, Point vertex) {
		
		int centerX = p1.x;
		int centerY = p1.y;
		
		int startX = vertex.x;
		int startY = vertex.y;
		
		int endX = p2.x;
		int endY = p2.y;
		
		double startAngle = Math.toDegrees(Math.atan2(centerX - startX, centerX - startY));
		double endAngle   = Math.toDegrees(Math.atan2(centerX - endX  , centerY - endY));

		double rotationAngle = startAngle - endAngle;

		// Normalize Quadrant II, III
		if (rotationAngle < 0) { rotationAngle += 360; }

		return Math.toRadians(rotationAngle);
	}

	public static Point rotatePointClockwise(Point2D p, Point2D origin, double angle) {

		double cosq = Math.cos(angle);
		double sinq = Math.sin(angle);
		
		double sx = p.getX() - origin.getX();
		double sy = p.getY() - origin.getY();
		
		double rx = (sx * cosq - sy * sinq) + origin.getX();
		double ry = (sx * sinq + sy * cosq) + origin.getY();

		int rotatedX = (int) Math.round(rx);
		int rotatedY = (int) Math.round(ry);

		return new Point(rotatedX, rotatedY);

	}

	public static Point rotatePointCounterClockwise(Point2D p, Point2D origin, double angle) {
		return rotatePointClockwise(p, origin, -angle);
	}

}
