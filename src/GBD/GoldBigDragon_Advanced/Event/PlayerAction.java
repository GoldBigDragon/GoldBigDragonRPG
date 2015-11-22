package GBD.GoldBigDragon_Advanced.Event;

import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class PlayerAction
{
	public void PlayerMove(PlayerMoveEvent event)
	{
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
	    YamlManager YM;

		Player player = event.getPlayer();
		
		//플레이어 움직이지 못하게 하기
		//player.teleport(event.getPlayer().getLocation());
		
	  	if(Config_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.StatConfig().CreateNewStats(player);
		YM = Config_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		
		if(Main.PlayerCurrentArea.get(player)==null)
			Main.PlayerCurrentArea.put(player, "null");
		
		String Area;
		GBD.GoldBigDragon_Advanced.ETC.Area A = new GBD.GoldBigDragon_Advanced.ETC.Area();
		Area = A.getAreaName(event.getPlayer());
		if(Area != null)
		{
			new GBD.GoldBigDragon_Advanced.ETC.Area().AreaMonsterSpawnAdd(Area, "-1");
			if(Main.PlayerCurrentArea.get(player).equals(Area)==false)
			{
				if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
					new OtherPlugins.NoteBlockAPIMain().Stop(player);
				Main.PlayerCurrentArea.put(player, Area);
				if(A.getAreaOption(Area, (char) 2) == true)
				{
					YM.set("ETC.LastVisited",Area);
					YM.saveConfig();
				}
				if(A.getAreaOption(Area, (char) 6) == true)
				{
					YamlManager AreaList = Config_YC.getNewConfig("Area/AreaList.yml");
					if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
						new OtherPlugins.NoteBlockAPIMain().Play(player, AreaList.getInt(Area+".BGM"));
				}
				if(A.getAreaOption(Area, (char) 4) == true)
				{
					YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
					YamlManager QuestList  = GUI_YC.getNewConfig("Quest/QuestList.yml");
					YamlManager PlayerQuestList  = GUI_YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
					
					Object[] b = PlayerQuestList.getConfigurationSection("Started.").getKeys(false).toArray();
					for(int count = 0; count < b.length; count++)
					{
						String QuestName = (String) b[count];
						if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Visit"))
						{
							if(PlayerQuestList.getString("Started."+QuestName+".AreaName").equalsIgnoreCase(Area))
								{
									PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
									PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
									PlayerQuestList.removeKey("Started."+QuestName+".AreaName");
									PlayerQuestList.saveConfig();
									GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
									QGUI.QuestTypeRouter(player, QuestName);
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
			String PrevArea = Main.PlayerCurrentArea.get(player);
			Main.PlayerCurrentArea.put(player, "null");
			if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
			{
				OtherPlugins.NoteBlockAPIMain NBAPIM = new OtherPlugins.NoteBlockAPIMain();
				NBAPIM.Stop(player);
			}
			if(Main.PlayerCurrentArea.containsValue(PrevArea)==false)
			{
				for(int count = 0; count < GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.size(); count++)
				{
					if(PrevArea.equals(GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.get(count)))
						GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.MobSpawningAreaList.remove(count);
				}
			}
			return;
		}
		return;
	}

	public void PlayerChatting(PlayerChatEvent event)
	{
	    GBD.GoldBigDragon_Advanced.Config.StatConfig stat = new GBD.GoldBigDragon_Advanced.Config.StatConfig();
		GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
	    Player player = event.getPlayer();

	    if(Main.TEMP.containsKey(player)==true)
	    {
	    	TEMProuter(event);
	    	return;
	    }
	    
	    if(Main.UserData.containsKey(player)==true)
		    if(Main.UserData.get(player).getType() != null)
			    if(Main.UserData.get(player).getType().equals("Quest"))
		    	{QuestTypeChatting(event); return;}
			    else if(Main.UserData.get(player).getType().equals("WorldCreater"))
		    	{WorldCreaterTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("UseableItem")
			    		||Main.UserData.get(player).getType().equals("Upgrade")
			    		||Main.UserData.get(player).getType().equals("Item"))
	    		{ItemTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("Area"))
		    	{AreaTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("NPC"))
		    	{NPCTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("NewBie"))
		    	{NewBieTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("Skill"))
		    	{SkillTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("Job"))
		    	{JobTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("Monster"))
		    	{MonsterTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("Teleport"))
		    	{TeleportTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("Event"))
		    	{EventChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("System"))
		    	{SystemTypeChatting(event);return;}
			    else if(Main.UserData.get(player).getType().equals("Navi"))
		    	{NaviTypeChatting(event);return;}
		YamlController Main_YC = GBD.GoldBigDragon_Advanced.Main.Main.Main_YC;
	  	if(Main_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		stat.CreateNewStats(player);

	  	YamlManager Chatter = Main_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
	  	YamlManager Config = Main_YC.getNewConfig("config.yml");
	  	String Prefix = "";
	  	if(Config.contains("Server.ChatPrefix"))
	  	{
		  	Calendar C = Calendar.getInstance();
	  		Prefix = Config.getString("Server.ChatPrefix");
			Prefix=Prefix.replace("%t%",C.get(Calendar.HOUR_OF_DAY)+"시"+C.get(Calendar.MINUTE)+"분");
			if(player.getGameMode()==GameMode.SURVIVAL)
				Prefix=Prefix.replace("%gm%","서바이벌");
			else if(player.getGameMode()==GameMode.ADVENTURE)
				Prefix=Prefix.replace("%gm%","어드밴쳐");
			else if(player.getGameMode()==GameMode.CREATIVE)
				Prefix=Prefix.replace("%gm%","크리에이티브");
			else if(player.getGameMode()==GameMode.SPECTATOR)
				Prefix=Prefix.replace("%gm%","관전");
			if(Chatter.getInt("Option.ChattingType")==0)
				Prefix=Prefix.replace("%ct%","일반");
			else if(Chatter.getInt("Option.ChattingType")==1)
				Prefix=Prefix.replace("%ct%","파티");
			else if(Chatter.getInt("Option.ChattingType")==3)
				Prefix=Prefix.replace("%ct%","관리자");
			Prefix=Prefix.replace("%lv%",Chatter.getInt("Stat.Level")+"");
			Prefix=Prefix.replace("%rlv%",Chatter.getInt("Stat.RealLevel")+"");
		  	YamlManager Job = Main_YC.getNewConfig("Skill/PlayerData/" + player.getUniqueId()+".yml");
			Prefix=Prefix.replace("%job%",Job.getString("Job.Type"));
			Prefix=Prefix.replace("%player%",player.getName());
			Prefix=Prefix.replace("%message%",event.getMessage());
  			event.setCancelled(true);
		  	switch(Chatter.getInt("Option.ChattingType"))
		  	{
		  	case 0:
		  		Bukkit.broadcastMessage(Prefix);
		  		return;
		  	case 1: 
		  		if(Main.PartyJoiner.containsKey(player) == false)
		  		{
		  			player.sendMessage(ChatColor.BLUE + "[파티] : 파티에 가입되어 있지 않습니다!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  		}
		  		else
		  		{
		  			Main.Party.get(Main.PartyJoiner.get(player)).PartyBroadCastMessage(ChatColor.BLUE + "[파티] "+Prefix,null);
			  		Bukkit.getConsoleSender().sendMessage("[파티] "+Prefix);
		  		}
	  			return;
		  	case 2:
	  			event.setCancelled(true);
		  		return;
		  	case 3:
	  			event.setCancelled(true);
	  			if(player.isOp() == false)
	  			{
		  			player.sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] : 당신은 관리자가 아닙니다!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  			}
	  			else
	  			{
	  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	  		    	Player[] a = new Player[playerlist.size()];
	  		    	playerlist.toArray(a);
	  	  			for(int count = 0; count<a.length;count++)
	  	  			{
	  	  		    	if(a[count].isOnline() == true)
	  	  		    	{
	  	  		    		Player send = (Player) Bukkit.getOfflinePlayer(((Player)a[count]).getName());
	  	  		    		send.sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] "+Prefix);
	  	  		    	}	
	  	  		    }
	  		  		Bukkit.getConsoleSender().sendMessage("[관리자] "+Prefix);
	  			}
	  			return;
		  	}
	  	}
	  	else
	  	{
		  	switch(Chatter.getInt("Option.ChattingType"))
		  	{
		  	case 0:
		  		return;
		  	case 1: 
	  			event.setCancelled(true);
		  		if(Main.PartyJoiner.containsKey(player) == false)
		  		{
		  			player.sendMessage(ChatColor.BLUE + "[파티] : 파티에 가입되어 있지 않습니다!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  		}
		  		else
		  		{
		  			Main.Party.get(Main.PartyJoiner.get(player)).PartyBroadCastMessage(ChatColor.BLUE + "[파티] "+player.getName()+" : " + event.getMessage(),null);
			  		Bukkit.getConsoleSender().sendMessage("[파티] "+player.getName()+" : " + event.getMessage());
		  		}
	  			return;
		  	case 2:
	  			event.setCancelled(true);
		  		return;
		  	case 3:
	  			event.setCancelled(true);
	  			if(player.isOp() == false)
	  			{
		  			player.sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] : 당신은 관리자가 아닙니다!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  			}
	  			else
	  			{
	  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	  		    	Player[] a = new Player[playerlist.size()];
	  		    	playerlist.toArray(a);
	  	  			for(int count = 0; count<a.length;count++)
	  	  			{
	  	  		    	if(a[count].isOnline() == true)
	  	  		    	{
	  	  		    		Player send = (Player) Bukkit.getOfflinePlayer(((Player)a[count]).getName());
	  	  		    		send.sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] "+player.getName()+" : " + event.getMessage());
	  	  		    	}	
	  	  		    }
	  		  		Bukkit.getConsoleSender().sendMessage("[관리자] "+player.getName()+" : " + event.getMessage());
	  			}
	  			return;
		  	}
	  	}
	}


	private void TEMProuter(PlayerChatEvent event)
	{
		event.setCancelled(true);
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		String Message = ChatColor.stripColor(event.getMessage());
		if(Main.TEMP.get(player).equals("FA"))
		{
			if(Message.equals(player.getName()))
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[친구] : 자기 자신을 추가할 수 없습니다!");
			}
			else
			{
				Message.replace(".", "");
				if(Bukkit.getServer().getPlayer(Message) != null)
					new GBD.GoldBigDragon_Advanced.GUI.EquipGUI().SetFriends(player, Bukkit.getServer().getPlayer(Message));
				else
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[친구] : 해당 플레이어를 찾을 수 없습니다!");
				}
			}
			new GBD.GoldBigDragon_Advanced.GUI.ETCGUI().FriendsGUI(player, 0);
		}
		Main.TEMP.remove(player);
		return;
	}
	
	private void QuestTypeChatting(PlayerChatEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
	    Player player = event.getPlayer();
    	GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager QuestConfig=Config_YC.getNewConfig("Quest/QuestList.yml");

    	event.setCancelled(true);
    	String message = ChatColor.stripColor(event.getMessage());
    	switch(Main.UserData.get(player).getString((byte)1))
    	{
	    	case "Cal"://Caluclate
	    	{
				if(isIntMinMax(message, player, 1, 5))
				{
					sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			    	Main.UserData.get(player).setString((byte)1, "CVS");
			    	Main.UserData.get(player).setInt((byte)1, Integer.parseInt(message));
			    	String Example=null;
			    	switch(Integer.parseInt(message))
			    	{
			    	case 1:
			    		Example = "플레이어 변수 ＋ B";
			    		break;
			    	case 2:
			    		Example = "플레이어 변수 － B";
			    		break;
			    	case 3:
			    		Example = "플레이어 변수 × B";
			    		break;
			    	case 4:
			    		Example = "플레이어 변수 ÷ B";
			    		break;
			    	case 5:
			    		Example = "플레이어 변수 ％ B";
			    		break;
			    	}
					player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" 에서 "+ChatColor.YELLOW+"B"+ChatColor.GREEN+" 값에 들어갈 수는 몇 인가요?");
			    	if(Integer.parseInt(message) <= 2)
						player.sendMessage(ChatColor.GRAY + "(최소 -1000 ~ 최대 20000)");
			    	else
						player.sendMessage(ChatColor.GRAY + "(최소 1 ~ 최대 100)");
					player.sendMessage(ChatColor.GRAY + "(계산 결과 -2000 이하거나 40000 이상일 경우 각각 -2000과 40000으로 고정)");
					player.sendMessage(ChatColor.GRAY + "(저장 타입이  Integer이므로, 결과 값이 너무 크거나 작으면 이상한 값이 나올수도 있음)");
				}
	    	}
	    	return;
	    	case "CVS"://Calculate Value Set
	    	{
	    		if(Main.UserData.get(player).getInt((byte)1)<=2)
	    		{
	    			if(isIntMinMax(message, player, -1000, 20000))
					{
		    			int size = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false).size();
						sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
						QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Type", "Cal");
		        		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Comparison", Main.UserData.get(player).getInt((byte)1));
			    		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	    	QuestConfig.saveConfig();
		    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 계산 공식 설정이 완료되었습니다!");
		    			QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
		    	    	Main.UserData.get(player).clearAll();
					}
	    		}
	    		else
	    		{
	    			if(isIntMinMax(message, player, 1, 100))
					{
		    			int size = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false).size();
						sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
						QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Type", "Cal");
		        		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Comparison", Main.UserData.get(player).getInt((byte)1));
			    		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	    	QuestConfig.saveConfig();
		    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 계산 공식 설정이 완료되었습니다!");
		    			QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
		    	    	Main.UserData.get(player).clearAll();
					}
	    		}
	    	}
	    	return;
    	case "IFMVS"://IF Max Value Set
    	{
			if(isIntMinMax(message, player, Main.UserData.get(player).getInt((byte)2), 40000))
			{
    			int size = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false).size();
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Type", "IF");
        		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Comparison", Main.UserData.get(player).getInt((byte)1));
	    		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Min", Main.UserData.get(player).getInt((byte)2));
	    		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Max", Integer.parseInt(message));
    	    	QuestConfig.saveConfig();
    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : IF문 설정이 완료되었습니다!");
    			QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
    	    	Main.UserData.get(player).clearAll();
			}
    	}
    	return;
    	case "IFVS"://IF Value Set
    	{
			if(isIntMinMax(message, player, -2000, 40000))
			{
    			int size = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false).size();
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
		    	if(Main.UserData.get(player).getInt((byte)1)!=7)
		    	{
	        		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Type", "IF");
	        		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Comparison", Main.UserData.get(player).getInt((byte)1));
		    		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
		    	}
		    	else
		    	{
		    		Main.UserData.get(player).setInt((byte)2, Integer.parseInt(message));
		    		QuestConfig.saveConfig();
			    	Main.UserData.get(player).setString((byte)1, "IFMVS");
					player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+ChatColor.YELLOW+Integer.parseInt(message)+" <= 플레이어 변수 <= B"+ChatColor.GREEN+" 에서 "+ChatColor.YELLOW+"C"+ChatColor.GREEN+" 값에 들어갈 수는 몇 인가요?");
					player.sendMessage(ChatColor.GRAY + "(최소 "+Integer.parseInt(message)+" ~ 최대 40000)");
		    		return;
		    	}
    	    	QuestConfig.saveConfig();
    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : IF문 설정이 완료되었습니다!");
    			QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
    	    	Main.UserData.get(player).clearAll();
			}
    	}
    	return;
    	case "IFTS"://IF Type Select
    	{
			if(isIntMinMax(message, player, 1, 7))
			{
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
		    	Main.UserData.get(player).setString((byte)1, "IFVS");
		    	Main.UserData.get(player).setInt((byte)1, Integer.parseInt(message));
		    	String Example=null;
		    	switch(Integer.parseInt(message))
		    	{
		    	case 1:
		    		Example = "플레이어 변수 == B";
		    		break;
		    	case 2:
		    		Example = "플레이어 변수 != B";
		    		break;
		    	case 3:
		    		Example = "플레이어 변수 > B";
		    		break;
		    	case 4:
		    		Example = "플레이어 변수 < B";
		    		break;
		    	case 5:
		    		Example = "플레이어 변수 >= B";
		    		break;
		    	case 6:
		    		Example = "플레이어 변수 <= B";
		    		break;
		    	case 7:
		    		Example = "C <= 플레이어 변수 <= B";
					player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" 에서 "+ChatColor.YELLOW+"C"+ChatColor.GREEN+" 값에 들어갈 수는 몇 인가요?");
					player.sendMessage(ChatColor.GRAY + "(최소 -2000 ~ 최대 40000)");
		    		return;
		    	}
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+ChatColor.YELLOW+Example+ChatColor.GREEN+" 에서 "+ChatColor.YELLOW+"B"+ChatColor.GREEN+" 값에 들어갈 수는 몇 인가요?");
				player.sendMessage(ChatColor.GRAY + "(최소 -2000 ~ 최대 40000)");
			}
    	}
    	return;
	    	case "CV"://ChangeVariable
	    	{
				if(isIntMinMax(message, player, -1000, 30000))
				{
					sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
	    			int size = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false).size();
	        		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Type", "VarChange");
	        		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Value", Integer.parseInt(message));
	    	    	QuestConfig.saveConfig();
	    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 변수 변경 설정이 완료되었습니다!");
	    			QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
	    	    	Main.UserData.get(player).clearAll();
				}
	    	}
	    	return;
    	case "SCV"://SetChoiceVariable
    	{
			if(isIntMinMax(message, player, -1000, 30000))
			{
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				Main.UserData.get(player).setInt((byte)(Main.UserData.get(player).getInt((byte)1)+2),Integer.parseInt(message));
				Main.UserData.get(player).setInt((byte)1,Main.UserData.get(player).getInt((byte)1)+1);
	    		if(Main.UserData.get(player).getInt((byte)0)==Main.UserData.get(player).getInt((byte)1))
	    		{
	    			int size = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false).size();
	        		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Type", "Choice");
	        		for(int count = 0; count <Main.UserData.get(player).getInt((byte)0);count++)
	        		{
	        			QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Choice."+count+".Lore", Main.UserData.get(player).getString((byte)(count+4)));
	        			QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Choice."+count+".Var", Main.UserData.get(player).getInt((byte)(count+2)));
	        		}
	    	    	QuestConfig.saveConfig();
	    			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 선택지가 성공적으로 등록되었습니다!");
	    			QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
	    	    	Main.UserData.get(player).clearAll();
	    		}
	    		else
	    		{
			    	Main.UserData.get(player).setString((byte)1, "SCL");
			    	player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+(Main.UserData.get(player).getInt((byte)1)+1)+"번째 선택에 적힐 말을 입력 하세요!");
					player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
					player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
					player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
					ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
							ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
					ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
							ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
	    		}
			}
			
    	}
    	return;
    	case "SCL"://SetChoiceLore
    	{
			sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			Main.UserData.get(player).setString((byte)(Main.UserData.get(player).getInt((byte)1)+4), event.getMessage());
	    	Main.UserData.get(player).setString((byte)1, "SCV");
			player.sendMessage(ChatColor.GREEN + "[퀘스트] : "+(Main.UserData.get(player).getInt((byte)1)+1)+"번 선택지를 고를 경우, 플레이어 변수는 몇으로 변환시킬까요?");
			player.sendMessage(ChatColor.GRAY + "(최소 -1000 ~ 최대 30000)");
    	}
    	return;
    	case "CS"://ChoiceSize
			if(isIntMinMax(message, player, 1, 4))
			{
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				Main.UserData.get(player).setInt((byte)0, Integer.parseInt(message));
				Main.UserData.get(player).setInt((byte)1, 0);
		    	Main.UserData.get(player).setString((byte)1, "SCL");
		    	player.sendMessage(ChatColor.GREEN + "[퀘스트] : 1번째 선택에 적힐 말을 입력 하세요!");
				player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			}
	    	return;
    	case "Whisper":
    	case "BroadCast":
    	case "PScript":
	    	{
	    		int size = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false).size();
	    		QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Type", Main.UserData.get(player).getString((byte)1));
		    	QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+size+".Message", event.getMessage());
		    	QuestConfig.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[퀘스트] : 대사가 성공적으로 등록되었습니다!");
				QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
		    	Main.UserData.get(player).clearAll();
	    	}
	    	return;
    	case "BPID"://BlockPlaceID
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				GBD.GoldBigDragon_Advanced.Event.Interact I = new GBD.GoldBigDragon_Advanced.Event.Interact();
				if(I.SetItemDefaultName(Integer.parseInt(message),(byte)0).equalsIgnoreCase("지정되지 않은 아이템"))
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				String QuestName = Main.UserData.get(player).getString((byte)2);
				int size = Main.UserData.get(player).getInt((byte)1);
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				QuestConfig.set(QuestName+".FlowChart."+size+".ID", Integer.parseInt(message));
				QuestConfig.saveConfig();
		    	Main.UserData.get(player).setString((byte)1, "BPDATA");
		    	player.sendMessage(ChatColor.GREEN + "[퀘스트] : 설치 될 블록 DATA를 입력 해 주세요!");
			}
	    	return;
    	case "BPDATA"://BlockPlaceDATA
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				String QuestName = Main.UserData.get(player).getString((byte)2);
				int size = Main.UserData.get(player).getInt((byte)1);
				sound.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				QuestConfig.set(QuestName+".FlowChart."+size+".DATA", Integer.parseInt(message));
				QuestConfig.saveConfig();
		    	Main.UserData.get(player).setString((byte)1, "BPDATA");
		    	Main.UserData.get(player).clearAll();
		    	QGUI.FixQuestGUI(player, 0, QuestName);
			}
	    	return;
    	case "Script":
	    	Main.UserData.get(player).setString((byte)3,ChatColor.WHITE + event.getMessage());
			player.sendMessage(ChatColor.GREEN+"[퀘스트] : 해당 대사를 말할 NPC를 우클릭 하세요.");
	    	return;
    	case "Visit":
			YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
			YamlManager AreaList = Event_YC.getNewConfig("Area/AreaList.yml");
			Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();

			if(arealist.length <= 0)
			{
				GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 생성된 영역이 없습니다!");
				Main.UserData.get(player).clearAll();
				return;
			}
			for(int count =0; count <arealist.length;count++)
			{
				if(event.getMessage().equalsIgnoreCase((String) arealist[count]) == true)
				{
					player.sendMessage(ChatColor.GREEN+"[퀘스트] : "+ChatColor.YELLOW + arealist[count] + ChatColor.GREEN+" 지역을 방문하도록 등록 되었습니다!");

					Set<String> b4 = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false);
					
			    	QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b4.size()+".Type", "Visit");
			    	QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+b4.size()+".AreaName", (String) arealist[count]);
			    	QuestConfig.saveConfig();

					QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
					Main.UserData.get(player).clearAll();
					return;
				}
				player.sendMessage(ChatColor.GREEN +"  "+ arealist[count].toString());
			}
			player.sendMessage(ChatColor.GREEN + "┌────────영역 목록────────┐");
			for(int count =0; count <arealist.length;count++)
			{
				player.sendMessage(ChatColor.GREEN +"  "+ arealist[count].toString());
			}
			player.sendMessage(ChatColor.GREEN + "└────────영역 목록────────┘");
			player.sendMessage(ChatColor.DARK_AQUA + "[퀘스트] : 방문해야 할 영역 이름을 적어 주세요!");
	    	return;
    	case "Hunt":
			if(isIntMinMax(event.getMessage(), player, 1, Integer.MAX_VALUE))
			{
				int Flownumber=0;
				int Monsternumber =0;
				Set<String> b = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false);
				if(Main.UserData.get(player).getInt((byte)3) != -1)
					Flownumber = b.size()-1;
				else
					Flownumber = b.size();
				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Type","Hunt");
				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Monster."+(-1)+".MonsterName", null);
				QuestConfig.removeKey(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Monster."+(-1));
				QuestConfig.saveConfig();
				Set<String> c = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Monster").getKeys(false);
				if(Main.UserData.get(player).getInt((byte)2) != -1)
					Monsternumber = Main.UserData.get(player).getInt((byte)2);
				else
					Monsternumber = c.size();
				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Monster."+Monsternumber+".MonsterName", Main.UserData.get(player).getString((byte)3));
				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Monster."+Monsternumber+".Amount", Integer.parseInt(event.getMessage()));
				QuestConfig.saveConfig();
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : " + ChatColor.YELLOW + QGUI.SkullType(Main.UserData.get(player).getString((byte)3)) + ChatColor.GREEN + " (을)를 " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" 마리 사냥하도록 설정되었습니다!");

				if(Main.UserData.get(player).getInt((byte)2) < 17)
					QGUI.KeepGoing(player, Main.UserData.get(player).getString((byte)2), Flownumber,Monsternumber,false);
				else
					QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
				Main.UserData.get(player).clearAll();
			}
			return;
    	case "Harvest":
	    	if(Main.UserData.get(player).getString((byte)3)!=null)
	    	{
	    		if(ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("x") ||ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("X") ||
	    				ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("o") ||ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("O"))
	    		{
		    		if(ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("x") ||ChatColor.stripColor(event.getMessage()).equalsIgnoreCase("X"))
		    			Main.UserData.get(player).setBoolean((byte)1, false);
		    		else
		    			Main.UserData.get(player).setBoolean((byte)1, true);
					Main.UserData.get(player).setString((byte)3,null);
			    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 블록을 얼마나 채집해야 할지 설정하세요! ("+ChatColor.YELLOW + "1"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"개)");
	    		}
	    		else
	    		{
    				player.sendMessage(ChatColor.RED + "[SYSTEM] : x혹은 o를 입력 해  주세요.");
      				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	    		}
  				return;
	    	}
	    	else
	    	{
				if(isIntMinMax(event.getMessage(), player, 1, Integer.MAX_VALUE))
				{
    				int Flownumber=0;
    				int BlockNumber =0;
    				Set<String> b = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart").getKeys(false);
    				if(Main.UserData.get(player).getInt((byte)3) != -1)
    					Flownumber = b.size()-1;
    				else
    					Flownumber = b.size();
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Type","Harvest");
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Block."+(-1)+".BlockID", null);
    				QuestConfig.removeKey(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Block."+(-1));
    				QuestConfig.saveConfig();
    				Set<String> c = QuestConfig.getConfigurationSection(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Block").getKeys(false);
    				if(Main.UserData.get(player).getInt((byte)4) != -1)
    					BlockNumber = Main.UserData.get(player).getInt((byte)4);
    				else
    					BlockNumber = 0;
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".BlockID", Main.UserData.get(player).getInt((byte)1));
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".BlockData", Main.UserData.get(player).getInt((byte)2));
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".Amount", Integer.parseInt(event.getMessage()));
    				QuestConfig.set(Main.UserData.get(player).getString((byte)2)+".FlowChart."+Flownumber+".Block."+BlockNumber+".DataEquals", Main.UserData.get(player).getBoolean((byte)1));
    				QuestConfig.saveConfig();
    				
    				if(Main.UserData.get(player).getBoolean((byte)1) == false)
    					player.sendMessage(ChatColor.GREEN + "[퀘스트] : 아이템 ID가 " + ChatColor.YELLOW + Main.UserData.get(player).getInt((byte)1) +ChatColor.GREEN + " 인 모든 블록을 " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" 개 채집하도록 설정되었습니다!");
    				else
    					player.sendMessage(ChatColor.GREEN + "[퀘스트] : 아이템 코드 " + ChatColor.YELLOW + Main.UserData.get(player).getInt((byte)1) +  ":"+ Main.UserData.get(player).getInt((byte)2) + ChatColor.GREEN + " 인 블록을 " + ChatColor.YELLOW + Integer.parseInt(event.getMessage())+ ChatColor.GREEN +" 개 채집하도록 설정되었습니다!");

    				if(Main.UserData.get(player).getInt((byte)2) < 17)
    					QGUI.KeepGoing(player, Main.UserData.get(player).getString((byte)2), Flownumber,BlockNumber,true);
    				else
    					QGUI.FixQuestGUI(player, 0, Main.UserData.get(player).getString((byte)2));
    				Main.UserData.get(player).clearAll();
				}
    			return;
	    	}
    	}
    	
    	if(Main.UserData.get(player).getString((byte)2)!=null)
    	{
    		if(Main.UserData.get(player).getString((byte)1).contains("District") == true)
    		{
				if(isIntMinMax(event.getMessage(), player, 0, Integer.MAX_VALUE))
				{
    				String QuestName = Main.UserData.get(player).getString((byte)2);
    				int value = Integer.parseInt(event.getMessage());
    				YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
    				YamlManager QuestList  = GUI_YC.getNewConfig("Quest/QuestList.yml");
    				switch(Main.UserData.get(player).getString((byte)1))
    				{
    				case "Level District":
    					QuestList.set(QuestName+".Need.LV", value);
    					break;
    				case "Love District":
    					QuestList.set(QuestName+".Need.Love", value);
    					break;
    				case "STR District":
    					QuestList.set(QuestName+".Need.STR", value);
    					break;
    				case "DEX District":
    					QuestList.set(QuestName+".Need.DEX", value);
    					break;
    				case "INT District":
    					QuestList.set(QuestName+".Need.INT", value);
    					break;
    				case "WILL District":
    					QuestList.set(QuestName+".Need.WILL", value);
    					break;
    				case "LUK District":
    					QuestList.set(QuestName+".Need.LUK", value);
    					break;
    				case "Accept District":
    					QuestList.set(QuestName+".Server.Limit", value);
    					break;
    				}
    				QuestList.saveConfig();
    				Main.UserData.get(player).clearAll();
    				QGUI.QuestOptionGUI(player, QuestName);
				}
    			return;
    		}
    	}
    	
		if(Main.UserData.get(player).getString((byte)4)!=null)
		{
			if(isIntMinMax(event.getMessage(), player, 0, Integer.MAX_VALUE))
			{
    			switch(Main.UserData.get(player).getString((byte)4))
    			{
	    			case "M":
	    		    	event.setCancelled(true);
	    				Main.UserData.get(player).setString((byte)4,null);
	    				Main.UserData.get(player).setInt((byte)1, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.GetPresentGUI(player, Main.UserData.get(player).getString((byte)3));
	    				break;
	    			case "E":
	    		    	event.setCancelled(true);
	    				Main.UserData.get(player).setString((byte)4,null);
	    				Main.UserData.get(player).setInt((byte)2, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.GetPresentGUI(player, Main.UserData.get(player).getString((byte)3));
	    				break;
	    			case "L":
	    		    	event.setCancelled(true);
	    				Main.UserData.get(player).setString((byte)4,null);
	    				Main.UserData.get(player).setInt((byte)3, Integer.parseInt(ChatColor.stripColor(event.getMessage())));
			    		QGUI.GetPresentGUI(player, Main.UserData.get(player).getString((byte)3));
	    				break;
    				default :
    					break;
    			}
			}
			return;
    	}
		return;
	}
	
	private void WorldCreaterTypeChatting(PlayerChatEvent event)
	{
		event.setCancelled(true);
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		String Message = ChatColor.stripColor(event.getMessage());
		s.SP(player, Sound.ANVIL_USE,1.0F, 0.8F);
		player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[월드 생성] : 월드 생성 중...");
		WorldType TYPE = WorldType.FLAT;
		
		switch(Main.UserData.get(player).getString((byte)3))
		{
		case "NORMAL":
			TYPE = WorldType.NORMAL;
			break;
		case "FLAT":
			TYPE = WorldType.FLAT;
			break;
		case "LARGE_BIOMES":
			TYPE = WorldType.LARGE_BIOMES;
			break;
		}
		Message = Message.replace(" ", "_");
		WorldCreator world = new WorldCreator(Message);
		world.type(TYPE);
		world.generateStructures();
		Bukkit.createWorld(world);
		Main.UserData.get(player).clearAll();
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager WorldConfig =GUI_YC.getNewConfig("WorldList.yml");
		WorldConfig.createSection(Message);
		WorldConfig.saveConfig();
		Object[] worldname = WorldConfig.getKeys().toArray();
		for(int count = 0; count < WorldConfig.getKeys().size();count++)
		{
			if(Bukkit.getWorld(worldname[count].toString()) == null)
			{
				WorldCreator.name(worldname[count].toString()).createWorld();
			}
		}
    	s.SP(player, Sound.WOLF_BARK,1.0F, 0.8F);
		player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[월드 생성] : 월드 생성 성공!");
		return;
	}
	
	private void ItemTypeChatting(PlayerChatEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.GUI.ItemGUI IGUI = new GBD.GoldBigDragon_Advanced.GUI.ItemGUI();
		GBD.GoldBigDragon_Advanced.GUI.UpGradeGUI UpGUI = new GBD.GoldBigDragon_Advanced.GUI.UpGradeGUI();
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager RecipeList = GUI_YC.getNewConfig("Item/Upgrade.yml");
		YamlManager ItemList = GUI_YC.getNewConfig("Item/ItemList.yml");
		if(Main.UserData.get(player).getType()=="UseableItem"||Main.UserData.get(player).getType().equals("UseableItem"))
			ItemList = GUI_YC.getNewConfig("Item/Consume.yml");
		event.setCancelled(true);
		int number = 0;
		String Message = ChatColor.stripColor(event.getMessage());
		if(Main.UserData.get(player).getInt((byte)3)!=-1)
			number = Main.UserData.get(player).getInt((byte)3);
		String Type = Main.UserData.get(player).getString((byte)1);
		switch(Main.UserData.get(player).getString((byte)1))
		{
			case "DisplayName":
			case "Lore":
				ItemList.set(number+"."+Type,event.getMessage());
				break;
			case "ID":
				if(isIntMinMax(Message, player, 1, Integer.MAX_VALUE))
				{
					GBD.GoldBigDragon_Advanced.Event.Interact I = new GBD.GoldBigDragon_Advanced.Event.Interact();
					if(I.SetItemDefaultName(Integer.parseInt(Message),(byte)0).equalsIgnoreCase("지정되지 않은 아이템"))
					{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
		  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  				return;
					}
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
				}
				break;
			case "Saturation":
			case "SkillPoint":
			case "StatPoint":
			case "Data":
			case "DEF":
			case "Protect":
			case "MaDEF":
			case "MaProtect":
			case "MaxUpgrade":
			case "MaxDamage":
			case "MaxMaDamage":
			case "Durability":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
				ItemList.set(number+"."+Type,Integer.parseInt(Message));
			break;
			case "MinDamage":
				if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "MaxDamage");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최대 대미지를 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				}
				return;
			case "MinMaDamage":
				if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "MaxMaDamage");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최대 마법 대미지를 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				}
				return;
			case "MaxDurability":
				if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "Durability");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 현재 내구도를 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+ItemList.getInt(number+".MaxDurability")+")");
				}
				return;
			case "HP":
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					if(Main.UserData.get(player).getInt((byte)4) != -1)
					{
						if(Main.UserData.get(player).getInt((byte)4) == -8)
						{
							GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI UGUI = new GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI();
							UGUI.NewUseableItemGUI(player, number);
							Main.UserData.get(player).clearAll();
							return;
						}
						else
						{
							Main.UserData.get(player).setType(Main.UserData.get(player).getType());
							Main.UserData.get(player).setString((byte)1, "MP");
							player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보너스 마나를 입력해 주세요!");
							player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
							return;
						}
					}
					else
					{
						Main.UserData.get(player).setType(Main.UserData.get(player).getType());
						Main.UserData.get(player).setString((byte)1, "MP");
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보너스 마나를 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
					}
				}
				return;
			case "MP":
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					if(Main.UserData.get(player).getInt((byte)4) != -1)
					{
						if(Main.UserData.get(player).getInt((byte)4) == -8)
						{
							GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI UGUI = new GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI();
							UGUI.NewUseableItemGUI(player, number);
							Main.UserData.get(player).clearAll();
							return;
						}
						else
						{
							Main.UserData.get(player).setType(Main.UserData.get(player).getType());
							Main.UserData.get(player).setString((byte)1, "STR");
							player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보너스 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.STR+"을 입력해 주세요!");
							player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
							return;
						}
					}
					else
					{
						Main.UserData.get(player).setType(Main.UserData.get(player).getType());
						Main.UserData.get(player).setString((byte)1, "STR");
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보너스 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.STR+"을 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
						return;
					}
				}
				return;
			case "STR":
				if(isIntMinMax(Message, player, -127, 127))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "DEX");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보너스 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.DEX+"를 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				}
				return;
			case "DEX":
				if(isIntMinMax(Message, player, -127, 127))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "INT");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보너스 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.INT+"을 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				}
				return;
			case "INT":
				if(isIntMinMax(Message, player, -127, 127))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "WILL");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보너스 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.WILL+"를 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				}
				return;
			case "WILL":
				if(isIntMinMax(Message, player, -127, 127))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "LUK");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보너스 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.LUK+"을 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				}
				return;
			case "LUK":
				if(isIntMinMax(Message, player, -127, 127))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "Balance");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 밸런스를 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				}
				return;
			case "Balance":
				if(isIntMinMax(Message, player, -127, 127))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "Critical");
					player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 크리티컬을 입력해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				}
				return;
			case "Critical":
				if(isIntMinMax(Message, player, -127, 127))
				{
					ItemList.set(number+"."+Type,Integer.parseInt(Message));
					ItemList.saveConfig();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
					if(Main.UserData.get(player).getType()=="UseableItem"||Main.UserData.get(player).getType().equals("UseableItem"))
					{
						GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI UGUI = new GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI();
						UGUI.NewUseableItemGUI(player, number);
					}
					else
						IGUI.NewItemGUI(player, number);
					Main.UserData.get(player).clearAll();
				}
				return;
			case "NUR"://NewUpgradeRecipe
				Message = Message.replace(".", "");
				RecipeList.set(Message+".Lore", ChatColor.WHITE+"무기의 날을 다듬는 개조식이다.%enter%"+ChatColor.WHITE+"날을 다듬은 무기는 내구성이%enter%"+ChatColor.WHITE+"떨어지지만, 위협적이다.");
				RecipeList.set(Message+".Only",ChatColor.RED+ "[근접 무기]");
				RecipeList.set(Message+".MaxDurability", -50);
				RecipeList.set(Message+".MinDamage", 1);
				RecipeList.set(Message+".MaxDamage", 8);
				RecipeList.set(Message+".MinMaDamage", 0);
				RecipeList.set(Message+".MaxMaDamage", 0);
				RecipeList.set(Message+".DEF", 0);
				RecipeList.set(Message+".Protect", 0);
				RecipeList.set(Message+".MaDEF", 0);
				RecipeList.set(Message+".MaProtect", 0);
				RecipeList.set(Message+".Critical", 2);
				RecipeList.set(Message+".Balance", 0);
				RecipeList.set(Message+".UpgradeAbleLevel", 0);
				RecipeList.set(Message+".DecreaseProficiency",30);
				RecipeList.saveConfig();
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				UpGUI.UpgradeRecipeSettingGUI(player, Message);
				Main.UserData.get(player).clearAll();
				return;
			case "UMinD"://UpgradeMinDamage
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".MinDamage", Integer.parseInt(Message));
					RecipeList.saveConfig();
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "UMaxD");
					player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 최대 공격력 수치를 입력하세요!");
					player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				return;
			case "UMaxD"://UpgradeMaxDamage
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".MaxDamage", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UMMinD"://UpgradeMagicMinDamage
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".MinMaDamage", Integer.parseInt(Message));
					RecipeList.saveConfig();
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					Main.UserData.get(player).setType(Main.UserData.get(player).getType());
					Main.UserData.get(player).setString((byte)1, "UMMaxD");
					player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 최대 마법 공격력 수치를 입력하세요!");
					player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				return;
			case "UMMaxD"://UpgradeMagicMaxDamage
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".MaxMaDamage", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UB"://UpgradeBalance
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".Balance", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UDEF"://UpgradeDefense
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".DEF", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UP"://UpgradeProtect
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".Protect", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UMDEF"://UpgradeMagicDefense
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".MaDEF", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UMP"://UpgradeMagicProtect
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".MaProtect", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UC"://UpgradeCritical
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".Critical", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UMD"://UpgradeMaxDurability
				if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".MaxDurability", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UUL"://UpgradeUpgradeLevel
				if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".UpgradeAbleLevel", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "UDP"://UpgradeDecreaseProficiency
				if(isIntMinMax(Message, player, 0, 100))
				{
					RecipeList.set(Main.UserData.get(player).getString((byte)6)+".DecreaseProficiency", Integer.parseInt(Message));
					RecipeList.saveConfig();
					UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
					Main.UserData.get(player).clearAll();
					s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				}
				return;
			case "ULC"://Upgrade Lore Change
				RecipeList.set(Main.UserData.get(player).getString((byte)6)+".Lore", event.getMessage());
				RecipeList.saveConfig();
				UpGUI.UpgradeRecipeSettingGUI(player, Main.UserData.get(player).getString((byte)6));
				Main.UserData.get(player).clearAll();
				s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				return;
		}
		ItemList.saveConfig();
		s.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
		if(Main.UserData.get(player).getType() == "UseableItem"
		||Main.UserData.get(player).getType().equals("UseableItem"))
			new GBD.GoldBigDragon_Advanced.GUI.UseableItemGUI().NewUseableItemGUI(player, number);
		else
			IGUI.NewItemGUI(player, number);
		Main.UserData.get(player).clearAll();
		return;
	}

	private void AreaTypeChatting(PlayerChatEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Player player = event.getPlayer();
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager AreaConfig =GUI_YC.getNewConfig("Area/AreaList.yml");
		event.setCancelled(true);
		GBD.GoldBigDragon_Advanced.GUI.AreaGUI AGUI = new GBD.GoldBigDragon_Advanced.GUI.AreaGUI();
		String Message = ChatColor.stripColor(event.getMessage());
		switch(Main.UserData.get(player).getString((byte)2))
		{
			case "ARR"://AreaRegenBlock
				if(isIntMinMax(Message, player, 1, 3600))
				{
					AreaConfig.set(Main.UserData.get(player).getString((byte)3)+".RegenBlock", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					AGUI.AreaGUI_Main(player, Main.UserData.get(player).getString((byte)3));
	    			Main.UserData.get(player).clearAll();
				}
				return;
			case "AMSC"://AreaMonsterSpawnCount
				if(isIntMinMax(Message, player, 1, 100))
				{
					AreaConfig.set(Main.UserData.get(player).getString((byte)3)+".MonsterSpawnRule."+Main.UserData.get(player).getString((byte)1)+".count", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					Main.UserData.get(player).setString((byte)2, "AMSMC");
					player.sendMessage(ChatColor.GREEN+"[영역] : 반경 20블록 이내 엔티티가 몇 마리 미만일 동안 스폰 할까요?");
					player.sendMessage(ChatColor.YELLOW+"(최소 1마리 ~ 최대 300마리)");
				}
				return;
			case "AMSMC"://AreaMonsterSpawnMaximumCount
				if(isIntMinMax(Message, player, 1, 300))
				{
					AreaConfig.set(Main.UserData.get(player).getString((byte)3)+".MonsterSpawnRule."+Main.UserData.get(player).getString((byte)1)+".max", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					Main.UserData.get(player).setString((byte)2, "AMST");
					player.sendMessage(ChatColor.GREEN+"[영역] : 몇 초마다 스폰되게 할까요?");
					player.sendMessage(ChatColor.YELLOW+"(최소 1초 ~ 최대 7200초(2시간))");
				}
				return;
			case "AMST"://AreaMonsterSpawnTimer
				if(isIntMinMax(Message, player, 1, 7200))
				{
					AreaConfig.set(Main.UserData.get(player).getString((byte)3)+".MonsterSpawnRule."+Main.UserData.get(player).getString((byte)1)+".timer", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
	    			/*
					Main.UserData.get(player).setString((byte)2, "AMSR");
					player.sendMessage(ChatColor.GREEN+"[영역] : 영역 내에 '입장'한 플레이어가 반경 몇 블록 이내에 들어 올 때 부터 스폰을 시작 할까요?");
					player.sendMessage(ChatColor.YELLOW+"(최소 1블록 ~ 최대 1000블록)");
					*/
	    			Main.UserData.get(player).setString((byte)2, "AMSM");
					player.sendMessage(ChatColor.GREEN+"[영역] : 특별히 스폰 하고 싶은 몬스터가 있나요?");
					player.sendMessage(ChatColor.YELLOW+"(O 혹은 X로 대답하세요!)");
				}
				return;
				/*
			case "AMSR"://AreaMonsterSpawnRange
				if(isIntMinMax(Message, player, 1, 1000))
				{
					AreaConfig.set(Main.UserData.get(player).getString((byte)3)+".MonsterSpawnRule."+Main.UserData.get(player).getString((byte)1)+".range", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					Main.UserData.get(player).setString((byte)2, "AMSM");
					player.sendMessage(ChatColor.GREEN+"[영역] : 특별히 스폰 하고 싶은 몬스터가 있나요?");
					player.sendMessage(ChatColor.YELLOW+"(O 혹은 X로 대답하세요!)");
				}
				return;
				*/
			case "AMSM"://AreaMonsterSpawnMonster
				int answer = askOX(Message, player);
				if(answer!=-1)
				{
					if(answer==0)
					{
		    			s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.0F);
		    			AGUI.AreaMonsterSpawnSettingGUI(player, 0, Main.UserData.get(player).getString((byte)3));
		    			String AreaName =Main.UserData.get(player).getString((byte)3);
		    			new GBD.GoldBigDragon_Advanced.ETC.Area().AreaMonsterSpawnAdd(AreaName, Main.UserData.get(player).getString((byte)1));
					}
					else
					{
						s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.7F);
						AGUI.AreaSpawnSpecialMonsterListGUI(player, 0, Main.UserData.get(player).getString((byte)3),Main.UserData.get(player).getString((byte)1));
					}
	    			Main.UserData.get(player).clearAll();
				}
				return;
			case "Priority"://영역 우선순위 설정
				if(isIntMinMax(Message, player, 0, 100))
				{
	    			AreaConfig.set(Main.UserData.get(player).getString((byte)3)+".Priority", Integer.parseInt(Message));
	    			AreaConfig.saveConfig();
	    			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
	    			AGUI.AreaGUI_Main(player, Main.UserData.get(player).getString((byte)3));
	    			Main.UserData.get(player).clearAll();
				}
				return;
		}
		return;
	}

	private void NPCTypeChatting(PlayerChatEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Player player = event.getPlayer();

		String NPCuuid = Main.UserData.get(player).getString((byte)3);
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		
	  	if(GUI_YC.isExit("NPC/NPCData/"+ NPCuuid +".yml") == false)
	  	{
	  		GBD.GoldBigDragon_Advanced.Config.NPCconfig NPCC = new GBD.GoldBigDragon_Advanced.Config.NPCconfig();
	  		NPCC.NPCNPCconfig(NPCuuid);
	  	}
		YamlManager NPCscript = GUI_YC.getNewConfig("NPC/NPCData/"+ NPCuuid +".yml");
		GBD.GoldBigDragon_Advanced.GUI.NPC_GUI NPGUI = new GBD.GoldBigDragon_Advanced.GUI.NPC_GUI();
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		switch(Main.UserData.get(player).getString((byte)4))
		{
		case "NUC"://NPC'sUpgradeCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				YamlManager NPCConfig =GUI_YC.getNewConfig("NPC/NPCData/"+Main.PlayerClickedNPCuuid.get(player)+".yml");
				NPCConfig.set("Job.UpgradeRecipe."+Main.UserData.get(player).getString((byte)6),  Integer.parseInt(Message));
				NPCConfig.saveConfig();
				GBD.GoldBigDragon_Advanced.GUI.NPC_GUI NGUI = new GBD.GoldBigDragon_Advanced.GUI.NPC_GUI();
				NGUI.UpgraderGUI(player, 0, Main.UserData.get(player).getString((byte)8));
				sound.SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				Main.UserData.get(player).clearAll();
			}
			return;
		case "NPC_TNL"://NPC_TalkNeedLove
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				switch(Main.UserData.get(player).getString((byte)5))
				{
				case "NT":
					NPCscript.set("NatureTalk."+Main.UserData.get(player).getString((byte)6)+".love", Integer.parseInt(Message));
					break;
				case "NN":
					NPCscript.set("NearByNEWS."+Main.UserData.get(player).getString((byte)6)+".love", Integer.parseInt(Message));
					break;
				case "AS":
					NPCscript.set("AboutSkills."+Main.UserData.get(player).getString((byte)6)+".love", Integer.parseInt(Message));
					break;
				}
				NPCscript.saveConfig();
				NPGUI.TalkSettingGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getString((byte)5), Integer.parseInt(Main.UserData.get(player).getString((byte)6)));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "NPC_TS"://NPC_TalkScript
			switch(Main.UserData.get(player).getString((byte)5))
			{
			case "NT":
				NPCscript.set("NatureTalk."+Main.UserData.get(player).getString((byte)6)+".Script",event.getMessage());
				break;
			case "NN":
				NPCscript.set("NearByNEWS."+Main.UserData.get(player).getString((byte)6)+".Script", event.getMessage());
				break;
			case "AS":
				NPCscript.set("AboutSkills."+Main.UserData.get(player).getString((byte)6)+".Script",event.getMessage());
				break;
			}
			NPCscript.saveConfig();
			NPGUI.TalkSettingGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getString((byte)5), Integer.parseInt(Main.UserData.get(player).getString((byte)6)));

			Main.UserData.get(player).clearAll();
			return;
		case "NPC_TS2"://NPC_TalkScript2
			switch(Main.UserData.get(player).getString((byte)5))
			{
			case "AS":
				NPCscript.set("AboutSkills."+Main.UserData.get(player).getString((byte)6)+".AlreadyGetScript",event.getMessage());
				break;
			}
			NPCscript.saveConfig();
			NPGUI.TalkSettingGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getString((byte)5), Integer.parseInt(Main.UserData.get(player).getString((byte)6)));

			Main.UserData.get(player).clearAll();
			return;
		case "WDN"://WarpDisplayName
			NPCscript.set("Job.WarpList."+Main.UserData.get(player).getInt((byte)4)+".DisplayName",event.getMessage());
			NPCscript.saveConfig();
			player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : "+ChatColor.YELLOW+event.getMessage()+ChatColor.DARK_AQUA+" 워프 지점의 이동 비용을 입력 하세요!");
			Main.UserData.get(player).setString((byte)4,"WC");
			return;
		case "WC"://WarpCost
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.WarpList."+Main.UserData.get(player).getInt((byte)4)+".Cost",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.WarpMainGUI(player, 0, Main.UserData.get(player).getString((byte)2));

				Main.UserData.get(player).clearAll();
			}
			return;
		case "FixRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.FixRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 수리 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 아이템 내구도 10포인트당 수리 비용을 입력 하세요! ");

				Main.UserData.get(player).setType("NPC");
				Main.UserData.get(player).setString((byte)4, "10Point");
			}
			return;
		case "10Point":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.10PointFixDeal",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, Main.UserData.get(player).getString((byte)2), player.isOp());

				Main.UserData.get(player).clearAll();
			}
			return;
		case "GoodRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.GoodRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 버프 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 최대 버프 세기를 설정해 주세요. ");

				Main.UserData.get(player).setType("NPC");
				Main.UserData.get(player).setString((byte)4, "BuffMaxStrog");
				
			}
			return;
		case "BuffMaxStrog":
			if(isIntMinMax(Message, player, 1,100))
			{
				NPCscript.set("Job.BuffMaxStrog",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 최대 버프 세기는 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 버프 시간을 설정해 주세요. (초 단위)");

				Main.UserData.get(player).setType("NPC");
				Main.UserData.get(player).setString((byte)4, "BuffTime");
			}
			return;
		case "BuffTime":
			if(isIntMinMax(Message, player, 1, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.BuffTime",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 최대 버프 시간은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 복채 비용을 설정해 주세요. ");

				Main.UserData.get(player).setType("NPC");
				Main.UserData.get(player).setString((byte)4, "Deal");
			}
			return;
		case "SuccessRate":
			if(isIntMinMax(Message, player, 1, 100))
			{
				NPCscript.set("Job.SuccessRate",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : 이 NPC의 룬 장착 성공률은 "+ChatColor.WHITE+ChatColor.stripColor(event.getMessage())+"%"+ChatColor.DARK_AQUA+"가 되었습니다!");
				player.sendMessage(ChatColor.DARK_AQUA + "[NPC] : NPC의 룬 장착 비용을 입력 하세요! ");

				Main.UserData.get(player).setType("NPC");
				Main.UserData.get(player).setString((byte)4, "Deal");
			}
			return;
		case "Deal":
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				NPCscript.set("Job.Deal",Integer.parseInt(ChatColor.stripColor(event.getMessage())));
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, Main.UserData.get(player).getString((byte)2), player.isOp());

				Main.UserData.get(player).clearAll();
			}
			return;
		case "NPCJL" ://NPC Job Lord 
			event.setCancelled(true);
			YamlManager JobList  = GUI_YC.getNewConfig("Skill/JobList.yml");
			YamlManager Config = GUI_YC.getNewConfig("config.yml");
			boolean isExitJob = false;
			Object[] Job = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			for(int count = 0; count < Job.length; count++)
			{
				Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
				for(int counter=0;counter<a.length;counter++)
				{
					if(a[counter].toString().equalsIgnoreCase(Message)==true && a[counter].toString().equalsIgnoreCase(Config.getString("Server.DefaultJob"))==false)
						isExitJob = true;
				}
			}
			if(isExitJob == true)
			{
				NPCscript = GUI_YC.getNewConfig("NPC/NPCData/"+ Main.UserData.get(player).getString((byte)2) +".yml");
				NPCscript.removeKey("Job");
				NPCscript.set("Job.Type", "Master");
				NPCscript.set("Job.Job", Message);
				NPCscript.saveConfig();
				NPGUI.MainGUI(player, Main.UserData.get(player).getString((byte)3),player.isOp());
				Main.UserData.get(player).clearAll();
			}
			else
			{
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[NPC] : 이 NPC는 어떤 직업으로 전직 시켜 주는 교관인가요?");
				for(int count = 0; count < Job.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job[count].toString()).getKeys(false).toArray();
					for(int counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(Config.getString("Server.DefaultJob"))==false)
							player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job[count].toString()+" ━ "+ChatColor.YELLOW + a[counter].toString());
					}
				}
			}
			return;
		}
		return;
	}

	private void NewBieTypeChatting(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		switch(Main.UserData.get(player).getString((byte)1))
		{
		case "NSM"://NewbieSupportMoney
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				YamlManager NewBieYM = GUI_YC.getNewConfig("ETC/NewBie.yml");
				NewBieYM.set("SupportMoney", Integer.parseInt(Message));
				NewBieYM.saveConfig();
				GBD.GoldBigDragon_Advanced.GUI.NewBieGUI NGUI = new GBD.GoldBigDragon_Advanced.GUI.NewBieGUI();
				NGUI.NewBieSupportItemGUI(player);
				Main.UserData.get(player).clearAll();
			}
			return;
		}
		return;
	}

	private void SkillTypeChatting(PlayerChatEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI SKGUI = new GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI();
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;

		YamlManager SkillList  = GUI_YC.getNewConfig("Skill/SkillList.yml");
		event.setCancelled(true);
		
		String Message = ChatColor.stripColor(event.getMessage());
		
		switch(Main.UserData.get(player).getString((byte)1))
		{
		case "SKL"://SkillLore
			sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 0.5F);
			SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".Lore", event.getMessage());
			SkillList.saveConfig();
			SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
			Main.UserData.get(player).clearAll();
			return;

		case "CS"://CreateSkill
			Message.replace(".", "");
			Message.replace("\"", "");
			Message.replace("\'", "");
			Message.replace("\\", "");
			if(Message.equals("")||Message.equals(null))
				Message = "이름없는 스킬";
			SkillList.set(Message+".ID",403);
			SkillList.set(Message+".DATA",0);
			SkillList.set(Message+".Amount",1);
			SkillList.set(Message+".SkillRank."+1+".Command","null");
			SkillList.set(Message+".SkillRank."+1+".BukkitPermission",false);
			SkillList.set(Message+".SkillRank."+1+".MagicSpells","null");
			SkillList.set(Message+".SkillRank."+1+".Lore",ChatColor.GRAY + "     [설명 없음]     ");
			SkillList.set(Message+".SkillRank."+1+".AffectStat","없음");
			SkillList.set(Message+".SkillRank."+1+".DistrictWeapon","없음");
			SkillList.saveConfig();
			sound.SP(player, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 0.5F);
			SKGUI.AllSkillsGUI(player, 0,false,"Maple");
			Main.UserData.get(player).clearAll();
			return;
		case "CSID" ://ChangeSkillID 
			if(isIntMinMax(Message, player, 1, 2267))
			{
				GBD.GoldBigDragon_Advanced.Event.Interact I = new GBD.GoldBigDragon_Advanced.Event.Interact();
				if(I.SetItemDefaultName(Integer.parseInt(Message),(byte)0).equalsIgnoreCase("지정되지 않은 아이템"))
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".ID", Integer.parseInt(Message));
				SkillList.saveConfig();
				Main.UserData.get(player).setType("Skill");
				Main.UserData.get(player).setString((byte)1, "CSD");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 스킬 아이콘의 DATA값을 입력 해 주세요!!");
			}
			return;
		case "CSD" ://ChangeSkillData
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".DATA", Integer.parseInt(Message));
				SkillList.saveConfig();
				Main.UserData.get(player).setType("Skill");
				Main.UserData.get(player).setString((byte)1, "CSA");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 스킬 아이콘의 개수를 입력 해 주세요!!");
			}
			return;
		case "CSA" ://ChangeSkillAmount
			if(isIntMinMax(Message, player, 1, 127))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".Amount", Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.AllSkillsGUI(player, 0,false,"Maple");
				Main.UserData.get(player).clearAll();
			}
			return;
		case "SP"://SkillPoint
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".SkillPoint",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BH"://BonusHealth
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusHP",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BM"://BonusMana
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusMP",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BSTR"://BonusSTR
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusSTR",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BDEX"://BonusDEX
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusDEX",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BINT"://BonusINT
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusINT",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BWILL"://BonusWILL
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusWILL",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BLUK"://BonusLUK
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusLUK",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BBAL"://BonusBalance
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusBAL",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BCRI"://BonusCritical
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusCRI",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BDEF"://BonusDefense
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusDEF",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BPRO"://BonusProtect
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusPRO",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BMDEF"://BonusMagicDefense
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusMDEF",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "BMPRO"://BonusMagicProtect
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				SkillList.set(Main.UserData.get(player).getString((byte)2)+".SkillRank."+Main.UserData.get(player).getInt((byte)4)+".BonusMPRO",Integer.parseInt(Message));
				SkillList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				SKGUI.SkillRankOptionGUI(player, Main.UserData.get(player).getString((byte)2), Main.UserData.get(player).getInt((byte)4));
				Main.UserData.get(player).clearAll();
			}
			return;
		}//Main.JobHashMap1를 비교하는 switch의 끝
	}

	private void JobTypeChatting(PlayerChatEvent event)
	{
		event.setCancelled(true);
		GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI SKGUI = new GBD.GoldBigDragon_Advanced.GUI.OPBoxSkillGUI();
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;

		String Message = ChatColor.stripColor(event.getMessage());
		YamlManager JobList  = GUI_YC.getNewConfig("Skill/JobList.yml");
		GBD.GoldBigDragon_Advanced.GUI.JobGUI JGUI = new GBD.GoldBigDragon_Advanced.GUI.JobGUI();

		switch(Main.UserData.get(player).getString((byte)1))
		{
		case "CC"://CreateCategory
			if(JobList.getConfigurationSection("Mabinogi").getKeys(false).toString().contains(Message) == false)
			{
				JobList.set("Mabinogi."+Message+".LV",null);
				JobList.saveConfig();
				sound.SP(player, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 0.5F);
				JGUI.Mabinogi_ChooseCategory(player,0);
				new GBD.GoldBigDragon_Advanced.ETC.Job().AllPlayerFixAllSkillAndJobYML();
				Main.UserData.get(player).clearAll();
			}
			else
			{
				player.sendMessage(ChatColor.RED + "[카테고리] : 이미 존재하는 카테고리 이름입니다!");
  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			return;
		case "CJ"://CreateJob
			JobList.set("MapleStory."+Message+"."+Message+".NeedLV",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedSTR",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedDEX",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedINT",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedWILL",0);
			JobList.set("MapleStory."+Message+"."+Message+".NeedLUK",0);
			JobList.set("MapleStory."+Message+"."+Message+".PrevJob","null");
			JobList.set("MapleStory."+Message+"."+Message+".IconID",267);
			JobList.set("MapleStory."+Message+"."+Message+".IconData",0);
			JobList.set("MapleStory."+Message+"."+Message+".IconAmount",1);
			JobList.set("MapleStory."+Message+"."+Message+".Skill.1",null);
			JobList.saveConfig();
			sound.SP(player, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 0.5F);
			JGUI.MapleStory_JobSetting(player,Message);
			Main.UserData.get(player).clearAll();
			return;
		case "CJL"://CreateJobLevel (승급 만들기)
			String JobName2 = Main.UserData.get(player).getString((byte)2);
			int NowJobLevel = JobList.getConfigurationSection("MapleStory."+JobName2).getKeys(false).size();
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedLV",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedSTR",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedDEX",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedINT",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedWILL",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".NeedLUK",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".PrevJob","null");
			JobList.set("MapleStory."+JobName2+"."+Message+".IconID",267);
			JobList.set("MapleStory."+JobName2+"."+Message+".IconData",0);
			JobList.set("MapleStory."+JobName2+"."+Message+".IconAmount",1);
			JobList.set("MapleStory."+JobName2+"."+Message+".Skill.1",null);
			JobList.saveConfig();
			sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
			JGUI.MapleStory_JobSetting(player, JobName2);
			Main.UserData.get(player).clearAll();
			return;
		case "JNL"://JobNeedLevel (승급을 위한 필요 레벨)
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = Main.UserData.get(player).getString((byte)2);
				String JobNick2 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedLV",Integer.parseInt(Message));
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JNS");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.STR+"을 설정하세요.");
			}
			return;
		case "JNS" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = Main.UserData.get(player).getString((byte)2);
				String JobNick2 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedSTR",Integer.parseInt(Message));
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JND");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.DEX+"를 설정하세요.");
			}
			return;
		case "JND" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = Main.UserData.get(player).getString((byte)2);
				String JobNick2 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedDEX",Integer.parseInt(Message));
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JNI");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.INT+"을 설정하세요.");
			}
			return;
		case "JNI" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = Main.UserData.get(player).getString((byte)2);
				String JobNick2 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedINT",Integer.parseInt(Message));
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JNW");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.WILL+"를 설정하세요.");
			}
			return;
		case "JNW" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = Main.UserData.get(player).getString((byte)2);
				String JobNick2 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedWILL",Integer.parseInt(Message));
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JNLU");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +"의 승급 필요 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.LUK+"을 설정하세요.");
			}
			return;
		case "JNLU" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName3 = Main.UserData.get(player).getString((byte)2);
				String JobNick2 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName3+"."+JobNick2+".NeedLUK",Integer.parseInt(Message));
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JNPJ");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : 어떤 직업이"+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +" 로 승급 가능하게 할까요?");
				
				Object[] Job2 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(int count = 0; count < Job2.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job2[count].toString()).getKeys(false).toArray();
					for(int counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(JobNick2)==false)
						player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job2[count].toString()+" ━ "+ChatColor.YELLOW + a[counter].toString());
					}
				}
				player.sendMessage(ChatColor.LIGHT_PURPLE + " 이전 직업이 무엇이든 상관 없을 경우 ━ "+ChatColor.YELLOW + "없음");
			}
			return;
		case "JNPJ" : 
			String JobName3 = Main.UserData.get(player).getString((byte)2);
			String JobNick2 = Main.UserData.get(player).getString((byte)3);
			Object[] Job2 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
			boolean checked = false;
			if(Message.equalsIgnoreCase("없음")==false)
			{
				for(int count = 0; count < Job2.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job2[count].toString()).getKeys(false).toArray();
					for(int counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(ChatColor.stripColor(Message)))
						{
							checked = true;
							break;
						}
					}
				}
			}
			else
				checked = true;
			if(checked == true)
			{
				if(Message.equalsIgnoreCase("없음")==false)
					JobList.set("MapleStory."+JobName3+"."+JobNick2+".PrevJob",ChatColor.stripColor(Message));
				else
					JobList.set("MapleStory."+JobName3+"."+JobNick2+".PrevJob","null");
					
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JNICONID");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick2+ChatColor.LIGHT_PURPLE +" 를 나타내는 아이콘 ID는 무엇인가요?");
			}
			else
			{
				Object[] Job1 = JobList.getConfigurationSection("MapleStory").getKeys(false).toArray();
				for(int count = 0; count < Job1.length; count++)
				{
					Object[] a = JobList.getConfigurationSection("MapleStory."+Job1[count].toString()).getKeys(false).toArray();
					for(int counter=0;counter<a.length;counter++)
					{
						if(a[counter].toString().equalsIgnoreCase(JobNick2)==false)
						player.sendMessage(ChatColor.LIGHT_PURPLE + " "+Job2[count].toString()+" ━ "+ChatColor.YELLOW + a[counter].toString());
					}
				}
				player.sendMessage(ChatColor.LIGHT_PURPLE + " 이전 직업이 무엇이든 상관 없을 경우 ━ "+ChatColor.YELLOW + "없음");
			}
			return;
		case "JNICONID" : 
			if(isIntMinMax(Message, player, 1, 2267))
			{
				GBD.GoldBigDragon_Advanced.Event.Interact I = new GBD.GoldBigDragon_Advanced.Event.Interact();
				if(I.SetItemDefaultName(Integer.parseInt(Message),(byte)0).equalsIgnoreCase("지정되지 않은 아이템"))
				{
					player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
	  				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				String JobName4 = Main.UserData.get(player).getString((byte)2);
				String JobNick3 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconID",Integer.parseInt(Message));
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JNICONDATA");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick3+ChatColor.LIGHT_PURPLE +" 를 나타내는 아이콘 DATA는 무엇인가요?");
			}
			return;
		case "JNICONDATA" : 
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				String JobName4 = Main.UserData.get(player).getString((byte)2);
				String JobNick3 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconData",Integer.parseInt(Message));
				JobList.saveConfig();
				Main.UserData.get(player).setType("Job");
				Main.UserData.get(player).setString((byte)1, "JNICONAMOUNT");
				sound.SP(player, org.bukkit.Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[직업] : "+ChatColor.YELLOW +JobNick3+ChatColor.LIGHT_PURPLE +" 를 나타내는 아이콘 갯수는 몇 개인가요?");
			}
			return;
		case "JNICONAMOUNT" : 
			if(isIntMinMax(Message, player, 1, 127))
			{
				String JobName4 = Main.UserData.get(player).getString((byte)2);
				String JobNick3 = Main.UserData.get(player).getString((byte)3);
				JobList.set("MapleStory."+JobName4+"."+JobNick3+".IconAmount",Integer.parseInt(Message));
				JobList.saveConfig();
				sound.SP(player, org.bukkit.Sound.ANVIL_USE, 1.0F, 1.0F);
				JGUI.MapleStory_JobSetting(player, JobName4);
				Main.UserData.get(player).clearAll();
			}
			return;
		}
		return;
	}

	private void MonsterTypeChatting(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.GUI.MonsterGUI MGUI = new GBD.GoldBigDragon_Advanced.GUI.MonsterGUI();
		GBD.GoldBigDragon_Advanced.ETC.Monster MC = new GBD.GoldBigDragon_Advanced.ETC.Monster();
		YamlController Main_YC = GBD.GoldBigDragon_Advanced.Main.Main.Main_YC;
		YamlManager Monster  = Main_YC.getNewConfig("Monster/MonsterList.yml");
		Object[] monsterlist = Monster.getConfigurationSection("").getKeys(false).toArray();
	    GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(Main.UserData.get(player).getString((byte)1))
		{
		case "Potion":
			if(isIntMinMax(message, player, 0, 100))
			{
				String MonsterName = Main.UserData.get(player).getString((byte)3);
				switch(Main.UserData.get(player).getString((byte)2))
				{
				case "Regenerate":
					Monster.set(MonsterName+".Potion.Regenerate", Integer.parseInt(message));break;
				case "Poision":
					Monster.set(MonsterName+".Potion.Poison", Integer.parseInt(message));break;
				case "Speed":
					Monster.set(MonsterName+".Potion.Speed", Integer.parseInt(message));break;
				case "Slow":
					Monster.set(MonsterName+".Potion.Slow", Integer.parseInt(message));break;
				case "Strength":
					Monster.set(MonsterName+".Potion.Strength", Integer.parseInt(message));break;
				case "Weak":
					Monster.set(MonsterName+".Potion.Weak", Integer.parseInt(message));break;
				case "Jump":
					Monster.set(MonsterName+".Potion.JumpBoost", Integer.parseInt(message));break;
				}
				Monster.saveConfig();
				Main.UserData.get(player).clearAll();
				s.SP(player, Sound.DRINK, 1.0F, 1.0F);
				MGUI.MonsterPotionGUI(player, MonsterName);
			}
		return;
		case "NM"://NewMonster
			message = message.replace(".", "");
			for(int count = 0; count < monsterlist.length;count++)
	    	{
	    		if(monsterlist[count].toString().equals(message) == true)
	    		{
				  	s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			    	player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름의 몬스터는 이미 존재합니다!");
			    	return;
	    		}
	    	}
			s.SP(player, org.bukkit.Sound.WOLF_BARK, 1.0F, 1.0F);
	    	MC.CreateMonster(message);
	    	player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.YELLOW+message+ChatColor.GREEN+" 몬스터 생성 완료! (추가 설정을 해 주세요)");
			s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
			MGUI.MonsterOptionSettingGUI(player, message);
			Main.UserData.get(player).clearAll();
	    	return;
		case "CN"://ChangeName
			message= event.getMessage().replace(".", "");
			Monster.set(Main.UserData.get(player).getString((byte)2)+".Name", message);
			Monster.saveConfig();
			s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
			MGUI.MonsterOptionSettingGUI(player, Main.UserData.get(player).getString((byte)2));
			Main.UserData.get(player).clearAll();
	    	return;
		case "HP"://HealthPoint
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, Main.UserData.get(player).getString((byte)2));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "EXP":
		case "LUK":
		case "Magic_Protect" : //MagicProtect
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, Main.UserData.get(player).getString((byte)2));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "MAX_Money"://Maximum Drop Money
			if(isIntMinMax(message, player, Main.UserData.get(player).getInt((byte)1), Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, Main.UserData.get(player).getString((byte)2));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "MIN_Money"://Minimum Drop Money
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				Main.UserData.get(player).setInt((byte)1, Integer.parseInt(message));
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 해당 몬스터가 드랍하는 최대 골드량을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"("+message+" ~ "+Integer.MAX_VALUE+")");
				Main.UserData.get(player).setString((byte)1, "MAX_Money");
			}
			return;
		case "STR"://Strength
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GBD.GoldBigDragon_Advanced.Main.ServerOption.DEX+"는 몬스터의 원거리 공격력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.DEX+"를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				Main.UserData.get(player).setString((byte)1, "DEX");
			}
			return;
		case "DEX"://DEX
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GBD.GoldBigDragon_Advanced.Main.ServerOption.INT+"은 몬스터의 폭발 공격력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.INT+"을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				Main.UserData.get(player).setString((byte)1, "INT");
			}
			return;
		case "INT"://INT
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GBD.GoldBigDragon_Advanced.Main.ServerOption.WILL+"는 몬스터의 크리티컬 확률을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.WILL+"를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				Main.UserData.get(player).setString((byte)1, "WILL");
			}
			return;
		case "WILL"://WILL
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GBD.GoldBigDragon_Advanced.Main.ServerOption.LUK+"은 몬스터의 크리티컬 확률을 크게 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+GBD.GoldBigDragon_Advanced.Main.ServerOption.LUK+"을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				Main.UserData.get(player).setString((byte)1, "LUK");
			}
			return;
		case "DEF"://Defense
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(보호는 몬스터의 물리 저항력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 보호를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				Main.UserData.get(player).setString((byte)1, "Protect");
			}
			return;
		case "Protect"://Protect
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(마법 방어는 몬스터의 마법 방어력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 마법 방어를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				Main.UserData.get(player).setString((byte)1, "Magic_DEF");
			}
			return;
		case "Magic_DEF"://MagicDefense
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(마법 보호는 몬스터의 마법 저항력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 마법 보호를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				Main.UserData.get(player).setString((byte)1, "Magic_Protect");
			}
			return;
		case "Head.DropChance"://DropChance
			if(isIntMinMax(message, player, 1, 1000))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 갑옷 드랍률을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ 1000)");
				Main.UserData.get(player).setString((byte)1, "Chest.DropChance");
			}
			return;
		case "Chest.DropChance"://DropChance
			if(isIntMinMax(message, player, 1, 1000))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 바지 드랍률을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ 1000)");
				Main.UserData.get(player).setString((byte)1, "Leggings.DropChance");
			}
			return;
		case "Leggings.DropChance"://DropChance
			if(isIntMinMax(message, player, 1, 1000))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 부츠 드랍률을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ 1000)");
				Main.UserData.get(player).setString((byte)1, "Boots.DropChance");
			}
			return;
		case "Boots.DropChance"://DropChance
			if(isIntMinMax(message, player, 1, 1000))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 무기 드랍률을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ 1000)");
				Main.UserData.get(player).setString((byte)1, "Hand.DropChance");
			}
			return;
		case "Hand.DropChance"://DropChance
			if(isIntMinMax(message, player, 1, 1000))
			{
				Monster.set(Main.UserData.get(player).getString((byte)2)+"."+Main.UserData.get(player).getString((byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, Main.UserData.get(player).getString((byte)2));
				Main.UserData.get(player).clearAll();
			}
			return;
		}
		return;
	}
	
	private void TeleportTypeChatting(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.GUI.WarpGUI WGUI = new GBD.GoldBigDragon_Advanced.GUI.WarpGUI();
	    GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(Main.UserData.get(player).getString((byte)1))
		{
		case "NW"://NewWarp
			message = message.replace(".", "");
			new GBD.GoldBigDragon_Advanced.ETC.Teleport().CreateNewTeleportSpot(player, message);
			Main.UserData.get(player).clearAll();
			return;
		}
		return;
	}
	
	private void EventChatting(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		GBD.GoldBigDragon_Advanced.Effect.PacketSender PS = new GBD.GoldBigDragon_Advanced.Effect.PacketSender();
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager Config =GUI_YC.getNewConfig("config.yml");
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
	    
		switch(Main.UserData.get(player).getString((byte)1))
		{
		case "SKP"://SkillPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_Level_Up_SkillPoint") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_SkillPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스킬 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스킬 포인트가 증가됩니다.]");
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_SkillPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스킬 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스킬 포인트가 증가됩니다.]");
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 스킬 포인트  "+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스킬 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스킬 포인트가 원래대로 돌아옵니다.]");
						Config.set("Event.Multiple_Level_Up_SkillPoint",1);
					}
				}
				Config.saveConfig();
				new GBD.GoldBigDragon_Advanced.GUI.EventGUI().EventGUI_Main(player);
				Main.UserData.get(player).clearAll();
			}
			return;
		case "STP"://StatPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_Level_Up_StatPoint") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_StatPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스텟 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스텟 포인트가 증가됩니다.]");
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_StatPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스텟 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스텟 포인트가 증가됩니다.]");
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 스텟 포인트  "+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스텟 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스텟 포인트가 원래대로 돌아옵니다.]");
						Config.set("Event.Multiple_Level_Up_StatPoint",1);
					}
				}
				Config.saveConfig();
				new GBD.GoldBigDragon_Advanced.GUI.EventGUI().EventGUI_Main(player);
				Main.UserData.get(player).clearAll();
			}
			return;

		case "EXP"://EXP
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_EXP_Get") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_EXP_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "경험치 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[획득하는 경험치량이 증가됩니다.]");
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_EXP_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "경험치 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[획득하는 경험치량이 증가됩니다.]");
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 경험치  "+ Config.getInt("Event.Multiple_EXP_Get")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "경험치 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[획득하는 경험치량이 원래대로 돌아옵니다.]");
						Config.set("Event.Multiple_EXP_Get",1);
					}
				}
				Config.saveConfig();
				new GBD.GoldBigDragon_Advanced.GUI.EventGUI().EventGUI_Main(player);
				Main.UserData.get(player).clearAll();
			}
			return;
		case "DROP"://DropChance
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.DropChance") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.DropChance", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "드랍률 "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[아이템 드랍률이 증가됩니다.]");
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.DropChance", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "드랍률 "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[아이템 드랍률이 증가됩니다.]");
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 드랍률  "+ Config.getInt("Event.DropChance")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "드랍률 "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[아이템 드랍률이 원래대로 돌아옵니다.]");
						Config.set("Event.DropChance",1);
					}
				}
				Config.saveConfig();
				new GBD.GoldBigDragon_Advanced.GUI.EventGUI().EventGUI_Main(player);
				Main.UserData.get(player).clearAll();
			}
			return;
		}
		return;
	}

	private void SystemTypeChatting(PlayerChatEvent event)
	{
		Player player = event.getPlayer();

		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager Config =GUI_YC.getNewConfig("config.yml");
		
	    GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(Main.UserData.get(player).getString((byte)1))
		{
		case "CCP"://ChangeChatPrefix
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			Config.set("Server.ChatPrefix", event.getMessage());
			Config.saveConfig();
			Main.UserData.get(player).clearAll();
			new GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI().OPBoxGUI_Setting(player);
			return;
		case "BMT"://BroadcastMessageTick
			if(isIntMinMax(message, player, 1, 3600))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				Config.set("Server.BroadCastSecond", Integer.parseInt(message));
				Config.saveConfig();
				new GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI().OPBoxGUI_BroadCast(player, 0);
				Main.UserData.get(player).clearAll();
			}
			return;
		case "NBM"://NewBroadcastMessage
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			YamlManager BroadCast =GUI_YC.getNewConfig("BroadCast.yml");
			BroadCast.set(Main.UserData.get(player).getInt((byte)0)+"", ChatColor.WHITE+event.getMessage());
			BroadCast.saveConfig();
			Main.UserData.get(player).clearAll();
			new GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI().OPBoxGUI_BroadCast(player, 0);
			return;
		case "JM"://JoinMessage
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			if(message.equals("없음"))
				Config.set("Server.JoinMessage", null);
			else
				Config.set("Server.JoinMessage", ChatColor.WHITE+event.getMessage());
			Config.saveConfig();
			Main.UserData.get(player).clearAll();
			new GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI().OPBoxGUI_Setting(player);
			return;
		case "QM"://QuitMessage
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			if(message.equals("없음"))
				Config.set("Server.QuitMessage", null);
			else
				Config.set("Server.QuitMessage", ChatColor.WHITE+event.getMessage());
			Config.saveConfig();
			Main.UserData.get(player).clearAll();
			new GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI().OPBoxGUI_Setting(player);
			return;
		case "CSN"://ChangeStatName
			{
<<<<<<< HEAD
				message.replace(".", "");
				message.replace(":", "");
				message.replace(" ", "");
				String Message = event.getMessage();
				Message.replace(".", "");
				Message.replace(":", "");
=======
				String Pa = event.getMessage();
				Pa.replace(".", "");
				Pa.replace(":", "");
				Pa.replace(" ", "");
>>>>>>> origin/GoldBigDragonRPG_Advanced
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				switch(Main.UserData.get(player).getString((byte)2))
				{
				case "체력":
<<<<<<< HEAD
					Config.set("Server.STR", message);
					break;
				case "체력 설명":
					Config.set("Server.STR_Lore", Message);
					break;
				case "솜씨":
					Config.set("Server.DEX", message);
					break;
				case "솜씨 설명":
					Config.set("Server.DEX_Lore", Message);
					break;
				case "지력":
					Config.set("Server.INT", message);
					break;
				case "지력 설명":
					Config.set("Server.INT_Lore", Message);
					break;
				case "의지":
					Config.set("Server.WILL", message);
					break;
				case "의지 설명":
					Config.set("Server.WILL_Lore", Message);
					break;
				case "행운":
					Config.set("Server.LUK", message);
					break;
				case "행운 설명":
					Config.set("Server.LUK_Lore", Message);
					break;
				case "화폐":
					String Pa = event.getMessage();
					Pa.replace(".", "");
					Pa.replace(":", "");
					Pa.replace(" ", "");
=======
					Config.set("Server.STR", Pa);
					break;
				case "솜씨":
					Config.set("Server.DEX", Pa);
					break;
				case "지력":
					Config.set("Server.INT", Pa);
					break;
				case "의지":
					Config.set("Server.WILL", Pa);
					break;
				case "행운":
					Config.set("Server.LUK", Pa);
					break;
				case "화폐":
>>>>>>> origin/GoldBigDragonRPG_Advanced
					Config.set("Server.MoneyName", Pa);
					break;
				}
				Config.saveConfig();
				Main.UserData.get(player).clearAll();
				player.sendMessage(ChatColor.GREEN + "[System] : 변경된 내용은 서버 안전을 위해, 서버 리로드 이후 일괄 적용됩니다.");
				new GBD.GoldBigDragon_Advanced.GUI.OPBoxGUI().OPBoxGUI_StatChange(player);
			}
			return;
		}
		return;
	}

	private void NaviTypeChatting(PlayerChatEvent event)
	{
		Player player = event.getPlayer();

		YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
		YamlManager NavigationConfig =Config_YC.getNewConfig("Navigation/NavigationList.yml");

	    GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(Main.UserData.get(player).getString((byte)0))
		{
		case "NN"://NewNavigation
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			long UTC = new GBD.GoldBigDragon_Advanced.Util.ETC().getNowUTC();
			NavigationConfig.set(UTC+".Name", event.getMessage());
			NavigationConfig.set(UTC+".world", player.getLocation().getWorld().getName());
			NavigationConfig.set(UTC+".x", (int)player.getLocation().getX());
			NavigationConfig.set(UTC+".y", (int)player.getLocation().getY());
			NavigationConfig.set(UTC+".z", (int)player.getLocation().getZ());
			NavigationConfig.set(UTC+".time", -1);
			NavigationConfig.set(UTC+".sensitive", 5);
			NavigationConfig.set(UTC+".onlyOPuse", true);
			NavigationConfig.set(UTC+".ShowArrow", 0);
			NavigationConfig.saveConfig();
			Main.UserData.get(player).clearAll();
			new GBD.GoldBigDragon_Advanced.GUI.NavigationGUI().NavigationOptionGUI(player,UTC+"");
			return;
		case "CNN"://ChangeNavigationName이름 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NavigationConfig.set(Main.UserData.get(player).getString((byte)1)+".Name", event.getMessage());
			NavigationConfig.saveConfig();
			new GBD.GoldBigDragon_Advanced.GUI.NavigationGUI().NavigationOptionGUI(player,Main.UserData.get(player).getString((byte)1));
			Main.UserData.get(player).clearAll();
			return;
		case "CNT"://ChangeNavigationTimer지속 시간
			if(isIntMinMax(message, player, -1, 3600))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(Main.UserData.get(player).getString((byte)1)+".time", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GBD.GoldBigDragon_Advanced.GUI.NavigationGUI().NavigationOptionGUI(player,Main.UserData.get(player).getString((byte)1));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "CNS"://ChangeNavigationSensitive도착 반경
			if(isIntMinMax(message, player, 1, 1000))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(Main.UserData.get(player).getString((byte)1)+".sensitive", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GBD.GoldBigDragon_Advanced.GUI.NavigationGUI().NavigationOptionGUI(player,Main.UserData.get(player).getString((byte)1));
				Main.UserData.get(player).clearAll();
			}
			return;
		case "CNA"://ChangeNavigationArrow네비 타입
			if(isIntMinMax(message, player, 0, 10))
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(Main.UserData.get(player).getString((byte)1)+".ShowArrow", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GBD.GoldBigDragon_Advanced.GUI.NavigationGUI().NavigationOptionGUI(player,Main.UserData.get(player).getString((byte)1));
				Main.UserData.get(player).clearAll();
			}
			return;
		}
		return;
	}
	
	private boolean isIntMinMax(String message,Player player, int Min, int Max)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		try
		{
			if(message.split(" ").length <= 1 && Integer.parseInt(message) >= Min&& Integer.parseInt(message) <= Max)
				return true;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : 최소 "+ChatColor.YELLOW +""+Min+ChatColor.RED+", 최대 "+ChatColor.YELLOW+""+Max+ChatColor.RED+" 이하의 숫자를 입력하세요!");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. ("+ChatColor.YELLOW +""+Min+ChatColor.RED+" ~ "+ChatColor.YELLOW+""+Max+ChatColor.RED+")");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}

	private byte askOX(String message,Player player)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		if(message.split(" ").length <= 1)
		{
			if(message.equals("x")||message.equals("X"))
				return 0;
			else if(message.equals("o")||message.equals("O"))
				return 1;
			else
			{
				player.sendMessage(ChatColor.RED + "[SYSTEM] : O 혹은 X를 입력 해 주세요!");
				sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			
		}
		else
		{
			player.sendMessage(ChatColor.RED + "[SYSTEM] : O 혹은 X를 입력 해 주세요!");
			sound.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		}
		return -1;
	}
}