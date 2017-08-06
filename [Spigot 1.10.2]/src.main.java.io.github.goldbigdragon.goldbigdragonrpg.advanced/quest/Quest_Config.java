package quest;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import util.YamlLoader;




public class Quest_Config
{
    public void CreateNewQuestConfig()
	{
	    YamlLoader questListYaml = new YamlLoader();
		questListYaml.getConfig("Quest/QuestList.yml");
    	if(questListYaml.isExit("Quest/QuestList.yml")==false)
    	{
    		questListYaml.set("Do_not_Touch_This", true);
    		questListYaml.saveConfig();
    	}
	  	return;
	}
    public void CreateNewPlayerConfig(Player player)
	{
	    YamlLoader playerQuestListYaml = new YamlLoader();
		playerQuestListYaml.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		playerQuestListYaml.set("PlayerName", player.getName());
		playerQuestListYaml.set("PlayerUUID", player.getUniqueId().toString());
		playerQuestListYaml.createSection("Started");
		playerQuestListYaml.createSection("Ended");
		playerQuestListYaml.saveConfig();

	    YamlLoader newbieQuestListYaml = new YamlLoader();
	    newbieQuestListYaml.getConfig("ETC/NewBie.yml");
		String QuestName = newbieQuestListYaml.getString("FirstQuest");
		if(QuestName.compareTo("null") != 0)
		{
		    YamlLoader questListYaml = new YamlLoader();
			questListYaml.getConfig("Quest/QuestList.yml");
			if(questListYaml.contains(QuestName))
			{
				if(questListYaml.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length != 0)
				{
					String QuestType = questListYaml.getString(QuestName+".FlowChart.0.Type");
					playerQuestListYaml.set("Started."+QuestName+".Flow", 0);
					playerQuestListYaml.set("Started."+QuestName+".Type", QuestType);
					if(QuestType.compareTo("Visit")==0)
						playerQuestListYaml.set("Started."+QuestName+".AreaName", questListYaml.getString(QuestName+".FlowChart.0.AreaName"));
					else if(QuestType.compareTo("Hunt")==0)
					{
						Object[] MobList = questListYaml.getConfigurationSection(QuestName+".FlowChart.0.Monster").getKeys(false).toArray();
						for(int counter = 0; counter < MobList.length; counter++)
							playerQuestListYaml.set("Started."+QuestName+".Hunt."+counter,0);
					}
					else if(QuestType.compareTo("Harvest")==0)
					{
						Object[] BlockList = questListYaml.getConfigurationSection(QuestName+".FlowChart.0.Block").getKeys(false).toArray();
						for(int counter = 0; counter < BlockList.length; counter++)
							playerQuestListYaml.set("Started."+QuestName+".Block."+counter,0);
					}
					playerQuestListYaml.saveConfig();
					player.sendMessage(ChatColor.YELLOW+"[Äù½ºÆ®] : »õ·Î¿î Äù½ºÆ®°¡ µµÂøÇß½À´Ï´Ù! " +ChatColor.GOLD+""+ChatColor.BOLD+"/Äù½ºÆ®");
					if(QuestType.compareTo("Nevigation")==0||QuestType.compareTo("Whisper")==0||
					QuestType.compareTo("BroadCast")==0||QuestType.compareTo("BlockPlace")==0||
					QuestType.compareTo("VarChange")==0||QuestType.compareTo("TelePort")==0)
						new quest.Quest_GUI().QuestRouter(player, QuestName);
				}
			}
		}
		playerQuestListYaml.saveConfig();
	  	return;
	}
	
}
