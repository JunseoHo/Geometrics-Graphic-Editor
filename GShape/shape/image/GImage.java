package shape.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import anchor.GUnrotatableAnchors;
import shape.GShape;
import shape.ShapeArea;
import shape.ShapeStyle;
import util.GColorEditor;
import util.GDialog;
import util.ShapeUtil;

@SuppressWarnings("serial")
public class GImage extends GShape {
	// Attributes
	private File imageFile = null;
	private int  width 	   = 0;
	private int  height    = 0;

	// Working Vaiables
	private boolean inversed   = false;
	private boolean grayScaled = false;
	
	
	public GImage(File imageFile) {
		super(new Rectangle(), ShapeStyle.IMAGE);
		// Set Atrributes
		this.imageFile = imageFile;
		
		// Set Anchors
		this.anchors = new GUnrotatableAnchors();
	}

	@Override
	public void setOrigin(Point p) {
		location = p;

		Rectangle rectangle = (Rectangle) shape;
		
		width  = getBufferedImage(imageFile).getWidth();
		height = getBufferedImage(imageFile).getHeight();
		
		rectangle.setFrame(location.getX(), location.getY(), width, height);
	}

	@Override
	public void setPoint(Point p) {
		/* DO NOTHING */
	}

	@Override
	public void paint(Graphics2D g2d) {
		// Set Graphics2D
		g2d = setGrapgics2D(g2d);

		// Paint Image
		BufferedImage bufferedImage = getBufferedImage(imageFile);

		if (inversed) 
			bufferedImage = setInversedRGB(bufferedImage);
		

		if (grayScaled) 
			bufferedImage = setGrayScaledRGB(bufferedImage);

		Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_FAST);
		

		g2d.drawImage(image, (int)location.getX(), (int)location.getY(), null);
		
		// Paint Anchors
		paintAnchors(g2d);
	}

	

	@Override
	public void move(int dx, int dy) {
		location.x += dx;
		location.y += dy;

		Rectangle rectangle = (Rectangle) shape;
		rectangle.setFrame(location.x, location.y, width, height);

		setFrameOfAnchorsByShape();
	}

	@Override
	public boolean isEmpty() {
		return imageFile == null;
	}

	@Override
	public void addPoint(Point p) {
		/* DO NOTHING */
	}

	@Override
	public ShapeArea contains(Point p) {
		// Initialize Shape Area
		ShapeArea area = null;

		// Check Anchor's Area
		area = anchors.contains(p);

		if (area != null) { return area; }

		// Check Image's Area

		if (new Rectangle(location.x, location.y, width, height).contains(p)) {
			area = ShapeArea.INNER_AREA;
		}

		return area;
	}

	@Override
	public void inverse() {
		inversed = !inversed;
	}

	private BufferedImage getBufferedImage(File imageFile) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			GDialog.showExceptionDialog(e);
		}

		return image;
	}

	private BufferedImage setInversedRGB(BufferedImage image) {
		for (int x = 0; x < image.getWidth(null); x++) {

			for (int y = 0; y < image.getHeight(null); y++) {

				int rgb = image.getRGB(x, y);

				Color c = new Color(rgb, true);
				c = GColorEditor.inverse(c);

				image.setRGB(x, y, c.getRGB());

			}
		}

		return image;
	}

	@Override
	protected Graphics2D setGrapgics2D(Graphics2D g2d) {

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getOpacity()));

		return g2d;
	}

	@Override
	public void setLocation(Point p) {
		location = p;
	}

	@Override
	public void setFrameByAnchor() {
		setFrame(anchors.getFrame());
	}

	@Override
	public void grayScale() {
		this.grayScaled = true;
	}

	private BufferedImage setGrayScaledRGB(BufferedImage image) {

		for (int x = 0; x < image.getWidth(null); x++) {

			for (int y = 0; y < image.getHeight(null); y++) {

				int rgb = image.getRGB(x, y);

				Color c = new Color(rgb, true);
				c = GColorEditor.grayScale(c);

				image.setRGB(x, y, c.getRGB());

			}
		}

		return image;

	}

	@Override
	public void setFrame(Rectangle frame) {
		Rectangle rectangle = (Rectangle) shape;
		
		rectangle.setFrame(frame);
		
		location = ShapeUtil.getNWPoint(frame);
		
		width = (int) frame.getWidth();
		height = (int) frame.getHeight();
	}

}
