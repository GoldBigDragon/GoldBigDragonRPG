package GBD_RPG.Admin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_Chat;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class OPbox_Chat extends Util_Chat
{

	public void SystemTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();

	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		
	    GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "RO_S_H"://RespawnOption_SpawnPoint_Health
		case "RO_T_H"://RespawnOption_There_Health
		case "RO_H_H"://RespawnOption_Help_Health
		case "RO_I_H"://RespawnOption_Item_Health
			if(isIntMinMax(message, player, 1, 100))
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_H":
						Config.set("Death.Spawn_Home.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_S_E");
						player.sendMessage(ChatColor.GREEN+"[부활] : 마지막 마을에서 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"경험치"+ChatColor.GREEN+"를 잃어버리도록 하겠습니까?");
						player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
						break;
					case "RO_T_H":
						Config.set("Death.Spawn_Here.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_T_E");
						player.sendMessage(ChatColor.GREEN+"[부활] : 제자리에서 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"경험치"+ChatColor.GREEN+"를 잃어버리도록 하겠습니까?");
						player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
						break;
					case "RO_H_H":
						Config.set("Death.Spawn_Help.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_H_E");
						player.sendMessage(ChatColor.GREEN+"[부활] : 도움을 받아 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"경험치"+ChatColor.GREEN+"를 잃어버리도록 하겠습니까?");
						player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
						break;
					case "RO_I_H":
						Config.set("Death.Spawn_Item.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_I_E");
						player.sendMessage(ChatColor.GREEN+"[부활] : 아이템을 사용하여 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"경험치"+ChatColor.GREEN+"를 잃어버리도록 하겠습니까?");
						player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
						break;
				}
				Config.saveConfig();
			}
			return;
		case "RO_S_E"://RespawnOption_SpawnPoint_EXP
		case "RO_T_E"://RespawnOption_There_EXP
		case "RO_H_E"://RespawnOption_Help_EXP
		case "RO_I_E"://RespawnOption_Item_EXP
			if(isIntMinMax(message, player, 0, 100))
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_E":
						Config.set("Death.Spawn_Home.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_S_M");
						player.sendMessage(ChatColor.GREEN+"[부활] : 마지막 마을에서 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"돈"+ChatColor.GREEN+"을 잃어버리도록 하겠습니까?");
						player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
						break;
					case "RO_T_E":
						Config.set("Death.Spawn_Here.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_T_M");
						player.sendMessage(ChatColor.GREEN+"[부활] : 제자리에서 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"돈"+ChatColor.GREEN+"을 잃어버리도록 하겠습니까?");
						player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
						break;
					case "RO_H_E":
						Config.set("Death.Spawn_Help.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_H_M");
						player.sendMessage(ChatColor.GREEN+"[부활] : 도움을 받아 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"돈"+ChatColor.GREEN+"을 잃어버리도록 하겠습니까?");
						player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
						break;
					case "RO_I_E":
						Config.set("Death.Spawn_Item.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_I_M");
						player.sendMessage(ChatColor.GREEN+"[부활] : 아이템을 사용하여 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"돈"+ChatColor.GREEN+"을 잃어버리도록 하겠습니까?");
						player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
						break;
				}
				Config.saveConfig();
			}
			return;
		case "RO_S_M"://RespawnOption_SpawnPoint_Money
		case "RO_T_M"://RespawnOption_There_Money
		case "RO_H_M"://RespawnOption_Help_Money
		case "RO_I_M"://RespawnOption_Item_Money
			if(isIntMinMax(message, player, 0, 100))
			{
				s.SP(player, Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_M":
						Config.set("Death.Spawn_Home.PenaltyMoney", message+"%");
						break;
					case "RO_T_M":
						Config.set("Death.Spawn_Here.PenaltyMoney", message+"%");
						break;
					case "RO_H_M":
						Config.set("Death.Spawn_Help.PenaltyMoney", message+"%");
						break;
					case "RO_I_M":
						Config.set("Death.Spawn_Item.PenaltyMoney", message+"%");
						break;
				}
				Config.saveConfig();
				u.clearAll(player);
				new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Death(player);
			}
			return;
		case "CCP"://ChangeChatPrefix
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			Config.set("Server.ChatPrefix", event.getMessage());
			Config.saveConfig();
			u.clearAll(player);
			new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Setting(player);
			return;
		case "BMT"://BroadcastMessageTick
			if(isIntMinMax(message, player, 1, 3600))
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				Config.set("Server.BroadCastSecond", Integer.parseInt(message));
				Config.saveConfig();
				new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_BroadCast(player, (byte) 0);
				u.clearAll(player);
			}
			return;
		case "NBM"://NewBroadcastMessage
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			YamlManager BroadCast =YC.getNewConfig("BroadCast.yml");
			BroadCast.set(u.getInt(player, (byte)0)+"", ChatColor.WHITE+event.getMessage());
			BroadCast.saveConfig();
			u.clearAll(player);
			new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_BroadCast(player, (byte) 0);
			return;
		case "JM"://JoinMessage
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(message.compareTo("없음")==0)
				Config.set("Server.JoinMessage", null);
			else
				Config.set("Server.JoinMessage", ChatColor.WHITE+event.getMessage());
			Config.saveConfig();
			u.clearAll(player);
			new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Setting(player);
			return;
		case "QM"://QuitMessage
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(message.compareTo("없음")==0)
				Config.set("Server.QuitMessage", null);
			else
				Config.set("Server.QuitMessage", ChatColor.WHITE+event.getMessage());
			Config.saveConfig();
			u.clearAll(player);
			new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Setting(player);
			return;
		case "CSN"://ChangeStatName
			{
				message.replace(".", "");
				message.replace(":", "");
				message.replace(" ", "");
				String Message = event.getMessage();
				Message.replace(".", "");
				Message.replace(":", "");
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)2))
				{
				case "체력":
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
				case "대미지":
					Config.set("Server.Damage", message);
					break;
				case "마법 대미지":
					Config.set("Server.MagicDamage", message);
					break;
				case "화폐":
					String Pa = event.getMessage();
					Pa.replace(".", "");
					Pa.replace(":", "");
					Pa.replace(" ", "");
					Config.set("Server.MoneyName", Pa);
					Config.saveConfig();
					u.clearAll(player);
					GBD_RPG.Main_Main.Main_ServerOption.Money = Pa;
					new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Money(player);
					return;
				}
				Config.saveConfig();
				u.clearAll(player);
				player.sendMessage(ChatColor.GREEN + "[System] : 변경된 내용은 서버 안전을 위해, 서버 리로드 이후 일괄 적용됩니다.");
				new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_StatChange(player);
			}
			return;
	
			case "CDML": //Change Drop Money Limit
			{
				if(isIntMinMax(message, player, 1000, 100000000))
				{
					int value = Integer.parseInt(message);
					GBD_RPG.Main_Main.Main_ServerOption.MaxDropMoney = value;
					Config.set("Server.Max_Drop_Money",value);
					Config.saveConfig();
					u.clearAll(player);
					new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Money(player);
				}
			}
			return;
			case "CMID": //Change Money ID
			{
				if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
				{
					if(new GBD_RPG.Main_Event.Main_Interact().SetItemDefaultName(Short.parseShort(message),(byte)0).compareTo("지정되지 않은 아이템")==0)
					{
						player.sendMessage(ChatColor.RED + "[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
		  				s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  				return;
					}
					int value = Integer.parseInt(message);
					switch(u.getInt(player, (byte)1))
					{
					case 50:
						Config.set("Server.Money.1.ID",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money1ID = (short) value;
						break;
					case 100:
						Config.set("Server.Money.2.ID",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money2ID = (short) value;
						break;
					case 1000:
						Config.set("Server.Money.3.ID",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money3ID = (short) value;
						break;
					case 10000:
						Config.set("Server.Money.4.ID",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money4ID = (short) value;
						break;
					case 50000:
						Config.set("Server.Money.5.ID",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money5ID = (short) value;
						break;
					case -1:
						Config.set("Server.Money.6.ID",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money6ID = (short) value;
						break;
					}
					Config.saveConfig();
					player.sendMessage(ChatColor.DARK_AQUA+"[System] : 화폐 모양으로 설정할 아이템 DATA를 입력 해 주세요!");
					u.setString(player, (byte)1, "CMDATA");
				}
			}
			return;
			case "CMDATA": //Change Money DATA
			{
				if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
				{
					int value = Integer.parseInt(message);
					switch(u.getInt(player, (byte)1))
					{
					case 50:
						Config.set("Server.Money.1.DATA",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money1DATA = (byte) value;
						break;
					case 100:
						Config.set("Server.Money.2.DATA",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money2DATA = (byte) value;
						break;
					case 1000:
						Config.set("Server.Money.3.DATA",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money3DATA = (byte) value;
						break;
					case 10000:
						Config.set("Server.Money.4.DATA",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money4DATA = (byte) value;
						break;
					case 50000:
						Config.set("Server.Money.5.DATA",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money5DATA = (byte) value;
						break;
					case -1:
						Config.set("Server.Money.6.DATA",value);
						GBD_RPG.Main_Main.Main_ServerOption.Money6DATA = (byte) value;
						break;
					}
					Config.saveConfig();
					u.clearAll(player);
					new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Money(player);
				}
			}
			return;
		}
		return;
	}

}
