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
			Inventory inv;
			Main.fileManager.createFile("inventorys/user", player.getName().toLowerCase());
			if (player.hasPermission("miepcraft.commands.backpack.big")) {
				inv = Bukkit.createInventory(null, 90, "10x9 Backpack");
			} else if (player.hasPermission("miepcraft.commands.backpack.middle")) {
				inv = Bukkit.createInventory(null, 36, "4x9 Backpack");
			} else {
				inv = Bukkit.createInventory(null, 18, "2x9 Backpack");
			}
			List<String> items = Main.fileManager.read("inventorys/user", player.getName().toLowerCase());
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
						String[] oneEnchantment = enchantment.split("=");
						thisItem.addEnchantment(Enchantment.getByName(oneEnchantment[0]), Integer.parseInt(oneEnchantment[1]));
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