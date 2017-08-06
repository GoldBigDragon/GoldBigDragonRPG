package area;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import effect.SoundEffect;
import util.YamlLoader;



public class Area_Main
{
	public void addAreaList()
	{
		main.Main_ServerOption.AreaList.clear();
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		String[] arealist = areaYaml.getKeys().toArray(new String[0]);

		for(int count =0; count <arealist.length;count++)
		{
			area.Area_Object AO = new area.Area_Object();
			AO.AreaName = arealist[count];
			AO.minX = areaYaml.getInt(arealist[count]+".X.Min");
			AO.maxX = areaYaml.getInt(arealist[count]+".X.Max");
			AO.minY = areaYaml.getInt(arealist[count]+".Y.Min");
			AO.maxY = areaYaml.getInt(arealist[count]+".Y.Max");
			AO.minZ = areaYaml.getInt(arealist[count]+".Z.Min");
			AO.maxZ = areaYaml.getInt(arealist[count]+".Z.Max");
			if(main.Main_ServerOption.AreaList.containsKey(areaYaml.getString(arealist[count]+".World")))
			{
				ArrayList<area.Area_Object> areaList = main.Main_ServerOption.AreaList.get(areaYaml.getString(arealist[count]+".World"));
				areaList.add(AO);
				main.Main_ServerOption.AreaList.remove(areaYaml.getString(arealist[count]+".World"));
				main.Main_ServerOption.AreaList.put(areaYaml.getString(arealist[count]+".World"), areaList);
			}
			else
			{
				ArrayList<area.Area_Object> areaList = new ArrayList<area.Area_Object>();
				areaList.add(AO);
				main.Main_ServerOption.AreaList.put(areaYaml.getString(arealist[count]+".World"), areaList);
			}
		}
	}

	public void CreateNewArea(Player player, Location loc1, Location loc2, String name)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		
		if(areaYaml.contains(name) == true)
		{
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름은 이미 등록되어 있습니다!");
			return;
		}
		
		if(loc1.getWorld().equals(loc2.getWorld()) == false)
		{
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 서로 다른 월드간은 영역 지정을 할 수 없습니다!");
			return;
		}
		loc1.add(1, 0, 0);
		loc2.add(0, 0, 1);
		areaYaml.set(name+".World", loc1.getWorld().getName());
		if(loc1.getX() > loc2.getX())
		{
			areaYaml.set(name+".X.Min", loc2.getX());
			areaYaml.set(name+".X.Max", loc1.getX());
			areaYaml.set(name+".SpawnLocation.X", loc2.getX());
		}
		else
		{
			areaYaml.set(name+".X.Min", loc1.getX());
			areaYaml.set(name+".X.Max", loc2.getX());
			areaYaml.set(name+".SpawnLocation.X", loc1.getX());
		}
		if(loc1.getY() > loc2.getY())
		{
			areaYaml.set(name+".Y.Min", loc2.getY());
			areaYaml.set(name+".Y.Max", loc1.getY());
			areaYaml.set(name+".SpawnLocation.Y", loc2.getY());
		}
		else
		{
			areaYaml.set(name+".Y.Min", loc1.getY());
			areaYaml.set(name+".Y.Max", loc2.getY());
			areaYaml.set(name+".SpawnLocation.Y", loc1.getY());
		}
		if(loc1.getZ() > loc2.getZ())
		{
			areaYaml.set(name+".Z.Min", loc2.getZ());
			areaYaml.set(name+".Z.Max", loc1.getZ());
			areaYaml.set(name+".SpawnLocation.Z", loc2.getZ());
		}
		else
		{
			areaYaml.set(name+".Z.Min", loc1.getZ());
			areaYaml.set(name+".Z.Max", loc2.getZ());
			areaYaml.set(name+".SpawnLocation.Z", loc1.getZ());
		}
		areaYaml.set(name+".SpawnLocation.Pitch", 0);
		areaYaml.set(name+".SpawnLocation.Yaw", 0);
		areaYaml.set(name+".Name", name);
		areaYaml.set(name+".Description",name + "에 오신 것을 환영합니다.");
		areaYaml.set(name+".PVP", false);
		areaYaml.set(name+".BlockPlace", false);
		areaYaml.set(name+".BlockBreak", false);
		areaYaml.set(name+".BlockUse", false);
		areaYaml.set(name+".SpawnPoint", true);
		areaYaml.set(name+".MobSpawn", false);
		areaYaml.set(name+".Alert", true);
		areaYaml.set(name+".Music", false);
		areaYaml.set(name+".BGM", 0);
		areaYaml.set(name+".Priority", 5);
		areaYaml.createSection(name+".Monster");
		areaYaml.createSection(name+".MonsterSpawnRule");
		areaYaml.createSection(name+".Fishing.54");
		areaYaml.createSection(name+".Fishing.30");
		areaYaml.createSection(name+".Fishing.10");
		areaYaml.createSection(name+".Fishing.5");
		areaYaml.createSection(name+".Fishing.1");
		areaYaml.createSection(name+".Mining");
		areaYaml.set(name+".Restrict.MinNowLevel", 0);
		areaYaml.set(name+".Restrict.MaxNowLevel", 0);
		areaYaml.set(name+".Restrict.MinRealLevel", 0);
		areaYaml.set(name+".Restrict.MaxRealLevel", 0);
		areaYaml.saveConfig();
		
