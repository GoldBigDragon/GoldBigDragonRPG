package admin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import effect.SoundEffect;
import user.UserDataObject;
import util.ChatUtil;
import util.YamlLoader;

public class OPboxChat extends ChatUtil
{
	public void systemTypeChatting(AsyncPlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();

	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
		
	    
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
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_H":
						configYaml.set("Death.Spawn_Home.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_S_E");
						player.sendMessage("§a[부활] : 마지막 마을에서 부활할 경우, 몇 %의 §e경험치§a를 잃어버리도록 하겠습니까?");
						player.sendMessage("§7(최소 0 ~ 최대 100)");
						break;
					case "RO_T_H":
						configYaml.set("Death.Spawn_Here.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_T_E");
						player.sendMessage("§a[부활] : 제자리에서 부활할 경우, 몇 %의 §e경험치§a를 잃어버리도록 하겠습니까?");
						player.sendMessage("§7(최소 0 ~ 최대 100)");
						break;
					case "RO_H_H":
						configYaml.set("Death.Spawn_Help.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_H_E");
						player.sendMessage("§a[부활] : 도움을 받아 부활할 경우, 몇 %의 §e경험치§a를 잃어버리도록 하겠습니까?");
						player.sendMessage("§7(최소 0 ~ 최대 100)");
						break;
					case "RO_I_H":
						configYaml.set("Death.Spawn_Item.SetHealth", message+"%");
						u.setString(player, (byte)1, "RO_I_E");
						player.sendMessage("§a[부활] : 아이템을 사용하여 부활할 경우, 몇 %의 §e경험치§a를 잃어버리도록 하겠습니까?");
						player.sendMessage("§7(최소 0 ~ 최대 100)");
						break;
				}
				configYaml.saveConfig();
			}
			return;
		case "RO_S_E"://RespawnOption_SpawnPoint_EXP
		case "RO_T_E"://RespawnOption_There_EXP
		case "RO_H_E"://RespawnOption_Help_EXP
		case "RO_I_E"://RespawnOption_Item_EXP
			if(isIntMinMax(message, player, 0, 100))
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_E":
						configYaml.set("Death.Spawn_Home.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_S_M");
						player.sendMessage("§a[부활] : 마지막 마을에서 부활할 경우, 몇 %의 §e돈§a을 잃어버리도록 하겠습니까?");
						player.sendMessage("§7(최소 0 ~ 최대 100)");
						break;
					case "RO_T_E":
						configYaml.set("Death.Spawn_Here.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_T_M");
						player.sendMessage("§a[부활] : 제자리에서 부활할 경우, 몇 %의 §e돈§a을 잃어버리도록 하겠습니까?");
						player.sendMessage("§7(최소 0 ~ 최대 100)");
						break;
					case "RO_H_E":
						configYaml.set("Death.Spawn_Help.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_H_M");
						player.sendMessage("§a[부활] : 도움을 받아 부활할 경우, 몇 %의 §e돈§a을 잃어버리도록 하겠습니까?");
						player.sendMessage("§7(최소 0 ~ 최대 100)");
						break;
					case "RO_I_E":
						configYaml.set("Death.Spawn_Item.PenaltyEXP", message+"%");
						u.setString(player, (byte)1, "RO_I_M");
						player.sendMessage("§a[부활] : 아이템을 사용하여 부활할 경우, 몇 %의 §e돈§a을 잃어버리도록 하겠습니까?");
						player.sendMessage("§7(최소 0 ~ 최대 100)");
						break;
				}
				configYaml.saveConfig();
			}
			return;
		case "RO_S_M"://RespawnOption_SpawnPoint_Money
		case "RO_T_M"://RespawnOption_There_Money
		case "RO_H_M"://RespawnOption_Help_Money
		case "RO_I_M"://RespawnOption_Item_Money
			if(isIntMinMax(message, player, 0, 100))
			{
				SoundEffect.playSound(player, Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
				switch(u.getString(player, (byte)1))
				{
					case "RO_S_M":
						configYaml.set("Death.Spawn_Home.PenaltyMoney", message+"%");
						break;
					case "RO_T_M":
						configYaml.set("Death.Spawn_Here.PenaltyMoney", message+"%");
						break;
					case "RO_H_M":
						configYaml.set("Death.Spawn_Help.PenaltyMoney", message+"%");
						break;
					case "RO_I_M":
						configYaml.set("Death.Spawn_Item.PenaltyMoney", message+"%");
						break;
				}
				configYaml.saveConfig();
				u.clearAll(player);
				new admin.OPboxGui().opBoxGuiDeath(player);
			}
			return;
		case "CCP"://ChangeChatPrefix
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			configYaml.set("Server.ChatPrefix", event.getMessage());
			configYaml.saveConfig();
			u.clearAll(player);
			new admin.OPboxGui().opBoxGuiSetting(player);
			return;
		case "BMT"://BroadcastMessageTick
			if(isIntMinMax(message, player, 1, 3600))
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				configYaml.set("Server.BroadCastSecond", Integer.parseInt(message));
				configYaml.saveConfig();
				new admin.OPboxGui().opBoxGuiBroadCast(player, (byte) 0);
				u.clearAll(player);
			}
			return;
		case "NBM"://NewBroadcastMessage
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			YamlLoader broadcastYaml = new YamlLoader();
			broadcastYaml.getConfig("BroadCast.yml");
			broadcastYaml.set(u.getInt(player, (byte)0)+"", "§f"+event.getMessage());
			broadcastYaml.saveConfig();
			u.clearAll(player);
			new admin.OPboxGui().opBoxGuiBroadCast(player, (byte) 0);
			return;
		case "JM"://JoinMessage
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(message.equals("없음"))
				configYaml.set("Server.JoinMessage", null);
			else
				configYaml.set("Server.JoinMessage", "§f"+event.getMessage());
			configYaml.saveConfig();
			u.clearAll(player);
			new admin.OPboxGui().opBoxGuiSetting(player);
			return;
		case "QM"://QuitMessage
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(message.equals("없음"))
				configYaml.set("Server.QuitMessage", null);
			else
				configYaml.set("Server.QuitMessage", "§f"+event.getMessage());
			configYaml.saveConfig();
			u.clearAll(player);
			new admin.OPboxGui().opBoxGuiSetting(player);
			return;
		case "CSN"://ChangeStatName
			{
				message.replace(".", "");
				message.replace(":", "");
				message.replace(" ", "");
				String Message = event.getMessage();
				Message.replace(".", "");
				Message.replace(":", "");
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				switch(u.getString(player, (byte)2))
				{
				case "체력":
					configYaml.set("Server.STR", message);
					break;
				case "체력 설명":
					configYaml.set("Server.STR_Lore", Message);
					break;
				case "솜씨":
					configYaml.set("Server.DEX", message);
					break;
				case "솜씨 설명":
					configYaml.set("Server.DEX_Lore", Message);
					break;
				case "지력":
					configYaml.set("Server.INT", message);
					break;
				case "지력 설명":
					configYaml.set("Server.INT_Lore", Message);
					break;
				case "의지":
					configYaml.set("Server.WILL", message);
					break;
				case "의지 설명":
					configYaml.set("Server.WILL_Lore", Message);
					break;
				case "행운":
					configYaml.set("Server.LUK", message);
					break;
				case "행운 설명":
					configYaml.set("Server.LUK_Lore", Message);
					break;
				case "대미지":
					configYaml.set("Server.Damage", message);
					break;
				case "마법 대미지":
					configYaml.set("Server.MagicDamage", message);
					break;
				case "화폐":
					String pa = event.getMessage();
					pa.replace(".", "");
					pa.replace(":", "");
					pa.replace(" ", "");
					configYaml.set("Server.MoneyName", pa);
					configYaml.saveConfig();
					u.clearAll(player);
					main.MainServerOption.money = pa;
					new admin.OPboxGui().opBoxGUIMoney(player);
					return;
				}
				configYaml.saveConfig();
				u.clearAll(player);
				player.sendMessage("§a[System] : 변경된 내용은 서버 안전을 위해, 서버 리로드 이후 일괄 적용됩니다.");
				new admin.OPboxGui().opBoxGuiStatChange(player);
			}
			return;
	
			case "CDML": //Change Drop Money Limit
			{
				if(isIntMinMax(message, player, 1000, 100000000))
				{
					int value = Integer.parseInt(message);
					main.MainServerOption.maxDropMoney = value;
					configYaml.set("Server.Max_Drop_Money",value);
					configYaml.saveConfig();
					u.clearAll(player);
					new admin.OPboxGui().opBoxGUIMoney(player);
				}
			}
			return;
			case "CMID": //Change Money ID
			{
				if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
				{
					if(new event.EventInteract().setItemDefaultName(Integer.parseInt(message), 0).equals("지정되지 않은 아이템"))
					{
						player.sendMessage("§c[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
		  				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		  				return;
					}
					int value = Integer.parseInt(message);
					switch(u.getInt(player, (byte)1))
					{
					case 50:
						configYaml.set("Server.Money.1.ID",value);
						main.MainServerOption.Money1ID = (short) value;
						break;
					case 100:
						configYaml.set("Server.Money.2.ID",value);
						main.MainServerOption.Money2ID = (short) value;
						break;
					case 1000:
						configYaml.set("Server.Money.3.ID",value);
						main.MainServerOption.Money3ID = (short) value;
						break;
					case 10000:
						configYaml.set("Server.Money.4.ID",value);
						main.MainServerOption.Money4ID = (short) value;
						break;
					case 50000:
						configYaml.set("Server.Money.5.ID",value);
						main.MainServerOption.Money5ID = (short) value;
						break;
					case -1:
						configYaml.set("Server.Money.6.ID",value);
						main.MainServerOption.Money6ID = (short) value;
						break;
					}
					configYaml.saveConfig();
					player.sendMessage("§3[System] : 화폐 모양으로 설정할 아이템 DATA를 입력 해 주세요!");
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
						configYaml.set("Server.Money.1.DATA",value);
						main.MainServerOption.Money1DATA = (byte) value;
						break;
					case 100:
						configYaml.set("Server.Money.2.DATA",value);
						main.MainServerOption.Money2DATA = (byte) value;
						break;
					case 1000:
						configYaml.set("Server.Money.3.DATA",value);
						main.MainServerOption.Money3DATA = (byte) value;
						break;
					case 10000:
						configYaml.set("Server.Money.4.DATA",value);
						main.MainServerOption.Money4DATA = (byte) value;
						break;
					case 50000:
						configYaml.set("Server.Money.5.DATA",value);
						main.MainServerOption.Money5DATA = (byte) value;
						break;
					case -1:
						configYaml.set("Server.Money.6.DATA",value);
						main.MainServerOption.Money6DATA = (byte) value;
						break;
					}
					configYaml.saveConfig();
					u.clearAll(player);
					new admin.OPboxGui().opBoxGUIMoney(player);
				}
			}
			return;
		}
		return;
	}

}
