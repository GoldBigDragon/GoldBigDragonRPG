package GBD.GoldBigDragon_Advanced.ServerTick;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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
        	  	//Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA +"1"+"초 경과");
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
		case "NV"://Navigation
			Navigation(UTC);
			return;
		default:
			return;
		}
	}
	
	public void AreaMobSpawn(Long UTC)
	{
		String AreaName = Schedule.get(UTC).getString((byte)0);
		if(MobSpawningAreaList.contains(AreaName))
		{
			YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
			YamlManager AreaConfig =GUI_YC.getNewConfig("Area/AreaList.yml");
			if(AreaConfig.contains(Schedule.get(UTC).getString((byte)0)+".MonsterSpawnRule."+Schedule.get(UTC).getString((byte)3)))
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
							/*
							Location loc = new Location(Bukkit.getServer().getWorld(Schedule.get(UTC).getString((byte)1)), Schedule.get(UTC).getInt((byte)0), Schedule.get(UTC).getInt((byte)1), Schedule.get(UTC).getInt((byte)2));
							if(getEntitiesNearby(loc, 20).size() <= Schedule.get(UTC).getInt((byte)5))
							{
								GBD.GoldBigDragon_Advanced.ETC.Monster MC = new GBD.GoldBigDragon_Advanced.ETC.Monster();
								for(int mobspawn=0;mobspawn<Schedule.get(UTC).getInt((byte)4);mobspawn++)
								{
									MC.SpawnMob(loc.add(-0.5, -1,-0.5), mob);
								}
							}
							 */
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

	public void Navigation(Long UTC)
	{
		if(Schedule.get(UTC).getMaxCount() != -1)
		{
			if(Schedule.get(UTC).getCount() < Schedule.get(UTC).getMaxCount())
			{
				Schedule.get(UTC).setCount(Schedule.get(UTC).getCount()+1);
				Routing(Schedule.get(UTC));
			}
		}
		else
			Routing(Schedule.get(UTC));
	}
	
	public void Routing(ServerTickScheduleObject STSO)
	{
		if(Bukkit.getServer().getPlayer(STSO.getString((byte)2)).isOnline())
		{
			Player player = Bukkit.getServer().getPlayer(STSO.getString((byte)2));

			Location DestinationLoc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte)1)), STSO.getInt((byte)0), STSO.getInt((byte)1), STSO.getInt((byte)2));
			Location SourceLoc = player.getLocation();
			
			int x = (int) (DestinationLoc.getX() - SourceLoc.getX());
			int y = (int) (DestinationLoc.getY() - SourceLoc.getY());
			int z = (int) (DestinationLoc.getZ() - SourceLoc.getZ());
			
			byte nearx = 0;
			byte neary = 0;
			byte nearz = 0;
			
			if(x > 10)//동쪽
				nearx = 1;
			else if(x < -10)
				nearx = -1;
			else
				nearx = 0;
			if(z > 10)//남쪽
				nearz = 1;
			else if(z < -10)
				nearz = -1;
			else
				nearz = 0;
			if(y > 3)//위쪽
				neary = 1;
			else if(y < -3)
				neary = -1;
			else
				neary = 0;
			
			String Way = "F";
			if(nearx == 1)
			{
				if(nearz==0)Way="E";
				if(nearz==1)Way="EN";
				if(nearz==-1)Way="ES";
			}
			else if(nearx == -1)
			{
				if(nearz==0)Way="W";
				if(nearz==1)Way="WN";
				if(nearz==-1)Way="WS";
			}
			else
			{
				if(nearz==1)Way="N";
				if(nearz==-1)Way="S";
			}
			if(nearx==0&&neary==0&&nearz==0)
				FindedWay(player,DestinationLoc);
			else
			{
				ShowWay(player,Way,neary);
				STSO.setTick(STSO.getTick()+1000);
				Schedule.put(STSO.getTick()+1000, STSO);
			}
		}
		return;
	}
	
	public void ShowWay(Player player,String Way, int y)
	{
		if(Way=="N")
			ArrowParticle_0(player, (byte) 0, y);
		else if(Way=="E")
			ArrowParticle_0(player, (byte) 1, y);
		else if(Way=="S")
			ArrowParticle_0(player, (byte) 2, y);
		else if(Way=="W")
			ArrowParticle_0(player, (byte) 3, y);
		
		if(Way=="EN")
			ArrowParticle_45(player, (byte) 1, y);
		else if(Way=="ES")
			ArrowParticle_45(player, (byte) 2, y);
		else if(Way=="WN")
			ArrowParticle_45(player, (byte) 3, y);
		else if(Way=="WS")
			ArrowParticle_45(player, (byte) 4, y);
		return;
	}
	
	private void ArrowParticle_0(Player player, byte rotation, int y)
	{
		GBD.GoldBigDragon_Advanced.Effect.Particle P = new GBD.GoldBigDragon_Advanced.Effect.Particle();

		P.RLPLR(player, 0, 0.4, 0.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 0.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 0.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 1, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 1.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 1.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 1.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0.5, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, -0.5, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0.25, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, -0.25, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0.75, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, -0.75, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, -1, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		
		if(y > 0)
			UPSign_0(player, rotation);
		else if(y < 0)
			DNSign_0(player, rotation);
	}

	private void ArrowParticle_45(Player player, byte rotation, int y)
	{
		GBD.GoldBigDragon_Advanced.Effect.Particle P = new GBD.GoldBigDragon_Advanced.Effect.Particle();
		P.RLPLR(player, 0.5, 0.4, 0.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 0.75, 0.4, 0.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1, 0.4, 1, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1.25, 0.4, 1.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1.5, 0.4, 1.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 1.75, 0.4, 1.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.25, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.5, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.75, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 2, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 2.25, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 2.5, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 3, 0.4, 2.75, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.25, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.5, 0.4, 3, Effect.LAVADRIP, 0, rotation);
		P.RLPLR(player, 2.75, 0.4, 3, Effect.LAVADRIP, 0, rotation);

		if(y>0)
			UPSign_45(player, rotation);
		else if(y<0)
			DNSign_45(player, rotation);
	}
	
	private void UPSign_0(Player player, byte rotation)
	{
		GBD.GoldBigDragon_Advanced.Effect.Particle P = new GBD.GoldBigDragon_Advanced.Effect.Particle();
		//U
		P.RLPLRR(player, -0.1, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.4, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.3, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.2, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		//P
		P.RLPLRR(player, 0.1, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.2, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.4, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.2, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.4, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
	}
	
	private void UPSign_45(Player player, byte rotation)
	{
		GBD.GoldBigDragon_Advanced.Effect.Particle P = new GBD.GoldBigDragon_Advanced.Effect.Particle();
		//U
		P.RLPLRR(player, 2.4, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.3, 1.4, 2.6, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.2, 1.4, 2.7, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.1, 1.4, 2.8, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 2, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.9, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.8, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.7, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.6, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.5, 2.9, Effect.WATERDRIP, 0, rotation);
		//P
		P.RLPLRR(player, 2.5, 2, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.9, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.8, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.7, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.6, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.5, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.4, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.6, 2, 2.2, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 2, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.8, 2, 2.0, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.6, 1.7, 2.2, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.7, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.8, 1.7, 2.0, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.9, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.8, 1.9, Effect.WATERDRIP, 0, rotation);
	}

	private void DNSign_0(Player player, byte rotation)
	{
		GBD.GoldBigDragon_Advanced.Effect.Particle P = new GBD.GoldBigDragon_Advanced.Effect.Particle();
		//D
		P.RLPLRR(player, -0.2, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.3, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.4, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.1, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.5, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.4, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.3, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, -0.2, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		//W
		P.RLPLRR(player, 0.1, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.1, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.2, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.3, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.4, 1.4, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 2, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 0.5, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
	}
	
	private void DNSign_45(Player player, byte rotation)
	{
		GBD.GoldBigDragon_Advanced.Effect.Particle P = new GBD.GoldBigDragon_Advanced.Effect.Particle();
		//D
		P.RLPLRR(player, 2, 1.4, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.5, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.6, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.7, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.8, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.9, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 2, 2.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.1, 2, 2.8, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.2, 2, 2.7, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.3, 2, 2.6, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.9, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.8, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.7, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.6, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.4, 1.5, 2.5, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.3, 1.4, 2.6, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.2, 1.4, 2.7, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.1, 1.4, 2.8, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2, 1.4, 2.9, Effect.WATERDRIP, 0, rotation);
		//W
		P.RLPLRR(player, 2.5, 2, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.9, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.8, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.7, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.6, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.5, 1.5, 2.3, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.6, 1.4, 2.2, Effect.WATERDRIP, 0, rotation);
		
		P.RLPLRR(player, 2.7, 2, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.9, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.8, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.7, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.6, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.7, 1.5, 2.1, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.8, 1.4, 2, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 2, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.9, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.8, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.7, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.6, 1.9, Effect.WATERDRIP, 0, rotation);
		P.RLPLRR(player, 2.9, 1.5, 1.9, Effect.WATERDRIP, 0, rotation);
	}
	
	public void FindedWay(Player player, Location DestinationLoc)
	{
		GBD.GoldBigDragon_Advanced.Effect.Particle P = new GBD.GoldBigDragon_Advanced.Effect.Particle();
		GBD.GoldBigDragon_Advanced.Util.Number n = new GBD.GoldBigDragon_Advanced.Util.Number();
		Location t = DestinationLoc;
		for(int count = 0; count<10000; count ++)
		{
			t.setX(DestinationLoc.getX()-n.RandomNum(0, 1));
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			t.setX(DestinationLoc.getX()+n.RandomNum(0, 1));
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			t.setY(DestinationLoc.getY()-n.RandomNum(0, 1));
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			t.setY(DestinationLoc.getY()+n.RandomNum(0, 1));
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			t.setZ(DestinationLoc.getZ()-n.RandomNum(0, 1));
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			t.setZ(DestinationLoc.getZ()+n.RandomNum(0, 1));
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
			P.PPL(player,t, Effect.EXPLOSION_HUGE, 100);
		}
		player.sendMessage("찾음");
		return;
	}
/*
	public List<Entity> getEntitiesNearby(Location loc, double range)
	{
        List<Entity> entities = loc.getWorld().getEntities();
        double distance = (range * range);
        for(Iterator<Entity> it = entities.iterator(); it.hasNext();)
        {
            if(it.next().getLocation().distanceSquared(loc) <= distance)
                it.remove();
        }
        return entities;
    }
    */
}
