package GoldBigDragon_RPG.GUI;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class NewBieGUI extends GUIutil
{
	public void NewBieGUIMain(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "초심자 옵션");

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
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
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "초심자 지원");

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
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "초심자 기본퀘 선택 : " + (page+1));

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
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");

		if(NewBieYM.contains("Guide")==false)
		{
			NewBieYM.set("Guide.1", null);
			NewBieYM.saveConfig();
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "초심자 가이드");

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
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		switch (event.getSlot())
		{
		case 0:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			GoldBigDragon_RPG.GUI.OPBoxGUI OGUI = new GoldBigDragon_RPG.GUI.OPBoxGUI();
			OGUI.OPBoxGUI_Main(player, (byte) 2);
			return;
		case 2://기본 아이템
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NewBieSupportItemGUI(player);
			return;
		case 3: //기본 퀘스트
			if(event.isLeftClick())
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				NewBieQuestGUI(player, (short) 0);
			}
			else if(event.isRightClick()&&event.isShiftClick())
			{
				s.SP(player, Sound.LAVA_POP, 1.0F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
				NewBieYM.set("FirstQuest", "null");
				NewBieYM.saveConfig();
				NewBieGUIMain(player);
			}
			return;
		case 4: //기본 시작 위치
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
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
			return;
		case 5://가이드
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NewBieGuideGUI(player);
			return;
		case 8:
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.0F);
			player.closeInventory();
			return;
		}
	}
	
	public void NewBieSupportItemGUIInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 45:
			event.setCancelled(true);
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NewBieGUIMain(player);
			return;
		case 49://기본 지원
			event.setCancelled(true);
			if(!ChatColor.stripColor(event.getInventory().getTitle()).equals("초심자 가이드"))
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[뉴비 지원금] : 얼마를 가지고 시작하도록 하시겠습니까?");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				Object_UserData u = new Object_UserData();
				u.setType(player, "NewBie");
				u.setString(player, (byte)1, "NSM");
			}
			return;
		case 46:
		case 47:
		case 48:
		case 50:
		case 51:
		case 52:
			event.setCancelled(true);
			return;
		case 53:
			event.setCancelled(true);
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.0F);
			player.closeInventory();
			return;
		}
	}
	
	public void NewBieQuestGUIInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		switch (event.getSlot())
		{
		case 45:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NewBieGUIMain(player);
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NewBieQuestGUI(player, (short) (page-1));
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			NewBieQuestGUI(player, (short) (page+1));
			return;
		case 54:
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.0F);
			player.closeInventory();
			return;
		default:
			String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
			YamlManager QuestList=YC.getNewConfig("Quest/QuestList.yml");
			
			if(QuestList.contains(QuestName)==true)
			{
				if(QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length != 0)
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					NewBieYM.set("FirstQuest", QuestName);
					NewBieYM.saveConfig();
					NewBieGUIMain(player);
				}
				else
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[뉴비 퀘스트] : 해당 퀘스트는 퀘스트 오브젝트가 존재하지 않습니다!");
				}
			}
			else
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[뉴비 퀘스트] : 해당 퀘스트는 존재하지 않습니다!");
			}
			
		}
	}

	public void InventoryClose_NewBie(InventoryCloseEvent event)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
		short num = 0;
		for(int count = 0; count < 45;count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(event.getInventory().getTitle().contains("가이드"))
					NewBieYM.set("Guide."+num, event.getInventory().getItem(count));
				else
					NewBieYM.set("SupportItem."+num, event.getInventory().getItem(count));
				num++;
			}
			else
				if(event.getInventory().getTitle().contains("가이드"))
					NewBieYM.removeKey("Guide."+count);
				else
					NewBieYM.removeKey("SupportItem."+count);
		}
		NewBieYM.saveConfig();

		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		if(event.getInventory().getTitle().contains("가이드"))
			event.getPlayer().sendMessage(ChatColor.GREEN + "[뉴비 가이드] : 성공적으로 저장 되었습니다!");
		else
			event.getPlayer().sendMessage(ChatColor.GREEN + "[뉴비 아이템] : 성공적으로 저장 되었습니다!");
		s.SP((Player) event.getPlayer(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
	}
}
