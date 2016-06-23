package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

	public Inventory inv2;

	@EventHandler
	public void InventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getTitle().contains("Online Spieler")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
		}
	}
}
