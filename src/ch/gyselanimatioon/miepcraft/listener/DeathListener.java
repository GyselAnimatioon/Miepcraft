package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DeathListener implements Listener {


	public DeathListener() {
	}

	@EventHandler
	public void Playerdeath(PlayerDeathEvent e) {

		Entity killer = e.getEntity().getKiller();
		Entity opfer = e.getEntity();
		String oMsg = "§8[§eTipp§8]§6 Deine Items können nur von dir aufgehoben werden.";

		Bukkit.getConsoleSender().sendMessage("§8[§eKill§8]§6 " + e.getDeathMessage());
		
		if (opfer instanceof Player && killer instanceof Player) {
			opfer.sendMessage(oMsg);
			sendCoord(opfer);
			killer.sendMessage("§8[§eKill§8]§6 Nur " + opfer.getName() + " kann seine Items aufheben.");
			
			// Kopf Drop Chance
			double ran = Math.random() * 100;
			int random = (int) ran;
			if(random <= 6) {
				Player playerKiller = (Player) killer;
				
				@SuppressWarnings("deprecation")
				ItemStack skull = new ItemStack(397, 1, (short) 3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(opfer.getName());
				skull.setItemMeta(meta);
				playerKiller.getInventory().addItem(skull);
				Bukkit.broadcastMessage("§8[§eKill§8]§6 " + opfer.getName() + " wurde von " + killer.getName() + " geköpft.");
			} else {
				Bukkit.broadcastMessage("§8[§eKill§8]§6 " + opfer.getName() + " wurde von " + killer.getName() + " getötet.");
			}

			
		} else if (opfer instanceof Player) {
			opfer.sendMessage(oMsg);
			sendCoord(opfer);
		}
		
	}
	
	public void sendCoord(Entity e) {
		int oX = e.getLocation().getBlockX();
		int oY = e.getLocation().getBlockY();
		int oZ = e.getLocation().getBlockZ();
		
		e.sendMessage("§8[§eTipp§8]§6 Todes Koordinaten:");
		e.sendMessage("§8[§eTipp§8]§6 X: " + oX);
		e.sendMessage("§8[§eTipp§8]§6 Y: " + oY);
		e.sendMessage("§8[§eTipp§8]§6 Z: " + oZ);
		
	}

}
