package customitem;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import admin.OPbox_GUI;
import effect.SoundEffect;
import main.Main_ServerOption;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UseableItem_GUI extends Util_GUI
{
	public void UseableItemListGUI(Player player, int page)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/Consume.yml");

		String UniqueCode = "§0§0§3§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0소모성 아이템 목록 : " + (page+1));

		String[] consumeItemList = itemYaml.getKeys().toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < consumeItemList.length;count++)
		{
			String ItemName = itemYaml.getString(consumeItemList[count]+".DisplayName");
			short ID = (short) itemYaml.getInt(consumeItemList[count]+".ID");
			byte Data = (byte) itemYaml.getInt(consumeItemList[count]+".Data");
			
			if(count > consumeItemList.length || loc >= 45) break;
			Stack2(ItemName, ID,Data,1,Arrays.asList(LoreCreater(Integer.parseInt(consumeItemList[count]))), loc, inv);
			loc++;
		}
		
		if(consumeItemList.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 아이템", 386,0,1,Arrays.asList("§7새로운 아이템을 생성합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void ChooseUseableItemTypeGUI(Player player, int number)
	{
		String UniqueCode = "§0§0§3§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0소모성 아이템 타입");

		Stack2("§f§l[귀환서]", 340,0,1,Arrays.asList("§7특정 위치로 신속히 이동할 수 있는","§7귀환서를 제작합니다."), 2, inv);
		Stack2("§f§l[주문서]", 339,0,1,Arrays.asList("§7특별한 기운이 담긴","§7주문서를 제작합니다."), 3, inv);
		Stack2("§f§l[스킬 북]", 403,0,1,Arrays.asList("§7특정 스킬을 배울 수 있는","§7스킬 북을 제작합니다.","","§c[게임 시스템이 '마비노기'여야 합니다.]"), 4, inv);
		Stack2("§f§l[음식, 포션]", 297,0,1,Arrays.asList("§7퀵슬롯으로 등록이 가능한","§7음식 및 포션 류를 제작합니다."), 5, inv);
		Stack2("§f§l[룬]", 381,0,1,Arrays.asList("§7무기의 능력을 올려주는","§7신비한 룬을 제작합니다."), 6, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ number), 8, inv);
		player.openInventory(inv);
	}
	
	public void NewUseableItemGUI(Player player, int number)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/Consume.yml");

		String UniqueCode = "§0§0§3§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0소모성 아이템 상세 설정");
		String ItemName = itemYaml.getString(number+".DisplayName");
		short ItemID = (short) itemYaml.getInt(number+".ID");
		byte ItemData = (byte) itemYaml.getInt(number+".Data");

		String Type = "";
		String Grade = "";
		for(int counter =0;counter < 13 - itemYaml.getString(number+".Type").length();counter++ )
			Type = Type +" ";
		Type = Type +itemYaml.getString(number+".Type");
		Grade = itemYaml.getString(number+".Grade");
		
		Stack2("§3[    결과물    ]", 58,0,1,null, 9, inv);
		Stack2("§3[    결과물    ]", 58,0,1,null, 10, inv);
		Stack2("§3[    결과물    ]", 58,0,1,null, 11, inv);
		Stack2("§3[    결과물    ]", 58,0,1,null, 18, inv);
		Stack2("§3[    결과물    ]", 58,0,1,null, 20, inv);
		Stack2("§3[    결과물    ]", 58,0,1,null, 27, inv);
		Stack2("§3[    결과물    ]", 58,0,1,null, 28, inv);
		Stack2("§3[    결과물    ]", 58,0,1,null, 29, inv);
		
		Stack2(ItemName, ItemID,ItemData,1,Arrays.asList(LoreCreater(number)), 19, inv);
		
		Stack2("§3[    형식 변경    ]", 421,0,1,Arrays.asList("§f아이템 설명창을","§f변경합니다.","","§f[    현재 형식    ]","       "+ itemYaml.getString(number+".ShowType"),""), 37, inv);
		Stack2("§3[    이름 변경    ]", 421,0,1,Arrays.asList("§f아이템의 이름을","§f변경합니다.",""), 13, inv);
		Stack2("§3[    설명 변경    ]", 386,0,1,Arrays.asList("§f아이템의 설명을","§f변경합니다.",""), 14, inv);
		Stack2("§3[    타입 변경    ]", 166,0,1,Arrays.asList("","§c[타입 변경이 불가능 합니다]",""), 15, inv);
		Stack2("§3[    등급 변경    ]", 266,0,1,Arrays.asList("§f아이템의 등급을","§f변경합니다.","","§f[    현재 등급    ]","       "+Grade,""), 16, inv);
		Stack2("§3[        ID        ]", 405,0,1,Arrays.asList("§f아이템의 ID값을","§f변경합니다.",""), 22, inv);
		Stack2("§3[       DATA       ]", 336,0,1,Arrays.asList("§f아이템의 DATA값을","§f변경합니다.",""), 23, inv);

		switch(ChatColor.stripColor(itemYaml.getString(number+".Type")))
		{
		case "[귀환서]":
			Stack("§3[    위치 지정    ]", 386,0,1,Arrays.asList("§f현재 서 있는 장소를","§f워프 지점으로 등록 합니다.","","§9[현재 등록된 위치]","§9월드 : "+itemYaml.getString(number+".World"),"§9좌표 : "+itemYaml.getInt(number+".X")+","+itemYaml.getInt(number+".Y")+","+itemYaml.getInt(number+".Z"),"","§e[좌 클릭시 현재 지점 등록]",""), 25, inv);
			break;
		case "[주문서]":
			Stack("§3[     스킬 포인트     ]", 403,0,1,Arrays.asList("§f주문서 사용시 즉시","§f스킬 포인트를 얻습니다.",""), 24, inv);
			Stack("§3[     스텟 포인트     ]", 403,0,1,Arrays.asList("§f주문서 사용시 즉시","§f스텟 포인트를 얻습니다.",""), 25, inv);
			Stack("§3[        방어        ]", 307,0,1,Arrays.asList("§f주문서 사용시 방어력을","§f상승 시켜 줍니다.",""), 31, inv);
			Stack("§3[        보호        ]", 306,0,1,Arrays.asList("§f주문서 사용시 보호를","§f상승 시켜 줍니다.",""), 32, inv);
			Stack("§3[      마법 방어      ]", 311,0,1,Arrays.asList("§f주문서 사용시 마법 방어를","§f상승 시켜 줍니다.",""), 33, inv);
			Stack("§3[      마법 보호      ]", 310,0,1,Arrays.asList("§f주문서 사용시 마법 보호를","§f상승 시켜 줍니다.",""), 34, inv);
			Stack("§3[        스텟        ]", 399,0,1,Arrays.asList("§f주문서 사용시 스텟을 영구적으로","§f상승 시켜 줍니다.",""), 40, inv);
			break;
		case "[스킬북]":
			if(itemYaml.getString(number+".Skill").equals("null"))
				Stack("§3[        스킬        ]", 340,0,1,Arrays.asList("§f스킬 북 사용시","§f아래 스킬을 습득합니다.","","§9[현재 등록된 스킬]","§f      없음"), 25, inv);
			else
				Stack("§3[        스킬        ]", 403,0,1,Arrays.asList("§f스킬 북 사용시","§f아래 스킬을 습득합니다.","","§9[현재 등록된 스킬]","§f"+itemYaml.getString(number+".Skill")), 25, inv);
			break;
		case "[소비]":
			Stack("§3[       포만감       ]", 364,0,1,Arrays.asList("§f아이템 사용시 허기를","§f감소 시켜 줍니다.",""), 31, inv);
			Stack("§3[       생명력       ]", 373,8261,1,Arrays.asList("§f아이템 사용시 생명력을","§f상승 시켜 줍니다.",""), 32, inv);
			Stack("§3[        마나        ]", 373,8230,1,Arrays.asList("§f아이템 사용시 마나를","§f상승 시켜 줍니다.",""), 33, inv);
			Stack("§3[        환생        ]", 399,0,1,Arrays.asList("§f아이템 사용시 플레이어의","§f레벨을 초기화 시켜 줍니다.","","§c[서버 시스템이 마비노기일 경우만 사용 가능합니다.]",""), 34, inv);
			break;
		case "[룬]":
			Stack("§3[       대미지       ]", 267,0,1,Arrays.asList("§f룬 장착시 "+Main_ServerOption.damage+"를","§f증가 시켜 줍니다.",""), 24, inv);
			Stack("§3[     마법 대미지     ]", 403,0,1,Arrays.asList("§f룬 장착시 "+Main_ServerOption.magicDamage+"를","§f증가 시켜 줍니다.",""), 25, inv);
			Stack("§3[        방어        ]", 307,0,1,Arrays.asList("§f룬 장착시 방어력을","§f증가 시켜 줍니다.",""), 31, inv);
			Stack("§3[        보호        ]", 306,0,1,Arrays.asList("§f룬 장착시 보호를","§f증가 시켜 줍니다.",""), 32, inv);
			Stack("§3[      마법 방어      ]", 311,0,1,Arrays.asList("§f룬 장착시 마법 방어를","§f증가 시켜 줍니다.",""), 33, inv);
			Stack("§3[      마법 보호      ]", 310,0,1,Arrays.asList("§f룬 장착시 마법 보호를","§f증가 시켜 줍니다.",""), 34, inv);
			Stack("§3[        스텟        ]", 399,0,1,Arrays.asList("§f룬 장착시 스텟을 영구적으로","§f증가 시켜 줍니다.",""), 40, inv);
			Stack("§3[       내구도       ]", 145,2,1,Arrays.asList("§f룬 장착시 아이템의 내구력을","§f증가 시켜 줍니다.","","§c[일반 아이템 불가능]",""), 41, inv);
			//Stack("§3[        개조        ]", 145,0,1,Arrays.asList("§f룬 장착시 최대 개조 횟수를","§f증가 시켜 줍니다.",""), 42, inv);
			break;
		}
		Stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+Type), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+number), 53, inv);
		player.openInventory(inv);
	}
	
	public void SelectSkillGUI(Player player, short page, int ItemNumber)
	{
	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");

		String UniqueCode = "§0§0§3§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0등록 가능 스킬 목록 : " + (page+1));

		String[] skillList= skillYaml.getKeys().toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < skillList.length;count++)
		{
			short JobLevel= (short) skillYaml.getConfigurationSection(skillList[count].toString()+".SkillRank").getKeys(false).size();
			if(count > skillList.length || loc >= 45) break;

		  	YamlLoader jobYaml = new YamlLoader();
			jobYaml.getConfig("Skill/JobList.yml");
			if(jobYaml.contains("Mabinogi.Added."+skillList[count])==true)
			{
				Stack2("§f§l" + skillList[count],  skillYaml.getInt(skillList[count].toString()+".ID"),skillYaml.getInt(skillList[count].toString()+".DATA"),skillYaml.getInt(skillList[count].toString()+".Amount"),Arrays.asList("§3최대 스킬 레벨 : §f"+JobLevel,"","§e[좌 클릭시 스킬 등록]"), loc, inv);
				loc++;	
			}
		}
		
		if(skillList.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ItemNumber), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void UseableItemListGUIClick(InventoryClickEvent event)
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
			if(slot==45)//이전 목록
				new OPbox_GUI().opBoxGuiMain(player, (byte) 2);
			else if(slot == 48)//이전 페이지
				UseableItemListGUI(player, page-1);
			else if(slot == 49)//새 아이템
				ChooseUseableItemTypeGUI(player, ((page*45)+event.getSlot()));
			else if(slot == 50)//다음 페이지
				UseableItemListGUI(player, page+1);
			else
			{
				int number = ((page*45)+event.getSlot());
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
				{
					player.sendMessage("§a[SYSTEM] : 클릭한 아이템을 지급 받았습니다!");
					player.getInventory().addItem(event.getCurrentItem());
				}
				if(event.isLeftClick() == true&&event.isShiftClick()==true)
					NewUseableItemGUI(player, number);
				else if(event.isRightClick()==true&&event.isShiftClick()==true)
				{
				  	YamlLoader itemYaml = new YamlLoader();
					itemYaml.getConfig("Item/Consume.yml");
					short Acount =  (short) (itemYaml.getKeys().toArray().length-1);

					for(int counter = number;counter <Acount;counter++)
						itemYaml.set(counter+"", itemYaml.get((counter+1)+""));
					itemYaml.removeKey(Acount+"");
					itemYaml.saveConfig();
					UseableItemListGUI(player, page);
				}
			}
		}
	}
	
	public void ChooseUseableItemTypeGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 8)
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)
				UseableItemListGUI(player, 0);
			else
			{
				String Type = "";
				String Lore = "";
				String DisplayName = "";
				short ID = 267;
				short Data = 0;
				
				if(slot == 2)
				{
					Type = "§f[귀환서]";
					Lore = "§f육체의 손실 없이 지정된 곳으로%enter%§f빠르게 이동할 수 있는 신비한%enter%§f귀환 주문서이다.";
					DisplayName = "§f0의 귀환 주문서";
					ID = 340;
				}
				else if(slot == 3)
				{
					Type = "§6[주문서]";
					Lore = "§f사용시 스킬 포인트를%enter%§f5만큼 상승시켜 준다.";
					DisplayName ="§f스킬 포인트 5 증가 주문서";
					ID = 339;
				}
				else if(slot == 4)
				{
					Type = "§5[스킬북]";
					Lore = "§f아직 아무것도 쓰여있지 않은%enter%§f빈 상태의 스킬 북이다.%enter% %enter%§f(아무것도 얻을 수 없을 것 같다.)";
					DisplayName = "§f빈 스킬 북";
					ID = 403;
				}
				else if(slot == 5)
				{
					Type = "§d[소비]";
					Lore = "§f퀵 슬롯에 놓고, 위급시%enter%§f사용할 경우, 생명력을%enter%§f10 치료해 주는 포션이다.";
					DisplayName = "§f시뻘건 포션";
					ID = 373;
					Data = 8261;
				}
				else if(slot == 6)
				{
					Type = "§9[룬]";
					Lore = "§f강렬한 녹색의 룬이다.%enter%§f무기의 밸런스를 올려주는 듯 하다.";
					DisplayName ="§f녹색 룬";
					ID = 351;
					Data = 10;
				}

			  	YamlLoader useableItemYaml = new YamlLoader();
				useableItemYaml.getConfig("Item/Consume.yml");
				
				int ItemCounter = useableItemYaml.getKeys().size();
				useableItemYaml.set(ItemCounter+".ShowType","§f[깔끔]");
				useableItemYaml.set(ItemCounter+".ID",ID);
				useableItemYaml.set(ItemCounter+".Data",Data);
				useableItemYaml.set(ItemCounter+".DisplayName",DisplayName);
				useableItemYaml.set(ItemCounter+".Lore",Lore);
				useableItemYaml.set(ItemCounter+".Type",Type);
				useableItemYaml.set(ItemCounter+".Grade","§f[일반]");
				
				if(slot == 2)
				{
					useableItemYaml.set(ItemCounter+".World",player.getLocation().getWorld().getName().toString());
					useableItemYaml.set(ItemCounter+".X",0);
					useableItemYaml.set(ItemCounter+".Y",0);
					useableItemYaml.set(ItemCounter+".Z",0);
					useableItemYaml.set(ItemCounter+".Pitch",0);
					useableItemYaml.set(ItemCounter+".Yaw",0);
				}
				else if(slot ==3)
				{
					useableItemYaml.set(ItemCounter+".DEF",0);
					useableItemYaml.set(ItemCounter+".Protect",0);
					useableItemYaml.set(ItemCounter+".MaDEF",0);
					useableItemYaml.set(ItemCounter+".MaProtect",0);
					useableItemYaml.set(ItemCounter+".STR",0);
					useableItemYaml.set(ItemCounter+".DEX",0);
					useableItemYaml.set(ItemCounter+".INT",0);
					useableItemYaml.set(ItemCounter+".WILL",0);
					useableItemYaml.set(ItemCounter+".LUK",0);
					useableItemYaml.set(ItemCounter+".Balance",0);
					useableItemYaml.set(ItemCounter+".Critical",0);
					useableItemYaml.set(ItemCounter+".HP",0);
					useableItemYaml.set(ItemCounter+".MP",0);
					useableItemYaml.set(ItemCounter+".SkillPoint",5);
					useableItemYaml.set(ItemCounter+".StatPoint",0);
				}
				else if(slot ==4)
					useableItemYaml.set(ItemCounter+".Skill","null");
				else if(slot ==5)
				{
					useableItemYaml.set(ItemCounter+".HP",10);
					useableItemYaml.set(ItemCounter+".MP",0);
					useableItemYaml.set(ItemCounter+".Saturation",0);
					useableItemYaml.set(ItemCounter+".Rebirth",false);
				}
				else if(slot ==6)
				{
					useableItemYaml.set(ItemCounter+".MinDamage",0);
					useableItemYaml.set(ItemCounter+".MaxDamage",0);
					useableItemYaml.set(ItemCounter+".MinMaDamage",0);
					useableItemYaml.set(ItemCounter+".MaxMaDamage",0);
					useableItemYaml.set(ItemCounter+".DEF",0);
					useableItemYaml.set(ItemCounter+".Protect",0);
					useableItemYaml.set(ItemCounter+".MaDEF",0);
					useableItemYaml.set(ItemCounter+".MaProtect",0);
					useableItemYaml.set(ItemCounter+".Durability",0);
					useableItemYaml.set(ItemCounter+".MaxDurability",0);
					useableItemYaml.set(ItemCounter+".STR",0);
					useableItemYaml.set(ItemCounter+".DEX",0);
					useableItemYaml.set(ItemCounter+".INT",0);
					useableItemYaml.set(ItemCounter+".WILL",0);
					useableItemYaml.set(ItemCounter+".LUK",0);
					useableItemYaml.set(ItemCounter+".Balance",10);
					useableItemYaml.set(ItemCounter+".Critical",0);
					useableItemYaml.set(ItemCounter+".HP",0);
					useableItemYaml.set(ItemCounter+".MP",0);
				}
				useableItemYaml.saveConfig();
				NewUseableItemGUI(player,ItemCounter);
			}
		}
	}

	public void NewUseableItemGUIclick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		String IconName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
		
		int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		if(IconName.equals("[        스킬        ]"))
		{
		  	YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			
			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				SelectSkillGUI(player, (short) 0, itemnumber);
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[스킬 북 생성] : 현재 서버 시스템이 §e'마비노기'§c가 아닙니다!");
			}
		}
		else if(IconName.equals("[    위치 지정    ]"))
		{
		  	YamlLoader useableItemYaml = new YamlLoader();
			useableItemYaml.getConfig("Item/Consume.yml");
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			useableItemYaml.set(itemnumber+".World", player.getLocation().getWorld().getName().toString());
			useableItemYaml.set(itemnumber+".X", player.getLocation().getX());
			useableItemYaml.set(itemnumber+".Y", player.getLocation().getY());
			useableItemYaml.set(itemnumber+".Z", player.getLocation().getZ());
			useableItemYaml.set(itemnumber+".Pitch", player.getLocation().getPitch());
			useableItemYaml.set(itemnumber+".Yaw", player.getLocation().getYaw());
			useableItemYaml.saveConfig();
			NewUseableItemGUI(player, itemnumber);
		}
		else if(IconName.equals("[        환생        ]"))
		{
		  	YamlLoader useableItemYaml = new YamlLoader();
			useableItemYaml.getConfig("Item/Consume.yml");
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(useableItemYaml.getBoolean(itemnumber+".Rebirth") == false)
				useableItemYaml.set(itemnumber+".Rebirth", true);
			else
				useableItemYaml.set(itemnumber+".Rebirth", false);
			useableItemYaml.saveConfig();
			NewUseableItemGUI(player, itemnumber);
		}
		else if(IconName.equals("[    형식 변경    ]"))
		{
		  	YamlLoader useableItemYaml = new YamlLoader();
			useableItemYaml.getConfig("Item/Consume.yml");
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(useableItemYaml.getString(itemnumber+".ShowType").contains("[깔끔]"))
				useableItemYaml.set(itemnumber+".ShowType","§e[컬러]");
			else if(useableItemYaml.getString(itemnumber+".ShowType").contains("[컬러]"))
				useableItemYaml.set(itemnumber+".ShowType","§d[기호]");
			else if(useableItemYaml.getString(itemnumber+".ShowType").contains("[기호]"))
				useableItemYaml.set(itemnumber+".ShowType","§9[분류]");
			else if(useableItemYaml.getString(itemnumber+".ShowType").contains("[분류]"))
				useableItemYaml.set(itemnumber+".ShowType","§f[깔끔]");
			useableItemYaml.saveConfig();
			NewUseableItemGUI(player, itemnumber);
		}
		else if(IconName.equals("[    타입 변경    ]"))
			SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 0.8F, 1.8F);
		else if(IconName.equals("[    등급 변경    ]"))
		{
		  	YamlLoader useableItemYaml = new YamlLoader();
			useableItemYaml.getConfig("Item/Consume.yml");
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(useableItemYaml.getString(itemnumber+".Grade").contains("[일반]"))
				useableItemYaml.set(itemnumber+".Grade","§a[상급]");
			else if(useableItemYaml.getString(itemnumber+".Grade").contains("[상급]"))
				useableItemYaml.set(itemnumber+".Grade","§9[매직]");
			else if(useableItemYaml.getString(itemnumber+".Grade").contains("[매직]"))
				useableItemYaml.set(itemnumber+".Grade","§e[레어]");
			else if(useableItemYaml.getString(itemnumber+".Grade").contains("[레어]"))
				useableItemYaml.set(itemnumber+".Grade","§5[에픽]");
			else if(useableItemYaml.getString(itemnumber+".Grade").contains("[에픽]"))
				useableItemYaml.set(itemnumber+".Grade","§6[전설]");
			else if(useableItemYaml.getString(itemnumber+".Grade").contains("[전설]"))
				useableItemYaml.set(itemnumber+".Grade","§4§l[§6§l초§2§l월§1§l]");
			else if(useableItemYaml.getString(itemnumber+".Grade").contains("초"))
				useableItemYaml.set(itemnumber+".Grade","§7[하급]");
			else if(useableItemYaml.getString(itemnumber+".Grade").contains("[하급]"))
				useableItemYaml.set(itemnumber+".Grade","§f[일반]");
			useableItemYaml.saveConfig();
			NewUseableItemGUI(player, itemnumber);
		}
		else if(IconName.equals("이전 목록"))
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			UseableItemListGUI(player, 0);
		}
		else if(IconName.equals("닫기"))
		{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
		}
		else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
		{
			UserData_Object u = new UserData_Object();
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "UseableItem");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -1);
			player.closeInventory();
			if(IconName.equals("[       포만감       ]"))
			{
				player.sendMessage("§3[아이템] : 회복할 포만감을 입력해 주세요!");
				u.setString(player, (byte)1, "Saturation");
			}
			else if(IconName.equals("[       생명력       ]"))
			{
				player.sendMessage("§3[아이템] : 회복할 생명력을 입력해 주세요!");
				u.setString(player, (byte)1, "HP");
				u.setInt(player, (byte)4, -8);
			}
			else if(IconName.equals("[        마나        ]"))
			{
				player.sendMessage("§3[아이템] : 회복할 마나를 입력해 주세요!");
				u.setString(player, (byte)1, "MP");
				u.setInt(player, (byte)4, -8);
			}
			else if(IconName.equals("[     스킬 포인트     ]"))
			{
				player.sendMessage("§3[아이템] : 주고자 하는 스킬 포인트의 양을 입력해 주세요!");
				u.setString(player, (byte)1, "SkillPoint");
			}
			else if(IconName.equals("[     스텟 포인트     ]"))
			{
				player.sendMessage("§3[아이템] : 주고자 하는 스텟 포인트의 양을 입력해 주세요!");
				u.setString(player, (byte)1, "StatPoint");
			}
			else if(IconName.equals("[        ID        ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 ID 값을 입력해 주세요!");
				u.setString(player, (byte)1, "ID");
			}
			else if(IconName.equals("[       DATA       ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 DATA 값을 입력해 주세요!");
				u.setString(player, (byte)1, "Data");
			}
			else if(IconName.equals("[       대미지       ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 최소 "+Main_ServerOption.damage+"를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MinDamage");
			}
			else if(IconName.equals("[     마법 대미지     ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 최소 "+Main_ServerOption.magicDamage+"를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MinMaDamage");
			}
			else if(IconName.equals("[        방어        ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 방어력을 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "DEF");
			}
			else if(IconName.equals("[        보호        ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 보호를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Protect");
			}
			else if(IconName.equals("[      마법 방어      ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 마법 방어력을 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaDEF");
			}
			else if(IconName.equals("[      마법 보호      ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 마법 보호를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaProtect");
			}
			else if(IconName.equals("[        스텟        ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 생명력 보너스를 입력해 주세요!");
				player.sendMessage("§3(-127 ~ 127)");
				u.setString(player, (byte)1, "HP");
			}
			else if(IconName.equals("[       내구도       ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 최대 내구력을 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaxDurability");
			}
			else
			{
				if(IconName.equals("[    이름 변경    ]"))
				{
					player.sendMessage("§3[아이템] : 아이템의 이름을 입력해 주세요!");
					u.setString(player, (byte)1, "DisplayName");
				}
				else if(IconName.equals("[    설명 변경    ]"))
				{
					player.sendMessage("§3[아이템] : 아이템의 설명을 입력해 주세요!");
					player.sendMessage("§6%enter%§f - 한줄 띄워 쓰기 -");
					u.setString(player, (byte)1, "Lore");
				}
				player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
				"§3&3 §4&4 §5&5 " +
						"§6&6 §7&7 §8&8 " +
				"§9&9 §a&a §b&b §c&c " +
						"§d&d §e&e §f&f");
			}
			
		}
	}
	
	public void SelectSkillGUIClick(InventoryClickEvent event)
	{
		
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				NewUseableItemGUI(player, itemnumber);
			else if(slot == 48)//이전 페이지
				SelectSkillGUI(player, (short) (page-1), itemnumber);
			else if(slot == 50)//다음 페이지
				SelectSkillGUI(player, (short) (page-1), itemnumber);
			else
			{
			  	YamlLoader useableItemYaml = new YamlLoader();
				useableItemYaml.getConfig("Item/Consume.yml");
				useableItemYaml.set(itemnumber+".Skill", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				useableItemYaml.saveConfig();
				NewUseableItemGUI(player, itemnumber);
			}
		}
	}
	
	
	public String[] LoreCreater(int ItemNumber)
	{
	  	YamlLoader useableItemYaml = new YamlLoader();
		useableItemYaml.getConfig("Item/Consume.yml");
		String lore = "";
		String Type = ChatColor.stripColor(useableItemYaml.getString(ItemNumber+".ShowType"));

		lore = lore+useableItemYaml.getString(ItemNumber+".Type");
		for(int count = 0; count<20-useableItemYaml.getString(ItemNumber+".Type").length();count++)
			lore = lore+" ";
		lore = lore+useableItemYaml.getString(ItemNumber+".Grade")+"%enter% %enter%";
		
		switch(Type)
		{
		case "[분류]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(ItemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 Ω 월드 : §f" + useableItemYaml.getString(ItemNumber+".World")+"%enter%";
				lore = lore+"§9 Ω X 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+"§9 Ω Y 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+"§9 Ω Z 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+"§3 + 스텟 포인트 : " + useableItemYaml.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+"§c - 스텟 포인트 : " + useableItemYaml.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+"§3 + 스킬 포인트 : " + useableItemYaml.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+"§c - 스킬 포인트 : " + useableItemYaml.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".STR") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".STR") < 0)
						lore = lore+"§c - "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".DEX") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".DEX") < 0)
						lore = lore+"§c - "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".INT") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".INT") < 0)
						lore = lore+"§c - "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".WILL") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".WILL") < 0)
						lore = lore+"§c - "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".LUK") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".LUK") < 0)
						lore = lore+"§c - "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(ItemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
					lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
					lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
					lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
					lore = lore+"§c - 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+"§3 + 포만감 : " + useableItemYaml.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+"§c - 포만감 : " + useableItemYaml.getInt(ItemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+"§3 + 최소 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+"§c - 최소 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+"§3 + 최대 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+"§c - 최대 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+"§3 + 최소 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+"§c - 최소 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 + 최대 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+"§c - 최대 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(ItemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";

				if(useableItemYaml.getInt(ItemNumber+".Durability")>0)
					lore = lore+"§3 + 현재 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".Durability")<0)
					lore = lore+"§c - 현재 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+"§3 + 최대 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+"§c - 최대 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";


				if(useableItemYaml.getInt(ItemNumber+".HP")!=0||useableItemYaml.getInt(ItemNumber+".MP")!=0||
				useableItemYaml.getInt(ItemNumber+".STR")!=0||useableItemYaml.getInt(ItemNumber+".DEX")!=0||
				useableItemYaml.getInt(ItemNumber+".INT")!=0||useableItemYaml.getInt(ItemNumber+".WILL")!=0||
				useableItemYaml.getInt(ItemNumber+".LUK")!=0)
				{
					if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".STR") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".STR") < 0)
						lore = lore+"§c - "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".DEX") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".DEX") < 0)
						lore = lore+"§c - "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".INT") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".INT") < 0)
						lore = lore+"§c - "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".WILL") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".WILL") < 0)
						lore = lore+"§c - "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".LUK") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".LUK") < 0)
						lore = lore+"§c - "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 + 개조 : " +useableItemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c - 개조 : " +useableItemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			}

			lore = lore+"%enter%§6___--------------------___%enter%§6       [아이템 설명]";
			lore = lore+"%enter%"+ useableItemYaml.getString(ItemNumber+".Lore")+"%enter%";
			lore = lore+"§6---____________________---%enter%";
		}
		break;
		case "[기호]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(ItemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 Ω 월드 : §f" + useableItemYaml.getString(ItemNumber+".World")+"%enter%";
				lore = lore+"§9 Ω X 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+"§9 Ω Y 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+"§9 Ω Z 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+"§3 + 스텟 포인트 : " + useableItemYaml.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+"§c - 스텟 포인트 : " + useableItemYaml.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+"§3 + 스킬 포인트 : " + useableItemYaml.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+"§c - 스킬 포인트 : " + useableItemYaml.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".STR") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".STR") < 0)
						lore = lore+"§c - "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".DEX") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".DEX") < 0)
						lore = lore+"§c - "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".INT") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".INT") < 0)
						lore = lore+"§c - "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".WILL") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".WILL") < 0)
						lore = lore+"§c - "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".LUK") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".LUK") < 0)
						lore = lore+"§c - "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(ItemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
					lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
					lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
					lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
					lore = lore+"§c - 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+"§3 + 포만감 : " + useableItemYaml.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+"§c - 포만감 : " + useableItemYaml.getInt(ItemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+"§3 + 최소 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+"§c - 최소 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+"§3 + 최대 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+"§c - 최대 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+"§3 + 최소 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+"§c - 최소 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 + 최대 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+"§c - 최대 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(ItemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Durability")>0)
					lore = lore+"§3 + 현재 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".Durability")<0)
					lore = lore+"§c - 현재 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+"§3 + 최대 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+"§c - 최대 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";

				if(useableItemYaml.getInt(ItemNumber+".HP")!=0||useableItemYaml.getInt(ItemNumber+".MP")!=0||
				useableItemYaml.getInt(ItemNumber+".STR")!=0||useableItemYaml.getInt(ItemNumber+".DEX")!=0||
				useableItemYaml.getInt(ItemNumber+".INT")!=0||useableItemYaml.getInt(ItemNumber+".WILL")!=0||
				useableItemYaml.getInt(ItemNumber+".LUK")!=0)
				{
					if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".STR") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".STR") < 0)
						lore = lore+"§c - "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".DEX") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".DEX") < 0)
						lore = lore+"§c - "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".INT") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".INT") < 0)
						lore = lore+"§c - "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".WILL") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".WILL") < 0)
						lore = lore+"§c - "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".LUK") > 0)
						lore = lore+"§3 + "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".LUK") < 0)
						lore = lore+"§c - "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 + 개조 : " +useableItemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c - 개조 : " +useableItemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			}			
			
			lore = lore+"%enter%"+ useableItemYaml.getString(ItemNumber+".Lore")+"%enter%%enter%";

		}
		break;
		case "[컬러]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(ItemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 월드 : §f" + useableItemYaml.getString(ItemNumber+".World")+"%enter%";
				lore = lore+"§9 X 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+"§9 Y 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+"§9 Z 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+"§3 스텟 포인트 : " + useableItemYaml.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+"§c 스텟 포인트 : " + useableItemYaml.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+"§3 스킬 포인트 : " + useableItemYaml.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+"§c 스킬 포인트 : " + useableItemYaml.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".DEF")>0)
					lore = lore+"§3 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".DEF")<0)
					lore = lore+"§c 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")>0)
					lore = lore+"§3 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")<0)
					lore = lore+"§c 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")>0)
					lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")<0)
					lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")>0)
					lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")<0)
					lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".STR") > 0)
						lore = lore+"§3 "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".STR") < 0)
						lore = lore+"§c "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".DEX") > 0)
						lore = lore+"§3 "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".DEX") < 0)
						lore = lore+"§c "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".INT") > 0)
						lore = lore+"§3 "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".INT") < 0)
						lore = lore+"§c "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".WILL") > 0)
						lore = lore+"§3 "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".WILL") < 0)
						lore = lore+"§c "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".LUK") > 0)
						lore = lore+"§3 "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".LUK") < 0)
						lore = lore+"§c "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(ItemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
					lore = lore+"§3 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
					lore = lore+"§c 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
					lore = lore+"§3 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
					lore = lore+"§c 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+"§3 포만감 : " + useableItemYaml.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+"§c 포만감 : " + useableItemYaml.getInt(ItemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+"§3 최소 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+"§c 최소 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+"§3 최대 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+"§c 최대 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+"§3 최소 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+"§c 최소 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 최대 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+"§c 최대 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(ItemNumber+".DEF")>0)
					lore = lore+"§3 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".DEF")<0)
					lore = lore+"§c 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")>0)
					lore = lore+"§3 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Protect")<0)
					lore = lore+"§c 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")>0)
					lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Balance")<0)
					lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")>0)
					lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".Critical")<0)
					lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";

				if(useableItemYaml.getInt(ItemNumber+".Durability")>0)
					lore = lore+"§3 현재 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".Durability")<0)
					lore = lore+"§c 현재 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+"§3 최대 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+"§c 최대 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";


					if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".STR") > 0)
						lore = lore+"§3 "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".STR") < 0)
						lore = lore+"§c "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".DEX") > 0)
						lore = lore+"§3 "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".DEX") < 0)
						lore = lore+"§c "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".INT") > 0)
						lore = lore+"§3 "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".INT") < 0)
						lore = lore+"§c "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".WILL") > 0)
						lore = lore+"§3 "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".WILL") < 0)
						lore = lore+"§c "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".LUK") > 0)
						lore = lore+"§3 "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".LUK") < 0)
						lore = lore+"§c "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 개조 : " +useableItemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c 개조 : " +useableItemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				break;
			}
			
			lore = lore+"%enter%"+ useableItemYaml.getString(ItemNumber+".Lore")+"%enter%%enter%";


		}
		break;
		
			case "[깔끔]":
			{
				switch(ChatColor.stripColor(useableItemYaml.getString(ItemNumber+".Type")))
				{
				case "[귀환서]":
					lore = lore+"§f 월드 : §f" + useableItemYaml.getString(ItemNumber+".World")+"%enter%";
					lore = lore+"§f X 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".X")+"%enter%";
					lore = lore+"§f Y 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".Y")+"%enter%";
					lore = lore+"§f Z 좌표 : §f" + useableItemYaml.getInt(ItemNumber+".Z")+"%enter%";
					break;
				case "[주문서]":
					if(useableItemYaml.getInt(ItemNumber+".StatPoint")>0)
						lore = lore+"§3 스텟 포인트 : " + useableItemYaml.getInt(ItemNumber+".StatPoint")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".StatPoint")<0)
						lore = lore+"§c 스텟 포인트 : " + useableItemYaml.getInt(ItemNumber+".StatPoint")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".SkillPoint")>0)
						lore = lore+"§3 스킬 포인트 : " + useableItemYaml.getInt(ItemNumber+".SkillPoint")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".SkillPoint")<0)
						lore = lore+"§c 스킬 포인트 : " + useableItemYaml.getInt(ItemNumber+".SkillPoint")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".DEF")>0)
						lore = lore+"§3 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".DEF")<0)
						lore = lore+"§c 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Protect")>0)
						lore = lore+"§3 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Protect")<0)
						lore = lore+"§c 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaDEF")>0)
						lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaDEF")<0)
						lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaProtect")>0)
						lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaProtect")<0)
						lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Balance")>0)
						lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Balance")<0)
						lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Critical")>0)
						lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Critical")<0)
						lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
					
						if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
							lore = lore+"§3 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
							lore = lore+"§c 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
							lore = lore+"§3 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
							lore = lore+"§c 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".STR") > 0)
							lore = lore+"§3 "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".STR") < 0)
							lore = lore+"§c "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".DEX") > 0)
							lore = lore+"§3 "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".DEX") < 0)
							lore = lore+"§c "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".INT") > 0)
							lore = lore+"§3 "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".INT") < 0)
							lore = lore+"§c "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".WILL") > 0)
							lore = lore+"§3 "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".WILL") < 0)
							lore = lore+"§c "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".LUK") > 0)
							lore = lore+"§3 "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".LUK") < 0)
							lore = lore+"§c "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
					break;
				case "[스킬북]":
					if(useableItemYaml.getString(ItemNumber+".Skill").equals("null"))
						lore = lore+"%enter%§f [아무것도 없는 빈 책]%enter%";
					else
						lore = lore+"%enter%§f"  +" [우 클릭시 아래 스킬 획득]%enter%§f + §f"+useableItemYaml.getString(ItemNumber+".Skill")+"%enter%";
					break;
				case "[소비]":
					if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Saturation") > 0)
						lore = lore+"§3 포만감 : " + useableItemYaml.getInt(ItemNumber+".Saturation")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".Saturation") < 0)
						lore = lore+"§c 포만감 : " + useableItemYaml.getInt(ItemNumber+".Saturation")+"%enter%";
					if(useableItemYaml.getBoolean(ItemNumber+".Rebirth") == true)
						lore = lore+"§6§l + 환생%enter%";
					break;
				case "[룬]":
					if(useableItemYaml.getInt(ItemNumber+".MinDamage")>0)
						lore = lore+"§3 최소 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinDamage")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MinDamage")<0)
						lore = lore+"§c 최소 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinDamage")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxDamage")>0)
						lore = lore+"§3 최대 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MaxDamage")<0)
						lore = lore+"§c 최대 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MinMaDamage")>0)
						lore = lore+"§3 최소 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinMaDamage")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MinMaDamage")<0)
						lore = lore+"§c 최소 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MinMaDamage")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxMaDamage")>0)
						lore = lore+"§3 최대 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MaxMaDamage")<0)
						lore = lore+"§c 최대 마법 공격력 : " + useableItemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
					
					
						
					if(useableItemYaml.getInt(ItemNumber+".DEF")>0)
						lore = lore+"§3 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".DEF")<0)
						lore = lore+"§c 방어 : " + useableItemYaml.getInt(ItemNumber+".DEF")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Protect")>0)
						lore = lore+"§3 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Protect")<0)
						lore = lore+"§c 보호 : " + useableItemYaml.getInt(ItemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaDEF")>0)
						lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaDEF")<0)
						lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaProtect")>0)
						lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaProtect")<0)
						lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Balance")>0)
						lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Balance")<0)
						lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(ItemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Critical")>0)
						lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Critical")<0)
						lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(ItemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".Durability")>0)
						lore = lore+"§3 현재 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".Durability")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".Durability")<0)
						lore = lore+"§c 현재 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".Durability")+"%enter%";
					if(useableItemYaml.getInt(ItemNumber+".MaxDurability")>0)
						lore = lore+"§3 최대 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";
					else if(useableItemYaml.getInt(ItemNumber+".MaxDurability")<0)
						lore = lore+"§c 최대 내구도 증가 : " + useableItemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";
					
						if(useableItemYaml.getInt(ItemNumber+".HP") > 0)
							lore = lore+"§3 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".HP") < 0)
							lore = lore+"§c 생명력 : " + useableItemYaml.getInt(ItemNumber+".HP")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".MP") > 0)
							lore = lore+"§3 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".MP") < 0)
							lore = lore+"§c 마나 : " + useableItemYaml.getInt(ItemNumber+".MP")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".STR") > 0)
							lore = lore+"§3 "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".STR") < 0)
							lore = lore+"§c "+Main_ServerOption.statSTR+" : " + useableItemYaml.getInt(ItemNumber+".STR")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".DEX") > 0)
							lore = lore+"§3 "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".DEX") < 0)
							lore = lore+"§c "+Main_ServerOption.statDEX+" : " + useableItemYaml.getInt(ItemNumber+".DEX")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".INT") > 0)
							lore = lore+"§3 "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".INT") < 0)
							lore = lore+"§c "+Main_ServerOption.statINT+" : " + useableItemYaml.getInt(ItemNumber+".INT")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".WILL") > 0)
							lore = lore+"§3 "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".WILL") < 0)
							lore = lore+"§c "+Main_ServerOption.statWILL+" : " + useableItemYaml.getInt(ItemNumber+".WILL")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".LUK") > 0)
							lore = lore+"§3 "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
						else if(useableItemYaml.getInt(ItemNumber+".LUK") < 0)
							lore = lore+"§c "+Main_ServerOption.statLUK+" : " + useableItemYaml.getInt(ItemNumber+".LUK")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
							lore = lore+"§5 개조 : " +useableItemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
						if(useableItemYaml.getInt(ItemNumber+".MaxUpgrade") < 0)
							lore = lore+"§3 개조 : " +useableItemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					break;
				}
				
				lore = lore+"%enter%"+ useableItemYaml.getString(ItemNumber+".Lore")+"%enter%%enter%";

			}
			break;
		}

		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}
	
}
