package events;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import osl.First.Main;
import stats.Statistics;
import stats.Strength;

public class TutorialEvents implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Statistics stats = new Statistics();
		HashMap<UUID, Statistics> playerStats = Main.getMap();
		Player player = event.getPlayer();
		if (!(playerStats.containsKey(player.getUniqueId()))) {
			playerStats.put(player.getUniqueId(), stats);
			player.sendMessage("You've been put.");
		}
		else
		{
			player.sendMessage("You're already in hashmap. Your strength is: "+playerStats.get(player.getUniqueId()).getStrength().getAmount());
		}
	}
	
	@EventHandler
	public static void onPlayerWalk(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();
		
		Material block = player.getWorld().getBlockAt(x, y-1, z).getType();
		
		if (block == Material.STONE) {
			player.sendMessage(ChatColor.GREEN+"You are stepping on stone!");
		}
	}
	
	@EventHandler
	public static void onZombieDrop(EntityDeathEvent event) {
		if (event.getEntity() instanceof Zombie) {
			List<ItemStack> items = event.getDrops();
			items.clear();
			if (event.getEntity().getKiller() instanceof Player) {
				Player player = (Player) event.getEntity().getKiller();
				ItemStack drop = new ItemStack(Material.STONE, 1);
				player.getWorld().dropItem(player.getLocation().add(0, 1, 0), drop);
			}
		}
	}

}
