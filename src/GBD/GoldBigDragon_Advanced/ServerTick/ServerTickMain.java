package GBD.GoldBigDragon_Advanced.ServerTick;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class ServerTickMain
{
	public static ArrayList<String> MobSpawningAreaList = new ArrayList<String>();
	public static HashMap<Long, ServerTickScheduleObject> Schedule = new HashMap<Long, ServerTickScheduleObject>();
	long nowUTC = 0;
	int BroadCastMessageTime =0;
  	int BroadCastMessageCool = 0;
	public ServerTickMain(Main plugin)
	{
	  	BukkitScheduler scheduler1 = Bukkit.getServer().getScheduler();
	  	scheduler1.scheduleSyncRepeatingTask(plugin,  new Runnable()
        {
            @Override
            public void run() 
            {
        		BroadCastMessage();
            	CheckShcedule();
        	  	//Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA +"1"+"ÃÊ °æ°ú");
            }
        }, 10, 20);
	}
	
	public void BroadCastMessage()
	{
		YamlController Scheduler_YC = GBD.GoldBigDragon_Advanced.Main.Main.Scheduler_YC;
		YamlManager Config = Scheduler_YC.getNewConfig("config.yml");
		BroadCastMessageTime = Config.getInt("Server.BroadCastSecond");
    	if(BroadCastMessageTime!=0)
		if(BroadCastMessageCool>=BroadCastMessageTime)
		{
			BroadCastMessageCool=0;
			YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
			YamlManager BroadCast =GUI_YC.getNewConfig("BroadCast.yml");
			if(BroadCast.contains("0"))
				if(BroadCast.getConfigurationSection("").getKeys(false).toArray().length != 0)
					Bukkit.broadcastMessage(BroadCast.getString(BroadCast.getConfigurationSection("").getKeys(false).toArray()[new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(0, BroadCast.getConfigurationSection("").getKeys(false).toArray().length-1)].toString()));
		}
		else
			BroadCastMessageCool=BroadCastMessageCool+1;
	}
	
	
	public void CheckShcedule()
	{
		nowUTC=new GBD.GoldBigDragon_Advanced.Util.ETC().getNowUTC();
		Object[] ScheduleList = Schedule.keySet().toArray();
		long scheduleUTC = 0;
		int SafeLine = 20;
		for(int count = 0; count<ScheduleList.length;count++)
		{
			if(SafeLine <= 0) break;
			scheduleUTC=Long.parseLong(ScheduleList[count].toString());
			if(scheduleUTC <= nowUTC)
			{
				ExcuteSchedule(scheduleUTC);
				SafeLine = SafeLine-1;
				Schedule.remove(Long.parseLong(ScheduleList[count].toString()));
			}
		}
	}

	public void ExcuteSchedule(Long UTC)
	{
		String Type = Schedule.get(UTC).getType();
		switch(Type)
		{
		case "A_MS"://Area_MonsterSpawn
			AreaMobSpawn(UTC);
			return;
		}
	}
	
	public void AreaMobSpawn(Long UTC)
	{
		String AreaName = Schedule.get(UTC).getString((byte)0);
		if(MobSpawningAreaList.contains(AreaName))
		{
			if(Schedule.get(UTC).getCount() >= Schedule.get(UTC).getMaxCount())
			{
				Schedule.get(UTC).setCount(0);
				YamlController Scheduler_YC = GBD.GoldBigDragon_Advanced.Main.Main.Scheduler_YC;
				YamlManager AreaList = Scheduler_YC.getNewConfig("Area/AreaList.yml");
				String mob = null;
				if(Schedule.get(UTC).getString((byte)2)==null)
				{
					Object[] MobList=AreaList.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray();
					if(MobList.length!=0)
						mob=MobList[new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(0, MobList.length-1)].toString();
				}
				else
					mob = Schedule.get(UTC).getString((byte)2);
				YamlManager MobList = Scheduler_YC.getNewConfig("Monster/MonsterList.yml");
				Object[] MonsterList = MobList.getConfigurationSection("").getKeys(false).toArray();
				for(int counter = 0; counter < MonsterList.length; counter++)
				{
					if(MonsterList[counter].toString().equals(mob))
					{
						Location loc = new Location(Bukkit.getServer().getWorld(Schedule.get(UTC).getString((byte)1)), Schedule.get(UTC).getInt((byte)0), Schedule.get(UTC).getInt((byte)1), Schedule.get(UTC).getInt((byte)2));
						if(Bukkit.getServer().getWorld(Schedule.get(UTC).getString((byte)1)).getNearbyEntities(loc, 20, 20, 20).size() <= Schedule.get(UTC).getInt((byte)5))
						{
							GBD.GoldBigDragon_Advanced.ETC.Monster MC = new GBD.GoldBigDragon_Advanced.ETC.Monster();
							for(int mobspawn=0;mobspawn<Schedule.get(UTC).getInt((byte)4);mobspawn++)
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
				Schedule.get(UTC).setCount(Schedule.get(UTC).getCount()+1);
			}
			Schedule.get(UTC).copyThisScheduleObject(UTC+1000);
		}
	}
}
