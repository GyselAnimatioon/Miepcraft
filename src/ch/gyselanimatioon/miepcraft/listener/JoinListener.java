package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		if (event.getPlayer().hasPermission("miepcraft.listener.join.silent")) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (players.hasPermission("miepcraft.listener.join.spy")) {
					if (!players.getName().toLowerCase().equalsIgnoreCase(event.getPlayer().getName().toLowerCase())) {
						players.sendMessage("§[§eJoinSpy§8]§ " + event.getPlayer().getName());
					}
				}
			}
		} else {
			Bukkit.broadcastMessage("§a§o" + event.getPlayer().getName());
		}
	}
}
