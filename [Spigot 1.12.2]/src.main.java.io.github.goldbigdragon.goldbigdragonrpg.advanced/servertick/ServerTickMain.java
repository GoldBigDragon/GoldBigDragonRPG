package servertick;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import area.AreaServerTask;
import dungeon.DungeonScheduleObject;
import dungeon.DungeonServerTask;
import effect.SoundEffect;
import main.Main;
import user.UserObject;
import util.YamlLoader;

class Object_RankingSet
{
	String stringValue;
	long longValue;
	public Object_RankingSet(String stringValue, long longValue)
	{
		this.stringValue = stringValue;
		this.longValue = longValue;
	}

	public Long getLongValue()
	{
		return longValue;
	}
}

class Descending_longValue implements Comparator<Object_RankingSet>
{
    @Override
    public int compare(Object_RankingSet o1, Object_RankingSet o2)
    {
        return o2.getLongValue().compareTo(o1.getLongValue());
    }
}

public class ServerTickMain
{
	public static ArrayList<String> MobSpawningAreaList = new ArrayList<>();
	public static ArrayList<String> NaviUsingList = new ArrayList<>();
	public static HashMap<String, String> PlayerTaskList = new HashMap<>();
	public static String ServerTask = "null";
	public static HashMap<Long, ServerTickObject> Schedule = new HashMap<>();
	public static HashMap<Long, DungeonScheduleObject> DungeonSchedule = new HashMap<>();
	public static long nowUTC = 0;
	int BroadCastMessageTime =0;
  	int BroadCastMessageCool = 0;
  	public static short SafeLine = 100;
  	public static byte oneSec = 0;
  	//초당 최대 실행 스케줄 갯수 설정
  	//(값이 클수록 초당 많은 작업을 하지만, 그만큼 서버에 부담이 된다.)

