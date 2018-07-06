package monster;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import admin.OPboxGui;
import battle.BattleCalculator;
import effect.SoundEffect;
import main.MainServerOption;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class MonsterGui extends GuiUtil
{
	public void monsterListGUI(Player player, int page)
	{
		YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		String uniqueCode = "§0§0§8§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0몬스터 목록 : " + (page+1));

		Object[] a= monsterYaml.getKeys().toArray();

		byte loc=0;
		StringBuilder sb = new StringBuilder();
		String monsterName = null;
		for(int count = page*45; count < a.length;count++)
		{
			if(loc >= 45) break;
			monsterName = a[count].toString();
			sb = new StringBuilder();
			sb.append("%enter%§f§l 이름 : §f");
			sb.append(monsterYaml.getString(monsterName+".Name"));
			sb.append("%enter%");
			sb.append("§f§l 타입 : §f");
			sb.append(monsterYaml.getString(monsterName+".Type"));
			sb.append("%enter%");
			sb.append("§f§l 스폰 바이옴 : §f");
			sb.append(monsterYaml.getString(monsterName+".Biome"));
			sb.append("%enter%");
			sb.append("§c§l 생명력 : §f");
			sb.append(monsterYaml.getInt(monsterName+".HP"));
			sb.append("%enter%");
			sb.append("§b§l 경험치 : §f");
			sb.append(monsterYaml.getInt(monsterName+".EXP"));
			sb.append("%enter%");
			sb.append("§e§l 드랍 금액 : §f");
			sb.append(monsterYaml.getInt(monsterName+".MIN_Money"));
			sb.append(" ~ ");
			sb.append(monsterYaml.getInt(monsterName+".MAX_Money"));
			sb.append("%enter%");
			sb.append("§c§l ");
			sb.append(MainServerOption.statSTR);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".STR"));
			sb.append("§7 [물공 : ");
			sb.append(BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), true));
			sb.append(" ~ ");
			sb.append(BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), false));
			sb.append("]%enter%");
			sb.append("§a§l ");
			sb.append(MainServerOption.statDEX);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".DEX"));
			sb.append("§7 [활공 : ");
			sb.append(BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, true));
			sb.append(" ~ ");
			sb.append(BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, false));
			sb.append("]%enter%");
			sb.append("§9§l ");
			sb.append(MainServerOption.statINT);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".INT"));
			sb.append("§7 [폭공 : ");
			sb.append(monsterYaml.getInt(monsterName+".INT")/4);
			sb.append(" ~ ");
			sb.append((int)(monsterYaml.getInt(monsterName+".INT")/2.5));
			sb.append("]%enter%");
			sb.append("§7§l ");
			sb.append(MainServerOption.statWILL);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".WILL"));
			sb.append("§7 [크리 : ");
			sb.append(BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0));
			sb.append(" %]%enter%");
			sb.append("§e§l ");
			sb.append(MainServerOption.statLUK);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".LUK"));
			sb.append("§7 [크리 : ");
			sb.append(BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0));
			sb.append(" %]%enter%");

			sb.append("§7§l 방어 : §f");
			sb.append(monsterYaml.getInt(monsterName+".DEF"));
			sb.append("%enter%");
			sb.append("§b§l 보호 : §f");
			sb.append(monsterYaml.getInt(monsterName+".Protect"));
			sb.append("%enter%");
			sb.append("§9§l 마법 방어 : §f");
			sb.append(monsterYaml.getInt(monsterName+".Magic_DEF"));
			sb.append("%enter%");
			sb.append("§1§l 마법 보호 : §f");
			sb.append(monsterYaml.getInt(monsterName+".Magic_Protect"));
			sb.append("%enter%");
			sb.append("%enter%§e§l[Shift + 좌 클릭시 스폰알 지급]%enter%§c§l[Shift + 우 클릭시 몬스터 제거]%enter%");

			String[] scriptA = sb.toString().split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			int id = 383;
			byte data = 0;
			switch(monsterYaml.getString(monsterName+".Type"))
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
				case "위더" : id=399; break;
				case "엔더드래곤" : id=122; break;
				case "엔더크리스탈" : id=46; break;
				//case "휴먼" : id=379; data = 3; break;
			}
			
			stack("§f"+monsterName, id, data, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
			stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		stack("§f§l새 몬스터", 339,0,1,Arrays.asList("§7새로운 몬스터를 생성합니다."), 49, inv);
		stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void MonsterOptionSettingGUI(Player player,String MonsterName)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		monsterListYaml.getConfig("Monster/MonsterList.yml");

		String UniqueCode = "§0§0§8§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 설정");

		String Lore=null;			
		Lore = "%enter%§f§l 이름 : §f"+monsterListYaml.getString(MonsterName+".Name")+"%enter%";
		Lore = Lore+"§f§l 타입 : §f"+monsterListYaml.getString(MonsterName+".Type")+"%enter%";
		Lore = Lore+"§f§l 스폰 바이옴 : §f"+monsterListYaml.getString(MonsterName+".Biome")+"%enter%";
		Lore = Lore+"§c§l 생명력 : §f"+monsterListYaml.getInt(MonsterName+".HP")+"%enter%";
		Lore = Lore+"§b§l 경험치 : §f"+monsterListYaml.getInt(MonsterName+".EXP")+"%enter%";
		Lore = Lore+"§e§l 드랍 금액 : §f"+monsterListYaml.getInt(MonsterName+".MIN_Money")+" ~ "+monsterListYaml.getInt(MonsterName+".MAX_Money")+"%enter%";
		Lore = Lore+"§c§l "+MainServerOption.statSTR+" : §f"+monsterListYaml.getInt(MonsterName+".STR")
		+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterListYaml.getInt(MonsterName+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterListYaml.getInt(MonsterName+".STR"), false) + "]%enter%";
		Lore = Lore+"§a§l "+MainServerOption.statDEX+" : §f"+monsterListYaml.getInt(MonsterName+".DEX")
		+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterListYaml.getInt(MonsterName+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterListYaml.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
		Lore = Lore+"§9§l "+MainServerOption.statINT+" : §f"+monsterListYaml.getInt(MonsterName+".INT")
		+"§7 [폭공 : " + (monsterListYaml.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(monsterListYaml.getInt(MonsterName+".INT")/2.5)+"]%enter%";
		Lore = Lore+"§7§l "+MainServerOption.statWILL+" : §f"+monsterListYaml.getInt(MonsterName+".WILL")
		+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterListYaml.getInt(MonsterName+".LUK"), (int)monsterListYaml.getInt(MonsterName+".WILL"),0) + " %]%enter%";
		Lore = Lore+"§e§l "+MainServerOption.statLUK+" : §f"+monsterListYaml.getInt(MonsterName+".LUK")
		+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterListYaml.getInt(MonsterName+".LUK"), (int)monsterListYaml.getInt(MonsterName+".WILL"),0) + " %]%enter%";
		Lore = Lore+"§7§l 방어 : §f"+monsterListYaml.getInt(MonsterName+".DEF")+"%enter%";
		Lore = Lore+"§b§l 보호 : §f"+monsterListYaml.getInt(MonsterName+".Protect")+"%enter%";
		Lore = Lore+"§9§l 마법 방어 : §f"+monsterListYaml.getInt(MonsterName+".Magic_DEF")+"%enter%";
		Lore = Lore+"§1§l 마법 보호 : §f"+monsterListYaml.getInt(MonsterName+".Magic_Protect")+"%enter%";

		
		String[] scriptA = Lore.split("%enter%");
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
		String Type = monsterListYaml.getString(MonsterName+".Type");
		switch(Type)
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

		removeFlagStack("§f"+ MonsterName, id,data,1,Arrays.asList(scriptA), 19, inv);
		
		
		removeFlagStack("§3[    이름 변경    ]", 421,0,1,Arrays.asList("§f몬스터의 이름을","§f변경합니다.","","§f[    현재 이름    ]"," §f"+monsterListYaml.getString(MonsterName+".Name"),""), 13, inv);
		removeFlagStack("§3[    타입 변경    ]", 383,0,1,Arrays.asList("§f몬스터의 타입을","§f변경합니다.","","§f[    현재 타입    ]"," §f"+monsterListYaml.getString(MonsterName+".Type"),""), 14, inv);

		data = 0;
		switch(monsterListYaml.getString(MonsterName+".Biome"))
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
		
		removeFlagStack("§3[ 스폰 바이옴 변경 ]", id,data,1,Arrays.asList("§f몬스터가 등장하는","§f바이옴을 변경합니다.","","§f[    등장 바이옴    ]"," §f"+monsterListYaml.getString(MonsterName+".Biome"),""), 15, inv);
		removeFlagStack("§3[    생명력 변경    ]", 351,1,1,Arrays.asList("§f몬스터의 생명력을","§f변경합니다.","","§f[    현재 생명력    ]"," §f"+monsterListYaml.getInt(MonsterName+".HP"),""), 16, inv);
		removeFlagStack("§3[    경험치 변경    ]", 384,0,1,Arrays.asList("§f몬스터 사냥시 얻는","§f경험치 량을 변경합니다.","","§f[    현재 경험치    ]"," §f"+monsterListYaml.getInt(MonsterName+".EXP"),""), 22, inv);
		removeFlagStack("§3[  드랍 금액 변경  ]", 266,0,1,Arrays.asList("§f몬스터 사냥시 얻는","§f금액을 변경합니다.","","§f[    현재 금액    ]"," §f"+monsterListYaml.getInt(MonsterName+".MIN_Money")+" ~ "+monsterListYaml.getInt(MonsterName+".MAX_Money"),""), 23, inv);
		removeFlagStack("§3[    장비 변경    ]", 307,0,1,Arrays.asList("§f몬스터의 장비를","§f설정 합니다.","","§e[    좌클릭시 변경    ]",""), 24, inv);
		removeFlagStack("§3[  장비 드랍률 변경  ]", 54,0,1,Arrays.asList("§f몬스터 사냥시 드랍되는","§f장비 확룰을 변경합니다.","","§f[    현재 드랍률    ]"," §f머리 : "+monsterListYaml.getInt(MonsterName+".Head.DropChance")/10.0+"%"
				," §f갑옷 : "+monsterListYaml.getInt(MonsterName+".Chest.DropChance")/10.0+"%"
				," §f바지 : "+monsterListYaml.getInt(MonsterName+".Leggings.DropChance")/10.0+"%"
				," §f신발 : "+monsterListYaml.getInt(MonsterName+".Boots.DropChance")/10.0+"%"
				," §f무기 : "+monsterListYaml.getInt(MonsterName+".Hand.DropChance")/10.0+"%","","§e[    좌클릭시 변경   ]",""), 25, inv);
		removeFlagStack("§3[  몬스터 스텟 변경  ]", 399,0,1,Arrays.asList("§f몬스터의 기본 스텟을","§f변경합니다.",""), 31, inv);
		removeFlagStack("§3[  몬스터 방어 변경  ]", 310,0,1,Arrays.asList("§f몬스터의 방어 및 보호를","§f변경합니다.",""), 32, inv);
		
		Lore = "§f몬스터의 AI를 변경합니다.%enter%%enter%§f[    현재 AI    ]%enter%§f"+monsterListYaml.getString(MonsterName+".AI")+"%enter%%enter%";
		if(Type.equals("초대형슬라임")||Type.equals("특대슬라임")||Type.equals("큰슬라임")||
		Type.equals("보통슬라임")||Type.equals("작은슬라임")||Type.equals("큰마그마큐브")||Type.equals("특대마그마큐브")||Type.equals("보통마그마큐브")||
		Type.equals("마그마큐브")||Type.equals("작은마그마큐브")||Type.equals("가스트")||Type.equals("위더")
		||Type.equals("엔더드래곤")||Type.equals("셜커")||Type.equals("양")||Type.equals("소")
		||Type.equals("돼지")||Type.equals("말")||Type.equals("박쥐")||Type.equals("토끼")
		||Type.equals("오셀롯")||Type.equals("늑대")||Type.equals("닭")||Type.equals("버섯소")
		||Type.equals("오징어")||Type.equals("주민")||Type.equals("눈사람")||Type.equals("골렘")
		)
		Lore = Lore + "§c[현재 선택 된 몬스터 타입은%enter%§c무조건 근접 AI만을 사용합니다.]";
		else
		{
			switch(monsterListYaml.getString(MonsterName+".AI"))
			{
			case "일반 행동" :
				Lore = Lore+"§f일반적인 행동을 합니다.%enter%";
				break;
			case "선공" :
				Lore = Lore+"§f무조건 선제 공격을합니다.%enter%";break;
			case "비선공" :
				Lore = Lore+"§f공격받기 전에는 공격하지 않습니다.%enter%";break;
			case "무뇌아" :
				Lore = Lore+"§f공격및 이동을 하지 않습니다.%enter%";break;
			case "동물" :
				Lore = Lore+"§f공격받을 경우 도망치기 바쁘며,%enter%§f절대로 공격하지 않습니다.%enter%";break;
			}
		}
		
		scriptA = Lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];
		
		
		removeFlagStack("§3[  몬스터 AI 변경  ]", 137,0,1,Arrays.asList(scriptA), 33, inv);
		removeFlagStack("§3[    포션 효과    ]", 373,0,1,Arrays.asList("§f몬스터에게 포션 효과를","§f부여합니다.",""), 34, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+MonsterName), 53, inv);
		player.openInventory(inv);
	}
	
	public void MonsterPotionGUI(Player player, String MonsterName)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		monsterListYaml.getConfig("Monster/MonsterList.yml");
		String UniqueCode = "§0§0§8§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 포션");
		
		removeFlagStack("§3[  재생  ]", 373,8193,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Regenerate")), 10, inv);
		removeFlagStack("§3[  독  ]", 373,8196,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Poison")), 11, inv);
		removeFlagStack("§3[  신속  ]", 373,8194,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Speed")), 12, inv);
		removeFlagStack("§3[  구속  ]", 373,8234,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Slow")), 13, inv);
		removeFlagStack("§3[  힘  ]", 373,8201,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Strength")), 14, inv);
		removeFlagStack("§3[  나약함  ]", 373,8232,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Weak")), 15, inv);
		removeFlagStack("§3[  도약  ]", 373,8267,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.JumpBoost")), 16, inv);

		if(monsterListYaml.getInt(MonsterName+".Potion.FireRegistance")!=0)
			removeFlagStack("§3[  화염 저항  ]", 373,8227,1,Arrays.asList("§a[  포션 적용  ]"), 19, inv);
		else
			removeFlagStack("§3[  화염 저항  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 19, inv);
		if(monsterListYaml.getInt(MonsterName+".Potion.WaterBreath")!=0)
			removeFlagStack("§3[  수중 호홉  ]", 373,8237,1,Arrays.asList("§a[  포션 적용  ]"), 20, inv);
		else
			removeFlagStack("§3[  수중 호홉  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 20, inv);
		if(monsterListYaml.getInt(MonsterName+".Potion.Invisible")!=0)
			removeFlagStack("§3[  투명  ]", 373,8238,1,Arrays.asList("§a[  포션 적용  ]"), 21, inv);
		else
			removeFlagStack("§3[  투명  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 21, inv);
			

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+MonsterName), 53, inv);
		player.openInventory(inv);
	}

	public void ArmorGUI(Player player, String mob)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		String UniqueCode = "§1§0§8§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0몬스터 장비 설정");
		monsterListYaml.getConfig("Monster/MonsterList.yml");

		if(monsterListYaml.contains(mob + ".Head.Item")==true&&
			monsterListYaml.getItemStack(mob + ".Head.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(0, monsterListYaml.getItemStack(mob + ".Head.Item"));
		else
			stack("§f머리", 302,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)0, inv);

		if(monsterListYaml.contains(mob + ".Chest.Item")==true&&
				monsterListYaml.getItemStack(mob + ".Chest.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(1, monsterListYaml.getItemStack(mob + ".Chest.Item"));
		else
			stack("§f갑옷", 303,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)1, inv);

		if(monsterListYaml.contains(mob + ".Leggings.Item")==true&&
				monsterListYaml.getItemStack(mob + ".Leggings.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(2, monsterListYaml.getItemStack(mob + ".Leggings.Item"));
		else
			stack("§f바지", 304,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)2, inv);

		if(monsterListYaml.contains(mob + ".Boots.Item")==true&&
		monsterListYaml.getItemStack(mob + ".Boots.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(3, monsterListYaml.getItemStack(mob + ".Boots.Item"));
		else
			stack("§f부츠", 305,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)3, inv);

		if(monsterListYaml.contains(mob + ".Hand.Item")==true&&
		monsterListYaml.getItemStack(mob + ".Hand.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(4, monsterListYaml.getItemStack(mob + ".Hand.Item"));
		else
			stack("§f오른손", 267,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)4, inv);

		if(monsterListYaml.contains(mob + ".OffHand.Item")==true&&
		monsterListYaml.getItemStack(mob + ".OffHand.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(5, monsterListYaml.getItemStack(mob + ".OffHand.Item"));
		else
			stack("§f왼손", 267,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)5, inv);
		
		stack("§f"+ mob, 416,(byte)0,(byte)1,Arrays.asList("§8"+ mob+"의 현재 장비입니다." ), (byte)8, inv);
		stack("§f", 30,(byte)0,(byte)1,Arrays.asList("§7이곳에는 아이템을","§7올려두지 마세요."), (byte)7, inv);
		stack("§f", 30,(byte)0,(byte)1,Arrays.asList("§7이곳에는 아이템을","§7올려두지 마세요."), (byte)6, inv);
		
		player.openInventory(inv);
		return;
	}

	public void MonsterTypeGUI(Player player, String MonsterName, int page)
	{
		String UniqueCode = "§1§0§8§0§b§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 타입 : " + (page+1) + " / 2");

		if(page==0)
		{
			MonsterStack(inv, 0, 54,  0, "§2§l[좀비]", Arrays.asList("§0"+54));
			MonsterStack(inv, 1, 27,  0, "§2§l[주민좀비]", Arrays.asList("§0"+27));
			MonsterStack(inv, 2, 53,  0, "§2§l[자이언트]", Arrays.asList("§0"+53));
			MonsterStack(inv, 3, 29,  0, "§2§l[좀비말]", Arrays.asList("§0"+29));
			MonsterStack(inv, 4, 57,  0, "§d§l[좀비피그맨]", Arrays.asList("§0"+57));
			MonsterStack(inv, 5, 23,  0, "§e§l[허스크]", Arrays.asList("§0"+23));
			MonsterStack(inv, 6, 50,  0, "§a§l[크리퍼]", Arrays.asList("§0"+50));
			MonsterStack(inv, 7, 50,  1, "§a§l[번개크리퍼]", Arrays.asList("§0"+50));
			MonsterStack(inv, 8, 51,  0, "§f§l[스켈레톤]", Arrays.asList("§0"+51));
			MonsterStack(inv, 9, 5,  0, "§8§l[네더스켈레톤]", Arrays.asList("§0"+5));
			MonsterStack(inv, 10, 28,  0, "§f§l[스켈레톤말]", Arrays.asList("§0"+28));
			MonsterStack(inv, 11, 6,  0, "§b§l[스트레이]", Arrays.asList("§0"+6));
			MonsterStack(inv, 12, 64,  0, "§8§l[위더]", Arrays.asList("§0"+64));
			MonsterStack(inv, 13, 52,  0, "§7§l[거미]", Arrays.asList("§0"+52));
			MonsterStack(inv, 14, 59,  1, "§7§l[동굴거미]", Arrays.asList("§0"+59));
			MonsterStack(inv, 13, 55,  0, "§a§l[작은슬라임]", Arrays.asList("§0"+55));
			MonsterStack(inv, 14, 55,  1, "§a§l[보통슬라임]", Arrays.asList("§0"+55));
			MonsterStack(inv, 15, 55,  2, "§a§l[큰슬라임]", Arrays.asList("§0"+55));
			MonsterStack(inv, 16, 55,  3, "§a§l[특대슬라임]", Arrays.asList("§0"+55));
			MonsterStack(inv, 17, 55,  4, "§a§l[초대형슬라임]", Arrays.asList("§0"+55));
			MonsterStack(inv, 18, 62,  0, "§7§l[작은마그마큐브]", Arrays.asList("§0"+62));
			MonsterStack(inv, 19, 62,  1, "§7§l[보통마그마큐브]", Arrays.asList("§0"+62));
			MonsterStack(inv, 20, 62,  2, "§7§l[큰마그마큐브]", Arrays.asList("§0"+62));
			MonsterStack(inv, 21, 62,  3, "§7§l[특대마그마큐브]", Arrays.asList("§0"+62));
			MonsterStack(inv, 22, 62,  4, "§7§l[초대형마그마큐브]", Arrays.asList("§0"+62));
			MonsterStack(inv, 23, 65,  0, "§8§l[박쥐]", Arrays.asList("§0"+65));
			MonsterStack(inv, 24, 56,  0, "§f§l[가스트]", Arrays.asList("§0"+56));
			MonsterStack(inv, 25, 61,  0, "§e§l[블레이즈]", Arrays.asList("§0"+61));
			MonsterStack(inv, 26, 60,  0, "§7§l[좀벌레]", Arrays.asList("§0"+60));
			MonsterStack(inv, 27, 67,  0, "§5§l[엔더진드기]", Arrays.asList("§0"+67));
			MonsterStack(inv, 28, 120,  0, "§a§l[주민]", Arrays.asList("§0"+120));
			MonsterStack(inv, 29, 66,  0, "§5§l[마녀]", Arrays.asList("§0"+66));
			MonsterStack(inv, 30, 36,  0, "§7§l[변명자]", Arrays.asList("§0"+36));
			MonsterStack(inv, 31, 34,  0, "§5§l[소환사]", Arrays.asList("§0"+34));
			MonsterStack(inv, 32, 35,  0, "§9§l[벡스]", Arrays.asList("§0"+35));
			MonsterStack(inv, 33, 68,  0, "§3§l[가디언]", Arrays.asList("§0"+68));
			MonsterStack(inv, 34, 4,  0, "§3§l[엘더가디언]", Arrays.asList("§0"+4));
			MonsterStack(inv, 35, 58,  0, "§8§l[엔더맨]", Arrays.asList("§0"+58));
			MonsterStack(inv, 36, 69,  0, "§5§l[셜커]", Arrays.asList("§0"+69));
			MonsterStack(inv, 37, 63,  0, "§8§l[엔더드래곤]", Arrays.asList("§0"+63));
			MonsterStack(inv, 39, 90,  0, "§d§l[돼지]", Arrays.asList("§0"+90));
			MonsterStack(inv, 40, 91,  0, "§f§l[양]", Arrays.asList("§0"+91));
			MonsterStack(inv, 41, 92,  0, "§7§l[소]", Arrays.asList("§0"+92));
			MonsterStack(inv, 42, 96,  0, "§c§l[버섯소]", Arrays.asList("§0"+96));
			MonsterStack(inv, 43, 93,  0, "§f§l[닭]", Arrays.asList("§0"+93));
			MonsterStack(inv, 44, 94,  0, "§8§l[오징어]", Arrays.asList("§0"+94));
		  	removeFlagStack("§f§l다음 페이지", 323, 0, 1, null, 50, inv);
		}
		else
		{
			MonsterStack(inv, 0, 95,  0, "§f§l[늑대]", Arrays.asList("§0"+95));
			MonsterStack(inv, 1, 98,  0, "§e§l[오셀롯]", Arrays.asList("§0"+98));
			MonsterStack(inv, 2, 97,  0, "§f§l[눈사람]", Arrays.asList("§0"+97));
			MonsterStack(inv, 3, 99,  0, "§f§l[철골렘]", Arrays.asList("§0"+99));
			MonsterStack(inv, 4, 101,  0, "§f§l[토끼]", Arrays.asList("§0"+101));
			MonsterStack(inv, 5, 102,  0, "§f§l[북극곰]", Arrays.asList("§0"+102));
			MonsterStack(inv, 6, 31,  0, "§6§l[당나귀]", Arrays.asList("§0"+31));
			MonsterStack(inv, 7, 32,  0, "§6§l[노새]", Arrays.asList("§0"+32));
			MonsterStack(inv, 8, 103,  0, "§6§l[라마]", Arrays.asList("§0"+103));
			MonsterStack(inv, 9, 100,  0, "§6§l[말]", Arrays.asList("§0"+100));
		  	removeFlagStack("§f§l이전 페이지", 323, 0, 1, null, 48, inv);
		}

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+MonsterName), 53, inv);
		player.openInventory(inv);
	}

	public void MonsterStack(Inventory inv, int loc, int monsterID, int option, String displayName, List<String> lore)
	{
		int ID = 383;
		int Data = 0;
		int Amount = 1;

		if(monsterID==4)//엘더 가디언
		{
			ID=409;
			Amount = 2;
		}
		else if(monsterID==5)//위더 스켈레톤
			ID=263;
		else if(monsterID==6)//스트레이
			ID=440;
		else if(monsterID==23)//허스크
		{
			ID=24;
			Data = 1;
		}
		else if(monsterID==27)//주민 좀비
		{
			ID=367;
			Amount = 2;
		}
		else if(monsterID==28)//스켈레톤 말
		{
			ID=352;
			Amount = 2;
		}
		else if(monsterID==29)//좀비 말
		{
			ID=367;
			Amount = 4;
		}
		else if(monsterID==31)//당나귀
			ID=54;
		else if(monsterID==32)//뮤엘
		{
			ID=54;
			Amount = 1;
		}
		else if(monsterID==34)//에보커
			ID=449;
		else if(monsterID==35)//벡스
			ID=452;
		else if(monsterID==36)//빈디케이터
			ID=258;
		else if(monsterID==49)//인간
		{
			ID=397;
			Data = 3;
		}
		else if(monsterID==50)//크리퍼
		{
			ID=289;
			if(option==1)//번개 크리퍼
				Amount = 2;
		}
		else if(monsterID==51)//스켈레톤
			ID=352;
		else if(monsterID==52)//거미
			ID=287;
		else if(monsterID==53)//자이언트
		{
			ID=367;
			Amount = 3;
		}
		else if(monsterID==54)//좀비
			ID=367;
		else if(monsterID==55)//슬라임
		{
			ID=341;
			Amount =  (option+Amount);
		}
		else if(monsterID==56)//가스트
			ID=370;
		else if(monsterID==57)//좀비 피그맨
			ID=283;
		else if(monsterID==58)//엔더맨
			ID=368;
		else if(monsterID==59)//동굴거미
		{
			ID=287;
			Amount = 2;
		}
		else if(monsterID==60)//좀벌레
			ID=1;
		else if(monsterID==61)//블레이즈
			ID=369;
		else if(monsterID==62)//마그마 큐브
		{
			ID=378;
			Amount =  (option+Amount);
		}
		else if(monsterID==63)//엔더 드래곤
			ID=122;
		else if(monsterID==64)//위더
			ID=399;
		else if(monsterID==65)//박쥐
			ID=362;
		else if(monsterID==66)//마너
			ID=438;
		else if(monsterID==67)//엔더 마이트
			ID=432;
		else if(monsterID==68)//가디언
			ID=409;
		else if(monsterID==69)//셜커
			ID=450;
		else if(monsterID==90)//돼지
			ID=319;
		else if(monsterID==91)//양
			ID=423;
		else if(monsterID==92)//소
			ID=363;
		else if(monsterID==93)//닭
			ID=365;
		else if(monsterID==94)//오징어
			ID=351;
		else if(monsterID==95)//늑대
			ID=280;
		else if(monsterID==96)//버섯소
			ID=40;
		else if(monsterID==97)//눈사람
			ID=332;
		else if(monsterID==98)//오셀롯
			ID=349;
		else if(monsterID==99)//철골렘
			ID=265;
		else if(monsterID==100)//말
			ID=417;
		else if(monsterID==101)//토끼
			ID=411;
		else if(monsterID==102)//북극곰
		{
			ID=349;
			Data = 1;
		}
		else if(monsterID==103)//라마
		{
			ID=54;
			Amount = 2;
		}
		else if(monsterID==120)//주민
			ID=388;
		else if(monsterID==200)//엔더 크리스탈
			ID=426;
		
		removeFlagStack(displayName, ID, Data, Amount, lore, loc, inv);
		return;
	}

	public void MonsterListGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new OPboxGui().opBoxGuiMain(player, (byte) 1);
			else if(slot == 48)//이전 페이지
				monsterListGUI(player, page-1);
			else if(slot == 49)//새 몬스터
			{
				player.closeInventory();
				player.sendMessage("§a[몬스터] : 새로운 몬스터 이름을 지어 주세요!");
				UserDataObject u = new UserDataObject();
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "NM");
			}
			else if(slot == 50)//다음 페이지
				monsterListGUI(player, page+1);
			else
			{
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
					MonsterOptionSettingGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isLeftClick() == true&&event.isShiftClick())
					new monster.MonsterSpawn().SpawnEggGive(player,ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isRightClick()&&event.isShiftClick())
				{
					SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
					YamlLoader monsterListYaml = new YamlLoader();
					monsterListYaml.getConfig("Monster/MonsterList.yml");
					monsterListYaml.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					monsterListYaml.saveConfig();
					monsterListGUI(player, page);
				}
			}
		}
	}

	public void MonsterOptionSettingGUIClick(InventoryClickEvent event)
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
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				monsterListGUI(player, 0);
			else if(slot == 14)//몹 타입 변경
				MonsterTypeGUI(player, MonsterName, 0);
			else if(slot == 15)//스폰 바이옴 변경
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				String Type = monsterListYaml.getString(MonsterName+".Biome");
				if(Type.equals("NULL"))
					monsterListYaml.set(MonsterName+".Biome", "BEACH");
				else if(Type.equals("BEACH"))
					monsterListYaml.set(MonsterName+".Biome", "DESERT");
				else if(Type.equals("DESERT"))
					monsterListYaml.set(MonsterName+".Biome", "EXTREME_HILLS");
				else if(Type.equals("EXTREME_HILLS"))
					monsterListYaml.set(MonsterName+".Biome", "FOREST");
				else if(Type.equals("FOREST"))
					monsterListYaml.set(MonsterName+".Biome", "HELL");
				else if(Type.equals("HELL"))
					monsterListYaml.set(MonsterName+".Biome", "JUNGLE");
				else if(Type.equals("JUNGLE"))
					monsterListYaml.set(MonsterName+".Biome", "MESA");
				else if(Type.equals("MESA"))
					monsterListYaml.set(MonsterName+".Biome", "OCEAN");
				else if(Type.equals("OCEAN"))
					monsterListYaml.set(MonsterName+".Biome", "PLAINS");
				else if(Type.equals("PLAINS"))
					monsterListYaml.set(MonsterName+".Biome", "RIVER");
				else if(Type.equals("RIVER"))
					monsterListYaml.set(MonsterName+".Biome", "SAVANNA");
				else if(Type.equals("SAVANNA"))
					monsterListYaml.set(MonsterName+".Biome", "SKY");
				else if(Type.equals("SKY"))
					monsterListYaml.set(MonsterName+".Biome", "SMALL_MOUNTAINS");
				else if(Type.equals("SMALL_MOUNTAINS"))
					monsterListYaml.set(MonsterName+".Biome", "SWAMPLAND");
				else if(Type.equals("SWAMPLAND"))
					monsterListYaml.set(MonsterName+".Biome", "TAIGA");
				else if(Type.equals("TAIGA"))
					monsterListYaml.set(MonsterName+".Biome", "NULL");
				else
					monsterListYaml.set(MonsterName+".Biome", "NULL");
				monsterListYaml.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
			else if(slot == 33)
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				String Type = monsterListYaml.getString(MonsterName+".AI");
				if(Type.equals("일반 행동"))
					monsterListYaml.set(MonsterName+".AI", "선공");
				else if(Type.equals("선공"))
					monsterListYaml.set(MonsterName+".AI", "비선공");
				else if(Type.equals("비선공"))
					monsterListYaml.set(MonsterName+".AI", "동물");
				else if(Type.equals("동물"))
					monsterListYaml.set(MonsterName+".AI", "무뇌아");
				else if(Type.equals("무뇌아"))
					monsterListYaml.set(MonsterName+".AI", "일반 행동");
				else
					monsterListYaml.set(MonsterName+".AI", "일반 행동");
				monsterListYaml.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
			else if(slot == 24)//장비 변경
			{
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.0F);
				ArmorGUI(player, MonsterName);
			}
			else if(slot == 34)//몬스터 포션 효과
				MonsterPotionGUI(player, MonsterName);
			else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
			{
				UserDataObject u = new UserDataObject();
				player.closeInventory();
				u.setType(player, "Monster");
				u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
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

	public void MonsterPotionGUIClick(InventoryClickEvent event)
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
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MonsterOptionSettingGUI(player, MonsterName);
			else if(slot >= 10 && slot <= 16)
			{
				UserDataObject u = new UserDataObject();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "Potion");
				u.setString(player, (byte)3, MonsterName);
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
					if(monsterListYaml.getInt(MonsterName+".Potion.FireRegistance")==0)
						monsterListYaml.set(MonsterName+".Potion.FireRegistance", 1);
					else
						monsterListYaml.set(MonsterName+".Potion.FireRegistance", 0);
				}
				else if(slot == 20)//수중 호홉
				{
					if(monsterListYaml.getInt(MonsterName+".Potion.WaterBreath")==0)
						monsterListYaml.set(MonsterName+".Potion.WaterBreath", 1);
					else
						monsterListYaml.set(MonsterName+".Potion.WaterBreath", 0);
				}
				else if(slot == 21)//투명
				{
					if(monsterListYaml.getInt(MonsterName+".Potion.Invisible")==0)
						monsterListYaml.set(MonsterName+".Potion.Invisible", 1);
					else
						monsterListYaml.set(MonsterName+".Potion.Invisible", 0);
				}
				monsterListYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_GENERIC_DRINK, 1.0F, 1.0F);
				MonsterPotionGUI(player, MonsterName);
			}
		}
	}

	public void ArmorGUIClick(InventoryClickEvent event)
	{
		if(event.getSlot() >=6 && event.getSlot() <= 8)
			event.setCancelled(true);
		else if(event.getCurrentItem().hasItemMeta())
			if(event.getCurrentItem().getItemMeta().hasLore())
				if(event.getCurrentItem().getItemMeta().getLore().get(0).equals("§7이곳에 아이템을 넣어 주세요."))
					event.getInventory().remove(event.getCurrentItem());
		return;
	}
	
	public void ArmorGUIClose(InventoryCloseEvent event)
	{
		YamlLoader monsterListYaml = new YamlLoader();

		monsterListYaml.getConfig("Monster/MonsterList.yml");
		String MonsterName = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName().toString());
		if(event.getInventory().getItem(0)==new util.GuiUtil().getItemStack("§f머리", 302,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".Head.Item", null);
		else
			monsterListYaml.set(MonsterName+".Head.Item", event.getInventory().getItem(0));
		
		if(event.getInventory().getItem(1)==new util.GuiUtil().getItemStack("§f갑옷", 303,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
					monsterListYaml.set(MonsterName+".Chest.Item", null);
		else
			monsterListYaml.set(MonsterName+".Chest.Item", event.getInventory().getItem(1));
		if(event.getInventory().getItem(2)==new util.GuiUtil().getItemStack("§f바지", 304,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".Leggings.Item", null);
		else
			monsterListYaml.set(MonsterName+".Leggings.Item", event.getInventory().getItem(2));
		if(event.getInventory().getItem(1)==new util.GuiUtil().getItemStack("§f부츠", 305,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".Boots.Item", null);
		else
			monsterListYaml.set(MonsterName+".Boots.Item", event.getInventory().getItem(3));
		if(event.getInventory().getItem(4)==new util.GuiUtil().getItemStack("§f무기", 267,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".Hand.Item", null);
		else
			monsterListYaml.set(MonsterName+".Hand.Item", event.getInventory().getItem(4));
		if(event.getInventory().getItem(5)==new util.GuiUtil().getItemStack("§f무기", 267,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".OffHand.Item", null);
		else
			monsterListYaml.set(MonsterName+".OffHand.Item", event.getInventory().getItem(5));
		monsterListYaml.saveConfig();
		event.getPlayer().sendMessage("§a[SYSTEM] : 아이템 설정이 저장되었습니다.");
		return;
	}

	public void MonsterTypeGUIClick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			int page = (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1].split(" / ")[0]));
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MonsterOptionSettingGUI(player, MonsterName);
			else if(slot == 48)//이전 페이지
				MonsterTypeGUI(player, MonsterName, page-2);
			else if(slot == 50)//다음 페이지
				MonsterTypeGUI(player, MonsterName, page);
			else
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				String type = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				type = type.substring(1, type.length()-1);
				monsterListYaml.set(MonsterName+".Type", type);
				monsterListYaml.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
		}
	}
}
