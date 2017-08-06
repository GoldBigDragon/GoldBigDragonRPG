package customitem;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import admin.OPbox_GUI;
import effect.SoundEffect;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;



public class CustomItem_GUI extends Util_GUI
{
	public void ItemListGUI(Player player, int page)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");

		String UniqueCode = "§0§0§3§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0아이템 목록 : " + (page+1));

		int itemAmounts = itemYaml.getKeys().size();

		byte loc=0;
		for(int count = page*45; count < itemAmounts;count++)
		{
			int number = ((page*45)+loc);
			if(count > itemAmounts || loc >= 45) break;
			String ItemName = itemYaml.getString(number+".DisplayName");
			int ItemID = itemYaml.getInt(number+".ID");
			int ItemData = itemYaml.getInt(number+".Data");
			Stack2(ItemName, ItemID, ItemData, 1,Arrays.asList(LoreCreater(number)), loc, inv);
			
			loc++;
		}
		
		if(itemAmounts-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 아이템", 145,0,1,Arrays.asList(ChatColor.GRAY + "새로운 아이템을 생성합니다."), 49, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}

	public void NewItemGUI(Player player, int number)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");

		String UniqueCode = "§0§0§3§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0아이템 상세 설정");
		String ItemName = itemYaml.getString(number+".DisplayName");
		short ItemID = (short) itemYaml.getInt(number+".ID");
		byte ItemData = (byte) itemYaml.getInt(number+".Data");

		String Type = "";
		String Grade = "";
		for(int counter =0;counter < 13 - itemYaml.getString(number+".Type").length();counter++ )
			Type = Type +" ";
		Type = Type +itemYaml.getString(number+".Type");
		Grade = itemYaml.getString(number+".Grade");
		
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 145,0,1,null, 9, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 145,0,1,null, 10, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 145,0,1,null, 11, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 145,0,1,null, 18, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 145,0,1,null, 20, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 145,0,1,null, 27, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 145,0,1,null, 28, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 145,0,1,null, 29, inv);
		Stack2(ItemName, ItemID,ItemData,1,Arrays.asList(LoreCreater(number)), 19, inv);
		
		Stack2(ChatColor.DARK_AQUA + "[    형식 변경    ]", 421,0,1,Arrays.asList(ChatColor.WHITE+"아이템 설명창을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 형식    ]","       "+ itemYaml.getString(number+".ShowType"),""), 37, inv);
		Stack2(ChatColor.DARK_AQUA + "[    이름 변경    ]", 421,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 이름을",ChatColor.WHITE+"변경합니다.",""), 13, inv);
		Stack2(ChatColor.DARK_AQUA + "[    설명 변경    ]", 386,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 설명을",ChatColor.WHITE+"변경합니다.",""), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "[    타입 변경    ]", 61,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 타입을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 타입    ]",Type,""), 15, inv);
		Stack2(ChatColor.DARK_AQUA + "[    등급 변경    ]", 266,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 등급을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 등급    ]","       "+Grade,""), 16, inv);
		Stack2(ChatColor.DARK_AQUA + "[        ID        ]", 405,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 ID값을",ChatColor.WHITE+"변경합니다.",""), 22, inv);
		Stack2(ChatColor.DARK_AQUA + "[       DATA       ]", 336,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 DATA값을",ChatColor.WHITE+"변경합니다.",""), 23, inv);
		Stack2(ChatColor.DARK_AQUA + "[       "+main.Main_ServerOption.Damage+"       ]", 267,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 "+main.Main_ServerOption.Damage+"를",ChatColor.WHITE+"변경합니다.",""), 24, inv);
		Stack2(ChatColor.DARK_AQUA + "[     "+main.Main_ServerOption.MagicDamage+"     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 "+main.Main_ServerOption.MagicDamage+"를",ChatColor.WHITE+"변경합니다.",""), 25, inv);
		Stack2(ChatColor.DARK_AQUA + "[        방어        ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 방어력을",ChatColor.WHITE+"변경합니다.",""), 31, inv);
		Stack2(ChatColor.DARK_AQUA + "[        보호        ]", 306,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 보호를",ChatColor.WHITE+"변경합니다.",""), 32, inv);
		Stack2(ChatColor.DARK_AQUA + "[      마법 방어      ]", 311,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 마법 방어를",ChatColor.WHITE+"변경합니다.",""), 33, inv);
		Stack2(ChatColor.DARK_AQUA + "[      마법 보호      ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 마법 보호를",ChatColor.WHITE+"변경합니다.",""), 34, inv);
		Stack2(ChatColor.DARK_AQUA + "[        스텟        ]", 399,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 보너스 스텟을",ChatColor.WHITE+"설정합니다.",""), 40, inv);
		Stack2(ChatColor.DARK_AQUA + "[       내구도       ]", 145,2,1,Arrays.asList(ChatColor.WHITE+"아이템의 내구력을",ChatColor.WHITE+"조절합니다.","",ChatColor.RED+"[0 설정시 일반 아이템 내구도 사용]",""), 41, inv);
		Stack2(ChatColor.DARK_AQUA + "[        개조        ]", 145,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 최대 개조 횟수를",ChatColor.WHITE+"조절합니다.","",ChatColor.RED+"[0 설정시 개조 불가능]",""), 42, inv);
		Stack2(ChatColor.DARK_AQUA + "[         룬         ]", 381,0,1,Arrays.asList(ChatColor.WHITE+"아이템의 최대 슬롯을",ChatColor.WHITE+"조절합니다.","",ChatColor.WHITE+"최대 "+itemYaml.getInt(number+".MaxSocket")+" 개","",ChatColor.RED+"[0 설정시 룬 장착 불가능]",""), 43, inv);
		Stack2(ChatColor.DARK_AQUA + "[      스텟 제한      ]", 166,0,1,Arrays.asList(ChatColor.WHITE+"아이템 장착에 제한을",ChatColor.WHITE+"걸어둡니다.",""), 49, inv);
		Stack2(ChatColor.DARK_AQUA + "[      직업 제한      ]", 397,3,1,Arrays.asList(ChatColor.WHITE+"아이템 장착에 제한을",ChatColor.WHITE+"걸어둡니다.",ChatColor.RED+"[우 클릭시 해제]",""), 50, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+number), 53, inv);
		player.openInventory(inv);
	}
	
	public void JobListGUI(Player player, short page, int number)
	{
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		String UniqueCode = "§0§0§3§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0아이템 직업 제한 : " + (page+1));

		String[] jobList = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < jobList.length;count++)
		{
			int JobLevel= jobYaml.getConfigurationSection("MapleStory."+jobList[count]).getKeys(false).size();
			
			if(count > jobList.length || loc >= 45) break;
			
			if(jobList[count].equalsIgnoreCase(configYaml.getString("Server.DefaultJob")))
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + jobList[count], 403,0,1,Arrays.asList(ChatColor.DARK_AQUA+"최대 승급 : " + ChatColor.WHITE+JobLevel+ChatColor.DARK_AQUA+"차 승급","",ChatColor.YELLOW+"[좌클릭시 전용 설정]","§e§l[서버 기본 직업]"), loc, inv);
			else
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + jobList[count], 340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"최대 승급 : " + ChatColor.WHITE+JobLevel+ChatColor.DARK_AQUA+"차 승급","",ChatColor.YELLOW+"[좌클릭시 전용 설정]"), loc, inv);
			
			loc++;
		}
		
		if(jobList.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+number), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void ItemListInventoryclick(InventoryClickEvent event)
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
			short page = (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new OPbox_GUI().OPBoxGUI_Main(player,(byte) 1);
			else if(slot == 48)//이전 페이지
				ItemListGUI(player,page-1);
			else if(slot == 50)//다음 페이지
				ItemListGUI(player,page+1);
			else
			{
			  	YamlLoader itemYaml = new YamlLoader();
				itemYaml.getConfig("Item/ItemList.yml");
				if(slot == 49)//새 아이템
				{
					int ItemCounter = itemYaml.getKeys().size();
					itemYaml.set(ItemCounter+".ShowType",ChatColor.WHITE+"[깔끔]");
					itemYaml.set(ItemCounter+".ID",267);
					itemYaml.set(ItemCounter+".Data",0);
					itemYaml.set(ItemCounter+".DisplayName",ChatColor.WHITE+"방금 만든 따끈한 검");
					itemYaml.set(ItemCounter+".Lore",ChatColor.WHITE+"방금 만들어진 무기이다.%enter%"+ChatColor.WHITE+"조금은 허접할지도...");
					itemYaml.set(ItemCounter+".Type",ChatColor.RED+"[근접 무기]");
					itemYaml.set(ItemCounter+".Grade",ChatColor.WHITE+"[일반]");
					itemYaml.set(ItemCounter+".MinDamage",1);
					itemYaml.set(ItemCounter+".MaxDamage",7);
					itemYaml.set(ItemCounter+".MinMaDamage",0);
					itemYaml.set(ItemCounter+".MaxMaDamage",0);
					itemYaml.set(ItemCounter+".DEF",0);
					itemYaml.set(ItemCounter+".Protect",0);
					itemYaml.set(ItemCounter+".MaDEF",0);
					itemYaml.set(ItemCounter+".MaProtect",0);
					itemYaml.set(ItemCounter+".Durability",256);
					itemYaml.set(ItemCounter+".MaxDurability",256);
					itemYaml.set(ItemCounter+".STR",0);
					itemYaml.set(ItemCounter+".DEX",0);
					itemYaml.set(ItemCounter+".INT",0);
					itemYaml.set(ItemCounter+".WILL",0);
					itemYaml.set(ItemCounter+".LUK",0);
					itemYaml.set(ItemCounter+".Balance",10);
					itemYaml.set(ItemCounter+".Critical",5);
					itemYaml.set(ItemCounter+".MaxUpgrade",5);
					itemYaml.set(ItemCounter+".MaxSocket",3);
					itemYaml.set(ItemCounter+".HP",0);
					itemYaml.set(ItemCounter+".MP",0);
					itemYaml.set(ItemCounter+".JOB","공용");
					itemYaml.set(ItemCounter+".MinLV",0);
					itemYaml.set(ItemCounter+".MinRLV",0);
					itemYaml.set(ItemCounter+".MinSTR",0);
					itemYaml.set(ItemCounter+".MinDEX",0);
					itemYaml.set(ItemCounter+".MinINT",0);
					itemYaml.set(ItemCounter+".MinWILL",0);
					itemYaml.set(ItemCounter+".MinLUK",0);
					itemYaml.saveConfig();
					NewItemGUI(player, ItemCounter);
				}
				else
				{
					int number = ((page*45)+event.getSlot());
					if(event.isLeftClick() == true&&event.isShiftClick()==false)
					{
						player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 클릭한 아이템을 지급 받았습니다!");
						player.getInventory().addItem(event.getCurrentItem());
					}
					if(event.isLeftClick() == true&&event.isShiftClick()==true)
						NewItemGUI(player, number);
					else if(event.isRightClick()==true&&event.isShiftClick()==true)
					{
						short Acount =  (short) (itemYaml.getKeys().toArray().length-1);
						for(int counter = (short) number;counter <Acount;counter++)
							itemYaml.set(counter+"", itemYaml.get((counter+1)+""));
						itemYaml.removeKey(Acount+"");
						itemYaml.saveConfig();
						ItemListGUI(player, page);
					}
				}
			}
		}
	}
	
	public void NewItemGUIclick(InventoryClickEvent event)
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
			if(slot == 45)//이전 화면
				ItemListGUI(player, 0);
			else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
			{
				int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			  	YamlLoader itemYaml = new YamlLoader();
				itemYaml.getConfig("Item/ItemList.yml");
				if(itemYaml.getString(itemnumber+"")==null)
				{
					player.sendMessage(ChatColor.RED+"[SYSTEM] : 다른 OP가 아이템을 삭제하여 반영되지 않았습니다!");
					SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				}
				if(slot==37)//쇼 타입
				{
					if(itemYaml.getString(itemnumber+".ShowType").contains("[깔끔]"))
						itemYaml.set(itemnumber+".ShowType",ChatColor.YELLOW+"[컬러]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[컬러]"))
						itemYaml.set(itemnumber+".ShowType",ChatColor.LIGHT_PURPLE+"[기호]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[기호]"))
						itemYaml.set(itemnumber+".ShowType",ChatColor.BLUE+"[분류]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[분류]"))
						itemYaml.set(itemnumber+".ShowType",ChatColor.WHITE+"[깔끔]");
					itemYaml.saveConfig();
					NewItemGUI(player, itemnumber);
				}
				else if(slot==15)//타입 변경
				{
					YamlLoader customTypeYaml = new YamlLoader();
				  	customTypeYaml.getConfig("Item/CustomType.yml");
				  	String[] weaponCustomType = customTypeYaml.getKeys().toArray(new String[0]);
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				  	if(weaponCustomType.length == 0)
				  	{
						if(itemYaml.getString(itemnumber+".Type").contains("[근접 무기]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[한손 검]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[한손 검]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[양손 검]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[양손 검]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[도끼]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[도끼]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[낫]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[낫]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_GREEN+"[원거리 무기]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[원거리 무기]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_GREEN+"[활]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[활]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_GREEN+"[석궁]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[석궁]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_AQUA+"[마법 무기]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[마법 무기]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_AQUA+"[원드]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[원드]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_AQUA+"[스태프]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[스태프]"))
							itemYaml.set(itemnumber+".Type",ChatColor.WHITE+"[방어구]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[방어구]"))
							itemYaml.set(itemnumber+".Type",ChatColor.GRAY+"[기타]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[기타]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[근접 무기]");
				  	}
				  	else
				  	{
						if(itemYaml.getString(itemnumber+".Type").contains("[근접 무기]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[한손 검]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[한손 검]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[양손 검]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[양손 검]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[도끼]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[도끼]"))
							itemYaml.set(itemnumber+".Type",ChatColor.RED+"[낫]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[낫]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_GREEN+"[원거리 무기]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[원거리 무기]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_GREEN+"[활]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[활]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_GREEN+"[석궁]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[석궁]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_AQUA+"[마법 무기]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[마법 무기]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_AQUA+"[원드]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[원드]"))
							itemYaml.set(itemnumber+".Type",ChatColor.DARK_AQUA+"[스태프]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[스태프]"))
							itemYaml.set(itemnumber+".Type",ChatColor.WHITE+"[방어구]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[방어구]"))
							itemYaml.set(itemnumber+".Type",ChatColor.GRAY+"[기타]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[기타]"))
							itemYaml.set(itemnumber+".Type",ChatColor.WHITE+weaponCustomType[0]);
						else
						{
							for(int count = 0; count < weaponCustomType.length; count++)
							{
								if((itemYaml.getString(itemnumber+".Type").contains(weaponCustomType[count])))
								{
									if(count+1 == weaponCustomType.length)
										itemYaml.set(itemnumber+".Type",ChatColor.RED+"[근접 무기]");
									else
										itemYaml.set(itemnumber+".Type",ChatColor.WHITE+weaponCustomType[(count+1)]);
									break;
								}
							}
						}
				  	}
					itemYaml.saveConfig();
					NewItemGUI(player, itemnumber);
				}
				else if(slot==16)//등급 변경
				{
					if(itemYaml.getString(itemnumber+".Grade").contains("[일반]"))
						itemYaml.set(itemnumber+".Grade",ChatColor.GREEN+"[상급]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[상급]"))
						itemYaml.set(itemnumber+".Grade",ChatColor.BLUE+"[매직]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[매직]"))
						itemYaml.set(itemnumber+".Grade",ChatColor.YELLOW+"[레어]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[레어]"))
						itemYaml.set(itemnumber+".Grade",ChatColor.DARK_PURPLE+"[에픽]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[에픽]"))
						itemYaml.set(itemnumber+".Grade",ChatColor.GOLD+"[전설]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[전설]"))
						itemYaml.set(itemnumber+".Grade",ChatColor.DARK_RED+""+ChatColor.BOLD+"["+ChatColor.GOLD+""+ChatColor.BOLD+"초"+ChatColor.DARK_GREEN+""+ChatColor.BOLD+"월"+"§1§l]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("초"))
						itemYaml.set(itemnumber+".Grade",ChatColor.GRAY+"[하급]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[하급]"))
						itemYaml.set(itemnumber+".Grade",ChatColor.WHITE+"[일반]");
					itemYaml.saveConfig();
					NewItemGUI(player, itemnumber);
				}
				else if(slot==43)//최대 슬롯 설정
				{
					if(itemYaml.getInt(itemnumber+".MaxSocket") < 5)
						itemYaml.set(itemnumber+".MaxSocket",itemYaml.getInt(itemnumber+".MaxSocket")+1);
					else if(itemYaml.getInt(itemnumber+".MaxSocket") == 5)
							itemYaml.set(itemnumber+".MaxSocket", 0);
					itemYaml.saveConfig();
					NewItemGUI(player, itemnumber);
				}
				else if(slot==50)//직업 제한
				{
					if(event.isLeftClick())
						JobListGUI(player, (short)0, itemnumber);
					else if(event.isRightClick())
					{
						SoundEffect.SP(player, Sound.ITEM_SHIELD_BREAK, 0.8F, 1.0F);
						itemYaml.set(itemnumber+".JOB", "공용");
						itemYaml.saveConfig();
						NewItemGUI(player, itemnumber);
					}
				}
				else
				{
					UserData_Object u = new UserData_Object();
					player.closeInventory();
					u.setType(player, "Item");
					u.setInt(player, (byte)3, itemnumber);
					if(slot == 13 || slot == 14)
					{
						if(slot == 13)
						{
							player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 이름을 입력해 주세요!");
							u.setString(player, (byte)1, "DisplayName");
						}
						else
						{
							player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 설명을 입력해 주세요!");
							player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
							u.setString(player, (byte)1, "Lore");
						}
						player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
						ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
								ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
						ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
								ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
					}
					else if(slot == 22)//ID변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 ID 값을 입력해 주세요!");
						u.setString(player, (byte)1, "ID");
					}
					else if(slot == 23)//DATA변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 DATA 값을 입력해 주세요!");
						u.setString(player, (byte)1, "Data");
					}
					else if(slot == 24)//대미지 변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최소 "+main.Main_ServerOption.Damage+"를 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MinDamage");
					}
					else if(slot == 25)//마법 대미지 변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최소 "+main.Main_ServerOption.MagicDamage+"를 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MinMaDamage");
					}
					else if(slot == 31)//방어변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 방어력을 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "DEF");
					}
					else if(slot == 32)//보호변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 보호를 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "Protect");
					}
					else if(slot == 33)//마법 방어 변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 마법 방어력을 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaDEF");
					}
					else if(slot == 34)//마법 보호 변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 마법 보호를 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaProtect");
					}
					else if(slot == 40)//스텟 보너스
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 생명력 보너스를 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
						u.setString(player, (byte)1, "HP");
					}
					else if(slot == 41)//내구도 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최대 내구력을 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaxDurability");
					}
					else if(slot == 42)//개조 횟수 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 최대 개조 횟수를 입력해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ 127)");
						u.setString(player, (byte)1, "MaxUpgrade");
					}
					else if(slot == 49)//스텟 제한
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 레벨 제한을 입력 해 주세요!");
						player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MinLV");
					}
				}
			}
		}
	}
	
	public void JobGUIClick(InventoryClickEvent event)
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
			int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			if(slot == 45)//이전 목록
				NewItemGUI(player, itemnumber);
			else
			{
			  	YamlLoader temYaml = new YamlLoader();
				temYaml.getConfig("Item/ItemList.yml");
				temYaml.set(itemnumber+".JOB", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				temYaml.saveConfig();
				SoundEffect.SP(player, Sound.ITEM_SHIELD_BREAK, 0.8F, 1.0F);
				NewItemGUI(player, itemnumber);
			}
		}
	}
	

	public String[] LoreCreater(int ItemNumber)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");
		String lore = "";
		String Type = ChatColor.stripColor(itemYaml.getString(ItemNumber+".ShowType"));
		if(Type.compareTo("[분류]")==0)
		{
			lore = lore+itemYaml.getString(ItemNumber+".Type");
			for(int count = 0; count<20-itemYaml.getString(ItemNumber+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(ItemNumber+".JOB").compareTo("공용")==0)
				lore = lore+itemYaml.getString(ItemNumber+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(ItemNumber+".Grade")+"%enter%"+ChatColor.GOLD+"장비 가능 직업 : " +ChatColor.WHITE + itemYaml.getString(ItemNumber+".JOB")+"%enter%%enter%";
				

			if(itemYaml.getInt(ItemNumber+".MinDamage") != 0||itemYaml.getInt(ItemNumber+".MaxDamage") != 0)
				lore = lore+ChatColor.RED + " ▩ "+main.Main_ServerOption.Damage+" : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MinDamage") + " ~ " + itemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MinMaDamage") != 0||itemYaml.getInt(ItemNumber+".MaxMaDamage") != 0)
				lore = lore+ChatColor.DARK_AQUA + " ▦ "+main.Main_ServerOption.MagicDamage+" : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".DEF") != 0)
				lore = lore+ChatColor.GRAY + " ▧ 방어 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".DEF")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Protect") != 0)
				lore = lore+ChatColor.WHITE + " ■ 보호 : " + ChatColor.WHITE +itemYaml.getInt(ItemNumber+".Protect")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaDEF") != 0)
				lore = lore+ChatColor.BLUE + " ◇ 마법 방어 : " + ChatColor.WHITE +itemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaProtect") != 0)
				lore = lore+ChatColor.DARK_BLUE + " ◆ 마법 보호 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Balance") != 0)
				lore = lore+ChatColor.DARK_GREEN + " △ 밸런스 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Balance")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Critical") != 0)
				lore = lore+ChatColor.YELLOW + " ▲ 크리티컬 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Critical")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxDurability") > 0)
				lore = lore+ChatColor.GOLD + " ▣ 내구도 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Durability")+" / "+ itemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
				lore = lore + ChatColor.WHITE+" ♣ 숙련도 : 0.0%"+ChatColor.WHITE+ "%enter%";
			
			lore = lore+ChatColor.GOLD+"___--------------------___%enter%"+ChatColor.GOLD+"       [아이템 설명]";
			lore = lore+"%enter%"+ itemYaml.getString(ItemNumber+".Lore")+"%enter%";
			lore = lore+ChatColor.GOLD+"---____________________---%enter%";


			if(itemYaml.getInt(ItemNumber+".HP")!=0||itemYaml.getInt(ItemNumber+".MP")!=0||
					itemYaml.getInt(ItemNumber+".STR")!=0||itemYaml.getInt(ItemNumber+".DEX")!=0||
					itemYaml.getInt(ItemNumber+".INT")!=0||itemYaml.getInt(ItemNumber+".WILL")!=0||
					itemYaml.getInt(ItemNumber+".LUK")!=0)
			{
				lore = lore+ChatColor.DARK_AQUA+"___--------------------___%enter%"+ChatColor.DARK_AQUA+"       [보너스 옵션]%enter%";
				if(itemYaml.getInt(ItemNumber+".HP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ▲ 생명력 : " + itemYaml.getInt(ItemNumber+".HP")+"%enter%";
				else if(itemYaml.getInt(ItemNumber+".HP") < 0)
					lore = lore+ChatColor.RED + " ▼ 생명력 : " + itemYaml.getInt(ItemNumber+".HP")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".MP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ▲ 마나 : " + itemYaml.getInt(ItemNumber+".MP")+"%enter%";
				else if(itemYaml.getInt(ItemNumber+".MP") < 0)
					lore = lore+ChatColor.RED + " ▼ 마나 : " + itemYaml.getInt(ItemNumber+".MP")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".STR") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".STR")+"%enter%";
				else if(itemYaml.getInt(ItemNumber+".STR") < 0)
					lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".STR")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".DEX") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".DEX")+"%enter%";
				else if(itemYaml.getInt(ItemNumber+".DEX") < 0)
					lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".DEX")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".INT") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".INT")+"%enter%";
				else if(itemYaml.getInt(ItemNumber+".INT") < 0)
					lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".INT")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".WILL") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".WILL")+"%enter%";
				else if(itemYaml.getInt(ItemNumber+".WILL") < 0)
					lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".WILL")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".LUK") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".LUK")+"%enter%";
				else if(itemYaml.getInt(ItemNumber+".LUK") < 0)
					lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".LUK")+"%enter%";
				lore = lore+ChatColor.DARK_AQUA+"---____________________---%enter%";
			}

			if(itemYaml.getInt(ItemNumber+".MaxSocket")!=0||itemYaml.getInt(ItemNumber+".MaxUpgrade")!=0)
			{
				lore = lore+ChatColor.LIGHT_PURPLE+"___--------------------___%enter%"+ChatColor.LIGHT_PURPLE+"       [아이템 강화]%enter%";
				if(itemYaml.getInt(ItemNumber+".MaxUpgrade") > 0 && itemYaml.getInt(ItemNumber+".MaxSocket") > 0)
				{
					lore = lore+ChatColor.DARK_PURPLE + " ★ 개조 : " + "0 / "+itemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					lore = lore+ChatColor.BLUE + " ⓛ 룬 : ";
					for(int count = 0; count <itemYaml.getInt(ItemNumber+".MaxSocket");count++)
						lore = lore+ChatColor.GRAY + "○ ";
				}
				else if(itemYaml.getInt(ItemNumber+".MaxUpgrade") <= 0 && itemYaml.getInt(ItemNumber+".MaxSocket") > 0)
				{
					lore = lore+ChatColor.BLUE + " ⓛ 룬 : ";
					for(int count = 0; count <itemYaml.getInt(ItemNumber+".MaxSocket");count++)
						lore = lore+ChatColor.GRAY + "○ ";
				}
				else
					lore = lore+ChatColor.DARK_PURPLE + " ★ 개조 : " + "0 / "+itemYaml.getInt(ItemNumber+".MaxUpgrade");
				lore = lore+"%enter%"+ChatColor.LIGHT_PURPLE+"---____________________---%enter%";
			}
			if(itemYaml.getInt(ItemNumber+".MinLV")!=0||itemYaml.getInt(ItemNumber+".MinRLV")!=0||
					itemYaml.getInt(ItemNumber+".MinSTR")!=0||itemYaml.getInt(ItemNumber+".MinDEX")!=0||
					itemYaml.getInt(ItemNumber+".MinINT")!=0||itemYaml.getInt(ItemNumber+".MinWILL")!=0||
					itemYaml.getInt(ItemNumber+".MinLUK")!=0)
			{
				lore = lore+ChatColor.DARK_RED+"___--------------------___%enter%"+ChatColor.DARK_RED+"       [제한 스텟]%enter%";
				if(itemYaml.getInt(ItemNumber+".MinLV") > 0)
					lore = lore+ChatColor.DARK_RED + " Ω 최소 레벨 : " + itemYaml.getInt(ItemNumber+".MinLV")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".MinRLV") > 0)
					lore = lore+ChatColor.DARK_RED + " Ω 최소 누적레벨 : " + itemYaml.getInt(ItemNumber+".MinRLV")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".MinSTR") > 0)
					lore = lore+ChatColor.DARK_RED + " Ω 최소 "+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".MinSTR")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".MinDEX") > 0)
					lore = lore+ChatColor.DARK_RED + " Ω 최소 "+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".MinDEX")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".MinINT") > 0)
					lore = lore+ChatColor.DARK_RED + " Ω 최소 "+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".MinINT")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".MinWILL") > 0)
					lore = lore+ChatColor.DARK_RED + " Ω 최소 "+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".MinWILL")+"%enter%";
				if(itemYaml.getInt(ItemNumber+".MinLUK") > 0)
					lore = lore+ChatColor.DARK_RED + " Ω 최소 "+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".MinLUK")+"%enter%";
				lore = lore+ChatColor.DARK_RED+"---____________________---%enter%";
				
			}
		}
		else if(Type.compareTo("[기호]")==0)
		{
			lore = lore+itemYaml.getString(ItemNumber+".Type");
			for(int count = 0; count<20-itemYaml.getString(ItemNumber+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(ItemNumber+".JOB").compareTo("공용")==0)
				lore = lore+itemYaml.getString(ItemNumber+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(ItemNumber+".Grade")+"%enter%"+ChatColor.GOLD+"장비 가능 직업 : " +ChatColor.WHITE + itemYaml.getString(ItemNumber+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(ItemNumber+".MinDamage") != 0||itemYaml.getInt(ItemNumber+".MaxDamage") != 0)
				lore = lore+ChatColor.RED + " ▩ "+main.Main_ServerOption.Damage+" : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MinDamage") + " ~ " + itemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MinMaDamage") != 0||itemYaml.getInt(ItemNumber+".MaxMaDamage") != 0)
				lore = lore+ChatColor.DARK_AQUA + " ▦ "+main.Main_ServerOption.MagicDamage+" : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".DEF") != 0)
				lore = lore+ChatColor.GRAY + " ▧ 방어 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".DEF")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Protect") != 0)
				lore = lore+ChatColor.WHITE + " ■ 보호 : " + ChatColor.WHITE +itemYaml.getInt(ItemNumber+".Protect")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaDEF") != 0)
				lore = lore+ChatColor.BLUE + " ◇ 마법 방어 : " + ChatColor.WHITE +itemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaProtect") != 0)
				lore = lore+ChatColor.DARK_BLUE + " ◆ 마법 보호 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Balance") != 0)
				lore = lore+ChatColor.DARK_GREEN + " △ 밸런스 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Balance")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Critical") != 0)
				lore = lore+ChatColor.YELLOW + " ▲ 크리티컬 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Critical")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxDurability") > 0)
				lore = lore+ChatColor.GOLD + " ▣ 내구도 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Durability")+" / "+ itemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";

			if(itemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
				lore = lore + ChatColor.WHITE+" ♣ 숙련도 : 0.0%"+ChatColor.WHITE+ "%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(ItemNumber+".Lore")+"%enter%";


			if(itemYaml.getInt(ItemNumber+".HP") > 0)
				lore = lore+ChatColor.DARK_AQUA + " ▲ 생명력 : " + itemYaml.getInt(ItemNumber+".HP")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".HP") < 0)
				lore = lore+ChatColor.RED + " ▼ 생명력 : " + itemYaml.getInt(ItemNumber+".HP")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MP") > 0)
				lore = lore+ChatColor.DARK_AQUA + " ▲ 마나 : " + itemYaml.getInt(ItemNumber+".MP")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".MP") < 0)
				lore = lore+ChatColor.RED + " ▼ 마나 : " + itemYaml.getInt(ItemNumber+".MP")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".STR") > 0)
				lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".STR")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".STR") < 0)
				lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".STR")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".DEX") > 0)
				lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".DEX")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".DEX") < 0)
				lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".DEX")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".INT") > 0)
				lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".INT")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".INT") < 0)
				lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".INT")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".WILL") > 0)
				lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".WILL")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".WILL") < 0)
				lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".WILL")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".LUK") > 0)
				lore = lore+ChatColor.DARK_AQUA + " ▲ "+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".LUK")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".LUK") < 0)
				lore = lore+ChatColor.RED + " ▼ "+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".LUK")+"%enter%";
			
			if(itemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_PURPLE + " ★ 개조 : " + "0 / "+itemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxSocket") > 0)
			{
				lore = lore+"%enter%"+ChatColor.BLUE + " ⓛ 룬 : ";
				for(int count = 0; count <itemYaml.getInt(ItemNumber+".MaxSocket");count++)
					lore = lore+ChatColor.GRAY + "○ ";
			}

			if(itemYaml.getInt(ItemNumber+".MinLV")!=0||itemYaml.getInt(ItemNumber+".MinRLV")!=0||
					itemYaml.getInt(ItemNumber+".MinSTR")!=0||itemYaml.getInt(ItemNumber+".MinDEX")!=0||
					itemYaml.getInt(ItemNumber+".MinINT")!=0||itemYaml.getInt(ItemNumber+".MinWILL")!=0||
					itemYaml.getInt(ItemNumber+".MinLUK")!=0)
				lore = lore+"%enter%";
			
			if(itemYaml.getInt(ItemNumber+".MinLV") > 0)
				lore = lore+ChatColor.DARK_RED + "%enter% Ω 최소 레벨 : " + itemYaml.getInt(ItemNumber+".MinLV");
			if(itemYaml.getInt(ItemNumber+".MinRLV") > 0)
				lore = lore+ChatColor.DARK_RED + "%enter% Ω 최소 누적레벨 : " + itemYaml.getInt(ItemNumber+".MinRLV");
			if(itemYaml.getInt(ItemNumber+".MinSTR") > 0)
				lore = lore+ChatColor.DARK_RED + "%enter% Ω 최소 "+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".MinSTR");
			if(itemYaml.getInt(ItemNumber+".MinDEX") > 0)
				lore = lore+ChatColor.DARK_RED + "%enter% Ω 최소 "+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".MinDEX");
			if(itemYaml.getInt(ItemNumber+".MinINT") > 0)
				lore = lore+ChatColor.DARK_RED + "%enter% Ω 최소 "+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".MinINT");
			if(itemYaml.getInt(ItemNumber+".MinWILL") > 0)
				lore = lore+ChatColor.DARK_RED + "%enter% Ω 최소 "+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".MinWILL");
			if(itemYaml.getInt(ItemNumber+".MinLUK") > 0)
				lore = lore+ChatColor.DARK_RED + "%enter% Ω 최소 "+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".MinLUK");
		}
		else if(Type.compareTo("[컬러]")==0)
		{

			lore = lore+itemYaml.getString(ItemNumber+".Type");
			for(int count = 0; count<20-itemYaml.getString(ItemNumber+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(ItemNumber+".JOB").compareTo("공용")==0)
				lore = lore+itemYaml.getString(ItemNumber+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(ItemNumber+".Grade")+"%enter%"+ChatColor.GOLD+"장비 가능 직업 : " +ChatColor.WHITE + itemYaml.getString(ItemNumber+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(ItemNumber+".MinDamage") != 0||itemYaml.getInt(ItemNumber+".MaxDamage") != 0)
				lore = lore+ChatColor.RED + main.Main_ServerOption.Damage+" : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MinDamage") + " ~ " + itemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MinMaDamage") != 0||itemYaml.getInt(ItemNumber+".MaxMaDamage") != 0)
				lore = lore+ChatColor.DARK_AQUA +main.Main_ServerOption.MagicDamage+" : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".DEF") != 0)
				lore = lore+ChatColor.GRAY + "방어 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".DEF")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Protect") != 0)
				lore = lore+ChatColor.WHITE + "보호 : " + ChatColor.WHITE +itemYaml.getInt(ItemNumber+".Protect")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaDEF") != 0)
				lore = lore+ChatColor.BLUE + "마법 방어 : " + ChatColor.WHITE +itemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaProtect") != 0)
				lore = lore+ChatColor.DARK_BLUE + "마법 보호 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Balance") != 0)
				lore = lore+ChatColor.DARK_GREEN + "밸런스 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Balance")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Critical") != 0)
				lore = lore+ChatColor.YELLOW + "크리티컬 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Critical")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxDurability") > 0)
				lore = lore+ChatColor.GOLD + "내구도 : " +ChatColor.WHITE + itemYaml.getInt(ItemNumber+".Durability")+" / "+ itemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
				lore = lore + ChatColor.WHITE+"숙련도 : 0.0%"+ChatColor.WHITE+ "%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(ItemNumber+".Lore")+"%enter%";


			if(itemYaml.getInt(ItemNumber+".HP") > 0)
				lore = lore+ChatColor.DARK_AQUA + "생명력 : " + itemYaml.getInt(ItemNumber+".HP")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".HP") < 0)
				lore = lore+ChatColor.RED + "생명력 : " + itemYaml.getInt(ItemNumber+".HP")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MP") > 0)
				lore = lore+ChatColor.DARK_AQUA + "마나 : " + itemYaml.getInt(ItemNumber+".MP")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".MP") < 0)
				lore = lore+ChatColor.RED + "마나 : " + itemYaml.getInt(ItemNumber+".MP")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".STR") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".STR")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".STR") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".STR")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".DEX") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".DEX")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".DEX") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".DEX")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".INT") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".INT")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".INT") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".INT")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".WILL") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".WILL")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".WILL") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".WILL")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".LUK") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".LUK")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".LUK") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".LUK")+"%enter%";
			
			if(itemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_PURPLE + "개조 : " + "0 / "+itemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxSocket") > 0)
			{
				lore = lore+"%enter%"+ChatColor.BLUE + "룬 : ";
				for(int count = 0; count <itemYaml.getInt(ItemNumber+".MaxSocket");count++)
					lore = lore+ChatColor.GRAY + "○ ";
			}
			if(itemYaml.getInt(ItemNumber+".MinLV")!=0||itemYaml.getInt(ItemNumber+".MinRLV")!=0||
					itemYaml.getInt(ItemNumber+".MinSTR")!=0||itemYaml.getInt(ItemNumber+".MinDEX")!=0||
					itemYaml.getInt(ItemNumber+".MinINT")!=0||itemYaml.getInt(ItemNumber+".MinWILL")!=0||
					itemYaml.getInt(ItemNumber+".MinLUK")!=0)
				lore = lore+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MinLV") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_RED +"최소 레벨 : " + itemYaml.getInt(ItemNumber+".MinLV");
			if(itemYaml.getInt(ItemNumber+".MinRLV") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_RED +"최소 누적레벨 : " + itemYaml.getInt(ItemNumber+".MinRLV");
			if(itemYaml.getInt(ItemNumber+".MinSTR") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_RED +"최소 "+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".MinSTR");
			if(itemYaml.getInt(ItemNumber+".MinDEX") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_RED +"최소 "+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".MinDEX");
			if(itemYaml.getInt(ItemNumber+".MinINT") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_RED +"최소 "+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".MinINT");
			if(itemYaml.getInt(ItemNumber+".MinWILL") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_RED +"최소 "+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".MinWILL");
			if(itemYaml.getInt(ItemNumber+".MinLUK") > 0)
				lore = lore+"%enter%"+ChatColor.DARK_RED +"최소 "+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".MinLUK");
		}
		else
		{
			lore = lore+itemYaml.getString(ItemNumber+".Type");
			for(int count = 0; count<20-itemYaml.getString(ItemNumber+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(ItemNumber+".JOB").compareTo("공용")==0)
				lore = lore+itemYaml.getString(ItemNumber+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(ItemNumber+".Grade")+"%enter%"+ChatColor.WHITE+"장비 가능 직업 : " + itemYaml.getString(ItemNumber+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(ItemNumber+".MinDamage") != 0||itemYaml.getInt(ItemNumber+".MaxDamage") != 0)
				lore = lore+ChatColor.WHITE +main.Main_ServerOption.Damage+" : " + itemYaml.getInt(ItemNumber+".MinDamage") + " ~ " + itemYaml.getInt(ItemNumber+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MinMaDamage") != 0||itemYaml.getInt(ItemNumber+".MaxMaDamage") != 0)
				lore = lore+ChatColor.WHITE +main.Main_ServerOption.MagicDamage+" : " + itemYaml.getInt(ItemNumber+".MinMaDamage") + " ~ " + itemYaml.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".DEF") != 0)
				lore = lore+ChatColor.WHITE + "방어 : " + itemYaml.getInt(ItemNumber+".DEF")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Protect") != 0)
				lore = lore+ChatColor.WHITE + "보호 : " + itemYaml.getInt(ItemNumber+".Protect")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaDEF") != 0)
				lore = lore+ChatColor.WHITE + "마법 방어 : " + itemYaml.getInt(ItemNumber+".MaDEF")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaProtect") != 0)
				lore = lore+ChatColor.WHITE + "마법 보호 : " + itemYaml.getInt(ItemNumber+".MaProtect")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Balance") != 0)
				lore = lore+ChatColor.WHITE + "밸런스 : " + itemYaml.getInt(ItemNumber+".Balance")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".Critical") != 0)
				lore = lore+ChatColor.WHITE + "크리티컬 : " + itemYaml.getInt(ItemNumber+".Critical")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxDurability") > 0)
				lore = lore+ChatColor.WHITE + "내구도 : " + itemYaml.getInt(ItemNumber+".Durability")+" / "+ itemYaml.getInt(ItemNumber+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
				lore = lore + ChatColor.WHITE+"숙련도 : 0.0%"+ChatColor.WHITE+ "%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(ItemNumber+".Lore")+"%enter%";


			if(itemYaml.getInt(ItemNumber+".HP") > 0)
				lore = lore+ChatColor.DARK_AQUA + "생명력 : " + itemYaml.getInt(ItemNumber+".HP")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".HP") < 0)
				lore = lore+ChatColor.RED + "생명력 : " + itemYaml.getInt(ItemNumber+".HP")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MP") > 0)
				lore = lore+ChatColor.DARK_AQUA + "마나 : " + itemYaml.getInt(ItemNumber+".MP")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".MP") < 0)
				lore = lore+ChatColor.RED + "마나 : " + itemYaml.getInt(ItemNumber+".MP")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".STR") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".STR")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".STR") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".STR")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".DEX") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".DEX")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".DEX") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".DEX")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".INT") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".INT")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".INT") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".INT")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".WILL") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".WILL")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".WILL") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".WILL")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".LUK") > 0)
				lore = lore+ChatColor.DARK_AQUA + ""+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".LUK")+"%enter%";
			else if(itemYaml.getInt(ItemNumber+".LUK") < 0)
				lore = lore+ChatColor.RED + ""+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".LUK")+"%enter%";
			
			if(itemYaml.getInt(ItemNumber+".MaxUpgrade") > 0)
				lore = lore+"%enter%"+ChatColor.WHITE + "개조 : " + "0 / "+itemYaml.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MaxSocket") > 0)
			{
				lore = lore+"%enter%"+ChatColor.WHITE + "룬 : ";
				for(int count = 0; count <itemYaml.getInt(ItemNumber+".MaxSocket");count++)
					lore = lore+ChatColor.GRAY + "○ ";
			}
			if(itemYaml.getInt(ItemNumber+".MinLV")!=0||itemYaml.getInt(ItemNumber+".MinRLV")!=0||
					itemYaml.getInt(ItemNumber+".MinSTR")!=0||itemYaml.getInt(ItemNumber+".MinDEX")!=0||
					itemYaml.getInt(ItemNumber+".MinINT")!=0||itemYaml.getInt(ItemNumber+".MinWILL")!=0||
					itemYaml.getInt(ItemNumber+".MinLUK")!=0)
				lore = lore+"%enter%";
			if(itemYaml.getInt(ItemNumber+".MinLV") > 0)
				lore = lore+"%enter%"+ChatColor.WHITE +"최소 레벨 : " + itemYaml.getInt(ItemNumber+".MinLV");
			if(itemYaml.getInt(ItemNumber+".MinRLV") > 0)
				lore = lore+"%enter%"+ChatColor.WHITE +"최소 누적레벨 : " + itemYaml.getInt(ItemNumber+".MinRLV");
			if(itemYaml.getInt(ItemNumber+".MinSTR") > 0)
				lore = lore+"%enter%"+ChatColor.WHITE +"최소 "+main.Main_ServerOption.STR+" : " + itemYaml.getInt(ItemNumber+".MinSTR");
			if(itemYaml.getInt(ItemNumber+".MinDEX") > 0)
				lore = lore+"%enter%"+ChatColor.WHITE +"최소 "+main.Main_ServerOption.DEX+" : " + itemYaml.getInt(ItemNumber+".MinDEX");
			if(itemYaml.getInt(ItemNumber+".MinINT") > 0)
				lore = lore+"%enter%"+ChatColor.WHITE +"최소 "+main.Main_ServerOption.INT+" : " + itemYaml.getInt(ItemNumber+".MinINT");
			if(itemYaml.getInt(ItemNumber+".MinWILL") > 0)
				lore = lore+"%enter%"+ChatColor.WHITE +"최소 "+main.Main_ServerOption.WILL+" : " + itemYaml.getInt(ItemNumber+".MinWILL");
			if(itemYaml.getInt(ItemNumber+".MinLUK") > 0)
				lore = lore+"%enter%"+ChatColor.WHITE +"최소 "+main.Main_ServerOption.LUK+" : " + itemYaml.getInt(ItemNumber+".MinLUK");
		}
		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}
}