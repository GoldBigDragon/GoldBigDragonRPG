package util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import effect.SoundEffect;
import main.Main_ServerOption;

public class Util_Player
{
	public void DungeonClear(Player player, long Money, long EXP)
	{
		main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(Money, EXP, false);
		SoundEffect.SP(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.5F, 1.8F);
		player.sendMessage("§e§l[던전 클리어 보상] : "+"§b§l[경험치] "+ EXP + " " + "§e§l["+ChatColor.WHITE+Main_ServerOption.Money+"§e§l] "+ Money);
	}
	
	public void addMoneyAndEXP(Player player, long Money, long EXP, Location loc, boolean givePartyMemberToo, boolean isDungeonClear)
	{
	    if(EXP * Main_ServerOption.Event_Exp > Long.MAX_VALUE)
	    	EXP = Long.MAX_VALUE;
	    else if(EXP * Main_ServerOption.Event_Exp < Long.MIN_VALUE)
	    	EXP = Long.MIN_VALUE;
	    else
	    	EXP = EXP * Main_ServerOption.Event_Exp;
		if(main.Main_ServerOption.PartyJoiner.containsKey(player)==false)
			main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(Money, EXP, true);
		else if(givePartyMemberToo)
		{
		    Player[] party = main.Main_ServerOption.Party.get(main.Main_ServerOption.PartyJoiner.get(player)).getMember();
			byte partymember=0;
			for(int count = 0; count<party.length;count++)
		    	if(party[count].isOnline() == true)
					if(party[count].getLocation().getWorld() == loc.getWorld())
						if(loc.distance(party[count].getLocation()) <= Main_ServerOption.EXPShareDistance)
							partymember++;
			Money = (Money/partymember);
			EXP = (EXP/partymember);
			
			for(int count = 0; count<party.length;count++)
			{
		    	if(party[count].isOnline() == true)
					if(party[count].getLocation().getWorld() == loc.getWorld())
						if(loc.distance(party[count].getLocation()) <= Main_ServerOption.EXPShareDistance)
							main.Main_ServerOption.PlayerList.get(party[count].getUniqueId().toString()).addStat_MoneyAndEXP(Money, EXP, true);
			}
		}
		return;
	}
	
	public boolean giveItem(Player player, ItemStack item)
	{
		if(item == null)
			return true;
		else if(player.getInventory().firstEmpty() == -1)
		{
			return false;
		}
		else
		{
			player.getInventory().addItem(item);
			return true;
		}
	}
	
