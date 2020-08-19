package menu;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import canvas.CanvasEnums.PaintMode;
import canvas.CanvasEnums.StrokeMode;
import canvas.GCanvas;
import canvas.SetFontFrame;
import main.VersionInfo;
import mainframe.GMainFrame;
import menu.MenuItem.EditMenuItem;
import menu.MenuItem.FileMenuItem;
import menu.MenuItem.PaintMenuItem;
import menu.MenuItem.SettingMenuItem;
import menu.MenuItem.SystemMenuItem;
import shape.GShape;
import system.GFileManager;
import system.GPath;
import system.GSystem;
import system.GUIText;
import system.SystemValues.Value_LAF;
import system.SystemValues.Value_Language;
import util.GComponentPrinter;
import util.GDialog;

@SuppressWarnings({ "serial", "unchecked", "static-access" })
public class GMenuBar extends JMenuBar {

	// Components
	private List<GMenu> menus = new LinkedList<>();

	// Associations
	private GCanvas canvas = null;

	public GMenuBar() {

		// Add GUI Components
		add(new GMenu(GUIText.get(39), FileMenuItem.values()));
		add(new GMenu(GUIText.get(40), EditMenuItem.values()));
		add(new GMenu(GUIText.get(41), PaintMenuItem.values()));
		add(new GMenu(GUIText.get(42), SettingMenuItem.values()));
		add(new GMenu(GUIText.get(43), SystemMenuItem.values()));

		// Add ActionListener
		addActionListener(menus);
	}

	public void setAssociation(GCanvas canvas) {
		this.canvas = canvas;
	}

	public void add(GMenu menu) {
		menus.add(menu);
		super.add(menu);
	}

	public void addActionListener(List<GMenu> menus) {

		for (GMenu menu : menus)
			menu.addActionListener(e -> invokeMethod(e.getActionCommand()));

	}

	public void invokeMethod(String methodName) {

		try {

			getClass().getMethod(methodName).invoke(this);

		} catch (Exception e) {
			GDialog.showExceptionDialog(e);
		}

	}

	////////////////////////////////////////////
	//// File Menu Methods
	////////////////////////////////////////////

	public void newFile() {
		int reply = checkSave();

		if (reply != 2) {
			canvas.clear();

			GFileManager.setCurrentFile(null);
		}
	}

	public void save() {

		if (GFileManager.getCurrentFile() == null) {
			saveAs();
		} else {
			GFileManager.writeFile(canvas.getShapes(), GFileManager.getCurrentFile());

			canvas.setUpdated(false);
		}
	}

	public void saveAs() {
		JFileChooser fileChooser = new JFileChooser(GFileManager.DEFAULT_SAVE_DIRECTORY);

		int reply = fileChooser.showSaveDialog(canvas);

		if (reply == JFileChooser.APPROVE_OPTION) {
			GFileManager.setCurrentFile(fileChooser.getSelectedFile());
			save();
		}
	}

	public void load() {
		int reply = checkSave();

		if (reply != 2) {
			JFileChooser fileChooser = new JFileChooser(GFileManager.DEFAULT_SAVE_DIRECTORY);

			reply = fileChooser.showOpenDialog(canvas);

			if (reply == JFileChooser.APPROVE_OPTION) {
				canvas.clear();

				GFileManager.setCurrentFile(fileChooser.getSelectedFile());

				Object shapes = GFileManager.readFile(GFileManager.getCurrentFile());

				canvas.setShapes((LinkedList<GShape>) shapes);

			}
		}
	}

