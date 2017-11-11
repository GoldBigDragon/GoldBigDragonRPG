package warp;

import org.bukkit.event.inventory.InventoryClickEvent;

public class _WarpGUIManager
{
	//Warp GUI Click Unique Number = 0c
	//워프 관련 GUI의 고유 번호는 0c입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 워프 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("00"))//워프 목록GUI
			new warp.Warp_GUI().WarpListGUIInventoryclick(event);
	}
}
