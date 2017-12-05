package admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import area.AreaGui;
import customitem.CustomItemGui;
import customitem.UseableItemGui;
import effect.SoundEffect;
import job.JobGUI;
import main.MainServerOption;
import monster.MonsterGui;
import quest.QuestGui;
import skill.OPboxSkillGui;
import structure.StructureGui;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;



public class OPboxGui extends UtilGui
{
	public void opBoxGuiMain (Player player, byte page)
	{
		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
		String uniqueCode = "§0§0§1§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0관리자 도구 : "+page+" / 3");
		
		removeFlagStack("§0"+page, 160,11,1,null, 0, inv);
		removeFlagStack(" ", 160,11,1,null, 1, inv);
		removeFlagStack("§c§l[월드 시간 변경 -낮-]", 160,4,1,Arrays.asList("§f현재 월드 시간을 낮으로","§f변경 시킵니다."), 2, inv);
		removeFlagStack("§7§l[월드 시간 변경 -밤-]", 160,4,1,Arrays.asList("§f현재 월드 시간을 밤으로","§f변경 시킵니다."), 3, inv);
		removeFlagStack("§e§l[현재 위치를 스폰으로]", 160,4,1,Arrays.asList("§f현재 위치를 스폰 지점으로","§f변경 시킵니다."), 4, inv);
		removeFlagStack("§3§l[월드 날씨 변경 -맑음-]", 160,4,1,Arrays.asList("§f현재 월드 날씨를 맑게","§f변경 시킵니다."), 5, inv);
		removeFlagStack("§7§l[월드 날씨 변경 -흐림-]", 160,4,1,Arrays.asList("§f현재 월드 날씨를 흐리게","§f변경 시킵니다."), 6, inv);
		removeFlagStack(" ", 160,11,1,null, 7, inv);
		removeFlagStack(" ", 160,11,1,null, 8, inv);
		removeFlagStack(" ", 160,11,1,null, 9, inv);
		removeFlagStack(" ", 160,11,1,null, 18, inv);
		removeFlagStack(" ", 160,11,1,null, 17, inv);
		removeFlagStack(" ", 160,11,1,null, 26, inv);
		removeFlagStack(" ", 160,11,1,null, 27, inv);
		removeFlagStack(" ", 160,11,1,null, 36, inv);
		removeFlagStack(" ", 160,11,1,null, 35, inv);
		removeFlagStack(" ", 160,11,1,null, 36, inv);
		removeFlagStack(" ", 160,11,1,null, 37, inv);
		removeFlagStack(" ", 160,11,1,null, 38, inv);
		removeFlagStack(" ", 160,11,1,null, 39, inv);
		removeFlagStack(" ", 160,11,1,null, 40, inv);
		removeFlagStack(" ", 160,11,1,null, 41, inv);
		removeFlagStack(" ", 160,11,1,null, 42, inv);
		removeFlagStack(" ", 160,11,1,null, 43, inv);
		removeFlagStack(" ", 160,11,1,null, 44, inv);

		removeFlagStack("§f§l플러그인 가이드", 340,0,1,Arrays.asList("§eGoldBigDragonAdvanced","§f플러그인에 대한","§f도움말을 봅니다."), 22, inv);
		
		switch(page)
		{
			case 1:
				stackItem(getPlayerSkull("§f§lGoldBigDragonRPG", 1, Arrays.asList("","§e[버전]","§f§l"+MainServerOption.serverVersion,"","§e[패치]","§f§l"+MainServerOption.serverUpdate), "GoldBigDragon"), 10, inv);
				removeFlagStack("§a§l서버 설정", 137,0,1,Arrays.asList("§7서버에 대한 전반적인 설정을 합니다."), 12, inv);
				removeFlagStack("§f§l몬스터", 397,4,1,Arrays.asList("§7커스텀 몬스터를 생성하거나","§7스폰 에그를 얻습니다."), 14, inv);
				removeFlagStack("§f§l이벤트", 401,0,1,Arrays.asList("§7이벤트 개최시 접속된 모든","§7플레이어들에게 알려지며,","§7이후 입장하는 모든 플레이어에게","§7이벤트 진행을 알려줍니다."), 16, inv);
				if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
				{removeFlagStack("§f§l게임 성향", 346,0,1,Arrays.asList("§f[마비노기]","§7환생과 누적레벨이 존재하며,","§7스텟을 임의로 올릴 수 없습니다.","§c플레이어의 스텟 데이터가 이어지므로","§c변경시 주의가 필요합니다."), 28, inv);}
				else
				{{removeFlagStack("§f§l게임 성향", 40,0,1,Arrays.asList("§6[메이플 스토리]","§7스텟을 임의로 올릴 수 있으며,","§7누적레벨과 환생이 존재하지 않습니다.","§c플레이어의 스텟 데이터가 이어지므로","§c변경시 주의가 필요합니다."), 28, inv);}	}
				removeFlagStack("§f§l커스텀 아이템", 389,0,1,Arrays.asList("§f커스텀 아이템을 생성하거나","§f클릭하여 지급 받습니다.","","§e[좌 클릭시 아이템 받기]","§e[Shift + 좌 클릭시 아이템 수정]","§c[Shift + 우 클릭시 아이템 삭제]"), 30, inv);
				removeFlagStack("§f§l네비게이션", 358,120,1,Arrays.asList("§f길찾기 시스템을 관리합니다."), 32, inv);
				removeFlagStack("§f§l퀘스트", 403,0,1,Arrays.asList("§f퀘스트를 새로 만들거나","§f수정하거나 삭제합니다."), 34, inv);
				removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
				break;

			case 2:
				removeFlagStack("§a§l스킬", 403,0,1,Arrays.asList("§7직업 혹은 카테고리에 등록 가능한","§7스킬들을 등록합니다."), 10, inv);
				removeFlagStack("§a§l카테고리 및 직업", 397,3,1,Arrays.asList("§7스킬과 직업에 관한 설정을 합니다."), 12, inv);
				removeFlagStack("§a§l소비 아이템", 260,0,1,Arrays.asList("§7우 클릭 혹은 단축키를 통해","§7사용 가능한 아이템을 관리합니다."), 14, inv);
				removeFlagStack("§a§l영역", 395,0,1,Arrays.asList("§7영역 지정을 통하여 구역마다","§7특수한 옵션을 설정할 수 있습니다."), 16, inv);
				removeFlagStack("§a§l개조식", 145,2,1,Arrays.asList("§7무기의 개조 방식을 새로 만들거나","§7삭제할 수 있습니다."), 28, inv);
				removeFlagStack("§a§l초심자", 54,0,1,Arrays.asList("§7서버에 처음 들어온 플레이어에","§7대하여 설정을 합니다."), 30, inv);
				removeFlagStack("§a§l월드 생성", 2,0,1,Arrays.asList("§7새로운 월드를 생성합니다."), 32, inv);
				removeFlagStack("§a§l워프", 345,0,1,Arrays.asList("§7워프 지점을 생성하거나, 이동합니다."), 34, inv);

				removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
				removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
				break;
			case 3:
				removeFlagStack("§a§l도박", 266,0,1,Arrays.asList("§7도박 관련 기능을 봅니다."), 10, inv);
				removeFlagStack("§a§l기능성 개체", 130,0,1,Arrays.asList("§7각종 기능을 가진 개체들을","§7설치하거나 제거합니다."), 12, inv);
				removeFlagStack("§a§l던전", 52,0,1,Arrays.asList("§7인스 턴스 던전에 대한 설정을 합니다."), 14, inv);
				removeFlagStack("§a§l제작", 58,0,1,Arrays.asList("§7제작에 대한 설정을 합니다."), 16, inv);
				
				removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
				break;
			
		}

		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7작업 관리자 창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void opBoxGuiSetting(Player player)
	{
		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
		String uniqueCode = "§0§0§1§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0관리자 옵션");
		
