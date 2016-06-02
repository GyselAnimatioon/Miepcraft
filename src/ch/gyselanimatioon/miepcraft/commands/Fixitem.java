package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;

public class Fixitem implements CommandExecutor {

	public Fixitem() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			if (Main.econ.getBalance(player) > 250) {
				player.getItemInHand().setDurability((short) 0);
				Main.econ.withdrawPlayer(player, 250);

				sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Item" + ChatColor.DARK_GRAY + "] "
						+ ChatColor.GOLD + "Item repariert.");
				sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Konto" + ChatColor.DARK_GRAY + "] "
						+ ChatColor.GOLD + "250 Coins abgezogen.");
			} else {
				sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Konto" + ChatColor.DARK_GRAY + "] "
						+ ChatColor.GOLD + "Zu wenig Coins.");
			}
		} else {
			sender.sendMessage(
					ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Miepcraft Fixitem Command" + ChatColor.DARK_GRAY
							+ "] " + ChatColor.GOLD + "Dieser Befehl kann nur als Spieler ausgeführt werden.");
		}
		return true;
	}

}