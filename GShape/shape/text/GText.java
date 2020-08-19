package shape.text;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import shape.GShape;
import shape.ShapeArea;
import shape.ShapeStyle;

@SuppressWarnings("serial")
public class GText extends GShape {
	// Attributes
	private String text = null;
	private Font font = null;
	
	
	public GText(String text) {
		super(new Rectangle(), ShapeStyle.TEXT);
		
		// Set Attributes
		this.text = text;
	}

	@Override
	public void setOrigin(Point p) {
		location = p;
	}

	@Override
	public void setPoint(Point p) {

	}

	@Override
	public void addPoint(Point p) {

	}

	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics 2D
		g2d = setGrapgics2D(g2d);
		g2d.setFont(font);
		
		// Paint Text
		g2d.drawString(text, location.x, location.y);
	}

	@Override
	public void move(int dx, int dy) {

	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ShapeArea contains(Point p) {
		
		return null;
	}

	@Override
	public void setLocation(Point p) {
		location = p;
	}

	@Override
	public void setFrame(Rectangle frame) {

	}
	
	public void setFont(Font f) {
		this.font = f;
	}

}
