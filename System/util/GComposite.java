package util;

import java.awt.AlphaComposite;

public final class GComposite {

	public static AlphaComposite getSourceOver(float opacity) {
		return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
	}

}
