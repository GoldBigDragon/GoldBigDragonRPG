package GBD_RPG.Monster;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GBD_RPG.Admin.OPbox_GUI;
import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_GUI;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Monster_GUI extends Util_GUI
{
	public void MonsterListGUI(Player player, int page)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
		GBD_RPG.Battle.Battle_Calculator d = new GBD_RPG.Battle.Battle_Calculator();
		String UniqueCode = "§0§0§8§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 목록 : " + (page+1));

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
			Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" "+Main_ServerOption.STR+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".STR")
			+ChatColor.GRAY+ " [물공 : " + d.CombatDamageGet(null, 0, MobList.getInt(MonsterName+".STR"), true) + " ~ " + d.CombatDamageGet(null, 0, MobList.getInt(MonsterName+".STR"), false) + "]%enter%";
			Lore = Lore+ChatColor.GREEN+""+ChatColor.BOLD+" "+Main_ServerOption.DEX+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEX")
			+ChatColor.GRAY+ " [활공 : " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, true) + " ~ " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
			Lore = Lore+ChatColor.DARK_AQUA+""+ChatColor.BOLD+" "+Main_ServerOption.INT+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".INT")
			+ChatColor.GRAY+ " [폭공 : " + (MobList.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(MobList.getInt(MonsterName+".INT")/2.5)+"]%enter%";
			Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" "+Main_ServerOption.WILL+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".WILL")
			+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" "+Main_ServerOption.LUK+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".LUK")
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
				//case "휴먼" : id=379; data = 3; break;
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
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");

		GBD_RPG.Battle.Battle_Calculator d = new GBD_RPG.Battle.Battle_Calculator();
		String UniqueCode = "§0§0§8§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 설정");

		String Lore=null;			
		Lore = "%enter%"+ChatColor.WHITE+""+ChatColor.BOLD+" 이름 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Name")+"%enter%";
		Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" 타입 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Type")+"%enter%";
		Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" 스폰 바이옴 : "+ChatColor.WHITE+MobList.getString(MonsterName+".Biome")+"%enter%";
		Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" 생명력 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".HP")+"%enter%";
		Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" 경험치 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".EXP")+"%enter%";
		Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" 드랍 금액 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".MIN_Money")+" ~ "+MobList.getInt(MonsterName+".MAX_Money")+"%enter%";
		Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" "+Main_ServerOption.STR+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".STR")
		+ChatColor.GRAY+ " [물공 : " + d.CombatDamageGet(null, 0, MobList.getInt(MonsterName+".STR"), true) + " ~ " + d.CombatDamageGet(null, 0, MobList.getInt(MonsterName+".STR"), false) + "]%enter%";
		Lore = Lore+ChatColor.GREEN+""+ChatColor.BOLD+" "+Main_ServerOption.DEX+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEX")
		+ChatColor.GRAY+ " [활공 : " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, true) + " ~ " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
		Lore = Lore+ChatColor.DARK_AQUA+""+ChatColor.BOLD+" "+Main_ServerOption.INT+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".INT")
		+ChatColor.GRAY+ " [폭공 : " + (MobList.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(MobList.getInt(MonsterName+".INT")/2.5)+"]%enter%";
		Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" "+Main_ServerOption.WILL+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".WILL")
		+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
		Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" "+Main_ServerOption.LUK+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".LUK")
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
		String Type = MobList.getString(MonsterName+".Type");
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
		if(Type.compareTo("초대형슬라임")==0||Type.compareTo("특대슬라임")==0||Type.compareTo("큰슬라임")==0||
		Type.compareTo("보통슬라임")==0||Type.compareTo("작은슬라임")==0||Type.compareTo("큰마그마큐브")==0||Type.compareTo("특대마그마큐브")==0||Type.compareTo("보통마그마큐브")==0||
		Type.compareTo("마그마큐브")==0||Type.compareTo("작은마그마큐브")==0||Type.compareTo("가스트")==0||Type.compareTo("위더")==0
		||Type.compareTo("엔더드래곤")==0||Type.compareTo("셜커")==0||Type.compareTo("양")==0||Type.compareTo("소")==0
		||Type.compareTo("돼지")==0||Type.compareTo("말")==0||Type.compareTo("박쥐")==0||Type.compareTo("토끼")==0
		||Type.compareTo("오셀롯")==0||Type.compareTo("늑대")==0||Type.compareTo("닭")==0||Type.compareTo("버섯소")==0
		||Type.compareTo("오징어")==0||Type.compareTo("주민")==0||Type.compareTo("눈사람")==0||Type.compareTo("골렘")==0
		)
		Lore = Lore + ChatColor.RED + "[현재 선택 된 몬스터 타입은%enter%"+ChatColor.RED+"무조건 근접 AI만을 사용합니다.]";
		else
		{
			switch(MobList.getString(MonsterName+".AI"))
			{
			case "일반 행동" :
				Lore = Lore+ChatColor.WHITE+"일반적인 행동을 합니다.%enter%";
				break;
			case "선공" :
				Lore = Lore+ChatColor.WHITE+"무조건 선제 공격을합니다.%enter%";break;
			case "비선공" :
				Lore = Lore+ChatColor.WHITE+"공격받기 전에는 공격하지 않습니다.%enter%";break;
			case "무뇌아" :
				Lore = Lore+ChatColor.WHITE+"공격및 이동을 하지 않습니다.%enter%";break;
			case "동물" :
				Lore = Lore+ChatColor.WHITE+"공격받을 경우 도망치기 바쁘며,%enter%"+ChatColor.WHITE+"절대로 공격하지 않습니다.%enter%";break;
			}
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
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
		String UniqueCode = "§0§0§8§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 포션");
		
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

	public void ArmorGUI(Player player, String mob)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		String UniqueCode = "§1§0§8§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0몬스터 장비 설정");
		YamlManager MobList  = YC.getNewConfig("Monster/MonsterList.yml");

		if(MobList.contains(mob + ".Head.Item")==true&&
			MobList.getItemStack(mob + ".Head.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(0, MobList.getItemStack(mob + ".Head.Item"));
		else
			Stack(ChatColor.WHITE + "머리", 302,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요."), (byte)0, inv);

		if(MobList.contains(mob + ".Chest.Item")==true&&
				MobList.getItemStack(mob + ".Chest.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(1, MobList.getItemStack(mob + ".Chest.Item"));
		else
			Stack(ChatColor.WHITE + "갑옷", 303,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요."), (byte)1, inv);

		if(MobList.contains(mob + ".Leggings.Item")==true&&
				MobList.getItemStack(mob + ".Leggings.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(2, MobList.getItemStack(mob + ".Leggings.Item"));
		else
			Stack(ChatColor.WHITE + "바지", 304,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요."), (byte)2, inv);

		if(MobList.contains(mob + ".Boots.Item")==true&&
		MobList.getItemStack(mob + ".Boots.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(3, MobList.getItemStack(mob + ".Boots.Item"));
		else
			Stack(ChatColor.WHITE + "부츠", 305,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요."), (byte)3, inv);

		if(MobList.contains(mob + ".Hand.Item")==true&&
		MobList.getItemStack(mob + ".Hand.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(4, MobList.getItemStack(mob + ".Hand.Item"));
		else
			Stack(ChatColor.WHITE + "오른손", 267,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요."), (byte)4, inv);

		if(MobList.contains(mob + ".OffHand.Item")==true&&
		MobList.getItemStack(mob + ".OffHand.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(5, MobList.getItemStack(mob + ".OffHand.Item"));
		else
			Stack(ChatColor.WHITE + "왼손", 267,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요."), (byte)5, inv);
		
		Stack(ChatColor.WHITE + mob, 416,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY + mob+"의 현재 장비입니다." ), (byte)8, inv);
		Stack(ChatColor.WHITE + "", 30,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY +"이곳에는 아이템을",ChatColor.GRAY +"올려두지 마세요."), (byte)7, inv);
		Stack(ChatColor.WHITE + "", 30,(byte)0,(byte)1,Arrays.asList(ChatColor.GRAY +"이곳에는 아이템을",ChatColor.GRAY +"올려두지 마세요."), (byte)6, inv);
		
		player.openInventory(inv);
		return;
	}

	public void MonsterTypeGUI(Player player, String MonsterName)
	{
		String UniqueCode = "§1§0§8§0§b§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 타입 설정");

		Stack2("§2§l[좀비]", 367,0,1,null, 0, inv);
		Stack2("§2§l[자이언트]", 367,0,2,null, 1, inv);
		Stack2("§d§l[좀비 피그맨]", 283,0,1,null, 2, inv);
		Stack2("§a§l[크리퍼]", 289,0,1,null, 3, inv);
		Stack2("§a§l[번개 크리퍼]", 289,0,1,null, 4, inv);
		Stack2("§f§l[스켈레톤]", 352,0,1,null, 5, inv);
		Stack2("§8§l[위더 스켈레톤]", 263,0,1,null, 6, inv);
		Stack2("§8§l[위더]", 399,0,1,null, 7, inv);
		Stack2("§7§l[거미]", 287,0,1,null, 8, inv);
		Stack2("§7§l[동굴거미]", 287,0,2,null, 9, inv);
		Stack2("§a§l[작은 슬라임]", 341,0,1,null, 10, inv);
		Stack2("§a§l[보통 슬라임]", 341,0,2,null, 11, inv);
		Stack2("§a§l[큰 슬라임]", 341,0,4,null, 12, inv);
		Stack2("§a§l[특대 슬라임]", 341,0,8,null, 13, inv);
		Stack2("§a§l[초대형 슬라임]", 341,0,16,null, 14, inv);
		Stack2("§7§l[작은 마그마큐브]", 378,0,1,null, 15, inv);
		Stack2("§7§l[보통 마그마큐브]", 378,0,1,null, 16, inv);
		Stack2("§7§l[큰 마그마큐브]", 378,0,1,null, 17, inv);
		Stack2("§7§l[특대 마그마큐브]", 378,0,1,null, 18, inv);
		Stack2("§7§l[초대형 마그마큐브]", 378,0,1,null, 19, inv);
		Stack2("§8§l[박쥐]", 362,0,1,null, 20, inv);
		Stack2("§f§l[가스트]", 370,0,1,null, 21, inv);
		Stack2("§e§l[블레이즈]", 369,0,1,null, 22, inv);
		Stack2("§7§l[좀벌레]", 1,0,1,null, 23, inv);
		Stack2("§5§l[엔더 진드기]", 432,0,1,null, 24, inv);
		Stack2("§a§l[주민]", 388,0,1,null, 25, inv);
		Stack2("§5§l[마녀]", 438,0,1,null, 26, inv);
		Stack2("§3§l[가디언]", 409,0,1,null, 27, inv);
		Stack2("§8§l[엔더맨]", 368,0,1,null, 28, inv);
		Stack2("§5§l[셜커]", 443,0,1,null, 29, inv);
		Stack2("§8§l[엔더드래곤]", 122,0,1,null, 30, inv);
		Stack2("§d§l[돼지]", 319,0,1,null, 31, inv);
		Stack2("§f§l[양]", 423,0,1,null, 32, inv);
		Stack2("§7§l[소]", 363,0,1,null, 33, inv);
		Stack2("§c§l[버섯 소]", 40,0,1,null, 34, inv);
		Stack2("§f§l[닭]", 365,0,1,null, 35, inv);
		Stack2("§8§l[오징어]", 351,0,1,null, 36, inv);
		Stack2("§7§l[늑대]", 280,0,1,null, 37, inv);
		Stack2("§e§l[오셀롯]", 349,0,1,null, 38, inv);
		Stack2("§f§l[눈사람]", 332,0,1,null, 39, inv);
		Stack2("§f§l[철골렘]", 265,0,1,null, 40, inv);
		Stack2("§f§l[토끼]", 411,0,1,null, 41, inv);
		Stack2("§f§l[북극곰]", 349,1,1,null, 42, inv);
		Stack2("§6§l[말]", 417,0,1,null, 43, inv);
		Stack2("§5§l[엔더 크리스탈]", 426,0,1,null, 44, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+MonsterName), 53, inv);
		player.openInventory(inv);
	}
	

	public void MonsterListGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		if(slot == 53)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new OPbox_GUI().OPBoxGUI_Main(player, (byte) 1);
			else if(slot == 48)//이전 페이지
				MonsterListGUI(player, page-1);
			else if(slot == 49)//새 몬스터
			{
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[몬스터] : 새로운 몬스터 이름을 지어 주세요!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "NM");
			}
			else if(slot == 50)//다음 페이지
				MonsterListGUI(player, page+1);
			else
			{
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
					MonsterOptionSettingGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isLeftClick() == true&&event.isShiftClick())
					new GBD_RPG.Monster.Monster_Spawn().SpawnEggGive(player,ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isRightClick()&&event.isShiftClick())
				{
					s.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
					MobList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					MobList.saveConfig();
					MonsterListGUI(player, page);
				}
			}
		}
	}

	public void MonsterOptionSettingGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		if(slot == 53)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MonsterListGUI(player, 0);
			else if(slot == 14)//몹 타입 변경
				MonsterTypeGUI(player, MonsterName);
			else if(slot == 15)//스폰 바이옴 변경
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
				String Type = MobList.getString(MonsterName+".Biome");
				if(Type.compareTo("NULL")==0)
					MobList.set(MonsterName+".Biome", "BEACH");
				else if(Type.compareTo("BEACH")==0)
					MobList.set(MonsterName+".Biome", "DESERT");
				else if(Type.compareTo("DESERT")==0)
					MobList.set(MonsterName+".Biome", "EXTREME_HILLS");
				else if(Type.compareTo("EXTREME_HILLS")==0)
					MobList.set(MonsterName+".Biome", "FOREST");
				else if(Type.compareTo("FOREST")==0)
					MobList.set(MonsterName+".Biome", "HELL");
				else if(Type.compareTo("HELL")==0)
					MobList.set(MonsterName+".Biome", "JUNGLE");
				else if(Type.compareTo("JUNGLE")==0)
					MobList.set(MonsterName+".Biome", "MESA");
				else if(Type.compareTo("MESA")==0)
					MobList.set(MonsterName+".Biome", "OCEAN");
				else if(Type.compareTo("OCEAN")==0)
					MobList.set(MonsterName+".Biome", "PLAINS");
				else if(Type.compareTo("PLAINS")==0)
					MobList.set(MonsterName+".Biome", "RIVER");
				else if(Type.compareTo("RIVER")==0)
					MobList.set(MonsterName+".Biome", "SAVANNA");
				else if(Type.compareTo("SAVANNA")==0)
					MobList.set(MonsterName+".Biome", "SKY");
				else if(Type.compareTo("SKY")==0)
					MobList.set(MonsterName+".Biome", "SMALL_MOUNTAINS");
				else if(Type.compareTo("SMALL_MOUNTAINS")==0)
					MobList.set(MonsterName+".Biome", "SWAMPLAND");
				else if(Type.compareTo("SWAMPLAND")==0)
					MobList.set(MonsterName+".Biome", "TAIGA");
				else if(Type.compareTo("TAIGA")==0)
					MobList.set(MonsterName+".Biome", "NULL");
				else
					MobList.set(MonsterName+".Biome", "NULL");
				MobList.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
			else if(slot == 33)
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
				String Type = MobList.getString(MonsterName+".AI");
				if(Type.compareTo("일반 행동")==0)
					MobList.set(MonsterName+".AI", "선공");
				else if(Type.compareTo("선공")==0)
					MobList.set(MonsterName+".AI", "비선공");
				else if(Type.compareTo("비선공")==0)
					MobList.set(MonsterName+".AI", "동물");
				else if(Type.compareTo("동물")==0)
					MobList.set(MonsterName+".AI", "무뇌아");
				else if(Type.compareTo("무뇌아")==0)
					MobList.set(MonsterName+".AI", "일반 행동");
				else
					MobList.set(MonsterName+".AI", "일반 행동");
				MobList.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
			else if(slot == 24)//장비 변경
			{
				s.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.0F);
				ArmorGUI(player, MonsterName);
			}
			else if(slot == 34)//몬스터 포션 효과
				MonsterPotionGUI(player, MonsterName);
			else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
			{
				UserData_Object u = new UserData_Object();
				player.closeInventory();
				u.setType(player, "Monster");
				u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
				if(slot==13)//몹 이름 변경
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 보여주는 이름을 설정하세요!");
					player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
					ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
							ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
					ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
							ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
					u.setString(player, (byte)1, "CN");
				}
				else if(slot == 16)//생명력 변경
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 해당 몬스터의 생명력을 설정 해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "HP");
				}
				else if(slot == 22)//경험치 변경
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 해당 몬스터의 경험치를 설정 해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "EXP");
				}
				else if(slot == 23)//드랍 금액 변경
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 해당 몬스터가 드랍하는 최소 골드량을 설정해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "MIN_Money");
				}
				else if(slot == 25)//장비 드랍률 변경
				{
					player.sendMessage(ChatColor.GRAY+"(확률 계산 : 1000 = 100%, 1 = 0.1%)");
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 투구 드랍률을 설정해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 1000)");
					u.setString(player, (byte)1, "Head.DropChance");
				}
				else if(slot == 31)//몬스터 스텟 변경
				{
					player.sendMessage(ChatColor.GRAY+"("+Main_ServerOption.STR+"은 몬스터의 물리 공격력을 상승시켜 줍니다.)");
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 "+Main_ServerOption.STR+"을 설정해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "STR");
				}
				else if(slot == 32)//몬스터 방어 변경
				{
					player.sendMessage(ChatColor.GRAY+"(물리방어는 몬스터의 물리적인 방어력을 상승시켜 줍니다.)");
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 물리 방어력을 설정해 주세요!");
					player.sendMessage(ChatColor.DARK_AQUA+"(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "DEF");
				}
			}
		}
	}

	public void MonsterPotionGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		if(slot == 53)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MonsterOptionSettingGUI(player, MonsterName);
			else if(slot >= 10 && slot <= 16)
			{
				UserData_Object u = new UserData_Object();
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "Potion");
				u.setString(player, (byte)3, MonsterName);
				if(slot == 10)
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 재생 효과는 몇 으로 설정하실건가요?");
					player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
					u.setString(player, (byte)2, "Regenerate");
				}
				else if(slot == 11)
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 독 효과는 몇 으로 설정하실건가요?");
					player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
					u.setString(player, (byte)2, "Poision");
				}
				else if(slot == 12)
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 신속 효과는 몇 으로 설정하실건가요?");
					player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
					u.setString(player, (byte)2, "Speed");
				}
				else if(slot == 13)
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 구속 효과는 몇 으로 설정하실건가요?");
					player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
					u.setString(player, (byte)2, "Slow");
				}
				else if(slot == 14)
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 힘 효과는 몇 으로 설정하실건가요?");
					player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
					u.setString(player, (byte)2, "Strength");
				}
				else if(slot == 15)
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 나약함 효과는 몇 으로 설정하실건가요?");
					player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
					u.setString(player, (byte)2, "Weak");
				}
				else if(slot == 16)
				{
					player.sendMessage(ChatColor.GREEN+"[몬스터] : 몬스터의 도약 효과는 몇 으로 설정하실건가요?");
					player.sendMessage(ChatColor.YELLOW+"(0 ~ 100)");
					u.setString(player, (byte)2, "Jump");
				}
			}
			else if(slot >= 19)
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
				if(slot == 19)//화염 저항
				{
					if(MobList.getInt(MonsterName+".Potion.FireRegistance")==0)
						MobList.set(MonsterName+".Potion.FireRegistance", 1);
					else
						MobList.set(MonsterName+".Potion.FireRegistance", 0);
				}
				else if(slot == 20)//수중 호홉
				{
					if(MobList.getInt(MonsterName+".Potion.WaterBreath")==0)
						MobList.set(MonsterName+".Potion.WaterBreath", 1);
					else
						MobList.set(MonsterName+".Potion.WaterBreath", 0);
				}
				else if(slot == 21)//투명
				{
					if(MobList.getInt(MonsterName+".Potion.Invisible")==0)
						MobList.set(MonsterName+".Potion.Invisible", 1);
					else
						MobList.set(MonsterName+".Potion.Invisible", 0);
				}
				MobList.saveConfig();
				s.SP(player, Sound.ENTITY_GENERIC_DRINK, 1.0F, 1.0F);
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
				if(event.getCurrentItem().getItemMeta().getLore().get(0).equals(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요."))
					event.getInventory().remove(event.getCurrentItem());
		return;
	}
	
	public void ArmorGUIClose(InventoryCloseEvent event)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);

		YamlManager Monster  = YC.getNewConfig("Monster/MonsterList.yml");
		String MonsterName = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName().toString());
		if(event.getInventory().getItem(0)==new GBD_RPG.Util.Util_GUI().getItemStack(ChatColor.WHITE + "머리", 302,0,1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요.")))
			Monster.set(MonsterName+".Head.Item", null);
		else
			Monster.set(MonsterName+".Head.Item", event.getInventory().getItem(0));
		
		if(event.getInventory().getItem(1)==new GBD_RPG.Util.Util_GUI().getItemStack(ChatColor.WHITE + "갑옷", 303,0,1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요.")))
					Monster.set(MonsterName+".Chest.Item", null);
		else
			Monster.set(MonsterName+".Chest.Item", event.getInventory().getItem(1));
		if(event.getInventory().getItem(2)==new GBD_RPG.Util.Util_GUI().getItemStack(ChatColor.WHITE + "바지", 304,0,1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요.")))
			Monster.set(MonsterName+".Leggings.Item", null);
		else
			Monster.set(MonsterName+".Leggings.Item", event.getInventory().getItem(2));
		if(event.getInventory().getItem(1)==new GBD_RPG.Util.Util_GUI().getItemStack(ChatColor.WHITE + "부츠", 305,0,1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요.")))
			Monster.set(MonsterName+".Boots.Item", null);
		else
			Monster.set(MonsterName+".Boots.Item", event.getInventory().getItem(3));
		if(event.getInventory().getItem(4)==new GBD_RPG.Util.Util_GUI().getItemStack(ChatColor.WHITE + "무기", 267,0,1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요.")))
			Monster.set(MonsterName+".Hand.Item", null);
		else
			Monster.set(MonsterName+".Hand.Item", event.getInventory().getItem(4));
		if(event.getInventory().getItem(5)==new GBD_RPG.Util.Util_GUI().getItemStack(ChatColor.WHITE + "무기", 267,0,1,Arrays.asList(ChatColor.GRAY + "이곳에 아이템을 넣어 주세요.")))
			Monster.set(MonsterName+".OffHand.Item", null);
		else
			Monster.set(MonsterName+".OffHand.Item", event.getInventory().getItem(5));
		Monster.saveConfig();
		event.getPlayer().sendMessage(ChatColor.GREEN + "[SYSTEM] : 아이템 설정이 저장되었습니다.");
		return;
	}

	public void MonsterTypeGUIClick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		if(slot == 53)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MonsterOptionSettingGUI(player, MonsterName);
			else
			{

				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
				if(slot == 0)
					MobList.set(MonsterName+".Type", "좀비");
				else if(slot == 1)
					MobList.set(MonsterName+".Type", "자이언트");
				else if(slot == 2)
					MobList.set(MonsterName+".Type", "좀비피그맨");
				else if(slot == 3)
					MobList.set(MonsterName+".Type", "크리퍼");
				else if(slot == 4)
					MobList.set(MonsterName+".Type", "번개크리퍼");
				else if(slot == 5)
					MobList.set(MonsterName+".Type", "스켈레톤");
				else if(slot == 6)
					MobList.set(MonsterName+".Type", "네더스켈레톤");
				else if(slot == 7)
					MobList.set(MonsterName+".Type", "위더");
				else if(slot == 8)
					MobList.set(MonsterName+".Type", "거미");
				else if(slot == 9)
					MobList.set(MonsterName+".Type", "동굴거미");
				else if(slot == 10)
					MobList.set(MonsterName+".Type", "작은슬라임");
				else if(slot == 11)
					MobList.set(MonsterName+".Type", "보통슬라임");
				else if(slot == 12)
					MobList.set(MonsterName+".Type", "큰슬라임");
				else if(slot == 13)
					MobList.set(MonsterName+".Type", "특대슬라임");
				else if(slot == 14)
					MobList.set(MonsterName+".Type", "초대형슬라임");
				else if(slot == 15)
					MobList.set(MonsterName+".Type", "작은마그마큐브");
				else if(slot == 16)
					MobList.set(MonsterName+".Type", "보통마그마큐브");
				else if(slot == 17)
					MobList.set(MonsterName+".Type", "큰마그마큐브");
				else if(slot == 18)
					MobList.set(MonsterName+".Type", "특대마그마큐브");
				else if(slot == 19)
					MobList.set(MonsterName+".Type", "초대형마그마큐브");
				else if(slot == 20)
					MobList.set(MonsterName+".Type", "박쥐");
				else if(slot == 21)
					MobList.set(MonsterName+".Type", "가스트");
				else if(slot == 22)
					MobList.set(MonsterName+".Type", "블레이즈");
				else if(slot == 23)
					MobList.set(MonsterName+".Type", "좀벌레");
				else if(slot == 24)
					MobList.set(MonsterName+".Type", "엔더진드기");
				else if(slot == 25)
					MobList.set(MonsterName+".Type", "주민");
				else if(slot == 26)
					MobList.set(MonsterName+".Type", "마녀");
				else if(slot == 27)
					MobList.set(MonsterName+".Type", "수호자");
				else if(slot == 28)
					MobList.set(MonsterName+".Type", "엔더맨");
				else if(slot == 29)
					MobList.set(MonsterName+".Type", "셜커");
				else if(slot == 30)
					MobList.set(MonsterName+".Type", "엔더드래곤");
				else if(slot == 31)
					MobList.set(MonsterName+".Type", "돼지");
				else if(slot == 32)
					MobList.set(MonsterName+".Type", "양");
				else if(slot == 33)
					MobList.set(MonsterName+".Type", "소");
				else if(slot == 34)
					MobList.set(MonsterName+".Type", "버섯소");
				else if(slot == 35)
					MobList.set(MonsterName+".Type", "닭");
				else if(slot == 36)
					MobList.set(MonsterName+".Type", "오징어");
				else if(slot == 37)
					MobList.set(MonsterName+".Type", "늑대");
				else if(slot == 38)
					MobList.set(MonsterName+".Type", "오셀롯");
				else if(slot == 39)
					MobList.set(MonsterName+".Type", "눈사람");
				else if(slot == 40)
					MobList.set(MonsterName+".Type", "골렘");
				else if(slot == 41)
					MobList.set(MonsterName+".Type", "토끼");
				else if(slot == 42)
					MobList.set(MonsterName+".Type", "북극곰");
				else if(slot == 43)
					MobList.set(MonsterName+".Type", "말");
				else if(slot == 44)
					MobList.set(MonsterName+".Type", "엔더크리스탈");
				MobList.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
		}
	}
}
