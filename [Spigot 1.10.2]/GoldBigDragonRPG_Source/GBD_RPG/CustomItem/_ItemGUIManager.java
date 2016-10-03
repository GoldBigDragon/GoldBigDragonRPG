package GBD_RPG.CustomItem;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _ItemGUIManager
{
	//Item GUI Click Unique Number = 03
	//아이템 관련 GUI의 고유 번호는 03입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 아이템 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("00")==0)//아이템 목록
			new GBD_RPG.CustomItem.CustomItem_GUI().ItemListInventoryclick(event);
		else if(SubjectCode.compareTo("01")==0)//아이템 설정
			new GBD_RPG.CustomItem.CustomItem_GUI().NewItemGUIclick(event);
		else if(SubjectCode.compareTo("02")==0)//아이템 직업 제한
			new GBD_RPG.CustomItem.CustomItem_GUI().JobGUIClick(event);
		else if(SubjectCode.compareTo("03")==0)//소모성 아이템 목록
			new GBD_RPG.CustomItem.UseableItem_GUI().UseableItemListGUIClick(event);
		else if(SubjectCode.compareTo("04")==0)//소모성 아이템 타입 선택
			new GBD_RPG.CustomItem.UseableItem_GUI().ChooseUseableItemTypeGUIClick(event);
		else if(SubjectCode.compareTo("05")==0)//소모성 아이템 설정
			new GBD_RPG.CustomItem.UseableItem_GUI().NewUseableItemGUIclick(event);
		else if(SubjectCode.compareTo("06")==0)//스킬북 스킬 등록
			new GBD_RPG.CustomItem.UseableItem_GUI().SelectSkillGUIClick(event);
	}
}
