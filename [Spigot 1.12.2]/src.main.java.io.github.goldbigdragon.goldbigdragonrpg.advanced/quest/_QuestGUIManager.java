package quest;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _QuestGUIManager
{
	//Quest GUI Click Unique Number = 05
	//퀘스트 관련 GUI의 고유 번호는 05입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 퀘스트 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("00"))//개인 퀘스트 수행 목록 GUI
			new quest.QuestGui().MyQuestListGUIClick(event);
		else if(SubjectCode.equals("01"))//전체 퀘스트 수행 목록 GUI
			new quest.QuestGui().AllOfQuestListGUIClick(event);
		else if(SubjectCode.equals("02"))//퀘스트 흐름도 GUI
			new quest.QuestGui().FixQuestGUIClick(event);
		else if(SubjectCode.equals("03"))//퀘스트 오브젝트 추가 GUI
			new quest.QuestGui().SelectObjectPageClick(event);
		else if(SubjectCode.equals("04"))//스크립트 GUI
			new quest.QuestGui().QuestScriptTypeGUIClick(event);
		else if(SubjectCode.equals("05"))//퀘스트 옵션 GUI
			new quest.QuestGui().QuestOptionGUIClick(event);
		else if(SubjectCode.equals("06"))//수집 해야 할 아이템 등록 GUI
			new quest.QuestGui().GetterItemSetingGUIClick(event);
		else if(SubjectCode.equals("07"))//선물로 줄 아이템 등록 GUI
			new quest.QuestGui().PresentItemSettingGUIClick(event);
		else if(SubjectCode.equals("08"))//보상, 모아야 할 아이템, 사냥 해야 할 몬스터, 채집 해야 할 블록 목록 GUI
			new quest.QuestGui().ShowNeedGUIClick(event);
		else if(SubjectCode.equals("09"))//등록을 계속 할 것인지 묻는 GUI
			new quest.QuestGui().KeepGoingClick(event);
		else if(SubjectCode.equals("0a"))//퀘스트 네비게이션 GUI
			new quest.QuestGui().Quest_NavigationListGUIClick(event);
		else if(SubjectCode.equals("0b"))//어드민 입장의 퀘스트 선택 GUI
			new quest.QuestGui().Quest_OPChoiceClick(event);
		else if(SubjectCode.equals("0c"))//유저 입장의 퀘스트 선택 GUI
			new quest.QuestGui().Quest_UserChoiceClick(event);
	}

	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("06"))//수집 해야 할 아이템 등록 GUI
			new quest.QuestGui().GetterItemSetingGUIClose(event);
		else if(SubjectCode.equals("07"))//선물로 줄 아이템 등록 GUI
			new quest.QuestGui().PresentItemSettingGUIClose(event);
	}
}
