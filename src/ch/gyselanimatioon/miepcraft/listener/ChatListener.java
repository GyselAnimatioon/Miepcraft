package ch.gyselanimatioon.miepcraft.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import ch.gyselanimatioon.miepcraft.Main;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {

	public ChatListener() {
		
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		
		List<String> list = Main.fileManager.read("log","chatLog");
		list.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("log","chatLog", list);
		
		List<String> list2 = Main.fileManager.read("log","serverLog");
		list2.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("log","serverLog", list2);
    }
}
