package ch.gyselanimatioon.miepcraft.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class Sup implements CommandExecutor {

	public Sup() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			if (!sender.hasPermission("miepcraft.commands.sup")) {
				sender.sendMessage("§8[§eTicket§8] §6Nutze /ticket add <ticket>.");
			} else {
				if (args.length > 0) {
					Yaml yaml = Main.getPlayerYaml(player);
					boolean beforeCommand = yaml.getBoolean("Supportmode");
					
					if (beforeCommand) {
						Date now = new Date();
						SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
						List<String> list = Main.fileManager.read("log", "supporting");
						list.add("[" + format.format(now) + "] " + player.getName() + " " + args.toString());
						Main.fileManager.write("log", "supporting", list);
						
						yaml.set("BackLocation", player.getLocation());
						player.setAllowFlight(true); // Fly for the player
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (!p.hasPermission("miepcraft.commands.sup.ignore")) {
								p.hidePlayer(player); // Vanish the player
							}
						}
						player.sendMessage("§8[§4Server§8] §aSupporting gestartet.");
					} else {
						player.teleport((Location) yaml.get("BackLocation"));
						player.setAllowFlight(false);
						for (Player p : Bukkit.getOnlinePlayers()) {
								p.showPlayer(player);
						}
						player.sendMessage("§8[§4Server§8] §cSupporting gestoppt.");
					}
					yaml.set("Supportmode", !beforeCommand);
					yaml.save();
				} else {
					player.sendMessage("§8[§eSupportMode§8] §6Gebe einen Support Grund an.");
				}
			}
		} else {
			sender.sendMessage("Nicht möglich in der Konsole.");
		}
		return true;
	}

}