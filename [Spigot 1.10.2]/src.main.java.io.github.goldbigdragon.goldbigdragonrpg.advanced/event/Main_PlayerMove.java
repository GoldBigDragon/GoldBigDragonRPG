package event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import util.YamlLoader;




public class Main_PlayerMove implements Listener
{
	@EventHandler
	public void PlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(new corpse.Corpse_Main().DeathCapture(player,false))
			return;

		if(player.getLocation().getWorld().getName().compareTo("Dungeon")!=0)
		{
			if(main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getDungeon_Enter() != null)
			{
				new otherplugins.NoteBlockAPIMain().Stop(player);
				main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
				main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
			}
			if(new area.Area_Main().getAreaName(event.getPlayer()) != null)
			{
				if(main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea()==null)
					main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
				area.Area_Main A = new area.Area_Main();
				String Area = A.getAreaName(event.getPlayer())[0];
				if(main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea().compareTo(Area) != 0)
				{
					main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
					main.Main_ServerOption.PlayerCurrentArea.put(player, Area);
					A.AreaMonsterSpawnAdd(Area, "-1");
					new otherplugins.NoteBlockAPIMain().Stop(player);
					main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
					if(A.getAreaOption(Area, (char) 2) == true)
						main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_LastVisited(Area);
    				if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
    				{
    					if(A.getAreaOption(Area, (char) 6) == true)
    					{
    					  	YamlLoader areaYaml = new YamlLoader();
    						areaYaml.getConfig("Area/AreaList.yml");
							new otherplugins.NoteBlockAPIMain().Play(player, areaYaml.getInt(Area+".BGM"));
    					}
    				}
					if(A.getAreaOption(Area, (char) 4) == true)
					{
					  	YamlLoader questYaml = new YamlLoader();
						questYaml.getConfig("Quest/QuestList.yml");
					  	YamlLoader playerQuestYaml = new YamlLoader();
						playerQuestYaml.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						
						String[] startedQuestList = playerQuestYaml.getConfigurationSection("Started").getKeys(false).toArray(new String[0]);
						for(int count = 0; count < startedQuestList.length; count++)
						{
							if(playerQuestYaml.getString("Started."+startedQuestList[count]+".Type").compareTo("Visit")==0)
							{
								if(playerQuestYaml.getString("Started."+startedQuestList[count]+".AreaName").compareTo(Area)==0)
									{
										playerQuestYaml.set("Started."+startedQuestList[count]+".Type",questYaml.getString(startedQuestList[count]+".FlowChart."+(playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1)+".Type"));
										playerQuestYaml.set("Started."+startedQuestList[count]+".Flow",playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1);
										playerQuestYaml.removeKey("Started."+startedQuestList[count]+".AreaName");
										playerQuestYaml.saveConfig();
										quest.Quest_GUI QGUI = new quest.Quest_GUI();
										QGUI.QuestRouter(player, startedQuestList[count]);
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
				main.Main_ServerOption.PlayerCurrentArea.put(player, "null");
				main.Main_ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
				new otherplugins.NoteBlockAPIMain().Stop(player);
			}
			return;
		}
	}

}
