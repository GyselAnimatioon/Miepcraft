package ch.gyselanimatioon.miepcraft;
//EDITED by jade99_

import ch.gyselanimatioon.miepcraft.commands.Backpack;
import ch.gyselanimatioon.miepcraft.commands.ClearChat;
import ch.gyselanimatioon.miepcraft.commands.CommandSpy;
import ch.gyselanimatioon.miepcraft.commands.Fixitem;
import ch.gyselanimatioon.miepcraft.commands.Fixxp;
import ch.gyselanimatioon.miepcraft.commands.Ping;
import ch.gyselanimatioon.miepcraft.commands.Ticket;
import ch.gyselanimatioon.miepcraft.listener.CommandListener;
import ch.gyselanimatioon.miepcraft.listener.InventoryCloseListener;
import ch.gyselanimatioon.miepcraft.listener.JoinListener;
import ch.gyselanimatioon.miepcraft.listener.QuitListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin implements Listener {
	// TODO Add language support;

	public static final Server SERVER = Bukkit.getServer();
	public static final Logger LOGGER = SERVER.getLogger();

	public static final char NUL = 0, SOH = 1, STX = 2, GS = 29, RS = 30, US = 31, SUB = 26;

	public static Economy econ = null;
	public static FileManager fileManager = null;
	public static PluginMain plugin = null;
	public static FileConfiguration config = null;

	public void onEnable() {
		if (!setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage("§8[§eMiepcraft§8] §6Economy deaktiviert.");
		}

		plugin = this;
		fileManager = new FileManager();
		config = getConfig();

		fileManager.createFile("log", "chatLog");
		fileManager.createFile("log", "commandLog");
		fileManager.createFile("log", "serverLog");
		fileManager.createFile("log", "supporting");
		fileManager.createFile(".", "tickets");
		fileManager.createFile("inventorys", "free");
		fileManager.createFile(".", "miepcraftGame");

		Bukkit.getServer().getPluginManager().registerEvents(new ch.gyselanimatioon.miepcraft.listener.DeathListener(),
				this);
		Bukkit.getServer().getPluginManager().registerEvents(new CommandListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new QuitListener(), this);

		getCommand("fixxp").setExecutor(new Fixxp());
		getCommand("fixitem").setExecutor(new Fixitem());
		getCommand("ticket").setExecutor(new Ticket());

		getCommand("backpack").setExecutor(new Backpack());
		getCommand("ping").setExecutor(new Ping());
		getCommand("teamchat").setExecutor(new ch.gyselanimatioon.miepcraft.commands.Teamchat());

		getCommand("commandspy").setExecutor(new CommandSpy());
		getCommand("clearchat").setExecutor(new ClearChat());

		getConfig().options().copyDefaults(true);
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
		econ = (Economy) rsp.getProvider();
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

	public static boolean isOnline(String username) {
		List<String> players = new ArrayList<String>();
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			players.add(player.getName());
		}
		if (players.contains(username)) {
			return true;
		} else {
			return false;
		}
	}
}