		removeFlagStack(" ", 160,11,1,null, 0, inv);
		removeFlagStack(" ", 160,11,1,null, 1, inv);
		removeFlagStack(" ", 160,11,1,null, 2, inv);
		removeFlagStack(" ", 160,11,1,null, 3, inv);
		removeFlagStack(" ", 160,11,1,null, 4, inv);
		removeFlagStack(" ", 160,11,1,null, 5, inv);
		removeFlagStack(" ", 160,11,1,null, 6, inv);
		removeFlagStack(" ", 160,11,1,null, 7, inv);
		removeFlagStack(" ", 160,11,1,null, 8, inv);
		removeFlagStack(" ", 160,11,1,null, 9, inv);
		removeFlagStack(" ", 160,11,1,null, 18, inv);
		removeFlagStack(" ", 160,11,1,null, 17, inv);
		removeFlagStack(" ", 160,11,1,null, 26, inv);
		removeFlagStack(" ", 160,11,1,null, 27, inv);
		removeFlagStack(" ", 160,11,1,null, 36, inv);
		removeFlagStack(" ", 160,11,1,null, 35, inv);
		removeFlagStack(" ", 160,11,1,null, 36, inv);
		removeFlagStack(" ", 160,11,1,null, 37, inv);
		removeFlagStack(" ", 160,11,1,null, 38, inv);
		removeFlagStack(" ", 160,11,1,null, 39, inv);
		removeFlagStack(" ", 160,11,1,null, 40, inv);
		removeFlagStack(" ", 160,11,1,null, 41, inv);
		removeFlagStack(" ", 160,11,1,null, 42, inv);
		removeFlagStack(" ", 160,11,1,null, 43, inv);
		removeFlagStack(" ", 160,11,1,null, 44, inv);

		if(configYaml.getBoolean("Server.EntitySpawn"))
		{removeFlagStack("§a§l엔티티 스폰", 52,0,1,Arrays.asList("§a[활성화]","§7해당 옵션이 비활성화 상태일 경우","§7더이상 엔티티들이 소환되지 않습니다."), 10, inv);}
		else
		{{removeFlagStack("§c§l엔티티 스폰", 166,0,1,Arrays.asList("§c[비 활성화]","§7해당 옵션이 비활성화 상태일 경우","§7더이상 엔티티들이 소환되지 않습니다."), 10, inv);}	}
		
