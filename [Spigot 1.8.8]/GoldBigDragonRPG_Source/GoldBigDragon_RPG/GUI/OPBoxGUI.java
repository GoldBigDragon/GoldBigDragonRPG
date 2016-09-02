package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.CustomItem.ItemGUI;
import GoldBigDragon_RPG.CustomItem.UseableItemGUI;
import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Monster.MonsterGUI;
import GoldBigDragon_RPG.Quest.QuestGUI;
import GoldBigDragon_RPG.Skill.OPBoxSkillGUI;
import GoldBigDragon_RPG.Structure.StructureGUI;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class OPBoxGUI extends GUIutil
{
	public void OPBoxGUI_Main (Player player, byte page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "관리자 도구 : "+page+" / 3");
		
		Stack2(ChatColor.BLACK +""+page, 160,11,1,null, 0, inv);
		Stack2(" ", 160,11,1,null, 1, inv);
		Stack2(ChatColor.RED +""+ ChatColor.BOLD + "[월드 시간 변경 -낮-]", 160,4,1,Arrays.asList(ChatColor.WHITE +"현재 월드 시간을 낮으로",ChatColor.WHITE +"변경 시킵니다."), 2, inv);
		Stack2(ChatColor.GRAY +""+ ChatColor.BOLD + "[월드 시간 변경 -밤-]", 160,4,1,Arrays.asList(ChatColor.WHITE +"현재 월드 시간을 밤으로",ChatColor.WHITE +"변경 시킵니다."), 3, inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "[현재 위치를 스폰으로]", 160,4,1,Arrays.asList(ChatColor.WHITE +"현재 위치를 스폰 지점으로",ChatColor.WHITE +"변경 시킵니다."), 4, inv);
		Stack2(ChatColor.DARK_AQUA +""+ ChatColor.BOLD + "[월드 날씨 변경 -맑음-]", 160,4,1,Arrays.asList(ChatColor.WHITE +"현재 월드 날씨를 맑게",ChatColor.WHITE +"변경 시킵니다."), 5, inv);
		Stack2(ChatColor.GRAY +""+ ChatColor.BOLD + "[월드 날씨 변경 -흐림-]", 160,4,1,Arrays.asList(ChatColor.WHITE +"현재 월드 날씨를 흐리게",ChatColor.WHITE +"변경 시킵니다."), 6, inv);
		Stack2(" ", 160,11,1,null, 7, inv);
		Stack2(" ", 160,11,1,null, 8, inv);
		Stack2(" ", 160,11,1,null, 9, inv);
		Stack2(" ", 160,11,1,null, 18, inv);
		Stack2(" ", 160,11,1,null, 17, inv);
		Stack2(" ", 160,11,1,null, 26, inv);
		Stack2(" ", 160,11,1,null, 27, inv);
		Stack2(" ", 160,11,1,null, 36, inv);
		Stack2(" ", 160,11,1,null, 35, inv);
		Stack2(" ", 160,11,1,null, 36, inv);
		Stack2(" ", 160,11,1,null, 37, inv);
		Stack2(" ", 160,11,1,null, 38, inv);
		Stack2(" ", 160,11,1,null, 39, inv);
		Stack2(" ", 160,11,1,null, 40, inv);
		Stack2(" ", 160,11,1,null, 41, inv);
		Stack2(" ", 160,11,1,null, 42, inv);
		Stack2(" ", 160,11,1,null, 43, inv);
		Stack2(" ", 160,11,1,null, 44, inv);

		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "플러그인 가이드", 340,0,1,Arrays.asList(ChatColor.YELLOW + "GoldBigDragonAdvanced",ChatColor.WHITE +"플러그인에 대한",ChatColor.WHITE +"도움말을 봅니다."), 22, inv);
		
		switch(page)
		{
			case 1:
				ItemStackStack(getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+"GoldBigDragonRPG", 1, Arrays.asList("",ChatColor.YELLOW+"[버전]",ChatColor.WHITE+""+ChatColor.BOLD+ServerOption.serverVersion,"",ChatColor.YELLOW+"[패치]",ChatColor.WHITE+""+ChatColor.BOLD+ServerOption.serverUpdate), "GoldBigDragon"), 10, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "서버 설정", 137,0,1,Arrays.asList(ChatColor.GRAY +"서버에 대한 전반적인 설정을 합니다."), 12, inv);
				//Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "던전", 52,0,1,Arrays.asList(ChatColor.GRAY +"던전의 생성과 삭제는 물론,",ChatColor.GRAY +"몬스터 설정 및 보상을 관리합니다."), 14, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터", 397,4,1,Arrays.asList(ChatColor.GRAY +"커스텀 몬스터를 생성하거나",ChatColor.GRAY +"스폰 에그를 얻습니다."), 14, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "이벤트", 401,0,1,Arrays.asList(ChatColor.GRAY +"이벤트 개최시 접속된 모든",ChatColor.GRAY +"플레이어들에게 알려지며,",ChatColor.GRAY +"이후 입장하는 모든 플레이어에게",ChatColor.GRAY +"이벤트 진행을 알려줍니다."), 16, inv);
				if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
				{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "게임 성향", 346,0,1,Arrays.asList(ChatColor.WHITE + "[마비노기]",ChatColor.GRAY+"환생과 누적레벨이 존재하며,",ChatColor.GRAY+"스텟을 임의로 올릴 수 없습니다.",ChatColor.RED+"플레이어의 스텟 데이터가 이어지므로",ChatColor.RED+"변경시 주의가 필요합니다."), 28, inv);}
				else
				{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "게임 성향", 40,0,1,Arrays.asList(ChatColor.GOLD + "[메이플 스토리]",ChatColor.GRAY+"스텟을 임의로 올릴 수 있으며,",ChatColor.GRAY+"누적레벨과 환생이 존재하지 않습니다.",ChatColor.RED+"플레이어의 스텟 데이터가 이어지므로",ChatColor.RED+"변경시 주의가 필요합니다."), 28, inv);}	}
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "커스텀 아이템", 389,0,1,Arrays.asList(ChatColor.WHITE + "커스텀 아이템을 생성하거나",ChatColor.WHITE+"클릭하여 지급 받습니다.","",ChatColor.YELLOW+"[좌 클릭시 아이템 받기]",ChatColor.YELLOW+"[Shift + 좌 클릭시 아이템 수정]",ChatColor.RED+"[Shift + 우 클릭시 아이템 삭제]"), 30, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "네비게이션", 358,120,1,Arrays.asList(ChatColor.WHITE + "길찾기 시스템을 관리합니다."), 32, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "퀘스트", 403,0,1,Arrays.asList(ChatColor.WHITE + "퀘스트를 새로 만들거나",ChatColor.WHITE+"수정하거나 삭제합니다."), 34, inv);
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
				break;

			case 2:
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "스킬", 403,0,1,Arrays.asList(ChatColor.GRAY +"직업 혹은 카테고리에 등록 가능한",ChatColor.GRAY+"스킬들을 등록합니다."), 10, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "카테고리 및 직업", 397,3,1,Arrays.asList(ChatColor.GRAY +"스킬과 직업에 관한 설정을 합니다."), 12, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "소비 아이템", 260,0,1,Arrays.asList(ChatColor.GRAY +"우 클릭 혹은 단축키를 통해",ChatColor.GRAY+"사용 가능한 아이템을 관리합니다."), 14, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "영역", 395,0,1,Arrays.asList(ChatColor.GRAY +"영역 지정을 통하여 구역마다",ChatColor.GRAY+"특수한 옵션을 설정할 수 있습니다."), 16, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "개조식", 145,2,1,Arrays.asList(ChatColor.GRAY +"무기의 개조 방식을 새로 만들거나",ChatColor.GRAY+"삭제할 수 있습니다."), 28, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "초심자", 54,0,1,Arrays.asList(ChatColor.GRAY +"서버에 처음 들어온 플레이어에",ChatColor.GRAY+"대하여 설정을 합니다."), 30, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "월드 생성", 2,0,1,Arrays.asList(ChatColor.GRAY +"새로운 월드를 생성합니다."), 32, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "워프", 345,0,1,Arrays.asList(ChatColor.GRAY +"워프 지점을 생성하거나, 이동합니다."), 34, inv);

				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
				break;
			case 3:
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "도박", 266,0,1,Arrays.asList(ChatColor.GRAY +"도박 관련 기능을 봅니다."), 10, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "기능성 개체", 130,0,1,Arrays.asList(ChatColor.GRAY +"각종 기능을 가진 개체들을",ChatColor.GRAY +"설치하거나 제거합니다."), 12, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "던전", 52,0,1,Arrays.asList(ChatColor.GRAY +"인스 턴스 던전에 대한 설정을 합니다."), 14, inv);
				
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
				break;
			
		}

		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_Setting(Player player)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "관리자 옵션");
		
		Stack2(" ", 160,11,1,null, 0, inv);
		Stack2(" ", 160,11,1,null, 1, inv);
		Stack2(" ", 160,11,1,null, 2, inv);
		Stack2(" ", 160,11,1,null, 3, inv);
		Stack2(" ", 160,11,1,null, 4, inv);
		Stack2(" ", 160,11,1,null, 5, inv);
		Stack2(" ", 160,11,1,null, 6, inv);
		Stack2(" ", 160,11,1,null, 7, inv);
		Stack2(" ", 160,11,1,null, 8, inv);
		Stack2(" ", 160,11,1,null, 9, inv);
		Stack2(" ", 160,11,1,null, 18, inv);
		Stack2(" ", 160,11,1,null, 17, inv);
		Stack2(" ", 160,11,1,null, 26, inv);
		Stack2(" ", 160,11,1,null, 27, inv);
		Stack2(" ", 160,11,1,null, 36, inv);
		Stack2(" ", 160,11,1,null, 35, inv);
		Stack2(" ", 160,11,1,null, 36, inv);
		Stack2(" ", 160,11,1,null, 37, inv);
		Stack2(" ", 160,11,1,null, 38, inv);
		Stack2(" ", 160,11,1,null, 39, inv);
		Stack2(" ", 160,11,1,null, 40, inv);
		Stack2(" ", 160,11,1,null, 41, inv);
		Stack2(" ", 160,11,1,null, 42, inv);
		Stack2(" ", 160,11,1,null, 43, inv);
		Stack2(" ", 160,11,1,null, 44, inv);

		if(Config.getBoolean("Server.EntitySpawn") == true)
		{Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "엔티티 스폰", 52,0,1,Arrays.asList(ChatColor.GREEN+"[활성화]",ChatColor.GRAY +"해당 옵션이 비활성화 상태일 경우",ChatColor.GRAY+"더이상 엔티티들이 소환되지 않습니다."), 10, inv);}
		else
		{{Stack2(ChatColor.RED +""+ ChatColor.BOLD + "엔티티 스폰", 166,0,1,Arrays.asList(ChatColor.RED+"[비 활성화]",ChatColor.GRAY +"해당 옵션이 비활성화 상태일 경우",ChatColor.GRAY+"더이상 엔티티들이 소환되지 않습니다."), 10, inv);}	}
		
		if(Config.getBoolean("Server.PVP") == true)
		{Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "유저간 PVP", 276,0,1,Arrays.asList(ChatColor.GREEN+"[활성화]",ChatColor.GRAY +"현재 유저간 전투가 허용 됩니다."), 11, inv);}
		else
		{{Stack2(ChatColor.RED +""+ ChatColor.BOLD + "유저간 PVP", 166,0,1,Arrays.asList(ChatColor.RED+"[비 활성화]",ChatColor.GRAY +"현재 유저간 전투가 허용되지 않습니다."), 11, inv);}	}
		switch(Config.getInt("Server.MonsterSpawnEffect"))
		{
			case 0 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터 스폰 효과", 383,0,1,Arrays.asList(ChatColor.WHITE + "[없음]"), 12, inv); break;
			case 1 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터 스폰 효과", 383,66,1,Arrays.asList(ChatColor.BLUE + "[마법]",ChatColor.RED + "※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 2 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터 스폰 효과", 383,58,1,Arrays.asList(ChatColor.DARK_PURPLE + "[엔더]",ChatColor.RED + "※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 3 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터 스폰 효과", 383,50,1,Arrays.asList(ChatColor.GREEN+ "[폭발]",ChatColor.RED + "※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 4 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터 스폰 효과", 383,61,1,Arrays.asList(ChatColor.DARK_RED + "[화염]",ChatColor.RED + "※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 5 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터 스폰 효과", 383,120,1,Arrays.asList(ChatColor.RED + "[화남]",ChatColor.RED + "※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 6 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터 스폰 효과", 383,90,1,Arrays.asList(ChatColor.LIGHT_PURPLE + "[게이]",ChatColor.RED + "※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
			case 7 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "몬스터 스폰 효과", 50,0,1,Arrays.asList(ChatColor.GOLD + "[골빅]",ChatColor.RED + "※ 서버 렉 유발의 원인이 됩니다!"), 12, inv); break;
		}

		if(Config.getBoolean("Server.CustomWeaponBreak") == true)
		{Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "커스텀 무기 파괴", 268,50,1,Arrays.asList(ChatColor.GREEN+"[활성화]",ChatColor.GRAY +"커스텀 무기의 내구도가 0이 될 경우",ChatColor.GRAY+"일반 무기와 같이 파괴됩니다."), 13, inv);}
		else
		{{Stack2(ChatColor.RED +""+ ChatColor.BOLD + "커스텀 무기 파괴", 166,0,1,Arrays.asList(ChatColor.RED+"[비 활성화]",ChatColor.GRAY +"커스텀 무기는 내구도가 0이 되어도",ChatColor.GRAY+"파괴되지 않습니다."), 13, inv);}	}

		if(Config.getString("Server.JoinMessage") != null)
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "입장 메시지", 386,0,1,Arrays.asList(ChatColor.GREEN+"[적용]",ChatColor.GRAY +Config.getString("Server.JoinMessage"),"",ChatColor.YELLOW+"[좌 클릭시 입장 메시지 변경]"), 14, inv);
		else
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "입장 메시지", 166,0,1,Arrays.asList(ChatColor.RED+"[제거]",ChatColor.GRAY + "입장 메시지가 없습니다.","",ChatColor.YELLOW+"[좌 클릭시 입장 메시지 등록]"), 14, inv);

		if(Config.getString("Server.QuitMessage") != null)
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "퇴장 메시지", 386,0,1,Arrays.asList(ChatColor.GREEN+"[적용]",ChatColor.GRAY +Config.getString("Server.QuitMessage"),"",ChatColor.YELLOW+"[좌 클릭시 퇴장 메시지 변경]"), 15, inv);
		else
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "퇴장 메시지", 166,0,1,Arrays.asList(ChatColor.RED+"[제거]",ChatColor.GRAY + "퇴장 메시지가 없습니다.","",ChatColor.YELLOW+"[좌 클릭시 퇴장 메시지 등록]"), 15, inv);
		Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "공지사항", 323,0,1,Arrays.asList(ChatColor.GRAY + "서버에 일정 시간마다",ChatColor.GRAY+"공지사항을 알립니다.","",ChatColor.YELLOW+"[좌 클릭시 공지사항 설정]"), 16, inv);

		if(Config.getString("Server.ChatPrefix")==null)
			ItemStackStack(getPlayerSkull(ChatColor.GREEN +""+ ChatColor.BOLD + "채팅 형태", 1, Arrays.asList(ChatColor.GRAY+"채팅 형태를 변경합니다.",ChatColor.GRAY+"단, 형태를 변경할 경우",ChatColor.GRAY+"모든 일반 채팅이 브로드",ChatColor.GRAY+"캐스트 형식으로 변경되므로",ChatColor.GRAY+"주의해야 합니다.","",ChatColor.DARK_AQUA+"[현재 채팅 형태]",ChatColor.WHITE+"[   없음   ]","",ChatColor.YELLOW+"[좌 클릭시 접두사 변경]",ChatColor.RED+"[우 클릭시 접두사 제거]","",ChatColor.GREEN+"[코드 제공]",ChatColor.WHITE+""+ChatColor.BOLD+"B4TT3RY"), "B4TT3RY__"), 19, inv);
		else
		{
			String Prefix = Config.getString("Server.ChatPrefix");
			Prefix=Prefix.replace("%t%","[시각]");
			Prefix=Prefix.replace("%gm%","[게임 모드]");
			Prefix=Prefix.replace("%ct%","[채팅 타입]");
			Prefix=Prefix.replace("%lv%","[레벨]");
			Prefix=Prefix.replace("%rlv%","[누적 레벨]");
			Prefix=Prefix.replace("%job%","[직업]");
			Prefix=Prefix.replace("%player%","[닉네임]");
			Prefix=Prefix.replace("%message%", "[내용]");
			ItemStackStack(getPlayerSkull(ChatColor.GREEN +""+ ChatColor.BOLD + "채팅 형태", 1, Arrays.asList(ChatColor.GRAY+"채팅 형태를 변경합니다.",ChatColor.GRAY+"단, 형태를 변경할 경우",ChatColor.GRAY+"모든 일반 채팅이 브로드",ChatColor.GRAY+"캐스트 형식으로 변경되므로",ChatColor.GRAY+"주의해야 합니다.","",ChatColor.DARK_AQUA+"[현재 채팅 형태]",ChatColor.WHITE+Prefix,"",ChatColor.YELLOW+"[좌 클릭시 접두사 변경]",ChatColor.RED+"[우 클릭시 접두사 제거]","",ChatColor.GREEN+"[코드 제공]",ChatColor.WHITE+""+ChatColor.BOLD+"B4TT3RY"), "B4TT3RY__"), 19, inv);
		}

		if(Config.getBoolean("Server.CustomBlockPlace") == true)
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "커스텀 블록 설치", 1,0,1,Arrays.asList(ChatColor.GREEN+"[가능]",ChatColor.GRAY +"아이템에 설명이 붙어 있거나",ChatColor.GRAY+"이름이 변경된 아이템이",ChatColor.GRAY+"설치되는 것을 막지 않습니다."), 20, inv);
		else
			Stack2(ChatColor.RED +""+ ChatColor.BOLD + "커스텀 블록 설치", 166,0,1,Arrays.asList(ChatColor.RED+"[불가능]",ChatColor.GRAY +"아이템에 설명이 붙어 있거나",ChatColor.GRAY+"이름이 변경된 아이템이",ChatColor.GRAY+"설치되는 것을 막습니다."), 20, inv);

		Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "스텟 이름 변경", 340,0,1,Arrays.asList(ChatColor.GRAY + "서버의 스텟 이름을",ChatColor.GRAY+"마음대로 변경합니다.","",ChatColor.YELLOW+"[좌 클릭시 스텟 이름 변경]","",ChatColor.RED+"[       주의       ]",ChatColor.RED+"스텟 이름 변경시 이전에 뿌려진",ChatColor.RED+"커스텀 아이템의 능력치는",ChatColor.RED+"모두 무효 처리가 되며,",ChatColor.RED+"스킬별 스텟 영향 옵션을",ChatColor.RED+"재 설정 하여야 합니다."), 21, inv);

		Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "금전 시스템 변경", 266,0,1,Arrays.asList(ChatColor.GRAY + "화폐에 관련된 시스템을",ChatColor.GRAY+"변경합니다.","",ChatColor.YELLOW+"[좌 클릭시 화폐 시스템GUI 이동]"), 22, inv);

		Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "사망 시스템 변경", 397,0,1,Arrays.asList(ChatColor.GRAY + "사망과 관련된 시스템을",ChatColor.GRAY+"변경합니다.","",ChatColor.YELLOW+"[좌 클릭시 사망 시스템GUI 이동]"), 23, inv);

		if(GoldBigDragon_RPG.Main.ServerOption.AntiExplode)
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "폭발 방지", 46,0,1,Arrays.asList(ChatColor.GREEN+"[활성화]",ChatColor.GRAY + "크리퍼, TNT, 엔더 크리스탈로 인한",ChatColor.GRAY+"블록 파괴를 방지합니다."), 24, inv);
		else
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "폭발 방지", 166,0,1,Arrays.asList(ChatColor.RED+"[비 활성화]",ChatColor.GRAY + "크리퍼, TNT, 엔더 크리스탈로 인한",ChatColor.GRAY+"블록 파괴를 방치합니다."), 24, inv);

		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_BroadCast(Player player, byte page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager BroadCast =YC.getNewConfig("BroadCast.yml");

		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "관리자 공지사항 : " + (page+1));

		if(BroadCast.contains("0"))
		{
			Object[] BroadCastList= BroadCast.getConfigurationSection("").getKeys(false).toArray();
			byte loc=0;
			for(short count = (short) (page*45); count < BroadCastList.length;count++)
			{				
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + count, 340,0,1,Arrays.asList(BroadCast.getString(count+"")
						,"",ChatColor.RED+"[Shift + 우클릭시 알림 삭제]"), loc, inv);
				
				loc++;
			}

			if(BroadCastList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		}
		YamlManager Config =YC.getNewConfig("config.yml");

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "공지 간격", 152,0,1,Arrays.asList(ChatColor.GRAY + "매 "+ChatColor.GOLD+Config.getInt("Server.BroadCastSecond")+ChatColor.GRAY+"초마다 공지"), 46, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 공지", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 공지사항을 생성합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_StatChange(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "관리자 스텟 설정");

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "체력", 267,0,1,Arrays.asList(ChatColor.GRAY + "[  현재 이름  ]",ChatColor.WHITE+ServerOption.STR), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "솜씨", 261,0,1,Arrays.asList(ChatColor.GRAY + "[  현재 이름  ]",ChatColor.WHITE+ServerOption.DEX), 1, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "지력", 369,0,1,Arrays.asList(ChatColor.GRAY + "[  현재 이름  ]",ChatColor.WHITE+ServerOption.INT), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "의지", 370,0,1,Arrays.asList(ChatColor.GRAY + "[  현재 이름  ]",ChatColor.WHITE+ServerOption.WILL), 3, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "행운", 322,0,1,Arrays.asList(ChatColor.GRAY + "[  현재 이름  ]",ChatColor.WHITE+ServerOption.LUK), 4, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "대미지", 276,0,1,Arrays.asList(ChatColor.GRAY + "[  현재 이름  ]",ChatColor.WHITE+ServerOption.Damage), 5, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "마법 대미지", 403,0,1,Arrays.asList(ChatColor.GRAY + "[  현재 이름  ]",ChatColor.WHITE+ServerOption.MagicDamage), 6, inv);
		
		
		String lore = ServerOption.STR_Lore;
		lore = lore.replace("%stat%", ServerOption.STR);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "체력 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 9, inv);
		lore = ServerOption.DEX_Lore;
		lore = lore.replace("%stat%", ServerOption.DEX);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "솜씨 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 10, inv);
		lore = ServerOption.INT_Lore;
		lore = lore.replace("%stat%", ServerOption.INT);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "지력 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 11, inv);
		lore = ServerOption.WILL_Lore;
		lore = lore.replace("%stat%", ServerOption.WILL);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "의지 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 12, inv);
		lore = ServerOption.LUK_Lore;
		lore = lore.replace("%stat%", ServerOption.LUK);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "행운 설명", 323,0,1,Arrays.asList(lore.split("%enter%")), 13, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_Money(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "관리자 화폐 설정");

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "드랍 최대 액수 제한", 166,0,1,Arrays.asList(ChatColor.GRAY + "드랍 가능한 최대 금액을",ChatColor.GRAY+"설정합니다.","",ChatColor.YELLOW + "[     현재 최대 드랍     ]",ChatColor.GRAY +" "+Config.getInt("Server.Max_Drop_Money")), 11, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "화폐", 421,0,1,Arrays.asList("",ChatColor.GRAY + "[     현재 이름     ]"," "+ChatColor.WHITE+ServerOption.Money), 13, inv);
	
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "화폐 모양 변경", ServerOption.Money1ID,ServerOption.Money1DATA,1,Arrays.asList(ChatColor.GRAY + "   [  50원 이하  ]  ",""), 28, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "화폐 모양 변경", ServerOption.Money2ID,ServerOption.Money2DATA,1,Arrays.asList(ChatColor.GRAY + "   [ 100원 이하 ]  ",""), 29, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "화폐 모양 변경", ServerOption.Money3ID,ServerOption.Money3DATA,1,Arrays.asList(ChatColor.GRAY + "   [1000원 이하]  ",""), 30, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "화폐 모양 변경", ServerOption.Money4ID,ServerOption.Money4DATA,1,Arrays.asList(ChatColor.GRAY + "   [10000원 이하]  ",""), 31, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "화폐 모양 변경", ServerOption.Money5ID,ServerOption.Money5DATA,1,Arrays.asList(ChatColor.GRAY + "   [50000원 이하]  ",""), 32, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "화폐 모양 변경", ServerOption.Money6ID,ServerOption.Money6DATA,1,Arrays.asList(ChatColor.GRAY + "   [50000원 초과]  ",""), 33, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "화폐 모양 초기화", 325,0,1,Arrays.asList(ChatColor.GRAY + "   [ 초기화 시킵니다 ]  ",""), 34, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}

	public void OPBoxGUI_Death(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "관리자 사망 설정");

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	  	YamlManager Config = YC.getNewConfig("config.yml");

		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[가까운 마을에서 부활]", 345,0,1,Arrays.asList("",ChatColor.GRAY +"[현재 설정 상태]","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Home.SetHealth")+" 생명력",ChatColor.RED+" - 경험치 "+Config.getString("Death.Spawn_Home.PenaltyEXP")+" 감소",ChatColor.RED+" - 소지금 "+Config.getString("Death.Spawn_Home.PenaltyMoney")+" 감소","",ChatColor.YELLOW+"[좌 클릭시 변경]"), 10, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[다시 일어선다]", 2266,0,1,Arrays.asList("",ChatColor.GRAY +"[현재 설정 상태]","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Here.SetHealth")+" 생명력",ChatColor.RED+" - 경험치 "+Config.getString("Death.Spawn_Here.PenaltyEXP")+" 감소",ChatColor.RED+" - 소지금 "+Config.getString("Death.Spawn_Here.PenaltyMoney")+" 감소","",ChatColor.YELLOW+"[좌 클릭시 변경]"), 12, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[구조를 기다린다]", 397,3,1,Arrays.asList("",ChatColor.GRAY +"[현재 설정 상태]","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Help.SetHealth")+" 생명력",ChatColor.RED+" - 경험치 "+Config.getString("Death.Spawn_Help.PenaltyEXP")+" 감소",ChatColor.RED+" - 소지금 "+Config.getString("Death.Spawn_Help.PenaltyMoney")+" 감소","",ChatColor.YELLOW+"[좌 클릭시 변경]"), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[부활석 사용]", 399,0,1,Arrays.asList("",ChatColor.GRAY +"[현재 설정 상태]","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Item.SetHealth")+" 생명력",ChatColor.RED+" - 경험치 "+Config.getString("Death.Spawn_Item.PenaltyEXP")+" 감소",ChatColor.RED+" - 소지금 "+Config.getString("Death.Spawn_Item.PenaltyMoney")+" 감소","",ChatColor.YELLOW+"[좌 클릭시 변경]"), 16, inv);
		if(Config.getInt("Death.Track")==-1||Config.contains("Death.Track")==false)
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[사망 BGM]", 166,0,1,Arrays.asList(ChatColor.RED +"[없음]",ChatColor.GRAY +"사망 BGM을 설정하지 않았습니다."), 19, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[사망 BGM]", 2264,0,1,Arrays.asList(ChatColor.GREEN +""+Config.getInt("Death.Track")+"번 트랙 재생"), 19, inv);
		
		if(Config.getBoolean("Death.DistrictDirectRevive"))
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[다시 일어서기]", 166,0,1,Arrays.asList(ChatColor.RED +"[불가능]",ChatColor.GRAY +"제자리 부활 옵션을 사용하지 않습니다."), 21, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[다시 일어서기]", 2264,0,1,Arrays.asList(ChatColor.GREEN +"[가능]",ChatColor.GRAY +"제자리 부활 옵션을 사용합니다."), 21, inv);
		if(Config.getBoolean("Death.SystemOn")==false)
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[사망 시스템]", 166,0,1,Arrays.asList(ChatColor.RED +"[비 활성화]",ChatColor.GRAY +"사망 시스템을 사용하지 않습니다."), 31, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[사망 시스템]", 397,0,1,Arrays.asList(ChatColor.GREEN +"[활성화]",ChatColor.GRAY +"사망 시스템을 사용합니다."), 31, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[구조 아이템]", 288,0,1,Arrays.asList(ChatColor.GRAY +"행동 불능된 플레이어를",ChatColor.GRAY +"다시 일으켜 세워주는",ChatColor.GRAY +"치료 아이템을 설정합니다.","",ChatColor.YELLOW+"[좌 클릭시 확인 및 변경]"), 23, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[부활 아이템]", 399,0,1,Arrays.asList(ChatColor.GRAY +"행동 불능이 되었을 경우",ChatColor.GRAY +"제자리에서 부활 할 수 있는",ChatColor.GRAY +"제자리 부활 아이템을 설정합니다.","",ChatColor.YELLOW+"[좌 클릭시 확인 및 변경]"), 25, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_SettingReviveItem(Player player)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "부활 아이템");

		ItemStack item = Config.getItemStack("Death.ReviveItem");
		
		if(item != null)
			ItemStackStack(item, 4, inv);
		
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 166,0,1,null, 1, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 166,0,1,null, 2, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 166,0,1,null, 3, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 166,0,1,null, 5, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 166,0,1,null, 6, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 166,0,1,null, 7, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
		player.openInventory(inv);
		return;
	}

	public void OPBoxGUI_SettingRescueItem(Player player)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "구조 아이템");

		ItemStack item = Config.getItemStack("Death.RescueItem");
		
		if(item != null)
			ItemStackStack(item, 4, inv);
		
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 166,0,1,null, 1, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 166,0,1,null, 2, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[아이템 넣기>", 166,0,1,null, 3, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 166,0,1,null, 5, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 166,0,1,null, 6, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<아이템 넣기]", 166,0,1,null, 7, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
		player.openInventory(inv);
		return;
	}
	
	
	//각종 GUI창 속의 아이콘을 눌렸을 때, 해당 아이콘에 기능을 넣는 메소드1   -스텟 GUI, 오피박스, 커스텀 몬스터GUI-//
	public void OPBoxGUIInventoryclick(InventoryClickEvent event)
	{

		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		if(event.getSlot() >= 2 && event.getSlot()<=6)
		{
			switch(event.getSlot())
			{
			case 2:
				{
					s.SP(player, Sound.CHICKEN_IDLE, 0.8F, 1.0F);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setTime(0);
					player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" 월드 시간을 낮으로 변경하였습니다!");
				}
				return;
			case 3:
				{
					s.SP(player, Sound.WOLF_HOWL, 0.8F, 1.0F);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setTime(14000);
					player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" 월드 시간을 밤으로 변경하였습니다!");
				}
				return;
			case 4:
				{
					s.SP(player, Sound.VILLAGER_YES, 0.8F, 1.0F);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setSpawnLocation((int)player.getLocation().getX(), (int)player.getLocation().getY(), (int)player.getLocation().getZ());
					player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" 월드의 스폰 지점을 "+(int)player.getLocation().getX()+","+(int)player.getLocation().getY()+","+(int)player.getLocation().getZ()+" 로 변경하였습니다!");
				}
				return;
			case 5:
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setStorm(false);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setWeatherDuration(180000);
					player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" 월드 날씨를 맑음으로 변경하였습니다!");
				}
				return;
			case 6:
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setStorm(true);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setWeatherDuration(180000);
					player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" 월드 날씨를 흐림으로 변경하였습니다!");
				}
				return;
			}
		}
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		short page = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getDisplayName()));
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
		case "던전":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonListMainGUI(player, 0, 52);
			return;
		case "기능성 개체":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new StructureGUI().StructureListGUI(player, 0);
			return;
		case"도박":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GambleGUI().GambleMainGUI(player);
			break;
		case"네비게이션":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new NavigationGUI().NavigationListGUI(player,(short) 0);
			break;
		case"GoldBigDragonRPG":
			if(ServerOption.serverVersion.compareTo(ServerOption.currentServerVersion)==0&&
					ServerOption.serverUpdate.compareTo(ServerOption.currentServerUpdate)==0)
			{
				s.SP(player,Sound.ANVIL_USE, 0.8F, 1.0F);
				player.sendMessage(ChatColor.YELLOW + "[버전 체크] : 현재 GoldBigDragonRPG는 최신 버전입니다!");
			}
			else
			{
				s.SP(player,Sound.ANVIL_USE, 0.8F, 1.0F);
				player.sendMessage(ChatColor.RED + "[버전 체크] : 현재 GoldBigDragonRPG는 업데이트가 필요합니다!");
				player.sendMessage(ChatColor.RED + "[현재 버전] : "+ServerOption.serverVersion);
				player.sendMessage(ChatColor.RED + "[최신 버전] : "+ServerOption.currentServerVersion);
				player.sendMessage(ChatColor.RED + "[현재 패치] : "+ServerOption.serverUpdate);
				player.sendMessage(ChatColor.RED + "[최신 패치] : "+ServerOption.currentServerUpdate);
			}
			break;
		case "서버 설정":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
			return;
		case "워프":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.GUI.WarpGUI().WarpListGUI(player, 0);
			return;
		case "월드 생성":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new GoldBigDragon_RPG.GUI.WorldCreateGUI().WorldCreateGUIMain(player);
			return;
		case "몬스터":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new MonsterGUI().MonsterListGUI(player, 0);
			return;
		case "초심자":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new NewBieGUI().NewBieGUIMain(player);
			return;
		case "개조식":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new UpGradeGUI().UpgradeRecipeGUI(player,0);
			return;
		case "이벤트":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new EventGUI().EventGUI_Main(player);
			return;
		case "게임 성향":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			Config.set("Time.LastSkillChanged", new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000)-new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000));
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true) {Config.set("Server.Like_The_Mabinogi_Online_Stat_System", false);}
			else{Config.set("Server.Like_The_Mabinogi_Online_Stat_System", true);}
			Config.saveConfig();
			OPBoxGUI_Main(player,(byte) 1);
			new GoldBigDragon_RPG.ETC.Job().AllPlayerFixAllSkillAndJobYML();
			return;
		case "커스텀 아이템":
			ItemGUI IGUI = new ItemGUI();
			IGUI.ItemListGUI(player, 0);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			return;
		case "몬스터 스폰 효과":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(Config.getInt("Server.MonsterSpawnEffect")<7) {Config.set("Server.MonsterSpawnEffect", Config.getInt("Server.MonsterSpawnEffect")+1);}
			else{Config.set("Server.MonsterSpawnEffect", 0);}
			Config.saveConfig();
			OPBoxGUI_Main(player,(byte) 1);
			return;
		case "플러그인 가이드":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			Guide_GUI(player);
			return;
		case "퀘스트":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new QuestGUI().AllOfQuestListGUI(player, (short) 0,false);
			return;
		case "스킬":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new OPBoxSkillGUI().AllSkillsGUI(player,(short) 0,false,null);
			return;
		case "카테고리 및 직업":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new JobGUI().ChooseSystemGUI(player);
			return;
		case "소비 아이템":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new UseableItemGUI().UseableItemListGUI(player, 0);
			return;
		case "영역":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			new AreaGUI().AreaListGUI(player, (short) 0);
			return;
		case "이전 페이지":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Main(player,(byte) (page-1));
			return;
		case "다음 페이지":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Main(player,(byte) (page+1));
			return;
		case "닫기":
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		return;
	}
	
	public void OPBoxGUI_SettingInventoryClick(InventoryClickEvent event)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		switch (event.getSlot())
		{
		case 10://엔티티 스폰
			if(Config.getBoolean("Server.EntitySpawn") == true) {Config.set("Server.EntitySpawn", false);}
			else{Config.set("Server.EntitySpawn", true);}
			Config.saveConfig();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
			return;
		case 11://PVP
			if(Config.getBoolean("Server.PVP")==true)
			{
				Config.set("Server.PVP", false);
				GoldBigDragon_RPG.Main.ServerOption.PVP = false;
			}
			else
			{
				Config.set("Server.PVP", true);
				GoldBigDragon_RPG.Main.ServerOption.PVP = true;
			}
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			Config.saveConfig();
			OPBoxGUI_Setting(player);
			return;
		case 12://몬스터 스폰 효과
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(Config.getInt("Server.MonsterSpawnEffect")<7) {Config.set("Server.MonsterSpawnEffect", Config.getInt("Server.MonsterSpawnEffect")+1);}
			else{Config.set("Server.MonsterSpawnEffect", 0);}
			Config.saveConfig();
			OPBoxGUI_Setting(player);
			return;
		case 13://커스텀 무기 파괴
			if(Config.getBoolean("Server.CustomWeaponBreak") == true) {Config.set("Server.CustomWeaponBreak", false);}
			else{Config.set("Server.CustomWeaponBreak", true);}
			Config.saveConfig();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
			return;
		case 14://입장 메시지 변경
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				Object_UserData u = new Object_UserData();
				u.setType(player, "System");
				u.setString(player, (byte)1,"JM");
				player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 입장 메시지를 입력 해 주세요! ("+ChatColor.WHITE+"없음"+ChatColor.GREEN+" 입력시 입장 메시지 제거)");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			}
			return;
		case 15://퇴장 메시지 변경
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				Object_UserData u = new Object_UserData();
				u.setType(player, "System");
				u.setString(player, (byte)1,"QM");
				player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 퇴장 메시지를 입력 해 주세요! ("+ChatColor.WHITE+"없음"+ChatColor.GREEN+" 입력시 퇴장 메시지 제거)");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			}
			return;
		case 16://공지사항
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_BroadCast(player, (byte) 0);
			return;
		case 19://채팅 형태 변경
			if(event.isLeftClick())
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				Object_UserData u = new Object_UserData();
				u.setType(player, "System");
				u.setString(player, (byte)1,"CCP");
				player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 채팅 형태를 입력 해 주세요!");
				player.sendMessage(ChatColor.GOLD + "%t%"+ChatColor.WHITE + " - 현재 시각 지칭하기 -");
				player.sendMessage(ChatColor.GOLD + "%gm%"+ChatColor.WHITE + " - 게임모드 지칭하기 -");
				player.sendMessage(ChatColor.GOLD + "%ct%"+ChatColor.WHITE + " - 채팅 타입 지칭하기 -");
				player.sendMessage(ChatColor.GOLD + "%lv%"+ChatColor.WHITE + " - 레벨 지칭하기 -");
				player.sendMessage(ChatColor.GOLD + "%rlv%"+ChatColor.WHITE + " - 누적 레벨 지칭하기 - (서버 성향이 메이플스토리일 경우 쓸모 없음.)");
				player.sendMessage(ChatColor.GOLD + "%job%"+ChatColor.WHITE + " - 직업 지칭하기 - (서버 성향이 마비노기일 경우 쓸모 없음.)");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.GOLD + "%message%"+ChatColor.WHITE + " - 채팅 내용 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			}
			else if(event.isRightClick())
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				Config.removeKey("Server.ChatPrefix");
				Config.saveConfig();
				player.sendMessage(ChatColor.RED+"[SYSTEM] : 접두사를 삭제하였습니다!");
				OPBoxGUI_Setting(player);
			}
			return;
		case 20://커스텀 블록 설치/설치 금지
			if(Config.getBoolean("Server.CustomBlockPlace") == true) {Config.set("Server.CustomBlockPlace", false);}
			else{Config.set("Server.CustomBlockPlace", true);}
			Config.saveConfig();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
			return;
		case 21: //스텟 이름 변경
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				OPBoxGUI_StatChange(player);
			}
			return;
		case 22: //금전 시스템 변경
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				OPBoxGUI_Money(player);
			}
			return;
		case 23: //사망 시스템 변경
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				OPBoxGUI_Death(player);
			}
			return;
			case 24: //폭발 방지 변경
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				if(Config.getBoolean("Server.AntiExplode") == true)
				{
					Config.set("Server.AntiExplode", false);
					GoldBigDragon_RPG.Main.ServerOption.AntiExplode = false;
				}
				else
				{
					Config.set("Server.AntiExplode", true);
					GoldBigDragon_RPG.Main.ServerOption.AntiExplode = true;
				}
				Config.saveConfig();
				
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				OPBoxGUI_Setting(player);
			}
			return;
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			OPBoxGUI_Main(player, (byte) 1);
			return;
		case 53://닫기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		return;
	}
	
	
	public void Guide_GUI (Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "관리자 가이드");
		
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "스텟 시스템", 340,0,1,Arrays.asList(ChatColor.GRAY+ "플러그인에는 5가지 스텟이 있습니다.",ChatColor.RED +"["+ServerOption.STR+"]",ChatColor.GRAY+""+ServerOption.STR+"은 플레이어의",ChatColor.GRAY+"물리적 데미지에 관여합니다.",ChatColor.GREEN +  "["+ServerOption.DEX+"]",ChatColor.GRAY+""+ServerOption.DEX+"는 플레이어의 밸런스 및",ChatColor.GRAY+"생산 성공률과 생산 품질,",ChatColor.GRAY+"원거리 데미지에 관여합니다.",ChatColor.BLUE+"["+ServerOption.INT+"]",ChatColor.GRAY+""+ServerOption.INT+"은 마법방어 및 마법보호,",ChatColor.GRAY+"마법 공격력에 관여합니다.",ChatColor.WHITE+"["+ServerOption.WILL+"]",ChatColor.GRAY + ""+ServerOption.WILL+"는 플레이어의",ChatColor.GRAY + "크리티컬에 관여합니다.",ChatColor.YELLOW + "["+ServerOption.LUK+"]",ChatColor.GRAY + ""+ServerOption.LUK+"은 크리티컬 및",ChatColor.GRAY +"럭키 피니시, 럭키 보너스 등",ChatColor.GRAY +"각종 '확률'에 관여합니다."), 0,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "럭키 피니시", 340,0,1,Arrays.asList(ChatColor.GRAY+ "몬스터를 사냥하였을 경우",ChatColor.GRAY+"일정 확률로 돈이 더 나오게 됩니다."), 1,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "럭키 보너스", 340,0,1,Arrays.asList(ChatColor.GRAY+ "채집을 할 경우 일정 확률로",ChatColor.GRAY+"채집 품목이 더 나오게 됩니다."), 2,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "커스텀 아이템 [1]", 340,0,1,Arrays.asList(ChatColor.GRAY+ "각종 커스텀 아이템을",ChatColor.GRAY+"생성하거나 등록하여",ChatColor.GRAY+"언제든 불러올 수 있습니다.","",ChatColor.GOLD+"[명령어]",ChatColor.YELLOW+"/아이템"), 3,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "커스텀 아이템 [2]", 340,0,1,Arrays.asList(ChatColor.GRAY+ ServerOption.Damage+", 방어, 보호 등의",ChatColor.GRAY+"특수 옵션이 붙은 아이템은",ChatColor.GRAY+"내구도가 0이 될 시 효과가",ChatColor.GRAY+"적용되지 않습니다.",ChatColor.GRAY+"그럴 경우, 직접 수리하거나",ChatColor.GRAY+"대장장이 NPC를 통해",ChatColor.GRAY+"수리 받아야 합니다.","",ChatColor.GOLD+"[명령어]",ChatColor.YELLOW+"/아이템 수리"), 4,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "영역 설정", 340,0,1,Arrays.asList(ChatColor.GRAY+ "영역을 지정하여 마을 혹은",ChatColor.GRAY+"PVP장, 던전 등등의 가상 공간을",ChatColor.GRAY+"형성할 수 있습니다.",ChatColor.GRAY+"영역 설정에 따라 최근 방문한",ChatColor.GRAY+"영역에서 부활 하거나,",ChatColor.GRAY+"영역 입장시 특수 메시지를",ChatColor.GRAY+"보낼 수 있습니다.","",ChatColor.GOLD+"[명령어]",ChatColor.YELLOW+"/영역"), 5,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "NPC", 340,0,1,Arrays.asList(ChatColor.GRAY+ "NPC 생성은 "+ChatColor.YELLOW+ "Citizens2 "+ChatColor.GRAY+ "플러그인을",ChatColor.GRAY+ "사용하시는 것을 권장합니다.",ChatColor.GRAY+ "NPC 생성 후, 해당 NPC를 우클릭 하여",ChatColor.GRAY+ "NPC에 관한 각종 설정을 할 수 있습니다."), 6,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "퀘스트", 340,0,1,Arrays.asList(ChatColor.GRAY+ "오피박스에서 서버 내의 퀘스트를",ChatColor.GRAY+"쉽고 빠르게 제작하실 수 있습니다.","",ChatColor.GOLD+"[명령어]",ChatColor.YELLOW+"/오피박스",ChatColor.YELLOW+"/퀘스트 생성"+ChatColor.DARK_AQUA+" [타입]"+ChatColor.YELLOW+" [퀘스트 이름]","",ChatColor.DARK_AQUA+"[일반/반복/일일/일주/한달]"), 7,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "이벤트", 340,0,1,Arrays.asList(ChatColor.GRAY+ "오피박스 1페이지에 있는 "+ChatColor.YELLOW+"'이벤트'",ChatColor.GRAY+ "아이콘을 클릭하여 들어간다면",ChatColor.GRAY+ "서버에 특정 이벤트를",ChatColor.GRAY+"개최 하실 수 있습니다.",ChatColor.GRAY+"유저들간 밸런스를 생각하며",ChatColor.GRAY+"적당한 이벤트를 열어 줍시다."), 8,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "스킬 [1]", 340,0,1,Arrays.asList(ChatColor.GRAY+ "오피박스 2페이지에 있는 "+ChatColor.YELLOW+"'스킬'",ChatColor.GRAY+ "아이콘을 클릭하여 들어간다면",ChatColor.GRAY+ "각종 스킬을 제작하거나 삭제하는",ChatColor.GRAY+"GUI 화면이 나타납니다.",ChatColor.GRAY+"이곳에서 생성한 각종 스킬들은",ChatColor.GRAY+"서버의 주된 시스템에 따라 분류하여",ChatColor.GRAY+"게임 내에서 사용 가능하게 되며",ChatColor.GRAY+"스킬로 "+ChatColor.LIGHT_PURPLE+"커맨드"+ChatColor.GRAY+"를 사용할 수 있으며,",ChatColor.DARK_AQUA+"MagicSpells 플러그인"+ChatColor.GRAY+"을"+ChatColor.DARK_AQUA+" 지원"+ChatColor.GRAY+"합니다."), 9,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "스킬 [2]", 340,0,1,Arrays.asList(ChatColor.GRAY+ "스킬을 생성 하셨다면",ChatColor.GRAY+ "스킬 랭크를 만드실 차례입니다.",ChatColor.GRAY+ "스킬 랭크를 만드셨다면",ChatColor.GRAY+ "각각의 스킬 랭크에 맞는",ChatColor.GRAY+ "커맨드 혹은 매직스펠의 스펠,",ChatColor.GRAY+ "랭크업시 얻는 각종 보너스 스텟,",ChatColor.GRAY+ "랭크업에 필요한 스킬 포인트 등",ChatColor.GRAY+ "다양한 옵션을 설정하실 수 있습니다."), 10,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "직업, 그리고 시스템 [1]", 340,0,1,Arrays.asList(ChatColor.YELLOW+ "GoldBigDragon_Advanced",ChatColor.GRAY+ "(이하 GBD_A)",ChatColor.GRAY+ "플러그인에는 두 가지 게임 시스템이",ChatColor.GRAY+ "존재합니다. "+ChatColor.STRIKETHROUGH+"(하이브리드 플러그인)",ChatColor.GRAY+ "이 둘의 특성은 비슷하면서도",ChatColor.GRAY+ "서로 다르기에, 시스템 변경시",ChatColor.GRAY+ "주의를 요합니다."), 11,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "직업, 그리고 시스템 [2]", 340,0,1,Arrays.asList(ChatColor.RED+""+ChatColor.BOLD+ "[메이플 스토리]",ChatColor.GRAY+ "첫 번째로 알아보실 시스템은",ChatColor.GRAY+ "여러분께 친숙한 게임인",ChatColor.GRAY+ "메이플스토리를 빼닮은",ChatColor.GRAY+ "메이플스토리형 시스템입니다.",ChatColor.GRAY+ "서버 내에 각종 직업 클래스를",ChatColor.GRAY+ "생성하실 수 있으며,",ChatColor.GRAY+ "직업 클래스 내에서 또다시",ChatColor.GRAY+ "2차 전직, 3차 전직과 같은",ChatColor.GRAY+ "승급 생성이 가능합니다.",ChatColor.GRAY+ "또한 직업 및 승급별로 개별적인",ChatColor.GRAY+ "스킬을 부여할 수 있게 되며,",ChatColor.GRAY+ "레벨업 시, 스텟 포인트를 지급하며",ChatColor.GRAY+ "유저는 스텟을 원하는 곳에",ChatColor.GRAY+ "투자함으로써 강해집니다.",ChatColor.GRAY+ "전직은 "+ChatColor.DARK_PURPLE+"Citizens 플러그인"+ChatColor.GRAY+"으로",ChatColor.GRAY+ "NPC를 생성한 후, 우 클릭시",ChatColor.GRAY+ "NPC전용 창이 나오며 그곳에서",ChatColor.GOLD+ "[직업 설정]"+ChatColor.GRAY+"버튼을 누른 다음",ChatColor.GOLD+ "[전직 교관]"+ChatColor.GRAY+"을 선택할 경우",ChatColor.GRAY+ "유저들이 해당 NPC를 통해",ChatColor.GRAY+ "정해진 클래스로 전직이 가능해 집니다."), 12,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "직업, 그리고 시스템 [3]", 340,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+ "[마비노기]",ChatColor.GRAY+ "두 번째로 알아보실 시스템은",ChatColor.GRAY+ "저의 본래 목적이었던",ChatColor.GRAY+ "마비노기 형식의 시스템입니다.",ChatColor.GRAY+ "마비노기 시스템의 경우",ChatColor.GRAY+ "메이플스토리 처럼 직업과",ChatColor.GRAY+ "스텟 포인트 개념이 없지만",ChatColor.GRAY+ "마음만 먹으면 서버 내의",ChatColor.GRAY+ "모든 스킬을 배울 수 있는",ChatColor.GRAY+ "굉장한 자유도를 가지고 있습니다.",ChatColor.GRAY+ "하지만 메이플 스토리처럼",ChatColor.GRAY+ "직업을 가지면 스킬을 모두 주는",ChatColor.GRAY+ "형식이 아니라, 자신이 직접",ChatColor.DARK_PURPLE+ "NPC와 대화"+ChatColor.GRAY+"를 하거나",ChatColor.DARK_PURPLE+ "특수한 책"+ChatColor.GRAY+"을 읽음으로써",ChatColor.GRAY+ "스킬을 터득할 수 있습니다.",ChatColor.GRAY+ "스킬은 컨텐츠별로 나뉘며",ChatColor.GRAY+ "전투탭에 등록한 스킬을",ChatColor.GRAY+ "마법탭에 등록할 수 없습니다.",ChatColor.AQUA+ "환생"+ChatColor.GRAY+" 및"+ChatColor.DARK_PURPLE+" 특수한 책"+ChatColor.GRAY+"에 대한 내용은",ChatColor.GRAY+"다음 가이드에서 설명하겠습니다."), 13,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "직업, 그리고 시스템 [4]", 340,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+"[마비노기]",ChatColor.AQUA+ "<환생>",ChatColor.GRAY+ "메이플 스토리는 만렙이 정해져 있습니다.",ChatColor.GRAY+ "하지만 마비노기에서 만렙이 있다면",ChatColor.GRAY+ "모든 스킬을 배울수가 없게 되지요.",ChatColor.GRAY+ "이런 갭을 극복하기 위한 장치가",ChatColor.AQUA+ "'환생'"+ChatColor.GRAY+"입니다.",ChatColor.GRAY+ "환생을 할 경우, 지금까지 쌓은",ChatColor.GRAY+ "모든 레벨은 '누적 레벨'에 더해지고,",ChatColor.GRAY+ "일반적인 레벨이 1로 초기화 됩니다.",ChatColor.GRAY+ "이는 너프가 아닌, 스킬 포인트를",ChatColor.GRAY+ "더욱 빠르게 모을 수 있는 장치입니다.",ChatColor.GRAY+ "레벨이 1이 된다면 자신이 모아야 할",ChatColor.GRAY+ "경험치 량도 적어질테니까요.",ChatColor.GRAY+ "보통 캐릭터의 나이가 20세가 되면",ChatColor.GRAY+ "환생이 가능하지만, 이 플러그인에서는",ChatColor.GRAY+ "소비 아이템인 "+ChatColor.YELLOW+"환생 포션"+ChatColor.GRAY+"을",ChatColor.GRAY+ "지원하고 있습니다."), 14,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "직업, 그리고 시스템 [5]", 340,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+ "[마비노기]",ChatColor.DARK_PURPLE+ "<특수한 책>",ChatColor.GRAY+ "오피박스 2페이지에 있는",ChatColor.GREEN+ "소비 아이템"+ChatColor.GRAY + " 아이콘을 클릭하면",ChatColor.GRAY+ "스킬 포인트 주문서에서 부터",ChatColor.GRAY+ "환생포션 까지의 거의",ChatColor.GRAY+ "대부분의 소비성 아이템을 만들거나",ChatColor.GRAY+ "삭제할 수 있으며, 지금 설명드릴",ChatColor.DARK_PURPLE+ "특수한 책"+ChatColor.GRAY+" 또한 제작이 가능합니다.",ChatColor.GRAY+ "특수한책의 경우, 읽으면 바로 소멸 되지만",ChatColor.GRAY+ "플레이어가 모르던 스킬을",ChatColor.GRAY+ "익힐 수 있도록 하거나,",ChatColor.GRAY+ "특정 스텟을 향상시켜주는",ChatColor.GRAY+ "능력을 가지고 있습니다.",ChatColor.GRAY+ "그렇기에 서버 내에서 당연",ChatColor.GRAY+ "중요 거래 품목이 될 가능성이",ChatColor.GRAY+ "높은 아이템이므로, 당신의",ChatColor.GRAY+ "적당한 밸런스 조정력이 필요한",ChatColor.GRAY+ "아이템 1순위라고 보아도",ChatColor.GRAY+ "과언이 아닙니다.",ChatColor.GRAY+ "밸런스는 몬스터 드랍률,",ChatColor.GRAY+ "이벤트를 통한 지급,",ChatColor.GRAY+ "던전 보상 등으로 조절이",ChatColor.GRAY+ "가능할 것입니다."), 15,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "개조", 340,0,1,Arrays.asList(ChatColor.GRAY+ "커스텀 아이템으로 만든",ChatColor.GRAY+ "아이템 중, 개조 옵션이",ChatColor.GRAY+ "붙은 아이템일 경우만",ChatColor.GRAY+ "개조가 가능하며, 개조는",ChatColor.DARK_AQUA+ "[개조 장인]"+ChatColor.GRAY+"을 직업으로 가진",ChatColor.GRAY+ "NPC에게 받을 수 있습니다.",ChatColor.GRAY+ "개조 장인에게 오피박스에서 만든",ChatColor.GRAY+ "개조식을 등록시키면",ChatColor.GRAY+ "해당 NPC에게서 해당 개조가",ChatColor.GRAY+ "가능하게 됩니다."), 16,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "룬", 340,0,1,Arrays.asList(ChatColor.GRAY+ "개조와 비슷한 개념이지만",ChatColor.GRAY+ "실패 확률이 있으며,",ChatColor.BLUE+ "룬"+ChatColor.GRAY+" 이라는 아이템이 필요합니다.",ChatColor.GRAY+ "룬은 오피박스 2페이지에서",ChatColor.GRAY+ "새로 만들 수 있습니다."), 17,inv);
		//Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "", 340,0,1,Arrays.asList(ChatColor.GRAY+ ""), 18,inv);
		Stack2(ChatColor.RED +"            "+ChatColor.BOLD +"버"+ChatColor.BLUE +""+ChatColor.BOLD +"그"+ChatColor.GREEN +""+ChatColor.BOLD +"리"+ChatColor.YELLOW +""+ChatColor.BOLD +"포"+ChatColor.WHITE +""+ChatColor.BOLD +"트            ", 403,0,1,Arrays.asList("",
				ChatColor.GRAY+ "심각성 : "+ChatColor.DARK_RED+"|||||||||||||||||||||||||||||||||||||||| [최고]",ChatColor.RED+ "♩ 광역형 매직스펠 사용시 마법보호 무시 및",ChatColor.RED+ "물리 방어/보호는 매직스펠 Pain 스킬",ChatColor.RED+ "클래스의 대미지를 흡수함","",
				ChatColor.GRAY+ "심각성 : "+ChatColor.GOLD+"||||||||||||||||||||"+ChatColor. GRAY+"||||||||||||||||||||"+ChatColor.GOLD+" [보통]",ChatColor.YELLOW+"♩ 가끔 나도 모르는 오류가 뜸. 제길","",
				ChatColor.GRAY+ "심각성 : "+ChatColor.DARK_GREEN+"||||||||||"+ChatColor. GRAY+"||||||||||||||||||||||||||||||"+ChatColor.DARK_GREEN+" [낮음]",ChatColor.GREEN+"♩ 마나와 생명력 보너스가 가끔",ChatColor.GREEN+"바로바로 업데이트가 되지 않으나",ChatColor.GREEN+"맞거나 핫바를 움직이면 새로고쳐짐."), 22,inv);

		Stack2(ChatColor.DARK_RED +""+ ChatColor.BOLD + "[동영상 가이드]", 389,0,1,Arrays.asList("",ChatColor.AQUA+ "[클릭시 동영상 URL이 보여집니다.]"), 36,inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void OPBoxGuideInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
		case "[동영상 가이드]":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"[YouTube] "+ChatColor.WHITE+""+ChatColor.BOLD+": "+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"https://www.youtube.com/playlist?list=PLxqihkJXVJABIlxU3n6bNhhC8x6xPbORP   "+ChatColor.YELLOW+""+ChatColor.BOLD+"[클릭시 가이드 페이지로 연결됩니다]");
			break;
		case "이전 목록":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			event.setCancelled(true);
			OPBoxGUI_Main(player,(byte) 1);
			break;
		case "닫기":
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			event.setCancelled(true);
			player.closeInventory();
			return;
			default : return;
		}
		return;
	}

	public void OPBoxGUI_BroadCastClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager BroadCast =YC.getNewConfig("BroadCast.yml");
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_BroadCast(player, (byte) (page-1));
			return;
		case 49://새 공지
		{
			int BCnumber = BroadCast.getConfigurationSection("").getKeys(false).size();
			BroadCast.set(BCnumber+"", ChatColor.YELLOW+"[새로운 공지사항 설정 중]");
			BroadCast.saveConfig();
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[공지] : 새로운 공지 사항을 입력 해 주세요!");
			player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
			ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
					ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
			ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
					ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			Object_UserData u = new Object_UserData();
			u.setType(player, "System");
			u.setString(player, (byte)1, "NBM");
			u.setInt(player, (byte)0, BCnumber);
		}
		return;
		case 46://공지 간격
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[공지] : 몇 초마다 공지를 띄울까요?");
				player.sendMessage(ChatColor.YELLOW+"(최소 1초 ~ 최대 3600초(1시간) 이하 숫자를 입력 하세요!)");
				Object_UserData u = new Object_UserData();
				u.setType(player, "System");
				u.setString(player, (byte)1, "BMT");
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_BroadCast(player, (byte) (page+1));
			return;
		default :
			if(event.isShiftClick() == true && event.isRightClick() == true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				int Acount =  BroadCast.getConfigurationSection("").getKeys(false).toArray().length-1;
				int number = ((page*45)+event.getSlot());
				for(int counter = number;counter <Acount;counter++)
					BroadCast.set(counter+"", BroadCast.get((counter+1)+""));
				BroadCast.removeKey(Acount+"");
				BroadCast.saveConfig();
				OPBoxGUI_BroadCast(player, (byte) page);
			}
			return;
		}
	}

	public void OPBoxGUI_StatChangeClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		default:
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
	
				if(event.getSlot()>=9&&event.getSlot()<=13)
				{
					player.sendMessage(ChatColor.DARK_AQUA+"[System] : 새로운 "+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+"을 입력 해 주세요!");
					player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄어 쓰기 -");
					player.sendMessage(ChatColor.GOLD + "%stat%"+ChatColor.WHITE + " - 스텟 이름 쓰기 -");
				}
				else
				{
					player.sendMessage(ChatColor.DARK_AQUA+"[System] : 새로운 "+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+" 스텟 이름을 입력 해 주세요!");
					player.sendMessage(ChatColor.GRAY+"(띄어 쓰기 및 기호 사용 불가)");
				}
				Object_UserData u = new Object_UserData();
				u.setType(player, "System");
				u.setString(player, (byte)1, "CSN");
				u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			return;
		}
	}

	public void OPBoxGUI_MoneyClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		Object_UserData u = new Object_UserData();

		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		if(event.getSlot()>=28&&event.getSlot()<=33)
		{
			switch(event.getSlot())
			{
				case 28:
				{u.setInt(player, (byte)1,50); break;}
				case 29:
				{u.setInt(player, (byte)1,100); break;}
				case 30:
				{u.setInt(player, (byte)1,1000); break;}
				case 31:
				{u.setInt(player, (byte)1,10000); break;}
				case 32:
				{u.setInt(player, (byte)1,50000); break;}
				case 33:
				{u.setInt(player, (byte)1,-1);}
			}
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[System] : 화폐 모양으로 설정할 아이템 ID를 입력 해 주세요!");
			u.setType(player, "System");
			u.setString(player, (byte)1, "CMID");
		}
		
		switch (event.getSlot())
		{
			case 11:
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[System] : 최대 얼마까지만 드랍 가능하도록 할까요?");
				player.sendMessage(ChatColor.GRAY + "(최소 1000(1천)원 이상, 최대 1000000000(1억)원 이하)");
				u.setType(player, "System");
				u.setString(player, (byte)1, "CDML");
			}
			return;
			case 13:
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[System] : 새로운 화폐 단위를 입력 해 주세요!");
				player.sendMessage(ChatColor.GRAY+"(띄어 쓰기 및 기호 사용 불가)");
				u.setType(player, "System");
				u.setString(player, (byte)1, "CSN");
				u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			return;

			case 34: //화폐 모양 초기화
			{
				s.SP(player, Sound.IRONGOLEM_THROW, 0.8F, 1.0F);
				player.sendMessage(ChatColor.DARK_AQUA+"[System] : 화폐 모양이 초기화 되었습니다!");

				Config.set("Server.Money.1.ID",348);
				ServerOption.Money1ID = 348;
				Config.set("Server.Money.2.ID",371);
				ServerOption.Money2ID = 371;
				Config.set("Server.Money.3.ID",147);
				ServerOption.Money3ID = 147;
				Config.set("Server.Money.4.ID",266);
				ServerOption.Money4ID = 266;
				Config.set("Server.Money.5.ID",41);
				ServerOption.Money5ID = 41;
				Config.set("Server.Money.6.ID",41);
				ServerOption.Money6ID = 41;
				Config.set("Server.Money.1.DATA",0);
				ServerOption.Money1DATA = 0;
				Config.set("Server.Money.2.DATA",0);
				ServerOption.Money2DATA = 0;
				Config.set("Server.Money.3.DATA",0);
				ServerOption.Money3DATA = 0;
				Config.set("Server.Money.4.DATA",0);
				ServerOption.Money4DATA = 0;
				Config.set("Server.Money.5.DATA",0);
				ServerOption.Money5DATA = 0;
				Config.set("Server.Money.6.DATA",0);
				ServerOption.Money6DATA = 0;

				Config.saveConfig();
				OPBoxGUI_Money(player);
			}
			return;
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void OPBoxGUI_DeathClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		switch(event.getSlot())
		{
		case 10://가까운 마을에서 부활
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "System");
			u.setString(player, (byte)1, "RO_S_H");
			player.sendMessage(ChatColor.GREEN+"[부활] : 마지막 마을에서 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"생명력"+ChatColor.GREEN+"을 가지고 부활 하도록 하겠습니까?");
			player.sendMessage(ChatColor.GRAY + "(최소 1 ~ 최대 100)");
			player.closeInventory();
			return;
		case 12://제자리에서 부활
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "System");
			u.setString(player, (byte)1, "RO_T_H");
			player.sendMessage(ChatColor.GREEN+"[부활] : 제자리에서 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"생명력"+ChatColor.GREEN+"을 가지고 부활 하도록 하겠습니까?");
			player.sendMessage(ChatColor.GRAY + "(최소 1 ~ 최대 100)");
			player.closeInventory();
			return;
		case 14://도움 요청
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "System");
			u.setString(player, (byte)1, "RO_H_H");
			player.sendMessage(ChatColor.GREEN+"[부활] : 도움을 받아 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"생명력"+ChatColor.GREEN+"을 가지고 부활 하도록 하겠습니까?");
			player.sendMessage(ChatColor.GRAY + "(최소 1 ~ 최대 100)");
			player.closeInventory();
			return;
		case 16://아이템 사용
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "System");
			u.setString(player, (byte)1, "RO_I_H");
			player.sendMessage(ChatColor.GREEN+"[부활] : 아이템을 사용하여 부활할 경우, 몇 %의 "+ChatColor.YELLOW+"생명력"+ChatColor.GREEN+"을 가지고 부활 하도록 하겠습니까?");
			player.sendMessage(ChatColor.GRAY + "(최소 1 ~ 최대 100)");
			player.closeInventory();
			return;
		case 19://사망 BGM
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			  	YamlManager Config = YC.getNewConfig("config.yml");
				if(Config.getInt("Death.Track")==-1||Config.contains("Death.Track")==false)
					new GoldBigDragon_RPG.GUI.AreaGUI().AreaMusicSettingGUI(player, 0, "DeathBGM¡");
				else
				{
					Config.set("Death.Track", -1);
					Config.saveConfig();
					OPBoxGUI_Death(player);
				}
				return;
			}
		case 21://제자리 부활 옵션 켜기/끄기
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			  	YamlManager Config = YC.getNewConfig("config.yml");
				if(Config.getBoolean("Death.DistrictDirectRevive"))
					Config.set("Death.DistrictDirectRevive",false);
				else
					Config.set("Death.DistrictDirectRevive",true);
				Config.saveConfig();
				OPBoxGUI_Death(player);
			}
		return;
		case 23://구조 아이템 설정
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_SettingRescueItem(player);
			return;
		case 25://부활 아이템 설정
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_SettingReviveItem(player);
			return;
		case 31://사망 시스템 온/오프
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			  	YamlManager Config = YC.getNewConfig("config.yml");
				if(Config.getBoolean("Death.SystemOn"))
					Config.set("Death.SystemOn",false);
				else
					Config.set("Death.SystemOn",true);
				Config.saveConfig();
				OPBoxGUI_Death(player);
			}
			return;
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}

	public void OPBoxGUI_RescueOrReviveClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();
		
		switch(event.getSlot())
		{
		case 1:
		case 2:
		case 3:
		case 5:
		case 6:
		case 7:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			event.setCancelled(true);
			return;
		case 0://이전 목록
			event.setCancelled(true);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Death(player);
			return;
		case 8://나가기
			event.setCancelled(true);
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	
	
	public void OPBoxGUI_RescueOrReviveClose(InventoryCloseEvent event)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		if(event.getInventory().getItem(4) != null)
		{
			if(event.getInventory().getTitle().contains("부활")==true)
			{
				Config.set("Death.ReviveItem", event.getInventory().getItem(4));
				ServerOption.DeathRevive = event.getInventory().getItem(4);
			}
			else
			{
				Config.set("Death.RescueItem", event.getInventory().getItem(4));
				ServerOption.DeathRescue = event.getInventory().getItem(4);
			}
		}
		else
		{
			if(event.getInventory().getTitle().contains("부활")==true)
			{
				Config.set("Death.ReviveItem", null);
				ServerOption.DeathRevive = null;
			}
			else
			{
				Config.set("Death.RescueItem", null);
				ServerOption.DeathRescue = null;
			}
		}
		if(event.getInventory().getTitle().contains("부활")==true)
			event.getPlayer().sendMessage(ChatColor.GREEN+"[SYSTEM] : 부활 아이템 설정이 완료되었습니다!");
		else
			event.getPlayer().sendMessage(ChatColor.GREEN+"[SYSTEM] : 구조 아이템 설정이 완료되었습니다!");
		Config.saveConfig();
		new GoldBigDragon_RPG.Effect.Sound().SP((Player)event.getPlayer(), Sound.ANVIL_USE, 1.0F, 1.0F);
		return;
	}
}
