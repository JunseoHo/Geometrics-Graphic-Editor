package transformer;

import java.awt.Point;

import shape.GShape;

public class GDrawer extends GTransformer {

	public GDrawer(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransform(Point p) {
		shape.setOrigin(p);
	}

	@Override
	public void keepTransform(Point p) {
		shape.setPoint(p);
	}

	@Override
	public void continueTransform(Point p) {
		shape.addPoint(p);
	}

	@Override
	public void finiTransform(Point p) {
	

	}

}
