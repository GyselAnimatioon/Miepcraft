package ch.gyselanimatioon.miepcraft.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class TracePlayers implements Listener {

	public TracePlayers() {

	}

	@EventHandler
	public void PlayerMoveEvent(PlayerMoveEvent e) {

		boolean xChange = e.getFrom().getBlockX() == e.getTo().getBlockX() ? false : true;
		boolean yChange = e.getFrom().getBlockY() == e.getTo().getBlockY() ? false : true;
		boolean zChange = e.getFrom().getBlockZ() == e.getTo().getBlockZ() ? false : true;
		
		if (xChange || yChange || zChange) {
			Yaml yaml = Main.getPlayerYaml(e.getPlayer());
			Yaml trace = Main.getPlayerTraceYaml(e.getPlayer());
			
			trace.add("Bewegung" + yaml.get("traveledBlocks") + ".Welt", e.getPlayer().getWorld().getName());
			trace.add("Bewegung" + yaml.get("traveledBlocks") + ".X", e.getPlayer().getLocation().getBlockX());
			trace.add("Bewegung" + yaml.get("traveledBlocks") + ".Y", e.getPlayer().getLocation().getBlockY());
			trace.add("Bewegung" + yaml.get("traveledBlocks") + ".Z", e.getPlayer().getLocation().getBlockZ());
			trace.save();
			yaml.increment("traveledBlocks");
			yaml.save();
		}
	}
}
