package GBD.GoldBigDragon_Advanced.Event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class Level
{
	private GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
	private GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();
	private GBD.GoldBigDragon_Advanced.Effect.PacketSender PS = new GBD.GoldBigDragon_Advanced.Effect.PacketSender();
	
	
	public void EXPadd(Player player, long EXP, Location loc)
	{
	    YamlManager YM;
	    YamlController Event_YC=GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
	    YamlManager Config = Event_YC.getNewConfig("config.yml");
	    if(EXP*Config.getInt("Event.Multiple_EXP_Get") < 0
				||EXP*Config.getInt("Event.Multiple_EXP_Get") > Long.MAX_VALUE)
	    	EXP = Long.MAX_VALUE;
	    else
	    	EXP = EXP*Config.getInt("Event.Multiple_EXP_Get");
	  	if(Event_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		stat.CreateNewStats(player);
	  	
		YM = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		
		if(Main.PartyJoiner.containsKey(player)==false)
		{
			if(YM.getBoolean("Alert.EXPget") == true)
			{
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[SYSTEM] : 경험치 획득 "+ EXP );
			}
			YM.set("Stat.EXP", YM.getLong("Stat.EXP")+EXP);
			YM.saveConfig();
			for(;;)
			{
				if(YM.getLong("Stat.EXP") < YM.getLong("Stat.MaxEXP"))
					break;
				else
				{
					if(Config.getInt("Server.MaxLevel") <= YM.getInt("Stat.Level"))
						break;
					YM.set("Stat.EXP", YM.getLong("Stat.EXP")-YM.getLong("Stat.MaxEXP"));
					YM.set("Stat.Level", YM.getInt("Stat.Level")+1);
					YM.set("Stat.RealLevel", YM.getInt("Stat.RealLevel")+1);
					if((long)(YM.getLong("Stat.Level") + (1.1) * YM.getLong("Stat.MaxEXP")) < 0
							||(long)(YM.getLong("Stat.Level") + (1.1) * YM.getLong("Stat.MaxEXP")) > Long.MAX_VALUE)
						YM.set("Stat.MaxEXP", Long.MAX_VALUE);
					else
						YM.set("Stat.MaxEXP", (long)(YM.getLong("Stat.Level") + (1.1) * YM.getLong("Stat.MaxEXP")));
					YM.set("Stat.SkillPoint", YM.getInt("Stat.SkillPoint") + (Config.getInt("Server.Level_Up_SkillPoint")*Config.getInt("Event.Multiple_Level_Up_SkillPoint")));
					YM.set("Stat.StatPoint", YM.getInt("Stat.StatPoint") + (Config.getInt("Server.Level_Up_StatPoint")*Config.getInt("Event.Multiple_Level_Up_StatPoint")));
					YM.saveConfig();
					PS.sendTitleSubTitle(player, "\'"+ChatColor.WHITE+"Level Up!\'", "\'"+ChatColor.WHITE + "레벨 " +ChatColor.YELLOW+ YM.getInt("Stat.Level")+ChatColor.WHITE + "이 되었습니다!\'", 1, 3, 1);
					sound.SP(player, org.bukkit.Sound.LEVEL_UP, 1.5F, 1.8F);
					if(Config.getInt("Server.MaxLevel") <= YM.getInt("Stat.Level"))
						player.sendMessage(ChatColor.YELLOW + "[최대 레벨에 도달하여 더이상 레벨업 하실 수가 없습니다!]");
				}
			}
			YM.saveConfig();
			return;
		}
		else
		{
			PartyEXPadd(Main.PartyJoiner.get(player),EXP,loc);
		}
	}
	
	
	public void PartyEXPadd(Long PartyCreateTime, long EXP, Location loc)
	{
	    YamlManager YM;
	    YamlController Event_YC=GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
	    YamlManager Config = Event_YC.getNewConfig("config.yml");

	    EXP = EXP*Config.getInt("Event.Multiple_EXP_Get");

	    Player[] player = Main.Party.get(PartyCreateTime).getMember();
		int partymember=0;
		for(int count = 0; count<player.length;count++)
		{
	    	if(player[count].isOnline() == true)
	    	{
				if(player[count].getLocation().getWorld() == loc.getWorld())
				{
					if(loc.distance(player[count].getLocation()) <= Config.getInt("Party.EXPShareDistance"))
					{
						partymember = partymember+1;
					}
				}
	    	}
		}
		EXP = (int)(EXP/partymember);
		for(int count = 0; count<player.length;count++)
		{
	    	if(player[count].isOnline() == true)
	    	{
				if(player[count].getLocation().getWorld() == loc.getWorld())
				{
					if(loc.distance(player[count].getLocation()) <= Config.getInt("Party.EXPShareDistance"))
					{
					  	if(Event_YC.isExit("Stats/" + player[count].getUniqueId()+".yml") == false)
					  		stat.CreateNewStats(player[count]);
					  	
						YM = Event_YC.getNewConfig("Stats/" + player[count].getUniqueId()+".yml");
						
						if(YM.getBoolean("Alert.EXPget") == true) player[count].sendMessage(ChatColor.LIGHT_PURPLE+"[SYSTEM] : 경험치 획득 "+ EXP );
						YM.set("Stat.EXP", YM.getInt("Stat.EXP") + EXP);
						for(;;)
						{
							if(YM.getLong("Stat.EXP") < YM.getLong("Stat.MaxEXP"))
							{
								break;
							}
							else
							{
								YM.set("Stat.EXP", YM.getLong("Stat.EXP")-YM.getLong("Stat.MaxEXP"));
								YM.set("Stat.Level", YM.getInt("Stat.Level")+1);
								YM.set("Stat.RealLevel", YM.getInt("Stat.RealLevel")+1);
								if((long)(YM.getLong("Stat.Level") + (1.1) * YM.getLong("Stat.MaxEXP")) < 0
										||(long)(YM.getLong("Stat.Level") + (1.1) * YM.getLong("Stat.MaxEXP")) > Long.MAX_VALUE)
									YM.set("Stat.MaxEXP", Long.MAX_VALUE);
								else
									YM.set("Stat.MaxEXP", (long)(YM.getLong("Stat.Level") + (1.1) * YM.getLong("Stat.MaxEXP")));
								YM.set("Stat.SkillPoint", YM.getInt("Stat.SkillPoint") + (Config.getInt("Server.Level_Up_SkillPoint")*Config.getInt("Event.Multiple_Level_Up_SkillPoint")));
								YM.set("Stat.StatPoint", YM.getInt("Stat.StatPoint") + (Config.getInt("Server.Level_Up_StatPoint")*Config.getInt("Event.Multiple_Level_Up_StatPoint")));
								YM.saveConfig();
								PS.sendTitleSubTitle(player[count], "\'"+ChatColor.WHITE+"Level Up!\'", "\'"+ChatColor.WHITE + "레벨 " +ChatColor.YELLOW+ YM.getInt("Stat.Level")+ChatColor.WHITE + "이 되었습니다!\'", 1, 3, 1);
								sound.SP(player[count], org.bukkit.Sound.LEVEL_UP, 1.5F, 1.8F);
							}
						}
						YM.saveConfig();
					}
				}
			}
		}
		return;
	}
}
