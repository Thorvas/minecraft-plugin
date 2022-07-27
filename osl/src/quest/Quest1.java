package quest;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Quest1 implements IQuest {
	public UUID playerID;
	private String questName = null;
	private String questDescription = null;
	private String beginMessage = null;
	private String completeMessage = null;
	public Player player;
	private boolean isFinished = false;
	private boolean isActive = false;
	private QuestState state1 = new QuestState(false, "First, kill 1 zombie and gather 20 stone", "Congratulations! You've done well!");
	private QuestState state2 = new QuestState(false, "Then, kill 1 skeleton!", "Congratulations! You've done well!");
	private QuestState state3 = new QuestState(true, "And lastly, kill 1 creeper!", "Congratulations! You've done well!");
	private QuestState actualState = state1;
	
	public Quest1(UUID id, String qName, String qDesc, String bMessage, String cMessage) {
		state1.addObjective(new KillQuest(0, EntityType.ZOMBIE, 1));
		state1.addObjective(new CollectQuest(Material.STONE, 20));
		state2.addObjective(new KillQuest(0, EntityType.SKELETON, 1));
		state3.addObjective(new KillQuest(0, EntityType.CREEPER, 1));
		questName = qName;
		questDescription = qDesc;
		beginMessage = bMessage;
		completeMessage = cMessage;
		this.playerID = id;
		this.player = Bukkit.getPlayer(id);
		state1.setNextState(state2);
		state2.setNextState(state3);
	}
	public QuestState getActualState() {
		return actualState;
	}
	
	public void setPlayer(UUID p) {
		this.player = Bukkit.getPlayer(p);
	}
	
	public String getQuestName() {
		return questName;
	}
	public void completeQuest() {
		player.sendMessage(completeMessage);
		player.getInventory().addItem(new ItemStack(Material.STONE, 1));
		this.isFinished = true;
		this.isActive = false;
	}
	public void Activate() {
		player.sendMessage(beginMessage);
		player.sendMessage(actualState.getBeginMessage());
		this.isActive = true;
	}
	
	public void Deactivate() {
		player.sendMessage("You've abandoned your first quest...");
		this.isActive = false;
	}
	
	public void changeActualState() {
		if (!actualState.getIsLast()) {
			player.sendMessage(actualState.getCompleteMessage());
			actualState = actualState.getNextState();
			player.sendMessage(actualState.getBeginMessage());
		}
		else {
			player.sendMessage(actualState.getCompleteMessage());
			this.completeQuest();
		}
	}
	public boolean checkIfActive() {
		return this.isActive;
	}
	
	public boolean checkIfFinished() {
		return isFinished;
	}

}
