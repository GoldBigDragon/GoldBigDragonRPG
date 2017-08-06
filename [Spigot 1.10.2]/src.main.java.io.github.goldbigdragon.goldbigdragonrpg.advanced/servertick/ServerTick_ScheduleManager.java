package servertick;

import org.bukkit.Bukkit;

import dungeon.Dungeon_ScheduleObject;
import util.YamlLoader;



public class ServerTick_ScheduleManager
{
	public void saveCategoriFile()
	{
		YamlLoader PHSF = new YamlLoader();
		PHSF.getConfig("PlayerHashMapSaveFile.yml");

		PHSF.set("ServerTask", ServerTick_Main.ServerTask);
		for(int count = 0; count < ServerTick_Main.MobSpawningAreaList.size(); count++)
			PHSF.set("MonbSpawningAreaList."+count, ServerTick_Main.MobSpawningAreaList.get(count));
		PHSF.saveConfig();

		for(int count = 0; count < ServerTick_Main.DungeonSchedule.size(); count++)
		{
			long UTC =Long.parseLong(ServerTick_Main.DungeonSchedule.keySet().toArray()[count].toString());
			PHSF.set("DungeonSchedule."+count+".BRA", ServerTick_Main.DungeonSchedule.get(UTC).isBossRoomAdded());
			PHSF.set("DungeonSchedule."+count+".Count", ServerTick_Main.DungeonSchedule.get(UTC).getCount());
			PHSF.set("DungeonSchedule."+count+".UTC", ServerTick_Main.DungeonSchedule.get(UTC).getUTC());
			PHSF.set("DungeonSchedule."+count+".Type", ServerTick_Main.DungeonSchedule.get(UTC).getType());
			PHSF.set("DungeonSchedule."+count+".size", ServerTick_Main.DungeonSchedule.get(UTC).getSize());
			PHSF.set("DungeonSchedule."+count+".DungeonType", ServerTick_Main.DungeonSchedule.get(UTC).getDungeonType());
			PHSF.set("DungeonSchedule."+count+".DungeonName", ServerTick_Main.DungeonSchedule.get(UTC).getDungeonName());
			
			PHSF.set("DungeonSchedule."+count+".StartX", ServerTick_Main.DungeonSchedule.get(UTC).getStartX());
			PHSF.set("DungeonSchedule."+count+".StartY", ServerTick_Main.DungeonSchedule.get(UTC).getStartY());
			PHSF.set("DungeonSchedule."+count+".StartZ", ServerTick_Main.DungeonSchedule.get(UTC).getStartZ());

			PHSF.set("DungeonSchedule."+count+".SpawnX", ServerTick_Main.DungeonSchedule.get(UTC).getSpawnX());
			PHSF.set("DungeonSchedule."+count+".SpawnZ", ServerTick_Main.DungeonSchedule.get(UTC).getSpawnX());
			
			for(int counter=0; counter<ServerTick_Main.DungeonSchedule.get(UTC).getDungeonMakerSize();counter++)
				PHSF.set("DungeonSchedule."+count+".DungeonMaker."+counter, ServerTick_Main.DungeonSchedule.get(UTC).getDungeonMaker(counter));
			for(int counter=0; counter<ServerTick_Main.DungeonSchedule.get(UTC).getGridSize();counter++)
			{
				PHSF.set("DungeonSchedule."+count+".Grid."+counter, ServerTick_Main.DungeonSchedule.get(UTC).getGrid(counter));
				PHSF.set("DungeonSchedule."+count+".GridLoc."+counter, ServerTick_Main.DungeonSchedule.get(UTC).getGridLoc(counter));
			}
			for(int counter=0; counter<ServerTick_Main.DungeonSchedule.get(UTC).getKeyGridSize();counter++)
			{
				PHSF.set("DungeonSchedule."+count+".KeyGrid."+counter, ServerTick_Main.DungeonSchedule.get(UTC).getKeyGrid(counter));
				PHSF.set("DungeonSchedule."+count+".KeyGridLoc."+counter, ServerTick_Main.DungeonSchedule.get(UTC).getKeyGridLoc(counter));
			}
			PHSF.saveConfig();
		}
		
		for(int count = 0; count < ServerTick_Main.Schedule.size(); count++)
		{
			long UTC =Long.parseLong(ServerTick_Main.Schedule.keySet().toArray()[count].toString());
			PHSF.set("Schedule."+count+".Tick", ServerTick_Main.Schedule.get(UTC).getTick());
			PHSF.set("Schedule."+count+".count", ServerTick_Main.Schedule.get(UTC).getCount());
			PHSF.set("Schedule."+count+".MaxCount", ServerTick_Main.Schedule.get(UTC).getMaxCount());
			PHSF.set("Schedule."+count+".Type", ServerTick_Main.Schedule.get(UTC).getType());
			for(int counter=0; counter<ServerTick_Main.Schedule.get(UTC).getStringSize();counter++)
				PHSF.set("Schedule."+count+".String."+counter, ServerTick_Main.Schedule.get(UTC).getString((byte)counter));
			for(int counter=0; counter<ServerTick_Main.Schedule.get(UTC).getIntSize();counter++)
				PHSF.set("Schedule."+count+".Int."+counter, ServerTick_Main.Schedule.get(UTC).getInt((byte)counter));
			for(int counter=0; counter<ServerTick_Main.Schedule.get(UTC).getBooleanSize();counter++)
				PHSF.set("Schedule."+count+".Bool."+counter, ServerTick_Main.Schedule.get(UTC).getBoolean((byte)counter));
			PHSF.saveConfig();
		}
		for(int count = 0; count < ServerTick_Main.PlayerTaskList.size(); count++)
		{
			PHSF.set("PlayerTaskList."+count+".Name", ServerTick_Main.PlayerTaskList.keySet().toArray()[count].toString());
			PHSF.set("PlayerTaskList."+count+".TaskUTC", ServerTick_Main.PlayerTaskList.get(ServerTick_Main.PlayerTaskList.keySet().toArray()[count].toString()));
			PHSF.saveConfig();
		}
		for(int counter=0; counter<ServerTick_Main.NaviUsingList.size();counter++)
			PHSF.set("NaviUsingList."+counter, ServerTick_Main.NaviUsingList.get(counter));
		PHSF.saveConfig();
		ServerTick_Main.Schedule.clear();
		ServerTick_Main.NaviUsingList.clear();
	}
	
