package GBD.GoldBigDragon_Advanced.ETC;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;


public class Area
{
	public void CreateNewArea(Player player, Location loc1, Location loc2, String name)
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		YamlManager AreaList = Event_YC.getNewConfig("Area/AreaList.yml");
		
		Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

		for(int count =0; count <arealist.length;count++)
		{
			if(arealist[count].toString().equalsIgnoreCase(name) == true)
			{
				new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름은 이미 등록되어 있습니다!");
				return;
			}
		}
		if(loc1.getWorld().equals(loc2.getWorld()) == false)
		{
			new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 서로 다른 월드간은 영역 지정을 할 수 없습니다!");
			return;
		}
		AreaList.set(name+".World", loc1.getWorld().getName());
		if(loc1.getX() > loc2.getX())
		{
			AreaList.set(name+".X.Min", loc2.getX());
			AreaList.set(name+".X.Max", loc1.getX());
			AreaList.set(name+".SpawnLocation.X", loc2.getX());
		}
		else
		{
			AreaList.set(name+".X.Min", loc1.getX());
			AreaList.set(name+".X.Max", loc2.getX());
			AreaList.set(name+".SpawnLocation.X", loc1.getX());
		}
		if(loc1.getY() > loc2.getY())
		{
			AreaList.set(name+".Y.Min", loc2.getY());
			AreaList.set(name+".Y.Max", loc1.getY());
			AreaList.set(name+".SpawnLocation.Y", loc2.getY());
		}
		else
		{
			AreaList.set(name+".Y.Min", loc1.getY());
			AreaList.set(name+".Y.Max", loc2.getY());
			AreaList.set(name+".SpawnLocation.Y", loc1.getY());
		}
		if(loc1.getZ() > loc2.getZ())
		{
			AreaList.set(name+".Z.Min", loc2.getZ());
			AreaList.set(name+".Z.Max", loc1.getZ());
			AreaList.set(name+".SpawnLocation.Z", loc2.getZ());
		}
		else
		{
			AreaList.set(name+".Z.Min", loc1.getZ());
			AreaList.set(name+".Z.Max", loc2.getZ());
			AreaList.set(name+".SpawnLocation.Z", loc1.getZ());
		}
		AreaList.set(name+".SpawnLocation.Pitch", 0);
		AreaList.set(name+".SpawnLocation.Yaw", 0);
		AreaList.set(name+".Name", name);
		AreaList.set(name+".Description",name + "에 오신 것을 환영합니다.");
		AreaList.set(name+".PVP", false);
		AreaList.set(name+".BlockPlace", false);
		AreaList.set(name+".BlockBreak", false);
		AreaList.set(name+".SpawnPoint", true);
		AreaList.set(name+".MobSpawn", false);
		AreaList.set(name+".Alert", true);
		AreaList.set(name+".Music", false);
		AreaList.set(name+".BGM", 0);
		AreaList.set(name+".Priority", 5);
		AreaList.set(name+".Monster.1", null);
		AreaList.set(name+".MonsterSpawnRule.1", null);
		AreaList.set(name+".Fishing.54.1", null);
		AreaList.set(name+".Fishing.30.1", null);
		AreaList.set(name+".Fishing.10.1", null);
		AreaList.set(name+".Fishing.5.1", null);
		AreaList.set(name+".Fishing.1.1", null);
		AreaList.set(name+".Mining.1", null);
		AreaList.saveConfig();
		