	public ServerTickMain()
	{}
	public ServerTickMain(JavaPlugin plugin)
	{
		nowUTC = new util.ETC().getNowUTC();
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            @Override
            public void run() 
            {
            	nowUTC += 50;
            	checkShcedule();
            }
        }, 0, 1);

		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable()
        {
			Iterator<Player> playerList;
			corpse.CorpseAPI corpse = new corpse.CorpseAPI();
			otherplugins.NoteBlockApiMain noteblockApi = new otherplugins.NoteBlockApiMain();
			YamlLoader areaList = new YamlLoader();
			area.AreaAPI areaMain = new area.AreaAPI();
			quest.QuestGui questGui = new quest.QuestGui();
        	String area;
        	String questName;
        	UserObject uo;
            @Override
            public void run() 
            {
        		broadCastMessage();
            	
            	playerList = (Iterator<Player>) Bukkit.getOnlinePlayers().iterator();
            	Player player;
	  			areaList.getConfig("config.yml");
			  	boolean isMabinogi = areaList.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System");
				while (playerList.hasNext())
				{
					player = playerList.next();
					uo = main.MainServerOption.PlayerList.get(player.getUniqueId().toString());
					corpse.asyncDeathCapture(player);
	        		if(!player.getLocation().getWorld().getName().equals("Dungeon"))
	        		{
	        			if(uo.getDungeon_Enter() != null)
	        			{
	        				noteblockApi.Stop(player);
	        				uo.setDungeon_Enter(null);
	        				uo.setDungeon_UTC(-1);
	        			}
	        			if(areaMain.getAreaName(player) != null)
	        			{
	        				if(uo.getETC_CurrentArea()==null)
	        					uo.setETC_CurrentArea("null");
	        				area = areaMain.getAreaName(player)[0];
	        				if(!uo.getETC_CurrentArea().equals(area))
	        				{
	        					areaList.getConfig("Area/AreaList.yml");
	        					//레벨 제한 확인
	        					boolean restrict = false;
	        					if(areaList.getInt(area+".Restrict.MinNowLevel")!=0 &&(areaList.getInt(area+".Restrict.MinNowLevel") > uo.getStat_Level()||areaList.getInt(area+".Restrict.MaxNowLevel") < uo.getStat_Level()))
	        						restrict=true;
	        					if(isMabinogi&&(areaList.getInt(area+".Restrict.MinRealLevel")!=0 &&(areaList.getInt(area+".Restrict.MinRealLevel") > uo.getStat_RealLevel()||areaList.getInt(area+".Restrict.MaxRealLevel") < uo.getStat_RealLevel())))
	        						restrict=true;
	        					if(restrict)
	        					{
	        						Location playerLoc = player.getLocation();
	        						int calc1 = areaList.getInt(area+".X.Max") - playerLoc.getBlockX();
	        						int calc2 = areaList.getInt(area+".X.Min") - playerLoc.getBlockX();
	        						int staticX = areaList.getInt(area+".X.Min");
	        						int staticZ = areaList.getInt(area+".Z.Min");

	        						int xF = 0;
	        						int zF = 0;
	        						
	        						if(calc1 < 0)
	        							calc1 *= -1;
	        						if(calc2 < 0)
	        							calc2 *= -1;
	        						if(calc1 < calc2)
	        						{
	        							staticX = areaList.getInt(area+".X.Max")+1;
	        							xF = calc1;
	        						}
	        						else
	        						{
	        							staticX = areaList.getInt(area+".X.Min")-1;
	        							xF = calc2;
	        						}

	        						calc1 = areaList.getInt(area+".Z.Max") - playerLoc.getBlockZ();
	        						calc2 = areaList.getInt(area+".Z.Min") - playerLoc.getBlockZ();
	        						calc1 = areaList.getInt(area+".Z.Max") - playerLoc.getBlockZ();
	        						calc2 = areaList.getInt(area+".Z.Min") - playerLoc.getBlockZ();
	        						if(calc1 < 0)
	        							calc1 *= -1;
	        						if(calc2 < 0)
	        							calc2 *= -1;
	        						if(calc1 < calc2)
	        						{
	        							staticZ = areaList.getInt(area+".Z.Max")+1;
	        							zF = calc1;
	        						}
	        						else
	        						{
	        							staticZ = areaList.getInt(area+".Z.Min")-1;
	        							zF = calc2;
	        						}

	        		    			SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
	        		    			new effect.SendPacket().sendActionBar(player, "§c§l레벨이 맞지 않아 입장할 수 없습니다!", false);
	        						if(xF < zF)
	        							player.teleport(new Location(player.getWorld(), staticX, playerLoc.getY()+0.2, playerLoc.getZ(), playerLoc.getYaw(), playerLoc.getPitch()));
	        						else
	        							player.teleport(new Location(player.getWorld(), playerLoc.getX(), playerLoc.getY()+0.2, staticZ, playerLoc.getYaw(), playerLoc.getPitch()));
	        					}
	        					else
	        					{
		        					uo.setETC_CurrentArea(area);
		        					main.MainServerOption.PlayerCurrentArea.put(player, area);
		        					areaMain.areaMonsterSpawnAdd(area, "-1");
		        					noteblockApi.Stop(player);
		        					uo.setETC_CurrentArea(area);
		        					if(areaList.getBoolean(area + ".SpawnPoint") == true)
		        						uo.setETC_LastVisited(area);
		            				if(uo.isBgmOn())
		            				{
		            					if(areaList.getBoolean(area + ".Music") == true)
		        							noteblockApi.Play(player, areaList.getInt(area+".BGM"));
		            				}
		        					if(areaList.getBoolean(area + ".Alert") == true)
		        					{
		        						YamlLoader QuestList = new YamlLoader();
		        						QuestList.getConfig("Quest/QuestList.yml");
		        						YamlLoader PlayerQuestList = new YamlLoader();
		        						PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
		        						
		        						Object[] b = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
		        						for(int count = 0; count < b.length; count++)
		        						{
		        							questName = (String) b[count];
		        							if(PlayerQuestList.getString("Started."+questName+".Type").equals("Visit"))
		        							{
		        								if(PlayerQuestList.getString("Started."+questName+".AreaName").equals(area))
	        									{
	        										PlayerQuestList.set("Started."+questName+".Type",QuestList.getString(questName+".FlowChart."+(PlayerQuestList.getInt("Started."+questName+".Flow")+1)+".Type"));
	        										PlayerQuestList.set("Started."+questName+".Flow",PlayerQuestList.getInt("Started."+questName+".Flow")+1);
	        										PlayerQuestList.removeKey("Started."+questName+".AreaName");
	        										PlayerQuestList.saveConfig();
	        										questGui.QuestRouter(player, questName);
	        										break;
	        									}
		        							}
		        						}
		        						areaMain.sendAreaTitle(player, area);
		        					}
	        					}
	        				}
	        			}
	        			else
	        			{
	        				main.MainServerOption.PlayerCurrentArea.put(player, "null");
	        				uo.setETC_CurrentArea("null");
	        				noteblockApi.Stop(player);
	        			}
	        		}
	        		else
	        		{
	        			if(uo.getDungeon_Enter() == null)
	        				noteblockApi.Stop(player);
	        		}
        		}
        	
            	
            }
        }, 0, 10);
	  	
	  	Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable()
        {
	  		File directory = new File(Main.plugin.getDataFolder() + "\\Stats"); 
            @Override
            public void run() 
            {
            	if(!directory.exists())
        			directory.mkdir();
        		File[] fileList = directory.listFiles();
    		  	YamlLoader YAML = new YamlLoader();
    			ArrayList<Object_RankingSet> MoneyRankingSet = new ArrayList<Object_RankingSet>();
    			Object_RankingSet ORS = null;
        		try
        		{
        			for(int count = 0 ; count < fileList.length ; count++)
        				if(fileList[count].isFile())
        				{
        					YAML.getConfig("Stats/"+fileList[count].getName());
        					ORS = new Object_RankingSet(YAML.getString("Player.Name"), YAML.getLong("Stat.Money"));
        					MoneyRankingSet.add(ORS);
        				}
        		}
        		catch(Exception e)
        		{
				}
        		Collections.sort(MoneyRankingSet, new Descending_longValue());
        		YAML.getConfig("Ranking/money.yml");
        		YAML.removeKey("Rank");
        		YAML.removeKey("NameSet");
    			for(int count = 0 ; count < MoneyRankingSet.size() ; count++)
    			{
    				YAML.set("Rank."+count+".Name", MoneyRankingSet.get(count).stringValue);
    				YAML.set("Rank."+count+".Money", MoneyRankingSet.get(count).longValue);
    				YAML.set("NameSet."+MoneyRankingSet.get(count).stringValue+".Rank", count);
    				YAML.set("NameSet."+MoneyRankingSet.get(count).stringValue+".Money", MoneyRankingSet.get(count).longValue);
    			}
    			YAML.saveConfig();
            }
        }, 0,1200);//1분마다
    	return;
	}
	
	public void broadCastMessage()
	{
		YamlLoader config = new YamlLoader();
		config.getConfig("config.yml");
		BroadCastMessageTime = config.getInt("Server.BroadCastSecond");
    	if(BroadCastMessageTime!=0)
		if(BroadCastMessageCool>=BroadCastMessageTime)
		{
			BroadCastMessageCool=0;
			YamlLoader broadCast = new YamlLoader();
			broadCast.getConfig("BroadCast.yml");
			if(broadCast.contains("0") &&
			broadCast.getConfigurationSection("").getKeys(false).toArray().length != 0)
				Bukkit.broadcastMessage(broadCast.getString(broadCast.getConfigurationSection("").getKeys(false).toArray()[new util.NumericUtil().RandomNum(0, broadCast.getConfigurationSection("").getKeys(false).toArray().length-1)].toString()));
		}
		else
			BroadCastMessageCool++;
    	return;
	}
	
	public void checkShcedule()
	{
		Object[] scheduleList = Schedule.keySet().toArray();
		Object[] dungeonScheduleList = DungeonSchedule.keySet().toArray();
		long scheduleUTC = 0;
		short safeLineCounter = SafeLine;
		for(int count = 0; count<scheduleList.length;count++)
		{
			if(safeLineCounter <= 0) break;
				scheduleUTC=Long.parseLong(scheduleList[count].toString());
			if(scheduleUTC <= nowUTC)
			{
				excuteSchedule(scheduleUTC);
				safeLineCounter--;
				Schedule.remove(Long.parseLong(scheduleList[count].toString()));
			}
		}
		safeLineCounter = SafeLine;
		for(int count = 0; count<dungeonScheduleList.length;count++)
		{
			if(safeLineCounter <= 0) break;
				scheduleUTC=Long.parseLong(dungeonScheduleList[count].toString());
			if(scheduleUTC <= nowUTC)
			{
				dungeonExcuteSchedule(scheduleUTC);
				safeLineCounter--;
				DungeonSchedule.remove(Long.parseLong(dungeonScheduleList[count].toString()));
			}
		}
    	return;
	}

	public void excuteSchedule(long utc)
	{
		String type = Schedule.get(utc).getType();
		switch(type)
		{
		case "A_MS"://Area_MonsterSpawn
			new AreaServerTask().areaMobSpawn(utc);
			return;
		case "A_RB"://Area_RegenBlock
			new AreaServerTask().areaRegenBlock(utc);
			return;
		case "NV"://Navigation
			new ServerTaskNavigation().Navigation(utc);
			return;
		case "P_EC"://Player_Exchange
			new ServerTaskPlayer().ExChangeTimer(utc);
			return;
		case "G_SM"://Gamble_SlotMachine
			new ServerTaskPlayer().Gamble_SlotMachine_Rolling(utc);
			return;
		case "C_S"://Create_Structure
			new ServerTaskServer().CreateStructureMain(utc);
			return;
		case "Sound":
			new ServerTaskEffect().PlaySoundEffect(Schedule.get(utc));
			return;
		case "P_UTS":
			new ServerTaskPlayer().UseTeleportScroll(utc);
			return;
		default:
			return;
		}
	}
	
	public void dungeonExcuteSchedule(long utc)
	{
		String type = DungeonSchedule.get(utc).getType();
		switch(type)
		{
		case "D_RC"://Dungeon_RoomCreate
			new DungeonServerTask().CreateRoom(DungeonSchedule.get(utc));
			return;
		case "D_KRC"://Dungeon_KeyRoomCreate
			new DungeonServerTask().CreateKeyRoom(DungeonSchedule.get(utc));
			return;
		default:
			return;
		}
	}
}
