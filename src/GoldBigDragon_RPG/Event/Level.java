package GoldBigDragon_RPG.Event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Main.Main;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Level
{
	public void EXPadd(Player player, long EXP, Location loc)
	{
		if(EXP != 0)
		{
		    YamlManager YM;
		    YamlController Event_YC=GoldBigDragon_RPG.Main.Main.YC_1;
		    YamlManager Config = Event_YC.getNewConfig("config.yml");
		    if(EXP*Config.getInt("Event.Multiple_EXP_Get") < 0
					||EXP*Config.getInt("Event.Multiple_EXP_Get") > Long.MAX_VALUE)
		    	EXP = Long.MAX_VALUE;
		    else
		    	EXP = EXP*Config.getInt("Event.Multiple_EXP_Get");
		  	if(Event_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
		  		new GoldBigDragon_RPG.Config.StatConfig().CreateNewStats(player);

			YM = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
			
			if(Main.PartyJoiner.containsKey(player)==false)
			{
				if(YM.getBoolean("Alert.EXPget") == true)
				{
					player.sendMessage(ChatColor.LIGHT_PURPLE+"[SYSTEM] : ����ġ ȹ�� "+ EXP );
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

						new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(player, "\'"+ChatColor.WHITE+"Level Up!\'", "\'"+ChatColor.WHITE + "���� " +ChatColor.YELLOW+ YM.getInt("Stat.Level")+ChatColor.WHITE + "�� �Ǿ����ϴ�!\'", 1, 3, 1);
						new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.LEVEL_UP, 1.5F, 1.8F);
						if(Config.getInt("Server.MaxLevel") <= YM.getInt("Stat.Level"))
							player.sendMessage(ChatColor.YELLOW + "[�ִ� ������ �����Ͽ� ���̻� ������ �Ͻ� ���� �����ϴ�!]");
					}
				}
				YM.saveConfig();
				return;
			}
			else
				PartyEXPadd(Main.PartyJoiner.get(player),EXP,loc);
		}
		return;
	}
	
	
	public void PartyEXPadd(Long PartyCreateTime, long EXP, Location loc)
	{
	    YamlManager YM;
	    YamlController Event_YC=GoldBigDragon_RPG.Main.Main.YC_1;
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
					  		new GoldBigDragon_RPG.Config.StatConfig().CreateNewStats(player[count]);
					  	
						YM = Event_YC.getNewConfig("Stats/" + player[count].getUniqueId()+".yml");
						
						if(YM.getBoolean("Alert.EXPget") == true) player[count].sendMessage(ChatColor.LIGHT_PURPLE+"[SYSTEM] : ����ġ ȹ�� "+ EXP );
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
								new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(player[count], "\'"+ChatColor.WHITE+"Level Up!\'", "\'"+ChatColor.WHITE + "���� " +ChatColor.YELLOW+ YM.getInt("Stat.Level")+ChatColor.WHITE + "�� �Ǿ����ϴ�!\'", 1, 3, 1);
								new GoldBigDragon_RPG.Effect.Sound().SP(player[count], org.bukkit.Sound.LEVEL_UP, 1.5F, 1.8F);
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