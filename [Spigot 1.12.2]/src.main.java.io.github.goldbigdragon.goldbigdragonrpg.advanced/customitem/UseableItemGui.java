package customitem;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import admin.OPboxGui;
import effect.SoundEffect;
import main.MainServerOption;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UseableItemGui extends UtilGui
{
	public void useableItemListGui(Player player, int page)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/Consume.yml");

		String uniqueCode = "§0§0§3§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0소모성 아이템 목록 : " + (page+1));

		String[] consumeItemList = itemYaml.getKeys().toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < consumeItemList.length;count++)
		{
			String ItemName = itemYaml.getString(consumeItemList[count]+".DisplayName");
			short ID = (short) itemYaml.getInt(consumeItemList[count]+".ID");
			byte Data = (byte) itemYaml.getInt(consumeItemList[count]+".Data");
			
			if(count > consumeItemList.length || loc >= 45) break;
			removeFlagStack(ItemName, ID,Data,1,Arrays.asList(loreCreater(Integer.parseInt(consumeItemList[count]))), loc, inv);
			loc++;
		}
		
		if(consumeItemList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l새 아이템", 386,0,1,Arrays.asList("§7새로운 아이템을 생성합니다."), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void chooseUseableItemTypeGui(Player player, int number)
	{
		String uniqueCode = "§0§0§3§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 9, uniqueCode + "§0소모성 아이템 타입");

		removeFlagStack("§f§l[귀환서]", 340,0,1,Arrays.asList("§7특정 위치로 신속히 이동할 수 있는","§7귀환서를 제작합니다."), 2, inv);
		removeFlagStack("§f§l[주문서]", 339,0,1,Arrays.asList("§7특별한 기운이 담긴","§7주문서를 제작합니다."), 3, inv);
		removeFlagStack("§f§l[스킬 북]", 403,0,1,Arrays.asList("§7특정 스킬을 배울 수 있는","§7스킬 북을 제작합니다.","","§c[게임 시스템이 '마비노기'여야 합니다.]"), 4, inv);
		removeFlagStack("§f§l[음식, 포션]", 297,0,1,Arrays.asList("§7퀵슬롯으로 등록이 가능한","§7음식 및 포션 류를 제작합니다."), 5, inv);
		removeFlagStack("§f§l[룬]", 381,0,1,Arrays.asList("§7무기의 능력을 올려주는","§7신비한 룬을 제작합니다."), 6, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ number), 8, inv);
		player.openInventory(inv);
	}
	
	public void newUseableItemGui(Player player, int number)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/Consume.yml");

		String uniqueCode = "§0§0§3§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0소모성 아이템 상세 설정");
		String itemName = itemYaml.getString(number+".DisplayName");
		short itemId = (short) itemYaml.getInt(number+".ID");
		byte itemData = (byte) itemYaml.getInt(number+".Data");

		String type = "";
		String grade = "";
		for(int counter =0;counter < 13 - itemYaml.getString(number+".Type").length();counter++ )
			type = type +" ";
		type = type +itemYaml.getString(number+".Type");
		grade = itemYaml.getString(number+".Grade");
		
		removeFlagStack("§3[    결과물    ]", 58,0,1,null, 9, inv);
		removeFlagStack("§3[    결과물    ]", 58,0,1,null, 10, inv);
		removeFlagStack("§3[    결과물    ]", 58,0,1,null, 11, inv);
		removeFlagStack("§3[    결과물    ]", 58,0,1,null, 18, inv);
		removeFlagStack("§3[    결과물    ]", 58,0,1,null, 20, inv);
		removeFlagStack("§3[    결과물    ]", 58,0,1,null, 27, inv);
		removeFlagStack("§3[    결과물    ]", 58,0,1,null, 28, inv);
		removeFlagStack("§3[    결과물    ]", 58,0,1,null, 29, inv);
		
		removeFlagStack(itemName, itemId,itemData,1,Arrays.asList(loreCreater(number)), 19, inv);
		
		removeFlagStack("§3[    형식 변경    ]", 421,0,1,Arrays.asList("§f아이템 설명창을","§f변경합니다.","","§f[    현재 형식    ]","       "+ itemYaml.getString(number+".ShowType"),""), 37, inv);
		removeFlagStack("§3[    이름 변경    ]", 421,0,1,Arrays.asList("§f아이템의 이름을","§f변경합니다.",""), 13, inv);
		removeFlagStack("§3[    설명 변경    ]", 386,0,1,Arrays.asList("§f아이템의 설명을","§f변경합니다.",""), 14, inv);
		removeFlagStack("§3[    타입 변경    ]", 166,0,1,Arrays.asList("","§c[타입 변경이 불가능 합니다]",""), 15, inv);
		removeFlagStack("§3[    등급 변경    ]", 266,0,1,Arrays.asList("§f아이템의 등급을","§f변경합니다.","","§f[    현재 등급    ]","       "+grade,""), 16, inv);
		removeFlagStack("§3[        ID        ]", 405,0,1,Arrays.asList("§f아이템의 ID값을","§f변경합니다.",""), 22, inv);
		removeFlagStack("§3[       DATA       ]", 336,0,1,Arrays.asList("§f아이템의 DATA값을","§f변경합니다.",""), 23, inv);

		switch(ChatColor.stripColor(itemYaml.getString(number+".Type")))
		{
		case "[귀환서]":
			stack("§3[    위치 지정    ]", 386,0,1,Arrays.asList("§f현재 서 있는 장소를","§f워프 지점으로 등록 합니다.","","§9[현재 등록된 위치]","§9월드 : "+itemYaml.getString(number+".World"),"§9좌표 : "+itemYaml.getInt(number+".X")+","+itemYaml.getInt(number+".Y")+","+itemYaml.getInt(number+".Z"),"","§e[좌 클릭시 현재 지점 등록]",""), 25, inv);
			break;
		case "[주문서]":
			stack("§3[     스킬 포인트     ]", 403,0,1,Arrays.asList("§f주문서 사용시 즉시","§f스킬 포인트를 얻습니다.",""), 24, inv);
			stack("§3[     스텟 포인트     ]", 403,0,1,Arrays.asList("§f주문서 사용시 즉시","§f스텟 포인트를 얻습니다.",""), 25, inv);
			stack("§3[        방어        ]", 307,0,1,Arrays.asList("§f주문서 사용시 방어력을","§f상승 시켜 줍니다.",""), 31, inv);
			stack("§3[        보호        ]", 306,0,1,Arrays.asList("§f주문서 사용시 보호를","§f상승 시켜 줍니다.",""), 32, inv);
			stack("§3[      마법 방어      ]", 311,0,1,Arrays.asList("§f주문서 사용시 마법 방어를","§f상승 시켜 줍니다.",""), 33, inv);
			stack("§3[      마법 보호      ]", 310,0,1,Arrays.asList("§f주문서 사용시 마법 보호를","§f상승 시켜 줍니다.",""), 34, inv);
			stack("§3[        스텟        ]", 399,0,1,Arrays.asList("§f주문서 사용시 스텟을 영구적으로","§f상승 시켜 줍니다.",""), 40, inv);
			break;
		case "[스킬북]":
			if(itemYaml.getString(number+".Skill").equals("null"))
				stack("§3[        스킬        ]", 340,0,1,Arrays.asList("§f스킬 북 사용시","§f아래 스킬을 습득합니다.","","§9[현재 등록된 스킬]","§f      없음"), 25, inv);
			else
				stack("§3[        스킬        ]", 403,0,1,Arrays.asList("§f스킬 북 사용시","§f아래 스킬을 습득합니다.","","§9[현재 등록된 스킬]","§f"+itemYaml.getString(number+".Skill")), 25, inv);
			break;
		case "[소비]":
			stack("§3[       포만감       ]", 364,0,1,Arrays.asList("§f아이템 사용시 허기를","§f감소 시켜 줍니다.",""), 31, inv);
			stack("§3[       생명력       ]", 373,8261,1,Arrays.asList("§f아이템 사용시 생명력을","§f상승 시켜 줍니다.",""), 32, inv);
			stack("§3[        마나        ]", 373,8230,1,Arrays.asList("§f아이템 사용시 마나를","§f상승 시켜 줍니다.",""), 33, inv);
			stack("§3[        환생        ]", 399,0,1,Arrays.asList("§f아이템 사용시 플레이어의","§f레벨을 초기화 시켜 줍니다.","","§c[서버 시스템이 마비노기일 경우만 사용 가능합니다.]",""), 34, inv);
			break;
		case "[룬]":
			stack("§3[       대미지       ]", 267,0,1,Arrays.asList("§f룬 장착시 "+MainServerOption.damage+"를","§f증가 시켜 줍니다.",""), 24, inv);
			stack("§3[     마법 대미지     ]", 403,0,1,Arrays.asList("§f룬 장착시 "+MainServerOption.magicDamage+"를","§f증가 시켜 줍니다.",""), 25, inv);
			stack("§3[        방어        ]", 307,0,1,Arrays.asList("§f룬 장착시 방어력을","§f증가 시켜 줍니다.",""), 31, inv);
			stack("§3[        보호        ]", 306,0,1,Arrays.asList("§f룬 장착시 보호를","§f증가 시켜 줍니다.",""), 32, inv);
			stack("§3[      마법 방어      ]", 311,0,1,Arrays.asList("§f룬 장착시 마법 방어를","§f증가 시켜 줍니다.",""), 33, inv);
			stack("§3[      마법 보호      ]", 310,0,1,Arrays.asList("§f룬 장착시 마법 보호를","§f증가 시켜 줍니다.",""), 34, inv);
			stack("§3[        스텟        ]", 399,0,1,Arrays.asList("§f룬 장착시 스텟을 영구적으로","§f증가 시켜 줍니다.",""), 40, inv);
			stack("§3[       내구도       ]", 145,2,1,Arrays.asList("§f룬 장착시 아이템의 내구력을","§f증가 시켜 줍니다.","","§c[일반 아이템 불가능]",""), 41, inv);
			//Stack("§3[        개조        ]", 145,0,1,Arrays.asList("§f룬 장착시 최대 개조 횟수를","§f증가 시켜 줍니다.",""), 42, inv);
			break;
		}
		stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+type), 45, inv);
		stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+number), 53, inv);
		player.openInventory(inv);
	}
	
	public void selectSkillGui(Player player, short page, int itemNumber)
	{
	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");

		String uniqueCode = "§0§0§3§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0등록 가능 스킬 목록 : " + (page+1));

		String[] skillList= skillYaml.getKeys().toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < skillList.length;count++)
		{
			short jobLevel= (short) skillYaml.getConfigurationSection(skillList[count].toString()+".SkillRank").getKeys(false).size();
			if(count > skillList.length || loc >= 45) break;

		  	YamlLoader jobYaml = new YamlLoader();
			jobYaml.getConfig("Skill/JobList.yml");
			if(jobYaml.contains("Mabinogi.Added."+skillList[count])==true)
			{
				removeFlagStack("§f§l" + skillList[count],  skillYaml.getInt(skillList[count].toString()+".ID"),skillYaml.getInt(skillList[count].toString()+".DATA"),skillYaml.getInt(skillList[count].toString()+".Amount"),Arrays.asList("§3최대 스킬 레벨 : §f"+jobLevel,"","§e[좌 클릭시 스킬 등록]"), loc, inv);
				loc++;	
			}
		}
		
		if(skillList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+itemNumber), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void useableItemListGuiClick(InventoryClickEvent event)
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
			if(slot==45)//이전 목록
				new OPboxGui().opBoxGuiMain(player, (byte) 2);
			else if(slot == 48)//이전 페이지
				useableItemListGui(player, page-1);
			else if(slot == 49)//새 아이템
				chooseUseableItemTypeGui(player, ((page*45)+event.getSlot()));
			else if(slot == 50)//다음 페이지
				useableItemListGui(player, page+1);
			else
			{
				int number = ((page*45)+event.getSlot());
				if(event.isLeftClick()&&!event.isShiftClick())
				{
					player.sendMessage("§a[SYSTEM] : 클릭한 아이템을 지급 받았습니다!");
					player.getInventory().addItem(event.getCurrentItem());
				}
				if(event.isLeftClick()&&event.isShiftClick())
					newUseableItemGui(player, number);
				else if(event.isRightClick()&&event.isShiftClick())
				{
				  	YamlLoader itemYaml = new YamlLoader();
					itemYaml.getConfig("Item/Consume.yml");
					short acount =  (short) (itemYaml.getKeys().toArray().length-1);

					for(int counter = number;counter <acount;counter++)
						itemYaml.set(counter+"", itemYaml.get((counter+1)+""));
					itemYaml.removeKey(acount+"");
					itemYaml.saveConfig();
					useableItemListGui(player, page);
				}
			}
		}
	}
	
	public void chooseUseableItemTypeGuiClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 8)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)
				useableItemListGui(player, 0);
			else
			{
				String type = "";
				String lore = "";
				String displayName = "";
				short id = 267;
				short data = 0;
				
				if(slot == 2)
				{
					type = "§f[귀환서]";
					lore = "§f육체의 손실 없이 지정된 곳으로%enter%§f빠르게 이동할 수 있는 신비한%enter%§f귀환 주문서이다.";
					displayName = "§f0의 귀환 주문서";
					id = 340;
				}
				else if(slot == 3)
				{
					type = "§6[주문서]";
					lore = "§f사용시 스킬 포인트를%enter%§f5만큼 상승시켜 준다.";
					displayName ="§f스킬 포인트 5 증가 주문서";
					id = 339;
				}
				else if(slot == 4)
				{
					type = "§5[스킬북]";
					lore = "§f아직 아무것도 쓰여있지 않은%enter%§f빈 상태의 스킬 북이다.%enter% %enter%§f(아무것도 얻을 수 없을 것 같다.)";
					displayName = "§f빈 스킬 북";
					id = 403;
				}
				else if(slot == 5)
				{
					type = "§d[소비]";
					lore = "§f퀵 슬롯에 놓고, 위급시%enter%§f사용할 경우, 생명력을%enter%§f10 치료해 주는 포션이다.";
					displayName = "§f시뻘건 포션";
					id = 373;
					data = 8261;
				}
				else if(slot == 6)
				{
					type = "§9[룬]";
					lore = "§f강렬한 녹색의 룬이다.%enter%§f무기의 밸런스를 올려주는 듯 하다.";
					displayName ="§f녹색 룬";
					id = 351;
					data = 10;
				}

			  	YamlLoader useableItemYaml = new YamlLoader();
				useableItemYaml.getConfig("Item/Consume.yml");
				
				int itemCounter = useableItemYaml.getKeys().size();
				useableItemYaml.set(itemCounter+".ShowType","§f[깔끔]");
				useableItemYaml.set(itemCounter+".ID",id);
				useableItemYaml.set(itemCounter+".Data",data);
				useableItemYaml.set(itemCounter+".DisplayName",displayName);
				useableItemYaml.set(itemCounter+".Lore",lore);
				useableItemYaml.set(itemCounter+".Type",type);
				useableItemYaml.set(itemCounter+".Grade","§f[일반]");
				
				if(slot == 2)
				{
					useableItemYaml.set(itemCounter+".World",player.getLocation().getWorld().getName().toString());
					useableItemYaml.set(itemCounter+".X",0);
					useableItemYaml.set(itemCounter+".Y",0);
					useableItemYaml.set(itemCounter+".Z",0);
					useableItemYaml.set(itemCounter+".Pitch",0);
					useableItemYaml.set(itemCounter+".Yaw",0);
				}
				else if(slot ==3)
				{
					useableItemYaml.set(itemCounter+".DEF",0);
					useableItemYaml.set(itemCounter+".Protect",0);
					useableItemYaml.set(itemCounter+".MaDEF",0);
					useableItemYaml.set(itemCounter+".MaProtect",0);
					useableItemYaml.set(itemCounter+".STR",0);
					useableItemYaml.set(itemCounter+".DEX",0);
					useableItemYaml.set(itemCounter+".INT",0);
					useableItemYaml.set(itemCounter+".WILL",0);
					useableItemYaml.set(itemCounter+".LUK",0);
					useableItemYaml.set(itemCounter+".Balance",0);
					useableItemYaml.set(itemCounter+".Critical",0);
					useableItemYaml.set(itemCounter+".HP",0);
					useableItemYaml.set(itemCounter+".MP",0);
					useableItemYaml.set(itemCounter+".SkillPoint",5);
					useableItemYaml.set(itemCounter+".StatPoint",0);
				}
				else if(slot ==4)
					useableItemYaml.set(itemCounter+".Skill","null");
				else if(slot ==5)
				{
					useableItemYaml.set(itemCounter+".HP",10);
					useableItemYaml.set(itemCounter+".MP",0);
					useableItemYaml.set(itemCounter+".Saturation",0);
					useableItemYaml.set(itemCounter+".Rebirth",false);
				}
				else if(slot ==6)
				{
					useableItemYaml.set(itemCounter+".MinDamage",0);
					useableItemYaml.set(itemCounter+".MaxDamage",0);
					useableItemYaml.set(itemCounter+".MinMaDamage",0);
					useableItemYaml.set(itemCounter+".MaxMaDamage",0);
					useableItemYaml.set(itemCounter+".DEF",0);
					useableItemYaml.set(itemCounter+".Protect",0);
					useableItemYaml.set(itemCounter+".MaDEF",0);
					useableItemYaml.set(itemCounter+".MaProtect",0);
					useableItemYaml.set(itemCounter+".Durability",0);
					useableItemYaml.set(itemCounter+".MaxDurability",0);
					useableItemYaml.set(itemCounter+".STR",0);
					useableItemYaml.set(itemCounter+".DEX",0);
					useableItemYaml.set(itemCounter+".INT",0);
					useableItemYaml.set(itemCounter+".WILL",0);
					useableItemYaml.set(itemCounter+".LUK",0);
					useableItemYaml.set(itemCounter+".Balance",10);
					useableItemYaml.set(itemCounter+".Critical",0);
					useableItemYaml.set(itemCounter+".HP",0);
					useableItemYaml.set(itemCounter+".MP",0);
				}
				useableItemYaml.saveConfig();
				newUseableItemGui(player,itemCounter);
			}
		}
	}

	public void newUseableItemGuiClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		String iconName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
		
		int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		if(iconName.equals("[        스킬        ]"))
		{
		  	YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			
			if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				selectSkillGui(player, (short) 0, itemnumber);
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[스킬 북 생성] : 현재 서버 시스템이 §e'마비노기'§c가 아닙니다!");
			}
		}
		else if(iconName.equals("[    위치 지정    ]"))
		{
		  	YamlLoader useableItemYaml = new YamlLoader();
			useableItemYaml.getConfig("Item/Consume.yml");
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			useableItemYaml.set(itemnumber+".World", player.getLocation().getWorld().getName().toString());
			useableItemYaml.set(itemnumber+".X", player.getLocation().getX());
			useableItemYaml.set(itemnumber+".Y", player.getLocation().getY());
			useableItemYaml.set(itemnumber+".Z", player.getLocation().getZ());
			useableItemYaml.set(itemnumber+".Pitch", player.getLocation().getPitch());
			useableItemYaml.set(itemnumber+".Yaw", player.getLocation().getYaw());
			useableItemYaml.saveConfig();
			newUseableItemGui(player, itemnumber);
		}
		else if(iconName.equals("[        환생        ]"))
		{
		  	YamlLoader useableItemYaml = new YamlLoader();
			useableItemYaml.getConfig("Item/Consume.yml");
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(useableItemYaml.getBoolean(itemnumber+".Rebirth") == false)
				useableItemYaml.set(itemnumber+".Rebirth", true);
			else
				useableItemYaml.set(itemnumber+".Rebirth", false);
			useableItemYaml.saveConfig();
			newUseableItemGui(player, itemnumber);
		}
		else if(iconName.equals("[    형식 변경    ]"))
		{
		  	YamlLoader useableItemYaml = new YamlLoader();
			useableItemYaml.getConfig("Item/Consume.yml");
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(useableItemYaml.getString(itemnumber+".ShowType").contains("[깔끔]"))
				useableItemYaml.set(itemnumber+".ShowType","§e[컬러]");
			else if(useableItemYaml.getString(itemnumber+".ShowType").contains("[컬러]"))
				useableItemYaml.set(itemnumber+".ShowType","§d[기호]");
			else if(useableItemYaml.getString(itemnumber+".ShowType").contains("[기호]"))
				useableItemYaml.set(itemnumber+".ShowType","§9[분류]");
			else if(useableItemYaml.getString(itemnumber+".ShowType").contains("[분류]"))
				useableItemYaml.set(itemnumber+".ShowType","§f[깔끔]");
			useableItemYaml.saveConfig();
			newUseableItemGui(player, itemnumber);
		}
		else if(iconName.equals("[    타입 변경    ]"))
			SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 0.8F, 1.8F);
		else if(iconName.equals("[    등급 변경    ]"))
		{
		  	YamlLoader useableItemYaml = new YamlLoader();
			useableItemYaml.getConfig("Item/Consume.yml");
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
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
			newUseableItemGui(player, itemnumber);
		}
		else if(iconName.equals("이전 목록"))
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			useableItemListGui(player, 0);
		}
		else if(iconName.equals("닫기"))
		{
				SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
		}
		else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
		{
			UserDataObject u = new UserDataObject();
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "UseableItem");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -1);
			player.closeInventory();
			if(iconName.equals("[       포만감       ]"))
			{
				player.sendMessage("§3[아이템] : 회복할 포만감을 입력해 주세요!");
				u.setString(player, (byte)1, "Saturation");
			}
			else if(iconName.equals("[       생명력       ]"))
			{
				player.sendMessage("§3[아이템] : 회복할 생명력을 입력해 주세요!");
				u.setString(player, (byte)1, "HP");
				u.setInt(player, (byte)4, -8);
			}
			else if(iconName.equals("[        마나        ]"))
			{
				player.sendMessage("§3[아이템] : 회복할 마나를 입력해 주세요!");
				u.setString(player, (byte)1, "MP");
				u.setInt(player, (byte)4, -8);
			}
			else if(iconName.equals("[     스킬 포인트     ]"))
			{
				player.sendMessage("§3[아이템] : 주고자 하는 스킬 포인트의 양을 입력해 주세요!");
				u.setString(player, (byte)1, "SkillPoint");
			}
			else if(iconName.equals("[     스텟 포인트     ]"))
			{
				player.sendMessage("§3[아이템] : 주고자 하는 스텟 포인트의 양을 입력해 주세요!");
				u.setString(player, (byte)1, "StatPoint");
			}
			else if(iconName.equals("[        ID        ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 ID 값을 입력해 주세요!");
				u.setString(player, (byte)1, "ID");
			}
			else if(iconName.equals("[       DATA       ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 DATA 값을 입력해 주세요!");
				u.setString(player, (byte)1, "Data");
			}
			else if(iconName.equals("[       대미지       ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 최소 "+MainServerOption.damage+"를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MinDamage");
			}
			else if(iconName.equals("[     마법 대미지     ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 최소 "+MainServerOption.magicDamage+"를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MinMaDamage");
			}
			else if(iconName.equals("[        방어        ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 방어력을 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "DEF");
			}
			else if(iconName.equals("[        보호        ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 보호를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Protect");
			}
			else if(iconName.equals("[      마법 방어      ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 마법 방어력을 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaDEF");
			}
			else if(iconName.equals("[      마법 보호      ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 마법 보호를 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaProtect");
			}
			else if(iconName.equals("[        스텟        ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 생명력 보너스를 입력해 주세요!");
				player.sendMessage("§3(-127 ~ 127)");
				u.setString(player, (byte)1, "HP");
			}
			else if(iconName.equals("[       내구도       ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 최대 내구력을 입력해 주세요!");
				player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaxDurability");
			}
			else
			{
				if(iconName.equals("[    이름 변경    ]"))
				{
					player.sendMessage("§3[아이템] : 아이템의 이름을 입력해 주세요!");
					u.setString(player, (byte)1, "DisplayName");
				}
				else if(iconName.equals("[    설명 변경    ]"))
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
	
	public void selectSkillGuiClick(InventoryClickEvent event)
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
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				newUseableItemGui(player, itemnumber);
			else if(slot == 48)//이전 페이지
				selectSkillGui(player, (short) (page-1), itemnumber);
			else if(slot == 50)//다음 페이지
				selectSkillGui(player, (short) (page-1), itemnumber);
			else
			{
			  	YamlLoader useableItemYaml = new YamlLoader();
				useableItemYaml.getConfig("Item/Consume.yml");
				useableItemYaml.set(itemnumber+".Skill", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				useableItemYaml.saveConfig();
				newUseableItemGui(player, itemnumber);
			}
		}
	}
	
	
	public String[] loreCreater(int itemNumber)
	{
	  	YamlLoader useableItemYaml = new YamlLoader();
		useableItemYaml.getConfig("Item/Consume.yml");
		String lore = "";
		String type = ChatColor.stripColor(useableItemYaml.getString(itemNumber+".ShowType"));

		lore = lore+useableItemYaml.getString(itemNumber+".Type");
		for(int count = 0; count<20-useableItemYaml.getString(itemNumber+".Type").length();count++)
			lore = lore+" ";
		lore = lore+useableItemYaml.getString(itemNumber+".Grade")+"%enter% %enter%";
		
		switch(type)
		{
		case "[분류]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 Ω 월드 : §f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"§9 Ω X 좌표 : §f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"§9 Ω Y 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"§9 Ω Z 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"§3 + 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"§c - 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"§3 + 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"§c - 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"§3 + 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"§c - 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"§3 + 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"§c - 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"§3 + 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"§c - 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"§3 + 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"§c - 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 + 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"§c - 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"§3 + 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"§c - 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"§3 + 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"§c - 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";


				if(useableItemYaml.getInt(itemNumber+".HP")!=0||useableItemYaml.getInt(itemNumber+".MP")!=0||
				useableItemYaml.getInt(itemNumber+".STR")!=0||useableItemYaml.getInt(itemNumber+".DEX")!=0||
				useableItemYaml.getInt(itemNumber+".INT")!=0||useableItemYaml.getInt(itemNumber+".WILL")!=0||
				useableItemYaml.getInt(itemNumber+".LUK")!=0)
				{
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 + 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c - 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			}

			lore = lore+"%enter%§6___--------------------___%enter%§6       [아이템 설명]";
			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%";
			lore = lore+"§6---____________________---%enter%";
		}
		break;
		case "[기호]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 Ω 월드 : §f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"§9 Ω X 좌표 : §f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"§9 Ω Y 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"§9 Ω Z 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"§3 + 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"§c - 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"§3 + 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"§c - 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"§3 + 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"§c - 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"§3 + 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"§c - 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"§3 + 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"§c - 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"§3 + 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"§c - 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 + 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"§c - 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 + 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c - 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 + 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c - 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 + 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c - 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 + 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c - 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 + 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c - 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 + 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c - 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"§3 + 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"§c - 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"§3 + 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"§c - 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".HP")!=0||useableItemYaml.getInt(itemNumber+".MP")!=0||
				useableItemYaml.getInt(itemNumber+".STR")!=0||useableItemYaml.getInt(itemNumber+".DEX")!=0||
				useableItemYaml.getInt(itemNumber+".INT")!=0||useableItemYaml.getInt(itemNumber+".WILL")!=0||
				useableItemYaml.getInt(itemNumber+".LUK")!=0)
				{
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 + 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c - 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 + 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c - 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 + "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c - "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 + "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c - "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 + "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c - "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 + "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c - "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 + "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c - "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 + 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c - 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			}			
			
			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";

		}
		break;
		case "[컬러]":
		{
			switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
			{
			case "[귀환서]":
				lore = lore+"§9 월드 : §f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
				lore = lore+"§9 X 좌표 : §f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
				lore = lore+"§9 Y 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
				lore = lore+"§9 Z 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
				break;
			case "[주문서]":
				if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
					lore = lore+"§3 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
					lore = lore+"§c 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
					lore = lore+"§3 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
					lore = lore+"§c 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
				break;
			case "[스킬북]":
				if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
					lore = lore+"%enter%§c [아무것도 없는 빈 책]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [우 클릭시 아래 스킬 획득]%enter%§3 + §f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
				break;
			case "[소비]":
				if(useableItemYaml.getInt(itemNumber+".HP") > 0)
					lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
					lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MP") > 0)
					lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
					lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
					lore = lore+"§3 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
					lore = lore+"§c 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
				if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
					lore = lore+"§6§l + 환생%enter%";
				break;
			case "[룬]":
				if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
					lore = lore+"§3 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
					lore = lore+"§c 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
					lore = lore+"§3 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
					lore = lore+"§c 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
					lore = lore+"§3 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
					lore = lore+"§c 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
					lore = lore+"§3 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
					lore = lore+"§c 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".DEF")>0)
					lore = lore+"§3 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
					lore = lore+"§c 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")>0)
					lore = lore+"§3 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Protect")<0)
					lore = lore+"§c 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
					lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
					lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
					lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
					lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")>0)
					lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Balance")<0)
					lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")>0)
					lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".Critical")<0)
					lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";

				if(useableItemYaml.getInt(itemNumber+".Durability")>0)
					lore = lore+"§3 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
					lore = lore+"§c 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
				if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
					lore = lore+"§3 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
				else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
					lore = lore+"§c 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";


					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".STR") > 0)
						lore = lore+"§3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
						lore = lore+"§c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
						lore = lore+"§3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
						lore = lore+"§c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".INT") > 0)
						lore = lore+"§3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
						lore = lore+"§c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
						lore = lore+"§3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
						lore = lore+"§c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
						lore = lore+"§3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
						lore = lore+"§c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
						lore = lore+"§5 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
						lore = lore+"§c 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
				break;
			}
			
			lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";


		}
		break;
		
			case "[깔끔]":
			{
				switch(ChatColor.stripColor(useableItemYaml.getString(itemNumber+".Type")))
				{
				case "[귀환서]":
					lore = lore+"§f 월드 : §f" + useableItemYaml.getString(itemNumber+".World")+"%enter%";
					lore = lore+"§f X 좌표 : §f" + useableItemYaml.getInt(itemNumber+".X")+"%enter%";
					lore = lore+"§f Y 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Y")+"%enter%";
					lore = lore+"§f Z 좌표 : §f" + useableItemYaml.getInt(itemNumber+".Z")+"%enter%";
					break;
				case "[주문서]":
					if(useableItemYaml.getInt(itemNumber+".StatPoint")>0)
						lore = lore+"§3 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".StatPoint")<0)
						lore = lore+"§c 스텟 포인트 : " + useableItemYaml.getInt(itemNumber+".StatPoint")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".SkillPoint")>0)
						lore = lore+"§3 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".SkillPoint")<0)
						lore = lore+"§c 스킬 포인트 : " + useableItemYaml.getInt(itemNumber+".SkillPoint")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".DEF")>0)
						lore = lore+"§3 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
						lore = lore+"§c 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")>0)
						lore = lore+"§3 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")<0)
						lore = lore+"§c 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
						lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
						lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
						lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
						lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")>0)
						lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")<0)
						lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")>0)
						lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")<0)
						lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					
						if(useableItemYaml.getInt(itemNumber+".HP") > 0)
							lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
							lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MP") > 0)
							lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
							lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".STR") > 0)
							lore = lore+"§3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
							lore = lore+"§c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
							lore = lore+"§3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
							lore = lore+"§c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".INT") > 0)
							lore = lore+"§3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
							lore = lore+"§c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
							lore = lore+"§3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
							lore = lore+"§c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
							lore = lore+"§3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
							lore = lore+"§c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
					break;
				case "[스킬북]":
					if(useableItemYaml.getString(itemNumber+".Skill").equals("null"))
						lore = lore+"%enter%§f [아무것도 없는 빈 책]%enter%";
					else
						lore = lore+"%enter%§f"  +" [우 클릭시 아래 스킬 획득]%enter%§f + §f"+useableItemYaml.getString(itemNumber+".Skill")+"%enter%";
					break;
				case "[소비]":
					if(useableItemYaml.getInt(itemNumber+".HP") > 0)
						lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
						lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MP") > 0)
						lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
						lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Saturation") > 0)
						lore = lore+"§3 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".Saturation") < 0)
						lore = lore+"§c 포만감 : " + useableItemYaml.getInt(itemNumber+".Saturation")+"%enter%";
					if(useableItemYaml.getBoolean(itemNumber+".Rebirth") == true)
						lore = lore+"§6§l + 환생%enter%";
					break;
				case "[룬]":
					if(useableItemYaml.getInt(itemNumber+".MinDamage")>0)
						lore = lore+"§3 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MinDamage")<0)
						lore = lore+"§c 최소 공격력 : " + useableItemYaml.getInt(itemNumber+".MinDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxDamage")>0)
						lore = lore+"§3 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxDamage")<0)
						lore = lore+"§c 최대 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MinMaDamage")>0)
						lore = lore+"§3 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MinMaDamage")<0)
						lore = lore+"§c 최소 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MinMaDamage")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")>0)
						lore = lore+"§3 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxMaDamage")<0)
						lore = lore+"§c 최대 마법 공격력 : " + useableItemYaml.getInt(itemNumber+".MaxMaDamage")+"%enter%";
					
					
						
					if(useableItemYaml.getInt(itemNumber+".DEF")>0)
						lore = lore+"§3 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".DEF")<0)
						lore = lore+"§c 방어 : " + useableItemYaml.getInt(itemNumber+".DEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")>0)
						lore = lore+"§3 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Protect")<0)
						lore = lore+"§c 보호 : " + useableItemYaml.getInt(itemNumber+".Protect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")>0)
						lore = lore+"§3 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaDEF")<0)
						lore = lore+"§c 마법 방어 : " + useableItemYaml.getInt(itemNumber+".MaDEF")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")>0)
						lore = lore+"§3 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaProtect")<0)
						lore = lore+"§c 마법 보호 : " + useableItemYaml.getInt(itemNumber+".MaProtect")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")>0)
						lore = lore+"§3 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Balance")<0)
						lore = lore+"§c 밸런스 : " + useableItemYaml.getInt(itemNumber+".Balance")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")>0)
						lore = lore+"§3 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Critical")<0)
						lore = lore+"§c 크리티컬 : " + useableItemYaml.getInt(itemNumber+".Critical")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".Durability")>0)
						lore = lore+"§3 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".Durability")<0)
						lore = lore+"§c 현재 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".Durability")+"%enter%";
					if(useableItemYaml.getInt(itemNumber+".MaxDurability")>0)
						lore = lore+"§3 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
					else if(useableItemYaml.getInt(itemNumber+".MaxDurability")<0)
						lore = lore+"§c 최대 내구도 증가 : " + useableItemYaml.getInt(itemNumber+".MaxDurability")+"%enter%";
					
						if(useableItemYaml.getInt(itemNumber+".HP") > 0)
							lore = lore+"§3 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".HP") < 0)
							lore = lore+"§c 생명력 : " + useableItemYaml.getInt(itemNumber+".HP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MP") > 0)
							lore = lore+"§3 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".MP") < 0)
							lore = lore+"§c 마나 : " + useableItemYaml.getInt(itemNumber+".MP")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".STR") > 0)
							lore = lore+"§3 "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".STR") < 0)
							lore = lore+"§c "+MainServerOption.statSTR+" : " + useableItemYaml.getInt(itemNumber+".STR")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".DEX") > 0)
							lore = lore+"§3 "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".DEX") < 0)
							lore = lore+"§c "+MainServerOption.statDEX+" : " + useableItemYaml.getInt(itemNumber+".DEX")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".INT") > 0)
							lore = lore+"§3 "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".INT") < 0)
							lore = lore+"§c "+MainServerOption.statINT+" : " + useableItemYaml.getInt(itemNumber+".INT")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".WILL") > 0)
							lore = lore+"§3 "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".WILL") < 0)
							lore = lore+"§c "+MainServerOption.statWILL+" : " + useableItemYaml.getInt(itemNumber+".WILL")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".LUK") > 0)
							lore = lore+"§3 "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						else if(useableItemYaml.getInt(itemNumber+".LUK") < 0)
							lore = lore+"§c "+MainServerOption.statLUK+" : " + useableItemYaml.getInt(itemNumber+".LUK")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") > 0)
							lore = lore+"§5 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
						if(useableItemYaml.getInt(itemNumber+".MaxUpgrade") < 0)
							lore = lore+"§3 개조 : " +useableItemYaml.getInt(itemNumber+".MaxUpgrade")+"%enter%";
					break;
				}
				
				lore = lore+"%enter%"+ useableItemYaml.getString(itemNumber+".Lore")+"%enter%%enter%";

			}
			break;
		}

		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}
}
