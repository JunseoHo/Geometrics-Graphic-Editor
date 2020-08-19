package canvas;

import java.awt.Color;
import java.io.Serializable;

import canvas.CanvasEnums.PaintMode;
import canvas.CanvasEnums.StrokeMode;

@SuppressWarnings("serial")
public class PaintOption implements Serializable {
	// Paint Options
	private PaintMode  paintMode   = null;
	private Color 	   paintColor  = null;
	private int 	   weight 	   = 0;
	private float 	   opacity 	   = 0;
	private StrokeMode strokeMode  = null;
	
	// Getter & Setter
	public PaintMode getPaintMode() { return paintMode; }

	public void setPaintMode(PaintMode paintMode) { this.paintMode = paintMode; }

	public Color getPaintColor() { return paintColor; }

	public void setPaintColor(Color c) { paintColor = c; }

	public int getWeight() { return weight; }

	public void setWeight(int weight) { this.weight = weight; }

	public float getOpacity() { return opacity; }

	public void setOpacity(float opacity) { this.opacity = opacity; }
	
	public StrokeMode getStrokeMode() { return strokeMode; }
	
	public void setStrokeMode(StrokeMode strokeMode) { this.strokeMode = strokeMode; }
	
	// Copy 
	public PaintOption copy() {
		
		PaintOption paintOption = new PaintOption();
		
		paintOption.setPaintMode(paintMode);
		paintOption.setPaintColor(new Color(paintColor.getRGB()));
		paintOption.setWeight(weight);
		paintOption.setOpacity(opacity);
		
		return paintOption;
		
	}

	


}
