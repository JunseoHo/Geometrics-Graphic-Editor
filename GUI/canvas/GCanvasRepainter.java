package canvas;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFileChooser;

import canvas.CanvasEnums.CanvasState;
import popupmenu.FocusPopupMenu;
import popupmenu.IdlePopupMenu;
import shape.GShape;
import shape.ShapeArea;
import shape.ShapeStyle;
import shape.image.GImage;
import shape.text.GText;
import system.GFileManager;
import system.GUIText;
import transformer.GDrawer;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotater;
import transformer.GTransformer;
import util.GCursor;
import util.GDialog;

public class GCanvasRepainter extends MouseAdapter {

	// Associations
	private GCanvas canvas = null;

	// Working Variable
	private Point 		 pressedPoint = null;
	private GTransformer transformer  = null;

	public GCanvasRepainter(GCanvas canvas) {
		// Set Associations
		this.canvas = canvas;
	}

	private void initTransform(GTransformer transformer, Point p) {
		// Back Up Current Shapes
		canvas.backUp();
		
		p = canvas.getTranslatedPoint(p);
		
		this.transformer = transformer;

		// Set Working Variables
		if (transformer.getShape() == null) { // Create New Shape
			GShape shape = canvas.getCurrentTool().getEmptyGShape();
			canvas.addShape(shape);
			transformer.setShape(shape);
			transformer.setPaintOption(canvas.getPaintOption());
		}

		// Set State
		canvas.setState(CanvasState.PROCESSING);

		// Paint
		transformer.initTransform(p);

	}

	private void keepTransform(Point p) {
		// Paint
		p = canvas.getTranslatedPoint(p);
		transformer.keepTransform(p);

		// Repaint Canvas
		canvas.repaint();
	}

	private void continueTransform(Point p) {
		// Paint
		p = canvas.getTranslatedPoint(p);
		transformer.continueTransform(p);

		// Repaint Canvas
		canvas.repaint();
	}

	private void finiTransform(Point p) {
		// Garbage Processing
		p = canvas.getTranslatedPoint(p);
		
		if (transformer.getShape().isEmpty())
			canvas.removeShape(transformer.getShape());

		// Select Shape
		transformer.finiTransform(p);

		selectShape(transformer.getShape());

		// Set Association Component
		transformer = null;

		// Set State
		canvas.repaint();
		canvas.setState(CanvasState.IDLE);

	}

	private void addImage(Point p) {
		// Back Up Current Shapes
		canvas.backUp();

		// Show Dialog & Get Reply
		JFileChooser fileChooser = new JFileChooser(GFileManager.DEFAULT_IMAGE_DIRECTORY);

		if (fileChooser.showOpenDialog(canvas) == JFileChooser.APPROVE_OPTION) {

			// Initialize Image
			GImage image = new GImage(fileChooser.getSelectedFile());

			// Set Association Component
			canvas.addShape(image);

			// Paint
			image.setPaintOption(canvas.getPaintOption());
			image.setOrigin(p);

			// Select Shape
			selectShape(image);

			// Repaint Canvas
			canvas.repaint();

		}

	}
	
	private void addText(Point p) {
		// Back Up Current Shapes
		canvas.backUp();

		// Show Text Dialog
		String text = GDialog.showInputDialog(canvas, GUIText.get(83));

		if (text != "") {

			// Initialize Image
			GText gText = new GText(text);

			// Set Association Component
			canvas.addShape(gText);

			// Paint
			gText.setPaintOption(canvas.getPaintOption());
			gText.setFont(canvas.getFont());
			gText.setOrigin(p);

			// Select Shape
			selectShape(gText);

			// Repaint Canvas
			canvas.repaint();

		}
	}

	private PointedArea onShape(Point p) {
		p = canvas.getTranslatedPoint(p);
		
		List<GShape> shapes = canvas.getShapes();

		for (int i = shapes.size() - 1; i > -1; i--) {

			GShape shape = shapes.get(i);
			ShapeArea area = shape.contains(p);

			if (area != null) {
				return new PointedArea(shape, area);
			}

		}

		return null;
	}

	private void selectShape(GShape selectedShape) {
		for (GShape shape : canvas.getShapes()) {
			shape.setSelected(false);
		}
		selectedShape.setSelected(true);
		canvas.setFocusedShape(selectedShape);
		canvas.repaint();
	}

