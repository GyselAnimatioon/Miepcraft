package ch.gyselanimatioon.miepcraft.features.updater;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.nossr50.api.ExperienceAPI;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;
import ch.gyselanimatioon.miepcraft.features.scoreboard.MiepcraftScoreboard;
import me.edge209.OnTime.OnTimeAPI;
import me.edge209.OnTime.OnTimeAPI.data;

public class Updater {

	public Updater() {
		// EVERY 5 SECONDS
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					
					/**
					 * YML Update
					 */
					Yaml yaml = Main.getPlayerYaml(p);
					yaml.set("Powerlevel", ExperienceAPI.getPowerLevel(p));
					yaml.set("Coins", Main.econ.getBalance(p.getName()));
					yaml.set("Spielzeit", (int) (OnTimeAPI.getPlayerTimeData(p.getName(), data.TOTALPLAY) / 1000));
					//TODO Claimblöcke
					yaml.save();
					
					/**
					 * Scoreboard Update
					 */
					if (!MiepcraftScoreboard.playerScoreboards.containsKey(p.getName())) {
						MiepcraftScoreboard.create(p, p.getName(), ChatColor.GOLD + "" + ChatColor.BOLD + "MIEPCRAFT.DE");
					}

					MiepcraftScoreboard.update(p);
					MiepcraftScoreboard.set(p);
					
					/**
					 * Random Miep
					 */
					int rand = (int) (Math.random() * 1000);
					if (rand < 1) {
						List<String> list4 = new ArrayList<>();
						list4.add("craft");
						Bukkit.broadcastMessage("§8[§eMiep§8]");
						Main.fileManager.write(".", "miepcraftGame", list4);
					}
					
					/**
					 * Fly Update
					 */
//					if(p.isFlying() && yaml.getBoolean("BoughtFly")) {
//						p.sendMessage("§8[§eFly§8] §6-100 Coins.");
//					}
				}
			}
		}, 0L, 100L);
	}

}
