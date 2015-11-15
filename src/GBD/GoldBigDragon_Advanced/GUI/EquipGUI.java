package GBD.GoldBigDragon_Advanced.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class EquipGUI extends GUIutil
{
	public void EquipWatchGUI(Player player, Player target)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "장비 구경");
		
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
	    
	  	if(GUI_YC.isExit("Stats/" + target.getUniqueId()+".yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.StatConfig().CreateNewStats(target);
	  	YamlManager Target = GUI_YC.getNewConfig("Stats/" + target.getUniqueId()+".yml");
	  	
	  	if(Target.getBoolean("Option.EquipLook") == true)
	  	{
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 0, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 1, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 2, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 3, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 4, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 9, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 18, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 27, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 36, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 45, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 46, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 47, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 48, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 49, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 40, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 31, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 22, inv);
			Stack(ChatColor.DARK_AQUA + "[    장비    ]", 160,7,1,null, 13, inv);
			
			
	  		if(target.getInventory().getHelmet() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "미착용", 302,0,1,null, 11, inv);
	  		else
	  			ItemStackStack(target.getInventory().getHelmet(), 11, inv);
	  		if(target.getInventory().getChestplate() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "미착용", 303,0,01,null, 20, inv);
	  		else
	  			ItemStackStack(target.getInventory().getChestplate(), 20, inv);
	  		if(target.getInventory().getLeggings() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "미착용", 304,0,1,null, 29, inv);
	  		else
	  			ItemStackStack(target.getInventory().getLeggings(), 29, inv);
	  		if(target.getInventory().getBoots() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "미착용", 305,0,1,null, 38, inv);
	  		else
	  			ItemStackStack(target.getInventory().getBoots(), 38, inv);
	  		if(target.getInventory().getItemInHand() == null)
	  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "맨손", 336,0,1,null, 28, inv);
	  		else
	  			if(target.getInventory().getItemInHand().getType() == Material.AIR)
		  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "맨손", 336,0,1,null, 28, inv);
	  			else
	  				ItemStackStack(target.getInventory().getItemInHand(), 28, inv);
	  	}
	  	else
	  	{
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 0, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 1, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 2, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 3, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 4, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 9, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 18, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 27, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 36, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 45, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 46, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 47, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 48, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 49, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 40, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 31, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 22, inv);
			Stack(ChatColor.RED + "[    비공개    ]", 160,14,1,null, 13, inv);
			
	  		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "알수없음", 302,0,1,null, 11, inv);
  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "알수없음", 303,0,1,null, 20, inv);
  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "알수없음", 304,0,1,null, 29, inv);
  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "알수없음", 305,0,1,null, 38, inv);
  			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "알수없음", 336,0,1,null, 28, inv);
	  	}

		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 5, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 6, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 7, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 8, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 14, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 17, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 23, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 26, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 32, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 35, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 41, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 44, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 50, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 51, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 52, inv);
		Stack(ChatColor.DARK_AQUA + "[    행동    ]", 160,9,1,null, 53, inv);
		
	  	Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "교환 신청", 388,0,1,Arrays.asList(
	  			ChatColor.RED+"[GoldBigDragonRPG 버전 업데이트 필요]",ChatColor.RED+"현재 버전 : 프로토타입 1.0"), 15, inv);
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "친구 추가", 397,3,1,Arrays.asList(
				ChatColor.GRAY+"친구 추가 요청을 합니다.","",ChatColor.YELLOW+"[    대상    ]",ChatColor.WHITE+""+ChatColor.BOLD+target.getName()), 16, inv);
		player.openInventory(inv);
	}
	
	
	
	
	
	public void optionInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		Player target = (Player) Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getInventory().getItem(16).getItemMeta().getLore().get(3)));

		switch(event.getSlot())
		{
		case 16:
			SetFriends(player, target);
			break;
		}
	}

	public void SetFriends(Player player, Player target)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager FriendsList  = GUI_YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.set("Friends.1", null);
			FriendsList.set("Waitting.1", null);
			FriendsList.saveConfig();
		}
		YamlManager SideFriendsList  = GUI_YC.getNewConfig("Friend/"+target.getUniqueId().toString()+".yml");
		if(SideFriendsList.contains("Name")==false)
		{
			SideFriendsList.set("Name", target.getName());
			SideFriendsList.set("Friends.1", null);
			SideFriendsList.set("Waitting.1", null);
			SideFriendsList.saveConfig();
		}

		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Long AddTime = new GBD.GoldBigDragon_Advanced.Util.ETC().getSec();
		Object[] Friend = FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();
		Object[] SideFriend = SideFriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();

		for(int counter= 0; counter <SideFriend.length;counter++)
		{
			if(SideFriend[counter].toString().equals(player.getName()))
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[친구] : 이미 상대방에게 친구 요청을 한 상태입니다!");
				return;
			}
		}
		
		for(int counter= 0; counter < Friend.length;counter++)
		{
			if(Friend[counter].toString().equals(target.getName()))
			{
				SideFriendsList.removeKey("Waitting."+player.getName());
				SideFriendsList.set("Friends."+player.getName(),AddTime);
				SideFriendsList.saveConfig();
				FriendsList.removeKey("Waitting."+target.getName());
				FriendsList.set("Friends."+target.getName(),AddTime);
				FriendsList.saveConfig();
				s.SP(player, Sound.LAVA_POP,1.0F, 1.2F);
				player.sendMessage(ChatColor.AQUA+"[친구] : "+ChatColor.YELLOW+target.getName()+ChatColor.AQUA+"님께서 친구로 등록되었습니다!");
				if(target.isOnline())
				{
					s.SP(target, Sound.LAVA_POP,1.0F, 1.2F);
					target.sendMessage(ChatColor.AQUA+"[친구] : "+ChatColor.YELLOW+player.getName()+ChatColor.AQUA+"님께서 친구로 등록되었습니다!");
				}
				return;
			}
		}
		Friend = FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
		for(int counter= 0; counter < Friend.length;counter++)
		{
			if(Friend[counter].toString().equals(target.getName()))
			{
				s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+"[친구] : 이미 친구로 등록된 플레이어 입니다!");
				return;
			}
		}
		SideFriendsList.removeKey("Friends."+player.getName());
		SideFriendsList.set("Waitting."+player.getName(), AddTime);
		SideFriendsList.saveConfig();
		s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
		player.sendMessage(ChatColor.YELLOW+"[친구] : 상대방에게 친구 신청을 하였습니다!");
		if(target.isOnline())
		{
			s.SP(target, Sound.WOLF_BARK,1.0F, 1.0F);
			target.sendMessage(ChatColor.YELLOW+"[친구] : "+ChatColor.WHITE+player.getName()+ChatColor.YELLOW+"님께서 친구 요청을 하셨습니다!");
			target.sendMessage(ChatColor.GOLD+"/친구"+ChatColor.WHITE+" 명령어를 입력하여 친구 신청을 확인 해 주세요!");
		}
	}
	
	public void FriendJoinQuitMessage(Player player, boolean isJoinMessage)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager FriendsList  = GUI_YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.set("Friends.1", null);
			FriendsList.set("Waitting.1", null);
			FriendsList.saveConfig();
			return;
		}
		Player target = null;
		YamlManager SideFriendsList  = null;
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		Object[] Friend = FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
		for(int counter= 0; counter < Friend.length;counter++)
		{
			target = Bukkit.getServer().getPlayer(Friend[counter].toString());
			if(target!=null)
			if(target.isOnline())
			{
				SideFriendsList  = GUI_YC.getNewConfig("Friend/"+target.getUniqueId().toString()+".yml");
				if(SideFriendsList.contains("Name"))
				{
					Object[] SideFriend = SideFriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
					for(int count= 0; count < SideFriend.length;count++)
					{
						if(SideFriend[counter].toString().equals(player.getName()))
						{
							if(isJoinMessage)
							{
								s.SP(target, Sound.DOOR_OPEN, 0.5F, 1.0F);
								target.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"[접속] "+ChatColor.WHITE+""+ChatColor.BOLD+player.getName());
							}
							else
							{
								s.SP(target, Sound.DOOR_CLOSE, 0.5F, 1.0F);
								target.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"[퇴장] "+ChatColor.GRAY+""+ChatColor.BOLD+player.getName());
							}
							break;
						}
					}
				}
			}
		}
	}
}