	private void setCurrentCursor(Point p) {

		PointedArea pointedArea = onShape(p);

		if (pointedArea != null) {
			ShapeArea area = pointedArea.getArea();
			if (area != null) {
				canvas.setCursor(area.getCursor()); 
			}

		} else {
			canvas.setCursor(GCursor.DEFAULT);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int count = e.getClickCount();

		if (count == 2)
			mouseDoubleClicked(e);
		else if (count == 1)
			mouseSingleClicked(e);

	}

	private void mouseDoubleClicked(MouseEvent e) {

		if (canvas.getCurrentToolStyle() == ShapeStyle.MULTI_POINTS) {

			if (canvas.isProcessing()) {
				finiTransform(e.getPoint());
			}

		}

	}

	private void mouseSingleClicked(MouseEvent e) {

		PointedArea pointedArea = onShape(e.getPoint());

		if (e.getButton() == MouseEvent.BUTTON1) {

			if (!canvas.isProcessing()) {

				pointedArea = onShape(e.getPoint());

				if (pointedArea == null) {

					if (canvas.getCurrentToolStyle() == ShapeStyle.MULTI_POINTS) {
						initTransform(new GDrawer(null), e.getPoint());
					} else if (canvas.getCurrentToolStyle() == ShapeStyle.IMAGE) {
						addImage(canvas.getTranslatedPoint(e.getPoint()));
					} else if (canvas.getCurrentToolStyle() == ShapeStyle.TEXT) {
						addText(canvas.getTranslatedPoint(e.getPoint()));
					}

				} else {
					if (!canvas.isProcessing()) {
						selectShape(pointedArea.getShape());
					}
				}

			} else if (canvas.isProcessing()) {

				if (canvas.getCurrentToolStyle() == ShapeStyle.MULTI_POINTS) {
					continueTransform(e.getPoint());
				}

			}

		} else if (e.getButton() == MouseEvent.BUTTON3) {

			if (!canvas.isProcessing()) {

				if (pointedArea != null) {
					new FocusPopupMenu(pointedArea.getShape(), canvas).show(canvas, e.getX(), e.getY());
				} else {
					new IdlePopupMenu(canvas, e.getPoint()).show(canvas, e.getX(), e.getY());
				}

			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (!canvas.isProcessing())
			setCurrentCursor(e.getPoint());
		else if (canvas.isProcessing()) {

			if (canvas.getCurrentToolStyle() == ShapeStyle.MULTI_POINTS)
				keepTransform(e.getPoint());

		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON2 && !canvas.isProcessing()) {
			pressedPoint = e.getPoint();
		}

		if (!canvas.isProcessing() && e.getButton() == MouseEvent.BUTTON1) {

			PointedArea pointedArea = onShape(e.getPoint());

			if (pointedArea != null) {

				ShapeArea area = pointedArea.getArea();

				if (area == ShapeArea.INNER_AREA) {
					initTransform(new GMover(pointedArea.getShape()), e.getPoint());
				} else if (area == ShapeArea.ROTATE_AREA) {
					initTransform(new GRotater(pointedArea.getShape()), e.getPoint());
				} else {
					initTransform(new GResizer(pointedArea), e.getPoint());
				}

			} else {

				ShapeStyle style = canvas.getCurrentToolStyle();

				if (style == ShapeStyle.DOUBLE_POINTS || style == ShapeStyle.BRUSH) {
					initTransform(new GDrawer(null), e.getPoint());
				}

			}

		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (!canvas.isProcessing()) {
			
			if(pressedPoint == null) {
				return;
			}
			
			int dx = pressedPoint.x - e.getPoint().x;
			int dy = pressedPoint.y - e.getPoint().y;
			canvas.translate(dx, dy);
			pressedPoint = e.getPoint();
			canvas.repaint();
		}

		if (canvas.isProcessing()) {
			if (canvas.getCurrentToolStyle() == ShapeStyle.BRUSH)
				continueTransform(e.getPoint());
			else
				keepTransform(e.getPoint());

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (canvas.isProcessing()) {

			ShapeStyle style = canvas.getCurrentToolStyle();

			if (style == ShapeStyle.DOUBLE_POINTS || style == ShapeStyle.BRUSH || style == ShapeStyle.IMAGE) {
				finiTransform(e.getPoint());
			}

			if (style == ShapeStyle.MULTI_POINTS && !(transformer instanceof GDrawer)) {
				finiTransform(e.getPoint());
			}

		}

	}

}
