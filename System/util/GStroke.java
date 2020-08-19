package util;

import java.awt.BasicStroke;
import java.awt.Stroke;

public class GStroke {

	public static Stroke getBasicStroke(int weight) {
		return new BasicStroke(weight);
	}

	public static Stroke getDottedStroke(int weight) {
		float dash[] = { weight, weight };
		return new BasicStroke(weight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, dash, 0);
	}

}
