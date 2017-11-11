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
	public void navigationListGui(Player player, short page)
	{
		YamlLoader navigationYaml = new YamlLoader();
		navigationYaml.getConfig("Navigation/NavigationList.yml");

		String uniqueCode = "§0§0§1§1§4§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0네비 목록 : " + (page+1));

		String[] navi= navigationYaml.getConfigurationSection("").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		String naviName = null;
		String world = null;
		int x = 0;
		int y = 0;
		int z = 0;
		int time = 0;
		int sensitive = 0;
		boolean permition = false;
		int showArrow = 0;
		
		String timeS = null;
		String permitionS = null;
		String sensitiveS = "§9<반경 "+sensitive+"블록 이내를 도착지로 판정>";
		String showArrowS = null;
		for(int count = page*45; count < navi.length;count++)
		{
			if(count > navi.length || loc >= 45) break;
			naviName = navigationYaml.getString(navi[count]+".Name");
			world = navigationYaml.getString(navi[count]+".world");
			x = navigationYaml.getInt(navi[count]+".x");
			y = navigationYaml.getInt(navi[count]+".y");
			z = navigationYaml.getInt(navi[count]+".z");
			time = navigationYaml.getInt(navi[count]+".time");
			sensitive = navigationYaml.getInt(navi[count]+".sensitive");
			permition = navigationYaml.getBoolean(navi[count]+".onlyOPuse");
			showArrow = navigationYaml.getInt(navi[count]+".ShowArrow");
			
			if(permition == false)
				permitionS = "§3<모두 사용 가능>";
			else
				permitionS = "§3<OP만 사용 가능>";
			if(time >= 0)
				timeS = "§3<"+time+"초 동안 유지>";
			else
				timeS = "§3<도착할 때 까지 유지>";
			switch(showArrow)
			{
			default:
				showArrowS = "§3<기본 화살표 모양>";
				break;
			}
			Stack2("§0§l" + navi[count], 395,0,1,Arrays.asList(
			"§e§l"+naviName,"",
			"§9[도착 지점]","§9월드 : §f"+world,
			"§9좌표 : §f"+x+","+y+","+z,sensitiveS,"",
			"§3[기타 옵션]",timeS,permitionS,showArrowS,""
			,"§e[좌 클릭시 네비 설정]","§c[Shift + 우클릭시 네비 삭제]"), loc, inv);
			loc++;
		}
		
		if(navi.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		Stack2("§f§l새 네비", 386,0,1,Arrays.asList("§7새 네비를 생성합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}

	public void navigationOptionGui(Player player, String naviUTC)
	{
		YamlLoader navigationYaml = new YamlLoader();
		navigationYaml.getConfig("Navigation/NavigationList.yml");

		String uniqueCode = "§0§0§1§1§5§r";
		Inventory inv = Bukkit.createInventory(null, 36, uniqueCode + "§0네비 설정");

		String naviName = navigationYaml.getString(naviUTC+".Name");
		String world = navigationYaml.getString(naviUTC+".world");
		int x = navigationYaml.getInt(naviUTC+".x");
		short y = (short) navigationYaml.getInt(naviUTC+".y");
		int z = navigationYaml.getInt(naviUTC+".z");
		int time = navigationYaml.getInt(naviUTC+".time");
		short sensitive = (short) navigationYaml.getInt(naviUTC+".sensitive");
		boolean permition = navigationYaml.getBoolean(naviUTC+".onlyOPuse");
		byte showArrow = (byte) navigationYaml.getInt(naviUTC+".ShowArrow");

		String timeS = "§9[도착할 때 까지 유지]";
		String permitionS = "§9[OP만 사용 가능]";
		String sensitiveS = "§9[반경 "+sensitive+"블록 이내를 도착지로 판정]";
		String showArrowS = "§9[기본 화살표 모양]";
		if(permition == false)
			permitionS = "§9[모두 사용 가능]";
		if(time >= 0)
			timeS = "§9["+time+"초 동안 유지]";
		switch(showArrow)
		{
		default:
			showArrowS = "§9[기본 화살표 모양]";
			break;
		}
		
		Stack2("§f§l이름 변경", 386,0,1,Arrays.asList("§7네비게이션 이름을 변경합니다.","","§9[현재 이름]","§f"+naviName,""), 10, inv);
		Stack2("§f§l좌표 변경", 358,0,1,Arrays.asList("§7클릭시 현재 위치로 변경합니다.","","§9[도착 지점]","§9월드 : §f"+world,"§9좌표 : §f"+x+","+y+","+z,""), 11, inv);
		Stack2("§f§l지속 시간", 347,0,1,Arrays.asList("§7네비게이션 지속 시간을 변경합니다.","",timeS,""), 12, inv);
		Stack2("§f§l도착 반경", 420,0,1,Arrays.asList("§7도착 판정 범위를 변경합니다.","",sensitiveS,""), 13, inv);
		Stack2("§f§l사용 권한", 137,0,1,Arrays.asList("§7네비게이션 사용 권한을 설정합니다.","",permitionS,""), 14, inv);
		Stack2("§f§l네비 타입", 381,0,1,Arrays.asList("§7네비게이션 타입을 설정합니다.","",showArrowS,""), 15, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 27, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+naviUTC), 35, inv);
		player.openInventory(inv);
	}
	
	public void useNavigationGui(Player player, short page)
	{
		YamlLoader navigationYaml = new YamlLoader();
		navigationYaml.getConfig("Navigation/NavigationList.yml");

		String uniqueCode = "§0§0§1§1§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0네비 사용 : " + (page+1));

		String[] navi= navigationYaml.getConfigurationSection("").getKeys(false).toArray(new String[0]);
		
		int loc=0;
		String naviName = null;
		String world = null;
		String timeS = null;
		for(int count = page*45; count < navi.length;count++)
		{
			if(count > navi.length || loc >= 45) break;
			boolean permition = navigationYaml.getBoolean(navi[count]+".onlyOPuse");
			if(permition)
			{
				if(player.isOp())
				{
					naviName = navigationYaml.getString(navi[count]+".Name");
					world = navigationYaml.getString(navi[count]+".world");
					int x = navigationYaml.getInt(navi[count]+".x");
					short y = (short) navigationYaml.getInt(navi[count]+".y");
					int z = navigationYaml.getInt(navi[count]+".z");

					int time = navigationYaml.getInt(navi[count]+".time");
					if(time >= 0)
						timeS = "§3<"+time+"초 동안 유지>";
					else
						timeS = "§3<도착할 때 까지 유지>";
					
					Stack2("§0§l" + navi[count], 395,0,1,Arrays.asList(
					"§e§l"+naviName,"",
					"§9[설정 지점]","§9월드 : §f"+world,
					"§9좌표 : §f"+x+","+y+","+z,"",timeS,"","§e[좌 클릭시 네비 사용]"), loc, inv);
					loc++;
				}
			}
			else
			{
				naviName = navigationYaml.getString(navi[count]+".Name");
				world = navigationYaml.getString(navi[count]+".world");
				int x = navigationYaml.getInt(navi[count]+".x");
				short y = (short) navigationYaml.getInt(navi[count]+".y");
				int z = navigationYaml.getInt(navi[count]+".z");

				int time = navigationYaml.getInt(navi[count]+".time");
				if(time >= 0)
					timeS = "§3<"+time+"초 동안 유지>";
				else
					timeS = "§3<도착할 때 까지 유지>";
				
				Stack2("§0§l" + navi[count], 395,0,1,Arrays.asList(
				"§e§l"+naviName,"",
				"§9[설정 지점]","§9월드 : §f"+world,
				"§9좌표 : §f"+x+","+y+","+z,"",timeS,"","§e[좌 클릭시 네비 사용]"), loc, inv);
				loc++;
			}
		}
		
		if(navi.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void navigationListGuiClick(InventoryClickEvent event)
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
				new OPbox_GUI().opBoxGuiMain(player, (byte) 1);
			else if(slot == 48)//이전 페이지
				navigationListGui(player, (short) (page-1));
			else if(slot == 49)//새 네비
			{
				player.closeInventory();
				player.sendMessage("§a[네비] : 새로운 네비게이션 이름을 입력 해 주세요!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Navi");
				u.setString(player, (byte)0, "NN");
			}
			else if(slot == 50)//다음 페이지
				navigationListGui(player, (short) (page+1));
			else
			{
				if(event.isLeftClick())
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					navigationOptionGui(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
				else if(event.isShiftClick() && event.isRightClick())
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					YamlLoader navigationYaml = new YamlLoader();
					navigationYaml.getConfig("Navigation/NavigationList.yml");
					navigationYaml.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					navigationYaml.saveConfig();
					navigationListGui(player, page);
				}
			}
		}
	}
	
	public void navigationOptionGuiClick(InventoryClickEvent event)
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
				navigationListGui(player, (short) 0);
			else
			{
				YamlLoader navigationYaml = new YamlLoader();
				navigationYaml.getConfig("Navigation/NavigationList.yml");
				String utc = ChatColor.stripColor(event.getInventory().getItem(35).getItemMeta().getLore().get(1));

				if(slot == 11)//좌표 변경
				{
					navigationYaml.set(utc+".world", player.getLocation().getWorld().getName());
					navigationYaml.set(utc+".x", (int)player.getLocation().getX());
					navigationYaml.set(utc+".y", (int)player.getLocation().getY());
					navigationYaml.set(utc+".z", (int)player.getLocation().getZ());
					navigationYaml.saveConfig();
					navigationOptionGui(player, utc);
				}
				else if(slot == 14)//사용 권한
				{
					if(navigationYaml.getBoolean(utc+".onlyOPuse"))
						navigationYaml.set(utc+".onlyOPuse", false);
					else
						navigationYaml.set(utc+".onlyOPuse", true);
					navigationYaml.saveConfig();
					navigationOptionGui(player, utc);
				}
				else
				{
					player.closeInventory();
					UserData_Object u = new UserData_Object();
					u.setType(player, "Navi");
					u.setString(player, (byte)1, utc);
					if(slot == 10)//이름 변경
					{
						player.sendMessage("§a[네비] : 원하는 네비게이션 이름을 입력 해 주세요!");
						u.setString(player, (byte)0, "CNN");
					}
					else if(slot == 12)//지속 시간
					{
						player.sendMessage("§a[네비] : 네비게이션 지속 시간을 입력 해 주세요!");
						player.sendMessage("§e(-1초(찾을 때 까지) ~ 3600초(1시간))");
						u.setString(player, (byte)0, "CNT");
					}
					else if(slot == 13)//도착 반경
					{
						player.sendMessage("§a[네비] : 도착 판정 범위를 입력 해 주세요!");
						player.sendMessage("§e(1 ~ 1000)");
						u.setString(player, (byte)0, "CNS");
					}
					else if(slot == 15)//네비 타입
					{
						player.sendMessage("§a[네비] : 네비게이션 화살표 모양을 선택하세요!");
						player.sendMessage("§e(0 ~ 10)");
						u.setString(player, (byte)0, "CNA");
					}
				}
			}
		}
	}

	public void useNavigationGuiClick(InventoryClickEvent event)
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
				useNavigationGui(player, (short) (page-1));
			else if(slot == 50)//다음 페이지
				useNavigationGui(player, (short) (page+1));
			else if(event.isLeftClick())
			{
				for(int count = 0; count < ServerTick_Main.NaviUsingList.size();count++)
				{
					if(ServerTick_Main.NaviUsingList.get(count).equals(player.getName()))
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[네비게이션] : 당신은 이미 네비게이션을 사용 중입니다!");
						return;
					}
				}
				ServerTick_Main.NaviUsingList.add(player.getName());
				YamlLoader navigationYaml = new YamlLoader();
				navigationYaml.getConfig("Navigation/NavigationList.yml");
				String utc = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				player.closeInventory();
				SoundEffect.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.0F);
				
				servertick.ServerTick_Object tickObject = new servertick.ServerTick_Object(servertick.ServerTick_Main.nowUTC, "NV");
				tickObject.setCount(0);//횟 수 초기화
				tickObject.setMaxCount(navigationYaml.getInt(utc+".time"));//N초간 네비게이션
				//-1초 설정시, N초간이 아닌, 찾아 갈 때 까지 네비게이션 지원
				tickObject.setString((byte)1, navigationYaml.getString(utc+".world"));//목적지 월드 이름 저장
				tickObject.setString((byte)2, player.getName());//플레이어 이름 저장
				
				tickObject.setInt((byte)0, navigationYaml.getInt(utc+".x"));//목적지X 위치저장
				tickObject.setInt((byte)1, navigationYaml.getInt(utc+".y"));//목적지Y 위치저장
				tickObject.setInt((byte)2, navigationYaml.getInt(utc+".z"));//목적지Z 위치저장
				tickObject.setInt((byte)3, navigationYaml.getInt(utc+".sensitive"));//판정 범위 저장
				tickObject.setInt((byte)4, navigationYaml.getInt(utc+".ShowArrow"));//파티클 설정
				
				servertick.ServerTick_Main.Schedule.put(servertick.ServerTick_Main.nowUTC, tickObject);
				player.sendMessage("§e[네비게이션] : 길찾기 시스템이 가동됩니다!");
				player.sendMessage("§e(화살표가 보이지 않을 경우, [ESC] → [설정] → [비디오 설정] 속의 [입자]를 [모두]로 변경해 주세요!)");
			}
		}
	}
}
