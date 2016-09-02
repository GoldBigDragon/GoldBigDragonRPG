package GoldBigDragon_RPG.GUI;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
//import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class GUIutil
{
	public void Stack(String Display, int ID, int DATA,  int Stack, List<String> Lore,  int Loc, Inventory inventory)
	{
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(Stack);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		inventory.setItem(Loc, Icon);
		return;
	}
	
	public void Stack2(String Display, int ID,  int DATA,  int Stack, List<String> Lore,  int Loc, Inventory inventory)
	{
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(Stack);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		inventory.setItem(Loc, Icon);
		return;
	}
	
	public ItemStack getPlayerSkull(String Display,  int Stack, List<String> Lore, String PlayerName)
	{
		ItemStack i = new ItemStack(Material.SKULL_ITEM, Stack);
		i.setDurability((short)3);
	    SkullMeta meta = (SkullMeta)i.getItemMeta();
	    meta.setOwner(PlayerName);
	    meta.setDisplayName(Display);
	    meta.setLore(Lore);
	    i.setItemMeta(meta);
	    return i;
	}
	
	public void ItemStackStack(ItemStack Item,  int Loc, Inventory inventory)
	{
		inventory.setItem(Loc, Item);
		return;
	}

	public ItemStack getItemStack(String Display, int ID,  int DATA, int Stack, List<String> Lore)
	{
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(Stack);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		return Icon;
	}
	
}
