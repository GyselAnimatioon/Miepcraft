package ch.gyselanimatioon.miepcraft.commands;
//DONE some deprecation fixes;

import ch.gyselanimatioon.miepcraft.PluginMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class Fixxp implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if ((sender instanceof Player)) {
			player = (Player) sender;
		}

		if (player != null) {
			if (PluginMain.econ.getBalance(player) > 1500.0D) {
				ItemStack item = player.getInventory().getItemInMainHand();

				ItemStack itemCopy = new ItemStack(item.getType());
				itemCopy.setAmount(item.getAmount());
				itemCopy.addEnchantments(item.getEnchantments());
				itemCopy.setDurability(item.getDurability());

				if (player.getInventory().getItemInMainHand().getType() == Material.ENCHANTED_BOOK) {
					itemCopy.setItemMeta((EnchantmentStorageMeta) item.getItemMeta());
				}

				player.getInventory().removeItem(new ItemStack[] { item });
				player.getInventory().setItemInMainHand(itemCopy);

				PluginMain.econ.withdrawPlayer(player, 1500.0D);

				sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Item" + ChatColor.DARK_GRAY + "] "
						+ ChatColor.GOLD + "XP zurückgesetzt.");
				sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Konto" + ChatColor.DARK_GRAY + "] "
						+ ChatColor.GOLD + "1500 Coins abgezogen.");
			} else {
				sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Konto" + ChatColor.DARK_GRAY + "] "
						+ ChatColor.GOLD + "Zu wenig Coins.");
			}
		} else {
			sender.sendMessage("Dieser Befehl kann nur als Spieler ausgeführt werden.");
		}
		return true;
	}
}
