package ch.gyselanimatioon.miepcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Tp implements CommandExecutor {

	public Tp() {

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			if (!sender.hasPermission("miepcraft.commands.tp")) {
				sender.sendMessage("§8[§eTeleport§8] §6Du hast keine Rechte auf diesen Befehl.");
			} else {
				if(args.length > 0) {
				Inventory inv = Bukkit.createInventory(null, 9, "Bist du gerade am Supporten?");

				ItemStack skull = new ItemStack(397, 1, (short) 3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(args[0]);
				skull.setItemMeta(meta);

				inv.setItem(0, new ItemStack(Material.EMERALD_BLOCK));
				inv.setItem(1, new ItemStack(Material.EMERALD_BLOCK));
				inv.setItem(2, new ItemStack(Material.EMERALD_BLOCK));
				inv.setItem(3, new ItemStack(Material.EMERALD_BLOCK));
				inv.setItem(4, skull);
				inv.setItem(5, new ItemStack(Material.REDSTONE_BLOCK));
				inv.setItem(6, new ItemStack(Material.REDSTONE_BLOCK));
				inv.setItem(7, new ItemStack(Material.REDSTONE_BLOCK));
				inv.setItem(8, new ItemStack(Material.REDSTONE_BLOCK));

				player.getPlayer().openInventory(inv);
				} else {
					sender.sendMessage("§8[§eTeleport§8] §6/tp [spieler]");
				}
			}
		} else {
			sender.sendMessage("Nicht möglich in der Konsole.");
		}
		return true;
	}

}