package wowlist.interfaces;

import wowlist.entities.Amount;

public interface MaterialChanged {
	public void materialChanged(Amount a);
	public void deleteMaterial(Amount a);
}
