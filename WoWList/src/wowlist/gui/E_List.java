package wowlist.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import wowlist.entities.Amount;
import wowlist.entities.Gruppe;
import wowlist.interfaces.ControlListener;
import wowlist.interfaces.MaterialChanged;
import wowlist.queries.AmountQueries;

public class E_List extends JPanel implements MaterialChanged{

	private JTextField 	groupNameJTF 	= new JTextField();
	
	private List<ControlListener> controllist = new ArrayList<ControlListener>();
	private List<E_L_NewLine> elnllist = new ArrayList<E_L_NewLine>();
	
	private List<Amount> amountlist = new ArrayList<Amount>();
	
	private AmountQueries aq = new AmountQueries();
	
	public E_List(){
		init();
	}
	
	public void init() {
		setLayout(new MigLayout("", "[]", "[]"));
		firstRow();
		
		newRow();
	}

	public void newRow() {
		boolean check = true;
		for(E_L_NewLine n : elnllist)
			if(n.getNewMat().getText() == "New Material")
				check = false;
		
		if(check){
			E_L_NewLine newline = new E_L_NewLine(this);
			newline.addMaterialChangedListener(this);
			newline.newLine();
			elnllist.add(newline);
		}
	}
	
	public void newRow(Amount a) {
		boolean check = true;
		for(E_L_NewLine n : elnllist)
			if(n.getNewMat().getText() == "New Material")
				check = false;
		
		if(check){
			E_L_NewLine newline = new E_L_NewLine(this);
			newline.addMaterialChangedListener(this);
			newline.newLine();
			elnllist.add(newline);
			newline.setA(a);
		}
	}
	
	public void deleteRow(E_L_NewLine e_L_NewLine) {
		elnllist.remove(e_L_NewLine);
	}

	private void firstRow() {
		// TODO Erste Zeile erstellen, welche es ermöglicht 
		//		die Bezeichnung der Gruppe zu ändern
		groupNameJTF.setColumns(25);
		
		add(groupNameJTF, "span 4, wrap");
	}
	
	public void fail(String string, String error) {
		G_Fail.infoBox(string, error);
	}
	
	public void loadAmounts(Gruppe g) {
		// TODO Auto-generated method stub
		if(g != null){
			firstRow();
			groupNameJTF.setText(g.getName());
			
			amountlist = aq.getAllAmountsFromGivenGroup(g);
			
			
			if(!amountlist.isEmpty()){
				for(Amount a : amountlist)
					newRow(a);
			}
				
			newRow();
		}
	}
	
	public void cleanUpList(){
		removeAll();
		elnllist.clear();
		groupNameJTF.setText("");
		//init();
		refreshUI();
	}

	@Override
	public void materialChanged(Amount a) {
		// TODO Materialliste aktualisieren
		if(amountlist.contains(a)){
			amountlist.remove(a);
			amountlist.add(a);
		}
		else
			amountlist.add(a);
		
		refreshTotalSumLabel();
	}
	
	@Override
	public void deleteMaterial(Amount a) {
		// TODO Auto-generated method stub
		amountlist.remove(a);
		aq.deleteAmount(a);
	}

	public void addControlListener(E_Main e_Main) {
		// TODO Auto-generated method stub
		controllist.add(e_Main);
	}

	public void refreshTotalSumLabel() {
		// TODO Auto-generated method stub
		for(ControlListener cl : controllist)
			cl.refreshTotalSum(amountlist);
	}

	public void refreshUI() {
		// TODO Auto-generated method stub
		for(ControlListener cl : controllist)
			cl.refreshUI();
	}
	
	public JTextField 	getGroupNameJTF() 							{ return groupNameJTF; }
	public void 		setGroupNameJTF(JTextField groupNameJTF) 	{ this.groupNameJTF = groupNameJTF; }

	public List<ControlListener> 	getControllist() 									{ return controllist; }
	public void 					setControllist(List<ControlListener> controllist) 	{ this.controllist = controllist; }
	
	public List<E_L_NewLine> 	getElnllist() 							{ return elnllist; }
	public void 				setElnllist(List<E_L_NewLine> elnllist) { this.elnllist = elnllist; }
	
	public List<Amount> getAmountlist() { return amountlist; }
	public void setAmountlist(List<Amount> amountlist) { this.amountlist = amountlist; }
}
