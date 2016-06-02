package ch.gyselanimatioon.miepcraft.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import ch.gyselanimatioon.miepcraft.Main;

public class CommandListener implements Listener {

	public CommandListener() {
		
	}
	
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		
		List<String> list = Main.fileManager.read("commandLog");
		list.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("commandLog", list);
		
		List<String> list2 = Main.fileManager.read("serverLog");
		list2.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("serverLog", list2);
    }
}
