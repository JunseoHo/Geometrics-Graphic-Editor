package transformer;

import java.awt.Point;

import shape.GShape;
import util.GMath;

public class GRotater extends GTransformer {

	public GRotater(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransform(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keepTransform(Point p) {
		// Set Working Variables
		Point centerPoint = shape.getCenterPoint();
		Point rotatingOrigin = shape.getRotatingOrigin();

		// Rotate
		double angle = GMath.radianOfRotation(centerPoint, p, rotatingOrigin);
		shape.setRotatedAngle(angle);

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
