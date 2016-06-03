package ch.gyselanimatioon.miepcraft.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;

public class Ticket implements CommandExecutor {

	public Ticket() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("add")) {
					if (args.length > 1) {
						Date now = new Date();
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						List<String> list = Main.fileManager.read(".","tickets");
						String newLine = player.getName() + " (" + format.format(now) + "): ";
						for (int i = 1; i < args.length; i++) {
							newLine = newLine + args[i] + " ";
						}
						list.add(newLine);
						Main.fileManager.write(".","tickets", list);
						player.sendMessage("§8[§eTicket§8] §6gespeichert.");
					} else {
						player.sendMessage("§8[§eTicket§8] §6/ticket add <Ticket>");
					}
				} else if (args[0].equalsIgnoreCase("list")) {
					if (!sender.hasPermission("miepcraft.commands.ticket.list")) {
						sender.sendMessage("§8[§eTicket§8] §6Du hast keine Rechte auf diesen Befehl.");
					} else {
						List<String> list = Main.fileManager.read(".","tickets");
						for (int i = 0; i < list.size(); i++) {
							sender.sendMessage("§8[§eTicket§8] §6" + list.get(i));
						}
					}
				} else {
						player.sendMessage("§8[§eTicket§8] §6/ticket add <Ticket>");
						player.sendMessage("§8[§eTicket§8] §6/ticket list");

				}
			} else {
					player.sendMessage("§8[§eTicket§8] §6/ticket add <Ticket>");
					player.sendMessage("§8[§eTicket§8] §6/ticket list");
			
			}
		} else {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("add")) {
					if (args.length > 1) {
						Date now = new Date();
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						List<String> list = Main.fileManager.read(".","tickets");
						String newLine = "Console (" + format.format(now) + "): ";
						for (int i = 1; i < args.length; i++) {
							newLine = newLine + args[i] + " ";
						}
						list.add(newLine);
						Main.fileManager.write(".","tickets", list);
						Bukkit.getConsoleSender().sendMessage("§8[§eTicket§8] §6gespeichert.");
					} else {
						Bukkit.getConsoleSender().sendMessage("§8[§eTicket§8] §6/ticket add <Ticket>");
					}
				} else if (args[0].equalsIgnoreCase("list")) {
					List<String> list = Main.fileManager.read(".","tickets");
					for (int i = 0; i < list.size(); i++) {
						Bukkit.getConsoleSender().sendMessage("§8[§eTicket§8] §6" + list.get(i));
					}
				} else {
						Bukkit.getConsoleSender().sendMessage("§8[§eTicket§8] §6/ticket add <Ticket>");
						Bukkit.getConsoleSender().sendMessage("§8[§eTicket§8] §6/ticket list");
				}
			} else {
					Bukkit.getConsoleSender().sendMessage("§8[§eTicket§8] §6/ticket add <Ticket>");
					Bukkit.getConsoleSender().sendMessage("§8[§eTicket§8] §6/ticket list");
			}
		}
		return true;
	}

}