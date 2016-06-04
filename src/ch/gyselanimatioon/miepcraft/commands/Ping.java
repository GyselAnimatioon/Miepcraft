package ch.gyselanimatioon.miepcraft.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ping implements CommandExecutor {

	public Ping() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player != null) {
			if(args.length == 0) {
			sender.sendMessage("§8[§ePing§8]§6 " + getPlayerPing(player));
			} else if(args[0].equalsIgnoreCase("all")){
				if(player.hasPermission("miepcraft.commands.ping.all")) {
					for(Player onePlayer : Bukkit.getOnlinePlayers()) {
						player.sendMessage("§8[§ePing§8]§6 " + onePlayer.getName() + ": " + getPlayerPing(onePlayer));
					}
				} else {
					player.sendMessage("§8[§ePing§8]§6 Du hast keine Rechte auf diesen Befehl.");
				}
			} else if (Bukkit.getPlayer(args[0]).isOnline()) {
				if(player.hasPermission("miepcraft.commands.ping.other")) {
						player.sendMessage("§8[§ePing§8]§6 " + args[0] + ": " + getPlayerPing(Bukkit.getPlayer(args[0])));	
				}
			} else {
				player.sendMessage("§8[§ePing§8]§6 Du hast keine Rechte auf diesen Befehl.");
			}
		} else {
			sender.sendMessage("Nicht möglich in der Konsole.");
		}
		return true;
	}

	public static int getPlayerPing(Player player) {
		try {
			Class<?> craftPlayer = Class
					.forName("org.bukkit.craftbukkit." + getServerVersion() + ".entity.CraftPlayer");
			Object converted = craftPlayer.cast(player);
			Method handle = converted.getClass().getMethod("getHandle", new Class[0]);
			Object entityPlayer = handle.invoke(converted, new Object[0]);
			Field pingField = entityPlayer.getClass().getField("ping");
			return pingField.getInt(entityPlayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public static String getServerVersion() {
		Pattern brand = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");
		String pkg = Bukkit.getServer().getClass().getPackage().getName();
		String version = pkg.substring(pkg.lastIndexOf('.') + 1);
		if (!brand.matcher(version).matches()) {
			version = "";
		}
		return version;
	}

}