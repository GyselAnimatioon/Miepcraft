package ch.gyselanimatioon.miepcraft.features.ontime;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class MiepcraftOntime implements Listener {
	
	public MiepcraftOntime() {
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent ev) {
		Yaml yaml = Main.getPlayerYaml(ev.getPlayer());
		yaml.set("Login", (System.currentTimeMillis() / 1000));
		yaml.save();
	}
	

	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent ev) {
		Yaml yaml = Main.getOfflinePlayerTraceYaml(ev.getPlayer().getName());
		yaml.set("Logout", (System.currentTimeMillis() / 1000));
		int sessionTime = (yaml.getInteger("Logout") - yaml.getInteger("Login"));
		yaml.set("Spielzeit", yaml.getInteger("Spielzeit") + sessionTime);
		yaml.save();
	}
}
