package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.gyselanimatioon.miepcraft.Main;

public class EcoAll implements CommandExecutor {

	public EcoAll() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (player != null) {
			if (player.hasPermission("miepcraft.commands.ecoall")) {
				int zahl = Integer.parseInt(args[0]);

				for (Player players : Bukkit.getOnlinePlayers()) {
					Main.econ.depositPlayer(players, zahl);
					players.sendMessage("§8[§eKontostand§8] §6" + args[0] + " Coins bekommen.");
				}
				player.sendMessage("§8[§eEcoAll§8] §6" + args[0] + " Coins an alle gegeben.");
			} else {
				player.sendMessage("§8[§eEcoAll§8] §6Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			int zahl = Integer.parseInt(args[0]);

			for (Player players : Bukkit.getOnlinePlayers()) {
				Main.econ.depositPlayer(players, zahl);
			}
		}
		return true;
	}

}