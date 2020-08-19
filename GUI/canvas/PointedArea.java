package canvas;

import shape.GShape;
import shape.ShapeArea;

public class PointedArea {

	private GShape shape = null;
	private ShapeArea area = null;

	public PointedArea(GShape shape, ShapeArea area) {
		this.shape = shape;
		this.area = area;
	}

	public GShape getShape() {
		return shape;
	}

	public ShapeArea getArea() {
		return area;
	}

}
