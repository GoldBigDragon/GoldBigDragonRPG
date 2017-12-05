package admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserDataObject;
import util.UtilChat;
import util.YamlLoader;

public class EventChat extends UtilChat
{
	public void eventChatting(PlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
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
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.Multiple_Level_Up_SkillPoint") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Level_Up_SkillPoint", value);
						sp.sendTitleAll("§f스킬 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_SkillPoint")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[레벨업시 얻는 스킬 포인트가 증가됩니다.]", true);
						main.MainServerOption.eventSkillPoint = (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Level_Up_SkillPoint", value);
						sp.sendTitleAll("§f스킬 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_SkillPoint")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[레벨업시 얻는 스킬 포인트가 증가됩니다.]", true);
						main.MainServerOption.eventSkillPoint = (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 스킬 포인트  "+ configYaml.getInt("Event.Multiple_Level_Up_SkillPoint")+"배 이벤트를 종료합니다.");
						sp.sendTitleAll("§f스킬 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_SkillPoint")+"§f배 이벤트가 종료되었습니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[레벨업시 얻는 스킬 포인트가 원래대로 돌아옵니다.]", true);
						configYaml.set("Event.Multiple_Level_Up_SkillPoint",1);
						main.MainServerOption.eventSkillPoint = 1;
					}
				}
				configYaml.saveConfig();
				new admin.EventGui().eventGuiMain(player);
				u.clearAll(player);
			}
			return;
		case "STP"://StatPoint
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.Multiple_Level_Up_StatPoint") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Level_Up_StatPoint", value);
						sp.sendTitleAll("§f스텟 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_StatPoint")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[레벨업시 얻는 스텟 포인트가 증가됩니다.]", true);
						main.MainServerOption.eventStatPoint = (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Level_Up_StatPoint", value);
						sp.sendTitleAll("§f스텟 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_StatPoint")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[레벨업시 얻는 스텟 포인트가 증가됩니다.]", true);
						main.MainServerOption.eventStatPoint = (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 스텟 포인트  "+ configYaml.getInt("Event.Multiple_Level_Up_StatPoint")+"배 이벤트를 종료합니다.");
						sp.sendTitleAll("§f스텟 포인트 §e"+ configYaml.getInt("Event.Multiple_Level_Up_StatPoint")+"§f배 이벤트가 종료되었습니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[레벨업시 얻는 스텟 포인트가 원래대로 돌아옵니다.]", true);
						configYaml.set("Event.Multiple_Level_Up_StatPoint",1);
						main.MainServerOption.eventStatPoint = 1;
					}
				}
				configYaml.saveConfig();
				new admin.EventGui().eventGuiMain(player);
				u.clearAll(player);
			}
			return;

		case "EXP"://EXP
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.Multiple_EXP_Get") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_EXP_Get", value);
						sp.sendTitleAll("§f경험치 §e"+ configYaml.getInt("Event.Multiple_EXP_Get")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[획득하는 경험치량이 증가됩니다.]", true);
						main.MainServerOption.eventExp= (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_EXP_Get", value);
						sp.sendTitleAll("§f경험치 §e"+ configYaml.getInt("Event.Multiple_EXP_Get")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[획득하는 경험치량이 증가됩니다.]", true);
						main.MainServerOption.eventExp= (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 경험치  "+ configYaml.getInt("Event.Multiple_EXP_Get")+"배 이벤트를 종료합니다.");
						sp.sendTitleAll("§f경험치 §e"+ configYaml.getInt("Event.Multiple_EXP_Get")+"§f배 이벤트가 종료되었습니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[획득하는 경험치량이 원래대로 돌아옵니다.]", true);
						configYaml.set("Event.Multiple_EXP_Get",1);
						main.MainServerOption.eventExp= 1;
					}
				}
				configYaml.saveConfig();
				new admin.EventGui().eventGuiMain(player);
				u.clearAll(player);
			}
			return;
		case "DROP"://DropChance
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.DropChance") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.DropChance", value);
						sp.sendTitleAll("§f드랍률 §e"+ configYaml.getInt("Event.DropChance")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[아이템 드랍률이 증가됩니다.]", true);
						main.MainServerOption.eventDropChance= (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.DropChance", value);
						sp.sendTitleAll("§f드랍률 §e"+ configYaml.getInt("Event.DropChance")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[아이템 드랍률이 증가됩니다.]", true);
						main.MainServerOption.eventDropChance= (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 드랍률  "+ configYaml.getInt("Event.DropChance")+"배 이벤트를 종료합니다.");
						sp.sendTitleAll("§f드랍률 §e"+ configYaml.getInt("Event.DropChance")+"§f배 이벤트가 종료되었습니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[아이템 드랍률이 원래대로 돌아옵니다.]", true);
						configYaml.set("Event.DropChance",1);
						main.MainServerOption.eventDropChance= 1;
					}
				}
				configYaml.saveConfig();
				new admin.EventGui().eventGuiMain(player);
				u.clearAll(player);
			}
			return;
		case "Proficiency"://Proficiency
			if(isIntMinMax(message, player, 1, 10))
			{
				int value = Integer.parseInt(message);
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(configYaml.getInt("Event.Multiple_Proficiency_Get") == 1)
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Proficiency_Get", value);
						sp.sendTitleAll("§f숙련도 §e"+ configYaml.getInt("Event.Multiple_Proficiency_Get")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[장비 숙련도 상승량이 증가됩니다.]", true);
						main.MainServerOption.eventProficiency= (byte) value;
					}
				}
				else
				{
					if(value != 1)
					{
						configYaml.set("Event.Multiple_Proficiency_Get", value);
						sp.sendTitleAll("§f숙련도 §e"+ configYaml.getInt("Event.Multiple_Proficiency_Get")+"§f배 이벤트를 시작합니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[장비 숙련도 상승량이 증가됩니다.]", true);
						main.MainServerOption.eventProficiency= (byte) value;
					}
					else
					{
						Bukkit.broadcastMessage("§d[Server] : 숙련도  "+ configYaml.getInt("Event.Multiple_Proficiency_Get")+"배 이벤트를 종료합니다.");
						sp.sendTitleAll("§f숙련도 §e"+ configYaml.getInt("Event.Multiple_Proficiency_Get")+"§f배 이벤트가 종료되었습니다.", null, 1, 2, 1);
						sp.sendActionBar(player, "§l§e[장비 숙련도 상승량이 원래대로 돌아옵니다.]", true);
						configYaml.set("Event.Multiple_Proficiency_Get",1);
						main.MainServerOption.eventProficiency= 1;
					}
				}
				configYaml.saveConfig();
				new admin.EventGui().eventGuiMain(player);
				u.clearAll(player);
			}
			return;
		}
		return;
	}

}
