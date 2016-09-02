package GoldBigDragon_RPG.Dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.GUI.OPBoxGUI;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public final class DungeonGUI extends GUIutil
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
	
	//GUI Router//
	public void InventoryClickRouter(InventoryClickEvent event, String InventoryName)
	{
		String Striped = ChatColor.stripColor(event.getInventory().getName().toString());
		
		//닫아서 저장하는 류는 여기에 넣기
		if(event.getInventory().getType()==InventoryType.CHEST)
		{
			if(!(Striped.contains("보상")||
			ChatColor.stripColor(InventoryName).contains("바치면")
			))
				event.setCancelled(true);
		}
		InventoryName = InventoryName.split(":")[0];

		if(ChatColor.stripColor(InventoryName).contains("바치면"))//일반 제단 기능 넣기
			AltarUseGUIClick(event);
		else if(InventoryName.contains("던전에서"))
			DungeonEXITClick(event);
		else if(InventoryName.contains("등록된"))
			AltarEnterCardSettingGUIClick(event);
		else if(InventoryName.contains("생성된"))
			AltarEnterCardListGUIClick(event);
		else if(InventoryName.contains("던전"))
		{
			if(InventoryName.contains("목록"))
				DungeonListMainGUIClick(event);
			else if(InventoryName.contains("설정"))
				DungeonSetUpGUIClick(event);
			else if(InventoryName.contains("보상"))
				DungeonChestRewardClick(event);
			else if(InventoryName.contains("몬스터"))
			{
				if(event.getInventory().getSize()==9)
					DungeonMonsterChooseMainClick(event);
				else if(event.getInventory().getSize()==54)
						DungeonMonsterGUIMainClick(event);
			}
			else if(InventoryName.contains("배경음"))
				DungeonMusicSettingGUIClick(event);
		}
		else if(InventoryName.contains("제단"))
		{
			if(InventoryName.contains("목록"))
				DungeonListMainGUIClick(event);
			else if(InventoryName.contains("형태"))
				AltarShapeListGUIClick(event);
			else if(InventoryName.contains("설정"))
				AltarSettingGUIClick(event);
			else if(InventoryName.contains("연결"))
				AltarDungeonSettingGUIClick(event);
			return;
		}
		else if(InventoryName.contains("통행증"))
		{
			if(InventoryName.contains("목록"))
				DungeonListMainGUIClick(event);	
			else if(InventoryName.contains("설정"))
				EnterCardSetUpGUIClick(event);
			else if(InventoryName.contains("연결"))
				EnterCardDungeonSettingGUIClick(event);
		}
		else if(InventoryName.contains("몬스터"))
		{
			if(InventoryName.contains("일반"))
				DungeonSelectNormalMonsterChooseClick(event);
			else if(InventoryName.contains("커스텀"))
				DungeonSelectCustomMonsterChooseClick(event);
		}
		return;
	}
	
	public void InventoryCloseRouter(InventoryCloseEvent event, String InventoryName)
	{
		if(InventoryName.contains("바치면"))
			AltarUSEGuiClose(event);
		else if(InventoryName.contains("던전"))
		{
			if(InventoryName.contains("보상"))
				DungeonChestRewardClose(event);
		}
	}
	//GUI Router//
	
	
	
	//DungeonGUI//
	public void DungeonListMainGUI(Player player, int page, int Type)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");

		Inventory inv = null;
		if(Type==52)
			inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "던전 목록 : " + (page+1));
		else if(Type==358)
			inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "통행증 목록 : " + (page+1));
		else if(Type==120)
			inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "제단 목록 : " + (page+1));
		Object[] DungeonList = null;
		if(Type==52)//던전
		{
			DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
			
			int loc=0;
			for(int count = page*45; count < DungeonList.length;count++)
			{
				YamlManager DungeonOptionYML = YC.getNewConfig("Dungeon/Dungeon/"+DungeonList[count].toString()+"/Option.yml");
				
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), 52,0,1,Arrays.asList(
				"",ChatColor.BLUE+"던전 형태 : "+ChatColor.WHITE+DungeonOptionYML.getString("Type.Name")
				,ChatColor.BLUE+"던전 크기 : "+ChatColor.WHITE+DungeonOptionYML.getInt("Size")
				,ChatColor.BLUE+"던전 밀집도 : "+ChatColor.WHITE+DungeonOptionYML.getInt("Maze_Level")
				,ChatColor.BLUE+"레벨 제한 : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.Level")+" 이상"
				,ChatColor.BLUE+"누적 레벨 제한 : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.RealLevel")+" 이상"
				,"",ChatColor.BLUE+"[기본 클리어 보상]"
				,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.Money")+" "+GoldBigDragon_RPG.Main.ServerOption.Money
				,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.EXP")+ChatColor.AQUA+" "+ChatColor.BOLD+"EXP"
				,"",ChatColor.YELLOW+"[좌 클릭시 던전 설정]",ChatColor.RED+"[Shift + 우클릭시 던전 삭제]"), loc, inv);
				
				loc=loc+1;
			}
		}
		else if(Type==358)//통행증
		{
			DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
			DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
			int loc=0;
			for(int count = page*45; count < DungeonList.length;count++)
			{
				String Time = null;
				if(DungeonConfig.getInt(DungeonList[count].toString()+".Hour")!=-1)
				{
					if(DungeonConfig.getInt(DungeonList[count].toString()+".Hour")!=0)
						Time = DungeonConfig.getInt(DungeonList[count].toString()+".Hour")+"시간 ";
					if(DungeonConfig.getInt(DungeonList[count].toString()+".Min")!=0)
						Time = Time+DungeonConfig.getInt(DungeonList[count].toString()+".Min")+"분 ";
					Time = Time+DungeonConfig.getInt(DungeonList[count].toString()+".Sec")+"초";
				}
				else
					Time = "무제한";
				if(DungeonConfig.getString(DungeonList[count].toString()+".Dungeon")==null)
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
					"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+"없음",
					ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
					ChatColor.BLUE+"유효 시간 : "+ChatColor.WHITE+""+Time,
					"",ChatColor.YELLOW+"[좌 클릭시 통행증 설정]",ChatColor.YELLOW+"[Shift + 좌 클릭시 통행증 발급]",ChatColor.RED+"[Shift + 우클릭시 통행증 삭제]"), loc, inv);
				else
				{
					YamlManager Dungeon = YC.getNewConfig("Dungeon/DungeonList.yml");
					if(Dungeon.contains(DungeonConfig.getString(DungeonList[count].toString()+".Dungeon")))
					{
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
						"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+""+DungeonConfig.getString(DungeonList[count].toString()+".Dungeon"),
						ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
						ChatColor.BLUE+"유효 시간 : "+ChatColor.WHITE+""+Time,
						"",ChatColor.YELLOW+"[좌 클릭시 통행증 설정]",ChatColor.YELLOW+"[Shift + 좌 클릭시 통행증 발급]",ChatColor.RED+"[Shift + 우클릭시 통행증 삭제]"), loc, inv);
					}
					else
					{
						DungeonConfig.set(DungeonList[count].toString()+".Dungeon",null);
						DungeonConfig.saveConfig();
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
						"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+"없음",
						ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
						ChatColor.BLUE+"유효 시간 : "+ChatColor.WHITE+""+Time,
						"",ChatColor.YELLOW+"[좌 클릭시 통행증 설정]",ChatColor.YELLOW+"[Shift + 좌 클릭시 통행증 발급]",ChatColor.RED+"[Shift + 우클릭시 통행증 삭제]"), loc, inv);
					}
						
				}
				
				loc=loc+1;
			}
		}
		else if(Type==120)//제단
		{
			DungeonConfig = YC.getNewConfig("Dungeon/AltarList.yml");
			DungeonList = DungeonConfig.getKeys().toArray();
			int loc=0;
			for(int count = page*45; count < DungeonList.length;count++)
			{
				String AltarCode = DungeonList[count].toString();
				Stack2(ChatColor.WHITE+DungeonConfig.getString(AltarCode+".Name"), DungeonConfig.getInt(AltarCode+".ID"),DungeonConfig.getInt(AltarCode+".DATA"),1,Arrays.asList(
				"",ChatColor.BLUE+"[제단 위치]",
				ChatColor.WHITE+" "+DungeonConfig.getString(AltarCode+".World"),
				ChatColor.WHITE+" "+DungeonConfig.getInt(AltarCode+".X")+","+DungeonConfig.getInt(AltarCode+".Y")+","+DungeonConfig.getInt(AltarCode+".Z"),
				"",ChatColor.YELLOW+"[좌 클릭시 제단 설정]",ChatColor.YELLOW+"[Shift + 좌 클릭시 제단 이동]",ChatColor.RED+"[Shift + 우클릭시 제단 철거]","",AltarCode), loc, inv);
				
				loc=loc+1;
			}
		}
		if(Type==52)
			Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[다음 항목]", 52,0,1,Arrays.asList(ChatColor.BLUE + "현재 항목 : "+ChatColor.WHITE+"던전","",ChatColor.YELLOW+"던전을 생성하기 위해서는",ChatColor.YELLOW+"아래의 3가지 구성물이 존재 해야 합니다.",ChatColor.YELLOW+"[던전, 통행증, 제단]"), 47, inv);
		else if(Type==358)
			Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[다음 항목]", 358,0,1,Arrays.asList(ChatColor.BLUE + "현재 항목 : "+ChatColor.WHITE+"통행증","",ChatColor.YELLOW+"던전을 생성하기 위해서는",ChatColor.YELLOW+"아래의 3가지 구성물이 존재 해야 합니다.",ChatColor.YELLOW+"[던전, 통행증, 제단]"), 47, inv);
		else if(Type==120)
			Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[다음 항목]", 120,0,1,Arrays.asList(ChatColor.BLUE + "현재 항목 : "+ChatColor.WHITE+"제단","",ChatColor.YELLOW+"던전을 생성하기 위해서는",ChatColor.YELLOW+"아래의 3가지 구성물이 존재 해야 합니다.",ChatColor.YELLOW+"[던전, 통행증, 제단]"), 47, inv);
		
		
		if(DungeonList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		if(Type==52)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "던전 제작", 383,0,1,Arrays.asList(ChatColor.GRAY + "새로운 던전을 생성합니다."), 49, inv);
		if(Type==358)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "통행증 제작", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 통행증을 생성합니다."), 49, inv);
		if(Type==120)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "제단 건설", 381,0,1,Arrays.asList(ChatColor.GRAY + "새로운 제단을 생성합니다.","",ChatColor.RED+""+ChatColor.BOLD+"[제단은 무조건 남쪽을 바라봅니다.]"), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void DungeonSetUpGUI(Player player, String DungeonName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
		
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "던전 설정 : " +DungeonName);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "던전 타입", DungeonConfig.getInt("Type.ID"),DungeonConfig.getInt("Type.DATA"),1,Arrays.asList(ChatColor.GRAY + "현재 던전 타입 : "+DungeonConfig.getString("Type.Name")), 11, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "던전 크기", 395,0,1,Arrays.asList(ChatColor.GRAY + "현재 던전 크기 : "+DungeonConfig.getInt("Size"),ChatColor.DARK_GRAY + "최소 : 5",ChatColor.DARK_GRAY + "최대 : 30"), 13, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "던전 길이", 53,0,1,Arrays.asList(ChatColor.GRAY + "현재 미로 레벨 : "+DungeonConfig.getInt("Maze_Level"),"",ChatColor.YELLOW+"[영향 받는 항목]",ChatColor.YELLOW+" - 구슬방 출현 빈도",ChatColor.YELLOW+" - 던전 갈림길 개수",ChatColor.YELLOW+" - 던전 밀집도"), 15, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "던전 제한", 101,0,1,Arrays.asList(ChatColor.GRAY + "던전 입장 제한을 설정합니다.",ChatColor.RED+"레벨 제한 : " + ChatColor.GRAY+DungeonConfig.getInt("District.Level"),ChatColor.RED+"누적 레벨 제한 : " + ChatColor.GRAY+ DungeonConfig.getInt("District.RealLevel")), 20, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "던전 보상", 266,0,1,Arrays.asList(ChatColor.GRAY + "던전 기본 보상을 설정합니다.",ChatColor.YELLOW+"보상 금액 : " + ChatColor.GRAY+DungeonConfig.getInt("Reward.Money"),ChatColor.AQUA+"보상 경험치 : " + ChatColor.GRAY+DungeonConfig.getInt("Reward.EXP")), 22, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "던전 보상 상자", 54,0,1,Arrays.asList(ChatColor.GRAY + "던전 추가 보상을 설정합니다."), 24, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "던전 몬스터", 383,0,1,Arrays.asList(ChatColor.GRAY + "던전 몬스터를 설정합니다."), 29, inv);
		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
		{
			String lore = "";
			int tracknumber = DungeonConfig.getInt("BGM.Normal");
			lore = " %enter%"+ChatColor.GRAY + "던전 BGM을 설정합니다.%enter% %enter%"+ChatColor.BLUE + "[클릭시 노트블록 사운드 설정]%enter% %enter%"+ChatColor.DARK_AQUA+"[트랙] "+ChatColor.BLUE +""+ tracknumber+"%enter%"
			+ChatColor.DARK_AQUA+"[제목] "+ChatColor.BLUE +""+ new OtherPlugins.NoteBlockAPIMain().getTitle(tracknumber)+"%enter%"
			+ChatColor.DARK_AQUA+"[저자] "+ChatColor.BLUE+new OtherPlugins.NoteBlockAPIMain().getAuthor(tracknumber)+"%enter%"+ChatColor.DARK_AQUA+"[설명] ";
			
			String Description = new OtherPlugins.NoteBlockAPIMain().getDescription(tracknumber);
			String lore2="";
			int a = 0;
			for(int count = 0; count <Description.toCharArray().length; count++)
			{
				lore2 = lore2+ChatColor.BLUE+Description.toCharArray()[count];
				a=a+1;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2;
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[던전 배경음]", 2263,0,1,Arrays.asList(lore.split("%enter%")), 31, inv);

			lore = "";
			tracknumber = DungeonConfig.getInt("BGM.BOSS");
			lore = " %enter%"+ChatColor.GRAY + "던전 BGM을 설정합니다.%enter% %enter%"+ChatColor.BLUE + "[클릭시 노트블록 사운드 설정]%enter% %enter%"+ChatColor.DARK_AQUA+"[트랙] "+ChatColor.BLUE +""+ tracknumber+"%enter%"
			+ChatColor.DARK_AQUA+"[제목] "+ChatColor.BLUE +""+ new OtherPlugins.NoteBlockAPIMain().getTitle(tracknumber)+"%enter%"
			+ChatColor.DARK_AQUA+"[저자] "+ChatColor.BLUE+new OtherPlugins.NoteBlockAPIMain().getAuthor(tracknumber)+"%enter%"+ChatColor.DARK_AQUA+"[설명] ";
			
			Description = new OtherPlugins.NoteBlockAPIMain().getDescription(tracknumber);
			lore2="";
			a = 0;
			for(int count = 0; count <Description.toCharArray().length; count++)
			{
				lore2 = lore2+ChatColor.BLUE+Description.toCharArray()[count];
				a=a+1;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2;
			Stack2(ChatColor.WHITE + ""+ChatColor.BOLD+"[보스 배경음]", 2259,0,1,Arrays.asList(lore.split("%enter%")), 33, inv);
		}
		else
		{
			Stack2(ChatColor.RED + ""+ChatColor.BOLD+"[던전 배경음]", 2266,0,1,Arrays.asList("",ChatColor.GRAY + "던전 입장시 테마 음을",ChatColor.GRAY+"재생 시킬 수 있습니다.","",ChatColor.RED + "[     필요 플러그인     ]",ChatColor.RED+" - NoteBlockAPI"), 33, inv);
			Stack2(ChatColor.RED + ""+ChatColor.BOLD+"[보스 배경음]", 2266,0,1,Arrays.asList("",ChatColor.GRAY + "보스방 입장시 테마 음을",ChatColor.GRAY+"재생 시킬 수 있습니다.","",ChatColor.RED + "[     필요 플러그인     ]",ChatColor.RED+" - NoteBlockAPI"), 35, inv);
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 44, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 36, inv);

		player.openInventory(inv);
	}

	public void DungeonChestReward(Player player, String DungeonName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Reward.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "던전 보상 : " +DungeonName);
	
		Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[100% 확률]", 160,11,1,Arrays.asList("",ChatColor.WHITE+"100% 확률로 나올 아이템을",ChatColor.WHITE+"이 줄에 놓으시면 됩니다.",ChatColor.WHITE+"100% 확률로 이 줄에 있는",ChatColor.WHITE+"아이템 중, 하나가 나옵니다.",""), 0, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[90% 확률]", 160,5,1,Arrays.asList("",ChatColor.WHITE+"90% 확률로 나올 아이템을",ChatColor.WHITE+"이 줄에 놓으시면 됩니다.",ChatColor.WHITE+"90% 확률로 이 줄에 있는",ChatColor.WHITE+"아이템 중, 하나가 나옵니다.",""), 9, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[50% 확률]", 160,4,1,Arrays.asList("",ChatColor.WHITE+"50% 확률로 나올 아이템을",ChatColor.WHITE+"이 줄에 놓으시면 됩니다.",ChatColor.WHITE+"50% 확률로 이 줄에 있는",ChatColor.WHITE+"아이템 중, 하나가 나옵니다.",""), 18, inv);
		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[10% 확률]", 160,1,1,Arrays.asList("",ChatColor.WHITE+"10% 확률로 나올 아이템을",ChatColor.WHITE+"이 줄에 놓으시면 됩니다.",ChatColor.WHITE+"10% 확률로 이 줄에 있는",ChatColor.WHITE+"아이템 중, 하나가 나옵니다.",""), 27, inv);
		Stack2(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[1% 확률]", 160,14,1,Arrays.asList("",ChatColor.WHITE+"1% 확률로 나올 아이템을",ChatColor.WHITE+"이 줄에 놓으시면 됩니다.",ChatColor.WHITE+"1% 확률로 이 줄에 있는",ChatColor.WHITE+"아이템 중, 하나가 나옵니다.",""), 36, inv);
		Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[0.1% 확률]", 160,10,1,Arrays.asList("",ChatColor.WHITE+"0.1% 확률로 나올 아이템을",ChatColor.WHITE+"이 줄에 놓으시면 됩니다.",ChatColor.WHITE+"0.1% 확률로 이 줄에 있는",ChatColor.WHITE+"아이템 중, 하나가 나옵니다.",""), 45, inv);

		for(int count = 0; count < 8; count++)
		{
			if(DungeonConfig.getItemStack("100."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("100."+count), count+1, inv);
			if(DungeonConfig.getItemStack("90."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("90."+count), count+10, inv);
			if(DungeonConfig.getItemStack("50."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("50."+count), count+19, inv);
			if(DungeonConfig.getItemStack("10."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("10."+count), count+28, inv);
			if(DungeonConfig.getItemStack("1."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("1."+count), count+37, inv);
			if(DungeonConfig.getItemStack("0."+count)!=null)
				ItemStackStack(DungeonConfig.getItemStack("0."+count), count+46, inv);
		}
		player.openInventory(inv);
	}
	
	public void DungeonMonsterGUIMain(Player player, String DungeonName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "던전 몬스터 : " +DungeonName);

		Stack2(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[BOSS]", 160,14,1,Arrays.asList("",ChatColor.WHITE+"보스방에서 나올 몬스터는",ChatColor.WHITE+"이 줄에서 설정합니다.",""), 0, inv);
		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[부 보스]", 160,5,1,Arrays.asList("",ChatColor.WHITE+"보스방 앞에서 나올 몬스터는",ChatColor.WHITE+"이 줄에서 설정합니다.",""), 9, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[상급 몬스터]", 160,4,1,Arrays.asList("",ChatColor.WHITE+"일반 방에서 나올 매우 강한 몬스터는",ChatColor.WHITE+"이 줄에서 설정합니다.",""), 18, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[중급 몬스터]", 160,5,1,Arrays.asList("",ChatColor.WHITE+"일반 방에서 나올 강한 몬스터는",ChatColor.WHITE+"이 줄에서 설정합니다.",""), 27, inv);
		Stack2(ChatColor.BLUE + "" + ChatColor.BOLD + "[하급 몬스터]", 160,11,1,Arrays.asList("",ChatColor.WHITE+"일반 방에서 나올 일반 몬스터는",ChatColor.WHITE+"이 줄에서 설정합니다.",""), 36, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);

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
				if(DungeonConfig.getString(Type+"."+count)==null)
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[없음]", 383, 0, 1,Arrays.asList("",ChatColor.YELLOW + "[좌 클릭시 등록]"), count+1+(count2*9), inv);
				else
				{
					switch(DungeonConfig.getString(Type+"."+count))
					{
					case "놂좀비":
						Stack2(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[일반 좀비]", 383, 54, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂스켈레톤":
						Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[일반 스켈레톤]", 383, 51, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂크리퍼":
						Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[일반 크리퍼]", 383, 50, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂거미":
						Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[일반 거미]", 383, 52, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂동굴거미":
						Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[일반 동굴 거미]", 383, 59, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂엔더맨":
						Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[일반 엔더맨]", 383, 58, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂슬라임":
						Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[일반 슬라임]", 383, 55, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂마그마큐브":
						Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[일반 마그마 큐브]", 383, 62, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂마녀":
						Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[일반 마녀]", 383, 66, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂좀비피그맨":
						Stack2(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[일반 좀비 피그맨]", 383, 57, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂블레이즈":
						Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[일반 블레이즈]", 383, 61, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂가스트":
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[일반 가스트]", 383, 56, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂가디언":
						Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[일반 수호자]", 383, 68, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂박쥐":
						Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[일반 박쥐]", 383, 65, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂돼지":
						Stack2(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[일반 돼지]", 383, 90, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂양":
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[일반 양]", 383, 91, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂소":
						Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[일반 소]", 383, 92, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂닭":
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[일반 닭]", 383, 93, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;	
					case "놂오징어":
						Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[일반 오징어]", 383, 94, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂늑대":
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[일반 늑대]", 383, 95, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂버섯소":
						Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[일반 버섯소]", 383, 96, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂오셀롯":
						Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[일반 오셀롯]", 383, 98, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂말":
						Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[일반 말]", 383, 100, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂토끼":
						Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[일반 토끼]", 383, 101, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					case "놂주민":
						Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[일반 주민]", 383, 120, 1,Arrays.asList(ChatColor.GRAY+"커스텀 몬스터가 아닌",ChatColor.GRAY+"일반 적인 몬스터입니다.","",ChatColor.YELLOW + "[좌 클릭시 변경]"), count+1+(count2*9), inv);break;
					default:
						YamlManager MonsterList = YC.getNewConfig("Monster/MonsterList.yml");
						String MobName = DungeonConfig.getString(Type+"."+count);
						boolean isExit = false;
						for(int count3=0;count3<MonsterList.getKeys().size();count3++)
						{
							if(MonsterList.getKeys().toArray()[count3].toString().compareTo(MobName)==0)
							{
								String MonsterName = MobName;
								String Lore=null;
								GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
								Lore = "%enter%"+ChatColor.WHITE+""+ChatColor.BOLD+" 이름 : "+ChatColor.WHITE+MonsterList.getString(MonsterName+".Name")+"%enter%";
								Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" 타입 : "+ChatColor.WHITE+MonsterList.getString(MonsterName+".Type")+"%enter%";
								Lore = Lore+ChatColor.WHITE+""+ChatColor.BOLD+" 스폰 바이옴 : "+ChatColor.WHITE+MonsterList.getString(MonsterName+".Biome")+"%enter%";
								Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" 생명력 : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".HP")+"%enter%";
								Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" 경험치 : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".EXP")+"%enter%";
								Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" 드랍 금액 : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".MIN_Money")+" ~ "+MonsterList.getInt(MonsterName+".MAX_Money")+"%enter%";
								Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.STR+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".STR")
								+ChatColor.GRAY+ " [물공 : " + d.CombatMinDamageGet(null, 0, MonsterList.getInt(MonsterName+".STR")) + " ~ " + d.CombatMaxDamageGet(null, 0, MonsterList.getInt(MonsterName+".STR")) + "]%enter%";
								Lore = Lore+ChatColor.GREEN+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.DEX+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".DEX")
								+ChatColor.GRAY+ " [활공 : " + d.returnRangeDamageValue(null, MonsterList.getInt(MonsterName+".DEX"), 0, true) + " ~ " + d.returnRangeDamageValue(null, MonsterList.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
								Lore = Lore+ChatColor.DARK_AQUA+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.INT+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".INT")
								+ChatColor.GRAY+ " [폭공 : " + (MonsterList.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(MonsterList.getInt(MonsterName+".INT")/2.5)+"]%enter%";
								Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.WILL+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".WILL")
								+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MonsterList.getInt(MonsterName+".LUK"), (int)MonsterList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
								Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.LUK+" : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".LUK")
								+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MonsterList.getInt(MonsterName+".LUK"), (int)MonsterList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
								Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" 방어 : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".DEF")+"%enter%";
								Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" 보호 : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".Protect")+"%enter%";
								Lore = Lore+ChatColor.BLUE+""+ChatColor.BOLD+" 마법 방어 : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".Magic_DEF")+"%enter%";
								Lore = Lore+ChatColor.DARK_BLUE+""+ChatColor.BOLD+" 마법 보호 : "+ChatColor.WHITE+MonsterList.getInt(MonsterName+".Magic_Protect")+"%enter%";
								Lore = Lore+"%enter%"+ChatColor.YELLOW+""+ChatColor.BOLD+"[좌 클릭시 변경]";

								String[] scriptA = Lore.split("%enter%");
								for(int counter = 0; counter < scriptA.length; counter++)
									scriptA[counter] =  " "+scriptA[counter];
								int id = 383;
								int data = 0;
								switch(MonsterList.getString(MonsterName+".Type"))
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
								Stack(ChatColor.WHITE+MonsterName, id, data, 1,Arrays.asList(scriptA), count+1+(count2*9), inv);
								ItemMeta a = inv.getItem(count+1+(count2*9)).getItemMeta();
								a.addEnchant(Enchantment.SILK_TOUCH, 3, true);
								inv.getItem(count+1+(count2*9)).setItemMeta(a);
								isExit = true;
								break;
							}
						}
						if(isExit==false)
						{
							DungeonConfig.set(Type+"."+count, null);
							DungeonConfig.saveConfig();
							Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[없음]", 383, 0, 1,Arrays.asList("",ChatColor.YELLOW + "[좌 클릭시 등록]"), count+1+(count2*9), inv);
						}
					}
				}
			}
		}
		player.openInventory(inv);
	}
	
	public void DungeonMonsterChooseMain(Player player, String DungeonName, int slot)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "던전 몬스터 : " +DungeonName);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[없음]", 166,0,1,Arrays.asList(ChatColor.GRAY + "몬스터 설정을 하지 않습니다."), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[일반]", 383,0,1,Arrays.asList(ChatColor.GRAY + "일반적인 몬스터 중 하나로 고릅니다."), 4, inv);
		Stack2(ChatColor.AQUA + "" + ChatColor.BOLD + "[커스텀]", 52,0,1,Arrays.asList(ChatColor.GRAY + "커스텀 몬스터 중 하나로 고릅니다.","",ChatColor.RED+"[엔더 크리스탈 형태의 몬스터를",ChatColor.RED+"선택할 경우, 고장의 원인이 됩니다.]"), 6, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+slot), 8, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		player.openInventory(inv);
	}
	
	public void DungeonSelectNormalMonsterChoose(Player player, String DungeonName, String Type, int slot)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ "일반 몬스터 : " +DungeonName);

		Stack2(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[좀비]",383,54,1,null, 0, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[스켈레톤]",383,51,1,null, 1, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[크리퍼]",383,50,1,null, 2, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[거미]",383,52,1,null, 3, inv);
		Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[동굴 거미]",383,59,1,null, 4, inv);
		Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[엔더맨]",383,58,1,null, 5, inv);
		Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "[슬라임]",383,55,1,null, 6, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[마그마 큐브]",383,62,1,null, 7, inv);
		Stack2(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "[마녀]",383,66,1,null, 8, inv);
		Stack2(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[좀비 피그맨]",383,57,1,null, 9, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[블레이즈]",383,61,1,null, 10, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[가스트]",383,56,1,null, 11, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[수호자]",383,68,1,null, 12, inv);
		Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[박쥐]",383,65,1,null, 13, inv);
		Stack2(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[돼지]",383,90,1,null, 14, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[양]",383,91,1,null, 15, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[소]",383,92,1,null, 16, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[닭]",383,93,1,null, 17, inv);
		Stack2(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[오징어]",383,94,1,null, 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[늑대]",383,95,1,null, 19, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[버섯 소]",383,96,1,null, 20, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[오셀롯]",383,98,1,null, 21, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[말]",383,100,1,null, 22, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[토끼]",383,101,1,null, 23, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[주민]",383,120,1,null, 24, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+slot), 53, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+Type), 45, inv);
		player.openInventory(inv);
	}
	
	public void DungeonSelectCustomMonsterChoose(Player player, String DungeonName, String Type, int slot, int page)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MobList = YC.getNewConfig("Monster/MonsterList.yml");
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +ChatColor.BLACK+ "커스텀 몬스터 : " + (page+1));

		Object[] a= MobList.getKeys().toArray();

		int loc=0;
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
			Lore = Lore+ChatColor.RED+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.STR+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".STR")
			+ChatColor.GRAY+ " [물공 : " + d.CombatMinDamageGet(null, 0, MobList.getInt(MonsterName+".STR")) + " ~ " + d.CombatMaxDamageGet(null, 0, MobList.getInt(MonsterName+".STR")) + "]%enter%";
			Lore = Lore+ChatColor.GREEN+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.DEX+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEX")
			+ChatColor.GRAY+ " [활공 : " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, true) + " ~ " + d.returnRangeDamageValue(null, MobList.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
			Lore = Lore+ChatColor.DARK_AQUA+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.INT+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".INT")
			+ChatColor.GRAY+ " [폭공 : " + (MobList.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(MobList.getInt(MonsterName+".INT")/2.5)+"]%enter%";
			Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.WILL+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".WILL")
			+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+ChatColor.YELLOW+""+ChatColor.BOLD+" "+GoldBigDragon_RPG.Main.ServerOption.LUK+" : "+ChatColor.WHITE+MobList.getInt(MonsterName+".LUK")
			+ChatColor.GRAY+ " [크리 : " + d.getCritical(null,MobList.getInt(MonsterName+".LUK"), (int)MobList.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+ChatColor.GRAY+""+ChatColor.BOLD+" 방어 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".DEF")+"%enter%";
			Lore = Lore+ChatColor.AQUA+""+ChatColor.BOLD+" 보호 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Protect")+"%enter%";
			Lore = Lore+ChatColor.BLUE+""+ChatColor.BOLD+" 마법 방어 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Magic_DEF")+"%enter%";
			Lore = Lore+ChatColor.DARK_BLUE+""+ChatColor.BOLD+" 마법 보호 : "+ChatColor.WHITE+MobList.getInt(MonsterName+".Magic_Protect")+"%enter%";

			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			int id = 383;
			int data = 0;
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
			loc=loc+1;
		}
		
		if(a.length-(page*44)>45)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+Type), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+slot,ChatColor.BLACK+DungeonName), 53, inv);
		player.openInventory(inv);
	}

	public void DungeonMusicSettingGUI(Player player, int page,String DungeonName, boolean isBOSS)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK + "던전 배경음 : " + (page+1));
		int loc=0;
		int model = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 11);
		for(int count = page*45; count < new OtherPlugins.NoteBlockAPIMain().Musics.size();count++)
		{
			if(model<11)
				model=model+1;
			else
				model=0;
			String lore = " %enter%"+ChatColor.DARK_AQUA+"[트랙] "+ChatColor.BLUE +""+ count+"%enter%"
			+ChatColor.DARK_AQUA+"[제목] "+ChatColor.BLUE +""+ new OtherPlugins.NoteBlockAPIMain().getTitle(count)+"%enter%"
			+ChatColor.DARK_AQUA+"[저자] "+ChatColor.BLUE+new OtherPlugins.NoteBlockAPIMain().getAuthor(count)+"%enter%"+ChatColor.DARK_AQUA+"[설명] ";
			String Description = new OtherPlugins.NoteBlockAPIMain().getDescription(count);
			String lore2="";
			int a = 0;
			for(int counter = 0; counter <Description.toCharArray().length; counter++)
			{
				lore2 = lore2+ChatColor.BLUE+Description.toCharArray()[counter];
				a=a+1;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2+"%enter% %enter%"+ChatColor.YELLOW+"[좌 클릭시 배경음 설정]";
			if(count > new OtherPlugins.NoteBlockAPIMain().Musics.size() || loc >= 45) break;
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 2256+model,0,1,Arrays.asList(lore.split("%enter%")), loc, inv);
			
			loc=loc+1;
		}
		
		if(new OtherPlugins.NoteBlockAPIMain().Musics.size()-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+""+isBOSS), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+DungeonName), 53, inv);
		player.openInventory(inv);
	}
	//DungeonGUI//
	
	
	//EnterCardGUI//
	public void EnterCardSetUpGUI(Player player, String EnterCardName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN+""+ChatColor.BOLD +ChatColor.BLACK+"통행증 설정");
		if(DungeonConfig.getString(EnterCardName+".Dungeon")!= null)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[통행증 연결 던전]", 52,0,1,Arrays.asList("",ChatColor.BLUE + "현재 이어진 던전 : "+ChatColor.WHITE+DungeonConfig.getString(EnterCardName+".Dungeon")), 2, inv);
		else
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[통행증 연결 던전]", 166,0,1,Arrays.asList("",ChatColor.BLUE + "현재 이어진 던전 : "+ChatColor.WHITE+"없음"), 2, inv);
			
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[통행증 형태 변경]", DungeonConfig.getInt(EnterCardName+".ID"),DungeonConfig.getInt(EnterCardName+".DATA"),1,Arrays.asList("",ChatColor.BLUE + "현재 아이템 타입 : "+ChatColor.WHITE+DungeonConfig.getInt(EnterCardName+".ID")+":"+ DungeonConfig.getInt(EnterCardName+".DATA"),"",ChatColor.YELLOW+"[F3 + H 입력시 타입 확인 가능]"), 3, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[통행증 형태 초기화]", 325,0,1,Arrays.asList("",ChatColor.WHITE + "통행증 형태가 나타나지 않을 때",ChatColor.WHITE+"누르면 초기화 해 줍니다."), 4, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[통행증 입장 인원]", 397,3,1,Arrays.asList("",ChatColor.BLUE + "입장 가능 인원 : "+ChatColor.WHITE+DungeonConfig.getInt(EnterCardName+".Capacity")+" 명"), 5, inv);

		String Time = null;
		if(DungeonConfig.getInt(EnterCardName.toString()+".Hour")!=-1)
		{
			if(DungeonConfig.getInt(EnterCardName.toString()+".Hour")!=0)
				Time = DungeonConfig.getInt(EnterCardName.toString()+".Hour")+"시간 ";
			if(DungeonConfig.getInt(EnterCardName.toString()+".Min")!=0)
				Time = Time+DungeonConfig.getInt(EnterCardName.toString()+".Min")+"분 ";
			Time = Time+DungeonConfig.getInt(EnterCardName.toString()+".Sec")+"초";
		}
		else
			Time = "무제한";
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[통행증 유효 시간]", 347,0,1,Arrays.asList("",ChatColor.WHITE+Time), 6, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+EnterCardName), 8, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);

		player.openInventory(inv);
	}

	public void EnterCardDungeonSettingGUI(Player player, int page, String EnterCardName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "통행증 연결 : " + (page+1));
		Object[] DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
		
		int loc=0;
		for(int count = page*45; count < DungeonList.length;count++)
		{
			YamlManager DungeonOptionYML = YC.getNewConfig("Dungeon/Dungeon/"+DungeonList[count].toString()+"/Option.yml");
			
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), 52,0,1,Arrays.asList(
			"",ChatColor.BLUE+"던전 형태 : "+ChatColor.WHITE+DungeonOptionYML.getString("Type.Name")
			,ChatColor.BLUE+"던전 크기 : "+ChatColor.WHITE+DungeonOptionYML.getInt("Size")
			,ChatColor.BLUE+"던전 밀집도 : "+ChatColor.WHITE+DungeonOptionYML.getInt("Maze_Level")
			,ChatColor.BLUE+"레벨 제한 : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.Level")+" 이상"
			,ChatColor.BLUE+"누적 레벨 제한 : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.RealLevel")+" 이상"
			,"",ChatColor.BLUE+"[기본 클리어 보상]"
			,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.Money")+" "+GoldBigDragon_RPG.Main.ServerOption.Money
			,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.EXP")+ChatColor.AQUA+" "+ChatColor.BOLD+"EXP"
			,"",ChatColor.YELLOW+"[좌 클릭시 연결]"), loc, inv);
			
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+EnterCardName), 53, inv);
		player.openInventory(inv);
		return;
	}
	//EnterCardGUI//

	//AltarGUI//
	public void AltarShapeListGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "제단 형태");
		Stack2(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[이끼 낀 제단]", 48,0,1,Arrays.asList("",ChatColor.BLUE + "크기 : "+ChatColor.WHITE+"소형",ChatColor.BLUE+"예상 건축 시간 : "+ChatColor.WHITE+"13초","",ChatColor.GOLD+""+ChatColor.BOLD+"[     건축가     ]",ChatColor.WHITE+"GoldBigDragon [모두]"), 0, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "[金泰龍]", 41,0,1,Arrays.asList("",ChatColor.BLUE + "크기 : "+ChatColor.WHITE+"대형",ChatColor.BLUE+"예상 건축 시간 : "+ChatColor.WHITE+"15분","",ChatColor.GOLD+""+ChatColor.BOLD+"[     건축가     ]",ChatColor.WHITE+"ComputerFairy [날개]",ChatColor.WHITE+"GoldBigDragon [용]"), 1, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[스톤 헨지]", 1,0,1,Arrays.asList("",ChatColor.BLUE + "크기 : "+ChatColor.WHITE+"소형",ChatColor.BLUE+"예상 건축 시간 : "+ChatColor.WHITE+"1분 5초","",ChatColor.GOLD+""+ChatColor.BOLD+"[     건축가     ]",ChatColor.WHITE+"GoldBigDragon [모두]"), 2, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[해부대]", 1,5,1,Arrays.asList("",ChatColor.BLUE + "크기 : "+ChatColor.WHITE+"소형",ChatColor.BLUE+"예상 건축 시간 : "+ChatColor.WHITE+"8초","",ChatColor.GOLD+""+ChatColor.BOLD+"[     건축가     ]",ChatColor.WHITE+"GoldBigDragon [모두]"), 3, inv);
		Stack2(ChatColor.GRAY + "" + ChatColor.BOLD + "[테스트용 제단]", 48,0,1,null, 44, inv);
		
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}

	public void AltarSettingGUI(Player player, String AltarName)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "제단 설정");

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AltarList = YC.getNewConfig("Dungeon/AltarList.yml");
		YamlManager AltarConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
		Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[이름 변경]", 421,0,1,Arrays.asList(ChatColor.GRAY+"제단의 이름을 변경합니다.","",ChatColor.BLUE+"[현재 제단 이름]",ChatColor.WHITE+AltarList.getString(AltarName+".Name")), 2, inv);
		if(AltarConfig.getString("NormalDungeon")==null)
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[일반 던전]", 166,0,1,Arrays.asList(ChatColor.GRAY+"이 제단에 통행증을 제외한",ChatColor.GRAY+"아이템을 바쳤을 경우 생성되는",ChatColor.GRAY+"일반 던전을 설정합니다.","",ChatColor.BLUE+"[현재 설정된 일반 던전]",ChatColor.WHITE+"설정되지 않음"), 4, inv);
		else
		{
			YamlManager DungeonList = YC.getNewConfig("Dungeon/DungeonList.yml");
			if(DungeonList.contains(AltarConfig.getString("NormalDungeon")))
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[일반 던전]", 52,0,1,Arrays.asList(ChatColor.GRAY+"제단에 통행증을 제외한",ChatColor.GRAY+"아이템을 바쳤을 경우 생성되는",ChatColor.GRAY+"일반 던전을 설정합니다.","",ChatColor.BLUE+"[현재 설정된 일반 던전]",ChatColor.WHITE+AltarConfig.getString("NormalDungeon")), 4, inv);
			else
			{
				AltarConfig.set("NormalDungeon", null);
				AltarConfig.saveConfig();
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[일반 던전]", 166,0,1,Arrays.asList(ChatColor.GRAY+"제단에 통행증을 제외한",ChatColor.GRAY+"아이템을 바쳤을 경우 생성되는",ChatColor.GRAY+"일반 던전을 설정합니다.","",ChatColor.BLUE+"[현재 설정된 일반 던전]",ChatColor.WHITE+"설정되지 않음"), 4, inv);
			}
		}
		Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[통행증 등록]", 358,0,1,Arrays.asList(ChatColor.GRAY+"제단에서 사용 가능한",ChatColor.GRAY+"통행증을 등록합니다.","",ChatColor.YELLOW+"[좌 클릭시 통행증 등록]"), 6, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+AltarName), 8, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);

		player.openInventory(inv);
		return;
	}
	
	public void AltarDungeonSettingGUI(Player player, int page, String AltarName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "제단 연결 : " + (page+1));
		Object[] DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
		
		int loc=0;
		for(int count = page*45; count < DungeonList.length;count++)
		{
			YamlManager DungeonOptionYML = YC.getNewConfig("Dungeon/Dungeon/"+DungeonList[count].toString()+"/Option.yml");
			
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), 52,0,1,Arrays.asList(
			"",ChatColor.BLUE+"던전 형태 : "+ChatColor.WHITE+DungeonOptionYML.getString("Type.Name")
			,ChatColor.BLUE+"던전 크기 : "+ChatColor.WHITE+DungeonOptionYML.getInt("Size")
			,ChatColor.BLUE+"던전 밀집도 : "+ChatColor.WHITE+DungeonOptionYML.getInt("Maze_Level")
			,ChatColor.BLUE+"레벨 제한 : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.Level")+" 이상"
			,ChatColor.BLUE+"누적 레벨 제한 : "+ChatColor.WHITE+DungeonOptionYML.getInt("District.RealLevel")+" 이상"
			,"",ChatColor.BLUE+"[기본 클리어 보상]"
			,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.Money")+" "+GoldBigDragon_RPG.Main.ServerOption.Money
			,ChatColor.BLUE+" - "+ChatColor.WHITE+DungeonOptionYML.getInt("Reward.EXP")+ChatColor.AQUA+" "+ChatColor.BOLD+"EXP"
			,"",ChatColor.YELLOW+"[좌 클릭시 연결]"), loc, inv);
			
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarEnterCardSettingGUI(Player player, int page, String AltarName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager AlterConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "등록된 통행증 : " + (page+1));

		if(AlterConfig.getConfigurationSection("EnterCard").getKeys(false).size()!=0)
		{
			Object[] EnterCardList = AlterConfig.getConfigurationSection("EnterCard").getKeys(false).toArray();

			int loc=0;
			for(int count = page*45; count < EnterCardList.length;count++)
			{
				YamlManager EnterCard = YC.getNewConfig("Dungeon/EnterCardList.yml");
				if(EnterCard.contains(EnterCardList[count].toString()))
				{
					if(EnterCard.getString(EnterCardList[count].toString()+".Dungeon")==null)
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + EnterCardList[count].toString(), EnterCard.getInt(EnterCardList[count].toString()+".ID"),EnterCard.getInt(EnterCardList[count].toString()+".DATA"),1,Arrays.asList(
								"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+"없음",
								ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+EnterCard.getInt(EnterCardList[count].toString()+".Capacity"),
								"",ChatColor.RED+"[Shift + 우클릭시 등록 해제]"), loc, inv);
					else
					{
						YamlManager Dungeon = YC.getNewConfig("Dungeon/DungeonList.yml");
						if(Dungeon.contains(EnterCard.getString(EnterCardList[count].toString()+".Dungeon")))
						{
							Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + EnterCardList[count].toString(), EnterCard.getInt(EnterCardList[count].toString()+".ID"),EnterCard.getInt(EnterCardList[count].toString()+".DATA"),1,Arrays.asList(
							"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+""+EnterCard.getString(EnterCardList[count].toString()+".Dungeon"),
							ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+EnterCard.getInt(EnterCardList[count].toString()+".Capacity"),
							"",ChatColor.RED+"[Shift + 우클릭시 등록 해제]"), loc, inv);
						}
						else
						{
							EnterCard.set(EnterCardList[count].toString()+".Dungeon",null);
							EnterCard.saveConfig();
							Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + EnterCardList[count].toString(), EnterCard.getInt(EnterCardList[count].toString()+".ID"),EnterCard.getInt(EnterCardList[count].toString()+".DATA"),1,Arrays.asList(
									"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+"없음",
									ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+EnterCard.getInt(EnterCardList[count].toString()+".Capacity"),
									"",ChatColor.RED+"[Shift + 우클릭시 등록 해제]"), loc, inv);
						}
							
					}
					loc=loc+1;
				}
				else
				{
					AlterConfig.removeKey("EnterCard."+EnterCardList[count].toString());
					AlterConfig.saveConfig();
				}
			}
			if(EnterCardList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "통행증 등록", 386,0,1,Arrays.asList(ChatColor.GRAY + "제단에 통행증을 등록합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarEnterCardListGUI(Player player, int page, String AltarName)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "생성된 통행증 목록 : " + (page+1));
		
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
		Object[] DungeonList = DungeonConfig.getConfigurationSection("").getKeys(false).toArray();
		int loc=0;
		for(int count = page*45; count < DungeonList.length;count++)
		{
			if(DungeonConfig.getString(DungeonList[count].toString()+".Dungeon")==null)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
				"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+"없음",
				ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
				"",ChatColor.YELLOW+"[좌 클릭시 통행증 등록]"), loc, inv);
			else
			{
				YamlManager Dungeon = YC.getNewConfig("Dungeon/DungeonList.yml");
				if(Dungeon.contains(DungeonConfig.getString(DungeonList[count].toString()+".Dungeon")))
				{
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
					"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+""+DungeonConfig.getString(DungeonList[count].toString()+".Dungeon"),
					ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
					"",ChatColor.YELLOW+"[좌 클릭시 통행증 등록]"), loc, inv);
				}
				else
				{
					DungeonConfig.set(DungeonList[count].toString()+".Dungeon",null);
					DungeonConfig.saveConfig();
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + DungeonList[count].toString(), DungeonConfig.getInt(DungeonList[count].toString()+".ID"),DungeonConfig.getInt(DungeonList[count].toString()+".DATA"),1,Arrays.asList(
					"",ChatColor.BLUE+"연결된 던전 : "+ChatColor.WHITE+"없음",
					ChatColor.BLUE+"입장 가능 인원 : "+ChatColor.WHITE+""+DungeonConfig.getInt(DungeonList[count].toString()+".Capacity"),
					"",ChatColor.YELLOW+"[좌 클릭시 통행증 등록]"), loc, inv);
				}
			}
			loc=loc+1;
		}
		
		if(DungeonList.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",AltarName), 53, inv);
		player.openInventory(inv);
		return;
	}
	
	public void AltarUseGUI(Player player, String AltarName)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "제단에 물건을 바치면 던전으로 이동합니다");

		Stack2(AltarName, 160,0,1,null, 0, inv);
		Stack2(AltarName, 160,0,1,null, 1, inv);
		Stack2(AltarName, 160,0,1,null, 2, inv);
		Stack2(AltarName, 160,0,1,null, 3, inv);
		Stack2(AltarName, 160,0,1,null, 5, inv);
		Stack2(AltarName, 160,0,1,null, 6, inv);
		Stack2(AltarName, 160,0,1,null, 7, inv);
		Stack2(AltarName, 160,0,1,null, 8, inv);
		player.openInventory(inv);
		return;
	}
	//AltarGUI//
	
	public void DungeonEXIT(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN +""+ChatColor.BOLD +""+ChatColor.BLACK+ "던전에서 나가시겠습니까?");

		Stack2(ChatColor.RED+""+ChatColor.BOLD+"[던전 잔류]", 166,0,1,null, 3, inv);
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD+"[던전 퇴장]", 138,0,1,null, 5, inv);
		player.openInventory(inv);
		return;
	}
	
	
	

	//DungeonGUI Click//
	public void DungeonListMainGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		int Type = event.getInventory().getItem(47).getTypeId();
		
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
				switch (event.getSlot())
				{
					case 45://이전 목록
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						new OPBoxGUI().OPBoxGUI_Main(player, (byte) 3);
						return;
					case 53://나가기
						s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
						player.closeInventory();
						return;
					case 47://타입 변경
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
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
						return;
					case 48://이전 페이지
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						DungeonListMainGUI(player, page-1,Type);
						return;
					case 49://새 던전
						if(GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.isEmpty())
						{
							s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED+"[SYSTEM] : 생성 가능한 던전 테마를 찾을 수 없습니다!");
							player.sendMessage(ChatColor.YELLOW+"(던전 테마 다운로드 : "+ChatColor.GOLD+"http://cafe.naver.com/goldbigdragon/56713"+ChatColor.YELLOW+")");
							return;
						}
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						Object_UserData u = new Object_UserData();
						u.setTemp(player, "Dungeon");
						player.closeInventory();
						if(Type == 52)
						{
							u.setType(player, "DungeonMain");
							u.setString(player, (byte)0, "ND");//NewDungeon
							player.sendMessage(ChatColor.GREEN+"[던전] : 새로운 던전 이름을 입력 해 주세요!");
						}
						else if(Type == 358)
						{
							u.setType(player, "EnterCard");
							u.setString(player, (byte)0, "NEC");//NewEnterCard
							player.sendMessage(ChatColor.GREEN+"[던전] : 새로운 통행증 이름을 입력 해 주세요!");
						}
						else if(Type == 120)
						{
							u.setType(player, "Altar");
							u.setString(player, (byte)0, "NA_Q");//NewAlter_Question
							player.sendMessage(ChatColor.GREEN+"[던전] : 현재 서 있는 위치에 제단을 세우시겠습니까? (네/아니오)");
						}
						return;
					case 50://다음 페이지
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						DungeonListMainGUI(player, page+1,Type);
						return;
					default :
						if(event.getCurrentItem()==null)
							return;
						if(event.getCurrentItem().hasItemMeta()==false)
							return;
			
						if(Type == 52)
						{
							String DungeonName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
							if(event.isLeftClick() == true)
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
								DungeonSetUpGUI(player, DungeonName);
							}
							else if(event.isShiftClick() == true && event.isRightClick() == true)
							{
								s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");
								DungeonConfig.removeKey(DungeonName);
								DungeonConfig.saveConfig();
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
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
								new GoldBigDragon_RPG.Dungeon.DungeonCreater().CreateTestSeed(player, DungeonConfig.getInt("Size"), DungeonConfig.getInt("Maze_Level"), DungeonConfig.getString("Type.Name"));
							}
						}
						if(Type == 358)
						{
							String DungeonName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
							if(event.isLeftClick() == true&&event.isShiftClick()==false)
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
								EnterCardSetUpGUI(player, DungeonName);
							}
							else if(event.isShiftClick()&&event.isRightClick())
							{
								s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
								DungeonConfig.removeKey(DungeonName);
								DungeonConfig.saveConfig();
								DungeonListMainGUI(player, page,Type);
							}
							else if(event.isShiftClick()&& event.isLeftClick())
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
								ItemStack Icon = new MaterialData(DungeonConfig.getInt(DungeonName+".ID"), (byte) DungeonConfig.getInt(DungeonName+".DATA")).toItemStack(1);
								ItemMeta Icon_Meta = Icon.getItemMeta();
								Icon_Meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"[던전 통행증]");
								Calendar Today = Calendar.getInstance();
								String UseableTime = "[제한 시간 없음]";
								if(DungeonConfig.getInt(DungeonName+".Hour")!=-1)
								{
									Today.add(Calendar.MONTH, 1);
									Today.add(Calendar.HOUR, DungeonConfig.getInt(DungeonName+".Hour"));
									Today.add(Calendar.MINUTE, DungeonConfig.getInt(DungeonName+".Min"));
									Today.add(Calendar.SECOND, DungeonConfig.getInt(DungeonName+".Sec"));
									
									UseableTime = Today.get(Calendar.YEAR)+"년 "+Today.get(Calendar.MONTH)+"월 "+Today.get(Calendar.DATE)+"일 "+Today.get(Calendar.HOUR)+"시 "+Today.get(Calendar.MINUTE)+"분 "+Today.get(Calendar.SECOND)+"초 까지";
								}
								Icon_Meta.setLore(Arrays.asList("",ChatColor.RED+DungeonName,"",ChatColor.RED+"인원 : "+ChatColor.WHITE+DungeonConfig.getInt(DungeonName+".Capacity"),"",ChatColor.WHITE+""+UseableTime));
								Icon.setItemMeta(Icon_Meta);
								player.getInventory().addItem(Icon);
							}
						}
						if(Type == 120)
						{
							String DungeonName = event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-1);
							if(event.isLeftClick() == true&&event.isShiftClick()==false)
							{
								s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
								AltarSettingGUI(player, DungeonName);
							}
							else if(event.isShiftClick() == true && event.isRightClick() == true)
							{
								s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/AltarList.yml");
								Location loc = new Location(Bukkit.getServer().getWorld(DungeonConfig.getString(DungeonName+".World")), DungeonConfig.getInt(DungeonName+".X"), DungeonConfig.getInt(DungeonName+".Y"), DungeonConfig.getInt(DungeonName+".Z"));
								int radius = DungeonConfig.getInt(DungeonName+".radius");
								Object[] EntitiList = Bukkit.getServer().getWorld(DungeonConfig.getString(DungeonName+".World")).getNearbyEntities(loc, radius, radius, radius).toArray();
								for(int count=0; count<EntitiList.length;count++)
									if(((Entity)EntitiList[count]).getCustomName()!=null)
										if(((Entity)EntitiList[count]).getCustomName().compareTo(DungeonName)==0)
											((Entity)EntitiList[count]).remove();
								DungeonConfig.removeKey(DungeonName);
								DungeonConfig.saveConfig();
								File file = new File("plugins/GoldBigDragonRPG/Dungeon/Altar/"+DungeonName+".yml");
								file.delete();
								DungeonListMainGUI(player, page,Type);
							}
							else if(event.isShiftClick() == true && event.isLeftClick() == true)
							{
								s.SP(player, Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
							  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
								YamlManager DungeonConfig = YC.getNewConfig("Dungeon/AltarList.yml");
								Location loc = new Location(Bukkit.getServer().getWorld(DungeonConfig.getString(DungeonName+".World")), DungeonConfig.getInt(DungeonName+".X"), DungeonConfig.getInt(DungeonName+".Y"), DungeonConfig.getInt(DungeonName+".Z"));
								player.teleport(loc);
								s.SP(player, Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
							}
						}
						return;
				}
	}
	
	public void DungeonSetUpGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
		
		switch (event.getSlot())
		{
		case 36://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonListMainGUI(player, 0, 52);
			return;
		case 44://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 11://던전 타입
			{
				if(GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.isEmpty())
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[SYSTEM] : 던전 테마를 찾을 수 없습니다!");
					player.sendMessage(ChatColor.YELLOW+"(던전 테마 다운로드 : "+ChatColor.GOLD+"http://cafe.naver.com/goldbigdragon/56713"+ChatColor.YELLOW+")");
					return;
				}
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
				String DungeonTheme = DungeonConfig.getString("Type.Name");
				if(GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.contains(DungeonTheme)==false)
					DungeonConfig.set("Type.Name", GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(0));
				else
				{
					for(int count = 0; count < GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.size(); count++)
						if(GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(count).compareTo(DungeonTheme)==0)
						{
							if(count+1 >= GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.size())
								DungeonConfig.set("Type.Name", GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(0));
							else
								DungeonConfig.set("Type.Name", GoldBigDragon_RPG.Main.ServerOption.DungeonTheme.get(count+1));
						}
				}
				DungeonConfig.saveConfig();
				DungeonSetUpGUI(player, DungeonName);
			}
			return;
		case 13://던전 크기
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			u.setType(player, "DungeonMain");
			u.setString(player, (byte)0, "DS");//DungeonSize
			u.setString(player, (byte)1, DungeonName);
			player.sendMessage(ChatColor.GREEN+"[던전] : 던전 크기를 입력 해 주세요! (최소 5 최대 50)");
			player.closeInventory();
			return;
		case 15://던전 미로 레벨
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			u.setType(player, "DungeonMain");
			u.setString(player, (byte)0, "DML");//DungeonMazeLevel
			u.setString(player, (byte)1, DungeonName);
			player.sendMessage(ChatColor.GREEN+"[던전] : 던전 미로 레벨을 입력 해 주세요! (최소 0 최대 10)");
			player.sendMessage(ChatColor.YELLOW+"[주의] 미로 레벨이 높아지면 유저들이 빡칠수도 있음!");
			player.closeInventory();
			return;
		case 20://레벨 제한
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			u.setType(player, "DungeonMain");
			u.setString(player, (byte)0, "DDL");//DungeonDistrictLevel
			u.setString(player, (byte)1, DungeonName);
			player.sendMessage(ChatColor.GREEN+"[던전] : 던전 입장 가능 레벨을 입력 해 주세요!");
			player.closeInventory();
			return;
		case 22://돈, 경험치 보상 설정
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			u.setType(player, "DungeonMain");
			u.setString(player, (byte)0, "DRM");//DungeonRewardMoney
			u.setString(player, (byte)1, DungeonName);
			player.sendMessage(ChatColor.GREEN+"[던전] : 던전 클리어 보상금을 입력 해 주세요!");
			player.closeInventory();
			return;
		case 24://보상 상자
			s.SP(player, Sound.HORSE_ARMOR, 0.8F, 1.8F);
			DungeonChestReward(player, DungeonName);
			return;
		case 29://몬스터
			s.SP(player, Sound.WOLF_BARK, 0.8F, 1.0F);
			DungeonMonsterGUIMain(player, DungeonName);
			return;
		case 31://던전BGM 설정
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMusicSettingGUI(player, 0, DungeonName, false);
			return;
		case 33://보스BGM 설정
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMusicSettingGUI(player, 0, DungeonName, true);
			return;
		}
	}

	public void DungeonChestRewardClick(InventoryClickEvent event)
	{
		if(event.getClickedInventory().getName().contains("보상"))
			if(event.getSlot()%9==0)
				event.setCancelled(true);
	}
	
	public void DungeonMonsterGUIMainClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);

		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSetUpGUI(player, DungeonName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		if(event.getSlot()%9==0)
			event.setCancelled(true);
		else
		{
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMonsterChooseMain(player, DungeonName, event.getSlot());
		}
		return;
	}
	
	public void DungeonMonsterChooseMainClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
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
		switch (event.getSlot())
		{
		case 0://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMonsterGUIMain(player, DungeonName);
			return;
		case 2://없음
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
			DungeonConfig.set(Type+"."+Slot+".1", null);
			DungeonConfig.saveConfig();
			DungeonMonsterGUIMain(player, DungeonName);
			return;
		case 4://일반 몬스터
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSelectNormalMonsterChoose(player, DungeonName, Type, Slot);
			return;
		case 6://커스텀 몬스터
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, 0);
			return;
		case 8://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void DungeonSelectNormalMonsterChooseClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);
		int Slot = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String Type = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()==383)
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
				switch(event.getCurrentItem().getData().getData())
				{
				case 50://Creeper
					DungeonConfig.set(Type+"."+Slot, "놂크리퍼"); break;
				case 51://Skeleton
					DungeonConfig.set(Type+"."+Slot, "놂스켈레톤"); break;
				case 52://Spider
					DungeonConfig.set(Type+"."+Slot, "놂거미"); break;
				case 54://Zombie
					DungeonConfig.set(Type+"."+Slot, "놂좀비"); break;
				case 55://Slime
					DungeonConfig.set(Type+"."+Slot, "놂슬라임"); break;
				case 56://Ghast
					DungeonConfig.set(Type+"."+Slot, "놂가스트"); break;
				case 57://Witch
					DungeonConfig.set(Type+"."+Slot, "놂좀비피그맨"); break;
				case 58://Enderman
					DungeonConfig.set(Type+"."+Slot, "놂엔더맨"); break;
				case 59://CaveSpider
					DungeonConfig.set(Type+"."+Slot, "놂동굴거미"); break;
				case 61://Blaze
					DungeonConfig.set(Type+"."+Slot, "놂블레이즈"); break;
				case 62://MagmaCube
					DungeonConfig.set(Type+"."+Slot, "놂마그마큐브"); break;
				case 65://Bat
					DungeonConfig.set(Type+"."+Slot, "놂박쥐"); break;
				case 66://Witch
					DungeonConfig.set(Type+"."+Slot, "놂마녀"); break;
				case 68://Guardian
					DungeonConfig.set(Type+"."+Slot, "놂가디언"); break;

				case 90://Pig
					DungeonConfig.set(Type+"."+Slot, "놂돼지"); break;
				case 91://Sheep
					DungeonConfig.set(Type+"."+Slot, "놂양"); break;
				case 92://Cow
					DungeonConfig.set(Type+"."+Slot, "놂소"); break;
				case 93://Chicken
					DungeonConfig.set(Type+"."+Slot, "놂닭"); break;
				case 94://Squid
					DungeonConfig.set(Type+"."+Slot, "놂오징어"); break;
				case 95://Wolf
					DungeonConfig.set(Type+"."+Slot, "놂늑대"); break;
				case 96://MushroomCow
					DungeonConfig.set(Type+"."+Slot, "놂버섯소"); break;
				case 98://Ocelot
					DungeonConfig.set(Type+"."+Slot, "놂오셀롯"); break;
				case 100://Horse
					DungeonConfig.set(Type+"."+Slot, "놂말"); break;
				case 101://Rabbit
					DungeonConfig.set(Type+"."+Slot, "놂토끼"); break;
				case 120://Villager
					DungeonConfig.set(Type+"."+Slot, "놂주민"); break;
				}
				DungeonConfig.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
				DungeonMonsterGUIMain(player, DungeonName);
				return;
			}
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMonsterChooseMain(player, DungeonName, Slot);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void DungeonSelectCustomMonsterChooseClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		String DungeonName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(2));
		int Slot = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String Type = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()==383)
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
				DungeonConfig.set(Type+"."+Slot, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				DungeonConfig.saveConfig();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
				DungeonMonsterGUIMain(player, DungeonName);
				return;
			}
		switch (event.getSlot())
		{
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, page-1);
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSelectCustomMonsterChoose(player, DungeonName, Type, Slot, page+1);
			return;
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMonsterChooseMain(player, DungeonName, Slot);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void DungeonMusicSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String DungeonName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		boolean isBoss = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonSetUpGUI(player, DungeonName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMusicSettingGUI(player, page-1,DungeonName,isBoss);
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonMusicSettingGUI(player, page+1,DungeonName,isBoss);
			return;
		default :
			if(event.isLeftClick())
			{
				if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta())
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
					if(isBoss)
						DungeonConfig.set("BGM.BOSS", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					else
						DungeonConfig.set("BGM.Normal", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					DungeonConfig.saveConfig();
					DungeonSetUpGUI(player, DungeonName);
				}
			}
			return;
		}
	}
	//DungeonGUI Click//

	
	//EnterCardGUI Click//
	public void EnterCardSetUpGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String EnterCardName = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1));

		Object_UserData u = new Object_UserData();
		switch (event.getSlot())
		{
		case 0://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			DungeonListMainGUI(player, 0, 358);
			return;
		case 2://던전 설정
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");
				if(DungeonConfig.getKeys().size()==0)
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[던전] : 생성된 던전이 없습니다! 던전을 먼저 만들고 오세요!");
				}
				else
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
					EnterCardDungeonSettingGUI(player, 0, EnterCardName);
				}
			}
			return;
		case 3://아이템 형태 설정
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			player.closeInventory();
			u.setType(player, "EnterCard");
			u.setString(player, (byte)0, "ECID");//EnterCardID
			u.setString(player, (byte)1, EnterCardName);
			player.sendMessage(ChatColor.GREEN+"[통행증] : 통행증 아이템 타입 ID를 입력 해 주세요.");
			return;
		case 4://아이템 형태 초기화
			{
				s.SP(player, Sound.IRONGOLEM_THROW, 1.0F, 1.8F);
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
				DungeonConfig.set(EnterCardName+".ID",358);
				DungeonConfig.set(EnterCardName+".DATA",0);
				DungeonConfig.saveConfig();
				EnterCardSetUpGUI(player, EnterCardName);
			}
			return;
		case 5://입장 인원 설정
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			player.closeInventory();
			u.setType(player, "EnterCard");
			u.setString(player, (byte)0, "ECC");//EnterCardCapacity
			u.setString(player, (byte)1, EnterCardName);
			player.sendMessage(ChatColor.GREEN+"[통행증] : 필요 입장 인원 수를 입력 해 주세요.");
			return;
		case 6://유효시간 설정
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			u.setTemp(player, "Dungeon");
			player.closeInventory();
			u.setType(player, "EnterCard");
			u.setString(player, (byte)0, "ECUH");//EnterCardUseableHour
			u.setString(player, (byte)1, EnterCardName);
			player.sendMessage(ChatColor.GREEN+"[통행증] : 유효 시간을 입력 해 주세요. (최대 24시간, -1입력시 무제한)");
			return;
		case 8://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void EnterCardDungeonSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String EnterCardName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 45://이전 목록
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					EnterCardSetUpGUI(player, EnterCardName);
					return;
				case 53://나가기
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager DungeonConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
					DungeonConfig.set(EnterCardName+".Dungeon", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					DungeonConfig.saveConfig();
					EnterCardSetUpGUI(player, EnterCardName);
					return;
				}
			}
	}
	//EnterCardGUI Click//

	
	//AltarGUI Click//
	public void AltarShapeListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()!=0)
			{
				switch (event.getSlot())
				{
				case 45://이전 목록
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					DungeonListMainGUI(player, 0, 120);
					return;
				case 53://나가기
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					player.closeInventory();
					if(ServerTickMain.ServerTask.compareTo("null")!=0)
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[Server] : 현재 서버는 "+ChatColor.YELLOW+ServerTickMain.ServerTask+ChatColor.RED+" 작업 중입니다.");
						return;
					}
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager AltarList = YC.getNewConfig("Dungeon/AltarList.yml");
					String Code = ChatColor.BLACK+""+ChatColor.BOLD;
					Code = Code+ChatColor.WHITE+"[제단]";
					String Salt = Code;
					int ID = 1;
					int DATA = 0;
					String Type = null;
					int radius = 5;
					switch(event.getSlot())
					{
					case 0:
						Type = "MossyAltar";
						ID = 48;
						radius = 3;
						break;
					case 1:
						Type = "GoldBigDragon";
						ID = 41;
						radius = 20;
						break;
					case 2:
						Type = "StoneHenge";
						ID = 1;
						radius = 8;
						break;
					case 3:
						Type = "AnatomicalBoard";
						ID = 1;
						DATA = 5;
						radius = 3;
						break;
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
					AltarList = YC.getNewConfig("Dungeon/Altar/"+Salt+".yml");
					AltarList.set("EnterCard.1", null);
					AltarList.saveConfig();
					new GoldBigDragon_RPG.Structure.StructureMain().CreateSturcture(player, Salt, (short) (101+event.getSlot()), 4);
					return;
				}
			}
	}

	public void AltarSettingGUIClick(InventoryClickEvent event)
	{
		String AltarName = event.getInventory().getItem(8).getItemMeta().getLore().get(1);
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getTypeId()!=0)
			{
				switch (event.getSlot())
				{
				case 0://이전 목록
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					DungeonListMainGUI(player, 0, 120);
					return;
				case 2://이름 변경
					{
						Object_UserData u = new Object_UserData();
						s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
						u.setTemp(player, "Dungeon");
						player.closeInventory();
						u.setType(player, "Altar");
						u.setString(player, (byte)0, "EAN");//EditAltarName
						u.setString(player, (byte)1, AltarName);
						player.sendMessage(ChatColor.GREEN+"[제단] : 제단 이름을 입력 해 주세요.");
					}
					return;
				case 4://일반 던전 설정
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					AltarDungeonSettingGUI(player, 0, AltarName);
					return;
				case 6://통행증 설정
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					AltarEnterCardSettingGUI(player, 0, AltarName.substring(2, AltarName.length()));
					return;
				case 8://나가기
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				}
			}
	}
	
	public void AltarUseGUIClick(InventoryClickEvent event)
	{
		if(event.getSlot()!=4)
			if(event.getCurrentItem()!=null)
				if(event.getCurrentItem().getTypeId()!=0)
					if(ChatColor.stripColor(event.getClickedInventory().getName()).compareTo("제단에 물건을 바치면 던전으로 이동합니다")==0)
							event.setCancelled(true);
	}
	
	public void AltarDungeonSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1).substring(2, event.getInventory().getItem(53).getItemMeta().getLore().get(1).length());

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 45://이전 목록
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarSettingGUI(player, AltarName);
					return;
				case 53://나가기
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
					DungeonConfig.set("NormalDungeon", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					DungeonConfig.saveConfig();
					AltarSettingGUI(player, AltarName);
					return;
				}
			}
	}
	
	public void AltarEnterCardSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1);

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 45://이전 목록
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarSettingGUI(player, AltarName);
					return;
				case 48://이전 페이지
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardSettingGUI(player, page-1, AltarName);
					return;
				case 49://통행증 등록
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardListGUI(player, page, AltarName);
					return;
				case 50://다음 페이지
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardSettingGUI(player, page+1, AltarName);
					return;
				case 53://나가기
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					if(event.isShiftClick()&&event.isRightClick())
					{
						s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
					  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
						YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
						DungeonConfig.removeKey("EnterCard."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						DungeonConfig.saveConfig();
						AltarEnterCardSettingGUI(player, page, AltarName);
						return;
					}
				}
			}
	}
	
	public void AltarEnterCardListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String AltarName = event.getInventory().getItem(53).getItemMeta().getLore().get(1);

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 45://이전 목록
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardSettingGUI(player, 0, AltarName);
					return;
				case 48://이전 페이지
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardListGUI(player, page-1, AltarName);
					return;
				case 50://다음 페이지
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					AltarEnterCardListGUI(player, page+1, AltarName);
					return;
				case 53://나가기
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				default:
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
					DungeonConfig.set("EnterCard."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+".1",null);
					DungeonConfig.saveConfig();
					AltarEnterCardSettingGUI(player, page, AltarName);
					return;
				}
			}
	}
	
	public String getRandomCode()
	{
		int randomNum = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 15);
		switch(randomNum)
		{
			case 0:
				return ChatColor.BLACK+"";
			case 1:
				return ChatColor.DARK_BLUE+"";
			case 2:
				return ChatColor.DARK_GREEN+"";
			case 3:
				return ChatColor.DARK_AQUA+"";
			case 4:
				return ChatColor.DARK_RED+"";
			case 5:
				return ChatColor.DARK_PURPLE+"";
			case 6:
				return ChatColor.GOLD+"";
			case 7:
				return ChatColor.GRAY+"";
			case 8:
				return ChatColor.DARK_GRAY+"";
			case 9:
				return ChatColor.BLUE+"";
			case 10:
				return ChatColor.GREEN+"";
			case 11:
				return ChatColor.AQUA+"";
			case 12:
				return ChatColor.RED+"";
			case 13:
				return ChatColor.LIGHT_PURPLE+"";
			case 14:
				return ChatColor.YELLOW+"";
			case 15:
				return ChatColor.WHITE+"";
		}
		return ChatColor.BLACK+"";
	}
	//AltarGUI Click//
	
	public void DungeonEXITClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		if(event.getCurrentItem()!=null)
			if(event.getCurrentItem().getType()!=Material.AIR)
			{
				switch (event.getSlot())
				{
				case 3://던전 잔류
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				case 5://던전 퇴장
					new GoldBigDragon_RPG.Dungeon.DungeonWork().EraseAllDungeonKey(player, true);
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					String DungeonName = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter();
					long UTC = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC();
					YamlManager PlayerConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Entered/"+UTC+".yml");
					if(PlayerConfig.contains("EnteredAlter"))
					{
						DungeonName = PlayerConfig.getString("EnteredAlter");
						PlayerConfig = YC.getNewConfig("Dungeon/AltarList.yml");
						if(PlayerConfig.contains(DungeonName))
						{
							Location loc = new Location(Bukkit.getServer().getWorld(PlayerConfig.getString(DungeonName+".World")), PlayerConfig.getLong(DungeonName+".X"), PlayerConfig.getLong(DungeonName+".Y")+1, PlayerConfig.getLong(DungeonName+".Z"));
							player.teleport(loc);
							return;
						}
					}
					new GoldBigDragon_RPG.Util.PlayerUtil().teleportToCurrentArea(player, true);
					return;
				}
			}
	}
	
	//DungeonGUI Close//
	public void DungeonChestRewardClose(InventoryCloseEvent event)
	{
		String DungeonName = ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]);

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Reward.yml");
		
		for(int count = 0; count < 8; count++)
		{
			if(event.getInventory().getItem(count+1)!=null)
				DungeonConfig.set("100."+count, event.getInventory().getItem(count+1));
			else
				DungeonConfig.set("100."+count+".1", null);
			if(event.getInventory().getItem(count+10)!=null)
				DungeonConfig.set("90."+count, event.getInventory().getItem(count+10));	
			else
				DungeonConfig.set("90."+count+".1", null);
			if(event.getInventory().getItem(count+19)!=null)
				DungeonConfig.set("50."+count, event.getInventory().getItem(count+19));	
			else
				DungeonConfig.set("50."+count+".1", null);
			if(event.getInventory().getItem(count+28)!=null)
				DungeonConfig.set("10."+count, event.getInventory().getItem(count+28));
			else
				DungeonConfig.set("10."+count+".1", null);
			if(event.getInventory().getItem(count+37)!=null)
				DungeonConfig.set("1."+count, event.getInventory().getItem(count+37));
			else
				DungeonConfig.set("1."+count+".1", null);		
			if(event.getInventory().getItem(count+46)!=null)
				DungeonConfig.set("0."+count, event.getInventory().getItem(count+46));
			else
				DungeonConfig.set("0."+count+".1", null);
		}
		DungeonConfig.saveConfig();

		new GoldBigDragon_RPG.Effect.Sound().SP((Player) event.getPlayer(), Sound.ITEM_PICKUP, 1.0F, 1.8F);
		event.getPlayer().sendMessage(ChatColor.GREEN+"[던전] : 보상 설정 완료!");
	}
	
	public void AltarUSEGuiClose(InventoryCloseEvent event)
	{
		ItemStack item = event.getInventory().getItem(4);
		if(item!=null)
		{
			String AltarName = event.getInventory().getItem(0).getItemMeta().getDisplayName();
			Player player = (Player) event.getPlayer();
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager AltarConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
			event.getInventory().setItem(4, null);
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			int LvDistrict = -1;
			int RealLvDistrict = -1;
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() != null)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.WHITE+"(이미 던전이 만들어 지고 있다...)");
				return;
			}
			if(item.hasItemMeta())
			{
				if(item.getItemMeta().hasDisplayName())
				{
					if(item.getItemMeta().getDisplayName().compareTo(ChatColor.RED+""+ChatColor.BOLD+"[던전 통행증]")==0)
					{
						if(AltarConfig.contains("EnterCard."+ChatColor.stripColor(item.getItemMeta().getLore().get(1))))
						{
							int capacity = Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(3)).split(" : ")[1]);
							String time = ChatColor.stripColor(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1));
							boolean canUse = false;
							if(time.compareTo("[제한 시간 없음]")==0)
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
								YamlManager EnterCardConfig = YC.getNewConfig("Dungeon/EnterCardList.yml");
								LvDistrict = YC.getNewConfig("Dungeon/Dungeon/"+EnterCardConfig.getString(ChatColor.stripColor(item.getItemMeta().getLore().get(1))+".Dungeon")+"/Option.yml").getInt("District.Level");
								RealLvDistrict = YC.getNewConfig("Dungeon/Dungeon/"+EnterCardConfig.getString(ChatColor.stripColor(item.getItemMeta().getLore().get(1))+".Dungeon")+"/Option.yml").getInt("District.RealLevel");
								if(EnterCardConfig.contains(ChatColor.stripColor(item.getItemMeta().getLore().get(1))))
								{
									PartyEnterDungeon(player, item, AltarName, capacity, EnterCardConfig.getString(ChatColor.stripColor(item.getItemMeta().getLore().get(1))+".Dungeon"), LvDistrict, RealLvDistrict);
								}
								else
								{
									if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
										new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
									s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
									player.sendMessage(ChatColor.WHITE+"(이 물건은 제물로 바칠 수 없는 듯 하다...)");
									return;
								}
							}
							else
							{
								if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
									new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
								s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
								player.sendMessage(ChatColor.WHITE+"(던전 통행증의 유효시간이 지났다...)");
								return;
							}
						}
						else
						{
							if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
								new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
							s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
							player.sendMessage(ChatColor.WHITE+"(이 물건은 제물로 바칠 수 없는 듯 하다...)");
							return;
						}
					}
					else
					{
						if(AltarConfig.getString("NormalDungeon")!=null)
						{
							YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");
							if(DungeonConfig.contains(AltarConfig.getString("NormalDungeon")))
								PartyEnterDungeon(player, item, AltarName, -1, AltarConfig.getString("NormalDungeon"), LvDistrict, RealLvDistrict);
							else
							{
								AltarConfig.set("NormalDungeon", null);
								AltarConfig.saveConfig();
								if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
									new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
								s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
								player.sendMessage(ChatColor.WHITE+"(이 물건은 제물로 바칠 수 없는 듯 하다...)");
								return;
							}
						}
						else
						{
							if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
								new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
							s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
							player.sendMessage(ChatColor.WHITE+"(이 물건은 제물로 바칠 수 없는 듯 하다...)");
							return;
						}
					}
				}
				return;
			}
			if(AltarConfig.getString("NormalDungeon")!=null)
			{
				YamlManager DungeonConfig = YC.getNewConfig("Dungeon/DungeonList.yml");
				if(DungeonConfig.contains(AltarConfig.getString("NormalDungeon")))
					PartyEnterDungeon(player, item, AltarName, -1, AltarConfig.getString("NormalDungeon"), LvDistrict, RealLvDistrict);
				else
				{
					AltarConfig.set("NormalDungeon", null);
					AltarConfig.saveConfig();
					if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
						new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
					s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
					player.sendMessage(ChatColor.WHITE+"(이 물건은 제물로 바칠 수 없는 듯 하다...)");
					return;
				}
			}
			else
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.WHITE+"(이 물건은 제물로 바칠 수 없는 듯 하다...)");
				return;
			}
		}
	}

	private void PartyEnterDungeon(Player player, ItemStack item, String AltarName, int capacity, String DungeonName, int LvDistrict, int RealLvDistrict)
	{
		if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player))
		{
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			if(capacity!=-1)
				if(GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).getPartyMembers()!=capacity)
				{
					if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
						new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
					s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
					player.sendMessage(ChatColor.RED + "[던전] : 던전 입장 인원이 맞지 않습니다! ("+capacity+"명)");
					return;
				}
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager AltarConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
			YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+AltarConfig.getString("NormalDungeon")+"/Option.yml");
			if(LvDistrict==-1)
				LvDistrict = DungeonConfig.getInt("District.Level");
			if(RealLvDistrict==-1)
				RealLvDistrict = DungeonConfig.getInt("District.RealLevel");
			if(DungeonName!=null)
				DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
			long UTC = GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player);
			if(GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getLeader().compareTo(player.getName())==0)
			{
				//파티원 추가하기//
				ArrayList<Player> NearPartyMember = new ArrayList<Player>();
				GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getMember();
				for(int count = 0; count < GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getPartyMembers(); count++)
				{
					if(player.getWorld().getName().compareTo(GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getMember()[count].getWorld().getName())==0)
						if(player.getLocation().distance(GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getMember()[count].getLocation()) < 11)
							NearPartyMember.add(GoldBigDragon_RPG.Main.ServerOption.Party.get(UTC).getMember()[count]);
				}
				for(int count = 0; count < NearPartyMember.size(); count++)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(NearPartyMember.get(count).getUniqueId().toString()).getStat_Level()< LvDistrict)
					{
						if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
							new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
						s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED + "[던전] : 파티원 "+NearPartyMember.get(count).getName()+"님의 레벨이 낮아 던전에 입장할 수 없습니다!");
						player.sendMessage(ChatColor.RED + "(레벨 제한 : "+DungeonConfig.getInt("District.Level")+")");
						return;
					}
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(NearPartyMember.get(count).getUniqueId().toString()).getStat_RealLevel()<RealLvDistrict)
					{
						if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
							new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
						s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
						player.sendMessage(ChatColor.RED + "[던전] : 파티원 "+NearPartyMember.get(count).getName()+"님의 누적 레벨이 낮아 던전에 입장할 수 없습니다!");
						player.sendMessage(ChatColor.RED + "(누적 레벨 제한 : "+DungeonConfig.getInt("District.RealLevel")+")");
						return;
					}
				}
				if(new GoldBigDragon_RPG.Dungeon.DungeonCreater().CreateDungeon(player, DungeonConfig.getInt("Size"), DungeonConfig.getInt("Maze_Level"), DungeonConfig.getString("Type.Name"),DungeonName,NearPartyMember, AltarName, item)==false)
				{
					if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
						new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
					s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
					return;
				}
			}
			else
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED+"[파티] : 파티의 리더만 제단에 물건을 바칠 수 있습니다!");
				return;
			}
		}
		else
			SoloEnterDungeon(player, item, AltarName, capacity, DungeonName, LvDistrict, RealLvDistrict);
	}
	
	private void SoloEnterDungeon(Player player, ItemStack item, String AltarName, int capacity, String DungeonName, int LvDistrict, int RealLvDistrict)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		if(capacity==-1||capacity==1)
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager AltarConfig = YC.getNewConfig("Dungeon/Altar/"+AltarName+".yml");
			YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+AltarConfig.getString("NormalDungeon")+"/Option.yml");
			if(DungeonName!=null)
				DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Option.yml");
			if(LvDistrict==-1)
				LvDistrict = DungeonConfig.getInt("District.Level");
			if(RealLvDistrict==-1)
				RealLvDistrict = DungeonConfig.getInt("District.RealLevel");
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level()< LvDistrict)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED + "[던전] : 당신의 레벨이 낮아 던전에 입장할 수 없습니다!");
				player.sendMessage(ChatColor.RED + "(레벨 제한 : "+DungeonConfig.getInt("District.Level")+")");
				return;
			}
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel()<RealLvDistrict)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				player.sendMessage(ChatColor.RED + "[던전] : 당신의 누적 레벨이 낮아 던전에 입장할 수 없습니다!");
				player.sendMessage(ChatColor.RED + "(누적 레벨 제한 : "+DungeonConfig.getInt("District.RealLevel")+")");
				return;
			}
			if(new GoldBigDragon_RPG.Dungeon.DungeonCreater().CreateDungeon(player, DungeonConfig.getInt("Size"), DungeonConfig.getInt("Maze_Level"), DungeonConfig.getString("Type.Name"),DungeonName,null, AltarName, item)==false)
			{
				if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
					new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
				s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
				return;
			}
		}
		else
		{
			if(new GoldBigDragon_RPG.Util.PlayerUtil().giveItem(player, item)==false)
				new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(player.getLocation(), item);
			s.SP(player, Sound.IRONGOLEM_WALK, 1.0F, 1.0F);
			player.sendMessage(ChatColor.RED + "[던전] : 던전 입장 인원이 맞지 않습니다! ("+capacity+"명)");
			return;
		}
	}
	//DungeonGUI Close//

}