		if(configYaml.getBoolean("Server.PVP"))
		{removeFlagStack("§a§l유저간 PVP", 276,0,1,Arrays.asList("§a[활성화]","§7현재 유저간 전투가 허용 됩니다."), 11, inv);}
		else
		{{removeFlagStack("§c§l유저간 PVP", 166,0,1,Arrays.asList("§c[비 활성화]","§7현재 유저간 전투가 허용되지 않습니다."), 11, inv);}	}
		switch(configYaml.getInt("Server.MonsterSpawnEffect"))
		{
			case 0 : removeFlagStack("§f§l몬스터 스폰 효과", 383,0,1,Arrays.asList("§f[없음]"), 12, inv); break;
			case 1 : removeFlagStack("§f§l몬스터 스폰 효과", 383,66,1,Arrays.asList("§9[마법]","§c※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 2 : removeFlagStack("§f§l몬스터 스폰 효과", 383,58,1,Arrays.asList("§5[엔더]","§c※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 3 : removeFlagStack("§f§l몬스터 스폰 효과", 383,50,1,Arrays.asList("§a[폭발]","§c※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 4 : removeFlagStack("§f§l몬스터 스폰 효과", 383,61,1,Arrays.asList("§4[화염]","§c※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 5 : removeFlagStack("§f§l몬스터 스폰 효과", 383,120,1,Arrays.asList("§c[화남]","§c※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 6 : removeFlagStack("§f§l몬스터 스폰 효과", 383,90,1,Arrays.asList("§d[게이]","§c※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 7 : removeFlagStack("§f§l몬스터 스폰 효과", 50,0,1,Arrays.asList("§6[골빅]","§c※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
		}

		if(configYaml.getBoolean("Server.CustomWeaponBreak"))
		{removeFlagStack("§a§l커스텀 무기 파괴", 268,50,1,Arrays.asList("§a[활성화]","§7커스텀 무기의 내구도가 0이 될 경우","§7일반 무기와 같이 파괴됩니다."), 13, inv);}
		else
		{{removeFlagStack("§c§l커스텀 무기 파괴", 166,0,1,Arrays.asList("§c[비 활성화]","§7커스텀 무기는 내구도가 0이 되어도","§7파괴되지 않습니다."), 13, inv);}	}

		if(configYaml.getString("Server.JoinMessage") != null)
			removeFlagStack("§a§l입장 메시지", 386,0,1,Arrays.asList("§a[적용]","§8"+configYaml.getString("Server.JoinMessage"),"","§e[좌 클릭시 입장 메시지 변경]"), 14, inv);
		else
			removeFlagStack("§a§l입장 메시지", 166,0,1,Arrays.asList("§c[제거]","§7입장 메시지가 없습니다.","","§e[좌 클릭시 입장 메시지 등록]"), 14, inv);

		if(configYaml.getString("Server.QuitMessage") != null)
			removeFlagStack("§a§l퇴장 메시지", 386,0,1,Arrays.asList("§a[적용]","§8"+configYaml.getString("Server.QuitMessage"),"","§e[좌 클릭시 퇴장 메시지 변경]"), 15, inv);
		else
			removeFlagStack("§a§l퇴장 메시지", 166,0,1,Arrays.asList("§c[제거]","§7퇴장 메시지가 없습니다.","","§e[좌 클릭시 퇴장 메시지 등록]"), 15, inv);
		removeFlagStack("§a§l공지사항", 323,0,1,Arrays.asList("§7서버에 일정 시간마다","§7공지사항을 알립니다.","","§e[좌 클릭시 공지사항 설정]"), 16, inv);

		if(configYaml.getString("Server.ChatPrefix")==null)
			stackItem(getPlayerSkull("§a§l채팅 형태", 1, Arrays.asList("§7채팅 형태를 변경합니다.","§7단, 형태를 변경할 경우","§7모든 일반 채팅이 브로드","§7캐스트 형식으로 변경되므로","§7주의해야 합니다.","","§3[현재 채팅 형태]","§f[   없음   ]","","§e[좌 클릭시 접두사 변경]","§c[우 클릭시 접두사 제거]","","§a[코드 제공]","§f§lB4TT3RY"), "B4TT3RY__"), 19, inv);
		else
		{
			String prefix = configYaml.getString("Server.ChatPrefix");
			prefix=prefix.replace("%t%","[시각]");
			prefix=prefix.replace("%gm%","[게임 모드]");
			prefix=prefix.replace("%ct%","[채팅 타입]");
			prefix=prefix.replace("%lv%","[레벨]");
			prefix=prefix.replace("%rlv%","[누적 레벨]");
			prefix=prefix.replace("%job%","[직업]");
			prefix=prefix.replace("%player%","[닉네임]");
			prefix=prefix.replace("%message%", "[내용]");
			stackItem(getPlayerSkull("§a§l채팅 형태", 1, Arrays.asList("§7채팅 형태를 변경합니다.","§7단, 형태를 변경할 경우","§7모든 일반 채팅이 브로드","§7캐스트 형식으로 변경되므로","§7주의해야 합니다.","","§3[현재 채팅 형태]","§f"+prefix,"","§e[좌 클릭시 접두사 변경]","§c[우 클릭시 접두사 제거]","","§a[코드 제공]","§f§lB4TT3RY"), "B4TT3RY__"), 19, inv);
		}

		if(configYaml.getBoolean("Server.CustomBlockPlace"))
			removeFlagStack("§a§l커스텀 블록 설치", 1,0,1,Arrays.asList("§a[가능]","§7아이템에 설명이 붙어 있거나","§7이름이 변경된 아이템이","§7설치되는 것을 막지 않습니다."), 20, inv);
		else
			removeFlagStack("§c§l커스텀 블록 설치", 166,0,1,Arrays.asList("§c[불가능]","§7아이템에 설명이 붙어 있거나","§7이름이 변경된 아이템이","§7설치되는 것을 막습니다."), 20, inv);

		removeFlagStack("§a§l스텟 이름 변경", 340,0,1,Arrays.asList("§7서버의 스텟 이름을","§7마음대로 변경합니다.","","§e[좌 클릭시 스텟 이름 변경]","","§c[       주의       ]","§c스텟 이름 변경시 이전에 뿌려진","§c커스텀 아이템의 능력치는","§c모두 무효 처리가 되며,","§c스킬별 스텟 영향 옵션을","§c재 설정 하여야 합니다."), 21, inv);

		removeFlagStack("§a§l금전 시스템 변경", 266,0,1,Arrays.asList("§7화폐에 관련된 시스템을","§7변경합니다.","","§e[좌 클릭시 화폐 시스템GUI 이동]"), 22, inv);

		removeFlagStack("§a§l사망 시스템 변경", 397,0,1,Arrays.asList("§7사망과 관련된 시스템을","§7변경합니다.","","§e[좌 클릭시 사망 시스템GUI 이동]"), 23, inv);

		if(main.MainServerOption.AntiExplode)
			removeFlagStack("§a§l폭발 방지", 46,0,1,Arrays.asList("§a[활성화]","§7크리퍼, TNT, 엔더 크리스탈로 인한","§7블록 파괴를 방지합니다."), 24, inv);
		else
			removeFlagStack("§a§l폭발 방지", 166,0,1,Arrays.asList("§c[비 활성화]","§7크리퍼, TNT, 엔더 크리스탈로 인한","§7블록 파괴를 방치합니다."), 24, inv);

		if(main.MainServerOption.dualWeapon)
			removeFlagStack("§a§l왼손 무기 데미지 적용", 442,0,1,Arrays.asList("§a[활성화]","§7왼손에 장착된 무기도","§7데미지 계산에 들어갑니다."), 25, inv);
		else
			removeFlagStack("§a§l왼손 무기 데미지 적용", 166,0,1,Arrays.asList("§c[비 활성화]","§7왼손에 장착된 무기는","§7없는 것으로 간주합니다."), 25, inv);

		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7작업 관리자 창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void opBoxGuiBroadCast(Player player, byte page)
	{
		YamlLoader broadcastYaml = new YamlLoader();
		broadcastYaml.getConfig("BroadCast.yml");

		String uniqueCode = "§0§0§1§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0관리자 공지사항 : " + (page+1));

		if(broadcastYaml.contains("0"))
		{
			int broadcastAmount = broadcastYaml.getConfigurationSection("").getKeys(false).size();
			byte loc=0;
			for(int count = (short) (page*45); count < broadcastAmount;count++)
			{				
				removeFlagStack("§0§l" + count, 340,0,1,Arrays.asList(broadcastYaml.getString(count+"")
						,"","§c[Shift + 우클릭시 알림 삭제]"), loc, inv);
				
				loc++;
			}

			if(broadcastAmount-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		}
		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		removeFlagStack("§f§l공지 간격", 152,0,1,Arrays.asList("§7매 §6"+configYaml.getInt("Server.BroadCastSecond")+"§7초마다 공지"), 46, inv);
		
		removeFlagStack("§f§l새 공지", 386,0,1,Arrays.asList("§7새로운 공지사항을 생성합니다."), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void opBoxGuiStatChange(Player player)
	{
		String uniqueCode = "§0§0§1§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0관리자 스텟 설정");

		removeFlagStack("§f§l체력", 267,0,1,Arrays.asList("§7[  현재 이름  ]","§f"+MainServerOption.statSTR), 0, inv);
		removeFlagStack("§f§l솜씨", 261,0,1,Arrays.asList("§7[  현재 이름  ]","§f"+MainServerOption.statDEX), 1, inv);
		removeFlagStack("§f§l지력", 369,0,1,Arrays.asList("§7[  현재 이름  ]","§f"+MainServerOption.statINT), 2, inv);
		removeFlagStack("§f§l의지", 370,0,1,Arrays.asList("§7[  현재 이름  ]","§f"+MainServerOption.statWILL), 3, inv);
		removeFlagStack("§f§l행운", 322,0,1,Arrays.asList("§7[  현재 이름  ]","§f"+MainServerOption.statLUK), 4, inv);

		removeFlagStack("§f§l대미지", 276,0,1,Arrays.asList("§7[  현재 이름  ]","§f"+MainServerOption.damage), 5, inv);
		removeFlagStack("§f§l마법 대미지", 403,0,1,Arrays.asList("§7[  현재 이름  ]","§f"+MainServerOption.magicDamage), 6, inv);
		
		
		String lore = MainServerOption.STR_Lore;
		lore = lore.replace("%stat%", MainServerOption.statSTR);
		removeFlagStack("§f§l체력 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 9, inv);
		lore = MainServerOption.DEX_Lore;
		lore = lore.replace("%stat%", MainServerOption.statDEX);
		removeFlagStack("§f§l솜씨 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 10, inv);
		lore = MainServerOption.INT_Lore;
		lore = lore.replace("%stat%", MainServerOption.statINT);
		removeFlagStack("§f§l지력 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 11, inv);
		lore = MainServerOption.WILL_Lore;
		lore = lore.replace("%stat%", MainServerOption.statWILL);
		removeFlagStack("§f§l의지 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 12, inv);
		lore = MainServerOption.LUK_Lore;
		lore = lore.replace("%stat%", MainServerOption.statLUK);
		removeFlagStack("§f§l행운 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 13, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void opBoxGUIMoney(Player player)
	{
		String uniqueCode = "§0§0§1§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0관리자 화폐 설정");

		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
		
		removeFlagStack("§f§l드랍 최대 액수 제한", 166,0,1,Arrays.asList("§7드랍 가능한 최대 금액을","§7설정합니다.","","§e[     현재 최대 드랍     ]","§7 "+configYaml.getInt("Server.Max_Drop_Money")), 11, inv);
		
		removeFlagStack("§f§l화폐", 421,0,1,Arrays.asList("","§7[     현재 이름     ]"," §f"+MainServerOption.money), 13, inv);
	
		removeFlagStack("§f§l화폐 모양 변경", MainServerOption.Money1ID,MainServerOption.Money1DATA,1,Arrays.asList("§7   [  50원 이하  ]  ",""), 28, inv);
		removeFlagStack("§f§l화폐 모양 변경", MainServerOption.Money2ID,MainServerOption.Money2DATA,1,Arrays.asList("§7   [ 100원 이하 ]  ",""), 29, inv);
		removeFlagStack("§f§l화폐 모양 변경", MainServerOption.Money3ID,MainServerOption.Money3DATA,1,Arrays.asList("§7   [1000원 이하]  ",""), 30, inv);
		removeFlagStack("§f§l화폐 모양 변경", MainServerOption.Money4ID,MainServerOption.Money4DATA,1,Arrays.asList("§7   [10000원 이하]  ",""), 31, inv);
		removeFlagStack("§f§l화폐 모양 변경", MainServerOption.Money5ID,MainServerOption.Money5DATA,1,Arrays.asList("§7   [50000원 이하]  ",""), 32, inv);
		removeFlagStack("§f§l화폐 모양 변경", MainServerOption.Money6ID,MainServerOption.Money6DATA,1,Arrays.asList("§7   [50000원 초과]  ",""), 33, inv);
		removeFlagStack("§f§l화폐 모양 초기화", 325,0,1,Arrays.asList("§7   [ 초기화 시킵니다 ]  ",""), 34, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}

	public void opBoxGuiDeath(Player player)
	{
		String uniqueCode = "§0§0§1§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0관리자 사망 설정");

		YamlLoader configYaml = new YamlLoader();
	  	configYaml.getConfig("config.yml");

		removeFlagStack("§6§l[가까운 마을에서 부활]", 345,0,1,Arrays.asList("","§7[현재 설정 상태]","","§a + "+configYaml.getString("Death.Spawn_Home.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Home.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Home.PenaltyMoney")+" 감소","","§e[좌 클릭시 변경]"), 10, inv);
		removeFlagStack("§c§l[다시 일어선다]", 2266,0,1,Arrays.asList("","§7[현재 설정 상태]","","§a + "+configYaml.getString("Death.Spawn_Here.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Here.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Here.PenaltyMoney")+" 감소","","§e[좌 클릭시 변경]"), 12, inv);
		removeFlagStack("§c§l[구조를 기다린다]", 397,3,1,Arrays.asList("","§7[현재 설정 상태]","","§a + "+configYaml.getString("Death.Spawn_Help.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Help.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Help.PenaltyMoney")+" 감소","","§e[좌 클릭시 변경]"), 14, inv);
		removeFlagStack("§3§l[부활석 사용]", 399,0,1,Arrays.asList("","§7[현재 설정 상태]","","§a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" 감소","","§e[좌 클릭시 변경]"), 16, inv);
		if(configYaml.getInt("Death.Track")==-1||configYaml.contains("Death.Track")==false)
			removeFlagStack("§3§l[사망 BGM]", 166,0,1,Arrays.asList("§c[없음]","§7사망 BGM을 설정하지 않았습니다."), 19, inv);
		else
			removeFlagStack("§3§l[사망 BGM]", 2264,0,1,Arrays.asList("§a"+configYaml.getInt("Death.Track")+"번 트랙 재생"), 19, inv);
		
		if(configYaml.getBoolean("Death.DistrictDirectRevive"))
			removeFlagStack("§3§l[다시 일어서기]", 166,0,1,Arrays.asList("§c[불가능]","§7제자리 부활 옵션을 사용하지 않습니다."), 21, inv);
		else
			removeFlagStack("§3§l[다시 일어서기]", 2264,0,1,Arrays.asList("§a[가능]","§7제자리 부활 옵션을 사용합니다."), 21, inv);
		if(configYaml.getBoolean("Death.SystemOn")==false)
			removeFlagStack("§3§l[사망 시스템]", 166,0,1,Arrays.asList("§c[비 활성화]","§7사망 시스템을 사용하지 않습니다."), 31, inv);
		else
			removeFlagStack("§3§l[사망 시스템]", 397,0,1,Arrays.asList("§a[활성화]","§7사망 시스템을 사용합니다."), 31, inv);
		removeFlagStack("§3§l[구조 아이템]", 288,0,1,Arrays.asList("§7행동 불능된 플레이어를","§7다시 일으켜 세워주는","§7치료 아이템을 설정합니다.","","§e[좌 클릭시 확인 및 변경]"), 23, inv);
		removeFlagStack("§3§l[부활 아이템]", 399,0,1,Arrays.asList("§7행동 불능이 되었을 경우","§7제자리에서 부활 할 수 있는","§7제자리 부활 아이템을 설정합니다.","","§e[좌 클릭시 확인 및 변경]"), 25, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void opBoxGuiSettingReviveItem(Player player)
	{
		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		String uniqueCode = "§1§0§1§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 9, uniqueCode + "§0부활 아이템");

		ItemStack item = configYaml.getItemStack("Death.ReviveItem");
		
		if(item != null)
			stackItem(item, 4, inv);
		
		removeFlagStack("§c§l[아이템 넣기>", 166,0,1,null, 1, inv);	
		removeFlagStack("§c§l[아이템 넣기>", 166,0,1,null, 2, inv);
		removeFlagStack("§c§l[아이템 넣기>", 166,0,1,null, 3, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 166,0,1,null, 5, inv);
		removeFlagStack("§c§l<아이템 넣기]", 166,0,1,null, 6, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 166,0,1,null, 7, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 8, inv);
		player.openInventory(inv);
		return;
	}

	public void opBoxGuiSettingRescueItem(Player player)
	{
		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		String uniqueCode = "§1§0§1§0§7§r";
		Inventory inv = Bukkit.createInventory(null, 9, uniqueCode + "§0구조 아이템");

		ItemStack item = configYaml.getItemStack("Death.RescueItem");
		
		if(item != null)
			stackItem(item, 4, inv);
		
		removeFlagStack("§c§l[아이템 넣기>", 166,0,1,null, 1, inv);	
		removeFlagStack("§c§l[아이템 넣기>", 166,0,1,null, 2, inv);
		removeFlagStack("§c§l[아이템 넣기>", 166,0,1,null, 3, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 166,0,1,null, 5, inv);
		removeFlagStack("§c§l<아이템 넣기]", 166,0,1,null, 6, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 166,0,1,null, 7, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 8, inv);
		player.openInventory(inv);
		return;
	}

	public void guideGui (Player player)
	{
		String uniqueCode = "§0§0§1§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0관리자 가이드");
		
		removeFlagStack("§e§l스텟 시스템", 340,0,1,Arrays.asList("§7플러그인에는 5가지 스텟이 있습니다.","§c["+MainServerOption.statSTR+"]","§7"+MainServerOption.statSTR+"은 플레이어의","§7물리적 데미지에 관여합니다.",ChatColor.GREEN + "["+MainServerOption.statDEX+"]","§7"+MainServerOption.statDEX+"는 플레이어의 밸런스 및","§7생산 성공률과 생산 품질,","§7원거리 데미지에 관여합니다.","§9["+MainServerOption.statINT+"]","§7"+MainServerOption.statINT+"은 마법방어 및 마법보호,","§7마법 공격력에 관여합니다.","§f["+MainServerOption.statWILL+"]","§7"+MainServerOption.statWILL+"는 플레이어의","§7크리티컬에 관여합니다.","§e["+MainServerOption.statLUK+"]","§7"+MainServerOption.statLUK+"은 크리티컬 및","§7럭키 피니시, 럭키 보너스 등","§7각종 '확률'에 관여합니다."), 0,inv);
		removeFlagStack("§e§l럭키 피니시", 340,0,1,Arrays.asList("§7몬스터를 사냥하였을 경우","§7일정 확률로 돈이 더 나오게 됩니다."), 1,inv);
		removeFlagStack("§e§l럭키 보너스", 340,0,1,Arrays.asList("§7채집을 할 경우 일정 확률로","§7채집 품목이 더 나오게 됩니다."), 2,inv);
		removeFlagStack("§e§l커스텀 아이템 [1]", 340,0,1,Arrays.asList("§7각종 커스텀 아이템을","§7생성하거나 등록하여","§7언제든 불러올 수 있습니다.","","§6[명령어]","§e/아이템"), 3,inv);
		removeFlagStack("§e§l커스텀 아이템 [2]", 340,0,1,Arrays.asList("§8"+ MainServerOption.damage+", 방어, 보호 등의","§7특수 옵션이 붙은 아이템은","§7내구도가 0이 될 시 효과가","§7적용되지 않습니다.","§7그럴 경우, 직접 수리하거나","§7대장장이 NPC를 통해","§7수리 받아야 합니다.","","§6[명령어]","§e/아이템 수리"), 4,inv);
		removeFlagStack("§e§l영역 설정", 340,0,1,Arrays.asList("§7영역을 지정하여 마을 혹은","§7PVP장, 던전 등등의 가상 공간을","§7형성할 수 있습니다.","§7영역 설정에 따라 최근 방문한","§7영역에서 부활 하거나,","§7영역 입장시 특수 메시지를","§7보낼 수 있습니다.","","§6[명령어]","§e/영역"), 5,inv);
		removeFlagStack("§e§lNPC", 340,0,1,Arrays.asList("§7NPC 생성은 §eCitizens2 §7플러그인을","§7사용하시는 것을 권장합니다.","§7NPC 생성 후, 해당 NPC를 우클릭 하여","§7NPC에 관한 각종 설정을 할 수 있습니다."), 6,inv);
		removeFlagStack("§e§l퀘스트", 340,0,1,Arrays.asList("§7오피박스에서 서버 내의 퀘스트를","§7쉽고 빠르게 제작하실 수 있습니다.","","§6[명령어]","§e/오피박스","§e/퀘스트 생성§3 [타입]§e [퀘스트 이름]","","§3[일반/반복/일일/일주/한달]"), 7,inv);
		removeFlagStack("§e§l이벤트", 340,0,1,Arrays.asList("§7오피박스 1페이지에 있는 §e'이벤트'","§7아이콘을 클릭하여 들어간다면","§7서버에 특정 이벤트를","§7개최 하실 수 있습니다.","§7유저들간 밸런스를 생각하며","§7적당한 이벤트를 열어 줍시다."), 8,inv);
		removeFlagStack("§e§l스킬 [1]", 340,0,1,Arrays.asList("§7오피박스 2페이지에 있는 §e'스킬'","§7아이콘을 클릭하여 들어간다면","§7각종 스킬을 제작하거나 삭제하는","§7GUI 화면이 나타납니다.","§7이곳에서 생성한 각종 스킬들은","§7서버의 주된 시스템에 따라 분류하여","§7게임 내에서 사용 가능하게 되며","§7스킬로 §d커맨드§7를 사용할 수 있으며,","§3MagicSpells 플러그인§7을§3 지원§7합니다."), 9,inv);
		removeFlagStack("§e§l스킬 [2]", 340,0,1,Arrays.asList("§7스킬을 생성 하셨다면","§7스킬 랭크를 만드실 차례입니다.","§7스킬 랭크를 만드셨다면","§7각각의 스킬 랭크에 맞는","§7커맨드 혹은 매직스펠의 스펠,","§7랭크업시 얻는 각종 보너스 스텟,","§7랭크업에 필요한 스킬 포인트 등","§7다양한 옵션을 설정하실 수 있습니다."), 10,inv);
		removeFlagStack("§e§l직업, 그리고 시스템 [1]", 340,0,1,Arrays.asList("§eGoldBigDragon_Advanced","§7(이하 GBD_A)","§7플러그인에는 두 가지 게임 시스템이","§7존재합니다. "+ChatColor.STRIKETHROUGH+"(하이브리드 플러그인)","§7이 둘의 특성은 비슷하면서도","§7서로 다르기에, 시스템 변경시","§7주의를 요합니다."), 11,inv);
		removeFlagStack("§e§l직업, 그리고 시스템 [2]", 340,0,1,Arrays.asList("§c§l[메이플 스토리]","§7첫 번째로 알아보실 시스템은","§7여러분께 친숙한 게임인","§7메이플스토리를 빼닮은","§7메이플스토리형 시스템입니다.","§7서버 내에 각종 직업 클래스를","§7생성하실 수 있으며,","§7직업 클래스 내에서 또다시","§72차 전직, 3차 전직과 같은","§7승급 생성이 가능합니다.","§7또한 직업 및 승급별로 개별적인","§7스킬을 부여할 수 있게 되며,","§7레벨업 시, 스텟 포인트를 지급하며","§7유저는 스텟을 원하는 곳에","§7투자함으로써 강해집니다.","§7전직은 §5Citizens 플러그인§7으로","§7NPC를 생성한 후, 우 클릭시","§7NPC전용 창이 나오며 그곳에서","§6[직업 설정]§7버튼을 누른 다음","§6[전직 교관]§7을 선택할 경우","§7유저들이 해당 NPC를 통해","§7정해진 클래스로 전직이 가능해 집니다."), 12,inv);
		removeFlagStack("§e§l직업, 그리고 시스템 [3]", 340,0,1,Arrays.asList("§f§l[마비노기]","§7두 번째로 알아보실 시스템은","§7저의 본래 목적이었던","§7마비노기 형식의 시스템입니다.","§7마비노기 시스템의 경우","§7메이플스토리 처럼 직업과","§7스텟 포인트 개념이 없지만","§7마음만 먹으면 서버 내의","§7모든 스킬을 배울 수 있는","§7굉장한 자유도를 가지고 있습니다.","§7하지만 메이플 스토리처럼","§7직업을 가지면 스킬을 모두 주는","§7형식이 아니라, 자신이 직접","§5NPC와 대화§7를 하거나","§5특수한 책§7을 읽음으로써","§7스킬을 터득할 수 있습니다.","§7스킬은 컨텐츠별로 나뉘며","§7전투탭에 등록한 스킬을","§7마법탭에 등록할 수 없습니다.","§b환생§7 및§5 특수한 책§7에 대한 내용은","§7다음 가이드에서 설명하겠습니다."), 13,inv);
		removeFlagStack("§e§l직업, 그리고 시스템 [4]", 340,0,1,Arrays.asList("§f§l[마비노기]","§b<환생>","§7메이플 스토리는 만렙이 정해져 있습니다.","§7하지만 마비노기에서 만렙이 있다면","§7모든 스킬을 배울수가 없게 되지요.","§7이런 갭을 극복하기 위한 장치가","§b'환생'§7입니다.","§7환생을 할 경우, 지금까지 쌓은","§7모든 레벨은 '누적 레벨'에 더해지고,","§7일반적인 레벨이 1로 초기화 됩니다.","§7이는 너프가 아닌, 스킬 포인트를","§7더욱 빠르게 모을 수 있는 장치입니다.","§7레벨이 1이 된다면 자신이 모아야 할","§7경험치 량도 적어질테니까요.","§7보통 캐릭터의 나이가 20세가 되면","§7환생이 가능하지만, 이 플러그인에서는","§7소비 아이템인 §e환생 포션§7을","§7지원하고 있습니다."), 14,inv);
		removeFlagStack("§e§l직업, 그리고 시스템 [5]", 340,0,1,Arrays.asList("§f§l[마비노기]","§5<특수한 책>","§7오피박스 2페이지에 있는","§a소비 아이템§7 아이콘을 클릭하면","§7스킬 포인트 주문서에서 부터","§7환생포션 까지의 거의","§7대부분의 소비성 아이템을 만들거나","§7삭제할 수 있으며, 지금 설명드릴","§5특수한 책§7 또한 제작이 가능합니다.","§7특수한책의 경우, 읽으면 바로 소멸 되지만","§7플레이어가 모르던 스킬을","§7익힐 수 있도록 하거나,","§7특정 스텟을 향상시켜주는","§7능력을 가지고 있습니다.","§7그렇기에 서버 내에서 당연","§7중요 거래 품목이 될 가능성이","§7높은 아이템이므로, 당신의","§7적당한 밸런스 조정력이 필요한","§7아이템 1순위라고 보아도","§7과언이 아닙니다.","§7밸런스는 몬스터 드랍률,","§7이벤트를 통한 지급,","§7던전 보상 등으로 조절이","§7가능할 것입니다."), 15,inv);
		removeFlagStack("§e§l개조", 340,0,1,Arrays.asList("§7커스텀 아이템으로 만든","§7아이템 중, 개조 옵션이","§7붙은 아이템일 경우만","§7개조가 가능하며, 개조는","§3[개조 장인]§7을 직업으로 가진","§7NPC에게 받을 수 있습니다.","§7개조 장인에게 오피박스에서 만든","§7개조식을 등록시키면","§7해당 NPC에게서 해당 개조가","§7가능하게 됩니다."), 16,inv);
		removeFlagStack("§e§l룬", 340,0,1,Arrays.asList("§7개조와 비슷한 개념이지만","§7실패 확률이 있으며,","§9룬§7 이라는 아이템이 필요합니다.","§7룬은 오피박스 2페이지에서","§7새로 만들 수 있습니다."), 17,inv);
		//Stack2("§e§l", 340,0,1,Arrays.asList("§7"), 18,inv);
		removeFlagStack("§c            §l버§9§l그§a§l리§e§l포§f§l트            ", 403,0,1,Arrays.asList("",
				"§7심각성 : §4|||||||||||||||||||||||||||||||||||||||| [최고]","§c♩ 광역형 매직스펠 사용시 마법보호 무시 및","§c물리 방어/보호는 매직스펠 Pain 스킬","§c클래스의 대미지를 흡수함","",
				"§7심각성 : §6||||||||||||||||||||"+ChatColor. GRAY+"||||||||||||||||||||§6 [보통]","§e♩ 가끔 나도 모르는 오류가 뜸. 제길","",
				"§7심각성 : §2||||||||||"+ChatColor. GRAY+"||||||||||||||||||||||||||||||§2 [낮음]","§a♩ 마나와 생명력 보너스가 가끔","§a바로바로 업데이트가 되지 않으나","§a맞거나 핫바를 움직이면 새로고쳐짐."), 22,inv);

		removeFlagStack("§4§l[동영상 가이드]", 389,0,1,Arrays.asList("","§b[클릭시 동영상 URL이 보여집니다.]"), 36,inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7작업 관리자 창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	
	//각종 GUI창 속의 아이콘을 눌렸을 때, 해당 아이콘에 기능을 넣는 메소드1   -스텟 GUI, 오피박스, 커스텀 몬스터GUI-//
	public void opBoxGuiInventoryClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if((slot >= 0 && slot <= 8)||(slot >= 45 && slot <= 53))
		{
			if(slot == 2)//월드 시간 낮으로
			{
				SoundEffect.playSound(player, Sound.ENTITY_CHICKEN_AMBIENT, 0.8F, 1.0F);
				Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setTime(0);
				player.sendMessage("§6[System] : "+player.getLocation().getWorld().getName()+" 월드 시간을 낮으로 변경하였습니다!");
			}
			else if(slot == 3)//월드 시간 밤으로
			{
				SoundEffect.playSound(player, Sound.ENTITY_WOLF_HOWL, 0.8F, 1.0F);
				Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setTime(14000);
				player.sendMessage("§6[System] : "+player.getLocation().getWorld().getName()+" 월드 시간을 밤으로 변경하였습니다!");
			}
			else if(slot == 4)//월드 스폰 지점 변경
			{
				SoundEffect.playSound(player, Sound.ENTITY_VILLAGER_YES, 0.8F, 1.0F);
				Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setSpawnLocation((int)player.getLocation().getX(), (int)player.getLocation().getY(), (int)player.getLocation().getZ());
				player.sendMessage("§6[System] : "+player.getLocation().getWorld().getName()+" 월드의 스폰 지점을 "+(int)player.getLocation().getX()+","+(int)player.getLocation().getY()+","+(int)player.getLocation().getZ()+" 로 변경하였습니다!");
			}
			else if(slot == 53)//닫기
			{
				SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(slot == 5)//월드 날씨 맑음
				{
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setStorm(false);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setWeatherDuration(180000);
					player.sendMessage("§6[System] : "+player.getLocation().getWorld().getName()+" 월드 날씨를 맑음으로 변경하였습니다!");
				}
				else if(slot == 6)//월드 날씨 흐림
				{
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setStorm(true);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setWeatherDuration(180000);
					player.sendMessage("§6[System] : "+player.getLocation().getWorld().getName()+" 월드 날씨를 흐림으로 변경하였습니다!");
				}
				else if(slot == 48)//이전 페이지
					opBoxGuiMain(player,(byte) (Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getDisplayName()))-1));
				else if(slot == 50)//다음 페이지
					opBoxGuiMain(player,(byte) (Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getDisplayName()))+1));
			}
		}
		else
		{
			String displayName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(displayName.equals("커스텀 아이템"))
				new CustomItemGui().itemListGui(player, 0);
			else if(displayName.equals("몬스터"))
				new MonsterGui().monsterListGUI(player, 0);
			else if(displayName.equals("서버 설정"))
				opBoxGuiSetting(player);
			else if(displayName.equals("퀘스트"))
				new QuestGui().AllOfQuestListGUI(player, (short) 0,false);
			else if(displayName.equals("스킬"))
				new OPboxSkillGui().AllSkillsGUI(player,(short) 0,false,null);
			else if(displayName.equals("카테고리 및 직업"))
				new JobGUI().ChooseSystemGUI(player);
			else if(displayName.equals("소비 아이템"))
				new UseableItemGui().useableItemListGui(player, 0);
			else if(displayName.equals("영역"))
				new AreaGui().areaListGui(player, (short) 0);
			else if(displayName.equals("던전"))
				new dungeon.DungeonGui().DungeonListMainGUI(player, 0, 52);
			else if(displayName.equals("기능성 개체"))
				new StructureGui().StructureListGUI(player, 0);
			else if(displayName.equals("도박"))
				new GambleGui().gambleMainGui(player);
			else if(displayName.equals("네비게이션"))
				new NavigationGui().navigationListGui(player,(short) 0);
			else if(displayName.equals("워프"))
				new warp.WarpGui().WarpListGUI(player, 0);
			else if(displayName.equals("월드 생성"))
				new admin.WorldCreateGui().worldCreateGuiMain(player);
			else if(displayName.equals("몬스터"))
				new MonsterGui().monsterListGUI(player, 0);
			else if(displayName.equals("초심자"))
				new NewBieGui().newBieGuiMain(player);
			else if(displayName.equals("개조식"))
				new UpgradeGui().upgradeRecipeGui(player,0);
			else if(displayName.equals("이벤트"))
				new EventGui().eventGuiMain(player);
			else if(displayName.equals("게임 성향"))
			{
				YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				configYaml.set("Time.LastSkillChanged", new util.UtilNumber().RandomNum(0, 100000)-new util.UtilNumber().RandomNum(0, 100000));
				if(configYaml.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true) {configYaml.set("Server.Like_The_Mabinogi_Online_Stat_System", false);}
				else{configYaml.set("Server.Like_The_Mabinogi_Online_Stat_System", true);}
				configYaml.saveConfig();
				opBoxGuiMain(player,(byte) 1);
				new job.JobMain().AllPlayerFixAllSkillAndJobYML();
			}
			else if(displayName.equals("플러그인 가이드"))
				guideGui(player);
			else if(displayName.equals("GoldBigDragonRPG"))
			{
				if(MainServerOption.serverVersion.equals(MainServerOption.currentServerVersion)&&MainServerOption.serverUpdate.equals(MainServerOption.currentServerUpdate))
				{
					SoundEffect.playSound(player,Sound.BLOCK_ANVIL_USE, 0.8F, 1.0F);
					player.sendMessage("§e[버전 체크] : 현재 GoldBigDragonRPG는 최신 버전입니다!");
				}
				else
				{
					SoundEffect.playSound(player,Sound.BLOCK_ANVIL_USE, 0.8F, 1.0F);
					player.sendMessage("§c[버전 체크] : 현재 GoldBigDragonRPG는 업데이트가 필요합니다!");
					player.sendMessage("§c[현재 버전] : "+MainServerOption.serverVersion);
					player.sendMessage("§c[최신 버전] : "+MainServerOption.currentServerVersion);
					player.sendMessage("§c[현재 패치] : "+MainServerOption.serverUpdate);
					player.sendMessage("§c[최신 패치] : "+MainServerOption.currentServerUpdate);
				}
			}
			else if(displayName.equals("제작"))
			{
				SoundEffect.playSound(player,Sound.BLOCK_ANVIL_USE, 0.8F, 1.0F);
				player.sendMessage("§c[버전 체크] : 다음 업데이트 때 공개됩니다!");
			}
			
		}
		return;
	}
	
	public void opBoxGuiSettingInventoryClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		if(slot == 53)//닫기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if((slot >= 10 && slot <= 13) || slot == 20 || slot == 24|| slot == 25)
			{
				if(slot == 10)//엔티티 스폰
					configYaml.set("Server.EntitySpawn", configYaml.getBoolean("Server.EntitySpawn")==false);
				else if(slot == 11)//PVP
				{
					configYaml.set("Server.PVP", configYaml.getBoolean("Server.PVP")==false);
					main.MainServerOption.PVP = configYaml.getBoolean("Server.PVP")==false;
				}
				else if(slot == 12)//몬스터 스폰 효과
				{
					if(configYaml.getInt("Server.MonsterSpawnEffect")<7)
						configYaml.set("Server.MonsterSpawnEffect", configYaml.getInt("Server.MonsterSpawnEffect")+1);
					else
						configYaml.set("Server.MonsterSpawnEffect", 0);
				}
				else if(slot == 13)//커스텀 무기 파괴
					configYaml.set("Server.CustomWeaponBreak", configYaml.getBoolean("Server.CustomWeaponBreak")==false);
				else if(slot == 20)//커스텀 블록 설치/설치 금지
					configYaml.set("Server.CustomBlockPlace", configYaml.getBoolean("Server.CustomBlockPlace")==false);
				else if(slot == 24)//폭발 방지 변경
				{
					configYaml.set("Server.AntiExplode", configYaml.getBoolean("Server.AntiExplode")==false);
					main.MainServerOption.AntiExplode = configYaml.getBoolean("Server.AntiExplode")==false;
				}
				else if(slot == 25)//왼손 무기 계산
				{
					configYaml.set("Server.LeftHandWeaponDamageEnable", configYaml.getBoolean("Server.LeftHandWeaponDamageEnable")==false);
					main.MainServerOption.dualWeapon = configYaml.getBoolean("Server.LeftHandWeaponDamageEnable")==false;
				}
				configYaml.saveConfig();
				opBoxGuiSetting(player);
			}
			else
			{
				if(slot == 45)//이전 목록
					opBoxGuiMain(player, (byte) 1);
				else if(slot == 16)//공지사항
					opBoxGuiBroadCast(player, (byte) 0);
				else if(slot == 21)//스텟 이름 변경
					opBoxGuiStatChange(player);
				else if(slot == 22)//금전 시스템 변경
					opBoxGUIMoney(player);
				else if(slot == 23)//사망 시스템 변경
					opBoxGuiDeath(player);
				else
				{
					player.closeInventory();
					UserDataObject u = new UserDataObject();
					if(slot == 14)//입장 메시지 변경
					{
						u.setString(player, (byte)1,"JM");
						player.sendMessage("§a[SYSTEM] : 입장 메시지를 입력 해 주세요! (§f없음§a 입력시 입장 메시지 제거)");
					}
					else if(slot == 15)//퇴장 메시지 변경
					{
						u.setString(player, (byte)1,"QM");
						player.sendMessage("§a[SYSTEM] : 퇴장 메시지를 입력 해 주세요! (§f없음§a 입력시 퇴장 메시지 제거)");
					}
					else if(slot == 19)//채팅 형태 변경
					{
						if(event.isRightClick())
						{
							SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
							configYaml.removeKey("Server.ChatPrefix");
							configYaml.saveConfig();
							player.sendMessage("§c[SYSTEM] : 접두사를 삭제하였습니다!");
							opBoxGuiSetting(player);
							return;
						}
						u.setString(player, (byte)1,"CCP");
						player.sendMessage("§a[SYSTEM] : 채팅 형태를 입력 해 주세요!");
						player.sendMessage("§6%t%§f - 현재 시각 지칭하기 -");
						player.sendMessage("§6%gm%§f - 게임모드 지칭하기 -");
						player.sendMessage("§6%ct%§f - 채팅 타입 지칭하기 -");
						player.sendMessage("§6%lv%§f - 레벨 지칭하기 -");
						player.sendMessage("§6%rlv%§f - 누적 레벨 지칭하기 - (서버 성향이 메이플스토리일 경우 쓸모 없음.)");
						player.sendMessage("§6%job%§f - 직업 지칭하기 - (서버 성향이 마비노기일 경우 쓸모 없음.)");
						player.sendMessage("§6%message%§f - 채팅 내용 지칭하기 -");
					}
					u.setType(player, "System");
					player.sendMessage("§6%player%§f - 플레이어 지칭하기 -");
					player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
					"§3&3 §4&4 §5&5 " +
							"§6&6 §7&7 §8&8 " +
					"§9&9 §a&a §b&b §c&c " +
							"§d&d §e&e §f&f");
				}
			}
		}
		return;
	}
	
	public void opBoxGuideInventoryClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		if(slot==53)//닫기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot==36)//동영상 가이드
			{
				player.closeInventory();
				player.sendMessage("§4§l[YouTube] §f§l: §9§lhttps://www.youtube.com/playlist?list=PLxqihkJXVJABIlxU3n6bNhhC8x6xPbORP   §e§l[클릭시 가이드 페이지로 연결됩니다]");
			}
			if(slot==45)//이전 목록
				opBoxGuiMain(player,(byte) 1);
		}
	}

	public void opBoxGuiBroadCastClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				opBoxGuiSetting(player);
			else if(slot == 46)//공지 간격
			{
				player.closeInventory();
				player.sendMessage("§3[공지] : 몇 초마다 공지를 띄울까요?");
				player.sendMessage("§e(최소 1초 ~ 최대 3600초(1시간) 이하 숫자를 입력 하세요!)");
				UserDataObject u = new UserDataObject();
				u.setType(player, "System");
				u.setString(player, (byte)1, "BMT");
			}
			else if(slot == 48)//이전 페이지
				opBoxGuiBroadCast(player, (byte) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 49)//새 공지
			{
				YamlLoader broadcastYaml = new YamlLoader();
				broadcastYaml.getConfig("BroadCast.yml");
				int broadCastNumber = broadcastYaml.getKeys().size();
				broadcastYaml.set(broadCastNumber+"", "§e[새로운 공지사항 설정 중]");
				broadcastYaml.saveConfig();
				player.closeInventory();
				player.sendMessage("§3[공지] : 새로운 공지 사항을 입력 해 주세요!");
				player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
				"§3&3 §4&4 §5&5 " +
						"§6&6 §7&7 §8&8 " +
				"§9&9 §a&a §b&b §c&c " +
						"§d&d §e&e §f&f");
				UserDataObject u = new UserDataObject();
				u.setType(player, "System");
				u.setString(player, (byte)1, "NBM");
				u.setInt(player, (byte)0, broadCastNumber);
			}
			else if(slot == 50)//다음 페이지
				opBoxGuiBroadCast(player, (byte) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])));
			else if(event.isShiftClick() && event.isRightClick())
			{
				SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
				YamlLoader broadcastYaml = new YamlLoader();
				broadcastYaml.getConfig("BroadCast.yml");
				int acount =  broadcastYaml.getKeys().size()-1;
				int number = (((Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1)*45)+event.getSlot());
				for(int counter = number;counter <acount;counter++)
					broadcastYaml.set(counter+"", broadcastYaml.get((counter+1)+""));
				broadcastYaml.removeKey(acount+"");
				broadcastYaml.saveConfig();
				opBoxGuiBroadCast(player, (byte) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
			}
		}
	}

	public void opBoxGuiStatChangeClick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		int slot = event.getSlot();
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				opBoxGuiSetting(player);
			else
			{
				player.closeInventory();
	
				if(slot >=9 && slot <=13)
				{
					player.sendMessage("§3[System] : 새로운 "+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+"을 입력 해 주세요!");
					player.sendMessage("§6%enter%§f - 한줄 띄어 쓰기 -");
					player.sendMessage("§6%stat%§f - 스텟 이름 쓰기 -");
				}
				else
				{
					player.sendMessage("§3[System] : 새로운 "+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+" 스텟 이름을 입력 해 주세요!");
					player.sendMessage("§7(띄어 쓰기 및 기호 사용 불가)");
				}
				UserDataObject u = new UserDataObject();
				u.setType(player, "System");
				u.setString(player, (byte)1, "CSN");
				u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
		}
	}

	public void opBoxGuiMoneyClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 45)//이전 목록
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			opBoxGuiSetting(player);
		}
		else if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 34)//화폐 모양 초기화
		{
			YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			
			SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_ATTACK, 0.8F, 1.0F);
			player.sendMessage("§3[System] : 화폐 모양이 초기화 되었습니다!");

