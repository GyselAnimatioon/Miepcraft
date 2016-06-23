package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class List implements CommandExecutor {

	
	
	public List() {

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			Inventory inv;
			inv = Bukkit.createInventory(null, 36, "Online Spieler");
		    int i = 0;
			for (Player players : Bukkit.getOnlinePlayers()) {
				ItemStack skull = new ItemStack(397, 1, (short) 3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(players.getName());
				skull.setItemMeta(meta);
				inv.setItem(i, skull);
				i++;
			}
		    player.openInventory(inv);
			
		} else {
			String list = "Online: " + Bukkit.getOnlinePlayers().size() + ". ";
			for (Player players : Bukkit.getOnlinePlayers()) {
				list += players.getName() + ", ";
			}
			sender.sendMessage(list);
		}
		return true;
	}

}