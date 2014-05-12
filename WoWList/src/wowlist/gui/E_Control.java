package wowlist.gui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import wowlist.interfaces.ControlListener;
import wowlist.misc.CSVImport;

public class E_Control extends JPanel{
	
	private JButton 	saveBut 	= new JButton("Save");
	private JButton 	closeBut 	= new JButton("Close");
	private JButton		importCSV	= new JButton("Import");
	
	private JLabel		total		= new JLabel("Total: ");
	
	//Placeholder
	private JLabel		saved		= new JLabel("PH");
	
	private List<ControlListener> list = new ArrayList<ControlListener>();

	/**
	 * Unterer Bereich für die Buttons<br>
	 * <li>Speichern
	 * <li>Schließen
	 * <li>CSV importieren
	 */
	public E_Control(){
		init();
	}
	
	/**
	 * Baut die GUI und fügt Actionlistener hinzu
	 */
	private void init() {
		//BorderLayout
		setLayout(new BorderLayout());
		
		
		JPanel buttons = new JPanel(new FlowLayout());
		
		buttons.add(saveBut);
		buttons.add(closeBut);
		
		add(total, 		BorderLayout.WEST);
		add(buttons, 	BorderLayout.CENTER);
		add(saved, 		BorderLayout.EAST);
		add(importCSV,	BorderLayout.EAST);	
		
		addActionListener();
	}

	private void addActionListener() {
		saveBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveList();
			}
		});
		
		closeBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveList();
			}
		});
		
		importCSV.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CSVImport csv = new CSVImport();
				csv.fullImport();
			}
		});
	}

	/**
	 * Für jede Klasse die dieses Interface implementiert wird aufgefordert
	 * diese Funktion auszuführen (Observer Pattern)
	 */
	protected void saveList(){
		for(ControlListener cl : list)
			cl.saveList();
	}
	
	/**
	 * Das Hauptfenster wird in die Liste von ControlListenern gepackt, damit
	 * diese auf Benutzereingaben reagiert.
	 * 
	 * @param e_Main - Das Hauptfenster
	 */
	public void addControlListener(E_Main e_Main) {
		list.add(e_Main);
	}
	
	
	
	
	public JButton 	getSaveBut() { return saveBut; }
	public void 	setSaveBut(JButton saveBut) { this.saveBut = saveBut; }
	
	public JLabel 	getTotal() { return total; }
	public void 	setTotal(JLabel total) { this.total = total; }
	
	public JLabel 	getSaved() { return saved; }
	public void 	setSaved(JLabel saved) { this.saved = saved; }
	
	public List<ControlListener> getList() { return list; }
	public void setList(List<ControlListener> list) { this.list = list; }

}
