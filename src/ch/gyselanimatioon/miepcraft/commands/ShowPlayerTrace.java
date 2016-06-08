package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class ShowPlayerTrace implements CommandExecutor {

	public ShowPlayerTrace() {

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player != null) {
			if (player.hasPermission("miepcraft.commands.showplayertrace")) {

				Player target = player;
				
				if(args.length > 0) {
					target = (Player) Bukkit.getOfflinePlayer(args[0]);
				}
				
				Yaml yaml = Main.getOfflinePlayerYaml(target.getName());
				Yaml trace = Main.getOfflinePlayerTraceYaml(target.getName());
				yaml.set("showtrace", !yaml.getBoolean("showtrace"));
				yaml.save();
				
				int j = yaml.getInteger("traveledBlocks");
				int i = j - 50;
				if(args.length > 1) {
					i = j - Integer.parseInt(args[1]);
				}
				if (yaml.getBoolean("showtrace")) {
					for (; i < j; i++) {
						World welt = Bukkit.getWorld("world");
						int x = trace.getInteger("Bewegung" + i + ".X");
						int y = trace.getInteger("Bewegung" + i + ".Y") - 1;
						int z = trace.getInteger("Bewegung" + i + ".Z");

						Block block = welt.getBlockAt(x, y, z);
						trace.set("Bewegung" + i + ".Block", block.getTypeId());
						yaml.set("lastshowtrace", j);
						yaml.save();
						trace.save();
						block.setType(Material.REDSTONE_BLOCK);
					}
				} else {
					j = yaml.getInteger("lastshowtrace");
					if(args.length > 1) {
						i = j - Integer.parseInt(args[1]);
					} else {
						i = j - 50;
					}
					for (; i < j; i++) {

						World welt = Bukkit.getWorld("world");
						int x = trace.getInteger("Bewegung" + i + ".X");
						int y = trace.getInteger("Bewegung" + i + ".Y") - 1;
						int z = trace.getInteger("Bewegung" + i + ".Z");

						Block block = welt.getBlockAt(x, y, z);
						block.setTypeId(trace.getInteger("Bewegung" + i + ".Block"));
					}
				}

			} else {
				player.sendMessage("§8[§eTrace§8]§6 Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			Log.info("[ShowPlayerTrace.java] Not supportet.");
		}
		return true;
	}

}