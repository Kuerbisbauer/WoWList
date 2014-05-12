package wowlist.interfaces;

import java.util.List;

import wowlist.entities.Amount;

public interface ControlListener {
	public void saveList();
	public void refreshTotalSum(List<Amount> amountlist);
	public void refreshUI();
}
