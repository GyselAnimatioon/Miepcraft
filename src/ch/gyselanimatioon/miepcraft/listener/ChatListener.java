package ch.gyselanimatioon.miepcraft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {


	public ChatListener() {
		
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		
		
		event.getPlayer().sendMessage("chat");
		
    }
	
}
