package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

	public QuitListener() {
	}

	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent ev) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (players.hasPermission("miepcraft.listener.quit.spy")) {
				// QuitSpy wird nich der Person angezeigt die geleftet ist.
				if(!players.getName().toLowerCase().equalsIgnoreCase(ev.getPlayer().getName().toLowerCase())) {
					players.sendMessage("§8[§eQuitSpy§8]§6 " + ev.getPlayer().getName());
				}
			}
		}
	}
}
