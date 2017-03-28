package GBD_RPG.Main_Event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Main_PlayerMove
{
	public void PlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(new GBD_RPG.Corpse.Corpse_Main().DeathCapture(player,false))
			return;

		if(player.getLocation().getWorld().getName().compareTo("Dungeon")!=0)
		{
			if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getDungeon_Enter() != null)
			{
				new OtherPlugins.NoteBlockAPIMain().Stop(player);
				GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
				GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
			}
			if(new GBD_RPG.Area.Area_Main().getAreaName(event.getPlayer()) != null)
			{
				if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea()==null)
					GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
				GBD_RPG.Area.Area_Main A = new GBD_RPG.Area.Area_Main();
				String Area = A.getAreaName(event.getPlayer())[0];
				if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea().compareTo(Area) != 0)
				{
					GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
					GBD_RPG.Main_Main.Main_ServerOption.PlayerCurrentArea.put(player, Area);
					A.AreaMonsterSpawnAdd(Area, "-1");
					new OtherPlugins.NoteBlockAPIMain().Stop(player);
					GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
					if(A.getAreaOption(Area, (char) 2) == true)
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_LastVisited(Area);
    				if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
    				{
    					if(A.getAreaOption(Area, (char) 6) == true)
    					{
    					  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
    						YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
							new OtherPlugins.NoteBlockAPIMain().Play(player, AreaList.getInt(Area+".BGM"));
    					}
    				}
					if(A.getAreaOption(Area, (char) 4) == true)
					{
					  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
						YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
						YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						
						Object[] b = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
						for(short count = 0; count < b.length; count++)
						{
							String QuestName = (String) b[count];
							if(PlayerQuestList.getString("Started."+QuestName+".Type").compareTo("Visit")==0)
							{
								if(PlayerQuestList.getString("Started."+QuestName+".AreaName").compareTo(Area)==0)
									{
										PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
										PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
										PlayerQuestList.removeKey("Started."+QuestName+".AreaName");
										PlayerQuestList.saveConfig();
										GBD_RPG.Quest.Quest_GUI QGUI = new GBD_RPG.Quest.Quest_GUI();
										QGUI.QuestRouter(player, QuestName);
										//퀘스트 완료 메시지//
										break;
									}
							}
						}
						A.sendAreaTitle(player, Area);
					}
					return;
				}
			}
			else
			{
				GBD_RPG.Main_Main.Main_ServerOption.PlayerCurrentArea.put(player, "null");
				GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
				new OtherPlugins.NoteBlockAPIMain().Stop(player);
			}
			return;
		}
	}

}
