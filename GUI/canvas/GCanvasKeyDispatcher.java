package canvas;

import java.awt.KeyEventDispatcher;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import popupmenu.FocusPopupMenu;
import popupmenu.IdlePopupMenu;
import system.GPath;
import util.GDialog;

public class GCanvasKeyDispatcher implements KeyEventDispatcher {

	// Association
	private GCanvas canvas = null;

	// Working Variables
	private boolean ctrl_Pressed = false;

	public GCanvasKeyDispatcher(GCanvas canvas) {
		this.canvas = canvas;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			ctrl_Pressed = true;
		}
		
		
		if (ctrl_Pressed) {
			PointerInfo pointerinfo = MouseInfo.getPointerInfo();

			switch (e.getKeyCode()) {
			case KeyEvent.VK_C:
				FocusPopupMenu popupMenu = new FocusPopupMenu(canvas.getFocusedShape(), canvas);
				popupMenu.copy();
				break;
			case KeyEvent.VK_V:
				IdlePopupMenu idleMenu = new IdlePopupMenu(canvas, pointerinfo.getLocation());
				idleMenu.paste();
				break;
			case KeyEvent.VK_D:
				popupMenu = new FocusPopupMenu(canvas.getFocusedShape(), canvas);
				popupMenu.delete();
				break;
			case KeyEvent.VK_Z:
				canvas.undo();
				break;
			case KeyEvent.VK_Y:
				canvas.redo();
				break;
			case KeyEvent.VK_P:
				screenCapture();
				break;
			case KeyEvent.VK_E:
				canvas.clear();
			default:
				break;
			}

		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			ctrl_Pressed = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			keyPressed(e);
		} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			keyReleased(e);
		} else if (e.getID() == KeyEvent.KEY_TYPED) {
			keyTyped(e);
		}
		return false;
	}
	
	public void screenCapture() {
		String saveFilePath = GPath.DEFAULT_SCREENSHOT_PATH;
		String saveFileName = "screenshot_" + System.currentTimeMillis();
		String saveFileExtension = "png";

		try {
			Robot robot = new Robot();
			Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = robot.createScreenCapture(rectangle);
			image.setRGB(0, 0, 100);

			File file = new File(saveFilePath + saveFileName + "." + saveFileExtension);
			ImageIO.write(image, saveFileExtension, file);
		} catch (Exception e) {
			GDialog.showExceptionDialog(e);
		}

	}

}
