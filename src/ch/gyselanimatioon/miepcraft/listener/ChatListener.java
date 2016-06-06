package ch.gyselanimatioon.miepcraft.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
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

		List<String> list = Main.fileManager.read("log", "chatLog");
		list.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("log", "chatLog", list);

		List<String> list2 = Main.fileManager.read("log", "serverLog");
		list2.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("log", "serverLog", list2);

		List<String> list3 = Main.fileManager.read(".", "miepcraftGame");
		if (list3.get(0).length() > 3) {
			if (list3.get(0).equalsIgnoreCase("craft")) {
				String miep = event.getMessage().toLowerCase();
				if (miep.contains("craft")) {
					List<String> list4 = new ArrayList<>();
					list4.add("");
					Main.fileManager.write(".", "miepcraftGame", list4);
					Main.econ.depositPlayer(event.getPlayer(), 10);
					event.getPlayer().sendMessage("§8[§eKontostand§8] §610 Coins erhalten.");
				}
			}
		}

		int rand = (int) (Math.random() * 100);
		//Log.info(rand);
		if (rand < 3) {
			List<String> list4 = new ArrayList<>();
			list4.add("craft");
			Bukkit.broadcastMessage("§8[§eMiep§8]");
			Main.fileManager.write(".", "miepcraftGame", list4);
		}

	}
}
