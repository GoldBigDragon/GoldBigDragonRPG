package customitem;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import admin.OPboxGui;
import effect.SoundEffect;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;



public class CustomItemGui extends UtilGui
{
	public void itemListGui(Player player, int page)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");

		String uniqueCode = "§0§0§3§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0아이템 목록 : " + (page+1));

		int itemAmounts = itemYaml.getKeys().size();

		byte loc=0;
		for(int count = page*45; count < itemAmounts;count++)
		{
			int number = ((page*45)+loc);
			if(count > itemAmounts || loc >= 45) break;
			String itemName = itemYaml.getString(number+".DisplayName");
			int itemId = itemYaml.getInt(number+".ID");
			int itemData = itemYaml.getInt(number+".Data");
			Stack2(itemName, itemId, itemData, 1,Arrays.asList(loreCreater(number)), loc, inv);
			
			loc++;
		}
		
		if(itemAmounts-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 아이템", 145,0,1,Arrays.asList("§7새로운 아이템을 생성합니다."), 49, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}

	public void newItemGui(Player player, int number)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");

		String uniqueCode = "§0§0§3§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0아이템 상세 설정");
		String itemName = itemYaml.getString(number+".DisplayName");
		short itemID = (short) itemYaml.getInt(number+".ID");
		byte itemData = (byte) itemYaml.getInt(number+".Data");

		String type = "";
		String grade = "";
		for(int counter =0;counter < 13 - itemYaml.getString(number+".Type").length();counter++ )
			type = type +" ";
		type = type +itemYaml.getString(number+".Type");
		grade = itemYaml.getString(number+".Grade");
		
		Stack2("§3[    결과물    ]", 145,0,1,null, 9, inv);
		Stack2("§3[    결과물    ]", 145,0,1,null, 10, inv);
		Stack2("§3[    결과물    ]", 145,0,1,null, 11, inv);
		Stack2("§3[    결과물    ]", 145,0,1,null, 18, inv);
		Stack2("§3[    결과물    ]", 145,0,1,null, 20, inv);
		Stack2("§3[    결과물    ]", 145,0,1,null, 27, inv);
		Stack2("§3[    결과물    ]", 145,0,1,null, 28, inv);
		Stack2("§3[    결과물    ]", 145,0,1,null, 29, inv);
		Stack2(itemName, itemID,itemData,1,Arrays.asList(loreCreater(number)), 19, inv);
		
		Stack2("§3[    형식 변경    ]", 421,0,1,Arrays.asList("§f아이템 설명창을","§f변경합니다.","","§f[    현재 형식    ]","       "+ itemYaml.getString(number+".ShowType"),""), 37, inv);
		Stack2("§3[    이름 변경    ]", 421,0,1,Arrays.asList("§f아이템의 이름을","§f변경합니다.",""), 13, inv);
		Stack2("§3[    설명 변경    ]", 386,0,1,Arrays.asList("§f아이템의 설명을","§f변경합니다.",""), 14, inv);
		Stack2("§3[    타입 변경    ]", 61,0,1,Arrays.asList("§f아이템의 타입을","§f변경합니다.","","§f[    현재 타입    ]",type,""), 15, inv);
		Stack2("§3[    등급 변경    ]", 266,0,1,Arrays.asList("§f아이템의 등급을","§f변경합니다.","","§f[    현재 등급    ]","       "+grade,""), 16, inv);
		Stack2("§3[        ID        ]", 405,0,1,Arrays.asList("§f아이템의 ID값을","§f변경합니다.",""), 22, inv);
		Stack2("§3[       DATA       ]", 336,0,1,Arrays.asList("§f아이템의 DATA값을","§f변경합니다.",""), 23, inv);
		Stack2("§3[       "+main.MainServerOption.damage+"       ]", 267,0,1,Arrays.asList("§f아이템의 "+main.MainServerOption.damage+"를","§f변경합니다.",""), 24, inv);
		Stack2("§3[     "+main.MainServerOption.magicDamage+"     ]", 403,0,1,Arrays.asList("§f아이템의 "+main.MainServerOption.magicDamage+"를","§f변경합니다.",""), 25, inv);
		Stack2("§3[        방어        ]", 307,0,1,Arrays.asList("§f아이템의 방어력을","§f변경합니다.",""), 31, inv);
		Stack2("§3[        보호        ]", 306,0,1,Arrays.asList("§f아이템의 보호를","§f변경합니다.",""), 32, inv);
		Stack2("§3[      마법 방어      ]", 311,0,1,Arrays.asList("§f아이템의 마법 방어를","§f변경합니다.",""), 33, inv);
		Stack2("§3[      마법 보호      ]", 310,0,1,Arrays.asList("§f아이템의 마법 보호를","§f변경합니다.",""), 34, inv);
		Stack2("§3[        스텟        ]", 399,0,1,Arrays.asList("§f아이템의 보너스 스텟을","§f설정합니다.",""), 40, inv);
		Stack2("§3[       내구도       ]", 145,2,1,Arrays.asList("§f아이템의 내구력을","§f조절합니다.","","§c[0 설정시 일반 아이템 내구도 사용]",""), 41, inv);
		Stack2("§3[        개조        ]", 145,0,1,Arrays.asList("§f아이템의 최대 개조 횟수를","§f조절합니다.","","§c[0 설정시 개조 불가능]",""), 42, inv);
		Stack2("§3[         룬         ]", 381,0,1,Arrays.asList("§f아이템의 최대 슬롯을","§f조절합니다.","","§f최대 "+itemYaml.getInt(number+".MaxSocket")+" 개","","§c[0 설정시 룬 장착 불가능]",""), 43, inv);
		Stack2("§3[      스텟 제한      ]", 166,0,1,Arrays.asList("§f아이템 장착에 제한을","§f걸어둡니다.",""), 49, inv);
		Stack2("§3[      직업 제한      ]", 397,3,1,Arrays.asList("§f아이템 장착에 제한을","§f걸어둡니다.","§c[우 클릭시 해제]",""), 50, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+number), 53, inv);
		player.openInventory(inv);
	}
	
	public void jobListGui(Player player, short page, int number)
	{
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		String uniqueCode = "§0§0§3§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0아이템 직업 제한 : " + (page+1));

		String[] jobList = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < jobList.length;count++)
		{
			int jobLevel= jobYaml.getConfigurationSection("MapleStory."+jobList[count]).getKeys(false).size();
			
			if(count > jobList.length || loc >= 45) break;
			
			if(jobList[count].equalsIgnoreCase(configYaml.getString("Server.DefaultJob")))
				Stack2("§f§l" + jobList[count], 403,0,1,Arrays.asList("§3최대 승급 : §f"+jobLevel+"§3차 승급","","§e[좌클릭시 전용 설정]","§e§l[서버 기본 직업]"), loc, inv);
			else
				Stack2("§f§l" + jobList[count], 340,0,1,Arrays.asList("§3최대 승급 : §f"+jobLevel+"§3차 승급","","§e[좌클릭시 전용 설정]"), loc, inv);
			
			loc++;
		}
		
		if(jobList.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+number), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void itemListInventoryclick(InventoryClickEvent event)
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
				new OPboxGui().opBoxGuiMain(player,(byte) 1);
			else if(slot == 48)//이전 페이지
				itemListGui(player,page-1);
			else if(slot == 50)//다음 페이지
				itemListGui(player,page+1);
			else
			{
			  	YamlLoader itemYaml = new YamlLoader();
				itemYaml.getConfig("Item/ItemList.yml");
				if(slot == 49)//새 아이템
				{
					int itemCounter = itemYaml.getKeys().size();
					itemYaml.set(itemCounter+".ShowType","§f[깔끔]");
					itemYaml.set(itemCounter+".ID",267);
					itemYaml.set(itemCounter+".Data",0);
					itemYaml.set(itemCounter+".DisplayName","§f방금 만든 따끈한 검");
					itemYaml.set(itemCounter+".Lore","§f방금 만들어진 무기이다.%enter%§f조금은 허접할지도...");
					itemYaml.set(itemCounter+".Type","§c[근접 무기]");
					itemYaml.set(itemCounter+".Grade","§f[일반]");
					itemYaml.set(itemCounter+".MinDamage",1);
					itemYaml.set(itemCounter+".MaxDamage",7);
					itemYaml.set(itemCounter+".MinMaDamage",0);
					itemYaml.set(itemCounter+".MaxMaDamage",0);
					itemYaml.set(itemCounter+".DEF",0);
					itemYaml.set(itemCounter+".Protect",0);
					itemYaml.set(itemCounter+".MaDEF",0);
					itemYaml.set(itemCounter+".MaProtect",0);
					itemYaml.set(itemCounter+".Durability",256);
					itemYaml.set(itemCounter+".MaxDurability",256);
					itemYaml.set(itemCounter+".STR",0);
					itemYaml.set(itemCounter+".DEX",0);
					itemYaml.set(itemCounter+".INT",0);
					itemYaml.set(itemCounter+".WILL",0);
					itemYaml.set(itemCounter+".LUK",0);
					itemYaml.set(itemCounter+".Balance",10);
					itemYaml.set(itemCounter+".Critical",5);
					itemYaml.set(itemCounter+".MaxUpgrade",5);
					itemYaml.set(itemCounter+".MaxSocket",3);
					itemYaml.set(itemCounter+".HP",0);
					itemYaml.set(itemCounter+".MP",0);
					itemYaml.set(itemCounter+".JOB","공용");
					itemYaml.set(itemCounter+".MinLV",0);
					itemYaml.set(itemCounter+".MinRLV",0);
					itemYaml.set(itemCounter+".MinSTR",0);
					itemYaml.set(itemCounter+".MinDEX",0);
					itemYaml.set(itemCounter+".MinINT",0);
					itemYaml.set(itemCounter+".MinWILL",0);
					itemYaml.set(itemCounter+".MinLUK",0);
					itemYaml.saveConfig();
					newItemGui(player, itemCounter);
				}
				else
				{
					int number = ((page*45)+event.getSlot());
					if(event.isLeftClick()&&!event.isShiftClick())
					{
						player.sendMessage("§a[SYSTEM] : 클릭한 아이템을 지급 받았습니다!");
						player.getInventory().addItem(event.getCurrentItem());
					}
					if(event.isLeftClick()&&event.isShiftClick())
						newItemGui(player, number);
					else if(event.isRightClick()&&event.isShiftClick())
					{
						short acount =  (short) (itemYaml.getKeys().toArray().length-1);
						for(int counter = (short) number;counter <acount;counter++)
							itemYaml.set(counter+"", itemYaml.get((counter+1)+""));
						itemYaml.removeKey(acount+"");
						itemYaml.saveConfig();
						itemListGui(player, page);
					}
				}
			}
		}
	}
	
	public void newItemGuiClick(InventoryClickEvent event)
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
				itemListGui(player, 0);
			else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
			{
				int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			  	YamlLoader itemYaml = new YamlLoader();
				itemYaml.getConfig("Item/ItemList.yml");
				if(itemYaml.getString(itemnumber+"")==null)
				{
					player.sendMessage("§c[SYSTEM] : 다른 OP가 아이템을 삭제하여 반영되지 않았습니다!");
					SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
					player.closeInventory();
					return;
				}
				if(slot==37)//쇼 타입
				{
					if(itemYaml.getString(itemnumber+".ShowType").contains("[깔끔]"))
						itemYaml.set(itemnumber+".ShowType","§e[컬러]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[컬러]"))
						itemYaml.set(itemnumber+".ShowType","§d[기호]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[기호]"))
						itemYaml.set(itemnumber+".ShowType","§9[분류]");
					else if(itemYaml.getString(itemnumber+".ShowType").contains("[분류]"))
						itemYaml.set(itemnumber+".ShowType","§f[깔끔]");
					itemYaml.saveConfig();
					newItemGui(player, itemnumber);
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
							itemYaml.set(itemnumber+".Type","§c[한손 검]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[한손 검]"))
							itemYaml.set(itemnumber+".Type","§c[양손 검]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[양손 검]"))
							itemYaml.set(itemnumber+".Type","§c[도끼]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[도끼]"))
							itemYaml.set(itemnumber+".Type","§c[낫]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[낫]"))
							itemYaml.set(itemnumber+".Type","§2[원거리 무기]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[원거리 무기]"))
							itemYaml.set(itemnumber+".Type","§2[활]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[활]"))
							itemYaml.set(itemnumber+".Type","§2[석궁]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[석궁]"))
							itemYaml.set(itemnumber+".Type","§3[마법 무기]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[마법 무기]"))
							itemYaml.set(itemnumber+".Type","§3[원드]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[원드]"))
							itemYaml.set(itemnumber+".Type","§3[스태프]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[스태프]"))
							itemYaml.set(itemnumber+".Type","§f[방어구]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[방어구]"))
							itemYaml.set(itemnumber+".Type","§7[기타]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[기타]"))
							itemYaml.set(itemnumber+".Type","§c[근접 무기]");
				  	}
				  	else
				  	{
						if(itemYaml.getString(itemnumber+".Type").contains("[근접 무기]"))
							itemYaml.set(itemnumber+".Type","§c[한손 검]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[한손 검]"))
							itemYaml.set(itemnumber+".Type","§c[양손 검]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[양손 검]"))
							itemYaml.set(itemnumber+".Type","§c[도끼]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[도끼]"))
							itemYaml.set(itemnumber+".Type","§c[낫]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[낫]"))
							itemYaml.set(itemnumber+".Type","§2[원거리 무기]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[원거리 무기]"))
							itemYaml.set(itemnumber+".Type","§2[활]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[활]"))
							itemYaml.set(itemnumber+".Type","§2[석궁]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[석궁]"))
							itemYaml.set(itemnumber+".Type","§3[마법 무기]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[마법 무기]"))
							itemYaml.set(itemnumber+".Type","§3[원드]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[원드]"))
							itemYaml.set(itemnumber+".Type","§3[스태프]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[스태프]"))
							itemYaml.set(itemnumber+".Type","§f[방어구]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[방어구]"))
							itemYaml.set(itemnumber+".Type","§7[기타]");
						else if(itemYaml.getString(itemnumber+".Type").contains("[기타]"))
							itemYaml.set(itemnumber+".Type","§f"+weaponCustomType[0]);
						else
						{
							for(int count = 0; count < weaponCustomType.length; count++)
							{
								if((itemYaml.getString(itemnumber+".Type").contains(weaponCustomType[count])))
								{
									if(count+1 == weaponCustomType.length)
										itemYaml.set(itemnumber+".Type","§c[근접 무기]");
									else
										itemYaml.set(itemnumber+".Type","§f"+weaponCustomType[(count+1)]);
									break;
								}
							}
						}
				  	}
					itemYaml.saveConfig();
					newItemGui(player, itemnumber);
				}
				else if(slot==16)//등급 변경
				{
					if(itemYaml.getString(itemnumber+".Grade").contains("[일반]"))
						itemYaml.set(itemnumber+".Grade","§a[상급]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[상급]"))
						itemYaml.set(itemnumber+".Grade","§9[매직]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[매직]"))
						itemYaml.set(itemnumber+".Grade","§e[레어]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[레어]"))
						itemYaml.set(itemnumber+".Grade","§5[에픽]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[에픽]"))
						itemYaml.set(itemnumber+".Grade","§6[전설]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[전설]"))
						itemYaml.set(itemnumber+".Grade","§4§l[§6§l초§2§l월§1§l]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("초"))
						itemYaml.set(itemnumber+".Grade","§7[하급]");
					else if(itemYaml.getString(itemnumber+".Grade").contains("[하급]"))
						itemYaml.set(itemnumber+".Grade","§f[일반]");
					itemYaml.saveConfig();
					newItemGui(player, itemnumber);
				}
				else if(slot==43)//최대 슬롯 설정
				{
					if(itemYaml.getInt(itemnumber+".MaxSocket") < 5)
						itemYaml.set(itemnumber+".MaxSocket",itemYaml.getInt(itemnumber+".MaxSocket")+1);
					else if(itemYaml.getInt(itemnumber+".MaxSocket") == 5)
							itemYaml.set(itemnumber+".MaxSocket", 0);
					itemYaml.saveConfig();
					newItemGui(player, itemnumber);
				}
				else if(slot==50)//직업 제한
				{
					if(event.isLeftClick())
						jobListGui(player, (short)0, itemnumber);
					else if(event.isRightClick())
					{
						SoundEffect.SP(player, Sound.ITEM_SHIELD_BREAK, 0.8F, 1.0F);
						itemYaml.set(itemnumber+".JOB", "공용");
						itemYaml.saveConfig();
						newItemGui(player, itemnumber);
					}
				}
				else
				{
					UserDataObject u = new UserDataObject();
					player.closeInventory();
					u.setType(player, "Item");
					u.setInt(player, (byte)3, itemnumber);
					if(slot == 13 || slot == 14)
					{
						if(slot == 13)
						{
							player.sendMessage("§3[아이템] : 아이템의 이름을 입력해 주세요!");
							u.setString(player, (byte)1, "DisplayName");
						}
						else
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
					else if(slot == 22)//ID변경
					{
						player.sendMessage("§3[아이템] : 아이템의 ID 값을 입력해 주세요!");
						u.setString(player, (byte)1, "ID");
					}
					else if(slot == 23)//DATA변경
					{
						player.sendMessage("§3[아이템] : 아이템의 DATA 값을 입력해 주세요!");
						u.setString(player, (byte)1, "Data");
					}
					else if(slot == 24)//대미지 변경
					{
						player.sendMessage("§3[아이템] : 아이템의 최소 "+main.MainServerOption.damage+"를 입력해 주세요!");
						player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MinDamage");
					}
					else if(slot == 25)//마법 대미지 변경
					{
						player.sendMessage("§3[아이템] : 아이템의 최소 "+main.MainServerOption.magicDamage+"를 입력해 주세요!");
						player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MinMaDamage");
					}
					else if(slot == 31)//방어변경
					{
						player.sendMessage("§3[아이템] : 아이템의 방어력을 입력해 주세요!");
						player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "DEF");
					}
					else if(slot == 32)//보호변경
					{
						player.sendMessage("§3[아이템] : 아이템의 보호를 입력해 주세요!");
						player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "Protect");
					}
					else if(slot == 33)//마법 방어 변경
					{
						player.sendMessage("§3[아이템] : 아이템의 마법 방어력을 입력해 주세요!");
						player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaDEF");
					}
					else if(slot == 34)//마법 보호 변경
					{
						player.sendMessage("§3[아이템] : 아이템의 마법 보호를 입력해 주세요!");
						player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaProtect");
					}
					else if(slot == 40)//스텟 보너스
					{
						player.sendMessage("§3[아이템] : 아이템의 생명력 보너스를 입력해 주세요!");
						player.sendMessage("§3(-127 ~ 127)");
						u.setString(player, (byte)1, "HP");
					}
					else if(slot == 41)//내구도 설정
					{
						player.sendMessage("§3[아이템] : 아이템의 최대 내구력을 입력해 주세요!");
						player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
						u.setString(player, (byte)1, "MaxDurability");
					}
					else if(slot == 42)//개조 횟수 설정
					{
						player.sendMessage("§3[아이템] : 아이템의 최대 개조 횟수를 입력해 주세요!");
						player.sendMessage("§3(0 ~ 127)");
						u.setString(player, (byte)1, "MaxUpgrade");
					}
					else if(slot == 49)//스텟 제한
					{
						player.sendMessage("§3[아이템] : 아이템의 레벨 제한을 입력 해 주세요!");
						player.sendMessage("§3(0 ~ "+Integer.MAX_VALUE+")");
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
				newItemGui(player, itemnumber);
			else
			{
			  	YamlLoader temYaml = new YamlLoader();
				temYaml.getConfig("Item/ItemList.yml");
				temYaml.set(itemnumber+".JOB", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				temYaml.saveConfig();
				SoundEffect.SP(player, Sound.ITEM_SHIELD_BREAK, 0.8F, 1.0F);
				newItemGui(player, itemnumber);
			}
		}
	}
	

	public String[] loreCreater(int itemNumbe)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");
		String lore = "";
		String type = ChatColor.stripColor(itemYaml.getString(itemNumbe+".ShowType"));
		if(type.equals("[분류]"))
		{
			lore = lore+itemYaml.getString(itemNumbe+".Type");
			for(int count = 0; count<20-itemYaml.getString(itemNumbe+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(itemNumbe+".JOB").equals("공용"))
				lore = lore+itemYaml.getString(itemNumbe+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(itemNumbe+".Grade")+"%enter%§6장비 가능 직업 : §f" + itemYaml.getString(itemNumbe+".JOB")+"%enter%%enter%";
				

			if(itemYaml.getInt(itemNumbe+".MinDamage") != 0||itemYaml.getInt(itemNumbe+".MaxDamage") != 0)
				lore = lore+"§c ▩ "+main.MainServerOption.damage+" : §f" + itemYaml.getInt(itemNumbe+".MinDamage") + " ~ " + itemYaml.getInt(itemNumbe+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MinMaDamage") != 0||itemYaml.getInt(itemNumbe+".MaxMaDamage") != 0)
				lore = lore+"§3 ▦ "+main.MainServerOption.magicDamage+" : §f" + itemYaml.getInt(itemNumbe+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumbe+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".DEF") != 0)
				lore = lore+"§7 ▧ 방어 : §f" + itemYaml.getInt(itemNumbe+".DEF")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Protect") != 0)
				lore = lore+"§f ■ 보호 : §f" +itemYaml.getInt(itemNumbe+".Protect")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaDEF") != 0)
				lore = lore+"§9 ◇ 마법 방어 : §f" +itemYaml.getInt(itemNumbe+".MaDEF")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaProtect") != 0)
				lore = lore+"§1 ◆ 마법 보호 : §f" + itemYaml.getInt(itemNumbe+".MaProtect")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Balance") != 0)
				lore = lore+"§2 △ 밸런스 : §f" + itemYaml.getInt(itemNumbe+".Balance")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Critical") != 0)
				lore = lore+"§e ▲ 크리티컬 : §f" + itemYaml.getInt(itemNumbe+".Critical")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxDurability") > 0)
				lore = lore+"§6 ▣ 내구도 : §f" + itemYaml.getInt(itemNumbe+".Durability")+" / "+ itemYaml.getInt(itemNumbe+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxUpgrade") > 0)
				lore = lore + "§f ♣ 숙련도 : 0.0%§f%enter%";
			
			lore = lore+"§6___--------------------___%enter%§6       [아이템 설명]";
			lore = lore+"%enter%"+ itemYaml.getString(itemNumbe+".Lore")+"%enter%";
			lore = lore+"§6---____________________---%enter%";


			if(itemYaml.getInt(itemNumbe+".HP")!=0||itemYaml.getInt(itemNumbe+".MP")!=0||
					itemYaml.getInt(itemNumbe+".STR")!=0||itemYaml.getInt(itemNumbe+".DEX")!=0||
					itemYaml.getInt(itemNumbe+".INT")!=0||itemYaml.getInt(itemNumbe+".WILL")!=0||
					itemYaml.getInt(itemNumbe+".LUK")!=0)
			{
				lore = lore+"§3___--------------------___%enter%§3       [보너스 옵션]%enter%";
				if(itemYaml.getInt(itemNumbe+".HP") > 0)
					lore = lore+"§3 ▲ 생명력 : " + itemYaml.getInt(itemNumbe+".HP")+"%enter%";
				else if(itemYaml.getInt(itemNumbe+".HP") < 0)
					lore = lore+"§c ▼ 생명력 : " + itemYaml.getInt(itemNumbe+".HP")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".MP") > 0)
					lore = lore+"§3 ▲ 마나 : " + itemYaml.getInt(itemNumbe+".MP")+"%enter%";
				else if(itemYaml.getInt(itemNumbe+".MP") < 0)
					lore = lore+"§c ▼ 마나 : " + itemYaml.getInt(itemNumbe+".MP")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".STR") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".STR")+"%enter%";
				else if(itemYaml.getInt(itemNumbe+".STR") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".STR")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".DEX") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".DEX")+"%enter%";
				else if(itemYaml.getInt(itemNumbe+".DEX") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".DEX")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".INT") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".INT")+"%enter%";
				else if(itemYaml.getInt(itemNumbe+".INT") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".INT")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".WILL") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".WILL")+"%enter%";
				else if(itemYaml.getInt(itemNumbe+".WILL") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".WILL")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".LUK") > 0)
					lore = lore+"§3 ▲ "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".LUK")+"%enter%";
				else if(itemYaml.getInt(itemNumbe+".LUK") < 0)
					lore = lore+"§c ▼ "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".LUK")+"%enter%";
				lore = lore+"§3---____________________---%enter%";
			}

			if(itemYaml.getInt(itemNumbe+".MaxSocket")!=0||itemYaml.getInt(itemNumbe+".MaxUpgrade")!=0)
			{
				lore = lore+"§d___--------------------___%enter%§d       [아이템 강화]%enter%";
				if(itemYaml.getInt(itemNumbe+".MaxUpgrade") > 0 && itemYaml.getInt(itemNumbe+".MaxSocket") > 0)
				{
					lore = lore+"§5 ★ 개조 : 0 / "+itemYaml.getInt(itemNumbe+".MaxUpgrade")+"%enter%";
					lore = lore+"§9 ⓛ 룬 : ";
					for(int count = 0; count <itemYaml.getInt(itemNumbe+".MaxSocket");count++)
						lore = lore+"§7○ ";
				}
				else if(itemYaml.getInt(itemNumbe+".MaxUpgrade") <= 0 && itemYaml.getInt(itemNumbe+".MaxSocket") > 0)
				{
					lore = lore+"§9 ⓛ 룬 : ";
					for(int count = 0; count <itemYaml.getInt(itemNumbe+".MaxSocket");count++)
						lore = lore+"§7○ ";
				}
				else
					lore = lore+"§5 ★ 개조 : 0 / "+itemYaml.getInt(itemNumbe+".MaxUpgrade");
				lore = lore+"%enter%§d---____________________---%enter%";
			}
			if(itemYaml.getInt(itemNumbe+".MinLV")!=0||itemYaml.getInt(itemNumbe+".MinRLV")!=0||
					itemYaml.getInt(itemNumbe+".MinSTR")!=0||itemYaml.getInt(itemNumbe+".MinDEX")!=0||
					itemYaml.getInt(itemNumbe+".MinINT")!=0||itemYaml.getInt(itemNumbe+".MinWILL")!=0||
					itemYaml.getInt(itemNumbe+".MinLUK")!=0)
			{
				lore = lore+"§4___--------------------___%enter%§4       [제한 스텟]%enter%";
				if(itemYaml.getInt(itemNumbe+".MinLV") > 0)
					lore = lore+"§4 Ω 최소 레벨 : " + itemYaml.getInt(itemNumbe+".MinLV")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".MinRLV") > 0)
					lore = lore+"§4 Ω 최소 누적레벨 : " + itemYaml.getInt(itemNumbe+".MinRLV")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".MinSTR") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".MinSTR")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".MinDEX") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".MinDEX")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".MinINT") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".MinINT")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".MinWILL") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".MinWILL")+"%enter%";
				if(itemYaml.getInt(itemNumbe+".MinLUK") > 0)
					lore = lore+"§4 Ω 최소 "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".MinLUK")+"%enter%";
				lore = lore+"§4---____________________---%enter%";
				
			}
		}
		else if(type.equals("[기호]"))
		{
			lore = lore+itemYaml.getString(itemNumbe+".Type");
			for(int count = 0; count<20-itemYaml.getString(itemNumbe+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(itemNumbe+".JOB").equals("공용"))
				lore = lore+itemYaml.getString(itemNumbe+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(itemNumbe+".Grade")+"%enter%§6장비 가능 직업 : §f" + itemYaml.getString(itemNumbe+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(itemNumbe+".MinDamage") != 0||itemYaml.getInt(itemNumbe+".MaxDamage") != 0)
				lore = lore+"§c ▩ "+main.MainServerOption.damage+" : §f" + itemYaml.getInt(itemNumbe+".MinDamage") + " ~ " + itemYaml.getInt(itemNumbe+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MinMaDamage") != 0||itemYaml.getInt(itemNumbe+".MaxMaDamage") != 0)
				lore = lore+"§3 ▦ "+main.MainServerOption.magicDamage+" : §f" + itemYaml.getInt(itemNumbe+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumbe+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".DEF") != 0)
				lore = lore+"§7 ▧ 방어 : §f" + itemYaml.getInt(itemNumbe+".DEF")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Protect") != 0)
				lore = lore+"§f ■ 보호 : §f" +itemYaml.getInt(itemNumbe+".Protect")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaDEF") != 0)
				lore = lore+"§9 ◇ 마법 방어 : §f" +itemYaml.getInt(itemNumbe+".MaDEF")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaProtect") != 0)
				lore = lore+"§1 ◆ 마법 보호 : §f" + itemYaml.getInt(itemNumbe+".MaProtect")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Balance") != 0)
				lore = lore+"§2 △ 밸런스 : §f" + itemYaml.getInt(itemNumbe+".Balance")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Critical") != 0)
				lore = lore+"§e ▲ 크리티컬 : §f" + itemYaml.getInt(itemNumbe+".Critical")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxDurability") > 0)
				lore = lore+"§6 ▣ 내구도 : §f" + itemYaml.getInt(itemNumbe+".Durability")+" / "+ itemYaml.getInt(itemNumbe+".MaxDurability")+"%enter%";

			if(itemYaml.getInt(itemNumbe+".MaxUpgrade") > 0)
				lore = lore + "§f ♣ 숙련도 : 0.0%§f%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(itemNumbe+".Lore")+"%enter%";


			if(itemYaml.getInt(itemNumbe+".HP") > 0)
				lore = lore+"§3 ▲ 생명력 : " + itemYaml.getInt(itemNumbe+".HP")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".HP") < 0)
				lore = lore+"§c ▼ 생명력 : " + itemYaml.getInt(itemNumbe+".HP")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MP") > 0)
				lore = lore+"§3 ▲ 마나 : " + itemYaml.getInt(itemNumbe+".MP")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".MP") < 0)
				lore = lore+"§c ▼ 마나 : " + itemYaml.getInt(itemNumbe+".MP")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".STR") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".STR")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".STR") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".STR")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".DEX") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".DEX")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".DEX") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".DEX")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".INT") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".INT")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".INT") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".INT")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".WILL") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".WILL")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".WILL") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".WILL")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".LUK") > 0)
				lore = lore+"§3 ▲ "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".LUK")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".LUK") < 0)
				lore = lore+"§c ▼ "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".LUK")+"%enter%";
			
			if(itemYaml.getInt(itemNumbe+".MaxUpgrade") > 0)
				lore = lore+"%enter%§5 ★ 개조 : 0 / "+itemYaml.getInt(itemNumbe+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxSocket") > 0)
			{
				lore = lore+"%enter%§9 ⓛ 룬 : ";
				for(int count = 0; count <itemYaml.getInt(itemNumbe+".MaxSocket");count++)
					lore = lore+"§7○ ";
			}

			if(itemYaml.getInt(itemNumbe+".MinLV")!=0||itemYaml.getInt(itemNumbe+".MinRLV")!=0||
					itemYaml.getInt(itemNumbe+".MinSTR")!=0||itemYaml.getInt(itemNumbe+".MinDEX")!=0||
					itemYaml.getInt(itemNumbe+".MinINT")!=0||itemYaml.getInt(itemNumbe+".MinWILL")!=0||
					itemYaml.getInt(itemNumbe+".MinLUK")!=0)
				lore = lore+"%enter%";
			
			if(itemYaml.getInt(itemNumbe+".MinLV") > 0)
				lore = lore+"§4%enter% Ω 최소 레벨 : " + itemYaml.getInt(itemNumbe+".MinLV");
			if(itemYaml.getInt(itemNumbe+".MinRLV") > 0)
				lore = lore+"§4%enter% Ω 최소 누적레벨 : " + itemYaml.getInt(itemNumbe+".MinRLV");
			if(itemYaml.getInt(itemNumbe+".MinSTR") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".MinSTR");
			if(itemYaml.getInt(itemNumbe+".MinDEX") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".MinDEX");
			if(itemYaml.getInt(itemNumbe+".MinINT") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".MinINT");
			if(itemYaml.getInt(itemNumbe+".MinWILL") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".MinWILL");
			if(itemYaml.getInt(itemNumbe+".MinLUK") > 0)
				lore = lore+"§4%enter% Ω 최소 "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".MinLUK");
		}
		else if(type.equals("[컬러]"))
		{

			lore = lore+itemYaml.getString(itemNumbe+".Type");
			for(int count = 0; count<20-itemYaml.getString(itemNumbe+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(itemNumbe+".JOB").equals("공용"))
				lore = lore+itemYaml.getString(itemNumbe+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(itemNumbe+".Grade")+"%enter%§6장비 가능 직업 : §f" + itemYaml.getString(itemNumbe+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(itemNumbe+".MinDamage") != 0||itemYaml.getInt(itemNumbe+".MaxDamage") != 0)
				lore = lore+ChatColor.RED + main.MainServerOption.damage+" : §f" + itemYaml.getInt(itemNumbe+".MinDamage") + " ~ " + itemYaml.getInt(itemNumbe+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MinMaDamage") != 0||itemYaml.getInt(itemNumbe+".MaxMaDamage") != 0)
				lore = lore+"§3"+main.MainServerOption.magicDamage+" : §f" + itemYaml.getInt(itemNumbe+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumbe+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".DEF") != 0)
				lore = lore+"§7방어 : §f" + itemYaml.getInt(itemNumbe+".DEF")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Protect") != 0)
				lore = lore+"§f보호 : §f" +itemYaml.getInt(itemNumbe+".Protect")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaDEF") != 0)
				lore = lore+"§9마법 방어 : §f" +itemYaml.getInt(itemNumbe+".MaDEF")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaProtect") != 0)
				lore = lore+"§1마법 보호 : §f" + itemYaml.getInt(itemNumbe+".MaProtect")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Balance") != 0)
				lore = lore+"§2밸런스 : §f" + itemYaml.getInt(itemNumbe+".Balance")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Critical") != 0)
				lore = lore+"§e크리티컬 : §f" + itemYaml.getInt(itemNumbe+".Critical")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxDurability") > 0)
				lore = lore+"§6내구도 : §f" + itemYaml.getInt(itemNumbe+".Durability")+" / "+ itemYaml.getInt(itemNumbe+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxUpgrade") > 0)
				lore = lore + "§f숙련도 : 0.0%§f%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(itemNumbe+".Lore")+"%enter%";


			if(itemYaml.getInt(itemNumbe+".HP") > 0)
				lore = lore+"§3생명력 : " + itemYaml.getInt(itemNumbe+".HP")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".HP") < 0)
				lore = lore+"§c생명력 : " + itemYaml.getInt(itemNumbe+".HP")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MP") > 0)
				lore = lore+"§3마나 : " + itemYaml.getInt(itemNumbe+".MP")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".MP") < 0)
				lore = lore+"§c마나 : " + itemYaml.getInt(itemNumbe+".MP")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".STR") > 0)
				lore = lore+"§3"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".STR")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".STR") < 0)
				lore = lore+"§c"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".STR")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".DEX") > 0)
				lore = lore+"§3"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".DEX")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".DEX") < 0)
				lore = lore+"§c"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".DEX")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".INT") > 0)
				lore = lore+"§3"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".INT")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".INT") < 0)
				lore = lore+"§c"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".INT")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".WILL") > 0)
				lore = lore+"§3"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".WILL")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".WILL") < 0)
				lore = lore+"§c"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".WILL")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".LUK") > 0)
				lore = lore+"§3"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".LUK")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".LUK") < 0)
				lore = lore+"§c"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".LUK")+"%enter%";
			
			if(itemYaml.getInt(itemNumbe+".MaxUpgrade") > 0)
				lore = lore+"%enter%§5개조 : 0 / "+itemYaml.getInt(itemNumbe+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxSocket") > 0)
			{
				lore = lore+"%enter%§9룬 : ";
				for(int count = 0; count <itemYaml.getInt(itemNumbe+".MaxSocket");count++)
					lore = lore+"§7○ ";
			}
			if(itemYaml.getInt(itemNumbe+".MinLV")!=0||itemYaml.getInt(itemNumbe+".MinRLV")!=0||
					itemYaml.getInt(itemNumbe+".MinSTR")!=0||itemYaml.getInt(itemNumbe+".MinDEX")!=0||
					itemYaml.getInt(itemNumbe+".MinINT")!=0||itemYaml.getInt(itemNumbe+".MinWILL")!=0||
					itemYaml.getInt(itemNumbe+".MinLUK")!=0)
				lore = lore+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MinLV") > 0)
				lore = lore+"%enter%§4최소 레벨 : " + itemYaml.getInt(itemNumbe+".MinLV");
			if(itemYaml.getInt(itemNumbe+".MinRLV") > 0)
				lore = lore+"%enter%§4최소 누적레벨 : " + itemYaml.getInt(itemNumbe+".MinRLV");
			if(itemYaml.getInt(itemNumbe+".MinSTR") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".MinSTR");
			if(itemYaml.getInt(itemNumbe+".MinDEX") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".MinDEX");
			if(itemYaml.getInt(itemNumbe+".MinINT") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".MinINT");
			if(itemYaml.getInt(itemNumbe+".MinWILL") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".MinWILL");
			if(itemYaml.getInt(itemNumbe+".MinLUK") > 0)
				lore = lore+"%enter%§4최소 "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".MinLUK");
		}
		else
		{
			lore = lore+itemYaml.getString(itemNumbe+".Type");
			for(int count = 0; count<20-itemYaml.getString(itemNumbe+".Type").length();count++)
				lore = lore+" ";
			if(itemYaml.getString(itemNumbe+".JOB").equals("공용"))
				lore = lore+itemYaml.getString(itemNumbe+".Grade")+"%enter%%enter%";
			else
				lore = lore+itemYaml.getString(itemNumbe+".Grade")+"%enter%§f장비 가능 직업 : " + itemYaml.getString(itemNumbe+".JOB")+"%enter%%enter%";
				
			if(itemYaml.getInt(itemNumbe+".MinDamage") != 0||itemYaml.getInt(itemNumbe+".MaxDamage") != 0)
				lore = lore+"§f"+main.MainServerOption.damage+" : " + itemYaml.getInt(itemNumbe+".MinDamage") + " ~ " + itemYaml.getInt(itemNumbe+".MaxDamage")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MinMaDamage") != 0||itemYaml.getInt(itemNumbe+".MaxMaDamage") != 0)
				lore = lore+"§f"+main.MainServerOption.magicDamage+" : " + itemYaml.getInt(itemNumbe+".MinMaDamage") + " ~ " + itemYaml.getInt(itemNumbe+".MaxMaDamage")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".DEF") != 0)
				lore = lore+"§f방어 : " + itemYaml.getInt(itemNumbe+".DEF")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Protect") != 0)
				lore = lore+"§f보호 : " + itemYaml.getInt(itemNumbe+".Protect")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaDEF") != 0)
				lore = lore+"§f마법 방어 : " + itemYaml.getInt(itemNumbe+".MaDEF")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaProtect") != 0)
				lore = lore+"§f마법 보호 : " + itemYaml.getInt(itemNumbe+".MaProtect")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Balance") != 0)
				lore = lore+"§f밸런스 : " + itemYaml.getInt(itemNumbe+".Balance")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".Critical") != 0)
				lore = lore+"§f크리티컬 : " + itemYaml.getInt(itemNumbe+".Critical")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxDurability") > 0)
				lore = lore+"§f내구도 : " + itemYaml.getInt(itemNumbe+".Durability")+" / "+ itemYaml.getInt(itemNumbe+".MaxDurability")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxUpgrade") > 0)
				lore = lore + "§f숙련도 : 0.0%§f%enter%";
			
			lore = lore+"%enter%"+ itemYaml.getString(itemNumbe+".Lore")+"%enter%";


			if(itemYaml.getInt(itemNumbe+".HP") > 0)
				lore = lore+"§3생명력 : " + itemYaml.getInt(itemNumbe+".HP")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".HP") < 0)
				lore = lore+"§c생명력 : " + itemYaml.getInt(itemNumbe+".HP")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MP") > 0)
				lore = lore+"§3마나 : " + itemYaml.getInt(itemNumbe+".MP")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".MP") < 0)
				lore = lore+"§c마나 : " + itemYaml.getInt(itemNumbe+".MP")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".STR") > 0)
				lore = lore+"§3"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".STR")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".STR") < 0)
				lore = lore+"§c"+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".STR")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".DEX") > 0)
				lore = lore+"§3"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".DEX")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".DEX") < 0)
				lore = lore+"§c"+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".DEX")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".INT") > 0)
				lore = lore+"§3"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".INT")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".INT") < 0)
				lore = lore+"§c"+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".INT")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".WILL") > 0)
				lore = lore+"§3"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".WILL")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".WILL") < 0)
				lore = lore+"§c"+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".WILL")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".LUK") > 0)
				lore = lore+"§3"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".LUK")+"%enter%";
			else if(itemYaml.getInt(itemNumbe+".LUK") < 0)
				lore = lore+"§c"+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".LUK")+"%enter%";
			
			if(itemYaml.getInt(itemNumbe+".MaxUpgrade") > 0)
				lore = lore+"%enter%§f개조 : 0 / "+itemYaml.getInt(itemNumbe+".MaxUpgrade")+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MaxSocket") > 0)
			{
				lore = lore+"%enter%§f룬 : ";
				for(int count = 0; count <itemYaml.getInt(itemNumbe+".MaxSocket");count++)
					lore = lore+"§7○ ";
			}
			if(itemYaml.getInt(itemNumbe+".MinLV")!=0||itemYaml.getInt(itemNumbe+".MinRLV")!=0||
					itemYaml.getInt(itemNumbe+".MinSTR")!=0||itemYaml.getInt(itemNumbe+".MinDEX")!=0||
					itemYaml.getInt(itemNumbe+".MinINT")!=0||itemYaml.getInt(itemNumbe+".MinWILL")!=0||
					itemYaml.getInt(itemNumbe+".MinLUK")!=0)
				lore = lore+"%enter%";
			if(itemYaml.getInt(itemNumbe+".MinLV") > 0)
				lore = lore+"%enter%§f최소 레벨 : " + itemYaml.getInt(itemNumbe+".MinLV");
			if(itemYaml.getInt(itemNumbe+".MinRLV") > 0)
				lore = lore+"%enter%§f최소 누적레벨 : " + itemYaml.getInt(itemNumbe+".MinRLV");
			if(itemYaml.getInt(itemNumbe+".MinSTR") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statSTR+" : " + itemYaml.getInt(itemNumbe+".MinSTR");
			if(itemYaml.getInt(itemNumbe+".MinDEX") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statDEX+" : " + itemYaml.getInt(itemNumbe+".MinDEX");
			if(itemYaml.getInt(itemNumbe+".MinINT") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statINT+" : " + itemYaml.getInt(itemNumbe+".MinINT");
			if(itemYaml.getInt(itemNumbe+".MinWILL") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statWILL+" : " + itemYaml.getInt(itemNumbe+".MinWILL");
			if(itemYaml.getInt(itemNumbe+".MinLUK") > 0)
				lore = lore+"%enter%§f최소 "+main.MainServerOption.statLUK+" : " + itemYaml.getInt(itemNumbe+".MinLUK");
		}
		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}
}