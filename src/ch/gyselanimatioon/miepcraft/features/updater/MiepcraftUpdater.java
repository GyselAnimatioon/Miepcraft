package ch.gyselanimatioon.miepcraft.features.updater;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.nossr50.api.ExperienceAPI;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class MiepcraftUpdater {

	public MiepcraftUpdater() {
		// EVERY 5 SECONDS
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					Yaml yaml = Main.getPlayerYaml(p);
					
					yaml.set("Powerlevel", ExperienceAPI.getPowerLevel(p));
					yaml.set("Coins", Main.econ.getBalance(p.getName()));
					
					yaml.save();
				}
			}
		}, 0L, 100L);
	}

}
