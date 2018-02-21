package util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class UtilGui
{
	public void stack(String display, int id, int data,  int amount, List<String> lore,  int loc, Inventory inventory)
	{
		ItemStack item = new ItemStack(id);
		item.setDurability((short) data);
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display);
		meta.setLore(lore);
		item.setItemMeta(meta);
		inventory.setItem(loc, item);
		return;
	}
	
	public void removeFlagStack(String display, int id,  int data,  int amount, List<String> lore,  int loc, Inventory inventory)
	{
		ItemStack item = new ItemStack(id);
		item.setDurability((short) data);
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags();
		meta.setUnbreakable(true);
		meta.setDisplayName(display);
		meta.setLore(lore);
		item.setItemMeta(meta);
		inventory.setItem(loc, item);
		return;
	}
	
	public ItemStack getPlayerSkull(String display,  int amount, List<String> lore, String playerName)
	{
		ItemStack i = new ItemStack(Material.SKULL_ITEM, amount);
		i.setDurability((short)3);
	    SkullMeta meta = (SkullMeta)i.getItemMeta();
	    meta.setOwner(playerName);
	    meta.setDisplayName(display);
	    meta.setLore(lore);
	    i.setItemMeta(meta);
	    return i;
	}
	
	public void stackItem(ItemStack item,  int loc, Inventory inventory)
	{
		inventory.setItem(loc, item);
		return;
	}

	public ItemStack getItemStack(String display, int id,  int data, int amount, List<String> lore)
	{
		ItemStack item = new ItemStack(id);
		item.setDurability((short) data);
		item.setAmount(amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
