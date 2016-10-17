package ch.gyselanimatioon.miepcraft.commands;
//EDITED by jade99_

import ch.gyselanimatioon.miepcraft.PluginMain;
import ch.gyselanimatioon.miepcraft.Yaml;

import static ch.gyselanimatioon.miepcraft.PluginMain.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Backpack implements CommandExecutor {
	private static short version = 0x0000;
	/*
	 * private static String uuid$ = ""; TODO auto-update on name-change;
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if ((sender instanceof Player)) {
			player = (Player) sender;
		}

		if (player != null) {
			Inventory inv;
			Yaml playerYaml = PluginMain.getPlayerYaml(player);
			Yaml userYaml = null;

			if (player.hasPermission("miepcraft.commands.backpack.others") && args.length == 1) {
				inv = Bukkit.createInventory(null, 90, "Backpack von " + args[0]);
				playerYaml.set("isInspecting", true);
				playerYaml.set("inspectedUser", args[0]);
				playerYaml.save();

				userYaml = PluginMain.getOfflinePlayerYaml(args[0]);
				userYaml.set("backpackUsed", true);
				userYaml.save();
			} else {
				System.out.println(1);
				playerYaml.set("inBackpack", true);
				playerYaml.save();
				if (player.hasPermission("miepcraft.commands.backpack.big")) {
					inv = Bukkit.createInventory(null, 90, "10x9 Backpack");
				} else if (player.hasPermission("miepcraft.commands.backpack.middle")) {
					inv = Bukkit.createInventory(null, 36, "4x9 Backpack");
				} else {
					inv = Bukkit.createInventory(null, 18, "2x9 Backpack");
				}
			}

			List<String> lines = new ArrayList<String>();
			if (playerYaml.getBoolean("isInspecting")) {
				PluginMain.fileManager.createFile("inventorys/user",
						playerYaml.getString("inspectedUser").toLowerCase());
				lines = PluginMain.fileManager.read("inventorys/user",
						playerYaml.getString("inspectedUser").toLowerCase());
			} else {
				PluginMain.fileManager.createFile("inventorys/user", player.getName().toLowerCase());
				lines = PluginMain.fileManager.read("inventorys/user", player.getName().toLowerCase());
			}
			List<String> items = new ArrayList<String>();

			if (!(lines.isEmpty())) {
				if (lines.get(0).charAt(0) == SOH && lines.get(0).charAt(lines.get(0).length() - 1) == STX) {
					String header = lines.get(0);
					char majorVersion = header.charAt(1);
					char minorVersion = header.charAt(2);
					version = (short) (((short) majorVersion << 8) | minorVersion);
					// uuid$ = header.substring(3, 24);

					for (int i = 1; i < lines.size(); i++) {
						items.add(i - 1, lines.get(i));
					}

				}
				if (version == 0x0203) {
					// TODO Banners/Shields
				} else if (version == 0x0202) {
					for (String item : items) {
						String[] itemDataArray = item.split(Character.toString(RS));

						String id = new String();
						String count = new String();
						String damage = new String();
						String slot = new String();
						String name = new String();

						String norm = itemDataArray[0].substring(1, itemDataArray[0].length() - 1);
						if (norm.contains(Character.toString(US))) {
							String[] normEntrys = norm.split(Character.toString(US));
							id = normEntrys[0];
							count = normEntrys[1];
							damage = normEntrys[2];
							slot = normEntrys[3];
							name = normEntrys[4];
						}

						ItemStack itemStack = new ItemStack(Material.valueOf(id));
						itemStack.setDurability(Short.parseShort(damage));
						itemStack.setAmount(Integer.parseInt(count));

						ItemMeta itemMeta = itemStack.getItemMeta();

						if (!(name.equals(Character.toString(NUL)))) {
							itemMeta.setDisplayName(name);
							itemStack.setItemMeta(itemMeta);
						}

						String ench = itemDataArray[1].substring(1, itemDataArray[1].length() - 1);
						if (ench.contains(Character.toString(GS))) {
							String[] enchEntrys = ench.split(Character.toString(GS));
							for (String enchEntry : enchEntrys) {
								String[] oneEnch = enchEntry.split(Character.toString(US));
								itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
										true);
							}
							itemStack.setItemMeta(itemMeta);
						} else if (ench.contains(Character.toString(US))) {
							String[] oneEnch = ench.split(Character.toString(US));
							itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]), true);
							itemStack.setItemMeta(itemMeta);
						}

						if (id.equals(Material.ENCHANTED_BOOK.toString())) {
							EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) itemMeta;
							String bookEnch = itemDataArray[2].substring(1, itemDataArray[2].length() - 1);
							if (bookEnch.contains(Character.toString(GS))) {
								String[] bookEnchEntrys = bookEnch.split(Character.toString(GS));
								for (String bookEnchEntry : bookEnchEntrys) {
									String[] oneBookEnch = bookEnchEntry.split(Character.toString(US));
									bookMeta.addStoredEnchant(Enchantment.getByName(oneBookEnch[0]),
											Integer.parseInt(oneBookEnch[1]), true);
								}
								itemStack.setItemMeta(bookMeta);
							} else if (bookEnch.contains(Character.toString(US))) {
								String[] oneBookEnch = bookEnch.split(Character.toString(US));
								bookMeta.addStoredEnchant(Enchantment.getByName(oneBookEnch[0]),
										Integer.parseInt(oneBookEnch[1]), true);
								itemStack.setItemMeta(bookMeta);
							}
						}

						if (id.equals(Material.POTION.name()) || id.equals(Material.SPLASH_POTION.name())
								|| id.equals(Material.LINGERING_POTION.name())
								|| id.equals(Material.TIPPED_ARROW.name())) {
							PotionMeta potMeta = (PotionMeta) itemMeta;

							String pot = itemDataArray[3].substring(1, itemDataArray[3].length() - 1);
							if (pot.contains(Character.toString(US))) {
								String[] potEntrys = pot.split(Character.toString(US));
								potMeta = (PotionMeta) itemMeta;
								potMeta.setBasePotionData(new PotionData(PotionType.valueOf(potEntrys[0]),
										Boolean.parseBoolean(potEntrys[1]), Boolean.parseBoolean(potEntrys[2])));
								itemStack.setItemMeta(potMeta);
							}

							String cPot = itemDataArray[4].substring(1, itemDataArray[4].length() - 1);
							if (cPot.contains(Character.toString(GS))) {
								String[] cPotEntrys = cPot.split(Character.toString(GS));
								for (String cPotEntry : cPotEntrys) {
									String[] oneCpot = cPotEntry.split(Character.toString(GS));
									potMeta = (PotionMeta) itemMeta;
									potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(oneCpot[0]),
											Integer.parseInt(oneCpot[1]), Integer.parseInt(oneCpot[2])), false);
									itemStack.setItemMeta(potMeta);
								}
							} else if (cPot.contains(Character.toString(US))) {
								String[] oneCpot = cPot.split(Character.toString(US));
								potMeta = (PotionMeta) itemMeta;
								potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(oneCpot[0]),
										Integer.parseInt(oneCpot[1]), Integer.parseInt(oneCpot[2])), false);
								itemStack.setItemMeta(potMeta);
							}
						}

						if (id.equals(Material.BOOK_AND_QUILL.name()) || id.equals(Material.WRITTEN_BOOK.name())) {
							BookMeta bookMeta = (BookMeta) itemMeta;
							String book = itemDataArray[5].substring(1, itemDataArray[5].length() - 1);
							if (book.contains(Character.toString(GS))) {
								String[] bookData = book.split(Character.toString(GS));
								String author = bookData[0];
								String title = bookData[1];
								String generation = bookData[2];
								String bookPages = bookData[3];
								if (bookPages.contains(Character.toString(US))) {
									String[] onePage = bookPages.split(Character.toString(US));
									for (String page : onePage) {
										bookMeta.addPage(page);
									}
								} else {
									String onePage = bookPages;
									bookMeta.addPage(onePage);
								}

								if (!(author.equals(Character.toString(NUL)))) {
									bookMeta.setAuthor(author);
								}

								if (!(title.equals(Character.toString(NUL)))) {
									bookMeta.setTitle(title);
								}

								if (!(generation.equals(Character.toString(NUL)))) {
									bookMeta.setGeneration(Generation.valueOf(generation));
								}
							}

							itemStack.setItemMeta(bookMeta);
						}

						if (id.equals(Material.SKULL_ITEM.name()) && Integer.parseInt(damage) == 3) {
							SkullMeta skullMeta = (SkullMeta) itemMeta;
							String skull = itemDataArray[6];
							if (!(name.equals(NUL))) {
								skullMeta.setOwner(skull);
								itemStack.setItemMeta(skullMeta);
							}
						}

						inv.setItem(Integer.parseInt(slot), itemStack);
					}

				} else if (version == 0x0200) {
					for (String item : items) {
						Bukkit.getLogger().info("TEST");
						String[] itemDataArray = item.split(Character.toString(RS));

						String id = new String();
						String count = new String();
						String damage = new String();
						String slot = new String();
						String name = new String();

						String norm = itemDataArray[0].substring(1, itemDataArray[0].length() - 1);
						if (norm.contains(Character.toString(US))) {
							String[] normEntrys = norm.split(Character.toString(US));
							id = normEntrys[0];
							count = normEntrys[1];
							damage = normEntrys[2];
							slot = normEntrys[3];
							name = normEntrys[4];
						}

						Bukkit.getLogger().info(id);

						ItemStack itemStack = new ItemStack(Material.valueOf(id));
						itemStack.setDurability(Short.parseShort(damage));
						itemStack.setAmount(Integer.parseInt(count));

						ItemMeta itemMeta = itemStack.getItemMeta();

						if (!(name.equals(Character.toString(NUL)))) {
							itemMeta.setDisplayName(name);
							itemStack.setItemMeta(itemMeta);
						}

						String ench = itemDataArray[1].substring(1, itemDataArray[1].length() - 1);
						if (ench.contains(Character.toString(GS))) {
							String[] enchEntrys = ench.split(Character.toString(GS));
							for (String enchEntry : enchEntrys) {
								String[] oneEnch = enchEntry.split(Character.toString(US));
								itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
										false);
							}
							itemStack.setItemMeta(itemMeta);
						} else if (ench.contains(Character.toString(US))) {
							String[] oneEnch = ench.split(Character.toString(US));
							itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]), false);
							itemStack.setItemMeta(itemMeta);
						}

						if (id.equals(Material.POTION.name()) || id.equals(Material.SPLASH_POTION.name())
								|| id.equals(Material.LINGERING_POTION.name())
								|| id.equals(Material.TIPPED_ARROW.name())) {
							PotionMeta potMeta = (PotionMeta) itemMeta;

							String pot = itemDataArray[2].substring(1, itemDataArray[2].length() - 1);
							if (pot.contains(Character.toString(US))) {
								String[] potEntrys = pot.split(Character.toString(US));
								potMeta = (PotionMeta) itemMeta;
								potMeta.setBasePotionData(new PotionData(PotionType.valueOf(potEntrys[0]),
										Boolean.parseBoolean(potEntrys[1]), Boolean.parseBoolean(potEntrys[2])));
								itemStack.setItemMeta(potMeta);
							}

							String cPot = itemDataArray[3].substring(1, itemDataArray[3].length() - 1);
							if (cPot.contains(Character.toString(GS))) {
								String[] cPotEntrys = cPot.split(Character.toString(GS));
								for (String cPotEntry : cPotEntrys) {
									String[] oneCpot = cPotEntry.split(Character.toString(GS));
									potMeta = (PotionMeta) itemMeta;
									potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(oneCpot[0]),
											Integer.parseInt(oneCpot[1]), Integer.parseInt(oneCpot[2])), false);
									itemStack.setItemMeta(potMeta);
								}
							} else if (cPot.contains(Character.toString(US))) {
								String[] oneCpot = cPot.split(Character.toString(US));
								potMeta = (PotionMeta) itemMeta;
								potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(oneCpot[0]),
										Integer.parseInt(oneCpot[1]), Integer.parseInt(oneCpot[2])), false);
								itemStack.setItemMeta(potMeta);
							}
						}

						if (id.equals(Material.SKULL_ITEM.name()) && Integer.parseInt(damage) == 3) {
							SkullMeta skullMeta = (SkullMeta) itemMeta;
							String skull = itemDataArray[4];
							if (!(name.equals(NUL))) {
								skullMeta.setOwner(skull);
								itemStack.setItemMeta(skullMeta);
							}
						}

						inv.setItem(Integer.parseInt(slot), itemStack);
					}

				} else if (version == 0x0201) {
					for (String item : items) {
						Bukkit.getLogger().info("TEST");
						String[] itemDataArray = item.split(Character.toString(RS));

						String id = new String();
						String count = new String();
						String damage = new String();
						String slot = new String();
						String name = new String();

						String norm = itemDataArray[0].substring(1, itemDataArray[0].length() - 1);
						if (norm.contains(Character.toString(US))) {
							String[] normEntrys = norm.split(Character.toString(US));
							id = normEntrys[0];
							count = normEntrys[1];
							damage = normEntrys[2];
							slot = normEntrys[3];
							name = normEntrys[4];
						}

						Bukkit.getLogger().info(id);

						ItemStack itemStack = new ItemStack(Material.valueOf(id));
						itemStack.setDurability(Short.parseShort(damage));
						itemStack.setAmount(Integer.parseInt(count));

						ItemMeta itemMeta = itemStack.getItemMeta();

						if (!(name.equals(Character.toString(NUL)))) {
							itemMeta.setDisplayName(name);
							itemStack.setItemMeta(itemMeta);
						}

						String ench = itemDataArray[1].substring(1, itemDataArray[1].length() - 1);
						if (ench.contains(Character.toString(GS))) {
							String[] enchEntrys = ench.split(Character.toString(GS));
							for (String enchEntry : enchEntrys) {
								String[] oneEnch = enchEntry.split(Character.toString(US));
								itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
										true);
							}
							itemStack.setItemMeta(itemMeta);
						} else if (ench.contains(Character.toString(US))) {
							String[] oneEnch = ench.split(Character.toString(US));
							itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]), true);
							itemStack.setItemMeta(itemMeta);
						}

						if (id.equals(Material.ENCHANTED_BOOK.toString())) {
							EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) itemMeta;
							String bookEnch = itemDataArray[2].substring(1, itemDataArray[2].length() - 1);
							if (bookEnch.contains(Character.toString(GS))) {
								String[] bookEnchEntrys = bookEnch.split(Character.toString(GS));
								for (String bookEnchEntry : bookEnchEntrys) {
									String[] oneBookEnch = bookEnchEntry.split(Character.toString(US));
									bookMeta.addStoredEnchant(Enchantment.getByName(oneBookEnch[0]),
											Integer.parseInt(oneBookEnch[1]), true);
								}
								itemStack.setItemMeta(bookMeta);
							} else if (bookEnch.contains(Character.toString(US))) {
								String[] oneBookEnch = bookEnch.split(Character.toString(US));
								bookMeta.addStoredEnchant(Enchantment.getByName(oneBookEnch[0]),
										Integer.parseInt(oneBookEnch[1]), true);
								itemStack.setItemMeta(bookMeta);
							}
						}

						if (id.equals(Material.POTION.name()) || id.equals(Material.SPLASH_POTION.name())
								|| id.equals(Material.LINGERING_POTION.name())
								|| id.equals(Material.TIPPED_ARROW.name())) {
							PotionMeta potMeta = (PotionMeta) itemMeta;

							String pot = itemDataArray[3].substring(1, itemDataArray[3].length() - 1);
							if (pot.contains(Character.toString(US))) {
								String[] potEntrys = pot.split(Character.toString(US));
								potMeta = (PotionMeta) itemMeta;
								potMeta.setBasePotionData(new PotionData(PotionType.valueOf(potEntrys[0]),
										Boolean.parseBoolean(potEntrys[1]), Boolean.parseBoolean(potEntrys[2])));
								itemStack.setItemMeta(potMeta);
							}

							String cPot = itemDataArray[4].substring(1, itemDataArray[4].length() - 1);
							if (cPot.contains(Character.toString(GS))) {
								String[] cPotEntrys = cPot.split(Character.toString(GS));
								for (String cPotEntry : cPotEntrys) {
									String[] oneCpot = cPotEntry.split(Character.toString(GS));
									potMeta = (PotionMeta) itemMeta;
									potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(oneCpot[0]),
											Integer.parseInt(oneCpot[1]), Integer.parseInt(oneCpot[2])), false);
									itemStack.setItemMeta(potMeta);
								}
							} else if (cPot.contains(Character.toString(US))) {
								String[] oneCpot = cPot.split(Character.toString(US));
								potMeta = (PotionMeta) itemMeta;
								potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(oneCpot[0]),
										Integer.parseInt(oneCpot[1]), Integer.parseInt(oneCpot[2])), false);
								itemStack.setItemMeta(potMeta);
							}
						}

						if (id.equals(Material.SKULL_ITEM.name()) && Integer.parseInt(damage) == 3) {
							SkullMeta skullMeta = (SkullMeta) itemMeta;
							String skull = itemDataArray[5];
							if (!(name.equals(NUL))) {
								skullMeta.setOwner(skull);
								itemStack.setItemMeta(skullMeta);
							}
						}

						inv.setItem(Integer.parseInt(slot), itemStack);
					}

				} else if (version == 0x0200) {
					for (String item : items) {
						Bukkit.getLogger().info("TEST");
						String[] itemDataArray = item.split(Character.toString(RS));

						String id = new String();
						String count = new String();
						String damage = new String();
						String slot = new String();
						String name = new String();

						String norm = itemDataArray[0].substring(1, itemDataArray[0].length() - 1);
						if (norm.contains(Character.toString(US))) {
							String[] normEntrys = norm.split(Character.toString(US));
							id = normEntrys[0];
							count = normEntrys[1];
							damage = normEntrys[2];
							slot = normEntrys[3];
							name = normEntrys[4];
						}

						Bukkit.getLogger().info(id);

						ItemStack itemStack = new ItemStack(Material.valueOf(id));
						itemStack.setDurability(Short.parseShort(damage));
						itemStack.setAmount(Integer.parseInt(count));

						ItemMeta itemMeta = itemStack.getItemMeta();

						if (!(name.equals(Character.toString(NUL)))) {
							itemMeta.setDisplayName(name);
							itemStack.setItemMeta(itemMeta);
						}

						String ench = itemDataArray[1].substring(1, itemDataArray[1].length() - 1);
						if (ench.contains(Character.toString(GS))) {
							String[] enchEntrys = ench.split(Character.toString(GS));
							for (String enchEntry : enchEntrys) {
								String[] oneEnch = enchEntry.split(Character.toString(US));
								itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
										true);
							}
							itemStack.setItemMeta(itemMeta);
						} else if (ench.contains(Character.toString(US))) {
							String[] oneEnch = ench.split(Character.toString(US));
							itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]), true);
							itemStack.setItemMeta(itemMeta);
						}

						if (id.equals(Material.POTION.name()) || id.equals(Material.SPLASH_POTION.name())
								|| id.equals(Material.LINGERING_POTION.name())
								|| id.equals(Material.TIPPED_ARROW.name())) {
							PotionMeta potMeta = (PotionMeta) itemMeta;

							String pot = itemDataArray[2].substring(1, itemDataArray[2].length() - 1);
							if (pot.contains(Character.toString(US))) {
								String[] potEntrys = pot.split(Character.toString(US));
								potMeta = (PotionMeta) itemMeta;
								potMeta.setBasePotionData(new PotionData(PotionType.valueOf(potEntrys[0]),
										Boolean.parseBoolean(potEntrys[1]), Boolean.parseBoolean(potEntrys[2])));
								itemStack.setItemMeta(potMeta);
							}

							String cPot = itemDataArray[3].substring(1, itemDataArray[3].length() - 1);
							if (cPot.contains(Character.toString(GS))) {
								String[] cPotEntrys = cPot.split(Character.toString(GS));
								for (String cPotEntry : cPotEntrys) {
									String[] oneCpot = cPotEntry.split(Character.toString(GS));
									potMeta = (PotionMeta) itemMeta;
									potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(oneCpot[0]),
											Integer.parseInt(oneCpot[1]), Integer.parseInt(oneCpot[2])), false);
									itemStack.setItemMeta(potMeta);
								}
							} else if (cPot.contains(Character.toString(US))) {
								String[] oneCpot = cPot.split(Character.toString(US));
								potMeta = (PotionMeta) itemMeta;
								potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(oneCpot[0]),
										Integer.parseInt(oneCpot[1]), Integer.parseInt(oneCpot[2])), false);
								itemStack.setItemMeta(potMeta);
							}
						}

						if (id.equals(Material.SKULL_ITEM.name()) && Integer.parseInt(damage) == 3) {
							SkullMeta skullMeta = (SkullMeta) itemMeta;
							String skull = itemDataArray[4];
							if (!(name.equals(NUL))) {
								skullMeta.setOwner(skull);
								itemStack.setItemMeta(skullMeta);
							}
						}

						inv.setItem(Integer.parseInt(slot), itemStack);
					}

				} else if (version == 0x0000) {
					items = lines;
					for (String item : items) {
						String[] itemDataArray = item.split(" . ");
						if (itemDataArray.length == 5) {
							String id = new String();
							String count = new String();
							String damage = new String();
							String slot = new String();
							String ench = new String();

							id = itemDataArray[0];
							slot = itemDataArray[2];
							count = itemDataArray[1];
							damage = itemDataArray[3];
							ItemStack itemStack = new ItemStack(Material.valueOf(id), Integer.parseInt(count));
							itemStack.setDurability(Short.parseShort(damage));

							ItemMeta itemMeta = itemStack.getItemMeta();

							ench = itemDataArray[4];
							if (ench.contains(",")) {
								String[] enchEntrys = ench.split(",");
								for (String enchEntry : enchEntrys) {
									String[] oneEnch = enchEntry.split("=");
									itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
											true);
									itemStack.setItemMeta(itemMeta);
								}
							} else if (ench.contains("=")) {
								String[] oneEnch = ench.split("=");
								itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
										true);
								itemStack.setItemMeta(itemMeta);
							}

							inv.setItem(Integer.parseInt(slot), itemStack);
						} else if (itemDataArray.length == 8) {
							String id = new String();
							String count = new String();
							String damage = new String();
							String slot = new String();
							String ench = new String();
							String type = new String();
							String ext = new String();
							String upg = new String();

							id = itemDataArray[0];
							slot = itemDataArray[2];
							count = itemDataArray[1];
							damage = itemDataArray[3];
							ItemStack itemStack = new ItemStack(Material.valueOf(id), Integer.parseInt(count));
							itemStack.setDurability(Short.parseShort(damage));

							ItemMeta itemMeta = itemStack.getItemMeta();

							ench = itemDataArray[4];
							if (ench.contains(",")) {
								String[] enchEntrys = ench.split(",");
								for (String enchEntry : enchEntrys) {
									String[] oneEnch = enchEntry.split("=");
									itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
											true);
									itemStack.setItemMeta(itemMeta);
								}
							} else if (ench.contains("=")) {
								String[] oneEnch = ench.split("=");
								itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
										true);
								itemStack.setItemMeta(itemMeta);
							}

							type = itemDataArray[5];
							ext = itemDataArray[6];
							upg = itemDataArray[7];
							if (id.equals(Material.POTION.name()) || id.equals(Material.SPLASH_POTION.name())
									|| id.equals(Material.LINGERING_POTION.name())
									|| id.equals(Material.TIPPED_ARROW.name())) {
								PotionMeta potMeta = (PotionMeta) itemMeta;
								potMeta.setBasePotionData(new PotionData(PotionType.valueOf(type),
										Boolean.parseBoolean(ext), Boolean.parseBoolean(upg)));
								itemStack.setItemMeta(potMeta);
							}

							inv.setItem(Integer.parseInt(slot), itemStack);
						} else if (itemDataArray.length == 10) {
							String id = new String();
							String count = new String();
							String damage = new String();
							String slot = new String();
							String ench = new String();
							String type = new String();
							String ext = new String();
							String upg = new String();
							String pot = new String();
							String name = new String();

							id = itemDataArray[0];
							slot = itemDataArray[2];
							count = itemDataArray[1];
							damage = itemDataArray[3];
							name = itemDataArray[9];
							ItemStack itemStack = new ItemStack(Material.valueOf(id), Integer.parseInt(count));
							itemStack.setDurability(Short.parseShort(damage));

							ItemMeta itemMeta = itemStack.getItemMeta();
							if (!(name.equals("null"))) {
								itemMeta.setDisplayName(name);
							}

							ench = itemDataArray[4];
							if (ench.contains(",")) {
								String[] enchEntrys = ench.split(",");
								for (String enchEntry : enchEntrys) {
									String[] oneEnch = enchEntry.split("=");
									itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
											true);
									itemStack.setItemMeta(itemMeta);
								}
							} else if (ench.contains("=")) {
								String[] oneEnch = ench.split("=");
								itemMeta.addEnchant(Enchantment.getByName(oneEnch[0]), Integer.parseInt(oneEnch[1]),
										true);
								itemStack.setItemMeta(itemMeta);
							}

							type = itemDataArray[5];
							ext = itemDataArray[6];
							upg = itemDataArray[7];
							pot = itemDataArray[8];
							if (id.equals(Material.POTION.name()) || id.equals(Material.SPLASH_POTION.name())
									|| id.equals(Material.LINGERING_POTION.name())
									|| id.equals(Material.TIPPED_ARROW.name())) {
								PotionMeta potMeta = (PotionMeta) itemMeta;
								potMeta.setBasePotionData(new PotionData(PotionType.valueOf(type),
										Boolean.parseBoolean(ext), Boolean.parseBoolean(upg)));
								itemStack.setItemMeta(potMeta);
								if (pot.contains(",")) {
									String[] potEntrys = pot.split(",");
									for (String potEntry : potEntrys) {
										String[] onePot = potEntry.split(":");
										potMeta.addCustomEffect(
												new PotionEffect(PotionEffectType.getByName(onePot[0]),
														Integer.parseInt(onePot[1]), Integer.parseInt(onePot[2])),
												false);
										itemStack.setItemMeta(potMeta);
									}
								} else if (pot.contains(":")) {
									String[] onePot = pot.split(":");
									Bukkit.getLogger()
											.info("ID:" + onePot[0] + "DUR:" + onePot[1] + "AMP:" + onePot[2]);
									potMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(onePot[0]),
											Integer.parseInt(onePot[1]), Integer.parseInt(onePot[2])), false);
									itemStack.setItemMeta(potMeta);
								}
							}

							inv.setItem(Integer.parseInt(slot), itemStack);
						}
					}
				}
			}
			if (playerYaml.getBoolean("backpackUsed")) {
				player.sendMessage("§4§lDein Backpack wird grade von einem Admin angeschaut!");
				playerYaml.set("isInspecting", false);
				playerYaml.set("inBackpack", false);
				playerYaml.save();
			} else {
				player.getPlayer().openInventory(inv);
			}
		} else {
			sender.sendMessage("Nicht moeglich in der Konsole.");
		}
		return true;
	}
}