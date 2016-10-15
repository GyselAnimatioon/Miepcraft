package ch.gyselanimatioon.miepcraft.listener;
//BROKEN

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent ev) {
		for (Player players : Bukkit.getServer().getOnlinePlayers()) {
			if (players.hasPermission("miepcraft.listener.quit.spy")) {
				if (!players.getName().toLowerCase().equalsIgnoreCase(ev.getPlayer().getName().toLowerCase())) {
					players.sendMessage("§8[§eQuitSpy§8]§6 " + ev.getPlayer().getName());
				}
			}
		}
	}
}