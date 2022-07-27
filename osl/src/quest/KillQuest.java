package quest;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class KillQuest implements QuestType {
	private int amount = 0;
	private int toKill = 0;
	private EntityType entity;
	
	public KillQuest(int newAmount, EntityType newType, int newToKill) {
		this.amount = newAmount;
		this.toKill = newToKill;
		this.entity = newType;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public int getToKill() {
		return this.toKill;
	}
	
	public EntityType getEntity() {
		return entity;
	}
	
	public void progressQuest(int a) {
		this.amount+=a;
	}
	
	public boolean checkCompletion() {
		if (this.getAmount() == this.getToKill()) {
			return true;
		}
		else {
			return false;
		}
	}
}
