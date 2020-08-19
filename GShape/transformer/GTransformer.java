package transformer;
import java.awt.Point;

import canvas.PaintOption;
import canvas.PointedArea;
import shape.GShape;

public abstract class GTransformer {

	protected GShape shape = null;
	protected PointedArea pointedArea = null;

	public GTransformer(GShape shape) {
		this.shape = shape;
	}
	
	public GTransformer(PointedArea pointedArea) {
		this.pointedArea = pointedArea;
	}
	
	public GShape getShape() {
		
		if(shape == null && pointedArea == null)
			return null;
		
		if(shape == null)
			return pointedArea.getShape();
		
		return shape;
	}
	
	public void setShape(GShape shape) {
		this.shape = shape;
	}
	
	public void setPaintOption(PaintOption paintOption) {
		this.shape.setPaintOption(paintOption);
	}
	
	abstract public void initTransform(Point p);
	
	abstract public void keepTransform(Point p);
	
	abstract public void continueTransform(Point p);
	
	abstract public void finiTransform(Point p);

}
