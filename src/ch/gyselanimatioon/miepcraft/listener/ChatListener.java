package ch.gyselanimatioon.miepcraft.listener;

import java.util.ArrayList;
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
		List<String> list3 = Main.fileManager.read(".", "miepcraftGame");
		if (list3.get(0).length() > 3) { // OutOfArray? " " zum miepcraftGame.txt hinzufügen
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
	}
}
