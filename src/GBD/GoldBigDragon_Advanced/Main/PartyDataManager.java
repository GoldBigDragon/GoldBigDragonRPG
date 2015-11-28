package GBD.GoldBigDragon_Advanced.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class PartyDataManager
{
	public void saveParty()
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager PartyConfig = Config_YC.getNewConfig("Party/PartyList.yml");
		
		Object[] PartyList = Main.Party.keySet().toArray();
		for(int count = 0; count < PartyList.length; count++)
		{
			Player[] PartyMember = Main.Party.get(PartyList[count]).getMember();
			PartyConfig.set("Party."+PartyList[count].toString()+".Title", Main.Party.get(PartyList[count]).getTitle());
			PartyConfig.set("Party."+PartyList[count].toString()+".Leader", Main.Party.get(PartyList[count]).getLeader());
			PartyConfig.set("Party."+PartyList[count].toString()+".PartyLock", Main.Party.get(PartyList[count]).getLock());
			PartyConfig.set("Party."+PartyList[count].toString()+".Password", Main.Party.get(PartyList[count]).getPassword());
			PartyConfig.set("Party."+PartyList[count].toString()+".Capacity", Main.Party.get(PartyList[count]).getCapacity());
			for(int counter = 0; counter < PartyMember.length; counter++)
			{
				Player m = Main.Party.get(PartyList[count]).getMember()[counter];
				if(m != null)
					PartyConfig.set("Party."+PartyList[count].toString()+".Member."+counter, PartyMember[counter].getName());
				else
					PartyConfig.set("Party."+PartyList[count].toString()+".Member."+counter, "null");
			}
		}
		PartyConfig.saveConfig();
		Main.Party.clear();
		Main.PartyJoiner.clear();
		return;
	}
	
	public void loadParty()
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager PartyConfig = Config_YC.getNewConfig("Party/PartyList.yml");

		if(PartyConfig.contains("Party"))
		{
			Object[] PartyList = PartyConfig.getConfigurationSection("Party").getKeys(false).toArray();
			for(int count = 0; count < PartyList.length; count++)
			{
				if(PartyConfig.contains("Party."+PartyList[count].toString()+".Member"))
				{
					long PCT = Long.parseLong(PartyList[count].toString());
					String PT = PartyConfig.getString("Party."+PartyList[count].toString()+".Title");
					String PL = PartyConfig.getString("Party."+PartyList[count].toString()+".Leader");
					boolean PLock = PartyConfig.getBoolean("Party."+PartyList[count].toString()+".PartyLock");
					String PP = PartyConfig.getString("Party."+PartyList[count].toString()+".Password");
					int PC = PartyConfig.getInt("Party."+PartyList[count].toString()+".Capacity");
					String[] PM = new String[PC];
					for(int counter = 0; counter < PartyConfig.getConfigurationSection("Party."+PartyList[count].toString()+".Member").getKeys(false).size();counter++)
						PM[counter] = PartyConfig.getString("Party."+PCT+".Member."+counter);
					Main.Party.put(PCT, new PartyDataObject(PCT, PL, PT, PLock, PP, PC, PM));
					
					for(int counter = 0; counter < PM.length;counter++)
						if(PM[counter] != null)
							if(Bukkit.getServer().getPlayer(PM[counter])==null)
								Main.Party.get(PCT).QuitParty((Player) Bukkit.getServer().getOfflinePlayer(PM[counter]));
							else
							{
								if(Bukkit.getServer().getPlayer(PM[counter]).isOnline()==false)
									Main.Party.get(PCT).QuitParty((Player) Bukkit.getServer().getOfflinePlayer(PM[counter]));
								else
									Main.PartyJoiner.put((Player) Bukkit.getServer().getPlayer(PM[counter]), PCT);
							}

					if(Bukkit.getServer().getOfflinePlayer(PL).isOnline() == false)
						Main.Party.get(PCT).QuitParty((Player) Bukkit.getServer().getOfflinePlayer(PL));
				}
			}
		}
		
		PartyConfig.removeKey("Party");
		PartyConfig.removeKey("PartyJoiner");
		PartyConfig.saveConfig();
		return;
	}
}
