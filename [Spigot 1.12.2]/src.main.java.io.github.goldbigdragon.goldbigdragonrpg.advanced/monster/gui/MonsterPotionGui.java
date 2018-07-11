package monster.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class MonsterPotionGui extends GuiUtil{
	
	private String uniqueCode = "§0§0§8§0§2§r";

	public void monsterPotionGui(Player player, String monsterName)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		monsterListYaml.getConfig("Monster/MonsterList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0몬스터 포션");
		
		removeFlagStack("§3[  재생  ]", 373,8193,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(monsterName+".Potion.Regenerate")), 10, inv);
		removeFlagStack("§3[  독  ]", 373,8196,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(monsterName+".Potion.Poison")), 11, inv);
		removeFlagStack("§3[  신속  ]", 373,8194,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(monsterName+".Potion.Speed")), 12, inv);
		removeFlagStack("§3[  구속  ]", 373,8234,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(monsterName+".Potion.Slow")), 13, inv);
		removeFlagStack("§3[  힘  ]", 373,8201,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(monsterName+".Potion.Strength")), 14, inv);
		removeFlagStack("§3[  나약함  ]", 373,8232,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(monsterName+".Potion.Weak")), 15, inv);
		removeFlagStack("§3[  도약  ]", 373,8267,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(monsterName+".Potion.JumpBoost")), 16, inv);

		if(monsterListYaml.getInt(monsterName+".Potion.FireRegistance")!=0)
			removeFlagStack("§3[  화염 저항  ]", 373,8227,1,Arrays.asList("§a[  포션 적용  ]"), 19, inv);
		else
			removeFlagStack("§3[  화염 저항  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 19, inv);
		if(monsterListYaml.getInt(monsterName+".Potion.WaterBreath")!=0)
			removeFlagStack("§3[  수중 호홉  ]", 373,8237,1,Arrays.asList("§a[  포션 적용  ]"), 20, inv);
		else
			removeFlagStack("§3[  수중 호홉  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 20, inv);
		if(monsterListYaml.getInt(monsterName+".Potion.Invisible")!=0)
			removeFlagStack("§3[  투명  ]", 373,8238,1,Arrays.asList("§a[  포션 적용  ]"), 21, inv);
		else
			removeFlagStack("§3[  투명  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 21, inv);
			

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+monsterName), 53, inv);
		player.openInventory(inv);
	}


	public void monsterPotionClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String monsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new MonsterOptionSettingGui().monsterOptionSettingGui(player, monsterName);
			else if(slot >= 10 && slot <= 16)
			{
				UserDataObject u = new UserDataObject();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "Potion");
				u.setString(player, (byte)3, monsterName);
				if(slot == 10)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 재생 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Regenerate");
				}
				else if(slot == 11)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 독 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Poision");
				}
				else if(slot == 12)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 신속 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Speed");
				}
				else if(slot == 13)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 구속 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Slow");
				}
				else if(slot == 14)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 힘 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Strength");
				}
				else if(slot == 15)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 나약함 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Weak");
				}
				else if(slot == 16)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 도약 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Jump");
				}
			}
			else if(slot >= 19)
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				if(slot == 19)//화염 저항
				{
					if(monsterListYaml.getInt(monsterName+".Potion.FireRegistance")==0)
						monsterListYaml.set(monsterName+".Potion.FireRegistance", 1);
					else
						monsterListYaml.set(monsterName+".Potion.FireRegistance", 0);
				}
				else if(slot == 20)//수중 호홉
				{
					if(monsterListYaml.getInt(monsterName+".Potion.WaterBreath")==0)
						monsterListYaml.set(monsterName+".Potion.WaterBreath", 1);
					else
						monsterListYaml.set(monsterName+".Potion.WaterBreath", 0);
				}
				else if(slot == 21)//투명
				{
					if(monsterListYaml.getInt(monsterName+".Potion.Invisible")==0)
						monsterListYaml.set(monsterName+".Potion.Invisible", 1);
					else
						monsterListYaml.set(monsterName+".Potion.Invisible", 0);
				}
				monsterListYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_GENERIC_DRINK, 1.0F, 1.0F);
				monsterPotionGui(player, monsterName);
			}
		}
	}

}
