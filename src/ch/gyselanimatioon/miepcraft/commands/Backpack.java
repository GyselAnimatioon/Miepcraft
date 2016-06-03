package ch.gyselanimatioon.miepcraft.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ch.gyselanimatioon.miepcraft.Main;

public class Backpack implements CommandExecutor {

	public Backpack() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			if (player.hasPermission("miepcraft.commands.backpack")) {
				Main.fileManager.createFile("inventorys/user", player.getName().toLowerCase());
				Inventory inv = Bukkit.createInventory(null, 54, "Backpack");
				List<String> items = Main.fileManager.read("inventorys/user", player.getName().toLowerCase());
				for (String item : items) {
					String[] itemDataArray = item.split(" . ");

					Material material = Material.matchMaterial(itemDataArray[0]);
					int amount = Integer.parseInt(itemDataArray[1]);
					int slot = Integer.parseInt(itemDataArray[2]);
					String[] enchantments = itemDataArray[3].split(",");

					ItemStack thisItem = new ItemStack(material);
					thisItem.setAmount(amount);
					for (String enchantment : enchantments) {
						String[] oneEnchantment = enchantment.split("=");
						thisItem.addEnchantment(Enchantment.getByName(oneEnchantment[0]),
								Integer.parseInt(oneEnchantment[1]));
					}
					inv.setItem(slot, thisItem);
				}
				player.getPlayer().openInventory(inv);
			} else {
				sender.sendMessage("�8[�eBackpack�8] �eDu brauchst VIP um dies zu benutzen.");
			}
		} else {
			sender.sendMessage("Nicht m�glich in der Konsole.");
		}
		return true;
	}

}