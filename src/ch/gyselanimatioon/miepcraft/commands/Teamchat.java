package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ch.gyselanimatioon.miepcraft.PluginMain.LOGGER;

public class Teamchat implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if ((sender instanceof Player)) {
			player = (Player) sender;
		}
		String message = "";
		for (int i = 0; i < args.length; i++) {
			message = message + args[i] + " ";
		}
		if (player != null) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (players.hasPermission("miepcraft.commands.teamchat")) {
					players.sendMessage("§8[§6Teamchat§8] §2" + sender.getName() + " §a" + message);
				}
			}
			LOGGER.info("[Teamchat] " + sender.getName() + " " + message);
		} else {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (players.hasPermission("miepcraft.commands.teamchat")) {
					players.sendMessage("8[§6Teamchat§8] §2" + sender.getName() + " §a" + message);
				}
			}
			LOGGER.info("[Teamchat] " + sender.getName() + " " + message);
		}

		return true;
	}
}