		new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.CHICKEN_EGG_POP, 2.0F, 1.7F);
		player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 지정 구역 등록 성공!");
		GBD.GoldBigDragon_Advanced.GUI.AreaGUI AGUI = new GBD.GoldBigDragon_Advanced.GUI.AreaGUI();
		AGUI.AreaGUI_Main(player, name);
		return;
	}
	
	public void RemoveArea(Player player, String name)
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		YamlManager AreaList = Event_YC.getNewConfig("Area/AreaList.yml");
		
		Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

		for(int count =0; count <arealist.length;count++)
		{
			if(arealist[count].toString().equalsIgnoreCase(name) == true)
			{
				AreaList.removeKey(name+".World");
				AreaList.removeKey(name+".X.Min");
				AreaList.removeKey(name+".X.Max");
				AreaList.removeKey(name+".Y.Min");
				AreaList.removeKey(name+".Y.Max");
				AreaList.removeKey(name+".Z.Min");
				AreaList.removeKey(name+".Z.Max");
				AreaList.removeKey(name+".Name");
				AreaList.removeKey(name+".SpawnLocation.X");
				AreaList.removeKey(name+".SpawnLocation.Y");
				AreaList.removeKey(name+".SpawnLocation.Z");
				AreaList.removeKey(name+".SpawnLocation.Pitch");
				AreaList.removeKey(name+".SpawnLocation.Yaw");
				AreaList.removeKey(name+".Description");
				AreaList.removeKey(name+".PVP");
				AreaList.removeKey(name+".BlockBreak");
				AreaList.removeKey(name+".BlockPlace");
				AreaList.removeKey(name+".SpawnPoint");
				AreaList.removeKey(name+".MobSpawn");
				AreaList.removeKey(name+".Alert");
				AreaList.removeKey(name);
				AreaList.saveConfig();
				new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.CHICKEN_EGG_POP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 지정 구역 삭제 성공!");
				return;
			}
		}
		new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
		return;
	}

	public void AreaListPrint(Player player)
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		YamlManager AreaList = Event_YC.getNewConfig("Area/AreaList.yml");
		
		Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

		if(arealist.length <= 0)
		{
			new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 생성된 영역이 없습니다!");
			return;
		}
		player.sendMessage(ChatColor.GREEN + "┌────────영역 목록────────┐");
		for(int count =0; count <arealist.length;count++)
		{
			player.sendMessage(ChatColor.GREEN +"  "+ arealist[count].toString());
		}
		player.sendMessage(ChatColor.GREEN + "└────────영역 목록────────┘");
		new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		return;
	}
	
	public void OptionSetting(Player player, String AreaName,char type,String string)
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		YamlManager AreaList = Event_YC.getNewConfig("Area/AreaList.yml");
		
		Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

		for(int count =0; count <arealist.length;count++)
		{
			if(arealist[count].toString().equalsIgnoreCase(AreaName) == true)
			{
				switch(type)
				{
				case 0:
					{
						AreaList.set(AreaName+".Name", string);
						player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW + AreaName+ChatColor.GREEN+" 영역의 이름이 "+ChatColor.YELLOW + string+ChatColor.GREEN+ " 으로 변경 되었습니다!");
					}
					break;
				case 1:
					{
						AreaList.set(AreaName+".Description", string);
						player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW + AreaName+ChatColor.GREEN+" 영역의 설명이 "+ChatColor.YELLOW + string+ChatColor.GREEN+ " 으로 변경 되었습니다!");
					}
					break;
				}
				AreaList.saveConfig();
				return;
			}
		}
		new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
		return;
	}
	
	public String getAreaName(Player player)
	{
		YamlController Location_YC = GBD.GoldBigDragon_Advanced.Main.Main.Location_YC;
		YamlManager AreaList = Location_YC.getNewConfig("Area/AreaList.yml");
		
		Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

		String TouchedArea = "n";
		for(int count =0; count <arealist.length;count++)
			if(AreaList.getString(arealist[count] + ".World").equalsIgnoreCase((player.getLocation().getWorld().getName())))
				if(AreaList.getInt(arealist[count]+".X.Min") <= player.getLocation().getX() && AreaList.getInt(arealist[count]+".X.Max") >= player.getLocation().getX())
					if(AreaList.getInt(arealist[count]+".Y.Min") <= player.getLocation().getY() && AreaList.getInt(arealist[count]+".Y.Max") >= player.getLocation().getY())
						if(AreaList.getInt(arealist[count]+".Z.Min") <= player.getLocation().getZ() && AreaList.getInt(arealist[count]+".Z.Max") >= player.getLocation().getZ())
							TouchedArea = TouchedArea+"%×%"+arealist[count].toString();
		if(TouchedArea.compareTo("n")==0)
			return null;
		String[] AreaSet = TouchedArea.split("%×%");
		if(AreaSet.length > 2)
		{
			String ret = AreaSet[1];
			for(int count =1; count <AreaSet.length;count++)
			{
				if(AreaList.contains(ret+".Priority")==false)
				{
					AreaList.set(ret+".Priority", 5);
					AreaList.saveConfig();
				}
				if(AreaList.contains(AreaSet[count]+".Priority")==false)
				{
					AreaList.set(AreaSet[count]+".Priority", 5);
					AreaList.saveConfig();
				}
				if(AreaList.getInt(AreaSet[count]+".Priority") >
					AreaList.getInt(ret+".Priority"))
				{
					ret = AreaSet[count];
				}
			}
			return ret;
		}
		else if(AreaSet.length == 2)
			return AreaSet[1];
		return null;
	}

	public String[] getAreaName(Entity entity)
	{
		YamlController Location_YC = GBD.GoldBigDragon_Advanced.Main.Main.Location_YC;
		YamlManager AreaList = Location_YC.getNewConfig("Area/AreaList.yml");
		
		Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

		String TouchedArea = "n";
		for(int count =0; count <arealist.length;count++)
			if(AreaList.getString(arealist[count] + ".World").equalsIgnoreCase((entity.getLocation().getWorld().getName())))
				if(AreaList.getInt(arealist[count]+".X.Min") <= entity.getLocation().getX() && AreaList.getInt(arealist[count]+".X.Max") >= entity.getLocation().getX())
					if(AreaList.getInt(arealist[count]+".Y.Min") <= entity.getLocation().getY() && AreaList.getInt(arealist[count]+".Y.Max") >= entity.getLocation().getY())
						if(AreaList.getInt(arealist[count]+".Z.Min") <= entity.getLocation().getZ() && AreaList.getInt(arealist[count]+".Z.Max") >= entity.getLocation().getZ())
							TouchedArea = TouchedArea+"%×%"+arealist[count].toString();
		
		String[] AreaSet = TouchedArea.split("%×%");
		if(AreaSet.length == 2)
		{
			String[] ret = new String[2];
			ret[0] = AreaSet[1];
			ret[1] = AreaList.getString(AreaSet[1]+".Name");
			return ret;
		}
		else if(AreaSet.length > 2)
		{
			String[] ret = new String[2];
			ret[0] = AreaSet[1];
			ret[1] = AreaList.getString(AreaSet[1]+".Name");
			for(int count =2; count <AreaSet.length;count++)
			{
				if(AreaList.contains(ret[0]+".Priority")==false)
				{
					AreaList.set(ret[0]+".Priority", 5);
					AreaList.saveConfig();
				}
				if(AreaList.contains(AreaSet[count]+".Priority")==false)
				{
					AreaList.set(AreaSet[count]+".Priority", 5);
					AreaList.saveConfig();
				}
				if(AreaList.getInt(AreaSet[count]+".Priority") >
					AreaList.getInt(ret[0]+".Priority"))
				{
					ret[0] = AreaSet[count];
					ret[1] = AreaList.getString(AreaSet[count]+".Name");
				}
			}
			return ret;
		}
		return null;
	}
	
	public boolean getAreaOption(String AreaName,char type)
	{
		YamlController Location_YC = GBD.GoldBigDragon_Advanced.Main.Main.Location_YC;
		YamlManager AreaList = Location_YC.getNewConfig("Area/AreaList.yml");
		
		switch(type)
		{
		case 0:
			return AreaList.getBoolean(AreaName + ".PVP");
		case 1:
			return AreaList.getBoolean(AreaName + ".BlockBreak");
		case 2:
			return  AreaList.getBoolean(AreaName + ".SpawnPoint");
		case 3:
			return AreaList.getBoolean(AreaName + ".MobSpawn");
		case 4:
			return AreaList.getBoolean(AreaName + ".Alert");
		case 5:
			return AreaList.getBoolean(AreaName + ".BlockPlace");
		case 6:
			return AreaList.getBoolean(AreaName + ".Music");
		}
		return false;
	}
	
	public void sendAreaTitle(Player player, String AreaName)
	{
		YamlController Location_YC = GBD.GoldBigDragon_Advanced.Main.Main.Location_YC;
		YamlManager AreaList = Location_YC.getNewConfig("Area/AreaList.yml");
		if(getAreaOption(AreaName, (char)4) == true)
		{
			String Title = AreaList.getString(AreaName+".Name").replace("%player%", player.getName());
			String Description = AreaList.getString(AreaName+".Description").replace("%player%", player.getName());
			new GBD.GoldBigDragon_Advanced.Effect.PacketSender().sendTitleSubTitle(player, "\'"+Title+"\'", "\'"+Description+"\'", 1, 10, 1);
		}
		return;
	}
	
	public String[] getAreaName(Block block)
	{
		YamlController Location_YC = GBD.GoldBigDragon_Advanced.Main.Main.Location_YC;
		YamlManager AreaList = Location_YC.getNewConfig("Area/AreaList.yml");
		
		Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

		for(int count =0; count <arealist.length;count++)
		{
			if(AreaList.getString(arealist[count] + ".World").equalsIgnoreCase((block.getLocation().getWorld().getName())))
			if(AreaList.getInt(arealist[count]+".X.Min") <= block.getLocation().getX() && AreaList.getInt(arealist[count]+".X.Max") >= block.getLocation().getX())
			{
				if(AreaList.getInt(arealist[count]+".Y.Min") <= block.getLocation().getY() && AreaList.getInt(arealist[count]+".Y.Max") >= block.getLocation().getY())
				{
					if(AreaList.getInt(arealist[count]+".Z.Min") <= block.getLocation().getZ() && AreaList.getInt(arealist[count]+".Z.Max") >= block.getLocation().getZ())
					{
						String[] ret = new String[2];
						ret[0] = arealist[count].toString();
						ret[1] = AreaList.getString(arealist[count].toString()+".Name");
						return ret;
					}
				}
			}
		}
		return null;
	}

	public void FishingSettingInventoryClose(InventoryCloseEvent event)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager AreaConfig =GUI_YC.getNewConfig("Area/AreaList.yml");
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		int loc = 0;
		for(int count = 1; count < 9; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.54."+loc, event.getInventory().getItem(count));
				loc=loc+1;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.54."+loc);
			AreaConfig.saveConfig();
		}
		loc = 0;
		for(int count = 10; count < 18; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.30."+loc, event.getInventory().getItem(count));
				loc=loc+1;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.30."+loc);
			AreaConfig.saveConfig();
		}
		loc = 0;
		for(int count = 19; count < 27; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.10."+loc, event.getInventory().getItem(count));
				loc=loc+1;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.10."+loc);
			AreaConfig.saveConfig();
		}
		loc = 0;
		for(int count = 28; count < 36; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.5."+loc, event.getInventory().getItem(count));
				loc=loc+1;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.5."+loc);
			AreaConfig.saveConfig();
		}
		loc = 0;
		for(int count = 37; count < 45; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.1."+loc, event.getInventory().getItem(count));
				loc=loc+1;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.1."+loc);
			AreaConfig.saveConfig();
		}
		for(int count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.54."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.54."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.54."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.54."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
	
		short line1 = 0;
		short line2 = 0;
		short line3 = 0;
		short line4 = 0;
		short line5 = 0;
		for(int count = 0; count < 45; count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(count>0&&count<9 )
					line1 = (short) (line1+1);
				else if(count>9&&count<18 )
					line2 = (short)(line2 +1);
				else if(count>18&&count<27 )
					line3 = (short)(line3 +1);
				else if(count>27&&count<36 )
					line4 = (short)(line4 +1);
				else if(count>36&&count<45 )
					line5 =(short) (line5 +1);
			}
		}
		for(int count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.54."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.54."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.54."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.54."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.30."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.30."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.30."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.30."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.10."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.10."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.10."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.10."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.5."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.5."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.5."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.5."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.1."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.1."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.1."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.1."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(int count = line1; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.54."+count))
				AreaConfig.removeKey(AreaName+".Fishing.54."+count);
		for(int count = line2; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.30."+count))
				AreaConfig.removeKey(AreaName+".Fishing.30."+count);
		for(int count = line3; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.10."+count))
				AreaConfig.removeKey(AreaName+".Fishing.10."+count);
		for(int count = line4; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.5."+count))
				AreaConfig.removeKey(AreaName+".Fishing.5."+count);
		for(int count = line5; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.1."+count))
				AreaConfig.removeKey(AreaName+".Fishing.1."+count);
		AreaConfig.saveConfig();
		return;
	}
	
	public void BlockItemSettingInventoryClose(InventoryCloseEvent event)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager AreaConfig =GUI_YC.getNewConfig("Area/AreaList.yml");
		String ItemData = ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1));
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1));
		if(event.getInventory().getItem(4) != null)
			AreaConfig.set(AreaName+".Mining."+ItemData, event.getInventory().getItem(4));
		else
		{
			AreaConfig.removeKey(AreaName+".Mining."+ItemData);
			AreaConfig.set(AreaName+".Mining."+ItemData,"0:0");
		}
		AreaConfig.saveConfig();
		return;
	}

	public void AreaMonsterSpawnAdd(String AreaName, String Count)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager AreaConfig =GUI_YC.getNewConfig("Area/AreaList.yml");
		if(Long.parseLong(Count)!=-1)
		{
			if(AreaConfig.contains(AreaName+".MonsterSpawnRule")==true)
			{
				if(AreaConfig.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).size()!=0)
				{
					if(AreaConfig.getString(AreaName+".MonsterSpawnRule."+Count+".loc.world")!=null)
					{
						if(GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.contains(AreaName)==false)
							GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.add(AreaName);
						
						Long UTC = GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.nowUTC+5;
						for(;;)
						{
							if(GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.containsKey(UTC))
								UTC=UTC+1;
							else
								break;
						}
						GBD.GoldBigDragon_Advanced.ServerTick.ServerTickScheduleObject OBJECT = new GBD.GoldBigDragon_Advanced.ServerTick.ServerTickScheduleObject(UTC, "A_MS");
						OBJECT.setString((byte)0, AreaName);
						OBJECT.setString((byte)1, AreaConfig.getString(AreaName+".MonsterSpawnRule."+Count+".loc.world"));
						if(AreaConfig.contains(AreaName+".MonsterSpawnRule."+Count+".Monster"))
							OBJECT.setString((byte)2, AreaConfig.getString(AreaName+".MonsterSpawnRule."+Count+".Monster"));
						OBJECT.setString((byte)3, Count);
						OBJECT.setInt((byte)0, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+Count+".loc.x"));
						OBJECT.setInt((byte)1, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+Count+".loc.y"));
						OBJECT.setInt((byte)2, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+Count+".loc.z"));
						OBJECT.setInt((byte)3, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+Count+".range"));
						OBJECT.setInt((byte)4, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+Count+".count"));
						OBJECT.setInt((byte)5, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+Count+".max"));
						OBJECT.setMaxCount(AreaConfig.getInt(AreaName+".MonsterSpawnRule."+Count+".timer"));
						GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.put(UTC, OBJECT);
					}
				}
			}
		}
		else
		{
			if(AreaConfig.contains(AreaName+".MonsterSpawnRule")==true)
			{
				if(AreaConfig.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).size()!=0)
				{
					if(GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.contains(AreaName)==false)
					{
						GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.add(AreaName);
						Object[] RuleName = AreaConfig.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).toArray();
						for(int count=0;count<RuleName.length;count++)
						{
							String ruleNumber = RuleName[count].toString();
							if(AreaConfig.getString(AreaName+".MonsterSpawnRule."+ruleNumber+".loc.world")!=null)
							{
								Long UTC = GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.nowUTC+5;
								for(;;)
								{
									if(GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.containsKey(UTC))
										UTC=UTC+1;
									else
										break;
								}
								GBD.GoldBigDragon_Advanced.ServerTick.ServerTickScheduleObject OBJECT = new GBD.GoldBigDragon_Advanced.ServerTick.ServerTickScheduleObject(UTC, "A_MS");
								OBJECT.setString((byte)0, AreaName);
								OBJECT.setString((byte)1, AreaConfig.getString(AreaName+".MonsterSpawnRule."+ruleNumber+".loc.world"));
								if(AreaConfig.contains(AreaName+".MonsterSpawnRule."+ruleNumber+".Monster"))
									OBJECT.setString((byte)2, AreaConfig.getString(AreaName+".MonsterSpawnRule."+ruleNumber+".Monster"));
								OBJECT.setString((byte)3, ruleNumber);
								OBJECT.setInt((byte)0, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+ruleNumber+".loc.x"));
								OBJECT.setInt((byte)1, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+ruleNumber+".loc.y"));
								OBJECT.setInt((byte)2, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+ruleNumber+".loc.z"));
								OBJECT.setInt((byte)3, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+ruleNumber+".range"));
								OBJECT.setInt((byte)4, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+ruleNumber+".count"));
								OBJECT.setInt((byte)5, AreaConfig.getInt(AreaName+".MonsterSpawnRule."+ruleNumber+".max"));
								OBJECT.setMaxCount(AreaConfig.getInt(AreaName+".MonsterSpawnRule."+ruleNumber+".timer"));
								GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.put(UTC, OBJECT);
							}
						}
					}
				}
			}
		}
		return;
	}
}