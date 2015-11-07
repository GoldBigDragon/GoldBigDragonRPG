package GBD.GoldBigDragon_Advanced.Config;

import org.bukkit.entity.Player;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class QuestConfig
{
    public void CreateNewQuestConfig()
	{
	    YamlManager QuestConfig;
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		QuestConfig=Config_YC.getNewConfig("Quest/QuestList.yml");

    	if(Config_YC.isExit("Quest/QuestList.yml")==false)
    	{
    		QuestConfig.set("Do_not_Touch_This", true);
    		QuestConfig.saveConfig();
    	}
	  	return;
	}
    public void CreateNewPlayerConfig(Player player)
	{
	    YamlManager QuestConfig;
		YamlController Location_YC = GBD.GoldBigDragon_Advanced.Main.Main.Location_YC;
		QuestConfig=Location_YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		QuestConfig.set("PlayerName", player.getName());
		QuestConfig.set("PlayerUUID", player.getUniqueId().toString());
		QuestConfig.set("Started.1", null);
		QuestConfig.set("Ended.1", null);
		QuestConfig.saveConfig();
	  	return;
	}
	
}
