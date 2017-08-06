package user;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import admin.Navigation_GUI;
import effect.SoundEffect;
import skill.UserSkill_GUI;
import util.Util_GUI;
import util.YamlLoader;



public final class ETC_GUI extends Util_GUI
{
	public void ETCGUI_Main(Player player)
	{
		String UniqueCode = "§0§0§0§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0기타");

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
		String UniqueCode = "§0§0§0§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0가이드");
	  	YamlLoader NewBieYM = new YamlLoader();
		NewBieYM.getConfig("ETC/NewBie.yml");

		if(NewBieYM.contains("Guide")==false)
		{
			NewBieYM.createSection("Guide");
			NewBieYM.saveConfig();
		}
		Object[] a= NewBieYM.getConfigurationSection("Guide").getKeys(false).toArray();

		byte loc = 0;
		for(int count = 0; count < a.length;count++)
			if(NewBieYM.getItemStack("Guide."+count) != null)
			{
				ItemStackStack(NewBieYM.getItemStack("Guide."+count), loc, inv);
				loc++;
			}
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void FriendsGUI(Player player, short page)
	{
		String UniqueCode = "§0§0§0§0§4§r";
	  	YamlLoader FriendsList = new YamlLoader();
		FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
	  	YamlLoader SideFriendsList = new YamlLoader();
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.createSection("Friends");
			FriendsList.createSection("Waitting");
			FriendsList.saveConfig();
		}
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0친구 목록 : " + (page+1));

		if(FriendsList.getConfigurationSection("Waitting").getKeys(false).size()!=0)
			Stack("§f§l친구 요청", 386,0,1,Arrays.asList(ChatColor.GRAY + "친구 요청이 들어 온 상태입니다!","",ChatColor.DARK_AQUA+"[   대기중인 요청   ]",ChatColor.WHITE+""+ChatColor.BOLD+FriendsList.getConfigurationSection("Waitting").getKeys(false).size()+ChatColor.DARK_AQUA+" 건"), 52, inv);
		
		Object[] Friends= FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
		byte loc=0;
		Long nowTime = new util.ETC().getNowUTC();
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
				SideFriendsList.getConfig("Friend/"+target.getUniqueId().toString()+".yml");
				Object[] SideFriends = SideFriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
				for(int counter=0;counter<SideFriends.length;counter++)
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
		Stack("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack("§f§l새 친구", 397,3,1,Arrays.asList(ChatColor.GRAY + "새로운 친구를 추가합니다."), 49, inv);
		Stack("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void WaittingFriendsGUI(Player player, short page)
	{
	  	YamlLoader FriendsList = new YamlLoader();
		FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.createSection("Friends");
			FriendsList.createSection("Waitting");
			FriendsList.saveConfig();
		}
		String UniqueCode = "§0§0§0§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0친구 요청 : " + (page+1));

		Object[] Friends= FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();
		byte loc=0;
		Long nowTime = new util.ETC().getNowUTC();
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
		Stack("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	public void ETCInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)
				new user.Stats_GUI().StatusGUI(player);
			else if(slot == 9)
				new UserSkill_GUI().MainSkillsListGUI(player, (short) 0);
			else if(slot == 18)
				new quest.Quest_GUI().MyQuestListGUI(player, (short) 0);
			else if(slot == 27)
				new user.Option_GUI().optionGUI(player);
			else if(slot == 36)
				new ETC_GUI().ETCGUI_Main(player);
			else
			{
				if(slot == 2)//가이드
					Information(player);
				else if(slot ==3)//파티
					new party.Party_GUI().PartyGUI_Main(player);
				else if(slot ==4)//워프
					new warp.Warp_GUI().WarpListGUI(player, 0);
				else if(slot ==5)//친구
					FriendsGUI(player, (short) 0);
				else if(slot ==6)//네비게이션
					new Navigation_GUI().UseNavigationGUI(player, (short) 0);
			}
		}
		return;
	}

	public void GuideInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 45)//이전 목록
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			ETCGUI_Main(player);
		}
		else if(slot == 53)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 36)//유튜브 동영상
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			player.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"[YouTube] "+"§f§l: "+"§9§lhttps://www.youtube.com/playlist?list=PLxqihkJXVJABIlxU3n6bNhhC8x6xPbORP   "+"§e§l[클릭시 가이드 페이지로 연결됩니다]");
			player.closeInventory();
		}
		return;
	}
	
	public void FriendsGUIclick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		int slot = event.getSlot();
		
		if(slot == 53)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				ETCGUI_Main(player);
			else if(slot == 52)//친구 요청
				WaittingFriendsGUI(player, (short) 0);
			else if(slot == 48)//이전 페이지
				FriendsGUI(player, (short) (page-1));
			else if(slot == 49)//새 친구
			{
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[친구] : 친구 요청을 하실 상대방의 닉네임을 입력 하세요!");
				new UserData_Object().setTemp(player, "FA");
			}
			else if(slot == 50)//다음 페이지
				FriendsGUI(player, (short) (page+1));
			else if(event.isShiftClick()&&event.isRightClick())
			{
				String FName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			  	YamlLoader FriendsList = new YamlLoader();
				FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
				FriendsList.removeKey("Friends."+FName);
				FriendsList.saveConfig();
				SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 0.7F);
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[친구] : "+ChatColor.YELLOW+FName+ChatColor.LIGHT_PURPLE+"님을 친구 목록에서 삭제하였습니다!");
				FriendsGUI(player, (short) page);
			}
		}
	}

	public void WaittingFriendsGUIclick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		int slot = event.getSlot();
		
		if(slot == 53)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				FriendsGUI(player, (short) 0);
			else if(slot == 48)//이전 페이지
				WaittingFriendsGUI(player, (short) (page-1));
			else if(slot == 50)//다음 페이지
				WaittingFriendsGUI(player, (short) (page+1));
			else
			{
				String FName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			  	YamlLoader FriendsList = new YamlLoader();
				FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
				if(event.isShiftClick()&&event.isRightClick())
				{
					FriendsList.removeKey("Waitting."+FName);
					FriendsList.saveConfig();
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 0.7F);
				}
				else if(event.isLeftClick()&&!event.isShiftClick())
					new user.Equip_GUI().SetFriends(player, Bukkit.getPlayer(FName));
				FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
				if(FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray().length == 0)
					FriendsGUI(player, (short) 0);
				else
					WaittingFriendsGUI(player, (short) page);
			}
		}
	}
}
