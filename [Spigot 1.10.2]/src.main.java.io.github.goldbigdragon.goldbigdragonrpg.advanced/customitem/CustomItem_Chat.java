package customitem;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_Chat;
import util.YamlLoader;



public class CustomItem_Chat extends Util_Chat
{
	public void ItemTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();
		customitem.CustomItem_GUI IGUI = new customitem.CustomItem_GUI();
		admin.Upgrade_GUI UpGUI = new admin.Upgrade_GUI();
		

	  	YamlLoader upgradeYaml = new YamlLoader();
		upgradeYaml.getConfig("Item/Upgrade.yml");
	  	YamlLoader itemYaml = new YamlLoader();
	  	itemYaml.getConfig("Item/ItemList.yml");
		if(u.getType(player).equals("UseableItem")||u.getType(player).equals("UseableItem"))
			itemYaml.getConfig("Item/Consume.yml");
		event.setCancelled(true);
		int number = 0;
		String Message = ChatColor.stripColor(event.getMessage());
		if(u.getInt(player, (byte)3)!=-1)
			number = u.getInt(player, (byte)3);
		if(itemYaml.getString(number+"")==null&&!u.getType(player).equals("Upgrade"))
		{
			player.sendMessage("§c[SYSTEM] : 다른 OP가 아이템을 삭제하여 반영되지 않았습니다!");
			return;
		}
		String SayType = u.getString(player, (byte)1);
		if(SayType.equals("DisplayName") || SayType.equals("Lore"))
			itemYaml.set(number+"."+SayType,event.getMessage());
		else if(SayType.equals("ID"))
		{
			if(isIntMinMax(Message, player, 1, Integer.MAX_VALUE))
			{
				event.Main_Interact I = new event.Main_Interact();
				if(I.SetItemDefaultName(Short.parseShort(Message),(byte)0).equals("지정되지 않은 아이템"))
				{
					player.sendMessage("§c[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
	  				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
			}
		}
		else if(SayType.equals("MinDamage"))
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "MaxDamage");
				player.sendMessage("§3[아이템] : 아이템의 최대 "+main.Main_ServerOption.damage+"를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
			}
			return;
		}
		else if(SayType.equals("MinMaDamage"))
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "MaxMaDamage");
				player.sendMessage("§3[아이템] : 아이템의 최대 "+main.Main_ServerOption.magicDamage+"를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
			}
			return;
		}
		else if(SayType.equals("MaxDurability"))
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "Durability");
				player.sendMessage("§3[아이템] : 아이템의 현재 내구도를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+itemYaml.getInt(number+".MaxDurability")+")");
			}
			return;
		}
		else if(SayType.equals("HP"))
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
				itemYaml.saveConfig();
				if(u.getInt(player, (byte)4) != -1)
				{
					if(u.getInt(player, (byte)4) == -8)
					{
						customitem.UseableItem_GUI UGUI = new customitem.UseableItem_GUI();
						UGUI.NewUseableItemGUI(player, number);
						u.clearAll(player);
						return;
					}
					else
					{
						u.setType(player, u.getType(player));
						u.setString(player, (byte)1, "MP");
						player.sendMessage("§3[아이템] : 아이템의 보너스 마나를 입력해 주세요!");
						player.sendMessage("§3(-127 ~ 127)");
						return;
					}
				}
				else
				{
					u.setType(player, u.getType(player));
					u.setString(player, (byte)1, "MP");
					player.sendMessage("§3[아이템] : 아이템의 보너스 마나를 입력해 주세요!");
					player.sendMessage("§3(-127 ~ 127)");
				}
			}
			return;
		}
		else if(SayType.equals("MP"))
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
				itemYaml.saveConfig();
				if(u.getInt(player, (byte)4) != -1)
				{
					if(u.getInt(player, (byte)4) == -8)
					{
						customitem.UseableItem_GUI UGUI = new customitem.UseableItem_GUI();
						UGUI.NewUseableItemGUI(player, number);
						u.clearAll(player);
						return;
					}
					else
					{
						u.setType(player, u.getType(player));
						u.setString(player, (byte)1, "STR");
						player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.Main_ServerOption.statSTR+"을 입력해 주세요!");
						player.sendMessage("§3(-127 ~ 127)");
						return;
					}
				}
				else
				{
					u.setType(player, u.getType(player));
					u.setString(player, (byte)1, "STR");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.Main_ServerOption.statSTR+"을 입력해 주세요!");
					player.sendMessage("§3(-127 ~ 127)");
					return;
				}
			}
			return;
		}
		else if(SayType.equals("STR")||SayType.equals("DEX")||SayType.equals("INT")||SayType.equals("WILL")||
				SayType.equals("LUK")||SayType.equals("Balance"))
		{
			if(isIntMinMax(Message, player, -127, 127))
			{
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				if(SayType.equals("STR"))
				{
					u.setString(player, (byte)1, "DEX");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.Main_ServerOption.statDEX+"를 입력해 주세요!");
				}
				else if(SayType.equals("DEX"))
				{
					u.setString(player, (byte)1, "INT");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.Main_ServerOption.statINT+"을 입력해 주세요!");
				}
				else if(SayType.equals("INT"))
				{
					u.setString(player, (byte)1, "WILL");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.Main_ServerOption.statWILL+"를 입력해 주세요!");
				}
				else if(SayType.equals("WILL"))
				{
					u.setString(player, (byte)1, "LUK");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.Main_ServerOption.statLUK+"을 입력해 주세요!");
				}
				else if(SayType.equals("LUK"))
				{
					u.setString(player, (byte)1, "Balance");
					player.sendMessage("§3[아이템] : 아이템의 밸런스를 입력해 주세요!");
				}
				else if(SayType.equals("Balance"))
				{
					u.setString(player, (byte)1, "Critical");
					player.sendMessage("§3[아이템] : 아이템의 크리티컬을 입력해 주세요!");
				}
				player.sendMessage("§3(-127 ~ 127)");
			}
			return;
		}
		else if(SayType.equals("Critical"))
		{
			if(isIntMinMax(Message, player, -127, 127))
			{
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
				itemYaml.saveConfig();
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				if(u.getType(player).equals("UseableItem"))
				{
					customitem.UseableItem_GUI UGUI = new customitem.UseableItem_GUI();
					UGUI.NewUseableItemGUI(player, number);
				}
				else
					IGUI.NewItemGUI(player, number);
				u.clearAll(player);
			}
			return;
		}
		else if(SayType.equals("Saturation") ||SayType.equals("SkillPoint") ||SayType.equals("StatPoint") ||
				SayType.equals("Data") ||SayType.equals("DEF") ||SayType.equals("Protect") ||SayType.equals("MaDEF") ||
				SayType.equals("MaProtect") ||SayType.equals("MaxUpgrade") ||SayType.equals("MaxDamage") ||SayType.equals("MaxMaDamage") ||
				SayType.equals("Durability"))
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
				itemYaml.set(number+"."+SayType,Integer.parseInt(Message));
		}
		else if(SayType.equals("NUR"))//NewUpgradeRecipe
		{
			Message = Message.replace(".", "");
			if(upgradeYaml.contains(Message)==true)
			{
				player.sendMessage("§c[개조] : 해당 이름의 개조식은 이미 존재합니다!");
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				return;
			}
			upgradeYaml.set(Message+".Lore", "§f무기의 날을 다듬는 개조식이다.%enter%§f날을 다듬은 무기는 내구성이%enter%§f떨어지지만, 위협적이다.");
			upgradeYaml.set(Message+".Only","§c[근접 무기]");
			upgradeYaml.set(Message+".MaxDurability", -50);
			upgradeYaml.set(Message+".MinDamage", 1);
			upgradeYaml.set(Message+".MaxDamage", 8);
			upgradeYaml.set(Message+".MinMaDamage", 0);
			upgradeYaml.set(Message+".MaxMaDamage", 0);
			upgradeYaml.set(Message+".DEF", 0);
			upgradeYaml.set(Message+".Protect", 0);
			upgradeYaml.set(Message+".MaDEF", 0);
			upgradeYaml.set(Message+".MaProtect", 0);
			upgradeYaml.set(Message+".Critical", 2);
			upgradeYaml.set(Message+".Balance", 0);
			upgradeYaml.set(Message+".UpgradeAbleLevel", 0);
			upgradeYaml.set(Message+".DecreaseProficiency",30);
			upgradeYaml.saveConfig();
			SoundEffect.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
			UpGUI.upgradeRecipeSettingGui(player, Message);
			u.clearAll(player);
			return;
		}
		else if(SayType.equals("UMinD"))//UpgradeMinDamage
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MinDamage", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "UMaxD");
				player.sendMessage("§3[개조] : 변화될 최대 공격력 수치를 입력하세요!");
				player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
			}
			return;
		}
		else if(SayType.equals("UMaxD"))//UpgradeMaxDamage
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaxDamage", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UMMinD"))//UpgradeMagicMinDamage
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MinMaDamage", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "UMMaxD");
				player.sendMessage("§3[개조] : 변화될 최대 마법 공격력 수치를 입력하세요!");
				player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
			}
			return;
		}
		else if(SayType.equals("UMMaxD"))//UpgradeMagicMaxDamage
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaxMaDamage", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UB"))//UpgradeBalance
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".Balance", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UDEF"))//UpgradeDefense
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".DEF", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UP"))//UpgradeProtect
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".Protect", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UMDEF"))//UpgradeMagicDefense
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaDEF", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UMP"))//UpgradeMagicProtect
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaProtect", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UC"))//UpgradeCritical
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".Critical", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UMD"))//UpgradeMaxDurability
		{
			if(isIntMinMax(Message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaxDurability", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UUL"))//UpgradeUpgradeLevel
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".UpgradeAbleLevel", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("UDP"))//UpgradeDecreaseProficiency
		{
			if(isIntMinMax(Message, player, 0, 100))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".DecreaseProficiency", Integer.parseInt(Message));
				upgradeYaml.saveConfig();
				UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(SayType.equals("ULC"))//Upgrade Lore Change
		{
			upgradeYaml.set(u.getString(player, (byte)6)+".Lore", event.getMessage());
			upgradeYaml.saveConfig();
			UpGUI.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
			u.clearAll(player);
			SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			return;
		}
		else if(SayType.equals("MinSTR")||SayType.equals("MinDEX")||SayType.equals("MinINT")||SayType.equals("MinWILL")||
				SayType.equals("MinLV")||SayType.equals("MinRLV")||SayType.equals("MinLUK"))
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+SayType, Integer.parseInt(Message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				if(SayType.equals("MinLV"))
				{
					u.setString(player, (byte)1, "MinRLV");
					player.sendMessage("§3[아이템] : 아이템의 누적레벨 제한을 입력 해 주세요!");
				}
				else if(SayType.equals("MinRLV"))
				{
					u.setString(player, (byte)1, "MinSTR");
					player.sendMessage("§3[아이템] : 아이템의 "+main.Main_ServerOption.statSTR+" 제한을 입력 해 주세요!");
				}
				else if(SayType.equals("MinSTR"))
				{
					u.setString(player, (byte)1, "MinDEX");
					player.sendMessage("§3[아이템] : 아이템의 "+main.Main_ServerOption.statDEX+" 제한을 입력 해 주세요!");
				}
				else if(SayType.equals("MinDEX"))
				{
					u.setString(player, (byte)1, "MinINT");
					player.sendMessage("§3[아이템] : 아이템의 "+main.Main_ServerOption.statINT+" 제한을 입력 해 주세요!");
				}
				else if(SayType.equals("MinINT"))
				{
					u.setString(player, (byte)1, "MinWILL");
					player.sendMessage("§3[아이템] : 아이템의 "+main.Main_ServerOption.statWILL+" 제한을 입력 해 주세요!");
				}
				else if(SayType.equals("MinWILL"))
				{
					u.setString(player, (byte)1, "MinLUK");
					player.sendMessage("§3[아이템] : 아이템의 "+main.Main_ServerOption.statLUK+" 제한을 입력 해 주세요!");
				}
				if(SayType.equals("MinLUK"))
				{
					SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
					IGUI.NewItemGUI(player, number);
					u.clearAll(player);
				}
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				}
			}
			return;
		}
		itemYaml.saveConfig();
		SoundEffect.SP(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
		if(u.getType(player).equals("UseableItem"))
			new customitem.UseableItem_GUI().NewUseableItemGUI(player, number);
		else
			IGUI.NewItemGUI(player, number);
		u.clearAll(player);
		return;
	}

}
