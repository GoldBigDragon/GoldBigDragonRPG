package GoldBigDragon_RPG.GUI;

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

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class GambleGUI extends GUIutil
{
	public void GambleMainGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "도박 메인");

		Stack2(ChatColor.WHITE + "상품 관리", 54,0,1,Arrays.asList(ChatColor.GRAY + "상품 패키지를 만들거나",ChatColor.GRAY + "삭제/확인 합니다."), 10, inv);
		
		Stack2(ChatColor.WHITE + "슬롯 게임", 137,0,1,Arrays.asList(ChatColor.GRAY + "슬롯 게임에 대한 설정을 합니다."), 12, inv);
		Stack2(ChatColor.WHITE + "랜덤 블록", 137,0,1,Arrays.asList(ChatColor.GRAY + "랜덤 블록에 대한 설정을 합니다.", ChatColor.RED+""+ChatColor.BOLD+"[업데이트가 필요합니다!]"), 14, inv);
		
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 메뉴로 돌아갑니다."), 36, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 44, inv);
		player.openInventory(inv);
	}
	
	public void GambleMainGUI_Click(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		switch (event.getSlot())
		{
		case 36://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI OPGUI = new OPBoxGUI();
			OPGUI.OPBoxGUI_Main(player, (byte) 3);
			return;
		case 44://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 10 ://상품 관리
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			GamblePresentGUI(player, (short)0, (byte)0, (short)-1, null);
			break;
		case 12 ://슬롯 머신
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SlotMachine_MainGUI(player,0);
			break;
		}
	}
	
	
	public void GamblePresentGUI(Player player, short page, byte isChoose, short DetailChoose, String DeDetailChoose)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "도박 상품 목록 : "+(page+1));

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PresentList  = YC.getNewConfig("Item/GamblePresent.yml");
		
		Object[] a = PresentList.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String PackageName = a[count].toString();
			String Grade = PresentList.getString(a[count].toString()+".Grade");
			byte PresentAmount = (byte) PresentList.getConfigurationSection(a[count].toString()+".Present").getKeys(false).size();
			
			if(count > a.length || loc >= 45) break;

			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+PackageName, 54,0,1,Arrays.asList(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"등급 : "+Grade, ChatColor.DARK_AQUA+""+ChatColor.BOLD+"등록된 아이템 수 : " +ChatColor.WHITE+ PresentAmount+"개","",ChatColor.YELLOW+"[좌 클릭시 패키지 확인]",ChatColor.RED+"[Shift + 우 클릭시 삭제]",""), loc, inv);
			
			loc++;
		}
		
		if(a.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		if(isChoose==1)
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "꽝", 166,0,1,Arrays.asList(ChatColor.GRAY + "아무것도 주지 않습니다.",ChatColor.BLACK+DeDetailChoose), 49, inv);
		else
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 상품", 130,0,1,Arrays.asList(ChatColor.GRAY + "새로운 상품을 생성합니다.",ChatColor.BLACK+DeDetailChoose), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+""+isChoose), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+DetailChoose), 53, inv);
		player.openInventory(inv);
	}
	
	public void GamblePresentGUI_Click(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		short page =  (short) (Short.parseShort(ChatColor.stripColor(event.getInventory().getTitle().split(" : ")[1]))-1);
		byte isChoose = Byte.parseByte(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		short DetailChoose = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String DeDetailChoose = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(isChoose==0)
				GambleMainGUI(player);
			else if(isChoose==1)//슬롯 머신의 상품 선택
				SlotMachine_DetailGUI(player, DeDetailChoose);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			GamblePresentGUI(player, (short) (page-1),isChoose,DetailChoose,DeDetailChoose);
			return;
		case 49://새 패키지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(isChoose==1)//슬롯 머신의 상품 선택
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager GambleConfig =YC.getNewConfig("ETC/SlotMachine.yml");
				GambleConfig.set(DeDetailChoose+"."+DetailChoose, "null");
				GambleConfig.saveConfig();
				SlotMachine_DetailGUI(player, DeDetailChoose);
				return;
			}
			Object_UserData u = new Object_UserData();
			player.closeInventory();
			u.setType(player, "Gamble");
			u.setString(player, (byte)0, "NP");
			player.sendMessage(ChatColor.GREEN + "[도박] : 상품 이름을 설정해 주세요!");
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			GamblePresentGUI(player, (short) (page+1),isChoose,DetailChoose,DeDetailChoose);
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(isChoose==0)
			{
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
					GambleDetailViewPackageGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isRightClick()==true&&event.isShiftClick()==true)
				{
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager PresentList = YC.getNewConfig("Item/GamblePresent.yml");
					PresentList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					PresentList.saveConfig();
					GamblePresentGUI(player, page,isChoose,DetailChoose,DeDetailChoose);
				}
			}
			else if(isChoose==1)//슬롯 머신의 상품 선택
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager GambleConfig =YC.getNewConfig("ETC/SlotMachine.yml");
				GambleConfig.set(DeDetailChoose+"."+DetailChoose, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				GambleConfig.saveConfig();
				SlotMachine_DetailGUI(player, DeDetailChoose);
			}
			return;
		}
	}
	
	
	public void GambleDetailViewPackageGUI(Player player, String Package)
	{
		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.BLACK + "도박 상품 정보");

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PresentList  = YC.getNewConfig("Item/GamblePresent.yml");
		
		byte loc=0;
		for(byte count = 0; count < 27;count++)
		{
			ItemStack item = PresentList.getItemStack(Package+".Present."+count);
			if(item!=null)
			{
				ItemStackStack(item, loc, inv);
				loc++;
			}
		}

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 28, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 29, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 30, inv);
		String Grade = PresentList.getString(Package+".Grade");
		Stack2(ChatColor.DARK_AQUA + "[    등급 변경    ]", 266,0,1,Arrays.asList(ChatColor.WHITE+"패키지의 등급을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 등급    ]","       "+Grade,""), 31, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 32, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 33, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "↑↑↑ [상품 넣기] ↑↑↑", 160,8,1,null, 34, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+Package), 27, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 35, inv);
		player.openInventory(inv);
	}

	public void GambleDetailViewPackageGUI_Click(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		if(event.getSlot()>=27)
			event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		String Package = ChatColor.stripColor(event.getInventory().getItem(27).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 27://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			GamblePresentGUI(player,(byte)0,(byte)0,(short)0,null);
			return;
		case 35://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 31://등급 변경
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager PresentList  = YC.getNewConfig("Item/GamblePresent.yml");
			String Grade = PresentList.getString(Package+".Grade");
			String MaximumGrade = ChatColor.DARK_RED+""+ChatColor.BOLD+"["+ChatColor.GOLD+""+ChatColor.BOLD+"초"+ChatColor.DARK_GREEN+""+ChatColor.BOLD+"월"+ChatColor.DARK_BLUE+""+ChatColor.BOLD+"]";
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(Grade.compareTo(ChatColor.WHITE+"[일반]")==0)
				PresentList.set(Package+".Grade",ChatColor.GREEN+"[상급]");
			else if(Grade.compareTo(ChatColor.GREEN+"[상급]")==0)
				PresentList.set(Package+".Grade",ChatColor.BLUE+"[매직]");
			else if(Grade.compareTo(ChatColor.BLUE+"[매직]")==0)
				PresentList.set(Package+".Grade",ChatColor.YELLOW+"[레어]");
			else if(Grade.compareTo(ChatColor.YELLOW+"[레어]")==0)
				PresentList.set(Package+".Grade",ChatColor.DARK_PURPLE+"[에픽]");
			else if(Grade.compareTo(ChatColor.DARK_PURPLE+"[에픽]")==0)
				PresentList.set(Package+".Grade",ChatColor.GOLD+"[전설]");
			else if(Grade.compareTo(ChatColor.GOLD+"[전설]")==0)
				PresentList.set(Package+".Grade",MaximumGrade);
			else if(Grade.compareTo(MaximumGrade)==0)
				PresentList.set(Package+".Grade",ChatColor.GRAY+"[하급]");
			else if(Grade.compareTo(ChatColor.GRAY+"[하급]")==0)
				PresentList.set(Package+".Grade",ChatColor.WHITE+"[일반]");
			else
				PresentList.set(Package+".Grade",ChatColor.WHITE+"[일반]");
			PresentList.saveConfig();
			Grade = PresentList.getString(Package+".Grade");
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
			PresentList.removeKey(Package+".Present");
			PresentList.saveConfig();
			for(byte count=0; count<itemcount;count++)
				PresentList.set(Package+".Present."+count,item[count]);
			PresentList.saveConfig();
			
			GambleDetailViewPackageGUI(player, Package);
			return;
		}
	}
	
	public void GambleDetailViewPackageGUI_Close(InventoryCloseEvent event)
	{
		String Package = ChatColor.stripColor(event.getInventory().getItem(27).getItemMeta().getLore().get(1));
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager PresentList  = YC.getNewConfig("Item/GamblePresent.yml");
		ItemStack item[] = new ItemStack[27];
		byte itemcount=0;
		for(byte count=0; count < 27; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				item[itemcount] = event.getInventory().getItem(count);
				itemcount++;
			}
		}
		PresentList.removeKey(Package+".Present");
		PresentList.set(Package+".Present.1",null);
		PresentList.saveConfig();
		for(byte count=0; count<itemcount;count++)
			PresentList.set(Package+".Present."+count,item[count]);
		PresentList.saveConfig();
	}
	
	
	public void SlotMachine_MainGUI(Player player, int page)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "도박 기계 목록 : "+(page+1));

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SlotMachineList  = YC.getNewConfig("ETC/SlotMachine.yml");
		
		Object[] a = SlotMachineList.getConfigurationSection("").getKeys(false).toArray();
		//월드_좌표
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String location = a[count].toString().split("_")[1];
			String world = a[count].toString().split("_")[0];
			
			if(count > a.length || loc >= 45) break;

			Stack2(ChatColor.BLACK+""+ChatColor.BOLD+a[count].toString(), 137,0,1,Arrays.asList(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"월드 : "+ChatColor.WHITE+""+ChatColor.BOLD+world, ChatColor.DARK_AQUA+""+ChatColor.BOLD+"좌표 : " +ChatColor.WHITE+ ""+ChatColor.BOLD+location,"",ChatColor.YELLOW+"[좌 클릭시 기기 설정]",ChatColor.RED+"[Shift + 우 클릭시 삭제]",""), loc, inv);
			
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 기계", 130,0,1,Arrays.asList(ChatColor.GRAY + "새로운 기계를 배치합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}	

	public void SlotMachine_MainGUI_Click(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);

		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			GambleMainGUI(player);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SlotMachine_MainGUI(player, page-1);
			return;
		case 49://새 슬롯 머신
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);

			Object_UserData u = new Object_UserData();
			player.closeInventory();
			u.setType(player, "Gamble");
			u.setString(player, (byte)0, "NSM");
			player.sendMessage(ChatColor.GREEN + "[도박] : 슬롯 머신을 등록 할 블록을 우클릭 해 주세요!");
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SlotMachine_MainGUI(player, page+1);
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(event.isLeftClick() == true&&event.isShiftClick()==false)
				SlotMachine_DetailGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			else if(event.isRightClick()==true&&event.isShiftClick()==true)
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager PresentList = YC.getNewConfig("ETC/SlotMachine.yml");
				PresentList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				PresentList.saveConfig();
				SlotMachine_MainGUI(player, page);
			}
			return;
		}
	}
	
	
	public void SlotMachine_DetailGUI(Player player, String MachineNumber)
	{
		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.BLACK + "도박 기계 설정 ");

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager GambleConfig =YC.getNewConfig("ETC/SlotMachine.yml");
		
		String Setter = GambleConfig.getString(MachineNumber+".0");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.GRAY+""+ChatColor.BOLD+"[모두 다를 경우]", 351,15,1,Arrays.asList("",ChatColor.WHITE+"■ "+ChatColor.YELLOW+"■ "+ChatColor.DARK_AQUA+"◆",ChatColor.GRAY+"슬롯 머신을 돌린 결과가",ChatColor.GRAY+"모두 다를 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 0, inv);
		Setter = GambleConfig.getString(MachineNumber+".1");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.DARK_GRAY+""+ChatColor.BOLD+"[트리플 코어]", 263,0,3,Arrays.asList("",ChatColor.DARK_GRAY+"● ● ●",ChatColor.GRAY+"슬롯 머신을 돌린 결과가",ChatColor.GRAY+"모두 석탄일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 1, inv);
		Setter = GambleConfig.getString(MachineNumber+".2");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[트리플 아이언]", 265,0,3,Arrays.asList("",ChatColor.WHITE+"■ ■ ■",ChatColor.GRAY+"슬롯 머신을 돌린 결과가",ChatColor.GRAY+"모두 철괴일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 2, inv);
		Setter = GambleConfig.getString(MachineNumber+".3");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+"[트리플 골드]", 266,0,3,Arrays.asList("",ChatColor.YELLOW+"■ ■ ■",ChatColor.GRAY+"슬롯 머신을 돌린 결과가",ChatColor.GRAY+"모두 금괴일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 3, inv);
		Setter = GambleConfig.getString(MachineNumber+".4");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"[트리플 다이아몬드]", 264,0,3,Arrays.asList("",ChatColor.DARK_AQUA+"◆ ◆ ◆",ChatColor.GRAY+"슬롯 머신을 돌린 결과가",ChatColor.GRAY+"모두 다이아일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 4, inv);
		Setter = GambleConfig.getString(MachineNumber+".5");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.GREEN+""+ChatColor.BOLD+"[트리플 에메랄드]", 388,0,3,Arrays.asList("",ChatColor.GREEN+"◈ ◈ ◈",ChatColor.GRAY+"슬롯 머신을 돌린 결과가",ChatColor.GRAY+"모두 에메랄드일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 5, inv);
		Setter = GambleConfig.getString(MachineNumber+".6");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD+"[트리플 네더 스타]", 399,0,3,Arrays.asList("",ChatColor.BLUE+"★ ★ ★",ChatColor.GRAY+"슬롯 머신을 돌린 결과가",ChatColor.GRAY+"모두 네더 별일 경우입니다.",ChatColor.GRAY+"당첨시 상품과 당첨자를",ChatColor.GRAY+"서버 전체에 알려 줍니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 6, inv);
		Setter = GambleConfig.getString(MachineNumber+".9");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD+"[싱글 네더 스타]", 399,0,1,Arrays.asList("",ChatColor.BLUE+"★ "+ChatColor.DARK_GRAY+"ⅹ ⅹ",ChatColor.GRAY+"슬롯 머신을 돌린 결과",ChatColor.GRAY+"네더 별이 1개 나온 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 9, inv);
		Setter = GambleConfig.getString(MachineNumber+".10");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.DARK_GRAY+""+ChatColor.BOLD+"[더블 코어]", 263,0,2,Arrays.asList("",ChatColor.DARK_GRAY+"● ● "+ChatColor.DARK_GRAY+"ⅹ",ChatColor.GRAY+"슬롯 머신을 돌린 결과",ChatColor.GRAY+"석탄이 2개일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 10, inv);
		Setter = GambleConfig.getString(MachineNumber+".11");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.WHITE+""+ChatColor.BOLD+"[더블 아이언]", 265,0,2,Arrays.asList("",ChatColor.WHITE+"■ ■ "+ChatColor.DARK_GRAY+"ⅹ",ChatColor.GRAY+"슬롯 머신을 돌린 결과",ChatColor.GRAY+"철괴가 2개일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 11, inv);
		Setter = GambleConfig.getString(MachineNumber+".12");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+"[더블 골드]", 266,0,2,Arrays.asList("",ChatColor.YELLOW+"■ ■ "+ChatColor.DARK_GRAY+"ⅹ",ChatColor.GRAY+"슬롯 머신을 돌린 결과",ChatColor.GRAY+"금괴가 2개일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 12, inv);
		Setter = GambleConfig.getString(MachineNumber+".13");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"[더블 다이아몬드]", 264,0,2,Arrays.asList("",ChatColor.DARK_AQUA+"◆ ◆ "+ChatColor.DARK_GRAY+"ⅹ",ChatColor.GRAY+"슬롯 머신을 돌린 결과",ChatColor.GRAY+"모두 다이아가 2개일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 13, inv);
		Setter = GambleConfig.getString(MachineNumber+".14");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.GREEN+""+ChatColor.BOLD+"[더블 에메랄드]", 388,0,2,Arrays.asList("",ChatColor.GREEN+"◈ ◈ "+ChatColor.DARK_GRAY+"ⅹ",ChatColor.GRAY+"슬롯 머신을 돌린 결과",ChatColor.GRAY+"에메랄드가 2개일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 14, inv);
		Setter = GambleConfig.getString(MachineNumber+".15");
		if(Setter.compareTo("null")==0)Setter="없음";
		Stack2(ChatColor.BLUE+""+ChatColor.BOLD+"[더블 네더 스타]", 399,0,2,Arrays.asList("",ChatColor.BLUE+"★ ★ "+ChatColor.DARK_GRAY+"ⅹ",ChatColor.GRAY+"슬롯 머신을 돌린 결과",ChatColor.GRAY+"네더 별이 2개일 경우입니다.","",ChatColor.YELLOW+"[좌 클릭시 상품 설정]","",ChatColor.GRAY+"[현재 상품]",ChatColor.GRAY+Setter), 15, inv);

		Stack2(ChatColor.DARK_GREEN+""+ChatColor.BOLD+"[도박 코인 설정]", 341,0,1,Arrays.asList("",ChatColor.GRAY+"슬롯 머신 1회 이용을 위해",ChatColor.GRAY+"필요한 코인을 설정합니다.","",ChatColor.YELLOW+"[좌 클릭시 코인 설정]",""), 8, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+MachineNumber), 27, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 35, inv);
		player.openInventory(inv);
	}
	
	public void SlotMachine_DetailGUI_Click(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		String MachineNumber =  ChatColor.stripColor(event.getInventory().getItem(27).getItemMeta().getLore().get(1));

		switch (event.getSlot())
		{
		case 0://각 상품들
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			GamblePresentGUI(player, (byte)0, (byte)1, (short)event.getSlot(),MachineNumber);
			return;
		case 8:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SlotMachineCoinGUI(player, MachineNumber);
			return;
		case 27://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SlotMachine_MainGUI(player,0);
			return;
		case 35://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void SlotMachineCoinGUI(Player player, String MachineNumber)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "도박 기계 코인");

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager GambleConfig =YC.getNewConfig("ETC/SlotMachine.yml");
		
		if(GambleConfig.contains(MachineNumber+".8"))
			ItemStackStack(GambleConfig.getItemStack(MachineNumber+".8"), 4, inv);
			
		Stack(ChatColor.LIGHT_PURPLE + "[코인 넣기]→→→", 166,0,1,null, 1, inv);
		Stack(ChatColor.LIGHT_PURPLE + "[코인 넣기]→→→", 166,0,1,null, 2, inv);
		Stack(ChatColor.LIGHT_PURPLE + "[코인 넣기]→→→", 166,0,1,null, 3, inv);
		Stack(ChatColor.LIGHT_PURPLE + "←←←[코인 넣기]", 166,0,1,null, 5, inv);
		Stack(ChatColor.LIGHT_PURPLE + "←←←[코인 넣기]", 166,0,1,null, 6, inv);
		Stack(ChatColor.LIGHT_PURPLE + "←←←[코인 넣기]", 166,0,1,null, 7, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+MachineNumber), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
		player.openInventory(inv);
	}
	
	public void SlotMachineCoinGUI_Click(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		if(event.getSlot()!=4)
			event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		String MachineNumber = ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 0://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SlotMachine_DetailGUI(player, MachineNumber);
			return;
		case 8://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void SlotMachineCoinGUI_Close(InventoryCloseEvent event)
	{
		String MachineNumber = ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getLore().get(1));

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager GambleConfig =YC.getNewConfig("ETC/SlotMachine.yml");
		
		if(event.getInventory().getItem(4)!=null)
			GambleConfig.set(MachineNumber+".8", event.getInventory().getItem(4));
		else
			GambleConfig.set(MachineNumber+".8", null);
		GambleConfig.saveConfig();
	}

	
	public void SlotMachine_PlayGUI(Player player, String MachineNumber)
	{
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "슬롯 머신");

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager GambleConfig =YC.getNewConfig("ETC/SlotMachine.yml");
		
		if(GambleConfig.contains(MachineNumber+".8"))
			ItemStackStack(GambleConfig.getItemStack(MachineNumber+".8"), 16, inv);
		else
			Stack2(ChatColor.RED+""+ChatColor.BOLD+"[기기 수리 중]", 166,0,1,Arrays.asList("",ChatColor.GRAY+"슬롯 머신 이용에",ChatColor.GRAY+"불편을 끼쳐드려",ChatColor.GRAY+"대단히 죄송합니다."), 16, inv);
		Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+"[운명의 여신이시여!]", 76,0,1,Arrays.asList("",ChatColor.GRAY+"슬롯 머신에 코인을 넣고",ChatColor.GRAY+"힘차게 돌립니다!","",ChatColor.GREEN+"[우측 아이템이 코인입니다.]"), 15, inv);

		for(byte count=0; count<5; count++)
			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,4,1,Arrays.asList(""), count, inv);
		for(byte count=5; count<9; count++)
			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,11,1,Arrays.asList(""), count, inv);

		Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,4,1,Arrays.asList(""), 9, inv);
		Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,4,1,Arrays.asList(""), 13, inv);
		Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,11,1,Arrays.asList(""), 14, inv);
		Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,11,1,Arrays.asList(ChatColor.BLACK+MachineNumber), 17, inv);
		
		for(byte count=18; count<23; count++)
			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,4,1,Arrays.asList(""), count, inv);
		for(byte count=23; count<27; count++)
			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,11,1,Arrays.asList(""), count, inv);
		
		for(byte count=1;count<4;count++)
		{
			byte randomnum = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 5);
			short ItemID=263;
			switch(randomnum)
			{
			case 0:
				ItemID = 263;
				break;
			case 1:
				ItemID = 265;
				break;
			case 2:
				ItemID = 266;
				break;
			case 3:
				ItemID = 264;
				break;
			case 4:
				ItemID = 388;
				break;
			case 5:
				ItemID = 399;
				break;
			}
			Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+""+count+" 번째 슬롯", ItemID,0,1,Arrays.asList(""), count+9, inv);
			
		}
		player.openInventory(inv);
	}

	public void SlotMachine_PlayGUI_Click(InventoryClickEvent event)
	{
		event.setCancelled(true);
		if(event.getSlot()==15)
		{
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			ItemStack Coin = event.getInventory().getItem(16);
			Player player = (Player) event.getWhoClicked();
			if(event.getCurrentItem().getTypeId()==69)
				return;
			for(byte countta=0;countta < player.getInventory().getSize(); countta++)
			{
				if(player.getInventory().getItem(countta)!=null)
				{
					if(player.getInventory().getItem(countta).isSimilar(Coin)==true)
					{
						if(player.getInventory().getItem(countta).getAmount()>=Coin.getAmount())
						{
							ItemStack ii = player.getInventory().getItem(countta);
							if(ii.getAmount()==Coin.getAmount())
								player.getInventory().setItem(countta, null);
							else
							{
								ii.setAmount(ii.getAmount()-Coin.getAmount());
								player.getInventory().setItem(countta, ii);
							}
							player.updateInventory();
							if(event.getInventory().getItem(16).getTypeId()==166 && event.getInventory().getItem(16).hasItemMeta()==true)
								if(event.getInventory().getItem(16).getItemMeta().getDisplayName().compareTo(ChatColor.RED+""+ChatColor.BOLD+"[기기 수리 중]")==0)
								{
									s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
									player.sendMessage(ChatColor.RED+"[슬롯 머신] : 현재 기기는 수리 중입니다! 관리자에게 문의하세요!");
									return;
								}
							
							if(GoldBigDragon_RPG.ServerTick.ServerTickMain.PlayerTaskList.containsKey(player.getName())==true)
							{
								s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
								player.sendMessage(ChatColor.RED+"[슬롯 머신] : 이미 대기중인 작업이 있습니다! 볼일을 다 보고 오세요!");
								return;
							}
							
							s.SP(player, Sound.CHEST_CLOSE, 1.0F, 0.5F);
							s.SP(player, Sound.CHEST_OPEN, 1.0F, 0.5F);
							
							ItemStack Icon = new MaterialData(69, (byte) 0).toItemStack(1);
							ItemMeta Icon_Meta = Icon.getItemMeta();
							Icon_Meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"[지금은 돌릴 수 없다!]");
							Icon_Meta.setLore(Arrays.asList("",ChatColor.GRAY+"결과를 기다리세요!"));
							Icon.setItemMeta(Icon_Meta);
							
							event.getInventory().setItem(15, Icon);
							short ItemID[] = new short[3];
							for(byte count=0;count<3;count++)
							{
								byte randomnum = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 5);
								ItemID[count]=263;
								switch(randomnum)
								{
								case 0:
									ItemID[count] = 263;
									break;
								case 1:
									ItemID[count] = 265;
									break;
								case 2:
									ItemID[count] = 266;
									break;
								case 3:
									ItemID[count] = 264;
									break;
								case 4:
									ItemID[count] = 388;
									break;
								case 5:
									ItemID[count] = 399;
									break;
								}
							}
							Long UTC = GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC+5;
							for(;;)
							{
								if(GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.containsKey(UTC))
									UTC=UTC+1;
								else
									break;
							}
							
							GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject OBJECT = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(UTC, "G_SM");
							OBJECT.setString((byte)0, player.getName());
							OBJECT.setString((byte)1, ChatColor.stripColor(event.getInventory().getItem(17).getItemMeta().getLore().get(0)));

							OBJECT.setInt((byte)0, ItemID[0]);
							OBJECT.setInt((byte)1, ItemID[1]);
							OBJECT.setInt((byte)2, ItemID[2]);
							OBJECT.setMaxCount(20);
							GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(UTC, OBJECT);
							GoldBigDragon_RPG.ServerTick.ServerTickMain.PlayerTaskList.put(player.getName(), "G_SM");
							return;
						}
					}
				}
			}
			s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
			player.sendMessage(ChatColor.RED+"[슬롯 머신] : 코인이 부족합니다!");
			return;
		}
	}

	public void SlotMachine_RollingGUI(String player, short[] itemID, boolean fin, String MachineNumber)
	{
		if(Bukkit.getServer().getPlayer(player)!=null)
		{
			if(Bukkit.getServer().getPlayer(player).isOnline())
			{
				Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "슬롯 머신");
				if(fin)
				{
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager GambleConfig =YC.getNewConfig("ETC/SlotMachine.yml");
					
					if(GambleConfig.contains(MachineNumber+".8"))
						ItemStackStack(GambleConfig.getItemStack(MachineNumber+".8"), 16, inv);
					else
						Stack2(ChatColor.RED+""+ChatColor.BOLD+"[기기 수리 중]", 166,0,1,Arrays.asList("",ChatColor.GRAY+"슬롯 머신 이용에",ChatColor.GRAY+"불편을 끼쳐드려",ChatColor.GRAY+"대단히 죄송합니다."), 16, inv);
					Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+"[운명의 여신이시여!]", 76,0,1,Arrays.asList("",ChatColor.GRAY+"슬롯 머신에 코인을 넣고",ChatColor.GRAY+"힘차게 돌립니다!","",ChatColor.GREEN+"[우측 아이템이 코인입니다.]"), 15, inv);
				}
				else
				{
					Stack2(ChatColor.RED+""+ChatColor.BOLD+"[지금은 돈을 넣을 수 없다!]", 166,0,1,Arrays.asList("",ChatColor.GRAY+"겸허한 마음으로 기다리자!"), 16, inv);
					Stack2(ChatColor.RED+""+ChatColor.BOLD+"[지금은 돌릴 수 없다!]", 69,0,1,Arrays.asList("",ChatColor.GRAY+"결과를 기다리세요!"), 15, inv);
				}
				for(byte count=0; count<5; count++)
					Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,4,1,Arrays.asList(""), count, inv);
				for(byte count=5; count<9; count++)
					Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,11,1,Arrays.asList(""), count, inv);

				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,4,1,Arrays.asList(""), 9, inv);
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,4,1,Arrays.asList(""), 13, inv);
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,11,1,Arrays.asList(""), 14, inv);
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,11,1,Arrays.asList(ChatColor.BLACK+MachineNumber), 17, inv);
				
				for(byte count=18; count<23; count++)
					Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,4,1,Arrays.asList(""), count, inv);
				for(byte count=23; count<27; count++)
					Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+" ", 160,11,1,Arrays.asList(""), count, inv);
				
				for(byte count=0;count<3;count++)
					Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+""+(count+1)+" 번째 슬롯", itemID[count],0,1,Arrays.asList(""), count+10, inv);
				Bukkit.getServer().getPlayer(player).openInventory(inv);
				GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
				s.SP(Bukkit.getServer().getPlayer(player), Sound.DIG_STONE, 1.0F, 1.0F);
			}
		}
	}
}
