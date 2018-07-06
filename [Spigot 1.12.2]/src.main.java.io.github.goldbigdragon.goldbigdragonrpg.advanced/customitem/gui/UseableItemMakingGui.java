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
import main.MainServerOption;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class UseableItemMakingGui extends GuiUtil{

	private String uniqueCode = "§0§0§3§0§5§r";
	
	public void useableItemMakingGui(Player player, int number)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/Consume.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0소모성 아이템 상세 설정");
		String itemName = itemYaml.getString(number+".DisplayName");
		short itemId = (short) itemYaml.getInt(number+".ID");
		byte itemData = (byte) itemYaml.getInt(number+".Data");

		String type = "";
		String grade = "";
		CustomItemAPI ciapi = new CustomItemAPI();
		
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
		
		removeFlagStack(itemName, itemId,itemData,1,Arrays.asList(ciapi.useableLoreCreater(number)), 19, inv);
		
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
		case "[공구]":
			stack("§3[       내구도       ]", 145,2,1,Arrays.asList("§f장비류의 최대 내구도를", "§f증가 시켜 줍니다.","","§c[일반 아이템 불가능]",""), 24, inv);
			stack("§3[       숙련도       ]", 416,0,1,Arrays.asList("§f장비류의 숙련도를","§f증가 시켜 줍니다.",""), 25, inv);
			break;
		}
		stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+type), 45, inv);
		stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+number), 53, inv);
		player.openInventory(inv);
	}

	public void useableItemMakingClick(InventoryClickEvent event)
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
				new SelectSkillGui().selectSkillGui(player, (short) 0, itemnumber);
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
			useableItemMakingGui(player, itemnumber);
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
			useableItemMakingGui(player, itemnumber);
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
			useableItemMakingGui(player, itemnumber);
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
			useableItemMakingGui(player, itemnumber);
		}
		else if(iconName.equals("이전 목록"))
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			new UseableItemListGui().useableItemListGui(player, 0);
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
			else if(iconName.equals("[       숙련도       ]"))
			{
				player.sendMessage("§3[아이템] : 아이템의 숙련도를 입력해 주세요!");
				player.sendMessage("§3(0 ~ 100)");
				u.setString(player, (byte)1, "Proficiency");
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
}
