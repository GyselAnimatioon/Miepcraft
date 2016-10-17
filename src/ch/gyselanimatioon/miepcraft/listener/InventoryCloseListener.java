package ch.gyselanimatioon.miepcraft.listener;
//EDITED by jade99_

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import ch.gyselanimatioon.miepcraft.PluginMain;
import ch.gyselanimatioon.miepcraft.Yaml;

import static ch.gyselanimatioon.miepcraft.PluginMain.*;

public class InventoryCloseListener implements org.bukkit.event.Listener {
	public Inventory inv2;
	//TODO Shields/Banners, backpack size in header, (Upcoming 1.11, Shulkerboxes)

	private static final short VERSION = 0x0202;

	@org.bukkit.event.EventHandler
	public void InventoryClose(InventoryCloseEvent e) {
		Yaml yaml = PluginMain.getPlayerYaml((Player) e.getPlayer());
		boolean inBackpack = yaml.getBoolean("inBackpack");
		boolean isInspecting = yaml.getBoolean("isInspecting");

		if (e.getInventory().getTitle().contains("Backpack") && (inBackpack || isInspecting)) {
			List<String> list = new ArrayList<String>();

			char minorVersion = (char) (VERSION & 0x00FF);
			char majorVersion = (char) (VERSION >> 8);

			String uuid$ = e.getPlayer().getUniqueId().toString();

			String header = Character.toString(SOH) + Character.toString(majorVersion)
					+ Character.toString(minorVersion) + uuid$ + Character.toString(STX);
			list.add(header);
			for (int slot = 0; slot < e.getInventory().getSize(); slot++) {
				if (e.getInventory().getItem(slot) != null) {
					ItemStack item = e.getInventory().getItem(slot);
					ItemMeta itemMeta = item.getItemMeta();

					/**
					 * count = item count;
					 * damage = metadata of item;
					 * id = itemID
					 * name = Custom Item-name (No character restrictions);
					 */
					int count = item.getAmount();
					short damage = item.getDurability();
					String id = item.getType().toString();
					String name = Character.toString(NUL);
					
					if (itemMeta.hasDisplayName()) {
						name = itemMeta.getDisplayName();
						if (name.contains("§")) {
							name = name.substring(2, name.length());
						}
					}
					
					String norm = "{" + id + US + count + US + damage + US + slot + US + name + "}";
					
					/*
					 *  potion = Vanilla potion;
					 *  potFX = Custom potions;
					 *  ench = enchanted Weapons/Armor/Tools (/!\ Only for enchantments that are possible in Survival-Vanilla Minecraft /!\);
					 *  bookEnch = enchanted Books
					 *  bookWrite = written Books (Signed and Unsigned)
					 *  skullOwner = Custom playerhead;
					 */
					String potion = "{}";
					String potFX = "{}";
					String ench = "{}";									
					String bookEnch = "{}";
					String bookWrite = "{}";
					String skullOwner = Character.toString(NUL);

					if (itemMeta.hasEnchants()) {
						ench = "";
						Map<Enchantment, Integer> enchs = itemMeta.getEnchants();
						for (Map.Entry<Enchantment, Integer> oneEnch : enchs.entrySet()) {
							ench += ((Enchantment) oneEnch.getKey()).getName() + US
									+ ((Integer) oneEnch.getValue()).toString() + GS;
						}
						ench = "{" + ench.substring(0, ench.length() - 1) + "}";
					}

					if (id == Material.POTION.toString() || id == Material.SPLASH_POTION.toString()
							|| id == Material.LINGERING_POTION.toString() || id == Material.TIPPED_ARROW.toString()) {
						PotionMeta potMeta = (PotionMeta) itemMeta;
						List<PotionEffect> potFXs = new ArrayList<PotionEffect>();
						if (potMeta.hasCustomEffects()) {
							potFXs = potMeta.getCustomEffects();

							for (PotionEffect fx : potFXs) {
								potFX = "";
								potFX += (fx.getType().getName() + US + fx.getDuration() + US + fx.getAmplifier() + GS);
							}
							potFX = "{" + potFX.substring(0, potFX.length() - 1) + "}";
						} else {
							PotionData potData = potMeta.getBasePotionData();
							String type = potData.getType().name();
							String ext = Boolean.toString(potData.isExtended());
							String upg = Boolean.toString(potData.isUpgraded());
							potion = "{" + type + US + ext + US + upg + "}";
						}
					} else if (id == Material.SKULL_ITEM.toString() && damage == 3) {
						SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
						if (skullMeta.getOwner() != null) {
							skullOwner = skullMeta.getOwner();
						}
					}

					if (id == Material.ENCHANTED_BOOK.toString()) {
						EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) itemMeta;
						if (bookMeta.hasStoredEnchants()) {
							bookEnch = "";
							Map<Enchantment, Integer> bookEnchs = bookMeta.getStoredEnchants();
							for (Map.Entry<Enchantment, Integer> oneBookEnch : bookEnchs.entrySet()) {
								bookEnch += ((Enchantment) oneBookEnch.getKey()).getName() + US
										+ ((Integer) oneBookEnch.getValue()).toString() + GS;
							}
							bookEnch = "{" + bookEnch.substring(0, bookEnch.length() - 1) + "}";
						}
					}

					if (id == Material.BOOK_AND_QUILL.toString() || id == Material.WRITTEN_BOOK.toString()) {
						bookWrite = "";

						String title = Character.toString(NUL);
						String generation = Character.toString(NUL);
						String author = Character.toString(NUL);
						String pages$ = "";

						BookMeta bookMeta = (BookMeta) itemMeta;

						if (bookMeta.hasAuthor()) {
							author = bookMeta.getAuthor();
						}

						if (bookMeta.hasGeneration()) {
							generation = bookMeta.getGeneration().name();
						}

						if (bookMeta.hasTitle()) {
							title = bookMeta.getTitle();
						}

						List<String> pages = bookMeta.getPages();
						for (String page : pages) {
							pages$ += page + US;
						}
						pages$ = pages$.substring(0, pages$.length() - 1);

						bookWrite = "{" + author + GS + title + GS + generation + GS + pages$ + "}";
					}
					String entry = norm + RS + ench + RS + bookEnch + RS + potion + RS + potFX + RS + bookWrite + RS
							+ skullOwner;
					list.add(entry);
				}
			}
			if (isInspecting) {
				PluginMain.fileManager.write("inventorys/user", yaml.getString("inspectedUser"), list);
				Yaml userYaml = PluginMain.getOfflinePlayerYaml(yaml.getString("inspectedUser"));
				userYaml.set("backpackUsed", false);
				userYaml.save();
				yaml.set("isInspecting", false);
				yaml.save();
			} else if (inBackpack) {
				PluginMain.fileManager.write("inventorys/user", e.getPlayer().getName().toLowerCase(), list);
				yaml.set("inBackpack", false);
				yaml.save();
			}
		}
	}
}