package GBD.GoldBigDragon_Advanced.Event;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class MonsterSpawn
{
	public void EntitySpawn(CreatureSpawnEvent event)
	{
	    YamlController Monster_YC=GBD.GoldBigDragon_Advanced.Main.Main.Monster_YC;
	    YamlManager 	Config = Monster_YC.getNewConfig("config.yml");
		if(Config.getString("Server.EntitySpawn") == "false")
		{
			event.setCancelled(true);
			return;
		}
		GBD.GoldBigDragon_Advanced.ETC.Monster M= new GBD.GoldBigDragon_Advanced.ETC.Monster();
		GBD.GoldBigDragon_Advanced.ETC.Area A = new GBD.GoldBigDragon_Advanced.ETC.Area();
		GBD.GoldBigDragon_Advanced.Util.Number etc = new GBD.GoldBigDragon_Advanced.Util.Number();
		
		String[] Area = A.getAreaName(event.getEntity());
		if(Area != null)
		{
			if(A.getAreaOption(Area[0], (char) 3) == false)
			{
				event.setCancelled(true);
				return;
			}
			YamlManager AreaList = Monster_YC.getNewConfig("Area/AreaList.yml");
			YamlManager Monster  = Monster_YC.getNewConfig("Monster/MonsterList.yml");
			String AreaName = A.getAreaName(event.getEntity())[0];
			Object[] MobNameList = AreaList.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray();
			boolean isExit = false;
			for(int count = 0;count<10;count++)
			{
				if(isExit==true) break;
				if(MobNameList.length != 0)
				{
					int RandomMob = etc.RandomNum(0, MobNameList.length-1);
					if(Monster.contains(MobNameList[RandomMob].toString()) == true)
					{
						M.SpawnMob(event.getLocation(), MobNameList[RandomMob].toString());
						if(GBD.GoldBigDragon_Advanced.Main.Main.spawntime==true)
							event.setCancelled(true);
						isExit = true;
					}
					else
					{
						AreaList.removeKey(AreaName+".Monster."+MobNameList[RandomMob]);
						AreaList.saveConfig();
					}
				}
				else
				{
					break;
				}
			}
		}
		if(event.getEntity().getWorld().getName().equalsIgnoreCase("Dungeon") == true)
		{
			if(event.getSpawnReason()== SpawnReason.NATURAL || event.getSpawnReason() == SpawnReason.CHUNK_GEN
					|| event.getSpawnReason() == SpawnReason.MOUNT|| event.getSpawnReason() == SpawnReason.JOCKEY)
			{
				event.setCancelled(true);
				return;
			}
		}
		M.SpawnEffect(event.getEntity(),event.getLocation(), Config.getInt("Server.MonsterSpawnEffect"));
	}
}
