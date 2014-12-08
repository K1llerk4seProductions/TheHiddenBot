package me.k1llerk4se.libs;
import me.k1llerk4se.TheHiddenBot;
import javax.swing.*;

/**
 * Created by mikel on 12/5/2014.
*/
public class GuiApp {
	public GuiApp(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("nogui")) {
				TheHiddenBot.runGUI = false;
			}
		}
		if (TheHiddenBot.runGUI) {
			JFrame guiFrame = new JFrame();
			guiFrame.setDefaultCloseOperation(3);
			guiFrame.setTitle("TheHiddenBot " + TheHiddenBot.VERSION);
			guiFrame.setSize(300, 200);
			guiFrame.setResizable(false);
			guiFrame.setLocationRelativeTo(null);
			guiFrame.setVisible(true);
		}
	}
}