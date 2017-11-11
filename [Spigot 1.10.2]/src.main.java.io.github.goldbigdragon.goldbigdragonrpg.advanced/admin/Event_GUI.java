package admin;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;



public class Event_GUI extends Util_GUI
{
	public void eventGuiMain (Player player)
	{
	  	YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");
		String uniqueCode = "§0§0§1§0§9§r";
		
		Inventory inv = Bukkit.createInventory(null, 45, uniqueCode + "§0이벤트 진행");
		
		if(configYaml.getInt("Event.Multiple_Level_Up_SkillPoint") == 1)
		{Stack2("§f§l스킬 포인트 추가 획득", 340,0,1,Arrays.asList("§c[비 활성화]","§7레벨 업당 얻는 스킬 포인트 : " + configYaml.getInt("Server.Level_Up_SkillPoint")), 10, inv);}
		else
		{Stack2("§f§l스킬 포인트 추가 획득", 403,0,configYaml.getInt("Event.Multiple_Level_Up_SkillPoint"),Arrays.asList("§a[활성화]","§e"+configYaml.getInt("Event.Multiple_Level_Up_SkillPoint") +"§7배","§7레벨 업당 얻는 스킬 포인트 : " + (configYaml.getInt("Server.Level_Up_SkillPoint") * configYaml.getInt("Event.Multiple_Level_Up_SkillPoint"))), 10, inv);}	

		if(configYaml.getInt("Event.Multiple_Level_Up_StatPoint") == 1)
		{Stack2("§f§l스텟 포인트 추가 획득", 351,15,1,Arrays.asList("§c[비 활성화]","§7레벨 업당 얻는 스텟 포인트 : " + configYaml.getInt("Server.Level_Up_StatPoint")), 11, inv);}
		else
		{Stack2("§f§l스텟 포인트 추가 획득", 399,0,configYaml.getInt("Event.Multiple_Level_Up_StatPoint"),Arrays.asList("§a[활성화]","§e"+configYaml.getInt("Event.Multiple_Level_Up_StatPoint") +"§7배","§7레벨 업당 얻는 스텟 포인트 : " + (configYaml.getInt("Server.Level_Up_StatPoint")*configYaml.getInt("Event.Multiple_Level_Up_StatPoint"))), 11, inv);}	

		if(configYaml.getInt("Event.Multiple_EXP_Get") == 1)
		{Stack2("§f§l경험치 추가 획득", 374,0,1,Arrays.asList("§c[비 활성화]","§7경험치 획득률 : " + configYaml.getInt("Event.Multiple_EXP_Get")+"배"), 12, inv);}
		else
		{Stack2("§f§l경험치 추가 획득", 384,0,configYaml.getInt("Event.Multiple_EXP_Get"),Arrays.asList("§a[활성화]","§7경험치 획득률 : §e"+configYaml.getInt("Event.Multiple_EXP_Get") +"§7배"), 12, inv);}	
		
		if(configYaml.getInt("Event.DropChance") == 1)
		{Stack2("§f§l드롭률 향상", 265,0,1,Arrays.asList("§c[비 활성화]","§7드롭률 : " + configYaml.getInt("Event.DropChance")+"배"), 13, inv);}
		else
		{Stack2("§f§l드롭률 향상", 266,0,configYaml.getInt("Event.DropChance"),Arrays.asList("§a[활성화]","§7드롭률 : §e"+configYaml.getInt("Event.DropChance") +"§7배"), 13, inv);}	

		if(configYaml.getInt("Event.Multiple_Proficiency_Get") == 1)
		{Stack2("§f§l숙련도 향상", 145,0,1,Arrays.asList("§c[비 활성화]","§7숙련도 : " + configYaml.getInt("Event.Multiple_Proficiency_Get")+"배"), 14, inv);}
		else
		{Stack2("§f§l숙련도 향상", 145,2,configYaml.getInt("Event.Multiple_Proficiency_Get"),Arrays.asList("§a[활성화]","§7숙련도 : §e"+configYaml.getInt("Event.Multiple_Proficiency_Get") +"§7배"), 14, inv);}	
		
		Stack2("§f§l전체 주기", 54,0,1,Arrays.asList("§7현재 접속한 모든 유저에게","§7아이템을 선물합니다."), 28, inv);
		Stack2("§f§l랜덤 주기", 130,0,1,Arrays.asList("§7현재 접속한 유저들 중,","§7한 사람에게만 지정된","§7아이템을 선물합니다."), 30, inv);

		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7작업 관리자 메뉴로 돌아갑니다."), 36, inv);

		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7작업 관리자 창을 닫습니다."), 44, inv);
		
		player.openInventory(inv);
	}

