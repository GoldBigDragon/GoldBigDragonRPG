package admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;



public class Gamble_GUI extends Util_GUI
{
	public void gambleMainGui(Player player)
	{
		String uniqueCode = "§0§0§1§0§c§r";
		Inventory inv = Bukkit.createInventory(null, 45, uniqueCode + "§0도박 메인");

		Stack2("§f상품 관리", 54,0,1,Arrays.asList("§7상품 패키지를 만들거나","§7삭제/확인 합니다."), 10, inv);
		
		Stack2("§f슬롯 게임", 137,0,1,Arrays.asList("§7슬롯 게임에 대한 설정을 합니다."), 12, inv);
		Stack2("§f랜덤 블록", 137,0,1,Arrays.asList("§7랜덤 블록에 대한 설정을 합니다.", "§c§l[업데이트가 필요합니다!]"), 14, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7작업 관리자 메뉴로 돌아갑니다."), 36, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7작업 관리자 창을 닫습니다."), 44, inv);
		player.openInventory(inv);
	}
	
	public void gambleMainGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 44)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 36)//이전 목록
				new OPbox_GUI().opBoxGuiMain(player, (byte) 3);
			else if(slot == 10)//상품 관리
				gamblePresentGui(player, (short)0, (byte)0, (short)-1, null);
			else if(slot == 12)//슬롯 머신
				slotMachineMainGui(player,0);
		}
	}
	
	
	public void gamblePresentGui(Player player, short page, byte isChoose, short detailChoose, String deDetailChoose)
	{
		String uniqueCode = "§0§0§1§0§d§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0도박 상품 목록 : "+(page+1));

	  	YamlLoader presentList = new YamlLoader();
	  	presentList.getConfig("Item/GamblePresent.yml");
		
		String[] itemList = presentList.getConfigurationSection("").getKeys(false).toArray(new String[0]);
		
		int loc = 0;
		int presentAmount = 0;
		String packageName = null;
		String grade = null;
		for(int count = page*45; count < itemList.length;count++)
		{
			packageName = itemList[count];
			grade = presentList.getString(itemList[count]+".Grade");
			presentAmount = presentList.getConfigurationSection(itemList[count]+".Present").getKeys(false).size();
			if(count > itemList.length || loc >= 45) break;
			Stack2("§e§l"+packageName, 54,0,1,Arrays.asList("§9§l등급 : "+grade, "§9§l등록된 아이템 수 : §f"+ presentAmount+"개","","§e[좌 클릭시 패키지 확인]","§c[Shift + 우 클릭시 삭제]",""), loc, inv);
			loc++;
		}
		
		if(itemList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		if(isChoose==1)
			Stack2("§c§l꽝", 166,0,1,Arrays.asList("§7아무것도 주지 않습니다.","§0"+deDetailChoose), 49, inv);
		else
			Stack2("§f§l새 상품", 130,0,1,Arrays.asList("§7새로운 상품을 생성합니다.","§0"+deDetailChoose), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+isChoose), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+detailChoose), 53, inv);
		player.openInventory(inv);
	}
	
	public void gamblePresentGuiClick(InventoryClickEvent event)
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

			short page =  (short) (Short.parseShort(ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]))-1);
			byte isChoose = Byte.parseByte(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
			short DetailChoose = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			String DeDetailChoose = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(1));
			
			if(slot == 45)//이전 목록
			{
				if(isChoose==0)
					gambleMainGui(player);
				else if(isChoose==1)//슬롯 머신의 상품 선택
					slotMachineDetailGUI(player, DeDetailChoose);
			}
			else if(slot == 48)//이전 페이지
				gamblePresentGui(player, (short) (page-1),isChoose,DetailChoose,DeDetailChoose);
			else if(slot == 49)//새 패키지
			{
				if(isChoose==1)//슬롯 머신의 상품 선택
				{
				  	YamlLoader gambleYaml = new YamlLoader();
				  	gambleYaml.getConfig("ETC/SlotMachine.yml");
					gambleYaml.set(DeDetailChoose+"."+DetailChoose, "null");
					gambleYaml.saveConfig();
					slotMachineDetailGUI(player, DeDetailChoose);
					return;
				}
				UserData_Object u = new UserData_Object();
				player.closeInventory();
				u.setType(player, "Gamble");
				u.setString(player, (byte)0, "NP");
				player.sendMessage("§a[도박] : 상품 이름을 설정해 주세요!");
			}
			else if(slot == 50)//다음 페이지
				gamblePresentGui(player, (short) (page+1),isChoose,DetailChoose,DeDetailChoose);
			else
			{
				if(isChoose==0)
				{
					if(event.isLeftClick() == true&&event.isShiftClick()==false)
						gambleDetailViewPackageGui(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					else if(event.isRightClick()==true&&event.isShiftClick()==true)
					{
					  	YamlLoader presentYaml = new YamlLoader();
					  	presentYaml.getConfig("Item/GamblePresent.yml");
						presentYaml.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						presentYaml.saveConfig();
						gamblePresentGui(player, page,isChoose,DetailChoose,DeDetailChoose);
					}
				}
				else if(isChoose==1)//슬롯 머신의 상품 선택
				{
				  	YamlLoader gambleYaml = new YamlLoader();
				  	gambleYaml.getConfig("ETC/SlotMachine.yml");
					gambleYaml.set(DeDetailChoose+"."+DetailChoose, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					gambleYaml.saveConfig();
					slotMachineDetailGUI(player, DeDetailChoose);
				}
			}
		}
	}
	
	public void gambleDetailViewPackageGui(Player player, String packageName)
	{
		String uniqueCode = "§1§0§1§0§e§r";
		Inventory inv = Bukkit.createInventory(null, 36, uniqueCode + "§0도박 상품 정보");

	  	YamlLoader presentYaml = new YamlLoader();
	  	presentYaml.getConfig("Item/GamblePresent.yml");
		
		byte loc=0;
		for(int count = 0; count < 27;count++)
		{
			ItemStack item = presentYaml.getItemStack(packageName+".Present."+count);
			if(item!=null)
			{
				ItemStackStack(item, loc, inv);
				loc++;
			}
		}

		Stack("§f§l↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 28, inv);
		Stack("§f§l↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 29, inv);
		Stack("§f§l↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 30, inv);
		String Grade = presentYaml.getString(packageName+".Grade");
		Stack2("§3[    등급 변경    ]", 266,0,1,Arrays.asList("§f패키지의 등급을","§f변경합니다.","","§f[    현재 등급    ]","       "+Grade,""), 31, inv);
		Stack("§f§l↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 32, inv);
		Stack("§f§l↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 33, inv);
		Stack("§f§l↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 34, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+packageName), 27, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 35, inv);
		player.openInventory(inv);
	}

	public void gambleDetailViewPackageGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot >= 27)
			event.setCancelled(true);
		if(slot == 35)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 27)//이전 목록
				gamblePresentGui(player,(byte)0,(byte)0,(short)0,null);
			else if(slot == 31)//등급 변경
			{
			  	YamlLoader presentYaml = new YamlLoader();
			  	presentYaml.getConfig("Item/GamblePresent.yml");
				String packageName = ChatColor.stripColor(event.getInventory().getItem(27).getItemMeta().getLore().get(1));
				String grade = presentYaml.getString(packageName+".Grade");
				String maximumGrade = "§4§l[§6§l초§2§l월§1§l]";
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(grade.equals("§f[일반]"))
					presentYaml.set(packageName+".Grade","§a[상급]");
				else if(grade.equals("§a[상급]"))
					presentYaml.set(packageName+".Grade","§9[매직]");
				else if(grade.equals("§9[매직]"))
					presentYaml.set(packageName+".Grade","§e[레어]");
				else if(grade.equals("§e[레어]"))
					presentYaml.set(packageName+".Grade","§5[에픽]");
				else if(grade.equals("§5[에픽]"))
					presentYaml.set(packageName+".Grade","§6[전설]");
				else if(grade.equals("§6[전설]"))
					presentYaml.set(packageName+".Grade",maximumGrade);
				else if(grade.equals(maximumGrade))
					presentYaml.set(packageName+".Grade","§7[하급]");
				else if(grade.equals("§7[하급]"))
					presentYaml.set(packageName+".Grade","§f[일반]");
				else
					presentYaml.set(packageName+".Grade","§f[일반]");
				presentYaml.saveConfig();
				grade = presentYaml.getString(packageName+".Grade");
				ItemStack item[] = new ItemStack[27];
				int itemcount=0;
				for(int count=0; count < 27; count++)
				{
					if(event.getInventory().getItem(count)!=null)
					{
						item[itemcount] = event.getInventory().getItem(count);
						itemcount++;
					}
				}
				presentYaml.removeKey(packageName+".Present");
				presentYaml.saveConfig();
				for(int count=0; count<itemcount;count++)
					presentYaml.set(packageName+".Present."+count,item[count]);
				presentYaml.saveConfig();
				gambleDetailViewPackageGui(player, packageName);
			}
		}
	}
	
	public void gambleDetailViewPackageGuiClose(InventoryCloseEvent event)
	{
		String packageName = ChatColor.stripColor(event.getInventory().getItem(27).getItemMeta().getLore().get(1));
	  	YamlLoader presentYaml = new YamlLoader();
	  	presentYaml.getConfig("Item/GamblePresent.yml");
		ItemStack item[] = new ItemStack[27];
		byte itemcount=0;
		for(int count=0; count < 27; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				item[itemcount] = event.getInventory().getItem(count);
				itemcount++;
			}
		}
		presentYaml.removeKey(packageName+".Present");
		presentYaml.createSection(packageName+".Present");
		presentYaml.saveConfig();
		for(int count=0; count<itemcount;count++)
			presentYaml.set(packageName+".Present."+count,item[count]);
		presentYaml.saveConfig();
	}
	
	
	public void slotMachineMainGui(Player player, int page)
	{
		String uniqueCode = "§0§0§1§0§f§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0도박 기계 목록 : "+(page+1));

	  	YamlLoader slotmachineYaml = new YamlLoader();
	  	slotmachineYaml.getConfig("ETC/SlotMachine.yml");
		
		String[] slotMachineList = slotmachineYaml.getConfigurationSection("").getKeys(false).toArray(new String[0]);
		//월드_좌표
		byte loc=0;
		String location = null;
		String world = null;
		for(int count = page*45; count < slotMachineList.length;count++)
		{
			location = slotMachineList[count].split("_")[1];
			world = slotMachineList[count].split("_")[0];
			if(count > slotMachineList.length || loc >= 45) break;

			Stack2("§0§l"+slotMachineList[count].toString(), 137,0,1,Arrays.asList("§9§l월드 : §f§l"+world, "§9§l좌표 : §f§l"+location,"","§e[좌 클릭시 기기 설정]","§c[Shift + 우 클릭시 삭제]",""), loc, inv);
			
			loc++;
		}
		
		if(slotMachineList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 기계", 130,0,1,Arrays.asList("§7새로운 기계를 배치합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}	

	public void slotMachineMainGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전목록
				gambleMainGui(player);
			else if(slot == 48)//이전 페이지
				slotMachineMainGui(player, page-1);
			else if(slot == 49)//새 슬롯 머신
			{
				UserData_Object u = new UserData_Object();
				player.closeInventory();
				u.setType(player, "Gamble");
				u.setString(player, (byte)0, "NSM");
				player.sendMessage("§a[도박] : 슬롯 머신을 등록 할 블록을 우클릭 해 주세요!");
			}
			else if(slot == 50)//다음 페이지
				slotMachineMainGui(player, page+1);
			else
			{
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
					slotMachineDetailGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isRightClick()==true&&event.isShiftClick()==true)
				{
				  	YamlLoader presentYaml = new YamlLoader();
				  	presentYaml.getConfig("ETC/SlotMachine.yml");
					presentYaml.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					presentYaml.saveConfig();
					slotMachineMainGui(player, page);
				}
			}
		}
	}
	
	
	public void slotMachineDetailGUI(Player player, String machineNumber)
	{
		String uniqueCode = "§0§0§1§1§0§r";
		Inventory inv = Bukkit.createInventory(null, 36, uniqueCode + "§0도박 기계 설정 ");

	  	YamlLoader gambleYaml = new YamlLoader();
	  	gambleYaml.getConfig("ETC/SlotMachine.yml");
		
		String setter = gambleYaml.getString(machineNumber+".0");
		if(setter.equals("null"))setter="없음";
		Stack2("§7§l[모두 다를 경우]", 351,15,1,Arrays.asList("","§f■ §e■ §3◆","§7슬롯 머신을 돌린 결과가","§7모두 다를 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 0, inv);
		setter = gambleYaml.getString(machineNumber+".1");
		if(setter.equals("null"))setter="없음";
		Stack2("§8§l[트리플 코어]", 263,0,3,Arrays.asList("","§8● ● ●","§7슬롯 머신을 돌린 결과가","§7모두 석탄일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 1, inv);
		setter = gambleYaml.getString(machineNumber+".2");
		if(setter.equals("null"))setter="없음";
		Stack2("§f§l[트리플 아이언]", 265,0,3,Arrays.asList("","§f■ ■ ■","§7슬롯 머신을 돌린 결과가","§7모두 철괴일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 2, inv);
		setter = gambleYaml.getString(machineNumber+".3");
		if(setter.equals("null"))setter="없음";
		Stack2("§e§l[트리플 골드]", 266,0,3,Arrays.asList("","§e■ ■ ■","§7슬롯 머신을 돌린 결과가","§7모두 금괴일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 3, inv);
		setter = gambleYaml.getString(machineNumber+".4");
		if(setter.equals("null"))setter="없음";
		Stack2("§9§l[트리플 다이아몬드]", 264,0,3,Arrays.asList("","§3◆ ◆ ◆","§7슬롯 머신을 돌린 결과가","§7모두 다이아일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 4, inv);
		setter = gambleYaml.getString(machineNumber+".5");
		if(setter.equals("null"))setter="없음";
		Stack2("§a§l[트리플 에메랄드]", 388,0,3,Arrays.asList("","§a◈ ◈ ◈","§7슬롯 머신을 돌린 결과가","§7모두 에메랄드일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 5, inv);
		setter = gambleYaml.getString(machineNumber+".6");
		if(setter.equals("null"))setter="없음";
		Stack2("§9§l[트리플 네더 스타]", 399,0,3,Arrays.asList("","§9★ ★ ★","§7슬롯 머신을 돌린 결과가","§7모두 네더 별일 경우입니다.","§7당첨시 상품과 당첨자를","§7서버 전체에 알려 줍니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 6, inv);
		setter = gambleYaml.getString(machineNumber+".9");
		if(setter.equals("null"))setter="없음";
		Stack2("§9§l[싱글 네더 스타]", 399,0,1,Arrays.asList("","§9★ §8ⅹ ⅹ","§7슬롯 머신을 돌린 결과","§7네더 별이 1개 나온 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 9, inv);
		setter = gambleYaml.getString(machineNumber+".10");
		if(setter.equals("null"))setter="없음";
		Stack2("§8§l[더블 코어]", 263,0,2,Arrays.asList("","§8● ● §8ⅹ","§7슬롯 머신을 돌린 결과","§7석탄이 2개일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 10, inv);
		setter = gambleYaml.getString(machineNumber+".11");
		if(setter.equals("null"))setter="없음";
		Stack2("§f§l[더블 아이언]", 265,0,2,Arrays.asList("","§f■ ■ §8ⅹ","§7슬롯 머신을 돌린 결과","§7철괴가 2개일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 11, inv);
		setter = gambleYaml.getString(machineNumber+".12");
		if(setter.equals("null"))setter="없음";
		Stack2("§e§l[더블 골드]", 266,0,2,Arrays.asList("","§e■ ■ §8ⅹ","§7슬롯 머신을 돌린 결과","§7금괴가 2개일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 12, inv);
		setter = gambleYaml.getString(machineNumber+".13");
		if(setter.equals("null"))setter="없음";
		Stack2("§9§l[더블 다이아몬드]", 264,0,2,Arrays.asList("","§3◆ ◆ §8ⅹ","§7슬롯 머신을 돌린 결과","§7모두 다이아가 2개일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 13, inv);
		setter = gambleYaml.getString(machineNumber+".14");
		if(setter.equals("null"))setter="없음";
		Stack2("§a§l[더블 에메랄드]", 388,0,2,Arrays.asList("","§a◈ ◈ §8ⅹ","§7슬롯 머신을 돌린 결과","§7에메랄드가 2개일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 14, inv);
		setter = gambleYaml.getString(machineNumber+".15");
		if(setter.equals("null"))setter="없음";
		Stack2("§9§l[더블 네더 스타]", 399,0,2,Arrays.asList("","§9★ ★ §8ⅹ","§7슬롯 머신을 돌린 결과","§7네더 별이 2개일 경우입니다.","","§e[좌 클릭시 상품 설정]","","§7[현재 상품]","§8"+setter), 15, inv);

		Stack2("§2§l[도박 코인 설정]", 341,0,1,Arrays.asList("","§7슬롯 머신 1회 이용을 위해","§7필요한 코인을 설정합니다.","","§e[좌 클릭시 코인 설정]",""), 8, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+machineNumber), 27, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 35, inv);
		player.openInventory(inv);
	}
	
	public void slotMachineDetailGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		String machineNumber =  ChatColor.stripColor(event.getInventory().getItem(27).getItemMeta().getLore().get(1));
		int slot = event.getSlot();

		if(slot == 35)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot <= 6 || (slot >= 9 && slot <= 15))//각 확률별 보상 설정
				gamblePresentGui(player, (byte)0, (byte)1, (short)event.getSlot(),machineNumber);
			else if(slot == 8)//코인 설정
				slotMachineCoinGui(player, machineNumber);
			else if(slot == 27)//이전 목록
				slotMachineMainGui(player,0);
		}
	}

	
	public void slotMachineCoinGui(Player player, String machineNumber)
	{
		String uniqueCode = "§1§0§1§1§1§r";
		Inventory inv = Bukkit.createInventory(null, 9, uniqueCode + "§0도박 기계 코인");

	  	YamlLoader gambleYaml = new YamlLoader();
	  	gambleYaml.getConfig("ETC/SlotMachine.yml");
		
		if(gambleYaml.contains(machineNumber+".8"))
			ItemStackStack(gambleYaml.getItemStack(machineNumber+".8"), 4, inv);
			
		Stack("§d[코인 넣기]→→→", 166,0,1,null, 1, inv);
		Stack("§d[코인 넣기]→→→", 166,0,1,null, 2, inv);
		Stack("§d[코인 넣기]→→→", 166,0,1,null, 3, inv);
		Stack("§d←←←[코인 넣기]", 166,0,1,null, 5, inv);
		Stack("§d←←←[코인 넣기]", 166,0,1,null, 6, inv);
		Stack("§d←←←[코인 넣기]", 166,0,1,null, 7, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+machineNumber), 0, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 8, inv);
		player.openInventory(inv);
	}
	
	public void slotMachineCoinGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		String machineNumber = ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1));

		if(!event.getClickedInventory().getTitle().equals("container.inventory"))
		{
			if(slot!=4)
				event.setCancelled(true);
			if(slot == 0)
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				slotMachineDetailGUI(player, machineNumber);
			}
			else if(slot == 8)
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
		}
	}

	public void slotMachineCoinGuiClose(InventoryCloseEvent event)
	{
		String machineNumber = ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1));

	  	YamlLoader gambleYaml = new YamlLoader();
	  	gambleYaml.getConfig("ETC/SlotMachine.yml");
		
		if(event.getInventory().getItem(4)!=null)
			gambleYaml.set(machineNumber+".8", event.getInventory().getItem(4));
		else
			gambleYaml.set(machineNumber+".8", null);
		gambleYaml.saveConfig();
	}

	
	public void slotMachinePlayGui(Player player, String machineNumber)
	{
		String uniqueCode = "§0§0§1§1§2§r";
		Inventory inv = Bukkit.createInventory(null, 27, uniqueCode + "§0슬롯 머신");

	  	YamlLoader gambleYaml = new YamlLoader();
	  	gambleYaml.getConfig("ETC/SlotMachine.yml");
		
		if(gambleYaml.contains(machineNumber+".8"))
			ItemStackStack(gambleYaml.getItemStack(machineNumber+".8"), 16, inv);
		else
			Stack2("§c§l[기기 수리 중]", 166,0,1,Arrays.asList("","§7슬롯 머신 이용에","§7불편을 끼쳐드려","§7대단히 죄송합니다."), 16, inv);
		Stack2("§e§l[운명의 여신이시여!]", 76,0,1,Arrays.asList("","§7슬롯 머신에 코인을 넣고","§7힘차게 돌립니다!","","§a[우측 아이템이 코인입니다.]"), 15, inv);

		for(int count=0; count<5; count++)
			Stack2("§e§l ", 160,4,1,Arrays.asList(""), count, inv);
		for(int count=5; count<9; count++)
			Stack2("§e§l ", 160,11,1,Arrays.asList(""), count, inv);

		Stack2("§e§l ", 160,4,1,Arrays.asList(""), 9, inv);
		Stack2("§e§l ", 160,4,1,Arrays.asList(""), 13, inv);
		Stack2("§e§l ", 160,11,1,Arrays.asList(""), 14, inv);
		Stack2("§e§l ", 160,11,1,Arrays.asList("§0"+machineNumber), 17, inv);
		
		for(int count=18; count<23; count++)
			Stack2("§e§l ", 160,4,1,Arrays.asList(""), count, inv);
		for(int count=23; count<27; count++)
			Stack2("§e§l ", 160,11,1,Arrays.asList(""), count, inv);
		
		for(int count=1;count<4;count++)
		{
			byte randomnum = (byte) new util.Util_Number().RandomNum(0, 5);
			short itemID=263;
			if(randomnum == 0)
				itemID = 263;
			else if(randomnum == 1)
				itemID = 265;
			else if(randomnum == 2)
				itemID = 266;
			else if(randomnum == 3)
				itemID = 264;
			else if(randomnum == 4)
				itemID = 388;
			else if(randomnum == 5)
				itemID = 399;
			Stack2("§e§l"+count+" 번째 슬롯", itemID,0,1,Arrays.asList(""), count+9, inv);
			
		}
		player.openInventory(inv);
	}

	public void slotMachinePlayGuiClick(InventoryClickEvent event)
	{
		if(event.getSlot()==15)
		{
			ItemStack coin = event.getInventory().getItem(16);
			Player player = (Player) event.getWhoClicked();
			if(event.getCurrentItem().getTypeId()==69)
				return;
			ItemStack coinItem = null;
			for(int countta=0;countta < player.getInventory().getSize(); countta++)
			{
				coinItem = player.getInventory().getItem(countta);
				if(coinItem!=null&&coinItem.isSimilar(coin)&&coinItem.getAmount()>=coin.getAmount())
				{
					if(coinItem.getAmount()==coin.getAmount())
						player.getInventory().setItem(countta, null);
					else
					{
						coinItem.setAmount(coinItem.getAmount()-coin.getAmount());
						player.getInventory().setItem(countta, coinItem);
					}
					player.updateInventory();
					if(event.getInventory().getItem(16).getTypeId()==166 && event.getInventory().getItem(16).hasItemMeta()==true)
						if(event.getInventory().getItem(16).getItemMeta().getDisplayName().equals("§c§l[기기 수리 중]"))
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage("§c[슬롯 머신] : 현재 기기는 수리 중입니다! 관리자에게 문의하세요!");
							return;
						}
					
					if(servertick.ServerTick_Main.PlayerTaskList.containsKey(player.getName())==true)
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[슬롯 머신] : 이미 대기중인 작업이 있습니다! 볼일을 다 보고 오세요!");
						return;
					}
					
					SoundEffect.SP(player, Sound.BLOCK_CHEST_CLOSE, 1.0F, 0.5F);
					SoundEffect.SP(player, Sound.BLOCK_CHEST_OPEN, 1.0F, 0.5F);
					
					ItemStack icon = new MaterialData(69, (byte) 0).toItemStack(1);
					ItemMeta iconMeta = icon.getItemMeta();
					iconMeta.setDisplayName("§c§l[지금은 돌릴 수 없다!]");
					iconMeta.setLore(Arrays.asList("","§7결과를 기다리세요!"));
					icon.setItemMeta(iconMeta);
					
					event.getInventory().setItem(15, icon);
					short itemId[] = new short[3];
					for(int count=0;count<3;count++)
					{
						int randomnum = (byte) new util.Util_Number().RandomNum(0, 5);
						itemId[count]=263;
						if(randomnum == 0)
							itemId[count] = 263;
						else if(randomnum == 1)
							itemId[count] = 265;
						else if(randomnum == 2)
							itemId[count] = 266;
						else if(randomnum == 3)
							itemId[count] = 264;
						else if(randomnum == 4)
							itemId[count] = 388;
						else if(randomnum == 5)
							itemId[count] = 399;
					}
					Long utc = servertick.ServerTick_Main.nowUTC+5;
					for(;;)
					{
						if(servertick.ServerTick_Main.Schedule.containsKey(utc))
							utc=utc+1;
						else
							break;
					}
					
					servertick.ServerTick_Object tickObject = new servertick.ServerTick_Object(utc, "G_SM");
					tickObject.setString((byte)0, player.getName());
					tickObject.setString((byte)1, ChatColor.stripColor(event.getInventory().getItem(17).getItemMeta().getLore().get(0)));

					tickObject.setInt((byte)0, itemId[0]);
					tickObject.setInt((byte)1, itemId[1]);
					tickObject.setInt((byte)2, itemId[2]);
					tickObject.setMaxCount(20);
					servertick.ServerTick_Main.Schedule.put(utc, tickObject);
					servertick.ServerTick_Main.PlayerTaskList.put(player.getName(), "G_SM");
					return;
				}
			}
			SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
			player.sendMessage("§c[슬롯 머신] : 코인이 부족합니다!");
			return;
		}
	}

	public void slotMachineRollingGui(String player, short[] itemID, boolean fin, String machineNumber)
	{
		String uniqueCode = "§1§0§1§1§3§r";
		if(Bukkit.getServer().getPlayer(player)!=null && Bukkit.getServer().getPlayer(player).isOnline())
		{
			Inventory inv = Bukkit.createInventory(null, 27, uniqueCode + "§0슬롯 머신");
			if(fin)
			{
			  	YamlLoader gambleYaml = new YamlLoader();
			  	gambleYaml.getConfig("ETC/SlotMachine.yml");
				
				if(gambleYaml.contains(machineNumber+".8"))
					ItemStackStack(gambleYaml.getItemStack(machineNumber+".8"), 16, inv);
				else
					Stack2("§c§l[기기 수리 중]", 166,0,1,Arrays.asList("","§7슬롯 머신 이용에","§7불편을 끼쳐드려","§7대단히 죄송합니다."), 16, inv);
				Stack2("§e§l[운명의 여신이시여!]", 76,0,1,Arrays.asList("","§7슬롯 머신에 코인을 넣고","§7힘차게 돌립니다!","","§a[우측 아이템이 코인입니다.]"), 15, inv);
			}
			else
			{
				Stack2("§c§l[지금은 돈을 넣을 수 없다!]", 166,0,1,Arrays.asList("","§7겸허한 마음으로 기다리자!"), 16, inv);
				Stack2("§c§l[지금은 돌릴 수 없다!]", 69,0,1,Arrays.asList("","§7결과를 기다리세요!"), 15, inv);
			}
			for(int count=0; count<5; count++)
				Stack2("§e§l ", 160,4,1,Arrays.asList(""), count, inv);
			for(int count=5; count<9; count++)
				Stack2("§e§l ", 160,11,1,Arrays.asList(""), count, inv);

			Stack2("§e§l ", 160,4,1,Arrays.asList(""), 9, inv);
			Stack2("§e§l ", 160,4,1,Arrays.asList(""), 13, inv);
			Stack2("§e§l ", 160,11,1,Arrays.asList(""), 14, inv);
			Stack2("§e§l ", 160,11,1,Arrays.asList("§0"+machineNumber), 17, inv);
			
			for(int count=18; count<23; count++)
				Stack2("§e§l ", 160,4,1,Arrays.asList(""), count, inv);
			for(int count=23; count<27; count++)
				Stack2("§e§l ", 160,11,1,Arrays.asList(""), count, inv);
			
			for(int count=0;count<3;count++)
				Stack2("§e§l"+(count+1)+" 번째 슬롯", itemID[count],0,1,Arrays.asList(""), count+10, inv);
			Bukkit.getServer().getPlayer(player).openInventory(inv);
			
			SoundEffect.SP(Bukkit.getServer().getPlayer(player), Sound.BLOCK_STONE_STEP, 1.0F, 1.0F);
		}
	}
}
