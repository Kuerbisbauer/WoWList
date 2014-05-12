package wowlist.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.JFileChooser;

import wowlist.entities.Item;


public class CSVImport {
	
	//public static final String csv = "c:\\csvtest.csv";
	
	private EntityManager em;
	
	public CSVImport(){
		
	}

	public void fullImport() {
		setEM();
		BufferedReader br = null;
		String line = "";
		String splitCSV = ",";
		
		Item m;
		
		em.getTransaction().begin();
		
		//Setzt alle Daten zurück
		//em.createNativeQuery("truncate table Materials").executeUpdate();
		
		try{
			JFileChooser jfc = new JFileChooser();
			jfc.showOpenDialog(null);
			File f = jfc.getSelectedFile();
			br = new BufferedReader(new FileReader(f));
			br.readLine();
			while((line = br.readLine()) != null){
				String[] itemName = line.split(splitCSV);
				double price = Double.parseDouble(itemName[9]);
				
				m = new Item();
				m.setI_name(itemName[5]);
				m.setI_price(price);
				
				em.persist(m);
				
				System.out.println(itemName[5] + " " + itemName[9]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		em.getTransaction().commit();
	}
	
	private void setEM()
	{
		em = Persistence.createEntityManagerFactory("WoWList").createEntityManager();
	}
}
