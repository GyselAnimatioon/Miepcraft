package ch.gyselanimatioon.miepcraft.features.fly;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class MiepcraftFly {
	public MiepcraftFly() {
		// EVERY 0.1 SECONDS
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					Yaml yaml = Main.getPlayerYaml(p);
					
					if(p.isFlying() && yaml.getBoolean("BoughtFly")) {
						Main.econ.withdrawPlayer(p.getName(), 2);
					}
					
					yaml.save();
				}
			}
		}, 0L, 2L);
	}
}
