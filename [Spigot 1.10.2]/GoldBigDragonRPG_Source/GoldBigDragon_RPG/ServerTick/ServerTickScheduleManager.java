package GoldBigDragon_RPG.ServerTick;

import org.bukkit.Bukkit;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class ServerTickScheduleManager
{
	public void saveCategoriFile()
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PHSF=YC.getNewConfig("PlayerHashMapSaveFile.yml");

		PHSF.set("ServerTask", ServerTickMain.ServerTask);
		for(short count = 0; count < ServerTickMain.MobSpawningAreaList.size(); count++)
			PHSF.set("MonbSpawningAreaList."+count, ServerTickMain.MobSpawningAreaList.get(count));
		PHSF.saveConfig();

		for(short count = 0; count < ServerTickMain.DungeonSchedule.size(); count++)
		{
			long UTC =Long.parseLong(ServerTickMain.DungeonSchedule.keySet().toArray()[count].toString());
			PHSF.set("DungeonSchedule."+count+".BRA", ServerTickMain.DungeonSchedule.get(UTC).isBossRoomAdded());
			PHSF.set("DungeonSchedule."+count+".Count", ServerTickMain.DungeonSchedule.get(UTC).getCount());
			PHSF.set("DungeonSchedule."+count+".UTC", ServerTickMain.DungeonSchedule.get(UTC).getUTC());
			PHSF.set("DungeonSchedule."+count+".Type", ServerTickMain.DungeonSchedule.get(UTC).getType());
			PHSF.set("DungeonSchedule."+count+".size", ServerTickMain.DungeonSchedule.get(UTC).getSize());
			PHSF.set("DungeonSchedule."+count+".DungeonType", ServerTickMain.DungeonSchedule.get(UTC).getDungeonType());
			PHSF.set("DungeonSchedule."+count+".DungeonName", ServerTickMain.DungeonSchedule.get(UTC).getDungeonName());
			
			PHSF.set("DungeonSchedule."+count+".StartX", ServerTickMain.DungeonSchedule.get(UTC).getStartX());
			PHSF.set("DungeonSchedule."+count+".StartY", ServerTickMain.DungeonSchedule.get(UTC).getStartY());
			PHSF.set("DungeonSchedule."+count+".StartZ", ServerTickMain.DungeonSchedule.get(UTC).getStartZ());

			PHSF.set("DungeonSchedule."+count+".SpawnX", ServerTickMain.DungeonSchedule.get(UTC).getSpawnX());
			PHSF.set("DungeonSchedule."+count+".SpawnZ", ServerTickMain.DungeonSchedule.get(UTC).getSpawnX());
			
			for(short counter=0; counter<ServerTickMain.DungeonSchedule.get(UTC).getDungeonMakerSize();counter++)
				PHSF.set("DungeonSchedule."+count+".DungeonMaker."+counter, ServerTickMain.DungeonSchedule.get(UTC).getDungeonMaker(counter));
			for(short counter=0; counter<ServerTickMain.DungeonSchedule.get(UTC).getGridSize();counter++)
			{
				PHSF.set("DungeonSchedule."+count+".Grid."+counter, ServerTickMain.DungeonSchedule.get(UTC).getGrid(counter));
				PHSF.set("DungeonSchedule."+count+".GridLoc."+counter, ServerTickMain.DungeonSchedule.get(UTC).getGridLoc(counter));
			}
			for(short counter=0; counter<ServerTickMain.DungeonSchedule.get(UTC).getKeyGridSize();counter++)
			{
				PHSF.set("DungeonSchedule."+count+".KeyGrid."+counter, ServerTickMain.DungeonSchedule.get(UTC).getKeyGrid(counter));
				PHSF.set("DungeonSchedule."+count+".KeyGridLoc."+counter, ServerTickMain.DungeonSchedule.get(UTC).getKeyGridLoc(counter));
			}
			PHSF.saveConfig();
		}
		
		for(short count = 0; count < ServerTickMain.Schedule.size(); count++)
		{
			long UTC =Long.parseLong(ServerTickMain.Schedule.keySet().toArray()[count].toString());
			PHSF.set("Schedule."+count+".Tick", ServerTickMain.Schedule.get(UTC).getTick());
			PHSF.set("Schedule."+count+".count", ServerTickMain.Schedule.get(UTC).getCount());
			PHSF.set("Schedule."+count+".MaxCount", ServerTickMain.Schedule.get(UTC).getMaxCount());
			PHSF.set("Schedule."+count+".Type", ServerTickMain.Schedule.get(UTC).getType());
			for(short counter=0; counter<ServerTickMain.Schedule.get(UTC).getStringSize();counter++)
				PHSF.set("Schedule."+count+".String."+counter, ServerTickMain.Schedule.get(UTC).getString((byte)counter));
			for(short counter=0; counter<ServerTickMain.Schedule.get(UTC).getIntSize();counter++)
				PHSF.set("Schedule."+count+".Int."+counter, ServerTickMain.Schedule.get(UTC).getInt((byte)counter));
			for(short counter=0; counter<ServerTickMain.Schedule.get(UTC).getBooleanSize();counter++)
				PHSF.set("Schedule."+count+".Bool."+counter, ServerTickMain.Schedule.get(UTC).getBoolean((byte)counter));
			PHSF.saveConfig();
		}
		for(short count = 0; count < ServerTickMain.PlayerTaskList.size(); count++)
		{
			PHSF.set("PlayerTaskList."+count+".Name", ServerTickMain.PlayerTaskList.keySet().toArray()[count].toString());
			PHSF.set("PlayerTaskList."+count+".TaskUTC", ServerTickMain.PlayerTaskList.get(ServerTickMain.PlayerTaskList.keySet().toArray()[count].toString()));
			PHSF.saveConfig();
		}
		for(short counter=0; counter<ServerTickMain.NaviUsingList.size();counter++)
			PHSF.set("NaviUsingList."+counter, ServerTickMain.NaviUsingList.get(counter));
		PHSF.saveConfig();
		ServerTickMain.Schedule.clear();
		ServerTickMain.NaviUsingList.clear();
	}
	
	public void loadCategoriFile()
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PHSF = YC.getNewConfig("PlayerHashMapSaveFile.yml");

		if(PHSF.contains("ServerTask"))
			ServerTickMain.ServerTask = PHSF.getString("ServerTask");
		if(PHSF.contains("MonbSpawningAreaList"))
		{
			for(short count = 0; count < PHSF.getConfigurationSection("MonbSpawningAreaList").getKeys(false).size(); count++)
				ServerTickMain.MobSpawningAreaList.add(PHSF.getString("MonbSpawningAreaList."+count));
		}
		if(PHSF.contains("DungeonSchedule"))
		{
			for(short count = 0; count < PHSF.getConfigurationSection("DungeonSchedule").getKeys(false).size(); count++)
			{
				long UTC =PHSF.getLong("DungeonSchedule."+count+".UTC");
				DungeonScheduleObject DSO = new DungeonScheduleObject(PHSF.getString("DungeonSchedule."+count+".Type"));
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
				for(short counter=0; counter<PHSF.getConfigurationSection("DungeonSchedule."+count+".DungeonMaker").getKeys(false).size();counter++)
					DSO.addDungeonMaker(PHSF.getString("DungeonSchedule."+count+".DungeonMaker."+counter));
				for(short counter=0; counter<PHSF.getConfigurationSection("DungeonSchedule."+count+".Grid").getKeys(false).size();counter++)
				{
					DSO.addGrid(PHSF.getString("DungeonSchedule."+count+".Grid."+counter).charAt(0));
					DSO.addGridLoc((short) PHSF.getInt("DungeonSchedule."+count+".GridLoc."+counter));
				}
				for(short counter=0; counter<PHSF.getConfigurationSection("DungeonSchedule."+count+".KeyGrid").getKeys(false).size();counter++)
				{
					DSO.addKeyGrid(PHSF.getString("DungeonSchedule."+count+".KeyGrid."+counter).charAt(0));
					DSO.addKeyGridLoc((short) PHSF.getInt("DungeonSchedule."+count+".KeyGridLoc."+counter));
				}
				
				ServerTickMain.DungeonSchedule.put(UTC, DSO);
			}
		}
		if(PHSF.contains("Schedule"))
		{
			for(short count = 0; count < PHSF.getConfigurationSection("Schedule").getKeys(false).size(); count++)
			{
				long UTC =PHSF.getLong("Schedule."+count+".Tick");
				ServerTickScheduleObject STSO = new ServerTickScheduleObject(UTC, PHSF.getString("Schedule."+count+".Type"));
				STSO.setCount(PHSF.getInt("Schedule."+count+".count"));
				STSO.setMaxCount(PHSF.getInt("Schedule."+count+".MaxCount"));
				for(short counter=0; counter<STSO.getStringSize();counter++)
					STSO.setString((byte)counter, PHSF.getString("Schedule."+count+".String."+counter));
				for(short counter=0; counter<STSO.getIntSize();counter++)
					STSO.setInt((byte)counter, PHSF.getInt("Schedule."+count+".Int."+counter));
				for(short counter=0; counter<STSO.getBooleanSize();counter++)
					STSO.setBoolean((byte)counter, PHSF.getBoolean("Schedule."+count+".Bool."+counter));
				ServerTickMain.Schedule.put(UTC, STSO);
			}
		}
		if(PHSF.contains("PlayerTaskList"))
		{
			for(short count = 0; count < PHSF.getConfigurationSection("PlayerTaskList").getKeys(false).size(); count++)
			{
				if(Bukkit.getServer().getPlayer(PHSF.getString("PlayerTaskList."+count+".Name")) != null)
					ServerTickMain.PlayerTaskList.put(PHSF.getString("PlayerTaskList."+count+".Name"), PHSF.getString("PlayerTaskList."+count+".TaskUTC"));
			}
		}
		if(PHSF.contains("NaviUsingList"))
		{
			for(short count = 0; count < PHSF.getConfigurationSection("NaviUsingList").getKeys(false).size(); count++)
			{
				if(Bukkit.getServer().getPlayer(PHSF.getString("NaviUsingList."+count))!=null)
					if(Bukkit.getServer().getPlayer(PHSF.getString("NaviUsingList."+count)).isOnline())
						ServerTickMain.NaviUsingList.add(PHSF.getString("NaviUsingList."+count));
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
