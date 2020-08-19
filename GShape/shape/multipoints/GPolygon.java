package shape.multipoints;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Line2D;

import canvas.CanvasEnums.PaintMode;
import shape.ShapeArea;
import shape.ShapeStyle;
import util.GMath;

@SuppressWarnings("serial")
public class GPolygon extends GPolyline {

	public GPolygon() { super(new Polygon(), ShapeStyle.MULTI_POINTS); }

	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d = setGrapgics2D(g2d);

		// Transform Shape
		Shape transformed = transform(shape);

		// Paint Shape
		if (getPaintMode() == PaintMode.DRAW) {
			
			g2d.draw(transformed);

		} else if (getPaintMode() == PaintMode.FILL) {

			if (getNPoints() < 3) 
				g2d.draw(transformed);
			else 
				g2d.fill(transformed);

		}
		
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
			
			Polygon polygon = (Polygon) shape;
			Shape transformed = transform(polygon);
			
			if (transformed.contains(p)) 
				area = ShapeArea.INNER_AREA;
			
		} else if (getPaintMode() == PaintMode.DRAW) {
			// Check Paths
			area = super.contains(p);

			
			// Check Closing Path
			if (area != null) { return area; }

			Polygon polygon = (Polygon) shape;

			int x1 = polygon.xpoints[polygon.npoints - 1];
			int y1 = polygon.ypoints[polygon.npoints - 1];
			int x2 = polygon.xpoints[0];
			int y2 = polygon.ypoints[0];

			Point p1 = GMath.rotatePointClockwise(new Point(x1, y1), getCenterPoint(), rotatedAngle);
			Point p2 = GMath.rotatePointClockwise(new Point(x2, y2), getCenterPoint(), rotatedAngle);

			if (new Line2D.Double(p1, p2).ptSegDist(p) < 5) { area = ShapeArea.INNER_AREA; }

		}

		return area;
	}
}
