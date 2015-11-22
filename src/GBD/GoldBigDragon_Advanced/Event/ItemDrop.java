package GBD.GoldBigDragon_Advanced.Event;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class ItemDrop
{
	public void PureItemNaturalDrop(Location loc, int Id,int Data,int Stack)
	{
		ItemStack Item = new MaterialData(Id, (byte) Data).toItemStack(Stack);
        Item item = loc.getWorld().dropItem(loc, Item);
		return;
	}
	
	public void PureItemPowerDrop(Location loc, Material m, double X, double Y, double Z)
	{
		Item item = loc.getWorld().dropItem(loc, new ItemStack(m));
        item.setVelocity(new Vector(X, Y, Z));
		return;
	}
	
	public void FixedItemNaturalDrop(Location loc, String Display, int ID, int DATA, int STACK, List<String> Lore)
	{
		ItemStack Item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta Item_Meta = Item.getItemMeta();
		Item_Meta.setDisplayName(Display);
		Item_Meta.setLore(Lore);
		Item.setItemMeta(Item_Meta);
	    loc.getWorld().dropItemNaturally(loc, Item);
		return;
	}
	
	public void FixedItemPowerDrop(Location loc, String Display, int ID, int DATA, int STACK, List<String> Lore, double X, double Y, double Z)
	{
		ItemStack Item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta Item_Meta = Item.getItemMeta();
		Item_Meta.setDisplayName(Display);
		Item_Meta.setLore(Lore);
		Item.setItemMeta(Item_Meta);
		Item item = loc.getWorld().dropItemNaturally(loc, Item);
        item.setVelocity(new Vector(X, Y, Z));
		return;
	}

	public void CustomItemDrop(Location loc, ItemStack m)
	{
        Item item = loc.getWorld().dropItem(loc, new ItemStack(m));
		return;
	}
	
	
	public void MoneyDrop(Location loc, int money)
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
	    YamlManager Config =  Event_YC.getNewConfig("config.yml");
		if(money >= Config.getInt("Server.Max_Drop_Money"))
			money = Config.getInt("Server.Max_Drop_Money");
		if(money <= 0)
		return;
		ItemStack Item;
		if(money<=50)
		Item = new MaterialData(348, (byte) 0).toItemStack(1);
		else if(money<=100)
		Item = new MaterialData(371, (byte) 0).toItemStack(1);
		else if(money<=1000)
		Item = new MaterialData(147, (byte) 0).toItemStack(1);
		else if(money<=10000)
		Item = new MaterialData(266, (byte) 0).toItemStack(1);
		else
		Item = new MaterialData(41, (byte) 0).toItemStack(1);
		ItemMeta Item_Meta = Item.getItemMeta();
		Item_Meta.setDisplayName(GBD.GoldBigDragon_Advanced.Main.ServerOption.Money+" 」f」f」f」l" + money);
		Item_Meta.setLore(Arrays.asList(ChatColor.YELLOW +""+ money +" "+GBD.GoldBigDragon_Advanced.Main.ServerOption.Money));
		Item_Meta.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 500, true);
		Item.setItemMeta(Item_Meta);
	    loc.getWorld().dropItemNaturally(loc, Item);
		return;
	}
}