			configYaml.set("Server.Money.1.ID",348);
			MainServerOption.Money1ID = 348;
			configYaml.set("Server.Money.2.ID",371);
			MainServerOption.Money2ID = 371;
			configYaml.set("Server.Money.3.ID",147);
			MainServerOption.Money3ID = 147;
			configYaml.set("Server.Money.4.ID",266);
			MainServerOption.Money4ID = 266;
			configYaml.set("Server.Money.5.ID",41);
			MainServerOption.Money5ID = 41;
			configYaml.set("Server.Money.6.ID",41);
			MainServerOption.Money6ID = 41;
			configYaml.set("Server.Money.1.DATA",0);
			MainServerOption.Money1DATA = 0;
			configYaml.set("Server.Money.2.DATA",0);
			MainServerOption.Money2DATA = 0;
			configYaml.set("Server.Money.3.DATA",0);
			MainServerOption.Money3DATA = 0;
			configYaml.set("Server.Money.4.DATA",0);
			MainServerOption.Money4DATA = 0;
			configYaml.set("Server.Money.5.DATA",0);
			MainServerOption.Money5DATA = 0;
			configYaml.set("Server.Money.6.DATA",0);
			MainServerOption.Money6DATA = 0;

			configYaml.saveConfig();
			opBoxGUIMoney(player);
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			UserDataObject u = new UserDataObject();
			u.setType(player, "System");
			if(slot >= 28 && slot <= 33)
			{
				if(slot == 28)
					u.setInt(player, (byte)1,50);
				else if(slot == 29)
					u.setInt(player, (byte)1,100);
				else if(slot == 30)
					u.setInt(player, (byte)1,1000);
				else if(slot == 31)
					u.setInt(player, (byte)1,10000);
				else if(slot == 32)
					u.setInt(player, (byte)1,50000);
				else if(slot == 33)
					u.setInt(player, (byte)1,-1);
				player.sendMessage("§3[System] : 화폐 모양으로 설정할 아이템 ID를 입력 해 주세요!");
				u.setString(player, (byte)1, "CMID");
			}
			else
			{
				if(slot == 11)
				{
					player.sendMessage("§3[System] : 최대 얼마까지만 드랍 가능하도록 할까요?");
					player.sendMessage("§7(최소 1000(1천)원 이상, 최대 1000000000(1억)원 이하)");
					u.setString(player, (byte)1, "CDML");
				}
				if(slot == 13)
				{
					player.sendMessage("§3[System] : 새로운 화폐 단위를 입력 해 주세요!");
					player.sendMessage("§7(띄어 쓰기 및 기호 사용 불가)");
					u.setString(player, (byte)1, "CSN");
					u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
			}
			player.closeInventory();
		}
		return;
	}

	public void opBoxGuiDeathClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 19)//사망 BGM
			{
				YamlLoader configYaml = new YamlLoader();
			  	configYaml.getConfig("config.yml");
				if(configYaml.getInt("Death.Track")==-1||configYaml.contains("Death.Track")==false)
					new area.AreaGui().areaMusicSettingGui(player, 0, "DeathBGM¡");
				else
				{
					configYaml.set("Death.Track", -1);
					configYaml.saveConfig();
					opBoxGuiDeath(player);
				}
			}
			else if(slot == 21)//제자리 부활 옵션 켜기/끄기
			{
				YamlLoader configYaml = new YamlLoader();
			  	configYaml.getConfig("config.yml");
				if(configYaml.getBoolean("Death.DistrictDirectRevive"))
					configYaml.set("Death.DistrictDirectRevive",false);
				else
					configYaml.set("Death.DistrictDirectRevive",true);
				configYaml.saveConfig();
				opBoxGuiDeath(player);
			}
			else if(slot == 23)//구조 아이템 설정
				opBoxGuiSettingRescueItem(player);
			else if(slot == 25)//부활 아이템 설정
				opBoxGuiSettingReviveItem(player);
			else if(slot == 31)//사망 시스템 온/오프
			{
				YamlLoader configYaml = new YamlLoader();
			  	configYaml.getConfig("config.yml");
				if(configYaml.getBoolean("Death.SystemOn"))
					configYaml.set("Death.SystemOn",false);
				else
					configYaml.set("Death.SystemOn",true);
				configYaml.saveConfig();
				opBoxGuiDeath(player);
			}
			else if(slot == 45)//이전 목록
				opBoxGuiSetting(player);
			else
			{
				UserDataObject u = new UserDataObject();
				u.setType(player, "System");
				if(slot == 10)//가까운 마을에서 부활
				{
					u.setString(player, (byte)1, "RO_S_H");
					player.sendMessage("§a[부활] : 마지막 마을에서 부활할 경우, 몇 %의 §e생명력§a을 가지고 부활 하도록 하겠습니까?");
				}
				else if(slot == 12)//제자리에서 부활
				{
					u.setString(player, (byte)1, "RO_T_H");
					player.sendMessage("§a[부활] : 제자리에서 부활할 경우, 몇 %의 §e생명력§a을 가지고 부활 하도록 하겠습니까?");
				}
				else if(slot == 14)//도움 요청
				{
					u.setString(player, (byte)1, "RO_H_H");
					player.sendMessage("§a[부활] : 도움을 받아 부활할 경우, 몇 %의 §e생명력§a을 가지고 부활 하도록 하겠습니까?");
				}
				else if(slot == 16)//아이템 사용
				{
					u.setString(player, (byte)1, "RO_I_H");
					player.sendMessage("§a[부활] : 아이템을 사용하여 부활할 경우, 몇 %의 §e생명력§a을 가지고 부활 하도록 하겠습니까?");
				}
				player.sendMessage("§7(최소 1 ~ 최대 100)");
				player.closeInventory();
			}
		}
	}

	public void opBoxGuiRescueOrReviveClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		if(!event.getClickedInventory().getTitle().equals("container.inventory"))
		{
			if(slot != 4)
				event.setCancelled(true);
			if(slot == 8)//나가기
			{
				SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(slot == 0)//이전 목록
					opBoxGuiDeath(player);
			}
		}
	}
	
	
	public void opBoxGuiRescueOrReviveClose(InventoryCloseEvent event, String subjectCode)
	{
		YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
		if(event.getInventory().getItem(4) != null)
		{
			if(subjectCode.equals("06"))//부활
			{
				configYaml.set("Death.ReviveItem", event.getInventory().getItem(4));
				MainServerOption.DeathRevive = event.getInventory().getItem(4);
			}
			else
			{
				configYaml.set("Death.RescueItem", event.getInventory().getItem(4));
				MainServerOption.DeathRescue = event.getInventory().getItem(4);
			}
		}
		else
		{
			if(subjectCode.equals("06"))//부활
			{
				configYaml.set("Death.ReviveItem", null);
				MainServerOption.DeathRevive = null;
			}
			else
			{
				configYaml.set("Death.RescueItem", null);
				MainServerOption.DeathRescue = null;
			}
		}
		if(subjectCode.equals("06"))//부활
			event.getPlayer().sendMessage("§a[SYSTEM] : 부활 아이템 설정이 완료되었습니다!");
		else
			event.getPlayer().sendMessage("§a[SYSTEM] : 구조 아이템 설정이 완료되었습니다!");
		configYaml.saveConfig();
		SoundEffect.playSound((Player)event.getPlayer(), Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
		return;
	}
}
