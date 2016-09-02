package GoldBigDragon_RPG.GUI;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class EventGUI extends GUIutil
{
	public void EventGUI_Main (Player player)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "이벤트 진행");
		
		if(Config.getInt("Event.Multiple_Level_Up_SkillPoint") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "스킬 포인트 추가 획득", 340,0,1,Arrays.asList(ChatColor.RED + "[비 활성화]",ChatColor.GRAY + "레벨 업당 얻는 스킬 포인트 : " + Config.getInt("Server.Level_Up_SkillPoint")), 10, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "스킬 포인트 추가 획득", 403,0,Config.getInt("Event.Multiple_Level_Up_SkillPoint"),Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.YELLOW +""+Config.getInt("Event.Multiple_Level_Up_SkillPoint") +ChatColor.GRAY +"배",ChatColor.GRAY + "레벨 업당 얻는 스킬 포인트 : " + (Config.getInt("Server.Level_Up_SkillPoint") * Config.getInt("Event.Multiple_Level_Up_SkillPoint"))), 10, inv);}	}

		if(Config.getInt("Event.Multiple_Level_Up_StatPoint") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "스텟 포인트 추가 획득", 351,15,1,Arrays.asList(ChatColor.RED + "[비 활성화]",ChatColor.GRAY + "레벨 업당 얻는 스텟 포인트 : " + Config.getInt("Server.Level_Up_StatPoint")), 11, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "스텟 포인트 추가 획득", 399,0,Config.getInt("Event.Multiple_Level_Up_StatPoint"),Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.YELLOW +""+Config.getInt("Event.Multiple_Level_Up_StatPoint") +ChatColor.GRAY +"배",ChatColor.GRAY + "레벨 업당 얻는 스텟 포인트 : " + (Config.getInt("Server.Level_Up_StatPoint")*Config.getInt("Event.Multiple_Level_Up_StatPoint"))), 11, inv);}	}

		if(Config.getInt("Event.Multiple_EXP_Get") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "경험치 추가 획득", 374,0,1,Arrays.asList(ChatColor.RED + "[비 활성화]",ChatColor.GRAY + "경험치 획득률 : " + Config.getInt("Event.Multiple_EXP_Get")+"배"), 12, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "경험치 추가 획득", 384,0,Config.getInt("Event.Multiple_EXP_Get"),Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY + "경험치 획득률 : "+ChatColor.YELLOW +""+Config.getInt("Event.Multiple_EXP_Get") +ChatColor.GRAY +"배"), 12, inv);}	}
		
		if(Config.getInt("Event.DropChance") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "드롭률 향상", 265,0,1,Arrays.asList(ChatColor.RED + "[비 활성화]",ChatColor.GRAY + "드롭률 : " + Config.getInt("Event.DropChance")+"배"), 13, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "드롭률 향상", 266,0,Config.getInt("Event.DropChance"),Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY + "드롭률 : "+ChatColor.YELLOW +""+Config.getInt("Event.DropChance") +ChatColor.GRAY +"배"), 13, inv);}	}

		if(Config.getInt("Event.Multiple_Proficiency_Get") == 1)
		{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "숙련도 향상", 145,0,1,Arrays.asList(ChatColor.RED + "[비 활성화]",ChatColor.GRAY + "숙련도 : " + Config.getInt("Event.Multiple_Proficiency_Get")+"배"), 14, inv);}
		else
		{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "숙련도 향상", 145,2,Config.getInt("Event.Multiple_Proficiency_Get"),Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY + "숙련도 : "+ChatColor.YELLOW +""+Config.getInt("Event.Multiple_Proficiency_Get") +ChatColor.GRAY +"배"), 14, inv);}	}
		
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "전체 주기", 54,0,1,Arrays.asList(ChatColor.GRAY + "현재 접속한 모든 유저에게",ChatColor.GRAY+"아이템을 선물합니다."), 28, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "랜덤 주기", 130,0,1,Arrays.asList(ChatColor.GRAY + "현재 접속한 유저들 중,",ChatColor.GRAY+"한 사람에게만 지정된",ChatColor.GRAY+"아이템을 선물합니다."), 30, inv);

		
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 메뉴로 돌아갑니다."), 36, inv);

		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 44, inv);
		
		player.openInventory(inv);
	}

	public void AllPlayerGiveEventGUI(Player player, boolean All)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "이벤트 전체 지급");
		if(All==false)
			inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "이벤트 랜덤 지급");
		for(byte count = 0; count <10;count++)
			Stack2(ChatColor.YELLOW+"[ 지급 할 아이템 ]", 160, 4, 1, null, count, inv);
		Stack2(ChatColor.YELLOW+"[ 지급 할 아이템 ]", 160, 4, 1, null, 17, inv);
		Stack2(ChatColor.YELLOW+"[ 지급 할 아이템 ]", 160, 4, 1, null, 18, inv);
		for(byte count = 26; count <36;count++)
			Stack2(ChatColor.YELLOW+"[ 지급 할 아이템 ]", 160, 4, 1, null, count, inv);

		for(byte count = 36; count <44;count++)
			Stack2(ChatColor.YELLOW+" ", 160, 8, 1, null, count, inv);

		if(All)
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "[지급 개시!]", 54,0,1,Arrays.asList(ChatColor.GRAY + "노란 테두리 속의 아이템을",ChatColor.GRAY+"모든 플레이어에게 지급합니다!"), 40, inv);
		else
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "[지급 개시!]", 130,0,1,Arrays.asList(ChatColor.GRAY + "노란 테두리 속의 아이템을",ChatColor.GRAY+"누가 받을지 모릅니다!"), 40, inv);
			
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이벤트 메뉴로 돌아갑니다."), 36, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 44, inv);
		player.openInventory(inv);
	}
	
	
	
	
	//각종 GUI창 속의 아이콘을 눌렸을 때, 해당 아이콘에 기능을 넣는 메소드1   -스텟 GUI, 오피박스, 커스텀 몬스터GUI-//
	public void EventGUIInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 28://전체 주기
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			AllPlayerGiveEventGUI(player,true);
			return;
		case 30://랜덤 주기
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			AllPlayerGiveEventGUI(player,false);
			return;
		case 10:
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[이벤트] : 스킬 포인트 상승량을 몇 배로 하실래요?");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1일 경우 이벤트 종료)");
				u.setType(player, "Event");
				u.setString(player, (byte)1, "SKP");
			}
			return;
		case 11:
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[이벤트] : 스텟 포인트 상승량을 몇 배로 하실래요?");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1일 경우 이벤트 종료)");
				u.setType(player, "Event");
				u.setString(player, (byte)1, "STP");
			}
			return;
		case 12:
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[이벤트] : 경험치 상승량을 몇 배로 하실래요?");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1일 경우 이벤트 종료)");
				u.setType(player, "Event");
				u.setString(player, (byte)1, "EXP");
			}
			return;
		case 13:
			{
				Object_UserData u = new Object_UserData();
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN+"[이벤트] : 경험치 상승량을 몇 배로 하실래요?");
				player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1일 경우 이벤트 종료)");
				u.setType(player, "Event");
				u.setString(player, (byte)1, "DROP");
			}
			return;
		case 14:
		{
			Object_UserData u = new Object_UserData();
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.GREEN+"[이벤트] : 숙련도 상승량을 몇 배로 하실래요?");
			player.sendMessage(ChatColor.YELLOW+"(1 ~ 10) (1일 경우 이벤트 종료)");
			u.setType(player, "Event");
			u.setString(player, (byte)1, "Proficiency");
		}
		return;
		case 36:
			OPBoxGUI OGUI = new OPBoxGUI();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OGUI.OPBoxGUI_Main(player,(byte) 1);
			return;
		case 44:
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		return;
	}

	public void AllPlayerGiveEventGUIclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		Player player = (Player) event.getWhoClicked();
		if(event.getSlot()>=0&&event.getSlot()<=9)
			event.setCancelled(true);
		if(event.getSlot()>=27&&event.getSlot()<=35)
			event.setCancelled(true);
		if(event.getSlot()>=35)
			event.setCancelled(true);
		switch (event.getSlot())
		{
		case 17:
		case 18:
		case 26:
			event.setCancelled(true);
			break;
		case 36:
			event.setCancelled(true);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			EventGUI_Main(player);
			return;
		case 40://지급 시작
			event.setCancelled(true);
			GoldBigDragon_RPG.Event.Interact IT = new GoldBigDragon_RPG.Event.Interact();
			if(event.getInventory().getTitle().contains("랜덤"))
			{
				boolean ItemExit = false;
  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
  		    	Player[] a = new Player[playerlist.size()];
  		    	playerlist.toArray(a);
  		    	short LuckyGuy = (short) new GoldBigDragon_RPG.Util.Number().RandomNum(0, a.length-1);
				for(byte count = 10; count < 17;count++)
				{
					if(event.getInventory().getItem(count) != null)
					{
						ItemExit = true;
						ItemStack item = event.getInventory().getItem(count);
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(a[LuckyGuy], item);
					}
				}
				for(byte count = 19; count < 26;count++)
				{
					if(event.getInventory().getItem(count) != null)
					{
						ItemExit = true;
						ItemStack item = event.getInventory().getItem(count);
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(a[LuckyGuy], item);
					}
				}
				if(ItemExit)
				{
					s.SP(a[LuckyGuy], Sound.ITEM_PICKUP, 1.0F, 1.9F);
					Bukkit.broadcastMessage(ChatColor.YELLOW+"[이벤트] : "+ChatColor.GOLD+""+ChatColor.BOLD+a[LuckyGuy].getName()+ChatColor.YELLOW+"님께서 랜덤 아이템 지급에 당첨 되셨습니다!");
					EventGUI_Main(player);
				}
			}
			else
			{
				boolean ItemExit = false;
				for(byte count = 10; count < 17;count++)
				{
					if(event.getInventory().getItem(count) != null)
					{
						ItemExit = true;
						ItemStack item = event.getInventory().getItem(count);
		  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
		  		    	Player[] a = new Player[playerlist.size()];
		  		    	playerlist.toArray(a);
		  	  			for(short counter = 0; counter<a.length;counter++)
		  	  			{
							new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(a[counter], item);
		  	  				if(item.hasItemMeta())
		  	  				{
		  	  					if(item.getItemMeta().hasDisplayName())
		  	  						a[counter].sendMessage(ChatColor.YELLOW+"[이벤트] : "+item.getItemMeta().getDisplayName()+ChatColor.YELLOW+" 아이템을 "+item.getAmount()+"개 지급 받았습니다!");
		  	  				}
		  	  				else
	  	  						a[counter].sendMessage(ChatColor.YELLOW+"[이벤트] : "+IT.SetItemDefaultName((short) item.getTypeId(), item.getData().getData())+ChatColor.YELLOW+" 아이템을 "+item.getAmount()+"개 지급 받았습니다!");
		  	  				s.SP(a[counter], Sound.ITEM_PICKUP, 0.7F, 1.8F);
		  	  			}
					}
				}
				for(byte count = 19; count < 26;count++)
				{
					if(event.getInventory().getItem(count) != null)
					{
						ItemExit = true;
						ItemStack item = event.getInventory().getItem(count);
		  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
		  		    	Player[] a = new Player[playerlist.size()];
		  		    	playerlist.toArray(a);
		  	  			for(short counter = 0; counter<a.length;counter++)
		  	  			{
							new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(a[counter], item);
		  	  				if(item.hasItemMeta())
		  	  				{
		  	  					if(item.getItemMeta().hasDisplayName())
		  	  						a[counter].sendMessage(ChatColor.YELLOW+"[이벤트] : "+item.getItemMeta().getDisplayName()+ChatColor.YELLOW+" 아이템을 "+item.getAmount()+"개 지급 받았습니다!");
		  	  				}
		  	  				else
	  	  						a[counter].sendMessage(ChatColor.YELLOW+"[이벤트] : "+IT.SetItemDefaultName((short) item.getTypeId(), item.getData().getData())+ChatColor.YELLOW+" 아이템을 "+item.getAmount()+"개 지급 받았습니다!");
		  	  				s.SP(a[counter], Sound.ITEM_PICKUP, 0.7F, 1.8F);	
		  	  			}
					}
				}
				if(ItemExit)
					EventGUI_Main(player);
			}
			return;
		case 44:
			event.setCancelled(true);
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
}
