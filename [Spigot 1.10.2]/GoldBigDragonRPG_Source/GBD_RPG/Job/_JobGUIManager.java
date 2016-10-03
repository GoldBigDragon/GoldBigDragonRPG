package GBD_RPG.Job;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _JobGUIManager
{
	//Job GUI Click Unique Number = 06
	//직업 관련 GUI의 고유 번호는 06입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 직업 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("00")==0)//직업 시스템 선택
			new GBD_RPG.Job.Job_GUI().ChooseSystemGUIClick(event);
		else if(SubjectCode.compareTo("01")==0)//메이플 스토리 형식 전체 직업 목록
			new GBD_RPG.Job.Job_GUI().MapleStory_ChooseJobClick(event);
		else if(SubjectCode.compareTo("02")==0)//메이플 스토리 형식 전체 직업 설정
			new GBD_RPG.Job.Job_GUI().MapleStory_JobSettingClick(event);
		else if(SubjectCode.compareTo("03")==0)//마비노기 전체 카테고리 목록
			new GBD_RPG.Job.Job_GUI().Mabinogi_ChooseCategoryClick(event);
		else if(SubjectCode.compareTo("04")==0)//마비노기 스킬 관리
			new GBD_RPG.Job.Job_GUI().Mabinogi_SkillSettingClick(event);
		else if(SubjectCode.compareTo("05")==0)//등록된 스킬 관리
			new GBD_RPG.Job.Job_GUI().AddedSkillsListGUIClick(event);
	}
}
