package wowlist.gui;

import javax.swing.JOptionPane;

public class G_Fail{

	public static void infoBox(String infoMessage, String location)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + location, JOptionPane.INFORMATION_MESSAGE);
    }
}
