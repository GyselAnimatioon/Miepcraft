package ch.gyselanimatioon.miepcraft.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import ch.gyselanimatioon.miepcraft.Main;

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
		if (e.getInventory().getTitle().contains("Bist du gerade am Supporten?")) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null) {
				return;
			} else if (e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
				Player player = (Player) e.getWhoClicked();

				SkullMeta skullMeta = (SkullMeta) e.getClickedInventory().getItem(4).getItemMeta();
				Player tpTo = Bukkit.getPlayer(skullMeta.getOwner());

				player.teleport(new Location(tpTo.getWorld(), tpTo.getLocation().getX(), tpTo.getLocation().getY(),
						tpTo.getLocation().getZ()));
			} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
				Player player = (Player) e.getWhoClicked();

				player.closeInventory();
			}
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

			List<String> list = Main.fileManager.read("teleportLog");
			list.add("[" + format.format(now) + "] " + e.getWhoClicked() + ": /tp " + Bukkit.getPlayer(((SkullMeta) e.getClickedInventory().getItem(4).getItemMeta()).getOwner()).getName() + ". Auswahl: " + e.getCurrentItem().getType().toString());
			Main.fileManager.write("teleportLog", list);

		}

	}
}