	public void print() {
		try {

			PrinterJob job = PrinterJob.getPrinterJob();
			PageFormat preformat = job.defaultPage();

			preformat.setOrientation(PageFormat.LANDSCAPE);

			PageFormat postformat = job.pageDialog(preformat);

			if (preformat != postformat) {
				// Set Print Component
				job.setPrintable(new GComponentPrinter(canvas), postformat);

				// Print
				if (job.printDialog())
					job.print();

			}

		} catch (PrinterException e) {
			GDialog.showExceptionDialog(e);
		}

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

	public void getVersionInfo() {
		GDialog.showMessageDialog(null, VersionInfo.getVersionInfo(), GUIText.get(46), GDialog.INFORMATION_MESSAGE);
	}

	public void exit() {
		int reply = checkSave();

		if (reply != 2) {
			GFileManager.deleteBackUpFile();
			System.exit(0);
		}
	}

	////////////////////////////////////////////
	//// Edit Menu Methods
	////////////////////////////////////////////

	public void undo() {
		canvas.undo();
	}

	public void redo() {
		canvas.redo();
	}

	public void inverse() {
		canvas.inverseAll();
	}

	public void setGrayScale() {
		canvas.grayScaleAll();
	}

	public void clear() {
		canvas.clear();
	}

	////////////////////////////////////////////
	//// Paint Menu Methods
	////////////////////////////////////////////

	public void setDrawMode() {
		canvas.setPaintMode(PaintMode.DRAW);
	}

	public void setFillMode() {
		canvas.setPaintMode(PaintMode.FILL);
	}

	public void setPaintColor() {
		Color c = new JColorChooser().showDialog(canvas, GUIText.get(47), canvas.getPaintColor());

		canvas.setPaintColor(c);
	}

	public void setWeight() {

		String value = null;
		int weight = 0;

		try {

			value = JOptionPane.showInputDialog(GUIText.get(48));
			weight = Integer.parseInt(value);

		} catch (NumberFormatException e) {

			GDialog.showMessageDialog(null, GUIText.get(49), GUIText.get(50), GDialog.WARNING_MESSAGE);

			weight = canvas.getWeight();

		} finally {

			canvas.setWeight(weight);

		}

	}

	public void setOpacity() {

		String value = null;
		float opacity = 0.0f;

		try {
			value = GDialog.showInputDialog(GUIText.get(51));
			opacity = Integer.parseInt(value);

			if (opacity < 0 || opacity > 100)
				throw new NumberFormatException();

			opacity *= 0.01f;

		} catch (NumberFormatException e) {

			GDialog.showMessageDialog(null, GUIText.get(52), GUIText.get(53), GDialog.WARNING_MESSAGE);

			opacity = canvas.getOpacity();

		} finally {

			canvas.setOpacity(opacity);

		}
	}

	public void setStraightMode() {
		canvas.setStrokeMode(StrokeMode.STRAIGHT);
	}

	public void setDottedMode() {
		canvas.setStrokeMode(StrokeMode.DOTTED);
	}

	////////////////////////////////////////////
	//// Setting Menu Methods
	////////////////////////////////////////////

	public void onCorrection() {
		GSystem.correction = true;
	}
	
	public void offCorrection() {
		GSystem.correction = false;
	}
	
	public void onAntiAliasing() {
		canvas.setAntiAliasing(true);
	}

	public void offAntiAliasing() {
		canvas.setAntiAliasing(false);
	}
	
	public void setFont() {
		new SetFontFrame(canvas).setVisible(true);
	}

	////////////////////////////////////////////
	//// System Menu Methods
	////////////////////////////////////////////

	public void setNimbusLAF() {
		GSystem.setSystemValue(GSystem.KEY_LOOKANDFEEL, Value_LAF.NIMBUS);

		restart();
	}

	public void setMetalLAF() {
		GSystem.setSystemValue(GSystem.KEY_LOOKANDFEEL, Value_LAF.METAL);

		restart();
	}

	public void setWindowsLAF() {
		GSystem.setSystemValue(GSystem.KEY_LOOKANDFEEL, Value_LAF.WINDOW);

		restart();
	}

	public void setEnglishLang() {
		GSystem.setSystemValue(GSystem.KEY_LANGUAGE, Value_Language.ENGLISH);

		restart();
	}

	public void setKoreanLang() {
		GSystem.setSystemValue(GSystem.KEY_LANGUAGE, Value_Language.KOREAN);

		restart();
	}

	////////////////////////////////////////////
	//// Private Methods
	////////////////////////////////////////////

	private int checkSave() {

		int reply = 0;

		if (canvas.isUpdated()) {
			reply = JOptionPane.showConfirmDialog(null, GUIText.get(56), GUIText.get(57),
					JOptionPane.YES_NO_CANCEL_OPTION);

			if (reply == 2) {
				return reply;
			}

			if (reply == JOptionPane.OK_OPTION)
				save();

		}
		return reply;
	}

	private void restart() {
		GMainFrame mainFrame = (GMainFrame) SwingUtilities.getWindowAncestor(this);
		mainFrame.restart();
	}

}
