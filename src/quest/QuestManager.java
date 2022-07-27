package quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import osl.First.Main;
import runnables.ItemChecker;
import stats.Statistics;

public class QuestManager implements Listener {
	private ItemChecker checkPlayer = null;
	private UUID playerID = null;
	private Player player = null;
	HashMap<UUID, List<IQuest>> playerQuests = Main.getQuests();
	HashMap<UUID, ItemChecker> checker = Main.getChecker();
	private Quest1 quest1 = null;
	
	
	//Assigning available quests to player (they are inactive by default)
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		player = event.getPlayer();
		this.checkPlayer = new ItemChecker(player.getUniqueId());
		if (!(checker.containsKey(player.getUniqueId()))) {
			checker.put(player.getUniqueId(), checkPlayer);
		}
		checker.get(player.getUniqueId()).startRunning();
		if (!(playerQuests.containsKey(player.getUniqueId()))) {
			this.quest1 = new Quest1(player.getUniqueId(), "The beginning", "The description", "You've begun your first quest!", "Your first quest ends here. Good luck!");
			playerQuests.put(player.getUniqueId(), new ArrayList<IQuest>());
			playerQuests.get(player.getUniqueId()).add(quest1);
			player.sendMessage("Quests have been assigned.");
		}
		else
		{
			for (IQuest quest : playerQuests.get(player.getUniqueId())) {
			quest.setPlayer(player.getUniqueId());
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		checker.get(event.getPlayer().getUniqueId()).stopRunning();
	}
	
	
	//Killing mobs mechanism
	@EventHandler
	public void onEntityDeath(EntityDeathEvent evt) {
		if (evt.getEntity().getKiller() instanceof Player) {
		Player attacker = (Player) evt.getEntity().getKiller();
		for (IQuest checkedQuest : playerQuests.get(player.getUniqueId())) {
			if (checkedQuest.checkIfActive()) {
			for (QuestType stage : checkedQuest.getActualState().getQuestObjective()) {
				if (stage instanceof KillQuest) {
					KillQuest q1 = (KillQuest) stage;
						if (q1.getEntity() == evt.getEntityType()) {	
							q1.progressQuest(1);
						}
					}
					else {
						continue;
				}
			}
			}
			else {
				continue;
			}
		}
	}
}
	
	//Drop Items 
		/*@EventHandler
		public void PickUpItem(EntityDropItemEvent event) {
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				for (IQuest checkedQuest : playerQuests.get(player.getUniqueId())) {
				if (checkedQuest.checkIfActive()) {
				for (QuestType stage : checkedQuest.getActualState().getQuestObjective()) {
					if (stage instanceof CollectQuest) {
						Item pickedItem = event.getItemDrop();
						CollectQuest q1 = (CollectQuest) stage;
						if (q1.getItem() == pickedItem.getItemStack().getType()) {
							q1.addAmount(-1);
							if (checkedQuest.getActualState().checkCompletion()) {
								checkedQuest.changeActualState();
							}
						}
					}
				
				}
				}
			}
		}
	}*/
	
	@EventHandler
	public void onEmptyBucket(PlayerBucketEmptyEvent evt) {
		evt.getPlayer().sendMessage("You emptied it!");
		for (IQuest quest : playerQuests.get(evt.getPlayer().getUniqueId())) {
		quest.Activate();
		}
	}
}
