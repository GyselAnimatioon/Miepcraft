/*    */ package ch.gyselanimatioon.miepcraft.commands;
/*    */ 
/*    */ import ch.gyselanimatioon.miepcraft.PluginMain;
/*    */ import ch.gyselanimatioon.miepcraft.Yaml;
		
import static ch.gyselanimatioon.miepcraft.PluginMain.LOGGER;

/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSpy
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 20 */     Player player = null;
/* 21 */     if ((sender instanceof Player)) {
/* 22 */       player = (Player)sender;
/*    */     }
/*    */     
/* 25 */     if (player != null) {
/* 26 */       if (player.hasPermission("miepcraft.listener.command.spy")) {
/* 27 */         Yaml yaml = PluginMain.getPlayerYaml(player);
/* 28 */         yaml.set("CommandSpy Enabled", Boolean.valueOf(!yaml.getBoolean("CommandSpy Enabled")));
/* 29 */         yaml.save();
/* 30 */         if (yaml.getBoolean("CommandSpy Enabled")) {
/* 31 */           player.sendMessage("§8[§4CommandSpy§8] §aCommandSpy ist nun ein.");
/* 32 */         } else if (!yaml.getBoolean("CommandSpy Enabled")) {
/* 33 */           player.sendMessage("§8[§4CommandSpy§8] §cCommandSpy ist nun aus.");
/*    */         }
/*    */       } else {
/* 36 */         player.sendMessage("§8[§eCommandSpy§8] §6Du hast keine Rechte auf diesen Befehl.");
/*    */       }
/*    */     } else {
/* 39 */       LOGGER.info("[CommandSpy.java] Not supportet." );
/*    */     }
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ }