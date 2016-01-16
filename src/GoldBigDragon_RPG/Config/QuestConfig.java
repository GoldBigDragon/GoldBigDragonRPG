package GoldBigDragon_RPG.Config;

import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class QuestConfig
{
    public void CreateNewQuestConfig()
	{
	    YamlManager QuestConfig;
		YamlController Config_YC = GoldBigDragon_RPG.Main.Main.YC_1;
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
		YamlController Location_YC = GoldBigDragon_RPG.Main.Main.YC_1;
		QuestConfig=Location_YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		QuestConfig.set("PlayerName", player.getName());
		QuestConfig.set("PlayerUUID", player.getUniqueId().toString());
		QuestConfig.set("Started.1", null);
		QuestConfig.set("Ended.1", null);
		QuestConfig.saveConfig();
	  	return;
	}
	
}
