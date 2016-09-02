package GoldBigDragon_RPG.Quest;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class QuestConfig
{
    public void CreateNewQuestConfig()
	{
	    YamlManager QuestConfig;
	    YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		QuestConfig=YC.getNewConfig("Quest/QuestList.yml");

    	if(YC.isExit("Quest/QuestList.yml")==false)
    	{
    		QuestConfig.set("Do_not_Touch_This", true);
    		QuestConfig.saveConfig();
    	}
	  	return;
	}
    public void CreateNewPlayerConfig(Player player)
	{
	    YamlManager QuestConfig;
	    YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		QuestConfig = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		QuestConfig.set("PlayerName", player.getName());
		QuestConfig.set("PlayerUUID", player.getUniqueId().toString());
		QuestConfig.set("Started.1", null);
		QuestConfig.set("Ended.1", null);
		QuestConfig.saveConfig();
		
		String QuestName = YC.getNewConfig("ETC/NewBie.yml").getString("FirstQuest");
		if(QuestName.compareTo("null") != 0)
		{
			YamlManager QuestList= YC.getNewConfig("Quest/QuestList.yml");
			if(QuestList.contains(QuestName))
			{
				if(QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length != 0)
				{
					QuestConfig.set("Started."+QuestName+".Flow", 0);
					QuestConfig.set("Started."+QuestName+".Type", QuestList.getString(QuestName+".FlowChart."+0+".Type"));
					QuestConfig.saveConfig();
					player.sendMessage(ChatColor.YELLOW+"[Äù½ºÆ®] : »õ·Î¿î Äù½ºÆ®°¡ µµÂøÇß½À´Ï´Ù! " +ChatColor.GOLD+""+ChatColor.BOLD+"/Äù½ºÆ®");
					if(QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("Nevigation")==0||
						QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("Whisper")==0||
						QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("BroadCast")==0||
						QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("BlockPlace")==0||
						QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("VarChange")==0||
						QuestList.getString(QuestName+".FlowChart."+0+".Type").compareTo("TelePort")==0)
						new GoldBigDragon_RPG.Quest.QuestGUI().QuestTypeRouter(player, QuestName);
				}
			}
		}
		QuestConfig.saveConfig();
	  	return;
	}
	
}
