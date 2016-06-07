package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class CommandSpy implements CommandExecutor {

	public CommandSpy() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			if (player.hasPermission("miepcraft.listener.command.spy")) {
				Yaml yaml = Main.getPlayerYaml(player);
				yaml.set("CommandSpy Enabled", !yaml.getBoolean("CommandSpy Enabled"));
				yaml.save();
				if (yaml.getBoolean("CommandSpy Enabled")) {
					player.sendMessage("§8[§4CommandSpy§8] §aCommandSpy ist nun ein.");
				} else if (!yaml.getBoolean("CommandSpy Enabled")) {
					player.sendMessage("§8[§4CommandSpy§8] §cCommandSpy ist nun aus.");
				}
			} else {
				player.sendMessage("§8[§eCommandSpy§8] §6Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			Log.info("[CommandSpy.java] Not supportet.");
		}

		return true;
	}

}