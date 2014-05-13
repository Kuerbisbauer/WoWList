package wowlist.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import wowlist.entities.Item;
import wowlist.queries.ItemQueries;

public class S_Search extends JDialog{
	
	private JList<Item> jlist;
	private DefaultListModel<Item> dlm = new DefaultListModel<Item>();
	
	private ItemQueries iq = new ItemQueries();
	
	private Item selectedItem;
	
	public S_Search(){
		setModal(true);
		init();
	}

	
	private void init() {
		setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane();
		JPanel panel = new JPanel();
		
		final JTextField searchIntpu = new JTextField();
		
		jlist = new JList<Item>(dlm);
		
		JButton ok = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		
		searchIntpu.setText("Suchen...");
		searchIntpu.setColumns(10);
		
		searchIntpu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				searchIntpu.setText("");
			}
		});
		searchIntpu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String s = searchIntpu.getText();
				getItemsLike(s);
			}
		});
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSelectedItem(jlist.getSelectedValue());
				dispose();
			}
		});
		
		scrollPane.setViewportView(jlist);
		
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(ok);
		panel.add(cancel);
		
		add(scrollPane, BorderLayout.CENTER);
		add(searchIntpu, BorderLayout.NORTH);
		add(panel, BorderLayout.SOUTH);
	}
	
	/**
	 * Jedes mal wenn etwas eingegeben wird, wird die DB nach dem 
	 * String abgefragt und alle Datensätze die diesen enthalten
	 * in der Liste angezeigt.
	 * 
	 * @param s - Eingegebener String
	 */
	protected void getItemsLike(String s){
		addData(iq.getItemsLikeString(s));
	}
	
	/**
	 * Die Datensätze aus der Datenbank werden in die Liste geschrieben
	 * 
	 * @param list - Ergebnisliste aus der Datenbank
	 */
	private void addData(List<Item> list){
		dlm.clear();
		for(Item m : list)
			dlm.addElement(m);
	}

	public JList<Item> 	getJlist() 					{ return jlist; }
	public void 		setJlist(JList<Item> jlist) { this.jlist = jlist; }
	
	public Item getSelectedItem() 					{ return selectedItem; }
	public void setSelectedItem(Item selectedItem) 	{ this.selectedItem = selectedItem; }
}
