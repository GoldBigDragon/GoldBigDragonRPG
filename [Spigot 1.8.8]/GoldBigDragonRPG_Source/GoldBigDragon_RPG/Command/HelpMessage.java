package GoldBigDragon_RPG.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class HelpMessage
{
	public void HelpMessager(Player player, byte what)
	{
		switch(what)
		{
			case 1:
			{
				player.sendMessage(ChatColor.YELLOW+"────────────[아이템 명령어]────────────");
				player.sendMessage(ChatColor.GOLD + "/아이템 수리" + ChatColor.YELLOW + " - 손에 들고있는 아이템을 수리합니다.");
				player.sendMessage(ChatColor.GOLD + "/아이템 이름 <문자열>" + ChatColor.YELLOW + " - 해당 아이템의 보여질 이름을 설정합니다.");
				player.sendMessage(ChatColor.GOLD + "/아이템 ID <숫자>" + ChatColor.YELLOW + " - 해당 아이템의 ID값을 설정합니다.");
				player.sendMessage(ChatColor.GOLD + "/아이템 DATA <숫자>" + ChatColor.YELLOW + " - 해당 아이템의 DATA값을 설정합니다.");
				player.sendMessage(ChatColor.GOLD + "/아이템 개수 <숫자>" + ChatColor.YELLOW + " - 해당 아이템의 개수를 설정합니다.");
				player.sendMessage(ChatColor.GOLD + "/아이템 태그삭제" + ChatColor.YELLOW + " - [1.8.0은 안됨] 다이아몬드 검의 +7 공격피해와 같은 아이템 태그를 삭제합니다.");
				//player.sendMessage(ChatColor.GOLD + "/아이템 포션 [효과] [강도]" + ChatColor.YELLOW + " - 아이템에 포션 효과를 더합니다.");
				//player.sendMessage(ChatColor.GOLD + "/아이템 인챈트 [인챈트] [레벨]" + ChatColor.YELLOW + " - 아이템에 인챈트 효과를 더합니다.");
				player.sendMessage(ChatColor.GOLD + "/아이템 설명추가 <문자열>" + ChatColor.YELLOW + " - 해당 아이템의 로어 한 줄을 추가합니다.");
				player.sendMessage(ChatColor.GOLD + "/아이템 설명제거" + ChatColor.YELLOW + " - 해당 아이템의 모든 로어를 삭제합니다.");
				player.sendMessage(ChatColor.GREEN + "[아래와 같은 설명을 추가할 시, 아이템에 효과가 생깁니다.]");
				player.sendMessage(ChatColor.AQUA + "/아이템 설명추가 "+GoldBigDragon_RPG.Main.ServerOption.Damage+" : 3 ~ 6" + ChatColor.RED +" (아이템 장착시 "+GoldBigDragon_RPG.Main.ServerOption.Damage+" 3 ~ 6 상승)");
				player.sendMessage(ChatColor.AQUA + "/아이템 설명추가 방어 : 3" + ChatColor.RED +" (아이템 장착시 방어 3상승)");
				player.sendMessage(ChatColor.GREEN + "[추가 가능한 옵션 태그]");
				player.sendMessage(ChatColor.AQUA + "["+GoldBigDragon_RPG.Main.ServerOption.Damage+" : <숫자> ~ <숫자>] [방어 : <숫자>] [보호 : <숫자>]\n"
						+ "["+GoldBigDragon_RPG.Main.ServerOption.MagicDamage+" : <숫자> ~ <숫자>] [마법 방어 : <숫자>] [마법 보호 : <숫자>]\n"
						+ "["+GoldBigDragon_RPG.Main.ServerOption.STR+" : <숫자>] ["+GoldBigDragon_RPG.Main.ServerOption.DEX+" : <숫자>] ["+GoldBigDragon_RPG.Main.ServerOption.INT+" : <숫자>] ["+GoldBigDragon_RPG.Main.ServerOption.WILL+" : <숫자>] ["+GoldBigDragon_RPG.Main.ServerOption.LUK+" : <숫자>]\n"
						+ "[크리티컬 : <숫자>] [밸런스 : <숫자>] [내구도 : <숫자> / <숫자>] \n"
						+ "[업그레이드 : <숫자> / <숫자>] [생명력 : <숫자>] [마나 : <숫자>]\n"+ChatColor.GREEN+"[아이템 타입 태그] - 퀵슬롯 등록된 스킬의 무기 제한 옵션에 관여"+ChatColor.AQUA+"\n[소비] [근접 무기] [한손 검] [양손 검] [원거리 무기] [활] [석궁] [도끼] [낫] [마법 무기] [원드] [스태프]");
				player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
			}
			break;
			case 3:
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			  	YamlManager Config = YC.getNewConfig("config.yml");
				player.sendMessage(ChatColor.YELLOW+"────────────[파티 명령어]────────────");
				player.sendMessage(ChatColor.GOLD + "/파티 생성 <이름>" + ChatColor.YELLOW + " - 해당 이름의 파티를 생성합니다.");
				player.sendMessage(ChatColor.GOLD + "/파티 탈퇴" + ChatColor.YELLOW + " - 현재 파티를 탈퇴합니다.");
				player.sendMessage(ChatColor.GOLD + "/파티 목록" + ChatColor.YELLOW + " - 현재 개설된 파티 목록을 봅니다.");
				player.sendMessage(ChatColor.GOLD + "/파티 정보" + ChatColor.YELLOW + " - 현재 파티 정보를 봅니다.");
				player.sendMessage(ChatColor.GOLD + "/파티 제목 <파티제목>" + ChatColor.YELLOW + " - 현재 파티의 제목을 변경합니다.");
				player.sendMessage(ChatColor.GOLD + "/파티 리더 <닉네임>" + ChatColor.YELLOW + " - 해당 플레이어에게 리더 권한을 넘겨줍니다.");
				player.sendMessage(ChatColor.GOLD + "/파티 인원 <2-" + Config.getInt("Party.MaxPartyUnit") + ">" + ChatColor.YELLOW + " - 제한 인원을 설정합니다.");
				player.sendMessage(ChatColor.GOLD + "/파티 잠금"+ChatColor.YELLOW+" - 파티 참여 신청을 받거나 받지 않습니다.");
				player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
			}
			break;
			case 4:
			{
				player.sendMessage(ChatColor.YELLOW+"────────────[던전 명령어]────────────");
				player.sendMessage(ChatColor.GOLD + "/던전" + ChatColor.YELLOW + " - 던전 설정 GUI 창을 엽니다.");
				player.sendMessage(ChatColor.GOLD + "/던전 목록" + ChatColor.YELLOW + " - 현재 생성된 던전 목록을 봅니다.");
				player.sendMessage(ChatColor.GOLD + "/던전 생성 <이름>" + ChatColor.YELLOW + " - 던전을 생성합니다.");
				player.sendMessage(ChatColor.GOLD + "/던전 삭제 <이름>" + ChatColor.YELLOW + " - 던전을 삭제합니다.");
				player.sendMessage(ChatColor.GOLD + "/던전 이름 <던전이름> <이름>" + ChatColor.YELLOW + " - 던전 이름을 변경합니다.");
				player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
			}
			break;
			case 5:
			{
				player.sendMessage(ChatColor.YELLOW+"────────────[텔레포트 명령어]────────────");
				player.sendMessage(ChatColor.GOLD + "/워프 목록" + ChatColor.YELLOW + " - 워프 가능 지역 목록을 봅니다.");
				player.sendMessage(ChatColor.GOLD + "/워프 <지역 이름>" + ChatColor.YELLOW + " - 해당 이름으로 등록된 지점으로 이동합니다.");
				player.sendMessage(ChatColor.GOLD + "/워프 등록 <지역 이름>" + ChatColor.YELLOW + " - 현재 위치를 워프 지점으로 등록합니다.");
				player.sendMessage(ChatColor.GOLD + "/워프 삭제 <지역 이름>" + ChatColor.YELLOW + " - 해당 워프 지점을 삭제합니다.");
				player.sendMessage(ChatColor.GOLD + "/워프 제한 <지역 이름>" + ChatColor.YELLOW + " - 일반 유저가 이동 가능/불가능 하도록 설정합니다.");
				player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
			}
			break;
			case 6:
			{
				player.sendMessage(ChatColor.YELLOW+"────────────[지역 설정 명령어]────────────");
				player.sendMessage(ChatColor.GOLD + "/영역 목록" + ChatColor.YELLOW + " - 영역 목록을 확인합니다.");
				player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름>" + ChatColor.YELLOW + " - 해당 영역 설정창을 엽니다.");
				player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름> 생성" + ChatColor.YELLOW + " - 해당 이름을 가진 영역을 생성합니다.");
				player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름> 삭제" + ChatColor.YELLOW + " - 해당 이름을 가진 영역을 삭제합니다.");
				player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름> 이름 <문자열>" + ChatColor.YELLOW + " - 해당 구역의 알림 메시지에 보일 이름을 정합니다.");
				player.sendMessage(ChatColor.GOLD + "/영역 <영역 이름> 설명 <문자열>" + ChatColor.YELLOW + " - 해당 구역의 알림 메시지에 보일 부가 설명을 정합니다.");
				player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
			}
			break;
			case 7:
			{
				player.sendMessage(ChatColor.YELLOW+"────────────[상점 구성 명령어]────────────");
				player.sendMessage(ChatColor.GOLD + "/상점 판매 [가격]" + ChatColor.YELLOW + " - 근처에 있는 NPC에게 당신이 손에든 물건을 해당 가격에 판매하도록 합니다.");
				player.sendMessage(ChatColor.GOLD + "/상점 구매 [가격]" + ChatColor.YELLOW + " - 근처에 있는 NPC에게 당신이 손에든 물건을 해당 가격에 구매하도록 합니다.");
				player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
			}
			break;
			case 8:
			{
				player.sendMessage(ChatColor.YELLOW+"────────────[퀘스트 명령어]────────────");
				player.sendMessage(ChatColor.GOLD + "/퀘스트" + ChatColor.YELLOW + " - 현재 자신이 진행중인 퀘스트 목록을 열람합니다.");
				player.sendMessage(ChatColor.GOLD + "/퀘스트 구성" + ChatColor.YELLOW + " - 새로운 퀘스트를 만들거나, 기존의 퀘스트를 삭제합니다.");
				player.sendMessage(ChatColor.GOLD + "/퀘스트 생성 [타입] [이름]" + ChatColor.YELLOW + " - 새로운 퀘스트를 생성하며, 설정창으로 바로 넘어갑니다.");
				player.sendMessage(ChatColor.GREEN + "[일반 / 반복 / 일일 / 일주 / 한달]");
				player.sendMessage(ChatColor.YELLOW+"────────────────────────────────");
			}
			break;
		}
	}
}
