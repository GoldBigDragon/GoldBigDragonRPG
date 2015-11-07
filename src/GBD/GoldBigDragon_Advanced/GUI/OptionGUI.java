package GBD.GoldBigDragon_Advanced.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class OptionGUI extends GUIutil
{
	public void optionGUI(Player player)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
	    YamlManager YM;
	  	if(GUI_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		stat.CreateNewStats(player);

		YM = GUI_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "옵션");

		Stack2(ChatColor.WHITE + "스텟", 397,3,1,Arrays.asList(ChatColor.GRAY + "스텟을 확인합니다."), 0, inv);
		Stack2(ChatColor.WHITE + "스킬", 403,0,1,Arrays.asList(ChatColor.GRAY + "스킬을 확인합니다."), 9, inv);
		Stack2(ChatColor.WHITE + "퀘스트", 358,0,1,Arrays.asList(ChatColor.GRAY + "현재 진행중인 퀘스트를 확인합니다."), 18, inv);
		Stack2(ChatColor.WHITE + "옵션", 160,4,1,Arrays.asList(ChatColor.GRAY + "기타 설정을 합니다."), 27, inv);
		Stack2(ChatColor.WHITE + "기타", 354,0,1,Arrays.asList(ChatColor.GRAY + "기타 내용을 확인합니다."), 36, inv);
		
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

		if(YM.getBoolean("Alert.EXPget") == true){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "경험치 획득 알림", 384,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"경험치 획득을 알립니다."), 2, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "경험치 획득 알림", 384,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"경험치 획득을 알리지 않습니다."), 2, inv);}
		if(YM.getBoolean("Alert.ItemPickUp") == true){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "아이템 획득 알림", 154,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"아이템 획득을 알립니다."), 3, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "아이템 획득 알림", 154,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"아이템 획득을 알리지 않습니다."), 3, inv);}
		if(YM.getBoolean("Alert.MobHealth") == true){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "전투 도우미", 381,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"각종 전투 상황을 봅니다."), 4, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "전투 도우미", 381,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"각종 전투 상황을 봅니다."), 4, inv);}
		if(YM.getBoolean("Alert.Damage") == true){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "데미지 알림", 267,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"적에게 입힌 피해를 알립니다."), 5, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "데미지 알림", 267,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"적에게 입힌 피해를 알리지 않습니다."), 5, inv);}
		if(YM.getBoolean("Alert.AttackDelay") == true){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "공격 딜레이 알림", 347,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"공격 속도를 알립니다."), 6, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "공격 딜레이 알림", 347,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"공격 속도를 알리지 않습니다."), 6, inv);}
		if(YM.getBoolean("Option.EquipLook") == true){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 구경", 416,0,1,Arrays.asList(ChatColor.GREEN + "[허용]",ChatColor.GRAY+"다른 플레이어가 자신의 장비를",ChatColor.GRAY+"구경할 수 있습니다."), 11, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 구경", 416,0,1,Arrays.asList(ChatColor.RED + "[비허용]",ChatColor.GRAY+"다른 플레이어가 자신의 장비를",ChatColor.GRAY+"구경할 수 없습니다."), 11, inv);}

		switch(YM.getInt("Option.ChattingType"))
		{
		case 0:
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "채팅 옵션", 2264,0,1,Arrays.asList(ChatColor.WHITE + "[일반]",ChatColor.GRAY+"일반적인 채팅을 합니다."), 12, inv);
			break;
		case 1:
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "채팅 옵션", 2261,0,1,Arrays.asList(ChatColor.BLUE + "[파티]",ChatColor.GRAY+"파티 채팅을 합니다."), 12, inv);
			break;
		case 2:
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "채팅 옵션", 2260,0,1,Arrays.asList(ChatColor.GREEN + "[무음]",ChatColor.GRAY+"욕 하고 싶으나 용기가 나지 않을때...."), 12, inv);
			break;
		case 3:
			Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "채팅 옵션", 2258,0,1,Arrays.asList(ChatColor.LIGHT_PURPLE + "[관리자]",ChatColor.GRAY+"관리자 끼리의 채팅을 합니다.",ChatColor.RED + "※ 일반 유저는 사용할 수 없습니다."), 12, inv);
			break;
		}
		if(YM.getBoolean("Option.HotBarSound") == true){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 전환 사운드", 307,0,1,Arrays.asList(ChatColor.GREEN + "[허용]",ChatColor.GRAY+"핫바를 움직일 때 마다 소리가 납니다."), 13, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 전환 사운드", 166,0,1,Arrays.asList(ChatColor.RED + "[비허용]",ChatColor.GRAY+"핫바를 움직여도 소리가 나지 않습니다."), 13, inv);}

		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 26, inv);

		player.openInventory(inv);
	}
	
	public void optionInventoryclick(InventoryClickEvent event)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
	    YamlManager YM;
		StatsGUI SGUI = new StatsGUI();
		ETCGUI EGUI = new ETCGUI();
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
	  	if(GUI_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		stat.CreateNewStats(player);

		YM = GUI_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");
		
		switch (event.getSlot())
		{
			case 0: //스텟
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				SGUI.StatusGUI(player);
				break;
			case 36://기타
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				EGUI.ETCGUI_Main(player);
				break;
			case 9://스킬
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PlayerSkillGUI PGUI = new PlayerSkillGUI();
				PGUI.MainSkillsListGUI(player, 0);
				break;
			case 18://퀘스트
				GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
				QGUI.MyQuestListGUI(player, 0);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				break;
			case 26://닫기
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			case 2://경험치 획득 알림
				if(YM.getBoolean("Alert.EXPget") == true){YM.set("Alert.EXPget", false);}
				else{YM.set("Alert.EXPget", true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YM.saveConfig();
				optionGUI(player);
				break;
			case 3://아이템 획득 알림
				if(YM.getBoolean("Alert.ItemPickUp") == true){YM.set("Alert.ItemPickUp", false);}
				else{YM.set("Alert.ItemPickUp", true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YM.saveConfig();
				optionGUI(player);
				break;
			case 4://전투 도우미
				if(YM.getBoolean("Alert.MobHealth") == true){YM.set("Alert.MobHealth", false);}
				else{YM.set("Alert.MobHealth", true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YM.saveConfig();
				optionGUI(player);
				break;
			case 5://데미지 알림
				if(YM.getBoolean("Alert.Damage") == true){YM.set("Alert.Damage", false);}
				else{YM.set("Alert.Damage", true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YM.saveConfig();
				optionGUI(player);
				break;
			case 6://공격 딜레이 알림
				if(YM.getBoolean("Alert.AttackDelay") == true){YM.set("Alert.AttackDelay", false);}
				else{YM.set("Alert.AttackDelay", true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YM.saveConfig();
				optionGUI(player);
				break;
			case 11://장비 구경
				if(YM.getBoolean("Option.EquipLook") == true){YM.set("Option.EquipLook", false);}
				else{YM.set("Option.EquipLook", true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YM.saveConfig();
				optionGUI(player);
				break;
			case 12://채팅 옵션
				if(YM.getInt("Option.ChattingType") < 3)
				{
					YM.set("Option.ChattingType", (YM.getInt("Option.ChattingType")+1));
				}
				else
				{
					YM.set("Option.ChattingType", 0);
				}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YM.saveConfig();
				optionGUI(player);
				break;
			case 13://장비 전환 사운드
				if(YM.getBoolean("Option.HotBarSound") == true){YM.set("Option.HotBarSound", false);}
				else{YM.set("Option.HotBarSound", true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YM.saveConfig();
				optionGUI(player);
				break;
		}
		return;
	}
	
}
