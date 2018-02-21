package user;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import skill.UserSkillGui;
import util.UtilGui;

public class OptionGui extends UtilGui
{
	public void optionGUI(Player player)
	{
		String UniqueCode = "§0§0§0§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode+"§0옵션");

		removeFlagStack("§f스텟", 397,3,1,Arrays.asList("§7스텟을 확인합니다."), 0, inv);
		removeFlagStack("§f스킬", 403,0,1,Arrays.asList("§7스킬을 확인합니다."), 9, inv);
		removeFlagStack("§f퀘스트", 358,0,1,Arrays.asList("§7현재 진행중인 퀘스트를 확인합니다."), 18, inv);
		removeFlagStack("§f옵션", 160,4,1,Arrays.asList("§7기타 설정을 합니다."), 27, inv);
		removeFlagStack("§f기타", 354,0,1,Arrays.asList("§7기타 내용을 확인합니다."), 36, inv);
		
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 1, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 7, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 10, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 16, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 19, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 25, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 28, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 34, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 37, inv);
		removeFlagStack("§c ", 66,0,1,Arrays.asList(""), 43, inv);

		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget()){removeFlagStack("§f§l돈, 경험치 획득 알림", 384,0,1,Arrays.asList("§a[활성화]","§7돈과 경험치 획득을 알립니다."), 2, inv);}
			else{removeFlagStack("§f§l돈, 경험치 획득 알림", 166,0,1,Arrays.asList("§c[비활성화]","§7돈과 경험치 획득을 알리지 않습니다."), 2, inv);}
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp()){removeFlagStack("§f§l아이템 획득 알림", 154,0,1,Arrays.asList("§a[활성화]","§7아이템 획득을 알립니다."), 3, inv);}
		else{removeFlagStack("§f§l아이템 획득 알림", 166,0,1,Arrays.asList("§c[비활성화]","§7아이템 획득을 알리지 않습니다."), 3, inv);}
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth()){removeFlagStack("§f§l전투 도우미", 381,0,1,Arrays.asList("§a[활성화]","§7각종 전투 상황을 봅니다."), 4, inv);}
		else{removeFlagStack("§f§l전투 도우미", 166,0,1,Arrays.asList("§c[비활성화]","§7각종 전투 상황을 보지 않습니다."), 4, inv);}
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage()){removeFlagStack("§f§l데미지 알림", 267,0,1,Arrays.asList("§a[활성화]","§7적에게 입힌 피해를 알립니다."), 5, inv);}
		else{removeFlagStack("§f§l데미지 알림", 166,0,1,Arrays.asList("§c[비활성화]","§7적에게 입힌 피해를 알리지 않습니다."), 5, inv);}
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()){removeFlagStack("§f§l클릭시 사용", 438,0,1,Arrays.asList("§a[활성화]","§7소비 아이템을 클릭시 사용합니다."), 6, inv);}
		else{removeFlagStack("§f§l클릭시 사용", 166,0,1,Arrays.asList("§c[비활성화]","§7소비 아이템을 단축키 처럼 사용합니다."), 6, inv);}
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()){removeFlagStack("§f§lBGM 재생", 2256,0,1,Arrays.asList("§a[활성화]","§7영역 BGM을 실행 시킵니다."), 14, inv);}
		else{removeFlagStack("§f§lBGM 재생", 166,0,1,Arrays.asList("§c[비활성화]","§7영역 BGM을 듣지 않습니다."), 14, inv);}

		
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook()){removeFlagStack("§f§l장비 구경", 416,0,1,Arrays.asList("§a[허용]","§7다른 플레이어가 자신의 장비를","§7구경할 수 있습니다."), 11, inv);}
		else{removeFlagStack("§f§l장비 구경", 166,0,1,Arrays.asList("§c[비허용]","§7다른 플레이어가 자신의 장비를","§7구경할 수 없습니다."), 11, inv);}

		switch(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType())
		{
		case 0:
			removeFlagStack("§f§l채팅 옵션", 2264,0,1,Arrays.asList("§f[일반]","§7일반적인 채팅을 합니다."), 12, inv);
			break;
		case 1:
			removeFlagStack("§f§l채팅 옵션", 2261,0,1,Arrays.asList("§9[파티]","§7파티 채팅을 합니다."), 12, inv);
			break;
		case 2:
			removeFlagStack("§f§l채팅 옵션", 2260,0,1,Arrays.asList("§a[무음]","§7욕 하고 싶으나 용기가 나지 않을때...."), 12, inv);
			break;
		case 3:
			removeFlagStack("§f§l채팅 옵션", 2258,0,1,Arrays.asList("§d[관리자]","§7관리자 끼리의 채팅을 합니다.","§c※ 일반 유저는 사용할 수 없습니다."), 12, inv);
			break;
		}
		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound()){removeFlagStack("§f§l장비 전환 사운드", 307,0,1,Arrays.asList("§a[허용]","§7핫바를 움직일 때 마다 소리가 납니다."), 13, inv);}
		else{removeFlagStack("§f§l장비 전환 사운드", 166,0,1,Arrays.asList("§c[비허용]","§7핫바를 움직여도 소리가 나지 않습니다."), 13, inv);}

		if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_SeeInventory()){removeFlagStack("§f§l우 클릭시 상대방 정보 보기", 307,0,1,Arrays.asList("§a[허용]","§7대상을 우 클릭하면 정보창이 뜹니다."), 15, inv);}
		else{removeFlagStack("§f§l우 클릭시 상대방 정보 보기", 166,0,1,Arrays.asList("§c[비허용]","§7대상을 우 클릭하여도 정보창이 뜨지 않습니다."), 15, inv);}

		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 26, inv);

		player.openInventory(inv);
	}
	
	public void optionInventoryclick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		int slot = event.getSlot();

		if(event.getSlot()==26)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)
				new user.StatsGui().StatusGUI(player);
			else if(slot == 9)
				new UserSkillGui().mainSkillsListGUI(player, (short) 0);
			else if(slot == 18)
				new quest.QuestGui().MyQuestListGUI(player, (short) 0);
			else if(slot == 36)
				new EtcGui().ETCGUI_Main(player);
			else
			{
				if(slot == 2)//경험치 획득 알림
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_EXPget(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_EXPget()==false);
				else if(slot == 3)//아이템 획득 알림
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_ItemPickUp(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_ItemPickUp()==false);
				else if(slot == 4)//전투 도우미
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_MobHealth(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_MobHealth()==false);
				else if(slot == 5)//데미지 알림
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setAlert_Damage(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isAlert_Damage()==false);
				else if(slot == 6)//클릭시 사용
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setClickUse(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isClickUse()==false);
				else if(slot == 11)//장비 구경
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_EquipLook(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_EquipLook()==false);
				else if(slot == 12)//채팅 옵션
				{
					if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType() < 3)
						main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getOption_ChattingType()+1));
					else
						main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_ChattingType((byte) (0));
				}
				else if(slot == 13)//장비 전환 사운드
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_HotBarSound(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_HotBarSound()==false);
				else if(slot == 14)//BGM 재생
				{
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setBgm(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn()==false);
					new otherplugins.NoteBlockApiMain().Stop(player);
				}
				else if(slot == 15)//우클릭시 상대방 정보 보기
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setOption_SeeInventory(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_SeeInventory()==false);
				
				optionGUI(player);
			}
		}
		return;
	}
}
