package GoldBigDragon_RPG.ETC;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;


public class Area
{
	public void addAreaList()
	{
		GoldBigDragon_RPG.Main.ServerOption.AreaList.clear();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");

		Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

		for(short count =0; count <arealist.length;count++)
		{
			GoldBigDragon_RPG.Main.Object_Area AO = new GoldBigDragon_RPG.Main.Object_Area();
			AO.setAreaName(arealist[count].toString());
			AO.setMinX(AreaList.getInt(arealist[count].toString()+".X.Min"));
			AO.setMaxX(AreaList.getInt(arealist[count].toString()+".X.Max"));
			AO.setMinY(AreaList.getInt(arealist[count].toString()+".Y.Min"));
			AO.setMaxY(AreaList.getInt(arealist[count].toString()+".Y.Max"));
			AO.setMinZ(AreaList.getInt(arealist[count].toString()+".Z.Min"));
			AO.setMaxZ(AreaList.getInt(arealist[count].toString()+".Z.Max"));
			if(GoldBigDragon_RPG.Main.ServerOption.AreaList.containsKey(AreaList.getString(arealist[count].toString()+".World")))
			{
				ArrayList<GoldBigDragon_RPG.Main.Object_Area> areaList = GoldBigDragon_RPG.Main.ServerOption.AreaList.get(AreaList.getString(arealist[count].toString()+".World"));
				areaList.add(AO);
				GoldBigDragon_RPG.Main.ServerOption.AreaList.remove(AreaList.getString(arealist[count].toString()+".World"));
				GoldBigDragon_RPG.Main.ServerOption.AreaList.put(AreaList.getString(arealist[count].toString()+".World"), areaList);
			}
			else
			{
				ArrayList<GoldBigDragon_RPG.Main.Object_Area> areaList = new ArrayList<GoldBigDragon_RPG.Main.Object_Area>();
				areaList.add(AO);
				GoldBigDragon_RPG.Main.ServerOption.AreaList.put(AreaList.getString(arealist[count].toString()+".World"), areaList);
			}
		}
	}

	public void CreateNewArea(Player player, Location loc1, Location loc2, String name)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
		
