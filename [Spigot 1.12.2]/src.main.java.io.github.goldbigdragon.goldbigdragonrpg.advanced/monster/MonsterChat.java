package monster;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import effect.SoundEffect;
import monster.gui.MonsterOptionSettingGui;
import monster.gui.MonsterPotionGui;
import user.UserDataObject;
import util.ChatUtil;
import util.YamlLoader;

public class MonsterChat extends ChatUtil
{
	public void monsterTypeChatting(AsyncPlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();
	  	YamlLoader monsterListYaml = new YamlLoader();
		monsterListYaml.getConfig("Monster/MonsterList.yml");
		String[] monsterlist = monsterListYaml.getKeys().toArray(new String[0]);
	    
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "Potion":
			if(isIntMinMax(message, player, 0, 100))
			{
				String monsterName = u.getString(player, (byte)3);
				switch(u.getString(player, (byte)2))
				{
				case "Regenerate":
					monsterListYaml.set(monsterName+".Potion.Regenerate", Integer.parseInt(message));break;
				case "Poision":
					monsterListYaml.set(monsterName+".Potion.Poison", Integer.parseInt(message));break;
				case "Speed":
					monsterListYaml.set(monsterName+".Potion.Speed", Integer.parseInt(message));break;
				case "Slow":
					monsterListYaml.set(monsterName+".Potion.Slow", Integer.parseInt(message));break;
				case "Strength":
					monsterListYaml.set(monsterName+".Potion.Strength", Integer.parseInt(message));break;
				case "Weak":
					monsterListYaml.set(monsterName+".Potion.Weak", Integer.parseInt(message));break;
				case "Jump":
					monsterListYaml.set(monsterName+".Potion.JumpBoost", Integer.parseInt(message));break;
				}
				monsterListYaml.saveConfig();
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_GENERIC_DRINK, 1.0F, 1.0F);
				new MonsterPotionGui().monsterPotionGui(player, monsterName);
			}
		return;
		case "NM"://NewMonster
			message = message.replace(".", "");
			for(int count = 0; count < monsterlist.length;count++)
	    	{
	    		if(monsterlist[count].equals(message))
	    		{
				  	SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			    	player.sendMessage("§c[SYSTEM] : 해당 이름의 몬스터는 이미 존재합니다!");
			    	return;
	    		}
	    	}
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_WOLF_AMBIENT, 1.0F, 1.0F);
			new monster.MonsterSpawn().createMonster(message);

		  	main.MainServerOption.MonsterNameMatching.put(message, message);
		  	main.MainServerOption.MonsterList.put(message, new monster.MonsterObject(message, message, 15, 20, 1, 10, 10, 10, 10, 10, 10, 0, 0, 0, 0));
		  	
	    	player.sendMessage("§a[SYSTEM] : §e"+message+"§a 몬스터 생성 완료! (추가 설정을 해 주세요)");
			SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
			new MonsterOptionSettingGui().monsterOptionSettingGui(player, message);
			u.clearAll(player);
	    	return;
		case "CN"://ChangeName
			message= event.getMessage().replace(".", "");
			main.MainServerOption.MonsterNameMatching.remove(u.getString(player, (byte)2)+monsterListYaml.getString(".Name"));
			monsterListYaml.set(u.getString(player, (byte)2)+".Name", message);
			monsterListYaml.saveConfig();
			
			main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setCustomName(message);
			main.MainServerOption.MonsterNameMatching.put(message, u.getString(player, (byte)2));
			
			SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
			new MonsterOptionSettingGui().monsterOptionSettingGui(player,  u.getString(player, (byte)2));
			u.clearAll(player);
	    	return;
		case "HP"://HealthPoint
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setHP(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
				new MonsterOptionSettingGui().monsterOptionSettingGui(player,  u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "EXP":
		case "LUK":
		case "Magic_Protect" : //MagicProtect
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				if(u.getString(player, (byte)1).equals("EXP"))
					main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setEXP(Integer.parseInt(message));
				else if(u.getString(player, (byte)1).equals("LUK"))
					main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setLUK(Integer.parseInt(message));
				else
					main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setMPRO(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
				new MonsterOptionSettingGui().monsterOptionSettingGui(player,  u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "MAX_Money"://Maximum Drop Money
			if(isIntMinMax(message, player, u.getInt(player, (byte)1), Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setMaxMoney(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
				new MonsterOptionSettingGui().monsterOptionSettingGui(player,  u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		case "MIN_Money"://Minimum Drop Money
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				u.setInt(player, (byte)1, Integer.parseInt(message));
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setMinMoney(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§a[몬스터] : 해당 몬스터가 드랍하는 최대 골드량을 설정해 주세요!");
				player.sendMessage("§3("+message+" ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MAX_Money");
			}
			return;
		case "STR"://Strength
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setSTR(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7("+main.MainServerOption.statDEX+"는 몬스터의 원거리 공격력을 상승시켜 줍니다.)");
				player.sendMessage("§a[몬스터] : 몬스터의 "+main.MainServerOption.statDEX+"를 설정해 주세요!");
				player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "DEX");
			}
			return;
		case "DEX"://DEX
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setDEX(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7("+main.MainServerOption.statINT+"은 몬스터의 폭발 공격력을 상승시켜 줍니다.)");
				player.sendMessage("§a[몬스터] : 몬스터의 "+main.MainServerOption.statINT+"을 설정해 주세요!");
				player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "INT");
			}
			return;
		case "INT"://INT
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setINT(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7("+main.MainServerOption.statWILL+"는 몬스터의 크리티컬 확률을 상승시켜 줍니다.)");
				player.sendMessage("§a[몬스터] : 몬스터의 "+main.MainServerOption.statWILL+"를 설정해 주세요!");
				player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "WILL");
			}
			return;
		case "WILL"://WILL
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setWILL(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7("+main.MainServerOption.statLUK+"은 몬스터의 크리티컬 확률을 크게 상승시켜 줍니다.)");
				player.sendMessage("§a[몬스터] : 몬스터의 "+main.MainServerOption.statLUK+"을 설정해 주세요!");
				player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "LUK");
			}
			return;
		case "DEF"://Defense
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setDEF(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7(보호는 몬스터의 물리 저항력을 상승시켜 줍니다.)");
				player.sendMessage("§a[몬스터] : 몬스터의 보호를 설정해 주세요!");
				player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Protect");
			}
			return;
		case "Protect"://Protect
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setPRO(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7(마법 방어는 몬스터의 마법 방어력을 상승시켜 줍니다.)");
				player.sendMessage("§a[몬스터] : 몬스터의 마법 방어를 설정해 주세요!");
				player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Magic_DEF");
			}
			return;
		case "Magic_DEF"://MagicDefense
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				main.MainServerOption.MonsterList.get(u.getString(player, (byte)2)).setMDEF(Integer.parseInt(message));
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7(마법 보호는 몬스터의 마법 저항력을 상승시켜 줍니다.)");
				player.sendMessage("§a[몬스터] : 몬스터의 마법 보호를 설정해 주세요!");
				player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Magic_Protect");
			}
			return;
		case "Head.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage("§a[몬스터] : 몬스터의 갑옷 드랍률을 설정해 주세요!");
				player.sendMessage("§3(0 ~ 1000)");
				u.setString(player, (byte)1, "Chest.DropChance");
			}
			return;
		case "Chest.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage("§a[몬스터] : 몬스터의 바지 드랍률을 설정해 주세요!");
				player.sendMessage("§3(0 ~ 1000)");
				u.setString(player, (byte)1, "Leggings.DropChance");
			}
			return;
		case "Leggings.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage("§a[몬스터] : 몬스터의 부츠 드랍률을 설정해 주세요!");
				player.sendMessage("§3(0 ~ 1000)");
				u.setString(player, (byte)1, "Boots.DropChance");
			}
			return;
		case "Boots.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				player.sendMessage("§7(확률 계산 : 1000 = 100%, 1 = 0.1%)");
				player.sendMessage("§a[몬스터] : 몬스터의 무기 드랍률을 설정해 주세요!");
				player.sendMessage("§3(0 ~ 1000)");
				u.setString(player, (byte)1, "Hand.DropChance");
			}
			return;
		case "Hand.DropChance"://DropChance
			if(isIntMinMax(message, player, 0, 1000))
			{
				monsterListYaml.set(u.getString(player, (byte)2)+"."+u.getString(player, (byte)1), Integer.parseInt(message));
				monsterListYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
				new MonsterOptionSettingGui().monsterOptionSettingGui(player,  u.getString(player, (byte)2));
				u.clearAll(player);
			}
			return;
		}
		return;
	}
}
