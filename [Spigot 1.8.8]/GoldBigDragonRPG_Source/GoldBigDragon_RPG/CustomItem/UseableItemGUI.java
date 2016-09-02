package GoldBigDragon_RPG.CustomItem;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.GUI.OPBoxGUI;
import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UseableItemGUI extends GUIutil
{
	public void UseableItemListGUI(Player player, int page)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager ItemList  = YC.getNewConfig("Item/Consume.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "소모성 아이템 목록 : " + (page+1));

		Object[] a = ItemList.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String ItemName = ItemList.getString(a[count].toString()+".DisplayName");
			short ID = (short) ItemList.getInt(a[count].toString()+".ID");
			byte Data = (byte) ItemList.getInt(a[count].toString()+".Data");
			
			if(count > a.length || loc >= 45) break;
			Stack2(ItemName, ID,Data,1,Arrays.asList(LoreCreater(Integer.parseInt(a[count].toString()))), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 아이템", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 아이템을 생성합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void ChooseUseableItemTypeGUI(Player player, int number)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "소모성 아이템 타입");

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[귀환서]", 340,0,1,Arrays.asList(ChatColor.GRAY + "특정 위치로 신속히 이동할 수 있는",ChatColor.GRAY+"귀환서를 제작합니다."), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[주문서]", 339,0,1,Arrays.asList(ChatColor.GRAY + "특별한 기운이 담긴",ChatColor.GRAY+"주문서를 제작합니다."), 3, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[스킬 북]", 403,0,1,Arrays.asList(ChatColor.GRAY + "특정 스킬을 배울 수 있는",ChatColor.GRAY+"스킬 북을 제작합니다.","",ChatColor.RED+"[게임 시스템이 '마비노기'여야 합니다.]"), 4, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[음식, 포션]", 297,0,1,Arrays.asList(ChatColor.GRAY + "퀵슬롯으로 등록이 가능한",ChatColor.GRAY+"음식 및 포션 류를 제작합니다."), 5, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[룬]", 381,0,1,Arrays.asList(ChatColor.GRAY + "무기의 능력을 올려주는",ChatColor.GRAY+"신비한 룬을 제작합니다."), 6, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK +""+ number), 8, inv);
		player.openInventory(inv);
	}
	
	public void NewUseableItemGUI(Player player, int number)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "소모성 아이템 상세 설정");
		String ItemName = ItemList.getString(number+".DisplayName");
		short ItemID = (short) ItemList.getInt(number+".ID");
		byte ItemData = (byte) ItemList.getInt(number+".Data");

		String Type = "";
		String Grade = "";
		for(short counter =0;counter < 13 - ItemList.getString(number+".Type").length();counter++ )
			Type = Type +" ";
		Type = Type +ItemList.getString(number+".Type");
		Grade = ItemList.getString(number+".Grade");
		
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 58,0,1,null, 9, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 58,0,1,null, 10, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 58,0,1,null, 11, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 58,0,1,null, 18, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 58,0,1,null, 20, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 58,0,1,null, 27, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 58,0,1,null, 28, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 58,0,1,null, 29, inv);
		
		Stack2(ItemName, ItemID,ItemData,1,Arrays.asList(LoreCreater(number)), 19, inv);
		
		Stack2(ChatColor.DARK_AQUA + "[    형식 변경    ]", 421,0,1,Arrays.asList(ChatColor.WHITE+"아이템 설명창을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 형식    ]","       "+ ItemList.getString(number+".ShowType"),""), 37, inv);
		Stack2(ChatColor.DARK_AQUA + "[    이름 변경    ]", 421,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 이름을",ChatColor.WHITE+"변경합니다.",""), 13, inv);
		Stack2(ChatColor.DARK_AQUA + "[    설명 변경    ]", 386,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 설명을",ChatColor.WHITE+"변경합니다.",""), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "[    타입 변경    ]", 166,0,1,Arrays.asList("",ChatColor.RED+"[타입 변경이 불가능 합니다]",""), 15, inv);
		Stack2(ChatColor.DARK_AQUA + "[    등급 변경    ]", 266,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 등급을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 등급    ]","       "+Grade,""), 16, inv);
		Stack2(ChatColor.DARK_AQUA + "[        ID        ]", 405,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 ID값을",ChatColor.WHITE+"변경합니다.",""), 22, inv);
		Stack2(ChatColor.DARK_AQUA + "[       DATA       ]", 336,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 DATA값을",ChatColor.WHITE+"변경합니다.",""), 23, inv);

		switch(ChatColor.stripColor(ItemList.getString(number+".Type")))
		{
		case "[귀환서]":
			Stack(ChatColor.DARK_AQUA + "[    위치 지정    ]", 386,0,1,Arrays.asList(ChatColor.WHITE+"현재 서 있는 장소를",ChatColor.WHITE+"워프 지점으로 등록 합니다.","",ChatColor.BLUE+"[현재 등록된 위치]",ChatColor.BLUE+"월드 : "+ItemList.getString(number+".World"),ChatColor.BLUE+"좌표 : "+ItemList.getInt(number+".X")+","+ItemList.getInt(number+".Y")+","+ItemList.getInt(number+".Z"),"",ChatColor.YELLOW+"[좌 클릭시 현재 지점 등록]",""), 25, inv);
			break;
		case "[주문서]":
			Stack(ChatColor.DARK_AQUA + "[     스킬 포인트     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"주문서 사용시 즉시",ChatColor.WHITE+"스킬 포인트를 얻습니다.",""), 24, inv);
			Stack(ChatColor.DARK_AQUA + "[     스텟 포인트     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"주문서 사용시 즉시",ChatColor.WHITE+"스텟 포인트를 얻습니다.",""), 25, inv);
			Stack(ChatColor.DARK_AQUA + "[        방어        ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"주문서 사용시 방어력을",ChatColor.WHITE+"상승 시켜 줍니다.",""), 31, inv);
			Stack(ChatColor.DARK_AQUA + "[        보호        ]", 306,0,1,Arrays.asList(ChatColor.WHITE+"주문서 사용시 보호를",ChatColor.WHITE+"상승 시켜 줍니다.",""), 32, inv);
			Stack(ChatColor.DARK_AQUA + "[      마법 방어      ]", 311,0,1,Arrays.asList(ChatColor.WHITE+"주문서 사용시 마법 방어를",ChatColor.WHITE+"상승 시켜 줍니다.",""), 33, inv);
			Stack(ChatColor.DARK_AQUA + "[      마법 보호      ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"주문서 사용시 마법 보호를",ChatColor.WHITE+"상승 시켜 줍니다.",""), 34, inv);
			Stack(ChatColor.DARK_AQUA + "[        스텟        ]", 399,0,1,Arrays.asList(ChatColor.WHITE+"주문서 사용시 스텟을 영구적으로",ChatColor.WHITE+"상승 시켜 줍니다.",""), 40, inv);
			break;
		case "[스킬북]":
			if(ItemList.getString(number+".Skill").equals("null"))
				Stack(ChatColor.DARK_AQUA + "[        스킬        ]", 340,0,1,Arrays.asList(ChatColor.WHITE+"스킬 북 사용시",ChatColor.WHITE+"아래 스킬을 습득합니다.","",ChatColor.BLUE+"[현재 등록된 스킬]",ChatColor.WHITE+"      없음"), 25, inv);
			else
				Stack(ChatColor.DARK_AQUA + "[        스킬        ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"스킬 북 사용시",ChatColor.WHITE+"아래 스킬을 습득합니다.","",ChatColor.BLUE+"[현재 등록된 스킬]",ChatColor.WHITE+ItemList.getString(number+".Skill")), 25, inv);
			break;
		case "[소비]":
			Stack(ChatColor.DARK_AQUA + "[       포만감       ]", 364,0,1,Arrays.asList(ChatColor.WHITE+"아이템 사용시 허기를",ChatColor.WHITE+"감소 시켜 줍니다.",""), 31, inv);
			Stack(ChatColor.DARK_AQUA + "[       생명력       ]", 373,8261,1,Arrays.asList(ChatColor.WHITE+"아이템 사용시 생명력을",ChatColor.WHITE+"상승 시켜 줍니다.",""), 32, inv);
			Stack(ChatColor.DARK_AQUA + "[        마나        ]", 373,8230,1,Arrays.asList(ChatColor.WHITE+"아이템 사용시 마나를",ChatColor.WHITE+"상승 시켜 줍니다.",""), 33, inv);
			Stack(ChatColor.DARK_AQUA + "[        환생        ]", 399,0,1,Arrays.asList(ChatColor.WHITE+"아이템 사용시 플레이어의",ChatColor.WHITE+"레벨을 초기화 시켜 줍니다.","",ChatColor.RED+"[서버 시스템이 마비노기일 경우만 사용 가능합니다.]",""), 34, inv);
			break;
		case "[룬]":
			Stack(ChatColor.DARK_AQUA + "[       대미지       ]", 267,0,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 "+ServerOption.Damage+"를",ChatColor.WHITE+"증가 시켜 줍니다.",""), 24, inv);
			Stack(ChatColor.DARK_AQUA + "[     마법 대미지     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 "+ServerOption.MagicDamage+"를",ChatColor.WHITE+"증가 시켜 줍니다.",""), 25, inv);
			Stack(ChatColor.DARK_AQUA + "[        방어        ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 방어력을",ChatColor.WHITE+"증가 시켜 줍니다.",""), 31, inv);
			Stack(ChatColor.DARK_AQUA + "[        보호        ]", 306,0,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 보호를",ChatColor.WHITE+"증가 시켜 줍니다.",""), 32, inv);
			Stack(ChatColor.DARK_AQUA + "[      마법 방어      ]", 311,0,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 마법 방어를",ChatColor.WHITE+"증가 시켜 줍니다.",""), 33, inv);
			Stack(ChatColor.DARK_AQUA + "[      마법 보호      ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 마법 보호를",ChatColor.WHITE+"증가 시켜 줍니다.",""), 34, inv);
			Stack(ChatColor.DARK_AQUA + "[        스텟        ]", 399,0,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 스텟을 영구적으로",ChatColor.WHITE+"증가 시켜 줍니다.",""), 40, inv);
			Stack(ChatColor.DARK_AQUA + "[       내구도       ]", 145,2,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 아이템의 내구력을",ChatColor.WHITE+"증가 시켜 줍니다.","",ChatColor.RED+"[일반 아이템 불가능]",""), 41, inv);
			//Stack(ChatColor.DARK_AQUA + "[        개조        ]", 145,0,1,Arrays.asList(ChatColor.WHITE+"룬 장착시 최대 개조 횟수를",ChatColor.WHITE+"증가 시켜 줍니다.",""), 42, inv);
			break;
		}
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+Type), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+number), 53, inv);
		player.openInventory(inv);
	}
	
	public void SelectSkillGUI(Player player, short page, int ItemNumber)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "등록 가능 스킬 목록 : " + (page+1));

		Object[] a= SkillList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String SkillName = a[count].toString();
			short JobLevel= (short) SkillList.getConfigurationSection(a[count].toString()+".SkillRank").getKeys(false).size();
			if(count > a.length || loc >= 45) break;
			
			YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
			if(JobList.contains("Mabinogi.Added."+SkillName)==true)
			{
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList(ChatColor.DARK_AQUA+"최대 스킬 레벨 : " + ChatColor.WHITE+JobLevel,"",ChatColor.YELLOW+"[좌 클릭시 스킬 등록]"), loc, inv);
				loc++;	
			}
		}
		
		if(a.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+ItemNumber), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void UseableItemListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

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
			UseableItemListGUI(player, page-1);
			return;
		case 49://새 아이템
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			ChooseUseableItemTypeGUI(player, ((page*45)+event.getSlot()));
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UseableItemListGUI(player, page+1);
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			int number = ((page*45)+event.getSlot());
			if(event.isLeftClick() == true&&event.isShiftClick()==false)
			{
				player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 클릭한 아이템을 지급 받았습니다!");
				player.getInventory().addItem(event.getCurrentItem());
			}
			if(event.isLeftClick() == true&&event.isShiftClick()==true)
				NewUseableItemGUI(player, number);
			else if(event.isRightClick()==true&&event.isShiftClick()==true)
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
				short Acount =  (short) (ItemList.getConfigurationSection("").getKeys(false).toArray().length-1);

				for(int counter = number;counter <Acount;counter++)
					ItemList.set(counter+"", ItemList.get((counter+1)+""));
				ItemList.removeKey(Acount+"");
				ItemList.saveConfig();
				UseableItemListGUI(player, page);
			}
			return;
		}
	}
	
	public void ChooseUseableItemTypeGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager UseableItemList = YC.getNewConfig("Item/Consume.yml");
		
		switch (event.getSlot())
		{
		case 2://귀환서
		case 3://주문서
		case 4://스킬 북
		case 5://음식, 포션
		case 6://룬
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			String Type = "";
			String Lore = "";
			String DisplayName = "";
			short ID = 267;
			short Data = 0;
			switch(event.getSlot())
			{
				case 2: Type = ChatColor.WHITE + "[귀환서]";
				Lore = ChatColor.WHITE+"육체의 손실 없이 지정된 곳으로%enter%"+ChatColor.WHITE+"빠르게 이동할 수 있는 신비한%enter%"+ChatColor.WHITE+"귀환 주문서이다.";
				DisplayName = ChatColor.WHITE+"0의 귀환 주문서";
				ID = 340;
				break;
				case 3: Type = ChatColor.GOLD + "[주문서]";
				Lore = ChatColor.WHITE+"사용시 스킬 포인트를%enter%"+ChatColor.WHITE+"5만큼 상승시켜 준다.";
				DisplayName =ChatColor.WHITE+ "스킬 포인트 5 증가 주문서";
				ID = 339;
				break;
				case 4: Type = ChatColor.DARK_PURPLE + "[스킬북]";
				Lore = ChatColor.WHITE+"아직 아무것도 쓰여있지 않은%enter%"+ChatColor.WHITE+"빈 상태의 스킬 북이다.%enter% %enter%"+ChatColor.WHITE+"(아무것도 얻을 수 없을 것 같다.)";
				DisplayName = ChatColor.WHITE+"빈 스킬 북";
				ID = 403;
				break;
				case 5: Type = ChatColor.LIGHT_PURPLE + "[소비]";
				Lore = ChatColor.WHITE+"퀵 슬롯에 놓고, 위급시%enter%"+ChatColor.WHITE+"사용할 경우, 생명력을%enter%"+ChatColor.WHITE+"10 치료해 주는 포션이다.";
				DisplayName = ChatColor.WHITE+"시뻘건 포션";
				ID = 373;
				Data = 8261;
				break;
				case 6: Type = ChatColor.BLUE + "[룬]";
				Lore = ChatColor.WHITE+"강렬한 녹색의 룬이다.%enter%"+ChatColor.WHITE+"무기의 밸런스를 올려주는 듯 하다.";
				DisplayName =ChatColor.WHITE+ "녹색 룬";
				ID = 351;
				Data = 10;
				break;
			}

			int ItemCounter = UseableItemList.getConfigurationSection("").getKeys(false).size();
			UseableItemList.set(ItemCounter+".ShowType",ChatColor.WHITE+"[깔끔]");
			UseableItemList.set(ItemCounter+".ID",ID);
			UseableItemList.set(ItemCounter+".Data",Data);
			UseableItemList.set(ItemCounter+".DisplayName",DisplayName);
			UseableItemList.set(ItemCounter+".Lore",Lore);
			UseableItemList.set(ItemCounter+".Type",Type);
			UseableItemList.set(ItemCounter+".Grade",ChatColor.WHITE+"[일반]");
			
			switch(event.getSlot())
			{
			case 2:
				UseableItemList.set(ItemCounter+".World",player.getLocation().getWorld().getName().toString());
				UseableItemList.set(ItemCounter+".X",0);
				UseableItemList.set(ItemCounter+".Y",0);
				UseableItemList.set(ItemCounter+".Z",0);
				UseableItemList.set(ItemCounter+".Pitch",0);
				UseableItemList.set(ItemCounter+".Yaw",0);
			break;
			case 3:
				UseableItemList.set(ItemCounter+".DEF",0);
				UseableItemList.set(ItemCounter+".Protect",0);
				UseableItemList.set(ItemCounter+".MaDEF",0);
				UseableItemList.set(ItemCounter+".MaProtect",0);
				UseableItemList.set(ItemCounter+".STR",0);
				UseableItemList.set(ItemCounter+".DEX",0);
				UseableItemList.set(ItemCounter+".INT",0);
				UseableItemList.set(ItemCounter+".WILL",0);
				UseableItemList.set(ItemCounter+".LUK",0);
				UseableItemList.set(ItemCounter+".Balance",0);
				UseableItemList.set(ItemCounter+".Critical",0);
				UseableItemList.set(ItemCounter+".HP",0);
				UseableItemList.set(ItemCounter+".MP",0);
				UseableItemList.set(ItemCounter+".SkillPoint",5);
				UseableItemList.set(ItemCounter+".StatPoint",0);
			break;
			case 4:
				UseableItemList.set(ItemCounter+".Skill","null");
			break;
			case 5:
				UseableItemList.set(ItemCounter+".HP",10);
				UseableItemList.set(ItemCounter+".MP",0);
				UseableItemList.set(ItemCounter+".Saturation",0);
				UseableItemList.set(ItemCounter+".Rebirth",false);
			break;
			case 6:
				UseableItemList.set(ItemCounter+".MinDamage",0);
				UseableItemList.set(ItemCounter+".MaxDamage",0);
				UseableItemList.set(ItemCounter+".MinMaDamage",0);
				UseableItemList.set(ItemCounter+".MaxMaDamage",0);
				UseableItemList.set(ItemCounter+".DEF",0);
				UseableItemList.set(ItemCounter+".Protect",0);
				UseableItemList.set(ItemCounter+".MaDEF",0);
				UseableItemList.set(ItemCounter+".MaProtect",0);
				UseableItemList.set(ItemCounter+".Durability",0);
				UseableItemList.set(ItemCounter+".MaxDurability",0);
				UseableItemList.set(ItemCounter+".STR",0);
				UseableItemList.set(ItemCounter+".DEX",0);
				UseableItemList.set(ItemCounter+".INT",0);
				UseableItemList.set(ItemCounter+".WILL",0);
				UseableItemList.set(ItemCounter+".LUK",0);
				UseableItemList.set(ItemCounter+".Balance",10);
				UseableItemList.set(ItemCounter+".Critical",0);
				UseableItemList.set(ItemCounter+".HP",0);
				UseableItemList.set(ItemCounter+".MP",0);
			break;
			}
			UseableItemList.saveConfig();
			NewUseableItemGUI(player,ItemCounter);
			return;
		case 0://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UseableItemListGUI(player, 0);
			return;
		case 8://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void NewUseableItemGUIclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		
		int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
		
		switch (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()))
		{
		case "[        스킬        ]":

			YamlManager Config = YC.getNewConfig("config.yml");
			
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				SelectSkillGUI(player, (short) 0, itemnumber);
			}
			else
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+ "[스킬 북 생성] : 현재 서버 시스템이 "+ChatColor.YELLOW+"'마비노기'"+ChatColor.RED+"가 아닙니다!");
			}
			return;
		case "[       포만감       ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 회복할 포만감을 입력해 주세요!");
			u.setType(player, "UseableItem");
			u.setString(player, (byte)1, "Saturation");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -1);
			return;
		case "[       생명력       ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 회복할 생명력을 입력해 주세요!");
			u.setType(player, "UseableItem");
			u.setString(player, (byte)1, "HP");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -8);
			return;
		case "[        마나        ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 회복할 마나를 입력해 주세요!");
			u.setType(player, "UseableItem");
			u.setString(player, (byte)1, "MP");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -8);
			return;
		case "[     스킬 포인트     ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 주고자 하는 스킬 포인트의 양을 입력해 주세요!");
			u.setType(player, "UseableItem");
			u.setString(player, (byte)1, "SkillPoint");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -1);
			return;
		case "[     스텟 포인트     ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 주고자 하는 스킬 포인트의 양을 입력해 주세요!");
			u.setType(player, "UseableItem");
			u.setString(player, (byte)1, "StatPoint");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -1);
			return;
		case "[    위치 지정    ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			ItemList.set(itemnumber+".World", player.getLocation().getWorld().getName().toString());
			ItemList.set(itemnumber+".X", player.getLocation().getX());
			ItemList.set(itemnumber+".Y", player.getLocation().getY());
			ItemList.set(itemnumber+".Z", player.getLocation().getZ());
			ItemList.set(itemnumber+".Pitch", player.getLocation().getPitch());
			ItemList.set(itemnumber+".Yaw", player.getLocation().getYaw());
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
			return;
		case  "[        환생        ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(ItemList.getBoolean(itemnumber+".Rebirth") == false)
				ItemList.set(itemnumber+".Rebirth", true);
			else
				ItemList.set(itemnumber+".Rebirth", false);
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
			return;
		case "[    형식 변경    ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(ItemList.getString(itemnumber+".ShowType").contains("[깔끔]"))
				ItemList.set(itemnumber+".ShowType",ChatColor.YELLOW+"[컬러]");
			else if(ItemList.getString(itemnumber+".ShowType").contains("[컬러]"))
				ItemList.set(itemnumber+".ShowType",ChatColor.LIGHT_PURPLE+"[기호]");
			else if(ItemList.getString(itemnumber+".ShowType").contains("[기호]"))
				ItemList.set(itemnumber+".ShowType",ChatColor.BLUE+"[분류]");
			else if(ItemList.getString(itemnumber+".ShowType").contains("[분류]"))
				ItemList.set(itemnumber+".ShowType",ChatColor.WHITE+"[깔끔]");
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
			return;
		case "[    이름 변경    ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 이름을 입력해 주세요!");
			player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
			ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
					ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
			ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
					ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			u.setType(player, "UseableItem");
			u.setString(player, (byte)1, "DisplayName");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -1);
			return;
		case  "[    설명 변경    ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 설명을 입력해 주세요!");
			player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
			player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
			ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
					ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
			ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
					ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			u.setType(player, "UseableItem");
			u.setString(player, (byte)1, "Lore");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -1);
			return;
		case "[    타입 변경    ]"://타입 변경
			s.SP(player, Sound.ANVIL_LAND, 0.8F, 1.8F);
			return;
		case "[    등급 변경    ]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(ItemList.getString(itemnumber+".Grade").contains("[일반]"))
				ItemList.set(itemnumber+".Grade",ChatColor.GREEN+"[상급]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[상급]"))
				ItemList.set(itemnumber+".Grade",ChatColor.BLUE+"[매직]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[매직]"))
				ItemList.set(itemnumber+".Grade",ChatColor.YELLOW+"[레어]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[레어]"))
				ItemList.set(itemnumber+".Grade",ChatColor.DARK_PURPLE+"[에픽]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[에픽]"))
				ItemList.set(itemnumber+".Grade",ChatColor.GOLD+"[전설]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[전설]"))
				ItemList.set(itemnumber+".Grade",ChatColor.DARK_RED+""+ChatColor.BOLD+"["+ChatColor.GOLD+""+ChatColor.BOLD+"초"+ChatColor.DARK_GREEN+""+ChatColor.BOLD+"월"+ChatColor.DARK_BLUE+""+ChatColor.BOLD+"]");
			else if(ItemList.getString(itemnumber+".Grade").contains("초"))
				ItemList.set(itemnumber+".Grade",ChatColor.GRAY+"[하급]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[하급]"))
				ItemList.set(itemnumber+".Grade",ChatColor.WHITE+"[일반]");
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
			return;
			case  "[        ID        ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 ID 값을 입력해 주세요!");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "ID");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "[       DATA       ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 DATA 값을 입력해 주세요!");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "Data");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "[       대미지       ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최소 "+ServerOption.Damage+"를 입력해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "MinDamage");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "[     마법 대미지     ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최소 "+ServerOption.MagicDamage+"를 입력해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "MinMaDamage");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "[        방어        ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 방어력을 입력해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "DEF");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "[        보호        ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보호를 입력해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "Protect");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "[      마법 방어      ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 마법 방어력을 입력해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "MaDEF");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "[      마법 보호      ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 마법 보호를 입력해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "MaProtect");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "[        스텟        ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 생명력 보너스를 입력해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "HP");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case"[       내구도       ]":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최대 내구력을 입력해 주세요!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setType(player, "UseableItem");
				u.setString(player, (byte)1, "MaxDurability");
				u.setInt(player, (byte)3, itemnumber);
				u.setInt(player, (byte)4, -1);
				return;
			case "이전 목록":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				UseableItemListGUI(player, 0);
				return;
			case "닫기":
				event.setCancelled(true);
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
		}
	}
	
	public void SelectSkillGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
		int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			NewUseableItemGUI(player, itemnumber);
			break;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SelectSkillGUI(player, (short) (page-1), itemnumber);
			break;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SelectSkillGUI(player, (short) (page+1), itemnumber);
			break;
		default:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			ItemList.set(itemnumber+".Skill", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
			break;
		}
	}
	
	public String[] LoreCreater(int ItemNumber)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
		String lore = "";
		String Type = ChatColor.stripColor(ItemList.getString(ItemNumber+".ShowType"));

		lore = lore+ItemList.getString(ItemNumber+".Type");
		for(short count = 0; count<20-ItemList.getString(ItemNumber+".Type").length();count++)
			lore = lore+" ";
		lore = lore+ItemList.getString(ItemNumber+".Grade")+"%enter% %enter%";
		
		switch(Type)
		{
		case "[분류]":
		{
			switch(ChatColor.stripColor(ItemList.getString(ItemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+ChatColor.BLUE + " Ω 월드 : " +ChatColor.WHITE + ItemList.getString(ItemNumber+".World")+"%enter%";
				lore = lore+ChatColor.BLUE + " Ω X 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+ChatColor.BLUE + " Ω Y 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+ChatColor.BLUE + " Ω Z 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(ItemList.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 스텟 포인트 : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+ChatColor.RED + " - 스텟 포인트 : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 스킬 포인트 : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+ChatColor.RED + " - 스킬 포인트 : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " - 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " - 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " - 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " - 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " - 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " - 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " - 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " - 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(ItemList.getString(ItemNumber+".Skill").equals("null"))
					lore = lore+"%enter%"+ChatColor.RED + " [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%"+ChatColor.DARK_AQUA+" + "+ChatColor.WHITE+ItemList.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(ItemList.getInt(ItemNumber+".HP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".HP") < 0)
					lore = lore+ChatColor.RED + " - 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MP") < 0)
					lore = lore+ChatColor.RED + " - 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + 포만감 : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+ChatColor.RED + " - 포만감 : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				if(ItemList.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+ChatColor.GOLD +""+ChatColor.BOLD+ " + 환생%enter%";
				break;
			case "[룬]":
				if(ItemList.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최소 공격력 : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+ChatColor.RED + " - 최소 공격력 : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최대 공격력 : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+ChatColor.RED + " - 최대 공격력 : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최소 마법 공격력 : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+ChatColor.RED + " - 최소 마법 공격력 : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최대 마법 공격력 : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+ChatColor.RED + " - 최대 마법 공격력 : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " - 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " - 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " - 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " - 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " - 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " - 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";

				if(ItemList.getInt(ItemNumber+".Durability")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 현재 내구도 증가 : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Durability")<0)
					lore = lore+ChatColor.RED + " - 현재 내구도 증가 : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최대 내구도 증가 : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+ChatColor.RED + " - 최대 내구도 증가 : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";


				if(ItemList.getInt(ItemNumber+".HP")!=0||ItemList.getInt(ItemNumber+".MP")!=0||
				ItemList.getInt(ItemNumber+".STR")!=0||ItemList.getInt(ItemNumber+".DEX")!=0||
				ItemList.getInt(ItemNumber+".INT")!=0||ItemList.getInt(ItemNumber+".WILL")!=0||
				ItemList.getInt(ItemNumber+".LUK")!=0)
				{
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " - 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " - 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+ChatColor.DARK_PURPLE + " + 개조 : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+ChatColor.RED + " - 개조 : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			}

			lore = lore+"%enter%"+ChatColor.GOLD+"___--------------------___%enter%"+ChatColor.GOLD+"       [아이템 설명]";
			lore = lore+"%enter%"+ ItemList.getString(ItemNumber+".Lore")+"%enter%";
			lore = lore+ChatColor.GOLD+"---____________________---%enter%";
		}
		break;
		case "[기호]":
		{
			switch(ChatColor.stripColor(ItemList.getString(ItemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+ChatColor.BLUE + " Ω 월드 : " +ChatColor.WHITE + ItemList.getString(ItemNumber+".World")+"%enter%";
				lore = lore+ChatColor.BLUE + " Ω X 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+ChatColor.BLUE + " Ω Y 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+ChatColor.BLUE + " Ω Z 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(ItemList.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 스텟 포인트 : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+ChatColor.RED + " - 스텟 포인트 : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 스킬 포인트 : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+ChatColor.RED + " - 스킬 포인트 : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " - 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " - 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " - 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " - 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " - 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " - 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " - 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " - 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(ItemList.getString(ItemNumber+".Skill").equals("null"))
					lore = lore+"%enter%"+ChatColor.RED + " [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%"+ChatColor.DARK_AQUA+" + "+ChatColor.WHITE+ItemList.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(ItemList.getInt(ItemNumber+".HP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".HP") < 0)
					lore = lore+ChatColor.RED + " - 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MP") < 0)
					lore = lore+ChatColor.RED + " - 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + 포만감 : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+ChatColor.RED + " - 포만감 : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				if(ItemList.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+ChatColor.GOLD +""+ChatColor.BOLD+ " + 환생%enter%";
				break;
			case "[룬]":
				if(ItemList.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최소 공격력 : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+ChatColor.RED + " - 최소 공격력 : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최대 공격력 : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+ChatColor.RED + " - 최대 공격력 : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최소 마법 공격력 : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+ChatColor.RED + " - 최소 마법 공격력 : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최대 마법 공격력 : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+ChatColor.RED + " - 최대 마법 공격력 : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " - 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " - 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " - 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " - 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " - 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " - 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Durability")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 현재 내구도 증가 : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Durability")<0)
					lore = lore+ChatColor.RED + " - 현재 내구도 증가 : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+ChatColor.DARK_AQUA + " + 최대 내구도 증가 : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+ChatColor.RED + " - 최대 내구도 증가 : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";

				if(ItemList.getInt(ItemNumber+".HP")!=0||ItemList.getInt(ItemNumber+".MP")!=0||
				ItemList.getInt(ItemNumber+".STR")!=0||ItemList.getInt(ItemNumber+".DEX")!=0||
				ItemList.getInt(ItemNumber+".INT")!=0||ItemList.getInt(ItemNumber+".WILL")!=0||
				ItemList.getInt(ItemNumber+".LUK")!=0)
				{
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " - 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " - 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " - "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+ChatColor.DARK_PURPLE + " + 개조 : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+ChatColor.RED + " - 개조 : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			}			
			
			lore = lore+"%enter%"+ ItemList.getString(ItemNumber+".Lore")+"%enter%%enter%";

		}
		break;
		case "[컬러]":
		{
			switch(ChatColor.stripColor(ItemList.getString(ItemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+ChatColor.BLUE + " 월드 : " +ChatColor.WHITE + ItemList.getString(ItemNumber+".World")+"%enter%";
				lore = lore+ChatColor.BLUE + " X 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+ChatColor.BLUE + " Y 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+ChatColor.BLUE + " Z 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(ItemList.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " 스텟 포인트 : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+ChatColor.RED + " 스텟 포인트 : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " 스킬 포인트 : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+ChatColor.RED + " 스킬 포인트 : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(ItemList.getString(ItemNumber+".Skill").equals("null"))
					lore = lore+"%enter%"+ChatColor.RED + " [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%"+ChatColor.DARK_AQUA+" + "+ChatColor.WHITE+ItemList.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(ItemList.getInt(ItemNumber+".HP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".HP") < 0)
					lore = lore+ChatColor.RED + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MP") < 0)
					lore = lore+ChatColor.RED + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+ChatColor.DARK_AQUA + " 포만감 : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+ChatColor.RED + " 포만감 : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				if(ItemList.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+ChatColor.GOLD +""+ChatColor.BOLD+ " + 환생%enter%";
				break;
			case "[룬]":
				if(ItemList.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " 최소 공격력 : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+ChatColor.RED + " 최소 공격력 : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " 최대 공격력 : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+ChatColor.RED + " 최대 공격력 : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " 최소 마법 공격력 : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+ChatColor.RED + " 최소 마법 공격력 : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " 최대 마법 공격력 : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+ChatColor.RED + " 최대 마법 공격력 : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";

				if(ItemList.getInt(ItemNumber+".Durability")>0)
					lore = lore+ChatColor.DARK_AQUA + " 현재 내구도 증가 : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Durability")<0)
					lore = lore+ChatColor.RED + " 현재 내구도 증가 : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+ChatColor.DARK_AQUA + " 최대 내구도 증가 : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+ChatColor.RED + " 최대 내구도 증가 : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";


					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+ChatColor.DARK_PURPLE + " 개조 : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+ChatColor.RED + " 개조 : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				break;
			}
			
			lore = lore+"%enter%"+ ItemList.getString(ItemNumber+".Lore")+"%enter%%enter%";


		}
		break;
		
			case "[깔끔]":
			{
				switch(ChatColor.stripColor(ItemList.getString(ItemNumber+".Type")))
				{
				case "[귀환서]":
					lore = lore+ChatColor.WHITE + " 월드 : " +ChatColor.WHITE + ItemList.getString(ItemNumber+".World")+"%enter%";
					lore = lore+ChatColor.WHITE + " X 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".X")+"%enter%";
					lore = lore+ChatColor.WHITE + " Y 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Y")+"%enter%";
					lore = lore+ChatColor.WHITE + " Z 좌표 : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Z")+"%enter%";
					break;
				case "[주문서]":
					if(ItemList.getInt(ItemNumber+".StatPoint")>0)
						lore = lore+ChatColor.DARK_AQUA + " 스텟 포인트 : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".StatPoint")<0)
						lore = lore+ChatColor.RED + " 스텟 포인트 : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
					if(ItemList.getInt(ItemNumber+".SkillPoint")>0)
						lore = lore+ChatColor.DARK_AQUA + " 스킬 포인트 : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".SkillPoint")<0)
						lore = lore+ChatColor.RED + " 스킬 포인트 : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEF")>0)
						lore = lore+ChatColor.DARK_AQUA + " 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEF")<0)
						lore = lore+ChatColor.RED + " 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Protect")>0)
						lore = lore+ChatColor.DARK_AQUA + " 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Protect")<0)
						lore = lore+ChatColor.RED + " 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaDEF")>0)
						lore = lore+ChatColor.DARK_AQUA + " 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaDEF")<0)
						lore = lore+ChatColor.RED + " 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaProtect")>0)
						lore = lore+ChatColor.DARK_AQUA + " 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaProtect")<0)
						lore = lore+ChatColor.RED + " 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Balance")>0)
						lore = lore+ChatColor.DARK_AQUA + " 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Balance")<0)
						lore = lore+ChatColor.RED + " 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Critical")>0)
						lore = lore+ChatColor.DARK_AQUA + " 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Critical")<0)
						lore = lore+ChatColor.RED + " 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
					
						if(ItemList.getInt(ItemNumber+".HP") > 0)
							lore = lore+ChatColor.DARK_AQUA + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".HP") < 0)
							lore = lore+ChatColor.RED + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
						if(ItemList.getInt(ItemNumber+".MP") > 0)
							lore = lore+ChatColor.DARK_AQUA + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".MP") < 0)
							lore = lore+ChatColor.RED + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
						if(ItemList.getInt(ItemNumber+".STR") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".STR") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
						if(ItemList.getInt(ItemNumber+".DEX") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".DEX") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
						if(ItemList.getInt(ItemNumber+".INT") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".INT") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
						if(ItemList.getInt(ItemNumber+".WILL") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".WILL") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
						if(ItemList.getInt(ItemNumber+".LUK") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".LUK") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					break;
				case "[스킬북]":
					if(ItemList.getString(ItemNumber+".Skill").equals("null"))
						lore = lore+"%enter%"+ChatColor.WHITE + " [아무것도 없는 빈 책]%enter%";
					else
						lore = lore+"%enter%"+ChatColor.WHITE  +" [우 클릭시 아래 스킬 획득]%enter%"+ChatColor.WHITE+" + "+ChatColor.WHITE+ItemList.getString(ItemNumber+".Skill")+"%enter%";
					break;
				case "[소비]":
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Saturation") > 0)
						lore = lore+ChatColor.DARK_AQUA + " 포만감 : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".Saturation") < 0)
						lore = lore+ChatColor.RED + " 포만감 : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
					if(ItemList.getBoolean(ItemNumber+".Rebirth") == true)
						lore = lore+ChatColor.GOLD +""+ChatColor.BOLD+ " + 환생%enter%";
					break;
				case "[룬]":
					if(ItemList.getInt(ItemNumber+".MinDamage")>0)
						lore = lore+ChatColor.DARK_AQUA + " 최소 공격력 : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MinDamage")<0)
						lore = lore+ChatColor.RED + " 최소 공격력 : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxDamage")>0)
						lore = lore+ChatColor.DARK_AQUA + " 최대 공격력 : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MaxDamage")<0)
						lore = lore+ChatColor.RED + " 최대 공격력 : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MinMaDamage")>0)
						lore = lore+ChatColor.DARK_AQUA + " 최소 마법 공격력 : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MinMaDamage")<0)
						lore = lore+ChatColor.RED + " 최소 마법 공격력 : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxMaDamage")>0)
						lore = lore+ChatColor.DARK_AQUA + " 최대 마법 공격력 : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MaxMaDamage")<0)
						lore = lore+ChatColor.RED + " 최대 마법 공격력 : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
					
					
						
					if(ItemList.getInt(ItemNumber+".DEF")>0)
						lore = lore+ChatColor.DARK_AQUA + " 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEF")<0)
						lore = lore+ChatColor.RED + " 방어 : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Protect")>0)
						lore = lore+ChatColor.DARK_AQUA + " 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Protect")<0)
						lore = lore+ChatColor.RED + " 보호 : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaDEF")>0)
						lore = lore+ChatColor.DARK_AQUA + " 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaDEF")<0)
						lore = lore+ChatColor.RED + " 마법 방어 : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaProtect")>0)
						lore = lore+ChatColor.DARK_AQUA + " 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaProtect")<0)
						lore = lore+ChatColor.RED + " 마법 보호 : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Balance")>0)
						lore = lore+ChatColor.DARK_AQUA + " 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Balance")<0)
						lore = lore+ChatColor.RED + " 밸런스 : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Critical")>0)
						lore = lore+ChatColor.DARK_AQUA + " 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Critical")<0)
						lore = lore+ChatColor.RED + " 크리티컬 : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Durability")>0)
						lore = lore+ChatColor.DARK_AQUA + " 현재 내구도 증가 : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".Durability")<0)
						lore = lore+ChatColor.RED + " 현재 내구도 증가 : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxDurability")>0)
						lore = lore+ChatColor.DARK_AQUA + " 최대 내구도 증가 : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MaxDurability")<0)
						lore = lore+ChatColor.RED + " 최대 내구도 증가 : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
					
						if(ItemList.getInt(ItemNumber+".HP") > 0)
							lore = lore+ChatColor.DARK_AQUA + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".HP") < 0)
							lore = lore+ChatColor.RED + " 생명력 : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
						if(ItemList.getInt(ItemNumber+".MP") > 0)
							lore = lore+ChatColor.DARK_AQUA + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".MP") < 0)
							lore = lore+ChatColor.RED + " 마나 : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
						if(ItemList.getInt(ItemNumber+".STR") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".STR") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
						if(ItemList.getInt(ItemNumber+".DEX") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".DEX") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
						if(ItemList.getInt(ItemNumber+".INT") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".INT") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
						if(ItemList.getInt(ItemNumber+".WILL") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".WILL") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
						if(ItemList.getInt(ItemNumber+".LUK") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".LUK") < 0)
							lore = lore+ChatColor.RED + " "+ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
						if(ItemList.getInt(ItemNumber+".MaxUpgrade") > 0)
							lore = lore+ChatColor.DARK_PURPLE + " 개조 : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
						if(ItemList.getInt(ItemNumber+".MaxUpgrade") < 0)
							lore = lore+ChatColor.DARK_AQUA + " 개조 : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					break;
				}
				
				lore = lore+"%enter%"+ ItemList.getString(ItemNumber+".Lore")+"%enter%%enter%";

			}
			break;
		}

		String[] scriptA = lore.split("%enter%");
		for(byte counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}
	
}
