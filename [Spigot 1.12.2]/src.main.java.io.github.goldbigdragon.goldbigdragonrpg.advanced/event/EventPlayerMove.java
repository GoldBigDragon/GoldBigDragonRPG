package event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import util.YamlLoader;




public class EventPlayerMove implements Listener
{
	@EventHandler
	public void PlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(new corpse.CorpseMain().deathCapture(player,false))
			return;

		if(!player.getLocation().getWorld().getName().equals("Dungeon"))
		{
			if(main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getDungeon_Enter() != null)
			{
				new otherplugins.NoteBlockApiMain().Stop(player);
				main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
				main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
			}
			if(new area.AreaMain().getAreaName(event.getPlayer()) != null)
			{
				if(main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea()==null)
					main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
				area.AreaMain A = new area.AreaMain();
				String Area = A.getAreaName(event.getPlayer())[0];
				if(!main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).getETC_CurrentArea().equals(Area))
				{
					main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
					main.MainServerOption.PlayerCurrentArea.put(player, Area);
					A.AreaMonsterSpawnAdd(Area, "-1");
					new otherplugins.NoteBlockApiMain().Stop(player);
					main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea(Area);
					if(A.getAreaOption(Area, (char) 2) == true)
						main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_LastVisited(Area);
    				if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
    				{
    					if(A.getAreaOption(Area, (char) 6) == true)
    					{
    					  	YamlLoader areaYaml = new YamlLoader();
    						areaYaml.getConfig("Area/AreaList.yml");
							new otherplugins.NoteBlockApiMain().Play(player, areaYaml.getInt(Area+".BGM"));
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
							if(playerQuestYaml.getString("Started."+startedQuestList[count]+".Type").equals("Visit"))
							{
								if(playerQuestYaml.getString("Started."+startedQuestList[count]+".AreaName").equals(Area))
									{
										playerQuestYaml.set("Started."+startedQuestList[count]+".Type",questYaml.getString(startedQuestList[count]+".FlowChart."+(playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1)+".Type"));
										playerQuestYaml.set("Started."+startedQuestList[count]+".Flow",playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1);
										playerQuestYaml.removeKey("Started."+startedQuestList[count]+".AreaName");
										playerQuestYaml.saveConfig();
										quest.QuestGui QGUI = new quest.QuestGui();
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
				main.MainServerOption.PlayerCurrentArea.put(player, "null");
				main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setETC_CurrentArea("null");
				new otherplugins.NoteBlockApiMain().Stop(player);
			}
			return;
		}
	}
}
