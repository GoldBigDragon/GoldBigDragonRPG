package admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import servertick.ServerTick_Main;
import user.ETC_GUI;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;



public class Navigation_GUI extends Util_GUI
{
	public void NavigationListGUI(Player player, short page)
	{
		YamlLoader navigationYaml = new YamlLoader();
		navigationYaml.getConfig("Navigation/NavigationList.yml");

		String UniqueCode = "§0§0§1§1§4§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0네비 목록 : " + (page+1));

		String[] Navi= navigationYaml.getConfigurationSection("").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		String NaviName = null;
		String world = null;
		int x = 0;
		int y = 0;
		int z = 0;
		int Time = 0;
		int sensitive = 0;
		boolean Permition = false;
		int ShowArrow = 0;
		
		String TimeS = null;
		String PermitionS = null;
		String sensitiveS = ChatColor.BLUE+"<반경 "+sensitive+"블록 이내를 도착지로 판정>";
		String ShowArrowS = null;
		for(int count = page*45; count < Navi.length;count++)
		{
			if(count > Navi.length || loc >= 45) break;
			NaviName = navigationYaml.getString(Navi[count]+".Name");
			world = navigationYaml.getString(Navi[count]+".world");
			x = navigationYaml.getInt(Navi[count]+".x");
			y = navigationYaml.getInt(Navi[count]+".y");
			z = navigationYaml.getInt(Navi[count]+".z");
			Time = navigationYaml.getInt(Navi[count]+".time");
			sensitive = navigationYaml.getInt(Navi[count]+".sensitive");
			Permition = navigationYaml.getBoolean(Navi[count]+".onlyOPuse");
			ShowArrow = navigationYaml.getInt(Navi[count]+".ShowArrow");
			
			if(Permition == false)
				PermitionS = ChatColor.DARK_AQUA+"<모두 사용 가능>";
			else
				PermitionS = ChatColor.DARK_AQUA+"<OP만 사용 가능>";
			if(Time >= 0)
				TimeS = ChatColor.DARK_AQUA+"<"+Time+"초 동안 유지>";
			else
				TimeS = ChatColor.DARK_AQUA+"<도착할 때 까지 유지>";
			switch(ShowArrow)
			{
			default:
				ShowArrowS = ChatColor.DARK_AQUA+"<기본 화살표 모양>";
				break;
			}
			Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count], 395,0,1,Arrays.asList(
			ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
			ChatColor.BLUE+"[도착 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,
			ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,sensitiveS,"",
			ChatColor.DARK_AQUA+"[기타 옵션]",TimeS,PermitionS,ShowArrowS,""
			,ChatColor.YELLOW+"[좌 클릭시 네비 설정]",ChatColor.RED+"[Shift + 우클릭시 네비 삭제]"), loc, inv);
			loc++;
		}
		
		if(Navi.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack2("§f§l새 네비", 386,0,1,Arrays.asList(ChatColor.GRAY + "새 네비를 생성합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}

	public void NavigationOptionGUI(Player player, String NaviUTC)
	{
		YamlLoader navigationYaml = new YamlLoader();
		navigationYaml.getConfig("Navigation/NavigationList.yml");

		String UniqueCode = "§0§0§1§1§5§r";
		Inventory inv = Bukkit.createInventory(null, 36, UniqueCode + "§0네비 설정");

		String NaviName = navigationYaml.getString(NaviUTC+".Name");
		String world = navigationYaml.getString(NaviUTC+".world");
		int x = navigationYaml.getInt(NaviUTC+".x");
		short y = (short) navigationYaml.getInt(NaviUTC+".y");
		int z = navigationYaml.getInt(NaviUTC+".z");
		int Time = navigationYaml.getInt(NaviUTC+".time");
		short sensitive = (short) navigationYaml.getInt(NaviUTC+".sensitive");
		boolean Permition = navigationYaml.getBoolean(NaviUTC+".onlyOPuse");
		byte ShowArrow = (byte) navigationYaml.getInt(NaviUTC+".ShowArrow");

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
		
		Stack2("§f§l이름 변경", 386,0,1,Arrays.asList(ChatColor.GRAY + "네비게이션 이름을 변경합니다.","",ChatColor.BLUE+"[현재 이름]",ChatColor.WHITE+NaviName,""), 10, inv);
		Stack2("§f§l좌표 변경", 358,0,1,Arrays.asList(ChatColor.GRAY + "클릭시 현재 위치로 변경합니다.","",ChatColor.BLUE+"[도착 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,""), 11, inv);
		Stack2("§f§l지속 시간", 347,0,1,Arrays.asList(ChatColor.GRAY + "네비게이션 지속 시간을 변경합니다.","",TimeS,""), 12, inv);
		Stack2("§f§l도착 반경", 420,0,1,Arrays.asList(ChatColor.GRAY + "도착 판정 범위를 변경합니다.","",sensitiveS,""), 13, inv);
		Stack2("§f§l사용 권한", 137,0,1,Arrays.asList(ChatColor.GRAY + "네비게이션 사용 권한을 설정합니다.","",PermitionS,""), 14, inv);
		Stack2("§f§l네비 타입", 381,0,1,Arrays.asList(ChatColor.GRAY + "네비게이션 타입을 설정합니다.","",ShowArrowS,""), 15, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 27, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+NaviUTC), 35, inv);
		player.openInventory(inv);
	}
	
	public void UseNavigationGUI(Player player, short page)
	{
		YamlLoader navigationYaml = new YamlLoader();
		navigationYaml.getConfig("Navigation/NavigationList.yml");

		String UniqueCode = "§0§0§1§1§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0네비 사용 : " + (page+1));

		String[] Navi= navigationYaml.getConfigurationSection("").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		String NaviName = null;
		String world = null;
		String TimeS = null;
		for(int count = page*45; count < Navi.length;count++)
		{
			if(count > Navi.length || loc >= 45) break;
			boolean Permition = navigationYaml.getBoolean(Navi[count]+".onlyOPuse");
			if(Permition)
			{
				if(player.isOp())
				{
					NaviName = navigationYaml.getString(Navi[count]+".Name");
					world = navigationYaml.getString(Navi[count]+".world");
					int x = navigationYaml.getInt(Navi[count]+".x");
					short y = (short) navigationYaml.getInt(Navi[count]+".y");
					int z = navigationYaml.getInt(Navi[count]+".z");

					int Time = navigationYaml.getInt(Navi[count]+".time");
					if(Time >= 0)
						TimeS = ChatColor.DARK_AQUA+"<"+Time+"초 동안 유지>";
					else
						TimeS = ChatColor.DARK_AQUA+"<도착할 때 까지 유지>";
					
					Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count], 395,0,1,Arrays.asList(
					ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
					ChatColor.BLUE+"[설정 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,
					ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,"",TimeS,"",ChatColor.YELLOW+"[좌 클릭시 네비 사용]"), loc, inv);
					loc++;
				}
			}
			else
			{
				NaviName = navigationYaml.getString(Navi[count]+".Name");
				world = navigationYaml.getString(Navi[count]+".world");
				int x = navigationYaml.getInt(Navi[count]+".x");
				short y = (short) navigationYaml.getInt(Navi[count]+".y");
				int z = navigationYaml.getInt(Navi[count]+".z");

				int Time = navigationYaml.getInt(Navi[count]+".time");
				if(Time >= 0)
					TimeS = ChatColor.DARK_AQUA+"<"+Time+"초 동안 유지>";
				else
					TimeS = ChatColor.DARK_AQUA+"<도착할 때 까지 유지>";
				
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count], 395,0,1,Arrays.asList(
				ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
				ChatColor.BLUE+"[설정 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,
				ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,"",TimeS,"",ChatColor.YELLOW+"[좌 클릭시 네비 사용]"), loc, inv);
				loc++;
			}
		}
		
		if(Navi.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void NavigationListGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		int slot = event.getSlot();

		
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new OPbox_GUI().OPBoxGUI_Main(player, (byte) 1);
			else if(slot == 48)//이전 페이지
				NavigationListGUI(player, (short) (page-1));
			else if(slot == 49)//새 네비
			{
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[네비] : 새로운 네비게이션 이름을 입력 해 주세요!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "NN");
			}
			else if(slot == 50)//다음 페이지
				NavigationListGUI(player, (short) (page+1));
			else
			{
				if(event.isLeftClick() == true)
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					NavigationOptionGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
				else if(event.isShiftClick() == true && event.isRightClick() == true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					YamlLoader navigationYaml = new YamlLoader();
					navigationYaml.getConfig("Navigation/NavigationList.yml");
					navigationYaml.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					navigationYaml.saveConfig();
					NavigationListGUI(player, page);
				}
			}
		}
	}
	
	public void NavigationOptionGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		
		
		if(slot == 35)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 27)//이전 목록
				NavigationListGUI(player, (short) 0);
			else
			{
				YamlLoader navigationYaml = new YamlLoader();
				navigationYaml.getConfig("Navigation/NavigationList.yml");
				String UTC = ChatColor.stripColor(event.getInventory().getItem(35).getItemMeta().getLore().get(1));

				if(slot == 11)//좌표 변경
				{
					navigationYaml.set(UTC+".world", player.getLocation().getWorld().getName());
					navigationYaml.set(UTC+".x", (int)player.getLocation().getX());
					navigationYaml.set(UTC+".y", (int)player.getLocation().getY());
					navigationYaml.set(UTC+".z", (int)player.getLocation().getZ());
					navigationYaml.saveConfig();
					NavigationOptionGUI(player, UTC);
				}
				else if(slot == 14)//사용 권한
				{
					if(navigationYaml.getBoolean(UTC+".onlyOPuse"))
						navigationYaml.set(UTC+".onlyOPuse", false);
					else
						navigationYaml.set(UTC+".onlyOPuse", true);
					navigationYaml.saveConfig();
					NavigationOptionGUI(player, UTC);
				}
				else
				{
					player.closeInventory();
					UserData_Object u = new UserData_Object();
					u.setType(player, "Navi");
					u.setString(player, (byte)1, UTC);
					if(slot == 10)//이름 변경
					{
						player.sendMessage(ChatColor.GREEN+"[네비] : 원하는 네비게이션 이름을 입력 해 주세요!");
						u.setString(player, (byte)0, "CNN");
					}
					else if(slot == 12)//지속 시간
					{
						player.sendMessage(ChatColor.GREEN+"[네비] : 네비게이션 지속 시간을 입력 해 주세요!");
						player.sendMessage(ChatColor.YELLOW+"(-1초(찾을 때 까지) ~ 3600초(1시간))");
						u.setString(player, (byte)0, "CNT");
					}
					else if(slot == 13)//도착 반경
					{
						player.sendMessage(ChatColor.GREEN+"[네비] : 도착 판정 범위를 입력 해 주세요!");
						player.sendMessage(ChatColor.YELLOW+"(1 ~ 1000)");
						u.setString(player, (byte)0, "CNS");
					}
					else if(slot == 15)//네비 타입
					{
						player.sendMessage(ChatColor.GREEN+"[네비] : 네비게이션 화살표 모양을 선택하세요!");
						player.sendMessage(ChatColor.YELLOW+"(0 ~ 10)");
						u.setString(player, (byte)0, "CNA");
					}
				}
			}
		}
	}

	public void UseNavigationGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		int slot = event.getSlot();
		
		
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new ETC_GUI().ETCGUI_Main(player);
			else if(slot == 48)//이전 페이지
				UseNavigationGUI(player, (short) (page-1));
			else if(slot == 50)//다음 페이지
				UseNavigationGUI(player, (short) (page+1));
			else if(event.isLeftClick() == true)
			{
				for(int count = 0; count < ServerTick_Main.NaviUsingList.size();count++)
				{
					if(ServerTick_Main.NaviUsingList.get(count).equals(player.getName()))
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[네비게이션] : 당신은 이미 네비게이션을 사용 중입니다!");
						return;
					}
				}
				ServerTick_Main.NaviUsingList.add(player.getName());
				YamlLoader navigationYaml = new YamlLoader();
				navigationYaml.getConfig("Navigation/NavigationList.yml");
				String UTC = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				player.closeInventory();
				SoundEffect.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.0F);
				
				servertick.ServerTick_Object STSO = new servertick.ServerTick_Object(servertick.ServerTick_Main.nowUTC, "NV");
				STSO.setCount(0);//횟 수 초기화
				STSO.setMaxCount(navigationYaml.getInt(UTC+".time"));//N초간 네비게이션
				//-1초 설정시, N초간이 아닌, 찾아 갈 때 까지 네비게이션 지원
				STSO.setString((byte)1, navigationYaml.getString(UTC+".world"));//목적지 월드 이름 저장
				STSO.setString((byte)2, player.getName());//플레이어 이름 저장
				
				STSO.setInt((byte)0, navigationYaml.getInt(UTC+".x"));//목적지X 위치저장
				STSO.setInt((byte)1, navigationYaml.getInt(UTC+".y"));//목적지Y 위치저장
				STSO.setInt((byte)2, navigationYaml.getInt(UTC+".z"));//목적지Z 위치저장
				STSO.setInt((byte)3, navigationYaml.getInt(UTC+".sensitive"));//판정 범위 저장
				STSO.setInt((byte)4, navigationYaml.getInt(UTC+".ShowArrow"));//파티클 설정
				
				servertick.ServerTick_Main.Schedule.put(servertick.ServerTick_Main.nowUTC, STSO);
				player.sendMessage(ChatColor.YELLOW+"[네비게이션] : 길찾기 시스템이 가동됩니다!");
				player.sendMessage(ChatColor.YELLOW+"(화살표가 보이지 않을 경우, [ESC] → [설정] → [비디오 설정] 속의 [입자]를 [모두]로 변경해 주세요!)");
			}
		}
	}
}
