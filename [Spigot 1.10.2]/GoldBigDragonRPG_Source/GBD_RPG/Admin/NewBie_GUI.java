package GBD_RPG.Admin;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_GUI;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class NewBie_GUI extends Util_GUI
{
	public void NewBieGUIMain(Player player)
	{
		String UniqueCode = "§0§0§1§1§7§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0초심자 옵션");

		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "기본 아이템", 54,0,1,Arrays.asList(ChatColor.GRAY + "첫 접속시 아이템을 지급합니다."), 2, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "기본 퀘스트", 386,0,1,Arrays.asList(ChatColor.GRAY + "접속 하자마자 퀘스트를 줍니다.",ChatColor.GRAY+"(일종의 튜토리얼 입니다.)","",ChatColor.DARK_AQUA+"[   기본 퀘스트   ]",ChatColor.WHITE+""+ChatColor.BOLD+NewBieYM.getString("FirstQuest"),"",ChatColor.YELLOW+"[클릭시 퀘스트를 변경합니다.]",ChatColor.RED+"[Shift + 우 클릭시 퀘스트를 삭제합니다.]"), 3, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "기본 시작 위치", 368,0,1,Arrays.asList(ChatColor.GRAY + "접속 하자마자 이동 시킵니다.","",ChatColor.DARK_AQUA+"[   시작 위치   ]",ChatColor.DARK_AQUA+" - 월드 : "+ChatColor.WHITE+""+ChatColor.BOLD+NewBieYM.getString("TelePort.World")
		,ChatColor.DARK_AQUA+" - 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+NewBieYM.getInt("TelePort.X")+","+NewBieYM.getInt("TelePort.Y")+","+NewBieYM.getInt("TelePort.Z"),"",ChatColor.YELLOW+"[클릭시 현재 위치로 등록 됩니다.]"), 4, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "가이드", 340,0,1,Arrays.asList(ChatColor.GRAY + "가이드창을 설정합니다.","",ChatColor.GRAY+"/기타",ChatColor.DARK_GRAY+"명령어를 통해 설정한",ChatColor.DARK_GRAY+"가이드를 확인하실 수 있습니다."), 5, inv);
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
		player.openInventory(inv);
	}
	
	public void NewBieSupportItemGUI(Player player)
	{
		String UniqueCode = "§1§0§1§1§8§r";
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0초심자 지원");

		Object[] a= NewBieYM.getConfigurationSection("SupportItem").getKeys(false).toArray();

		byte num = 0;
		for(byte count = 0; count < a.length;count++)
			if(NewBieYM.getItemStack("SupportItem."+count) != null)
			{
				ItemStackStack(NewBieYM.getItemStack("SupportItem."+count), num, inv);
				num++;
			}

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 46, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 47, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 48, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "기본 지원금", 266,0,1,Arrays.asList(ChatColor.GRAY + "기본적으로 지원해 주는 돈을 설정합니다.","",ChatColor.YELLOW+"[현재 지원금]",ChatColor.WHITE+""+ChatColor.BOLD+""+NewBieYM.getInt("SupportMoney")),49, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 50, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 51, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 52, inv);
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void NewBieQuestGUI(Player player, short page)
	{
		String UniqueCode = "§0§0§1§1§9§r";
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0초심자 기본퀘 선택 : " + (page+1));

		Object[] a = QuestList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = a[count].toString();
			Set<String> QuestFlow= QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false);
			if(count > a.length || loc >= 45) break;
			switch(QuestList.getString(a[count].toString() + ".Type"))
			{
				case "N" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일반 퀘스트",""), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 반복 퀘스트",""), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일일 퀘스트",""), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일주 퀘스트",""), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 한달 퀘스트",""), loc, inv);
					break;
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void NewBieGuideGUI(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");

		if(NewBieYM.contains("Guide")==false)
		{
			NewBieYM.createSection("Guide");
			NewBieYM.saveConfig();
		}

		String UniqueCode = "§1§0§1§1§a§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0초심자 가이드");

		Object[] a= NewBieYM.getConfigurationSection("Guide").getKeys(false).toArray();

		byte num = 0;
		for(short count = 0; count < a.length;count++)
			if(NewBieYM.getItemStack("Guide."+count) != null)
			{
				ItemStackStack(NewBieYM.getItemStack("Guide."+count), num, inv);
				num++;
			}

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 46, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 47, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 48, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 49, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 50, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 51, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 52, inv);
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	
	public void NewBieGUIMainInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		if(slot == 8)//닫기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			if(slot == 3)//기본 퀘스트
			{
				if(event.isLeftClick())
					NewBieQuestGUI(player, (short) 0);
				else if(event.isRightClick()&&event.isShiftClick())
				{
					s.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
					NewBieYM.set("FirstQuest", "null");
					NewBieYM.saveConfig();
					NewBieGUIMain(player);
				}
			}
			else
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				if(slot == 0)//이전 목록
					new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Main(player, (byte) 2);
				else if(slot == 2)//기본 아이템
					NewBieSupportItemGUI(player);
				else if(slot == 4)//기본 시작 위치
				{
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
					NewBieYM.set("TelePort.World", player.getLocation().getWorld().getName());
					NewBieYM.set("TelePort.X", player.getLocation().getX());
					NewBieYM.set("TelePort.Y", player.getLocation().getY());
					NewBieYM.set("TelePort.Z", player.getLocation().getZ());
					NewBieYM.set("TelePort.Pitch", player.getLocation().getPitch());
					NewBieYM.set("TelePort.Yaw", player.getLocation().getYaw());
					NewBieYM.saveConfig();
					player.sendMessage(ChatColor.GREEN+"[뉴비 텔레포트지점] : 접속시 이동 위치 변경 완료!");
					NewBieGUIMain(player);
				}
				else if(slot == 5)//가이드
					NewBieGuideGUI(player);
			}
		}
	}
	
	public void NewBieSupportItemGUIInventoryclick(InventoryClickEvent event, String SubjectCode)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		if(slot >= 45)
			event.setCancelled(true);
		if(slot == 53)//닫기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(slot == 45)//이전 목록
				NewBieGUIMain(player);
			else if(slot == 49)//기본 지원
			{
				if(SubjectCode.compareTo("1a")!=0)//초심자 가이드 설정창이 아닐 경우
				{
					s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					player.closeInventory();
					player.sendMessage(ChatColor.DARK_AQUA+"[뉴비 지원금] : 얼마를 가지고 시작하도록 하시겠습니까?");
					player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
					UserData_Object u = new UserData_Object();
					u.setType(player, "NewBie");
					u.setString(player, (byte)1, "NSM");
				}
			}
		}
	}
	
	public void NewBieQuestGUIInventoryclick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 53)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(slot == 45)//이전 목록
				NewBieGUIMain(player);
			else if(slot == 48)//이전 페이지
				NewBieQuestGUI(player, (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//다음 페이지
				NewBieQuestGUI(player, (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])));
			else
			{
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
				YamlManager QuestList=YC.getNewConfig("Quest/QuestList.yml");
				
				if(QuestList.contains(QuestName)==true)
				{
					if(QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length != 0)
					{
						NewBieYM.set("FirstQuest", QuestName);
						NewBieYM.saveConfig();
						NewBieGUIMain(player);
					}
					else
					{
						s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[뉴비 퀘스트] : 해당 퀘스트는 퀘스트 오브젝트가 존재하지 않습니다!");
					}
				}
				else
				{
					s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[뉴비 퀘스트] : 해당 퀘스트는 존재하지 않습니다!");
				}
			}
		}
	}

	public void InventoryClose_NewBie(InventoryCloseEvent event, String SubjectCode)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
		short num = 0;
		for(int count = 0; count < 45;count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(SubjectCode.compareTo("1a")==0)//가이드
					NewBieYM.set("Guide."+num, event.getInventory().getItem(count));
				else//if(SubjectCode.compareTo("18")==0)//지원 아이템
					NewBieYM.set("SupportItem."+num, event.getInventory().getItem(count));
				num++;
			}
			else
				if(SubjectCode.compareTo("1a")==0)//가이드
					NewBieYM.removeKey("Guide."+count);
				else//if(SubjectCode.compareTo("18")==0)//지원 아이템
					NewBieYM.removeKey("SupportItem."+count);
		}
		NewBieYM.saveConfig();

		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();

		if(SubjectCode.compareTo("1a")==0)//가이드
			event.getPlayer().sendMessage(ChatColor.GREEN + "[뉴비 가이드] : 성공적으로 저장 되었습니다!");
		else//if(SubjectCode.compareTo("18")==0)//지원 아이템
			event.getPlayer().sendMessage(ChatColor.GREEN + "[뉴비 아이템] : 성공적으로 저장 되었습니다!");
		s.SP((Player) event.getPlayer(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
	}
}
