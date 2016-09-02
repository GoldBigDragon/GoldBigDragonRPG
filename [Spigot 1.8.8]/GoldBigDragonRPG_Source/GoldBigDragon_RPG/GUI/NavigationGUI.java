package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class NavigationGUI extends GUIutil
{
	public void NavigationListGUI(Player player, short page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "네비 목록 : " + (page+1));

		Object[] Navi= NavigationConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < Navi.length;count++)
		{
			if(count > Navi.length || loc >= 45) break;
			String NaviName = NavigationConfig.getString(Navi[count]+".Name");
			String world = NavigationConfig.getString(Navi[count]+".world");
			int x = NavigationConfig.getInt(Navi[count]+".x");
			int y = NavigationConfig.getInt(Navi[count]+".y");
			int z = NavigationConfig.getInt(Navi[count]+".z");
			int Time = NavigationConfig.getInt(Navi[count]+".time");
			int sensitive = NavigationConfig.getInt(Navi[count]+".sensitive");
			boolean Permition = NavigationConfig.getBoolean(Navi[count]+".onlyOPuse");
			int ShowArrow = NavigationConfig.getInt(Navi[count]+".ShowArrow");
			
			
			String TimeS = ChatColor.DARK_AQUA+"<도착할 때 까지 유지>";
			String PermitionS = ChatColor.DARK_AQUA+"<OP만 사용 가능>";
			String sensitiveS = ChatColor.BLUE+"<반경 "+sensitive+"블록 이내를 도착지로 판정>";
			String ShowArrowS = ChatColor.DARK_AQUA+"<기본 화살표 모양>";
			if(Permition == false)
				PermitionS = ChatColor.DARK_AQUA+"<모두 사용 가능>";
			if(Time >= 0)
				TimeS = ChatColor.DARK_AQUA+"<"+Time+"초 동안 유지>";
			switch(ShowArrow)
			{
			default:
				ShowArrowS = ChatColor.DARK_AQUA+"<기본 화살표 모양>";
				break;
			}
			Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395,0,1,Arrays.asList(
			ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
			ChatColor.BLUE+"[도착 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,
			ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,sensitiveS,"",
			ChatColor.DARK_AQUA+"[기타 옵션]",TimeS,PermitionS,ShowArrowS,""
			,ChatColor.YELLOW+"[좌 클릭시 네비 설정]",ChatColor.RED+"[Shift + 우클릭시 네비 삭제]"), loc, inv);
			loc++;
		}
		
		if(Navi.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 네비", 386,0,1,Arrays.asList(ChatColor.GRAY + "새 네비를 생성합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}

	public void NavigationOptionGUI(Player player, String NaviUTC)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.BLACK + "네비 설정");

		String NaviName = NavigationConfig.getString(NaviUTC+".Name");
		String world = NavigationConfig.getString(NaviUTC+".world");
		int x = NavigationConfig.getInt(NaviUTC+".x");
		short y = (short) NavigationConfig.getInt(NaviUTC+".y");
		int z = NavigationConfig.getInt(NaviUTC+".z");
		int Time = NavigationConfig.getInt(NaviUTC+".time");
		short sensitive = (short) NavigationConfig.getInt(NaviUTC+".sensitive");
		boolean Permition = NavigationConfig.getBoolean(NaviUTC+".onlyOPuse");
		byte ShowArrow = (byte) NavigationConfig.getInt(NaviUTC+".ShowArrow");

		String TimeS = ChatColor.BLUE+"[도착할 때 까지 유지]";
		String PermitionS = ChatColor.BLUE+"[OP만 사용 가능]";
		String sensitiveS = ChatColor.BLUE+"[반경 "+sensitive+"블록 이내를 도착지로 판정]";
		String ShowArrowS = ChatColor.BLUE+"[기본 화살표 모양]";
		if(Permition == false)
			PermitionS = ChatColor.BLUE+"[모두 사용 가능]";
		if(Time >= 0)
			TimeS = ChatColor.BLUE+"["+Time+"초 동안 유지]";
		switch(ShowArrow)
		{
		default:
			ShowArrowS = ChatColor.BLUE+"[기본 화살표 모양]";
			break;
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이름 변경", 386,0,1,Arrays.asList(ChatColor.GRAY + "네비게이션 이름을 변경합니다.","",ChatColor.BLUE+"[현재 이름]",ChatColor.WHITE+NaviName,""), 10, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "좌표 변경", 358,0,1,Arrays.asList(ChatColor.GRAY + "클릭시 현재 위치로 변경합니다.","",ChatColor.BLUE+"[도착 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,""), 11, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "지속 시간", 347,0,1,Arrays.asList(ChatColor.GRAY + "네비게이션 지속 시간을 변경합니다.","",TimeS,""), 12, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "도착 반경", 420,0,1,Arrays.asList(ChatColor.GRAY + "도착 판정 범위를 변경합니다.","",sensitiveS,""), 13, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "사용 권한", 137,0,1,Arrays.asList(ChatColor.GRAY + "네비게이션 사용 권한을 설정합니다.","",PermitionS,""), 14, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "네비 타입", 381,0,1,Arrays.asList(ChatColor.GRAY + "네비게이션 타입을 설정합니다.","",ShowArrowS,""), 15, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 27, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+NaviUTC), 35, inv);
		player.openInventory(inv);
	}
	
	public void UseNavigationGUI(Player player, short page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "네비 사용 : " + (page+1));

		Object[] Navi= NavigationConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < Navi.length;count++)
		{
			if(count > Navi.length || loc >= 45) break;
			boolean Permition = NavigationConfig.getBoolean(Navi[count]+".onlyOPuse");
			if(Permition)
			{
				if(player.isOp())
				{
					String NaviName = NavigationConfig.getString(Navi[count]+".Name");
					String world = NavigationConfig.getString(Navi[count]+".world");
					int x = NavigationConfig.getInt(Navi[count]+".x");
					short y = (short) NavigationConfig.getInt(Navi[count]+".y");
					int z = NavigationConfig.getInt(Navi[count]+".z");

					int Time = NavigationConfig.getInt(Navi[count]+".time");
					String TimeS = ChatColor.DARK_AQUA+"<도착할 때 까지 유지>";
					if(Time >= 0)
						TimeS = ChatColor.DARK_AQUA+"<"+Time+"초 동안 유지>";
					
					Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395,0,1,Arrays.asList(
					ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
					ChatColor.BLUE+"[설정 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,
					ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,"",TimeS,"",ChatColor.YELLOW+"[좌 클릭시 네비 사용]"), loc, inv);
					loc++;
				}
			}
			else
			{
				String NaviName = NavigationConfig.getString(Navi[count]+".Name");
				String world = NavigationConfig.getString(Navi[count]+".world");
				int x = NavigationConfig.getInt(Navi[count]+".x");
				short y = (short) NavigationConfig.getInt(Navi[count]+".y");
				int z = NavigationConfig.getInt(Navi[count]+".z");

				int Time = NavigationConfig.getInt(Navi[count]+".time");
				String TimeS = ChatColor.DARK_AQUA+"<도착할 때 까지 유지>";
				if(Time >= 0)
					TimeS = ChatColor.DARK_AQUA+"<"+Time+"초 동안 유지>";
				
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395,0,1,Arrays.asList(
				ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
				ChatColor.BLUE+"[설정 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,
				ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,"",TimeS,"",ChatColor.YELLOW+"[좌 클릭시 네비 사용]"), loc, inv);
				loc++;
			}
		}
		
		if(Navi.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void NavigationListGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new OPBoxGUI().OPBoxGUI_Main(player, (byte) 1);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			NavigationListGUI(player, (short) (page-1));
			return;
		case 49://새 네비
			{
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[네비] : 새로운 네비게이션 이름을 입력 해 주세요!");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "NN");
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			NavigationListGUI(player, (short) (page+1));
			return;
		default :
			if(event.isLeftClick() == true)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				NavigationOptionGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			else if(event.isShiftClick() == true && event.isRightClick() == true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
				NavigationConfig.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				NavigationConfig.saveConfig();
				NavigationListGUI(player, page);
			}
			return;
		}
	}
	
	public void NavigationOptionGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
		String UTC = ChatColor.stripColor(event.getInventory().getItem(35).getItemMeta().getLore().get(1));
		
		switch (event.getSlot())
		{
		case 10://이름 변경
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.9F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[네비] : 원하는 네비게이션 이름을 입력 해 주세요!");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "CNN");
				u.setString(player, (byte)1, UTC);
			}
			return;
		case 11://좌표 변경
			s.SP(player, Sound.ANVIL_LAND, 1.0F, 0.9F);
			NavigationConfig.set(UTC+".world", player.getLocation().getWorld().getName());
			NavigationConfig.set(UTC+".x", (int)player.getLocation().getX());
			NavigationConfig.set(UTC+".y", (int)player.getLocation().getY());
			NavigationConfig.set(UTC+".z", (int)player.getLocation().getZ());
			NavigationConfig.saveConfig();
			NavigationOptionGUI(player, UTC);
			return;
		case 12://지속 시간
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.9F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[네비] : 네비게이션 지속 시간을 입력 해 주세요!");
				player.sendMessage(ChatColor.YELLOW+"(-1초(찾을 때 까지) ~ 3600초(1시간))");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "CNT");
				u.setString(player, (byte)1, UTC);
			}
			return;
		case 13://도착 반경
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.9F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[네비] : 도착 판정 범위를 입력 해 주세요!");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 1000)");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "CNS");
				u.setString(player, (byte)1, UTC);
			}
			return;
		case 14://사용 권한
			s.SP(player, Sound.ANVIL_LAND, 1.0F, 0.9F);
			if(NavigationConfig.getBoolean(UTC+".onlyOPuse"))
				NavigationConfig.set(UTC+".onlyOPuse", false);
			else
				NavigationConfig.set(UTC+".onlyOPuse", true);
			NavigationConfig.saveConfig();
			NavigationOptionGUI(player, UTC);
			return;
		case 15://네비 타입
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 0.9F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[네비] : 네비게이션 화살표 모양을 선택하세요!");
				player.sendMessage(ChatColor.YELLOW+"(0 ~ 10)");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "CNA");
				u.setString(player, (byte)1, UTC);
			}
			return;
		case 27://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			NavigationListGUI(player, (short) 0);
			return;
		case 35://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void UseNavigationGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new ETCGUI().ETCGUI_Main(player);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;

		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UseNavigationGUI(player, (short) (page-1));
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UseNavigationGUI(player, (short) (page+1));
			return;
		default :
			if(event.isLeftClick() == true)
			{
				for(int count = 0; count < ServerTickMain.NaviUsingList.size();count++)
				{
					if(ServerTickMain.NaviUsingList.get(count).equals(player.getName()))
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[네비게이션] : 당신은 이미 네비게이션을 사용 중입니다!");
						return;
					}
				}
				ServerTickMain.NaviUsingList.add(player.getName());
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
				String UTC = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				player.closeInventory();
				s.SP(player, Sound.NOTE_PLING, 1.0F, 1.0F);
				
				GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC, "NV");
				STSO.setCount(0);//횟 수 초기화
				STSO.setMaxCount(NavigationConfig.getInt(UTC+".time"));//N초간 네비게이션
				//-1초 설정시, N초간이 아닌, 찾아 갈 때 까지 네비게이션 지원
				STSO.setString((byte)1, NavigationConfig.getString(UTC+".world"));//목적지 월드 이름 저장
				STSO.setString((byte)2, player.getName());//플레이어 이름 저장
				
				STSO.setInt((byte)0, NavigationConfig.getInt(UTC+".x"));//목적지X 위치저장
				STSO.setInt((byte)1, NavigationConfig.getInt(UTC+".y"));//목적지Y 위치저장
				STSO.setInt((byte)2, NavigationConfig.getInt(UTC+".z"));//목적지Z 위치저장
				STSO.setInt((byte)3, NavigationConfig.getInt(UTC+".sensitive"));//판정 범위 저장
				STSO.setInt((byte)4, NavigationConfig.getInt(UTC+".ShowArrow"));//파티클 설정
				
				GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC, STSO);
				player.sendMessage(ChatColor.YELLOW+"[네비게이션] : 길찾기 시스템이 가동됩니다!");
				player.sendMessage(ChatColor.YELLOW+"(화살표가 보이지 않을 경우, [ESC] → [설정] → [비디오 설정] 속의 [입자]를 [모두]로 변경해 주세요!)");
			}
			return;
		}
	}
}
