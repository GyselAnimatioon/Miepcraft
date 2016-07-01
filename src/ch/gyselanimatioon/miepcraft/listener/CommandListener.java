package ch.gyselanimatioon.miepcraft.listener;

import java.sql.ResultSet;
import java.sql.Statement;

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

		try {
			Statement statement = Main.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM miepcraft_newbies WHERE uuid = '" + event.getPlayer().getUniqueId() + "';");
			while (result.next()) {
				boolean locked = result.getBoolean("locked");
				if(locked) {
					event.getPlayer().sendMessage("§r§7" + Main.config.getString("unlock_stages." + result.getString("unlock_stage")) + " [" + result.getString("unlock_stage") + "]");
					event.setCancelled(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Player players : Bukkit.getOnlinePlayers()) {
			if (players.hasPermission("miepcraft.listener.command.spy")) {
				// CommandSpy wird nich der Person angezeigt die den Command
				// benutzt hat.
				if (!players.getName().toLowerCase().equalsIgnoreCase(event.getPlayer().getName().toLowerCase())) {
					Yaml yaml = Main.getPlayerYaml(players);
					if (yaml.getBoolean("CommandSpy Enabled")) {
						players.sendMessage(
								"§8[§eCommandSpy§8] §7" + event.getPlayer().getName() + " §f" + event.getMessage());
					}
				}
			}
		}

		if (!event.getPlayer().hasPermission("miepcraft.listener.command.bypass")) {
			String message = event.getMessage().toLowerCase();
			boolean permissions = true;
			if (message.contains("bukkit")) {
				permissions = false;
			} else if (message.startsWith("mv")) {
				permissions = false;
			} else if (message.contains("multiverse")) {
				permissions = false;
			} else if (message.contains("core")) {
				permissions = false;
			} else if (message.contains("portal")) {
				permissions = false;
			} else if (message.contains("mv")) {
				permissions = false;
			} else if (message.startsWith("hd")) {
				permissions = false;
			} else if (message.startsWith("rg")) {
				permissions = false;
			} else if (message.contains("miepcraft")) {
				permissions = false;
			} else if (message.contains("minecraft")) {
				permissions = false;
			} else if (message.contains("holo")) {
				permissions = false;
			} else if (message.contains("ignore")) {
				permissions = false;
			} else if (message.contains("head")) {
				permissions = false;
			} else if (message.contains("tool")) {
				permissions = false;
			} else if (message.contains("toggle")) {
				permissions = false;
			} else if (message.contains("chest")) {
				permissions = false;
			} else if (message.contains("region")) {
				permissions = false;
			} else if (message.contains("inspect")) {
				permissions = false;
			}

			if (!permissions) {
				event.setCancelled(true);
				event.getPlayer()
						.sendMessage("§8[§eNope§8]§6 Eines oder mehrere Wörter in deinem Command sind gesperrt.");
			}
		}

	}
}
