package GoldBigDragon_RPG.ServerTick;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import GoldBigDragon_RPG.Main.Main;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class ServerTickMain
{
	public static ArrayList<String> MobSpawningAreaList = new ArrayList<String>();
	public static ArrayList<String> NaviUsingList = new ArrayList<String>();
	public static HashMap<String, String> PlayerTaskList = new HashMap<String, String>();
	public static HashMap<Long, ServerTickScheduleObject> Schedule = new HashMap<Long, ServerTickScheduleObject>();
	public static long nowUTC = 0;
	int BroadCastMessageTime =0;
  	int BroadCastMessageCool = 0;
  	public static int SafeLine = 100;
  	//초당 최대 실행 스케줄 갯수 설정
  	//(값이 클수록 초당 많은 작업을 하지만, 그만큼 서버에 부담이 된다.)

	public ServerTickMain()
	{}
	public ServerTickMain(Main plugin)
	{
		nowUTC = new GoldBigDragon_RPG.Util.ETC().getNowUTC();
	  	BukkitScheduler scheduler1 = Bukkit.getServer().getScheduler();
	  	scheduler1.scheduleSyncRepeatingTask(plugin,  new Runnable()
        {
            @Override
            public void run() 
            {
        		BroadCastMessage();
            	CheckShcedule();
            	nowUTC = nowUTC +980;
        	  	//Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA +"1"+"초 경과");
            }
        }, 10, 20);
    	return;
	}
	
	public void BroadCastMessage()
	{
		YamlController Scheduler_YC = GoldBigDragon_RPG.Main.Main.YC_2;
		YamlManager Config = Scheduler_YC.getNewConfig("config.yml");
		BroadCastMessageTime = Config.getInt("Server.BroadCastSecond");
    	if(BroadCastMessageTime!=0)
		if(BroadCastMessageCool>=BroadCastMessageTime)
		{
			BroadCastMessageCool=0;
			YamlController GUI_YC = GoldBigDragon_RPG.Main.Main.YC_2;
			YamlManager BroadCast =GUI_YC.getNewConfig("BroadCast.yml");
			if(BroadCast.contains("0"))
				if(BroadCast.getConfigurationSection("").getKeys(false).toArray().length != 0)
					Bukkit.broadcastMessage(BroadCast.getString(BroadCast.getConfigurationSection("").getKeys(false).toArray()[new GoldBigDragon_RPG.Util.Number().RandomNum(0, BroadCast.getConfigurationSection("").getKeys(false).toArray().length-1)].toString()));
		}
		else
			BroadCastMessageCool=BroadCastMessageCool+1;
    	return;
	}
	
	public void CheckShcedule()
	{
		Object[] ScheduleList = Schedule.keySet().toArray();
		long scheduleUTC = 0;
		int SafeLineCounter = SafeLine;
		for(int count = 0; count<ScheduleList.length;count++)
		{
			if(SafeLineCounter <= 0) break;
			scheduleUTC=Long.parseLong(ScheduleList[count].toString());
			if(scheduleUTC <= nowUTC)
			{
				ExcuteSchedule(scheduleUTC);
				SafeLineCounter = SafeLineCounter-1;
				Schedule.remove(Long.parseLong(ScheduleList[count].toString()));
			}
		}
    	return;
	}

	public void ExcuteSchedule(Long UTC)
	{
		String Type = Schedule.get(UTC).getType();
		switch(Type)
		{
		case "A_MS"://Area_MonsterSpawn
			new ServerTask_Area().AreaMobSpawn(UTC);
			return;
		case "A_RB"://Area_RegenBlock
			new ServerTask_Area().AreaRegenBlock(UTC);
			return;
		case "NV"://Navigation
			new ServerTask_Navigation().Navigation(UTC);
			return;
		case "P_EC"://Player_Exchange
			new ServerTask_Player().ExChangeTimer(UTC);
			return;
		default:
			return;
		}
	}
	
	public Long getNowUTC()
	{
		return nowUTC;
	}
}