	public void giveItemForce(Player player, ItemStack item)
	{
		if(player.isOnline())
		{
			if(player.getInventory().firstEmpty() == -1)
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c§l[SYSTEM] : 인벤토리가 부족하여 우편함으로 아이템을 발송하였습니다!");
				new structure.Struct_PostBox().SendPost_Server(player.getUniqueId().toString(), "[시스템]", "[인벤토리 부족]", "인벤토리가 부족하여 우편으로 아이템이 배송되었습니다.", item);
			}
			else
				player.getInventory().addItem(item);
		}
		else
			new structure.Struct_PostBox().SendPost_Server(player.getUniqueId().toString(), "[시스템]", "[인벤토리 부족]", "인벤토리가 부족하여 우편으로 아이템이 배송되었습니다.", item);
		return;
	}
	
	public void giveItemDrop(Player player, ItemStack item, Location loc)
	{
		if(player.getInventory().firstEmpty() == -1)
			new event.Main_ItemDrop().CustomItemDrop(loc, item);
		else
			player.getInventory().addItem(item);
	}
	
	public boolean deleteItem(Player player, ItemStack item, int Amount)
	{
		if(Amount == -1)
		{
			ItemStack originalItem = new ItemStack(item);
			originalItem.setAmount(1);
			for(int count = 0; count < player.getInventory().getSize(); count++)
			{
				if(player.getInventory().getItem(count)!=null)
				{
					ItemStack attatchedItem = new ItemStack(player.getInventory().getItem(count));
					int attatchedItemAmount = attatchedItem.getAmount();
					attatchedItem.setAmount(1);
					if(attatchedItem.equals(originalItem))
						player.getInventory().setItem(count, null);
				}
			}
			return true;
		}
		int originalItemAmount = item.getAmount();
		ItemStack originalItem = new ItemStack(item);
		
		originalItem.setAmount(1);
		int totalItemAmount = 0;
		for(int count = 0; count < player.getInventory().getSize(); count++)
		{
			if(player.getInventory().getItem(count)!=null)
			{
				ItemStack attatchedItem = new ItemStack(player.getInventory().getItem(count));
				int attatchedItemAmount = attatchedItem.getAmount();
				attatchedItem.setAmount(1);
				if(attatchedItem.equals(originalItem))
					totalItemAmount = totalItemAmount + attatchedItemAmount;
			}
		}
		
		if(totalItemAmount < Amount * originalItemAmount)
			return false;
		else
		{
			Amount = Amount * originalItemAmount;
			totalItemAmount = 0;
			for(int count = 0; count < player.getInventory().getSize(); count++)
			{
				if(player.getInventory().getItem(count)!=null)
				{
					ItemStack attatchedItem = new ItemStack(player.getInventory().getItem(count));
					int attatchedItemAmount = attatchedItem.getAmount();
					attatchedItem.setAmount(1);
					if(attatchedItem.equals(originalItem))
					{
						if(totalItemAmount+attatchedItemAmount <= Amount)
						{
							player.getInventory().setItem(count, null);
							totalItemAmount = totalItemAmount+attatchedItemAmount;
						}
						else if(totalItemAmount+attatchedItemAmount > Amount)
						{
							attatchedItem.setAmount(attatchedItemAmount-(Amount-totalItemAmount));
							player.getInventory().setItem(count, attatchedItem);
							return true;
						}
					}
				}
				if(totalItemAmount==Amount)
					return true;
			}
		}
		return false;
	}

	public int getItemAmount(Player player, ItemStack item)
	{
		int originalItemAmount = item.getAmount();
		ItemStack originalItem = new ItemStack(item);
		originalItem.setAmount(1);
		int totalItemAmount = 0;
		for(int count = 0; count < player.getInventory().getSize(); count++)
		{
			if(player.getInventory().getItem(count)!=null)
			{
				ItemStack attatchedItem = new ItemStack(player.getInventory().getItem(count));
				int attatchedItemAmount = attatchedItem.getAmount();
				attatchedItem.setAmount(1);
				if(attatchedItem.equals(originalItem))
					totalItemAmount = totalItemAmount + attatchedItemAmount;
			}
		}
		return (int)(totalItemAmount / originalItemAmount);
	}
	
	public void dropItem(Player player, ItemStack item, boolean isKeyDrop)
	{
		ItemStack originalItem = new ItemStack(item);
		originalItem.setAmount(1);
		for(int count = 0; count < player.getInventory().getSize(); count++)
		{
			if(player.getInventory().getItem(count)!=null)
			{
				ItemStack attatchedItem = new ItemStack(player.getInventory().getItem(count));
				int attatchedItemAmount = attatchedItem.getAmount();
				attatchedItem.setAmount(1);
				if(attatchedItem.equals(originalItem))
				{
					new event.Main_ItemDrop().CustomItemDrop(player.getLocation().add(0,2,0), player.getInventory().getItem(count));
					if(isKeyDrop)
						SoundEffect.SL(player.getLocation().add(0,2,0), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5F, 1.8F);
					else
						SoundEffect.SL(player.getLocation().add(0,2,0), Sound.BLOCK_LAVA_POP, 1.5F, 1.8F);
					player.getInventory().setItem(count, null);
				}
			}
		}
	}

	public boolean teleportToCurrentArea(Player player, boolean isSearchforLastVisitedToo)
	{
		Object[] WorldList = main.Main_ServerOption.AreaList.keySet().toArray();
		for(int count = 0; count < WorldList.length; count++)
		{
			if(main.Main_ServerOption.AreaList.containsKey(WorldList[count].toString()))
			{
				if(main.Main_ServerOption.AreaList.get(WorldList[count].toString()) != null)
				{
					String CurrentArea = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_CurrentArea();
					for(int count2 = 0; count2 < main.Main_ServerOption.AreaList.get(WorldList[count].toString()).size(); count2++)
					{
						Object[] WorldList2 = main.Main_ServerOption.AreaList.get(WorldList[count].toString()).toArray();
						if(WorldList2[count2].toString().compareTo(CurrentArea)==0)
						{
						  	YamlLoader PlayerConfig = new YamlLoader();
							PlayerConfig.getConfig("Area/AreaList.yml");
							Location loc = new Location(Bukkit.getServer().getWorld(PlayerConfig.getString(CurrentArea+".World")), PlayerConfig.getInt(CurrentArea+".SpawnLocation.X"), PlayerConfig.getInt(CurrentArea+".SpawnLocation.Y"), PlayerConfig.getInt(CurrentArea+".SpawnLocation.Z"),PlayerConfig.getInt(CurrentArea+".SpawnLocation.Yaw"),PlayerConfig.getInt(CurrentArea+".SpawnLocation.Pitch"));
							player.teleport(loc);
							return true;
						}
					}
				}
			}
		}
		if(isSearchforLastVisitedToo)
		{
			teleportToLastVisited(player);
			return true;
		}
		else
			return false;
	}
	
	public void teleportToLastVisited(Player player)
	{
		Object[] WorldList = main.Main_ServerOption.AreaList.keySet().toArray();
		for(int count = 0; count < WorldList.length; count++)
		{
			String CurrentArea = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited();
			for(int count2 = 0; count2 < main.Main_ServerOption.AreaList.get(WorldList[count].toString()).size(); count2++)
			{
				Object[] WorldList2 = main.Main_ServerOption.AreaList.get(WorldList[count].toString()).toArray();
				if(WorldList2[count2].toString().compareTo(CurrentArea)==0)
				{
				  	YamlLoader PlayerConfig = new YamlLoader();
					PlayerConfig.getConfig("Area/AreaList.yml");
					Location loc = new Location(Bukkit.getServer().getWorld(PlayerConfig.getString(CurrentArea+".World")), PlayerConfig.getInt(CurrentArea+".SpawnLocation.X"), PlayerConfig.getInt(CurrentArea+".SpawnLocation.Y"), PlayerConfig.getInt(CurrentArea+".SpawnLocation.Z"),PlayerConfig.getInt(CurrentArea+".SpawnLocation.Yaw"),PlayerConfig.getInt(CurrentArea+".SpawnLocation.Pitch"));
					player.teleport(loc);
					return;
				}
			}
		}
		if(Bukkit.getWorld("world")!=null)
		{
			player.teleport(Bukkit.getWorld("world").getSpawnLocation());
			return;
		}
			
		for(int count =0; count < Bukkit.getWorlds().size(); count++)
		{
			if(Bukkit.getWorlds().get(count).getName().compareTo("world")==0)
			{
				player.teleport(Bukkit.getWorlds().get(count).getSpawnLocation());
				return;
			}
		}
		for(int count =0; count < Bukkit.getWorlds().size(); count++)
			if(Bukkit.getWorlds().get(count).getName().compareTo("Dungeon")!=0)
				player.teleport(Bukkit.getWorlds().get(count).getSpawnLocation());
	}
}