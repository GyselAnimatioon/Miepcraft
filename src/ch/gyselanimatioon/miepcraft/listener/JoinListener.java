package ch.gyselanimatioon.miepcraft.listener;

import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ch.gyselanimatioon.miepcraft.Main;

public class JoinListener implements Listener {

	public JoinListener() {
	}

	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event) {
		try {
			Statement statement = Main.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM miepcraft_newbies WHERE uuid = '" + event.getPlayer().getUniqueId() + "';");
			if(result.next()) {
				boolean locked = result.getBoolean("locked");
				if (locked) {
					event.getPlayer().sendMessage("§r§7" + Main.config.getString("unlock_stages." + result.getString("unlock_stage")));
				}
			} else {
				statement.execute("INSERT INTO miepcraft_newbies (uuid) VALUES ('" + event.getPlayer().getUniqueId() + "');");
				event.getPlayer().sendMessage("§r§7" + Main.config.getString("unlock_stages.0"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (event.getPlayer().hasPermission("miepcraft.listener.join.silent")) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (players.hasPermission("miepcraft.listener.join.spy")) {
					// JoinSpy wird nich der Person angezeigt die gejoint ist.
					if (!players.getName().toLowerCase().equalsIgnoreCase(event.getPlayer().getName().toLowerCase())) {
						players.sendMessage("§8[§eJoinSpy§8]§6 " + event.getPlayer().getName());
					}
				}
			}
		} else {
			Bukkit.broadcastMessage("§8[§eBeigetreten§8]§6 " + event.getPlayer().getName());
		}
	}

}
