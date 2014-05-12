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
	
	private void goimport(){
		CSVImport csv = new CSVImport();
		csv.fullImport();
	}

	//Holt sich die ausgewählte Gruppe aus E_List
	@Override
	public void getCurrentGroup() {
		// TODO Die ausgewählte Gruppe wird von E_Creation geholt
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
	
	//Speichert die Liste von E_List in die Datenbank
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

	private void saveNewAmount() {
		Gruppe g = (Gruppe) creation.getJcb().getSelectedItem();
		mem.removeAll(diff);
		
		for(Amount a : mem)
			aq.newAmount(g, a.getFk_Item(), a.getA_amount());
		
	}

	private void saveExistingAmount() {
		List<Amount> alist = mem;
		
		for(Amount a : alist)
			aq.editAmount(a);
	}

	private void saveGroup() {
		Gruppe g = (Gruppe) creation.getJcb().getSelectedItem();
		
		String curGrpName = g.getName();
		String jtfGrpName = list.getGroupNameJTF().getText();
		
		mem = list.getAmountlist();
		
		if(!curGrpName.equals(jtfGrpName))
			g.setName(jtfGrpName);
		
		gq.editGruppe(g);
		
		//Workaround
		//Das Element wird zunächst gelöscht und an letzter Stelle wieder
		//hinzugefügt
		creation.changeItemComboBox(g);
	}
	
	@Override
	public void refreshTotalSum(List<Amount> amountlist) {
		// TODO Die Summe aller Mengen werden zusammengezählt
		//		und auf einem JLabel sichtbar gemacht
		double total = 0;
		
		for(Amount a : amountlist)
			total += a.getA_amount()*a.getFk_Item().getI_price();
		
		JLabel controlTotal = control.getTotal();
		controlTotal.setText(String.valueOf(total));
	}
	
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

	@Override
	public void refreshUI() {
		validate();
		repaint();
	}

	
}
