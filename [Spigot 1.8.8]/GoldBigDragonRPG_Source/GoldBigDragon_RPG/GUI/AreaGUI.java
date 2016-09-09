package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class AreaGUI extends GUIutil
{
	public void AreaListGUI(Player player, short page)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "전체 영역 목록 : " + (page+1));

		Object[] AreaList= AreaConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < AreaList.length;count++)
		{
			String AreaName = AreaList[count].toString();
			
			if(count > AreaList.length || loc >= 45) break;
			String world = AreaConfig.getString(AreaName+".World");
			int MinXLoc = AreaConfig.getInt(AreaName+".X.Min");
			byte MinYLoc = (byte) AreaConfig.getInt(AreaName+".Y.Min");
			int MinZLoc = AreaConfig.getInt(AreaName+".Z.Min");
			int MaxXLoc = AreaConfig.getInt(AreaName+".X.Max");
			byte MaxYLoc = (byte) AreaConfig.getInt(AreaName+".Y.Max");
			int MaxZLoc = AreaConfig.getInt(AreaName+".Z.Max");
			
			byte Priority = (byte) AreaConfig.getInt(AreaName+".Priority");
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
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 영역", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 영역을 생성합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AreaGUI_Main (Player player, String AreaName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "영역 설정");

		if(AreaConfig.getBoolean(AreaName+".BlockUse") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 사용]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 9, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 사용]", 116,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 9, inv);

		if(AreaConfig.getBoolean(AreaName+".BlockPlace") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 설치]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 10, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 설치]", 2,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 10, inv);

		if(AreaConfig.getBoolean(AreaName+".BlockBreak") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 파괴]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 11, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 파괴]", 278,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 11, inv);

		if(AreaConfig.getBoolean(AreaName+".PVP") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[   PVP   ]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 12, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[   PVP   ]", 267,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 12, inv);

		if(AreaConfig.getBoolean(AreaName+".MobSpawn") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[몬스터 스폰]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   거부   ]",""), 13, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[몬스터 스폰]", 52,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   허용   ]",""), 13, inv);

		if(AreaConfig.getBoolean(AreaName+".Alert") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[입장 메시지]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   없음   ]",""), 14, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[입장 메시지]", 340,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   전송   ]",""), 14, inv);

		if(AreaConfig.getBoolean(AreaName+".SpawnPoint") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[리스폰 장소]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   불가   ]",""), 15, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[리스폰 장소]", 397,3,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   가능   ]",""), 15, inv);

		if(AreaConfig.getBoolean(AreaName+".Music") == false)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[배경음 재생]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   중지   ]",""), 16, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[배경음 재생]", 84,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   재생   ]",""), 16, inv);

		if(AreaConfig.getInt(AreaName+".RegenBlock") == 0)
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 리젠]", 166,0,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[   중지   ]",""), 28, inv);
		else
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[블록 리젠]", 103,0,1,Arrays.asList("",ChatColor.GREEN + ""+ChatColor.BOLD+ "[   활성   ]","",ChatColor.DARK_AQUA+""+AreaConfig.getInt(AreaName+".RegenBlock")+" 초 마다 리젠","",ChatColor.RED+"[플레이어가 직접 캔 블록만 리젠 됩니다]",""), 28, inv);

		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[특산품 설정]", 15,0,1,Arrays.asList("",ChatColor.GRAY + "현재 영역에서 블록을 캐면",ChatColor.GRAY+"지정된 아이템이 나오게",ChatColor.GRAY+"설정 합니다.","",ChatColor.YELLOW + "[클릭시 특산품 설정]"), 19, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[낚시 아이템]", 346,0,1,Arrays.asList("",ChatColor.GRAY + "현재 영역에서 낚시를 하여",ChatColor.GRAY+"얻을 수 있는 물건을 확률별로",ChatColor.GRAY+"설정합니다.",ChatColor.YELLOW + "[클릭시 낚시 아이템 설정]"), 20, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[우선순위 변경]", 384,0,1,Arrays.asList("",ChatColor.GRAY+"영역끼리 서로 겹칠 경우",ChatColor.GRAY+"우선 순위가 더 높은 영역이",ChatColor.GRAY+"적용됩니다.",ChatColor.GRAY+"이 속성을 이용하여 마을을 만들고,",ChatColor.GRAY+"마을 내부의 각종 상점 및",ChatColor.GRAY+"구역을 나눌 수 있습니다.",ChatColor.BLUE+"[   현재 우선 순위   ]",ChatColor.WHITE +" "+AreaConfig.getInt(AreaName+".Priority"),"",ChatColor.YELLOW + "[클릭시 우선 순위 변경]"), 21, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[몬스터 설정]", 383,0,1,Arrays.asList("",ChatColor.GRAY + "현재 영역에서 자연적으로",ChatColor.GRAY+"스폰되는 몬스터 대신에",ChatColor.GRAY+"커스텀 몬스터로 변경합니다.","",ChatColor.YELLOW + "[클릭시 커스텀 몬스터 설정]"), 22, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[몬스터 스폰 설정]", 52,0,1,Arrays.asList("",ChatColor.GRAY + "현재 영역의 특정 구역에",ChatColor.GRAY+"특정 시각마다 몬스터를",ChatColor.GRAY+"스폰 합니다.","",ChatColor.YELLOW + "[클릭시 몬스터 스폰 설정]",ChatColor.RED+"[몬스터 스폰 설정시 비활성]"), 31, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[메시지 변경]", 386,0,1,Arrays.asList("",ChatColor.GRAY + "영역 입장 메시지를 변경합니다.","",ChatColor.YELLOW + "[클릭시 입장 메시지 설정]"), 23, inv);
		Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[중심지 변경]", 138,0,1,Arrays.asList("",ChatColor.GRAY + "마을 귀환, 최근 방문 위치에서",ChatColor.GRAY+"리스폰 등의 현재 영역으로",ChatColor.GRAY+"텔레포트 되는 이벤트가 발생할 경우",ChatColor.GRAY+"현재 위치가 중심점이 됩니다.","",ChatColor.DARK_AQUA+"[  현재 중심지  ]",ChatColor.DARK_AQUA+""+AreaConfig.getString(AreaName+".World")+" : "+AreaConfig.getInt(AreaName+".SpawnLocation.X")+","+AreaConfig.getInt(AreaName+".SpawnLocation.Y")+","+AreaConfig.getInt(AreaName+".SpawnLocation.Z"),"",ChatColor.YELLOW+ "[클릭시 현재 위치로 변경]"), 24, inv);
		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
		{
			String lore = "";
			short tracknumber = (short) AreaConfig.getInt(AreaName+".BGM");
			lore = " %enter%"+ChatColor.GRAY + "영역 입장시 테마 음을%enter%"+ChatColor.GRAY+"재생 시킬 수 있습니다.%enter% %enter%"+ChatColor.BLUE + "[클릭시 노트블록 사운드 설정]%enter% %enter%"+ChatColor.DARK_AQUA+"[트랙] "+ChatColor.BLUE +""+ tracknumber+"%enter%"
			+ChatColor.DARK_AQUA+"[제목] "+ChatColor.BLUE +""+ new OtherPlugins.NoteBlockAPIMain().getTitle(tracknumber)+"%enter%"
			+ChatColor.DARK_AQUA+"[저자] "+ChatColor.BLUE+new OtherPlugins.NoteBlockAPIMain().getAuthor(tracknumber)+"%enter%"+ChatColor.DARK_AQUA+"[설명] ";
			
			String Description = new OtherPlugins.NoteBlockAPIMain().getDescription(AreaConfig.getInt(AreaName+".BGM"));
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
		}
		else
		{
			Stack2(ChatColor.RED + ""+ChatColor.BOLD+"[영역 배경음]", 2266,0,1,Arrays.asList("",ChatColor.GRAY + "영역 입장시 테마 음을",ChatColor.GRAY+"재생 시킬 수 있습니다.","",ChatColor.RED + "[     필요 플러그인     ]",ChatColor.RED+" - NoteBlockAPI"), 25, inv);
		}
		
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "영역 이동", 368,0,1,Arrays.asList(ChatColor.GRAY + "현재 영역으로 빠르게 이동합니다."), 40, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "영역 목록으로 돌아갑니다."), 36, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "현재 창을 닫습니다.",ChatColor.BLACK+AreaName), 44, inv);
		
		player.openInventory(inv);
		return;
	}
	
	public void AreaMonsterSpawnSettingGUI(Player player, short page, String AreaName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "영역 몬스터 스폰 룰 : " + (page+1));
		if(AreaConfig.contains(AreaName+".MonsterSpawnRule")==false)
		{
			AreaConfig.set(AreaName+".MonsterSpawnRule.1", null);
			AreaConfig.saveConfig();
		}
		Object[] RuleList= AreaConfig.getConfigurationSection(AreaName+".MonsterSpawnRule").getKeys(false).toArray();
		byte loc=0;
		for(int count = page*45; count <RuleList.length ;count++)
		{
			if(count > RuleList.length || loc >= 45) break;
			String RuleNumber = RuleList[count].toString();
			if(AreaConfig.contains(AreaName+".MonsterSpawnRule."+RuleNumber+".Monster"))
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + (RuleNumber), 383,0,1,Arrays.asList(
						ChatColor.GOLD+"[     스폰 옵션     ]",ChatColor.RED+"-영역에 유저가 있을 때만 작동 -",ChatColor.GOLD+"월드 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.world"),
						ChatColor.GOLD+"좌표 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.x")+","+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.y")+","+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.z"),
						ChatColor.GOLD+"인식 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".range")+"블록",
						ChatColor.GOLD+"시간 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".timer")+"초마다 "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".count")+"마리 스폰",
						ChatColor.GOLD+"최대 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".max")+"마리",
						ChatColor.GOLD+"스폰 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".Monster")
						,"",ChatColor.RED+"[Shift + 우클릭시 룰 삭제]"), loc, inv);
			else
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + (RuleNumber), 52,0,1,Arrays.asList(
					ChatColor.GOLD+"[     스폰 옵션     ]",ChatColor.RED+"-영역에 유저가 있을 때만 작동 -",ChatColor.GOLD+"월드 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.world"),
					ChatColor.GOLD+"좌표 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.x")+","+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.y")+","+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".loc.z"),
					ChatColor.GOLD+"인식 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".range")+"블록",
					ChatColor.GOLD+"시간 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".timer")+"초마다 "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".count")+"마리 스폰",
					ChatColor.GOLD+"최대 : "+AreaConfig.getString(AreaName+".MonsterSpawnRule."+RuleNumber+".max")+"마리"
					,"",ChatColor.RED+"[Shift + 우클릭시 룰 삭제]"), loc, inv);
			loc++;
		}

		if(RuleList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 규칙 추가", 52,0,1,Arrays.asList(ChatColor.GRAY + "새 스폰 규칙을 추가합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AreaMonsterSettingGUI(Player player, short page, String AreaName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		YamlManager MonsterConfig =YC.getNewConfig("Monster/MonsterList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "영역 대체 몬스터 : " + (page+1));

		Object[] MonsterNameList= AreaConfig.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray();
		
		byte loc=0;
		short MobNameListLength = (short) MonsterNameList.length;
		for(int count = page*45; count <MobNameListLength ;count++)
		{
			String MonsterName = MonsterNameList[count].toString();
			if(MonsterConfig.contains(MonsterName) == true)
			{
				String Name = MonsterConfig.getString(MonsterName+".Name");
				if(count > MobNameListLength || loc >= 45) break;
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + MonsterName, 383,0,1,Arrays.asList(
						ChatColor.WHITE+"이름 : " + Name,ChatColor.WHITE+"타입 : " + MonsterConfig.getString(MonsterName+".Type"),
						ChatColor.WHITE+"생명력 : " + MonsterConfig.getInt(MonsterName+".HP"),ChatColor.WHITE+"경험치 : " + MonsterConfig.getInt(MonsterName+".EXP"),
						ChatColor.WHITE+"골드 : " + MonsterConfig.getInt(MonsterName+".MIN_Money")+" ~ " +MonsterConfig.getInt(MonsterName+".MAX_Money"),
						"",ChatColor.RED+"[Shift + 우클릭시 등록 해제]"), loc, inv);
				loc++;
			}
			else
			{
				AreaConfig.removeKey(AreaName+".Monster."+MonsterName);
				AreaConfig.saveConfig();
			}
		}
		
		if(MobNameListLength-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "몬스터 추가", 52,0,1,Arrays.asList(ChatColor.GRAY + "새 커스텀 몬스터를 추가합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AreaFishSettingGUI(Player player, String AreaName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "영역 추가 어류");
		
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[     54%     ]", 160,5,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 54% 확률로 낚일 아이템을 올리세요."), 0, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[     30%     ]", 160,4,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 30% 확률로 낚일 아이템을 올리세요."), 9, inv);
		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[     10%     ]", 160,1,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 10% 확률로 낚일 아이템을 올리세요."), 18, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[      5%      ]", 160,14,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 5% 확률로 낚일 아이템을 올리세요."), 27, inv);
		Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[      1%      ]", 160,10,1,Arrays.asList(ChatColor.GRAY + "이 줄에는 1% 확률로 낚일 아이템을 올리세요."), 36, inv);

		Object[] FishingItemList = AreaConfig.getConfigurationSection(AreaName+".Fishing.54").getKeys(false).toArray();
		for(short count = 0; count < FishingItemList.length; count++)
			ItemStackStack(AreaConfig.getItemStack(AreaName+".Fishing.54."+FishingItemList[count]), count+1, inv);
		FishingItemList = AreaConfig.getConfigurationSection(AreaName+".Fishing.30").getKeys(false).toArray();
		for(short count = 0; count < FishingItemList.length; count++)
			ItemStackStack(AreaConfig.getItemStack(AreaName+".Fishing.30."+FishingItemList[count]), count+10, inv);
		FishingItemList = AreaConfig.getConfigurationSection(AreaName+".Fishing.10").getKeys(false).toArray();
		for(short count = 0; count < FishingItemList.length; count++)
			ItemStackStack(AreaConfig.getItemStack(AreaName+".Fishing.10."+FishingItemList[count]), count+19, inv);
		FishingItemList = AreaConfig.getConfigurationSection(AreaName+".Fishing.5").getKeys(false).toArray();
		for(short count = 0; count < FishingItemList.length; count++)
			ItemStackStack(AreaConfig.getItemStack(AreaName+".Fishing.5."+FishingItemList[count]), count+28, inv);
		FishingItemList = AreaConfig.getConfigurationSection(AreaName+".Fishing.1").getKeys(false).toArray();
		for(short count = 0; count < FishingItemList.length; count++)
			ItemStackStack(AreaConfig.getItemStack(AreaName+".Fishing.1."+FishingItemList[count]), count+37, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AreaBlockSettingGUI(Player player, short page, String AreaName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		GoldBigDragon_RPG.Event.Interact I = new GoldBigDragon_RPG.Event.Interact();
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "영역 특산품 : " + (page+1));

		Object[] BlockIdDataList= AreaConfig.getConfigurationSection(AreaName+".Mining").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count <BlockIdDataList.length ;count++)
		{
			short ID = Short.parseShort(BlockIdDataList[count].toString().split(":")[0]);
			byte Data = Byte.parseByte(BlockIdDataList[count].toString().split(":")[1]);

			Stack2(I.SetItemDefaultName(ID, (byte) Data), ID,Data,1,Arrays.asList(
					"",ChatColor.RED+"[Shift + 우클릭시 등록 해제]"), loc, inv);
				loc++;
		}
		
		if(BlockIdDataList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "특산물 추가", 52,0,1,Arrays.asList(ChatColor.GRAY + "새로운 블록을 설정합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AreaBlockItemSettingGUI(Player player,String AreaName,String ItemData)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "해당 블록을 캐면 나올 아이템");

		ItemStack item = AreaConfig.getItemStack(AreaName+".Mining."+ItemData+".100");
		
		ItemStackStack(item, 4, inv);

		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 0, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 1, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 2, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 3, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 5, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 6, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 7, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,11,1,Arrays.asList(ChatColor.GRAY + "[100% 확률로 나올 아이템]"), 8, inv);

		item = AreaConfig.getItemStack(AreaName+".Mining."+ItemData+".90");
		ItemStackStack(item, 13, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 9, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 10, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 11, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 12, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 14, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 15, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 16, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,9,1,Arrays.asList(ChatColor.GRAY + "[90% 확률로 나올 아이템]"), 17, inv);

		item = AreaConfig.getItemStack(AreaName+".Mining."+ItemData+".50");
		ItemStackStack(item, 22, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 18, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 19, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 20, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 21, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 23, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 24, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 25, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,4,1,Arrays.asList(ChatColor.GRAY + "[50% 확률로 나올 아이템]"), 26, inv);

		item = AreaConfig.getItemStack(AreaName+".Mining."+ItemData+".10");
		ItemStackStack(item, 31, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 27, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 28, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 29, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 30, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 32, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 33, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 34, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,1,1,Arrays.asList(ChatColor.GRAY + "[10% 확률로 나올 아이템]"), 35, inv);

		item = AreaConfig.getItemStack(AreaName+".Mining."+ItemData+".1");
		ItemStackStack(item, 40, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 36, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 37, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 38, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 39, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 41, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 42, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 43, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,14,1,Arrays.asList(ChatColor.GRAY + "[1% 확률로 나올 아이템]"), 44, inv);

		item = AreaConfig.getItemStack(AreaName+".Mining."+ItemData+".0");
		ItemStackStack(item, 49, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 46, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 47, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 48, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 50, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 51, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 160,15,1,Arrays.asList(ChatColor.GRAY + "[0.1% 확률로 나올 아이템]"), 52, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+ItemData), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + AreaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AreaAddMonsterListGUI(Player player, short page,String AreaName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "영역 몬스터 선택 : " + (page+1));

		Object[] a= MobList.getKeys().toArray();
		Object[] MonsterNameList= AreaConfig.getConfigurationSection(AreaName+".Monster").getKeys(false).toArray();
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			String MonsterName =a[count].toString();
			boolean isExit = false;
			for(short count2 = 0; count2 < MonsterNameList.length; count2++)
			{
				if(MonsterNameList[count2].toString().compareTo(MonsterName)==0)
				{
					isExit=true;
					break;
				}
			}
			
			if(isExit == false)
			{

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
				Lore = Lore+"%enter%"+ChatColor.YELLOW+""+ChatColor.BOLD+"[좌 클릭시 몬스터 등록]";

				String[] scriptA = Lore.split("%enter%");
				for(byte counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  " "+scriptA[counter];
				short id = 383;
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
		}
		
		if(a.length-(page*44)>45)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+AreaName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AreaSpawnSpecialMonsterListGUI(Player player, short page,String AreaName,String RuleCount)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "영역 특수 몬스터  : " + (page+1));

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
			Lore = Lore+"%enter%"+ChatColor.YELLOW+""+ChatColor.BOLD+"[좌 클릭시 몬스터 등록]";

			String[] scriptA = Lore.split("%enter%");
			for(byte counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			short id = 383;
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

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "취소", 166,0,1,Arrays.asList(ChatColor.GRAY + "지정 몬스터 스폰대신",ChatColor.GRAY+"영역에 등록 된 몬스터를",ChatColor.GRAY+"랜덤하게 스폰 합니다.",ChatColor.BLACK+AreaName,ChatColor.BLACK+""+RuleCount), 49, inv);
		player.openInventory(inv);
	}

	public void AreaMusicSettingGUI(Player player, int page,String AreaName)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "영역 배경음 : " + (page+1));
		byte loc=0;
		byte model = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 11);
		for(int count = page*45; count < new OtherPlugins.NoteBlockAPIMain().Musics.size();count++)
		{
			if(model<11)
				model++;
			else
				model=0;
			String lore = " %enter%"+ChatColor.DARK_AQUA+"[트랙] "+ChatColor.BLUE +""+ count+"%enter%"
			+ChatColor.DARK_AQUA+"[제목] "+ChatColor.BLUE +""+ new OtherPlugins.NoteBlockAPIMain().getTitle(count)+"%enter%"
			+ChatColor.DARK_AQUA+"[저자] "+ChatColor.BLUE+new OtherPlugins.NoteBlockAPIMain().getAuthor(count)+"%enter%"+ChatColor.DARK_AQUA+"[설명] ";
			String Description = new OtherPlugins.NoteBlockAPIMain().getDescription(count);
			String lore2="";
			short a = 0;
			for(short counter = 0; counter <Description.toCharArray().length; counter++)
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
			if(count > new OtherPlugins.NoteBlockAPIMain().Musics.size() || loc >= 45) break;
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 2256+model,0,1,Arrays.asList(lore.split("%enter%")), loc, inv);
			
			loc++;
		}
		
		if(new OtherPlugins.NoteBlockAPIMain().Musics.size()-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+AreaName), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void AreaListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String AreaName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI OGUI = new OPBoxGUI();
			OGUI.OPBoxGUI_Main(player, (byte) 2);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaListGUI(player, (short) (page-1));
			return;
		case 49://새 영역
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Config = YC.getNewConfig("config.yml");
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				GoldBigDragon_RPG.Event.Interact IT = new GoldBigDragon_RPG.Event.Interact();
				player.sendMessage(ChatColor.DARK_AQUA + "[영역] : " + IT.SetItemDefaultName((short) Config.getInt("Server.AreaSettingWand"),(byte)0) +ChatColor.DARK_AQUA+" 아이템으로 구역을 설정을 한 뒤,");
				player.sendMessage(ChatColor.GOLD +""+ChatColor.BOLD+ " /영역 <영역이름> 생성 "+ChatColor.DARK_AQUA+"명령어를 입력해 주세요!");
				s.SP((Player)player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaListGUI(player, (short) (page+1));
			return;
		default :
			if(event.isLeftClick() == true)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AreaGUI_Main(player, AreaName);
			}
			else if(event.isShiftClick() == true && event.isRightClick() == true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
				for(int count = 0; count < ServerOption.AreaList.get(AreaConfig.getString(AreaName+".World")).size(); count++)
					if(ServerOption.AreaList.get(AreaConfig.getString(AreaName+".World")).get(count).getAreaName().compareTo(AreaName)==0)
						ServerOption.AreaList.get(AreaConfig.getString(AreaName+".World")).remove(count);
				AreaConfig.removeKey(AreaName);
				AreaConfig.saveConfig();
				AreaListGUI(player, page);
			}
			return;
		}
	}
	
	public void AreaGUIInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(44).getItemMeta().getLore().get(1));

		event.setCancelled(true);
		
		Player player = (Player) event.getWhoClicked();
		
		if(event.getSlot() == 44)//창닫기
		{
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
		
		if(event.getSlot() == 36)//이전 목록
		{
			AreaListGUI(player,(short) 0);
			return;
		}
		
		switch (event.getSlot())
		{
		case 9://블록 사용
			if(AreaConfig.getBoolean(AreaName+".BlockUse") == false)
				AreaConfig.set(AreaName+".BlockUse", true);
			else
				AreaConfig.set(AreaName+".BlockUse", false);
			break;
		case 10://블록 설치
			if(AreaConfig.getBoolean(AreaName+".BlockPlace") == false)
				AreaConfig.set(AreaName+".BlockPlace", true);
			else
				AreaConfig.set(AreaName+".BlockPlace", false);
			break;
		case 11://블록 파괴
			if(AreaConfig.getBoolean(AreaName+".BlockBreak") == false)
				AreaConfig.set(AreaName+".BlockBreak", true);
			else
				AreaConfig.set(AreaName+".BlockBreak", false);
			break;
		case 12://PVP
			if(AreaConfig.getBoolean(AreaName+".PVP") == false)
				AreaConfig.set(AreaName+".PVP", true);
			else
				AreaConfig.set(AreaName+".PVP", false);
			break;
		case 13://몬스터 스폰
			if(AreaConfig.getBoolean(AreaName+".MobSpawn") == false)
				AreaConfig.set(AreaName+".MobSpawn", true);
			else
				AreaConfig.set(AreaName+".MobSpawn", false);
			break;
		case 14://입장 메시지
			if(AreaConfig.getBoolean(AreaName+".Alert") == false)
				AreaConfig.set(AreaName+".Alert", true);
			else
				AreaConfig.set(AreaName+".Alert", false);
			break;
		case 15://리스폰 장소
			if(AreaConfig.getBoolean(AreaName+".SpawnPoint") == false)
				AreaConfig.set(AreaName+".SpawnPoint", true);
			else
				AreaConfig.set(AreaName+".SpawnPoint", false);
			break;
		case 16://배경음 재생
			if(AreaConfig.getBoolean(AreaName+".Music") == false)
				AreaConfig.set(AreaName+".Music", true);
			else
				AreaConfig.set(AreaName+".Music", false);
			break;
		case 19://특산품 설정
			AreaBlockSettingGUI(player, (short) 0, AreaName);
			return;
		case 20://낚시 아이템
			AreaFishSettingGUI(player, AreaName);
			return;
		case 21://우선 순위 변경
			{
				Object_UserData u = new Object_UserData();
				player.closeInventory();
				u.setType(player, "Area");
				u.setString(player, (byte)2, "Priority");
				u.setString(player, (byte)3, AreaName);
				player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+AreaName+ChatColor.GREEN+" 영역의 우선 순위를 입력하세요!");
				player.sendMessage(ChatColor.GRAY + "(최소 0 ~ 최대 100)");
			}
			return;
		case 25://영역 배경음 설정
			if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
			{
				OtherPlugins.NoteBlockAPIMain NBAPIM = new OtherPlugins.NoteBlockAPIMain();
				if(NBAPIM.SoundList(player,true))
					AreaMusicSettingGUI(player, 0, AreaName);
			}
			else
				s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.9F);
			return;
		case 22://몬스터 설정
			AreaMonsterSettingGUI(player,(short) 0, AreaName);
			return;
		case 31://몬스터 스폰 룰
			AreaMonsterSpawnSettingGUI(player, (short) 0, AreaName);
			return;
		case 23://메시지 변경
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.sendMessage(ChatColor.GOLD + "/영역 "+AreaName+" 이름 <문자열>" + ChatColor.YELLOW + "\n - "+AreaName+" 구역의 알림 메시지에 보일 이름을 정합니다.");
			player.sendMessage(ChatColor.GOLD + "/영역 "+AreaName+" 설명 <문자열>" + ChatColor.YELLOW + "\n - "+AreaName+" 구역의 알림 메시지에 보일 부가 설명을 정합니다.");
			player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
			player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
			ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
					ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
			ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
					ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			player.closeInventory();
			return;
		case 24://중심지 변경
			AreaConfig.set(AreaName+".World", player.getLocation().getWorld().getName());
			AreaConfig.set(AreaName+".SpawnLocation.X", player.getLocation().getX());
			AreaConfig.set(AreaName+".SpawnLocation.Y", player.getLocation().getY());
			AreaConfig.set(AreaName+".SpawnLocation.Z", player.getLocation().getZ());
			AreaConfig.set(AreaName+".SpawnLocation.Pitch", player.getLocation().getPitch());
			AreaConfig.set(AreaName+".SpawnLocation.Yaw", player.getLocation().getYaw());
			break;
		case 28://블록 리젠
			if(AreaConfig.getInt(AreaName+".RegenBlock") == 0)
			{
				player.closeInventory();
				Object_UserData u = new Object_UserData();
				AreaConfig.set(AreaName+".RegenBlock", 1);
				AreaConfig.saveConfig();
				u.setType(player, "Area");
				u.setString(player, (byte)2, "ARR");
				u.setString(player, (byte)3, AreaName);
				player.sendMessage(ChatColor.GREEN + "[영역] : "+ChatColor.YELLOW+AreaName+ChatColor.GREEN+" 영역의 블록 리젠 속도를 설정하세요!");
				player.sendMessage(ChatColor.GRAY + "(최소 1초 ~ 최대 3600초(1시간))");
			}
			else
			{
				AreaConfig.set(AreaName+".RegenBlock", 0);
				AreaConfig.saveConfig();
				AreaGUI_Main(player, AreaName);
			}
			return;
		case 40://영역 이동
			player.closeInventory();
			player.teleport(new Location(Bukkit.getWorld(AreaConfig.getString(AreaName+".World")),AreaConfig.getInt(AreaName+".SpawnLocation.X"), AreaConfig.getInt(AreaName+".SpawnLocation.Y"),AreaConfig.getInt(AreaName+".SpawnLocation.Z"),AreaConfig.getInt(AreaName+".SpawnLocation.Yaw"),AreaConfig.getInt(AreaName+".SpawnLocation.Pitch")));
			break;
		}
		AreaConfig.saveConfig();
		AreaGUI_Main(player, AreaName);
		return;
	}

	public void AreaMonsterSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		String MonsterName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaGUI_Main(player, AreaName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaMonsterSettingGUI(player, (short) (page-1), AreaName);
			return;
		case 49://몬스터 추가
			YamlManager MonsterConfig =YC.getNewConfig("Monster/MonsterList.yml");
			if(MonsterConfig.getConfigurationSection("").getKeys(false).size() == 0)
			{
				s.SP(player,Sound.ORB_PICKUP,1.0F, 1.8F);
				player.sendMessage(ChatColor.RED + "[영역] : 현재 등록된 커스텀 몬스터가 존재하지 않습니다!");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD + "/몬스터 <이름> 생성 " + ChatColor.YELLOW +"해당 이름을 가진 몬스터를 생성합니다.");
			}
			else
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AreaAddMonsterListGUI(player, page, AreaName);
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaMonsterSettingGUI(player, (short) (page+1),AreaName);
			return;
		default :
			if(event.isShiftClick() == true && event.isRightClick() == true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				AreaConfig.removeKey(AreaName+".Monster."+MonsterName);
				AreaConfig.saveConfig();
				AreaMonsterSettingGUI(player, page,AreaName);
			}
			return;
		}
	}

	public void AreaFishSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();

		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		switch (event.getSlot())
		{
		case 0:
		case 9:
		case 18:
		case 27:
		case 36:
			event.setCancelled(true);
			return;
		case 45://이전 목록
			event.setCancelled(true);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaGUI_Main(player, AreaName);
			return;
		case 53://나가기
			event.setCancelled(true);
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void AreaBlockSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		String BlockName = event.getCurrentItem().getTypeId()+":"+event.getCurrentItem().getData().getData();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaGUI_Main(player, AreaName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaBlockSettingGUI(player, (short) (page-1), AreaName);
			return;
		case 49://특산물 추가
			{
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA + "[영역] : 설정할 블록을 좌클릭 하세요!");

				Object_UserData u = new Object_UserData();
				u.setType(player, "Area");
				u.setString(player, (byte)2, AreaName);
				u.setString(player, (byte)3, "ANBI");
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaBlockSettingGUI(player, (short) (page+1), AreaName);
			return;
		default :
			if(event.isShiftClick()==false&&event.isLeftClick()==true)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AreaBlockItemSettingGUI(player, AreaName, BlockName);
			}
			if(event.isShiftClick() == true && event.isRightClick() == true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
				AreaConfig.removeKey(AreaName+".Mining."+BlockName);
				AreaConfig.saveConfig();
				AreaBlockSettingGUI(player, page, AreaName);
			}
			return;
		}
	}

	public void AreaBlockItemSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();
		
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		if(event.getSlot()!=4&&event.getSlot()!=13&&event.getSlot()!=22
			&&event.getSlot()!=31&&event.getSlot()!=40&&event.getSlot()!=49)
		{
			switch(event.getSlot())
			{
			case 45://이전 목록
				event.setCancelled(true);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AreaBlockSettingGUI(player, (short) 0, AreaName);
				return;
			case 53://나가기
				event.setCancelled(true);
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			default:
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				event.setCancelled(true);
				return;
			}
		}
	}

	public void AreaAddMonsterSpawnRuleGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaGUI_Main(player, AreaName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaMonsterSpawnSettingGUI(player, (short) (page-1), AreaName);
			return;
		case 49://룰 추가
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				long count = new GoldBigDragon_RPG.Util.ETC().getNowUTC();
				AreaConfig.set(AreaName+".MonsterSpawnRule."+count+".range", 1);
				AreaConfig.set(AreaName+".MonsterSpawnRule."+count+".count", 4);
				AreaConfig.set(AreaName+".MonsterSpawnRule."+count+".timer", 10);
				AreaConfig.set(AreaName+".MonsterSpawnRule."+count+".max", 10);
				Object_UserData u = new Object_UserData();
				u.setType(player, "Area");
				u.setString(player, (byte)1, count+"");
				u.setString(player, (byte)2, AreaName);
				u.setString(player, (byte)3, "MLS");
				AreaConfig.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[영역] : 몬스터가 스폰 될 위치를 마우스 우 클릭 하세요!");
				player.closeInventory();
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaMonsterSpawnSettingGUI(player, (short) (page+1), AreaName);
			return;
		default :
			if(event.isRightClick()&&event.isShiftClick())
			{
				s.SP(player, Sound.LAVA_POP, 1.0F, 1.0F);
				AreaConfig.removeKey(AreaName+".MonsterSpawnRule."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				AreaConfig.saveConfig();
				AreaMonsterSpawnSettingGUI(player, (short) page, AreaName);
			}
		}
	}
	
	
	public void AreaAddMonsterListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaMonsterSettingGUI(player, (short) 0, AreaName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaAddMonsterListGUI(player, (short) (page-1), AreaName);
			return;
		case 49:
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaAddMonsterListGUI(player, (short) (page+1), AreaName);
			return;
		default :
			String MobName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			s.SP(player, Sound.WOLF_BARK, 0.8F, 1.0F);
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
			AreaConfig.set(AreaName+".Monster."+MobName+".1", null);
			AreaConfig.saveConfig();
			AreaAddMonsterListGUI(player, page, AreaName);
			return;
		}
	}

	public void AreaSpawnSpecialMonsterListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(3));
		String RuleCounter = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(4));

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 49://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			AreaMonsterSpawnSettingGUI(player, (short) 0, AreaName);
			new GoldBigDragon_RPG.ETC.Area().AreaMonsterSpawnAdd(AreaName, RuleCounter);
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaAddMonsterListGUI(player, (short) (page-1), AreaName);
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaAddMonsterListGUI(player, (short) (page+1), AreaName);
			return;
		default :
			String MobName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			s.SP(player, Sound.ANVIL_LAND, 0.8F, 1.0F);
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
			AreaConfig.set(AreaName+".MonsterSpawnRule."+RuleCounter+".Monster", MobName);
			AreaConfig.saveConfig();
			AreaMonsterSpawnSettingGUI(player, (short) 0, AreaName);
			
			new GoldBigDragon_RPG.ETC.Area().AreaMonsterSpawnAdd(AreaName, RuleCounter);
			return;
		}
	}

	public void AreaMusicSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String AreaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		if(AreaName.compareTo("DeathBGM¡")==0)
		{
			switch (event.getSlot())
			{
			case 45://이전 목록
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Death(player);
				return;
			case 53://나가기
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
			case 48://이전 페이지
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AreaMusicSettingGUI(player, page-1,AreaName);
				return;
			case 50://다음 페이지
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				AreaMusicSettingGUI(player, page+1,AreaName);
				return;
			default :
				if(event.isLeftClick())
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager Config =YC.getNewConfig("config.yml");
					Config.set("Death.Track", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					Config.saveConfig();
					new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Death(player);
				}
				return;
			}
		}
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaGUI_Main(player, AreaName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaMusicSettingGUI(player, page-1,AreaName);
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AreaMusicSettingGUI(player, page+1,AreaName);
			return;
		default :
			if(event.isLeftClick())
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
				AreaConfig.set(AreaName+".BGM", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
				AreaConfig.saveConfig();
				AreaGUI_Main(player, AreaName);
			}
			return;
		}
	}
}
