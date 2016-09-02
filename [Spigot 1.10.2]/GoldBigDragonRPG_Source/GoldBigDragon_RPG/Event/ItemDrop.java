package GoldBigDragon_RPG.Event;

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

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class ItemDrop
{
	public static int passmoney;
	public void PureItemNaturalDrop(Location loc, short Id,byte Data,byte Stack)
	{
		ItemStack Item = new MaterialData(Id, Data).toItemStack(Stack);
        Item item = loc.getWorld().dropItem(loc, Item);
		return;
	}
	
	public void PureItemPowerDrop(Location loc, Material m, double X, float Y, double Z)
	{
		Item item = loc.getWorld().dropItem(loc, new ItemStack(m));
        item.setVelocity(new Vector(X, Y, Z));
		return;
	}
	
	public void FixedItemNaturalDrop(Location loc, String Display, short ID, byte DATA, byte STACK, List<String> Lore)
	{
		ItemStack Item = new MaterialData(ID, DATA).toItemStack(STACK);
		ItemMeta Item_Meta = Item.getItemMeta();
		Item_Meta.setDisplayName(Display);
		Item_Meta.setLore(Lore);
		Item.setItemMeta(Item_Meta);
	    loc.getWorld().dropItemNaturally(loc, Item);
		return;
	}
	
	public void FixedItemPowerDrop(Location loc, String Display, short ID, byte DATA, byte STACK, List<String> Lore, double X, float Y, double Z)
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
        item.setVelocity(new Vector(0, 0, 0));
		return;
	}
	
	
	public void MoneyDrop(Location loc, int money)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	    YamlManager Config =  YC.getNewConfig("config.yml");
		if(money <= 0)
		return;
	    int mok = money/Config.getInt("Server.Max_Drop_Money");
	    //if(mok >= 20)
	    //	mok = 20;
	    for(int count = 0; count < mok; count++)
		    loc.getWorld().dropItemNaturally(loc, MoneyShape(Config.getInt("Server.Max_Drop_Money")));

	    int na = money%Config.getInt("Server.Max_Drop_Money");
	    if(na >= 1)
	    	loc.getWorld().dropItemNaturally(loc, MoneyShape(na));
		return;
	}
	public ItemStack MoneyShape(int money)
	{
		GoldBigDragon_RPG.Main.ServerOption SO = new GoldBigDragon_RPG.Main.ServerOption();
		ItemStack Item;
		if(money<=50)
		Item = new MaterialData(SO.Money1ID, (byte) SO.Money1DATA).toItemStack(1);
		else if(money<=100)
		Item = new MaterialData(SO.Money2ID, (byte) SO.Money2DATA).toItemStack(1);
		else if(money<=1000)
		Item = new MaterialData(SO.Money3ID, (byte) SO.Money3DATA).toItemStack(1);
		else if(money<=10000)
		Item = new MaterialData(SO.Money4ID, (byte) SO.Money4DATA).toItemStack(1);
		else if(money<=50000)
		Item = new MaterialData(SO.Money5ID, (byte) SO.Money5DATA).toItemStack(1);
		else
		Item = new MaterialData(SO.Money6ID, (byte) SO.Money6DATA).toItemStack(1);
		ItemMeta Item_Meta = Item.getItemMeta();
		Item_Meta.setDisplayName(GoldBigDragon_RPG.Main.ServerOption.Money+" 」f」f」f」l" + money);
		Item_Meta.setLore(Arrays.asList(ChatColor.YELLOW +""+ money +" "+GoldBigDragon_RPG.Main.ServerOption.Money,passmoney+""));
		Item_Meta.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 500, true);
		Item.setItemMeta(Item_Meta);
		passmoney++;
		return Item;
	}
	
}
