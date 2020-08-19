package canvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.File;
import java.util.LinkedList;

import canvas.CanvasEnums.BackUpState;
import canvas.CanvasEnums.BuiltInFont;
import canvas.CanvasEnums.CanvasState;
import canvas.CanvasEnums.FontStyle;
import canvas.CanvasEnums.PaintMode;
import canvas.CanvasEnums.StrokeMode;
import common.GPanel;
import shape.GShape;
import shape.ShapeStyle;
import shape.doublepoints.GRectangle;
import system.GFileManager;
import system.GPath;
import system.GUIText;
import util.CloneUtil;
import util.GCursor;
import util.GDialog;

@SuppressWarnings({ "serial", "unchecked" })
public class GCanvas extends GPanel {
	// Rendering Objects
	private LinkedList<GShape> shapes 		= new LinkedList<>();
	private LinkedList<GShape> backUpShapes = null;
	// Paint Options
	private PaintMode  paintMode 	= PaintMode.DRAW;
	private Color 	   paintColor 	= Color.BLACK;
	private int 	   weight 		= 5;
	private float 	   opacity 		= 1.0f; // Legal Values : 0.0f ~ 1.0f
	private StrokeMode strokeMode 	= StrokeMode.STRAIGHT;

	// Working Variables
	private CanvasState state 	   	 = CanvasState.IDLE;
	private int 		translateX 	 = 0;
	private int 		translateY 	 = 0;

	private BackUpState backUpState  = BackUpState.UNDO;

	private GShape 		focusedShape = null;

	private GShape 		copiedShape  = null;

	private GShape 		currentTool  = new GRectangle();

	private boolean 	antiAliasing = false;

	private boolean 	bUpdated 	 = false;

	private int 		backUpCount  = 0;

	private final int 	backUpPeriod = 2;

	
	public GCanvas() {
		// Set Attributes
		setBackground(Color.WHITE);
		setFont(new Font(BuiltInFont.ARIAL.getFontName(), FontStyle.PLAIN.getStyle(), 20));
		
		// Add Event Listeners
		addMouseAdapter(new GCanvasRepainter(this));

		// Check Back Up
		if (GFileManager.getCurrentFile() != null) {
			if(GFileManager.getCurrentFile().getName().equals(GPath.BACKUP_FILE_NAME)) {
				LinkedList<GShape> shapes = (LinkedList<GShape>) GFileManager.readFile(GFileManager.getCurrentFile());
				setShapes(shapes);
				GFileManager.setCurrentFile(null);
				GFileManager.deleteBackUpFile();
				GDialog.showBackUpDialog();
				
				setUpdated(true);
			}
	
		}
	}

	@Override
	public void paint(Graphics g) {
		// Paint Canvas
		super.paint(g);

		// Initialize Graphics2D
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(translateX, translateY);
		
		// Set Graphics2D
		if (antiAliasing) {
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}
		

		// Paint Shapes
		for (GShape shape : shapes) {
			shape.paint(g2d);
		}

	}

	public void addShape(GShape shape) {
		shapes.add(shape);
	}

	public void removeShape(GShape shape) {
		shapes.remove(shape);
	}

	public LinkedList<GShape> getShapes() {
		return shapes;
	}

	public void setShapes(LinkedList<GShape> shapes) {
		this.shapes = shapes;
		repaint();
	}

	public GShape getCopiedShape() {
		return copiedShape;
	}

	public void setCopiedShape(GShape shape) {
		copiedShape = shape;
	}

	public PaintMode getPaintMode() {
		return paintMode;
	}

	public void setPaintMode(PaintMode paintMode) {
		this.paintMode = paintMode;
	}

	public Color getPaintColor() {
		return paintColor;
	}

	public void setPaintColor(Color c) {
		paintColor = c;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public StrokeMode getStrokeMode() {
		return strokeMode;
	}

	public void setStrokeMode(StrokeMode strokeMode) {
		this.strokeMode = strokeMode;
	}

	public CanvasState getState() {
		return state;
	}

	public void setState(CanvasState state) {
		this.state = state;
	}

	public GShape getCurrentTool() {
		return currentTool;
	}

	public void setCurrentTool(GShape tool) {
		currentTool = tool;
	}

	public ShapeStyle getCurrentToolStyle() {
		return currentTool.getStyle();
	}

	public boolean isAntiAliasing() {
		return antiAliasing;
	}

	public void setAntiAliasing(boolean antiAliasing) {
		this.antiAliasing = antiAliasing;
		repaint();
	}

	public boolean isUpdated() {
		return bUpdated;
	}

	public void setUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;
	}

	public boolean isProcessing() {
		return state == CanvasState.PROCESSING;
	}

	public PaintOption getPaintOption() {
		PaintOption paintOption = new PaintOption();

		paintOption.setPaintMode(paintMode);
		paintOption.setPaintColor(paintColor);
		paintOption.setWeight(weight);
		paintOption.setOpacity(opacity);
		paintOption.setStrokeMode(strokeMode);

		return paintOption;
	}

	private void setBackUpState(BackUpState backUpState) {
		this.backUpState = backUpState;
	}

	public void clear() {
		backUp();
		
		shapes.clear();
		repaint();
	}

	public void inverseAll() {
		backUp();

		for (GShape shape : shapes)
			shape.inverse();

		repaint();
	}

	public void grayScaleAll() {
		backUp();

		for (GShape shape : shapes)
			shape.grayScale();

		repaint();
	}

	public void backUp() {
		backUpShapes = getCopiedShapes(shapes);
		backUpState = BackUpState.UNDO;
		
		backUpCount++;
		
		if(backUpCount == backUpPeriod) {
			backUpCount = 0;
			saveBackUpFile();
		}
		
		setUpdated(true);
	}

	private void saveBackUpFile() {
		GFileManager.writeFile(getShapes(), new File(GPath.BACKUP_FILE_PATH));
	}

	public void undo() {
		// Check Null
		if (backUpShapes == null || backUpState == BackUpState.REDO) {
			GDialog.showMessageDialog(this, GUIText.get(64));
			return;
		}

		// Copy Undo & Redo
		LinkedList<GShape> undo = getCopiedShapes(backUpShapes);
		backUp();

		// Set Undo & Redo & Shape
		setShapes(undo);

		// Set State
		setBackUpState(BackUpState.REDO);

		// Repaint
		repaint();

	}

	public void redo() {
		// Check Null
		if (backUpShapes == null || backUpState == BackUpState.UNDO) {
			GDialog.showMessageDialog(this, GUIText.get(65));
			return;
		}

		// Copy Undo & Redo
		LinkedList<GShape> redo = getCopiedShapes(backUpShapes);
		backUp();

		// Set Undo & Redo & Shape
		setShapes(redo);

		// Set State
		setBackUpState(BackUpState.UNDO);

		// Repaint
		repaint();
	}

	private LinkedList<GShape> getCopiedShapes(LinkedList<GShape> shapes) {
		// Check Null
		if (shapes == null)
			return null;

		// Copy All Shapes
		LinkedList<GShape> copiedShapes = (LinkedList<GShape>) CloneUtil.deepClone(shapes);

		// Return
		return copiedShapes;
	}

	public void setCursor(GCursor cursor) {
		super.setCursor(cursor.getCursor());
	}

	public GShape getFocusedShape() {
		return focusedShape;
	}

	public void setFocusedShape(GShape shape) {
		focusedShape = shape;
	}

	public void translate(int dx, int dy) {
		this.translateX -= dx;
		this.translateY -= dy;
	}

	public Point getTranslatedPoint(Point p) {
		return new Point(p.x - translateX, p.y - translateY);
	}

}
