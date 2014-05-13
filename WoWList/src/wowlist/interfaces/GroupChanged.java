package wowlist.interfaces;

public interface GroupChanged {
	
	/**
	 * Die ausgewählte Gruppe in der ComboBox
	 */
	public void getCurrentGroup();
	
	/**
	 * Wenn eine neue Gruppe erstellt wird
	 */
	public void newGroup();
}