	public void allPlayerGiveEventGui(Player player, boolean isGiveToAll)
	{
		String uniqueCode = "§1§0§1§0§a§r";
		Inventory inv = Bukkit.createInventory(null, 45, uniqueCode + "§0이벤트 전체 지급");
		if(!isGiveToAll)
			inv = Bukkit.createInventory(null, 45, "§1§0§1§0§b§r§0이벤트 랜덤 지급");
		for(int count = 0; count <10;count++)
			Stack2("§e[ 지급 할 아이템 ]", 160, 4, 1, null, count, inv);
		Stack2("§e[ 지급 할 아이템 ]", 160, 4, 1, null, 17, inv);
		Stack2("§e[ 지급 할 아이템 ]", 160, 4, 1, null, 18, inv);
		for(int count = 26; count <36;count++)
			Stack2("§e[ 지급 할 아이템 ]", 160, 4, 1, null, count, inv);

		for(int count = 36; count <44;count++)
			Stack2("§e ", 160, 8, 1, null, count, inv);

		if(isGiveToAll)
			Stack2("§f§l[지급 개시!]", 54,0,1,Arrays.asList("§7노란 테두리 속의 아이템을","§7모든 플레이어에게 지급합니다!"), 40, inv);
		else
			Stack2("§f§l[지급 개시!]", 130,0,1,Arrays.asList("§7노란 테두리 속의 아이템을","§7누가 받을지 모릅니다!"), 40, inv);
			
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이벤트 메뉴로 돌아갑니다."), 36, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7작업 관리자 창을 닫습니다."), 44, inv);
		player.openInventory(inv);
	}
	
	
	//각종 GUI창 속의 아이콘을 눌렸을 때, 해당 아이콘에 기능을 넣는 메소드1   -스텟 GUI, 오피박스, 커스텀 몬스터GUI-//
	public void eventGuiInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 44)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 36)//이전 목록
				new OPbox_GUI().opBoxGuiMain(player,(byte) 1);
			else if(slot == 28)//전체 주기
				allPlayerGiveEventGui(player,true);
			else if(slot == 30)//랜덤 주기
				allPlayerGiveEventGui(player,false);
			else
			{
				UserData_Object u = new UserData_Object();
				if(slot == 10)
				{
					player.sendMessage("§a[이벤트] : 스킬 포인트 상승량을 몇 배로 하실래요?");
					u.setString(player, (byte)1, "SKP");
				}
				else if(slot == 11)
				{
					player.sendMessage("§a[이벤트] : 스텟 포인트 상승량을 몇 배로 하실래요?");
					u.setString(player, (byte)1, "STP");
				}
				else if(slot == 12)
				{
					player.sendMessage("§a[이벤트] : 경험치 상승량을 몇 배로 하실래요?");
					u.setString(player, (byte)1, "EXP");
				}
				else if(slot == 13)
				{
					player.sendMessage("§a[이벤트] : 드롭률 상승량을 몇 배로 하실래요?");
					u.setString(player, (byte)1, "DROP");
				}
				else if(slot == 14)
				{
					player.sendMessage("§a[이벤트] : 숙련도 상승량을 몇 배로 하실래요?");
					u.setString(player, (byte)1, "Proficiency");
				}
				player.sendMessage("§e(1 ~ 10) (1일 경우 이벤트 종료)");
				u.setType(player, "Event");
				player.closeInventory();
			}
		}
		return;
	}

	public void allPlayerGiveEventGuiClick(InventoryClickEvent event, String subjectCode)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		if(!event.getClickedInventory().getTitle().equals("container.inventory"))
		{
			if((slot >=0 && slot <= 9)||slot == 17||slot == 18||slot >= 26)
				event.setCancelled(true);
			if(slot == 36)//이전 화면
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				eventGuiMain(player);
			}
			else if(slot == 40)//지급 시작
			{
				event.Main_Interact interact = new event.Main_Interact();
				if(subjectCode.equals("0b"))//랜덤 지급
				{
					boolean itemExit = false;
	  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	  		    	Player[] a = new Player[playerlist.size()];
	  		    	playerlist.toArray(a);
	  		    	int luckyGuy = new util.Util_Number().RandomNum(0, a.length-1);
					for(int count = 10; count < 17;count++)
					{
						if(event.getInventory().getItem(count) != null)
						{
							itemExit = true;
							ItemStack item = event.getInventory().getItem(count);
							new util.Util_Player().giveItemForce(a[luckyGuy], item);
						}
					}
					for(int count = 19; count < 26;count++)
					{
						if(event.getInventory().getItem(count) != null)
						{
							itemExit = true;
							ItemStack item = event.getInventory().getItem(count);
							new util.Util_Player().giveItemForce(a[luckyGuy], item);
						}
					}
					if(itemExit)
					{
						SoundEffect.SP(a[luckyGuy], Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.9F);
						Bukkit.broadcastMessage("§e[이벤트] : §6§l"+a[luckyGuy].getName()+"§e님께서 랜덤 아이템 지급에 당첨 되셨습니다!");
						eventGuiMain(player);
					}
				}
				else
				{
					boolean itemExit = false;
					for(int count = 10; count < 17;count++)
					{
						if(event.getInventory().getItem(count) != null)
						{
							itemExit = true;
							ItemStack item = event.getInventory().getItem(count);
			  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
			  		    	Player[] a = new Player[playerlist.size()];
			  		    	playerlist.toArray(a);
			  	  			for(int counter = 0; counter<a.length;counter++)
			  	  			{
								new util.Util_Player().giveItemForce(a[counter], item);
			  	  				if(item.hasItemMeta())
			  	  				{
			  	  					if(item.getItemMeta().hasDisplayName())
			  	  						a[counter].sendMessage("§e[이벤트] : "+item.getItemMeta().getDisplayName()+"§e 아이템을 "+item.getAmount()+"개 지급 받았습니다!");
			  	  				}
			  	  				else
		  	  						a[counter].sendMessage("§e[이벤트] : "+interact.SetItemDefaultName((short) item.getTypeId(), item.getData().getData())+"§e 아이템을 "+item.getAmount()+"개 지급 받았습니다!");
			  	  				SoundEffect.SP(a[counter], Sound.ENTITY_ITEM_PICKUP, 0.7F, 1.8F);
			  	  			}
						}
					}
					for(int count = 19; count < 26;count++)
					{
						if(event.getInventory().getItem(count) != null)
						{
							itemExit = true;
							ItemStack item = event.getInventory().getItem(count);
			  		    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
			  		    	Player[] a = new Player[playerlist.size()];
			  		    	playerlist.toArray(a);
			  	  			for(int counter = 0; counter<a.length;counter++)
			  	  			{
								new util.Util_Player().giveItemForce(a[counter], item);
			  	  				if(item.hasItemMeta())
			  	  				{
			  	  					if(item.getItemMeta().hasDisplayName())
			  	  						a[counter].sendMessage("§e[이벤트] : "+item.getItemMeta().getDisplayName()+"§e 아이템을 "+item.getAmount()+"개 지급 받았습니다!");
			  	  				}
			  	  				else
		  	  						a[counter].sendMessage("§e[이벤트] : "+interact.SetItemDefaultName((short) item.getTypeId(), item.getData().getData())+"§e 아이템을 "+item.getAmount()+"개 지급 받았습니다!");
			  	  				SoundEffect.SP(a[counter], Sound.ENTITY_ITEM_PICKUP, 0.7F, 1.8F);	
			  	  			}
						}
					}
					if(itemExit)
						eventGuiMain(player);
				}
			}
			else if(slot == 44)//나가기
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
		}
	}
}
