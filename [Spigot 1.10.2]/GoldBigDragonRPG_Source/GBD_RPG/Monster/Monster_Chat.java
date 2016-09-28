package GBD_RPG.Monster;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_Chat;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Monster_Chat extends Util_Chat
{
	public void MonsterTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();
		GBD_RPG.Monster.Monster_GUI MGUI = new GBD_RPG.Monster.Monster_GUI();
		GBD_RPG.Monster.Monster_Spawn MC = new GBD_RPG.Monster.Monster_Spawn();
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Monster  = YC.getNewConfig("Monster/MonsterList.yml");
		Object[] monsterlist = Monster.getConfigurationSection("").getKeys(false).toArray();
	    GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "Potion":
			if(isIntMinMax(message, player, 0, 100))
			{
				String MonsterName = u.getString(player, (byte)3);
				switch(u.getString(player, (byte)2))
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
				u.clearAll(player);
				s.SP(player, Sound.ENTITY_GENERIC_DRINK, 1.0F, 1.0F);
				MGUI.MonsterPotionGUI(player, MonsterName);
			}
		return;
		case "NM"://NewMonster
			message = message.replace(".", "");
			for(short count = 0; count < monsterlist.length;count++)
	    	{
	    		if(monsterlist[count].toString().compareTo(message)==0)
	    		{
				  	s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			    	player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름의 몬스터는 이미 존재합니다!");
			    	return;
	    		}
	    	}
			s.SP(player, org.bukkit.Sound.ENTITY_WOLF_AMBIENT, 1.0F, 1.0F);
	    	MC.CreateMonster(message);

		  	GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.put(message, message);
		  	GBD_RPG.Main_Main.Main_ServerOption.MonsterList.put(message, new GBD_RPG.Monster.Monster_Object(message, message, 15, 20, 1, 10, 10, 10, 10, 10, 10, 0, 0, 0, 0));
		  	
	    	player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.YELLOW+message+ChatColor.GREEN+" 몬스터 생성 완료! (추가 설정을 해 주세요)");
			s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
			MGUI.MonsterOptionSettingGUI(player, message);
			u.clearAll(player);
	    	return;
		case "CN"://ChangeName
			message= event.getMessage().replace(".", "");
			GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.remove(u.getString(player, (byte)2)+Monster.getString(".Name"));
			Monster.set(u.getString(player, (byte)2)+".Name", message);
			Monster.saveConfig();
			
			GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setCustomName(message);
			GBD_RPG.Main_Main.Main_ServerOption.MonsterNameMatching.put(message, u.getString(player, (byte)2));
			
			s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
			MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
			u.clearAll(player);
	    	return;
		case "HP"://HealthPoint
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setHP(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "EXP":
		case "LUK":
		case "Magic_Protect" : //MagicProtect
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				if(u.getString(player, (byte)1).compareTo("EXP")==0)
					GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setEXP(Integer.parseInt(message));
				else if(u.getString(player, (byte)1).compareTo("LUK")==0)
					GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setLUK(Integer.parseInt(message));
				else
					GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setMPRO(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "MAX_Money"://Maximum Drop Money
			if(isIntMinMax(message, player, u.getInt(player, (byte)1), Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setMaxMoney(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "MIN_Money"://Minimum Drop Money
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				u.setInt(player, (byte)1, Integer.parseInt(message));
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setMinMoney(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 해당 몬스터가 드랍하는 최대 골드량을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"("+message+" ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MAX_Money");
			}
			return;
		case "STR"://Strength
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setSTR(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GBD_RPG.Main_Main.Main_ServerOption.DEX+"는 몬스터의 원거리 공격력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+GBD_RPG.Main_Main.Main_ServerOption.DEX+"를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "DEX");
			}
			return;
		case "DEX"://DEX
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setDEX(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GBD_RPG.Main_Main.Main_ServerOption.INT+"은 몬스터의 폭발 공격력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+GBD_RPG.Main_Main.Main_ServerOption.INT+"을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "INT");
			}
			return;
		case "INT"://INT
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setINT(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GBD_RPG.Main_Main.Main_ServerOption.WILL+"는 몬스터의 크리티컬 확률을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+GBD_RPG.Main_Main.Main_ServerOption.WILL+"를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "WILL");
			}
			return;
		case "WILL"://WILL
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setWILL(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"("+GBD_RPG.Main_Main.Main_ServerOption.LUK+"은 몬스터의 크리티컬 확률을 크게 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+GBD_RPG.Main_Main.Main_ServerOption.LUK+"을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "LUK");
			}
			return;
		case "DEF"://Defense
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setDEF(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(보호는 몬스터의 물리 저항력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 보호를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Protect");
			}
			return;
		case "Protect"://Protect
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setPRO(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(마법 방어는 몬스터의 마법 방어력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 마법 방어를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Magic_DEF");
			}
			return;
		case "Magic_DEF"://MagicDefense
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				GBD_RPG.Main_Main.Main_ServerOption.MonsterList.get(u.getString(player, (byte)2)).setMDEF(Integer.parseInt(message));
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(마법 보호는 몬스터의 마법 저항력을 상승시켜 줍니다.)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 마법 보호를 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Magic_Protect");
			}
			return;
		case "Head.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 갑옷 드랍률을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
				u.setString(player, (byte)1, "Chest.DropChance");
			}
			return;
		case "Chest.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 바지 드랍률을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
				u.setString(player, (byte)1, "Leggings.DropChance");
			}
			return;
		case "Leggings.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 부츠 드랍률을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
				u.setString(player, (byte)1, "Boots.DropChance");
			}
			return;
		case "Boots.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 무기 드랍률을 설정해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
				u.setString(player, (byte)1, "Hand.DropChance");
			}
			return;
		case "Hand.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				Monster.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				Monster.saveConfig();
				s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
				MGUI.MonsterOptionSettingGUI(player, u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		}
		return;
	}
	
}
