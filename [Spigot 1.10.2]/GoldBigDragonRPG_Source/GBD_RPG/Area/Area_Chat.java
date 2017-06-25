package GBD_RPG.Area;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_Chat;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Area_Chat extends Util_Chat
{
	public void AreaTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = event.getPlayer();
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		event.setCancelled(true);
		GBD_RPG.Area.Area_GUI AGUI = new GBD_RPG.Area.Area_GUI();
		String Message = ChatColor.stripColor(event.getMessage());
		String subType = u.getString(player, (byte)2);
		if(subType.compareTo("ARR")==0)//AreaRegenBlock
		{
			if(isIntMinMax(Message, player, 1, 3600))
			{
				AreaConfig.set(u.getString(player, (byte)3)+".RegenBlock", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				AGUI.AreaSettingGUI(player, u.getString(player, (byte)3));
    			u.clearAll(player);
			}
		}
		else if(subType.compareTo("AMSC")==0)//AreaMonsterSpawnCount
		{
			if(isIntMinMax(Message, player, 1, 100))
			{
				AreaConfig.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".count", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				u.setString(player, (byte)2, "AMSMC");
				player.sendMessage(ChatColor.GREEN+"[영역] : 반경 20블록 이내 엔티티가 몇 마리 미만일 동안 스폰 할까요?");
				player.sendMessage(ChatColor.YELLOW+"(최소 1마리 ~ 최대 300마리)");
			}
		}
		else if(subType.compareTo("AMSMC")==0)//AreaMonsterSpawnMaximumCount
		{
			if(isIntMinMax(Message, player, 1, 300))
			{
				AreaConfig.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".max", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				u.setString(player, (byte)2, "AMST");
				player.sendMessage(ChatColor.GREEN+"[영역] : 몇 초마다 스폰되게 할까요?");
				player.sendMessage(ChatColor.YELLOW+"(최소 1초 ~ 최대 7200초(2시간))");
			}
		}
		else if(subType.compareTo("AMST")==0)//AreaMonsterSpawnTimer
		{
			if(isIntMinMax(Message, player, 1, 7200))
			{
				AreaConfig.set(u.getString(player, (byte)3)+".MonsterSpawnRule."+u.getString(player, (byte)1)+".timer", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			u.setString(player, (byte)2, "AMSM");
				player.sendMessage(ChatColor.GREEN+"[영역] : 특별히 스폰 하고 싶은 몬스터가 있나요?");
				player.sendMessage(ChatColor.YELLOW+"(O 혹은 X로 대답하세요!)");
			}
		}
		else if(subType.compareTo("AMSM")==0)//AreaMonsterSpawnMonster
		{
			byte answer = askOX(Message, player);
			if(answer!=-1)
			{
				if(answer==0)
				{
	    			s.SP(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
	    			AGUI.AreaMonsterSpawnSettingGUI(player, (short) 0, u.getString(player, (byte)3));
	    			String AreaName =u.getString(player, (byte)3);
	    			new GBD_RPG.Area.Area_Main().AreaMonsterSpawnAdd(AreaName, u.getString(player, (byte)1));
				}
				else
				{
					s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.7F);
					AGUI.AreaSpawnSpecialMonsterListGUI(player, (short) 0, u.getString(player, (byte)3),u.getString(player, (byte)1));
				}
    			u.clearAll(player);
			}
		}
		else if(subType.compareTo("Priority")==0)//영역 우선순위 설정
		{
			if(isIntMinMax(Message, player, 0, 100))
			{
    			AreaConfig.set(u.getString(player, (byte)3)+".Priority", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			AGUI.AreaSettingGUI(player, u.getString(player, (byte)3));
    			u.clearAll(player);
			}
		}
		else if(subType.compareTo("MinNLR")==0)//MinNowLevelRestrict
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
    			AreaConfig.set(u.getString(player, (byte)3)+".Restrict.MinNowLevel", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			if(Integer.parseInt(Message) == 0)
    			{
    				AreaConfig = YC.getNewConfig("config.yml");
        			if(AreaConfig.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
        			{
            			u.setString(player, (byte)2, "MinRLR");
        				player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+u.getString(player, (byte)3)+ChatColor.GREEN+" 영역의 입장에 필요한 최소 누적 레벨을 입력 하세요!"+ChatColor.GRAY + " (0 입력시 제한 없음)");
        			}
        			else
        			{
            			AreaConfig.set(u.getString(player, (byte)3)+".Restrict.MaxNowLevel", 0);
            			AreaConfig.saveConfig();
            			AGUI.AreaSettingGUI(player, u.getString(player, (byte)3));
            			u.clearAll(player);
        			}
    			}
    			else
    			{
        			u.setString(player, (byte)2, "MaxNLR");
    				player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+u.getString(player, (byte)3)+ChatColor.GREEN+" 영역의 입장에 필요한 최대 레벨을 입력 하세요!"+ChatColor.GRAY + " ("+Message+" 이상)");
    			}
			}
		}
		else if(subType.compareTo("MaxNLR")==0)//MaxNowLevelRestrict
		{
			if(isIntMinMax(Message, player, AreaConfig.getInt(u.getString(player, (byte)3)+".Restrict.MinNowLevel"), Integer.MAX_VALUE))
			{
    			AreaConfig.set(u.getString(player, (byte)3)+".Restrict.MaxNowLevel", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			AreaConfig =YC.getNewConfig("config.yml");
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			if(AreaConfig.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
    			{
        			u.setString(player, (byte)2, "MinRLR");
    				player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+u.getString(player, (byte)3)+ChatColor.GREEN+" 영역의 입장에 필요한 최소 누적 레벨을 입력 하세요!"+ChatColor.GRAY + " (0 입력시 제한 없음)");
    			}
    			else
    			{
        			AGUI.AreaSettingGUI(player, u.getString(player, (byte)3));
        			u.clearAll(player);
    			}
			}
		}
		else if(subType.compareTo("MinRLR")==0)//MinRealLevelRestrict
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
    			AreaConfig.set(u.getString(player, (byte)3)+".Restrict.MinRealLevel", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			if(Integer.parseInt(Message) == 0)
    			{
        			AreaConfig.set(u.getString(player, (byte)3)+".Restrict.MaxRealLevel", 0);
        			AreaConfig.saveConfig();
        			AGUI.AreaSettingGUI(player, u.getString(player, (byte)3));
        			u.clearAll(player);
    			}
    			else
    			{
        			u.setString(player, (byte)2, "MaxRLR");
    				player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+u.getString(player, (byte)3)+ChatColor.GREEN+" 영역의 입장에 필요한 최대 누적 레벨을 입력 하세요!"+ChatColor.GRAY + " ("+Message+" 이상)");
    			}
			}
		}
		else if(subType.compareTo("MaxRLR")==0)//MaxRealLevelRestrict
		{
			if(isIntMinMax(Message, player, AreaConfig.getInt(u.getString(player, (byte)3)+".Restrict.MinRealLevel"), Integer.MAX_VALUE))
			{
    			AreaConfig.set(u.getString(player, (byte)3)+".Restrict.MaxRealLevel", Integer.parseInt(Message));
    			AreaConfig.saveConfig();
    			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
    			AGUI.AreaSettingGUI(player, u.getString(player, (byte)3));
    			u.clearAll(player);
			}
		}

	}

}
