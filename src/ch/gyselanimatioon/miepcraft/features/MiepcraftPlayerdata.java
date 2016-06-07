package ch.gyselanimatioon.miepcraft.features;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ch.gyselanimatioon.miepcraft.Main;

public class MiepcraftPlayerdata implements Listener {

	public MiepcraftPlayerdata() {
	}

	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent ev) {
		List<String> old = Main.fileManager.read("user", ev.getPlayer().getName());
		String[] oldLines = old.toArray(new String[old.size()]);
		
		long onlineTime = Long.parseLong(oldLines[0]);
		long joinTime = Long.parseLong(oldLines[1]);
		long leaveTime = (System.currentTimeMillis() / 1000);
		
		onlineTime = onlineTime + (leaveTime - joinTime);
		
		List<String> newL = new ArrayList<>();
		newL.add("" + onlineTime);
		Main.fileManager.write("user", ev.getPlayer().getName(), newL);
	}
	
	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent ev) {
		if(Main.fileManager.createFile("user",ev.getPlayer().getName())) {
			List<String> newFileLine = new ArrayList<>();
			newFileLine.add("0");
			newFileLine.add("" + (System.currentTimeMillis() / 1000));
			Main.fileManager.write("user", ev.getPlayer().getName(), newFileLine);
		}
		List<String> old = Main.fileManager.read("user", ev.getPlayer().getName());
		String[] oldLines = old.toArray(new String[old.size()]);
		List<String> newFileLine = new ArrayList<>();
		newFileLine.add("" + oldLines[0]);
		newFileLine.add("" + (System.currentTimeMillis() / 1000));
		Main.fileManager.write("user", ev.getPlayer().getName(), newFileLine);
	}
}
