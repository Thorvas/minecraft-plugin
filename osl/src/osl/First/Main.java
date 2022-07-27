package osl.First;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import commands.NewCommand;
import events.TutorialEvents;
import quest.IQuest;
import quest.QuestManager;
import runnables.ItemChecker;
import stats.Statistics;
import stats.Strength;

public class Main extends JavaPlugin {
	private static HashMap<UUID, Statistics> playerStats = new HashMap<UUID, Statistics>();
	private static HashMap<UUID, List<IQuest>> playerQuests = new HashMap<UUID, List<IQuest>>();
	private static HashMap<UUID, ItemChecker> runnableChecking = new HashMap<UUID, ItemChecker>();
	private static Main instance;
	
	@Override
	public void onEnable() {
		NewCommand cmd = new NewCommand(this);
		instance = this;
		TutorialEvents evt = new TutorialEvents();
		getServer().getPluginManager().registerEvents(evt, this);
		getServer().getPluginManager().registerEvents(new QuestManager(), this);
		getCommand("heal").setExecutor(cmd);
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "PLUGIN IS ENABLEEEEEEEEEEED.");
	}
	
	@Override
	public void onDisable() {
		System.out.println(ChatColor.GREEN + "Plugin disabled.");
	}
	
	public static HashMap<UUID, Statistics> getMap() {
		return playerStats;
	}
	
	public static HashMap<UUID, List<IQuest>> getQuests() {
		return playerQuests;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static HashMap<UUID, ItemChecker> getChecker() {
		return runnableChecking;
	}

}
