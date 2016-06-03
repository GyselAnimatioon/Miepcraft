package ch.gyselanimatioon.miepcraft.listener;

import java.util.ArrayList;
import java.util.List;

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

			for (int i = 0; i < e.getInventory().getSize(); i++) {

				if (!(e.getInventory().getItem(i) == null)) {

					String material = e.getInventory().getItem(i).getType().toString();
					int amount = e.getInventory().getItem(i).getAmount();

					e.getPlayer().sendMessage(i + ": " + material + "," + amount);

					String entry = material + "," + amount;

					list.add(entry);
				}
			}
			Main.fileManager.write("inventorys/user", e.getPlayer().getName().toLowerCase(), list);

		}

	}
}
