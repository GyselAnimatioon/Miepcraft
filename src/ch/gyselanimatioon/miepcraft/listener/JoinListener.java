package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

	public JoinListener() {
	}

	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent ev) {
		if(ev.getPlayer().hasPermission("miepcraft.listener.join.silent")) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (players.hasPermission("miepcraft.listener.join.spy")) {
					// JoinSpy wird nich der Person angezeigt die gejoint ist.
					if(!players.getName().toLowerCase().equalsIgnoreCase(ev.getPlayer().getName().toLowerCase())) {
						players.sendMessage("§8[§eJoinSpy§8]§6 " + ev.getPlayer().getName());
					}
				}
			}
		} else {
			Bukkit.broadcastMessage("§8[§eBeigetreten§8]§6 " + ev.getPlayer().getName());
		}
	}

}
