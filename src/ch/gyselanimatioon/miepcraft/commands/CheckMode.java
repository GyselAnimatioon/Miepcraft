package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

public class CheckMode implements CommandExecutor {

	public CheckMode() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player != null) {
			if(player.hasPermission("miepcraft.commands.checkmode")) {
				for(Player onePlayer : Bukkit.getOnlinePlayers()) {
					player.sendMessage("§8[§eCheckmode§8] §6" + onePlayer.getName() + ": " + onePlayer.getGameMode().toString() + " + " + onePlayer.getAllowFlight());
				}
			} else {
				player.sendMessage("§8[§ePing§8]§6 Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			for(Player onePlayer : Bukkit.getOnlinePlayers()) {
				Log.info("§8[§eCheckmode§8] §6" + onePlayer.getName() + ": " + onePlayer.getGameMode().toString() + " + " + onePlayer.getAllowFlight());
			}
		}
		return true;
	}

}