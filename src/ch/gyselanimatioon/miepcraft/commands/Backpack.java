package ch.gyselanimatioon.miepcraft.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

			Main.fileManager.createFile("inventorys/user", player.getName().toLowerCase());
			
			Inventory inv = Bukkit.createInventory(null, 54, "Backpack");

			List<String> items = Main.fileManager.read("inventorys/user", player.getName().toLowerCase());

			int i = 0;
			for (String item : items) {
				String[] itemDataArray = item.split(",");

				Material material = Material.matchMaterial(itemDataArray[0]);
				int amount = Integer.parseInt(itemDataArray[1]);

				ItemStack thisItem = new ItemStack(material);
				thisItem.setAmount(amount);

				inv.setItem(i, thisItem);
				i++;
			}

			player.getPlayer().openInventory(inv);
		} else {
			sender.sendMessage("Nicht möglich in der Konsole.");
		}
		return true;
	}

}