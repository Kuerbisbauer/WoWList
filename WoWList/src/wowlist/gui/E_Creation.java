package wowlist.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import wowlist.entities.Amount;
import wowlist.entities.Gruppe;
import wowlist.interfaces.*;
import wowlist.queries.AmountQueries;
import wowlist.queries.GroupQueries;

public class E_Creation extends JPanel{
	
	private JComboBox<Gruppe> 				jcb;
	private DefaultComboBoxModel<Gruppe> 	dcm = new DefaultComboBoxModel<Gruppe>();
	
	private JButton newBut;
	private JButton deleteBut;
	
	private List<GroupChanged> list = new ArrayList<GroupChanged>();
	
	private GroupQueries 	gq = new GroupQueries();
	private AmountQueries 	aq = new AmountQueries();
	
	/**
	 * Obere Leiste, enthält:<br>
	 * <li>ComboBox für beinahltende Listen
	 * <li>Neue Liste
	 * <li>Liste löschen
	 */
	public E_Creation(){
		init();
	}

	private void init() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		jcb = new JComboBox<Gruppe>(dcm);
		
		newBut 		= new JButton("NEW");
		deleteBut 	= new JButton("DELETE");
		
		add(jcb);
		add(newBut);
		add(deleteBut);
		
		fillComboBox();
		
		addActionListener();
	}
	
	/**
	 * Die ComboBox wird mit allen verfügbaren Listen befüllt
	 */
	public void fillComboBox() {
		//Zuerst wird die ComboBox geleert um die Reihenfolge beizubehalten
		dcm.removeAllElements();
		List<Gruppe> list = gq.getAllGroups();
		
		for(Gruppe g : list)
			dcm.addElement(g);
	}
	
	/**
	 * Neue Elemente werden in die ComboBox gelegt
	 * 
	 * @param g - Neue Gruppe
	 */
	public void addToComboBox(Gruppe g) {
		dcm.addElement(g);
	}

	/**
	 * Ausgewähltes Element wird entfernt
	 * 
	 * @param g - Ausgewählte Gruppe
	 */
	public void deleteFromComboBox(Gruppe g) {
		dcm.removeElement(g);
	}
	
	/**
	 * Wird der Name einer Gruppe geändert, wird zuerst das Element entfernt und
	 * dann neuhinzugefügt (Workarround).
	 * 
	 * @param g - Ausgewählte Gruppe
	 */
	public void changeItemComboBox(Gruppe g) {
		deleteFromComboBox(g);
		addToComboBox(g);
		dcm.setSelectedItem(g);
	}
	
	/**
	 * Es wird das letzte Item in der ComboBox ausgewählt (benötigt für den Workaround)
	 */
	public void selectLastItemComboBox(){
		jcb.setSelectedIndex(dcm.getSize()-1);
	}
	
	private void addActionListener() {
		jcb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeGroup();
			}
		});
		
		newBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGroup();
			}
		});
		
		deleteBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteGroup();
			}
		});
	}

	/**
	 * Die ausgewählte Gruppe wird gelöscht und die ComboBox aktualisiert
	 */
	protected void deleteGroup() {
		if(jcb.getItemCount() > 0){
			Gruppe g = (Gruppe) jcb.getSelectedItem();
			
			int selectedIndex = jcb.getSelectedIndex()-1;
			
			for(Amount a : aq.getAllAmountsFromGivenGroup(g))
				aq.deleteAmount(a);
			
			gq.deleteGruppe((Gruppe) jcb.getSelectedItem());
			
			deleteFromComboBox(g);
			
			if(selectedIndex > 0)
				jcb.setSelectedIndex(selectedIndex);
		}
	}

	/**
	 * ObserverPattern falls eine neue Gruppe erstellt wurde
	 */
	protected void newGroup() {
		for(GroupChanged gc : list)
			gc.newGroup();
	}

	/**
	 * ObserverPattern falls eine Gruppe geändert wurde
	 */
	protected void changeGroup() {
		for(GroupChanged gc : list)
			gc.getCurrentGroup();
	}

	/**
	 * ObserverPattern, es wird das Hauptfenster in die Liste hinzugefügt
	 * 
	 * @param e_Main - Das Hauptfenster
	 */
	public void addGroupChangedListener(E_Main e_Main) {
		list.add(e_Main);
	}
	
	
	public JButton 	getNewBut() 				{ return newBut; }
	public void 	setNewBut(JButton newBut) 	{ this.newBut = newBut; }

	public JButton 	getDeleteBut() 					{ return deleteBut; }
	public void 	setDeleteBut(JButton deleteBut) { this.deleteBut = deleteBut; }

	public JComboBox<Gruppe> getJcb() 						{ return jcb; }
	public void 			setJcb(JComboBox<Gruppe> jcb) 	{ this.jcb = jcb; }

	public List<GroupChanged> 	getList() 							{ return list; }
	public void 				setList(List<GroupChanged> list) 	{ this.list = list; }

	public DefaultComboBoxModel<Gruppe> 	getDcm() 								{ return dcm; }
	public void 						setDcm(DefaultComboBoxModel<Gruppe> dcm) { this.dcm = dcm;}

}
