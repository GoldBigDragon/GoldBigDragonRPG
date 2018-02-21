package util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import effect.SoundEffect;
import main.MainServerOption;

public class UtilPlayer
{
	public void dungeonClear(Player player, long money, long exp)
	{
		main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(money, exp, false);
		SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.5F, 1.8F);
		player.sendMessage("§e§l[던전 클리어 보상] : §b§l[경험치] "+ exp + " §e§l[§f"+MainServerOption.money+"§e§l] "+ money);
	}
	
	public void addMoneyAndEXP(Player player, long money, long exp, Location loc, boolean givePartyMemberToo, boolean isDungeonClear)
	{
	    if(exp * MainServerOption.eventExp > Long.MAX_VALUE)
	    	exp = Long.MAX_VALUE;
	    else if(exp * MainServerOption.eventExp < Long.MIN_VALUE)
	    	exp = Long.MIN_VALUE;
	    else
	    	exp = exp * MainServerOption.eventExp;
		if( ! main.MainServerOption.partyJoiner.containsKey(player))
			main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(money, exp, true);
		else if(givePartyMemberToo)
		{
		    Player[] party = main.MainServerOption.party.get(main.MainServerOption.partyJoiner.get(player)).getMember();
			byte partymember=0;
			for(int count = 0; count<party.length;count++)
		    	if(party[count].isOnline())
					if(party[count].getLocation().getWorld() == loc.getWorld())
						if(loc.distance(party[count].getLocation()) <= MainServerOption.expShareDistance)
							partymember++;
			money = (money/partymember);
			exp = (exp/partymember);
			
			for(int count = 0; count<party.length;count++)
			{
		    	if(party[count].isOnline())
					if(party[count].getLocation().getWorld() == loc.getWorld())
						if(loc.distance(party[count].getLocation()) <= MainServerOption.expShareDistance)
							main.MainServerOption.PlayerList.get(party[count].getUniqueId().toString()).addStat_MoneyAndEXP(money, exp, true);
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
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c§l[SYSTEM] : 인벤토리가 부족하여 우편함으로 아이템을 발송하였습니다!");
				new structure.StructPostBox().SendPost_Server(player.getUniqueId().toString(), "[시스템]", "[인벤토리 부족]", "인벤토리가 부족하여 우편으로 아이템이 배송되었습니다.", item);
			}
			else
				player.getInventory().addItem(item);
		}
		else
			new structure.StructPostBox().SendPost_Server(player.getUniqueId().toString(), "[시스템]", "[인벤토리 부족]", "인벤토리가 부족하여 우편으로 아이템이 배송되었습니다.", item);
		return;
	}
	
	public void giveItemDrop(Player player, ItemStack item, Location loc)
	{
		if(player.getInventory().firstEmpty() == -1)
			new event.EventItemDrop().CustomItemDrop(loc, item);
		else
			player.getInventory().addItem(item);
	}
	
	public boolean deleteItem(Player player, ItemStack item, int amount)
	{
		if(amount == -1)
		{
			ItemStack originalItem = new ItemStack(item);
			originalItem.setAmount(1);
			for(int count = 0; count < player.getInventory().getSize(); count++)
			{
				if(player.getInventory().getItem(count)!=null)
				{
					ItemStack attatchedItem = new ItemStack(player.getInventory().getItem(count));
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
		
		if(totalItemAmount < amount * originalItemAmount)
			return false;
		else
		{
			amount = amount * originalItemAmount;
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
						if(totalItemAmount+attatchedItemAmount <= amount)
						{
							player.getInventory().setItem(count, null);
							totalItemAmount = totalItemAmount+attatchedItemAmount;
						}
						else if(totalItemAmount+attatchedItemAmount > amount)
						{
							attatchedItem.setAmount(attatchedItemAmount-(amount-totalItemAmount));
							player.getInventory().setItem(count, attatchedItem);
							return true;
						}
					}
				}
				if(totalItemAmount==amount)
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
				attatchedItem.setAmount(1);
				if(attatchedItem.equals(originalItem))
				{
					new event.EventItemDrop().CustomItemDrop(player.getLocation().add(0,2,0), player.getInventory().getItem(count));
					if(isKeyDrop)
						SoundEffect.playSoundLocation(player.getLocation().add(0,2,0), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5F, 1.8F);
					else
						SoundEffect.playSoundLocation(player.getLocation().add(0,2,0), Sound.BLOCK_LAVA_POP, 1.5F, 1.8F);
					player.getInventory().setItem(count, null);
				}
			}
		}
	}

	public boolean teleportToCurrentArea(Player player, boolean isSearchforLastVisitedToo)
	{
		String[] worldList = main.MainServerOption.AreaList.keySet().toArray(new String[0]);
	  	YamlLoader playerConfig = new YamlLoader();
		for(int count = 0; count < worldList.length; count++)
		{
			if(main.MainServerOption.AreaList.containsKey(worldList[count]))
			{
				if(main.MainServerOption.AreaList.get(worldList[count]) != null)
				{
					String currentArea = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_CurrentArea();
					for(int count2 = 0; count2 < main.MainServerOption.AreaList.get(worldList[count]).size(); count2++)
					{
						String[] worldList2 = main.MainServerOption.AreaList.get(worldList[count]).toArray(new String[0]);
						if(worldList2[count2].equals(currentArea))
						{
							playerConfig.getConfig("Area/AreaList.yml");
							Location loc = new Location(Bukkit.getServer().getWorld(playerConfig.getString(currentArea+".World")), playerConfig.getInt(currentArea+".SpawnLocation.X"), playerConfig.getInt(currentArea+".SpawnLocation.Y"), playerConfig.getInt(currentArea+".SpawnLocation.Z"),playerConfig.getInt(currentArea+".SpawnLocation.Yaw"),playerConfig.getInt(currentArea+".SpawnLocation.Pitch"));
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
	  	YamlLoader playerConfig = new YamlLoader();
		String[] worldList = main.MainServerOption.AreaList.keySet().toArray(new String[0]);
		for(int count = 0; count < worldList.length; count++)
		{
			String currentArea = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited();
			for(int count2 = 0; count2 < main.MainServerOption.AreaList.get(worldList[count]).size(); count2++)
			{
				String[] worldList2 = main.MainServerOption.AreaList.get(worldList[count]).toArray(new String[0]);
				if(worldList2[count2].toString().equals(currentArea))
				{
					playerConfig.getConfig("Area/AreaList.yml");
					Location loc = new Location(Bukkit.getServer().getWorld(playerConfig.getString(currentArea+".World")), playerConfig.getInt(currentArea+".SpawnLocation.X"), playerConfig.getInt(currentArea+".SpawnLocation.Y"), playerConfig.getInt(currentArea+".SpawnLocation.Z"),playerConfig.getInt(currentArea+".SpawnLocation.Yaw"),playerConfig.getInt(currentArea+".SpawnLocation.Pitch"));
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
			if(Bukkit.getWorlds().get(count).getName().equals("world"))
			{
				player.teleport(Bukkit.getWorlds().get(count).getSpawnLocation());
				return;
			}
		}
		for(int count =0; count < Bukkit.getWorlds().size(); count++)
			if(!Bukkit.getWorlds().get(count).getName().equals("Dungeon"))
				player.teleport(Bukkit.getWorlds().get(count).getSpawnLocation());
	}
}