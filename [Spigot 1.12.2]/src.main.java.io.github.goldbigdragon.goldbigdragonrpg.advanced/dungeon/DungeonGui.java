package dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import admin.OPboxGui;
import battle.BattleCalculator;
import effect.SoundEffect;
import servertick.ServerTickMain;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;



public final class DungeonGui extends UtilGui
{
	/*
	던전 만듬.
	통행증 만듬.
	제단 만듬.
	각 통행증 마다 던전을 연결.
	제단마다 통행증 연결.
	일반 아이템을 넣을 경우의 던전 연결.
	
	고로 제일 먼저 던전 설정.
	다음 제단 설정.
	다음 던전 설정.
	
	고로 던전/제단/통행증은 모두 각기 다름.
	 */
	
	//DungeonGUI//
	public void DungeonListMainGUI(Player player, int page, int Type)
	{
	  	YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/DungeonList.yml");

		String UniqueCode = "§0§0§a§0§0§r";
		Inventory inv = null;
		if(Type==52)
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0던전 목록 : " + (page+1));
		else if(Type==358)
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0통행증 목록 : " + (page+1));
		else if(Type==120)
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0제단 목록 : " + (page+1));
		String[] dungeonList = null;
		if(Type==52)//던전
		{
			dungeonList = dungeonYaml.getKeys().toArray(new String[0]);
			
			int loc=0;
			for(int count = page*45; count < dungeonList.length;count++)
			{
			  	YamlLoader DungeonOptionYML = new YamlLoader();
				DungeonOptionYML.getConfig("Dungeon/Dungeon/"+dungeonList[count]+"/Option.yml");
				
				removeFlagStack("§f§l" + dungeonList[count], 52,0,1,Arrays.asList(
				"","§9던전 형태 : §f"+DungeonOptionYML.getString("Type.Name")
				,"§9던전 크기 : §f"+DungeonOptionYML.getInt("Size")
				,"§9던전 밀집도 : §f"+DungeonOptionYML.getInt("Maze_Level")
				,"§9레벨 제한 : §f"+DungeonOptionYML.getInt("District.Level")+" 이상"
				,"§9누적 레벨 제한 : §f"+DungeonOptionYML.getInt("District.RealLevel")+" 이상"
				,"","§9[기본 클리어 보상]"
				,"§9 - §f"+DungeonOptionYML.getInt("Reward.Money")+" "+main.MainServerOption.money
				,"§9 - §f"+DungeonOptionYML.getInt("Reward.EXP")+"§b§lEXP"
				,"","§e[좌 클릭시 던전 설정]","§c[Shift + 우클릭시 던전 삭제]"), loc, inv);
				
				loc=loc+1;
			}
		}
		else if(Type==358)//통행증
		{
			dungeonYaml.getConfig("Dungeon/EnterCardList.yml");
			dungeonList = dungeonYaml.getKeys().toArray(new String[0]);
			int loc=0;
			String Time = null;
			for(int count = page*45; count < dungeonList.length;count++)
			{
				if(dungeonYaml.getInt(dungeonList[count]+".Hour")!=-1)
				{
					if(dungeonYaml.getInt(dungeonList[count]+".Hour")!=0)
						Time = dungeonYaml.getInt(dungeonList[count]+".Hour")+"시간 ";
					if(dungeonYaml.getInt(dungeonList[count]+".Min")!=0)
						Time = Time+dungeonYaml.getInt(dungeonList[count]+".Min")+"분 ";
					Time = Time+dungeonYaml.getInt(dungeonList[count]+".Sec")+"초";
				}
				else
					Time = "무제한";
				if(dungeonYaml.getString(dungeonList[count]+".Dungeon")==null)
					removeFlagStack("§f§l" + dungeonList[count], dungeonYaml.getInt(dungeonList[count]+".ID"),dungeonYaml.getInt(dungeonList[count]+".DATA"),1,Arrays.asList(
					"","§9연결된 던전 : §f없음",
					"§9입장 가능 인원 : §f"+dungeonYaml.getInt(dungeonList[count]+".Capacity"),
					"§9유효 시간 : §f"+Time,
					"","§e[좌 클릭시 통행증 설정]","§e[Shift + 좌 클릭시 통행증 발급]","§c[Shift + 우클릭시 통행증 삭제]"), loc, inv);
				else
				{
				  	YamlLoader Dungeon = new YamlLoader();
					Dungeon.getConfig("Dungeon/DungeonList.yml");
					if(Dungeon.contains(dungeonYaml.getString(dungeonList[count]+".Dungeon")))
					{
						removeFlagStack("§f§l" + dungeonList[count], dungeonYaml.getInt(dungeonList[count]+".ID"),dungeonYaml.getInt(dungeonList[count]+".DATA"),1,Arrays.asList(
						"","§9연결된 던전 : §f"+dungeonYaml.getString(dungeonList[count]+".Dungeon"),
						"§9입장 가능 인원 : §f"+dungeonYaml.getInt(dungeonList[count]+".Capacity"),
						"§9유효 시간 : §f"+Time,
						"","§e[좌 클릭시 통행증 설정]","§e[Shift + 좌 클릭시 통행증 발급]","§c[Shift + 우클릭시 통행증 삭제]"), loc, inv);
					}
					else
					{
						dungeonYaml.set(dungeonList[count]+".Dungeon",null);
						dungeonYaml.saveConfig();
						removeFlagStack("§f§l" + dungeonList[count], dungeonYaml.getInt(dungeonList[count]+".ID"),dungeonYaml.getInt(dungeonList[count]+".DATA"),1,Arrays.asList(
						"","§9연결된 던전 : §f없음",
						"§9입장 가능 인원 : §f"+dungeonYaml.getInt(dungeonList[count]+".Capacity"),
						"§9유효 시간 : §f"+Time,
						"","§e[좌 클릭시 통행증 설정]","§e[Shift + 좌 클릭시 통행증 발급]","§c[Shift + 우클릭시 통행증 삭제]"), loc, inv);
					}
						
				}
				
				loc=loc+1;
			}
		}
		else if(Type==120)//제단
		{
			dungeonYaml.getConfig("Dungeon/AltarList.yml");
			dungeonList = dungeonYaml.getKeys().toArray(new String[0]);
			int loc=0;
			String AltarCode = null;
			for(int count = page*45; count < dungeonList.length;count++)
			{
				AltarCode = dungeonList[count];
				removeFlagStack("§f"+dungeonYaml.getString(AltarCode+".Name"), dungeonYaml.getInt(AltarCode+".ID"),dungeonYaml.getInt(AltarCode+".DATA"),1,Arrays.asList(
				"","§9[제단 위치]",
				"§f "+dungeonYaml.getString(AltarCode+".World"),
				"§f "+dungeonYaml.getInt(AltarCode+".X")+","+dungeonYaml.getInt(AltarCode+".Y")+","+dungeonYaml.getInt(AltarCode+".Z"),
				"","§e[좌 클릭시 제단 설정]","§e[Shift + 좌 클릭시 제단 이동]","§c[Shift + 우클릭시 제단 철거]","",AltarCode), loc, inv);
				
				loc=loc+1;
			}
		}
		if(Type==52)
			removeFlagStack("§9§l[다음 항목]", 52,0,1,Arrays.asList("§9현재 항목 : §f던전","","§e던전을 생성하기 위해서는","§e아래의 3가지 구성물이 존재 해야 합니다.","§e[던전, 통행증, 제단]"), 47, inv);
		else if(Type==358)
			removeFlagStack("§9§l[다음 항목]", 358,0,1,Arrays.asList("§9현재 항목 : §f통행증","","§e던전을 생성하기 위해서는","§e아래의 3가지 구성물이 존재 해야 합니다.","§e[던전, 통행증, 제단]"), 47, inv);
		else if(Type==120)
			removeFlagStack("§9§l[다음 항목]", 120,0,1,Arrays.asList("§9현재 항목 : §f제단","","§e던전을 생성하기 위해서는","§e아래의 3가지 구성물이 존재 해야 합니다.","§e[던전, 통행증, 제단]"), 47, inv);
		
		
		if(dungeonList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		if(Type==52)
			removeFlagStack("§f§l던전 제작", 383,0,1,Arrays.asList("§7새로운 던전을 생성합니다."), 49, inv);
		if(Type==358)
			removeFlagStack("§f§l통행증 제작", 386,0,1,Arrays.asList("§7새로운 통행증을 생성합니다."), 49, inv);
		if(Type==120)
			removeFlagStack("§f§l제단 건설", 381,0,1,Arrays.asList("§7새로운 제단을 생성합니다.","","§c§l[제단은 무조건 남쪽을 바라봅니다.]"), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void DungeonSetUpGUI(Player player, String DungeonName)
	{
	  	YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");

		String UniqueCode = "§0§0§a§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0던전 설정 : " +DungeonName);
		removeFlagStack("§f§l던전 타입", dungeonYaml.getInt("Type.ID"),dungeonYaml.getInt("Type.DATA"),1,Arrays.asList("§7현재 던전 타입 : "+dungeonYaml.getString("Type.Name")), 11, inv);
		
		removeFlagStack("§f§l던전 크기", 395,0,1,Arrays.asList("§7현재 던전 크기 : "+dungeonYaml.getInt("Size"),"§8최소 : 5","§8최대 : 30"), 13, inv);
		removeFlagStack("§f§l던전 길이", 53,0,1,Arrays.asList("§7현재 미로 레벨 : "+dungeonYaml.getInt("Maze_Level"),"","§e[영향 받는 항목]","§e - 구슬방 출현 빈도","§e - 던전 갈림길 개수","§e - 던전 밀집도"), 15, inv);
		
		removeFlagStack("§f§l던전 제한", 101,0,1,Arrays.asList("§7던전 입장 제한을 설정합니다.","§c레벨 제한 : §8"+dungeonYaml.getInt("District.Level"),"§c누적 레벨 제한 : §8"+ dungeonYaml.getInt("District.RealLevel")), 20, inv);
		removeFlagStack("§f§l던전 보상", 266,0,1,Arrays.asList("§7던전 기본 보상을 설정합니다.","§e보상 금액 : §8"+dungeonYaml.getInt("Reward.Money"),"§b보상 경험치 : §8"+dungeonYaml.getInt("Reward.EXP")), 22, inv);
		removeFlagStack("§f§l던전 보상 상자", 54,0,1,Arrays.asList("§7던전 추가 보상을 설정합니다."), 24, inv);
		removeFlagStack("§f§l던전 몬스터", 383,0,1,Arrays.asList("§7던전 몬스터를 설정합니다."), 29, inv);
		
		String lore = "";
		otherplugins.NoteBlockApiMain NBAPI = new otherplugins.NoteBlockApiMain();
		int tracknumber = dungeonYaml.getInt("BGM.Normal");
		lore = " %enter%§7던전 BGM을 설정합니다.%enter% %enter%§9[클릭시 노트블록 사운드 설정]%enter% %enter%§3[트랙] §9"+ tracknumber+"%enter%"
		+"§3[제목] §9"+ NBAPI.getTitle(tracknumber)+"%enter%"
		+"§3[저자] §9"+NBAPI.getAuthor(tracknumber)+"%enter%§3[설명] ";
		
		String Description = NBAPI.getDescription(tracknumber);
		String lore2="";
		int a = 0;
		for(int count = 0; count <Description.toCharArray().length; count++)
		{
			lore2 = lore2+"§9"+Description.toCharArray()[count];
			a=a+1;
			if(a >= 15)
			{
				a = 0;
				lore2 = lore2+"%enter%      ";
			}
		}
		lore = lore + lore2;
		removeFlagStack("§f§l[던전 배경음]", 2263,0,1,Arrays.asList(lore.split("%enter%")), 31, inv);

		lore = "";
		tracknumber = dungeonYaml.getInt("BGM.BOSS");
		lore = " %enter%§7던전 BGM을 설정합니다.%enter% %enter%§9[클릭시 노트블록 사운드 설정]%enter% %enter%§3[트랙] §9"+ tracknumber+"%enter%"
		+"§3[제목] §9"+ NBAPI.getTitle(tracknumber)+"%enter%"
		+"§3[저자] §9"+NBAPI.getAuthor(tracknumber)+"%enter%§3[설명] ";
		
		Description = NBAPI.getDescription(tracknumber);
		lore2="";
		a = 0;
		for(int count = 0; count <Description.toCharArray().length; count++)
		{
			lore2 = lore2+"§9"+Description.toCharArray()[count];
			a=a+1;
			if(a >= 15)
			{
				a = 0;
				lore2 = lore2+"%enter%      ";
			}
		}
		lore = lore + lore2;
		removeFlagStack("§f§l[보스 배경음]", 2259,0,1,Arrays.asList(lore.split("%enter%")), 33, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 44, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 36, inv);

		player.openInventory(inv);
	}

	public void DungeonChestReward(Player player, String DungeonName)
	{
	  	YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Reward.yml");

		String UniqueCode = "§1§0§a§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0던전 보상 : " +DungeonName);
	
		removeFlagStack("§9§l[100% 확률]", 160,11,1,Arrays.asList("","§f100% 확률로 나올 아이템을","§f이 줄에 놓으시면 됩니다.","§f100% 확률로 이 줄에 있는","§f아이템 중, 하나가 나옵니다.",""), 0, inv);
		removeFlagStack("§a§l[90% 확률]", 160,5,1,Arrays.asList("","§f90% 확률로 나올 아이템을","§f이 줄에 놓으시면 됩니다.","§f90% 확률로 이 줄에 있는","§f아이템 중, 하나가 나옵니다.",""), 9, inv);
		removeFlagStack("§e§l[50% 확률]", 160,4,1,Arrays.asList("","§f50% 확률로 나올 아이템을","§f이 줄에 놓으시면 됩니다.","§f50% 확률로 이 줄에 있는","§f아이템 중, 하나가 나옵니다.",""), 18, inv);
		removeFlagStack("§6§l[10% 확률]", 160,1,1,Arrays.asList("","§f10% 확률로 나올 아이템을","§f이 줄에 놓으시면 됩니다.","§f10% 확률로 이 줄에 있는","§f아이템 중, 하나가 나옵니다.",""), 27, inv);
		removeFlagStack("§4§l[1% 확률]", 160,14,1,Arrays.asList("","§f1% 확률로 나올 아이템을","§f이 줄에 놓으시면 됩니다.","§f1% 확률로 이 줄에 있는","§f아이템 중, 하나가 나옵니다.",""), 36, inv);
		removeFlagStack("§7§l[0.1% 확률]", 160,10,1,Arrays.asList("","§f0.1% 확률로 나올 아이템을","§f이 줄에 놓으시면 됩니다.","§f0.1% 확률로 이 줄에 있는","§f아이템 중, 하나가 나옵니다.",""), 45, inv);

		for(int count = 0; count < 8; count++)
		{
			if(dungeonYaml.getItemStack("100."+count)!=null)
				stackItem(dungeonYaml.getItemStack("100."+count), count+1, inv);
			if(dungeonYaml.getItemStack("90."+count)!=null)
				stackItem(dungeonYaml.getItemStack("90."+count), count+10, inv);
			if(dungeonYaml.getItemStack("50."+count)!=null)
				stackItem(dungeonYaml.getItemStack("50."+count), count+19, inv);
			if(dungeonYaml.getItemStack("10."+count)!=null)
				stackItem(dungeonYaml.getItemStack("10."+count), count+28, inv);
			if(dungeonYaml.getItemStack("1."+count)!=null)
				stackItem(dungeonYaml.getItemStack("1."+count), count+37, inv);
			if(dungeonYaml.getItemStack("0."+count)!=null)
				stackItem(dungeonYaml.getItemStack("0."+count), count+46, inv);
		}
		player.openInventory(inv);
	}
	
	public void DungeonMonsterGUIMain(Player player, String DungeonName)
	{
	  	YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");

		String UniqueCode = "§0§0§a§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0던전 몬스터 : " +DungeonName);

		removeFlagStack("§4§l[BOSS]", 160,14,1,Arrays.asList("","§f보스방에서 나올 몬스터는","§f이 줄에서 설정합니다.",""), 0, inv);
		removeFlagStack("§6§l[부 보스]", 160,5,1,Arrays.asList("","§f보스방 앞에서 나올 몬스터는","§f이 줄에서 설정합니다.",""), 9, inv);
		removeFlagStack("§e§l[상급 몬스터]", 160,4,1,Arrays.asList("","§f일반 방에서 나올 매우 강한 몬스터는","§f이 줄에서 설정합니다.",""), 18, inv);
		removeFlagStack("§a§l[중급 몬스터]", 160,5,1,Arrays.asList("","§f일반 방에서 나올 강한 몬스터는","§f이 줄에서 설정합니다.",""), 27, inv);
		removeFlagStack("§9§l[하급 몬스터]", 160,11,1,Arrays.asList("","§f일반 방에서 나올 일반 몬스터는","§f이 줄에서 설정합니다.",""), 36, inv);

		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);

