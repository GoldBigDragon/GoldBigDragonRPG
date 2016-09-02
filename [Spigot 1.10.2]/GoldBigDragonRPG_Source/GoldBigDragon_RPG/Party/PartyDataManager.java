package GoldBigDragon_RPG.Party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class PartyDataManager
{
	public void saveParty()
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PartyConfig = YC.getNewConfig("Party/PartyList.yml");
		
		Object[] PartyList = GoldBigDragon_RPG.Main.ServerOption.Party.keySet().toArray();
		for(short count = 0; count < PartyList.length; count++)
		{
			Player[] PartyMember = GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyList[count]).getMember();
			PartyConfig.set("Party."+PartyList[count].toString()+".Title", GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyList[count]).getTitle());
			PartyConfig.set("Party."+PartyList[count].toString()+".Leader", GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyList[count]).getLeader());
			PartyConfig.set("Party."+PartyList[count].toString()+".PartyLock", GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyList[count]).getLock());
			PartyConfig.set("Party."+PartyList[count].toString()+".Password", GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyList[count]).getPassword());
			PartyConfig.set("Party."+PartyList[count].toString()+".Capacity", GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyList[count]).getCapacity());
			for(byte counter = 0; counter < PartyMember.length; counter++)
			{
				Player m = GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyList[count]).getMember()[counter];
				if(m != null)
					PartyConfig.set("Party."+PartyList[count].toString()+".Member."+counter, PartyMember[counter].getName());
				else
					PartyConfig.set("Party."+PartyList[count].toString()+".Member."+counter, "null");
			}
		}
		PartyConfig.saveConfig();
		GoldBigDragon_RPG.Main.ServerOption.Party.clear();
		GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.clear();
		return;
	}
	
	public void loadParty()
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
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
					GoldBigDragon_RPG.Main.ServerOption.Party.put(PCT, new PartyDataObject(PCT, PL, PT, PLock, PP, PC, PM));
					
					for(byte counter = 0; counter < PM.length;counter++)
						if(PM[counter] != null)
							if(Bukkit.getServer().getPlayer(PM[counter])==null)
								GoldBigDragon_RPG.Main.ServerOption.Party.get(PCT).QuitPartyOfflinePlayer(PM[counter]);
							else
							{
								if(Bukkit.getServer().getPlayer(PM[counter]).isOnline()==false)
									GoldBigDragon_RPG.Main.ServerOption.Party.get(PCT).QuitParty((Player) Bukkit.getServer().getOfflinePlayer(PM[counter]));
								else
									GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.put((Player) Bukkit.getServer().getPlayer(PM[counter]), PCT);
							}

					if(Bukkit.getServer().getOfflinePlayer(PL).isOnline() == false)
						GoldBigDragon_RPG.Main.ServerOption.Party.get(PCT).QuitPartyOfflinePlayer(PL);
					
					if(GoldBigDragon_RPG.Main.ServerOption.Party.get(PCT).getPartyMembers()==0)
						GoldBigDragon_RPG.Main.ServerOption.Party.remove(PCT);
				}
			}
		}
		
		PartyConfig.removeKey("Party");
		PartyConfig.removeKey("PartyJoiner");
		PartyConfig.saveConfig();
		return;
	}
}
