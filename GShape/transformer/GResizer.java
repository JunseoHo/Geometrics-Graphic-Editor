package transformer;

import java.awt.Point;

import canvas.PointedArea;

public class GResizer extends GTransformer {
	
	public GResizer(PointedArea pointedArea) {
		super(pointedArea);
	}

	@Override
	public void initTransform(Point p) {

	}

	@Override
	public void keepTransform(Point p) {
		pointedArea.getShape().resizeAnchor(p, pointedArea.getArea());
	}

	@Override
	public void continueTransform(Point p) {

	}

	@Override
	public void finiTransform(Point p) {
		pointedArea.getShape().setFrameByAnchor();
	}

}
