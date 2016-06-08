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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player != null) {
			if(player.hasPermission("miepcraft.commands.showplayertrace")) {
				
				Yaml yaml = Main.getPlayerYaml(player);
				Yaml trace = Main.getPlayerTraceYaml(player);
				
				for(int i = 0;i < Integer.parseInt(yaml.get("traveledBlocks").toString());i++) {

					World welt = Bukkit.getWorld("world");
					//World welt = Bukkit.getWorld(trace.getString("Bewegung" + i + ".Welt"));
					int x = trace.getInteger("Bewegung" + i + ".X");
					int y = trace.getInteger("Bewegung" + i + ".Y") - 1;
					int z = trace.getInteger("Bewegung" + i + ".Z");
					
					Block block = welt.getBlockAt(x, y, z);
					block.setType(Material.REDSTONE_BLOCK);
				}
				
			} else {
				player.sendMessage("§8[§eTrace§8]§6 Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			for(Player onePlayer : Bukkit.getOnlinePlayers()) {
				Log.info("§8[§eCheckmode§8] §6" + onePlayer.getName() + ": " + onePlayer.getGameMode().toString() + " + " + onePlayer.getAllowFlight());
			}
		}
		return true;
	}

}