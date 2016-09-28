package GBD_RPG.Admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_Chat;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Event_Chat extends Util_Chat
{
	public void EventChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		GBD_RPG.Effect.Effect_Packet PS = new GBD_RPG.Effect.Effect_Packet();
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
	    
		switch(u.getString(player, (byte)1))
		{
		case "SKP"://SkillPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_Level_Up_SkillPoint") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_SkillPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스킬 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스킬 포인트가 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_SkillPoint = (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_SkillPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스킬 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스킬 포인트가 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_SkillPoint = (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 스킬 포인트  "+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스킬 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_SkillPoint")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스킬 포인트가 원래대로 돌아옵니다.]");
						Config.set("Event.Multiple_Level_Up_SkillPoint",1);
						GBD_RPG.Main_Main.Main_ServerOption.Event_SkillPoint = 1;
					}
				}
				Config.saveConfig();
				new GBD_RPG.Admin.Event_GUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;
		case "STP"://StatPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_Level_Up_StatPoint") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_StatPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스텟 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스텟 포인트가 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_StatPoint = (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Level_Up_StatPoint", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스텟 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스텟 포인트가 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_StatPoint = (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 스텟 포인트  "+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "스텟 포인트 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Level_Up_StatPoint")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[레벨업시 얻는 스텟 포인트가 원래대로 돌아옵니다.]");
						Config.set("Event.Multiple_Level_Up_StatPoint",1);
						GBD_RPG.Main_Main.Main_ServerOption.Event_StatPoint = 1;
					}
				}
				Config.saveConfig();
				new GBD_RPG.Admin.Event_GUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;

		case "EXP"://EXP
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_EXP_Get") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_EXP_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "경험치 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[획득하는 경험치량이 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_Exp= (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_EXP_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "경험치 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[획득하는 경험치량이 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_Exp= (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 경험치  "+ Config.getInt("Event.Multiple_EXP_Get")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "경험치 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_EXP_Get")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[획득하는 경험치량이 원래대로 돌아옵니다.]");
						Config.set("Event.Multiple_EXP_Get",1);
						GBD_RPG.Main_Main.Main_ServerOption.Event_Exp= 1;
					}
				}
				Config.saveConfig();
				new GBD_RPG.Admin.Event_GUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;
		case "DROP"://DropChance
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.DropChance") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.DropChance", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "드랍률 "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[아이템 드랍률이 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_DropChance= (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.DropChance", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "드랍률 "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[아이템 드랍률이 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_DropChance= (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 드랍률  "+ Config.getInt("Event.DropChance")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "드랍률 "+ChatColor.YELLOW +""+ Config.getInt("Event.DropChance")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[아이템 드랍률이 원래대로 돌아옵니다.]");
						Config.set("Event.DropChance",1);
						GBD_RPG.Main_Main.Main_ServerOption.Event_DropChance= 1;
					}
				}
				Config.saveConfig();
				new GBD_RPG.Admin.Event_GUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;
		case "Proficiency"://Proficiency
			if(isIntMinMax(message, player, 1, 10))
			{
				int Value = Integer.parseInt(message);
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getInt("Event.Multiple_Proficiency_Get") == 1)
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Proficiency_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "숙련도 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Proficiency_Get")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[장비 숙련도 상승량이 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_Proficiency= (byte) Value;
					}
				}
				else
				{
					if(Value != 1)
					{
						Config.set("Event.Multiple_Proficiency_Get", Value);
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "숙련도 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Proficiency_Get")+ChatColor.WHITE +"배 이벤트를 시작합니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[장비 숙련도 상승량이 증가됩니다.]");
						GBD_RPG.Main_Main.Main_ServerOption.Event_Proficiency= (byte) Value;
					}
					else
					{
						Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE+"[Server] : 숙련도  "+ Config.getInt("Event.Multiple_Proficiency_Get")+"배 이벤트를 종료합니다.");
						PS.sendTitleAllPlayers("\'"+ChatColor.WHITE + "숙련도 "+ChatColor.YELLOW +""+ Config.getInt("Event.Multiple_Proficiency_Get")+ChatColor.WHITE +"배 이벤트가 종료되었습니다.\'");
						PS.sendActionBarAllPlayers(ChatColor.BOLD+""+ChatColor.YELLOW +"[장비 숙련도 상승량이 원래대로 돌아옵니다.]");
						Config.set("Event.Multiple_Proficiency_Get",1);
						GBD_RPG.Main_Main.Main_ServerOption.Event_Proficiency= 1;
					}
				}
				Config.saveConfig();
				new GBD_RPG.Admin.Event_GUI().EventGUI_Main(player);
				u.clearAll(player);
			}
			return;
		}
		return;
	}

}