		String Type = "Boss";
		for(int count2 = 0; count2 < 5; count2 ++)
		{
			switch(count2)
			{
			case 0:
				Type = "Boss";	break;
			case 1:
				Type = "SubBoss";	break;
			case 2:
				Type = "High";	break;
			case 3:
				Type = "Middle";	break;
			case 4:
				Type = "Normal";	break;
			}
			
			for(int count = 0; count < 8; count ++)
			{
				if(dungeonYaml.getString(Type+"."+count)==null)
					removeFlagStack("§f§l[없음]", 383, 0, 1,Arrays.asList("","§e[좌 클릭시 등록]"), count+1+(count2*9), inv);
				else
				{
					switch(dungeonYaml.getString(Type+"."+count))
					{
					case "놂좀비":
						removeFlagStack("§2§l[일반 좀비]", 397, 2, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂스켈레톤":
						removeFlagStack("§7§l[일반 스켈레톤]", 397, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂크리퍼":
						removeFlagStack("§a§l[일반 크리퍼]", 397, 4, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂거미":
						removeFlagStack("§7§l[일반 거미]", 287, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂동굴거미":
						removeFlagStack("§7§l[일반 동굴 거미]", 375, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂엔더맨":
						removeFlagStack("§7§l[일반 엔더맨]", 368, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂슬라임":
						removeFlagStack("§a§l[일반 슬라임]", 341, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂마그마큐브":
						removeFlagStack("§e§l[일반 마그마 큐브]", 378, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂마녀":
						removeFlagStack("§7§l[일반 마녀]", 438, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂좀비피그맨":
						removeFlagStack("§d§l[일반 좀비 피그맨]", 283, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂블레이즈":
						removeFlagStack("§e§l[일반 블레이즈]", 369, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂가스트":
						removeFlagStack("§f§l[일반 가스트]", 370, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂가디언":
						removeFlagStack("§3§l[일반 수호자]", 410, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂박쥐":
						removeFlagStack("§7§l[일반 박쥐]", 1, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂돼지":
						removeFlagStack("§d§l[일반 돼지]", 319, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂양":
						removeFlagStack("§f§l[일반 양]", 423, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂소":
						removeFlagStack("§7§l[일반 소]", 363, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂닭":
						removeFlagStack("§f§l[일반 닭]", 365, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂오징어":
						removeFlagStack("§7§l[일반 오징어]", 351, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂늑대":
						removeFlagStack("§f§l[일반 늑대]", 352, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂버섯소":
						removeFlagStack("§c§l[일반 버섯소]", 40, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂오셀롯":
						removeFlagStack("§e§l[일반 오셀롯]", 349, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂말":
						removeFlagStack("§6§l[일반 말]", 418, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂토끼":
						removeFlagStack("§6§l[일반 토끼]", 411, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂주민":
						removeFlagStack("§6§l[일반 주민]", 388, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂북극곰":
						removeFlagStack("§f§l[일반 북극곰]", 80, 0, 1,Arrays.asList("§7커스텀 몬스터가 아닌","§7일반 적인 몬스터입니다.","","§e[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					default:
						YamlLoader monsterYaml = new YamlLoader();
						monsterYaml.getConfig("Monster/MonsterList.yml");
						String mobName = dungeonYaml.getString(Type+"."+count);
						boolean isExit = false;

						List<String> lore = new ArrayList<String>();
						String[] monsterList = monsterYaml.getKeys().toArray(new String[0]);
						for(int count3=0;count3 < monsterList.length;count3++)
						{
							if(monsterList[count3].equals(mobName))
							{
								lore.clear();
								lore.add("");
								lore.add("§f§l 이름 : §f"+monsterYaml.getString(mobName+".Name"));
								lore.add("§f§l 타입 : §f"+monsterYaml.getString(mobName+".Type"));
								lore.add("§f§l 스폰 바이옴 : §f"+monsterYaml.getString(mobName+".Biome"));
								lore.add("§c§l 생명력 : §f"+monsterYaml.getInt(mobName+".HP"));
								lore.add("§b§l 경험치 : §f"+monsterYaml.getInt(mobName+".EXP"));
								lore.add("§e§l 드랍 금액 : §f"+monsterYaml.getInt(mobName+".MIN_Money")+" ~ "+monsterYaml.getInt(mobName+".MAX_Money"));
								lore.add("§c§l "+main.MainServerOption.statSTR+" : §f"+monsterYaml.getInt(mobName+".STR")+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(mobName+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(mobName+".STR"), false) + "]");
								lore.add("§a§l "+main.MainServerOption.statDEX+" : §f"+monsterYaml.getInt(mobName+".DEX")+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(mobName+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(mobName+".DEX"), 0, false) + "]");
								lore.add("§3§l "+main.MainServerOption.statINT+" : §f"+monsterYaml.getInt(mobName+".INT")+"§7 [폭공 : " + (monsterYaml.getInt(mobName+".INT")/4)+ " ~ "+(int)(monsterYaml.getInt(mobName+".INT")/2.5)+"]");
								lore.add("§7§l "+main.MainServerOption.statWILL+" : §f"+monsterYaml.getInt(mobName+".WILL")+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(mobName+".LUK"), (int)monsterYaml.getInt(mobName+".WILL"),0) + " %]");
								lore.add("§e§l "+main.MainServerOption.statLUK+" : §f"+monsterYaml.getInt(mobName+".LUK")+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(mobName+".LUK"), (int)monsterYaml.getInt(mobName+".WILL"),0) + " %]");
								lore.add("§7§l 방어 : §f"+monsterYaml.getInt(mobName+".DEF"));
								lore.add("§b§l 보호 : §f"+monsterYaml.getInt(mobName+".Protect"));
								lore.add("§9§l 마법 방어 : §f"+monsterYaml.getInt(mobName+".Magic_DEF"));
								lore.add("§1§l 마법 보호 : §f"+monsterYaml.getInt(mobName+".Magic_Protect"));
								lore.add("");
								lore.add("§e§l[좌 클릭시 변경]");
								
								int id = 383;
								int data = 0;
								switch(monsterYaml.getString(mobName+".Type"))
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
								stack("§f"+mobName, id, data, 1, lore, count+1+(count2*9), inv);
								ItemMeta a = inv.getItem(count+1+(count2*9)).getItemMeta();
								a.addEnchant(Enchantment.SILK_TOUCH, 3, true);
								inv.getItem(count+1+(count2*9)).setItemMeta(a);
								isExit = true;
								break;
							}
						}
						if(isExit==false)
						{
							dungeonYaml.set(Type+"."+count, null);
							dungeonYaml.saveConfig();
							removeFlagStack("§f§l[없음]", 383, 0, 1,Arrays.asList("","§e[좌 클릭시 등록]"), count+1+(count2*9), inv);
						}
					}
				}
			}
		}
		player.openInventory(inv);
	}
	
	public void DungeonMonsterChooseMain(Player player, String DungeonName, int slot)
	{
		String UniqueCode = "§0§0§a§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0던전 몬스터 : " +DungeonName);
		removeFlagStack("§c§l[없음]", 166,0,1,Arrays.asList("§7몬스터 설정을 하지 않습니다."), 2, inv);
		removeFlagStack("§f§l[일반]", 383,0,1,Arrays.asList("§7일반적인 몬스터 중 하나로 고릅니다."), 4, inv);
		removeFlagStack("§b§l[커스텀]", 52,0,1,Arrays.asList("§7커스텀 몬스터 중 하나로 고릅니다.","","§c[엔더 크리스탈 형태의 몬스터를","§c선택할 경우, 고장의 원인이 됩니다.]"), 6, inv);
		
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+slot), 8, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);
		player.openInventory(inv);
	}
	
	public void DungeonSelectNormalMonsterChoose(Player player, String DungeonName, String Type, int slot)
	{
		String UniqueCode = "§0§0§a§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0일반 몬스터 : " +DungeonName);

		removeFlagStack("§2§l[좀비]",397,2,1,null, 0, inv);
		removeFlagStack("§7§l[스켈레톤]",397,0,1,null, 1, inv);
		removeFlagStack("§a§l[크리퍼]",397,4,1,null, 2, inv);
		removeFlagStack("§7§l[거미]",287,0,1,null, 3, inv);
		removeFlagStack("§7§l[동굴 거미]",375,0,1,null, 4, inv);
		removeFlagStack("§7§l[엔더맨]",368,0,1,null, 5, inv);
		removeFlagStack("§a§l[슬라임]",341,0,1,null, 6, inv);
		removeFlagStack("§e§l[마그마 큐브]",378,0,1,null, 7, inv);
		removeFlagStack("§7§l[마녀]",438,0,1,null, 8, inv);
		removeFlagStack("§d§l[좀비 피그맨]",283,0,1,null, 9, inv);
		removeFlagStack("§e§l[블레이즈]",369,0,1,null, 10, inv);
		removeFlagStack("§f§l[가스트]",370,0,1,null, 11, inv);
		removeFlagStack("§3§l[수호자]",410,0,1,null, 12, inv);
		removeFlagStack("§7§l[박쥐]",1,0,1,null, 13, inv);
		removeFlagStack("§d§l[돼지]",319,0,1,null, 14, inv);
		removeFlagStack("§f§l[양]",423,0,1,null, 15, inv);
		removeFlagStack("§7§l[소]",363,0,1,null, 16, inv);
		removeFlagStack("§f§l[닭]",365,0,1,null, 17, inv);
		removeFlagStack("§7§l[오징어]",351,0,1,null, 18, inv);
		removeFlagStack("§f§l[늑대]",352,0,1,null, 19, inv);
		removeFlagStack("§f§l[버섯 소]",40,0,1,null, 20, inv);
		removeFlagStack("§f§l[오셀롯]",349,0,1,null, 21, inv);
		removeFlagStack("§f§l[말]",418,0,1,null, 22, inv);
		removeFlagStack("§f§l[토끼]",411,0,1,null, 23, inv);
		removeFlagStack("§f§l[주민]",388,0,1,null, 24, inv);
		removeFlagStack("§f§l[북극곰]",80,0,1,null, 25, inv);

		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+slot), 53, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+Type), 45, inv);
		player.openInventory(inv);
	}
	
	public void DungeonSelectCustomMonsterChoose(Player player, String DungeonName, String Type, int slot, int page)
	{
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		String UniqueCode = "§0§0§a§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0커스텀 몬스터 : " + (page+1));

		String[] monsterList= monsterYaml.getKeys().toArray(new String[0]);

		int loc=0;
		List<String> lore = new ArrayList<String>();
		for(int count = page*45; count < monsterList.length;count++)
		{
			if(count > monsterList.length || loc >= 45) break;
			lore.clear();
			lore.add("");
			lore.add("§f§l 이름 : §f"+monsterYaml.getString(monsterList[count]+".Name"));
			lore.add("§f§l 타입 : §f"+monsterYaml.getString(monsterList[count]+".Type"));
			lore.add("§f§l 스폰 바이옴 : §f"+monsterYaml.getString(monsterList[count]+".Biome"));
			lore.add("§c§l 생명력 : §f"+monsterYaml.getInt(monsterList[count]+".HP"));
			lore.add("§b§l 경험치 : §f"+monsterYaml.getInt(monsterList[count]+".EXP"));
			lore.add("§e§l 드랍 금액 : §f"+monsterYaml.getInt(monsterList[count]+".MIN_Money")+" ~ "+monsterYaml.getInt(monsterList[count]+".MAX_Money"));
			lore.add("§c§l "+main.MainServerOption.statSTR+" : §f"+monsterYaml.getInt(monsterList[count]+".STR")+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterList[count]+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterList[count]+".STR"), false) + "]");
			lore.add("§a§l "+main.MainServerOption.statDEX+" : §f"+monsterYaml.getInt(monsterList[count]+".DEX")+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterList[count]+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterList[count]+".DEX"), 0, false) + "]");
			lore.add("§3§l "+main.MainServerOption.statINT+" : §f"+monsterYaml.getInt(monsterList[count]+".INT")+"§7 [폭공 : " + (monsterYaml.getInt(monsterList[count]+".INT")/4)+ " ~ "+(int)(monsterYaml.getInt(monsterList[count]+".INT")/2.5)+"]");
			lore.add("§7§l "+main.MainServerOption.statWILL+" : §f"+monsterYaml.getInt(monsterList[count]+".WILL")+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(monsterList[count]+".LUK"), (int)monsterYaml.getInt(monsterList[count]+".WILL"),0) + " %]");
			lore.add("§e§l "+main.MainServerOption.statLUK+" : §f"+monsterYaml.getInt(monsterList[count]+".LUK")+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(monsterList[count]+".LUK"), (int)monsterYaml.getInt(monsterList[count]+".WILL"),0) + " %]");
			lore.add("§7§l 방어 : §f"+monsterYaml.getInt(monsterList[count]+".DEF"));
			lore.add("§b§l 보호 : §f"+monsterYaml.getInt(monsterList[count]+".Protect"));
			lore.add("§9§l 마법 방어 : §f"+monsterYaml.getInt(monsterList[count]+".Magic_DEF"));
			lore.add("§1§l 마법 보호 : §f"+monsterYaml.getInt(monsterList[count]+".Magic_Protect"));

			int id = 383;
			int data = 0;
			switch(monsterYaml.getString(monsterList[count]+".Type"))
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
			
			stack("§f"+monsterList[count], id, data, 1,lore, loc, inv);
			loc=loc+1;
		}
		
		if(monsterList.length-(page*44)>45)
		stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+Type), 45, inv);
		stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+slot,"§0"+DungeonName), 53, inv);
		player.openInventory(inv);
	}

	public void DungeonMusicSettingGUI(Player player, int page,String DungeonName, boolean isBOSS)
	{
		String UniqueCode = "§0§0§a§0§7§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0던전 배경음 : " + (page+1));
		int loc=0;
		int model = new util.UtilNumber().RandomNum(0, 11);
		for(int count = page*45; count < otherplugins.NoteBlockApiMain.Musics.size();count++)
		{
			if(model<11)
				model=model+1;
			else
				model=0;
			otherplugins.NoteBlockApiMain NBAPI = new otherplugins.NoteBlockApiMain();
			String lore = " %enter%§3[트랙] §9"+ count+"%enter%"
			+"§3[제목] §9"+ NBAPI.getTitle(count)+"%enter%"
			+"§3[저자] §9"+NBAPI.getAuthor(count)+"%enter%§3[설명] ";
			String Description = NBAPI.getDescription(count);
			String lore2="";
			int a = 0;
			for(int counter = 0; counter <Description.toCharArray().length; counter++)
			{
				lore2 = lore2+"§9"+Description.toCharArray()[counter];
				a=a+1;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2+"%enter% %enter%§e[좌 클릭시 배경음 설정]";
			if(count > otherplugins.NoteBlockApiMain.Musics.size() || loc >= 45) break;
			removeFlagStack("§f§l" + count, 2256+model,0,1,Arrays.asList(lore.split("%enter%")), loc, inv);
			
			loc=loc+1;
		}
		
		if(otherplugins.NoteBlockApiMain.Musics.size()-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+isBOSS), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+DungeonName), 53, inv);
		player.openInventory(inv);
	}
	//DungeonGUI//
	
	
	//EnterCardGUI//
	public void EnterCardSetUpGUI(Player player, String EnterCardName)
	{
	  	YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/EnterCardList.yml");

		String UniqueCode = "§0§0§a§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0통행증 설정");
		if(dungeonYaml.getString(EnterCardName+".Dungeon")!= null)
			removeFlagStack("§f§l[통행증 연결 던전]", 52,0,1,Arrays.asList("","§9현재 이어진 던전 : §f"+dungeonYaml.getString(EnterCardName+".Dungeon")), 2, inv);
		else
			removeFlagStack("§f§l[통행증 연결 던전]", 166,0,1,Arrays.asList("","§9현재 이어진 던전 : §f없음"), 2, inv);
			
		removeFlagStack("§f§l[통행증 형태 변경]", dungeonYaml.getInt(EnterCardName+".ID"),dungeonYaml.getInt(EnterCardName+".DATA"),1,Arrays.asList("","§9현재 아이템 타입 : §f"+dungeonYaml.getInt(EnterCardName+".ID")+":"+ dungeonYaml.getInt(EnterCardName+".DATA"),"","§e[F3 + H 입력시 타입 확인 가능]"), 3, inv);
		removeFlagStack("§f§l[통행증 형태 초기화]", 325,0,1,Arrays.asList("","§f통행증 형태가 나타나지 않을 때","§f누르면 초기화 해 줍니다."), 4, inv);
		removeFlagStack("§f§l[통행증 입장 인원]", 397,3,1,Arrays.asList("","§9입장 가능 인원 : §f"+dungeonYaml.getInt(EnterCardName+".Capacity")+" 명"), 5, inv);

		String Time = null;
		if(dungeonYaml.getInt(EnterCardName.toString()+".Hour")!=-1)
		{
			if(dungeonYaml.getInt(EnterCardName.toString()+".Hour")!=0)
				Time = dungeonYaml.getInt(EnterCardName.toString()+".Hour")+"시간 ";
			if(dungeonYaml.getInt(EnterCardName.toString()+".Min")!=0)
				Time = Time+dungeonYaml.getInt(EnterCardName.toString()+".Min")+"분 ";
			Time = Time+dungeonYaml.getInt(EnterCardName.toString()+".Sec")+"초";
		}
		else
			Time = "무제한";
		removeFlagStack("§f§l[통행증 유효 시간]", 347,0,1,Arrays.asList("","§f"+Time), 6, inv);

		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+EnterCardName), 8, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);

		player.openInventory(inv);
	}

	public void EnterCardDungeonSettingGUI(Player player, int page, String EnterCardName)
	{
	  	YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/DungeonList.yml");

		String UniqueCode = "§0§0§a§0§9§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0통행증 연결 : " + (page+1));
		String[] DungeonList = dungeonYaml.getKeys().toArray(new String[0]);
		
		int loc=0;
		for(int count = page*45; count < DungeonList.length;count++)
		{
			YamlLoader DungeonOptionYML = new YamlLoader();
			DungeonOptionYML.getConfig("Dungeon/Dungeon/"+DungeonList[count]+"/Option.yml");
			
			removeFlagStack("§f§l" + DungeonList[count], 52,0,1,Arrays.asList(
			"","§9던전 형태 : §f"+DungeonOptionYML.getString("Type.Name")
			,"§9던전 크기 : §f"+DungeonOptionYML.getInt("Size")
			,"§9던전 밀집도 : §f"+DungeonOptionYML.getInt("Maze_Level")
			,"§9레벨 제한 : §f"+DungeonOptionYML.getInt("District.Level")+" 이상"
			,"§9누적 레벨 제한 : §f"+DungeonOptionYML.getInt("District.RealLevel")+" 이상"
			,"","§9[기본 클리어 보상]"
			,"§9 - §f"+DungeonOptionYML.getInt("Reward.Money")+" "+main.MainServerOption.money
			,"§9 - §f"+DungeonOptionYML.getInt("Reward.EXP")+"§b§lEXP"
			,"","§e[좌 클릭시 연결]"), loc, inv);
			
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+EnterCardName), 53, inv);
		player.openInventory(inv);
		return;
	}
	//EnterCardGUI//

	//AltarGUI//
	public void AltarShapeListGUI(Player player)
	{
		String UniqueCode = "§0§0§a§0§a§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0제단 형태");
		removeFlagStack("§2§l[이끼 낀 제단]", 48,0,1,Arrays.asList("","§9크기 : §f소형","§9예상 건축 시간 : §f13초","","§6§l[     건축가     ]","§fGoldBigDragon [모두]"), 0, inv);
		removeFlagStack("§e§l[金泰龍]", 41,0,1,Arrays.asList("","§9크기 : §f대형","§9예상 건축 시간 : §f15분","","§6§l[     건축가     ]","§fComputerFairy [날개]","§fGoldBigDragon [용]"), 1, inv);
		removeFlagStack("§7§l[스톤 헨지]", 1,0,1,Arrays.asList("","§9크기 : §f소형","§9예상 건축 시간 : §f1분 5초","","§6§l[     건축가     ]","§fGoldBigDragon [모두]"), 2, inv);
		removeFlagStack("§7§l[해부대]", 1,5,1,Arrays.asList("","§9크기 : §f소형","§9예상 건축 시간 : §f8초","","§6§l[     건축가     ]","§fGoldBigDragon [모두]"), 3, inv);
		removeFlagStack("§7§l[테스트용 제단]", 48,0,1,null, 44, inv);
		
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}

	public void AltarSettingGUI(Player player, String AltarName)
	{
		String UniqueCode = "§0§0§a§0§b§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0제단 설정");

	  	YamlLoader AltarList = new YamlLoader();
		AltarList.getConfig("Dungeon/AltarList.yml");
	  	YamlLoader AltarConfig = new YamlLoader();
		AltarConfig.getConfig("Dungeon/Altar/"+AltarName+".yml");
		removeFlagStack("§f§l[이름 변경]", 421,0,1,Arrays.asList("§7제단의 이름을 변경합니다.","","§9[현재 제단 이름]","§f"+AltarList.getString(AltarName+".Name")), 2, inv);
		if(AltarConfig.getString("NormalDungeon")==null)
			removeFlagStack("§f§l[일반 던전]", 166,0,1,Arrays.asList("§7이 제단에 통행증을 제외한","§7아이템을 바쳤을 경우 생성되는","§7일반 던전을 설정합니다.","","§9[현재 설정된 일반 던전]","§f설정되지 않음"), 4, inv);
		else
		{
		  	YamlLoader DungeonList = new YamlLoader();
			DungeonList.getConfig("Dungeon/DungeonList.yml");
			if(DungeonList.contains(AltarConfig.getString("NormalDungeon")))
				removeFlagStack("§f§l[일반 던전]", 52,0,1,Arrays.asList("§7제단에 통행증을 제외한","§7아이템을 바쳤을 경우 생성되는","§7일반 던전을 설정합니다.","","§9[현재 설정된 일반 던전]","§f"+AltarConfig.getString("NormalDungeon")), 4, inv);
			else
			{
				AltarConfig.set("NormalDungeon", null);
				AltarConfig.saveConfig();
				removeFlagStack("§f§l[일반 던전]", 166,0,1,Arrays.asList("§7제단에 통행증을 제외한","§7아이템을 바쳤을 경우 생성되는","§7일반 던전을 설정합니다.","","§9[현재 설정된 일반 던전]","§f설정되지 않음"), 4, inv);
			}
		}
		removeFlagStack("§f§l[통행증 등록]", 358,0,1,Arrays.asList("§7제단에서 사용 가능한","§7통행증을 등록합니다.","","§e[좌 클릭시 통행증 등록]"), 6, inv);

		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+AltarName), 8, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);

		player.openInventory(inv);
		return;
	}
	
	public void AltarDungeonSettingGUI(Player player, int page, String AltarName)
	{
	  	YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/DungeonList.yml");

		String UniqueCode = "§0§0§a§0§c§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0제단 연결 : " + (page+1));
		String[] DungeonList = dungeonYaml.getKeys().toArray(new String[0]);
		
		int loc=0;
		YamlLoader DungeonOptionYML = new YamlLoader();
		for(int count = page*45; count < DungeonList.length;count++)
		{
			DungeonOptionYML.getConfig("Dungeon/Dungeon/"+DungeonList[count]+"/Option.yml");
			
			removeFlagStack("§f§l" + DungeonList[count], 52,0,1,Arrays.asList(
			"","§9던전 형태 : §f"+DungeonOptionYML.getString("Type.Name")
			,"§9던전 크기 : §f"+DungeonOptionYML.getInt("Size")
			,"§9던전 밀집도 : §f"+DungeonOptionYML.getInt("Maze_Level")
			,"§9레벨 제한 : §f"+DungeonOptionYML.getInt("District.Level")+" 이상"
			,"§9누적 레벨 제한 : §f"+DungeonOptionYML.getInt("District.RealLevel")+" 이상"
			,"","§9[기본 클리어 보상]"
			,"§9 - §f"+DungeonOptionYML.getInt("Reward.Money")+" "+main.MainServerOption.money
			,"§9 - §f"+DungeonOptionYML.getInt("Reward.EXP")+"§b§lEXP"
			,"","§e[좌 클릭시 연결]"), loc, inv);
			
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarEnterCardSettingGUI(Player player, int page, String AltarName)
	{
	  	YamlLoader AlterConfig = new YamlLoader();
		AlterConfig.getConfig("Dungeon/Altar/"+AltarName+".yml");

		String UniqueCode = "§0§0§a§0§d§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0등록된 통행증 : " + (page+1));

		if(AlterConfig.getConfigurationSection("EnterCard").getKeys(false).size()!=0)
		{
			String[] EnterCardList = AlterConfig.getConfigurationSection("EnterCard").getKeys(false).toArray(new String[0]);

			int loc=0;
		  	YamlLoader EnterCard = new YamlLoader();
		  	YamlLoader Dungeon = new YamlLoader();
			for(int count = page*45; count < EnterCardList.length;count++)
			{
				EnterCard.getConfig("Dungeon/EnterCardList.yml");
				if(EnterCard.contains(EnterCardList[count]))
				{
					if(EnterCard.getString(EnterCardList[count]+".Dungeon")==null)
						removeFlagStack("§f§l" + EnterCardList[count], EnterCard.getInt(EnterCardList[count]+".ID"),EnterCard.getInt(EnterCardList[count]+".DATA"),1,Arrays.asList(
								"","§9연결된 던전 : §f없음",
								"§9입장 가능 인원 : §f"+EnterCard.getInt(EnterCardList[count]+".Capacity"),
								"","§c[Shift + 우클릭시 등록 해제]"), loc, inv);
					else
					{
						Dungeon.getConfig("Dungeon/DungeonList.yml");
						if(Dungeon.contains(EnterCard.getString(EnterCardList[count]+".Dungeon")))
						{
							removeFlagStack("§f§l" + EnterCardList[count], EnterCard.getInt(EnterCardList[count]+".ID"),EnterCard.getInt(EnterCardList[count]+".DATA"),1,Arrays.asList(
							"","§9연결된 던전 : §f"+EnterCard.getString(EnterCardList[count]+".Dungeon"),
							"§9입장 가능 인원 : §f"+EnterCard.getInt(EnterCardList[count]+".Capacity"),
							"","§c[Shift + 우클릭시 등록 해제]"), loc, inv);
						}
						else
						{
							EnterCard.set(EnterCardList[count]+".Dungeon",null);
							EnterCard.saveConfig();
							removeFlagStack("§f§l" + EnterCardList[count], EnterCard.getInt(EnterCardList[count]+".ID"),EnterCard.getInt(EnterCardList[count]+".DATA"),1,Arrays.asList(
									"","§9연결된 던전 : §f없음",
									"§9입장 가능 인원 : §f"+EnterCard.getInt(EnterCardList[count]+".Capacity"),
									"","§c[Shift + 우클릭시 등록 해제]"), loc, inv);
						}
							
					}
					loc=loc+1;
				}
				else
				{
					AlterConfig.removeKey("EnterCard."+EnterCardList[count]);
					AlterConfig.saveConfig();
				}
			}
			if(EnterCardList.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		}
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l통행증 등록", 386,0,1,Arrays.asList("§7제단에 통행증을 등록합니다."), 49, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarEnterCardListGUI(Player player, int page, String AltarName)
	{
	  	YamlLoader dungeonYaml = new YamlLoader();

		String UniqueCode = "§0§0§a§0§e§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0생성된 통행증 목록 : " + (page+1));
		
		dungeonYaml.getConfig("Dungeon/EnterCardList.yml");
		String[] DungeonList = dungeonYaml.getKeys().toArray(new String[0]);
		int loc=0;
	  	YamlLoader Dungeon = new YamlLoader();
		for(int count = page*45; count < DungeonList.length;count++)
		{
			if(dungeonYaml.getString(DungeonList[count]+".Dungeon")==null)
				removeFlagStack("§f§l" + DungeonList[count], dungeonYaml.getInt(DungeonList[count]+".ID"),dungeonYaml.getInt(DungeonList[count]+".DATA"),1,Arrays.asList(
				"","§9연결된 던전 : §f없음",
				"§9입장 가능 인원 : §f"+dungeonYaml.getInt(DungeonList[count]+".Capacity"),
				"","§e[좌 클릭시 통행증 등록]"), loc, inv);
			else
			{
				Dungeon.getConfig("Dungeon/DungeonList.yml");
				if(Dungeon.contains(dungeonYaml.getString(DungeonList[count]+".Dungeon")))
				{
					removeFlagStack("§f§l" + DungeonList[count], dungeonYaml.getInt(DungeonList[count]+".ID"),dungeonYaml.getInt(DungeonList[count]+".DATA"),1,Arrays.asList(
					"","§9연결된 던전 : §f"+dungeonYaml.getString(DungeonList[count]+".Dungeon"),
					"§9입장 가능 인원 : §f"+dungeonYaml.getInt(DungeonList[count]+".Capacity"),
					"","§e[좌 클릭시 통행증 등록]"), loc, inv);
				}
				else
				{
					dungeonYaml.set(DungeonList[count]+".Dungeon",null);
					dungeonYaml.saveConfig();
					removeFlagStack("§f§l" + DungeonList[count], dungeonYaml.getInt(DungeonList[count]+".ID"),dungeonYaml.getInt(DungeonList[count]+".DATA"),1,Arrays.asList(
					"","§9연결된 던전 : §f없음",
					"§9입장 가능 인원 : §f"+dungeonYaml.getInt(DungeonList[count]+".Capacity"),
					"","§e[좌 클릭시 통행증 등록]"), loc, inv);
				}
			}
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarUseGUI(Player player, String AltarName)
	{
		String UniqueCode = "§1§0§a§0§f§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0제단에 물건을 바치면 던전으로 이동합니다");

		removeFlagStack(AltarName, 160,0,1,null, 0, inv);
		removeFlagStack(AltarName, 160,0,1,null, 1, inv);
		removeFlagStack(AltarName, 160,0,1,null, 2, inv);
		removeFlagStack(AltarName, 160,0,1,null, 3, inv);
		removeFlagStack(AltarName, 160,0,1,null, 5, inv);
		removeFlagStack(AltarName, 160,0,1,null, 6, inv);
		removeFlagStack(AltarName, 160,0,1,null, 7, inv);
		removeFlagStack(AltarName, 160,0,1,null, 8, inv);
		player.openInventory(inv);
		return;
	}
	//AltarGUI//
	
	public void DungeonEXIT(Player player)
	{
		String UniqueCode = "§0§0§a§1§0§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0던전에서 나가시겠습니까?");

		removeFlagStack("§c§l[던전 잔류]", 166,0,1,null, 3, inv);
		removeFlagStack("§9§l[던전 퇴장]", 138,0,1,null, 5, inv);
		player.openInventory(inv);
		return;
	}
	
	
	

	//DungeonGUI Click//
	public void DungeonListMainGUIClick(InventoryClickEvent event)
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
			int Type = event.getInventory().getItem(47).getTypeId();
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			
			if(slot == 45)//이전 목록
				new OPboxGui().opBoxGuiMain(player, (byte) 3);
			else if(slot == 47)//타입 변경
			{
				if(event.isLeftClick())
				{
					if(Type == 52)
						DungeonListMainGUI(player, 0,358);
					else if(Type == 358)
						DungeonListMainGUI(player, 0,120);
					else if(Type == 120)
						DungeonListMainGUI(player, 0,52);
				}
				else
				{
					if(Type == 52)
						DungeonListMainGUI(player, 0,120);
					else if(Type == 358)
						DungeonListMainGUI(player, 0,52);
					else if(Type == 120)
						DungeonListMainGUI(player, 0,358);
				}
			}
			else if(slot == 48)//이전 페이지
				DungeonListMainGUI(player, page-1,Type);
			else if(slot == 49)//새 던전
			{
				if(main.MainServerOption.dungeonTheme.isEmpty())
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[SYSTEM] : 생성 가능한 던전 테마를 찾을 수 없습니다!");
					player.sendMessage("§e(던전 테마 다운로드 : §6http://cafe.naver.com/goldbigdragon/56713§e)");
					return;
				}
				UserDataObject u = new UserDataObject();
				u.setTemp(player, "Dungeon");
				player.closeInventory();
				if(Type == 52)
				{
					u.setType(player, "DungeonMain");
					u.setString(player, (byte)0, "ND");//NewDungeon
					player.sendMessage("§a[던전] : 새로운 던전 이름을 입력 해 주세요!");
				}
				else if(Type == 358)
				{
					u.setType(player, "EnterCard");
					u.setString(player, (byte)0, "NEC");//NewEnterCard
					player.sendMessage("§a[던전] : 새로운 통행증 이름을 입력 해 주세요!");
				}
				else if(Type == 120)
				{
					u.setType(player, "Altar");
					u.setString(player, (byte)0, "NA_Q");//NewAlter_Question
					player.sendMessage("§a[던전] : 현재 서 있는 위치에 제단을 세우시겠습니까? (네/아니오)");
				}
			}
			else if(slot == 50)//다음 페이지
				DungeonListMainGUI(player, page+1,Type);
			else
			{
				if(event.getCurrentItem().hasItemMeta()==false)
					return;
				if(Type == 52)
				{
					String DungeonName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				  	YamlLoader dungeonYaml = new YamlLoader();
					if(event.isLeftClick() == true)
						DungeonSetUpGUI(player, DungeonName);
					else if(event.isShiftClick() == true && event.isRightClick() == true)
					{
						SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						dungeonYaml.getConfig("Dungeon/DungeonList.yml");
						dungeonYaml.removeKey(DungeonName);
						dungeonYaml.saveConfig();
						File file = new File("plugins/GoldBigDragonRPG/Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
						file.delete();
						file = new File("plugins/GoldBigDragonRPG/Dungeon/Dungeon/"+DungeonName+"/Option.yml");
						file.delete();
						file = new File("plugins/GoldBigDragonRPG/Dungeon/Dungeon/"+DungeonName+"/Reward.yml");
						file.delete();
						file = new File("plugins/GoldBigDragonRPG/Dungeon/Dungeon/"+DungeonName);
						file.delete();
						DungeonListMainGUI(player, page,Type);
					}
					else if(event.isShiftClick()==false&&event.isRightClick())
					{
						dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
						new dungeon.DungeonCreater().createTestSeed(player, dungeonYaml.getInt("Size"), dungeonYaml.getInt("Maze_Level"), dungeonYaml.getString("Type.Name"));
					}
				}
				if(Type == 358)
				{
					String DungeonName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					if(event.isLeftClick() == true&&event.isShiftClick()==false)
						EnterCardSetUpGUI(player, DungeonName);
					else if(event.isShiftClick()&&event.isRightClick())
					{
						SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					  	YamlLoader dungeonYaml = new YamlLoader();
						dungeonYaml.getConfig("Dungeon/EnterCardList.yml");
						dungeonYaml.removeKey(DungeonName);
						dungeonYaml.saveConfig();
						DungeonListMainGUI(player, page,Type);
					}
					else if(event.isShiftClick()&& event.isLeftClick())
					{
					  	YamlLoader dungeonYaml = new YamlLoader();
						dungeonYaml.getConfig("Dungeon/EnterCardList.yml");
						ItemStack Icon = new MaterialData(dungeonYaml.getInt(DungeonName+".ID"), (byte) dungeonYaml.getInt(DungeonName+".DATA")).toItemStack(1);
						ItemMeta Icon_Meta = Icon.getItemMeta();
						Icon_Meta.setDisplayName("§c§l[던전 통행증]");
						Calendar Today = Calendar.getInstance();
						String UseableTime = "[제한 시간 없음]";
						if(dungeonYaml.getInt(DungeonName+".Hour")!=-1)
						{
							Today.add(Calendar.MONTH, 1);
							Today.add(Calendar.HOUR, dungeonYaml.getInt(DungeonName+".Hour"));
							Today.add(Calendar.MINUTE, dungeonYaml.getInt(DungeonName+".Min"));
							Today.add(Calendar.SECOND, dungeonYaml.getInt(DungeonName+".Sec"));
							
							UseableTime = Today.get(Calendar.YEAR)+"년 "+Today.get(Calendar.MONTH)+"월 "+Today.get(Calendar.DATE)+"일 "+Today.get(Calendar.HOUR)+"시 "+Today.get(Calendar.MINUTE)+"분 "+Today.get(Calendar.SECOND)+"초 까지";
						}
						Icon_Meta.setLore(Arrays.asList("","§c"+DungeonName,"","§c인원 : §f"+dungeonYaml.getInt(DungeonName+".Capacity"),"","§f"+UseableTime));
						Icon.setItemMeta(Icon_Meta);
						player.getInventory().addItem(Icon);
					}
				}
				if(Type == 120)
				{
					String DungeonName = event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-1);
					if(event.isLeftClick() == true&&event.isShiftClick()==false)
						AltarSettingGUI(player, DungeonName);
					else if(event.isShiftClick() == true && event.isRightClick() == true)
					{
						SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					  	YamlLoader dungeonYaml = new YamlLoader();
						dungeonYaml.getConfig("Dungeon/AltarList.yml");
						Location loc = new Location(Bukkit.getServer().getWorld(dungeonYaml.getString(DungeonName+".World")), dungeonYaml.getInt(DungeonName+".X"), dungeonYaml.getInt(DungeonName+".Y"), dungeonYaml.getInt(DungeonName+".Z"));
						int radius = dungeonYaml.getInt(DungeonName+".radius");
						Object[] EntitiList = Bukkit.getServer().getWorld(dungeonYaml.getString(DungeonName+".World")).getNearbyEntities(loc, radius, radius, radius).toArray();
						for(int count=0; count<EntitiList.length;count++)
							if(((Entity)EntitiList[count]).getCustomName()!=null)
								if(((Entity)EntitiList[count]).getCustomName().equals(DungeonName))
									((Entity)EntitiList[count]).remove();
						dungeonYaml.removeKey(DungeonName);
						dungeonYaml.saveConfig();
						File file = new File("plugins/GoldBigDragonRPG/Dungeon/Altar/"+DungeonName+".yml");
						file.delete();
						DungeonListMainGUI(player, page,Type);
					}
					else if(event.isShiftClick() == true && event.isLeftClick() == true)
					{
						SoundEffect.playSound(player, Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
					  	YamlLoader dungeonYaml = new YamlLoader();
						dungeonYaml.getConfig("Dungeon/AltarList.yml");
						Location loc = new Location(Bukkit.getServer().getWorld(dungeonYaml.getString(DungeonName+".World")), dungeonYaml.getInt(DungeonName+".X"), dungeonYaml.getInt(DungeonName+".Y"), dungeonYaml.getInt(DungeonName+".Z"));
						player.teleport(loc);
						SoundEffect.playSound(player, Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
					}
				}
			}
		}
	}
	
	public void DungeonSetUpGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 44)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
			if(slot == 36)//이전 목록
				DungeonListMainGUI(player, 0, 52);
			else if(slot == 11)//던전 타입
			{
				if(main.MainServerOption.dungeonTheme.isEmpty())
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[SYSTEM] : 던전 테마를 찾을 수 없습니다!");
					player.sendMessage("§e(던전 테마 다운로드 : §6http://cafe.naver.com/goldbigdragon/56713§e)");
					return;
				}
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
				String DungeonTheme = dungeonYaml.getString("Type.Name");
				if(main.MainServerOption.dungeonTheme.contains(DungeonTheme)==false)
					dungeonYaml.set("Type.Name", main.MainServerOption.dungeonTheme.get(0));
				else
				{
					for(int count = 0; count < main.MainServerOption.dungeonTheme.size(); count++)
						if(main.MainServerOption.dungeonTheme.get(count).equals(DungeonTheme))
						{
							if(count+1 >= main.MainServerOption.dungeonTheme.size())
								dungeonYaml.set("Type.Name", main.MainServerOption.dungeonTheme.get(0));
							else
								dungeonYaml.set("Type.Name", main.MainServerOption.dungeonTheme.get(count+1));
						}
				}
				dungeonYaml.saveConfig();
				DungeonSetUpGUI(player, DungeonName);
			}
			else if(slot == 24)//보상 상자
			{
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.8F);
				DungeonChestReward(player, DungeonName);
			}
			else if(slot == 29)//몬스터
			{
				SoundEffect.playSound(player, Sound.ENTITY_WOLF_AMBIENT, 0.8F, 1.0F);
				DungeonMonsterGUIMain(player, DungeonName);
			}
			else if(slot == 31)//던전BGM 설정
				DungeonMusicSettingGUI(player, 0, DungeonName, false);
			else if(slot == 33)//보스BGM 설정
				DungeonMusicSettingGUI(player, 0, DungeonName, true);
			else
			{
				UserDataObject u = new UserDataObject();
				u.setTemp(player, "Dungeon");
				u.setType(player, "DungeonMain");
				u.setString(player, (byte)1, DungeonName);
				player.closeInventory();
				if(slot == 13)//던전 크기
				{
					u.setString(player, (byte)0, "DS");//DungeonSize
					player.sendMessage("§a[던전] : 던전 크기를 입력 해 주세요! (최소 5 최대 50)");
				}
				else if(slot == 15)//미로 레벨
				{
					u.setString(player, (byte)0, "DML");//DungeonMazeLevel
					player.sendMessage("§a[던전] : 던전 미로 레벨을 입력 해 주세요! (최소 0 최대 10)");
					player.sendMessage("§e[주의] 미로 레벨이 높아지면 유저들이 빡칠수도 있음!");
				}
				else if(slot == 20)//레벨 제한
				{
					u.setString(player, (byte)0, "DDL");//DungeonDistrictLevel
					player.sendMessage("§a[던전] : 던전 입장 가능 레벨을 입력 해 주세요!");
				}
				else if(slot == 22)//돈, 경험치 보상 설정
				{
					u.setString(player, (byte)0, "DRM");//DungeonRewardMoney
					player.sendMessage("§a[던전] : 던전 클리어 보상금을 입력 해 주세요!");
				}
			}
		}
	}

	public void DungeonChestRewardClick(InventoryClickEvent event)
	{
		if(event.getSlot()%9==0)
			if(event.getClickedInventory().getName().contains("보상"))
				event.setCancelled(true);
	}
	
	public void DungeonMonsterGUIMainClick(InventoryClickEvent event)
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
			String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
			if(slot == 45)
				DungeonSetUpGUI(player, DungeonName);
			else if(slot%9 != 0 && slot <= 44)
				DungeonMonsterChooseMain(player, DungeonName, slot);
		}
		return;
	}
	
	public void DungeonMonsterChooseMainClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 8)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
			
			if(slot == 0)//이전 목록
				DungeonMonsterGUIMain(player, DungeonName);
			else
			{
				int Slot = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1)));
				String Type = null;
				if(Slot< 9)
				{
					Type="Boss";
					Slot = Slot-1;
				}
				else if(Slot < 18)
				{
					Type="SubBoss";
					Slot = Slot-10;
				}
				else if(Slot < 27)
				{
					Type="High";
					Slot = Slot-19;
				}
				else if(Slot < 36)
				{
					Type="Middle";
					Slot = Slot-28;
				}
				else if(Slot < 45)
				{
					Type="Normal";
					Slot = Slot-37;
				}
				if(slot == 2)//없음
				{
				  	YamlLoader dungeonYaml = new YamlLoader();
					dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
					dungeonYaml.removeKey(Type+"."+Slot);
					dungeonYaml.saveConfig();
					DungeonMonsterGUIMain(player, DungeonName);
				}
				else if(slot == 4)//일반 몬스터
					DungeonSelectNormalMonsterChoose(player, DungeonName, Type, Slot);
				else if(slot == 6)//커스텀 몬스터
					DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, 0);
			}
		}
	}
	
	public void DungeonSelectNormalMonsterChooseClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		int slot = event.getSlot();
		
		if(slot==53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
			int Slot = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot==45)
				DungeonMonsterChooseMain(player, DungeonName, Slot);
			else
			{
				String Type = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
				if(slot==0)
					dungeonYaml.set(Type+"."+Slot, "놂좀비");
				else if(slot==1)
					dungeonYaml.set(Type+"."+Slot, "놂스켈레톤");
				else if(slot==2)
					dungeonYaml.set(Type+"."+Slot, "놂크리퍼");
				else if(slot==3)
					dungeonYaml.set(Type+"."+Slot, "놂거미");
				else if(slot==4)
					dungeonYaml.set(Type+"."+Slot, "놂동굴거미");
				else if(slot==5)
					dungeonYaml.set(Type+"."+Slot, "놂엔더맨");
				else if(slot==6)
					dungeonYaml.set(Type+"."+Slot, "놂슬라임");
				else if(slot==7)
					dungeonYaml.set(Type+"."+Slot, "놂마그마큐브");
				else if(slot==8)
					dungeonYaml.set(Type+"."+Slot, "놂마녀");
				else if(slot==9)
					dungeonYaml.set(Type+"."+Slot, "놂좀비피그맨");
				else if(slot==10)
					dungeonYaml.set(Type+"."+Slot, "놂블레이즈");
				else if(slot==11)
					dungeonYaml.set(Type+"."+Slot, "놂가스트");
				else if(slot==12)
					dungeonYaml.set(Type+"."+Slot, "놂수호자");
				else if(slot==13)
					dungeonYaml.set(Type+"."+Slot, "놂박쥐");
				else if(slot==14)
					dungeonYaml.set(Type+"."+Slot, "놂돼지");
				else if(slot==15)
					dungeonYaml.set(Type+"."+Slot, "놂양");
				else if(slot==16)
					dungeonYaml.set(Type+"."+Slot, "놂소");
				else if(slot==17)
					dungeonYaml.set(Type+"."+Slot, "놂닭");
				else if(slot==18)
					dungeonYaml.set(Type+"."+Slot, "놂오징어");
				else if(slot==19)
					dungeonYaml.set(Type+"."+Slot, "놂늑대");
				else if(slot==20)
					dungeonYaml.set(Type+"."+Slot, "놂버섯소");
				else if(slot==21)
					dungeonYaml.set(Type+"."+Slot, "놂오셀롯");
				else if(slot==22)
					dungeonYaml.set(Type+"."+Slot, "놂말");
				else if(slot==23)
					dungeonYaml.set(Type+"."+Slot, "놂토끼");
				else if(slot==24)
					dungeonYaml.set(Type+"."+Slot, "놂주민");
				else if(slot==25)
					dungeonYaml.set(Type+"."+Slot, "놂북극곰");
				dungeonYaml.saveConfig();
				DungeonMonsterGUIMain(player, DungeonName);
			}
		}
	}
	
	public void DungeonSelectCustomMonsterChooseClick(InventoryClickEvent event)
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
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			int Slot = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			String Type = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
			String DungeonName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(2));
			
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
			if(slot == 45)//이전 목록
				DungeonMonsterChooseMain(player, DungeonName, Slot);
			else if(slot == 48)//이전 페이지
				DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, page-1);
			else if(slot == 50)//다음 페이지
				DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, page+1);
			else if(event.getCurrentItem().getTypeId()==383)
			{
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
				dungeonYaml.set(Type+"."+Slot, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				dungeonYaml.saveConfig();
				DungeonMonsterGUIMain(player, DungeonName);
			}
		}
	}
	
	public void DungeonMusicSettingGUIClick(InventoryClickEvent event)
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
			String DungeonName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			boolean isBoss = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				DungeonSetUpGUI(player, DungeonName);
			else if(slot == 48)//이전 페이지
				DungeonMusicSettingGUI(player, page-1,DungeonName,isBoss);
			else if(slot == 50)//다음 페이지
				DungeonMusicSettingGUI(player, page+1,DungeonName,isBoss);
			else
			{
				if(event.getCurrentItem().hasItemMeta())
				{
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlLoader dungeonYaml = new YamlLoader();
					dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
					if(isBoss)
						dungeonYaml.set("BGM.BOSS", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					else
						dungeonYaml.set("BGM.Normal", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					dungeonYaml.saveConfig();
					DungeonSetUpGUI(player, DungeonName);
				}
			}
		}
			
		
		switch (event.getSlot())
		{
		default :
			return;
		}
	}
	//DungeonGUI Click//

	
	//EnterCardGUI Click//
	public void EnterCardSetUpGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 8)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String EnterCardName = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1));
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)//이전 목록
				DungeonListMainGUI(player, 0, 358);
			else if(slot == 2)//던전 설정
			{
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/DungeonList.yml");
				if(dungeonYaml.getKeys().size()==0)
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[던전] : 생성된 던전이 없습니다! 던전을 먼저 만들고 오세요!");
				}
				else
					EnterCardDungeonSettingGUI(player, 0, EnterCardName);
			}
			else if(slot == 4)//아이템 형태 초기화
			{
				SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.8F);
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/EnterCardList.yml");
				dungeonYaml.set(EnterCardName+".ID",358);
				dungeonYaml.set(EnterCardName+".DATA",0);
				dungeonYaml.saveConfig();
				EnterCardSetUpGUI(player, EnterCardName);
			}
			else
			{
				UserDataObject u = new UserDataObject();
				player.closeInventory();
				u.setTemp(player, "Dungeon");
				u.setType(player, "EnterCard");
				u.setString(player, (byte)1, EnterCardName);
				if(slot == 3)//아이템 형태 설정
				{
					u.setString(player, (byte)0, "ECID");//EnterCardID
					player.sendMessage("§a[통행증] : 통행증 아이템 타입 ID를 입력 해 주세요.");
				}
				else if(slot == 5)//입장 인원 설정
				{
					u.setString(player, (byte)0, "ECC");//EnterCardCapacity
					player.sendMessage("§a[통행증] : 필요 입장 인원 수를 입력 해 주세요.");
				}
				else if(slot == 6)//유효시간 설정
				{
					u.setString(player, (byte)0, "ECUH");//EnterCardUseableHour
					player.sendMessage("§a[통행증] : 유효 시간을 입력 해 주세요. (최대 24시간, -1입력시 무제한)");
				}
			}
		}
	}

	public void EnterCardDungeonSettingGUIClick(InventoryClickEvent event)
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
			String EnterCardName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				EnterCardSetUpGUI(player, EnterCardName);
			else
			{
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/EnterCardList.yml");
				dungeonYaml.set(EnterCardName+".Dungeon", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				dungeonYaml.saveConfig();
				EnterCardSetUpGUI(player, EnterCardName);
			}
		}
	}
	//EnterCardGUI Click//

	
	//AltarGUI Click//
	public void AltarShapeListGUIClick(InventoryClickEvent event)
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
			if(slot == 45)//이전 목록
				DungeonListMainGUI(player, 0, 120);
			else
			{
				if(!ServerTickMain.ServerTask.equals("null"))
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[Server] : 현재 서버는 §e"+ServerTickMain.ServerTask+"§c 작업 중입니다.");
					return;
				}
				player.closeInventory();
			  	YamlLoader AltarList = new YamlLoader();
				AltarList.getConfig("Dungeon/AltarList.yml");
				String Code = "§0§l";
				Code = Code+"§f[제단]";
				String Salt = Code;
				int ID = 1;
				int DATA = 0;
				String Type = null;
				int radius = 5;
				if(slot == 0)
				{
					Type = "MossyAltar";
					ID = 48;
					radius = 3;
				}
				else if(slot == 1)
				{
					Type = "GoldBigDragon";
					ID = 41;
					radius = 20;
				}
				else if(slot == 2)
				{
					Type = "StoneHenge";
					ID = 1;
					radius = 8;
				}
				else if(slot == 3)
				{
					Type = "AnatomicalBoard";
					ID = 1;
					DATA = 5;
					radius = 3;
				}
				
				for(;;)
				{
					for(int count=0;count < 6; count++)
						Salt = Salt+getRandomCode();
					if(AltarList.contains(Salt)==false)
						break;
					Salt = Code;
				}
				AltarList.set(Salt+".Name", "방금 지어진 제단");
				AltarList.set(Salt+".Type", Type);
				AltarList.set(Salt+".radius", radius);
				AltarList.set(Salt+".ID", ID);
				AltarList.set(Salt+".DATA", DATA);
				AltarList.set(Salt+".World", player.getLocation().getWorld().getName());
				AltarList.set(Salt+".X", (int)player.getLocation().getX());
				AltarList.set(Salt+".Y", (int)player.getLocation().getY());
				AltarList.set(Salt+".Z", (int)player.getLocation().getZ());
				AltarList.saveConfig();
				AltarList.getConfig("Dungeon/Altar/"+Salt+".yml");
				AltarList.createSection("EnterCard");
				AltarList.saveConfig();
				new structure.StructureMain().CreateSturcture(player, Salt, (short) (101+event.getSlot()), 4);
			}
		}
	}

	public void AltarSettingGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		String AltarName = event.getInventory().getItem(8).getItemMeta().getLore().get(1);
		
		
		if(slot == 8)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)//이전 목록
				DungeonListMainGUI(player, 0, 120);
			else if(slot == 2)//이름 변경
			{
				UserDataObject u = new UserDataObject();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				u.setTemp(player, "Dungeon");
				player.closeInventory();
				u.setType(player, "Altar");
				u.setString(player, (byte)0, "EAN");//EditAltarName
				u.setString(player, (byte)1, AltarName);
				player.sendMessage("§a[제단] : 제단 이름을 입력 해 주세요.");
			}
			else if(slot == 4)//일반 던전 설정
				AltarDungeonSettingGUI(player, 0, AltarName);
			else if(slot == 6)//통행증 설정
				AltarEnterCardSettingGUI(player, 0, AltarName.substring(2, AltarName.length()));
		}
	}
	
	public void AltarUseGUIClick(InventoryClickEvent event)
	{
		if(event.getSlot()!=4)
			if(ChatColor.stripColor(event.getClickedInventory().getName()).equals("제단에 물건을 바치면 던전으로 이동합니다"))
				event.setCancelled(true);
	}
	
	public void AltarDungeonSettingGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1).substring(2, event.getInventory().getItem(53).getItemMeta().getLore().get(1).length());
		int slot = event.getSlot();
		
		if(slot == 53)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)
				AltarSettingGUI(player, AltarName);
			else
			{
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/Altar/"+AltarName+".yml");
				dungeonYaml.set("NormalDungeon", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				dungeonYaml.saveConfig();
				AltarSettingGUI(player, AltarName);
			}
		}
	}
	
	public void AltarEnterCardSettingGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1);
		int slot = event.getSlot();

		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			if(slot == 45)//이전 목록
				AltarSettingGUI(player, AltarName);
			else if(slot == 48)//이전 페이지
				AltarEnterCardSettingGUI(player, page-1, AltarName);
			else if(slot == 49)//통행증 등록
				AltarEnterCardListGUI(player, page, AltarName);
			else if(slot == 50)//다음 페이지
				AltarEnterCardSettingGUI(player, page+1, AltarName);
			else if(event.isShiftClick()&&event.isRightClick())
			{
				SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/Altar/"+AltarName+".yml");
				dungeonYaml.removeKey("EnterCard."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				dungeonYaml.saveConfig();
				AltarEnterCardSettingGUI(player, page, AltarName);
				return;
			}
		}
	}
	
	public void AltarEnterCardListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1);
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			if(slot == 45)//이전 목록
				AltarEnterCardSettingGUI(player, 0, AltarName);
			else if(slot == 48)//이전 페이지
				AltarEnterCardListGUI(player, page-1, AltarName);
			else if(slot == 50)//다음 페이지
				AltarEnterCardListGUI(player, page+1, AltarName);
			else
			{
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/Altar/"+AltarName+".yml");
				dungeonYaml.createSection("EnterCard."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				dungeonYaml.saveConfig();
				AltarEnterCardSettingGUI(player, page, AltarName);
			}
		}
	}
	
	public String getRandomCode()
	{
		int randomNum = new util.UtilNumber().RandomNum(0, 15);
		switch(randomNum)
		{
			case 0:
				return "§0";
			case 1:
				return "§1";
			case 2:
				return "§2";
			case 3:
				return "§3";
			case 4:
				return "§4";
			case 5:
				return "§5";
			case 6:
				return "§6";
			case 7:
				return "§7";
			case 8:
				return "§8";
			case 9:
				return "§9";
			case 10:
				return "§a";
			case 11:
				return "§b";
			case 12:
				return "§c";
			case 13:
				return "§d";
			case 14:
				return "§e";
			case 15:
				return "§f";
		}
		return "§0";
	}
	//AltarGUI Click//
	
	public void DungeonEXITClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		int slot = event.getSlot();
		player.closeInventory();
		if(slot == 3)
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
		else if(slot == 5)
		{
			new dungeon.DungeonMain().eraseAllDungeonKey(player, true);
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		  	YamlLoader playerYaml = new YamlLoader();
			String DungeonName = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter();
			long UTC = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC();
			playerYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Entered/"+UTC+".yml");
			if(playerYaml.contains("EnteredAlter"))
			{
				DungeonName = playerYaml.getString("EnteredAlter");
				playerYaml.getConfig("Dungeon/AltarList.yml");
				if(playerYaml.contains(DungeonName))
				{
					Location loc = new Location(Bukkit.getServer().getWorld(playerYaml.getString(DungeonName+".World")), playerYaml.getLong(DungeonName+".X"), playerYaml.getLong(DungeonName+".Y")+1, playerYaml.getLong(DungeonName+".Z"));
					player.teleport(loc);
					return;
				}
			}
			new util.UtilPlayer().teleportToCurrentArea(player, true);
			return;
		}
	}
	
	//DungeonGUI Close//
	public void DungeonChestRewardClose(InventoryCloseEvent event)
	{
		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);

	  	YamlLoader dungeonYaml = new YamlLoader();
		dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Reward.yml");
		
		for(int count = 0; count < 8; count++)
		{
			if(event.getInventory().getItem(count+1)!=null)
				dungeonYaml.set("100."+count, event.getInventory().getItem(count+1));
			else
				dungeonYaml.removeKey("100."+count);
			if(event.getInventory().getItem(count+10)!=null)
				dungeonYaml.set("90."+count, event.getInventory().getItem(count+10));	
			else
				dungeonYaml.removeKey("90."+count);
			if(event.getInventory().getItem(count+19)!=null)
				dungeonYaml.set("50."+count, event.getInventory().getItem(count+19));	
			else
				dungeonYaml.removeKey("50."+count);
			if(event.getInventory().getItem(count+28)!=null)
				dungeonYaml.set("10."+count, event.getInventory().getItem(count+28));
			else
				dungeonYaml.removeKey("10."+count);
			if(event.getInventory().getItem(count+37)!=null)
				dungeonYaml.set("1."+count, event.getInventory().getItem(count+37));
			else
				dungeonYaml.removeKey("1."+count);	
			if(event.getInventory().getItem(count+46)!=null)
				dungeonYaml.set("0."+count, event.getInventory().getItem(count+46));
			else
				dungeonYaml.removeKey("0."+count);
		}
		dungeonYaml.saveConfig();

		SoundEffect.playSound((Player) event.getPlayer(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
		event.getPlayer().sendMessage("§a[던전] : 보상 설정 완료!");
	}
	
	public void AltarUSEGuiClose(InventoryCloseEvent event)
	{
		ItemStack item = event.getInventory().getItem(4);
		if(item!=null)
		{
			String AltarName = event.getInventory().getItem(0).getItemMeta().getDisplayName();
			Player player = (Player) event.getPlayer();
		  	YamlLoader altarYaml = new YamlLoader();
			altarYaml.getConfig("Dungeon/Altar/"+AltarName+".yml");
			event.getInventory().setItem(4, null);
			
			int LvDistrict = -1;
			int RealLvDistrict = -1;
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() != null)
			{
				if(new util.UtilPlayer().giveItem(player, item)==false)
					new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
				SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
				player.sendMessage("§f(이미 던전이 만들어 지고 있다...)");
				return;
			}
			if(item.hasItemMeta())
			{
				if(item.getItemMeta().hasDisplayName())
				{
					if(item.getItemMeta().getDisplayName().equals("§c§l[던전 통행증]"))
					{
						if(altarYaml.contains("EnterCard."+ChatColor.stripColor(item.getItemMeta().getLore().get(1))))
						{
							int capacity = Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(3)).split(" : ")[1]);
							String time = ChatColor.stripColor(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1));
							boolean canUse = false;
							if(time.equals("[제한 시간 없음]"))
								canUse = true;
							else
							{
								int year = Integer.parseInt(time.split(" ")[0].substring(0, time.split(" ")[0].length()-1));
								int month = Integer.parseInt(time.split(" ")[1].substring(0, time.split(" ")[1].length()-1));
								int day = Integer.parseInt(time.split(" ")[2].substring(0, time.split(" ")[2].length()-1));
								int hour = Integer.parseInt(time.split(" ")[3].substring(0, time.split(" ")[3].length()-1));
								int min = Integer.parseInt(time.split(" ")[4].substring(0, time.split(" ")[4].length()-1));
								int sec = Integer.parseInt(time.split(" ")[5].substring(0, time.split(" ")[5].length()-1));

								Calendar Today = Calendar.getInstance();
								Today.add(Calendar.MONTH, 1);
								
								if(year > Today.get(Calendar.YEAR))
									canUse = true;
								else if(year == Today.get(Calendar.YEAR))
									if(month > Today.get(Calendar.MONTH))
										canUse = true;
									else if(month == Today.get(Calendar.MONTH))
										if(day > Today.get(Calendar.DATE))
											canUse = true;
										else if(day == Today.get(Calendar.DATE))
											if(hour > Today.get(Calendar.HOUR))
												canUse = true;
											else if(hour == Today.get(Calendar.HOUR))
												if(min > Today.get(Calendar.MINUTE))
													canUse = true;
												else if(min == Today.get(Calendar.MINUTE))
													if(sec > Today.get(Calendar.SECOND))
														canUse = true;
							}
							if(canUse)
							{
							  	YamlLoader enterCardYaml = new YamlLoader();
								enterCardYaml.getConfig("Dungeon/EnterCardList.yml");
							  	YamlLoader levelYaml = new YamlLoader();
							  	levelYaml.getConfig("Dungeon/Dungeon/"+enterCardYaml.getString(ChatColor.stripColor(item.getItemMeta().getLore().get(1))+".Dungeon")+"/Option.yml");
							  	int levelDistrict = levelYaml.getInt("District.Level");
							  	int realLevelDistrict = levelYaml.getInt("District.RealLevel");
							  	
							  	
								if(enterCardYaml.contains(ChatColor.stripColor(item.getItemMeta().getLore().get(1))))
								{
									PartyEnterDungeon(player, item, AltarName, capacity, enterCardYaml.getString(ChatColor.stripColor(item.getItemMeta().getLore().get(1))+".Dungeon"), levelDistrict, realLevelDistrict);
								}
								else
								{
									if(new util.UtilPlayer().giveItem(player, item)==false)
										new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
									SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
									player.sendMessage("§f(이 물건은 제물로 바칠 수 없는 듯 하다...)");
									return;
								}
							}
							else
							{
								if(new util.UtilPlayer().giveItem(player, item)==false)
									new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
								SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
								player.sendMessage("§f(던전 통행증의 유효시간이 지났다...)");
								return;
							}
						}
						else
						{
							if(new util.UtilPlayer().giveItem(player, item)==false)
								new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
							SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
							player.sendMessage("§f(이 물건은 제물로 바칠 수 없는 듯 하다...)");
							return;
						}
					}
					else
					{
						if(altarYaml.getString("NormalDungeon")!=null)
						{
						  	YamlLoader dungeonYaml = new YamlLoader();
							dungeonYaml.getConfig("Dungeon/DungeonList.yml");
							if(dungeonYaml.contains(altarYaml.getString("NormalDungeon")))
								PartyEnterDungeon(player, item, AltarName, -1, altarYaml.getString("NormalDungeon"), LvDistrict, RealLvDistrict);
							else
							{
								altarYaml.set("NormalDungeon", null);
								altarYaml.saveConfig();
								if(new util.UtilPlayer().giveItem(player, item)==false)
									new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
								SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
								player.sendMessage("§f(이 물건은 제물로 바칠 수 없는 듯 하다...)");
								return;
							}
						}
						else
						{
							if(new util.UtilPlayer().giveItem(player, item)==false)
								new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
							SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
							player.sendMessage("§f(이 물건은 제물로 바칠 수 없는 듯 하다...)");
							return;
						}
					}
				}
				return;
			}
			if(altarYaml.getString("NormalDungeon")!=null)
			{
			  	YamlLoader dungeonYaml = new YamlLoader();
				dungeonYaml.getConfig("Dungeon/DungeonList.yml");
				if(dungeonYaml.contains(altarYaml.getString("NormalDungeon")))
					PartyEnterDungeon(player, item, AltarName, -1, altarYaml.getString("NormalDungeon"), LvDistrict, RealLvDistrict);
				else
				{
					altarYaml.set("NormalDungeon", null);
					altarYaml.saveConfig();
					if(new util.UtilPlayer().giveItem(player, item)==false)
						new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
					SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
					player.sendMessage("§f(이 물건은 제물로 바칠 수 없는 듯 하다...)");
					return;
				}
			}
			else
			{
				if(new util.UtilPlayer().giveItem(player, item)==false)
					new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
				SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
				player.sendMessage("§f(이 물건은 제물로 바칠 수 없는 듯 하다...)");
				return;
			}
		}
	}

	private void PartyEnterDungeon(Player player, ItemStack item, String AltarName, int capacity, String DungeonName, int LvDistrict, int RealLvDistrict)
	{
		if(main.MainServerOption.partyJoiner.containsKey(player))
		{
			
			if(capacity!=-1)
				if(main.MainServerOption.party.get(main.MainServerOption.partyJoiner.get(player)).getPartyMembers()!=capacity)
				{
					if(new util.UtilPlayer().giveItem(player, item)==false)
						new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
					SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
					player.sendMessage("§c[던전] : 던전 입장 인원이 맞지 않습니다! ("+capacity+"명)");
					return;
				}
		  	YamlLoader AltarConfig = new YamlLoader();
		  	YamlLoader dungeonYaml = new YamlLoader();
			AltarConfig.getConfig("Dungeon/Altar/"+AltarName+".yml");
			dungeonYaml.getConfig("Dungeon/Dungeon/"+AltarConfig.getString("NormalDungeon")+"/Option.yml");
			if(LvDistrict==-1)
				LvDistrict = dungeonYaml.getInt("District.Level");
			if(RealLvDistrict==-1)
				RealLvDistrict = dungeonYaml.getInt("District.RealLevel");
			if(DungeonName!=null)
				dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
			long UTC = main.MainServerOption.partyJoiner.get(player);
			if(main.MainServerOption.party.get(UTC).getLeader().equals(player.getName()))
			{
				//파티원 추가하기//
				ArrayList<Player> NearPartyMember = new ArrayList<Player>();
				main.MainServerOption.party.get(UTC).getMember();
				for(int count = 0; count < main.MainServerOption.party.get(UTC).getPartyMembers(); count++)
				{
					if(player.getWorld().getName().equals(main.MainServerOption.party.get(UTC).getMember()[count].getWorld().getName()))
						if(player.getLocation().distance(main.MainServerOption.party.get(UTC).getMember()[count].getLocation()) < 11)
							NearPartyMember.add(main.MainServerOption.party.get(UTC).getMember()[count]);
				}
				for(int count = 0; count < NearPartyMember.size(); count++)
				{
					if(main.MainServerOption.PlayerList.get(NearPartyMember.get(count).getUniqueId().toString()).getStat_Level()< LvDistrict)
					{
						if(new util.UtilPlayer().giveItem(player, item)==false)
							new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
						SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
						player.sendMessage("§c[던전] : 파티원 "+NearPartyMember.get(count).getName()+"님의 레벨이 낮아 던전에 입장할 수 없습니다!");
						player.sendMessage("§c(레벨 제한 : "+dungeonYaml.getInt("District.Level")+")");
						return;
					}
					if(main.MainServerOption.PlayerList.get(NearPartyMember.get(count).getUniqueId().toString()).getStat_RealLevel()<RealLvDistrict)
					{
						if(new util.UtilPlayer().giveItem(player, item)==false)
							new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
						SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
						player.sendMessage("§c[던전] : 파티원 "+NearPartyMember.get(count).getName()+"님의 누적 레벨이 낮아 던전에 입장할 수 없습니다!");
						player.sendMessage("§c(누적 레벨 제한 : "+dungeonYaml.getInt("District.RealLevel")+")");
						return;
					}
				}
				if(new dungeon.DungeonCreater().CreateDungeon(player, dungeonYaml.getInt("Size"), dungeonYaml.getInt("Maze_Level"), dungeonYaml.getString("Type.Name"),DungeonName,NearPartyMember, AltarName, item)==false)
				{
					if(new util.UtilPlayer().giveItem(player, item)==false)
						new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
					SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
					return;
				}
			}
			else
			{
				if(new util.UtilPlayer().giveItem(player, item)==false)
					new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
				SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
				player.sendMessage("§c[파티] : 파티의 리더만 제단에 물건을 바칠 수 있습니다!");
				return;
			}
		}
		else
			SoloEnterDungeon(player, item, AltarName, capacity, DungeonName, LvDistrict, RealLvDistrict);
	}
	
	private void SoloEnterDungeon(Player player, ItemStack item, String AltarName, int capacity, String DungeonName, int LvDistrict, int RealLvDistrict)
	{
		
		if(capacity==-1||capacity==1)
		{
		  	YamlLoader AltarConfig = new YamlLoader();
		  	YamlLoader dungeonYaml = new YamlLoader();
			AltarConfig.getConfig("Dungeon/Altar/"+AltarName+".yml");
			dungeonYaml.getConfig("Dungeon/Dungeon/"+AltarConfig.getString("NormalDungeon")+"/Option.yml");
			if(DungeonName!=null)
				dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
			if(LvDistrict==-1)
				LvDistrict = dungeonYaml.getInt("District.Level");
			if(RealLvDistrict==-1)
				RealLvDistrict = dungeonYaml.getInt("District.RealLevel");
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()< LvDistrict)
			{
				if(new util.UtilPlayer().giveItem(player, item)==false)
					new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
				SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
				player.sendMessage("§c[던전] : 당신의 레벨이 낮아 던전에 입장할 수 없습니다!");
				player.sendMessage("§c(레벨 제한 : "+dungeonYaml.getInt("District.Level")+")");
				return;
			}
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel()<RealLvDistrict)
			{
				if(new util.UtilPlayer().giveItem(player, item)==false)
					new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
				SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
				player.sendMessage("§c[던전] : 당신의 누적 레벨이 낮아 던전에 입장할 수 없습니다!");
				player.sendMessage("§c(누적 레벨 제한 : "+dungeonYaml.getInt("District.RealLevel")+")");
				return;
			}
			if(new dungeon.DungeonCreater().CreateDungeon(player, dungeonYaml.getInt("Size"), dungeonYaml.getInt("Maze_Level"), dungeonYaml.getString("Type.Name"),DungeonName,null, AltarName, item)==false)
			{
				if(new util.UtilPlayer().giveItem(player, item)==false)
					new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
				SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
				return;
			}
		}
		else
		{
			if(new util.UtilPlayer().giveItem(player, item)==false)
				new event.EventItemDrop().CustomItemDrop(player.getLocation(), item);
			SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
			player.sendMessage("§c[던전] : 던전 입장 인원이 맞지 않습니다! ("+capacity+"명)");
			return;
		}
	}
	//DungeonGUI Close//

}
