package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

public class Teamchat implements CommandExecutor {

	public Teamchat() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		String message = "";
		if (player != null) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (players.hasPermission("miepcraft.commands.teamchat")) {
					for (int i = 0; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					players.sendMessage("§8[§6Teamchat§8] §2" + sender.getName() + " §a" + message);
				}
			}
			Log.info("[Teamchat] " + sender.getName() + " " + message);
		} else {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (players.hasPermission("miepcraft.commands.teamchat")) {
					for (int i = 0; i < args.length; i++) {
						message = message + args[i] + " ";
					}
					players.sendMessage("§8[§6Teamchat§8] §2" + sender.getName() + " §a" + message);
				}
			}
			Log.info("[Teamchat] " + sender.getName() + " " + message);
		}

		return true;
	}

}