package wowlist.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import wowlist.entities.Amount;
import wowlist.entities.Gruppe;
import wowlist.interfaces.ControlListener;
import wowlist.interfaces.GroupChanged;
import wowlist.misc.CSVImport;
import wowlist.queries.AmountQueries;
import wowlist.queries.GroupQueries;

public class E_Main extends JFrame implements GroupChanged, ControlListener{
	
	private E_Creation 	creation 	= new E_Creation();
	private E_List 		list 		= new E_List();
	private E_Control	control		= new E_Control();
	
	private GroupQueries 	gq = new GroupQueries();
	private AmountQueries 	aq = new AmountQueries();
	
	/**
	 * <b>onstruktor</b><p>
	 * Das Hauptfenster wird zusammengebaut und die Buttons mit ActionListener versehen.<br>
	 */
	public E_Main(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//goimport();
		init();
		creation.addGroupChangedListener(this);
		list.addControlListener(this);
		control.addControlListener(this);
		
		list.refreshTotalSumLabel();
		getCurrentGroup();
		list.refreshUI();
	}

	private void init() {
		setLayout(new BorderLayout());
		
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(list);
		
		this.add(creation, 	BorderLayout.NORTH);
		this.add(jsp, 		BorderLayout.CENTER);
		this.add(control, 	BorderLayout.SOUTH);
	}
	
	/**
	 * Die CSV Datei wird eingelesen und alle relevanten Daten in die DB gespeichert
	 */
	private void goimport(){
		CSVImport csv = new CSVImport();
		csv.fullImport();
	}

	//Holt sich die ausgew�hlte Gruppe aus E_List
	@Override
	public void getCurrentGroup() {
		// TODO Die ausgew�hlte Gruppe wird von E_Creation geholt
		//		Diese wird in eine Query miteingeschlossen und
		//		alle relevanten Mengen werden abgeholt
		checkSaved();
		
		Gruppe g = (Gruppe) creation.getJcb().getSelectedItem();
		
		list.cleanUpList();
		list.loadAmounts(g);
		
		diff = list.getAmountlist();
		refreshTotalSum(diff);
	}

	private void checkSaved() {	
		// Es wird gefragt ob gespeichert werden soll (JDIALOG)
		
	}

	List<Amount> diff = new ArrayList<Amount>();
	List<Amount> mem = new ArrayList<Amount>();
	
	/**
	 * Speichert die Liste von E_List in die Datenbank
	 */
	@Override
	public void saveList() {
		// TODO Die aktuelle Mengenliste wird abgeholt und
		//		in die Datenbank gespeichert
		//System.out.println("SAVELIST");
		
		saveGroup();
		saveExistingAmount();
		saveNewAmount();
		getCurrentGroup();
	}

	/**
	 * S�mtliche Mengen werden in die DB gepseichert
	 */
	private void saveNewAmount() {
		Gruppe g = (Gruppe) creation.getJcb().getSelectedItem();
		mem.removeAll(diff);
		
		for(Amount a : mem)
			aq.newAmount(g, a.getFk_Item(), a.getA_amount());
		
	}

	/**
	 * Falls eine Menge vorhanden ist, wird diese in der DB editiert
	 */
	private void saveExistingAmount() {
		List<Amount> alist = mem;
		
		for(Amount a : alist)
			aq.editAmount(a);
	}

	/**
	 * Eine neue Gruppe wird erstellt mit Namen und den enthaltenen Mengen (Items und Anzahl)
	 */
	private void saveGroup() {
		Gruppe g = (Gruppe) creation.getJcb().getSelectedItem();
		
		String curGrpName = g.getName();
		String jtfGrpName = list.getGroupNameJTF().getText();
		
		mem = list.getAmountlist();
		
		if(!curGrpName.equals(jtfGrpName))
			g.setName(jtfGrpName);
		
		gq.editGruppe(g);
		
		//Workaround
		//Das Element wird zun�chst gel�scht und an letzter Stelle wieder
		//hinzugef�gt
		creation.changeItemComboBox(g);
	}
	
	/**
	 * Die Anzahl und die Preise aller Items werden zusammengez�hlt und im unteren JLabel
	 * angezeigt
	 */
	@Override
	public void refreshTotalSum(List<Amount> amountlist) {
		// TODO Die Summe aller Mengen werden zusammengez�hlt
		//		und auf einem JLabel sichtbar gemacht
		double total = 0;
		
		for(Amount a : amountlist)
			total += a.getA_amount()*a.getFk_Item().getI_price();
		
		JLabel controlTotal = control.getTotal();
		controlTotal.setText(String.valueOf(total));
	}
	
	/**
	 * Eine neue Gruppe wird erstellt.<br>
	 * S�mtliche vorhandenen Felder werden gel�scht und ein leeres Dokument angezeigt.
	 */
	@Override
	public void newGroup() {
		// TODO Auto-generated method stub
		System.out.println("NEW GRP");
		list.cleanUpList();
		list.init();
		
		Gruppe g = new Gruppe();
		g.setName("");
		creation.addToComboBox(g);
		
		creation.selectLastItemComboBox();
		
		refreshUI();
	}

	/**
	 * GUI wird aktualisiert
	 */
	@Override
	public void refreshUI() {
		validate();
		repaint();
	}

	
}
