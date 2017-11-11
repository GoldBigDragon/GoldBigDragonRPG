package admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_Chat;
import util.YamlLoader;

public class Event_Chat extends Util_Chat
{
	public void eventChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();
		
		effect.SendPacket sp = new effect.SendPacket();
		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
	    
		switch(u.getString(player, (byte)1))
		{
		case "SKP"://SkillPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.Multiple_Level_Up_SkillPoint") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Level_Up_SkillPoint", value);
						sp.sendTitleAllPlayers("\'§f스킬 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_SkillPoint")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[레벨업시 얻는 스킬 포인트가 증가됩니다.]");
						main.Main_ServerOption.eventSkillPoint = (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Level_Up_SkillPoint", value);
						sp.sendTitleAllPlayers("\'§f스킬 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_SkillPoint")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[레벨업시 얻는 스킬 포인트가 증가됩니다.]");
						main.Main_ServerOption.eventSkillPoint = (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 스킬 포인트  "+ configYaml.getInt("Event.Multiple_Level_Up_SkillPoint")+"배 이벤트를 종료합니다.");
						sp.sendTitleAllPlayers("\'§f스킬 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_SkillPoint")+"§f배 이벤트가 종료되었습니다.\'");
						sp.sendActionBarAllPlayers("§l§e[레벨업시 얻는 스킬 포인트가 원래대로 돌아옵니다.]");
						configYaml.set("Event.Multiple_Level_Up_SkillPoint",1);
						main.Main_ServerOption.eventSkillPoint = 1;
					}
				}
				configYaml.saveConfig();
				new admin.Event_GUI().eventGuiMain(player);
				u.clearAll(player);
			}
			return;
		case "STP"://StatPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.Multiple_Level_Up_StatPoint") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Level_Up_StatPoint", value);
						sp.sendTitleAllPlayers("\'§f스텟 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_StatPoint")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[레벨업시 얻는 스텟 포인트가 증가됩니다.]");
						main.Main_ServerOption.eventStatPoint = (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Level_Up_StatPoint", value);
						sp.sendTitleAllPlayers("\'§f스텟 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_StatPoint")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[레벨업시 얻는 스텟 포인트가 증가됩니다.]");
						main.Main_ServerOption.eventStatPoint = (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 스텟 포인트  "+ configYaml.getInt("Event.Multiple_Level_Up_StatPoint")+"배 이벤트를 종료합니다.");
						sp.sendTitleAllPlayers("\'§f스텟 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_StatPoint")+"§f배 이벤트가 종료되었습니다.\'");
						sp.sendActionBarAllPlayers("§l§e[레벨업시 얻는 스텟 포인트가 원래대로 돌아옵니다.]");
						configYaml.set("Event.Multiple_Level_Up_StatPoint",1);
						main.Main_ServerOption.eventStatPoint = 1;
					}
				}
				configYaml.saveConfig();
				new admin.Event_GUI().eventGuiMain(player);
				u.clearAll(player);
			}
			return;

		case "EXP"://EXP
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.Multiple_EXP_Get") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_EXP_Get", value);
						sp.sendTitleAllPlayers("\'§f경험치 §e"+ configYaml.getInt("Event.Multiple_EXP_Get")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[획득하는 경험치량이 증가됩니다.]");
						main.Main_ServerOption.eventExp= (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_EXP_Get", value);
						sp.sendTitleAllPlayers("\'§f경험치 §e"+ configYaml.getInt("Event.Multiple_EXP_Get")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[획득하는 경험치량이 증가됩니다.]");
						main.Main_ServerOption.eventExp= (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 경험치  "+ configYaml.getInt("Event.Multiple_EXP_Get")+"배 이벤트를 종료합니다.");
						sp.sendTitleAllPlayers("\'§f경험치 §e"+ configYaml.getInt("Event.Multiple_EXP_Get")+"§f배 이벤트가 종료되었습니다.\'");
						sp.sendActionBarAllPlayers("§l§e[획득하는 경험치량이 원래대로 돌아옵니다.]");
						configYaml.set("Event.Multiple_EXP_Get",1);
						main.Main_ServerOption.eventExp= 1;
					}
				}
				configYaml.saveConfig();
				new admin.Event_GUI().eventGuiMain(player);
				u.clearAll(player);
			}
			return;
		case "DROP"://DropChance
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.DropChance") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.DropChance", value);
						sp.sendTitleAllPlayers("\'§f드랍률 §e"+ configYaml.getInt("Event.DropChance")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[아이템 드랍률이 증가됩니다.]");
						main.Main_ServerOption.eventDropChance= (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.DropChance", value);
						sp.sendTitleAllPlayers("\'§f드랍률 §e"+ configYaml.getInt("Event.DropChance")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[아이템 드랍률이 증가됩니다.]");
						main.Main_ServerOption.eventDropChance= (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 드랍률  "+ configYaml.getInt("Event.DropChance")+"배 이벤트를 종료합니다.");
						sp.sendTitleAllPlayers("\'§f드랍률 §e"+ configYaml.getInt("Event.DropChance")+"§f배 이벤트가 종료되었습니다.\'");
						sp.sendActionBarAllPlayers("§l§e[아이템 드랍률이 원래대로 돌아옵니다.]");
						configYaml.set("Event.DropChance",1);
						main.Main_ServerOption.eventDropChance= 1;
					}
				}
				configYaml.saveConfig();
				new admin.Event_GUI().eventGuiMain(player);
				u.clearAll(player);
			}
			return;
		case "Proficiency"://Proficiency
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.Multiple_Proficiency_Get") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Proficiency_Get", value);
						sp.sendTitleAllPlayers("\'§f숙련도 §e"+ configYaml.getInt("Event.Multiple_Proficiency_Get")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[장비 숙련도 상승량이 증가됩니다.]");
						main.Main_ServerOption.eventProficiency= (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Proficiency_Get", value);
						sp.sendTitleAllPlayers("\'§f숙련도 §e"+ configYaml.getInt("Event.Multiple_Proficiency_Get")+"§f배 이벤트를 시작합니다.\'");
						sp.sendActionBarAllPlayers("§l§e[장비 숙련도 상승량이 증가됩니다.]");
						main.Main_ServerOption.eventProficiency= (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 숙련도  "+ configYaml.getInt("Event.Multiple_Proficiency_Get")+"배 이벤트를 종료합니다.");
						sp.sendTitleAllPlayers("\'§f숙련도 §e"+ configYaml.getInt("Event.Multiple_Proficiency_Get")+"§f배 이벤트가 종료되었습니다.\'");
						sp.sendActionBarAllPlayers("§l§e[장비 숙련도 상승량이 원래대로 돌아옵니다.]");
						configYaml.set("Event.Multiple_Proficiency_Get",1);
						main.Main_ServerOption.eventProficiency= 1;
					}
				}
				configYaml.saveConfig();
				new admin.Event_GUI().eventGuiMain(player);
				u.clearAll(player);
			}
			return;
		}
		return;
	}

}
