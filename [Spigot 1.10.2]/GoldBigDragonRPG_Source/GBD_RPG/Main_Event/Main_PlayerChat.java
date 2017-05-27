package GBD_RPG.Main_Event;

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

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_Chat;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Main_PlayerChat extends Util_Chat implements Listener
{
	@EventHandler
	public void PlayerChatting(PlayerChatEvent event)
	{
		event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
		UserData_Object u = new UserData_Object();
		GBD_RPG.Effect.Effect_Sound sound = new GBD_RPG.Effect.Effect_Sound();
	    Player player = event.getPlayer();
	    String playerUUID = event.getPlayer().getUniqueId().toString();
	    if(u.getTemp(player) != null)
	    {
	    	TEMProuter(event, u.getTemp(player));
	    	return;
	    }
	    if(player.isOp()==true)
		    if(u.getType(player) != null)
			    if(u.getType(player).compareTo("Quest")==0)
		    	{new GBD_RPG.Quest.Quest_Chat().QuestTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("WorldCreater")==0)
		    	{new GBD_RPG.Admin.WorldCreate_Chat().WorldCreaterTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("UseableItem")==0
			    		||u.getType(player).compareTo("Upgrade")==0
			    		||u.getType(player).compareTo("Item")==0)
	    		{new GBD_RPG.CustomItem.CustomItem_Chat().ItemTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("Area")==0)
		    	{new GBD_RPG.Area.Area_Chat().AreaTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("NPC")==0)
		    	{new GBD_RPG.NPC.NPC_Chat().NPCTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("NewBie")==0)
		    	{new GBD_RPG.Admin.NewBie_Chat().NewBieTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("Skill")==0)
		    	{new GBD_RPG.Skill.Skill_Chat().SkillTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("Job")==0)
		    	{new GBD_RPG.Job.Job_Chat().JobTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("Monster")==0)
		    	{new GBD_RPG.Monster.Monster_Chat().MonsterTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("Teleport")==0)
		    	{new GBD_RPG.Warp.Warp_Chat().TeleportTypeChatting(event);return;}
			    else if(u.getType(player).compareTo("Event")==0)
		    	{new GBD_RPG.Admin.Event_Chat().EventChatting(event); return;}
			    else if(u.getType(player).compareTo("System")==0)
		    	{new GBD_RPG.Admin.OPbox_Chat().SystemTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("Navi")==0)
		    	{new GBD_RPG.Admin.Navigation_Chat().NaviTypeChatting(event); return;}
			    else if(u.getType(player).compareTo("Gamble")==0)
		    	{new GBD_RPG.Admin.Gamble_Chat().GambleChatting(event); return;}
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
	  	YamlManager Config = YC.getNewConfig("config.yml");
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
			
			if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType()==0)
				Prefix=Prefix.replace("%ct%","일반");
			else if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType()==1)
				Prefix=Prefix.replace("%ct%","파티");
			else if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType()==3)
				Prefix=Prefix.replace("%ct%","관리자");
			Prefix=Prefix.replace("%lv%",GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(playerUUID).getStat_Level()+"");
			Prefix=Prefix.replace("%rlv%",GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(playerUUID).getStat_RealLevel()+"");
		  	YamlManager Job = YC.getNewConfig("Skill/PlayerData/" + player.getUniqueId()+".yml");
			Prefix=Prefix.replace("%job%",Job.getString("Job.Type"));
			Prefix=Prefix.replace("%player%",player.getName());
			Prefix=Prefix.replace("%message%",event.getMessage());
  			event.setCancelled(true);
		  	switch(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType())
		  	{
		  	case 0:
		  		Bukkit.broadcastMessage(Prefix);
		  		return;
		  	case 1: 
		  		if(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.containsKey(player) == false)
		  		{
		  			player.sendMessage(ChatColor.BLUE + "[파티] : 파티에 가입되어 있지 않습니다!");
	  				sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  		}
		  		else
		  		{
		  			GBD_RPG.Main_Main.Main_ServerOption.Party.get(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player)).PartyBroadCastMessage(ChatColor.BLUE + "[파티] "+Prefix,null);
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
	  				sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  			}
	  			else
	  			{
	  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	  		    	Player[] a = new Player[playerlist.size()];
	  		    	playerlist.toArray(a);
	  	  			for(int count = 0; count < a.length;count++)
	  	  			{
	  	  		    	if(a[count].isOp())
	  	  		    		a[count].sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] "+Prefix);
	  	  		    }
	  		  		Bukkit.getConsoleSender().sendMessage("[관리자] "+Prefix);
	  			}
	  			return;
		  	}
	  	}
	  	else
	  	{
		  	switch(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(playerUUID).getOption_ChattingType())
		  	{
		  	case 0:
		  		return;
		  	case 1: 
	  			event.setCancelled(true);
		  		if(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.containsKey(player) == false)
		  		{
		  			player.sendMessage(ChatColor.BLUE + "[파티] : 파티에 가입되어 있지 않습니다!");
	  				sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  		}
		  		else
		  		{
		  			GBD_RPG.Main_Main.Main_ServerOption.Party.get(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player)).PartyBroadCastMessage(ChatColor.BLUE + "[파티] "+player.getName()+" : " + event.getMessage(),null);
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
	  				sound.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  			}
	  			else
	  			{
	  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	  		    	Player[] a = new Player[playerlist.size()];
	  		    	playerlist.toArray(a);
	  	  			for(short count = 0; count<a.length;count++)
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

	public void TEMProuter(PlayerChatEvent event, String Temp)
	{
		event.setCancelled(true);
		Player player = event.getPlayer();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		String Message = ChatColor.stripColor(event.getMessage());
		if(Temp.compareTo("FA")==0)
		{
			if(Message.compareTo(player.getName())==0)
			{
				s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[친구] : 자기 자신을 추가할 수 없습니다!");
			}
			else
			{
				Message.replace(".", "");
				if(Bukkit.getServer().getPlayer(Message) != null)
					new GBD_RPG.User.Equip_GUI().SetFriends(player, Bukkit.getServer().getPlayer(Message));
				else
				{
					s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[친구] : 해당 플레이어를 찾을 수 없습니다!");
				}
			}
			new GBD_RPG.User.ETC_GUI().FriendsGUI(player, (short) 0);
			new UserData_Object().initTemp(player);
		}
		else if(Temp.compareTo("Structure")==0)
			new GBD_RPG.Structure.Structure_Chat().PlayerChatrouter(event);
		else if(Temp.compareTo("Dungeon")==0)
			new GBD_RPG.Dungeon.Dungeon_Chat().PlayerChatrouter(event);
		return;
	}
	
}