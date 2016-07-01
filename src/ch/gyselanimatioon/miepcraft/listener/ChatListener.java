package ch.gyselanimatioon.miepcraft.listener;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
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
		Log.info(ChatColor.AQUA + event.getPlayer().getName() + ": " + event.getMessage());
		try {
			Statement statement = Main.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM miepcraft_newbies WHERE uuid = '" + event.getPlayer().getUniqueId() + "';");
			while (result.next()) {
				boolean locked = result.getBoolean("locked");
				if (locked) {
					event.setCancelled(true);
					if (!event.getMessage().toLowerCase().contains("ja") && !event.getMessage().toLowerCase().contains("nein")) {
						event.getPlayer().sendMessage("§r§7" + Main.config.getString("unlock_stages." + result.getString("unlock_stage")));
					} else {
						int stage = result.getInt("unlock_stage");
						if (event.getMessage().equalsIgnoreCase(Main.config.getString("unlock_stages_answer." + stage))) {
							statement.execute("UPDATE miepcraft_newbies SET unlock_stage = unlock_stage + 1;");
							event.getPlayer().sendMessage("§2Deine Antwort war Korrekt.");
							if (stage < 3) {
								event.getPlayer().sendMessage("§r§7" + Main.config.getString("unlock_stages." + (stage + 1)));
							} else {
								event.getPlayer().sendMessage("§6Willkommen auf dem Miepcraft Freebuild Server!");
								statement.execute("UPDATE miepcraft_newbies SET locked = 0;");
							}
						} else {
							event.getPlayer().sendMessage("§cDeine Antwort war Falsch.");
							event.getPlayer().sendMessage("§r§7" + Main.config.getString("unlock_stages." + stage));
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Some Errors...");
		}

		List<String> list3 = Main.fileManager.read(".", "miepcraftGame");
		if (list3.get(0).length() > 3) { // OutOfArray? " " zum
											// miepcraftGame.txt hinzufügen
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
