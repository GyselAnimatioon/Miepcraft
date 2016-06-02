package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void InventoryClick(InventoryClickEvent e) {
		//Player p = (Player) e.getWhoClicked();

		if (e.getInventory().getTitle().contains("Online Spieler")) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null) {
				return;
			}
		}
	}

}
