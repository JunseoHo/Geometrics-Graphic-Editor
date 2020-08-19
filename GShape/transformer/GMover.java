package transformer;

import java.awt.Point;

import shape.GShape;

public class GMover extends GTransformer {

	private Point startPoint = null;

	public GMover(GShape shape) {
		super(shape);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initTransform(Point p) {
		startPoint = p;

	}

	@Override
	public void keepTransform(Point p) {
		// Set Working Variables
		int dx = p.x - startPoint.x;
		int dy = p.y - startPoint.y;

		startPoint = p;

		// Paint
		shape.move(dx, dy);
	}

	@Override
	public void continueTransform(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finiTransform(Point p) {
		// TODO Auto-generated method stub

	}

}
