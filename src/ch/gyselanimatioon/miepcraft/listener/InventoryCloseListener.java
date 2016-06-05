package ch.gyselanimatioon.miepcraft.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import ch.gyselanimatioon.miepcraft.Main;

public class InventoryCloseListener implements Listener {

	public Inventory inv2;

	@EventHandler
	public void InventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getTitle().contains("Backpack")) {
			List<String> list = new ArrayList<String>();
			for (int slot = 0; slot < e.getInventory().getSize(); slot++) {
				if (!(e.getInventory().getItem(slot) == null)) {
					String material = e.getInventory().getItem(slot).getType().toString();
					int amount = e.getInventory().getItem(slot).getAmount();
					String entchantment = e.getInventory().getItem(slot).getEnchantments().toString();

					Map<Enchantment, Integer> allEnchantments = e.getInventory().getItem(slot).getEnchantments();

					if (entchantment.length() > 4) {
						entchantment = "";
						for (Map.Entry<Enchantment, Integer> oneEnchantment : allEnchantments.entrySet()) {
							entchantment += oneEnchantment.getKey().getName() + "="
									+ oneEnchantment.getValue().toString() + ",";
						}
						entchantment = entchantment.substring(0, entchantment.length() - 1);
					}
					String entry = material + " . " + amount + " . " + slot + " . " + entchantment;
					list.add(entry);
				}
			}
			Main.fileManager.write("inventorys/user", e.getPlayer().getName().toLowerCase(), list);

		}

	}
}
