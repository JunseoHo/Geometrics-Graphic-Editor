package anchor;

import java.io.Serializable;

import shape.ShapeArea;

@SuppressWarnings("serial")
public class GUnrotatableAnchors extends GAnchors implements Serializable {


	public GUnrotatableAnchors() {
		// Initialize Components		
		anchors.clear();
		
		for (int i = 0; i < ShapeArea.values().length - 2; i++) // Exclude INNER_AREA & ROTATE_AREA
			anchors.add(new GAnchor(ShapeArea.values()[i]));
	}
	
}
