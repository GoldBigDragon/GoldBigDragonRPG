package customitem.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _CustomItemGUIManager
{
	//Item GUI Click Unique Number = 03
	//아이템 관련 GUI의 고유 번호는 03입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 아이템 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void clickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("00"))//아이템 목록
			new EquipItemListGui().itemListClick(event);
		else if(SubjectCode.equals("01"))//아이템 설정
			new EquipItemForgingGui().itemForgingClick(event);
		else if(SubjectCode.equals("02"))//아이템 직업 제한
			new RestrictJobSelectGui().restrictJobSelectClick(event);
		else if(SubjectCode.equals("03"))//소모성 아이템 목록
			new UseableItemListGui().useableItemListClick(event);
		else if(SubjectCode.equals("04"))//소모성 아이템 타입 선택
			new UseableItemTypeSelectGui().useableItemTypeSelectClick(event);
		else if(SubjectCode.equals("05"))//소모성 아이템 설정
			new UseableItemMakingGui().useableItemMakingClick(event);
		else if(SubjectCode.equals("06"))//스킬북 스킬 등록
			new SelectSkillGui().selectSkillGuiClick(event);
		else if(SubjectCode.equals("07"))//공구 사용
			new ApplyToolGui().applyToolClick(event);
	}

	public void closeRouting(InventoryCloseEvent event, String subjectCode)
	{
		if(subjectCode.equals("07"))//공구 사용
			new ApplyToolGui().applyToolClose(event);
	}
}