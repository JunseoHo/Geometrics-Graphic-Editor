package menu;

import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import system.GUIText;

@SuppressWarnings("serial")
public class GMenu extends JMenu {

	// GUI Components
	private List<JMenuItem> menuItemList = new LinkedList<>();

	public GMenu(String menuName, MenuItem[] menuItems) {
		super(menuName);

		for (MenuItem item : menuItems) {

			if (item.getSubTextCodes() == null) { // Create Single Menu
				
				JMenuItem root = new JMenuItem(GUIText.get(item.getMainTextCode()));
				menuItemList.add(root);
				root.setActionCommand(item.getMethodNames()[0]);
				add(root);
				
			} else { // Create Compound Menu

				JMenu root = new JMenu(GUIText.get(item.getMainTextCode()));

				for (int i = 0; i < item.getSubTextCodes().length; i++) {

					JMenuItem menuItem = new JMenuItem(GUIText.get(item.getSubTextCodes()[i]));
					menuItemList.add(menuItem);
					menuItem.setActionCommand(item.getMethodNames()[i]);
					
					root.add(menuItem);

				}
				
				add(root);
			}

		}

	}

	public void addActionListener(ActionListener l) {
		for (JMenuItem menuItem : menuItemList) 
			menuItem.addActionListener(l);	
	}
}
