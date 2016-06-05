package ch.gyselanimatioon.miepcraft.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import ch.gyselanimatioon.miepcraft.Main;

public class CommandListener implements Listener {

	public CommandListener() {

	}

	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (players.hasPermission("miepcraft.listener.command.spy")) {
				players.sendMessage("§8[§eCommandSpy§8] §7" + event.getPlayer().getName() + " §f" + event.getMessage());
			}
		}

		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

		List<String> list = Main.fileManager.read("log","commandLog");
		list.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("log","commandLog", list);

		List<String> list2 = Main.fileManager.read("log","serverLog");
		list2.add("[" + format.format(now) + "] " + event.getPlayer().getName() + ": " + event.getMessage());
		Main.fileManager.write("log","serverLog", list2);
		
		int rand = (int) (Math.random() * 100);
		Log.info(rand);
		if(rand < 3) {
			List<String> list4 = new ArrayList<>();
			list4.add("craft");
			Bukkit.broadcastMessage("§8[§eMiep§8]");
			Main.fileManager.write(".","miepcraftGame", list4);
		}
		
	}
}
