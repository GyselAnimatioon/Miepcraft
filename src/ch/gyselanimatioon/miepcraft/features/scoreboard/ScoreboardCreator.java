package ch.gyselanimatioon.miepcraft.features.scoreboard;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.nossr50.api.ExperienceAPI;

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class ScoreboardCreator {

	public static HashMap<String, Scoreboard> playerScoreboards = new HashMap<String, Scoreboard>();

	public static HashMap<String, Score> coinscore = new HashMap<String, Score>();
	public static HashMap<String, Score> powerscore = new HashMap<String, Score>();

	@SuppressWarnings("deprecation")
	public static void create(Player p, String BoardName, String BoardDisplayName) {
		String pname = p.getName().toLowerCase();
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();

		Objective objective = board.registerNewObjective(BoardName, "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(BoardDisplayName);

		Score coin = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Coins:"));
		Score power = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Powerlevel:"));

		coin.setScore(0);
		power.setScore(0);
		
		coinscore.put(pname, coin);
		powerscore.put(pname, power);
		playerScoreboards.put(pname, board);
	}

	@SuppressWarnings("deprecation")
	public static void update(Player p) {
		Yaml yaml = Main.getPlayerYaml(p);
		String pname = p.getName().toLowerCase();

		Score coin = coinscore.get(pname);
		Score power = powerscore.get(pname);
		
		coin.setScore((int) Main.econ.getBalance(p.getName()));
		power.setScore(ExperienceAPI.getPowerLevel(p));
	}

	public static void set(Player p) {
		String pname = p.getName().toLowerCase();
		p.setScoreboard(playerScoreboards.get(pname));
	}
}
