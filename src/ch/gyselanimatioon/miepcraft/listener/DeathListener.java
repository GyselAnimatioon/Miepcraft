package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import static ch.gyselanimatioon.miepcraft.PluginMain.LOGGER;

public class DeathListener implements Listener {
	@EventHandler
	public void Playerdeath(PlayerDeathEvent e) {
		Entity killer = e.getEntity().getKiller();
		Entity opfer = e.getEntity();

		Bukkit.getConsoleSender().sendMessage("§8[§eKill§8]§6 " + e.getDeathMessage());

		if (((opfer instanceof Player)) && ((killer instanceof Player))) {
			sendCoord(opfer);

			double ran = Math.random() * 100.0D;
			int random = (int) ran;
			if (random <= 6) {
				Player playerKiller = (Player) killer;

				@SuppressWarnings("deprecation")
				ItemStack skull = new ItemStack(397, 1, (short) 3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(opfer.getName());
				skull.setItemMeta(meta);
				playerKiller.getInventory().addItem(new ItemStack[] { skull });
				Bukkit.broadcastMessage("§a§o" + opfer.getName() + " wurde von " + killer.getName() + " gekÃ¶pft");
			} else {
				LOGGER.info(ChatColor.RED + killer.getName() + " killt " + opfer.getName());
			}

		} else if ((opfer instanceof Player)) {
			sendCoord(opfer);
		}
	}

	public void sendCoord(Entity e) {
		int oX = e.getLocation().getBlockX();
		int oY = e.getLocation().getBlockY();
		int oZ = e.getLocation().getBlockZ();

		e.sendMessage("§c§oTodespunkt: x" + oX + " y" + oY + " z" + oZ);
	}
}