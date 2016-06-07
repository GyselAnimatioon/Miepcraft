package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {

	public ClearChat() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player != null) {
			if (player.hasPermission("miepcraft.commands.clearchat")) {
				for(int i = 0;i < 200;i++) {
					Bukkit.broadcastMessage("");
				}
				Bukkit.broadcastMessage("§8[§eChat§8] §6Der Chat wurde geleert!");
			} else {
				player.sendMessage("§8[§eClearChat§8] §6Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			for(int i = 0;i < 200;i++) {
				Bukkit.broadcastMessage("");
			}
			Bukkit.broadcastMessage("§8[§eChat§8] §6Der Chat wurde geleert!");
		}
		return true;
	}

}