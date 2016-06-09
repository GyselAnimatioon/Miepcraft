package ch.gyselanimatioon.miepcraft.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class CreatePlayerProfile implements Listener {

	public CreatePlayerProfile() {
	}

	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent ev) {
		Yaml yaml = Main.getPlayerYaml(ev.getPlayer());
		yaml.save();
	}

	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent ev) {
		Yaml yaml = Main.getPlayerYaml(ev.getPlayer());
		yaml.add("Playername", ev.getPlayer().getName());
		if(!yaml.contains("CommandSpy Enabled")) {
			yaml.add("CommandSpy Enabled", false);
		}
		if(!yaml.contains("nick")) {
			yaml.add("nick", ev.getPlayer().getName());
		}
		if(!yaml.contains("traveledBlocks")) {
			yaml.add("traveledBlocks", 0);
		}
		if(!yaml.contains("showtrace")) {
			yaml.add("showtrace", false);
		}
		if(!yaml.contains("lastshowtrace")) {
			yaml.add("lastshowtrace", 0);
		}
		if(!yaml.contains("Spielzeit")) {
			yaml.add("Spielzeit", 0);
		}
		if(!yaml.contains("BoughtFly")) {
			yaml.add("BoughtFly", false);
		}
		yaml.save();

	}
}
