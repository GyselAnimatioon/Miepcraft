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

import ch.gyselanimatioon.miepcraft.Main;
import ch.gyselanimatioon.miepcraft.Yaml;

public class MiepcraftScoreboard {

	public static HashMap<String, Scoreboard> playerScoreboards = new HashMap<String, Scoreboard>();

	public static HashMap<String, Score> coinscore = new HashMap<String, Score>();
	public static HashMap<String, Score> powerscore = new HashMap<String, Score>();
	public static HashMap<String, Score> ontimescore = new HashMap<String, Score>();
	public static Score online;

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
		Score ontime = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Spielzeit:"));
		online = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY + "Online:"));

		coin.setScore(0);
		power.setScore(0);
		ontime.setScore(0);
		online.setScore(0);
		
		coinscore.put(pname, coin);
		powerscore.put(pname, power);
		ontimescore.put(pname, ontime);
		playerScoreboards.put(pname, board);
	}

	public static void update(Player p) {
		Yaml yaml = Main.getPlayerYaml(p);
		String pname = p.getName().toLowerCase();

		Score coin = coinscore.get(pname);
		Score power = powerscore.get(pname);
		Score ontime = ontimescore.get(pname);
		
		coin.setScore(yaml.getInteger("Coins"));
		power.setScore(yaml.getInteger("Powerlevel"));
		ontime.setScore((int) (yaml.getInteger("Spielzeit") / 3600));
		online.setScore(Bukkit.getOnlinePlayers().size());
	}

	public static void set(Player p) {
		String pname = p.getName().toLowerCase();
		p.setScoreboard(playerScoreboards.get(pname));
	}
}
