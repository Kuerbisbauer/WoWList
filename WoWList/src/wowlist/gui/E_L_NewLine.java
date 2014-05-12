package wowlist.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import wowlist.entities.Amount;
import wowlist.entities.Item;
import wowlist.interfaces.MaterialChanged;
import wowlist.misc.IntegerField;

public class E_L_NewLine{

	private E_List e_list;
	
	private Amount a = new Amount();
	
	private JButton			newMat		= new JButton();
	private JButton			deleteMat	= new JButton();
	private IntegerField	amount		= new IntegerField();
	private JTextField		price		= new JTextField();
	private JTextField		total		= new JTextField();
	
	private List<MaterialChanged> 	materialchanged_list	= new ArrayList<MaterialChanged>();
	
	public E_L_NewLine(E_List e_List){
		this.e_list = e_List;
	}
	
	public void newLine() {
		newMat.setText("New Material");
		deleteMat.setText("Delete Material");
		
		amount.setPreferredSize(new Dimension( 70, 24 ) );
		price.setPreferredSize(new Dimension( 70, 24 ) );
		total.setPreferredSize(new Dimension( 70, 24 ) );
		
		price.setEnabled(false);
		total.setEnabled(false);
		
		e_list.add(newMat, "grow");
		e_list.add(amount, "");
		e_list.add(price, "");
		e_list.add(total, "");
		e_list.add(deleteMat, "wrap");
		
		setActionListener();
	}
	
	private void setActionListener() {
		
		newMat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				S_Search ss = new S_Search();
				ss.setSize(new Dimension(200, 300));
				ss.setVisible(true);
				
				Item i = ss.getSelectedItem();
				
				if(i != null){
					a.setFk_Item(i);
					
					newMat.setText(i.getI_name());
					price.setText(String.valueOf(i.getI_price()));
					
					e_list.newRow();
					
					e_list.refreshUI();
				}
			}
		});
		amount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try{
					int i = Integer.parseInt(amount.getText());
					Double d = Double.parseDouble(price.getText());
		
					Double id = i*d;
					
					total.setText(String.valueOf(id));

					a.setA_amount(i);
					materialChanged();
				}catch(Exception ex){};
			}
		});
		deleteMat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e_list.getElnllist().size() > 1){
					if(newMat.getText() != "New Material"){
						e_list.deleteRow(newLineClass());
						
						e_list.remove(newMat);
						e_list.remove(amount);
						e_list.remove(price);
						e_list.remove(total);
						e_list.remove(deleteMat);
						
						e_list.refreshUI();
						deleteMaterial(a);
					}
				}
			}
		});
	}
	
	

	private void loadAmountIntoComponents() {
		newMat.setText(a.getFk_Item().getI_name());
		amount.setText(String.valueOf(a.getA_amount()));
		price.setText(String.valueOf(a.getFk_Item().getI_price()));
		total.setText(String.valueOf(a.getA_amount()*a.getFk_Item().getI_price()));
	}
	
	public E_L_NewLine newLineClass(){
		return this;
	}

	protected void materialChanged() {
		for(MaterialChanged mc : materialchanged_list)
			mc.materialChanged(a);
	}
	
	protected void deleteMaterial(Amount a) {
		for(MaterialChanged mc : materialchanged_list)
			mc.deleteMaterial(a);
	}
	
	public void addMaterialChangedListener(E_List e_List) {
		materialchanged_list.add(e_List);
	}
	public Amount 	getA() 			{ return a; }
	public void 	setA(Amount a) 	{ this.a = a; loadAmountIntoComponents();}

	public JButton 	getNewMat() 				{ return newMat;}
	public void 	setNewMat(JButton newMat) 	{ this.newMat = newMat; }
}
