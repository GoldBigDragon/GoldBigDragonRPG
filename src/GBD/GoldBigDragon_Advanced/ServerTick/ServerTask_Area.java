package GBD.GoldBigDragon_Advanced.ServerTick;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class ServerTask_Area
{
	public void AreaMobSpawn(Long UTC)
	{
		String AreaName = ServerTickMain.Schedule.get(UTC).getString((byte)0);
		if(ServerTickMain.MobSpawningAreaList.contains(AreaName))
		{
			YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
			YamlManager AreaConfig =GUI_YC.getNewConfig("Area/AreaList.yml");
			if(AreaConfig.contains(ServerTickMain.Schedule.get(UTC).getString((byte)0)+".MonsterSpawnRule."+ServerTickMain.Schedule.get(UTC).getString((byte)3)))
			{
				if(ServerTickMain.Schedule.get(UTC).getCount() >= ServerTickMain.Schedule.get(UTC).getMaxCount())
				{
					ServerTickMain.Schedule.get(UTC).setCount(0);
					YamlController Scheduler_YC = GBD.GoldBigDragon_Advanced.Main.Main.Scheduler_YC;
					YamlManager AreaList = Scheduler_YC.getNewConfig("Area/AreaList.yml");
					String mob = null;
					if(ServerTickMain.Schedule.get(UTC).getString((byte)2)==null)
					{
						Object[] MobList=AreaList.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray();
						if(MobList.length!=0)
							mob=MobList[new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(0, MobList.length-1)].toString();
					}
					else
						mob = ServerTickMain.Schedule.get(UTC).getString((byte)2);
					YamlManager MobList = Scheduler_YC.getNewConfig("Monster/MonsterList.yml");
					Object[] MonsterList = MobList.getConfigurationSection("").getKeys(false).toArray();
					for(int counter = 0; counter < MonsterList.length; counter++)
					{
						if(MonsterList[counter].toString().equals(mob))
						{
							Location loc = new Location(Bukkit.getServer().getWorld(ServerTickMain.Schedule.get(UTC).getString((byte)1)), ServerTickMain.Schedule.get(UTC).getInt((byte)0), ServerTickMain.Schedule.get(UTC).getInt((byte)1), ServerTickMain.Schedule.get(UTC).getInt((byte)2));
							
							if(getEntitiesNearby(loc, 20) <= ServerTickMain.Schedule.get(UTC).getInt((byte)5))
							{
								GBD.GoldBigDragon_Advanced.ETC.Monster MC = new GBD.GoldBigDragon_Advanced.ETC.Monster();
								for(int mobspawn=0;mobspawn<ServerTickMain.Schedule.get(UTC).getInt((byte)4);mobspawn++)
								{
									MC.SpawnMob(loc.add(-0.5, -1,-0.5), mob);
								}
							}
							break;
						}
					}
				}
				else
				{
					ServerTickMain.Schedule.get(UTC).setCount(ServerTickMain.Schedule.get(UTC).getCount()+1);
				}
				ServerTickMain.Schedule.get(UTC).copyThisScheduleObject(UTC+1000);
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

	public void AreaRegenBlock(Long UTC)
	{
		Location loc = new Location(Bukkit.getServer().getWorld(ServerTickMain.Schedule.get(UTC).getString((byte)1)), ServerTickMain.Schedule.get(UTC).getInt((byte)0), ServerTickMain.Schedule.get(UTC).getInt((byte)1), ServerTickMain.Schedule.get(UTC).getInt((byte)2));
		loc.getWorld().getBlockAt(loc).setTypeId(ServerTickMain.Schedule.get(UTC).getInt((byte)3));
		loc.getWorld().getBlockAt(loc).setData((byte) ServerTickMain.Schedule.get(UTC).getInt((byte)4));
		ServerTickMain.Schedule.remove(UTC);
	}

}
