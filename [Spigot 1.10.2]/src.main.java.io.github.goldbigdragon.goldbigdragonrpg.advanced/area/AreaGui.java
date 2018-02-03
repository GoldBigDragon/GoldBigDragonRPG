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

import admin.OPboxGui;
import battle.BattleCalculator;
import effect.SoundEffect;
import main.MainServerOption;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;



public class AreaGui extends UtilGui
{
	public void areaListGui(Player player, short page)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String uniqueCode = "§0§0§2§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0전체 영역 목록 : " + (page+1));

		String[] areaList= areaYaml.getKeys().toArray(new String[0]);
		
		byte loc=0;
		String areaName = null;
		String world = null;
		for(int count = page*45; count < areaList.length;count++)
		{
			areaName = areaList[count];
			
			if(count > areaList.length || loc >= 45) break;
			world = areaYaml.getString(areaName+".World");
			int minXLoc = areaYaml.getInt(areaName+".X.Min");
			int minYLoc = areaYaml.getInt(areaName+".Y.Min");
			int minZLoc = areaYaml.getInt(areaName+".Z.Min");
			int maxXLoc = areaYaml.getInt(areaName+".X.Max");
			int maxYLoc = areaYaml.getInt(areaName+".Y.Max");
			int maxZLoc = areaYaml.getInt(areaName+".Z.Max");
			
			byte priority = (byte) areaYaml.getInt(areaName+".Priority");
			Stack2("§f§l" + areaName, 395,0,1,Arrays.asList(
					"§3월드 : "+world,"§3X 영역 : "+minXLoc+" ~ " + maxXLoc
					,"§3Y 영역 : "+minYLoc+" ~ " + maxYLoc
					,"§3Z 영역 : "+minZLoc+" ~ " + maxZLoc
					,"§3우선 순위 : "+ priority
					,"§8영역끼리 서로 겹칠 경우",
					"§8우선 순위가 더 높은 영역이",
					"§8적용됩니다."
					,"","§e[좌 클릭시 영역 설정]","§c[Shift + 우클릭시 영역 삭제]"), loc, inv);
			
			loc++;
		}
		
		if(areaList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 영역", 386,0,1,Arrays.asList("§7새로운 영역을 생성합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void areaSettingGui (Player player, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String uniqueCode = "§0§0§2§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 45, uniqueCode + "§0영역 설정");

		if(!areaYaml.getBoolean(areaName+".BlockUse"))
			Stack2("§f§l[블록 사용]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 9, inv);
		else
			Stack2("§f§l[블록 사용]", 116,0,1,Arrays.asList("","§a§l[   허용   ]",""), 9, inv);

		if(!areaYaml.getBoolean(areaName+".BlockPlace"))
			Stack2("§f§l[블록 설치]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 10, inv);
		else
			Stack2("§f§l[블록 설치]", 2,0,1,Arrays.asList("","§a§l[   허용   ]",""), 10, inv);

		if(!areaYaml.getBoolean(areaName+".BlockBreak"))
			Stack2("§f§l[블록 파괴]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 11, inv);
		else
			Stack2("§f§l[블록 파괴]", 278,0,1,Arrays.asList("","§a§l[   허용   ]",""), 11, inv);

		if(!areaYaml.getBoolean(areaName+".PVP"))
			Stack2("§f§l[   PVP   ]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 12, inv);
		else
			Stack2("§f§l[   PVP   ]", 267,0,1,Arrays.asList("","§a§l[   허용   ]",""), 12, inv);

		if(!areaYaml.getBoolean(areaName+".MobSpawn"))
			Stack2("§f§l[몬스터 스폰]", 166,0,1,Arrays.asList("","§c§l[   거부   ]",""), 13, inv);
		else
			Stack2("§f§l[몬스터 스폰]", 52,0,1,Arrays.asList("","§a§l[   허용   ]",""), 13, inv);

		if(!areaYaml.getBoolean(areaName+".Alert"))
			Stack2("§f§l[입장 메시지]", 166,0,1,Arrays.asList("","§c§l[   없음   ]",""), 14, inv);
		else
			Stack2("§f§l[입장 메시지]", 340,0,1,Arrays.asList("","§a§l[   전송   ]",""), 14, inv);

		if(!areaYaml.getBoolean(areaName+".SpawnPoint"))
			Stack2("§f§l[리스폰 장소]", 166,0,1,Arrays.asList("","§c§l[   불가   ]",""), 15, inv);
		else
			Stack2("§f§l[리스폰 장소]", 397,3,1,Arrays.asList("","§a§l[   가능   ]",""), 15, inv);

		if(!areaYaml.getBoolean(areaName+".Music"))
			Stack2("§f§l[배경음 재생]", 166,0,1,Arrays.asList("","§c§l[   중지   ]",""), 16, inv);
		else
			Stack2("§f§l[배경음 재생]", 84,0,1,Arrays.asList("","§a§l[   재생   ]",""), 16, inv);

		if(areaYaml.getInt(areaName+".RegenBlock")==0)
			Stack2("§f§l[블록 리젠]", 166,0,1,Arrays.asList("","§c§l[   중지   ]",""), 28, inv);
		else
			Stack2("§f§l[블록 리젠]", 103,0,1,Arrays.asList("","§a§l[   활성   ]","","§3"+areaYaml.getInt(areaName+".RegenBlock")+" 초 마다 리젠","","§c[플레이어가 직접 캔 블록만 리젠 됩니다]",""), 28, inv);

		Stack2("§f§l[특산품 설정]", 15,0,1,Arrays.asList("","§7현재 영역에서 블록을 캐면","§7지정된 아이템이 나오게","§7설정 합니다.","","§e[클릭시 특산품 설정]"), 19, inv);
		Stack2("§f§l[낚시 아이템]", 346,0,1,Arrays.asList("","§7현재 영역에서 낚시를 하여","§7얻을 수 있는 물건을 확률별로","§7설정합니다.","§e[클릭시 낚시 아이템 설정]"), 20, inv);
		Stack2("§f§l[우선순위 변경]", 384,0,1,Arrays.asList("","§7영역끼리 서로 겹칠 경우","§7우선 순위가 더 높은 영역이","§7적용됩니다.","§7이 속성을 이용하여 마을을 만들고,","§7마을 내부의 각종 상점 및","§7구역을 나눌 수 있습니다.","§9[   현재 우선 순위   ]","§f "+areaYaml.getInt(areaName+".Priority"),"","§e[클릭시 우선 순위 변경]"), 21, inv);
		Stack2("§f§l[몬스터 설정]", 383,0,1,Arrays.asList("","§7현재 영역에서 자연적으로","§7스폰되는 몬스터 대신에","§7커스텀 몬스터로 변경합니다.","","§e[클릭시 커스텀 몬스터 설정]","§c[몬스터 스폰 설정시 비활성]"), 22, inv);
		Stack2("§f§l[몬스터 스폰 설정]", 52,0,1,Arrays.asList("","§7현재 영역의 특정 구역에","§7특정 시각마다 몬스터를","§7스폰 합니다.","","§e[클릭시 몬스터 스폰 설정]"), 31, inv);
		Stack2("§f§l[메시지 변경]", 386,0,1,Arrays.asList("","§7영역 입장 메시지를 변경합니다.","","§e[클릭시 입장 메시지 설정]"), 23, inv);
		Stack2("§f§l[중심지 변경]", 138,0,1,Arrays.asList("","§7마을 귀환, 최근 방문 위치에서","§7리스폰 등의 현재 영역으로","§7텔레포트 되는 이벤트가 발생할 경우","§7현재 위치가 중심점이 됩니다.","","§3[  현재 중심지  ]","§3"+areaYaml.getString(areaName+".World")+" : "+areaYaml.getInt(areaName+".SpawnLocation.X")+","+areaYaml.getInt(areaName+".SpawnLocation.Y")+","+areaYaml.getInt(areaName+".SpawnLocation.Z"),"","§e[클릭시 현재 위치로 변경]"), 24, inv);
		
		if(areaYaml.getInt(areaName+".Restrict.MinNowLevel")+areaYaml.getInt(areaName+".Restrict.MinNowLevel")+
			areaYaml.getInt(areaName+".Restrict.MinRealLevel")+areaYaml.getInt(areaName+".Restrict.MaxRealLevel")==0)
			Stack2("§a§l[입장 레벨 제한 없음]", 166,0,1,Arrays.asList("","§7레벨에 따른 입장 제한이 없습니다.",""), 34, inv);
		else
			Stack2("§c§l[입장 레벨 제한]", 399,0,1,Arrays.asList("","§7레벨에 따른 입장 제한이 있습니다.",""
			,"§3[  최소 현재 레벨  ]", "  §3"+areaYaml.getInt(areaName+".Restrict.MinNowLevel")
			,"§3[  최대 현재 레벨  ]", "  §3"+areaYaml.getInt(areaName+".Restrict.MaxNowLevel")
			,"§7 ▼ 마비노기 시스템일 경우 추가 적용 ▼"
			,"§3[  최소 누적 레벨  ]", "  §3"+areaYaml.getInt(areaName+".Restrict.MinRealLevel")
			,"§3[  최대 누적 레벨  ]", "  §3"+areaYaml.getInt(areaName+".Restrict.MaxRealLevel"),""), 34, inv);
		String lore = "";
		short tracknumber = (short) areaYaml.getInt(areaName+".BGM");
		lore = " %enter%§7영역 입장시 테마 음을%enter%§7재생 시킬 수 있습니다.%enter% %enter%§9[클릭시 노트블록 사운드 설정]%enter% %enter%§3[트랙] §9"+ tracknumber+"%enter%"
		+"§3[제목] §9"+ new otherplugins.NoteBlockApiMain().getTitle(tracknumber)+"%enter%"
		+"§3[저자] §9"+new otherplugins.NoteBlockApiMain().getAuthor(tracknumber)+"%enter%§3[설명] ";
		
		String description = new otherplugins.NoteBlockApiMain().getDescription(areaYaml.getInt(areaName+".BGM"));
		String lore2="";
		short a = 0;
		for(int count = 0; count <description.toCharArray().length; count++)
		{
			lore2 = lore2+"§9"+description.toCharArray()[count];
			a++;
			if(a >= 15)
			{
				a = 0;
				lore2 = lore2+"%enter%      ";
			}
		}
		lore = lore + lore2;
		
		Stack2("§f§l[영역 배경음]", 2263,0,1,Arrays.asList(lore.split("%enter%")), 25, inv);
		
		Stack2("§f§l영역 이동", 368,0,1,Arrays.asList("§7현재 영역으로 빠르게 이동합니다."), 40, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7영역 목록으로 돌아갑니다."), 36, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7현재 창을 닫습니다.","§0"+areaName), 44, inv);
		
		player.openInventory(inv);
		return;
	}
	
	public void areaMonsterSpawnSettingGui(Player player, short page, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		String uniqueCode = "§0§0§2§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 몬스터 스폰 룰 : " + (page+1));
		if(!areaYaml.contains(areaName+".MonsterSpawnRule"))
		{
			areaYaml.createSection(areaName+".MonsterSpawnRule");
			areaYaml.saveConfig();
		}
		String[] ruleList= areaYaml.getConfigurationSection(areaName+".MonsterSpawnRule").getKeys(false).toArray(new String[0]);
		byte loc=0;
		for(int count = page*45; count <ruleList.length ;count++)
		{
			if(count > ruleList.length || loc >= 45) break;
			String ruleNumber = ruleList[count];
			if(areaYaml.contains(areaName+".MonsterSpawnRule."+ruleNumber+".Monster"))
				Stack2("§0§l" + (ruleNumber), 383,0,1,Arrays.asList(
						"§6[     스폰 옵션     ]","§c-영역에 유저가 있을 때만 작동 -","§6월드 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".loc.world"),
						"§6좌표 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".loc.x")+","+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".loc.y")+","+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".loc.z"),
						"§6인식 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".range")+"블록",
						"§6시간 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".timer")+"초마다 "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".count")+"마리 스폰",
						"§6최대 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".max")+"마리",
						"§6스폰 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".Monster")
						,"","§c[Shift + 우클릭시 룰 삭제]"), loc, inv);
			else
				Stack2("§0§l" + (ruleNumber), 52,0,1,Arrays.asList(
					"§6[     스폰 옵션     ]","§c-영역에 유저가 있을 때만 작동 -","§6월드 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".loc.world"),
					"§6좌표 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".loc.x")+","+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".loc.y")+","+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".loc.z"),
					"§6인식 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".range")+"블록",
					"§6시간 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".timer")+"초마다 "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".count")+"마리 스폰",
					"§6최대 : "+areaYaml.getString(areaName+".MonsterSpawnRule."+ruleNumber+".max")+"마리"
					,"","§c[Shift + 우클릭시 룰 삭제]"), loc, inv);
			loc++;
		}

		if(ruleList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 규칙 추가", 52,0,1,Arrays.asList("§7새 스폰 규칙을 추가합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ areaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void areaMonsterSettingGui(Player player, short page, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");

		String uniqueCode = "§0§0§2§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 대체 몬스터 : " + (page+1));

		String[] monsterNameList= areaYaml.getConfigurationSection(areaName+".Monster").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		short mobNameListLength = (short) monsterNameList.length;
		String monsterName = null;
		String name = null;
		for(int count = page*45; count <mobNameListLength ;count++)
		{
			monsterName = monsterNameList[count];
			if(monsterYaml.contains(monsterName) == true)
			{
				name = monsterYaml.getString(monsterName+".Name");
				if(count > mobNameListLength || loc >= 45) break;
				Stack2("§f§l" + monsterName, 383,0,1,Arrays.asList(
						"§f이름 : " + name,"§f타입 : " + monsterYaml.getString(monsterName+".Type"),
						"§f생명력 : " + monsterYaml.getInt(monsterName+".HP"),"§f경험치 : " + monsterYaml.getInt(monsterName+".EXP"),
						"§f골드 : " + monsterYaml.getInt(monsterName+".MIN_Money")+" ~ " +monsterYaml.getInt(monsterName+".MAX_Money"),
						"","§c[Shift + 우클릭시 등록 해제]"), loc, inv);
				loc++;
			}
			else
			{
				areaYaml.removeKey(areaName+".Monster."+monsterName);
				areaYaml.saveConfig();
			}
		}
		if(mobNameListLength-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l몬스터 추가", 52,0,1,Arrays.asList("§7새 커스텀 몬스터를 추가합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ areaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void areaFishSettingGui(Player player, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		String uniqueCode = "§1§0§2§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 추가 어류");
		
		Stack2("§a§l[     54%     ]", 160,5,1,Arrays.asList("§7이 줄에는 54% 확률로 낚일 아이템을 올리세요."), 0, inv);
		Stack2("§e§l[     30%     ]", 160,4,1,Arrays.asList("§7이 줄에는 30% 확률로 낚일 아이템을 올리세요."), 9, inv);
		Stack2("§6§l[     10%     ]", 160,1,1,Arrays.asList("§7이 줄에는 10% 확률로 낚일 아이템을 올리세요."), 18, inv);
		Stack2("§c§l[      5%      ]", 160,14,1,Arrays.asList("§7이 줄에는 5% 확률로 낚일 아이템을 올리세요."), 27, inv);
		Stack2("§7§l[      1%      ]", 160,10,1,Arrays.asList("§7이 줄에는 1% 확률로 낚일 아이템을 올리세요."), 36, inv);

		String[] fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.54").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(areaName+".Fishing.54."+fishingItemList[count]), count+1, inv);
		fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.30").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(areaName+".Fishing.30."+fishingItemList[count]), count+10, inv);
		fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.10").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(areaName+".Fishing.10."+fishingItemList[count]), count+19, inv);
		fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.5").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(areaName+".Fishing.5."+fishingItemList[count]), count+28, inv);
		fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.1").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			ItemStackStack(areaYaml.getItemStack(areaName+".Fishing.1."+fishingItemList[count]), count+37, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ areaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void areaBlockSettingGui(Player player, short page, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		event.EventInteract I = new event.EventInteract();

		String uniqueCode = "§0§0§2§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 특산품 : " + (page+1));

		String[] blockIdDataList= areaYaml.getConfigurationSection(areaName+".Mining").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count <blockIdDataList.length ;count++)
		{
			short id = Short.parseShort(blockIdDataList[count].split(":")[0]);
			byte data = Byte.parseByte(blockIdDataList[count].split(":")[1]);

			Stack2(I.SetItemDefaultName(id, (byte) data), id,data,1,Arrays.asList(
					"","§c[Shift + 우클릭시 등록 해제]"), loc, inv);
				loc++;
		}
		
		if(blockIdDataList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l특산물 추가", 52,0,1,Arrays.asList("§7새로운 블록을 설정합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ areaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void areaBlockItemSettingGui(Player player,String areaName,String itemData)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		String uniqueCode = "§1§0§2§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0해당 블록을 캐면 나올 아이템");

		ItemStack item = areaYaml.getItemStack(areaName+".Mining."+itemData+".100");
		
		ItemStackStack(item, 4, inv);

		Stack2("§c§l[아이템 넣기>", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 0, inv);
		Stack2("§c§l[아이템 넣기>", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 1, inv);	
		Stack2("§c§l[아이템 넣기>", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 2, inv);
		Stack2("§c§l[아이템 넣기>", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 3, inv);	
		Stack2("§c§l<아이템 넣기]", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 5, inv);
		Stack2("§c§l<아이템 넣기]", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 6, inv);	
		Stack2("§c§l<아이템 넣기]", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 7, inv);
		Stack2("§c§l<아이템 넣기]", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 8, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".90");
		ItemStackStack(item, 13, inv);
		Stack2("§c§l[아이템 넣기>", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 9, inv);
		Stack2("§c§l[아이템 넣기>", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 10, inv);	
		Stack2("§c§l[아이템 넣기>", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 11, inv);
		Stack2("§c§l[아이템 넣기>", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 12, inv);	
		Stack2("§c§l<아이템 넣기]", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 14, inv);
		Stack2("§c§l<아이템 넣기]", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 15, inv);	
		Stack2("§c§l<아이템 넣기]", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 16, inv);
		Stack2("§c§l<아이템 넣기]", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 17, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".50");
		ItemStackStack(item, 22, inv);
		Stack2("§c§l[아이템 넣기>", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 18, inv);
		Stack2("§c§l[아이템 넣기>", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 19, inv);	
		Stack2("§c§l[아이템 넣기>", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 20, inv);
		Stack2("§c§l[아이템 넣기>", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 21, inv);	
		Stack2("§c§l<아이템 넣기]", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 23, inv);
		Stack2("§c§l<아이템 넣기]", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 24, inv);	
		Stack2("§c§l<아이템 넣기]", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 25, inv);
		Stack2("§c§l<아이템 넣기]", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 26, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".10");
		ItemStackStack(item, 31, inv);
		Stack2("§c§l[아이템 넣기>", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 27, inv);
		Stack2("§c§l[아이템 넣기>", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 28, inv);	
		Stack2("§c§l[아이템 넣기>", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 29, inv);
		Stack2("§c§l[아이템 넣기>", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 30, inv);	
		Stack2("§c§l<아이템 넣기]", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 32, inv);
		Stack2("§c§l<아이템 넣기]", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 33, inv);	
		Stack2("§c§l<아이템 넣기]", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 34, inv);
		Stack2("§c§l<아이템 넣기]", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 35, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".1");
		ItemStackStack(item, 40, inv);
		Stack2("§c§l[아이템 넣기>", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 36, inv);
		Stack2("§c§l[아이템 넣기>", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 37, inv);	
		Stack2("§c§l[아이템 넣기>", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 38, inv);
		Stack2("§c§l[아이템 넣기>", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 39, inv);	
		Stack2("§c§l<아이템 넣기]", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 41, inv);
		Stack2("§c§l<아이템 넣기]", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 42, inv);	
		Stack2("§c§l<아이템 넣기]", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 43, inv);
		Stack2("§c§l<아이템 넣기]", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 44, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".0");
		ItemStackStack(item, 49, inv);
		Stack2("§c§l[아이템 넣기>", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 46, inv);	
		Stack2("§c§l[아이템 넣기>", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 47, inv);
		Stack2("§c§l[아이템 넣기>", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 48, inv);	
		Stack2("§c§l<아이템 넣기]", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 50, inv);
		Stack2("§c§l<아이템 넣기]", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 51, inv);	
		Stack2("§c§l<아이템 넣기]", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 52, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+itemData), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ areaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void areaAddMonsterListGui(Player player, short page,String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");

		String uniqueCode = "§0§0§2§0§7§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 몬스터 선택 : " + (page+1));

		String[] monsterList= monsterYaml.getKeys().toArray(new String[0]);
		String[] monsterNameList= areaYaml.getConfigurationSection(areaName+".Monster").getKeys(false).toArray(new String[0]);

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
				if(monsterNameList[count2].equals(monsterName))
				{
					isExit=true;
					break;
				}
			}
			
			if(!isExit)
			{
				lore = null;
				lore = "%enter%§f§l 이름 : §f"+monsterYaml.getString(monsterName+".Name")+"%enter%";
				lore = lore+"§f§l 타입 : §f"+monsterYaml.getString(monsterName+".Type")+"%enter%";
				lore = lore+"§f§l 스폰 바이옴 : §f"+monsterYaml.getString(monsterName+".Biome")+"%enter%";
				lore = lore+"§c§l 생명력 : §f"+monsterYaml.getInt(monsterName+".HP")+"%enter%";
				lore = lore+"§b§l 경험치 : §f"+monsterYaml.getInt(monsterName+".EXP")+"%enter%";
				lore = lore+"§e§l 드랍 금액 : §f"+monsterYaml.getInt(monsterName+".MIN_Money")+" ~ "+monsterYaml.getInt(monsterName+".MAX_Money")+"%enter%";
				lore = lore+"§c§l "+MainServerOption.statSTR+" : §f"+monsterYaml.getInt(monsterName+".STR")
				+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), false) + "]%enter%";
				lore = lore+"§a§l "+MainServerOption.statDEX+" : §f"+monsterYaml.getInt(monsterName+".DEX")
				+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, false) + "]%enter%";
				lore = lore+"§9§l "+MainServerOption.statINT+" : §f"+monsterYaml.getInt(monsterName+".INT")
				+"§7 [폭공 : " + (monsterYaml.getInt(monsterName+".INT")/4)+ " ~ "+(int)(monsterYaml.getInt(monsterName+".INT")/2.5)+"]%enter%";
				lore = lore+"§7§l "+MainServerOption.statWILL+" : §f"+monsterYaml.getInt(monsterName+".WILL")
				+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
				lore = lore+"§e§l "+MainServerOption.statLUK+" : §f"+monsterYaml.getInt(monsterName+".LUK")
				+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
				lore = lore+"§7§l 방어 : §f"+monsterYaml.getInt(monsterName+".DEF")+"%enter%";
				lore = lore+"§b§l 보호 : §f"+monsterYaml.getInt(monsterName+".Protect")+"%enter%";
				lore = lore+"§9§l 마법 방어 : §f"+monsterYaml.getInt(monsterName+".Magic_DEF")+"%enter%";
				lore = lore+"§1§l 마법 보호 : §f"+monsterYaml.getInt(monsterName+".Magic_Protect")+"%enter%";
				lore = lore+"%enter%§e§l[좌 클릭시 몬스터 등록]";

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
				
				Stack("§f"+monsterName, id, data, 1,Arrays.asList(scriptA), loc, inv);
				loc++;
			}
		}
		
		if(monsterList.length-(page*44)>45)
			Stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+areaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void areaSpawnSpecialMonsterListGui(Player player, short page,String areaName,String ruleCount)
	{
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		String uniqueCode = "§0§0§2§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 특수 몬스터  : " + (page+1));

		String[] monsterList= monsterYaml.getKeys().toArray(new String[0]);

		byte loc=0;
		String monsterName = null;
		String lore = null;
		for(int count = page*45; count < monsterList.length;count++)
		{
			if(count > monsterList.length || loc >= 45) break;
			monsterName = monsterList[count];
			lore = null;
			lore = "%enter%§f§l 이름 : §f"+monsterYaml.getString(monsterName+".Name")+"%enter%";
			lore = lore+"§f§l 타입 : §f"+monsterYaml.getString(monsterName+".Type")+"%enter%";
			lore = lore+"§f§l 스폰 바이옴 : §f"+monsterYaml.getString(monsterName+".Biome")+"%enter%";
			lore = lore+"§c§l 생명력 : §f"+monsterYaml.getInt(monsterName+".HP")+"%enter%";
			lore = lore+"§b§l 경험치 : §f"+monsterYaml.getInt(monsterName+".EXP")+"%enter%";
			lore = lore+"§e§l 드랍 금액 : §f"+monsterYaml.getInt(monsterName+".MIN_Money")+" ~ "+monsterYaml.getInt(monsterName+".MAX_Money")+"%enter%";
			lore = lore+"§c§l "+MainServerOption.statSTR+" : §f"+monsterYaml.getInt(monsterName+".STR")
			+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), false) + "]%enter%";
			lore = lore+"§a§l "+MainServerOption.statDEX+" : §f"+monsterYaml.getInt(monsterName+".DEX")
			+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, false) + "]%enter%";
			lore = lore+"§9§l "+MainServerOption.statINT+" : §f"+monsterYaml.getInt(monsterName+".INT")
			+"§7 [폭공 : " + (monsterYaml.getInt(monsterName+".INT")/4)+ " ~ "+(int)(monsterYaml.getInt(monsterName+".INT")/2.5)+"]%enter%";
			lore = lore+"§7§l "+MainServerOption.statWILL+" : §f"+monsterYaml.getInt(monsterName+".WILL")
			+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
			lore = lore+"§e§l "+MainServerOption.statLUK+" : §f"+monsterYaml.getInt(monsterName+".LUK")
			+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
			lore = lore+"§7§l 방어 : §f"+monsterYaml.getInt(monsterName+".DEF")+"%enter%";
			lore = lore+"§b§l 보호 : §f"+monsterYaml.getInt(monsterName+".Protect")+"%enter%";
			lore = lore+"§9§l 마법 방어 : §f"+monsterYaml.getInt(monsterName+".Magic_DEF")+"%enter%";
			lore = lore+"§1§l 마법 보호 : §f"+monsterYaml.getInt(monsterName+".Magic_Protect")+"%enter%";
			lore = lore+"%enter%§e§l[좌 클릭시 몬스터 등록]";

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
			
			Stack("§f"+monsterName, id, data, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(monsterList.length-(page*44)>45)
			Stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack("§f§l취소", 166,0,1,Arrays.asList("§7지정 몬스터 스폰대신","§7영역에 등록 된 몬스터를","§7랜덤하게 스폰 합니다.","§0"+areaName,"§0"+ruleCount), 49, inv);
		player.openInventory(inv);
	}

	public void areaMusicSettingGui(Player player, int page,String areaName)
	{
		String uniqueCode = "§0§0§2§0§9§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 배경음 : " + (page+1));
		byte loc=0;
		byte model = (byte) new util.UtilNumber().RandomNum(0, 11);
		for(int count = page*45; count < new otherplugins.NoteBlockApiMain().Musics.size();count++)
		{
			if(model<11)
				model++;
			else
				model=0;
			String lore = " %enter%§3[트랙] §9"+ count+"%enter%"
			+"§3[제목] §9"+ new otherplugins.NoteBlockApiMain().getTitle(count)+"%enter%"
			+"§3[저자] §9"+new otherplugins.NoteBlockApiMain().getAuthor(count)+"%enter%§3[설명] ";
			String description = new otherplugins.NoteBlockApiMain().getDescription(count);
			String lore2="";
			short a = 0;
			for(int counter = 0; counter <description.toCharArray().length; counter++)
			{
				lore2 = lore2+"§9"+description.toCharArray()[counter];
				a++;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2+"%enter% %enter%§e[좌 클릭시 배경음 설정]";
			if(count > new otherplugins.NoteBlockApiMain().Musics.size() || loc >= 45) break;
				Stack2("§f§l" + count, 2256+model,0,1,Arrays.asList(lore.split("%enter%")), loc, inv);
			
			loc++;
		}
		
		if(new otherplugins.NoteBlockApiMain().Musics.size()-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+areaName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void areaListGuiClick(InventoryClickEvent event)
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
			String areaName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			
			if(slot == 45)//이전 목록
				new OPboxGui().opBoxGuiMain(player, (byte) 2);
			else if(slot == 48)//이전 페이지
				areaListGui(player, (short) (page-1));
			else if(slot == 49)//영역 추가
			{
			  	YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				player.closeInventory();
				event.EventInteract IT = new event.EventInteract();
				player.sendMessage("§3[영역] : " + IT.SetItemDefaultName((short) configYaml.getInt("Server.AreaSettingWand"),(byte)0) +"§3 아이템으로 구역을 설정을 한 뒤,");
				player.sendMessage("§6§l /영역 <영역이름> 생성 §3명령어를 입력해 주세요!");
				SoundEffect.SP((Player)player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
			else if(slot == 50)//다음 페이지
				areaListGui(player, (short) (page+1));
			else
			{
				if(event.isLeftClick())
					areaSettingGui(player, areaName);
				else if(event.isShiftClick() && event.isRightClick())
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					for(int count = 0; count < MainServerOption.AreaList.get(areaYaml.getString(areaName+".World")).size(); count++)
						if(MainServerOption.AreaList.get(areaYaml.getString(areaName+".World")).get(count).areaName.equals(areaName))
							MainServerOption.AreaList.get(areaYaml.getString(areaName+".World")).remove(count);
					areaYaml.removeKey(areaName);
					areaYaml.saveConfig();
					areaListGui(player, page);
				}
			}
		}
	}
	
	public void areaSettingGuiInventoryClick(InventoryClickEvent event)
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
			String areaName = ChatColor.stripColor(event.getInventory().getItem(44).getItemMeta().getLore().get(1));
			if(slot == 36)//이전 화면
				areaListGui(player,(short) 0);
			else if(slot >= 9 && slot <= 16)
			{
				if(slot == 9)//블록 사용
				{
					if(areaYaml.getBoolean(areaName+".BlockUse") == false)
						areaYaml.set(areaName+".BlockUse", true);
					else
						areaYaml.set(areaName+".BlockUse", false);
				}
				else if(slot == 10)//블록 설치
				{
					if(areaYaml.getBoolean(areaName+".BlockPlace") == false)
						areaYaml.set(areaName+".BlockPlace", true);
					else
						areaYaml.set(areaName+".BlockPlace", false);
				}
				else if(slot == 11)//블록 파괴
				{
					if(areaYaml.getBoolean(areaName+".BlockBreak") == false)
						areaYaml.set(areaName+".BlockBreak", true);
					else
						areaYaml.set(areaName+".BlockBreak", false);
				}
				else if(slot == 12)//PVP
				{
					if(areaYaml.getBoolean(areaName+".PVP") == false)
						areaYaml.set(areaName+".PVP", true);
					else
						areaYaml.set(areaName+".PVP", false);
				}
				else if(slot == 13)//몬스터 스폰
				{
					if(areaYaml.getBoolean(areaName+".MobSpawn") == false)
						areaYaml.set(areaName+".MobSpawn", true);
					else
						areaYaml.set(areaName+".MobSpawn", false);
				}
				else if(slot == 14)//입장 메시지
				{
					if(areaYaml.getBoolean(areaName+".Alert") == false)
						areaYaml.set(areaName+".Alert", true);
					else
						areaYaml.set(areaName+".Alert", false);
				}
				else if(slot == 15)//리스폰 장소
				{
					if(areaYaml.getBoolean(areaName+".SpawnPoint") == false)
						areaYaml.set(areaName+".SpawnPoint", true);
					else
						areaYaml.set(areaName+".SpawnPoint", false);
				}
				else if(slot == 16)//배경음 재생
				{
					if(areaYaml.getBoolean(areaName+".Music") == false)
						areaYaml.set(areaName+".Music", true);
					else
						areaYaml.set(areaName+".Music", false);
				}
				areaYaml.saveConfig();
				areaSettingGui(player, areaName);
			}
			else if(slot == 21)//우선 순위 변경
			{
				UserDataObject u = new UserDataObject();
				player.closeInventory();
				u.setType(player, "Area");
				u.setString(player, (byte)2, "Priority");
				u.setString(player, (byte)3, areaName);
				player.sendMessage("§a[영역] : §e"+areaName+"§a 영역의 우선 순위를 입력하세요!");
				player.sendMessage("§7(최소 0 ~ 최대 100)");
			}
			else if(slot == 23)//메시지 변경
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.sendMessage("§6/영역 "+areaName+" 이름 <문자열>§e\n - "+areaName+" 구역의 알림 메시지에 보일 이름을 정합니다.");
				player.sendMessage("§6/영역 "+areaName+" 설명 <문자열>§e\n - "+areaName+" 구역의 알림 메시지에 보일 부가 설명을 정합니다.");
				player.sendMessage("§6%player%§f - 플레이어 지칭하기 -");
				player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
				"§3&3 §4&4 §5&5 " +
						"§6&6 §7&7 §8&8 " +
				"§9&9 §a&a §b&b §c&c " +
						"§d&d §e&e §f&f");
				player.closeInventory();
			}
			else if(slot == 24)//중심지 변경
			{
				areaYaml.set(areaName+".World", player.getLocation().getWorld().getName());
				areaYaml.set(areaName+".SpawnLocation.X", player.getLocation().getX());
				areaYaml.set(areaName+".SpawnLocation.Y", player.getLocation().getY());
				areaYaml.set(areaName+".SpawnLocation.Z", player.getLocation().getZ());
				areaYaml.set(areaName+".SpawnLocation.Pitch", player.getLocation().getPitch());
				areaYaml.set(areaName+".SpawnLocation.Yaw", player.getLocation().getYaw());
				areaYaml.saveConfig();
				areaSettingGui(player, areaName);
			}
			else if(slot == 25)//영역 배경음 설정
			{
				if(new otherplugins.NoteBlockApiMain().SoundList(player,true))
					areaMusicSettingGui(player, 0, areaName);
				else
					SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.9F);
			}
			else if(slot == 28)//블록 리젠
			{
				if(areaYaml.getInt(areaName+".RegenBlock")==0)
				{
					player.closeInventory();
					UserDataObject u = new UserDataObject();
					areaYaml.set(areaName+".RegenBlock", 1);
					areaYaml.saveConfig();
					u.setType(player, "Area");
					u.setString(player, (byte)2, "ARR");
					u.setString(player, (byte)3, areaName);
					player.sendMessage("§a[영역] : §e"+areaName+"§a 영역의 블록 리젠 속도를 설정하세요!");
					player.sendMessage("§7(최소 1초 ~ 최대 3600초(1시간))");
				}
				else
				{
					areaYaml.set(areaName+".RegenBlock", 0);
					areaYaml.saveConfig();
					areaSettingGui(player, areaName);
				}
			}
			else if(slot == 19)//특산품 설정
				areaBlockSettingGui(player, (short) 0, areaName);
			else if(slot == 20)//낚시 아이템
				areaFishSettingGui(player, areaName);
			else if(slot == 22)//몬스터 설정
				areaMonsterSettingGui(player,(short) 0, areaName);
			else if(slot == 31)//몬스터 스폰 룰
				areaMonsterSpawnSettingGui(player, (short) 0, areaName);
			else if(slot == 34)//레벨 제한
			{
				UserDataObject u = new UserDataObject();
				player.closeInventory();
				u.setType(player, "Area");
				u.setString(player, (byte)2, "MinNLR");
				u.setString(player, (byte)3, areaName);
				player.sendMessage("§a[영역] : §e"+areaName+"§a 영역의 입장에 필요한 최소 레벨을 입력 하세요!§7 (0 입력시 제한 없음)");
			}
			else if(slot == 40)//영역 이동
			{
				player.closeInventory();
				player.teleport(new Location(Bukkit.getWorld(areaYaml.getString(areaName+".World")),areaYaml.getInt(areaName+".SpawnLocation.X"), areaYaml.getInt(areaName+".SpawnLocation.Y"),areaYaml.getInt(areaName+".SpawnLocation.Z"),areaYaml.getInt(areaName+".SpawnLocation.Yaw"),areaYaml.getInt(areaName+".SpawnLocation.Pitch")));
			}
		}
		return;
	}

	public void areaMonsterSettingGuiClick(InventoryClickEvent event)
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
			String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 화면
				areaSettingGui(player, areaName);
			else if(slot == 48)//이전 페이지
				areaMonsterSettingGui(player, (short) (page-1), areaName);
			else if(slot == 49)//몬스터 추가
			{
			  	YamlLoader monsterYaml = new YamlLoader();
				monsterYaml.getConfig("Monster/MonsterList.yml");
				if(monsterYaml.getKeys().size() == 0)
				{
					SoundEffect.SP(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0F, 1.8F);
					player.sendMessage("§c[영역] : 현재 등록된 커스텀 몬스터가 존재하지 않습니다!");
					player.sendMessage("§6§l/몬스터 <이름> 생성 §e해당 이름을 가진 몬스터를 생성합니다.");
				}
				else
					areaAddMonsterListGui(player, page, areaName);
			}
			else if(slot == 50)//다음 페이지
				areaMonsterSettingGui(player, (short) (page+1),areaName);
			else
			{
				if(event.isShiftClick() && event.isRightClick())
				{
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					String monsterName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					areaYaml.removeKey(areaName+".Monster."+monsterName);
					areaYaml.saveConfig();
					areaMonsterSettingGui(player, page,areaName);
				}
			}
		}
	}

	public void areaFishSettingGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
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
			areaSettingGui(player, areaName);
		}
	}

	public void areaBlockSettingGuiClick(InventoryClickEvent event)
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
			String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				areaSettingGui(player, areaName);
			else if(slot == 48)//이전 페이지
				areaBlockSettingGui(player, (short) (page-1), areaName);
			else if(slot == 49)//특산물 추가
			{
				player.closeInventory();
				player.sendMessage("§3[영역] : 설정할 블록을 좌클릭 하세요!");

				UserDataObject u = new UserDataObject();
				u.setType(player, "Area");
				u.setString(player, (byte)2, areaName);
				u.setString(player, (byte)3, "ANBI");
			}
			else if(slot == 50)//다음 페이지
				areaBlockSettingGui(player, (short) (page+1), areaName);
			else
			{
				String blockName = event.getCurrentItem().getTypeId()+":"+event.getCurrentItem().getData().getData();
				if(!event.isShiftClick()&&event.isLeftClick())
					areaBlockItemSettingGui(player, areaName, blockName);
				else if(event.isShiftClick() && event.isRightClick())
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					areaYaml.removeKey(areaName+".Mining."+blockName);
					areaYaml.saveConfig();
					areaBlockSettingGui(player, page, areaName);
				}
			}
		}
	}

	public void areaBlockItemSettingGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(!event.getClickedInventory().getTitle().equals("container.inventory"))
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
				areaBlockSettingGui(player, (short) 0, areaName);
			}
		}
	}

	public void areaAddMonsterSpawnRuleGuiClick(InventoryClickEvent event)
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
			String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			if(slot == 45)//이전 목록
				areaSettingGui(player, areaName);
			else if(slot == 48)//이전 페이지
				areaMonsterSpawnSettingGui(player, (short) (page-1), areaName);
			else if(slot == 49)//룰 추가
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				long count = new util.ETC().getNowUTC();
				areaYaml.set(areaName+".MonsterSpawnRule."+count+".range", 1);
				areaYaml.set(areaName+".MonsterSpawnRule."+count+".count", 4);
				areaYaml.set(areaName+".MonsterSpawnRule."+count+".timer", 10);
				areaYaml.set(areaName+".MonsterSpawnRule."+count+".max", 10);
				UserDataObject u = new UserDataObject();
				u.setType(player, "Area");
				u.setString(player, (byte)1, count+"");
				u.setString(player, (byte)2, areaName);
				u.setString(player, (byte)3, "MLS");
				areaYaml.saveConfig();
				player.sendMessage("§a[영역] : 몬스터가 스폰 될 위치를 마우스 우 클릭 하세요!");
				player.closeInventory();
			}
			else if(slot == 50)//다음 페이지
				areaMonsterSpawnSettingGui(player, (short) (page+1), areaName);
			else if(event.isRightClick()&&event.isShiftClick())
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
				areaYaml.removeKey(areaName+".MonsterSpawnRule."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				areaYaml.saveConfig();
				areaMonsterSpawnSettingGui(player, (short) page, areaName);
			}
		}
	}
	
	public void areaAddMonsterListGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
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
				areaMonsterSettingGui(player, (short) 0, areaName);
			else if(slot == 45)//이전 페이지
				areaAddMonsterListGui(player, (short) (page-1), areaName);
			else if(slot == 50)//다음 페이지
				areaAddMonsterListGui(player, (short) (page+1), areaName);
			else
			{
				String mobName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				SoundEffect.SP(player, Sound.ENTITY_WOLF_AMBIENT, 0.8F, 1.0F);
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				areaYaml.createSection(areaName+".Monster."+mobName);
				areaYaml.saveConfig();
				areaAddMonsterListGui(player, page, areaName);
			}
		}
	}

	public void areaSpawnSpecialMonsterListGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		String areaName = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(3));
		String ruleCounter = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(4));
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		if(slot == 49)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			areaMonsterSpawnSettingGui(player, (short) 0, areaName);
			new area.AreaMain().AreaMonsterSpawnAdd(areaName, ruleCounter);
		}
		else if(slot == 48)//이전 페이지
			areaSpawnSpecialMonsterListGui(player, (short) (page-1), areaName, ruleCounter);
		else if(slot == 50)//다음 페이지
			areaSpawnSpecialMonsterListGui(player, (short) (page+1), areaName, ruleCounter);
		else
		{
			String mobName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 0.8F, 1.0F);
		  	YamlLoader areaYaml = new YamlLoader();
			areaYaml.getConfig("Area/AreaList.yml");
			areaYaml.set(areaName+".MonsterSpawnRule."+ruleCounter+".Monster", mobName);
			areaYaml.saveConfig();
			areaMonsterSpawnSettingGui(player, (short) 0, areaName);
			
			new area.AreaMain().AreaMonsterSpawnAdd(areaName, ruleCounter);
		}
	}

	public void areaMusicSettingGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		int slot = event.getSlot();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);

		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(areaName.equals("DeathBGM¡"))
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)
				new admin.OPboxGui().opBoxGuiDeath(player);
			else if(slot == 48)
				areaMusicSettingGui(player, page-1,areaName);
			else if(slot == 50)
				areaMusicSettingGui(player, page+1,areaName);
			else
			{
			  	YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				configYaml.set("Death.Track", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
				configYaml.saveConfig();
				new admin.OPboxGui().opBoxGuiDeath(player);
			}
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				areaSettingGui(player, areaName);
			else if(slot == 48)//이전 페이지
				areaMusicSettingGui(player, page-1,areaName);
			else if(slot == 50)//다음 페이지
				areaMusicSettingGui(player, page+1,areaName);
			else
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				areaYaml.set(areaName+".BGM", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
				areaYaml.saveConfig();
				areaSettingGui(player, areaName);
			}
		}
	}

	
	
	public void fishingSettingInventoryClose(InventoryCloseEvent event)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		byte loc = 0;
		for(int count = 1; count < 9; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.54."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.54."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 10; count < 18; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.30."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.30."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 19; count < 27; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.10."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.10."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 28; count < 36; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.5."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.5."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 37; count < 45; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.1."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.1."+loc);
			areaYaml.saveConfig();
		}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.54."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.54."+(counter), areaYaml.getItemStack(areaName+".Fishing.54."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.54."+(counter+1));
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
			if(areaYaml.getItemStack(areaName+".Fishing.54."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.54."+(counter), areaYaml.getItemStack(areaName+".Fishing.54."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.54."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.30."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.30."+(counter), areaYaml.getItemStack(areaName+".Fishing.30."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.30."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.10."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.10."+(counter), areaYaml.getItemStack(areaName+".Fishing.10."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.10."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.5."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.5."+(counter), areaYaml.getItemStack(areaName+".Fishing.5."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.5."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.1."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.1."+(counter), areaYaml.getItemStack(areaName+".Fishing.1."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.1."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = line1; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.54."+count))
				areaYaml.removeKey(areaName+".Fishing.54."+count);
		for(int count = line2; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.30."+count))
				areaYaml.removeKey(areaName+".Fishing.30."+count);
		for(int count = line3; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.10."+count))
				areaYaml.removeKey(areaName+".Fishing.10."+count);
		for(int count = line4; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.5."+count))
				areaYaml.removeKey(areaName+".Fishing.5."+count);
		for(int count = line5; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.1."+count))
				areaYaml.removeKey(areaName+".Fishing.1."+count);
		areaYaml.saveConfig();
		return;
	}
	
	public void blockItemSettingInventoryClose(InventoryCloseEvent event)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String itemData = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(event.getInventory().getItem(4) != null)
			areaYaml.set(areaName+".Mining."+itemData+".100", event.getInventory().getItem(4));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".100");
			areaYaml.set(areaName+".Mining."+itemData,"0:0");
		}
		if(event.getInventory().getItem(13) != null)
			areaYaml.set(areaName+".Mining."+itemData+".90", event.getInventory().getItem(13));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".90");
			areaYaml.set(areaName+".Mining."+itemData+".90","0:0");
		}
		if(event.getInventory().getItem(22) != null)
			areaYaml.set(areaName+".Mining."+itemData+".50", event.getInventory().getItem(22));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".50");
			areaYaml.set(areaName+".Mining."+itemData+".50","0:0");
		}
		if(event.getInventory().getItem(31) != null)
			areaYaml.set(areaName+".Mining."+itemData+".10", event.getInventory().getItem(31));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".10");
			areaYaml.set(areaName+".Mining."+itemData+".10","0:0");
		}
		if(event.getInventory().getItem(40) != null)
			areaYaml.set(areaName+".Mining."+itemData+".1", event.getInventory().getItem(40));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".1");
			areaYaml.set(areaName+".Mining."+itemData+".1","0:0");
		}
		if(event.getInventory().getItem(49) != null)
			areaYaml.set(areaName+".Mining."+itemData+".0", event.getInventory().getItem(49));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".0");
			areaYaml.set(areaName+".Mining."+itemData+".0","0:0");
		}
		areaYaml.saveConfig();
		return;
	}
}