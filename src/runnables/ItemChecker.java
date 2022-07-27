package runnables;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import osl.First.Main;
import quest.CollectQuest;
import quest.IQuest;
import quest.QuestType;

public class ItemChecker {
	private HashMap<UUID, List<IQuest>> playerQuests = Main.getQuests();
	private BukkitTask itemChecking = null;
	private HashMap<UUID, ItemChecker> existingMap = Main.getChecker();
	private BukkitTask questChecking = null;
	private UUID playerID;
	private Player player;
	
	
	public ItemChecker(UUID id) {
		playerID = id;
		player = Bukkit.getPlayer(id);
		for (UUID tocheck : existingMap) {
			
		}
	}
	public void startRunning() {
		questChecking = new BukkitRunnable() {
			@Override
			public void run() {
				for (IQuest checkedQuest : playerQuests.get(player.getUniqueId())) {
					if (checkedQuest.checkIfActive()) {
					for (QuestType stage : checkedQuest.getActualState().getQuestObjective()) {
						if (stage instanceof CollectQuest) {
							CollectQuest q1 = (CollectQuest) stage;
							int i = 0;
							for (ItemStack is : player.getInventory().getContents()) {
							    if (is != null && is.getType() != null && is.getType().equals(q1.getItem())) {
							        i += is.getAmount();
							    }
							    else
							    {
							    	continue;
							    }
							}
							q1.setAmount(i);
							if (q1.getOwned() == q1.toGather()) {
								player.getInventory().removeItem(new ItemStack(q1.getItem(), q1.toGather()));
							}
						}
					}
					}
					else {
						continue;
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 0L, 5L);
		
		itemChecking = new BukkitRunnable() {
			  @Override
			  public void run() {
				  
				//Quest completion mechanism
				  for (IQuest checkedQuest : playerQuests.get(player.getUniqueId())) { 
				  if (checkedQuest.checkIfActive()) {
					  if (checkedQuest.getActualState().checkCompletion()) {
							checkedQuest.changeActualState();
						}
				  }
				  else {
					  continue;
				  }
				  }
			  }
			}.runTaskTimer(Main.getInstance(), 0L, 5L);
	}
	
	public void stopRunning() {
		itemChecking.cancel();
		questChecking.cancel();
	}
}
