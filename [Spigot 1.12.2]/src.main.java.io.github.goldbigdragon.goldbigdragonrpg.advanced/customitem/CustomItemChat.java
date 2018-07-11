package customitem;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import customitem.gui.EquipItemForgingGui;
import customitem.gui.UseableItemMakingGui;
import effect.SoundEffect;
import user.UserDataObject;
import util.ChatUtil;
import util.YamlLoader;

public class CustomItemChat extends ChatUtil
{
	public void customItemChat(AsyncPlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();
		admin.UpgradeGui upgradeGui = new admin.UpgradeGui();
		

	  	YamlLoader upgradeYaml = new YamlLoader();
		upgradeYaml.getConfig("Item/Upgrade.yml");
	  	YamlLoader itemYaml = new YamlLoader();
	  	itemYaml.getConfig("Item/ItemList.yml");
		if(u.getType(player).equals("UseableItem")||u.getType(player).equals("UseableItem"))
			itemYaml.getConfig("Item/Consume.yml");
		event.setCancelled(true);
		int number = 0;
		String message = ChatColor.stripColor(event.getMessage());
		if(u.getInt(player, (byte)3)!=-1)
			number = u.getInt(player, (byte)3);
		if(itemYaml.getString(number+"")==null&&!u.getType(player).equals("Upgrade"))
		{
			player.sendMessage("§c[SYSTEM] : 다른 OP가 아이템을 삭제하여 반영되지 않았습니다!");
			return;
		}
		String sayType = u.getString(player, (byte)1);
		if(sayType.equals("DisplayName") || sayType.equals("Lore"))
			itemYaml.set(number+"."+sayType,event.getMessage());
		else if(sayType.equals("ID"))
		{
			if(isIntMinMax(message, player, 1, Integer.MAX_VALUE))
			{
				event.EventInteract interact = new event.EventInteract();
				if(interact.setItemDefaultName(Integer.parseInt(message), 0).equals("지정되지 않은 아이템"))
				{
					player.sendMessage("§c[SYSTEM] : 해당 아이템은 존재하지 않습니다!");
	  				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  				return;
				}
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
			}
		}
		else if(sayType.equals("MinDamage"))
		{
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "MaxDamage");
				player.sendMessage("§3[아이템] : 아이템의 최대 "+main.MainServerOption.damage+"를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
			}
			return;
		}
		else if(sayType.equals("MinMaDamage"))
		{
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "MaxMaDamage");
				player.sendMessage("§3[아이템] : 아이템의 최대 "+main.MainServerOption.magicDamage+"를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
			}
			return;
		}
		else if(sayType.equals("MaxDurability"))
		{
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "Durability");
				player.sendMessage("§3[아이템] : 아이템의 현재 내구도를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+itemYaml.getInt(number+".MaxDurability")+")");
			}
			return;
		}
		else if(sayType.equals("HP"))
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
				itemYaml.saveConfig();
				if(u.getInt(player, (byte)4) != -1)
				{
					if(u.getInt(player, (byte)4) == -8)
					{
						new UseableItemMakingGui().useableItemMakingGui(player, number);
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
		else if(sayType.equals("MP"))
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
				itemYaml.saveConfig();
				if(u.getInt(player, (byte)4) != -1)
				{
					if(u.getInt(player, (byte)4) == -8)
					{
						new UseableItemMakingGui().useableItemMakingGui(player, number);
						u.clearAll(player);
						return;
					}
					else
					{
						u.setType(player, u.getType(player));
						u.setString(player, (byte)1, "STR");
						player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.MainServerOption.statSTR+"을 입력해 주세요!");
						player.sendMessage("§3(-127 ~ 127)");
						return;
					}
				}
				else
				{
					u.setType(player, u.getType(player));
					u.setString(player, (byte)1, "STR");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.MainServerOption.statSTR+"을 입력해 주세요!");
					player.sendMessage("§3(-127 ~ 127)");
					return;
				}
			}
			return;
		}
		else if(sayType.equals("STR")||sayType.equals("DEX")||sayType.equals("INT")||sayType.equals("WILL")||
				sayType.equals("LUK")||sayType.equals("Balance"))
		{
			if(isIntMinMax(message, player, -127, 127))
			{
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				if(sayType.equals("STR"))
				{
					u.setString(player, (byte)1, "DEX");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.MainServerOption.statDEX+"를 입력해 주세요!");
				}
				else if(sayType.equals("DEX"))
				{
					u.setString(player, (byte)1, "INT");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.MainServerOption.statINT+"을 입력해 주세요!");
				}
				else if(sayType.equals("INT"))
				{
					u.setString(player, (byte)1, "WILL");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.MainServerOption.statWILL+"를 입력해 주세요!");
				}
				else if(sayType.equals("WILL"))
				{
					u.setString(player, (byte)1, "LUK");
					player.sendMessage("§3[아이템] : 아이템의 보너스 "+main.MainServerOption.statLUK+"을 입력해 주세요!");
				}
				else if(sayType.equals("LUK"))
				{
					u.setString(player, (byte)1, "Balance");
					player.sendMessage("§3[아이템] : 아이템의 밸런스를 입력해 주세요!");
				}
				else if(sayType.equals("Balance"))
				{
					u.setString(player, (byte)1, "Critical");
					player.sendMessage("§3[아이템] : 아이템의 크리티컬을 입력해 주세요!");
				}
				player.sendMessage("§3(-127 ~ 127)");
			}
			return;
		}
		else if(sayType.equals("Critical"))
		{
			if(isIntMinMax(message, player, -127, 127))
			{
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
				itemYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				if(u.getType(player).equals("UseableItem"))
				{
					new UseableItemMakingGui().useableItemMakingGui(player, number);
				}
				else
					new EquipItemForgingGui().itemForgingGui(player, number);
				u.clearAll(player);
			}
			return;
		}
		else if(sayType.equals("Saturation") ||sayType.equals("SkillPoint") ||sayType.equals("StatPoint") ||
				sayType.equals("Data") ||sayType.equals("DEF") ||sayType.equals("Protect") ||sayType.equals("MaDEF") ||
				sayType.equals("MaProtect") ||sayType.equals("MaxUpgrade") ||sayType.equals("MaxDamage") ||sayType.equals("MaxMaDamage") ||
				sayType.equals("Durability"))
		{
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
				itemYaml.set(number+"."+sayType,Integer.parseInt(message));
		}
		else if(sayType.equals("NUR"))//NewUpgradeRecipe
		{
			message = message.replace(".", "");
			if(upgradeYaml.contains(message)==true)
			{
				player.sendMessage("§c[개조] : 해당 이름의 개조식은 이미 존재합니다!");
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				return;
			}
			upgradeYaml.set(message+".Lore", "§f무기의 날을 다듬는 개조식이다.%enter%§f날을 다듬은 무기는 내구성이%enter%§f떨어지지만, 위협적이다.");
			upgradeYaml.set(message+".Only","§c[근접 무기]");
			upgradeYaml.set(message+".MaxDurability", -50);
			upgradeYaml.set(message+".MinDamage", 1);
			upgradeYaml.set(message+".MaxDamage", 8);
			upgradeYaml.set(message+".MinMaDamage", 0);
			upgradeYaml.set(message+".MaxMaDamage", 0);
			upgradeYaml.set(message+".DEF", 0);
			upgradeYaml.set(message+".Protect", 0);
			upgradeYaml.set(message+".MaDEF", 0);
			upgradeYaml.set(message+".MaProtect", 0);
			upgradeYaml.set(message+".Critical", 2);
			upgradeYaml.set(message+".Balance", 0);
			upgradeYaml.set(message+".UpgradeAbleLevel", 0);
			upgradeYaml.set(message+".DecreaseProficiency",30);
			upgradeYaml.saveConfig();
			SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.8F);
			upgradeGui.upgradeRecipeSettingGui(player, message);
			u.clearAll(player);
			return;
		}
		else if(sayType.equals("UMinD"))//UpgradeMinDamage
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MinDamage", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "UMaxD");
				player.sendMessage("§3[개조] : 변화될 최대 공격력 수치를 입력하세요!");
				player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
			}
			return;
		}
		else if(sayType.equals("UMaxD"))//UpgradeMaxDamage
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaxDamage", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UMMinD"))//UpgradeMagicMinDamage
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MinMaDamage", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				u.setType(player, u.getType(player));
				u.setString(player, (byte)1, "UMMaxD");
				player.sendMessage("§3[개조] : 변화될 최대 마법 공격력 수치를 입력하세요!");
				player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
			}
			return;
		}
		else if(sayType.equals("UMMaxD"))//UpgradeMagicMaxDamage
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaxMaDamage", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UB"))//UpgradeBalance
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".Balance", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UDEF"))//UpgradeDefense
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".DEF", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UP"))//UpgradeProtect
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".Protect", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UMDEF"))//UpgradeMagicDefense
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaDEF", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UMP"))//UpgradeMagicProtect
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaProtect", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UC"))//UpgradeCritical
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".Critical", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UMD"))//UpgradeMaxDurability
		{
			if(isIntMinMax(message, player, Integer.MIN_VALUE, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".MaxDurability", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UUL"))//UpgradeUpgradeLevel
		{
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".UpgradeAbleLevel", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("UDP"))//UpgradeDecreaseProficiency
		{
			if(isIntMinMax(message, player, 0, 100))
			{
				upgradeYaml.set(u.getString(player, (byte)6)+".DecreaseProficiency", Integer.parseInt(message));
				upgradeYaml.saveConfig();
				upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
				u.clearAll(player);
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			}
			return;
		}
		else if(sayType.equals("ULC"))//Upgrade Lore Change
		{
			upgradeYaml.set(u.getString(player, (byte)6)+".Lore", event.getMessage());
			upgradeYaml.saveConfig();
			upgradeGui.upgradeRecipeSettingGui(player, u.getString(player, (byte)6));
			u.clearAll(player);
			SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
			return;
		}
		else if(sayType.equals("MinSTR")||sayType.equals("MinDEX")||sayType.equals("MinINT")||sayType.equals("MinWILL")||
				sayType.equals("MinLV")||sayType.equals("MinRLV")||sayType.equals("MinLUK"))
		{
			if(isIntMinMax(message, player, 0, Integer.MAX_VALUE))
			{
				itemYaml.set(number+"."+sayType, Integer.parseInt(message));
				itemYaml.saveConfig();
				u.setType(player, u.getType(player));
				if(sayType.equals("MinLV"))
				{
					u.setString(player, (byte)1, "MinRLV");
					player.sendMessage("§3[아이템] : 아이템의 누적레벨 제한을 입력 해 주세요!");
				}
				else if(sayType.equals("MinRLV"))
				{
					u.setString(player, (byte)1, "MinSTR");
					player.sendMessage("§3[아이템] : 아이템의 "+main.MainServerOption.statSTR+" 제한을 입력 해 주세요!");
				}
				else if(sayType.equals("MinSTR"))
				{
					u.setString(player, (byte)1, "MinDEX");
					player.sendMessage("§3[아이템] : 아이템의 "+main.MainServerOption.statDEX+" 제한을 입력 해 주세요!");
				}
				else if(sayType.equals("MinDEX"))
				{
					u.setString(player, (byte)1, "MinINT");
					player.sendMessage("§3[아이템] : 아이템의 "+main.MainServerOption.statINT+" 제한을 입력 해 주세요!");
				}
				else if(sayType.equals("MinINT"))
				{
					u.setString(player, (byte)1, "MinWILL");
					player.sendMessage("§3[아이템] : 아이템의 "+main.MainServerOption.statWILL+" 제한을 입력 해 주세요!");
				}
				else if(sayType.equals("MinWILL"))
				{
					u.setString(player, (byte)1, "MinLUK");
					player.sendMessage("§3[아이템] : 아이템의 "+main.MainServerOption.statLUK+" 제한을 입력 해 주세요!");
				}
				if(sayType.equals("MinLUK"))
				{
					SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
					new EquipItemForgingGui().itemForgingGui(player, number);
					u.clearAll(player);
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				}
			}
			return;
		}
		itemYaml.saveConfig();
		SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
		if(u.getType(player).equals("UseableItem"))
			new UseableItemMakingGui().useableItemMakingGui(player, number);
		else
			new EquipItemForgingGui().itemForgingGui(player, number);
		u.clearAll(player);
		return;
	}
}
