package skill;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _SkillGUIManager
{
	//Skill GUI Click Unique Number = 0b
	//스킬 관련 GUI의 고유 번호는 0b입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 스킬 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("00"))//전체 스킬 목록
			new skill.OPboxSkillGui().AllSkillsGUIClick(event);
		else if(SubjectCode.equals("01"))//스킬 관리
			new skill.OPboxSkillGui().IndividualSkillOptionGUIClick(event);
		else if(SubjectCode.equals("02"))//스킬 랭크별 관리
			new skill.OPboxSkillGui().SkillRankOptionGUIClick(event);
		else if(SubjectCode.equals("03"))//직업군 선택
			new skill.UserSkillGui().MapleStory_MainSkillsListGUIClick(event);
		else if(SubjectCode.equals("04"))//카테고리 선택
			new skill.UserSkillGui().Mabinogi_MainSkillsListGUIClick(event);
		else if(SubjectCode.equals("05"))//보유 스킬 목록
			new skill.UserSkillGui().SkillListGUIClick(event);
		else if(SubjectCode.equals("06"))//퀵슬롯 등록
			new skill.UserSkillGui().AddQuickBarGUIClick(event);
		else if(SubjectCode.equals("07"))//모든 매직스펠 보기
			new otherplugins.SpellMain().ShowAllMaigcGUIClick(event);
	}
}
