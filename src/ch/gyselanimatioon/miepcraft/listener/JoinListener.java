package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class JoinListener implements Listener {

	public JoinListener() {
	}

	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent e) {
		CraftPlayer p = (CraftPlayer) e.getPlayer();
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + "test" + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}
}
