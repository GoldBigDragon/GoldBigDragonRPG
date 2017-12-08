package area;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import effect.SoundEffect;
import util.YamlLoader;



public class AreaMain
{
	public void addAreaList()
	{
		main.MainServerOption.AreaList.clear();
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		String[] arealist = areaYaml.getKeys().toArray(new String[0]);

		for(int count =0; count <arealist.length;count++)
		{
			area.AreaObject AO = new area.AreaObject();
			AO.areaName = arealist[count];
			AO.minX = areaYaml.getInt(arealist[count]+".X.Min");
			AO.maxX = areaYaml.getInt(arealist[count]+".X.Max");
			AO.minY = areaYaml.getInt(arealist[count]+".Y.Min");
			AO.maxY = areaYaml.getInt(arealist[count]+".Y.Max");
			AO.minZ = areaYaml.getInt(arealist[count]+".Z.Min");
			AO.maxZ = areaYaml.getInt(arealist[count]+".Z.Max");
			if(main.MainServerOption.AreaList.containsKey(areaYaml.getString(arealist[count]+".World")))
			{
				ArrayList<area.AreaObject> areaList = main.MainServerOption.AreaList.get(areaYaml.getString(arealist[count]+".World"));
				areaList.add(AO);
				main.MainServerOption.AreaList.remove(areaYaml.getString(arealist[count]+".World"));
				main.MainServerOption.AreaList.put(areaYaml.getString(arealist[count]+".World"), areaList);
			}
			else
			{
				ArrayList<area.AreaObject> areaList = new ArrayList<area.AreaObject>();
				areaList.add(AO);
				main.MainServerOption.AreaList.put(areaYaml.getString(arealist[count]+".World"), areaList);
			}
		}
	}

	public void CreateNewArea(Player player, Location loc1, Location loc2, String name)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		
		if(areaYaml.contains(name))
		{
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 해당 이름은 이미 등록되어 있습니다!");
			return;
		}
		
		if(!loc1.getWorld().equals(loc2.getWorld()))
		{
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 서로 다른 월드간은 영역 지정을 할 수 없습니다!");
			return;
		}
		areaYaml.set(name+".World", loc1.getWorld().getName());
		int minX = (int)loc1.getX();
		int minY = (int)loc1.getY();
		int minZ = (int)loc1.getZ();
		int maxX = (int)loc2.getX();
		int maxY = (int)loc2.getY();
		int maxZ = (int)loc2.getZ();
		int temp = 0;
		if(minX > maxX)
		{
			temp = maxX;
			maxX = minX;
			minX = temp;
		}
		if(minY > maxY)
		{
			temp = maxY;
			maxY = minY;
			minY = temp;
		}
		if(minZ > maxZ)
		{
			temp = maxZ;
			maxZ = minZ;
			minZ = temp;
		}
		maxX++;
		maxZ++;
		areaYaml.set(name+".X.Min", minX);
		areaYaml.set(name+".X.Max", maxX);
		areaYaml.set(name+".SpawnLocation.X", minX);
		areaYaml.set(name+".Y.Min", minY);
		areaYaml.set(name+".Y.Max", maxY);
		areaYaml.set(name+".SpawnLocation.Y", minY);
		areaYaml.set(name+".Z.Min", minZ);
		areaYaml.set(name+".Z.Max", maxZ);
		areaYaml.set(name+".SpawnLocation.Z", minZ);

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
		
		SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.7F);
		player.sendMessage("§a[SYSTEM] : 지정 구역 등록 성공!");
		area.AreaGui AGUI = new area.AreaGui();
		AGUI.areaSettingGui(player, name);

		area.AreaObject AO = new area.AreaObject();
		AO.areaName = name;
		AO.minX = minX;
		AO.maxX = maxX;
		AO.minY = minY;
		AO.maxY = maxY;
		AO.minZ = minZ;
		AO.maxZ = maxZ;
		if(main.MainServerOption.AreaList.containsKey(areaYaml.getString(name+".World")))
		{
			ArrayList<area.AreaObject> areaList = main.MainServerOption.AreaList.get(areaYaml.getString(name+".World"));
			areaList.add(AO);
			main.MainServerOption.AreaList.remove(areaYaml.getString(name+".World"));
			main.MainServerOption.AreaList.put(areaYaml.getString(name+".World"), areaList);
		}
		else
		{
			ArrayList<area.AreaObject> areaList = new ArrayList<area.AreaObject>();
			areaList.add(AO);
			main.MainServerOption.AreaList.put(areaYaml.getString(name+".World"), areaList);
		}
		return;
	}
	
	public void RemoveArea(Player player, String name)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		
		if(main.MainServerOption.AreaList.get(areaYaml.getString(name+".World")).size()==1)
			main.MainServerOption.AreaList.remove(areaYaml.getString(name+".World"));
		else
		{
			ArrayList<area.AreaObject> areaList = main.MainServerOption.AreaList.get(areaYaml.getString(name+".World"));
			for(int count = 0; count < areaList.size(); count ++)
			{
				if(areaList.get(count).toString().equals(name))
				{
					areaList.remove(count);
					break;
				}
			}
			main.MainServerOption.AreaList.put(areaYaml.getString(name+".World"), areaList);
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
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_CHICKEN_EGG, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 지정 구역 삭제 성공!");
		}
		else
		{
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
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
					player.sendMessage("§a[SYSTEM] : §e"+ AreaName+"§a 영역의 이름이 §e"+ string+"§a 으로 변경 되었습니다!");
				}
				break;
			case 1:
				{
					areaYaml.set(AreaName+".Description", string);
					player.sendMessage("§a[SYSTEM] : §e"+ AreaName+"§a 영역의 설명이 §e"+ string+"§a 으로 변경 되었습니다!");
				}
				break;
			}
			areaYaml.saveConfig();
		}
		else
		{
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : 해당 이름의 구역은 존재하지 않습니다!");
		}
		return;
	}
	
	public String[] SearchAreaName(Location loc)
	{
		if(main.MainServerOption.AreaList.containsKey(loc.getWorld().getName()))
		{
			ArrayList<String> AreaName = new ArrayList<String>();
			ArrayList<area.AreaObject> AreaList = main.MainServerOption.AreaList.get(loc.getWorld().getName());
			for(int count = 0; count < AreaList.size(); count++)
			{
				if(AreaList.get(count).minX <= loc.getX() && AreaList.get(count).maxX >= loc.getX())
					if(AreaList.get(count).minY <= loc.getY() && AreaList.get(count).maxY>= loc.getY())
						if(AreaList.get(count).minZ <= loc.getZ() && AreaList.get(count).maxZ >= loc.getZ())
							AreaName.add(AreaList.get(count).areaName);
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

	public boolean getAreaOption(String areaName,char type)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		
		switch(type)
		{
		case 0:
			return areaYaml.getBoolean(areaName + ".PVP");
		case 1:
			return areaYaml.getBoolean(areaName + ".BlockBreak");
		case 2:
			return  areaYaml.getBoolean(areaName + ".SpawnPoint");
		case 3:
			return areaYaml.getBoolean(areaName + ".MobSpawn");
		case 4:
			return areaYaml.getBoolean(areaName + ".Alert");
		case 5:
			return areaYaml.getBoolean(areaName + ".BlockPlace");
		case 6:
			return areaYaml.getBoolean(areaName + ".Music");
		case 7:
			return areaYaml.getBoolean(areaName + ".BlockUse");
		case 8:
		{
			if(areaYaml.contains(areaName + ".MonsterSpawnRule"))
			{
				if(areaYaml.getConfigurationSection(areaName + ".MonsterSpawnRule").getKeys(false).size() <= 0)
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
	
	public void sendAreaTitle(Player player, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		if(getAreaOption(areaName, (char)4))
		{
			String title = areaYaml.getString(areaName+".Name");
			if(title != null)
				title = title.replace("%player%", player.getName());
			String description = areaYaml.getString(areaName+".Description");
			if(description != null)
				description = description.replace("%player%", player.getName());
			new effect.SendPacket().sendTitle(player, title, description, 1, 10, 1);
		}
		return;
	}

	public void AreaMonsterSpawnAdd(String areaName, String Count)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		if(Long.parseLong(Count)!=-1)
		{
			if(areaYaml.contains(areaName+".MonsterSpawnRule"))
			{
				if(areaYaml.getConfigurationSection(areaName+".MonsterSpawnRule").getKeys(false).size()!=0)
				{
					if(areaYaml.getString(areaName+".MonsterSpawnRule."+Count+".loc.world")!=null)
					{
						if(!servertick.ServerTickMain.MobSpawningAreaList.contains(areaName))
							servertick.ServerTickMain.MobSpawningAreaList.add(areaName);
						
						Long utc = servertick.ServerTickMain.nowUTC+5;
						for(;;)
						{
							if(servertick.ServerTickMain.Schedule.containsKey(utc))
								utc=utc+1;
							else
								break;
						}
						servertick.ServerTickObject OBJECT = new servertick.ServerTickObject(utc, "A_MS");
						OBJECT.setString((byte)0, areaName);
						OBJECT.setString((byte)1, areaYaml.getString(areaName+".MonsterSpawnRule."+Count+".loc.world"));
						if(areaYaml.contains(areaName+".MonsterSpawnRule."+Count+".Monster"))
							OBJECT.setString((byte)2, areaYaml.getString(areaName+".MonsterSpawnRule."+Count+".Monster"));
						OBJECT.setString((byte)3, Count);
						OBJECT.setInt((byte)0, areaYaml.getInt(areaName+".MonsterSpawnRule."+Count+".loc.x"));
						OBJECT.setInt((byte)1, areaYaml.getInt(areaName+".MonsterSpawnRule."+Count+".loc.y"));
						OBJECT.setInt((byte)2, areaYaml.getInt(areaName+".MonsterSpawnRule."+Count+".loc.z"));
						OBJECT.setInt((byte)3, areaYaml.getInt(areaName+".MonsterSpawnRule."+Count+".range"));
						OBJECT.setInt((byte)4, areaYaml.getInt(areaName+".MonsterSpawnRule."+Count+".count"));
						OBJECT.setInt((byte)5, areaYaml.getInt(areaName+".MonsterSpawnRule."+Count+".max"));
						OBJECT.setMaxCount(areaYaml.getInt(areaName+".MonsterSpawnRule."+Count+".timer"));
						servertick.ServerTickMain.Schedule.put(utc, OBJECT);
					}
				}
			}
		}
		else
		{
			if(areaYaml.contains(areaName+".MonsterSpawnRule")==true)
			{
				if(areaYaml.getConfigurationSection(areaName+".MonsterSpawnRule").getKeys(false).size()!=0)
				{
					if(servertick.ServerTickMain.MobSpawningAreaList.contains(areaName)==false)
					{
						servertick.ServerTickMain.MobSpawningAreaList.add(areaName);
						String[] RuleName = areaYaml.getConfigurationSection(areaName+".MonsterSpawnRule").getKeys(false).toArray(new String[0]);
						for(int count=0;count<RuleName.length;count++)
						{
							if(areaYaml.getString(areaName+".MonsterSpawnRule."+RuleName[count]+".loc.world")!=null)
							{
								Long UTC = servertick.ServerTickMain.nowUTC+5;
								for(;;)
								{
									if(servertick.ServerTickMain.Schedule.containsKey(UTC))
										UTC=UTC+1;
									else
										break;
								}
								servertick.ServerTickObject OBJECT = new servertick.ServerTickObject(UTC, "A_MS");
								OBJECT.setString((byte)0, areaName);
								OBJECT.setString((byte)1, areaYaml.getString(areaName+".MonsterSpawnRule."+RuleName[count]+".loc.world"));
								if(areaYaml.contains(areaName+".MonsterSpawnRule."+RuleName[count]+".Monster"))
									OBJECT.setString((byte)2, areaYaml.getString(areaName+".MonsterSpawnRule."+RuleName[count]+".Monster"));
								OBJECT.setString((byte)3, RuleName[count]);
								OBJECT.setInt((byte)0, areaYaml.getInt(areaName+".MonsterSpawnRule."+RuleName[count]+".loc.x"));
								OBJECT.setInt((byte)1, areaYaml.getInt(areaName+".MonsterSpawnRule."+RuleName[count]+".loc.y"));
								OBJECT.setInt((byte)2, areaYaml.getInt(areaName+".MonsterSpawnRule."+RuleName[count]+".loc.z"));
								OBJECT.setInt((byte)3, areaYaml.getInt(areaName+".MonsterSpawnRule."+RuleName[count]+".range"));
								OBJECT.setInt((byte)4, areaYaml.getInt(areaName+".MonsterSpawnRule."+RuleName[count]+".count"));
								OBJECT.setInt((byte)5, areaYaml.getInt(areaName+".MonsterSpawnRule."+RuleName[count]+".max"));
								OBJECT.setMaxCount(areaYaml.getInt(areaName+".MonsterSpawnRule."+RuleName[count]+".timer"));
								servertick.ServerTickMain.Schedule.put(UTC, OBJECT);
							}
						}
					}
				}
			}
		}
		return;
	}
}