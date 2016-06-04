package ch.gyselanimatioon.miepcraft;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import ch.gyselanimatioon.miepcraft.commands.Backpack;
import ch.gyselanimatioon.miepcraft.commands.Fixitem;
import ch.gyselanimatioon.miepcraft.commands.Fixxp;
import ch.gyselanimatioon.miepcraft.commands.List;
import ch.gyselanimatioon.miepcraft.commands.Ping;
import ch.gyselanimatioon.miepcraft.commands.Ticket;
import ch.gyselanimatioon.miepcraft.commands.Tp;
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

	@Override
	public void onEnable() {
		if (!setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage("§8[§eMiepcraft§8] §6Economy deaktiviert.");
		}

		plugin = this;
		fileManager = new FileManager();

		fileManager.createFile("log","chatLog");
		fileManager.createFile("log","commandLog");
		fileManager.createFile("log","serverLog");
		fileManager.createFile("log","teleportLog");
		fileManager.createFile(".","tickets");
		fileManager.createFile("inventorys","free");

		Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CommandListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new QuitListener(), this);

		getCommand("fixxp").setExecutor(new Fixxp());
		getCommand("fixitem").setExecutor(new Fixitem());
		getCommand("ticket").setExecutor(new Ticket());
		getCommand("list").setExecutor(new List());
		getCommand("tp").setExecutor(new Tp());
		getCommand("backpack").setExecutor(new Backpack());
		getCommand("ping").setExecutor(new Ping());
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

}

