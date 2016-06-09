package ch.gyselanimatioon.miepcraft.features.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;

public class MiepcraftScoreboard {

	public MiepcraftScoreboard() {
		// REFRESH SCOREBOARD EVERY 5 SECONDS
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {

					if (!ScoreboardCreator.playerScoreboards.containsKey(p.getName())) {
						ScoreboardCreator.create(p, p.getName(), ChatColor.GOLD + "" + ChatColor.BOLD + "MIEPCRAFT.DE");
					}

					ScoreboardCreator.update(p);
					ScoreboardCreator.set(p);
				}
			}
		}, 0L, 100L);
	}

}
