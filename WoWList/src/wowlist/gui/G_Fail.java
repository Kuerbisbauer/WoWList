package wowlist.gui;

import javax.swing.JOptionPane;

public class G_Fail{

	/**
	 * Eine Fehlermeldung wird generiert.
	 * 
	 * @param infoMessage   
	 * @param location		
	 */
	public static void infoBox(String infoMessage, String location)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + location, JOptionPane.INFORMATION_MESSAGE);
    }
}
