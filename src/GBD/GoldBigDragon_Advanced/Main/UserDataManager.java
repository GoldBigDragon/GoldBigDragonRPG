package GBD.GoldBigDragon_Advanced.Main;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class UserDataManager
{
	public void saveCategoriFile()
	{
    	Collection<? extends Player> playerlister = Bukkit.getServer().getOnlinePlayers();
    	Player[] playerlist = new Player[playerlister.size()];
    	playerlister.toArray(playerlist);
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager PHSF=Config_YC.getNewConfig("PlayerHashMapSaveFile.yml");
		for(int count = 0; count < playerlist.length;count++)
		{
			if(playerlist[count].isOnline()&&Main.UserData.containsKey(playerlist[count]))
			{
				Player nowPlayer = Bukkit.getPlayer(playerlist[count].getName());
				String PlayerName = nowPlayer.getName();
				PHSF.set(PlayerName+".Player", PlayerName);
				if(Main.UserData.get(nowPlayer).getType()==null)
					PHSF.set(PlayerName+".Type", "null");
				else
					PHSF.set(PlayerName+".Type", Main.UserData.get(nowPlayer).getType());
				for(byte counter = 0; counter < Main.UserData.get(nowPlayer).getStringSize();counter++)
				{
					if(Main.UserData.get(nowPlayer).getString(counter)==null)
						PHSF.set(PlayerName+".String"+counter, "null");
					else
						PHSF.set(PlayerName+".String"+counter, Main.UserData.get(nowPlayer).getString(counter));
				}
				for(byte counter = 0; counter < Main.UserData.get(nowPlayer).getIntSize();counter++)
					PHSF.set(PlayerName+".Int"+counter, Main.UserData.get(nowPlayer).getInt(counter));
				for(byte counter = 0; counter < Main.UserData.get(nowPlayer).getBooleanSize();counter++)
					PHSF.set(PlayerName+".Boolean"+counter, Main.UserData.get(nowPlayer).getBoolean(counter));
				PHSF.saveConfig();
			}
		}
		Main.UserData.clear();
	}
	
	public void loadCategoriFile()
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager PHSF=Config_YC.getNewConfig("PlayerHashMapSaveFile.yml");

    	Collection<? extends Player> playerlister = Bukkit.getServer().getOnlinePlayers();
    	Player[] playerlist = new Player[playerlister.size()];
    	playerlister.toArray(playerlist);
		for(int count = 0; count < playerlist.length;count++)
		{
			Player nowPlayer = Bukkit.getPlayer(playerlist[count].getName());
			String PlayerName = nowPlayer.getName();
			if(nowPlayer.isOp()==true)
			{
				if(PHSF.getString(PlayerName+".Player") != null)
				{
					if(Main.UserData.containsKey(nowPlayer)==false)
						Main.UserData.put(nowPlayer, new UserDataObject(nowPlayer));
					Bukkit.broadcastMessage(Main.UserData.get(nowPlayer).getString((byte)0));
					if(PHSF.getString(PlayerName+".Type")==null)
						Main.UserData.get(nowPlayer).setType(null);
					else
						Main.UserData.get(nowPlayer).setType(PHSF.getString(PlayerName+".Type"));
					for(byte counter = 0; counter < Main.UserData.get(nowPlayer).getStringSize();counter++)
					{
						if(PHSF.getString(PlayerName+".String"+counter).equals("null"))
							Main.UserData.get(nowPlayer).setString(counter, null);
						else
							Main.UserData.get(nowPlayer).setString(counter, PHSF.getString(PlayerName+".String"+counter));
					}
					for(byte counter = 0; counter < Main.UserData.get(nowPlayer).getIntSize();counter++)
						Main.UserData.get(nowPlayer).setInt(counter, PHSF.getInt(PlayerName+".Int"+counter));
					for(byte counter = 0; counter < Main.UserData.get(nowPlayer).getBooleanSize();counter++)
						Main.UserData.get(nowPlayer).setBoolean(counter, PHSF.getBoolean(PlayerName+".Boolean"+counter));
				}
				else
				{
					Main.UserData.put(nowPlayer, new UserDataObject(nowPlayer));
				}
			}
		}
		for(int count = 0; count < playerlist.length;count++)
			PHSF.removeKey(playerlist[count].getName());
		PHSF.saveConfig();
	}
}
