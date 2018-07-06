package area;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import main.MainServerOption;
import servertick.ServerTickMain;
import util.YamlLoader;

public class AreaServerTask
{
	public void areaMobSpawn(long utc)
	{
		String areaName = ServerTickMain.Schedule.get(utc).getString((byte)0);
		if(!MainServerOption.PlayerCurrentArea.values().contains(areaName))
		{
			ServerTickMain.MobSpawningAreaList.remove(areaName);
			return;
		}
		if(ServerTickMain.MobSpawningAreaList.contains(areaName))
		{
			YamlLoader areaYaml = new YamlLoader();
			areaYaml.getConfig("Area/AreaList.yml");
			if(areaYaml.contains(ServerTickMain.Schedule.get(utc).getString((byte)0)+".MonsterSpawnRule."+ServerTickMain.Schedule.get(utc).getString((byte)3)))
			{
				if(ServerTickMain.Schedule.get(utc).getCount() >= ServerTickMain.Schedule.get(utc).getMaxCount())
				{
					ServerTickMain.Schedule.get(utc).setCount(0);
					String mob = null;
					if(ServerTickMain.Schedule.get(utc).getString((byte)2)==null)
					{
						String[] mobList =areaYaml.getConfigurationSection(areaName+".Monster").getKeys(false).toArray(new String[0]);
						if(mobList.length!=0)
							mob=mobList[new util.NumericUtil().RandomNum(0, mobList.length-1)];
					}
					else
						mob = ServerTickMain.Schedule.get(utc).getString((byte)2);
					if(main.MainServerOption.MonsterList.containsKey(mob))
					{
						Location loc = new Location(Bukkit.getServer().getWorld(ServerTickMain.Schedule.get(utc).getString((byte)1)), ServerTickMain.Schedule.get(utc).getInt((byte)0), ServerTickMain.Schedule.get(utc).getInt((byte)1), ServerTickMain.Schedule.get(utc).getInt((byte)2));
						if(getEntitiesNearby(loc, 20) <= ServerTickMain.Schedule.get(utc).getInt((byte)5))
						{
							monster.MonsterSpawn monsterSpawn = new monster.MonsterSpawn();
							for(int mobspawn=0;mobspawn<ServerTickMain.Schedule.get(utc).getInt((byte)4);mobspawn++)
								monsterSpawn.SpawnMob(loc.add(-0.5, -1,-0.5), mob, (byte) -1, null, (char) -1, false);
						}
					}
				}
				else
				{
					ServerTickMain.Schedule.get(utc).setCount(ServerTickMain.Schedule.get(utc).getCount()+1);
				}
				ServerTickMain.Schedule.get(utc).copyThisScheduleObject(utc+1000);
			}
		}
	}

	public int getEntitiesNearby(Location loc, double range)
	{
        Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, range, range, range);
        for(Iterator<Entity> it = entities.iterator(); it.hasNext();)
        {
        	Entity entity = it.next();
            if(entity.getType() == EntityType.DROPPED_ITEM)
                it.remove();
            else if(entity.getType() == EntityType.EXPERIENCE_ORB)
                it.remove();
            else if(entity.getType() == EntityType.ITEM_FRAME)
                it.remove();
            else if(entity.getType() == EntityType.FISHING_HOOK)
                it.remove();
            else if(entity.getType() == EntityType.PAINTING)
                it.remove();
            else if(entity.getType() == EntityType.ARROW)
                it.remove();
            else if(entity.getType() == EntityType.PLAYER)
            {
            	if(Bukkit.getServer().getPlayer(entity.getName()) != null)
            		it.remove();
            }
        }
        return entities.size();
    }

	public void areaRegenBlock(Long utc)
	{
		Location loc = new Location(Bukkit.getServer().getWorld(ServerTickMain.Schedule.get(utc).getString((byte)1)), ServerTickMain.Schedule.get(utc).getInt((byte)0), ServerTickMain.Schedule.get(utc).getInt((byte)1), ServerTickMain.Schedule.get(utc).getInt((byte)2));
		loc.getWorld().getBlockAt(loc).setTypeId(ServerTickMain.Schedule.get(utc).getInt((byte)3));
		loc.getWorld().getBlockAt(loc).setData((byte) ServerTickMain.Schedule.get(utc).getInt((byte)4));
		ServerTickMain.Schedule.remove(utc);
	}

}
