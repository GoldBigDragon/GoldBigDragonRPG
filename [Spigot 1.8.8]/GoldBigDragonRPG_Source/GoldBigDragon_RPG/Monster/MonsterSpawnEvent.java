package GoldBigDragon_RPG.Monster;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class MonsterSpawnEvent
{
	public void EntitySpawn(CreatureSpawnEvent event)
	{
		if(event.getLocation().getWorld().getName().compareTo("Dungeon") == 0)
		{
			if(event.getSpawnReason()== SpawnReason.NATURAL || event.getSpawnReason() == SpawnReason.CHUNK_GEN
					|| event.getSpawnReason() == SpawnReason.MOUNT|| event.getSpawnReason() == SpawnReason.JOCKEY
					|| event.getSpawnReason() == SpawnReason.SLIME_SPLIT)
			{
				event.setCancelled(true);
				return;
			}
		}
		if(event.getEntity().getType()==EntityType.ARMOR_STAND)
			return;

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	    YamlManager Config = YC.getNewConfig("config.yml");
		
		GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
		String[] Area = A.getAreaName(event.getEntity());
		
		if(Area != null)
		{
			if(A.getAreaOption(Area[0], (char) 3) == false)
			{
				event.setCancelled(true);
				return;
			}
			else if(A.getAreaOption(Area[0], (char) 8) == false)
			{
				YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
				String AreaName = A.getAreaName(event.getEntity())[0];
				Object[] MobNameList = AreaList.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray();
				boolean isExit = false;
				for(byte count = 0;count<10;count++)
				{
					if(isExit==true) break;
					if(MobNameList.length != 0)
					{
						short RandomMob = (short) new GoldBigDragon_RPG.Util.Number().RandomNum(0, MobNameList.length-1);
						if(GoldBigDragon_RPG.Main.ServerOption.MonsterList.containsKey(MobNameList[RandomMob].toString()))
						{
							new GoldBigDragon_RPG.Monster.MonsterSpawn().SpawnMob(event.getLocation(), MobNameList[RandomMob].toString(), (byte) -1, null,(char) -1, false);
							isExit = true;
						}
						else
						{
							AreaList.removeKey(AreaName+".Monster."+MobNameList[RandomMob]);
							AreaList.saveConfig();
						}
					}
					else
						break;
				}
			}
		}
		new GoldBigDragon_RPG.Monster.MonsterSpawn().SpawnEffect(event.getEntity(),event.getLocation(), (byte) Config.getInt("Server.MonsterSpawnEffect"));
		return;
	}
}
