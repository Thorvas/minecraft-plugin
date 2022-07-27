package quest;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Item;

public class CollectQuest implements QuestType {
	private int toCollect = 0;
	private int ownedAmount = 0;
	private Material toGet = null;
	
	CollectQuest(Material newToGet, int newToCollect) {
		this.toGet = newToGet;
		this.toCollect = newToCollect;
	}
	
	public void setAmount(int newOwnedAmount) {
		this.ownedAmount = newOwnedAmount;
	}
	
	public int getOwned() {
		return this.ownedAmount;
	}
	
	public Material getItem() {
		return this.toGet;
	}
	
	public int toGather() {
		return this.toCollect;
	}
	
	public boolean checkCompletion() {
		if (this.toCollect == this.ownedAmount) {
			return true;
		}
		else {
			return false;
		}
	}
}
