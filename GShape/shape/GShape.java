package shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.Serializable;

import anchor.GAnchors;
import canvas.CanvasEnums.PaintMode;
import canvas.CanvasEnums.StrokeMode;
import canvas.PaintOption;
import util.CloneUtil;
import util.GColorEditor;
import util.GComposite;
import util.GDialog;
import util.GStroke;
import util.ShapeUtil;

@SuppressWarnings("serial")
public abstract class GShape implements Serializable {
	// Attributes
	protected  		Shape 		shape;
	protected final ShapeStyle 	style;
	protected 		PaintOption paintOption 	= null;
	protected 		Point 		location 		= null;
	protected 		double 		rotatedAngle 	= 0;

	// Working Variables
	private   		boolean 	selected 		= false;

	// Components
	protected 		GAnchors 	anchors 		= new GAnchors();

	public GShape(Shape shape, ShapeStyle style) {
		this.shape = shape;
		this.style = style;
	}

	public ShapeStyle getStyle() {
		return style;
	}
	
	public PaintOption getPaintOption() {
		return paintOption;
	}

	public void setPaintOption(PaintOption paintOption) {
		this.paintOption = paintOption;
	}

	public PaintMode getPaintMode() {
		return paintOption.getPaintMode();
	}

	public void setPaintMode(PaintMode paintMode) {
		paintOption.setPaintMode(paintMode);
	}

	public Color getPaintColor() {
		return paintOption.getPaintColor();
	}

	public void setPaintColor(Color c) {
		paintOption.setPaintColor(c);
	}

	public int getWeight() {
		return paintOption.getWeight();
	}

	public void setWeight(int weight) {
		paintOption.setWeight(weight);
	}

	public float getOpacity() {
		return paintOption.getOpacity();
	}

	public void setOpacity(float opacity) {
		paintOption.setOpacity(opacity);
	}
	
	public StrokeMode getStrokeMode() {
		return paintOption.getStrokeMode();
	}
	
	public void setStrokeMode(StrokeMode strokeMode) {
		paintOption.setStrokeMode(strokeMode);
	}

	public ShapeArea containsForAnchors(Point p) {
		if (selected) 
			return anchors.contains(p);
		
		return null;
	}

	public Rectangle getBounds() {
		return shape.getBounds();
	}

	public Point getRotatingOrigin() {
		int centerX = (int) shape.getBounds().getCenterX();
		int minY = (int) shape.getBounds().getMinY();
		return new Point(centerX, minY);
	}

	public Point getCenterPoint() {
		return ShapeUtil.getCenterPoint(getBounds());
	}

	public GShape getEmptyGShape() {

		GShape shape = null;

		try {
			
			shape = getClass().getConstructor().newInstance();
			
		} catch (Exception e) { GDialog.showExceptionDialog(e); }

		return shape;
	}

	protected Graphics2D setGrapgics2D(Graphics2D g2d) {

		g2d.setColor(getPaintColor());
		g2d.setComposite(GComposite.getSourceOver(getOpacity()));
		
		if(getStrokeMode() == StrokeMode.STRAIGHT) {
			g2d.setStroke(GStroke.getBasicStroke(getWeight()));
		}else if(getStrokeMode() == StrokeMode.DOTTED) {
			g2d.setStroke(GStroke.getDottedStroke(getWeight()));
		}

		return g2d;
	}
	
	public void inverse() {
		setPaintColor(GColorEditor.inverse(getPaintColor()));
	}

	public void grayScale() {
		setPaintColor(GColorEditor.grayScale(getPaintColor()));
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		selected = b;
		setFrameOfAnchorsByShape();
	}

	public void paintAnchors(Graphics2D g2d) {
		if (selected) 
			anchors.paint(g2d);
	}

	public void setRotatedAngle(double angle) {
		rotatedAngle = angle;
		anchors.setRotatedAngle(angle);
	}

	public Shape transform(Shape shape) {
		return ShapeUtil.getShapeRotatedClockWise(shape, getCenterPoint(), rotatedAngle);
	}

	public void resizeAnchor(Point p, ShapeArea area) {
		anchors.resize(p, area);
	}

	public void setFrameByAnchor() {
		setFrame(anchors.getFrame());
	}
	
	public GShape clone() {
		return (GShape) CloneUtil.deepClone(this);
	}
	
	protected void setFrameOfAnchorsByShape() {
//		if(selected)
			anchors.setFrame(shape.getBounds());
	}

	public abstract void setOrigin(Point p);

	public abstract void setPoint(Point p);

	public abstract void addPoint(Point p);

	public abstract void paint(Graphics2D g2d);

	public abstract void move(int dx, int dy);

	public abstract boolean isEmpty();

	public abstract ShapeArea contains(Point p);
	
	public abstract void setLocation(Point p);

	public abstract void setFrame(Rectangle frame);

}
