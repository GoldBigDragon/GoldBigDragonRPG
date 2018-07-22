package monster.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import battle.BattleCalculator;
import effect.SoundEffect;
import main.MainServerOption;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class MonsterOptionSettingGui extends GuiUtil{

	private String uniqueCode = "§0§0§8§0§1§r";
	
	public void monsterOptionSettingGui(Player player,String monsterName)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		monsterListYaml.getConfig("Monster/MonsterList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0몬스터 설정");

		String lore=null;			
		lore = "%enter%§f§l 이름 : §f"+monsterListYaml.getString(monsterName+".Name")+"%enter%";
		lore = lore+"§f§l 타입 : §f"+monsterListYaml.getString(monsterName+".Type")+"%enter%";
		lore = lore+"§f§l 스폰 바이옴 : §f"+monsterListYaml.getString(monsterName+".Biome")+"%enter%";
		lore = lore+"§c§l 생명력 : §f"+monsterListYaml.getInt(monsterName+".HP")+"%enter%";
		lore = lore+"§b§l 경험치 : §f"+monsterListYaml.getInt(monsterName+".EXP")+"%enter%";
		lore = lore+"§e§l 드랍 금액 : §f"+monsterListYaml.getInt(monsterName+".MIN_Money")+" ~ "+monsterListYaml.getInt(monsterName+".MAX_Money")+"%enter%";
		lore = lore+"§c§l "+MainServerOption.statSTR+" : §f"+monsterListYaml.getInt(monsterName+".STR")
		+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterListYaml.getInt(monsterName+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterListYaml.getInt(monsterName+".STR"), false) + "]%enter%";
		lore = lore+"§a§l "+MainServerOption.statDEX+" : §f"+monsterListYaml.getInt(monsterName+".DEX")
		+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterListYaml.getInt(monsterName+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterListYaml.getInt(monsterName+".DEX"), 0, false) + "]%enter%";
		lore = lore+"§9§l "+MainServerOption.statINT+" : §f"+monsterListYaml.getInt(monsterName+".INT")
		+"§7 [폭공 : " + (monsterListYaml.getInt(monsterName+".INT")/4)+ " ~ "+(int)(monsterListYaml.getInt(monsterName+".INT")/2.5)+"]%enter%";
		lore = lore+"§7§l "+MainServerOption.statWILL+" : §f"+monsterListYaml.getInt(monsterName+".WILL")
		+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterListYaml.getInt(monsterName+".LUK"), (int)monsterListYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
		lore = lore+"§e§l "+MainServerOption.statLUK+" : §f"+monsterListYaml.getInt(monsterName+".LUK")
		+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterListYaml.getInt(monsterName+".LUK"), (int)monsterListYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
		lore = lore+"§7§l 방어 : §f"+monsterListYaml.getInt(monsterName+".DEF")+"%enter%";
		lore = lore+"§b§l 보호 : §f"+monsterListYaml.getInt(monsterName+".Protect")+"%enter%";
		lore = lore+"§9§l 마법 방어 : §f"+monsterListYaml.getInt(monsterName+".Magic_DEF")+"%enter%";
		lore = lore+"§1§l 마법 보호 : §f"+monsterListYaml.getInt(monsterName+".Magic_Protect")+"%enter%";
		
		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];

		removeFlagStack("§c[    몬스터    ]", 52,0,1,null, 9, inv);
		removeFlagStack("§c[    몬스터    ]", 52,0,1,null, 10, inv);
		removeFlagStack("§c[    몬스터    ]", 52,0,1,null, 11, inv);
		removeFlagStack("§c[    몬스터    ]", 52,0,1,null, 18, inv);
		removeFlagStack("§c[    몬스터    ]", 52,0,1,null, 20, inv);
		removeFlagStack("§c[    몬스터    ]", 52,0,1,null, 27, inv);
		removeFlagStack("§c[    몬스터    ]", 52,0,1,null, 28, inv);
		removeFlagStack("§c[    몬스터    ]", 52,0,1,null, 29, inv);
		int id = 383;
		byte data = 0;
		String type = monsterListYaml.getString(monsterName+".Type");
		switch(type)
		{
			case "번개크리퍼" : case "크리퍼" : data=50; break;
			case "네더스켈레톤" : case "스켈레톤" : data=51; break;
			case "거미" : data=52; break;
			case "좀비" :case "자이언트" : data=54; break;
			case "초대형슬라임" :case "특대슬라임" : case "큰슬라임" :case "보통슬라임" : case "작은슬라임" : data=55; break;
			case "가스트" : data=56; break;
			case "좀비피그맨" : data=57; break;
			case "엔더맨" : data=58; break;
			case "동굴거미" : data=59; break;
			case "좀벌레" : data=60; break;
			case "블레이즈" : data=61; break;
			case "큰마그마큐브" :case "특대마그마큐브" : case "보통마그마큐브": case "마그마큐브" : case "작은마그마큐브" : data=62; break;
			case "박쥐" : data=65; break;
			case "마녀" : data=66; break;
			case "엔더진드기" : data=67; break;
			case "수호자" : data=68; break;
			case "돼지" : data=90; break;
			case "양" : data=91; break;
			case "소" : data=92; break;
			case "닭" : data=93; break;
			case "오징어" : data=94; break;
			case "늑대" : data=95; break;
			case "버섯소" : data=96; break;
			case "오셀롯" : data=98; break;
			case "말" : data=100; break;
			case "토끼" : data=101; break;
			case "주민" : data=120; break;
			case "눈사람" :id = 332; data=0; break;
			case "골렘" :id = 265; data=0; break;
			case "위더" : id=399; break;
			case "엔더드래곤" : id=122; break;
			case "엔더크리스탈" : id=46; break;
			//case "휴먼" : id=379; data=3;break;
		}
		removeFlagStack("§f"+ monsterName, id,data,1,Arrays.asList(scriptA), 19, inv);
		
		removeFlagStack("§3[    이름 변경    ]", 421,0,1,Arrays.asList("§f몬스터의 이름을","§f변경합니다.","","§f[    현재 이름    ]"," §f"+monsterListYaml.getString(monsterName+".Name"),""), 13, inv);
		removeFlagStack("§3[    타입 변경    ]", 383,0,1,Arrays.asList("§f몬스터의 타입을","§f변경합니다.","","§f[    현재 타입    ]"," §f"+monsterListYaml.getString(monsterName+".Type"),""), 14, inv);

		data = 0;
		switch(monsterListYaml.getString(monsterName+".Biome"))
		{
			case "BEACH" : id=337;break;
			case "DESERT" : id=12;break;
			case "EXTREME_HILLS" : id=129;break;
			case "FOREST" : id=17;break;
			case "HELL" : id=87;break;
			case "JUNGLE" : id=6;data=3;break;
			case "MESA" : id=159;break;
			case "OCEAN" : id=410;break;
			case "PLAINS" : id=2;break;
			case "RIVER" : id=346;break;
			case "SAVANNA" : id=32;break;
			case "SKY" : id=121;break;
			case "SMALL_MOUNTAINS" : id=6;data=0;break;
			case "SWAMPLAND" : id=111;break;
			case "TAIGA" : id=78;break;
			default : id=166;break;
		}
		
		removeFlagStack("§3[ 스폰 바이옴 변경 ]", id,data,1,Arrays.asList("§f몬스터가 등장하는","§f바이옴을 변경합니다.","","§f[    등장 바이옴    ]"," §f"+monsterListYaml.getString(monsterName+".Biome"),""), 15, inv);
		removeFlagStack("§3[    생명력 변경    ]", 351,1,1,Arrays.asList("§f몬스터의 생명력을","§f변경합니다.","","§f[    현재 생명력    ]"," §f"+monsterListYaml.getInt(monsterName+".HP"),""), 16, inv);
		removeFlagStack("§3[    경험치 변경    ]", 384,0,1,Arrays.asList("§f몬스터 사냥시 얻는","§f경험치 량을 변경합니다.","","§f[    현재 경험치    ]"," §f"+monsterListYaml.getInt(monsterName+".EXP"),""), 22, inv);
		removeFlagStack("§3[  드랍 금액 변경  ]", 266,0,1,Arrays.asList("§f몬스터 사냥시 얻는","§f금액을 변경합니다.","","§f[    현재 금액    ]"," §f"+monsterListYaml.getInt(monsterName+".MIN_Money")+" ~ "+monsterListYaml.getInt(monsterName+".MAX_Money"),""), 23, inv);
		removeFlagStack("§3[    장비 변경    ]", 307,0,1,Arrays.asList("§f몬스터의 장비를","§f설정 합니다.","","§e[    좌클릭시 변경    ]",""), 24, inv);
		removeFlagStack("§3[  장비 드랍률 변경  ]", 54,0,1,Arrays.asList("§f몬스터 사냥시 드랍되는","§f장비 확룰을 변경합니다.","","§f[    현재 드랍률    ]"," §f머리 : "+monsterListYaml.getInt(monsterName+".Head.DropChance")/10.0+"%"
				," §f갑옷 : "+monsterListYaml.getInt(monsterName+".Chest.DropChance")/10.0+"%"
				," §f바지 : "+monsterListYaml.getInt(monsterName+".Leggings.DropChance")/10.0+"%"
				," §f신발 : "+monsterListYaml.getInt(monsterName+".Boots.DropChance")/10.0+"%"
				," §f무기 : "+monsterListYaml.getInt(monsterName+".Hand.DropChance")/10.0+"%","","§e[    좌클릭시 변경   ]",""), 25, inv);
		removeFlagStack("§3[  몬스터 스텟 변경  ]", 399,0,1,Arrays.asList("§f몬스터의 기본 스텟을","§f변경합니다.",""), 31, inv);
		removeFlagStack("§3[  몬스터 방어 변경  ]", 310,0,1,Arrays.asList("§f몬스터의 방어 및 보호를","§f변경합니다.",""), 32, inv);
		
		lore = "§f몬스터의 AI를 변경합니다.%enter%%enter%§f[    현재 AI    ]%enter%§f"+monsterListYaml.getString(monsterName+".AI")+"%enter%%enter%";
		if(type.equals("초대형슬라임")||type.equals("특대슬라임")||type.equals("큰슬라임")||
		type.equals("보통슬라임")||type.equals("작은슬라임")||type.equals("큰마그마큐브")||type.equals("특대마그마큐브")||type.equals("보통마그마큐브")||
		type.equals("마그마큐브")||type.equals("작은마그마큐브")||type.equals("가스트")||type.equals("위더")
		||type.equals("엔더드래곤")||type.equals("셜커")||type.equals("양")||type.equals("소")
		||type.equals("돼지")||type.equals("말")||type.equals("박쥐")||type.equals("토끼")
		||type.equals("오셀롯")||type.equals("늑대")||type.equals("닭")||type.equals("버섯소")
		||type.equals("오징어")||type.equals("주민")||type.equals("눈사람")||type.equals("골렘")
		)
		lore = lore + "§c[현재 선택 된 몬스터 타입은%enter%§c무조건 근접 AI만을 사용합니다.]";
		else
		{
			switch(monsterListYaml.getString(monsterName+".AI"))
			{
			case "일반 행동" :
				lore = lore+"§f일반적인 행동을 합니다.%enter%";
				break;
			case "선공" :
				lore = lore+"§f무조건 선제 공격을합니다.%enter%";break;
			case "비선공" :
				lore = lore+"§f공격받기 전에는 공격하지 않습니다.%enter%";break;
			case "무뇌아" :
				lore = lore+"§f공격및 이동을 하지 않습니다.%enter%";break;
			case "동물" :
				lore = lore+"§f공격받을 경우 도망치기 바쁘며,%enter%§f절대로 공격하지 않습니다.%enter%";break;
			}
		}
		
		scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];
		
		
		removeFlagStack("§3[  몬스터 AI 변경  ]", 137,0,1,Arrays.asList(scriptA), 33, inv);
		removeFlagStack("§3[    포션 효과    ]", 373,0,1,Arrays.asList("§f몬스터에게 포션 효과를","§f부여합니다.",""), 34, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+monsterName), 53, inv);
		player.openInventory(inv);
	}


	public void monsterOptionSettingGUIClick(InventoryClickEvent event)
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
			String monsterName = event.getInventory().getItem(53).getItemMeta().getLore().get(1).substring(2);
			
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new MonsterListGui().monsterListGUI(player, 0);
			else if(slot == 14)//몹 타입 변경
				new MonsterTypeGui().monsterTypeGui(player, monsterName, 0);
			else if(slot == 15)//스폰 바이옴 변경
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				String type = monsterListYaml.getString(monsterName+".Biome");
				if(type.equals("NULL"))
					monsterListYaml.set(monsterName+".Biome", "BEACH");
				else if(type.equals("BEACH"))
					monsterListYaml.set(monsterName+".Biome", "DESERT");
				else if(type.equals("DESERT"))
					monsterListYaml.set(monsterName+".Biome", "EXTREME_HILLS");
				else if(type.equals("EXTREME_HILLS"))
					monsterListYaml.set(monsterName+".Biome", "FOREST");
				else if(type.equals("FOREST"))
					monsterListYaml.set(monsterName+".Biome", "HELL");
				else if(type.equals("HELL"))
					monsterListYaml.set(monsterName+".Biome", "JUNGLE");
				else if(type.equals("JUNGLE"))
					monsterListYaml.set(monsterName+".Biome", "MESA");
				else if(type.equals("MESA"))
					monsterListYaml.set(monsterName+".Biome", "OCEAN");
				else if(type.equals("OCEAN"))
					monsterListYaml.set(monsterName+".Biome", "PLAINS");
				else if(type.equals("PLAINS"))
					monsterListYaml.set(monsterName+".Biome", "RIVER");
				else if(type.equals("RIVER"))
					monsterListYaml.set(monsterName+".Biome", "SAVANNA");
				else if(type.equals("SAVANNA"))
					monsterListYaml.set(monsterName+".Biome", "SKY");
				else if(type.equals("SKY"))
					monsterListYaml.set(monsterName+".Biome", "SMALL_MOUNTAINS");
				else if(type.equals("SMALL_MOUNTAINS"))
					monsterListYaml.set(monsterName+".Biome", "SWAMPLAND");
				else if(type.equals("SWAMPLAND"))
					monsterListYaml.set(monsterName+".Biome", "TAIGA");
				else if(type.equals("TAIGA"))
					monsterListYaml.set(monsterName+".Biome", "NULL");
				else
					monsterListYaml.set(monsterName+".Biome", "NULL");
				monsterListYaml.saveConfig();
				monsterOptionSettingGui(player, monsterName);
			}
			else if(slot == 33)
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				String type = monsterListYaml.getString(monsterName+".AI");
				if(type.equals("일반 행동"))
					monsterListYaml.set(monsterName+".AI", "선공");
				else if(type.equals("선공"))
					monsterListYaml.set(monsterName+".AI", "비선공");
				else if(type.equals("비선공"))
					monsterListYaml.set(monsterName+".AI", "동물");
				else if(type.equals("동물"))
					monsterListYaml.set(monsterName+".AI", "무뇌아");
				else if(type.equals("무뇌아"))
					monsterListYaml.set(monsterName+".AI", "일반 행동");
				else
					monsterListYaml.set(monsterName+".AI", "일반 행동");
				monsterListYaml.saveConfig();
				monsterOptionSettingGui(player, monsterName);
			}
			else if(slot == 24)//장비 변경
			{
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.0F);
				new MonsterEquipGui().monsterEquipGui(player, monsterName);
			}
			else if(slot == 34)//몬스터 포션 효과
				new MonsterPotionGui().monsterPotionGui(player, monsterName);
			else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
			{
				UserDataObject u = new UserDataObject();
				player.closeInventory();
				u.setType(player, "Monster");
				u.setString(player, (byte)2, monsterName);
				if(slot==13)//몹 이름 변경
				{
					player.sendMessage("§a[몬스터] : 몬스터의 보여주는 이름을 설정하세요!");
					player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
					"§3&3 §4&4 §5&5 " +
							"§6&6 §7&7 §8&8 " +
					"§9&9 §a&a §b&b §c&c " +
							"§d&d §e&e §f&f");
					u.setString(player, (byte)1, "CN");
				}
				else if(slot == 16)//생명력 변경
				{
					player.sendMessage("§a[몬스터] : 해당 몬스터의 생명력을 설정 해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "HP");
				}
				else if(slot == 22)//경험치 변경
				{
					player.sendMessage("§a[몬스터] : 해당 몬스터의 경험치를 설정 해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "EXP");
				}
				else if(slot == 23)//드랍 금액 변경
				{
					player.sendMessage("§a[몬스터] : 해당 몬스터가 드랍하는 최소 골드량을 설정해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "MIN_Money");
				}
				else if(slot == 25)//장비 드랍률 변경
				{
					player.sendMessage("§7(확률 계산 : 1000 = 100%, 1 = 0.1%)");
					player.sendMessage("§a[몬스터] : 몬스터의 투구 드랍률을 설정해 주세요!");
					player.sendMessage("§3(0 ~ 1000)");
					u.setString(player, (byte)1, "Head.DropChance");
				}
				else if(slot == 31)//몬스터 스텟 변경
				{
					player.sendMessage("§7("+MainServerOption.statSTR+"은 몬스터의 물리 공격력을 상승시켜 줍니다.)");
					player.sendMessage("§a[몬스터] : 몬스터의 "+MainServerOption.statSTR+"을 설정해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "STR");
				}
				else if(slot == 32)//몬스터 방어 변경
				{
					player.sendMessage("§7(물리방어는 몬스터의 물리적인 방어력을 상승시켜 줍니다.)");
					player.sendMessage("§a[몬스터] : 몬스터의 물리 방어력을 설정해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "DEF");
				}
			}
		}
	}

}
