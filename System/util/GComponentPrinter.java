package util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class GComponentPrinter implements Printable {
	// Attributes
	private Component component = null;

	public GComponentPrinter(Component c) { component = c; }

	@Override
	public int print(Graphics g, PageFormat pf, int i) throws PrinterException {
		// Check Preconditions
		if (i > 0)
			return Printable.NO_SUCH_PAGE;

		// Get Bounds Of The Component
		Dimension dim  = component.getSize();
		
		double cHeight = dim.getHeight();
		double cWidth  = dim.getWidth();

		// Get Bounds Of The Printable Area
		double pHeight = pf.getImageableHeight();
		double pWidth  = pf.getImageableWidth();

		double pXStart = pf.getImageableX();
		double pYStart = pf.getImageableY();

		double xRatio  = pWidth / cWidth;
		double yRatio  = pHeight / cHeight;

		// Set Graphics2D
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.translate(pXStart, pYStart);
		g2d.scale(xRatio, yRatio);
		
		component.paint(g2d);

		return Printable.PAGE_EXISTS;
	}

}
