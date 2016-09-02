package GoldBigDragon_RPG.Monster;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.GUI.OPBoxGUI;
import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class MonsterGUI extends GUIutil
{

	public void MonsterListGUI(Player player, int page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "몬스터 목록 : " + (page+1));

		Object[] a= MobList.getKeys().toArray();

		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			String MonsterName =a[count].toString();
			String Lore=null;
			
			Lore = "%enter%"+ChatColor.WHITE+""+ChatColor.BOLD+" 이름 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Name")+"%enter%";
			Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" 타입 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Type")+"%enter%";
			Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" 스폰 바이옴 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Biome")+"%enter%";
			Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" 생명력 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".HP")+"%enter%";
			Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" 경험치 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".EXP")+"%enter%";
			Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" 드랍 금액 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".MIN_Money")+" ~ "+MobList.getInt(MonsterName+".MAX_Money")+"%enter%";
			Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" "+ServerOption.STR+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".STR")
			+ChatColor.GRAY+ " [물공 : " + d.CombatMinDamageGet(null, 0, MobList.getInt(MonsterName+".STR")) + " ~ " + d.CombatMaxDamageGet(null, 0, MobList.getInt(MonsterName+".STR")) + "]%enter%";
			Lore = Lore+ChatColor.GREEN+""+ChatColor.BOLD+" "+ServerOption.DEX+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEX")
			+ChatColor.GRAY+ " [활공 : " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, true) + " ~ " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
			Lore = Lore+ChatColor.DARK_AQUA+""+ChatColor.BOLD+" "+ServerOption.INT+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".INT")
			+ChatColor.GRAY+ " [폭공 : " + (MobList.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(MobList.getInt(MonsterName+".INT")/2.5)+"]%enter%";
			Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" "+ServerOption.WILL+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".WILL")
			+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" "+ServerOption.LUK+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".LUK")
			+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" 방어 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEF")+"%enter%";
			Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" 보호 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Protect")+"%enter%";
			Lore = Lore+ChatColor.BLUE+""+ChatColor.BOLD+" 마법 방어 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Magic_DEF")+"%enter%";
			Lore = Lore+ChatColor.DARK_BLUE+""+ChatColor.BOLD+" 마법 보호 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Magic_Protect")+"%enter%";
			Lore = Lore+"%enter%"+ChatColor.YELLOW+""+ChatColor.BOLD+"[Shift + 좌 클릭시 스폰알 지급]"+"%enter%"+ChatColor.RED+""+ChatColor.BOLD+"[Shift + 우 클릭시 몬스터 제거]";

			String[] scriptA = Lore.split("%enter%");
			for(byte counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			int id = 383;
			byte data = 0;
			switch(MobList.getString(MonsterName+".Type"))
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
			}
			
			Stack(ChatColor.WHITE+MonsterName, id, data, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "새 몬스터", 339,0,1,Arrays.asList(ChatColor.GRAY + "새로운 몬스터를 생성합니다."), 49, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void MonsterOptionSettingGUI(Player player,String MonsterName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");

		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "몬스터 설정");

		String Lore=null;			
		Lore = "%enter%"+ChatColor.WHITE+""+ChatColor.BOLD+" 이름 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Name")+"%enter%";
		Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" 타입 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Type")+"%enter%";
		Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" 스폰 바이옴 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Biome")+"%enter%";
		Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" 생명력 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".HP")+"%enter%";
		Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" 경험치 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".EXP")+"%enter%";
		Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" 드랍 금액 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".MIN_Money")+" ~ "+MobList.getInt(MonsterName+".MAX_Money")+"%enter%";
		Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" "+ServerOption.STR+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".STR")
		+ChatColor.GRAY+ " [물공 : " + d.CombatMinDamageGet(null, 0, MobList.getInt(MonsterName+".STR")) + " ~ " + d.CombatMaxDamageGet(null, 0, MobList.getInt(MonsterName+".STR")) + "]%enter%";
		Lore = Lore+ChatColor.GREEN+""+ChatColor.BOLD+" "+ServerOption.DEX+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEX")
		+ChatColor.GRAY+ " [활공 : " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, true) + " ~ " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
		Lore = Lore+ChatColor.DARK_AQUA+""+ChatColor.BOLD+" "+ServerOption.INT+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".INT")
		+ChatColor.GRAY+ " [폭공 : " + (MobList.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(MobList.getInt(MonsterName+".INT")/2.5)+"]%enter%";
		Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" "+ServerOption.WILL+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".WILL")
		+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
		Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" "+ServerOption.LUK+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".LUK")
		+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
		Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" 방어 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEF")+"%enter%";
		Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" 보호 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Protect")+"%enter%";
		Lore = Lore+ChatColor.BLUE+""+ChatColor.BOLD+" 마법 방어 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Magic_DEF")+"%enter%";
		Lore = Lore+ChatColor.DARK_BLUE+""+ChatColor.BOLD+" 마법 보호 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Magic_Protect")+"%enter%";

		
		String[] scriptA = Lore.split("%enter%");
		for(byte counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];

		Stack2(ChatColor.RED + "[    몬스터    ]", 52,0,1,null, 9, inv);
		Stack2(ChatColor.RED + "[    몬스터    ]", 52,0,1,null, 10, inv);
		Stack2(ChatColor.RED + "[    몬스터    ]", 52,0,1,null, 11, inv);
		Stack2(ChatColor.RED + "[    몬스터    ]", 52,0,1,null, 18, inv);
		Stack2(ChatColor.RED + "[    몬스터    ]", 52,0,1,null, 20, inv);
		Stack2(ChatColor.RED + "[    몬스터    ]", 52,0,1,null, 27, inv);
		Stack2(ChatColor.RED + "[    몬스터    ]", 52,0,1,null, 28, inv);
		Stack2(ChatColor.RED + "[    몬스터    ]", 52,0,1,null, 29, inv);
		int id = 383;
		byte data = 0;
		switch(MobList.getString(MonsterName+".Type"))
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
		}

		Stack2(ChatColor.WHITE + MonsterName, id,data,1,Arrays.asList(scriptA), 19, inv);
		
		
		Stack2(ChatColor.DARK_AQUA + "[    이름 변경    ]", 421,0,1,Arrays.asList(ChatColor.WHITE+"몬스터의 이름을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 이름    ]"," "+ChatColor.WHITE+MobList.getString(MonsterName+".Name"),""), 13, inv);
		Stack2(ChatColor.DARK_AQUA + "[    타입 변경    ]", 383,0,1,Arrays.asList(ChatColor.WHITE+"몬스터의 타입을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 타입    ]"," "+ChatColor.WHITE+MobList.getString(MonsterName+".Type"),""), 14, inv);

		data = 0;
		switch(MobList.getString(MonsterName+".Biome"))
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
		
		Stack2(ChatColor.DARK_AQUA + "[ 스폰 바이옴 변경 ]", id,data,1,Arrays.asList(ChatColor.WHITE+"몬스터가 등장하는",ChatColor.WHITE+"바이옴을 변경합니다.","",ChatColor.WHITE+"[    등장 바이옴    ]"," "+ChatColor.WHITE+MobList.getString(MonsterName+".Biome"),""), 15, inv);
		Stack2(ChatColor.DARK_AQUA + "[    생명력 변경    ]", 351,1,1,Arrays.asList(ChatColor.WHITE+"몬스터의 생명력을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 생명력    ]"," "+ChatColor.WHITE+""+MobList.getInt(MonsterName+".HP"),""), 16, inv);
		Stack2(ChatColor.DARK_AQUA + "[    경험치 변경    ]", 384,0,1,Arrays.asList(ChatColor.WHITE+"몬스터 사냥시 얻는",ChatColor.WHITE+"경험치 량을 변경합니다.","",ChatColor.WHITE+"[    현재 경험치    ]"," "+ChatColor.WHITE+""+MobList.getInt(MonsterName+".EXP"),""), 22, inv);
		Stack2(ChatColor.DARK_AQUA + "[  드랍 금액 변경  ]", 266,0,1,Arrays.asList(ChatColor.WHITE+"몬스터 사냥시 얻는",ChatColor.WHITE+"금액을 변경합니다.","",ChatColor.WHITE+"[    현재 금액    ]"," "+ChatColor.WHITE+""+MobList.getInt(MonsterName+".MIN_Money")+" ~ "+MobList.getInt(MonsterName+".MAX_Money"),""), 23, inv);
		Stack2(ChatColor.DARK_AQUA + "[    장비 변경    ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"몬스터의 장비를",ChatColor.WHITE+"설정 합니다.","",ChatColor.YELLOW+"[    좌클릭시 변경    ]",""), 24, inv);
		Stack2(ChatColor.DARK_AQUA + "[  장비 드랍률 변경  ]", 54,0,1,Arrays.asList(ChatColor.WHITE+"몬스터 사냥시 드랍되는",ChatColor.WHITE+"장비 확룰을 변경합니다.","",ChatColor.WHITE+"[    현재 드랍률    ]"," "+ChatColor.WHITE+"머리 : "+MobList.getInt(MonsterName+".Head.DropChance")/10.0+"%"
				," "+ChatColor.WHITE+"갑옷 : "+MobList.getInt(MonsterName+".Chest.DropChance")/10.0+"%"
				," "+ChatColor.WHITE+"바지 : "+MobList.getInt(MonsterName+".Leggings.DropChance")/10.0+"%"
				," "+ChatColor.WHITE+"신발 : "+MobList.getInt(MonsterName+".Boots.DropChance")/10.0+"%"
				," "+ChatColor.WHITE+"무기 : "+MobList.getInt(MonsterName+".Hand.DropChance")/10.0+"%","",ChatColor.YELLOW+"[    좌클릭시 변경   ]",""), 25, inv);
		Stack2(ChatColor.DARK_AQUA + "[  몬스터 스텟 변경  ]", 399,0,1,Arrays.asList(ChatColor.WHITE+"몬스터의 기본 스텟을",ChatColor.WHITE+"변경합니다.",""), 31, inv);
		Stack2(ChatColor.DARK_AQUA + "[  몬스터 방어 변경  ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"몬스터의 방어 및 보호를",ChatColor.WHITE+"변경합니다.",""), 32, inv);
		
		Lore = ChatColor.WHITE+"몬스터의 AI를 변경합니다.%enter%%enter%"+ChatColor.WHITE+"[    현재 AI    ]%enter%"+ChatColor.WHITE+MobList.getString(MonsterName+".AI")+"%enter%%enter%";
		
		switch(MobList.getString(MonsterName+".AI"))
		{
		case "일반 행동" :
			Lore = Lore+ChatColor.WHITE+"일반적인 행동을 합니다.%enter%";
			break;
			
		case "근접 선공" :
			Lore = Lore+ChatColor.WHITE+"무조건 근접 공격을합니다.%enter%"+
		ChatColor.WHITE+"근접 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%";break;
		case "근접 비선공" :
			Lore = Lore+ChatColor.WHITE+"공격받아야만 근접 공격을합니다.%enter%"+
		ChatColor.WHITE+"근접 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%";break;

		case "자폭 선공" :
			Lore = Lore+ChatColor.WHITE+"무조건 자폭 공격을합니다.%enter%"+
		ChatColor.WHITE+"자폭 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%"+
					ChatColor.RED+"[엔더맨/수호자/말/위더 소환 불가]%enter%"+
					ChatColor.GREEN+"[    크리퍼 소리가 납니다.    ]%enter%";break;
		case "자폭 비선공" :
			Lore = Lore+ChatColor.WHITE+"공격받아야만 자폭 공격을합니다.%enter%"+
		ChatColor.WHITE+"자폭 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%"+
					ChatColor.RED+"[엔더맨/수호자/말/위더 소환 불가]%enter%"+
					ChatColor.GREEN+"[    크리퍼 소리가 납니다.    ]%enter%";break;

		case "원거리 선공" :
			Lore = Lore+ChatColor.WHITE+"무조건 원거리 공격을합니다.%enter%"+
		ChatColor.WHITE+"원거리 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%"+
					ChatColor.GRAY+"[   스켈레톤 소리가 납니다.   ]%enter%"+
						ChatColor.YELLOW+"[몬스터 손에 활을 장착 시켜야 합니다.]%enter%";break;
		case "원거리 비선공" :
			Lore = Lore+ChatColor.WHITE+"공격받아야만 원거리 공격을합니다.%enter%"+
		ChatColor.WHITE+"원거리 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%"+
					ChatColor.GRAY+"[   스켈레톤 소리가 납니다.   ]%enter%"+
					ChatColor.YELLOW+"[몬스터 손에 활을 장착 시켜야 합니다.]%enter%";break;
		case "엘리트 원거리 선공" :
			Lore = Lore+ChatColor.WHITE+"무조건 원거리 공격을합니다.%enter%"+
		ChatColor.WHITE+"원거리 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%"+
					ChatColor.GRAY+"[   스켈레톤 소리가 납니다.   ]%enter%"+
						ChatColor.YELLOW+"[몬스터 손에 활을 장착 시켜야 합니다.]%enter%"+
			ChatColor.LIGHT_PURPLE+"[한 번에 5발의 화살을 발사합니다.]%enter%";break;
		case "엘리트 원거리 비선공" :
			Lore = Lore+ChatColor.WHITE+"공격받아야만 원거리 공격을합니다.%enter%"+
		ChatColor.WHITE+"원거리 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%"+
					ChatColor.GRAY+"[   스켈레톤 소리가 납니다.   ]%enter%"+
					ChatColor.YELLOW+"[몬스터 손에 활을 장착 시켜야 합니다.]%enter%"+
					ChatColor.LIGHT_PURPLE+"[한 번에 5발의 화살을 발사합니다.]%enter%";break;
		case "비행" :
			Lore = Lore+ChatColor.WHITE+"난잡하게 비행하며 돌아다닙니다..%enter%"+
		ChatColor.WHITE+"비행 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"공격하지 않습니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%"+
					ChatColor.RED+"[엔더맨/수호자/말 소환 불가]%enter%"+
					ChatColor.YELLOW+"[     박쥐 소리가 납니다.     ]%enter%";break;

		case "무뇌아" :
			Lore = Lore+ChatColor.WHITE+"공격및 이동을 하지 않습니다.%enter%"+
		ChatColor.WHITE+"무뇌아 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%";break;
		case "동물" :
			Lore = Lore+ChatColor.WHITE+"공격받을 경우 도망치기 바쁘며,%enter%"+ChatColor.WHITE+"절대로 공격하지 않습니다.%enter%"+
		ChatColor.WHITE+"동물 AI를 사용하는 몬스터는%enter%"+ChatColor.WHITE+"하늘을 날 수 없게 됩니다.%enter%"+
					ChatColor.RED+"[AI사용시 슬라임은 무조건 소형]%enter%";break;
		}
		
		scriptA = Lore.split("%enter%");
		for(byte counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];
		
		
		Stack2(ChatColor.DARK_AQUA + "[  몬스터 AI 변경  ]", 137,0,1,Arrays.asList(scriptA), 33, inv);
		Stack2(ChatColor.DARK_AQUA + "[    포션 효과    ]", 373,0,1,Arrays.asList(ChatColor.WHITE+"몬스터에게 포션 효과를",ChatColor.WHITE+"부여합니다.",""), 34, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+MonsterName), 53, inv);
		player.openInventory(inv);
	}
	
	public void MonsterPotionGUI(Player player, String MonsterName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "몬스터 포션");
		
		Stack2(ChatColor.DARK_AQUA + "[  재생  ]", 373,8193,1,Arrays.asList(ChatColor.WHITE+"[  포션 농도  ]",ChatColor.YELLOW+" "+MobList.getInt(MonsterName+".Potion.Regenerate")), 10, inv);
		Stack2(ChatColor.DARK_AQUA + "[  독  ]", 373,8196,1,Arrays.asList(ChatColor.WHITE+"[  포션 농도  ]",ChatColor.YELLOW+" "+MobList.getInt(MonsterName+".Potion.Poison")), 11, inv);
		Stack2(ChatColor.DARK_AQUA + "[  신속  ]", 373,8194,1,Arrays.asList(ChatColor.WHITE+"[  포션 농도  ]",ChatColor.YELLOW+" "+MobList.getInt(MonsterName+".Potion.Speed")), 12, inv);
		Stack2(ChatColor.DARK_AQUA + "[  구속  ]", 373,8234,1,Arrays.asList(ChatColor.WHITE+"[  포션 농도  ]",ChatColor.YELLOW+" "+MobList.getInt(MonsterName+".Potion.Slow")), 13, inv);
		Stack2(ChatColor.DARK_AQUA + "[  힘  ]", 373,8201,1,Arrays.asList(ChatColor.WHITE+"[  포션 농도  ]",ChatColor.YELLOW+" "+MobList.getInt(MonsterName+".Potion.Strength")), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "[  나약함  ]", 373,8232,1,Arrays.asList(ChatColor.WHITE+"[  포션 농도  ]",ChatColor.YELLOW+" "+MobList.getInt(MonsterName+".Potion.Weak")), 15, inv);
		Stack2(ChatColor.DARK_AQUA + "[  도약  ]", 373,8267,1,Arrays.asList(ChatColor.WHITE+"[  포션 농도  ]",ChatColor.YELLOW+" "+MobList.getInt(MonsterName+".Potion.JumpBoost")), 16, inv);

		if(MobList.getInt(MonsterName+".Potion.FireRegistance")!=0)
			Stack2(ChatColor.DARK_AQUA + "[  화염 저항  ]", 373,8227,1,Arrays.asList(ChatColor.GREEN+"[  포션 적용  ]"), 19, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "[  화염 저항  ]", 166,0,1,Arrays.asList(ChatColor.RED+"[  포션 미적용  ]"), 19, inv);
		if(MobList.getInt(MonsterName+".Potion.WaterBreath")!=0)
			Stack2(ChatColor.DARK_AQUA + "[  수중 호홉  ]", 373,8237,1,Arrays.asList(ChatColor.GREEN+"[  포션 적용  ]"), 20, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "[  수중 호홉  ]", 166,0,1,Arrays.asList(ChatColor.RED+"[  포션 미적용  ]"), 20, inv);
		if(MobList.getInt(MonsterName+".Potion.Invisible")!=0)
			Stack2(ChatColor.DARK_AQUA + "[  투명  ]", 373,8238,1,Arrays.asList(ChatColor.GREEN+"[  포션 적용  ]"), 21, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "[  투명  ]", 166,0,1,Arrays.asList(ChatColor.RED+"[  포션 미적용  ]"), 21, inv);
			

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+MonsterName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void MonsterListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		switch (event.getSlot())
		{
		case 45:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI OPGUI = new OPBoxGUI();
			OPGUI.OPBoxGUI_Main(player, (byte) 1);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MonsterListGUI(player, page-1);
			return;
		case 49://새 몬스터
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 새로운 몬스터 이름을 지어 주세요!");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "NM");
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MonsterListGUI(player, page+1);
			return;
		default :
			if(event.isLeftClick() == true&&event.isShiftClick()==false)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				MonsterOptionSettingGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			else if(event.isLeftClick() == true&&event.isShiftClick())
			{
				GoldBigDragon_RPG.Monster.MonsterSpawn MC = new GoldBigDragon_RPG.Monster.MonsterSpawn();
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				MC.SpawnEggGive(player,ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			else if(event.isRightClick()&&event.isShiftClick())
			{
				s.SP(player, Sound.LAVA_POP, 1.0F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
				MobList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				MobList.saveConfig();
				MonsterListGUI(player, page);
			}
			return;
		}
	}

	public void MonsterOptionSettingGUIClick(InventoryClickEvent event)
	{
		if(event.getInventory().getTitle().contains("장비")&&
			event.getInventory().getTitle().contains("설정"))
		{
			new GoldBigDragon_RPG.Monster.MonsterSpawn().ArmorGUIClick(event);
			return;
		}
		String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");

		Object_UserData u = new Object_UserData();
		
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 13://몹 이름 변경
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 보여주는 이름을 설정하세요!");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "CN");
				u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
			}
			return;
		case 14://몹 타입 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			if(event.isLeftClick())
			switch(MobList.getString(MonsterName+".Type"))
			{
				case "크리퍼" : MobList.set(MonsterName+".Type", "번개크리퍼");break;
				case "번개크리퍼" : MobList.set(MonsterName+".Type", "스켈레톤");break;
				case "스켈레톤" : MobList.set(MonsterName+".Type", "네더스켈레톤");break;
				case "네더스켈레톤" : MobList.set(MonsterName+".Type", "거미");break;
				case "거미" : MobList.set(MonsterName+".Type", "좀비");break;
				case "좀비" : MobList.set(MonsterName+".Type", "자이언트");break;
				case "자이언트" : MobList.set(MonsterName+".Type", "작은슬라임");break;
				case "작은슬라임" : MobList.set(MonsterName+".Type", "보통슬라임");break;
				case "보통슬라임" : MobList.set(MonsterName+".Type", "큰슬라임");break;
				case "큰슬라임" : MobList.set(MonsterName+".Type", "특대슬라임");break;
				case "특대슬라임" : MobList.set(MonsterName+".Type", "초대형슬라임");break;
				case "초대형슬라임" : MobList.set(MonsterName+".Type", "가스트");break;
				case "가스트" : MobList.set(MonsterName+".Type", "좀비피그맨");break;
				case "좀비피그맨" : MobList.set(MonsterName+".Type", "엔더맨");break;
				case "엔더맨" : MobList.set(MonsterName+".Type", "동굴거미");break;
				case "동굴거미" : MobList.set(MonsterName+".Type", "좀벌레");break;
				case "좀벌레" : MobList.set(MonsterName+".Type", "블레이즈");break;
				case "블레이즈" : MobList.set(MonsterName+".Type", "작은마그마큐브");break;
				case "작은마그마큐브" : MobList.set(MonsterName+".Type", "보통마그마큐브");break;
				case "보통마그마큐브" : MobList.set(MonsterName+".Type", "큰마그마큐브");break;
				case "큰마그마큐브" : MobList.set(MonsterName+".Type", "특대마그마큐브");break;
				case "특대마그마큐브" : MobList.set(MonsterName+".Type", "박쥐");break;
				case "박쥐" : MobList.set(MonsterName+".Type", "마녀");break;
				case "마녀" : MobList.set(MonsterName+".Type", "엔더진드기");break;
				case "엔더진드기" : MobList.set(MonsterName+".Type", "수호자");break;
				case "수호자" : MobList.set(MonsterName+".Type", "돼지");break;
				case "돼지" : MobList.set(MonsterName+".Type", "양");break;
				case "양" : MobList.set(MonsterName+".Type", "소");break;
				case "소" : MobList.set(MonsterName+".Type", "닭");break;
				case "닭" : MobList.set(MonsterName+".Type", "오징어");break;
				case "오징어" : MobList.set(MonsterName+".Type", "늑대"); break;
				case "늑대" : MobList.set(MonsterName+".Type", "버섯소"); break;
				case "버섯소" : MobList.set(MonsterName+".Type", "오셀롯"); break;
				case "오셀롯" : MobList.set(MonsterName+".Type", "말"); break;
				case "말" : MobList.set(MonsterName+".Type", "토끼"); break;
				case "토끼" : MobList.set(MonsterName+".Type", "주민"); break;
				case "주민" : MobList.set(MonsterName+".Type", "눈사람"); break;
				case "눈사람" : MobList.set(MonsterName+".Type", "골렘"); break;
				case "골렘" : MobList.set(MonsterName+".Type", "위더"); break;
				case "위더" : MobList.set(MonsterName+".Type", "엔더드래곤"); break;
				case "엔더드래곤" : MobList.set(MonsterName+".Type", "엔더크리스탈"); break;
				case "엔더크리스탈" : MobList.set(MonsterName+".Type", "크리퍼"); break;
				default : MobList.set(MonsterName+".Type", "좀비");break;
			}
			else
				switch(MobList.getString(MonsterName+".Type"))
				{
					case "크리퍼" : MobList.set(MonsterName+".Type", "엔더크리스탈");break;
					case "번개크리퍼" : MobList.set(MonsterName+".Type", "크리퍼");break;
					case "스켈레톤" : MobList.set(MonsterName+".Type", "번개크리퍼");break;
					case "네더스켈레톤" : MobList.set(MonsterName+".Type", "스켈레톤");break;
					case "거미" : MobList.set(MonsterName+".Type", "네더스켈레톤");break;
					case "좀비" : MobList.set(MonsterName+".Type", "거미");break;
					case "자이언트" : MobList.set(MonsterName+".Type", "좀비");break;
					case "작은슬라임" : MobList.set(MonsterName+".Type", "자이언트");break;
					case "보통슬라임" : MobList.set(MonsterName+".Type", "작은슬라임");break;
					case "큰슬라임" : MobList.set(MonsterName+".Type", "보통슬라임");break;
					case "특대슬라임" : MobList.set(MonsterName+".Type", "큰슬라임");break;
					case "초대형슬라임" : MobList.set(MonsterName+".Type", "특대슬라임");break;
					case "가스트" : MobList.set(MonsterName+".Type", "초대형슬라임");break;
					case "좀비피그맨" : MobList.set(MonsterName+".Type", "가스트");break;
					case "엔더맨" : MobList.set(MonsterName+".Type", "좀비피그맨");break;
					case "동굴거미" : MobList.set(MonsterName+".Type", "엔더맨");break;
					case "좀벌레" : MobList.set(MonsterName+".Type", "동굴거미");break;
					case "블레이즈" : MobList.set(MonsterName+".Type", "좀벌레");break;
					case "작은마그마큐브" : MobList.set(MonsterName+".Type", "블레이즈");break;
					case "보통마그마큐브" : MobList.set(MonsterName+".Type", "작은마그마큐브");break;
					case "큰마그마큐브" : MobList.set(MonsterName+".Type", "보통마그마큐브");break;
					case "특대마그마큐브" : MobList.set(MonsterName+".Type", "큰마그마큐브");break;
					case "박쥐" : MobList.set(MonsterName+".Type", "특대마그마큐브");break;
					case "마녀" : MobList.set(MonsterName+".Type", "박쥐");break;
					case "엔더진드기" : MobList.set(MonsterName+".Type", "마녀");break;
					case "수호자" : MobList.set(MonsterName+".Type", "엔더진드기");break;
					case "돼지" : MobList.set(MonsterName+".Type", "수호자");break;
					case "양" : MobList.set(MonsterName+".Type", "돼지");break;
					case "소" : MobList.set(MonsterName+".Type", "양");break;
					case "닭" : MobList.set(MonsterName+".Type", "소");break;
					case "오징어" : MobList.set(MonsterName+".Type", "닭"); break;
					case "늑대" : MobList.set(MonsterName+".Type", "오징어"); break;
					case "버섯소" : MobList.set(MonsterName+".Type", "늑대"); break;
					case "오셀롯" : MobList.set(MonsterName+".Type", "버섯소"); break;
					case "말" : MobList.set(MonsterName+".Type", "오셀롯"); break;
					case "토끼" : MobList.set(MonsterName+".Type", "말"); break;
					case "주민" : MobList.set(MonsterName+".Type", "토끼"); break;
					case "눈사람" : MobList.set(MonsterName+".Type", "주민"); break;
					case "골렘" : MobList.set(MonsterName+".Type", "눈사람"); break;
					case "위더" : MobList.set(MonsterName+".Type", "골렘"); break;
					case "엔더드래곤" : MobList.set(MonsterName+".Type", "위더"); break;
					case "엔더크리스탈" : MobList.set(MonsterName+".Type", "엔더드래곤"); break;
					default : MobList.set(MonsterName+".Type", "좀비");break;
				}
			MobList.saveConfig();
			MonsterOptionSettingGUI(player, MonsterName);
			break;
		case 15://스폰 바이옴 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			if(event.isLeftClick())
			switch(MobList.getString(MonsterName+".Biome"))
			{
				case "NULL" : MobList.set(MonsterName+".Biome", "BEACH");break;
				case "BEACH" : MobList.set(MonsterName+".Biome", "DESERT");break;
				case "DESERT" : MobList.set(MonsterName+".Biome", "EXTREME_HILLS");break;
				case "EXTREME_HILLS" : MobList.set(MonsterName+".Biome", "FOREST");break;
				case "FOREST" : MobList.set(MonsterName+".Biome", "HELL");break;
				case "HELL" : MobList.set(MonsterName+".Biome", "JUNGLE");break;
				case "JUNGLE" : MobList.set(MonsterName+".Biome", "MESA");break;
				case "MESA" : MobList.set(MonsterName+".Biome", "OCEAN");break;
				case "OCEAN" : MobList.set(MonsterName+".Biome", "PLAINS");break;
				case "PLAINS" : MobList.set(MonsterName+".Biome", "RIVER");break;
				case "RIVER" : MobList.set(MonsterName+".Biome", "SAVANNA");break;
				case "SAVANNA" : MobList.set(MonsterName+".Biome", "SKY");break;
				case "SKY" : MobList.set(MonsterName+".Biome", "SMALL_MOUNTAINS");break;
				case "SMALL_MOUNTAINS" : MobList.set(MonsterName+".Biome", "SWAMPLAND");break;
				case "SWAMPLAND" : MobList.set(MonsterName+".Biome", "TAIGA");break;
				case "TAIGA" : MobList.set(MonsterName+".Biome", "NULL");break;
				default : MobList.set(MonsterName+".Biome", "NULL");break;
			}
			else
				switch(MobList.getString(MonsterName+".Biome"))
				{
					case "NULL" : MobList.set(MonsterName+".Biome", "TAIGA");break;
					case "BEACH" : MobList.set(MonsterName+".Biome", "NULL");break;
					case "DESERT" : MobList.set(MonsterName+".Biome", "BEACH");break;
					case "EXTREME_HILLS" : MobList.set(MonsterName+".Biome", "DESERT");break;
					case "FOREST" : MobList.set(MonsterName+".Biome", "EXTREME_HILLS");break;
					case "HELL" : MobList.set(MonsterName+".Biome", "FOREST");break;
					case "JUNGLE" : MobList.set(MonsterName+".Biome", "HELL");break;
					case "MESA" : MobList.set(MonsterName+".Biome", "JUNGLE");break;
					case "OCEAN" : MobList.set(MonsterName+".Biome", "MESA");break;
					case "PLAINS" : MobList.set(MonsterName+".Biome", "OCEAN");break;
					case "RIVER" : MobList.set(MonsterName+".Biome", "PLAINS");break;
					case "SAVANNA" : MobList.set(MonsterName+".Biome", "RIVER");break;
					case "SKY" : MobList.set(MonsterName+".Biome", "SAVANNA");break;
					case "SMALL_MOUNTAINS" : MobList.set(MonsterName+".Biome", "SKY");break;
					case "SWAMPLAND" : MobList.set(MonsterName+".Biome", "SMALL_MOUNTAINS");break;
					case "TAIGA" : MobList.set(MonsterName+".Biome", "SWAMPLAND");break;
					default : MobList.set(MonsterName+".Biome", "NULL");break;
				}
			MobList.saveConfig();
			MonsterOptionSettingGUI(player, MonsterName);
			break;
		case 16://생명력 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 해당 몬스터의 생명력을 설정 해 주세요!");
			player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
			u.setType(player, "Monster");
			u.setString(player, (byte)1, "HP");
			u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
			return;
		case 22://경험치 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 해당 몬스터의 경험치를 설정 해 주세요!");
			player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
			u.setType(player, "Monster");
			u.setString(player, (byte)1, "EXP");
			u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
			return;
		case 23://드랍 금액 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 해당 몬스터가 드랍하는 최소 골드량을 설정해 주세요!");
			player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
			u.setType(player, "Monster");
			u.setString(player, (byte)1, "MIN_Money");
			u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
			return;
		case 24://장비 변경
			GoldBigDragon_RPG.Monster.MonsterSpawn MC = new GoldBigDragon_RPG.Monster.MonsterSpawn();
			s.SP(player, Sound.HORSE_ARMOR, 1.0F, 1.0F);
			MC.ArmorGUI(player, MonsterName);
			break;
		case 25://장비 드랍률 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 투구 드랍률을 설정해 주세요!");
			player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
			u.setType(player, "Monster");
			u.setString(player, (byte)1, "Head.DropChance");
			u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
			return;
		case 31://몬스터 스텟 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.GRAY+"("+ServerOption.STR+"은 몬스터의 물리 공격력을 상승시켜 줍니다.)");
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+ServerOption.STR+"을 설정해 주세요!");
			player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
			u.setType(player, "Monster");
			u.setString(player, (byte)1, "STR");
			u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
			return;
		case 32://몬스터 방어 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.GRAY+"(물리방어는 몬스터의 물리적인 방어력을 상승시켜 줍니다.)");
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 물리 방어력을 설정해 주세요!");
			player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
			u.setType(player, "Monster");
			u.setString(player, (byte)1, "DEF");
			u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
			return;
		case 33://몬스터 AI 변경
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			if(event.isLeftClick())
			switch(MobList.getString(MonsterName+".AI"))
			{
				case "일반 행동" : MobList.set(MonsterName+".AI", "근접 선공");break;
				case "근접 선공" : MobList.set(MonsterName+".AI", "근접 비선공");break;
				case "근접 비선공" : MobList.set(MonsterName+".AI", "자폭 선공");break;
				case "자폭 선공" : MobList.set(MonsterName+".AI", "자폭 비선공");break;
				case "자폭 비선공" : MobList.set(MonsterName+".AI", "원거리 선공");break;
				case "원거리 선공" : MobList.set(MonsterName+".AI", "원거리 비선공");break;
				case "원거리 비선공" : MobList.set(MonsterName+".AI", "엘리트 원거리 선공");break;
				case "엘리트 원거리 선공" : MobList.set(MonsterName+".AI", "엘리트 원거리 비선공");break;
				case "엘리트 원거리 비선공" : MobList.set(MonsterName+".AI", "비행");break;
				case "비행" : MobList.set(MonsterName+".AI", "동물");break;
				case "동물" : MobList.set(MonsterName+".AI", "무뇌아");break;
				case "무뇌아" : MobList.set(MonsterName+".AI", "일반 행동");break;
				default : MobList.set(MonsterName+".AI", "일반 행동");break;
			}
			else
			switch(MobList.getString(MonsterName+".AI"))
			{
				case "일반 행동" : MobList.set(MonsterName+".AI", "무뇌아");break;
				case "근접 선공" : MobList.set(MonsterName+".AI", "일반 행동");break;
				case "근접 비선공" : MobList.set(MonsterName+".AI", "근접 선공");break;
				case "자폭 선공" : MobList.set(MonsterName+".AI", "근접 비선공");break;
				case "자폭 비선공" : MobList.set(MonsterName+".AI", "자폭 선공");break;
				case "원거리 선공" : MobList.set(MonsterName+".AI", "자폭 비선공");break;
				case "원거리 비선공" : MobList.set(MonsterName+".AI", "원거리 선공");break;
				case "엘리트 원거리 선공" : MobList.set(MonsterName+".AI", "원거리 비선공");break;
				case "엘리트 원거리 비선공" : MobList.set(MonsterName+".AI", "엘리트 원거리 선공");break;
				case "비행" : MobList.set(MonsterName+".AI", "엘리트 원거리 비선공");break;
				case "동물" : MobList.set(MonsterName+".AI", "비행");break;
				case "무뇌아" : MobList.set(MonsterName+".AI", "동물");break;
				default : MobList.set(MonsterName+".AI", "일반 행동");break;
			}
		MobList.saveConfig();
		MonsterOptionSettingGUI(player, MonsterName);
		return;

		case 34://몬스터 포션 효과
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			MonsterPotionGUI(player, MonsterName);
			return;
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MonsterListGUI(player, 0);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void MonsterPotionGUIClick(InventoryClickEvent event)
	{
		String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");

		Object_UserData u = new Object_UserData();
		
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getSlot()>=10&&event.getSlot()<=16)
		{
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			u.setType(player, "Monster");
			u.setString(player, (byte)1, "Potion");
			u.setString(player, (byte)3, MonsterName);
		}
		switch (event.getSlot())
		{
		case 10://재생
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 재생 효과는 몇 으로 설정하실건가요?");
			player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
			u.setString(player, (byte)2, "Regenerate");
			return;
		case 11://독
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 독 효과는 몇 으로 설정하실건가요?");
			player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
			u.setString(player, (byte)2, "Poision");
			return;
		case 12://신속
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 신속 효과는 몇 으로 설정하실건가요?");
			player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
			u.setString(player, (byte)2, "Speed");
			return;
		case 13://구속
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 구속 효과는 몇 으로 설정하실건가요?");
			player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
			u.setString(player, (byte)2, "Slow");
			return;
		case 14://힘
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 힘 효과는 몇 으로 설정하실건가요?");
			player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
			u.setString(player, (byte)2, "Strength");
			return;
		case 15://나약함
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 나약함 효과는 몇 으로 설정하실건가요?");
			player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
			u.setString(player, (byte)2, "Weak");
			return;
		case 16://도약
			player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 도약 효과는 몇 으로 설정하실건가요?");
			player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
			u.setString(player, (byte)2, "Jump");
			return;
			
		case 19://화염 저항
			if(MobList.getInt(MonsterName+".Potion.FireRegistance")==0)
				MobList.set(MonsterName+".Potion.FireRegistance", 1);
			else
				MobList.set(MonsterName+".Potion.FireRegistance", 0);
			MobList.saveConfig();
			s.SP(player, Sound.DRINK, 1.0F, 1.0F);
			MonsterPotionGUI(player, MonsterName);
			return;
		case 20://수중 호홉
			if(MobList.getInt(MonsterName+".Potion.WaterBreath")==0)
				MobList.set(MonsterName+".Potion.WaterBreath", 1);
			else
				MobList.set(MonsterName+".Potion.WaterBreath", 0);
			MobList.saveConfig();
			s.SP(player, Sound.DRINK, 1.0F, 1.0F);
			MonsterPotionGUI(player, MonsterName);
			return;
		case 21://투명
			if(MobList.getInt(MonsterName+".Potion.Invisible")==0)
				MobList.set(MonsterName+".Potion.Invisible", 1);
			else
				MobList.set(MonsterName+".Potion.Invisible", 0);
			MobList.saveConfig();
			s.SP(player, Sound.DRINK, 1.0F, 1.0F);
			MonsterPotionGUI(player, MonsterName);
			return;
			
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MonsterOptionSettingGUI(player, MonsterName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
}
