package GBD_RPG.Party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Party_DataManager
{
	public void saveParty()
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager PartyConfig = YC.getNewConfig("Party/PartyList.yml");
		
		Object[] PartyList = GBD_RPG.Main_Main.Main_ServerOption.Party.keySet().toArray();
		for(short count = 0; count < PartyList.length; count++)
		{
			Player[] PartyMember = GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyList[count]).getMember();
			PartyConfig.set("Party."+PartyList[count].toString()+".Title", GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyList[count]).getTitle());
			PartyConfig.set("Party."+PartyList[count].toString()+".Leader", GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyList[count]).getLeader());
			PartyConfig.set("Party."+PartyList[count].toString()+".PartyLock", GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyList[count]).getLock());
			PartyConfig.set("Party."+PartyList[count].toString()+".Password", GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyList[count]).getPassword());
			PartyConfig.set("Party."+PartyList[count].toString()+".Capacity", GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyList[count]).getCapacity());
			for(byte counter = 0; counter < PartyMember.length; counter++)
			{
				Player m = GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyList[count]).getMember()[counter];
				if(m != null)
					PartyConfig.set("Party."+PartyList[count].toString()+".Member."+counter, PartyMember[counter].getName());
				else
					PartyConfig.set("Party."+PartyList[count].toString()+".Member."+counter, "null");
			}
		}
		PartyConfig.saveConfig();
		GBD_RPG.Main_Main.Main_ServerOption.Party.clear();
		GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.clear();
		return;
	}
	
	public void loadParty()
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager PartyConfig = YC.getNewConfig("Party/PartyList.yml");

		if(PartyConfig.contains("Party"))
		{
			Object[] PartyList = PartyConfig.getConfigurationSection("Party").getKeys(false).toArray();
			for(short count = 0; count < PartyList.length; count++)
			{
				if(PartyConfig.contains("Party."+PartyList[count].toString()+".Member"))
				{
					long PCT = Long.parseLong(PartyList[count].toString());
					String PT = PartyConfig.getString("Party."+PartyList[count].toString()+".Title");
					String PL = PartyConfig.getString("Party."+PartyList[count].toString()+".Leader");
					boolean PLock = PartyConfig.getBoolean("Party."+PartyList[count].toString()+".PartyLock");
					String PP = PartyConfig.getString("Party."+PartyList[count].toString()+".Password");
					byte PC = (byte) PartyConfig.getInt("Party."+PartyList[count].toString()+".Capacity");
					String[] PM = new String[PC];
					for(byte counter = 0; counter < PartyConfig.getConfigurationSection("Party."+PartyList[count].toString()+".Member").getKeys(false).size();counter++)
						PM[counter] = PartyConfig.getString("Party."+PCT+".Member."+counter);
					GBD_RPG.Main_Main.Main_ServerOption.Party.put(PCT, new Party_Object(PCT, PL, PT, PLock, PP, PC, PM));
					
					for(byte counter = 0; counter < PM.length;counter++)
						if(PM[counter] != null)
							if(Bukkit.getServer().getPlayer(PM[counter])==null)
								GBD_RPG.Main_Main.Main_ServerOption.Party.get(PCT).QuitPartyOfflinePlayer(PM[counter]);
							else
							{
								if(Bukkit.getServer().getPlayer(PM[counter]).isOnline()==false)
									GBD_RPG.Main_Main.Main_ServerOption.Party.get(PCT).QuitParty((Player) Bukkit.getServer().getOfflinePlayer(PM[counter]));
								else
									GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.put((Player) Bukkit.getServer().getPlayer(PM[counter]), PCT);
							}

					if(Bukkit.getServer().getOfflinePlayer(PL).isOnline() == false)
						GBD_RPG.Main_Main.Main_ServerOption.Party.get(PCT).QuitPartyOfflinePlayer(PL);
					
					if(GBD_RPG.Main_Main.Main_ServerOption.Party.get(PCT).getPartyMembers()==0)
						GBD_RPG.Main_Main.Main_ServerOption.Party.remove(PCT);
				}
			}
		}
		
		PartyConfig.removeKey("Party");
		PartyConfig.removeKey("PartyJoiner");
		PartyConfig.saveConfig();
		return;
	}
}
