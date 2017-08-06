package area;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import admin.OPbox_GUI;
import battle.Battle_Calculator;
import effect.SoundEffect;
import main.Main_ServerOption;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;



public class Area_GUI extends Util_GUI
{
	public void AreaListGUI(Player player, short page)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String UniqueCode = "§0§0§2§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0전체 영역 목록 : " + (page+1));

		String[] AreaList= areaYaml.getKeys().toArray(new String[0]);
		
		byte loc=0;
		String AreaName = null;
		String world = null;
		for(int count = page*45; count < AreaList.length;count++)
		{
			AreaName = AreaList[count];
			
			if(count > AreaList.length || loc >= 45) break;
			world = areaYaml.getString(AreaName+".World");
			int MinXLoc = areaYaml.getInt(AreaName+".X.Min");
			byte MinYLoc = (byte) areaYaml.getInt(AreaName+".Y.Min");
			int MinZLoc = areaYaml.getInt(AreaName+".Z.Min");
			int MaxXLoc = areaYaml.getInt(AreaName+".X.Max");
			byte MaxYLoc = (byte) areaYaml.getInt(AreaName+".Y.Max");
			int MaxZLoc = areaYaml.getInt(AreaName+".Z.Max");
			
			byte Priority = (byte) areaYaml.getInt(AreaName+".Priority");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + AreaName, 395,0,1,Arrays.asList(
					ChatColor.DARK_AQUA+"월드 : "+world,ChatColor.DARK_AQUA+"X 영역 : "+MinXLoc+" ~ " + MaxXLoc
					,ChatColor.DARK_AQUA+"Y 영역 : "+MinYLoc+" ~ " + MaxYLoc
					,ChatColor.DARK_AQUA+"Z 영역 : "+MinZLoc+" ~ " + MaxZLoc
					,ChatColor.DARK_AQUA+"우선 순위 : "+ Priority
					,ChatColor.DARK_GRAY+"영역끼리 서로 겹칠 경우",
					ChatColor.DARK_GRAY+"우선 순위가 더 높은 영역이",
					ChatColor.DARK_GRAY+"적용됩니다."
					,"",ChatColor.YELLOW+"[좌 클릭시 영역 설정]",ChatColor.RED+"[Shift + 우클릭시 영역 삭제]"), loc, inv);
			
			loc++;
		}
		
		if(AreaList.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 영역", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 영역을 생성합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AreaSettingGUI (Player player, String AreaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String UniqueCode = "§0§0§2§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0영역 설정");

		if(areaYaml.getBoolean(AreaName+".BlockUse") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 사용]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 9, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 사용]", 116,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 9, inv);

		if(areaYaml.getBoolean(AreaName+".BlockPlace") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 설치]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 10, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 설치]", 2,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 10, inv);

		if(areaYaml.getBoolean(AreaName+".BlockBreak") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 파괴]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 11, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 파괴]", 278,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 11, inv);

		if(areaYaml.getBoolean(AreaName+".PVP") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[   PVP   ]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 12, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[   PVP   ]", 267,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 12, inv);

		if(areaYaml.getBoolean(AreaName+".MobSpawn") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[몬스터 스폰]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 13, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[몬스터 스폰]", 52,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 13, inv);

		if(areaYaml.getBoolean(AreaName+".Alert") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[입장 메시지]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   없음   ]",""), 14, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[입장 메시지]", 340,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   전송   ]",""), 14, inv);

		if(areaYaml.getBoolean(AreaName+".SpawnPoint") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[리스폰 장소]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   불가   ]",""), 15, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[리스폰 장소]", 397,3,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   가능   ]",""), 15, inv);

		if(areaYaml.getBoolean(AreaName+".Music") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[배경음 재생]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   중지   ]",""), 16, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[배경음 재생]", 84,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   재생   ]",""), 16, inv);

		if(areaYaml.getInt(AreaName+".RegenBlock") == 0)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 리젠]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   중지   ]",""), 28, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 리젠]", 103,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   활성   ]","",ChatColor.DARK_AQUA+""+areaYaml.getInt(AreaName+".RegenBlock")+" 초 마다 리젠","",ChatColor.RED+"[플레이어가 직접 캔 블록만 리젠 됩니다]",""), 28, inv);

		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[특산품 설정]", 15,0,1,Arrays.asList("",ChatColor.GRAY + "현재 영역에서 블록을 캐면",ChatColor.GRAY+"지정된 아이템이 나오게",ChatColor.GRAY+"설정 합니다.","",ChatColor.YELLOW + "[클릭시 특산품 설정]"), 19, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[낚시 아이템]", 346,0,1,Arrays.asList("",ChatColor.GRAY + "현재 영역에서 낚시를 하여",ChatColor.GRAY+"얻을 수 있는 물건을 확률별로",ChatColor.GRAY+"설정합니다.",ChatColor.YELLOW + "[클릭시 낚시 아이템 설정]"), 20, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[우선순위 변경]", 384,0,1,Arrays.asList("",ChatColor.GRAY+"영역끼리 서로 겹칠 경우",ChatColor.GRAY+"우선 순위가 더 높은 영역이",ChatColor.GRAY+"적용됩니다.",ChatColor.GRAY+"이 속성을 이용하여 마을을 만들고,",ChatColor.GRAY+"마을 내부의 각종 상점 및",ChatColor.GRAY+"구역을 나눌 수 있습니다.",ChatColor.BLUE+"[   현재 우선 순위   ]",ChatColor.WHITE +" "+areaYaml.getInt(AreaName+".Priority"),"",ChatColor.YELLOW + "[클릭시 우선 순위 변경]"), 21, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[몬스터 설정]", 383,0,1,Arrays.asList("",ChatColor.GRAY + "현재 영역에서 자연적으로",ChatColor.GRAY+"스폰되는 몬스터 대신에",ChatColor.GRAY+"커스텀 몬스터로 변경합니다.","",ChatColor.YELLOW + "[클릭시 커스텀 몬스터 설정]",ChatColor.RED+"[몬스터 스폰 설정시 비활성]"), 22, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[몬스터 스폰 설정]", 52,0,1,Arrays.asList("",ChatColor.GRAY + "현재 영역의 특정 구역에",ChatColor.GRAY+"특정 시각마다 몬스터를",ChatColor.GRAY+"스폰 합니다.","",ChatColor.YELLOW + "[클릭시 몬스터 스폰 설정]"), 31, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[메시지 변경]", 386,0,1,Arrays.asList("",ChatColor.GRAY + "영역 입장 메시지를 변경합니다.","",ChatColor.YELLOW + "[클릭시 입장 메시지 설정]"), 23, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[중심지 변경]", 138,0,1,Arrays.asList("",ChatColor.GRAY + "마을 귀환, 최근 방문 위치에서",ChatColor.GRAY+"리스폰 등의 현재 영역으로",ChatColor.GRAY+"텔레포트 되는 이벤트가 발생할 경우",ChatColor.GRAY+"현재 위치가 중심점이 됩니다.","",ChatColor.DARK_AQUA+"[  현재 중심지  ]",ChatColor.DARK_AQUA+""+areaYaml.getString(AreaName+".World")+" : "+areaYaml.getInt(AreaName+".SpawnLocation.X")+","+areaYaml.getInt(AreaName+".SpawnLocation.Y")+","+areaYaml.getInt(AreaName+".SpawnLocation.Z"),"",ChatColor.YELLOW+ "[클릭시 현재 위치로 변경]"), 24, inv);
		
		if(areaYaml.getInt(AreaName+".Restrict.MinNowLevel")+areaYaml.getInt(AreaName+".Restrict.MinNowLevel")+
			areaYaml.getInt(AreaName+".Restrict.MinRealLevel")+areaYaml.getInt(AreaName+".Restrict.MaxRealLevel")==0)
			Stack2(ChatColor.GREEN + ""+ChatColor.BOLD+"[입장 레벨 제한 없음]", 166,0,1,Arrays.asList("",ChatColor.GRAY + "레벨에 따른 입장 제한이 없습니다.",""), 34, inv);
		else
			Stack2(ChatColor.RED + ""+ChatColor.BOLD+"[입장 레벨 제한]", 399,0,1,Arrays.asList("",ChatColor.GRAY + "레벨에 따른 입장 제한이 있습니다.",""
			,ChatColor.DARK_AQUA+"[  최소 현재 레벨  ]", "  "+ChatColor.DARK_AQUA+""+areaYaml.getInt(AreaName+".Restrict.MinNowLevel")
			,ChatColor.DARK_AQUA+"[  최대 현재 레벨  ]", "  "+ChatColor.DARK_AQUA+""+areaYaml.getInt(AreaName+".Restrict.MaxNowLevel")
			,ChatColor.GRAY+" ▼ 마비노기 시스템일 경우 추가 적용 ▼"
			,ChatColor.DARK_AQUA+"[  최소 누적 레벨  ]", "  "+ChatColor.DARK_AQUA+""+areaYaml.getInt(AreaName+".Restrict.MinRealLevel")
			,ChatColor.DARK_AQUA+"[  최대 누적 레벨  ]", "  "+ChatColor.DARK_AQUA+""+areaYaml.getInt(AreaName+".Restrict.MaxRealLevel"),""), 34, inv);
		String lore = "";
		short tracknumber = (short) areaYaml.getInt(AreaName+".BGM");
		lore = " %enter%"+ChatColor.GRAY + "영역 입장시 테마 음을%enter%"+ChatColor.GRAY+"재생 시킬 수 있습니다.%enter% %enter%"+ChatColor.BLUE + "[클릭시 노트블록 사운드 설정]%enter% %enter%"+ChatColor.DARK_AQUA+"[트랙] "+ChatColor.BLUE +""+ tracknumber+"%enter%"
		+ChatColor.DARK_AQUA+"[제목] "+ChatColor.BLUE +""+ new otherplugins.NoteBlockAPIMain().getTitle(tracknumber)+"%enter%"
		+ChatColor.DARK_AQUA+"[저자] "+ChatColor.BLUE+new otherplugins.NoteBlockAPIMain().getAuthor(tracknumber)+"%enter%"+ChatColor.DARK_AQUA+"[설명] ";
		
		String Description = new otherplugins.NoteBlockAPIMain().getDescription(areaYaml.getInt(AreaName+".BGM"));
		String lore2="";
		short a = 0;
		for(int count = 0; count <Description.toCharArray().length; count++)
		{
			lore2 = lore2+ChatColor.BLUE+Description.toCharArray()[count];
			a++;
			if(a >= 15)
			{
				a = 0;
				lore2 = lore2+"%enter%      ";
			}
		}
		lore = lore + lore2;
		
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[영역 배경음]", 2263,0,1,Arrays.asList(lore.split("%enter%")), 25, inv);
		
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "영역 이동", 368,0,1,Arrays.asList(ChatColor.GRAY + "현재 영역으로 빠르게 이동합니다."), 40, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "영역 목록으로 돌아갑니다."), 36, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "현재 창을 닫습니다.",ChatColor.BLACK+AreaName), 44, inv);
		
		player.openInventory(inv);
		return;
	}
	
	public void AreaMonsterSpawnSettingGUI(Player player, short page, String AreaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		String UniqueCode = "§0§0§2§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0영역 몬스터 스폰 룰 : " + (page+1));
		if(areaYaml.contains(AreaName+".MonsterSpawnRule")==false)
		{
			areaYaml.createSection(AreaName+".MonsterSpawnRule");
			areaYaml.saveConfig();
		}
		String[] RuleList= areaYaml.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).toArray(new String[0]);
		byte loc=0;
		for(int count = page*45; count <RuleList.length ;count++)
		{
			if(count > RuleList.length || loc >= 45) break;
			String RuleNumber = RuleList[count];
			if(areaYaml.contains(AreaName+".MonsterSpawnRule."+RuleNumber+".Monster"))
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + (RuleNumber), 383,0,1,Arrays.asList(
						ChatColor.GOLD+"[     스폰 옵션     ]",ChatColor.RED+"-영역에 유저가 있을 때만 작동 -",ChatColor.GOLD+"월드 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.world"),
						ChatColor.GOLD+"좌표 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.x")+","+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.y")+","+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.z"),
						ChatColor.GOLD+"인식 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".range")+"블록",
						ChatColor.GOLD+"시간 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".timer")+"초마다 "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".count")+"마리 스폰",
						ChatColor.GOLD+"최대 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".max")+"마리",
						ChatColor.GOLD+"스폰 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".Monster")
						,"",ChatColor.RED+"[Shift + 우클릭시 룰 삭제]"), loc, inv);
			else
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + (RuleNumber), 52,0,1,Arrays.asList(
					ChatColor.GOLD+"[     스폰 옵션     ]",ChatColor.RED+"-영역에 유저가 있을 때만 작동 -",ChatColor.GOLD+"월드 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.world"),
					ChatColor.GOLD+"좌표 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.x")+","+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.y")+","+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.z"),
					ChatColor.GOLD+"인식 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".range")+"블록",
					ChatColor.GOLD+"시간 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".timer")+"초마다 "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".count")+"마리 스폰",
					ChatColor.GOLD+"최대 : "+areaYaml.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".max")+"마리"
					,"",ChatColor.RED+"[Shift + 우클릭시 룰 삭제]"), loc, inv);
			loc++;
		}

		if(RuleList.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 규칙 추가", 52,0,1,Arrays.asList(ChatColor.GRAY + "새 스폰 규칙을 추가합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AreaMonsterSettingGUI(Player player, short page, String AreaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");

		String UniqueCode = "§0§0§2§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0영역 대체 몬스터 : " + (page+1));

		String[] MonsterNameList= areaYaml.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		short MobNameListLength = (short) MonsterNameList.length;
		String MonsterName = null;
		String Name = null;
		for(int count = page*45; count <MobNameListLength ;count++)
		{
			MonsterName = MonsterNameList[count];
			if(monsterYaml.contains(MonsterName) == true)
			{
				Name = monsterYaml.getString(MonsterName+".Name");
				if(count > MobNameListLength || loc >= 45) break;
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + MonsterName, 383,0,1,Arrays.asList(
						ChatColor.WHITE+"이름 : " + Name,ChatColor.WHITE+"타입 : " + monsterYaml.getString(MonsterName+".Type"),
						ChatColor.WHITE+"생명력 : " + monsterYaml.getInt(MonsterName+".HP"),ChatColor.WHITE+"경험치 : " + monsterYaml.getInt(MonsterName+".EXP"),
						ChatColor.WHITE+"골드 : " + monsterYaml.getInt(MonsterName+".MIN_Money")+" ~ " +monsterYaml.getInt(MonsterName+".MAX_Money"),
						"",ChatColor.RED+"[Shift + 우클릭시 등록 해제]"), loc, inv);
				loc++;
			}
			else
			{
				areaYaml.removeKey(AreaName+".Monster."+MonsterName);
				areaYaml.saveConfig();
			}
		}
		
		if(MobNameListLength-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l몬스터 추가", 52,0,1,Arrays.asList(ChatColor.GRAY + "새 커스텀 몬스터를 추가합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AreaFishSettingGUI(Player player, String AreaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		String UniqueCode = "§1§0§2§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0영역 추가 어류");
		
		Stack2("§a§l[     54%     ]", 160,5,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 54% 확률로 낚일 아이템을 올리세요."), 0, inv);
		Stack2("§e§l[     30%     ]", 160,4,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 30% 확률로 낚일 아이템을 올리세요."), 9, inv);
		Stack2("§6§l[     10%     ]", 160,1,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 10% 확률로 낚일 아이템을 올리세요."), 18, inv);
		Stack2("§c§l[      5%      ]", 160,14,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 5% 확률로 낚일 아이템을 올리세요."), 27, inv);
		Stack2("§8§l[      1%      ]", 160,10,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 1% 확률로 낚일 아이템을 올리세요."), 36, inv);

		String[] FishingItemList = areaYaml.getConfigurationSection(AreaName+".Fishing.54").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < FishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(AreaName+".Fishing.54."+FishingItemList[count]), count+1, inv);
		FishingItemList = areaYaml.getConfigurationSection(AreaName+".Fishing.30").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < FishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(AreaName+".Fishing.30."+FishingItemList[count]), count+10, inv);
		FishingItemList = areaYaml.getConfigurationSection(AreaName+".Fishing.10").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < FishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(AreaName+".Fishing.10."+FishingItemList[count]), count+19, inv);
		FishingItemList = areaYaml.getConfigurationSection(AreaName+".Fishing.5").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < FishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(AreaName+".Fishing.5."+FishingItemList[count]), count+28, inv);
		FishingItemList = areaYaml.getConfigurationSection(AreaName+".Fishing.1").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < FishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(AreaName+".Fishing.1."+FishingItemList[count]), count+37, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AreaBlockSettingGUI(Player player, short page, String AreaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		event.Main_Interact I = new event.Main_Interact();

		String UniqueCode = "§0§0§2§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0영역 특산품 : " + (page+1));

		String[] BlockIdDataList= areaYaml.getConfigurationSection(AreaName+".Mining").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count <BlockIdDataList.length ;count++)
		{
			short ID = Short.parseShort(BlockIdDataList[count].split(":")[0]);
			byte Data = Byte.parseByte(BlockIdDataList[count].split(":")[1]);

			Stack2(I.SetItemDefaultName(ID, (byte) Data), ID,Data,1,Arrays.asList(
					"",ChatColor.RED+"[Shift + 우클릭시 등록 해제]"), loc, inv);
				loc++;
		}
		
		if(BlockIdDataList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l특산물 추가", 52,0,1,Arrays.asList(ChatColor.GRAY + "새로운 블록을 설정합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AreaBlockItemSettingGUI(Player player,String AreaName,String ItemData)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		String UniqueCode = "§1§0§2§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0해당 블록을 캐면 나올 아이템");

		ItemStack item = areaYaml.getItemStack(AreaName+".Mining."+ItemData+".100");
		
		ItemStackStack(item, 4, inv);

		Stack2("§c§l[아이템 넣기>", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 0, inv);
		Stack2("§c§l[아이템 넣기>", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 1, inv);	
		Stack2("§c§l[아이템 넣기>", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 2, inv);
		Stack2("§c§l[아이템 넣기>", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 3, inv);	
		Stack2("§c§l<아이템 넣기]", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 5, inv);
		Stack2("§c§l<아이템 넣기]", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 6, inv);	
		Stack2("§c§l<아이템 넣기]", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 7, inv);
		Stack2("§c§l<아이템 넣기]", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 8, inv);

		item = areaYaml.getItemStack(AreaName+".Mining."+ItemData+".90");
		ItemStackStack(item, 13, inv);
		Stack2("§c§l[아이템 넣기>", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 9, inv);
		Stack2("§c§l[아이템 넣기>", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 10, inv);	
		Stack2("§c§l[아이템 넣기>", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 11, inv);
		Stack2("§c§l[아이템 넣기>", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 12, inv);	
		Stack2("§c§l<아이템 넣기]", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 14, inv);
		Stack2("§c§l<아이템 넣기]", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 15, inv);	
		Stack2("§c§l<아이템 넣기]", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 16, inv);
		Stack2("§c§l<아이템 넣기]", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 17, inv);

		item = areaYaml.getItemStack(AreaName+".Mining."+ItemData+".50");
		ItemStackStack(item, 22, inv);
		Stack2("§c§l[아이템 넣기>", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 18, inv);
		Stack2("§c§l[아이템 넣기>", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 19, inv);	
		Stack2("§c§l[아이템 넣기>", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 20, inv);
		Stack2("§c§l[아이템 넣기>", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 21, inv);	
		Stack2("§c§l<아이템 넣기]", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 23, inv);
		Stack2("§c§l<아이템 넣기]", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 24, inv);	
		Stack2("§c§l<아이템 넣기]", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 25, inv);
		Stack2("§c§l<아이템 넣기]", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 26, inv);

		item = areaYaml.getItemStack(AreaName+".Mining."+ItemData+".10");
		ItemStackStack(item, 31, inv);
		Stack2("§c§l[아이템 넣기>", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 27, inv);
		Stack2("§c§l[아이템 넣기>", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 28, inv);	
		Stack2("§c§l[아이템 넣기>", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 29, inv);
		Stack2("§c§l[아이템 넣기>", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 30, inv);	
		Stack2("§c§l<아이템 넣기]", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 32, inv);
		Stack2("§c§l<아이템 넣기]", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 33, inv);	
		Stack2("§c§l<아이템 넣기]", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 34, inv);
		Stack2("§c§l<아이템 넣기]", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 35, inv);

		item = areaYaml.getItemStack(AreaName+".Mining."+ItemData+".1");
		ItemStackStack(item, 40, inv);
		Stack2("§c§l[아이템 넣기>", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 36, inv);
		Stack2("§c§l[아이템 넣기>", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 37, inv);	
		Stack2("§c§l[아이템 넣기>", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 38, inv);
		Stack2("§c§l[아이템 넣기>", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 39, inv);	
		Stack2("§c§l<아이템 넣기]", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 41, inv);
		Stack2("§c§l<아이템 넣기]", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 42, inv);	
		Stack2("§c§l<아이템 넣기]", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 43, inv);
		Stack2("§c§l<아이템 넣기]", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 44, inv);

		item = areaYaml.getItemStack(AreaName+".Mining."+ItemData+".0");
		ItemStackStack(item, 49, inv);
		Stack2("§c§l[아이템 넣기>", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 46, inv);	
		Stack2("§c§l[아이템 넣기>", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 47, inv);
		Stack2("§c§l[아이템 넣기>", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 48, inv);	
		Stack2("§c§l<아이템 넣기]", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 50, inv);
		Stack2("§c§l<아이템 넣기]", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 51, inv);	
		Stack2("§c§l<아이템 넣기]", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 52, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+ItemData), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AreaAddMonsterListGUI(Player player, short page,String AreaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");

		String UniqueCode = "§0§0§2§0§7§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0영역 몬스터 선택 : " + (page+1));

		String[] monsterList= monsterYaml.getKeys().toArray(new String[0]);
		String[] monsterNameList= areaYaml.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray(new String[0]);

		String lore = null;
		byte loc=0;
		String monsterName = null;
		for(int count = page*45; count < monsterList.length;count++)
		{
			if(count > monsterList.length || loc >= 45) break;
			monsterName = monsterList[count];
			boolean isExit = false;
			for(int count2 = 0; count2 < monsterNameList.length; count2++)
			{
				if(monsterNameList[count2].compareTo(monsterName)==0)
				{
					isExit=true;
					break;
				}
			}
			
			if(isExit == false)
			{
				lore = null;
				lore = "%enter%"+"§f§l 이름 : "+ChatColor.WHITE+monsterYaml.getString(monsterName+".Name")+"%enter%";
				lore = lore+"§f§l 타입 : "+ChatColor.WHITE+monsterYaml.getString(monsterName+".Type")+"%enter%";
				lore = lore+"§f§l 스폰 바이옴 : "+ChatColor.WHITE+monsterYaml.getString(monsterName+".Biome")+"%enter%";
				lore = lore+"§c§l 생명력 : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".HP")+"%enter%";
				lore = lore+"§b§l 경험치 : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".EXP")+"%enter%";
				lore = lore+"§e§l 드랍 금액 : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".MIN_Money")+" ~ "+monsterYaml.getInt(monsterName+".MAX_Money")+"%enter%";
				lore = lore+"§c§l "+Main_ServerOption.STR+" : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".STR")
				+ChatColor.GRAY+ " [물공 : " + Battle_Calculator.CombatDamageGet(null, 0, monsterYaml.getInt(monsterName+".STR"), true) + " ~ " + Battle_Calculator.CombatDamageGet(null, 0, monsterYaml.getInt(monsterName+".STR"), false) + "]%enter%";
				lore = lore+"§a§l "+Main_ServerOption.DEX+" : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".DEX")
				+ChatColor.GRAY+ " [활공 : " + Battle_Calculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, true) + " ~ " + Battle_Calculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, false) + "]%enter%";
				lore = lore+"§9§l "+Main_ServerOption.INT+" : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".INT")
				+ChatColor.GRAY+ " [폭공 : " + (monsterYaml.getInt(monsterName+".INT")/4)+ " ~ "+(int)(monsterYaml.getInt(monsterName+".INT")/2.5)+"]%enter%";
				lore = lore+"§7§l "+Main_ServerOption.WILL+" : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".WILL")
				+ChatColor.GRAY+ " [크리 : " + Battle_Calculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
				lore = lore+"§e§l "+Main_ServerOption.LUK+" : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".LUK")
				+ChatColor.GRAY+ " [크리 : " + Battle_Calculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
				lore = lore+"§7§l 방어 : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".DEF")+"%enter%";
				lore = lore+"§b§l 보호 : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".Protect")+"%enter%";
				lore = lore+"§9§l 마법 방어 : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".Magic_DEF")+"%enter%";
				lore = lore+"§1§l 마법 보호 : "+ChatColor.WHITE+monsterYaml.getInt(monsterName+".Magic_Protect")+"%enter%";
				lore = lore+"%enter%"+"§e§l[좌 클릭시 몬스터 등록]";

				String[] scriptA = lore.split("%enter%");
				for(int counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  " "+scriptA[counter];
				short id = 383;
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
				}
				
				Stack(ChatColor.WHITE+monsterName, id, data, 1,Arrays.asList(scriptA), loc, inv);
				loc++;
			}
		}
		
		if(monsterList.length-(page*44)>45)
			Stack("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+AreaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AreaSpawnSpecialMonsterListGUI(Player player, short page,String AreaName,String RuleCount)
	{
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		String UniqueCode = "§0§0§2§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0영역 특수 몬스터  : " + (page+1));

		String[] monsterList= monsterYaml.getKeys().toArray(new String[0]);

		byte loc=0;
		String MonsterName = null;
		String lore = null;
		for(int count = page*45; count < monsterList.length;count++)
		{
			if(count > monsterList.length || loc >= 45) break;
			MonsterName = monsterList[count];
			lore = null;
			lore = "%enter%"+"§f§l 이름 : "+ChatColor.WHITE+monsterYaml.getString(MonsterName+".Name")+"%enter%";
			lore = lore+"§f§l 타입 : "+ChatColor.WHITE+monsterYaml.getString(MonsterName+".Type")+"%enter%";
			lore = lore+"§f§l 스폰 바이옴 : "+ChatColor.WHITE+monsterYaml.getString(MonsterName+".Biome")+"%enter%";
			lore = lore+"§c§l 생명력 : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".HP")+"%enter%";
			lore = lore+"§b§l 경험치 : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".EXP")+"%enter%";
			lore = lore+"§e§l 드랍 금액 : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".MIN_Money")+" ~ "+monsterYaml.getInt(MonsterName+".MAX_Money")+"%enter%";
			lore = lore+"§c§l "+Main_ServerOption.STR+" : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".STR")
			+ChatColor.GRAY+ " [물공 : " + Battle_Calculator.CombatDamageGet(null, 0, monsterYaml.getInt(MonsterName+".STR"), true) + " ~ " + Battle_Calculator.CombatDamageGet(null, 0, monsterYaml.getInt(MonsterName+".STR"), false) + "]%enter%";
			lore = lore+"§a§l "+Main_ServerOption.DEX+" : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".DEX")
			+ChatColor.GRAY+ " [활공 : " + Battle_Calculator.returnRangeDamageValue(null, monsterYaml.getInt(MonsterName+".DEX"), 0, true) + " ~ " + Battle_Calculator.returnRangeDamageValue(null, monsterYaml.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
			lore = lore+"§9§l "+Main_ServerOption.INT+" : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".INT")
			+ChatColor.GRAY+ " [폭공 : " + (monsterYaml.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(monsterYaml.getInt(MonsterName+".INT")/2.5)+"]%enter%";
			lore = lore+"§7§l "+Main_ServerOption.WILL+" : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".WILL")
			+ChatColor.GRAY+ " [크리 : " + Battle_Calculator.getCritical(null,monsterYaml.getInt(MonsterName+".LUK"), (int)monsterYaml.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			lore = lore+"§e§l "+Main_ServerOption.LUK+" : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".LUK")
			+ChatColor.GRAY+ " [크리 : " + Battle_Calculator.getCritical(null,monsterYaml.getInt(MonsterName+".LUK"), (int)monsterYaml.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			lore = lore+"§7§l 방어 : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".DEF")+"%enter%";
			lore = lore+"§b§l 보호 : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".Protect")+"%enter%";
			lore = lore+"§9§l 마법 방어 : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".Magic_DEF")+"%enter%";
			lore = lore+"§1§l 마법 보호 : "+ChatColor.WHITE+monsterYaml.getInt(MonsterName+".Magic_Protect")+"%enter%";
			lore = lore+"%enter%"+"§e§l[좌 클릭시 몬스터 등록]";

			String[] scriptA = lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			short id = 383;
			byte data = 0;
			switch(monsterYaml.getString(MonsterName+".Type"))
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
		
		if(monsterList.length-(page*44)>45)
		Stack("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack("§f§l취소", 166,0,1,Arrays.asList(ChatColor.GRAY + "지정 몬스터 스폰대신",ChatColor.GRAY+"영역에 등록 된 몬스터를",ChatColor.GRAY+"랜덤하게 스폰 합니다.",ChatColor.BLACK+AreaName,ChatColor.BLACK+""+RuleCount), 49, inv);
		player.openInventory(inv);
	}

	public void AreaMusicSettingGUI(Player player, int page,String AreaName)
	{
		String UniqueCode = "§0§0§2§0§9§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0영역 배경음 : " + (page+1));
		byte loc=0;
		byte model = (byte) new util.Util_Number().RandomNum(0, 11);
		for(int count = page*45; count < new otherplugins.NoteBlockAPIMain().Musics.size();count++)
		{
			if(model<11)
				model++;
			else
				model=0;
			String lore = " %enter%"+ChatColor.DARK_AQUA+"[트랙] "+ChatColor.BLUE +""+ count+"%enter%"
			+ChatColor.DARK_AQUA+"[제목] "+ChatColor.BLUE +""+ new otherplugins.NoteBlockAPIMain().getTitle(count)+"%enter%"
			+ChatColor.DARK_AQUA+"[저자] "+ChatColor.BLUE+new otherplugins.NoteBlockAPIMain().getAuthor(count)+"%enter%"+ChatColor.DARK_AQUA+"[설명] ";
			String Description = new otherplugins.NoteBlockAPIMain().getDescription(count);
			String lore2="";
			short a = 0;
			for(int counter = 0; counter <Description.toCharArray().length; counter++)
			{
				lore2 = lore2+ChatColor.BLUE+Description.toCharArray()[counter];
				a++;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2+"%enter% %enter%"+ChatColor.YELLOW+"[좌 클릭시 배경음 설정]";
			if(count > new otherplugins.NoteBlockAPIMain().Musics.size() || loc >= 45) break;
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 2256+model,0,1,Arrays.asList(lore.split("%enter%")), loc, inv);
			
			loc++;
		}
		
		if(new otherplugins.NoteBlockAPIMain().Musics.size()-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+AreaName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void AreaListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			String AreaName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			
			if(slot == 45)//이전 목록
				new OPbox_GUI().OPBoxGUI_Main(player, (byte) 2);
			else if(slot == 48)//이전 페이지
				AreaListGUI(player, (short) (page-1));
			else if(slot == 49)//영역 추가
			{
			  	YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				player.closeInventory();
				event.Main_Interact IT = new event.Main_Interact();
				player.sendMessage(ChatColor.DARK_AQUA + "[영역] : " + IT.SetItemDefaultName((short) configYaml.getInt("Server.AreaSettingWand"),(byte)0) +ChatColor.DARK_AQUA+" 아이템으로 구역을 설정을 한 뒤,");
				player.sendMessage(ChatColor.GOLD +""+ChatColor.BOLD+ " /영역 <영역이름> 생성 "+ChatColor.DARK_AQUA+"명령어를 입력해 주세요!");
				SoundEffect.SP((Player)player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
			else if(slot == 50)//다음 페이지
				AreaListGUI(player, (short) (page+1));
			else
			{
				if(event.isLeftClick() == true)
					AreaSettingGUI(player, AreaName);
				else if(event.isShiftClick() == true && event.isRightClick() == true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					for(int count = 0; count < Main_ServerOption.AreaList.get(areaYaml.getString(AreaName+".World")).size(); count++)
						if(Main_ServerOption.AreaList.get(areaYaml.getString(AreaName+".World")).get(count).AreaName.compareTo(AreaName)==0)
							Main_ServerOption.AreaList.get(areaYaml.getString(AreaName+".World")).remove(count);
					areaYaml.removeKey(AreaName);
					areaYaml.saveConfig();
					AreaListGUI(player, page);
				}
			}
		}
	}
	
	public void AreaSettingGUIInventoryclick(InventoryClickEvent event)
	{
		
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 44)//창닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
		  	YamlLoader areaYaml = new YamlLoader();
			areaYaml.getConfig("Area/AreaList.yml");
			String AreaName = ChatColor.stripColor(event.getInventory().getItem(44).getItemMeta().getLore().get(1));
			if(slot == 36)//이전 화면
				AreaListGUI(player,(short) 0);
			else if(slot >= 9 && slot <= 16)
			{
				if(slot == 9)//블록 사용
				{
					if(areaYaml.getBoolean(AreaName+".BlockUse") == false)
						areaYaml.set(AreaName+".BlockUse", true);
					else
						areaYaml.set(AreaName+".BlockUse", false);
				}
				else if(slot == 10)//블록 설치
				{
					if(areaYaml.getBoolean(AreaName+".BlockPlace") == false)
						areaYaml.set(AreaName+".BlockPlace", true);
					else
						areaYaml.set(AreaName+".BlockPlace", false);
				}
				else if(slot == 11)//블록 파괴
				{
					if(areaYaml.getBoolean(AreaName+".BlockBreak") == false)
						areaYaml.set(AreaName+".BlockBreak", true);
					else
						areaYaml.set(AreaName+".BlockBreak", false);
				}
				else if(slot == 12)//PVP
				{
					if(areaYaml.getBoolean(AreaName+".PVP") == false)
						areaYaml.set(AreaName+".PVP", true);
					else
						areaYaml.set(AreaName+".PVP", false);
				}
				else if(slot == 13)//몬스터 스폰
				{
					if(areaYaml.getBoolean(AreaName+".MobSpawn") == false)
						areaYaml.set(AreaName+".MobSpawn", true);
					else
						areaYaml.set(AreaName+".MobSpawn", false);
				}
				else if(slot == 14)//입장 메시지
				{
					if(areaYaml.getBoolean(AreaName+".Alert") == false)
						areaYaml.set(AreaName+".Alert", true);
					else
						areaYaml.set(AreaName+".Alert", false);
				}
				else if(slot == 15)//리스폰 장소
				{
					if(areaYaml.getBoolean(AreaName+".SpawnPoint") == false)
						areaYaml.set(AreaName+".SpawnPoint", true);
					else
						areaYaml.set(AreaName+".SpawnPoint", false);
				}
				else if(slot == 16)//배경음 재생
				{
					if(areaYaml.getBoolean(AreaName+".Music") == false)
						areaYaml.set(AreaName+".Music", true);
					else
						areaYaml.set(AreaName+".Music", false);
				}
				areaYaml.saveConfig();
				AreaSettingGUI(player, AreaName);
			}
			else if(slot == 21)//우선 순위 변경
			{
				UserData_Object u = new UserData_Object();
				player.closeInventory();
				u.setType(player, "Area");
				u.setString(player, (byte)2, "Priority");
				u.setString(player, (byte)3, AreaName);
				player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+AreaName+ChatColor.GREEN+" 영역의 우선 순위를 입력하세요!");
				player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
			}
			else if(slot == 23)//메시지 변경
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.sendMessage(ChatColor.GOLD + "/영역 "+AreaName+" 이름 <문자열>" + ChatColor.YELLOW + "\n - "+AreaName+" 구역의 알림 메시지에 보일 이름을 정합니다.");
				player.sendMessage(ChatColor.GOLD + "/영역 "+AreaName+" 설명 <문자열>" + ChatColor.YELLOW + "\n - "+AreaName+" 구역의 알림 메시지에 보일 부가 설명을 정합니다.");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
			}
			else if(slot == 24)//중심지 변경
			{
				areaYaml.set(AreaName+".World", player.getLocation().getWorld().getName());
				areaYaml.set(AreaName+".SpawnLocation.X", player.getLocation().getX());
				areaYaml.set(AreaName+".SpawnLocation.Y", player.getLocation().getY());
				areaYaml.set(AreaName+".SpawnLocation.Z", player.getLocation().getZ());
				areaYaml.set(AreaName+".SpawnLocation.Pitch", player.getLocation().getPitch());
				areaYaml.set(AreaName+".SpawnLocation.Yaw", player.getLocation().getYaw());
				areaYaml.saveConfig();
				AreaSettingGUI(player, AreaName);
			}
			else if(slot == 25)//영역 배경음 설정
			{
				if(new otherplugins.NoteBlockAPIMain().SoundList(player,true))
					AreaMusicSettingGUI(player, 0, AreaName);
				else
					SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.9F);
			}
			else if(slot == 28)//블록 리젠
			{
				if(areaYaml.getInt(AreaName+".RegenBlock") == 0)
				{
					player.closeInventory();
					UserData_Object u = new UserData_Object();
					areaYaml.set(AreaName+".RegenBlock", 1);
					areaYaml.saveConfig();
					u.setType(player, "Area");
					u.setString(player, (byte)2, "ARR");
					u.setString(player, (byte)3, AreaName);
					player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+AreaName+ChatColor.GREEN+" 영역의 블록 리젠 속도를 설정하세요!");
					player.sendMessage(ChatColor.GRAY + "(최소 1초 ~ 최대 3600초(1시간))");
				}
				else
				{
					areaYaml.set(AreaName+".RegenBlock", 0);
					areaYaml.saveConfig();
					AreaSettingGUI(player, AreaName);
				}
			}
			else if(slot == 19)//특산품 설정
				AreaBlockSettingGUI(player, (short) 0, AreaName);
			else if(slot == 20)//낚시 아이템
				AreaFishSettingGUI(player, AreaName);
			else if(slot == 22)//몬스터 설정
				AreaMonsterSettingGUI(player,(short) 0, AreaName);
			else if(slot == 31)//몬스터 스폰 룰
				AreaMonsterSpawnSettingGUI(player, (short) 0, AreaName);
			else if(slot == 34)//레벨 제한
			{
				UserData_Object u = new UserData_Object();
				player.closeInventory();
				u.setType(player, "Area");
				u.setString(player, (byte)2, "MinNLR");
				u.setString(player, (byte)3, AreaName);
				player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+AreaName+ChatColor.GREEN+" 영역의 입장에 필요한 최소 레벨을 입력 하세요!"+ChatColor.GRAY + " (0 입력시 제한 없음)");
			}
			else if(slot == 40)//영역 이동
			{
				player.closeInventory();
				player.teleport(new Location(Bukkit.getWorld(areaYaml.getString(AreaName+".World")),areaYaml.getInt(AreaName+".SpawnLocation.X"), areaYaml.getInt(AreaName+".SpawnLocation.Y"),areaYaml.getInt(AreaName+".SpawnLocation.Z"),areaYaml.getInt(AreaName+".SpawnLocation.Yaw"),areaYaml.getInt(AreaName+".SpawnLocation.Pitch")));
			}
		}
		return;
	}

	public void AreaMonsterSettingGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		if(slot == 53)//창닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 화면
				AreaSettingGUI(player, AreaName);
			else if(slot == 48)//이전 페이지
				AreaMonsterSettingGUI(player, (short) (page-1), AreaName);
			else if(slot == 49)//몬스터 추가
			{
			  	YamlLoader monsterYaml = new YamlLoader();
				monsterYaml.getConfig("Monster/MonsterList.yml");
				if(monsterYaml.getKeys().size() == 0)
				{
					SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0F, 1.8F);
					player.sendMessage(ChatColor.RED + "[영역] : 현재 등록된 커스텀 몬스터가 존재하지 않습니다!");
					player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD + "/몬스터 <이름> 생성 " + ChatColor.YELLOW +"해당 이름을 가진 몬스터를 생성합니다.");
				}
				else
					AreaAddMonsterListGUI(player, page, AreaName);
			}
			else if(slot == 50)//다음 페이지
				AreaMonsterSettingGUI(player, (short) (page+1),AreaName);
			else
			{
				if(event.isShiftClick() == true && event.isRightClick() == true)
				{
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					String MonsterName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					areaYaml.removeKey(AreaName+".Monster."+MonsterName);
					areaYaml.saveConfig();
					AreaMonsterSettingGUI(player, page,AreaName);
				}
			}
		}
	}

	public void AreaFishSettingGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		if(slot == 0 || slot == 9 || slot == 18 || slot == 27 || slot == 36 || slot >= 45)
			event.setCancelled(true);
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 45)//이전 목록
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			AreaSettingGUI(player, AreaName);
		}
	}

	public void AreaBlockSettingGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				AreaSettingGUI(player, AreaName);
			else if(slot == 48)//이전 페이지
				AreaBlockSettingGUI(player, (short) (page-1), AreaName);
			else if(slot == 49)//특산물 추가
			{
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA + "[영역] : 설정할 블록을 좌클릭 하세요!");

				UserData_Object u = new UserData_Object();
				u.setType(player, "Area");
				u.setString(player, (byte)2, AreaName);
				u.setString(player, (byte)3, "ANBI");
			}
			else if(slot == 50)//다음 페이지
				AreaBlockSettingGUI(player, (short) (page+1), AreaName);
			else
			{
				String BlockName = event.getCurrentItem().getTypeId()+":"+event.getCurrentItem().getData().getData();
				if(event.isShiftClick()==false&&event.isLeftClick()==true)
					AreaBlockItemSettingGUI(player, AreaName, BlockName);
				else if(event.isShiftClick() == true && event.isRightClick() == true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					areaYaml.removeKey(AreaName+".Mining."+BlockName);
					areaYaml.saveConfig();
					AreaBlockSettingGUI(player, page, AreaName);
				}
			}
		}
	}

	public void AreaBlockItemSettingGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(event.getClickedInventory().getTitle().compareTo("container.inventory") != 0)
		{
			if(slot==4||slot==13||slot==22||slot==31||slot==40||slot==49)
				event.setCancelled(false);
			else if(slot == 53)//나가기
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else if(slot == 45)//이전 목록
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				AreaBlockSettingGUI(player, (short) 0, AreaName);
			}
		}
	}

	public void AreaAddMonsterSpawnRuleGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			if(slot == 45)//이전 목록
				AreaSettingGUI(player, AreaName);
			else if(slot == 48)//이전 페이지
				AreaMonsterSpawnSettingGUI(player, (short) (page-1), AreaName);
			else if(slot == 49)//룰 추가
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				long count = new util.ETC().getNowUTC();
				areaYaml.set(AreaName+".MonsterSpawnRule."+count+".range", 1);
				areaYaml.set(AreaName+".MonsterSpawnRule."+count+".count", 4);
				areaYaml.set(AreaName+".MonsterSpawnRule."+count+".timer", 10);
				areaYaml.set(AreaName+".MonsterSpawnRule."+count+".max", 10);
				UserData_Object u = new UserData_Object();
				u.setType(player, "Area");
				u.setString(player, (byte)1, count+"");
				u.setString(player, (byte)2, AreaName);
				u.setString(player, (byte)3, "MLS");
				areaYaml.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[영역] : 몬스터가 스폰 될 위치를 마우스 우 클릭 하세요!");
				player.closeInventory();
			}
			else if(slot == 50)//다음 페이지
				AreaMonsterSpawnSettingGUI(player, (short) (page+1), AreaName);
			else if(event.isRightClick()&&event.isShiftClick())
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
				areaYaml.removeKey(AreaName+".MonsterSpawnRule."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				areaYaml.saveConfig();
				AreaMonsterSpawnSettingGUI(player, (short) page, AreaName);
			}
		}
	}
	
	public void AreaAddMonsterListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				AreaMonsterSettingGUI(player, (short) 0, AreaName);
			else if(slot == 45)//이전 페이지
				AreaAddMonsterListGUI(player, (short) (page-1), AreaName);
			else if(slot == 50)//다음 페이지
				AreaAddMonsterListGUI(player, (short) (page+1), AreaName);
			else
			{
				String MobName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				SoundEffect.SP(player, Sound.ENTITY_WOLF_AMBIENT, 0.8F, 1.0F);
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				areaYaml.createSection(AreaName+".Monster."+MobName);
				areaYaml.saveConfig();
				AreaAddMonsterListGUI(player, page, AreaName);
			}
		}
	}

	public void AreaSpawnSpecialMonsterListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		String AreaName = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(3));
		String RuleCounter = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(4));
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		if(slot == 49)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			AreaMonsterSpawnSettingGUI(player, (short) 0, AreaName);
			new area.Area_Main().AreaMonsterSpawnAdd(AreaName, RuleCounter);
		}
		else if(slot == 48)//이전 페이지
			AreaAddMonsterListGUI(player, (short) (page-1), AreaName);
		else if(slot == 50)//다음 페이지
			AreaAddMonsterListGUI(player, (short) (page+1), AreaName);
		else
		{
			String MobName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 0.8F, 1.0F);
		  	YamlLoader areaYaml = new YamlLoader();
			areaYaml.getConfig("Area/AreaList.yml");
			areaYaml.set(AreaName+".MonsterSpawnRule."+RuleCounter+".Monster", MobName);
			areaYaml.saveConfig();
			AreaMonsterSpawnSettingGUI(player, (short) 0, AreaName);
			
			new area.Area_Main().AreaMonsterSpawnAdd(AreaName, RuleCounter);
		}
	}

	public void AreaMusicSettingGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		int slot = event.getSlot();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);

		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(AreaName.compareTo("DeathBGM¡")==0)
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)
				new admin.OPbox_GUI().OPBoxGUI_Death(player);
			else if(slot == 48)
				AreaMusicSettingGUI(player, page-1,AreaName);
			else if(slot == 50)
				AreaMusicSettingGUI(player, page+1,AreaName);
			else
			{
			  	YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				configYaml.set("Death.Track", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
				configYaml.saveConfig();
				new admin.OPbox_GUI().OPBoxGUI_Death(player);
			}
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				AreaSettingGUI(player, AreaName);
			else if(slot == 48)//이전 페이지
				AreaMusicSettingGUI(player, page-1,AreaName);
			else if(slot == 50)//다음 페이지
				AreaMusicSettingGUI(player, page+1,AreaName);
			else
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				areaYaml.set(AreaName+".BGM", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
				areaYaml.saveConfig();
				AreaSettingGUI(player, AreaName);
			}
		}
	}

	
	
	public void FishingSettingInventoryClose(InventoryCloseEvent event)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		byte loc = 0;
		for(int count = 1; count < 9; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(AreaName + ".Fishing.54."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(AreaName+".Fishing.54."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 10; count < 18; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(AreaName + ".Fishing.30."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(AreaName+".Fishing.30."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 19; count < 27; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(AreaName + ".Fishing.10."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(AreaName+".Fishing.10."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 28; count < 36; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(AreaName + ".Fishing.5."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(AreaName+".Fishing.5."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 37; count < 45; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(AreaName + ".Fishing.1."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(AreaName+".Fishing.1."+loc);
			areaYaml.saveConfig();
		}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(AreaName+".Fishing.54."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(AreaName+".Fishing.54."+(counter), areaYaml.getItemStack(AreaName+".Fishing.54."+(counter+1)));
					areaYaml.removeKey(AreaName+".Fishing.54."+(counter+1));
				}
				areaYaml.saveConfig();
			}
	
		byte line1 = 0;
		byte line2 = 0;
		byte line3 = 0;
		byte line4 = 0;
		byte line5 = 0;
		for(int count = 0; count < 45; count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(count>0&&count<9 )
					line1 = (byte) (line1+1);
				else if(count>9&&count<18 )
					line2 = (byte)(line2 +1);
				else if(count>18&&count<27 )
					line3 = (byte)(line3 +1);
				else if(count>27&&count<36 )
					line4 = (byte)(line4 +1);
				else if(count>36&&count<45 )
					line5 =(byte) (line5 +1);
			}
		}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(AreaName+".Fishing.54."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(AreaName+".Fishing.54."+(counter), areaYaml.getItemStack(AreaName+".Fishing.54."+(counter+1)));
					areaYaml.removeKey(AreaName+".Fishing.54."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(AreaName+".Fishing.30."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(AreaName+".Fishing.30."+(counter), areaYaml.getItemStack(AreaName+".Fishing.30."+(counter+1)));
					areaYaml.removeKey(AreaName+".Fishing.30."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(AreaName+".Fishing.10."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(AreaName+".Fishing.10."+(counter), areaYaml.getItemStack(AreaName+".Fishing.10."+(counter+1)));
					areaYaml.removeKey(AreaName+".Fishing.10."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(AreaName+".Fishing.5."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(AreaName+".Fishing.5."+(counter), areaYaml.getItemStack(AreaName+".Fishing.5."+(counter+1)));
					areaYaml.removeKey(AreaName+".Fishing.5."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(AreaName+".Fishing.1."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(AreaName+".Fishing.1."+(counter), areaYaml.getItemStack(AreaName+".Fishing.1."+(counter+1)));
					areaYaml.removeKey(AreaName+".Fishing.1."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = line1; count <7;count++)
			if(areaYaml.contains(AreaName+".Fishing.54."+count))
				areaYaml.removeKey(AreaName+".Fishing.54."+count);
		for(int count = line2; count <7;count++)
			if(areaYaml.contains(AreaName+".Fishing.30."+count))
				areaYaml.removeKey(AreaName+".Fishing.30."+count);
		for(int count = line3; count <7;count++)
			if(areaYaml.contains(AreaName+".Fishing.10."+count))
				areaYaml.removeKey(AreaName+".Fishing.10."+count);
		for(int count = line4; count <7;count++)
			if(areaYaml.contains(AreaName+".Fishing.5."+count))
				areaYaml.removeKey(AreaName+".Fishing.5."+count);
		for(int count = line5; count <7;count++)
			if(areaYaml.contains(AreaName+".Fishing.1."+count))
				areaYaml.removeKey(AreaName+".Fishing.1."+count);
		areaYaml.saveConfig();
		return;
	}
	
	public void BlockItemSettingInventoryClose(InventoryCloseEvent event)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String ItemData = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(event.getInventory().getItem(4) != null)
			areaYaml.set(AreaName+".Mining."+ItemData+".100", event.getInventory().getItem(4));
		else
		{
			areaYaml.removeKey(AreaName+".Mining."+ItemData+".100");
			areaYaml.set(AreaName+".Mining."+ItemData,"0:0");
		}
		if(event.getInventory().getItem(13) != null)
			areaYaml.set(AreaName+".Mining."+ItemData+".90", event.getInventory().getItem(13));
		else
		{
			areaYaml.removeKey(AreaName+".Mining."+ItemData+".90");
			areaYaml.set(AreaName+".Mining."+ItemData+".90","0:0");
		}
		if(event.getInventory().getItem(22) != null)
			areaYaml.set(AreaName+".Mining."+ItemData+".50", event.getInventory().getItem(22));
		else
		{
			areaYaml.removeKey(AreaName+".Mining."+ItemData+".50");
			areaYaml.set(AreaName+".Mining."+ItemData+".50","0:0");
		}
		if(event.getInventory().getItem(31) != null)
			areaYaml.set(AreaName+".Mining."+ItemData+".10", event.getInventory().getItem(31));
		else
		{
			areaYaml.removeKey(AreaName+".Mining."+ItemData+".10");
			areaYaml.set(AreaName+".Mining."+ItemData+".10","0:0");
		}
		if(event.getInventory().getItem(40) != null)
			areaYaml.set(AreaName+".Mining."+ItemData+".1", event.getInventory().getItem(40));
		else
		{
			areaYaml.removeKey(AreaName+".Mining."+ItemData+".1");
			areaYaml.set(AreaName+".Mining."+ItemData+".1","0:0");
		}
		if(event.getInventory().getItem(49) != null)
			areaYaml.set(AreaName+".Mining."+ItemData+".0", event.getInventory().getItem(49));
		else
		{
			areaYaml.removeKey(AreaName+".Mining."+ItemData+".0");
			areaYaml.set(AreaName+".Mining."+ItemData+".0","0:0");
		}
		areaYaml.saveConfig();
		return;
	}

}