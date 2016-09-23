package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Skill.PlayerSkillGUI;
import GoldBigDragon_RPG.Util.YamlController;

public class OptionGUI extends GUIutil
{
	public void optionGUI(Player player)
	{
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

		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "돈, 경험치 획득 알림", 384,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"돈과 경험치 획득을 알립니다."), 2, inv);}
			else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "돈, 경험치 획득 알림", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"돈과 경험치 획득을 알리지 않습니다."), 2, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "아이템 획득 알림", 154,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"아이템 획득을 알립니다."), 3, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "아이템 획득 알림", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"아이템 획득을 알리지 않습니다."), 3, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "전투 도우미", 381,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"각종 전투 상황을 봅니다."), 4, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "전투 도우미", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"각종 전투 상황을 보지 않습니다."), 4, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "데미지 알림", 267,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"적에게 입힌 피해를 알립니다."), 5, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "데미지 알림", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"적에게 입힌 피해를 알리지 않습니다."), 5, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "클릭시 사용", 373,8261,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"소비 아이템을 클릭시 사용합니다."), 6, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "클릭시 사용", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"소비 아이템을 단축키 처럼 사용합니다."), 6, inv);}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "BGM 재생", 2256,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"영역 BGM을 실행 시킵니다."), 14, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "BGM 재생", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"영역 BGM을 듣지 않습니다."), 14, inv);}

		
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 구경", 416,0,1,Arrays.asList(ChatColor.GREEN + "[허용]",ChatColor.GRAY+"다른 플레이어가 자신의 장비를",ChatColor.GRAY+"구경할 수 있습니다."), 11, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 구경", 166,0,1,Arrays.asList(ChatColor.RED + "[비허용]",ChatColor.GRAY+"다른 플레이어가 자신의 장비를",ChatColor.GRAY+"구경할 수 없습니다."), 11, inv);}

		switch(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType())
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
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 전환 사운드", 307,0,1,Arrays.asList(ChatColor.GREEN + "[허용]",ChatColor.GRAY+"핫바를 움직일 때 마다 소리가 납니다."), 13, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 전환 사운드", 166,0,1,Arrays.asList(ChatColor.RED + "[비허용]",ChatColor.GRAY+"핫바를 움직여도 소리가 나지 않습니다."), 13, inv);}

		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 26, inv);

		player.openInventory(inv);
	}
	
	
	public void optionInventoryclick(InventoryClickEvent event)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		StatsGUI SGUI = new StatsGUI();
		ETCGUI EGUI = new ETCGUI();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
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
				PGUI.MainSkillsListGUI(player, (short) 0);
				break;
			case 18://퀘스트
				GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
				QGUI.MyQuestListGUI(player, (short) 0);
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				break;
			case 26://닫기
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			case 2://경험치 획득 알림
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_EXPget(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_EXPget(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 3://아이템 획득 알림
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_ItemPickUp(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_ItemPickUp(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 4://전투 도우미
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_MobHealth(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_MobHealth(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 5://데미지 알림
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_Damage(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_Damage(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 6://클릭시 사용
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setClickUse(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setClickUse(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 11://장비 구경
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_EquipLook(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_EquipLook(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 12://채팅 옵션
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType() < 3)
					GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType()+1));
				else
					GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (0));
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 13://장비 전환 사운드
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_HotBarSound(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_HotBarSound(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				optionGUI(player);
				break;
			case 14://BGM 재생
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()){GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setBgm(false);}
				else{GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setBgm(true);}
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(ServerOption.NoteBlockAPIAble)
					new OtherPlugins.NoteBlockAPIMain().Stop(player);
				optionGUI(player);
				break;
		}
		return;
	}
	
}
