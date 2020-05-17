package customitem.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import customitem.CustomItemAPI;
import effect.SoundEffect;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class EquipItemForgingGui extends GuiUtil{

	private String uniqueCode = "§0§0§3§0§1§r";
	
	public void itemForgingGui(Player player, int number)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0아이템 상세 설정");
		String itemName = itemYaml.getString(number+".DisplayName");
		short itemID = (short) itemYaml.getInt(number+".ID");
		byte itemData = (byte) itemYaml.getInt(number+".Data");

		String type = "";
		String grade = "";
		CustomItemAPI ciapi = new CustomItemAPI();
		
		for(int counter =0;counter < 13 - itemYaml.getString(number+".Type").length();counter++ )
			type = type +" ";
		type = type +itemYaml.getString(number+".Type");
		grade = itemYaml.getString(number+".Grade");
		
		removeFlagStack("§3[    결과물    ]", 145,0,1,null, 9, inv);
		removeFlagStack("§3[    결과물    ]", 145,0,1,null, 10, inv);
		removeFlagStack("§3[    결과물    ]", 145,0,1,null, 11, inv);
		removeFlagStack("§3[    결과물    ]", 145,0,1,null, 18, inv);
		removeFlagStack("§3[    결과물    ]", 145,0,1,null, 20, inv);
		removeFlagStack("§3[    결과물    ]", 145,0,1,null, 27, inv);
		removeFlagStack("§3[    결과물    ]", 145,0,1,null, 28, inv);
		removeFlagStack("§3[    결과물    ]", 145,0,1,null, 29, inv);
		removeFlagStack(itemName, itemID,itemData,1,Arrays.asList(ciapi.loreCreater(number)), 19, inv);
		
		removeFlagStack("§3[    형식 변경    ]", 421,0,1,Arrays.asList("§f아이템 설명창을","§f변경합니다.","","§f[    현재 형식    ]","       "+ itemYaml.getString(number+".ShowType"),""), 37, inv);
		removeFlagStack("§3[    이름 변경    ]", 421,0,1,Arrays.asList("§f아이템의 이름을","§f변경합니다.",""), 13, inv);
		removeFlagStack("§3[    설명 변경    ]", 386,0,1,Arrays.asList("§f아이템의 설명을","§f변경합니다.",""), 14, inv);
		removeFlagStack("§3[    타입 변경    ]", 61,0,1,Arrays.asList("§f아이템의 타입을","§f변경합니다.","","§f[    현재 타입    ]",type,""), 15, inv);
		removeFlagStack("§3[    등급 변경    ]", 266,0,1,Arrays.asList("§f아이템의 등급을","§f변경합니다.","","§f[    현재 등급    ]","       "+grade,""), 16, inv);
		removeFlagStack("§3[        ID        ]", 405,0,1,Arrays.asList("§f아이템의 ID값을","§f변경합니다.",""), 22, inv);
		removeFlagStack("§3[       DATA       ]", 336,0,1,Arrays.asList("§f아이템의 DATA값을","§f변경합니다.",""), 23, inv);
		removeFlagStack("§3[       "+main.MainServerOption.damage+"       ]", 267,0,1,Arrays.asList("§f아이템의 "+main.MainServerOption.damage+"를","§f변경합니다.",""), 24, inv);
		removeFlagStack("§3[     "+main.MainServerOption.magicDamage+"     ]", 403,0,1,Arrays.asList("§f아이템의 "+main.MainServerOption.magicDamage+"를","§f변경합니다.",""), 25, inv);
		removeFlagStack("§3[        방어        ]", 307,0,1,Arrays.asList("§f아이템의 방어력을","§f변경합니다.",""), 31, inv);
		removeFlagStack("§3[        보호        ]", 306,0,1,Arrays.asList("§f아이템의 보호를","§f변경합니다.",""), 32, inv);
		removeFlagStack("§3[      마법 방어      ]", 311,0,1,Arrays.asList("§f아이템의 마법 방어를","§f변경합니다.",""), 33, inv);
		removeFlagStack("§3[      마법 보호      ]", 310,0,1,Arrays.asList("§f아이템의 마법 보호를","§f변경합니다.",""), 34, inv);
		removeFlagStack("§3[        스텟        ]", 399,0,1,Arrays.asList("§f아이템의 보너스 스텟을","§f설정합니다.",""), 40, inv);
		removeFlagStack("§3[       내구도       ]", 145,2,1,Arrays.asList("§f아이템의 내구력을","§f조절합니다.","","§c[0 설정시 일반 아이템 내구도 사용]",""), 41, inv);
		removeFlagStack("§3[        개조        ]", 145,0,1,Arrays.asList("§f아이템의 최대 개조 횟수를","§f조절합니다.","","§c[0 설정시 개조 불가능]",""), 42, inv);
		removeFlagStack("§3[         룬         ]", 381,0,1,Arrays.asList("§f아이템의 최대 슬롯을","§f조절합니다.","","§f최대 "+itemYaml.getInt(number+".MaxSocket")+" 개","","§c[0 설정시 룬 장착 불가능]",""), 43, inv);
		removeFlagStack("§3[      스텟 제한      ]", 166,0,1,Arrays.asList("§f아이템 장착에 제한을","§f걸어둡니다.",""), 49, inv);
		removeFlagStack("§3[      직업 제한      ]", 397,3,1,Arrays.asList("§f아이템 장착에 제한을","§f걸어둡니다.","§c[우 클릭시 해제]",""), 50, inv);
		
		boolean removeDelay = itemYaml.getBoolean(number+".NoAttackDelay");
		if(removeDelay)
			removeFlagStack("§c[  공격 딜레이 없음      ]", 166,0,1,Arrays.asList("§f언제나 데미지가 동일합니다.",""), 51, inv);
		else
			removeFlagStack("§3[  공격 딜레이 있음      ]", 347,0,1,Arrays.asList("§f공격 딜레이에 따라", "§f데미지가 달라집니다.",""), 51, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+number), 53, inv);
		player.openInventory(inv);
	}

	public void itemForgingClick(InventoryClickEvent event)
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
			if(slot == 45)//이전 화면
				new EquipItemListGui().itemListGui(player, 0);
			else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29))||event.getSlot()==51)
			{
				int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			  	YamlLoader itemYaml = new YamlLoader();
				itemYaml.getConfig("Item/ItemList.yml");
				if(itemYaml.getString(itemnumber+"")==null)
				{
					player.sendMessage("§c[SYSTEM] : 다른 OP가 아이템을 삭제하여 반영되지 않았습니다!");
					SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
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
					itemForgingGui(player, itemnumber);
				}
				else if(slot==15)//타입 변경
				{
					YamlLoader customTypeYaml = new YamlLoader();
				  	customTypeYaml.getConfig("Item/CustomType.yml");
				  	String[] weaponCustomType = customTypeYaml.getKeys().toArray(new String[0]);
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
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
					itemForgingGui(player, itemnumber);
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
					itemForgingGui(player, itemnumber);
				}
				else if(slot==43)//최대 슬롯 설정
				{
					if(itemYaml.getInt(itemnumber+".MaxSocket") < 5)
						itemYaml.set(itemnumber+".MaxSocket",itemYaml.getInt(itemnumber+".MaxSocket")+1);
					else if(itemYaml.getInt(itemnumber+".MaxSocket") == 5)
							itemYaml.set(itemnumber+".MaxSocket", 0);
					itemYaml.saveConfig();
					itemForgingGui(player, itemnumber);
				}
				else if(slot==50)//직업 제한
				{
					if(event.isLeftClick())
						new RestrictJobSelectGui().restrictJobSelectGui(player, (short)0, itemnumber);
					else if(event.isRightClick())
					{
						SoundEffect.playSound(player, Sound.ITEM_SHIELD_BREAK, 0.8F, 1.0F);
						itemYaml.set(itemnumber+".JOB", "공용");
						itemYaml.saveConfig();
						itemForgingGui(player, itemnumber);
					}
				}
				else if(slot==51)//공격 딜레이
				{
					boolean removeDelay = itemYaml.getBoolean(itemnumber+".NoAttackDelay");
					itemYaml.set(itemnumber+".NoAttackDelay", !removeDelay);
					itemYaml.saveConfig();
					SoundEffect.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.8F, 1.0F);
					itemForgingGui(player, itemnumber);
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
}
