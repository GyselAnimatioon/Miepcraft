package ch.gyselanimatioon.miepcraft;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import ch.gyselanimatioon.miepcraft.commands.Backpack;
import ch.gyselanimatioon.miepcraft.commands.CheckMode;
import ch.gyselanimatioon.miepcraft.commands.ClearChat;
import ch.gyselanimatioon.miepcraft.commands.CommandSpy;
import ch.gyselanimatioon.miepcraft.commands.EcoAll;
import ch.gyselanimatioon.miepcraft.commands.Fixitem;
import ch.gyselanimatioon.miepcraft.commands.Fixxp;
import ch.gyselanimatioon.miepcraft.commands.List;
import ch.gyselanimatioon.miepcraft.commands.Ping;
import ch.gyselanimatioon.miepcraft.commands.ReloadWarning;
import ch.gyselanimatioon.miepcraft.commands.Savepack;
import ch.gyselanimatioon.miepcraft.commands.Sup;
import ch.gyselanimatioon.miepcraft.commands.Teamchat;
import ch.gyselanimatioon.miepcraft.commands.Ticket;
import ch.gyselanimatioon.miepcraft.features.listener.CreatePlayerProfile;
import ch.gyselanimatioon.miepcraft.features.scoreboard.MiepcraftScoreboard;
import ch.gyselanimatioon.miepcraft.features.updater.Updater;
import ch.gyselanimatioon.miepcraft.listener.ChatListener;
import ch.gyselanimatioon.miepcraft.listener.CommandListener;
import ch.gyselanimatioon.miepcraft.listener.DeathListener;
import ch.gyselanimatioon.miepcraft.listener.InventoryClickListener;
import ch.gyselanimatioon.miepcraft.listener.InventoryCloseListener;
import ch.gyselanimatioon.miepcraft.listener.JoinListener;
import ch.gyselanimatioon.miepcraft.listener.QuitListener;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {

	public static Economy econ = null;
	public static FileManager fileManager = null;
	public static Main plugin = null;
	public static Title title = null;
	public static FileConfiguration config = null;
	public static Connection connection;
	private String host, database, username, password;
	private int port;

	@Override
	public void onEnable() {
		if (!setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage("§8[§eMiepcraft§8] §6Economy deaktiviert.");
		}
		
		plugin = this;
		fileManager = new FileManager();
		title = new Title();
		config = getConfig();
		
		host = getConfig().getString("host");
		port = getConfig().getInt("port");
		database = getConfig().getString("database");
		username = getConfig().getString("username");
		password = getConfig().getString("password");
		
		try {
			openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		fileManager.createFile("log", "chatLog");
		fileManager.createFile("log", "commandLog");
		fileManager.createFile("log", "serverLog");
		fileManager.createFile("log", "supporting");
		fileManager.createFile(".", "tickets");
		fileManager.createFile("inventorys", "free");
		fileManager.createFile(".", "miepcraftGame");

		Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CommandListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new QuitListener(), this);

		Bukkit.getServer().getPluginManager().registerEvents(new CreatePlayerProfile(), this);

		getCommand("fixxp").setExecutor(new Fixxp());
		getCommand("fixitem").setExecutor(new Fixitem());
		getCommand("ticket").setExecutor(new Ticket());
		getCommand("list").setExecutor(new List());
		getCommand("backpack").setExecutor(new Backpack());
		getCommand("savepack").setExecutor(new Savepack());
		getCommand("ping").setExecutor(new Ping());
		getCommand("teamchat").setExecutor(new Teamchat());
		getCommand("ecoall").setExecutor(new EcoAll());
		getCommand("reloadwarning").setExecutor(new ReloadWarning());
		getCommand("commandspy").setExecutor(new CommandSpy());
		getCommand("clearchat").setExecutor(new ClearChat());
		getCommand("checkmode").setExecutor(new CheckMode());
		getCommand("sup").setExecutor(new Sup());
		// getCommand("gofly").setExecutor(new GoFly());

		new Updater();
		new MiepcraftScoreboard();
		// new MiepcraftFly();
		
		this.getConfig().options().copyDefaults(true);
		saveConfig();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public static Yaml getPlayerYaml(Player player) {
		String path = plugin.getDataFolder().getAbsolutePath();
		return new Yaml(path + File.separator + "players" + File.separator + player.getName() + ".yml");

	}

	public static Yaml getOfflinePlayerYaml(String string) {
		String path = plugin.getDataFolder().getAbsolutePath();
		return new Yaml(path + File.separator + "players" + File.separator + string + ".yml");

	}
	
	public void openConnection() throws SQLException, ClassNotFoundException {
		if (connection != null && !connection.isClosed()) {
			return;
		}

		synchronized (this) {
			if (connection != null && !connection.isClosed()) {
				return;
			}
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);

		}
	}
}
