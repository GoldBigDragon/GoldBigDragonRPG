package GBD_RPG.ServerTick;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import GBD_RPG.Area.Area_ServerTask;
import GBD_RPG.Dungeon.Dungeon_ScheduleObject;
import GBD_RPG.Dungeon.Dungeon_ServerTask;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class ServerTick_Main
{
	public static ArrayList<String> MobSpawningAreaList = new ArrayList<String>();
	public static ArrayList<String> NaviUsingList = new ArrayList<String>();
	public static HashMap<String, String> PlayerTaskList = new HashMap<String, String>();
	public static String ServerTask = "null";
	public static HashMap<Long, ServerTick_Object> Schedule = new HashMap<Long, ServerTick_Object>();
	public static HashMap<Long, Dungeon_ScheduleObject> DungeonSchedule = new HashMap<Long, Dungeon_ScheduleObject>();
	public static long nowUTC = 0;
	int BroadCastMessageTime =0;
  	int BroadCastMessageCool = 0;
  	public static short SafeLine = 100;
  	public static byte oneSec = 0;
  	//초당 최대 실행 스케줄 갯수 설정
  	//(값이 클수록 초당 많은 작업을 하지만, 그만큼 서버에 부담이 된다.)

	public ServerTick_Main()
	{}
	public ServerTick_Main(JavaPlugin plugin)
	{
		nowUTC = new GBD_RPG.Util.ETC().getNowUTC();
	  	BukkitScheduler scheduler1 = Bukkit.getServer().getScheduler();
	  	scheduler1.scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            @Override
            public void run() 
            {
            	if(oneSec==10)
            	{
            		BroadCastMessage();
            		oneSec=0;
            	}
            	else
            		oneSec++;
            	CheckShcedule();
            	nowUTC = nowUTC +110;
        	  	//Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA +"1"+"초 경과");
            }
        }, 10, 2);
    	return;
	}
	
	public void BroadCastMessage()
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config = YC.getNewConfig("config.yml");
		BroadCastMessageTime = Config.getInt("Server.BroadCastSecond");
    	if(BroadCastMessageTime!=0)
		if(BroadCastMessageCool>=BroadCastMessageTime)
		{
			BroadCastMessageCool=0;
			YamlManager BroadCast =YC.getNewConfig("BroadCast.yml");
			if(BroadCast.contains("0"))
				if(BroadCast.getConfigurationSection("").getKeys(false).toArray().length != 0)
					Bukkit.broadcastMessage(BroadCast.getString(BroadCast.getConfigurationSection("").getKeys(false).toArray()[new GBD_RPG.Util.Util_Number().RandomNum(0, BroadCast.getConfigurationSection("").getKeys(false).toArray().length-1)].toString()));
		}
		else
			BroadCastMessageCool++;
    	return;
	}
	
	public void CheckShcedule()
	{
		Object[] ScheduleList = Schedule.keySet().toArray();
		Object[] DungeonScheduleList = DungeonSchedule.keySet().toArray();
		long scheduleUTC = 0;
		short SafeLineCounter = SafeLine;
		for(short count = 0; count<ScheduleList.length;count++)
		{
			if(SafeLineCounter <= 0) break;
				scheduleUTC=Long.parseLong(ScheduleList[count].toString());
			if(scheduleUTC <= nowUTC)
			{
				ExcuteSchedule(scheduleUTC);
				SafeLineCounter--;
				Schedule.remove(Long.parseLong(ScheduleList[count].toString()));
			}
		}
		SafeLineCounter = SafeLine;
		for(short count = 0; count<DungeonScheduleList.length;count++)
		{
			if(SafeLineCounter <= 0) break;
				scheduleUTC=Long.parseLong(DungeonScheduleList[count].toString());
			if(scheduleUTC <= nowUTC)
			{
				DungeonExcuteSchedule(scheduleUTC);
				SafeLineCounter--;
				DungeonSchedule.remove(Long.parseLong(DungeonScheduleList[count].toString()));
			}
		}
    	return;
	}

	public void ExcuteSchedule(long UTC)
	{
		String Type = Schedule.get(UTC).getType();
		switch(Type)
		{
		case "A_MS"://Area_MonsterSpawn
			new Area_ServerTask().AreaMobSpawn(UTC);
			return;
		case "A_RB"://Area_RegenBlock
			new Area_ServerTask().AreaRegenBlock(UTC);
			return;
		case "NV"://Navigation
			new ServerTask_Navigation().Navigation(UTC);
			return;
		case "P_EC"://Player_Exchange
			new ServerTask_Player().ExChangeTimer(UTC);
			return;
		case "G_SM"://Gamble_SlotMachine
			new ServerTask_Player().Gamble_SlotMachine_Rolling(UTC);
			return;
		case "C_S"://Create_Structure
			new ServerTask_Server().CreateStructureMain(UTC);
			return;
		case "Sound":
			new ServerTask_Effect().PlaySoundEffect(Schedule.get(UTC));
			return;
		default:
			return;
		}
	}
	
	public void DungeonExcuteSchedule(long UTC)
	{
		String Type = DungeonSchedule.get(UTC).getType();
		switch(Type)
		{
		case "D_RC"://Dungeon_RoomCreate
			new Dungeon_ServerTask().CreateRoom(DungeonSchedule.get(UTC));
			return;
		case "D_KRC"://Dungeon_KeyRoomCreate
			new Dungeon_ServerTask().CreateKeyRoom(DungeonSchedule.get(UTC));
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