	public void loadCategoriFile()
	{
		YamlLoader PHSF = new YamlLoader();
		PHSF.getConfig("PlayerHashMapSaveFile.yml");

		if(PHSF.contains("ServerTask"))
			ServerTick_Main.ServerTask = PHSF.getString("ServerTask");
		if(PHSF.contains("MonbSpawningAreaList"))
		{
			for(int count = 0; count < PHSF.getConfigurationSection("MonbSpawningAreaList").getKeys(false).size(); count++)
				ServerTick_Main.MobSpawningAreaList.add(PHSF.getString("MonbSpawningAreaList."+count));
		}
		if(PHSF.contains("DungeonSchedule"))
		{
			for(int count = 0; count < PHSF.getConfigurationSection("DungeonSchedule").getKeys(false).size(); count++)
			{
				long UTC =PHSF.getLong("DungeonSchedule."+count+".UTC");
				Dungeon_ScheduleObject DSO = new Dungeon_ScheduleObject(PHSF.getString("DungeonSchedule."+count+".Type"));
				DSO.setBossRoomAdded(PHSF.getBoolean("DungeonSchedule."+count+".BRA"));
				DSO.setSize((byte) PHSF.getInt("DungeonSchedule."+count+".size"));
				DSO.setDungeonType(PHSF.getString("DungeonSchedule."+count+".DungeonType"));
				DSO.setDungeonName(PHSF.getString("DungeonSchedule."+count+".DungeonName"));
				DSO.setStartX(PHSF.getLong("DungeonSchedule."+count+".StartX"));
				DSO.setStartY((short) PHSF.getInt("DungeonSchedule."+count+".StartY"));
				DSO.setStartZ(PHSF.getLong("DungeonSchedule."+count+".StartZ"));
				DSO.setCount((short) PHSF.getInt("DungeonSchedule."+count+".Count"));
				DSO.setSpawnX(PHSF.getLong("DungeonSchedule."+count+".SpawnX"));
				DSO.setSpawnZ(PHSF.getLong("DungeonSchedule."+count+".SpawnZ"));
				for(int counter=0; counter<PHSF.getConfigurationSection("DungeonSchedule."+count+".DungeonMaker").getKeys(false).size();counter++)
					DSO.addDungeonMaker(PHSF.getString("DungeonSchedule."+count+".DungeonMaker."+counter));
				for(int counter=0; counter<PHSF.getConfigurationSection("DungeonSchedule."+count+".Grid").getKeys(false).size();counter++)
				{
					DSO.addGrid(PHSF.getString("DungeonSchedule."+count+".Grid."+counter).charAt(0));
					DSO.addGridLoc((short) PHSF.getInt("DungeonSchedule."+count+".GridLoc."+counter));
				}
				for(int counter=0; counter<PHSF.getConfigurationSection("DungeonSchedule."+count+".KeyGrid").getKeys(false).size();counter++)
				{
					DSO.addKeyGrid(PHSF.getString("DungeonSchedule."+count+".KeyGrid."+counter).charAt(0));
					DSO.addKeyGridLoc((short) PHSF.getInt("DungeonSchedule."+count+".KeyGridLoc."+counter));
				}
				
				ServerTick_Main.DungeonSchedule.put(UTC, DSO);
			}
		}
		if(PHSF.contains("Schedule"))
		{
			for(int count = 0; count < PHSF.getConfigurationSection("Schedule").getKeys(false).size(); count++)
			{
				long UTC =PHSF.getLong("Schedule."+count+".Tick");
				ServerTick_Object STSO = new ServerTick_Object(UTC, PHSF.getString("Schedule."+count+".Type"));
				STSO.setCount(PHSF.getInt("Schedule."+count+".count"));
				STSO.setMaxCount(PHSF.getInt("Schedule."+count+".MaxCount"));
				for(int counter=0; counter<STSO.getStringSize();counter++)
					STSO.setString((byte)counter, PHSF.getString("Schedule."+count+".String."+counter));
				for(int counter=0; counter<STSO.getIntSize();counter++)
					STSO.setInt((byte)counter, PHSF.getInt("Schedule."+count+".Int."+counter));
				for(int counter=0; counter<STSO.getBooleanSize();counter++)
					STSO.setBoolean((byte)counter, PHSF.getBoolean("Schedule."+count+".Bool."+counter));
				ServerTick_Main.Schedule.put(UTC, STSO);
			}
		}
		if(PHSF.contains("PlayerTaskList"))
		{
			for(int count = 0; count < PHSF.getConfigurationSection("PlayerTaskList").getKeys(false).size(); count++)
			{
				if(Bukkit.getServer().getPlayer(PHSF.getString("PlayerTaskList."+count+".Name")) != null)
					ServerTick_Main.PlayerTaskList.put(PHSF.getString("PlayerTaskList."+count+".Name"), PHSF.getString("PlayerTaskList."+count+".TaskUTC"));
			}
		}
		if(PHSF.contains("NaviUsingList"))
		{
			for(int count = 0; count < PHSF.getConfigurationSection("NaviUsingList").getKeys(false).size(); count++)
			{
				if(Bukkit.getServer().getPlayer(PHSF.getString("NaviUsingList."+count))!=null)
					if(Bukkit.getServer().getPlayer(PHSF.getString("NaviUsingList."+count)).isOnline())
						ServerTick_Main.NaviUsingList.add(PHSF.getString("NaviUsingList."+count));
			}
		}
		PHSF.removeKey("ServerTask");
		PHSF.removeKey("DungeonSchedule");
		PHSF.removeKey("MonbSpawningAreaList");
		PHSF.removeKey("Schedule");
		PHSF.removeKey("NaviUsingList");
		PHSF.removeKey("PlayerTaskList");
		PHSF.saveConfig();
	}
}
