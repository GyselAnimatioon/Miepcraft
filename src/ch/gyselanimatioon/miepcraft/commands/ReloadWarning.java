package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;

public class ReloadWarning implements CommandExecutor {

	public ReloadWarning() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player != null) {
			if (player.hasPermission("miepcraft.commands.reloadwarning")) {
				Main.title.setTitle("Server Reload");
				Main.title.setSubtitle("Bitte bewege dich nicht!");
				Main.title.setTitleColor(ChatColor.DARK_RED);
				Main.title.setSubtitleColor(ChatColor.RED);
				Main.title.setFadeInTime(1);
				Main.title.setFadeOutTime(1);
				Main.title.setStayTime(5);
				Main.title.broadcast();
				player.sendMessage("§8[§eReloadWarning§8] §6Reload Warning gesendet.");
			} else {
				player.sendMessage("§8[§eReloadWarning§8] §6Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			Main.title.setTitle("Server Reload");
			Main.title.setSubtitle("Bitte bewege dich nicht!");
			Main.title.setFadeInTime(1);
			Main.title.setFadeOutTime(1);
			Main.title.setStayTime(5);
			Main.title.setTitleColor(ChatColor.DARK_RED);
			Main.title.setSubtitleColor(ChatColor.RED);
			Main.title.broadcast();
			sender.sendMessage("[ReloadWarning] Reload Warning gesendet.");
		}
		return true;
	}

}