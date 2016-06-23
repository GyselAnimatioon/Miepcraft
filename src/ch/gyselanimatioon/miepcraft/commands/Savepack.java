package ch.gyselanimatioon.miepcraft.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ch.gyselanimatioon.miepcraft.Main;

public class Savepack implements CommandExecutor {

	public Savepack() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			Inventory inv = Bukkit.createInventory(null, 18, "Beta Speicher");
			Main.fileManager.createFile("save/user", player.getName().toLowerCase());
			List<String> items = Main.fileManager.read("save/user", player.getName().toLowerCase());
			for (String item : items) {
				String[] itemDataArray = item.split(" . ");

				Material material = Material.matchMaterial(itemDataArray[0]);
				int amount = Integer.parseInt(itemDataArray[1]);
				int slot = Integer.parseInt(itemDataArray[2]);
				short dur = Short.parseShort(itemDataArray[3]);
				String ench = itemDataArray[4];
				String[] enchantments = null;
				if (ench.length() > 5) {
					enchantments = itemDataArray[4].split(",");
				}

				ItemStack thisItem = new ItemStack(material);
				thisItem.setAmount(amount);
				thisItem.setDurability(dur);
				if (ench.length() > 5) {
					for (String enchantment : enchantments) {
						Log.info(enchantment);
						String[] oneEnchantment = enchantment.split("=");
						thisItem.addUnsafeEnchantment(Enchantment.getByName(oneEnchantment[0]), Integer.parseInt(oneEnchantment[1]));
					}
				}
				
				inv.setItem(slot, thisItem);
			}
			player.getPlayer().openInventory(inv);
		} else {
			sender.sendMessage("Nicht möglich in der Konsole.");
		}
		return true;
	}

}