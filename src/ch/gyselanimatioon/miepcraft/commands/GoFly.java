package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class GoFly implements CommandExecutor {

	public GoFly() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			Yaml yaml = Main.getPlayerYaml(player);
			if(yaml.getBoolean("BoughtFly")) {
				yaml.set("BoughtFly", false);
				player.setAllowFlight(false);
				sender.sendMessage("§8[§eFly§8] §6Du kannst nun nicht mehr fliegen.");
			} else {
				yaml.set("BoughtFly", true);
				player.setAllowFlight(true);
				sender.sendMessage("§8[§eFly§8] §6Du kannst nun fliegen.");
			}
			yaml.save();
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden.");
		}
		return true;
	}

}