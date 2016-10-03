package GBD_RPG.Quest;

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
		if(SubjectCode.compareTo("00")==0)//개인 퀘스트 수행 목록 GUI
			new GBD_RPG.Quest.Quest_GUI().MyQuestListGUIClick(event);
		else if(SubjectCode.compareTo("01")==0)//전체 퀘스트 수행 목록 GUI
			new GBD_RPG.Quest.Quest_GUI().AllOfQuestListGUIClick(event);
		else if(SubjectCode.compareTo("02")==0)//퀘스트 흐름도 GUI
			new GBD_RPG.Quest.Quest_GUI().FixQuestGUIClick(event);
		else if(SubjectCode.compareTo("03")==0)//퀘스트 오브젝트 추가 GUI
			new GBD_RPG.Quest.Quest_GUI().SelectObjectPageClick(event);
		else if(SubjectCode.compareTo("04")==0)//스크립트 GUI
			new GBD_RPG.Quest.Quest_GUI().QuestScriptTypeGUIClick(event);
		else if(SubjectCode.compareTo("05")==0)//퀘스트 옵션 GUI
			new GBD_RPG.Quest.Quest_GUI().QuestOptionGUIClick(event);
		else if(SubjectCode.compareTo("06")==0)//수집 해야 할 아이템 등록 GUI
			new GBD_RPG.Quest.Quest_GUI().GetterItemSetingGUIClick(event);
		else if(SubjectCode.compareTo("07")==0)//선물로 줄 아이템 등록 GUI
			new GBD_RPG.Quest.Quest_GUI().PresentItemSettingGUIClick(event);
		else if(SubjectCode.compareTo("08")==0)//보상, 모아야 할 아이템, 사냥 해야 할 몬스터, 채집 해야 할 블록 목록 GUI
			new GBD_RPG.Quest.Quest_GUI().ShowNeedGUIClick(event);
		else if(SubjectCode.compareTo("09")==0)//등록을 계속 할 것인지 묻는 GUI
			new GBD_RPG.Quest.Quest_GUI().KeepGoingClick(event);
		else if(SubjectCode.compareTo("0a")==0)//퀘스트 네비게이션 GUI
			new GBD_RPG.Quest.Quest_GUI().Quest_NavigationListGUIClick(event);
		else if(SubjectCode.compareTo("0b")==0)//어드민 입장의 퀘스트 선택 GUI
			new GBD_RPG.Quest.Quest_GUI().Quest_OPChoiceClick(event);
		else if(SubjectCode.compareTo("0c")==0)//유저 입장의 퀘스트 선택 GUI
			new GBD_RPG.Quest.Quest_GUI().Quest_UserChoiceClick(event);
	}

	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("06")==0)//수집 해야 할 아이템 등록 GUI
			new GBD_RPG.Quest.Quest_GUI().GetterItemSetingGUIClose(event);
		else if(SubjectCode.compareTo("07")==0)//선물로 줄 아이템 등록 GUI
			new GBD_RPG.Quest.Quest_GUI().PresentItemSettingGUIClose(event);
	}
}