		SoundEffect.SP(player, org.bukkit.Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.7F);
		player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 지정 구역 등록 성공!");
		area.Area_GUI AGUI = new area.Area_GUI();
		AGUI.AreaSettingGUI(player, name);

		area.Area_Object AO = new area.Area_Object();
		AO.AreaName = name;
		AO.minX = areaYaml.getInt(name+".X.Min");
		AO.maxX = areaYaml.getInt(name+".X.Max");
		AO.minY = areaYaml.getInt(name+".Y.Min");
		AO.maxY = areaYaml.getInt(name+".Y.Max");
		AO.minZ = areaYaml.getInt(name+".Z.Min");
		AO.maxZ = areaYaml.getInt(name+".Z.Max");
		if(main.Main_ServerOption.AreaList.containsKey(areaYaml.getString(name+".World")))
		{
			ArrayList<area.Area_Object> areaList = main.Main_ServerOption.AreaList.get(areaYaml.getString(name+".World"));
			areaList.add(AO);
			main.Main_ServerOption.AreaList.remove(areaYaml.getString(name+".World"));
			main.Main_ServerOption.AreaList.put(areaYaml.getString(name+".World"), areaList);
		}
		else
		{
			ArrayList<area.Area_Object> areaList = new ArrayList<area.Area_Object>();
			areaList.add(AO);
			main.Main_ServerOption.AreaList.put(areaYaml.getString(name+".World"), areaList);
		}
		return;
	}
	
	public void RemoveArea(Player player, String name)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		
		if(main.Main_ServerOption.AreaList.get(areaYaml.getString(name+".World")).size()==1)
			main.Main_ServerOption.AreaList.remove(areaYaml.getString(name+".World"));
		else
		{
			ArrayList<area.Area_Object> areaList = main.Main_ServerOption.AreaList.get(areaYaml.getString(name+".World"));
			for(int count = 0; count < areaList.size(); count ++)
			{
				if(areaList.get(count).toString().compareTo(name)==0)
				{
					areaList.remove(count);
					break;
				}
			}
			main.Main_ServerOption.AreaList.put(areaYaml.getString(name+".World"), areaList);
		}
		
		if(areaYaml.contains(name))
		{
			areaYaml.removeKey(name+".World");
			areaYaml.removeKey(name+".X.Min");
			areaYaml.removeKey(name+".X.Max");
			areaYaml.removeKey(name+".Y.Min");
			areaYaml.removeKey(name+".Y.Max");
			areaYaml.removeKey(name+".Z.Min");
			areaYaml.removeKey(name+".Z.Max");
			areaYaml.removeKey(name+".Name");
			areaYaml.removeKey(name+".SpawnLocation.X");
			areaYaml.removeKey(name+".SpawnLocation.Y");
			areaYaml.removeKey(name+".SpawnLocation.Z");
			areaYaml.removeKey(name+".SpawnLocation.Pitch");
			areaYaml.removeKey(name+".SpawnLocation.Yaw");
			areaYaml.removeKey(name+".Description");
			areaYaml.removeKey(name+".PVP");
			areaYaml.removeKey(name+".BlockBreak");
			areaYaml.removeKey(name+".BlockPlace");
			areaYaml.removeKey(name+".SpawnPoint");
			areaYaml.removeKey(name+".MobSpawn");
			areaYaml.removeKey(name+".Alert");
			areaYaml.removeKey(name+".Restrict.MinNowLevel");
			areaYaml.removeKey(name+".Restrict.MaxNowLevel");
			areaYaml.removeKey(name+".Restrict.MinRealLevel");
			areaYaml.removeKey(name+".Restrict.MaxRealLevel");
			areaYaml.removeKey(name);
			areaYaml.saveConfig();
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 지정 구역 삭제 성공!");
		}
		else
		{
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
		}
		return;
	}
	
	public void OptionSetting(Player player, String AreaName,char type,String string)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		
		if(areaYaml.contains(AreaName))
		{
			switch(type)
			{
			case 0:
				{
					areaYaml.set(AreaName+".Name", string);
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW + AreaName+ChatColor.GREEN+" 영역의 이름이 "+ChatColor.YELLOW + string+ChatColor.GREEN+ " 으로 변경 되었습니다!");
				}
				break;
			case 1:
				{
					areaYaml.set(AreaName+".Description", string);
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.YELLOW + AreaName+ChatColor.GREEN+" 영역의 설명이 "+ChatColor.YELLOW + string+ChatColor.GREEN+ " 으로 변경 되었습니다!");
				}
				break;
			}
			areaYaml.saveConfig();
		}
		else
		{
			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
		}
		return;
	}
	
	public String[] SearchAreaName(Location loc)
	{
		if(main.Main_ServerOption.AreaList.containsKey(loc.getWorld().getName()))
		{
			ArrayList<String> AreaName = new ArrayList<String>();
			ArrayList<area.Area_Object> AreaList = main.Main_ServerOption.AreaList.get(loc.getWorld().getName());
			for(int count = 0; count < AreaList.size(); count++)
			{
				if(AreaList.get(count).minX <= loc.getX() && AreaList.get(count).maxX >= loc.getX())
					if(AreaList.get(count).minY <= loc.getY() && AreaList.get(count).maxY>= loc.getY())
						if(AreaList.get(count).minZ <= loc.getZ() && AreaList.get(count).maxZ >= loc.getZ())
							AreaName.add(AreaList.get(count).AreaName);
			}
			
			if(AreaName.isEmpty())
				return null;
			else
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");

				int TopPriority = -1000;

				String[] ret = new String[2];
				for(int count =0; count < AreaName.size(); count++)
				{
					if(areaYaml.contains(AreaName.get(count) + ".Priority")==false)
					{
						areaYaml.set(AreaName.get(count) + ".Priority", 5);
						areaYaml.saveConfig();
					}
					int AreaPriority = areaYaml.getInt(AreaName.get(count)+".Priority");
					if(AreaPriority >= TopPriority)
					{
						ret[0] = AreaName.get(count);
						TopPriority = AreaPriority;
					}
				}
				ret[1] = areaYaml.getString(ret[0] + ".Name");
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
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		
		switch(type)
		{
		case 0:
			return areaYaml.getBoolean(AreaName + ".PVP");
		case 1:
			return areaYaml.getBoolean(AreaName + ".BlockBreak");
		case 2:
			return  areaYaml.getBoolean(AreaName + ".SpawnPoint");
		case 3:
			return areaYaml.getBoolean(AreaName + ".MobSpawn");
		case 4:
			return areaYaml.getBoolean(AreaName + ".Alert");
		case 5:
			return areaYaml.getBoolean(AreaName + ".BlockPlace");
		case 6:
			return areaYaml.getBoolean(AreaName + ".Music");
		case 7:
			return areaYaml.getBoolean(AreaName + ".BlockUse");
		case 8:
		{
			if(areaYaml.contains(AreaName + ".MonsterSpawnRule"))
			{
				if(areaYaml.getConfigurationSection(AreaName + ".MonsterSpawnRule").getKeys(false).size() <= 0)
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
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		if(getAreaOption(AreaName, (char)4) == true)
		{
			String Title = areaYaml.getString(AreaName+".Name").replace("%player%", player.getName());
			String Description = areaYaml.getString(AreaName+".Description").replace("%player%", player.getName());
			new effect.SendPacket().sendTitleSubTitle(player, "\'"+Title+"\'", "\'"+Description+"\'", (byte)1, (byte)10, (byte)1);
		}
		return;
	}

	public void AreaMonsterSpawnAdd(String AreaName, String Count)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		if(Long.parseLong(Count)!=-1)
		{
			if(areaYaml.contains(AreaName+".MonsterSpawnRule")==true)
			{
				if(areaYaml.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).size()!=0)
				{
					if(areaYaml.getString(AreaName+".MonsterSpawnRule."+Count+".loc.world")!=null)
					{
						if(servertick.ServerTick_Main.MobSpawningAreaList.contains(AreaName)==false)
							servertick.ServerTick_Main.MobSpawningAreaList.add(AreaName);
						
						Long UTC = servertick.ServerTick_Main.nowUTC+5;
						for(;;)
						{
							if(servertick.ServerTick_Main.Schedule.containsKey(UTC))
								UTC=UTC+1;
							else
								break;
						}
						servertick.ServerTick_Object OBJECT = new servertick.ServerTick_Object(UTC, "A_MS");
						OBJECT.setString((byte)0, AreaName);
						OBJECT.setString((byte)1, areaYaml.getString(AreaName+".MonsterSpawnRule."+Count+".loc.world"));
						if(areaYaml.contains(AreaName+".MonsterSpawnRule."+Count+".Monster"))
							OBJECT.setString((byte)2, areaYaml.getString(AreaName+".MonsterSpawnRule."+Count+".Monster"));
						OBJECT.setString((byte)3, Count);
						OBJECT.setInt((byte)0, areaYaml.getInt(AreaName+".MonsterSpawnRule."+Count+".loc.x"));
						OBJECT.setInt((byte)1, areaYaml.getInt(AreaName+".MonsterSpawnRule."+Count+".loc.y"));
						OBJECT.setInt((byte)2, areaYaml.getInt(AreaName+".MonsterSpawnRule."+Count+".loc.z"));
						OBJECT.setInt((byte)3, areaYaml.getInt(AreaName+".MonsterSpawnRule."+Count+".range"));
						OBJECT.setInt((byte)4, areaYaml.getInt(AreaName+".MonsterSpawnRule."+Count+".count"));
						OBJECT.setInt((byte)5, areaYaml.getInt(AreaName+".MonsterSpawnRule."+Count+".max"));
						OBJECT.setMaxCount(areaYaml.getInt(AreaName+".MonsterSpawnRule."+Count+".timer"));
						servertick.ServerTick_Main.Schedule.put(UTC, OBJECT);
					}
				}
			}
		}
		else
		{
			if(areaYaml.contains(AreaName+".MonsterSpawnRule")==true)
			{
				if(areaYaml.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).size()!=0)
				{
					if(servertick.ServerTick_Main.MobSpawningAreaList.contains(AreaName)==false)
					{
						servertick.ServerTick_Main.MobSpawningAreaList.add(AreaName);
						String[] RuleName = areaYaml.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).toArray(new String[0]);
						for(int count=0;count<RuleName.length;count++)
						{
							if(areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleName[count]+".loc.world")!=null)
							{
								Long UTC = servertick.ServerTick_Main.nowUTC+5;
								for(;;)
								{
									if(servertick.ServerTick_Main.Schedule.containsKey(UTC))
										UTC=UTC+1;
									else
										break;
								}
								servertick.ServerTick_Object OBJECT = new servertick.ServerTick_Object(UTC, "A_MS");
								OBJECT.setString((byte)0, AreaName);
								OBJECT.setString((byte)1, areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleName[count]+".loc.world"));
								if(areaYaml.contains(AreaName+".MonsterSpawnRule."+RuleName[count]+".Monster"))
									OBJECT.setString((byte)2, areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleName[count]+".Monster"));
								OBJECT.setString((byte)3, RuleName[count]);
								OBJECT.setInt((byte)0, areaYaml.getInt(AreaName+".MonsterSpawnRule."+RuleName[count]+".loc.x"));
								OBJECT.setInt((byte)1, areaYaml.getInt(AreaName+".MonsterSpawnRule."+RuleName[count]+".loc.y"));
								OBJECT.setInt((byte)2, areaYaml.getInt(AreaName+".MonsterSpawnRule."+RuleName[count]+".loc.z"));
								OBJECT.setInt((byte)3, areaYaml.getInt(AreaName+".MonsterSpawnRule."+RuleName[count]+".range"));
								OBJECT.setInt((byte)4, areaYaml.getInt(AreaName+".MonsterSpawnRule."+RuleName[count]+".count"));
								OBJECT.setInt((byte)5, areaYaml.getInt(AreaName+".MonsterSpawnRule."+RuleName[count]+".max"));
								OBJECT.setMaxCount(areaYaml.getInt(AreaName+".MonsterSpawnRule."+RuleName[count]+".timer"));
								servertick.ServerTick_Main.Schedule.put(UTC, OBJECT);
							}
						}
					}
				}
			}
		}
		return;
	}
}