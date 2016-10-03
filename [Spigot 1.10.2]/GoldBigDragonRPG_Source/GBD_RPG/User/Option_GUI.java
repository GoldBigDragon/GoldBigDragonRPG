package GBD_RPG.User;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.Skill.UserSkill_GUI;
import GBD_RPG.Util.Util_GUI;

public class Option_GUI extends Util_GUI
{
	public void optionGUI(Player player)
	{
		String UniqueCode = "§0§0§0§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode+"§0옵션");

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

		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "돈, 경험치 획득 알림", 384,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"돈과 경험치 획득을 알립니다."), 2, inv);}
			else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "돈, 경험치 획득 알림", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"돈과 경험치 획득을 알리지 않습니다."), 2, inv);}
		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "아이템 획득 알림", 154,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"아이템 획득을 알립니다."), 3, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "아이템 획득 알림", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"아이템 획득을 알리지 않습니다."), 3, inv);}
		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "전투 도우미", 381,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"각종 전투 상황을 봅니다."), 4, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "전투 도우미", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"각종 전투 상황을 보지 않습니다."), 4, inv);}
		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "데미지 알림", 267,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"적에게 입힌 피해를 알립니다."), 5, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "데미지 알림", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"적에게 입힌 피해를 알리지 않습니다."), 5, inv);}
		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "클릭시 사용", 438,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"소비 아이템을 클릭시 사용합니다."), 6, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "클릭시 사용", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"소비 아이템을 단축키 처럼 사용합니다."), 6, inv);}
		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "BGM 재생", 2256,0,1,Arrays.asList(ChatColor.GREEN + "[활성화]",ChatColor.GRAY+"영역 BGM을 실행 시킵니다."), 14, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "BGM 재생", 166,0,1,Arrays.asList(ChatColor.RED + "[비활성화]",ChatColor.GRAY+"영역 BGM을 듣지 않습니다."), 14, inv);}

		
		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 구경", 416,0,1,Arrays.asList(ChatColor.GREEN + "[허용]",ChatColor.GRAY+"다른 플레이어가 자신의 장비를",ChatColor.GRAY+"구경할 수 있습니다."), 11, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 구경", 166,0,1,Arrays.asList(ChatColor.RED + "[비허용]",ChatColor.GRAY+"다른 플레이어가 자신의 장비를",ChatColor.GRAY+"구경할 수 없습니다."), 11, inv);}

		switch(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType())
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
		if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound()){Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 전환 사운드", 307,0,1,Arrays.asList(ChatColor.GREEN + "[허용]",ChatColor.GRAY+"핫바를 움직일 때 마다 소리가 납니다."), 13, inv);}
		else{Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "장비 전환 사운드", 166,0,1,Arrays.asList(ChatColor.RED + "[비허용]",ChatColor.GRAY+"핫바를 움직여도 소리가 나지 않습니다."), 13, inv);}

		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 26, inv);

		player.openInventory(inv);
	}
	
	public void optionInventoryclick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();

		int slot = event.getSlot();

		if(event.getSlot()==26)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)
				new GBD_RPG.User.Stats_GUI().StatusGUI(player);
			else if(slot == 9)
				new UserSkill_GUI().MainSkillsListGUI(player, (short) 0);
			else if(slot == 18)
				new GBD_RPG.Quest.Quest_GUI().MyQuestListGUI(player, (short) 0);
			else if(slot == 36)
				new ETC_GUI().ETCGUI_Main(player);
			else
			{
				if(slot == 2)//경험치 획득 알림
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget())
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_EXPget(false);
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_EXPget(true);
				}
				else if(slot == 3)//아이템 획득 알림
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp())
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_ItemPickUp(false);
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_ItemPickUp(true);
				}
				else if(slot == 4)//전투 도우미
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth())
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_MobHealth(false);
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_MobHealth(true);
				}
				else if(slot == 5)//데미지 알림
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage())
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_Damage(false);
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_Damage(true);
				}
				else if(slot == 6)//클릭시 사용
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse())
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setClickUse(false);
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setClickUse(true);
				}
				else if(slot == 11)//장비 구경
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook())
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_EquipLook(false);
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_EquipLook(true);
				}
				else if(slot == 12)//채팅 옵션
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType() < 3)
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType()+1));
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (0));
				}
				else if(slot == 13)//장비 전환 사운드
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound())
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_HotBarSound(false);
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_HotBarSound(true);
				}
				else if(slot == 14)//BGM 재생
				{
					if(GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setBgm(false);
					else
						GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setBgm(true);
					if(Main_ServerOption.NoteBlockAPIAble)
						new OtherPlugins.NoteBlockAPIMain().Stop(player);
				}
				
				optionGUI(player);
			}
		}
		return;
	}
	
}