		if(AreaList.contains(name) == true)
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름은 이미 등록되어 있습니다!");
			return;
		}
		
		if(loc1.getWorld().equals(loc2.getWorld()) == false)
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 서로 다른 월드간은 영역 지정을 할 수 없습니다!");
			return;
		}
		loc1.add(1, 0, 0);
		loc2.add(0, 0, 1);
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
		AreaList.set(name+".BlockUse", false);
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
		
		new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.CHICKEN_EGG_POP, 2.0F, 1.7F);
		player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 지정 구역 등록 성공!");
		GoldBigDragon_RPG.GUI.AreaGUI AGUI = new GoldBigDragon_RPG.GUI.AreaGUI();
		AGUI.AreaGUI_Main(player, name);
		GoldBigDragon_RPG.Main.Object_Area AO = new GoldBigDragon_RPG.Main.Object_Area();
		AO.setAreaName(name);
		AO.setMinX(AreaList.getInt(name+".X.Min"));
		AO.setMaxX(AreaList.getInt(name+".X.Max"));
		AO.setMinY(AreaList.getInt(name+".Y.Min"));
		AO.setMaxY(AreaList.getInt(name+".Y.Max"));
		AO.setMinZ(AreaList.getInt(name+".Z.Min"));
		AO.setMaxZ(AreaList.getInt(name+".Z.Max"));
		if(GoldBigDragon_RPG.Main.ServerOption.AreaList.containsKey(AreaList.getString(name+".World")))
		{
			ArrayList<GoldBigDragon_RPG.Main.Object_Area> areaList = GoldBigDragon_RPG.Main.ServerOption.AreaList.get(AreaList.getString(name+".World"));
			areaList.add(AO);
			GoldBigDragon_RPG.Main.ServerOption.AreaList.remove(AreaList.getString(name+".World"));
			GoldBigDragon_RPG.Main.ServerOption.AreaList.put(AreaList.getString(name+".World"), areaList);
		}
		else
		{
			ArrayList<GoldBigDragon_RPG.Main.Object_Area> areaList = new ArrayList<GoldBigDragon_RPG.Main.Object_Area>();
			areaList.add(AO);
			GoldBigDragon_RPG.Main.ServerOption.AreaList.put(AreaList.getString(name+".World"), areaList);
		}
		return;
	}
	
	public void RemoveArea(Player player, String name)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
		
		if(GoldBigDragon_RPG.Main.ServerOption.AreaList.get(AreaList.getString(name+".World")).size()==1)
			GoldBigDragon_RPG.Main.ServerOption.AreaList.remove(AreaList.getString(name+".World"));
		else
		{
			ArrayList<GoldBigDragon_RPG.Main.Object_Area> areaList = GoldBigDragon_RPG.Main.ServerOption.AreaList.get(AreaList.getString(name+".World"));
			for(short count = 0; count < areaList.size(); count ++)
			{
				if(areaList.get(count).toString().compareTo(name)==0)
				{
					areaList.remove(count);
					break;
				}
			}
			GoldBigDragon_RPG.Main.ServerOption.AreaList.put(AreaList.getString(name+".World"), areaList);
		}
		
		if(AreaList.contains(name))
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
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.CHICKEN_EGG_POP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 지정 구역 삭제 성공!");
		}
		else
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
		}
		return;
	}
	
	public void OptionSetting(Player player, String AreaName,char type,String string)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
		
		if(AreaList.contains(AreaName))
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
		}
		else
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
		}
		return;
	}
	
	public String[] SearchAreaName(Location loc)
	{
		if(GoldBigDragon_RPG.Main.ServerOption.AreaList.containsKey(loc.getWorld().getName()))
		{
			ArrayList<String> AreaName = new ArrayList<String>();
			ArrayList<GoldBigDragon_RPG.Main.Object_Area> AreaList = GoldBigDragon_RPG.Main.ServerOption.AreaList.get(loc.getWorld().getName());
			for(short count = 0; count < AreaList.size(); count++)
			{
				if(AreaList.get(count).getMinX() <= loc.getX() && AreaList.get(count).getMaxX() >= loc.getX())
					if(AreaList.get(count).getMinY() <= loc.getY() && AreaList.get(count).getMaxY()>= loc.getY())
						if(AreaList.get(count).getMinZ() <= loc.getZ() && AreaList.get(count).getMaxZ() >= loc.getZ())
							AreaName.add(AreaList.get(count).getAreaName());
			}
			
			if(AreaName.isEmpty())
				return null;
			else
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager AreaYM = YC.getNewConfig("Area/AreaList.yml");

				int TopPriority = -1000;

				String[] ret = new String[2];
				for(short count =0; count < AreaName.size(); count++)
				{
					if(AreaYM.contains(AreaName.get(count) + ".Priority")==false)
					{
						AreaYM.set(AreaName.get(count) + ".Priority", 5);
						AreaYM.saveConfig();
					}
					int AreaPriority = AreaYM.getInt(AreaName.get(count)+".Priority");
					if(AreaPriority >= TopPriority)
					{
						ret[0] = AreaName.get(count);
						TopPriority = AreaPriority;
					}
				}
				ret[1] = AreaYM.getString(ret[0] + ".Name");
				return ret;
			}
		}
		return null;
	}
	
	public String[] getAreaName(Player player)
	{
		return SearchAreaName(player.getLocation());
	}

	public String[] getAreaName(Entity entity)
	{
		return SearchAreaName(entity.getLocation());
	}
	
	public String[] getAreaName(Block block)
	{
		return SearchAreaName(block.getLocation());
	}

	public boolean getAreaOption(String AreaName,char type)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
		
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
		case 7:
			return AreaList.getBoolean(AreaName + ".BlockUse");
		case 8:
		{
			if(AreaList.contains(AreaName + ".MonsterSpawnRule"))
			{
				if(AreaList.getConfigurationSection(AreaName + ".MonsterSpawnRule").getKeys(false).size() <= 0)
					return false;
				else
					return true;
			}
			else
				return false;
		}
		}
		return false;
	}
	
	public void sendAreaTitle(Player player, String AreaName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
		if(getAreaOption(AreaName, (char)4) == true)
		{
			String Title = AreaList.getString(AreaName+".Name").replace("%player%", player.getName());
			String Description = AreaList.getString(AreaName+".Description").replace("%player%", player.getName());
			new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(player, "\'"+Title+"\'", "\'"+Description+"\'", (byte)1, (byte)10, (byte)1);
		}
		return;
	}

	public void FishingSettingInventoryClose(InventoryCloseEvent event)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		byte loc = 0;
		for(byte count = 1; count < 9; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.54."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.54."+loc);
			AreaConfig.saveConfig();
		}
		loc = 0;
		for(byte count = 10; count < 18; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.30."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.30."+loc);
			AreaConfig.saveConfig();
		}
		loc = 0;
		for(byte count = 19; count < 27; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.10."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.10."+loc);
			AreaConfig.saveConfig();
		}
		loc = 0;
		for(byte count = 28; count < 36; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.5."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.5."+loc);
			AreaConfig.saveConfig();
		}
		loc = 0;
		for(byte count = 37; count < 45; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				AreaConfig.set(AreaName + ".Fishing.1."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				AreaConfig.removeKey(AreaName+".Fishing.1."+loc);
			AreaConfig.saveConfig();
		}
		for(byte count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.54."+count) == null)
			{
				for(byte counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.54."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.54."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.54."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
	
		byte line1 = 0;
		byte line2 = 0;
		byte line3 = 0;
		byte line4 = 0;
		byte line5 = 0;
		for(byte count = 0; count < 45; count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(count>0&&count<9 )
					line1 = (byte) (line1+1);
				else if(count>9&&count<18 )
					line2 = (byte)(line2 +1);
				else if(count>18&&count<27 )
					line3 = (byte)(line3 +1);
				else if(count>27&&count<36 )
					line4 = (byte)(line4 +1);
				else if(count>36&&count<45 )
					line5 =(byte) (line5 +1);
			}
		}
		for(byte count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.54."+count) == null)
			{
				for(byte counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.54."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.54."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.54."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(byte count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.30."+count) == null)
			{
				for(byte counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.30."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.30."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.30."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(byte count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.10."+count) == null)
			{
				for(byte counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.10."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.10."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.10."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(byte count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.5."+count) == null)
			{
				for(byte counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.5."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.5."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.5."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(byte count = 0; count <7;count++)
			if(AreaConfig.getItemStack(AreaName+".Fishing.1."+count) == null)
			{
				for(byte counter = count; counter <7; counter++)
				{
					AreaConfig.set(AreaName+".Fishing.1."+(counter), AreaConfig.getItemStack(AreaName+".Fishing.1."+(counter+1)));
					AreaConfig.removeKey(AreaName+".Fishing.1."+(counter+1));
				}
				AreaConfig.saveConfig();
			}
		for(byte count = line1; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.54."+count))
				AreaConfig.removeKey(AreaName+".Fishing.54."+count);
		for(byte count = line2; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.30."+count))
				AreaConfig.removeKey(AreaName+".Fishing.30."+count);
		for(byte count = line3; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.10."+count))
				AreaConfig.removeKey(AreaName+".Fishing.10."+count);
		for(byte count = line4; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.5."+count))
				AreaConfig.removeKey(AreaName+".Fishing.5."+count);
		for(byte count = line5; count <7;count++)
			if(AreaConfig.contains(AreaName+".Fishing.1."+count))
				AreaConfig.removeKey(AreaName+".Fishing.1."+count);
		AreaConfig.saveConfig();
		return;
	}
	
	public void BlockItemSettingInventoryClose(InventoryCloseEvent event)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		String ItemData = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(event.getInventory().getItem(4) != null)
			AreaConfig.set(AreaName+".Mining."+ItemData+".100", event.getInventory().getItem(4));
		else
		{
			AreaConfig.removeKey(AreaName+".Mining."+ItemData+".100");
			AreaConfig.set(AreaName+".Mining."+ItemData,"0:0");
		}
		if(event.getInventory().getItem(13) != null)
			AreaConfig.set(AreaName+".Mining."+ItemData+".90", event.getInventory().getItem(13));
		else
		{
			AreaConfig.removeKey(AreaName+".Mining."+ItemData+".90");
			AreaConfig.set(AreaName+".Mining."+ItemData+".90","0:0");
		}
		if(event.getInventory().getItem(22) != null)
			AreaConfig.set(AreaName+".Mining."+ItemData+".50", event.getInventory().getItem(22));
		else
		{
			AreaConfig.removeKey(AreaName+".Mining."+ItemData+".50");
			AreaConfig.set(AreaName+".Mining."+ItemData+".50","0:0");
		}
		if(event.getInventory().getItem(31) != null)
			AreaConfig.set(AreaName+".Mining."+ItemData+".10", event.getInventory().getItem(31));
		else
		{
			AreaConfig.removeKey(AreaName+".Mining."+ItemData+".10");
			AreaConfig.set(AreaName+".Mining."+ItemData+".10","0:0");
		}
		if(event.getInventory().getItem(40) != null)
			AreaConfig.set(AreaName+".Mining."+ItemData+".1", event.getInventory().getItem(40));
		else
		{
			AreaConfig.removeKey(AreaName+".Mining."+ItemData+".1");
			AreaConfig.set(AreaName+".Mining."+ItemData+".1","0:0");
		}
		if(event.getInventory().getItem(49) != null)
			AreaConfig.set(AreaName+".Mining."+ItemData+".0", event.getInventory().getItem(49));
		else
		{
			AreaConfig.removeKey(AreaName+".Mining."+ItemData+".0");
			AreaConfig.set(AreaName+".Mining."+ItemData+".0","0:0");
		}
		AreaConfig.saveConfig();
		return;
	}

	public void AreaMonsterSpawnAdd(String AreaName, String Count)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		if(Long.parseLong(Count)!=-1)
		{
			if(AreaConfig.contains(AreaName+".MonsterSpawnRule")==true)
			{
				if(AreaConfig.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).size()!=0)
				{
					if(AreaConfig.getString(AreaName+".MonsterSpawnRule."+Count+".loc.world")!=null)
					{
						if(GoldBigDragon_RPG.ServerTick.ServerTickMain.MobSpawningAreaList.contains(AreaName)==false)
							GoldBigDragon_RPG.ServerTick.ServerTickMain.MobSpawningAreaList.add(AreaName);
						
						Long UTC = GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC+5;
						for(;;)
						{
							if(GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.containsKey(UTC))
								UTC=UTC+1;
							else
								break;
						}
						GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject OBJECT = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(UTC, "A_MS");
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
						GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(UTC, OBJECT);
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
					if(GoldBigDragon_RPG.ServerTick.ServerTickMain.MobSpawningAreaList.contains(AreaName)==false)
					{
						GoldBigDragon_RPG.ServerTick.ServerTickMain.MobSpawningAreaList.add(AreaName);
						Object[] RuleName = AreaConfig.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).toArray();
						for(short count=0;count<RuleName.length;count++)
						{
							String ruleNumber = RuleName[count].toString();
							if(AreaConfig.getString(AreaName+".MonsterSpawnRule."+ruleNumber+".loc.world")!=null)
							{
								Long UTC = GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC+5;
								for(;;)
								{
									if(GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.containsKey(UTC))
										UTC=UTC+1;
									else
										break;
								}
								GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject OBJECT = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(UTC, "A_MS");
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
								GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(UTC, OBJECT);
							}
						}
					}
				}
			}
		}
		return;
	}
}