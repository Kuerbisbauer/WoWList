package wowlist.interfaces;

import java.util.List;

import wowlist.entities.Amount;

public interface ControlListener {
	/**
	 * Die Liste wird gespeichert. Alle enthaltenen Mengen und Items werden
	 * in die Datenbank geschrieben.
	 */
	public void saveList();
	
	/**
	 * Der Gesamtpreis der Liste wird angezeigt.
	 * 
	 * @param amountlist - Erhaltene Liste von Mengen (Diese enthalten: Item, Anzahl und Preis)
	 */
	public void refreshTotalSum(List<Amount> amountlist);
	
	/**
	 * Falls eine neue Zeile erstellt wurde, so muss die GUI dementsprechend aktualisiert werden.
	 */
	public void refreshUI();
}
