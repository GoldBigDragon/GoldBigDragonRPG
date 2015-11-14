package GBD.GoldBigDragon_Advanced.ServerTick;

import org.bukkit.Bukkit;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class ServerTickScheduleManager
{
	public void saveCategoriFile()
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager PHSF=Config_YC.getNewConfig("PlayerHashMapSaveFile.yml");

		for(int count = 0; count < ServerTickMain.MobSpawningAreaList.size(); count++)
			PHSF.set("MonbSpawningAreaList."+count, ServerTickMain.MobSpawningAreaList.get(count));
		PHSF.saveConfig();
		for(int count = 0; count < ServerTickMain.Schedule.size(); count++)
		{
			long UTC =Long.parseLong(ServerTickMain.Schedule.keySet().toArray()[count].toString());
			PHSF.set("Schedule."+count+".Tick", ServerTickMain.Schedule.get(UTC).getTick());
			PHSF.set("Schedule."+count+".count", ServerTickMain.Schedule.get(UTC).getCount());
			PHSF.set("Schedule."+count+".MaxCount", ServerTickMain.Schedule.get(UTC).getMaxCount());
			PHSF.set("Schedule."+count+".Type", ServerTickMain.Schedule.get(UTC).getType());
			for(int counter=0; counter<ServerTickMain.Schedule.get(UTC).getStringSize();counter++)
				PHSF.set("Schedule."+count+".String."+counter, ServerTickMain.Schedule.get(UTC).getString((byte)counter));
			for(int counter=0; counter<ServerTickMain.Schedule.get(UTC).getIntSize();counter++)
				PHSF.set("Schedule."+count+".Int."+counter, ServerTickMain.Schedule.get(UTC).getInt((byte)counter));
			for(int counter=0; counter<ServerTickMain.Schedule.get(UTC).getBooleanSize();counter++)
				PHSF.set("Schedule."+count+".Bool."+counter, ServerTickMain.Schedule.get(UTC).getBoolean((byte)counter));
			PHSF.saveConfig();
		}
		for(int counter=0; counter<ServerTickMain.NaviUsingList.size();counter++)
			PHSF.set("NaviUsingList."+counter, ServerTickMain.NaviUsingList.get(counter));
		PHSF.saveConfig();
		ServerTickMain.Schedule.clear();
		ServerTickMain.NaviUsingList.clear();
	}
	
	public void loadCategoriFile()
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager PHSF=Config_YC.getNewConfig("PlayerHashMapSaveFile.yml");

		if(PHSF.contains("MonbSpawningAreaList"))
		{
			for(int count = 0; count < PHSF.getConfigurationSection("MonbSpawningAreaList").getKeys(false).size(); count++)
				ServerTickMain.MobSpawningAreaList.add(PHSF.getString("MonbSpawningAreaList."+count));
		}
		if(PHSF.contains("Schedule"))
		{
			for(int count = 0; count < PHSF.getConfigurationSection("Schedule").getKeys(false).size(); count++)
			{
				long UTC =PHSF.getLong("Schedule."+count+".Tick");
				ServerTickScheduleObject STSO = new ServerTickScheduleObject(UTC, PHSF.getString("Schedule."+count+".Type"));
				STSO.setCount(PHSF.getInt("Schedule."+count+".count"));
				STSO.setMaxCount(PHSF.getInt("Schedule."+count+".MaxCount"));
				for(int counter=0; counter<STSO.getStringSize();counter++)
					STSO.setString((byte)counter, PHSF.getString("Schedule."+count+".String."+counter));
				for(int counter=0; counter<STSO.getIntSize();counter++)
					STSO.setInt((byte)counter, PHSF.getInt("Schedule."+count+".Int."+counter));
				for(int counter=0; counter<STSO.getBooleanSize();counter++)
					STSO.setBoolean((byte)counter, PHSF.getBoolean("Schedule."+count+".Bool."+counter));
				ServerTickMain.Schedule.put(UTC, STSO);
			}
		}
		if(PHSF.contains("NaviUsingList"))
		{
			for(int count = 0; count < PHSF.getConfigurationSection("NaviUsingList").getKeys(false).size(); count++)
			{
				if(Bukkit.getServer().getPlayer(PHSF.getString("NaviUsingList."+count))!=null)
					if(Bukkit.getServer().getPlayer(PHSF.getString("NaviUsingList."+count)).isOnline())
						ServerTickMain.NaviUsingList.add(PHSF.getString("NaviUsingList."+count));
			}
		}
		PHSF.removeKey("MonbSpawningAreaList");
		PHSF.removeKey("Schedule");
		PHSF.removeKey("NaviUsingList");
		PHSF.saveConfig();
	}
}
