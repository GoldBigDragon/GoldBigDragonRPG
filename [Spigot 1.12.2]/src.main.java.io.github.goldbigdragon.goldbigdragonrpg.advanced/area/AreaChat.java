package area;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import area.gui.AreaMonsterSpawnSettingGui;
import area.gui.AreaSettingGui;
import area.gui.AreaSpawnSpecialMonsterListGui;
import effect.SoundEffect;
import user.UserDataObject;
import util.ChatUtil;
import util.YamlLoader;

public class AreaChat extends ChatUtil
{
	public void areaTypeChatting(AsyncPlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		event.setCancelled(true);
		String message = ChatColor.stripColor(event.getMessage());
		String subType = u.getString(player, (byte)2);
		if(subType.equals("ARR"))//AreaRegenBlock
		{
			if(isIntMinMax(message, player, 1, 3600))
			{
				areaYaml.set(u.getString(player, (byte)3)+".RegenBlock", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				new AreaSettingGui().areaSettingGui(player, u.getString(player, (byte)3));
    			u.clearAll(player);
			}
		}
		else if(subType.equals("AMSC"))//AreaMonsterSpawnCount
		{
			if(isIntMinMax(message, player, 1, 100))
			{
				areaYaml.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".count", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				u.setString(player, (byte)2, "AMSMC");
				player.sendMessage("§a[영역] : 반경 20블록 이내 엔티티가 몇 마리 미만일 동안 스폰 할까요?");
				player.sendMessage("§e(최소 1마리 ~ 최대 300마리)");
			}
		}
		else if(subType.equals("AMSMC"))//AreaMonsterSpawnMaximumCount
		{
			if(isIntMinMax(message, player, 1, 300))
			{
				areaYaml.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".max", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				u.setString(player, (byte)2, "AMST");
				player.sendMessage("§a[영역] : 몇 초마다 스폰되게 할까요?");
				player.sendMessage("§e(최소 1초 ~ 최대 7200초(2시간))");
			}
		}
		else if(subType.equals("AMST"))//AreaMonsterSpawnTimer
		{
			if(isIntMinMax(message, player, 1, 7200))
			{
				areaYaml.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".timer", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			u.setString(player, (byte)2, "AMSM");
				player.sendMessage("§a[영역] : 특별히 스폰 하고 싶은 몬스터가 있나요?");
				player.sendMessage("§e(O 혹은 X로 대답하세요!)");
			}
		}
		else if(subType.equals("AMSM"))//AreaMonsterSpawnMonster
		{
			byte answer = askOX(message, player);
			if(answer!=-1)
			{
				if(answer==0)
				{
	    			SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
	    			new AreaMonsterSpawnSettingGui().areaMonsterSpawnSettingGui(player, 0, u.getString(player, (byte)3));
	    			String areaName =u.getString(player, (byte)3);
	    			new area.AreaAPI().areaMonsterSpawnAdd(areaName, u.getString(player, (byte)1));
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.7F);
					new AreaSpawnSpecialMonsterListGui().areaSpawnSpecialMonsterListGui(player, 0, u.getString(player, (byte)3), u.getString(player, (byte)1));
				}
    			u.clearAll(player);
			}
		}
		else if(subType.equals("Priority"))//영역 우선순위 설정
		{
			if(isIntMinMax(message, player, 0, 100))
			{
    			areaYaml.set(u.getString(player, (byte)3)+".Priority", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				new AreaSettingGui().areaSettingGui(player, u.getString(player, (byte)3));
    			u.clearAll(player);
			}
		}
		else if(subType.equals("MinNLR"))//MinNowLevelRestrict
		{
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
    			areaYaml.set(u.getString(player, (byte)3)+".Restrict.MinNowLevel", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			if(Integer.parseInt(message) == 0)
    			{
    				YamlLoader configYaml = new YamlLoader();
    				configYaml.getConfig("config.yml");
        			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
        			{
            			u.setString(player, (byte)2, "MinRLR");
        				player.sendMessage("§a[영역] : §e"+u.getString(player, (byte)3)+"§a 영역의 입장에 필요한 최소 누적 레벨을 입력 하세요!§7 (0 입력시 제한 없음)");
        			}
        			else
        			{
            			areaYaml.set(u.getString(player, (byte)3)+".Restrict.MaxNowLevel", 0);
            			areaYaml.saveConfig();
        				new AreaSettingGui().areaSettingGui(player, u.getString(player, (byte)3));
            			u.clearAll(player);
        			}
    			}
    			else
    			{
        			u.setString(player, (byte)2, "MaxNLR");
    				player.sendMessage("§a[영역] : §e"+u.getString(player, (byte)3)+"§a 영역의 입장에 필요한 최대 레벨을 입력 하세요!§7 ("+message+" 이상)");
    			}
			}
		}
		else if(subType.equals("MaxNLR"))//MaxNowLevelRestrict
		{
			if(isIntMinMax(message, player, areaYaml.getInt(u.getString(player, (byte)3)+".Restrict.MinNowLevel"), Integer.MAX_VALUE))
			{
    			areaYaml.set(u.getString(player, (byte)3)+".Restrict.MaxNowLevel", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			YamlLoader configYaml = new YamlLoader();
    			configYaml.getConfig("config.yml");
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
    			{
        			u.setString(player, (byte)2, "MinRLR");
    				player.sendMessage("§a[영역] : §e"+u.getString(player, (byte)3)+"§a 영역의 입장에 필요한 최소 누적 레벨을 입력 하세요!§7 (0 입력시 제한 없음)");
    			}
    			else
    			{
    				new AreaSettingGui().areaSettingGui(player, u.getString(player, (byte)3));
        			u.clearAll(player);
    			}
			}
		}
		else if(subType.equals("MinRLR"))//MinRealLevelRestrict
		{
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
    			areaYaml.set(u.getString(player, (byte)3)+".Restrict.MinRealLevel", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			if(Integer.parseInt(message) == 0)
    			{
        			areaYaml.set(u.getString(player, (byte)3)+".Restrict.MaxRealLevel", 0);
        			areaYaml.saveConfig();
    				new AreaSettingGui().areaSettingGui(player, u.getString(player, (byte)3));
        			u.clearAll(player);
    			}
    			else
    			{
        			u.setString(player, (byte)2, "MaxRLR");
    				player.sendMessage("§a[영역] : §e"+u.getString(player, (byte)3)+"§a 영역의 입장에 필요한 최대 누적 레벨을 입력 하세요!§7 ("+message+" 이상)");
    			}
			}
		}
		else if(subType.equals("MaxRLR"))//MaxRealLevelRestrict
		{
			if(isIntMinMax(message, player, areaYaml.getInt(u.getString(player, (byte)3)+".Restrict.MinRealLevel"), Integer.MAX_VALUE))
			{
    			areaYaml.set(u.getString(player, (byte)3)+".Restrict.MaxRealLevel", Integer.parseInt(message));
    			areaYaml.saveConfig();
    			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				new AreaSettingGui().areaSettingGui(player, u.getString(player, (byte)3));
    			u.clearAll(player);
			}
		}

	}

}
