package ch.gyselanimatioon.miepcraft.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class Nick implements CommandExecutor {

	public Nick() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			if (player.hasPermission("miepcraft.command.nick")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("remove")) {
						player.setPlayerListName(player.getName());
						player.setCustomName(player.getName());
						player.setDisplayName(player.getName());
					}
				} else {
					List<String> nicks = Main.fileManager.read(".", "nicknames");
					int anzahl = nicks.size();
					int random = (int) (Math.random() * anzahl);

					String[] nick = nicks.toArray(new String[nicks.size()]);
					String nickname = nick[random];
					player.setPlayerListName(nickname);
					player.setCustomName(nickname);
					player.setDisplayName(nickname);
					
					Yaml yaml = Main.getPlayerYaml(player);
					yaml.set("nick", nickname);
					yaml.save();
				}
			} else {
				player.sendMessage("§8[§eNick§8] §6Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			Log.info("[Nick.java] Not supportet.");
		}
		return true;
	}

}
