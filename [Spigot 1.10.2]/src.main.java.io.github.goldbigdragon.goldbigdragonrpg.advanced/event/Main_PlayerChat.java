package event;

import java.util.Calendar;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_Chat;
import util.YamlLoader;



public class Main_PlayerChat extends Util_Chat implements Listener
{
	@EventHandler
	public void PlayerChatting(PlayerChatEvent event)
	{
		event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
		UserData_Object u = new UserData_Object();
	    Player player = event.getPlayer();
	    String playerUUID = event.getPlayer().getUniqueId().toString();
	    if(u.getTemp(player) != null)
	    {
	    	TEMProuter(event, u.getTemp(player));
	    	return;
	    }
	    if(player.isOp()==true)
		    if(u.getType(player) != null)
			    if(u.getType(player).equals("Quest"))
		    	{new quest.Quest_Chat().QuestTypeChatting(event); return;}
			    else if(u.getType(player).equals("WorldCreater"))
		    	{new admin.WorldCreate_Chat().worldCreaterTypeChatting(event); return;}
			    else if(u.getType(player).equals("UseableItem")
			    		||u.getType(player).equals("Upgrade")
			    		||u.getType(player).equals("Item"))
	    		{new customitem.CustomItem_Chat().ItemTypeChatting(event); return;}
			    else if(u.getType(player).equals("Area"))
		    	{new area.Area_Chat().areaTypeChatting(event); return;}
			    else if(u.getType(player).equals("NPC"))
		    	{new npc.NPC_Chat().NPCTypeChatting(event); return;}
			    else if(u.getType(player).equals("NewBie"))
		    	{new admin.NewBie_Chat().newBieTypeChatting(event); return;}
			    else if(u.getType(player).equals("Skill"))
		    	{new skill.Skill_Chat().SkillTypeChatting(event); return;}
			    else if(u.getType(player).equals("Job"))
		    	{new job.Job_Chat().JobTypeChatting(event); return;}
			    else if(u.getType(player).equals("Monster"))
		    	{new monster.Monster_Chat().MonsterTypeChatting(event); return;}
			    else if(u.getType(player).equals("Teleport"))
		    	{new warp.Warp_Chat().TeleportTypeChatting(event);return;}
			    else if(u.getType(player).equals("Event"))
		    	{new admin.Event_Chat().eventChatting(event); return;}
			    else if(u.getType(player).equals("System"))
		    	{new admin.OPbox_Chat().systemTypeChatting(event); return;}
			    else if(u.getType(player).equals("Navi"))
		    	{new admin.Navigation_Chat().naviTypeChatting(event); return;}
			    else if(u.getType(player).equals("Gamble"))
		    	{new admin.Gamble_Chat().gambleChatting(event); return;}
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
	  	String Prefix = "";
	  	if(configYaml.contains("Server.ChatPrefix"))
	  	{
		  	Calendar C = Calendar.getInstance();
	  		Prefix = configYaml.getString("Server.ChatPrefix");
			Prefix=Prefix.replace("%t%",C.get(Calendar.HOUR_OF_DAY)+"시"+C.get(Calendar.MINUTE)+"분");
			if(player.getGameMode()==GameMode.SURVIVAL)
				Prefix=Prefix.replace("%gm%","서바이벌");
			else if(player.getGameMode()==GameMode.ADVENTURE)
				Prefix=Prefix.replace("%gm%","어드밴쳐");
			else if(player.getGameMode()==GameMode.CREATIVE)
				Prefix=Prefix.replace("%gm%","크리에이티브");
			else if(player.getGameMode()==GameMode.SPECTATOR)
				Prefix=Prefix.replace("%gm%","관전");
			
			if(main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType()==0)
				Prefix=Prefix.replace("%ct%","일반");
			else if(main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType()==1)
				Prefix=Prefix.replace("%ct%","파티");
			else if(main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType()==3)
				Prefix=Prefix.replace("%ct%","관리자");
			Prefix=Prefix.replace("%lv%",main.Main_ServerOption.PlayerList.get(playerUUID).getStat_Level()+"");
			Prefix=Prefix.replace("%rlv%",main.Main_ServerOption.PlayerList.get(playerUUID).getStat_RealLevel()+"");
		  	YamlLoader playerSkillYaml = new YamlLoader();
		  	playerSkillYaml.getConfig("Skill/PlayerData/" + player.getUniqueId()+".yml");
			Prefix=Prefix.replace("%job%",playerSkillYaml.getString("Job.Type"));
			Prefix=Prefix.replace("%player%",player.getName());
			Prefix=Prefix.replace("%message%",event.getMessage());
  			event.setCancelled(true);
		  	switch(main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType())
		  	{
		  	case 0:
		  		Bukkit.broadcastMessage(Prefix);
		  		return;
		  	case 1: 
		  		if(main.Main_ServerOption.partyJoiner.containsKey(player) == false)
		  		{
		  			player.sendMessage("§9[파티] : 파티에 가입되어 있지 않습니다!");
		  			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  		}
		  		else
		  		{
		  			main.Main_ServerOption.party.get(main.Main_ServerOption.partyJoiner.get(player)).PartyBroadCastMessage("§9[파티] "+Prefix,null);
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
		  			player.sendMessage("§d[관리자] : 당신은 관리자가 아닙니다!");
		  			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  			}
	  			else
	  			{
	  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	  		    	Player[] a = new Player[playerlist.size()];
	  		    	playerlist.toArray(a);
	  	  			for(int count = 0; count < a.length;count++)
	  	  			{
	  	  		    	if(a[count].isOp())
	  	  		    		a[count].sendMessage("§d[관리자] "+Prefix);
	  	  		    }
	  		  		Bukkit.getConsoleSender().sendMessage("[관리자] "+Prefix);
	  			}
	  			return;
		  	}
	  	}
	  	else
	  	{
		  	switch(main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType())
		  	{
		  	case 0:
		  		return;
		  	case 1: 
	  			event.setCancelled(true);
		  		if(main.Main_ServerOption.partyJoiner.containsKey(player) == false)
		  		{
		  			player.sendMessage("§9[파티] : 파티에 가입되어 있지 않습니다!");
		  			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  		}
		  		else
		  		{
		  			main.Main_ServerOption.party.get(main.Main_ServerOption.partyJoiner.get(player)).PartyBroadCastMessage("§9[파티] "+player.getName()+" : " + event.getMessage(),null);
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
		  			player.sendMessage("§d[관리자] : 당신은 관리자가 아닙니다!");
		  			SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
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
	  	  		    		send.sendMessage("§d[관리자] "+player.getName()+" : " + event.getMessage());
	  	  		    	}	
	  	  		    }
	  		  		Bukkit.getConsoleSender().sendMessage("[관리자] "+player.getName()+" : " + event.getMessage());
	  			}
	  			return;
		  	}
	  	}
	}

	public void TEMProuter(PlayerChatEvent event, String Temp)
	{
		event.setCancelled(true);
		Player player = event.getPlayer();
		
		String Message = ChatColor.stripColor(event.getMessage());
		if(Temp.equals("FA"))
		{
			if(Message.equals(player.getName()))
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[친구] : 자기 자신을 추가할 수 없습니다!");
			}
			else
			{
				Message.replace(".", "");
				if(Bukkit.getServer().getPlayer(Message) != null)
					new user.Equip_GUI().SetFriends(player, Bukkit.getServer().getPlayer(Message));
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[친구] : 해당 플레이어를 찾을 수 없습니다!");
				}
			}
			new user.ETC_GUI().FriendsGUI(player, (short) 0);
			new UserData_Object().initTemp(player);
		}
		else if(Temp.equals("Structure"))
			new structure.Structure_Chat().PlayerChatrouter(event);
		else if(Temp.equals("Dungeon"))
			new dungeon.Dungeon_Chat().PlayerChatrouter(event);
		return;
	}
	
}