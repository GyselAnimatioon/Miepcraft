package ch.gyselanimatioon.miepcraft.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class CommandListener implements Listener {

	public CommandListener() {

	}

	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (players.hasPermission("miepcraft.listener.command.spy")) {
				// CommandSpy wird nich der Person angezeigt die den Command benutzt hat.
				if (!players.getName().toLowerCase().equalsIgnoreCase(event.getPlayer().getName().toLowerCase())) {
					Yaml yaml = Main.getPlayerYaml(players);
					if (yaml.getBoolean("CommandSpy Enabled")) {
						players.sendMessage("§8[§eCommandSpy§8] §7" + event.getPlayer().getName() + " §f" + event.getMessage());
					}
				}
			}
		}

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

		List<String> list = Main.fileManager.read("log", "commandLog");
		list.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("log", "commandLog", list);

		List<String> list2 = Main.fileManager.read("log", "serverLog");
		list2.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("log", "serverLog", list2);

		int rand = (int) (Math.random() * 100);
		// Log.info(rand);
		if (rand < 3) {
			List<String> list4 = new ArrayList<>();
			list4.add("craft");
			Bukkit.broadcastMessage("§8[§eMiep§8]");
			Main.fileManager.write(".", "miepcraftGame", list4);
		}
		
		if(!event.getPlayer().hasPermission("miepcraft.listener.command.bypass")) {
			String message = event.getMessage().toLowerCase();
			boolean permissions = true;
			if(message.contains("bukkit")) {
				permissions = false;
			} else if(message.contains("mv")) {
				permissions = false;
			} else if(message.contains("multiverse")) {
				permissions = false;
			} else if(message.contains("core")) {
				permissions = false;
			} else if(message.contains("portal")) {
				permissions = false;
			} else if(message.contains("mv")) {
				permissions = false;
			} else if(message.contains("hd")) {
				permissions = false;
			} else if(message.contains("rg")) {
				permissions = false;
			} else if(message.contains("miepcraft")) {
				permissions = false;
			} else if(message.contains("minecraft")) {
				permissions = false;
			} else if(message.contains("holo")) {
				permissions = false;
			} else if(message.contains("ignore")) {
				permissions = false;
			} else if(message.contains("head")) {
				permissions = false;
			} else if(message.contains("tool")) {
				permissions = false;
			} else if(message.contains("toggle")) {
				permissions = false;
			} else if(message.contains("chest")) {
				permissions = false;
			} else if(message.contains("region")) {
				permissions = false;
			} else if(message.contains("inspect")) {
				permissions = false;
			}
			
			if(!permissions) {
				event.setCancelled(true);
				event.getPlayer().sendMessage("§8[§eNope§8]§6 Eines oder mehrere Wörter in deinem Command sind gesperrt.");
			}
		}

	}
}
