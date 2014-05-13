package wowlist.interfaces;

import wowlist.entities.Amount;

public interface MaterialChanged {
	
	/**
	 * Wenn sich das Material geändert hat, so wird die Anzahl, Preis und Gesamtpreis
	 * zurückgesetzt. Sowie in der Statusleiste der Gesamtpreis der Liste.
	 * 
	 * @param a - Erhaltene Menge (beinhaltet Item und Anzahl)
	 */
	public void materialChanged(Amount a);
	
	/**
	 * Falls ein Material gelöscht wird - siehe oben
	 * 
	 * @param a - Erhaltene Menge (beinhaltet Item und Anzahl)
	 */
	public void deleteMaterial(Amount a);
}
