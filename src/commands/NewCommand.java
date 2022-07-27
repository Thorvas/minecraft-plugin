package commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import osl.First.Main;

public class NewCommand implements CommandExecutor {
	int a = 0;
	private Main main;

	public NewCommand(Main mainClass) {
		main = mainClass;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("heal")) {
			double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
			player.setHealth(maxHealth);
			player.sendMessage("You were poisoned!");
			new BukkitRunnable() {
				@Override
				public void run() {
				if (a<5)
				{
					player.sendMessage("Damaging...");
					player.damage(1);
					a++;
				}
					
				else {
					a=0;
					Bukkit.getScheduler().cancelTask(this.getTaskId());
					}
				}
				}.runTaskTimer(main, 20L, 10L);
			return true;
		}
		return true;
	}

}
