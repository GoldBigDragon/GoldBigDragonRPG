package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Skill.PlayerSkillGUI;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public final class ETCGUI extends GUIutil
{
	private GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	
	public void ETCGUI_Main(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "기타");

		Stack2(ChatColor.WHITE + "스텟", 397,3,1,Arrays.asList(ChatColor.GRAY + "스텟을 확인합니다."), 0, inv);
		Stack2(ChatColor.WHITE + "스킬", 403,0,1,Arrays.asList(ChatColor.GRAY + "스킬을 확인합니다."), 9, inv);
		Stack2(ChatColor.WHITE + "퀘스트", 358,0,1,Arrays.asList(ChatColor.GRAY + "현재 진행중인 퀘스트를 확인합니다."), 18, inv);
		Stack2(ChatColor.WHITE + "옵션", 145,0,1,Arrays.asList(ChatColor.GRAY + "기타 설정을 합니다."), 27, inv);
		Stack2(ChatColor.WHITE + "기타", 160,4,1,Arrays.asList(ChatColor.GRAY + "기타 내용을 확인합니다."), 36, inv);
		
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 1, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 7, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 10, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 16, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 19, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 25, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 28, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 34, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 37, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 43, inv);

		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "가이드", 340,0,1,Arrays.asList(ChatColor.GRAY + "서버에 대한 내용을 알아봅니다."), 2, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "파티", 389,0,1,Arrays.asList(ChatColor.GRAY + "파티에 대한 내용을 확인합니다."), 3, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "워프", 345,0,1,Arrays.asList(ChatColor.GRAY + "워프 가능한 지역을 확인합니다."), 4, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "친구", 397,3,1,Arrays.asList(ChatColor.GRAY + "친구 목록을 확인합니다."), 5, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "네비게이션", 358,3,1,Arrays.asList(ChatColor.GRAY + "서버에 설정된 네비게이션을",ChatColor.GRAY+"동작 시킵니다."), 6, inv);
		
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 26, inv);

		player.openInventory(inv);
	}

	public void Information(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "가이드");
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");

		if(NewBieYM.contains("Guide")==false)
		{
			NewBieYM.set("Guide.1", null);
			NewBieYM.saveConfig();
		}
		Object[] a= NewBieYM.getConfigurationSection("Guide").getKeys(false).toArray();

		byte loc = 0;
		for(short count = 0; count < a.length;count++)
			if(NewBieYM.getItemStack("Guide."+count) != null)
			{
				ItemStackStack(NewBieYM.getItemStack("Guide."+count), loc, inv);
				loc++;
			}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void FriendsGUI(Player player, short page)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
		
		YamlManager SideFriendsList  = null;
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.set("Friends.1", null);
			FriendsList.set("Waitting.1", null);
			FriendsList.saveConfig();
		}
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "친구 목록 : " + (page+1));

		if(FriendsList.getConfigurationSection("Waitting").getKeys(false).size()!=0)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "친구 요청", 386,0,1,Arrays.asList(ChatColor.GRAY + "친구 요청이 들어 온 상태입니다!","",ChatColor.DARK_AQUA+"[   대기중인 요청   ]",ChatColor.WHITE+""+ChatColor.BOLD+FriendsList.getConfigurationSection("Waitting").getKeys(false).size()+ChatColor.DARK_AQUA+" 건"), 52, inv);
		
		Object[] Friends= FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
		byte loc=0;
		Long nowTime = new GoldBigDragon_RPG.Util.ETC().getNowUTC();
		for(int count = page*45; count < Friends.length;count++)
		{
			if(loc >= 45) break;
			Player target = (Player) Bukkit.getServer().getPlayer(Friends[count].toString());
			Long AcceptedTime = FriendsList.getLong("Friends."+Friends[count].toString());
			Long WaitingTime = (nowTime-AcceptedTime)/1000;
			byte day = 0;
			
			day = (byte) (WaitingTime/86400);
			WaitingTime = WaitingTime-(86400*day);

			String TimeSetting=day+"일 째 친구 관계 유지 중";
			
			if(target!=null)
			{
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+target.getName(), 166, 0, 1, Arrays.asList(ChatColor.GRAY+"[   오프라인   ]","",ChatColor.RED+"[Shift + 우 클릭시 친구 삭제]","","",ChatColor.GOLD+TimeSetting), loc, inv);
				SideFriendsList = YC.getNewConfig("Friend/"+target.getUniqueId().toString()+".yml");
				Object[] SideFriends = SideFriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
				for(short counter=0;counter<SideFriends.length;counter++)
				{
					if(SideFriends[counter].equals(player.getName()))
						if(target.isOnline())
						{
							ItemStackStack(getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+target.getName(), 1, Arrays.asList(ChatColor.DARK_AQUA+"[   온라인   ]","",ChatColor.DARK_AQUA+"[월드] : "+ChatColor.WHITE+target.getLocation().getWorld().getName(),
							ChatColor.DARK_AQUA+"[좌표] : "+ChatColor.WHITE+""+(int)target.getLocation().getX()+","+(int)target.getLocation().getY()+","+(int)target.getLocation().getZ(),
							"",ChatColor.RED+"[Shift + 우 클릭시 친구 삭제]","","",ChatColor.GOLD+TimeSetting), target.getName()), loc, inv);
							break;
						}
				}
			}
			else
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+Friends[count].toString(), 166, 0, 1, Arrays.asList(ChatColor.GRAY+"[   오프라인   ]","",ChatColor.RED+"[Shift + 우 클릭시 친구 삭제]","","",ChatColor.GOLD+TimeSetting), loc, inv);
			
			loc++;
		}
		
		if(Friends.length-(page*44)>45)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "새 친구", 397,3,1,Arrays.asList(ChatColor.GRAY + "새로운 친구를 추가합니다."), 49, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void WaittingFriendsGUI(Player player, short page)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.set("Friends.1", null);
			FriendsList.set("Waitting.1", null);
			FriendsList.saveConfig();
		}
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "친구 요청 : " + (page+1));

		Object[] Friends= FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();
		byte loc=0;
		Long nowTime = new GoldBigDragon_RPG.Util.ETC().getNowUTC();
		for(int count = page*45; count < Friends.length;count++)
		{
			if(loc >= 45) break;
			Player target = (Player) Bukkit.getServer().getPlayer(Friends[count].toString());
			Long AcceptedTime = FriendsList.getLong("Waitting."+Friends[count].toString());
			Long WaitingTime = (nowTime-AcceptedTime)/1000;
			byte day = 0;
			byte week = 0;
			String TimeSetting=null;
			
			if(WaitingTime >= 2419200)
				TimeSetting = "오래 전";
			else
			{
				week = (byte)(WaitingTime/604800);
				WaitingTime = WaitingTime-(604800*week);

				day = (byte) (WaitingTime/86400);
				WaitingTime = WaitingTime-(86400*day);

				if(week>0)
					TimeSetting = week+"주 전";
				else if(day>=0)
					if(day==0)
						TimeSetting = "오늘";
					else
						TimeSetting = day+"일 전";
			}
			if(TimeSetting == null)
				TimeSetting = "알 수 없음";
			
			if(target!=null)
			{
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+target.getName(), 166, 0, 1, Arrays.asList(ChatColor.GRAY+"[   오프라인   ]","",ChatColor.YELLOW+"[좌 클릭시 친구 수락]",ChatColor.RED+"[Shift + 우 클릭시 거절]","","",ChatColor.GOLD+"신청일 : "+TimeSetting), loc, inv);
				if(target.isOnline())
				{
					ItemStackStack(getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+target.getName(), 1, Arrays.asList(ChatColor.DARK_AQUA+"[   온라인   ]","",ChatColor.YELLOW+"[좌 클릭시 친구 수락]",ChatColor.RED+"[Shift + 우 클릭시 거절]","","",ChatColor.GOLD+"신청일 : "+TimeSetting), target.getName()), loc, inv);
					break;
				}
			}
			else
				Stack2(ChatColor.YELLOW+""+ChatColor.BOLD+Friends[count].toString(), 166, 0, 1, Arrays.asList(ChatColor.GRAY+"[   오프라인   ]","",ChatColor.YELLOW+"[좌 클릭시 친구 수락]",ChatColor.RED+"[Shift + 우 클릭시 거절]","","",ChatColor.GOLD+"신청일 : "+TimeSetting), loc, inv);
			
			loc++;
		}
		
		if(Friends.length-(page*44)>45)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	
	public void ETCInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase("가이드") == true)
		{
			switch (event.getSlot())
			{
				case 45://이전 목록
					ETCGUI_Main(player);
					break;
				case 53://닫기
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
					player.closeInventory();
					break;
				case 36:
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					player.closeInventory();
					event.setCancelled(true);
					player.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"[YouTube] "+ChatColor.WHITE+""+ChatColor.BOLD+": "+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"https://www.youtube.com/playlist?list=PLxqihkJXVJABIlxU3n6bNhhC8x6xPbORP   "+ChatColor.YELLOW+""+ChatColor.BOLD+"[클릭시 가이드 페이지로 연결됩니다]");
					break;
					default : return;
			}
			return;
		}
		switch (event.getSlot())
		{
		case 0://스텟
			GoldBigDragon_RPG.GUI.StatsGUI stat = new GoldBigDragon_RPG.GUI.StatsGUI();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			stat.StatusGUI(player);
			break;
		case 27://옵션
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OptionGUI OGUI = new OptionGUI();
			OGUI.optionGUI(player);
			break;
		case 9://스킬
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			PlayerSkillGUI PSGUI = new PlayerSkillGUI();
			PSGUI.MainSkillsListGUI(player, (short) 0);
			break;
		case 18://퀘스트
			GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
			QGUI.MyQuestListGUI(player, (short) 0);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			break;
		case 2://가이드
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			Information(player);
			break;
		case 3://파티
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.Party.PartyGUI().PartyGUI_Main(player);
			break;
		case 4://워프
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.GUI.WarpGUI().WarpListGUI(player, 0);
			break;
		case 5://친구
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FriendsGUI(player, (short) 0);
			break;
		case 6://네비게이션
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new NavigationGUI().UseNavigationGUI(player, (short) 0);
			break;
		case 26://닫기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
			default: return;
		}
			return;
	}
	
	public void FriendsGUIclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			ETCGUI_Main(player);
			return;
		case 52://친구 요청
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			WaittingFriendsGUI(player, (short) 0);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FriendsGUI(player, (short) (page-1));
			return;
		case 49://새 친구
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[친구] : 친구 요청을 하실 상대방의 닉네임을 입력 하세요!");
				new Object_UserData().setTemp(player, "FA");
			}
		return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FriendsGUI(player, (short) (page+1));
			return;
		default:
			if(event.isShiftClick()&&event.isRightClick())
			{
				String FName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
				FriendsList.removeKey("Friends."+FName);
				FriendsList.saveConfig();
				s.SP(player, Sound.LAVA_POP, 1.0F, 0.7F);
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[친구] : "+ChatColor.YELLOW+FName+ChatColor.LIGHT_PURPLE+"님을 친구 목록에서 삭제하였습니다!");
				FriendsGUI(player, (short) page);
				return;
			}
		}
	}

	public void WaittingFriendsGUIclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			FriendsGUI(player, (short) 0);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			WaittingFriendsGUI(player, (short) (page-1));
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			WaittingFriendsGUI(player, (short) (page+1));
			return;
		default:
			String FName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
			if(event.isShiftClick()&&event.isRightClick())
			{
				FriendsList.removeKey("Waitting."+FName);
				FriendsList.saveConfig();
				s.SP(player, Sound.LAVA_POP, 1.0F, 0.7F);
			}
			else if(event.isLeftClick()&&!event.isShiftClick())
			{
				new GoldBigDragon_RPG.GUI.EquipGUI().SetFriends(player, Bukkit.getPlayer(FName));
			}
			FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
			if(FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray().length == 0)
				FriendsGUI(player, (short) 0);
			else
				WaittingFriendsGUI(player, (short) page);
			break;
		}
	}
}
